package jp.groupsession.v2.convert.convert212.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v2.1.2へコンバートする際に使用する
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
     * @throws SQLException 例外
     */
    public void convert() throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //新規テーブルのcreate、insertを行う
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
     */
    private List<SqlBuffer> __createSQL() {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" alter table CMN_BINF add BIN_FILE_EXTENSION varchar(20)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_BINF add BIN_FILE_SIZE bigint");
        sqlList.add(sql);
//        sql = new SqlBuffer();
//        sql.addSql(" drop table CMN_INOUT_CONF;");
//        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_CONTM add CNT_SESSION_TIME integer default 720");
        sqlList.add(sql);

        //スケジュールDBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_PRI_CONF add SCC_RELOAD Integer not null default 600000");
        sqlList.add(sql);

        //ショートメールDBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_USER add SML_RELOAD Integer not null default 600000");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN add SMA_FWLMT_KBN integer not null default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SML_FWLMT");
        sql.addSql(" (");
        sql.addSql("     SFL_TEXT       varchar(50)      not null,");
        sql.addSql("     SFL_AUID       integer          not null,");
        sql.addSql("     SFL_ADATE      timestamp        not null,");
        sql.addSql("     SFL_EUID       integer          not null,");
        sql.addSql("     SFL_EDATE      timestamp        not null");
        sql.addSql(" )");
        sqlList.add(sql);

        //施設予約DBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER add RSU_RELOAD Integer not null default 600000");
        sqlList.add(sql);

        //回覧板DBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_USER add CUR_RELOAD integer not null default 600000");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INF add CIF_SHOW integer default(1) not null");
        sqlList.add(sql);

        //掲示板DBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_SORT integer not null default 0");
        sqlList.add(sql);

        //RSSリーダーDBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table RSS_INFOM add RSM_AUTH integer default 0 not null");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSS_INFOM add RSM_AUTH_ID varchar(20)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSS_INFOM add RSM_AUTH_PSWD varchar(44)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSS_DATA add RSD_AUTH integer default 0 not null");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSS_DATA add RSD_AUTH_ID varchar(20)");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSS_DATA add RSD_AUTH_PSWD varchar(44)");
        sqlList.add(sql);

        //在席管理DBコンバート
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_PRI_CONF add ZPC_RELOAD integer not null default 600000");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" create table ZAI_GPRI_CONF");
        sql.addSql(" (");
        sql.addSql("     USR_SID          integer         not null,");
        sql.addSql("     ZGC_GRP          integer         not null,");
        sql.addSql("     ZGC_GKBN         integer         not null,");
        sql.addSql("     ZGC_VIEW_KBN     integer         not null,");
        sql.addSql("     ZGC_SORT_KEY1    integer         not null,");
        sql.addSql("     ZGC_SORT_ORDER1  integer         not null,");
        sql.addSql("     ZGC_SORT_KEY2    integer         not null,");
        sql.addSql("     ZGC_SORT_ORDER2  integer         not null,");
        sql.addSql("     ZGC_SCH_VIEW_DF  integer         not null,");
        sql.addSql("     ZGC_AUID         integer         not null,");
        sql.addSql("     ZGC_ADATE        timestamp       not null,");
        sql.addSql("     ZGC_EUID         integer         not null,");
        sql.addSql("     ZGC_EDATE        timestamp       not null,");
        sql.addSql("     primary key (USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table ZAI_FIX_UPDATE");
        sql.addSql(" (");
        sql.addSql("     ZFU_UPDATE_KBN         integer         not null,");
        sql.addSql("     ZFU_FIX_UPDATE_TIME    integer         not null,");
        sql.addSql("     ZFU_STATUS             integer         not null,");
        sql.addSql("     ZFU_MSG                varchar(10)     not null,");
        sql.addSql("     ZFU_AUID               integer         not null,");
        sql.addSql("     ZFU_ADATE              timestamp       not null,");
        sql.addSql("     ZFU_EUID               integer         not null,");
        sql.addSql("     ZFU_EDATE              timestamp       not null");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_PRI_CONF add ZPC_SORT_KEY1 integer default 1 not null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_PRI_CONF add ZPC_SORT_ORDER1 integer default 0 not null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_PRI_CONF add ZPC_SORT_KEY2 integer default 1 not null");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table ZAI_PRI_CONF add ZPC_SORT_ORDER2 integer default 0 not null");
        sqlList.add(sql);

        return sqlList;
    }
}