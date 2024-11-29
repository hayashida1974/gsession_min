package jp.groupsession.v2.adr.adr100;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr100.model.Adr100SearchModel;
import jp.groupsession.v2.adr.adr120.CompanyCsvModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] 会社情報CSV出力用データを取得するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr100CompanyDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr100CompanyDao.class);

    /**
    * <p>Set Connection
    * @param con Connection
    */
   public Adr100CompanyDao(Connection con) {
       super(con);
   }


    /**
     * <br>[機  能] 会社情報CSV出力する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rl CompanyCsvRecordListenerImpl
     * @param model 検索条件
     * @throws SQLException SQL実行時例外
     * @throws CSVException CSV出力時例外
     */
   public void selectCompanyInfoForCsv(
            CompanyCsvRecordListenerImpl rl, Adr100SearchModel model)
    throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("   ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("   ADR_COMPANY.ACO_POSTNO1 as ACO_POSTNO1,");
            sql.addSql("   ADR_COMPANY.ACO_POSTNO2 as ACO_POSTNO2,");
            sql.addSql("   ADR_COMPANY.TDF_SID as COMPANY_TDF_SID,");
            sql.addSql("   ADR_COMPANY.ACO_ADDR1 as ACO_ADDR1,");
            sql.addSql("   ADR_COMPANY.ACO_ADDR2 as ACO_ADDR2,");
            sql.addSql("   ADR_COMPANY.ACO_URL as ACO_URL,");
            sql.addSql("   ADR_COMPANY.ACO_BIKO as ACO_BIKO,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID as ABA_SID,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_POSTNO1 as ABA_POSTNO1,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_POSTNO2 as ABA_POSTNO2,");
            sql.addSql("   ADR_COMPANY_BASE.TDF_SID as TDF_SID,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_ADDR1 as ABA_ADDR1,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_ADDR2 as ABA_ADDR2,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_BIKO as ABA_BIKO");

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
                    sql.addSql("   ADR_COMPANY.ACO_CODE" + orderStr + ",");
                    break;
                //会社名
                case GSConstAddress.COMPANY_SORT_NAME:
                    sql.addSql("   ADR_COMPANY.ACO_NAME_KN" + orderStr + ",");
                    break;
                //備考
                case GSConstAddress.COMPANY_SORT_BIKO:
                    sql.addSql("   ADR_COMPANY.ACO_BIKO" + orderStr + ",");
                    break;
                default:
                    break;
            }

            sql.addSql("   ADR_COMPANY_BASE.ABA_TYPE asc,");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID asc");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                setCompCsvRecordFromRs(rs, rl);
            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

   /**
    * <p>結果セットからGroupCsvModelを取得する。
    * @param rs 結果セット
    * @param rl UsrCsvRecordListenerImpl
    * @throws SQLException SQL実行例外
    * @throws CSVException CSV出力時例外
    */
   public static void setCompCsvRecordFromRs(ResultSet rs, CompanyCsvRecordListenerImpl rl)
       throws SQLException, CSVException {

       CompanyCsvModel mdl = new CompanyCsvModel();

       mdl.setCompanyCode(rs.getString("ACO_CODE"));
       mdl.setCompanyName(rs.getString("ACO_NAME"));
       mdl.setCompanyNameKn(rs.getString("ACO_NAME_KN"));
       mdl.setCompanyPostNo(
               __getPostNo(rs.getString("ACO_POSTNO1"), rs.getString("ACO_POSTNO2")));
       int tdfSid = rs.getInt("COMPANY_TDF_SID");
       if (tdfSid != 0) {
           mdl.setCompanyTdfk(String.valueOf(tdfSid));
       } else {
           mdl.setCompanyTdfk("");
       }
       mdl.setCompanyAddress1(rs.getString("ACO_ADDR1"));
       mdl.setCompanyAddress2(rs.getString("ACO_ADDR2"));
       mdl.setCompanyUrl(rs.getString("ACO_URL"));
       mdl.setCompanyBiko(rs.getString("ACO_BIKO"));

       mdl.setCompanyBaseType(String.valueOf(rs.getInt("ABA_TYPE")));
       mdl.setCompanyBaseName(rs.getString("ABA_NAME"));
       mdl.setCompanyBasePostNo(
               __getPostNo(rs.getString("ABA_POSTNO1"), rs.getString("ABA_POSTNO2")));
       if (rs.getInt("TDF_SID") != 0) {
           mdl.setCompanyBaseTdfk(String.valueOf(rs.getInt("TDF_SID")));
       } else {
           mdl.setCompanyBaseTdfk("");
       }
       mdl.setCompanyBaseAddress1(rs.getString("ABA_ADDR1"));
       mdl.setCompanyBaseAddress2(rs.getString("ABA_ADDR2"));
       mdl.setCompanyBaseBiko(rs.getString("ABA_BIKO"));

       rl.setRecord(mdl);
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
        sql.addSql(" left join");
        sql.addSql("   ADR_COMPANY_BASE");
        sql.addSql(" on");
        sql.addSql("   ADR_COMPANY.ACO_SID = ADR_COMPANY_BASE.ACO_SID");


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
                sql.addSql("   ADR_COMPANY.ACO_SID in (");
                sql.addSql("     select ACO_SID from ADR_COMPANY_BASE");
                sql.addSql("     where");
                sql.addSql("       TDF_SID = ?");
                sql.addSql("   )");
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
     * <br>[機  能] 郵便番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param postNo1 郵便番号上３桁
     * @param postNo2 郵便番号下４桁
     * @return 郵便番号
     */
    private static String __getPostNo(String postNo1, String postNo2) {
        String postNo = "";
        if (!StringUtil.isNullZeroStringSpace(postNo1)
            && !StringUtil.isNullZeroStringSpace(postNo2)) {
            postNo = postNo1 + "-" + postNo2;
        }

        return postNo;
    }
}
