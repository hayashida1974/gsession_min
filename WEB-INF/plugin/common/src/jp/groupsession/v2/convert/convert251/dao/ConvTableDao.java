package jp.groupsession.v2.convert.convert251.dao;

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
 * <br>[解  説] v2.5.1へコンバートする際に使用する
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

        //スケジュール
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" create table SCH_COMPANY");
        sql.addSql(" (");
        sql.addSql("   SCD_SID         integer         not null,");
        sql.addSql("   ACO_SID         integer         not null,");
        sql.addSql("   ABA_SID         integer         not null,");
        sql.addSql("   SCC_AUID        integer         not null,");
        sql.addSql("   SCC_ADATE       timestamp      not null,");
        sql.addSql("   SCC_EUID        integer         not null,");
        sql.addSql("   SCC_EDATE       timestamp      not null,");
        sql.addSql("   primary key (SCD_SID, ACO_SID, ABA_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_EXCOMPANY");
        sql.addSql(" (");
        sql.addSql("   SCE_SID         integer         not null,");
        sql.addSql("   ACO_SID         integer         not null,");
        sql.addSql("   ABA_SID         integer         not null,");
        sql.addSql("   SCC_AUID        integer         not null,");
        sql.addSql("   SCC_ADATE       timestamp      not null,");
        sql.addSql("   SCC_EUID        integer         not null,");
        sql.addSql("   SCC_EDATE       timestamp      not null,");
        sql.addSql("   primary key (SCE_SID, ACO_SID, ABA_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_ADDRESS");
        sql.addSql(" (");
        sql.addSql("   SCD_SID         integer         not null,");
        sql.addSql("   ADR_SID         integer         not null,");
        sql.addSql("   ADC_SID         integer,");
        sql.addSql("   SCA_AUID        integer         not null,");
        sql.addSql("   SCA_ADATE       timestamp      not null,");
        sql.addSql("   SCA_EUID        integer         not null,");
        sql.addSql("   SCA_EDATE       timestamp      not null,");
        sql.addSql("   primary key (SCD_SID, ADR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table SCH_EXADDRESS");
        sql.addSql(" (");
        sql.addSql("   SCE_SID         integer         not null,");
        sql.addSql("   ADR_SID         integer         not null,");
        sql.addSql("   SEA_AUID        integer         not null,");
        sql.addSql("   SEA_ADATE       timestamp      not null,");
        sql.addSql("   SEA_EUID        integer         not null,");
        sql.addSql("   SEA_EDATE       timestamp      not null,");
        sql.addSql("   primary key (SCE_SID, ADR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEB検索
        sql = new SqlBuffer();
        sql.addSql(" create table WSH_ACONF");
        sql.addSql(" (");
        sql.addSql("         WSA_WORD_NUM      integer      not null,");
        sql.addSql("         WSA_AUID          integer      not null,");
        sql.addSql("         WSA_ADATE         timestamp    not null,");
        sql.addSql("         WSA_EUID          integer      not null,");
        sql.addSql("         WSA_EDATE         timestamp    not null");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WSH_KEYW");
        sql.addSql(" (");
        sql.addSql("         WSK_KEY_SID       integer       not null,");
        sql.addSql("         USR_SID           integer       not null,");
        sql.addSql("         WSK_KEY_WORD      varchar(1024) not null,");
        sql.addSql("         WSK_AUID          integer       not null,");
        sql.addSql("         WSK_ADATE         timestamp     not null,");
        sql.addSql("         WSK_EUID          integer       not null,");
        sql.addSql("         WSK_EDATE         timestamp     not null,");
        sql.addSql("         primary key (WSK_KEY_SID, USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table WSH_UCONF");
        sql.addSql(" (");
        sql.addSql("         USR_SID           integer      not null,");
        sql.addSql("         WSU_WORD_NUM      integer      not null,");
        sql.addSql("         WSU_AUID          integer      not null,");
        sql.addSql("         WSU_ADATE         timestamp    not null,");
        sql.addSql("         WSU_EUID          integer      not null,");
        sql.addSql("         WSU_EDATE         timestamp    not null,");
        sql.addSql("         primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        return sqlList;
    }
}