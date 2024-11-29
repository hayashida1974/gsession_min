package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrCompanySearchModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] アドレス帳　会社情報に関するDaoクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrCompanySearchDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrCompanySearchDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrCompanySearchDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrCompanySearchDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 会社情報の検索結果件数を取得する
     * <br>[解  説] 
     * <br>[備  考]
     * @param AdrCompanySearchModel 検索用モデル
     * @return 検索結果件数
     * @throws SQLException SQL実行例外
     */
    public Integer countSearchCompany(AdrCompanySearchModel searchMdl) throws SQLException {

        List<Integer> acoSids = searchCompany(searchMdl, true);
        return acoSids.size();
    }

    /**
     * <br>[機  能] 会社情報を検索する
     * <br>[解  説] 
     * <br>[備  考]
     * @param AdrCompanySearchModel 検索用モデル
     * @return 会社SIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> searchCompany(AdrCompanySearchModel searchMdl) throws SQLException {

        return searchCompany(searchMdl, false);
    }

    /**
     * <br>[機  能] 会社情報を検索する
     * <br>[解  説] 
     * <br>[備  考]
     * @param AdrCompanySearchModel 検索用モデル
     * @param boolean カウントフラグ（true：limit/offsetなし false：limit/offsetあり）
     * @return 会社SIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> searchCompany(AdrCompanySearchModel searchMdl, boolean countFlg) throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY");

            boolean conditionFlg = false;

            //検索キーワード
            if (!StringUtil.isNullZeroString(searchMdl.getKeywordText())) {
                String[] keywords = searchMdl.getKeywordText().split(" ");
                for (String keyword : keywords) {
                    conditionFlg = __setCondition(sql, conditionFlg);
                    sql.addSql("   (");
                    //会社名
                    sql.addSql("   ADR_COMPANY.ACO_NAME like '%"
                            + JDBCUtil.escapeForLikeSearch(keyword)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("or");
                    //会社名カナ
                    sql.addSql("   ADR_COMPANY.ACO_NAME_KN like '%"
                            + JDBCUtil.escapeForLikeSearch(keyword)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("or");
                    //会社 備考
                    sql.addSql("   ADR_COMPANY.ACO_BIKO like '%"
                            + JDBCUtil.escapeForLikeSearch(keyword)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("or");
                    //会社拠点名
                    sql.addSql("   ADR_COMPANY.ACO_SID in (");
                    sql.addSql("     select ACO_SID from ADR_COMPANY_BASE");
                    sql.addSql("     where");
                    sql.addSql("       ABA_NAME like '%"
                                + JDBCUtil.escapeForLikeSearch(keyword)
                                + "%' ESCAPE '"
                                + JDBCUtil.def_esc
                                + "'");
                    sql.addSql("   )");
                    sql.addSql("or");
                    //会社拠点名
                    sql.addSql("   ADR_COMPANY.ACO_SID in (");
                    sql.addSql("     select ACO_SID from ADR_COMPANY_BASE");
                    sql.addSql("     where");
                    sql.addSql("       ABA_BIKO like '%"
                                + JDBCUtil.escapeForLikeSearch(keyword)
                                + "%' ESCAPE '"
                                + JDBCUtil.def_esc
                                + "'");
                    sql.addSql("   )");
                    sql.addSql("   )");
                }
            }   
            
            //会社名先頭カナ1文字
            if (!StringUtil.isNullZeroString(searchMdl.getCompanyNameKanaStartText())) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_SINI = '"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getCompanyNameKanaStartText()) + "'");
            }

            //業種
            if (searchMdl.getIndustrySid() > 0) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_SID in (");
                sql.addSql("     select ACO_SID from ADR_BELONG_INDUSTRY");
                sql.addSql("     where");
                sql.addSql("       ATI_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(searchMdl.getIndustrySid());
            }

            //都道府県
            if (searchMdl.getTodofukenSid() > 0) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   (");
                sql.addSql("     ADR_COMPANY.TDF_SID = ?");
                sql.addSql("   or");
                sql.addSql("     ADR_COMPANY.ACO_SID in (");
                sql.addSql("       select ACO_SID from ADR_COMPANY_BASE");
                sql.addSql("       where TDF_SID = ?");
                sql.addSql("     )");
                sql.addSql("   )");
                sql.addIntValue(searchMdl.getTodofukenSid());
                sql.addIntValue(searchMdl.getTodofukenSid());
            }
            
            if (searchMdl.getKanaStartOffsetText() != null) {
                //カナ順取得開始位置を設定
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_SINI");
                if (searchMdl.getSortOrderFlg() == GSConst.ORDER_KEY_ASC) {
                    sql.addSql("     >=");
                    sql.addSql("  ?");
                    sql.addStrValue(searchMdl.getKanaStartOffsetText());
                    __createOrder(sql, GSConst.ORDER_KEY_ASC);
                } else if (searchMdl.getSortOrderFlg() == GSConst.ORDER_KEY_DESC) {
                    sql.addSql("     <");
                    sql.addSql("  ?");
                    sql.addStrValue(searchMdl.getKanaStartOffsetText());
                    __createOrder(sql, GSConst.ORDER_KEY_DESC);
                }
            } else {
                __createOrder(sql, GSConst.ORDER_KEY_ASC);
            }
            
            if (!countFlg) {
                sql.setPagingValue(searchMdl.getOffset(), searchMdl.getLimit());
            }
            
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("ACO_SID"));
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
     * <br>[機  能] 条件文を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param conditionFlg conditionFlg
     * @return conditionFlg
     */
    private boolean __setCondition(SqlBuffer sql, boolean conditionFlg) {
        if (conditionFlg) {
            sql.addSql(" and");
        } else {
            sql.addSql(" where");
            conditionFlg = true;
        }

        return conditionFlg;
    }

    /**
     * <br>[機  能] order句を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param sortOrderFlg ソート順(0:asc 1:desc)
     */
    private void __createOrder(SqlBuffer sql, int sortOrderFlg) {

        sql.addSql(" order by");
        sql.addSql(" ADR_COMPANY.ACO_NAME_KN");
        if (sortOrderFlg == GSConst.ORDER_KEY_DESC) {
            sql.addSql(" desc");
        }
        sql.addSql(" , ADR_COMPANY.ACO_SID");
    }
}
