package jp.groupsession.v3.tools;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.DataSourceModel;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.base.CmnOtpConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnOtpUserDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.jdbc.GsDataSourceFactory;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定リセット
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ResetOtpConf {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ResetOtpConf.class);

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


        //ワンタイムパスワード設定リセット
        resetOtpConf(con);
        //admin ワンタイムパスワード通知先アドレスリセット
        resetAdminOTPA(con);
        con.commit();
        log__.info("ワンタイムパスワード設定リセット成功");
        log__.info("ワンタイムパスワードは「使用しない」に再設定されました。");

        //コネクションClose
        JDBCUtil.closeConnection(con);
        log__.info("終了します");

    }
    /**
     *
     * <br>[機  能] ワンタイムパスワード設定をリセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException 実行時例外
     */
    public static void resetOtpConf(Connection con) throws SQLException {
        //ワンタイムパスワード設定対象ユーザを削除
        CmnOtpUserDao couDao = new CmnOtpUserDao(con);
        couDao.deleteAll();
        //ワンタイムパスワード設定を読み込み
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        CmnOtpConfModel otpConf = otpBiz.getOtpConf(con);

        otpConf.setCocUseOtp(GSConstMain.OTP_NOUSE);
        otpConf.setCocOtpUser(GSConstMain.OTP_TARGET_ALL);
        otpConf.setCocOtpUserType(GSConstMain.OTP_USRTYPE_USE);
        otpConf.setCocOtpIpcond(GSConstMain.OTP_IPCOND_ALL);
        otpConf.setCocOtpIp("");
        otpConf.setCocSmtpUrl("");
        otpConf.setCocSmtpPort("");
        otpConf.setCocSmtpSsluse(0);
        otpConf.setCocSmtpFrom("");
        otpConf.setCocSmtpUser("");
        otpConf.setCocSmtpPass("");

        //DB状態書き込み
        CmnOtpConfDao otpConfDao = new CmnOtpConfDao(con);
        if (otpConfDao.update(otpConf) == 0) {
            otpConfDao.insert(otpConf);
        }



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
