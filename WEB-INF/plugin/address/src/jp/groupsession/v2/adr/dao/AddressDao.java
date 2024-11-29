package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳プラグイン全般で使用される共通DAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AddressDao.class);

    /**
     * <p>Default Constructor
     */
    public AddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AddressDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ユーザの一覧を取得する
     * <br>[解  説] 削除されたユーザ、予約済みユーザは除く
     * <br>[備  考]
     * @param sortMdl ソート情報
     * @return ユーザ一覧
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getAllUserList(CmnCmbsortConfModel sortMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID > ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");

            sql = __setOrderSQL(sql, sortMdl);

            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("USI_SEI")
                                    + " " + rs.getString("USI_MEI");
                LabelValueBean label = new LabelValueBean(userName,
                                                        String.valueOf(rs.getInt("USR_SID")));
                ret.add(label);
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
     * <br>[機  能] 指定したグループに所属するユーザの一覧を取得する
     * <br>[解  説] 削除されたユーザ、予約済みユーザは除く
     * <br>[備  考]
     * @param grpSid グループSID
     * @param sortMdl ソート情報
     * @return ユーザ一覧
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getUserListBelongGroup(int grpSid, CmnCmbsortConfModel sortMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID > ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");

            sql = __setOrderSQL(sql, sortMdl);

            sql.addIntValue(grpSid);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("USI_SEI")
                                    + " " + rs.getString("USI_MEI");
                LabelValueBean label = new LabelValueBean(userName,
                                                        String.valueOf(rs.getInt("USR_SID")));
                ret.add(label);
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
     * <br>[機  能] 指定したアドレス帳に関するバイナリー情報を論理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteBinData(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       ADR_CONTACT_BIN.BIN_SID as BIN_SID");
            sql.addSql("     from");
            sql.addSql("       ADR_CONTACT,");
            sql.addSql("       ADR_CONTACT_BIN");
            sql.addSql("     where");
            sql.addSql("       ADR_CONTACT.ADC_SID = ADR_CONTACT_BIN.ADC_SID");
            sql.addSql("     and");
            sql.addSql("       ADR_CONTACT.ADR_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したアドレス帳に関するコンタクト履歴添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteContactBinToAddress(int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID in (");
            sql.addSql("     select ADC_SID from ADR_CONTACT");
            sql.addSql("     where ADR_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したアドレス帳が閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @param userSid ユーザSID
     * @return 判定結果 true:閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isViewAddressData(int adrSid, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where");
            sql.addSql("   ADR_SID = ?");
            sql.addIntValue(adrSid);
            sql.addSql(" and");
            AddressDao.addViewableWhereSQL(sql, userSid);



            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
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
     * <br>[機  能] 指定したアドレス帳が編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSid アドレス帳SID
     * @param userSid ユーザSID
     * @return 判定結果 true:編集可能 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditAddressData(int adrSid, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ADR_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" where");
            sql.addSql("   ADR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     ADR_PERMIT_EDIT = ?");
            sql.addSql("   or");

            sql.addSql("     (");
            sql.addSql("       ADR_PERMIT_EDIT = ?");

            //担当者の誰かであること
            sql.addSql("     and");
            sql.addSql("       ? in (");
            sql.addSql("         select USR_SID from ADR_PERSONCHARGE");
            sql.addSql("         where ADR_SID = ?");
            sql.addSql("       )");
            sql.addSql("     )");

            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       ADR_PERMIT_EDIT = ?");
            sql.addSql("     and");
            sql.addSql("       ADR_SID in (");
            sql.addSql("         select ADR_SID from ADR_PERMIT_EDIT");
            sql.addSql("         where");
            sql.addSql("           GRP_SID in (");
            sql.addSql("             select GRP_SID from CMN_BELONGM");
            sql.addSql("             where USR_SID = ?");
            sql.addSql("           )");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       ADR_PERMIT_EDIT = ?");
            sql.addSql("     and");
            sql.addSql("       ADR_SID in (");
            sql.addSql("         select ADR_SID from ADR_PERMIT_EDIT");
            sql.addSql("         where USR_SID = ?");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addIntValue(adrSid);
            sql.addIntValue(GSConst.ADR_VIEWPERMIT_NORESTRICTION);
            sql.addIntValue(GSConst.ADR_VIEWPERMIT_OWN);
            sql.addIntValue(userSid);
            sql.addIntValue(adrSid);

            sql.addIntValue(GSConst.ADR_VIEWPERMIT_GROUP);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConst.ADR_VIEWPERMIT_USER);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
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
     * <br>[機  能] SqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortMdl ソート情報
     * @return sqlBuffer
     */
    private SqlBuffer __setOrderSQL(SqlBuffer sql, CmnCmbsortConfModel sortMdl) {

        sql.addSql(" order by ");

        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   USI_BDATE " + order);
                break;

            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   USI_SORTKEY1 " + order);
                break;

            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   USI_SORTKEY2 " + order);
                break;

            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   USI_SEI_KN asc,");
                sql.addSql("   USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }

        return sql;
    }

    /**
     * <br>[機  能] プロジェクト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSids PRJ_SIDリスト
     * @return Projectリスト
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getProjectData(String[] prjSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRJ_NAME");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID in (");

            if (prjSids != null && prjSids.length > 0) {
                for (int i = 0; i < prjSids.length; i++) {
                    sql.addSql("     ? ");
                    sql.addIntValue(NullDefault.getInt(prjSids[i], 0));

                    if (i < prjSids.length - 1) {
                        sql.addSql("     , ");
                    }
                }
            }

            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   PRJ_DATE_START DESC ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjdataFromRs(rs));
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
     * <p>Create PRJ_PRJDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    private LabelValueBean __getPrjPrjdataFromRs(ResultSet rs) throws SQLException {
        LabelValueBean bean = new LabelValueBean(rs.getString("PRJ_NAME"), rs.getString("PRJ_SID"));
        return bean;
    }

    /**
     *
     * <br>[機  能] SQLに閲覧権限制限用Where句を追加
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 追記先SQLBuffer
     * @param sessionUserSid ユーザSID
     */
     public static void addViewableWhereSQL(SqlBuffer sql, int sessionUserSid) {

         sql.addSql("   (");
         sql.addSql("     ADR_PERMIT_VIEW = ?");
         sql.addSql("   or");
         sql.addSql("     (");
         sql.addSql("       ADR_PERMIT_VIEW = ?");
         sql.addSql("     and");
         sql.addSql("       exists (");
         sql.addSql("         select ADR_SID from ADR_PERSONCHARGE");
         sql.addSql("         where ADR_PERSONCHARGE.USR_SID = ?");
         sql.addSql("         and ADR_PERSONCHARGE.ADR_SID = ADR_ADDRESS.ADR_SID");
         sql.addSql("       )");
         sql.addSql("     )");
         sql.addSql("   or");
         sql.addSql("     (");
         sql.addSql("       ADR_ADDRESS.ADR_PERMIT_VIEW = ?");
         sql.addSql("     and");
         sql.addSql("       exists (");
         sql.addSql("         select ADR_SID from ADR_PERMIT_VIEW");
         sql.addSql("         where");
         sql.addSql("           ADR_PERMIT_VIEW.GRP_SID in (");
         sql.addSql("             select GRP_SID from CMN_BELONGM");
         sql.addSql("             where CMN_BELONGM.USR_SID = ?");
         sql.addSql("           )");
         sql.addSql("         and ADR_PERMIT_VIEW.ADR_SID = ADR_ADDRESS.ADR_SID");
         sql.addSql("       )");
         sql.addSql("     )");
         sql.addSql("   or");
         sql.addSql("     (");
         sql.addSql("       ADR_ADDRESS.ADR_PERMIT_VIEW = ?");
         sql.addSql("     and");
         sql.addSql("       exists (");
         sql.addSql("         select ADR_SID from ADR_PERMIT_VIEW");
         sql.addSql("         where ADR_PERMIT_VIEW.USR_SID = ?");
         sql.addSql("         and ADR_PERMIT_VIEW.ADR_SID = ADR_ADDRESS.ADR_SID");
         sql.addSql("       )");
         sql.addSql("     )");

         sql.addSql("   or");
         sql.addSql("     (");
         sql.addSql("       ADR_PERMIT_EDIT = ?");
         sql.addSql("     and");
         sql.addSql("       exists (");
         sql.addSql("         select ADR_SID from ADR_PERSONCHARGE");
         sql.addSql("         where ADR_PERSONCHARGE.USR_SID = ?");
         sql.addSql("         and ADR_PERSONCHARGE.ADR_SID = ADR_ADDRESS.ADR_SID");
         sql.addSql("       )");
         sql.addSql("     )");
         sql.addSql("   or");
         sql.addSql("     (");
         sql.addSql("       ADR_ADDRESS.ADR_PERMIT_EDIT = ?");
         sql.addSql("     and");
         sql.addSql("       exists (");
         sql.addSql("         select ADR_SID from ADR_PERMIT_EDIT");
         sql.addSql("         where");
         sql.addSql("           ADR_PERMIT_EDIT.GRP_SID in (");
         sql.addSql("             select GRP_SID from CMN_BELONGM");
         sql.addSql("             where CMN_BELONGM.USR_SID = ?");
         sql.addSql("           )");
         sql.addSql("         and ADR_PERMIT_EDIT.ADR_SID = ADR_ADDRESS.ADR_SID");
         sql.addSql("       )");
         sql.addSql("     )");
         sql.addSql("   or");
         sql.addSql("     (");
         sql.addSql("       ADR_ADDRESS.ADR_PERMIT_EDIT = ?");
         sql.addSql("     and");
         sql.addSql("       exists (");
         sql.addSql("         select ADR_SID from ADR_PERMIT_EDIT");
         sql.addSql("         where ADR_PERMIT_EDIT.USR_SID = ?");
         sql.addSql("         and ADR_PERMIT_EDIT.ADR_SID = ADR_ADDRESS.ADR_SID");
         sql.addSql("       )");
         sql.addSql("     )");

         sql.addSql("   )");


        sql.addIntValue(GSConst.ADR_VIEWPERMIT_NORESTRICTION);
        sql.addIntValue(GSConst.ADR_VIEWPERMIT_OWN);
        sql.addIntValue(sessionUserSid);
        sql.addIntValue(GSConst.ADR_VIEWPERMIT_GROUP);
        sql.addIntValue(sessionUserSid);
        sql.addIntValue(GSConst.ADR_VIEWPERMIT_USER);
        sql.addIntValue(sessionUserSid);

        sql.addIntValue(GSConst.ADR_VIEWPERMIT_OWN);
        sql.addIntValue(sessionUserSid);
        sql.addIntValue(GSConst.ADR_VIEWPERMIT_GROUP);
        sql.addIntValue(sessionUserSid);
        sql.addIntValue(GSConst.ADR_VIEWPERMIT_USER);
        sql.addIntValue(sessionUserSid);


    }
}
