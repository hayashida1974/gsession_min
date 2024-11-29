package jp.groupsession.v2.convert.convert250.dao;

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
 * <br>[解  説] v2.5.0へコンバートする際に使用する
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

        //アドレス帳
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS {alter ADR_SYOZOKU varchar(60)}");
        sqlList.add(sql);

        //ショートメール
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_HINA add SHN_CKBN integer default 1");
        sqlList.add(sql);

        //稟議
        sql = new SqlBuffer();
        sql.addSql(" create table RNG_TEMPLATE_USER");
        sql.addSql(" (");
        sql.addSql("   RTP_SID   integer   not null,");
        sql.addSql("   USR_SID   integer   not null,");
        sql.addSql("   RTU_SORT  integer   not null,");
        sql.addSql("   RTU_TYPE  integer   not null,");
        sql.addSql("   RTU_AUID  integer   not null,");
        sql.addSql("   RTU_ADATE timestamp not null,");
        sql.addSql("   RTU_EUID  integer   not null,");
        sql.addSql("   RTU_EDATE timestamp not null,");
        sql.addSql("   primary key (RTP_SID, USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table RNG_TEMPLATE add RTP_RNG_TITLE varchar(100)");
        sqlList.add(sql);

        //施設予約
        sql = new SqlBuffer();
        sql.addSql(" create table RSV_SIS_MAIN");
        sql.addSql(" (");
        sql.addSql("   USR_SID       integer not null,");
        sql.addSql("   RSG_SID       integer not null,");
        sql.addSql("   RSM_AUID      integer not null,");
        sql.addSql("   RSM_ADATE     timestamp not null,");
        sql.addSql("   RSM_EUID      integer not null,");
        sql.addSql("   RSM_EDATE     timestamp not null,");
        sql.addSql("   primary key   (USR_SID, RSG_SID)");
        sql.addSql(" )");
        sqlList.add(sql);

        //ユーザ情報(所属 20→60)
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_INF alter COLUMN USI_SYOZOKU varchar(60)");
        sqlList.add(sql);

        //グループ情報(ID 8→15)
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUPM alter COLUMN GRP_ID varchar(15)");
        sqlList.add(sql);

        //ファイル情報　拡張子にNULLを許容
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_FILE_BIN alter COLUMN FFL_EXT SET NULL");
        sqlList.add(sql);

        return sqlList;
    }
}