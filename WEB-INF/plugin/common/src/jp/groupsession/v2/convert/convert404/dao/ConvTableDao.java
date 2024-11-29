package jp.groupsession.v2.convert.convert404.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.0.4へのコンバートで使用する
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
     * @param mtCon 採番コントローラ
     * @throws SQLException 例外
     */
    public void convert(MlCountMtController mtCon) throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //create Table or alter table
        createTable();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL();

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
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL() throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;

        //ショートメールメールインデックス
        sql = new SqlBuffer();
        sql.addSql(" create index SML_SMEIS_INDEX2 on SML_SMEIS(SMS_ADATE);");
        sqlList.add(sql);

        //WEBメールインデックス
        sql = new SqlBuffer();
        sql.addSql(" create index WML_TEMPFILE_INDEX1 on WML_TEMPFILE(WTF_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_HEADER_DATA_INDEX1 on WML_HEADER_DATA(WAC_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_LABEL_INDEX1 on WML_LABEL(WAC_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop index WML_LABEL_RELATION_INDEX1;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_LABEL_RELATION_INDEX");
        sql.addSql(" on WML_LABEL_RELATION(WAC_SID,WLB_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_MAIL_BODY_INDEX1 on WML_MAIL_BODY(WAC_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_MAIL_LOG_INDEX2 on WML_MAIL_LOG(WAC_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_MAIL_LOG_SEND_INDEX1 on WML_MAIL_LOG_SEND(WAC_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" drop index WML_MAILDATA_INDEX;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_MAILDATA_INDEX on  WML_MAILDATA(WAC_SID,WDR_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create index WML_SENDADDRESS_INDEX1 on WML_SENDADDRESS(WAC_SID);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" analyze;");
        sqlList.add(sql);

        //WEB管理者設定
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_SEND_HOST varchar(100);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_SEND_PORT integer default 25;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_SEND_SSL integer default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_RECEIVE_HOST varchar(100);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_RECEIVE_PORT integer default 110;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table WML_ADM_CONF add WAD_RECEIVE_SSL integer default 0;");
        sqlList.add(sql);

        return sqlList;
    }
}