package jp.groupsession.v2.adr.adr150.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr150.model.Adr150DetailModel;
import jp.groupsession.v2.adr.adr150.model.Adr150SearchModel;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 会社選択で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr150Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr150Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr150Dao(Connection con) {
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
    public int getSearchCount(Adr150SearchModel model) throws SQLException {

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
    public List<Adr150DetailModel> getSearchResultList(Adr150SearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Adr150DetailModel> ret = new ArrayList<Adr150DetailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("   COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
            sql.addSql("   COMPANY_BASE.ABA_NAME as ABA_NAME,");
            sql.addSql("   COMPANY_BASE.ABA_SID as ABA_SID,");
            sql.addSql("   COMPANY_BASE.ABA_NAME as ABA_NAME,");
            sql.addSql("   COMPANY_BASE.ABA_ADDR1 as ABA_ADDR1,");
            sql.addSql("   COMPANY_BASE.ABA_ADDR2 as ABA_ADDR2,");
            sql.addSql("   COMPANY_BASE.TDF_SID as TDF_SID,");
            sql.addSql("   COMPANY_BASE.TDF_NAME as TDF_NAME,");
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
                //会社名
                case GSConstAddress.COMPANY_SORT_NAME:
                    sql.addSql("   ADR_COMPANY.ACO_NAME_KN" + orderStr);
                    break;
                //拠点区分
                case GSConstAddress.COMPANY_SORT_ABA_TYPE:
                    sql.addSql("   COMPANY_BASE.ABA_TYPE" + orderStr);
                    break;
                //拠点名
                case GSConstAddress.COMPANY_SORT_ABA_NAME:
                    sql.addSql("   COMPANY_BASE.ABA_NAME" + orderStr);
                    break;
                //都道府県
                case GSConstAddress.COMPANY_SORT_TDFK:
                    sql.addSql("   COMPANY_BASE.TDF_SID" + orderStr);
                    break;
                //住所1
                case GSConstAddress.COMPANY_SORT_ADDR1:
                    sql.addSql("   COMPANY_BASE.ABA_ADDR1" + orderStr);
                    break;
                //住所2
                case GSConstAddress.COMPANY_SORT_ADDR2:
                    sql.addSql("   COMPANY_BASE.ABA_ADDR2" + orderStr);
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
                Adr150DetailModel detailMdl = new Adr150DetailModel();

                detailMdl.setAcoSid(rs.getInt("ACO_SID"));
                detailMdl.setAbaSid(rs.getInt("ABA_SID"));
                detailMdl.setCompanyName(rs.getString("ACO_NAME"));
                detailMdl.setCompanyBaseType(rs.getInt("ABA_TYPE"));
                detailMdl.setCompanyBaseName(rs.getString("ABA_NAME"));
                detailMdl.setTdfSid(rs.getInt("TDF_SID"));
                detailMdl.setTdfkName(rs.getString("TDF_NAME"));
                detailMdl.setCompanyBaseAddress1(rs.getString("ABA_ADDR1"));
                detailMdl.setCompanyBaseAddress2(rs.getString("ABA_ADDR2"));
                detailMdl.setCompanyBiko(rs.getString("ACO_BIKO"));

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
    private SqlBuffer __createSearchSql(SqlBuffer sql, Adr150SearchModel model)
    throws SQLException {

        sql.addSql(" from");
        sql.addSql("   ADR_COMPANY");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("      select");
        sql.addSql("        ADR_COMPANY_BASE.ACO_SID as ACO_SID,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_SID as ABA_SID,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_ADDR1 as ABA_ADDR1,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_ADDR2 as ABA_ADDR2,");
        sql.addSql("        CMN_TDFK.TDF_SID as TDF_SID,");
        sql.addSql("        CMN_TDFK.TDF_NAME as TDF_NAME");
        sql.addSql("      from");
        sql.addSql("        ADR_COMPANY_BASE");
        sql.addSql("        left join");
        sql.addSql("           CMN_TDFK");
        sql.addSql("         on");
        sql.addSql("           ADR_COMPANY_BASE.TDF_SID = CMN_TDFK.TDF_SID");
        sql.addSql("     ) COMPANY_BASE");
        sql.addSql("   on");
        sql.addSql("     ADR_COMPANY.ACO_SID = COMPANY_BASE.ACO_SID");

        boolean conditionFlg = false;

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
            sql.addSql("   COMPANY_BASE.ABA_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(model.getCoBaseName())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
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
            sql.addSql("   COMPANY_BASE.TDF_SID = ?");
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
                    sb.append(",");
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
