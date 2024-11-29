package jp.groupsession.v2.convert;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.co.sjts.util.log.log4j.Log4jConfig;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.convert.convert202.db.DBConverter;
import jp.groupsession.v2.convert.convert202.user.position.PositionConverter;
import jp.groupsession.v2.convert.convert203.dao.BbsThreViewIndexDao;
import jp.groupsession.v2.convert.dao.VersionDao;
import jp.groupsession.v2.convert.model.VersionModel;

/**
 * <br>[機  能] バージョンアップ時のデータコンバートを行うListener
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvertGsListenerImpl {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ConvertGsListenerImpl.class);

    /**
     * <br>[機  能] コンバートチェック、コンバートを行う
     * <br>[解  説] Servlet init()時に実行される
     * <br>[備  考]
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws Exception 実行時例外
     */
    public void gsInit(GSContext gscontext, Connection con, String domain) throws Exception {
        log__.info("コンバート：バージョンチェック開始");

        //バージョンのチェック
        VersionDao vDao = new VersionDao(con);
        VersionModel vMdl = vDao.select();

        if (vMdl == null) {
            //バージョン情報が取得できない場合はコンバートを行う
            vMdl = new VersionModel();
        }
        log__.info("DBバージョン = " + vMdl.getVerVersion());
        log__.info("APPバージョン = " + GSConst.VERSION);

        String version = NullDefault.getString(vMdl.getVerVersion(), "");
        log__.info("コンバート：バージョンチェック完了");

        if (version.equals(GSConst.VERSION)) {
            //DBのバージョン情報が現バージョンと同じ場合は処理終了
            log__.info("コンバート：必要なし");
            return;
        }
        log__.info("コンバート：必要あり");

        log__.info("コンバート実行");
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);
        try {
            //H2 databaseの場合、QUERY_TIMEOUTを無制限に設定する
            if (h2db) {
                __setQueryTimeout(con, 0);
            }

            //コンバートを行う
            __convert(con, gscontext, version, domain);

        } finally {

            //H2 databaseの場合、QUERY_TIMEOUTを元に戻す
            if (h2db) {
                ResourceBundle optionResource = ResourceBundle.getBundle("connectOption");
                int queryTimeout = Integer.parseInt(optionResource.getString("QUERY_TIMEOUT"));
                __setQueryTimeout(con, queryTimeout);
            }
        }

        log__.info("コンバート完了");
    }

    /**
     * <br>[機  能] コンバートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gscontext 基本情報
     * @param version バージョン
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     * @throws ServletException 採番コントローラ取得失敗
     */
    private void __convert(Connection con, GSContext gscontext, String version, String domain)
    throws SQLException, IOToolsException, IOException, ServletException {

        log__.info("-- コンバート開始 --");
        boolean commitFlg = false;

        MlCountMtController saiban;
        try {
            saiban = GroupSession.getResourceManager().getCountController(domain, gscontext);
        } catch (Exception e) {
            log__.error("採番コントローラの取得に失敗。", e);
            throw new ServletException(e);
        }
        //MlCountMtController saiban = (MlCountMtController) tmp;
        try {

            con.setAutoCommit(false);

            if (StringUtil.isNullZeroString(version)) {
                version = ConvertConst.VERSION200;
            }

            if (version.equals(ConvertConst.VERSION200)
                    || version.equals(ConvertConst.VERSION201)) {
                //v2.0.0 or v2.0.1からv2.0.2へコンバート
                log__.info("v2.0.0 or v2.0.1からv2.0.2へコンバート");
                __convert202(con, saiban);
                version = ConvertConst.VERSION202;
            }

            //v2.0.2からv2.0.3へコンバート
            if (version.equals(ConvertConst.VERSION202)) {
                log__.info("v2.0.2からv2.0.3へコンバート");
                __convert203(con);
                version = ConvertConst.VERSION203;
            }

            //v2.0.3からv2.1.0へコンバート
            if (version.equals(ConvertConst.VERSION203)) {
                log__.info("v2.0.3からv2.1.0へコンバート");
                __convert210(con);
                version = ConvertConst.VERSION210;
            }

            //v2.1.0(beta版含む)からv2.1.1へコンバート
            if (version.equals(ConvertConst.VERSION210)) {
                log__.info("v2.1.0(beta版含む)からv2.1.1へコンバート");
                __convert211(con);
                version = ConvertConst.VERSION211;
            }

            //v2.1.1からv2.1.2へコンバート
            if (version.equals(ConvertConst.VERSION211)) {
                log__.info("v2.1.1からv2.1.2へコンバート");
                __convert212(con);
                version = ConvertConst.VERSION212;
            }

            //v2.1.2からv2.1.3へコンバート
            if (version.equals(ConvertConst.VERSION212)) {
                log__.info("v2.1.2からv2.1.3へコンバート");
                __convert213(con);
                version = ConvertConst.VERSION213;
            }
            //v2.1.3からv2.2.0へコンバート
            if (version.equals(ConvertConst.VERSION213)) {
                log__.info("v2.1.3からv2.2.0へコンバート");
                __convert220(con);
                version = ConvertConst.VERSION220;
            }

            //v2.2.0からv2.2.1へコンバート
            if (version.equals(ConvertConst.VERSION220)) {
                log__.info("v2.2.0からv2.2.1へコンバート");
                __convert221(con);
                version = ConvertConst.VERSION221;
            }

            //v2.2.1からv2.3.0へコンバート
            if (version.equals(ConvertConst.VERSION221)) {
                log__.info("v2.2.1からv2.3.0へコンバート");
                __convert230(con);
                version = ConvertConst.VERSION230;
            }
            //v2.3.0からv2.3.1へコンバート
            if (version.equals(ConvertConst.VERSION230)) {
                log__.info("v2.3.0からv2.3.1へコンバート");
                __convert231(con);
                version = ConvertConst.VERSION231;
            }
            //v2.3.1からv2.4.0へコンバート
            if (version.equals(ConvertConst.VERSION231)) {
                log__.info("v2.3.1からv2.4.0へコンバート");
                __convert240(saiban, gscontext, con);
                version = ConvertConst.VERSION240;
            }
            //v2.4.0からv2.4.1へコンバート
            if (version.equals(ConvertConst.VERSION240)) {
                log__.info("v2.4.0からv2.4.1へコンバート");
                __convert241(con);
                version = ConvertConst.VERSION241;
            }
            //v2.4.1からv2.5.0へコンバート
            if (version.equals(ConvertConst.VERSION241)) {
                log__.info("v2.4.1からv2.5.0へコンバート");
                __convert250(con);
                version = ConvertConst.VERSION250;
            }
            //v2.5.0からv2.5.1へコンバート
            if (version.equals(ConvertConst.VERSION250)) {
                log__.info("v2.5.0からv2.5.1へコンバート");
                __convert251(con);
                version = ConvertConst.VERSION251;
            }
            //v2.5.1からv2.5.2へコンバート
            if (version.equals(ConvertConst.VERSION251)) {
                log__.info("v2.5.1からv2.5.2へコンバート");
                __convert252(con);
                version = ConvertConst.VERSION252;
            }
            //v2.5.2からv2.5.3へコンバート
            if (version.equals(ConvertConst.VERSION252)) {
                log__.info("v2.5.2からv2.5.3へコンバート");
                __convert253(con);
                version = ConvertConst.VERSION253;
            }

            //v2.5.3からv3.0.0へコンバート
            if (version.equals(ConvertConst.VERSION253)) {
                log__.info("v2.5.3からv3.0.0へコンバート");
                __convert300(con);
                version = ConvertConst.VERSION300;
            }

            //v3.0.0からv3.0.1へコンバート
            if (version.equals(ConvertConst.VERSION300)) {
                log__.info("v3.0.0からv3.0.1へコンバート");
                __convert301(con, saiban);
                version = ConvertConst.VERSION301;
            }

            //v3.0.1からv3.0.2へコンバート
            if (version.equals(ConvertConst.VERSION301)) {
                log__.info("v3.0.1からv3.0.2へコンバート");
                __convert302(con, saiban);
                version = ConvertConst.VERSION302;
            }

            //v3.0.2からv3.0.3へコンバート
            if (version.equals(ConvertConst.VERSION302)) {
                log__.info("v3.0.2からv3.0.3へコンバート");
                __convert303(con, saiban);
                version = ConvertConst.VERSION303;
            }

            //v3.0.3からv3.1.0へコンバート
            if (version.equals(ConvertConst.VERSION303)) {
                log__.info("v3.0.3からv3.1.0へコンバート");
                __convert310(con, saiban);
                version = ConvertConst.VERSION310;
            }

            //v3.1.0からv3.2.0へコンバート
            if (version.equals(ConvertConst.VERSION310)) {
                log__.info("v3.1.0からv3.2.0へコンバート");
                __convert320(con, saiban);
                version = ConvertConst.VERSION320;
            }

            //v3.2.0からv3.2.1へコンバート
            if (version.equals(ConvertConst.VERSION320)) {
                log__.info("v3.2.0からv3.2.1へコンバート");
                __convert321(con, saiban);
                version = ConvertConst.VERSION321;
            }

            //v3.2.1からv3.5.0へコンバート
            if (version.equals(ConvertConst.VERSION321)) {

                //v3.5.0へのコンバート前に一度コミットする
                con.commit();

                log__.info("v3.2.1からv3.5.0へコンバート");
                __convert350(con, saiban, gscontext);
                version = ConvertConst.VERSION350;
            }

            //v3.5.0からv3.5.1へコンバート
            if (version.equals(ConvertConst.VERSION350)) {

                log__.info("v3.5.0からv3.5.1へコンバート");
                __convert351(con, saiban, gscontext);
                version = ConvertConst.VERSION351;
            }

            //v3.5.1からv3.5.2へコンバート
            if (version.equals(ConvertConst.VERSION351)) {

                //ポータル関連のテーブルが存在する場合はv4.0.0として扱う
                jp.groupsession.v2.convert.convert400.dao.ConvTableDao ctDao400 =
                    new jp.groupsession.v2.convert.convert400.dao.ConvTableDao(con);

                if (ctDao400.checkPortal(con)) {
                    log__.info("ポータルデータが存在するため、v4.0.0として扱う");
                    version = ConvertConst.VERSION400;
                } else {
                    log__.info("v3.5.1からv3.5.2へコンバート");
                    __convert352(con);
                    version = ConvertConst.VERSION352;
                }
            }

            //v3.5.2からv3.5.3へコンバート
            if (version.equals(ConvertConst.VERSION352)) {

                log__.info("v3.5.2からv3.5.3へコンバート");
                __convert353(con);
                version = ConvertConst.VERSION353;
            }

            //v3.5.3からv3.5.4へコンバート
            if (version.equals(ConvertConst.VERSION353)) {

                log__.info("v3.5.3からv3.5.4へコンバート");
                __convert354(con);
                version = ConvertConst.VERSION354;
            }

            //v3.5.4からv3.5.5へコンバート
            if (version.equals(ConvertConst.VERSION354)) {

                //ポータル関連のテーブルが存在する場合はv4.0.0として扱う
                jp.groupsession.v2.convert.convert400.dao.ConvTableDao ctDao400 =
                    new jp.groupsession.v2.convert.convert400.dao.ConvTableDao(con);

                if (ctDao400.checkPortal(con)) {
                    log__.info("ポータルデータが存在するため、v4.0.0として扱う");
                    version = ConvertConst.VERSION400;
                } else {
                    log__.info("v3.5.4からv3.5.5へコンバート");
                    __convert355(con);
                    version = ConvertConst.VERSION355;
                }
            }

            //v3.5.5からv3.5.6へコンバート
            if (version.equals(ConvertConst.VERSION355)) {

                log__.info("v3.5.5からv3.5.6へコンバート");
                __convert356(con);
                version = ConvertConst.VERSION356;
            }

            //v3.5.6からv4.0.0へコンバート
            if (version.equals(ConvertConst.VERSION356)) {

                log__.info("v3.5.6からv4.0.0へコンバート");
                __convert400(con, saiban);
                version = ConvertConst.VERSION400;
            }

            //v4.0.0からv4.0.1へコンバート
            if (version.equals(ConvertConst.VERSION400)) {

                log__.info("v4.0.0からv4.0.1へコンバート");
                __convert401(con);
                version = ConvertConst.VERSION401;
            }

            //v4.0.1からv4.0.2へコンバート
            if (version.equals(ConvertConst.VERSION401)) {

                log__.info("v4.0.1からv4.0.2へコンバート");
                __convert402(con);
                version = ConvertConst.VERSION402;
            }

            //v4.0.2からv4.0.3へコンバート
            if (version.equals(ConvertConst.VERSION402)) {

                log__.info("v4.0.2からv4.0.3へコンバート");
                __convert403(con);
                version = ConvertConst.VERSION403;
            }

            //v4.0.3からv4.0.4へコンバート
            if (version.equals(ConvertConst.VERSION403)) {

                log__.info("v4.0.3からv4.0.4へコンバート");
                __convert404(con, saiban);
                version = ConvertConst.VERSION404;
            }

            //v4.0.4からv4.0.5へコンバート
            if (version.equals(ConvertConst.VERSION404)) {

                log__.info("v4.0.4からv4.0.5へコンバート");
                __convert405(con, saiban);
                version = ConvertConst.VERSION405;
            }

            //v4.0.5からv4.0.6へコンバート
            if (version.equals(ConvertConst.VERSION405)) {

                log__.info("v4.0.5からv4.0.6へコンバート");
                __convert406(con);
                version = ConvertConst.VERSION406;
            }

            //v4.0.6からv4.0.7へコンバート
            if (version.equals(ConvertConst.VERSION406)) {

                log__.info("v4.0.6からv4.0.7へコンバート");
                __convert407(con);
                version = ConvertConst.VERSION407;
            }

            //v4.0.7からv4.0.8へコンバート
            if (version.equals(ConvertConst.VERSION407)) {

                log__.info("v4.0.7からv4.0.8へコンバート");
                __convert408(con);
                version = ConvertConst.VERSION408;
            }

            //v4.0.8からv4.1.0へコンバート
            if (version.equals(ConvertConst.VERSION408)) {

                log__.info("v4.0.8からv4.1.0へコンバート");
                __convert410(con, saiban);
                version = ConvertConst.VERSION410;
            }

            //v4.1.0からv4.1.1へコンバート
            if (version.equals(ConvertConst.VERSION410)) {

                log__.info("v4.1.0からv4.1.1へコンバート");
                __convert411(con);
                version = ConvertConst.VERSION411;
            }

            //v4.1.1からv4.1.2へコンバート
            if (version.equals(ConvertConst.VERSION411)) {

                log__.info("v4.1.1からv4.1.2へコンバート");
                __convert412(con);
                version = ConvertConst.VERSION412;
            }

            //v4.1.2からv4.1.3へコンバート
            if (version.equals(ConvertConst.VERSION412)) {

                log__.info("v4.1.2からv4.1.3へコンバート");
                __convert413(con);
                version = ConvertConst.VERSION413;
            }

            //v4.1.3からv4.1.4へコンバート
            if (version.equals(ConvertConst.VERSION413)) {

                log__.info("v4.1.3からv4.1.4へコンバート");
                __convert414(con);
                version = ConvertConst.VERSION414;
            }

            //v4.1.4からv4.2.0へコンバート
            if (version.equals(ConvertConst.VERSION414)) {

                log__.info("v4.1.4からv4.2.0へコンバート");
                __convert420(con, saiban);
                version = ConvertConst.VERSION420;
            }

            //v4.2.0からv4.2.1へコンバート
            if (version.equals(ConvertConst.VERSION420)) {

                log__.info("v4.2.0からv4.2.1へコンバート");
                __convert421(con, saiban);
                version = ConvertConst.VERSION421;
            }

            //v4.2.1からv4.2.2へコンバート
            if (version.equals(ConvertConst.VERSION421)) {
                log__.info("v4.2.1からv4.2.2へコンバート");
                //バージョン情報をv4.2.2に更新
                version = ConvertConst.VERSION422;
                __updateVersion(con, version);
            }

            //v4.2.2からv4.2.3へコンバート
            if (version.equals(ConvertConst.VERSION422)) {
                log__.info("v4.2.2からv4.2.3へコンバート");
                //バージョン情報をv4.2.3に更新
                version = ConvertConst.VERSION423;
                __updateVersion(con, version);
            }

            //v4.2.3からv4.2.4へコンバート
            if (version.equals(ConvertConst.VERSION423)) {
                log__.info("v4.2.3からv4.2.4へコンバート");
                __convert424(con);
                version = ConvertConst.VERSION424;
            }

            //v4.2.4からv4.2.5へコンバート
            if (version.equals(ConvertConst.VERSION424)) {
                log__.info("v4.2.4からv4.2.5へコンバート");
                //バージョン情報をv4.2.5に更新
                version = ConvertConst.VERSION425;
                __updateVersion(con, version);
            }

            //v4.2.5からv4.2.6へコンバート
            if (version.equals(ConvertConst.VERSION425)) {
                log__.info("v4.2.5からv4.2.6へコンバート");
                //バージョン情報をv4.2.6に更新
                version = ConvertConst.VERSION426;
                __updateVersion(con, version);
            }

            //v4.2.6からv4.2.7へコンバート
            if (version.equals(ConvertConst.VERSION426)) {
                log__.info("v4.2.6からv4.2.7へコンバート");
                //バージョン情報をv4.2.7に更新
                version = ConvertConst.VERSION427;
                __updateVersion(con, version);
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLExceptionが発生しました。\r\n原因: ", e);
            log__.error("データのコンバートに失敗");
            throw e;
        } catch (IOToolsException e) {
            log__.error("IOToolsExceptionが発生しました。\r\n原因: ", e);
            log__.error("データのコンバートに失敗");
            throw e;
        } catch (IOException e) {
            log__.error("IOExceptionが発生しました。\r\n原因: ", e);
            log__.error("データのコンバートに失敗");
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //V4.3以降
        try {
            //v4.2.7からv4.3.0へコンバート
            //ここからはAutoCommit trueで処理を実行する
            con.setAutoCommit(true);
            if (version.equals(ConvertConst.VERSION427)) {
                log__.info("v4.2.7からv4.3.0へコンバートを開始します。");
                __convert430(con, saiban);
                version = ConvertConst.VERSION430;
                __updateVersion(con, version);
                log__.info("4.2.7からv4.3.0のコンバートが完了しました。");
            }

            //v4.3.0からv4.3.1へコンバート
            if (version.equals(ConvertConst.VERSION430)) {
                log__.info("v4.3.0からv4.3.1へコンバート");
                //バージョン情報をv4.3.1に更新
                version = ConvertConst.VERSION431;
                __updateVersion(con, version);
            }

            //v4.3.1からv4.5.0へコンバート
            if (version.equals(ConvertConst.VERSION431)) {
                log__.info("v4.3.1からv4.5.0へコンバート");
                __convert450(con);
                version = ConvertConst.VERSION450;
                __updateVersion(con, version);
            }

            //v4.5.0からv4.5.1へコンバート
            if (version.equals(ConvertConst.VERSION450)) {
                log__.info("v4.5.0からv4.5.1へコンバート");
                __convert451(con);
                version = ConvertConst.VERSION451;
                __updateVersion(con, version);
            }

            //v4.5.1からv4.5.2へコンバート
            if (version.equals(ConvertConst.VERSION451)) {
                log__.info("v4.5.1からv4.5.2へコンバート");
                __convert452(con);
                version = ConvertConst.VERSION452;
                __updateVersion(con, version);
            }

            //v4.5.2からv4.5.3へコンバート
            if (version.equals(ConvertConst.VERSION452)) {
                log__.info("v4.5.2からv4.5.3へコンバート");
                __convert453(con);
                version = ConvertConst.VERSION453;
                __updateVersion(con, version);
            }

            //v4.5.3からv4.5.4へコンバート
            if (version.equals(ConvertConst.VERSION453)) {
                log__.info("v4.5.3からv4.5.4へコンバート");
                __convert454(con);
                version = ConvertConst.VERSION454;
                __updateVersion(con, version);
            }

            //v4.5.4からv4.6.0へコンバート
            if (version.equals(ConvertConst.VERSION454)) {
                log__.info("v4.5.4からv4.6.0へコンバート");
                __convert460(con);
                version = ConvertConst.VERSION460;
                __updateVersion(con, version);
            }
            //v4.6.0からv4.6.1へコンバート
            if (version.equals(ConvertConst.VERSION460)) {
                log__.info("v4.6.0からv4.6.1へコンバート");
                __convert461(con);
                version = ConvertConst.VERSION461;
                __updateVersion(con, version);
            }
            //v4.6.1からv4.6.2へコンバート
            if (version.equals(ConvertConst.VERSION461)) {
                log__.info("v4.6.1からv4.6.2へコンバート");
                __convert462(con, saiban);
                version = ConvertConst.VERSION462;
                __updateVersion(con, version);
            }
            //v4.6.2からv4.6.3へコンバート
            if (version.equals(ConvertConst.VERSION462)) {
                log__.info("v4.6.2からv4.6.3へコンバート");
                __convert463(con, saiban);
                version = ConvertConst.VERSION463;
                __updateVersion(con, version);
            }
          //4.6.3から4.6.4へコンバート
            if (version.equals(ConvertConst.VERSION463)) {
                log__.info("v4.6.3からv4.6.4へコンバート");
                __convert464(con, saiban);
                version = ConvertConst.VERSION464;
                __updateVersion(con, version);
            }
            //4.6.4から4.7.0へコンバート
            if (version.equals(ConvertConst.VERSION464)) {
                log__.info("v4.6.4からv4.7.0へコンバート");
                __convert470(con, saiban);
                version = ConvertConst.VERSION470;
                __updateVersion(con, version);
            }
            //4.7.0から4.7.1へコンバート
            if (version.equals(ConvertConst.VERSION470)) {
                log__.info("v4.7.0からv4.7.1へコンバート");
                __convert471(con, saiban);
                version = ConvertConst.VERSION471;
                __updateVersion(con, version);
            }
            //4.7.1から4.7.2へコンバート
            if (version.equals(ConvertConst.VERSION471)) {
                log__.info("v4.7.1からv4.7.2へコンバート");
                __convert472(con, saiban);
                version = ConvertConst.VERSION472;
                __updateVersion(con, version);
            }
            //4.7.2から4.8.0へコンバート
            if (version.equals(ConvertConst.VERSION472)) {
                log__.info("v4.7.2からv4.8.0へコンバート");
                __convert480(con, saiban);
                version = ConvertConst.VERSION480;
                __updateVersion(con, version);
            }
            //4.8.0から4.8.1へコンバート
            if (version.equals(ConvertConst.VERSION480)) {
                log__.info("v4.8.0からv4.8.1へコンバート");
                __convert481(con, saiban);
                version = ConvertConst.VERSION481;
                __updateVersion(con, version);
            }
            //4.8.1から4.8.2へコンバート
            if (version.equals(ConvertConst.VERSION481)) {
                log__.info("v4.8.1からv4.8.2へコンバート");
                __convert482(con, saiban);
                version = ConvertConst.VERSION482;
                __updateVersion(con, version);
            }
            //4.8.2から4.9.0へコンバート
            if (version.equals(ConvertConst.VERSION482)) {
                log__.info("v4.8.2からv4.9.0へコンバート");
                __convert490(con, saiban);
                version = ConvertConst.VERSION490;
                __updateVersion(con, version);
            }
            //4.9.0から4.9.1へコンバート
            if (version.equals(ConvertConst.VERSION490)) {
                log__.info("v4.9.0からv4.9.1へコンバート");
                __convert491(con, saiban);
                version = ConvertConst.VERSION491;
                __updateVersion(con, version);
            }
            //4.9.1から4.9.2へコンバート
            if (version.equals(ConvertConst.VERSION491)) {
                log__.info("v4.9.1からv4.9.2へコンバート");
                __convert492(con, saiban);
                version = ConvertConst.VERSION492;
                __updateVersion(con, version);
            }
            //4.9.2から4.9.3へコンバート
            if (version.equals(ConvertConst.VERSION492)) {
                log__.info("v4.9.2からv4.9.3へコンバート");
                __convert493(con, saiban);
                version = ConvertConst.VERSION493;
                __updateVersion(con, version);
            }
            //4.9.3から4.9.4へコンバート
            if (version.equals(ConvertConst.VERSION493)) {
                log__.info("v4.9.3からv4.9.4へコンバート");
                __convert494(con, saiban);
                version = ConvertConst.VERSION494;
                __updateVersion(con, version);
            }
            //4.9.4から4.9.5へコンバート
            if (version.equals(ConvertConst.VERSION494)) {
                log__.info("v4.9.4からv4.9.5へコンバート");
                __convert495(con, saiban);
                version = ConvertConst.VERSION495;
                __updateVersion(con, version);
            }
            //4.9.5から4.9.6へコンバート
            if (version.equals(ConvertConst.VERSION495)) {
                log__.info("v4.9.5からv4.9.6へコンバート");
                version = ConvertConst.VERSION496;
                __updateVersion(con, version);
            }
            //4.9.6から4.9.7へコンバート
            if (version.equals(ConvertConst.VERSION496)) {
                log__.info("v4.9.6からv4.9.7へコンバート");
                __convert497(con, saiban);
                version = ConvertConst.VERSION497;
                __updateVersion(con, version);
            }
            //4.9.7から4.9.8へコンバート
            if (version.equals(ConvertConst.VERSION497)) {
                log__.info("v4.9.7からv4.9.8へコンバート");
                version = ConvertConst.VERSION498;
                __updateVersion(con, version);
            }
            //4.9.8から4.9.9へコンバート
            if (version.equals(ConvertConst.VERSION498)) {
                log__.info("v4.9.8からv4.9.9へコンバート");
                __convert499(con, saiban);
                version = ConvertConst.VERSION499;
                __updateVersion(con, version);
            }
            //4.9.9から5.0.0へコンバート
            if (version.equals(ConvertConst.VERSION499)) {
                log__.info("v4.9.9からv5.0.0へコンバート");
                __convert500(con, saiban);
                version = ConvertConst.VERSION500;
                __updateVersion(con, version);
            }
            //5.0.0から5.1.0へコンバート
            if (version.equals(ConvertConst.VERSION500)) {
                log__.info("v5.0.0からv5.1.0へコンバート");
                __convert510(con, saiban);
                version = ConvertConst.VERSION510;
                __updateVersion(con, version);
            }
            //5.1.0から5.1.1へコンバート
            if (version.equals(ConvertConst.VERSION510)) {
                log__.info("v5.1.0からv5.1.1へコンバート");
                version = ConvertConst.VERSION511;
                __updateVersion(con, version);
            }
            //5.1.1から5.1.2へコンバート
            if (version.equals(ConvertConst.VERSION511)) {
                log__.info("v5.1.1からv5.1.2へコンバート");
                version = ConvertConst.VERSION512;
                __updateVersion(con, version);
            }
            //5.1.2から5.1.3へコンバート
            if (version.equals(ConvertConst.VERSION512)) {
                log__.info("v5.1.2からv5.1.3へコンバート");
                version = ConvertConst.VERSION513;
                __updateVersion(con, version);
            }
            //5.1.3から5.2.0へコンバート
            if (version.equals(ConvertConst.VERSION513)) {
                log__.info("v5.1.3からv5.2.0へコンバート");
                __convert520(con, saiban);
                version = ConvertConst.VERSION520;
                __updateVersion(con, version);
            }
            //5.2.0から5.2.1へコンバート
            if (version.equals(ConvertConst.VERSION520)) {
                log__.info("v5.2.0からv5.2.1へコンバート");
                version = ConvertConst.VERSION521;
                __updateVersion(con, version);
            }
            //5.2.1から5.3.0へコンバート
            if (version.equals(ConvertConst.VERSION521)) {
                log__.info("v5.2.1からv5.3.0へコンバート");
                __convert530(con, saiban);
                version = ConvertConst.VERSION530;
                __updateVersion(con, version);
            }
            //5.3.0から5.3.1へコンバート
            if (version.equals(ConvertConst.VERSION530)) {
                log__.info("v5.3.0からv5.3.1へコンバート");
                version = ConvertConst.VERSION531;
                __updateVersion(con, version);
            }
            //5.3.1から5.3.2へコンバート
            if (version.equals(ConvertConst.VERSION531)) {
                log__.info("v5.3.1からv5.3.2へコンバート");
                version = ConvertConst.VERSION532;
                __updateVersion(con, version);
            }
            //5.3.2から5.3.3へコンバート
            if (version.equals(ConvertConst.VERSION532)) {
                log__.info("v5.3.2からv5.3.3へコンバート");
                __convert533(con, saiban);
                version = ConvertConst.VERSION533;
                __updateVersion(con, version);
            }
            //5.3.3から5.4.0へコンバート
            if (version.equals(ConvertConst.VERSION533)) {
                log__.info("v5.3.3からv5.4.0へコンバート");
                __convert540(con, saiban);
                version = ConvertConst.VERSION540;
                __updateVersion(con, version);
            }
            //5.4.0から5.4.1へコンバート
            if (version.equals(ConvertConst.VERSION540)) {
                log__.info("v5.4.0からv5.4.1へコンバート");
                __convert541(con, saiban);
                version = ConvertConst.VERSION541;
                __updateVersion(con, version);
            }
            //5.4.1から5.5.0へコンバート
            if (version.equals(ConvertConst.VERSION541)) {
                log__.info("v5.4.1からv5.5.0へコンバート");
                __convert550(con, saiban);
                version = ConvertConst.VERSION550;
                __updateVersion(con, version);
            }
            //5.5.0から5.6.0へコンバート
            if (version.equals(ConvertConst.VERSION550)) {
                log__.info("v5.5.0からv5.6.0へコンバート");
                __convert560(con, saiban);
                version = ConvertConst.VERSION560;
                __updateVersion(con, version);
            }
            //5.6.0から5.6.1へコンバート
            if (version.equals(ConvertConst.VERSION560)) {
                log__.info("v5.6.0からv5.6.1へコンバート");
                __convert561(con, saiban);
                version = ConvertConst.VERSION561;
                __updateVersion(con, version);
            }

            log__.info("全てのデータのコンバートが完了しました。");
            log__.info("もうしばらくお待ちください。");
        } catch (SQLException e) {
            log__.error("SQLExceptionが発生しました。\r\n原因: ", e);
            log__.error("データのコンバートに失敗");
            throw e;
        }
        log__.info("-- コンバート完了 --");
    }


    /**
     * <br>[機  能] v2.0.0 or v2.0.1からv2.0.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert202(Connection con, MlCountMtController saiban)
    throws SQLException {
        log__.debug("v2.0.0 or v2.0.1からv2.0.2へコンバート");
        //DBのコンバート
        DBConverter dbconv = new DBConverter();
        dbconv.convert(con);

        //役職情報のコンバート
        PositionConverter posconv = new PositionConverter();
        posconv.convert(con, saiban);

        //バージョン情報をv2.0.2に更新
        __updateVersion(con, ConvertConst.VERSION202);
    }
    /**
     * <br>[機  能] v2.0.2からv2.0.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert203(Connection con)
    throws SQLException {
        log__.debug("v2.0.2からv2.0.3へコンバート");
        //掲示板DBへインデックスを設定
        BbsThreViewIndexDao indexDao = new BbsThreViewIndexDao(con);
        indexDao.createIndex();

        //バージョン情報をv2.0.3に更新
        __updateVersion(con, ConvertConst.VERSION203);
    }

    /**
     * <br>[機  能] v2.0.3からv2.1.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert210(Connection con)
    throws SQLException {
        log__.debug("v2.0.3からv2.1.0へコンバート");

        //DBのコンバート
        jp.groupsession.v2.convert.convert210.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert210.dao.ConvTableDao(con);
        ctDao.convert();

        //バージョン情報をv2.1.0に更新
        __updateVersion(con, ConvertConst.VERSION210);
    }

    /**
     * <br>[機  能] v2.1.0(beta版含む)からv2.1.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert211(Connection con)
    throws SQLException {
        log__.debug("v2.1.0(beta版含む)からv2.1.1へコンバート");

        jp.groupsession.v2.convert.convert210.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert210.dao.ConvTableDao(con);
        if (!ctDao.isExistsSmtpPortField(con)) {
            ctDao.addColumnOfSmtpPort(con);
        }

        //バージョン情報をv2.1.1に更新
        __updateVersion(con, ConvertConst.VERSION211);
    }

    /**
     * <br>[機  能] v2.1.1からv2.1.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert212(Connection con)
    throws SQLException {
        log__.debug("v2.1.1からv2.1.2へコンバート");

        jp.groupsession.v2.convert.convert212.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert212.dao.ConvTableDao(con);
        ctDao.convert();

        //バージョン情報をv2.1.2に更新
        __updateVersion(con, ConvertConst.VERSION212);
    }

    /**
     * <br>[機  能] v2.1.2からv2.1.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert213(Connection con)
    throws SQLException {
        log__.debug("v2.1.2からv2.1.3へコンバート");

        //バージョン情報をv2.1.3に更新
        __updateVersion(con, ConvertConst.VERSION213);
    }

    /**
     * <br>[機  能] v2.1.3からv2.2.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert220(Connection con)
    throws SQLException {
        log__.debug("v2.1.3からv2.2.0へコンバート");
        jp.groupsession.v2.convert.convert220.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert220.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.2.0に更新
        __updateVersion(con, ConvertConst.VERSION220);
    }

    /**
     * <br>[機  能] v2.2.0からv2.2.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert221(Connection con)
    throws SQLException {
        log__.debug("v2.2.0からv2.2.1へコンバート");
        jp.groupsession.v2.convert.convert221.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert221.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.2.1に更新
        __updateVersion(con, ConvertConst.VERSION221);
    }

    /**
     * <br>[機  能] v2.2.1からv2.3.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert230(Connection con)
    throws SQLException {
        log__.debug("v2.2.1からv2.3.0へコンバート");
        jp.groupsession.v2.convert.convert230.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert230.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.3.0に更新
        __updateVersion(con, ConvertConst.VERSION230);
    }
    /**
     * <br>[機  能] v2.3.0からv2.3.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert231(Connection con)
    throws SQLException {
        log__.debug("v2.3.0からv2.3.1へコンバート");
        //バージョン情報をv2.3.0に更新
        __updateVersion(con, ConvertConst.VERSION231);
    }

    /**
     * <br>[機  能] v2.3.1からv2.4.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントロール
     * @param gscontext コンテキスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセスに失敗
     * @throws IOException ファイル書込み例外
     */
    private void __convert240(MlCountMtController saiban, GSContext gscontext, Connection con)
    throws SQLException, IOToolsException, IOException {
        log__.debug("v2.3.1からv2.4.0へコンバート");
        Object pathObj = gscontext.get(GSContext.APP_ROOT_PATH);
        //アプリケーションのルートパスを取得
        String appRootPath = "";
        if (pathObj != null) {
            appRootPath = (String) pathObj;
        }
        jp.groupsession.v2.convert.convert240.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert240.dao.ConvTableDao(con);
        ctDao.convert(saiban, appRootPath);
        //バージョン情報をv2.4.0に更新
        __updateVersion(con, ConvertConst.VERSION240);
    }

    /**
     * <br>[機  能] v2.4.0からv2.4.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert241(Connection con)
    throws SQLException {
        log__.debug("v2.4.0からv2.4.1へコンバート");
        //バージョン情報をv2.4.1に更新
        __updateVersion(con, ConvertConst.VERSION241);
    }

    /**
     * <br>[機  能] v2.4.1からv2.5.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert250(Connection con)
    throws SQLException {
        log__.debug("v2.4.1からv2.5.0へコンバート");
        jp.groupsession.v2.convert.convert250.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert250.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.5.0に更新
        __updateVersion(con, ConvertConst.VERSION250);
    }

    /**
     * <br>[機  能] v2.5.0からv2.5.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert251(Connection con)
    throws SQLException {
        log__.debug("v2.5.0からv2.5.1へコンバート");
        jp.groupsession.v2.convert.convert251.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert251.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.5.1に更新
        __updateVersion(con, ConvertConst.VERSION251);
    }

    /**
     * <br>[機  能] v2.5.1からv2.5.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert252(Connection con)
    throws SQLException {
        log__.debug("v2.5.1からv2.5.2へコンバート");
        jp.groupsession.v2.convert.convert252.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert252.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.5.2に更新
        __updateVersion(con, ConvertConst.VERSION252);
    }

    /**
     * <br>[機  能] v2.5.2からv2.5.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert253(Connection con)
    throws SQLException {
        log__.debug("v2.5.2からv2.5.3へコンバート");
        jp.groupsession.v2.convert.convert253.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert253.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv2.5.3に更新
        __updateVersion(con, ConvertConst.VERSION253);
    }

    /**
     * <br>[機  能] v2.5.3からv3.0.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert300(Connection con)
    throws SQLException {
        log__.debug("v2.5.3からv3.0.0へコンバート");
        jp.groupsession.v2.convert.convert300.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert300.dao.ConvTableDao(con);
        ctDao.convert();
        //バージョン情報をv3.0.0に更新
        __updateVersion(con, ConvertConst.VERSION300);
    }

    /**
     * <br>[機  能] v3.0.0からv3.0.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert301(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.0.0からv3.0.1へコンバート");
        jp.groupsession.v2.convert.convert301.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert301.dao.ConvTableDao(con);
        ctDao.convert(mtCon);
        //バージョン情報をv3.0.1に更新
        __updateVersion(con, ConvertConst.VERSION301);
    }

    /**
     * <br>[機  能] v3.0.1からv3.0.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert302(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.0.1からv3.0.2へコンバート");
        //バージョン情報をv3.0.2に更新
        __updateVersion(con, ConvertConst.VERSION302);
    }

    /**
     * <br>[機  能] v3.0.2からv3.0.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert303(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.0.2からv3.0.3へコンバート");
        //バージョン情報をv3.0.3に更新
        jp.groupsession.v2.convert.convert303.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert303.dao.ConvTableDao(con);
        ctDao.convert(mtCon);
        __updateVersion(con, ConvertConst.VERSION303);
    }

    /**
     * <br>[機  能] v3.0.3からv3.1.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert310(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.0.3からv3.1.0へコンバート");
        //バージョン情報をv3.1.0に更新
        jp.groupsession.v2.convert.convert310.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert310.dao.ConvTableDao(con);
        ctDao.convert(mtCon);
        __updateVersion(con, ConvertConst.VERSION310);
    }

    /**
     * <br>[機  能] v3.1.0からv3.2.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert320(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.1.0からv3.2.0へコンバート");
        jp.groupsession.v2.convert.convert320.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert320.dao.ConvTableDao(con);
        ctDao.convert(mtCon);

        //バージョン情報をv3.2.0に更新
        __updateVersion(con, ConvertConst.VERSION320);
    }

    /**
     * <br>[機  能] v3.2.0からv3.2.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert321(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.2.0からv3.2.1へコンバート");
        jp.groupsession.v2.convert.convert321.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert321.dao.ConvTableDao(con);
        ctDao.convert(mtCon);

        //バージョン情報をv3.2.1に更新
        __updateVersion(con, ConvertConst.VERSION321);
    }

    /**
     * <br>[機  能] v3.2.1からv3.5.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @param gscontext GS共通情報
     * @throws SQLException SQL実行時例外
     */
    private void __convert350(Connection con, MlCountMtController mtCon, GSContext gscontext)
    throws SQLException {
        log__.debug("v3.2.1からv3.5.0へコンバート");

        con.commit();
        con.setAutoCommit(false);

        //WEBメールのコンバートを行う
        jp.groupsession.v2.convert.convert350.dao.ConvWebmail350 ctWebmail =
            new jp.groupsession.v2.convert.convert350.dao.ConvWebmail350();
        ctWebmail.convert(gscontext);

        //v3.2.1からv3.5.0へコンバート
        jp.groupsession.v2.convert.convert350.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert350.dao.ConvTableDao(con);
        ctDao.convert(mtCon);

        //バージョン情報をv3.5.0に更新
        __updateVersion(con, ConvertConst.VERSION350);
    }

    /**
     * <br>[機  能] v3.5.0からv3.5.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @param gscontext GS共通情報
     * @throws SQLException SQL実行時例外
     */
    private void __convert351(Connection con, MlCountMtController mtCon, GSContext gscontext)
    throws SQLException {
        log__.debug("v3.5.0からv3.5.1へコンバート");

        jp.groupsession.v2.convert.convert351.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert351.dao.ConvTableDao(con);

        //WEBメール アカウント情報のコンバート
        ctDao.convertWebmailAccount(gscontext, con);

        //v3.5.0からv3.5.1へコンバート
        ctDao.convert(mtCon);

        //バージョン情報をv3.5.1に更新
        __updateVersion(con, ConvertConst.VERSION351);
    }

    /**
     * <br>[機  能] v3.5.1からv3.5.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert352(Connection con)
    throws SQLException {
        log__.debug("v3.5.1からv3.5.2へコンバート");

        //バージョン情報をv3.5.2に更新
        __updateVersion(con, ConvertConst.VERSION352);
    }

    /**
     * <br>[機  能] v3.5.2からv3.5.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert353(Connection con)
    throws SQLException {
        log__.debug("v3.5.2からv3.5.3へコンバート");

        //バージョン情報をv3.5.3に更新
        __updateVersion(con, ConvertConst.VERSION353);
    }

    /**
     * <br>[機  能] v3.5.3からv3.5.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert354(Connection con)
    throws SQLException {
        log__.debug("v3.5.3からv3.5.4へコンバート");

        //アドレス帳のコンバートを行う
        jp.groupsession.v2.convert.convert354.dao.ConvAddress354 ctAddress =
            new jp.groupsession.v2.convert.convert354.dao.ConvAddress354();
        ctAddress.convert(con);

        //バージョン情報をv3.5.4に更新
        __updateVersion(con, ConvertConst.VERSION354);
    }

    /**
     * <br>[機  能] v3.5.4からv3.5.5へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert355(Connection con)
    throws SQLException {
        log__.debug("v3.5.4からv3.5.5へコンバート");

        //v3.5.4からv3.5.5へコンバート
        jp.groupsession.v2.convert.convert355.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert355.dao.ConvTableDao(con);
        ctDao.convert();

        //バージョン情報をv3.5.5に更新
        __updateVersion(con, ConvertConst.VERSION355);
    }

    /**
     * <br>[機  能] v3.5.5からv3.5.6へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert356(Connection con)
    throws SQLException {
        log__.debug("v3.5.5からv3.5.6へコンバート");

        //バージョン情報をv3.5.6に更新
        __updateVersion(con, ConvertConst.VERSION356);
    }

    /**
     * <br>[機  能] v3.5.6からv4.0.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert400(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v3.5.6からv4.0.0へコンバート");

        jp.groupsession.v2.convert.convert400.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert400.dao.ConvTableDao(con);

        //v3.5.6からv4.0.0へコンバート
        ctDao.convert(mtCon);

        //バージョン情報をv4.0.0に更新
        __updateVersion(con, ConvertConst.VERSION400);
    }

    /**
     * <br>[機  能] v4.0.0からv4.0.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert401(Connection con)
    throws SQLException {
        log__.debug("v4.0.0からv4.0.1へコンバート");

        //バージョン情報をv4.0.1に更新
        __updateVersion(con, ConvertConst.VERSION401);
    }

    /**
     * <br>[機  能] v4.0.1からv4.0.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert402(Connection con)
    throws SQLException {
        log__.debug("v4.0.1からv4.0.2へコンバート");

        //バージョン情報をv4.0.2に更新
        __updateVersion(con, ConvertConst.VERSION402);
    }

    /**
     * <br>[機  能] v4.0.2からv4.0.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert403(Connection con)
    throws SQLException {
        log__.debug("v4.0.2からv4.0.3へコンバート");

        //バージョン情報をv4.0.3に更新
        __updateVersion(con, ConvertConst.VERSION403);
    }

    /**
     * <br>[機  能] v4.0.3からv4.0.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert404(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v4.0.3からv4.0.4へコンバート");

        jp.groupsession.v2.convert.convert404.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert404.dao.ConvTableDao(con);

        //v4.0.3からv4.0.4へコンバート
        ctDao.convert(mtCon);

        //バージョン情報をv4.0.4に更新
        __updateVersion(con, ConvertConst.VERSION404);
    }

    /**
     * <br>[機  能] v4.0.4からv4.0.5へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert405(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v4.0.4からv4.0.5へコンバート");

        jp.groupsession.v2.convert.convert405.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert405.dao.ConvTableDao(con);

        //v4.0.4からv4.0.5へコンバート
        ctDao.convert(mtCon);

        //バージョン情報をv4.0.5に更新
        __updateVersion(con, ConvertConst.VERSION405);
    }

    /**
     * <br>[機  能] v4.0.5からv4.0.6へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert406(Connection con)
    throws SQLException {
        log__.debug("v4.0.5からv4.0.6へコンバート");

        //バージョン情報をv4.0.6に更新
        __updateVersion(con, ConvertConst.VERSION406);
    }

    /**
     * <br>[機  能] v4.0.6からv4.0.7へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert407(Connection con)
    throws SQLException {
        log__.debug("v4.0.6からv4.0.7へコンバート");

        //バージョン情報をv4.0.7に更新
        __updateVersion(con, ConvertConst.VERSION407);
    }

    /**
     * <br>[機  能] v4.0.7からv4.0.8へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert408(Connection con)
    throws SQLException {
        log__.debug("v4.0.7からv4.0.8へコンバート");

        jp.groupsession.v2.convert.convert408.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert408.dao.ConvTableDao(con);

        //v4.0.7からv4.0.8へコンバート
        ctDao.convert();

        //バージョン情報をv4.0.8に更新
        __updateVersion(con, ConvertConst.VERSION408);
    }

    /**
     * <br>[機  能] v4.0.8からv4.1.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert410(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v4.0.8からv4.1.0へコンバート");

        jp.groupsession.v2.convert.convert410.dao.ConvTableDao ctDao =
            new jp.groupsession.v2.convert.convert410.dao.ConvTableDao(con);

        //v4.0.8からv4.1.0へコンバート
        ctDao.convert(mtCon);

        //バージョン情報をv4.1.0に更新
        __updateVersion(con, ConvertConst.VERSION410);
    }

    /**
     * <br>[機  能] v4.1.0からv4.1.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert411(Connection con)
    throws SQLException {
        log__.debug("v4.1.0からv4.1.1へコンバート");
        //バージョン情報をv4.1.0に更新
        __updateVersion(con, ConvertConst.VERSION411);
    }

    /**
     * <br>[機  能] v4.1.1からv4.1.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert412(Connection con)
    throws SQLException {
        log__.debug("v4.1.1からv4.1.2へコンバート");
        //バージョン情報をv4.1.2に更新
        __updateVersion(con, ConvertConst.VERSION412);
    }

    /**
     * <br>[機  能] v4.1.2からv4.1.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert413(Connection con)
    throws SQLException {
        log__.debug("v4.1.2からv4.1.3へコンバート");
        //バージョン情報をv4.1.3に更新
        __updateVersion(con, ConvertConst.VERSION413);
    }

    /**
     * <br>[機  能] v4.1.3からv4.1.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert414(Connection con)
    throws SQLException {
        log__.debug("v4.1.3からv4.1.4へコンバート");
        //バージョン情報をv4.1.4に更新
        __updateVersion(con, ConvertConst.VERSION414);
    }

    /**
     * <br>[機  能] v4.1.4からv4.2.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert420(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v4.1.4からv4.2.0へコンバート");

        jp.groupsession.v2.convert.convert420.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert420.dao.ConvTableDao(con);

        //v4.1.4からv4.2.0へコンバート
        ctDao.convert(mtCon);

        //バージョン情報をv4.2.0に更新
        __updateVersion(con, ConvertConst.VERSION420);
    }

    /**
     * <br>[機  能] v4.2.0からv4.2.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert421(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v4.2.0からv4.2.1へコンバート");

        //バージョン情報をv4.2.1に更新
        __updateVersion(con, ConvertConst.VERSION421);
    }

    /**
     * <br>[機  能] v4.2.3からv4.2.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert424(Connection con) throws SQLException {
        log__.debug("v4.2.3からv4.2.4へコンバート");

        jp.groupsession.v2.convert.convert424.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert424.dao.ConvTableDao(con);

        //v4.2.3からv4.2.4へコンバート
        ctDao.convert();

        //バージョン情報をv4.2.4に更新
        __updateVersion(con, ConvertConst.VERSION424);
    }

    /**
     * <br>[機  能] v4.2.6からv4.3.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __convert430(Connection con, MlCountMtController mtCon)
    throws SQLException {
        log__.debug("v4.2.6からv4.3.0へコンバート");

        try {

            jp.groupsession.v2.convert.convert430.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvTableDao(con);

            //1. WEBメール コンバート
            log__.info("v4.3.0 コンバートSTEP1/7 WEBメール コンバート 開始");
            jp.groupsession.v2.convert.convert430.dao.ConvWebmail430Dao webmailDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvWebmail430Dao(con);
            webmailDao.convert(mtCon);
            log__.info("v4.3.0 コンバートSTEP1/7 WEBメール コンバート 終了");

            //2. v4.2.6からv4.3.0へコンバート
            log__.info("v4.3.0 コンバートSTEP2/7 共通 コンバート　開始");
            ctDao.convert(mtCon);
            log__.info("v4.3.0 コンバートSTEP2/7 共通 コンバート　終了");

            //3. v4.3.0ファイル管理コンバート
            log__.info("v4.3.0 コンバートSTEP3/7 ファイル管理 コンバート　開始");
            jp.groupsession.v2.convert.convert430.dao.ConvFileKanri430Dao fileDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvFileKanri430Dao(con);
            fileDao.fileConv(mtCon);
            log__.info("v4.3.0 コンバートSTEP3/7 ファイル管理 コンバート　終了");

            //4. ショートメールコンバート
            log__.info("v4.3.0 コンバートSTEP4/7 ショートメール コンバート　開始");
            jp.groupsession.v2.convert.convert430.dao.ConvSmail430Dao smlDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvSmail430Dao(con);
            smlDao.smailConv(mtCon);
            log__.info("v4.3.0 コンバートSTEP4/7 ショートメール コンバート　終了");

            //5. 掲示板 コンバート
            log__.info("v4.3.0 コンバートSTEP5/7 掲示板 コンバート　開始");
            jp.groupsession.v2.convert.convert430.dao.ConvBulletin430Dao bbsDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvBulletin430Dao(con);
            bbsDao.convert(mtCon);
            log__.info("v4.3.0 コンバートSTEP5/7 掲示板 コンバート　終了");

            //6. 回覧板コンバート
            log__.info("v4.3.0 コンバートSTEP6/7 回覧板 コンバート　開始");
            jp.groupsession.v2.convert.convert430.dao.ConvCircular430Dao cirDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvCircular430Dao(con);
            cirDao.cirConv(mtCon);
            log__.info("v4.3.0 コンバートSTEP6/7 回覧板 コンバート　終了");

            //7. コンバート最終処理
            log__.info("v4.3.0 コンバートSTEP7/7 その他 コンバート　開始");
            jp.groupsession.v2.convert.convert430.dao.ConvTableAfterDao ctAftDao =
                new jp.groupsession.v2.convert.convert430.dao.ConvTableAfterDao(con);
            ctAftDao.convert(mtCon);
            log__.info("v4.3.0 コンバートSTEP7/7 その他 コンバート　終了");

        } catch (Throwable e) {
            log__.error("データのコンバートに失敗\r\n原因: ", e);
            throw e;
        }
    }


    /**
     * <br>[機  能] v4.3.1からv4.5.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert450(Connection con) throws SQLException {
        log__.debug("v4.3.1からv4.5.0へコンバート");

        jp.groupsession.v2.convert.convert450.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert450.dao.ConvTableDao(con);

        //v4.3.1からv4.5.0へコンバート
        ctDao.convert();

    }

    /**
     * <br>[機  能] v4.5.0からv4.5.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert451(Connection con) throws SQLException {
        log__.debug("v4.5.0からv4.5.1へコンバート");

        jp.groupsession.v2.convert.convert451.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert451.dao.ConvTableDao(con);

        //v4.5.0からv4.5.1へコンバート
        ctDao.convert();

    }
    /**
     * <br>[機  能] v4.5.1からv4.5.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert452(Connection con) throws SQLException {
        log__.debug("v4.5.1からv4.5.2へコンバート");

        jp.groupsession.v2.convert.convert452.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert452.dao.ConvTableDao(con);

        //v4.5.1からv4.5.2へコンバート
        ctDao.convert();
    }
    /**
     * <br>[機  能] v4.5.2からv4.5.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert453(Connection con) throws SQLException {
        log__.debug("v4.5.2からv4.5.3へコンバート");

        jp.groupsession.v2.convert.convert453.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert453.dao.ConvTableDao(con);

        //v4.5.2からv4.5.3へコンバート
        ctDao.convert();
    }
    /**
     * <br>[機  能] v4.5.3からv4.5.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert454(Connection con) throws SQLException {
        log__.debug("v4.5.3からv4.5.4へコンバート");

        jp.groupsession.v2.convert.convert454.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert454.dao.ConvTableDao(con);

        //掲示板 BBS_THRE_VIEWから削除されたユーザSIDの情報を削除する
        ctDao.deleteThreView();

        //v4.5.3からv4.5.4へコンバート
        ctDao.convert();
    }
    /**
     * <br>[機  能] v4.5.4からv4.6.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert460(Connection con) throws SQLException {
        log__.debug("v4.5.4からv4.6.0へコンバート");

        jp.groupsession.v2.convert.convert460.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert460.dao.ConvTableDao(con);

        //v4.5.4からv4.6.0へコンバート
        ctDao.convert();
    }
    /**
     * <br>[機  能] v4.6.0からv4.6.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __convert461(Connection con) throws SQLException {
        log__.debug("v4.6.0からv4.6.1へコンバート");

        jp.groupsession.v2.convert.convert461.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert461.dao.ConvTableDao(con);

        //v4.5.4からv4.6.0へコンバート
        ctDao.convert();
    }
    /**
     * <br>[機  能] v4.6.1からv4.6.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert462(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.6.1からv4.6.2へコンバート");

        jp.groupsession.v2.convert.convert462.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert462.dao.ConvTableDao(con);

        //v4.6.1からv4.6.2へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.6.2からv4.6.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert463(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.6.2からv4.6.3へコンバート");

        jp.groupsession.v2.convert.convert463.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert463.dao.ConvTableDao(con);

        //v4.6.2からv4.6.3へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.6.3からv4.6.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert464(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.6.3からv4.6.4へコンバート");

        jp.groupsession.v2.convert.convert464.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert464.dao.ConvTableDao(con);

        //v4.6.3からv4.6.4へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.6.4からv4.7.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert470(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.6.4からv4.7.0へコンバート");

        jp.groupsession.v2.convert.convert470.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert470.dao.ConvTableDao(con);

        //v4.6.4からv4.7.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.7.0からv4.7.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert471(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.7.0からv4.7.1へコンバート");

        jp.groupsession.v2.convert.convert471.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert471.dao.ConvTableDao(con);

        //v4.7.0からv4.7.1へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.7.1からv4.7.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert472(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.7.1からv4.7.2へコンバート");

        jp.groupsession.v2.convert.convert472.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert472.dao.ConvTableDao(con);

        //v4.7.1からv4.7.2へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v4.7.2からv4.8.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert480(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.7.2からv4.8.0へコンバート");

        jp.groupsession.v2.convert.convert480.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert480.dao.ConvTableDao(con);

        //v4.7.2からv4.8.0へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v4.8.0からv4.8.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert481(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.8.0からv4.8.1へコンバート");

        jp.groupsession.v2.convert.convert481.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert481.dao.ConvTableDao(con);

        //v4.8.0からv4.8.1へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v4.8.1からv4.8.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert482(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.8.1からv4.8.2へコンバート");

        jp.groupsession.v2.convert.convert482.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert482.dao.ConvTableDao(con);

        //v4.8.1からv4.8.2へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v4.8.2からv4.9.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert490(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.8.2からv4.9.0へコンバート");

        jp.groupsession.v2.convert.convert490.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert490.dao.ConvTableDao(con);

        //v4.8.2からv4.9.0へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v4.9.0からv4.9.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert491(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.0からv4.9.1へコンバート");

        jp.groupsession.v2.convert.convert491.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert491.dao.ConvTableDao(con);

        //v4.9.0からv4.9.1へコンバート
        ctDao.convert(saiban);
    }


    /**
     * <br>[機  能] v4.9.1からv4.9.2へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert492(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.1からv4.9.2へコンバート");

        jp.groupsession.v2.convert.convert492.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert492.dao.ConvTableDao(con);

        //v4.9.1からv4.9.2へコンバート
        ctDao.convert(saiban);
    }


    /**
     * <br>[機  能] v4.9.2からv4.9.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert493(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.2からv4.9.3へコンバート");

        jp.groupsession.v2.convert.convert493.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert493.dao.ConvTableDao(con);

        //v4.9.2からv4.9.3へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.9.3からv4.9.4へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert494(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.3からv4.9.4へコンバート");

        jp.groupsession.v2.convert.convert494.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert494.dao.ConvTableDao(con);

        //v4.9.2からv4.9.3へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v4.9.4からv4.9.5へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert495(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.4からv4.9.5へコンバート");

        jp.groupsession.v2.convert.convert495.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert495.dao.ConvTableDao(con);

        //v4.9.4からv4.9.5へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.9.6からv4.9.7へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert497(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.6からv4.9.7へコンバート");

        jp.groupsession.v2.convert.convert497.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert497.dao.ConvTableDao(con);

        //v4.9.6からv4.9.7へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.9.8からv4.9.9へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert499(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.8からv4.9.9へコンバート");

        jp.groupsession.v2.convert.convert499.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert499.dao.ConvTableDao(con);

        //v4.9.8からv4.9.9へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v4.9.9からv5.0.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert500(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v4.9.9からv5.0.0へコンバート");

        jp.groupsession.v2.convert.convert500.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert500.dao.ConvTableDao(con);

        //v4.9.9からv5.0.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v5.0.0からv5.1.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert510(Connection con, MlCountMtController saiban) throws SQLException {
        log__.debug("v5.0.0からv5.1.0へコンバート");

        jp.groupsession.v2.convert.convert510.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert510.dao.ConvTableDao(con);

        //vv5.0.0からv5.1.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v5.1.0からv5.2.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert520(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.1.0からv5.2.0へコンバート");

        jp.groupsession.v2.convert.convert520.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert520.dao.ConvTableDao(con);

        //vv5.1.0からv5.2.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v5.2.1からv5.3.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert530(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.2.1からv5.3.0へコンバート");

        jp.groupsession.v2.convert.convert530.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert530.dao.ConvTableDao(con);

        //vv5.2.1からv5.3.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v5.3.2からv5.3.3へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert533(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.3.2からv5.3.3へコンバート");

        jp.groupsession.v2.convert.convert533.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert533.dao.ConvTableDao(con);

        //v5.3.2からv5.3.3へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v5.3.3からv5.4.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert540(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.3.3からv5.4.0へコンバート");

        jp.groupsession.v2.convert.convert540.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert540.dao.ConvTableDao(con);

        //v5.3.3からv5.4.0へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v5.4.0からv5.4.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert541(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.4.0からv5.4.1へコンバート");

        jp.groupsession.v2.convert.convert541.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert541.dao.ConvTableDao(con);

        //v5.4.0からv5.4.1へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v5.4.1からv5.5.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert550(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.4.1からv5.5.0へコンバート");

        jp.groupsession.v2.convert.convert550.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert550.dao.ConvTableDao(con);

        //v5.4.1からv5.5.0へコンバート
        ctDao.convert(saiban);
    }
    /**
     * <br>[機  能] v5.5.0からv5.6.0へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert560(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.5.0からv5.6.0へコンバート");

        jp.groupsession.v2.convert.convert560.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert560.dao.ConvTableDao(con);

        //v5.5.1からv5.6.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] v5.6.0からv5.6.1へコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban v2の採番オブジェクト
     * @throws SQLException SQL実行時例外
     */
    private void __convert561(Connection con, MlCountMtController saiban) throws SQLException {
        log__.info("v5.6.0からv5.6.1へコンバート");

        jp.groupsession.v2.convert.convert561.dao.ConvTableDao ctDao =
                new jp.groupsession.v2.convert.convert561.dao.ConvTableDao(con);

        //v5.5.1からv5.6.0へコンバート
        ctDao.convert(saiban);
    }

    /**
     * <br>[機  能] QUERY_TIMEOUT を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param queryTimeout QUERY_TIMEOUT
     * @throws SQLException SQL実行時例外
     */
    private void __setQueryTimeout(Connection con, int queryTimeout) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("set QUERY_TIMEOUT ?;");
            sql.addIntValue(queryTimeout);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] バージョン情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param version バージョン
     * @throws SQLException SQL実行時例外
     */
    private void __updateVersion(Connection con, String version) throws SQLException {
        VersionDao vDao = new VersionDao(con);
        int count = vDao.update(version);
        if (count < 1) {
            vDao.insert(version);
        }
    }

    /**
     * メインメソッド
     * @param args パラメータ
     */
    public static void main(String[] args) {
        __readLog4j2Xml();

        log__.info("データのコンバートを開始します。");

        boolean commit = false;
        Connection con = null;
        String appRoot = args[0];
        String[] domain  = null;


        try {

            ConfigBundle.readConfig(appRoot);

            domain = GroupSession.getResourceManager().getDomain();
            if (!domain[0].equals(GSConst.GS_DOMAIN)) {

                GSContext context = new GSContext();
                context.put(GSContext.APP_ROOT_PATH, appRoot);
                GroupSession.setGscontext(context);
            }

        } catch (Exception e) {
            log__.error("ドメインの取得に失敗", e);
        }
        ConvertGsListenerImpl convert = new ConvertGsListenerImpl();
        IDbUtil dbUtil = DBUtilFactory.getInstance();

        for (String dsKey : domain) {
            try {

                log__.info("----------------「" + dsKey + "」コンバート開始-----------------------");

                if (StringUtil.isNullZeroString(appRoot)) {
                    log__.error("アプリケーションルートパスが設定されていません。");
                    return;
                }

                log__.info("アプリケーションルートパス = " + appRoot);

                GSContext gscontext = new GSContext();

                MessageResourcesFactory msgFactory
                    = MessageResourcesFactory.createFactory();

                PropertyMessageResources resources
                    = new PropertyMessageResources(
                            msgFactory,
                            "ApplicationResources_ja");
                gscontext.put(GSContext.MSG_RESOURCE, resources);

                log__.info("DBの接続設定を開始");
                try {
                    //DBの設定
                    dbUtil.init(appRoot);
                } catch (Exception e) {
                    log__.error("DBの初期化処理に失敗", e);
                    throw new ServletException(e);
                }

                //DBの起動
                log__.info("DB起動処理を実行");
                try {
                    dbUtil.startDbServer(appRoot);
                } catch (Exception e) {
                    log__.error("DBサーバの起動に失敗", e);
                    throw new ServletException(e);
                }

                //デフォルトエスケープ文字を設定する。
                JDBCUtil.def_esc = dbUtil.defaultEscape();
                JDBCUtil.def_like_esc = dbUtil.defaultLikeEscape();
                JDBCUtil.def_like_esc_after = dbUtil.defaultLikeEscapeAfter();

                gscontext.put(GSContext.APP_ROOT_PATH, appRoot);
                ConfigBundle.readConfig(appRoot);

                DataSource ds = GroupSession.getResourceManager().getDataSource(dsKey, gscontext);

                con = JDBCUtil.getConnection(ds);
                con.setAutoCommit(true);
                convert.gsInit(gscontext, con, dsKey);
                log__.info("もうしばらくお待ち下さい。");
                con.commit();
                commit = true;

                log__.info("----------------「" + dsKey + "」コンバート終了-----------------------");

            } catch (Throwable e) {
                log__.error("コンバートに失敗", e);
            } finally {
                if (con != null) {
                    if (!commit) {
                        JDBCUtil.rollback(con);
                    }

                    try {

                        dbUtil.shutdownDbServer(appRoot, con);
                    } catch (Exception e) {
                    }
                }
                JDBCUtil.closeConnection(con);

                if (commit) {
                    log__.info("すべての処理が正常に完了しました。");
                }
            }
        }
    }

    /**
     * <br>[機  能] log4j2.xmlを読み込みます。
     * <br>[解  説]
     * <br>[備  考]
     */
    private static void __readLog4j2Xml() {
        //カレントディレクトリ取得
        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir);
        currentDir = file.getParent();

        //xmlファイルまでのパス取得
        String xmlPath = IOTools.replaceSlashFileSep(
                currentDir + "/log4j/");

        //log4j2.xmlファイルにクラスパスを通す
        System.setProperty(
                ConfigurationFactory.CONFIGURATION_FILE_PROPERTY,
                "file:///" + xmlPath + "log4j2.xml");

        String logConfFile = IOTools.replaceSlashFileSep(
                xmlPath + "/log4j2.xml");
        Log4jConfig.reloadConfigFile(logConfFile);
    }
}
