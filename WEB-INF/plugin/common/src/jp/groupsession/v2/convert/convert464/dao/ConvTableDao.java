package jp.groupsession.v2.convert.convert464.dao;

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
import jp.groupsession.v2.cmn.GSConstShortMail;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.6.4へコンバートする際に使用する
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
//        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

      //スケジュール管理者設定から、開始および終了時間を一括で設定できるようにする
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_TIME_STYPE integer not null default 0; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_FRH integer not null default 9; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_FRM integer not null default 0; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_TOH integer not null default 18; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_ADM_CONF add SAD_INI_TOM integer not null default 0; ");
        sqlList.add(sql);

        //施設予約管理者設定から、開始および終了時間を一括で設定できるようにする
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_PERIOD_KBN integer not null default 0; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_FR_H integer not null default 9; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_FR_M integer not null default 0; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_TO_H integer not null default 18; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_INI_TO_M integer not null default 0; ");
        sqlList.add(sql);

        //システムメールのショートメールアカウントの初期値を修正
        sql = new SqlBuffer();
        sql.addSql("update SML_ACCOUNT set SAC_NAME=");
        sql.addSql(" '" + GSConstShortMail.SYSTEM_MAIL_NAME_STRING + "' ");
        sql.addSql(" where");
        sql.addSql(" SAC_SID=");
        sql.addSql(" '" + GSConstShortMail.SYSTEM_MAIL_SID_STRING + "' ");
        sql.addSql(";");
        sqlList.add(sql);

        return sqlList;
    }
}