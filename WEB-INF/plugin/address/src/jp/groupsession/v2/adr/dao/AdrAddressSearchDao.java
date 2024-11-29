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
import jp.groupsession.v2.adr.model.AdrAddressSearchModel;
import jp.groupsession.v2.adr.restapi.biz.AdrRestAddressBiz;
import jp.groupsession.v2.adr.restapi.entities.AdrEntitiesResultModel;
import jp.groupsession.v2.adr.restapi.entities.AdrEntitiesResultModel.LabelInfo;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;

/**
 * <br>[機  能] アドレス情報の検索で使用されるDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrAddressSearchDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrAddressSearchDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrAddressSearchDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アドレス帳情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @param reqMdl RequestModel
     * @return 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(
            AdrAddressSearchModel model,
            RequestModel reqMdl)
                    throws SQLException {

        int count = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_ADDRESS.ADR_SID) as CNT");

            //検索条件設定
            sql = __createSearchSql(sql, model, reqMdl);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] アドレス帳情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @param reqMdl RequestModel
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<AdrEntitiesResultModel> getSearchAddressList(
            AdrAddressSearchModel model,
            RequestModel reqMdl)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AdrEntitiesResultModel> ret = new ArrayList<AdrEntitiesResultModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_ADDRESS.ADR_SID as ADR_SID,");
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

            //検索条件設定
            sql = __createSearchSql(sql, model, reqMdl);

            //並び順を設定
            String order = "";
            if (model.getOrderKey() == GSConst.ORDER_KEY_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            sql.addSql("   ADR_ADDRESS.ADR_SEI_KN" + order + ",");
            sql.addSql("   ADR_ADDRESS.ADR_MEI_KN" + order);

            sql.setPagingValue(model.getOffset(), model.getLimit());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AdrEntitiesResultModel detailMdl = _AdrDetailModelFromRs(rs, reqMdl);
                ret.add(detailMdl);
            }


            //アドレス情報に付与されているラベル情報、及びラベルカテゴリ情報を取得する
            if (!ret.isEmpty()) {
                JDBCUtil.closeStatement(pstmt);

                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   ADR_LABEL.ALB_SID as ALB_SID,");
                sql.addSql("   ADR_LABEL.ALB_NAME as ALB_NAME,");
                sql.addSql("   ADR_LABEL_CATEGORY.ALC_SID as ALC_SID,");
                sql.addSql("   ADR_LABEL_CATEGORY.ALC_NAME as ALC_NAME");
                sql.addSql(" from");
                sql.addSql("   ADR_LABEL,");
                sql.addSql("   ADR_LABEL_CATEGORY,");
                sql.addSql("   ADR_BELONG_LABEL");
                sql.addSql(" where");
                sql.addSql("   ADR_BELONG_LABEL.ADR_SID = ?");
                sql.addSql(" and");
                sql.addSql("   ADR_LABEL.ALB_SID = ADR_BELONG_LABEL.ALB_SID");
                sql.addSql(" and");
                sql.addSql("   ADR_LABEL.ALC_SID = ADR_LABEL_CATEGORY.ALC_SID");
                sql.addSql(" order by");
                sql.addSql("   ADR_LABEL.ALB_SORT");

                pstmt = con.prepareStatement(sql.toSqlString());

                CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
                CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
                AdrRestAddressBiz addressBiz = new AdrRestAddressBiz();

                for (int idx = 0; idx < ret.size(); idx++) {
                    int adrSid = ret.get(idx).getSid();
                    JDBCUtil.closeResultSet(rs);
                    sql.clearValue();
                    sql.addIntValue(adrSid);
                    log__.info(sql.toLogString());

                    List<LabelInfo> labelList = new ArrayList<>();
                    pstmt.setInt(1, adrSid);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        LabelInfo labelData = new LabelInfo();
                        labelData.setSid(rs.getInt("ALB_SID"));
                        labelData.setName(rs.getString("ALB_NAME"));
                        labelData.setCategorySid(rs.getInt("ALC_SID"));
                        labelData.setCategoryName(rs.getString("ALC_NAME"));

                        labelList.add(labelData);
                    }

                    ret.get(idx).setLabelArray(labelList);

                    //担当者情報を取得する
                    ret.get(idx).setTantoUserArray(
                        addressBiz.getTantoUserList(con, adrSid, sortMdl));
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
     * <br>[機  能] アドレス帳の検索SQLの条件部を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param model 検索条件
     * @param reqMdl RequestModel
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createSearchSql(
        SqlBuffer sql,
        AdrAddressSearchModel model,
        RequestModel reqMdl) throws SQLException {

        sql.addSql(" from");
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

        //閲覧権限
        sql.addSql(" where");
        AddressDao.addViewableWhereSQL(sql, reqMdl.getSmodel().getUsrsid());

        //カナ順取得開始位置
        if (!StringUtil.isNullZeroString(model.getKanaStartOffset())) {
            sql.addSql(" and");
            if (model.getOrderKey() == GSConst.ORDER_KEY_DESC) {
                sql.addSql("   ADR_ADDRESS.ADR_SINI < ?");
            } else {
                sql.addSql("   ADR_ADDRESS.ADR_SINI >= ?");
            }
            sql.addStrValue(model.getKanaStartOffset());
        }

        //検索キーワード
        if (!StringUtil.isNullZeroString(model.getKeyword())) {
            //キーワードを半角スペースで分割する
            String[] keywordArray = model.getKeyword().split(" ");

            sql.addSql(" and");
            sql.addSql("   (");

            //氏名
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_SEI");
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_MEI");

            //氏名カナ
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_SEI_KN");
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_MEI_KN");

            //メールアドレス
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_MAIL1");
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_MAIL2");
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_MAIL3");

            //会社名・会社名カナ
            sql.addSql("   or");
            sql.addSql("     ADR_ADDRESS.ACO_SID in (");
            sql.addSql("       select ACO_SID from ADR_COMPANY");
            sql.addSql("       where");
            __addKeywordSql(sql, keywordArray, "ACO_NAME");
            sql.addSql("       or");
            __addKeywordSql(sql, keywordArray, "ACO_NAME_KN");
            sql.addSql("     )");

            //支店・営業所名
            sql.addSql("   or");
            sql.addSql("     ADR_ADDRESS.ABA_SID in (");
            sql.addSql("       select ABA_SID from ADR_COMPANY_BASE");
            sql.addSql("       where");
            __addKeywordSql(sql, keywordArray, "ABA_NAME");
            sql.addSql("     )");

            //所属
            sql.addSql("   or");
            __addKeywordSql(sql, keywordArray, "ADR_ADDRESS.ADR_SYOZOKU");

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
            for (int albSid : model.getLabel()) {
                if (index == lastIndex) {
                    sql.addSql("         ?");
                } else {
                    sql.addSql("         ?,");
                }
                sql.addIntValue(albSid);
                index++;
            }
            sql.addSql("       )");

            sql.addSql("   )");
        }

        //氏名カナ 先頭1文字
        if (!StringUtil.isNullZeroString(model.getUnameKnHead())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SINI = ?");
            sql.addStrValue(model.getUnameKnHead());
        }

        //役職
        if (model.getPosition() > 0) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.APS_SID = ?");
            sql.addIntValue(model.getPosition());
        }

        //都道府県SID
        if (model.getTdfk() > 0) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.TDF_SID = ?");
            sql.addIntValue(model.getTdfk());
        }

        //ユーザID
        if (!StringUtil.isNullZeroString(model.getUserId())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");
            sql.addSql("     select ADR_SID");
            sql.addSql("     from ADR_PERSONCHARGE, CMN_USRM");
            sql.addSql("     where CMN_USRM.USR_LGID = ?");
            sql.addSql("     and ADR_PERSONCHARGE.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("   )");
            sql.addStrValue(model.getUserId());
        }

        //グループID
        if (!StringUtil.isNullZeroString(model.getGroupId())) {
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");
            sql.addSql("     select");
            sql.addSql("       ADR_PERSONCHARGE.ADR_SID");
            sql.addSql("     from");
            sql.addSql("       ADR_PERSONCHARGE,");
            sql.addSql("       (");
            sql.addSql("         select");
            sql.addSql("           CMN_BELONGM.USR_SID");
            sql.addSql("         from");
            sql.addSql("           CMN_GROUPM,");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where");
            sql.addSql("           CMN_GROUPM.GRP_ID = ?");
            sql.addSql("         and");
            sql.addSql("           CMN_GROUPM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("       ) GRP");
            sql.addSql("     where ADR_PERSONCHARGE.USR_SID = GRP.USR_SID");
            sql.addSql("   )");
            sql.addStrValue(model.getGroupId());
        }

        if (model.getCompanySearchType() == AdrAddressSearchModel.COMPANYSEACHTYPE_MITOROKU_ONLY) {
            //会社未登録アドレス情報のみ検索時
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ACO_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ABA_SID = ?");
            sql.addIntValue(0);
            sql.addIntValue(0);

        } else if (model.getCompanySearchType() == AdrAddressSearchModel.COMPANYSEACHTYPE_NO_LMIT) {
            //企業コード
            if (!StringUtil.isNullZeroString(model.getCoCode())) {
                sql.addSql(" and");
                sql.addSql("   ADR_COMPANY.ACO_CODE = ?");
                sql.addStrValue(model.getCoCode());
            }

            //会社拠点SID
            if (model.getCompanyBaseSid() > 0) {
                sql.addSql(" and");
                sql.addSql("   COALESCE(COMPANY_BASE.ABA_SID, 0) = ?");
                sql.addIntValue(model.getCompanyBaseSid().intValue());
            }

            //会社名カナ 頭文字
            if (!StringUtil.isNullZeroString(model.getCnameKnHead())) {
                sql.addSql(" and");
                sql.addSql("   ADR_COMPANY.ACO_SINI = ?");
                sql.addStrValue(model.getCnameKnHead());
            }

            //業種SID
            if (model.getAtiSid() > 0) {
                sql.addSql(" and");
                sql.addSql("   ADR_COMPANY.ACO_SID in (");
                sql.addSql("     select ACO_SID from ADR_BELONG_INDUSTRY");
                sql.addSql("     where");
                sql.addSql("       ATI_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(model.getAtiSid());
            }
        }
        return sql;
    }

    /**
     * <p>取得したアドレス帳情報を結果Modelに格納する
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @return created AdrBelongLabelModel
     * @throws SQLException SQL実行例外
     */
    protected AdrEntitiesResultModel _AdrDetailModelFromRs(ResultSet rs,
            RequestModel reqMdl) throws SQLException {
        AdrEntitiesResultModel detailMdl = new AdrEntitiesResultModel();

        //アドレス帳SID
        detailMdl.setSid(rs.getInt("ADR_SID"));
        //氏名 姓
        detailMdl.setSeiText(rs.getString("ADR_SEI"));
        //氏名 名
        detailMdl.setMeiText(rs.getString("ADR_MEI"));
        //氏名 姓(カナ)
        detailMdl.setSeiKanaText(rs.getString("ADR_SEI_KN"));
        //氏名 名(カナ)
        detailMdl.setMeiKanaText(rs.getString("ADR_MEI_KN"));

        //所属
        detailMdl.setSyozokuText(rs.getString("ADR_SYOZOKU"));
        //役職SID
        detailMdl.setYakusyokuSid(__getNosetValue(rs.getInt("APS_SID")));
        //役職名
        detailMdl.setYakusyokuName(rs.getString("APS_NAME"));

        //メールアドレス１
        detailMdl.setMail1Text(rs.getString("ADR_MAIL1"));
        //メールアドレスコメント１
        detailMdl.setMail1CommentText(rs.getString("ADR_MAIL_CMT1"));
        //メールアドレス２
        detailMdl.setMail2Text(rs.getString("ADR_MAIL2"));
        //メールアドレスコメント１
        detailMdl.setMail2CommentText(rs.getString("ADR_MAIL_CMT2"));
        //メールアドレス３
        detailMdl.setMail3Text(rs.getString("ADR_MAIL3"));
        //メールアドレスコメント１
        detailMdl.setMail3CommentText(rs.getString("ADR_MAIL_CMT3"));

        //電話番号１
        detailMdl.setTel1Text(rs.getString("ADR_TEL1"));
        //内線１
        detailMdl.setTel1NaisenText(rs.getString("ADR_TEL_NAI1"));
        //電話番号コメント１
        detailMdl.setTel1CommentText(rs.getString("ADR_TEL_CMT1"));
        //電話番号２
        detailMdl.setTel2Text(rs.getString("ADR_TEL2"));
        //内線２
        detailMdl.setTel2NaisenText(rs.getString("ADR_TEL_NAI2"));
        //電話番号コメント２
        detailMdl.setTel2CommentText(rs.getString("ADR_TEL_CMT2"));
        //電話番号３
        detailMdl.setTel3Text(rs.getString("ADR_TEL3"));
        //内線３
        detailMdl.setTel3NaisenText(rs.getString("ADR_TEL_NAI3"));
        //電話番号コメント３
        detailMdl.setTel3CommentText(rs.getString("ADR_TEL_CMT3"));

        //ＦＡＸ１
        detailMdl.setFax1Text(rs.getString("ADR_FAX1"));
        //ＦＡＸコメント１
        detailMdl.setFax1CommentText(rs.getString("ADR_FAX_CMT1"));
        //ＦＡＸ２
        detailMdl.setFax2Text(rs.getString("ADR_FAX2"));
        //ＦＡＸコメント２
        detailMdl.setFax2CommentText(rs.getString("ADR_FAX_CMT2"));
        //ＦＡＸ３
        detailMdl.setFax3Text(rs.getString("ADR_FAX3"));
        //ＦＡＸコメント３
        detailMdl.setFax3CommentText(rs.getString("ADR_FAX_CMT3"));

        //郵便番号上３桁
        detailMdl.setZip1Text(rs.getString("ADR_POSTNO1"));
        //郵便番号下４桁
        detailMdl.setZip2Text(rs.getString("ADR_POSTNO2"));
        //都道府県SID
        detailMdl.setTodofukenSid(__getNosetValue(rs.getInt("TDF_SID")));
        //都道府県
        detailMdl.setTodofukenName(rs.getString("ADDRESS_TDF_NAME"));
        //住所１
        detailMdl.setAddress1Text(rs.getString("ADR_ADDR1"));
        //住所２
        detailMdl.setAddress2Text(rs.getString("ADR_ADDR2"));
        //備考
        detailMdl.setMemoText(rs.getString("ADR_BIKO"));

        //企業コード
        detailMdl.setCompanyId(rs.getString("ACO_CODE"));
        //会社拠点SID
        detailMdl.setBaseSid(rs.getInt("ABA_SID"));

        //企業名
        detailMdl.setCompanyName(rs.getString("ACO_NAME"));
        //会社拠点名
        detailMdl.setBaseName(rs.getString("ABA_NAME"));

        //会社拠点種別
        detailMdl.setBaseType(__getNosetValue(rs.getInt("ABA_TYPE")));

        return detailMdl;
    }

    /**
     * キーワード検索SQLの設定を行う
     * @param sql SqlBuffer
     * @param keywordArray 検索キーワード(複数)
     * @param fieldName 検索対象フィールド名
     */
    private void __addKeywordSql(SqlBuffer sql, String[] keywordArray, String fieldName) {
        if (keywordArray.length > 1) {
            sql.addSql("     (");
        }

        __addKeywordSql(sql, keywordArray[0], fieldName);

        for (int index = 1; index < keywordArray.length; index++) {
            sql.addSql("     and");
            __addKeywordSql(sql, keywordArray[index], fieldName);
        }

        if (keywordArray.length > 1) {
            sql.addSql("     )");
        }
    }

    /**
     * キーワード検索SQLの設定を行う
     * @param sql SqlBuffer
     * @param keyword 検索キーワード
     * @param fieldName 検索対象フィールド名
     */
    private void __addKeywordSql(SqlBuffer sql, String keyword, String fieldName) {
        sql.addSql("       " + fieldName + " like '%"
        + JDBCUtil.escapeForLikeSearch(keyword)
        + "%' ESCAPE '"
        + JDBCUtil.def_esc
        + "'");
    }

    /**
     * 未設定 ( <= 0) の場合、「未設定を示す値(-1)」を返す
     * @param value 値
     * @return 値
     */
    private int __getNosetValue(int value) {
        if (value <= 0) {
            return -1;
        }
        return value;
    }
}
