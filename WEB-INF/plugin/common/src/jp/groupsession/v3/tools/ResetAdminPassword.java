package jp.groupsession.v3.tools;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * <br>[機  能] "admin"ユーザのパスワード初期化を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ResetAdminPassword {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ResetAdminPassword.class);

    /**
     * <br>[機  能] mainメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param args コマンドライン引数
     * @throws Exception 例外発生
     */
    public static void main(String[] args) throws Exception {
        File rootDir = new File(args[0]);

        //GS3ルートディレクトリ
        String appRoot = IOTools.setEndPathChar(rootDir.getAbsolutePath());
        log__.info("appRoot = " + appRoot);

        //DBディレクトリ
        String dbDir = IOTools.setEndPathChar(GsDataSourceFactory.getDbDir(appRoot));
        log__.info("dbDir = " + dbDir);

        //コネクション取得
        Connection con;
        try {
            con = getConnection(appRoot, dbDir);
        } catch (Exception e) {
            log__.info("");
            log__.info("");
            log__.info("コネクションの取得に失敗しました", e);
            log__.info("");
            log__.info("ERROR:コネクションの取得に失敗");
            log__.info("TOMCATが停止していない場合");
            log__.info("TOMCATを停止し再度実行してください。");
            return;
        }
        log__.info("コネクション取得成功");


        //adminパスワードリセット
        resetAdminPassU(con);
        //admin ワンタイムパスワード通知先アドレスリセット
        resetAdminOTPA(con);
        con.commit();
        log__.info("adminパスワードリセット成功");
        log__.info("adminのパスワードは「admin」に再設定されました。");

        //コネクションClose
        JDBCUtil.closeConnection(con);
        log__.info("終了します");

    }
    /**
     * <br>[機  能] adminユーザのパスワードを更新
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワードの暗号化に失敗
     */
    public static void resetAdminPassU(Connection con) throws SQLException, EncryptionException {
        int adminSid = 0;
        String adminPass = "admin";

        CmnUsrmModel usrmMdl = new CmnUsrmModel();
        usrmMdl.setUsrSid(adminSid);
        usrmMdl.setUsrPswd(GSPassword.getEncryPassword(adminPass));
        usrmMdl.setUsrEuid(adminSid);
        usrmMdl.setUsrEdate(new UDate());
        usrmMdl.setUsrPswdKbn(GSConstUser.PSWD_UPDATE_OFF);
        usrmMdl.setUsrPswdEdate(new UDate());

        CmnUsrmDao usrmDao = new CmnUsrmDao(con);
        usrmDao.updatePassword(usrmMdl);
    }
    /**
     * <br>[機  能] adminユーザのワンタイムパスワード通知アドレスを更新
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワードの暗号化に失敗
     */
    public static void resetAdminOTPA(Connection con) throws SQLException, EncryptionException {
        int adminSid = 0;
        //既存情報 取得
        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usrInf = usrDao.select(adminSid);
        //更新
        usrInf.setUsiOtpsendAddress("");
        usrInf.setUsiEdate(new UDate());
        usrInf.setUsiEuid(adminSid);
        usrDao.updateCmnUserInf(usrInf);
    }

    /**
     * <br>[機  能] DBコネクションを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot ホームディレクトリ(例:c:\tomcat\webapps\gsession2)
     * @param dbRoot DBディレクトリ
     * @return DBコネクション
     * @throws Exception 例外発生
     */
    public static Connection getConnection(String appRoot, String dbRoot) throws Exception {

        Connection con = null;
        String url = "jdbc:h2:" + dbRoot + "/gs2db/gs2db";
        // 組み込み環境
        try {
            //変更前XMLを取得
            DataSourceModel model = getDataSourceModel(appRoot);

            Class.forName("org.h2.Driver");
            /* データベースが存在しない時は作成 */
            con = DriverManager.getConnection(url, model.getUser(), model.getPass());
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (SAXException e) {
            throw e;
        }
        return con;
    }

    /**
     * 現データソース設定ファイルの内容をオブジェクトとして取得します
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションRootPath
     * @return DataSourceModel
     * @throws IOException IO例外
     * @throws SAXException XML読み込み例外
     */
    public static DataSourceModel getDataSourceModel(String appRoot)
    throws IOException, SAXException {
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        //XMLを取得
        String prefix = IOTools.setEndPathChar(appRoot);
        String dsPath = IOTools.replaceSlashFileSep(prefix
                + "/WEB-INF/conf/dataSource.xml");
        DataSourceModel model = GsDataSourceFactory.createDataSourceModel(
                GSConst.DATASOURCE_KEY, dsPath, dbUtil, appRoot);
        return model;
    }
}
