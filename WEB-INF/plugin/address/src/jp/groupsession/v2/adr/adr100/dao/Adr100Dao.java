package jp.groupsession.v2.adr.adr100.dao;


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
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr100.model.Adr100DetailModel;
import jp.groupsession.v2.adr.adr100.model.Adr100SearchModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] アドレス帳 会社一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr100Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr100Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr100Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(Adr100SearchModel model) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int result = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_COMPANY.ACO_SID) as CNT");

            sql = __createSearchSql(sql, model);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] 会社情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<Adr100DetailModel> getSearchResultList(Adr100SearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Adr100DetailModel> ret = new ArrayList<Adr100DetailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("   ADR_COMPANY.ACO_BIKO as ACO_BIKO");

            sql = __createSearchSql(sql, model);

            int sortKey = model.getSortKey();
            int orderKey = model.getOrderKey();

            //オーダー
            String orderStr = " asc";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                orderStr = " desc";
            }

            sql.addSql(" order by");

            //ソートカラム
            switch (sortKey) {
                //企業コード
                case GSConstAddress.COMPANY_SORT_CODE:
                    sql.addSql("   ADR_COMPANY.ACO_CODE" + orderStr);
                    break;
                //会社名
                case GSConstAddress.COMPANY_SORT_NAME:
                    sql.addSql("   ADR_COMPANY.ACO_NAME_KN" + orderStr);
                    break;
                //備考
                case GSConstAddress.COMPANY_SORT_BIKO:
                    sql.addSql("   ADR_COMPANY.ACO_BIKO" + orderStr);
                    break;
                default:
                    break;
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int page = model.getPage();
            int maxCnt = model.getMaxViewCount();
            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                Adr100DetailModel detailMdl = new Adr100DetailModel();

                detailMdl.setAcoSid(rs.getInt("ACO_SID"));
                detailMdl.setCompanyCode(rs.getString("ACO_CODE"));
                detailMdl.setCompanyName(rs.getString("ACO_NAME"));
                detailMdl.setCompanyBiko(rs.getString("ACO_BIKO"));

                //業種名称を取得する
                detailMdl.setIndustryName(getIndustryName(detailMdl.getAcoSid()));

                ret.add(detailMdl);
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
     * <br>[機  能] アドレス帳の検索SQLの条件部を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param model 検索条件
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createSearchSql(SqlBuffer sql, Adr100SearchModel model)
    throws SQLException {

        sql.addSql(" from");
        sql.addSql("   ADR_COMPANY");

        boolean conditionFlg = false;

        if (model.getMode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {

            //会社名カナ
            if (!StringUtil.isNullZeroString(model.getCoSini())) {
                sql.addSql(" where");
                sql.addSql("   ADR_COMPANY.ACO_SINI = '"
                        + JDBCUtil.escapeForLikeSearch(model.getCoSini()) + "'");
            }

        } else if (model.getMode() == GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) {

            //企業コード
            if (!StringUtil.isNullZeroString(model.getCoCode())) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_CODE = ?");
                sql.addStrValue(model.getCoCode());
            }

            //会社名
            if (!StringUtil.isNullZeroString(model.getCoName())) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(model.getCoName())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }

            //会社名カナ
            if (!StringUtil.isNullZeroString(model.getCoNameKn())) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_NAME_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(model.getCoNameKn())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }

            //支店・営業所名
            if (!StringUtil.isNullZeroString(model.getCoBaseName())) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_SID in (");
                sql.addSql("     select ACO_SID from ADR_COMPANY_BASE");
                sql.addSql("     where");
                sql.addSql("       ABA_NAME like '%"
                            + JDBCUtil.escapeForLikeSearch(model.getCoBaseName())
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                sql.addSql("   )");
            }

            //業種
            if (model.getAtiSid() > 0) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_SID in (");
                sql.addSql("     select ACO_SID from ADR_BELONG_INDUSTRY");
                sql.addSql("     where");
                sql.addSql("       ATI_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(model.getAtiSid());
            }

            //都道府県
            if (model.getTdfk() > 0) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   (");
                sql.addSql("     ADR_COMPANY.TDF_SID = ?");
                sql.addSql("   or");
                sql.addSql("     ADR_COMPANY.ACO_SID in (");
                sql.addSql("       select ACO_SID from ADR_COMPANY_BASE");
                sql.addSql("       where TDF_SID = ?");
                sql.addSql("     )");
                sql.addSql("   )");
                sql.addIntValue(model.getTdfk());
                sql.addIntValue(model.getTdfk());
            }

            //備考
            if (!StringUtil.isNullZeroString(model.getBiko())) {
                conditionFlg = __setCondition(sql, conditionFlg);
                sql.addSql("   ADR_COMPANY.ACO_BIKO like '%"
                        + JDBCUtil.escapeForLikeSearch(model.getBiko())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }

        }

        return sql;
    }

    /**
     * <br>[機  能] 業種名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @return 業種名称
     * @throws SQLException SQL実行例外
     */
    public String getIndustryName(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String indstryName = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_TYPEINDUSTRY.ATI_NAME as ATI_NAME");
            sql.addSql(" from");
            sql.addSql("   ADR_TYPEINDUSTRY,");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ADR_TYPEINDUSTRY.ATI_SID = ADR_BELONG_INDUSTRY.ATI_SID");
            sql.addSql(" and ");
            sql.addSql("   ADR_BELONG_INDUSTRY.ACO_SID = ?");

            sql.addIntValue(acoSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                StringBuilder sb = new StringBuilder("");
                sb.append(rs.getString("ATI_NAME"));

                while (rs.next()) {
                    sb.append("、 ");
                    sb.append(rs.getString("ATI_NAME"));
                }

                indstryName = sb.toString();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return indstryName;
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
}
