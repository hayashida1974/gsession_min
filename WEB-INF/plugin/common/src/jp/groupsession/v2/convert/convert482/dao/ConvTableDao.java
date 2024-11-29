package jp.groupsession.v2.convert.convert482.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @param saiban 採番用コントローラー
     * @throws SQLException 例外
     */
    public void convert(
            MlCountMtController saiban) throws SQLException {

        log__.debug("-- DBコンバート開始 --");
        // 稟議テーブルのコンバート
        ConvRingiDao rngConvDao = new ConvRingiDao();
        rngConvDao.convert(getCon());

        //create Table or alter table
        createTable(saiban);

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @throws SQLException SQL実行例外
     */
    public void createTable(
            MlCountMtController saiban) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL(saiban);

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL(
            MlCountMtController saiban) throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;

        //タイムカード休日日数 桁数 変更
        sql = new SqlBuffer();
        sql.addSql(" alter table TCD_TCDATA alter TCD_HOLCNT type decimal(6, 3);");
        sqlList.add(sql);

        //WEBメールリンク制限設定を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_LINK_LIMIT integer not null default 0;");
        sqlList.add(sql);

        //掲示板無制限設定を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_UNLIMITED integer not null default 0;");
        sqlList.add(sql);

        // メイン画面に受信アンケートを表示するための設定を追加
        sql = new SqlBuffer();
        sql.addSql("alter table ENQ_ADM_CONF add EAC_DSPCNT_MAIN integer not null default 5;");
        sql.addSql("alter table ENQ_ADM_CONF add EAC_DSP_CHECKED integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql("alter table ENQ_PRI_CONF add EPC_DSPCNT_MAIN integer not null default 5;");
        sql.addSql("alter table ENQ_PRI_CONF add EPC_DSP_CHECKED integer not null default 0;");
        sqlList.add(sql);

        // 稟議管理者設定に汎用稟議使用フラグ設定を追加RAR_HANYO_FLG
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF add RAR_HANYO_FLG integer not null default 1;");
        sqlList.add(sql);

        // 稟議管理者設定に個人テンプレート使用フラグ設定を追加RAR_TEMPLATE_PERSONAL_FLG
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF add"
                + " RAR_TEMPLATE_PERSONAL_FLG integer not null default 1;");
        sqlList.add(sql);

        // 稟議管理者設定に個人経路テンプレート使用フラグ設定を追加RAR_KEIRO_PERSONAL_FLG
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ACONF add"
                + " RAR_KEIRO_PERSONAL_FLG integer not null default 1;");
        sqlList.add(sql);

        // 稟議IDに採番日時を追加
        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_ID add RID_USE_DATE timestamp;");
        sqlList.add(sql);
        // 採番日時に当日を設定
        sql = new SqlBuffer();
        sql.addSql(" update RNG_ID set ");
        sql.addSql(" RID_USE_DATE=current_timestamp; ");
        sqlList.add(sql);
        // 採番初期値を1から開始に設定
        sql = new SqlBuffer();
        sql.addSql(" update RNG_ID set ");
        sql.addSql(" RID_INIT=1 ");
        sql.addSql(" where RID_INIT=0 ");
        sqlList.add(sql);


        return sqlList;
    }




}
