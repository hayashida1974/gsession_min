package jp.groupsession.v2.dba.dba001.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.dba.dba001.model.Dba001IndexModel;
import jp.groupsession.v2.dba.dba001.model.Dba001Model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] DBAのDaoクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Dba001Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Dba001Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Dba001Dao(Connection con) {
        super(con);
    }



    /**
     * <br>[機  能] シーケンス情報を全取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return シーケンス一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Dba001Model> getSequenses()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Dba001Model> ret = new ArrayList<Dba001Model>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_CATALOG as CATALOG,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_SCHEMA as SCHEMA,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.SEQUENCE_NAME as NAME,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.CURRENT_VALUE as VALUE,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.INCREMENT as INCREMENT,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.IS_GENERATED as GENERATED,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.REMARKS as REMARKS,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.CACHE as CACHE,");
            sql.addSql("   INFORMATION_SCHEMA.SEQUENCES.ID as ID");
            sql.addSql(" from");
            sql.addSql(" INFORMATION_SCHEMA.SEQUENCES");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Dba001Model mdl = new Dba001Model();

                mdl.setDbaCatalog(rs.getString("CATALOG"));
                mdl.setDbaSchema(rs.getString("SCHEMA"));
                mdl.setDbaName(rs.getString("NAME"));
                mdl.setDbaValue(rs.getInt("VALUE"));
                mdl.setDbaIncrement(rs.getInt("INCREMENT"));
                mdl.setDbaGenerated(rs.getBoolean("GENERATED"));
                mdl.setDbaRemarks(rs.getString("REMARKS"));
                mdl.setDbaCache(rs.getInt("CACHE"));
                mdl.setDbaId(rs.getInt("ID"));

                ret.add(mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] INDEX情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return シーケンス一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Dba001IndexModel> getIndexes()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Dba001IndexModel> ret = new ArrayList<Dba001IndexModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.TABLE_CATALOG as TABLE_CATALOG,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.TABLE_SCHEMA as TABLE_SCHEMA,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.TABLE_NAME as TABLE_NAME,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.NON_UNIQUE as NON_UNIQUE,");
            sql.addSql("  INFORMATION_SCHEMA.INDEXES.INDEX_NAME as INDEX_NAME");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.ORDINAL_POSITION as ORDINAL_POSITION,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.COLUMN_NAME as COLUMN_NAME,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.CARDINALITY as CARDINALITY,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.PRIMARY_KEY as PRIMARY_KEY,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.INDEX_TYPE_NAME as INDEX_TYPE_NAME,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.IS_GENERATED as IS_GENERATED,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.INDEX_TYPE as INDEX_TYPE,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.ASC_OR_DESC as ASC_OR_DESC,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.PAGES as PAGES,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.FILTER_CONDITION as FILTER_CONDITION,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.REMARKS as REMARKS,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.SQL as SQL,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.ID as ID,");
//            sql.addSql("  INFORMATION_SCHEMA.INDEXES.SORT_TYPE as SORT_TYPE");
            sql.addSql(" from");
            sql.addSql("  INFORMATION_SCHEMA.INDEXES");
            sql.addSql(" where");
            sql.addSql("  INDEX_TYPE_NAME<>'PRIMARY KEY'");
            sql.addSql(" group by");
            sql.addSql("  INDEX_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Dba001IndexModel mdl = new Dba001IndexModel();
//                mdl.setTableCatalog(rs.getString("TABLE_CATALOG"));
//                mdl.setTableSchema(rs.getString("TABLE_SCHEMA"));
//                mdl.setTableName(rs.getString("TABLE_SCHEMA"));
//                mdl.setNonUnique(rs.getString("NON_UNIQUE"));
                mdl.setIndexName(rs.getString("INDEX_NAME"));
//                mdl.setOrdinalPosition(rs.getString("ORDINAL_POSITION"));
//                mdl.setColumnName(rs.getString("COLUMN_NAME"));
//                mdl.setCardInality(rs.getString("CARDINALITY"));
//                mdl.setPrimaryKey(rs.getString("PRIMARY_KEY"));
//                mdl.setIndexTypeName(rs.getString("INDEX_TYPE_NAME"));
//                mdl.setIsGenerated(rs.getString("IS_GENERATED"));
//                mdl.setIndexType(rs.getString("INDEX_TYPE"));
//                mdl.setAscOrDesc(rs.getString("ASC_OR_DESC"));
//                mdl.setPages(rs.getString("PAGES"));
//                mdl.setFilterCondition(rs.getString("NON_UNIQUE"));
//                mdl.setRemarks(rs.getString("REMARKS"));
//                mdl.setSql(rs.getString("SQL"));
//                mdl.setId(rs.getString("ID"));
//                mdl.setSortType(rs.getString("SORT_TYPE"));
                ret.add(mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }
}
