package jp.groupsession.v2.adr.adr010.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Const;
import jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel;
import jp.groupsession.v2.adr.adr010.model.Adr010DetailModel;
import jp.groupsession.v2.adr.adr010.model.Adr010ProjectDataModel;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;

/**
 * <br>[機  能] アドレス帳画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr010Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Adr010Dao(Connection con) {
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
    public int getSearchCount(Adr010SearchModel model) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int result = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            //プロジェクト検索
            if (model.isPrjSerchFlg() == true) {
                sql.addSql("   count(distinct ADR_ADDRESS.ADR_SID) as CNT");
            } else {
                sql.addSql("   count(ADR_ADDRESS.ADR_SID) as CNT");
            }

            sql = _createSearchSql(sql, model);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] アドレス帳情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<Adr010DetailModel> getSearchResultList(Adr010SearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Adr010DetailModel> ret = new ArrayList<Adr010DetailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            //プロジェクト検索
            if (model.isPrjSerchFlg() == true) {
                sql.addSql(" distinct ADR_ADDRESS.ADR_SID as ADR_SID,");
                sql.addSql("   ADR_ADDRESS.ADR_SEI_KN as ADR_SEI_KN,");
                sql.addSql("   ADR_ADDRESS.ADR_MEI_KN as ADR_MEI_KN,");
                sql.addSql("   ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            } else {
                sql.addSql("   ADR_ADDRESS.ADR_SID as ADR_SID,");
            }
            sql.addSql("   ADR_ADDRESS.ADR_SEI as ADR_SEI,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI as ADR_MEI,");
            sql.addSql("   ADR_ADDRESS.ACO_SID as ACO_SID,");
            sql.addSql("   ADR_ADDRESS.ABA_SID as ABA_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL1 as ADR_MAIL1,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL2 as ADR_MAIL2,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL3 as ADR_MAIL3,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL_CMT1 as ADR_MAIL_CMT1,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL_CMT2 as ADR_MAIL_CMT2,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL_CMT3 as ADR_MAIL_CMT3,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL1 as ADR_TEL1,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL2 as ADR_TEL2,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL3 as ADR_TEL3,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL_CMT1 as ADR_TEL_CMT1,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL_CMT2 as ADR_TEL_CMT2,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL_CMT3 as ADR_TEL_CMT3,");
            sql.addSql("   ADR_ADDRESS.APS_SID as APS_SID,");
            sql.addSql("   (case");
            sql.addSql("      when ADR_ADDRESS.APS_SID= -1 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("   COMPANY_BASE.ABA_NAME as ABA_NAME,");
            sql.addSql("   ADR_POSITION.APS_NAME as APS_NAME,");
            sql.addSql("   ADR_POSITION.APS_SORT as APS_SORT");

            if (model.getCmdMode() == Adr010Const.CMDMODE_CONTACT) {
                sql.addSql("   ,CONTACT.ADC_SID as ADC_SID,");
                sql.addSql("   CONTACT.ADC_TITLE as ADC_TITLE,");
                sql.addSql("   CONTACT.ADC_TYPE as ADC_TYPE,");
                sql.addSql("   CONTACT.ADC_CTTIME as ADC_CTTIME,");
                sql.addSql("   CONTACT.ADC_CTTIME_TO as ADC_CTTIME_TO,");
                sql.addSql("   CONTACT.USI_SEI as CONTACT_USI_SEI,");
                sql.addSql("   CONTACT.USI_MEI as CONTACT_USI_MEI,");
                sql.addSql("   CONTACT.USR_UKO_FLG as CONTACT_USR_UKO_FLG,");
                sql.addSql("   CONTACT.USR_JKBN as CONTACT_USR_JKBN");
            }

            sql = _createSearchSql(sql, model);

            //並び順を設定
            int order = model.getOrderKey();
            sql.addSql(" order by");
            switch (model.getSortKey()) {
                case Adr010Const.SORTKEY_UNAME :
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_SEI_KN", order) + ",");
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_MEI_KN", order));
                    break;
                case Adr010Const.SORTKEY_CONAME :
                    sql.addSql(_getSortSql("ADR_COMPANY.ACO_NAME_KN", order));
                    break;
                case Adr010Const.SORTKEY_COBASENAME :
                    sql.addSql(_getSortSql("COMPANY_BASE.ABA_NAME", order));
                    break;
                case Adr010Const.SORTKEY_POSITION :
                    sql.addSql(_getSortSql("YAKUSYOKU_EXIST", order) + ",");
                    sql.addSql(_getSortSql("ADR_POSITION.APS_SORT", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_DATE :
                    sql.addSql(_getSortSql("CONTACT.ADC_CTTIME", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_TYPE :
                    sql.addSql(_getSortSql("CONTACT.ADC_TYPE", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_TITLE :
                    sql.addSql(_getSortSql("CONTACT.ADC_TITLE", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_ENTRYUSER :
                    sql.addSql(_getSortSql("CONTACT.USI_SEI_KN", order) + ",");
                    sql.addSql(_getSortSql("CONTACT.USI_MEI_KN", order));
                    break;
                default :
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_SEI", order) + ",");
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_MEI", order));
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
                Adr010DetailModel detailMdl = new Adr010DetailModel();

                detailMdl.setAdrSid(rs.getInt("ADR_SID"));
                detailMdl.setUserName(
                        rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
                detailMdl.setAcoSid(rs.getInt("ACO_SID"));
                detailMdl.setAbaSid(rs.getInt("ABA_SID"));
                detailMdl.setCompanyName(rs.getString("ACO_NAME"));
                detailMdl.setCompanyBaseName(rs.getString("ABA_NAME"));

                //プロジェクト検索以外
                if (model.isPrjSerchFlg() == false) {
                    detailMdl.setCompanyCode(rs.getString("ACO_CODE"));
                    detailMdl.setApsSid(rs.getInt("APS_SID"));
                    detailMdl.setPositionName(rs.getString("APS_NAME"));
                }

                detailMdl.setMail1(rs.getString("ADR_MAIL1"));
                detailMdl.setMail2(rs.getString("ADR_MAIL2"));
                detailMdl.setMail3(rs.getString("ADR_MAIL3"));
                detailMdl.setMailCmt1(rs.getString("ADR_MAIL_CMT1"));
                detailMdl.setMailCmt2(rs.getString("ADR_MAIL_CMT2"));
                detailMdl.setMailCmt3(rs.getString("ADR_MAIL_CMT3"));
                detailMdl.setTel1(rs.getString("ADR_TEL1"));
                detailMdl.setTel2(rs.getString("ADR_TEL2"));
                detailMdl.setTel3(rs.getString("ADR_TEL3"));
                detailMdl.setTelCmt1(rs.getString("ADR_TEL_CMT1"));
                detailMdl.setTelCmt2(rs.getString("ADR_TEL_CMT2"));
                detailMdl.setTelCmt3(rs.getString("ADR_TEL_CMT3"));

                if (model.getCmdMode() == Adr010Const.CMDMODE_CONTACT) {
                    detailMdl.setContactSid(rs.getInt("ADC_SID"));
                    detailMdl.setContactDate(
                            __cleateDspDate(UDate.getInstanceTimestamp(
                                    rs.getTimestamp("ADC_CTTIME"))));
                    detailMdl.setContactType(rs.getInt("ADC_TYPE"));
                    detailMdl.setContactTitle(rs.getString("ADC_TITLE"));
                    detailMdl.setContactEntryUser(
                            rs.getString("CONTACT_USI_SEI")
                            + " "
                            + rs.getString("CONTACT_USI_MEI"));
                    detailMdl.setContactEntryUsrJkbn(rs.getInt("CONTACT_USR_JKBN"));
                    detailMdl.setContactEntryUsrUkoFlg(rs.getInt("CONTACT_USR_UKO_FLG"));
                }

                ret.add(detailMdl);
            }

            //アドレス情報に付与されているラベルの名称を取得する
            if (!ret.isEmpty()) {
                JDBCUtil.closeStatement(pstmt);

                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   ADR_LABEL.ALB_NAME as ALB_NAME");
                sql.addSql(" from");
                sql.addSql("   ADR_LABEL,");
                sql.addSql("   ADR_BELONG_LABEL");
                sql.addSql(" where");
                sql.addSql("   ADR_BELONG_LABEL.ADR_SID = ?");
                sql.addSql(" and");
                sql.addSql("   ADR_LABEL.ALB_SID = ADR_BELONG_LABEL.ALB_SID");
                sql.addSql(" order by");
                sql.addSql("   ADR_LABEL.ALB_SORT");

                pstmt = con.prepareStatement(sql.toSqlString());

                for (int idx = 0; idx < ret.size(); idx++) {
                    int adrSid = ret.get(idx).getAdrSid();
                    JDBCUtil.closeResultSet(rs);
                    sql.clearValue();
                    sql.addIntValue(adrSid);
                    sql.toLogString();

                    List<String> labelNameList = new ArrayList<String>();
                    pstmt.setInt(1, adrSid);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        labelNameList.add(rs.getString("ALB_NAME"));
                    }
                    ret.get(idx).setLabelNameList(labelNameList);
                }

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
     * <br>[機  能] アドレス帳の検索SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     *
     */
    protected SqlBuffer _createSearchSQLForExport(Adr010SearchModel model) throws SQLException {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        //プロジェクト検索
        if (model.isPrjSerchFlg() == true) {
            sql.addSql(" distinct ADR_ADDRESS.ADR_SID as ADR_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI_KN as ADR_SEI_KN,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI_KN as ADR_MEI_KN,");
            sql.addSql("   ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
        } else {
            sql.addSql("   ADR_ADDRESS.ADR_SID as ADR_SID,");
        }
        sql.addSql("   ADR_ADDRESS.ADR_SEI as ADR_SEI,");
        sql.addSql("   ADR_ADDRESS.ADR_MEI as ADR_MEI,");
        sql.addSql("   ADR_ADDRESS.ADR_SEI_KN as ADR_SEI_KN,");
        sql.addSql("   ADR_ADDRESS.ADR_MEI_KN as ADR_MEI_KN,");
        sql.addSql("   ADR_ADDRESS.ACO_SID as ACO_SID,");
        sql.addSql("   ADR_ADDRESS.ABA_SID as ABA_SID,");
        sql.addSql("   ADR_ADDRESS.ADR_SYOZOKU as ADR_SYOZOKU,");
        sql.addSql("   ADR_ADDRESS.APS_SID as APS_SID,");
        sql.addSql("   (case");
        sql.addSql("      when ADR_ADDRESS.APS_SID= -1 then 1");
        sql.addSql("      else 0");
        sql.addSql("    end) as YAKUSYOKU_EXIST,");
        sql.addSql("   ADR_POSITION.APS_NAME as APS_NAME,");
        sql.addSql("   ADR_POSITION.APS_SORT as APS_SORT,");
        sql.addSql("   ADR_ADDRESS.ADR_MAIL1 as ADR_MAIL1,");
        sql.addSql("   ADR_ADDRESS.ADR_MAIL_CMT1 as ADR_MAIL_CMT1,");
        sql.addSql("   ADR_ADDRESS.ADR_MAIL2 as ADR_MAIL2,");
        sql.addSql("   ADR_ADDRESS.ADR_MAIL_CMT2 as ADR_MAIL_CMT2,");
        sql.addSql("   ADR_ADDRESS.ADR_MAIL3 as ADR_MAIL3,");
        sql.addSql("   ADR_ADDRESS.ADR_MAIL_CMT3 as ADR_MAIL_CMT3,");
        sql.addSql("   ADR_ADDRESS.ADR_POSTNO1 as ADR_POSTNO1,");
        sql.addSql("   ADR_ADDRESS.ADR_POSTNO2 as ADR_POSTNO2,");
        sql.addSql("   ADR_ADDRESS.TDF_SID as TDF_SID,");
        sql.addSql("   CMN_TDFK.TDF_NAME as ADDRESS_TDF_NAME,");
        sql.addSql("   ADR_ADDRESS.ADR_ADDR1 as ADR_ADDR1,");
        sql.addSql("   ADR_ADDRESS.ADR_ADDR2 as ADR_ADDR2,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL1 as ADR_TEL1,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL_NAI1 as ADR_TEL_NAI1,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL_CMT1 as ADR_TEL_CMT1,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL2 as ADR_TEL2,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL_NAI2 as ADR_TEL_NAI2,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL_CMT2 as ADR_TEL_CMT2,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL3 as ADR_TEL3,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL_NAI3 as ADR_TEL_NAI3,");
        sql.addSql("   ADR_ADDRESS.ADR_TEL_CMT3 as ADR_TEL_CMT3,");
        sql.addSql("   ADR_ADDRESS.ADR_FAX1 as ADR_FAX1,");
        sql.addSql("   ADR_ADDRESS.ADR_FAX_CMT1 as ADR_FAX_CMT1,");
        sql.addSql("   ADR_ADDRESS.ADR_FAX2 as ADR_FAX2,");
        sql.addSql("   ADR_ADDRESS.ADR_FAX_CMT2 as ADR_FAX_CMT2,");
        sql.addSql("   ADR_ADDRESS.ADR_FAX3 as ADR_FAX3,");
        sql.addSql("   ADR_ADDRESS.ADR_FAX_CMT3 as ADR_FAX_CMT3,");
        sql.addSql("   ADR_ADDRESS.ADR_BIKO as ADR_BIKO,");

        if (model.getCmdMode() == Adr010Const.CMDMODE_CONTACT) {
            sql.addSql("   CONTACT.ADC_SID as ADC_SID,");
            sql.addSql("   CONTACT.ADC_TITLE as ADC_TITLE,");
            sql.addSql("   CONTACT.ADC_TYPE as ADC_TYPE,");
            sql.addSql("   CONTACT.ADC_CTTIME as ADC_CTTIME,");
            sql.addSql("   CONTACT.ADC_CTTIME_TO as ADC_CTTIME_TO,");
            sql.addSql("   CONTACT.USI_SEI as CONTACT_USI_SEI,");
            sql.addSql("   CONTACT.USI_MEI as CONTACT_USI_MEI,");
            sql.addSql("   CONTACT.ADC_BIKO as ADC_BIKO,");
        }

        sql.addSql("   ADR_COMPANY.ACO_CODE as ACO_CODE,");
        sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME,");
        sql.addSql("   ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
        sql.addSql("   ADR_COMPANY.ACO_URL as ACO_URL,");
        sql.addSql("   ADR_COMPANY.ACO_BIKO as ACO_BIKO,");
        sql.addSql("   ADR_COMPANY.ACO_POSTNO1 as ACO_POSTNO1,");
        sql.addSql("   ADR_COMPANY.ACO_POSTNO2 as ACO_POSTNO2,");
        sql.addSql("   ADR_COMPANY.TDF_SID as COMPANY_TDF_SID,");
        sql.addSql("   COMPANY_TDFK.TDF_NAME as COMPANY_TDF_NAME,");
        sql.addSql("   ADR_COMPANY.ACO_ADDR1 as ACO_ADDR1,");
        sql.addSql("   ADR_COMPANY.ACO_ADDR2 as ACO_ADDR2,");

        sql.addSql("   COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
        sql.addSql("   COMPANY_BASE.ABA_NAME as ABA_NAME,");
        sql.addSql("   COMPANY_BASE.ABA_POSTNO1 as ABA_POSTNO1,");
        sql.addSql("   COMPANY_BASE.ABA_POSTNO2 as ABA_POSTNO2,");
        sql.addSql("   COMPANY_BASE.TDF_NAME as COMPANY_BASE_TDF_NAME,");
        sql.addSql("   COMPANY_BASE.ABA_ADDR1 as ABA_ADDR1,");
        sql.addSql("   COMPANY_BASE.ABA_ADDR2 as ABA_ADDR2,");
        sql.addSql("   COMPANY_BASE.ABA_BIKO as ABA_BIKO");

        sql = _createSearchSql(sql, model);
        return sql;
    }

    /**
     * <p>Create ADR_ADRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param model 検索条件
     * @param reqMdl RequestModel
     * @return created AdrBelongLabelModel
     * @throws SQLException SQL実行例外
     */
    protected Adr010CsvDetailModel _Adr010CsvDetailModelFromRs(ResultSet rs,
            Adr010SearchModel model,
            RequestModel reqMdl) throws SQLException {
        Adr010CsvDetailModel detailMdl = new Adr010CsvDetailModel();

        //アドレス帳SID
        detailMdl.setAdrSid(rs.getInt("ADR_SID"));
        //会社SID
        detailMdl.setAcoSid(rs.getInt("ACO_SID"));
        //会社拠点SID
        detailMdl.setAbaSid(rs.getInt("ABA_SID"));
        //氏名(姓)
        detailMdl.setUserSei(rs.getString("ADR_SEI"));
        //氏名(名)
        detailMdl.setUserMei(rs.getString("ADR_MEI"));
        //氏名カナ(姓)
        detailMdl.setUserSeiKn(rs.getString("ADR_SEI_KN"));
        //氏名カナ(名)
        detailMdl.setUserMeiKn(rs.getString("ADR_MEI_KN"));
        //氏名(姓名)
        detailMdl.setUserName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
        //氏名カナ(姓名)
        detailMdl.setUserNameKn(rs.getString("ADR_SEI_KN")
                                + " " + rs.getString("ADR_MEI_KN"));
        //企業コード
        detailMdl.setCompanyCode(rs.getString("ACO_CODE"));
        //会社名
        detailMdl.setCompanyName(rs.getString("ACO_NAME"));
        //会社名カナ
        detailMdl.setCompanyNameKn(rs.getString("ACO_NAME_KN"));
        //url
        detailMdl.setCompanyUrl(rs.getString("ACO_URL"));
        //会社情報備考
        detailMdl.setCompanyRemarks(rs.getString("ACO_BIKO"));

        //会社情報郵便番号上３桁
        detailMdl.setCompanyPostNo1(rs.getString("ACO_POSTNO1"));
        //会社情報郵便番号下４桁
        detailMdl.setCompanyPostNo2(rs.getString("ACO_POSTNO2"));
        //会社情報都道府県
        detailMdl.setCompanyTdfk(rs.getString("COMPANY_TDF_NAME"));
        //会社情報住所１
        detailMdl.setCompanyAddress1(rs.getString("ACO_ADDR1"));
        //会社情報住所２
        detailMdl.setCompanyAddress2(rs.getString("ACO_ADDR2"));

        //支店・営業所種別
        int abaType = rs.getInt("ABA_TYPE");
        detailMdl.setCompanyBaseType(AddressBiz.getCompanyBaseTypeName(abaType, reqMdl));
        //支店・営業所名
        detailMdl.setCompanyBaseName(rs.getString("ABA_NAME"));
        //支店・営業所郵便番号上３桁
        detailMdl.setCompanyBasePostNo1(rs.getString("ABA_POSTNO1"));
        //支店・営業所郵便番号下４桁
        detailMdl.setCompanyBasePostNo2(rs.getString("ABA_POSTNO2"));
        //支店・営業所都道府県
        detailMdl.setCompanyBaseTdfk(rs.getString("COMPANY_BASE_TDF_NAME"));
        //支店・営業所住所１
        detailMdl.setCompanyBaseAddress1(rs.getString("ABA_ADDR1"));
        //支店・営業所住所２
        detailMdl.setCompanyBaseAddress2(rs.getString("ABA_ADDR2"));
        //企業拠点備考
        detailMdl.setCompanyBaseRemarks(rs.getString("ABA_BIKO"));

        //所属
        detailMdl.setSyozoku(rs.getString("ADR_SYOZOKU"));
        //役職名
        detailMdl.setPositionName(rs.getString("APS_NAME"));

        //メールアドレス１
        detailMdl.setMail1(rs.getString("ADR_MAIL1"));
        //メールアドレスコメント１
        detailMdl.setMail1Comment(rs.getString("ADR_MAIL_CMT1"));
        //メールアドレス２
        detailMdl.setMail2(rs.getString("ADR_MAIL2"));
        //メールアドレスコメント１
        detailMdl.setMail2Comment(rs.getString("ADR_MAIL_CMT2"));
        //メールアドレス３
        detailMdl.setMail3(rs.getString("ADR_MAIL3"));
        //メールアドレスコメント１
        detailMdl.setMail3Comment(rs.getString("ADR_MAIL_CMT3"));

        //郵便番号上３桁
        detailMdl.setPostNo1(rs.getString("ADR_POSTNO1"));
        //郵便番号下４桁
        detailMdl.setPostNo2(rs.getString("ADR_POSTNO2"));
        //都道府県
        detailMdl.setTdfk(rs.getString("ADDRESS_TDF_NAME"));
        //住所１
        detailMdl.setAddress1(rs.getString("ADR_ADDR1"));
        //住所２
        detailMdl.setAddress2(rs.getString("ADR_ADDR2"));

        //電話番号１
        detailMdl.setTel1(rs.getString("ADR_TEL1"));
        //内線１
        detailMdl.setNai1(rs.getString("ADR_TEL_NAI1"));
        //電話番号コメント１
        detailMdl.setTel1Comment(rs.getString("ADR_TEL_CMT1"));
        //内線２
        detailMdl.setNai2(rs.getString("ADR_TEL2"));
        //電話番号２
        detailMdl.setTel2(rs.getString("ADR_TEL_NAI2"));
        //電話番号コメント２
        detailMdl.setTel2Comment(rs.getString("ADR_TEL_CMT2"));
        //電話番号３
        detailMdl.setTel3(rs.getString("ADR_TEL3"));
        //内線３
        detailMdl.setNai3(rs.getString("ADR_TEL_NAI3"));
        //電話番号コメント３
        detailMdl.setTel3Comment(rs.getString("ADR_TEL_CMT3"));

        //ＦＡＸ１
        detailMdl.setFax1(rs.getString("ADR_FAX1"));
        //ＦＡＸコメント１
        detailMdl.setFax1Comment(rs.getString("ADR_FAX_CMT1"));
        //ＦＡＸ２
        detailMdl.setFax2(rs.getString("ADR_FAX2"));
        //ＦＡＸコメント２
        detailMdl.setFax2Comment(rs.getString("ADR_FAX_CMT2"));
        //ＦＡＸ３
        detailMdl.setFax3(rs.getString("ADR_FAX3"));
        //ＦＡＸコメント３
        detailMdl.setFax3Comment(rs.getString("ADR_FAX_CMT3"));

        //備考
        detailMdl.setBiko(rs.getString("ADR_BIKO"));

        if (model.getCmdMode() == Adr010Const.CMDMODE_CONTACT) {
            detailMdl.setContactSid(rs.getInt("ADC_SID"));

            UDate frm = UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME"));
            detailMdl.setContactDate(frm.getStrYear()  + "/"
                         + frm.getStrMonth()  + "/"
                         + frm.getStrDay());
            detailMdl.setContactTime(frm.getStrHour()  + ":"
                              + frm.getStrMinute());
            UDate to = UDate.getInstanceTimestamp(rs.getTimestamp("ADC_CTTIME_TO"));
            detailMdl.setContactDateTo(to.getStrYear()  + "/"
                       + to.getStrMonth()  + "/"
                       + to.getStrDay());
            detailMdl.setContactTimeTo(to.getStrHour()  + ":"
                             + to.getStrMinute());

            detailMdl.setContactType(rs.getInt("ADC_TYPE"));
            detailMdl.setContactTitle(rs.getString("ADC_TITLE"));
            detailMdl.setContactEntryUser(
                    rs.getString("CONTACT_USI_SEI")
                    + " "
                    + rs.getString("CONTACT_USI_MEI"));

            detailMdl.setContactBiko(rs.getString("ADC_BIKO"));

            //コンタクト履歴種別
//            if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_OTHER) {
//                detailMdl.setTypeName(GSConst.TEXT_CONTYP_OTHER);
//            } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_TEL) {
//                detailMdl.setTypeName(GSConst.TEXT_CONTYP_TEL);
//            } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_MAIL) {
//                detailMdl.setTypeName(GSConst.TEXT_CONTYP_MAIL);
//            } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_WEB) {
//                detailMdl.setTypeName(GSConst.TEXT_CONTYP_WEB);
//            } else if (rs.getInt("ADC_TYPE") == GSConst.CONTYP_MEETING) {
//                detailMdl.setTypeName(GSConst.TEXT_CONTYP_MEETING);
//            }
        }
        return detailMdl;
    }

    /**
     * <br>[機  能] アドレス帳情報の検索を行う(エクスポート用)
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @param reqMdl RequestModel
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<Adr010CsvDetailModel> getSearchResultListForExport(
            Adr010SearchModel model,
            RequestModel reqMdl)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Adr010CsvDetailModel> ret = new ArrayList<Adr010CsvDetailModel>();
        con = getCon();

        try {
            SqlBuffer sql = _createSearchSQLForExport(model);
            //並び順を設定
            int order = model.getOrderKey();
            sql.addSql(" order by");
            switch (model.getSortKey()) {
                case Adr010Const.SORTKEY_UNAME :
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_SEI_KN", order) + ",");
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_MEI_KN", order));
                    break;
                case Adr010Const.SORTKEY_CONAME :
                    sql.addSql(_getSortSql("ADR_COMPANY.ACO_NAME_KN", order));
                    break;
                case Adr010Const.SORTKEY_COBASENAME :
                    sql.addSql(_getSortSql("COMPANY_BASE.ABA_NAME", order));
                    break;
                case Adr010Const.SORTKEY_POSITION :
                    sql.addSql(_getSortSql("YAKUSYOKU_EXIST", order) + ",");
                    sql.addSql(_getSortSql("ADR_POSITION.APS_SORT", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_DATE :
                    sql.addSql(_getSortSql("CONTACT.ADC_CTTIME", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_TYPE :
                    sql.addSql(_getSortSql("CONTACT.ADC_TYPE", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_TITLE :
                    sql.addSql(_getSortSql("CONTACT.ADC_TITLE", order));
                    break;
                case Adr010Const.SORTKEY_CONTACT_ENTRYUSER :
                    sql.addSql(_getSortSql("CONTACT.USI_SEI_KN", order) + ",");
                    sql.addSql(_getSortSql("CONTACT.USI_MEI_KN", order));
                    break;
                default :
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_SEI", order) + ",");
                    sql.addSql(_getSortSql("ADR_ADDRESS.ADR_MEI", order));
                    break;
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Adr010CsvDetailModel detailMdl = _Adr010CsvDetailModelFromRs(rs, model, reqMdl);
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
     * <br>[機  能] 氏名カナ 姓の先頭一文字の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<String> getSeiInitialList(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_SINI");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where");
            AddressDao.addViewableWhereSQL(sql, userSid);
            sql.addSql(" group by");
            sql.addSql("   ADR_SINI");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("ADR_SINI"));
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
     * <br>[機  能] 会社名の先頭一文字の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<String> getCompanyInitialList(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_COMPANY.ACO_SINI");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS,");
            sql.addSql("   ADR_COMPANY");
            sql.addSql(" where");
            sql.addSql("   ADR_COMPANY.ACO_SID = ADR_ADDRESS.ACO_SID");
            sql.addSql(" and");
            AddressDao.addViewableWhereSQL(sql, userSid);
            sql.addSql(" group by");
            sql.addSql("   ADR_COMPANY.ACO_SINI");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("ACO_SINI"));
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
    protected SqlBuffer _createSearchSql(SqlBuffer sql, Adr010SearchModel model)
    throws SQLException {

        sql.addSql(" from");
        if (model.getCmdMode() == Adr010Const.CMDMODE_CONTACT) {
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      ADR_CONTACT.ADC_SID as ADC_SID,");
            sql.addSql("      ADR_CONTACT.ADR_SID as ADR_SID,");
            sql.addSql("      ADR_CONTACT.ADC_TITLE as ADC_TITLE,");
            sql.addSql("      ADR_CONTACT.ADC_TYPE as ADC_TYPE,");
            sql.addSql("      ADR_CONTACT.ADC_CTTIME as ADC_CTTIME,");
            sql.addSql("      ADR_CONTACT.ADC_CTTIME_TO as ADC_CTTIME_TO,");
            sql.addSql("      ADR_CONTACT.PRJ_SID as PRJ_SID,");
            sql.addSql("      ADR_CONTACT.ADC_BIKO as ADC_BIKO,");
            sql.addSql("      CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("      CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("      CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("      CMN_USRM.USR_JKBN as USR_JKBN");
            sql.addSql("    from");
            sql.addSql("      ADR_CONTACT");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM_INF");
            sql.addSql("      on");
            sql.addSql("        ADR_CONTACT.ADC_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM");
            sql.addSql("      on");
            sql.addSql("        ADR_CONTACT.ADC_AUID = CMN_USRM.USR_SID");
            if (model.getPrjSid() > 0) {
                sql.addSql("      left join");
                sql.addSql("        ADR_CONTACT_PRJ");
                sql.addSql("      on");
                sql.addSql("        ADR_CONTACT.ADC_SID = ADR_CONTACT_PRJ.ADC_SID");
            }

            sql.addSql("    where");
            if (model.isDateSchFlg()) {
                sql.addSql("      ADR_CONTACT.ADC_CTTIME>=?");
                sql.addSql("    and");
                sql.addSql("      ADR_CONTACT.ADC_CTTIME_TO<=?");
                sql.addSql("    and");
                sql.addDateValue(model.getDateFr());
                sql.addDateValue(model.getDateTo());
            }

            if (model.getSyubetsu() == GSConstAddress.NOT_SYUBETU) {
                sql.addSql("      ADR_CONTACT.ADC_TYPE>=0");
            } else {
                sql.addSql("      ADR_CONTACT.ADC_TYPE=?");
                sql.addIntValue(model.getSyubetsu());
            }

            if (model.getPrjSid() > 0) {
                sql.addSql("    and");
                sql.addSql("      ADR_CONTACT_PRJ.PRJ_SID=?");
                sql.addIntValue(model.getPrjSid());
            }

            // キーワード入力時
            List<String> keywordList = model.getAdrKeyValue();
            if (keywordList != null && keywordList.size() > 0) {

                String keywordJoin = "  and";
                if (model.getKeyWordkbn() == GSConstAddress.KEY_WORD_KBN_OR) {
                    keywordJoin = "   or";
                }

                //検索対象の「タイトル」がチェック済みの場合
                if (model.isTargetTitle()) {
                    sql.addSql("  and");
                    if (model.isTargetBiko()) {
                        sql.addSql("    (");
                    }
                    sql.addSql("      (");
                    for (int i = 0; i < keywordList.size(); i++) {
                        if (i > 0) {
                            sql.addSql(keywordJoin);
                        }
                        sql.addSql("       ADR_CONTACT.ADC_TITLE like '%"
                                + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                                + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                    }
                    sql.addSql("      )");
                }

                //検索対象の「内容」がチェック済みの場合
                if (model.isTargetBiko()) {
                    if (model.isTargetTitle()) {
                        sql.addSql("      or");
                    } else {
                        sql.addSql("      and");
                    }
                    sql.addSql("      (");
                    for (int i = 0; i < keywordList.size(); i++) {
                        if (i > 0) {
                            sql.addSql(keywordJoin);
                        }
                        sql.addSql("       ADR_CONTACT.ADC_BIKO like '%"
                                + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                                + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                    }
                    sql.addSql("      )");

                    if (model.isTargetTitle()) {
                        sql.addSql("    )");
                    }
                }
            }
            sql.addSql("   ) CONTACT,");
        }
        //プロジェクト検索
        if (model.isPrjSerchFlg() == true) {
            sql.addSql("   PRJ_MEMBERS,");
            sql.addSql("   PRJ_PRJDATA,");
        }
        sql.addSql("   ADR_ADDRESS");
        sql.addSql("   left join");
        sql.addSql("     ADR_COMPANY");
        sql.addSql("   on");
        sql.addSql("     ADR_ADDRESS.ACO_SID = ADR_COMPANY.ACO_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_TDFK COMPANY_TDFK");
        sql.addSql("   on");
        sql.addSql("     ADR_COMPANY.TDF_SID = COMPANY_TDFK.TDF_SID");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("      select");
        sql.addSql("        ADR_COMPANY_BASE.ABA_SID as ABA_SID,");
        sql.addSql("        ADR_COMPANY_BASE.ACO_SID as ACO_SID,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_POSTNO1 as ABA_POSTNO1,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_POSTNO2 as ABA_POSTNO2,");
        sql.addSql("        ADR_COMPANY_BASE.TDF_SID as TDF_SID,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_ADDR1 as ABA_ADDR1,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_ADDR2 as ABA_ADDR2,");
        sql.addSql("        ADR_COMPANY_BASE.ABA_BIKO as ABA_BIKO,");
        sql.addSql("        CMN_TDFK.TDF_NAME as TDF_NAME");
        sql.addSql("      from");
        sql.addSql("        ADR_COMPANY_BASE");
        sql.addSql("      left join");
        sql.addSql("        CMN_TDFK");
        sql.addSql("      on");
        sql.addSql("        ADR_COMPANY_BASE.TDF_SID = CMN_TDFK.TDF_SID");
        sql.addSql("     ) COMPANY_BASE");
        sql.addSql("   on");
        sql.addSql("     ADR_ADDRESS.ABA_SID = COMPANY_BASE.ABA_SID");

        if (model.getTdfk() > 0) {
            sql.addSql("   left join");
            sql.addSql("     ADR_COMPANY_BASE");
            sql.addSql("   on");
            sql.addSql("     ADR_ADDRESS.ACO_SID = ADR_COMPANY_BASE.ACO_SID");
        }

        sql.addSql("   left join");
        sql.addSql("     ADR_POSITION");
        sql.addSql("   on");
        sql.addSql("     ADR_ADDRESS.APS_SID = ADR_POSITION.APS_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_TDFK");
        sql.addSql("   on");
        sql.addSql("     ADR_ADDRESS.TDF_SID = CMN_TDFK.TDF_SID");

          //プロジェクト検索
          if (model.isPrjSerchFlg() == true) {
            sql.addSql("   left join");
            sql.addSql("     PRJ_ADDRESS");
            sql.addSql("   on");
            sql.addSql("     ADR_ADDRESS.ADR_SID = PRJ_ADDRESS.ADR_SID");
            sql.addSql("   left join");
            sql.addSql("     PRJ_COMPANY");
            sql.addSql("   on");
            sql.addSql("     ADR_COMPANY.ACO_SID = PRJ_COMPANY.ACO_SID");
          }

        //閲覧権限
        sql.addSql(" where");

        if (model.getCmdMode() == Adr010Const.CMDMODE_CONTACT) {
            sql.addSql("   CONTACT.ADR_SID = ADR_ADDRESS.ADR_SID");
            sql.addSql(" and");

            int cnt = model.getHaveTmpFileAdcSids().size();

            if (model.getTempFileExist() != GSConstAddress.TEMPFILE_KBN_FREE && cnt > 0) {
                //添付ファイルが存在するコンタクト履歴SID
                if (model.getTempFileExist() == GSConstAddress.TEMPFILE_KBN_EXIST) {
                    sql.addSql(" CONTACT.ADC_SID in(");

                //添付ファイルが存在しないコンタクト履歴SID
                } else if (model.getTempFileExist() == GSConstAddress.TEMPFILE_KBN_NOT_EXIST) {
                    sql.addSql(" CONTACT.ADC_SID not in(");
                }

                for (Integer adcSid : model.getHaveTmpFileAdcSids()) {
                    sql.addSql(String.valueOf(adcSid));
                    if (cnt > 1) {
                        sql.addSql(",");
                    }
                    cnt--;
                }
                sql.addSql(")");
                sql.addSql(" and");
            }
        }

        int sessionUserSid = model.getSessionUser();

        //閲覧権限
        if (model.isAdminSearchFlg()) {
            //管理者設定 アドレス管理の検索では閲覧権限を無視して全件表示する
            sql.addSql("   ADR_ADDRESS.ADR_SID >= 0 ");
        } else {
            AddressDao.addViewableWhereSQL(sql, sessionUserSid);
        }
        //プロジェクト検索
        if (model.isPrjSerchFlg() == true) {
            sql.addSql(" and");
            sql.addSql("   PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_MEMBERS.PRJ_SID = PRJ_ADDRESS.PRJ_SID");

            //参加プロジェクトかどうか
            if (model.getProjectKbn() == 0) {
                sql.addSql(" and");
                sql.addSql("   PRJ_MEMBERS.USR_SID = ?");
            }

            //プロジェクト状態:未完了
            if (model.getStatusKbn() == 0) {
                sql.addSql(" and");
                sql.addSql(" exists (");
                sql.addSql("   select * from");
                sql.addSql("   PRJ_PRJDATA,");
                sql.addSql("   PRJ_PRJSTATUS");
                sql.addSql("   where");
                sql.addSql("     PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
                sql.addSql("   and");
                sql.addSql("     PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");
                sql.addSql("   and");
                sql.addSql("     PRJ_ADDRESS.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
                sql.addSql("   and");
                sql.addSql("     PRS_RATE < 100");
                sql.addSql(" )");

            //プロジェクト状態:完了
            } else if (model.getStatusKbn() == 1) {
                sql.addSql(" and");
                sql.addSql(" exists (");
                sql.addSql("   select * from");
                sql.addSql("   PRJ_PRJDATA,");
                sql.addSql("   PRJ_PRJSTATUS");
                sql.addSql("   where");
                sql.addSql("     PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
                sql.addSql("   and");
                sql.addSql("     PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");
                sql.addSql("   and");
                sql.addSql("     PRJ_ADDRESS.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
                sql.addSql("   and");
                sql.addSql("     PRS_RATE = 100");
                sql.addSql(" )");
            }

            if (model.getPrjSid() != -1) {
                sql.addSql(" and");
                sql.addSql("   PRJ_ADDRESS.PRJ_SID = ?");
            }
        }

        //プロジェクト検索
        if (model.isPrjSerchFlg() == true) {
            if (model.getProjectKbn() == 0) {
                sql.addIntValue(sessionUserSid);
            }

            if (model.getPrjSid() != -1) {
                sql.addIntValue(model.getPrjSid());
            }
        }

        //会社名カナ 頭文字
        if (!StringUtil.isNullZeroString(model.getCnameKnHead())) {
            sql.addSql(" and");
            sql.addSql("   ADR_COMPANY.ACO_SINI = ?");
            sql.addStrValue(model.getCnameKnHead());
            return sql;
        }

        //企業コード
        if (!StringUtil.isNullZeroString(model.getCoCode())) {
            sql.addSql(" and");
            sql.addSql("   ADR_COMPANY.ACO_CODE = ?");
            sql.addStrValue(model.getCoCode());
        }

        //会社名
        if (!StringUtil.isNullZeroString(model.getCoName())) {
            sql.addSql(" and");
            sql.addSql("   ADR_COMPANY.ACO_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getCoName())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //会社名カナ
        if (!StringUtil.isNullZeroString(model.getCoNameKn())) {
            sql.addSql(" and");
            sql.addSql("   ADR_COMPANY.ACO_NAME_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getCoNameKn())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //支店・営業所名
        if (!StringUtil.isNullZeroString(model.getCoBaseName())) {
            sql.addSql(" and");
            sql.addSql("   COMPANY_BASE.ABA_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getCoBaseName())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //業種
        if (model.getAtiSid() > 0) {
            sql.addSql(" and");
            sql.addSql("   ADR_COMPANY.ACO_SID in (");
            sql.addSql("     select ACO_SID from ADR_BELONG_INDUSTRY");
            sql.addSql("     where");
            sql.addSql("       ATI_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(model.getAtiSid());
        }

        //都道府県
        if (model.getTdfk() > 0) {
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     ADR_COMPANY.TDF_SID = ?");
            sql.addSql("   or");
            sql.addSql("     ADR_COMPANY_BASE.TDF_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(model.getTdfk());
            sql.addIntValue(model.getTdfk());
        }

        //備考
        if (!StringUtil.isNullZeroString(model.getBiko())) {
            sql.addSql(" and");
            sql.addSql("   ADR_COMPANY.ACO_BIKO like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getBiko())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //氏名カナ 頭文字
        if (!StringUtil.isNullZeroString(model.getUnameKnHead())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SINI = ?");
            sql.addStrValue(model.getUnameKnHead());
        }

        //ユーザ・グループ
        if (model.getGroup() > 0 && model.getUser() < 1) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");
            sql.addSql("     select");
            sql.addSql("       ADR_PERSONCHARGE.ADR_SID");
            sql.addSql("     from");
            sql.addSql("       ADR_PERSONCHARGE,");
            sql.addSql("       (");
            sql.addSql("         select");
            sql.addSql("           USR_SID");
            sql.addSql("         from");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where");
            sql.addSql("           GRP_SID = ?");
            sql.addSql("       ) GRP");
            sql.addSql("     where ADR_PERSONCHARGE.USR_SID = GRP.USR_SID");
            sql.addSql("   )");
            sql.addIntValue(model.getGroup());
        } else if (model.getUser() > 0) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");
            sql.addSql("     select ADR_SID from ADR_PERSONCHARGE");
            sql.addSql("     where USR_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(model.getUser());
        }

        //氏名 姓
        if (!StringUtil.isNullZeroString(model.getUnameSei())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SEI like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getUnameSei())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //氏名 名
        if (!StringUtil.isNullZeroString(model.getUnameMei())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_MEI like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getUnameMei())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //氏名カナ 姓
        if (!StringUtil.isNullZeroString(model.getUnameSeiKn())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SEI_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getUnameSeiKn())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //氏名カナ 名
        if (!StringUtil.isNullZeroString(model.getUnameMeiKn())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_MEI_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getUnameMeiKn())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //所属
        if (!StringUtil.isNullZeroString(model.getSyozoku())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SYOZOKU like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getSyozoku())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //役職
        if (model.getPosition() > 0) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.APS_SID = ?");
            sql.addIntValue(model.getPosition());
        }

        //E-MAIL
        if (!StringUtil.isNullZeroString(model.getMail())) {
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      ADR_ADDRESS.ADR_MAIL1 like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getMail())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   or");
            sql.addSql("      ADR_ADDRESS.ADR_MAIL2 like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getMail())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   or");
            sql.addSql("      ADR_ADDRESS.ADR_MAIL3 like '%"
                    + JDBCUtil.escapeForLikeSearch(model.getMail())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   )");
        }

        //ラベル
        if (model.getLabel() != null && model.getLabel().length > 0) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");
            sql.addSql("     select ADR_SID from ADR_BELONG_LABEL");
            sql.addSql("     where");
            sql.addSql("       ALB_SID in (");

            int index = 0;
            int lastIndex = model.getLabel().length - 1;
            for (String albSid : model.getLabel()) {
                if (index == lastIndex) {
                    sql.addSql("         ?");
                } else {
                    sql.addSql("         ?,");
                }
                sql.addIntValue(NullDefault.getInt(albSid, 0));
                index++;
            }
            sql.addSql("       )");

            sql.addSql("   )");
        }

        //会社SID
        if (model.getCompanySid() != null) {
            sql.addSql(" and");
            sql.addSql("   COALESCE(ADR_COMPANY.ACO_SID, 0) = ?");
            sql.addIntValue(model.getCompanySid().intValue());
        }

        //会社拠点SID
        if (model.getCompanyBaseSid() != null) {
            sql.addSql(" and");
            sql.addSql("   COALESCE(COMPANY_BASE.ABA_SID, 0) = ?");
            sql.addIntValue(model.getCompanyBaseSid().intValue());
        }
        return sql;
    }

    /**
     * <br>[機  能] ソート部分のSQLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortField 並び替えの基準となるフィールド名称
     * @param order 並び順(0:asc or 1:desc)
     * @return ソート部分のSQL
     */
    protected String _getSortSql(String sortField, int order) {

        StringBuilder sb = new StringBuilder("   ");
        sb.append(sortField);
        if (order == Adr010Const.ORDERKEY_DESC) {
            sb.append(" desc");
        } else {
            sb.append(" asc");
        }

        return sb.toString();
    }

    /**
     * <br>[機  能] yyyy/mm/dd hh:mm を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @return String
     */
    private String __cleateDspDate(UDate date) {

        if (date == null) {
            return "";
        }

        StringBuilder viewDate = new StringBuilder("");

        viewDate.append(date.getStrYear());
        viewDate.append("/");
        viewDate.append(date.getStrMonth());
        viewDate.append("/");
        viewDate.append(date.getStrDay());
        viewDate.append(" ");
        viewDate.append(date.getStrHour());
        viewDate.append(":");
        viewDate.append(date.getStrMinute());

        return viewDate.toString();
    }

    /**
     * <br>[機  能] プロジェクト情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int AddressPrjSearchModel
     * @throws SQLException SQL実行例外
     */
    public List<Adr010ProjectDataModel> getDashBoardProjectList(Adr010SearchModel search)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Adr010ProjectDataModel> ret = new ArrayList<Adr010ProjectDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateDashBoardProjectGetSql(search);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Adr010ProjectDataModel retMdl = new Adr010ProjectDataModel();
                retMdl.setProjectSid(rs.getInt("PRJ_SID"));
                retMdl.setProjectName(rs.getString("PRJ_NAME"));
                ret.add(retMdl);
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
     * <br>[機  能] SQLを作成する(プロジェクト取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __cleateDashBoardProjectGetSql(Adr010SearchModel search) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        //一覧表示
        sql.addSql("    distinct PRJ_PRJDATA.PRJ_SID as PRJ_SID,");
        sql.addSql("    PRJ_PRJDATA.PRJ_NAME as PRJ_NAME");
        sql.addSql("  from");
        sql.addSql("    PRJ_PRJDATA,");
        sql.addSql("    PRJ_PRJSTATUS");
        sql.addSql("  where");
        sql.addSql("    PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
        sql.addSql("  and");
        sql.addSql("    PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");

        sql.addSql("  and");
        sql.addSql("    (");

        //自分のマイプロジェクト
        sql.addSql("      (");
        sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
        sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
        sql.addSql("        and");
        sql.addSql("          exists");
        sql.addSql("            (");
        sql.addSql("              select");
        sql.addSql("                *");
        sql.addSql("              from");
        sql.addSql("                PRJ_MEMBERS");
        sql.addSql("              where");
        sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
        sql.addIntValue(search.getUsrSid());
        sql.addSql("              and");
        sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
        sql.addSql("            )");
        sql.addSql("      )");

        int prjStatus1 = search.getProjectKbn();
        int prjStatus2 = search.getStatusKbn();

        //プロジェクト条件：参加プロジェクト
        if (prjStatus1 == GSConstAddress.PROTYPE_ADD) {

            //所属している通常プロジェクト
            sql.addSql("      or");
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("        and");
            sql.addSql("          exists");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                *");
            sql.addSql("              from");
            sql.addSql("                PRJ_MEMBERS");
            sql.addSql("              where");
            sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
            sql.addIntValue(search.getUsrSid());
            sql.addSql("              and");
            sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("            )");
            sql.addSql("      )");
            sql.addSql("    )");

            switch (prjStatus2) {
                //未完
                case GSConstAddress.STATUS_NO:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                    sql.addIntValue(Adr010Const.RATE_MAX);
                    break;
                //完了
                case GSConstAddress.STATUS_COMP:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                    sql.addIntValue(Adr010Const.RATE_MAX);
                    break;
                //全て
                case GSConstAddress.STATUS_ALL:
                    break;
                default:
                    break;
            }

        //プロジェクト条件：全て(システム管理者限定)
        } else if (prjStatus1 == GSConstAddress.PROTYPE_ALL && search.isUsrKbn()) {
            //通常プロジェクト
            sql.addSql("      or");
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("      )");
            sql.addSql("    )");

            switch (prjStatus2) {
                //全ての未完プロジェクト
                case GSConstAddress.STATUS_NO:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //全ての完了プロジェクト
                case GSConstAddress.STATUS_COMP:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //全てのプロジェクト
                case GSConstAddress.STATUS_ALL:
                    break;
                default:
                    break;
            }

        } else {
            sql.addSql(")");
        }

        return sql;
    }

    /**
     * <br>[機  能] プロジェクト名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public String getProjectName(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_NAME");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("PRJ_NAME");
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
