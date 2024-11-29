package jp.groupsession.v2.sml.sml340;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.model.LabelDataModel;
import jp.groupsession.v2.sml.model.MailFilterConditionModel;
import jp.groupsession.v2.sml.model.MailFilterModel;
import jp.groupsession.v2.sml.model.SmlFilterConditionModel;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ登録画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml340Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml340Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml340Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sml200account アカウントSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelDataModel> getLabelList(int sml200account)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelDataModel> ret =
            new ArrayList<LabelDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SLB_SID, ");
            sql.addSql("   SLB_NAME, ");
            sql.addSql("   SLB_ORDER ");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL ");
            sql.addSql(" where ");
            sql.addSql("    (SLB_TYPE = ? ");
            sql.addSql("   or (SLB_TYPE = ? ");
            sql.addSql("   and ");
            sql.addSql("        SAC_SID = ?)) ");
            sql.addSql(" order by ");
            sql.addSql("   SLB_ORDER asc ");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstSmail.LABELTYPE_ALL);
            sql.addIntValue(GSConstSmail.LABELTYPE_ONES);
            sql.addIntValue(sml200account);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LabelDataModel model = new LabelDataModel();
                model.setLabelSid(rs.getInt("SLB_SID"));
                model.setLabelName(rs.getString("SLB_NAME"));
                model.setLabelOrder(rs.getInt("SLB_ORDER"));
                ret.add(model);
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
     * <br>[機  能] ラベル情報を取得する(全てのアカウント)
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelDataModel> getLabelListOnlyAll()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelDataModel> ret =
            new ArrayList<LabelDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SLB_SID, ");
            sql.addSql("   SLB_NAME, ");
            sql.addSql("   SLB_ORDER ");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   SLB_TYPE = ? ");
            sql.addSql(" order by ");
            sql.addSql("   SLB_ORDER asc ");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstSmail.LABELTYPE_ALL);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LabelDataModel model = new LabelDataModel();
                model.setLabelSid(rs.getInt("WLB_SID"));
                model.setLabelName(rs.getString("WLB_NAME"));
                model.setLabelOrder(rs.getInt("WLB_ORDER"));
                ret.add(model);
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
     * <br>[機  能] フィルター条件を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sml200editFilter 編集対象フィルターSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<SmlFilterConditionModel> selectFilCon(int sml200editFilter) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlFilterConditionModel> ret = new ArrayList<SmlFilterConditionModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SFC_NUM,");
            sql.addSql("   SFC_TYPE,");
            sql.addSql("   SFC_EXPRESSION,");
            sql.addSql("   SFC_TEXT");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER_CONDITION");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sml200editFilter);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlFilterConditionModel model = new SmlFilterConditionModel();

                model.setSfcNum(rs.getInt("SFC_NUM"));
                model.setSfcType(rs.getInt("SFC_TYPE"));
                model.setSfcExpression(rs.getInt("SFC_EXPRESSION"));
                model.setSfcText(rs.getString("SFC_TEXT"));
                ret.add(model);
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
     * <br>[機  能] メール情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return メール情報一覧
     * @throws SQLException SQL実行時例外
     */
    public long getMailCount(Sml340ParamModel paramMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long count = 0;
        con = getCon();

        try {
            SmailDao smlDao = new SmailDao(con);
            MailFilterModel filterData = __createFilterMdl(paramMdl);
            List<MailFilterConditionModel> conditionList = __getFilterConditionList(paramMdl);

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   distinct");
            sql.addSql("   count(SML_JMEIS_DETAIL.smjSid) as CNT");
            smlDao.setFromSqlForFiltering(sql, filterData,
                    conditionList, paramMdl.getSmlAccountSid(), 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getLong("CNT");
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
     * <br>[機  能] メール情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param start データ取得開始位置
     * @param maxCount 最大取得件数
     * @return メール情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Sml340MailModel> getMailList(Sml340ParamModel paramMdl,
                                            int start, int maxCount)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Sml340MailModel> mailList = new ArrayList<Sml340MailModel>();
        con = getCon();
        SmailDao smlDao = new SmailDao(con);
        MailFilterModel filterData = __createFilterMdl(paramMdl);
        List<MailFilterConditionModel> conditionList = __getFilterConditionList(paramMdl);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   distinct ");
            sql.addSql("   SML_JMEIS_DETAIL.smjSid as smjSid,");
            sql.addSql("   SML_JMEIS_DETAIL.smjOpkbn as smjOpkbn,");
            sql.addSql("   SML_JMEIS_DETAIL.smjSendkbn as smjSendkbn,");
            sql.addSql("   SML_JMEIS_DETAIL.smjRtnKbn as smjRtnKbn ,");
            sql.addSql("   SML_JMEIS_DETAIL.smjFwKbn as smjFwKbn,");
            sql.addSql("   SML_JMEIS_DETAIL.smsMark as smsMark,");
            sql.addSql("   SML_JMEIS_DETAIL.smsTitle as smsTitle,");
            sql.addSql("   SML_JMEIS_DETAIL.smsBody as smsBody,");
            sql.addSql("   SML_JMEIS_DETAIL.smsBodyPlain as smsBodyPlain,");
            sql.addSql("   SML_JMEIS_DETAIL.smsSdate as smsSdate,");
            sql.addSql("   SML_JMEIS_DETAIL.smsSize as smsSize,");
            sql.addSql("   SML_JMEIS_DETAIL.sacSid as sacSid,");
            sql.addSql("   SML_JMEIS_DETAIL.sacName as sacName,");
            sql.addSql("   SML_JMEIS_DETAIL.sacJkbn as sacJkbn,");
            sql.addSql("   SML_JMEIS_DETAIL.usrSid as usrSid,");
            sql.addSql("   SML_JMEIS_DETAIL.usrJkbn as usrJkbn,");
            sql.addSql("   SML_JMEIS_DETAIL.usiSei as usiSei,");
            sql.addSql("   SML_JMEIS_DETAIL.usiMei as usiMei,");
            sql.addSql("   SML_JMEIS_DETAIL.usiSeiKn as usiSeiKn,");
            sql.addSql("   SML_JMEIS_DETAIL.usiMeiKn as usiMeiKn,");
            sql.addSql("   SML_JMEIS_DETAIL.usrUkoFlg as usrUkoFlg,");
            sql.addSql("   SML_JMEIS_DETAIL.binSid as binSid,");
            sql.addSql("   SML_JMEIS_DETAIL.usiPictKf as usiPictKf,");
            sql.addSql("   SML_JMEIS_DETAIL.binCnt as binCnt");
            smlDao.setFromSqlForFiltering(sql, filterData,
                    conditionList, paramMdl.getSmlAccountSid(), 0);

            String order = " desc";
            if (paramMdl.getSml340mailListOrder() == Sml340Form.SML340_ORDER_DESC) {
                order = "";
            }

            sql.addSql(" order by");
            switch (paramMdl.getSml340mailListSortKey()) {
                case Sml340Form.SML340_SKEY_SUBJECT:
                    sql.addSql("   SML_JMEIS_DETAIL.smsTitle" + order);
                    break;
                case Sml340Form.SML340_SKEY_FROM:
                    sql.addSql("  SML_JMEIS_DETAIL.usiSeiKn");
                    sql.addSql(order);
                    sql.addSql(",");
                    sql.addSql("  SML_JMEIS_DETAIL.usiMeiKn");
                    sql.addSql(order);
                    break;
                default:
                    sql.addSql("   SML_JMEIS_DETAIL.smsSdate" + order);
            }

            log__.info(sql.toLogString());


            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (start > 1) {
                rs.absolute(start - 1);
            }

            for (int i = 0; rs.next() && i < maxCount; i++) {
                Sml340MailModel mdl = new Sml340MailModel();
                mdl.setSmlSid(rs.getInt("smjSid"));
                mdl.setSmjOpkbn(rs.getInt("smjOpkbn"));
                mdl.setSmjSendkbn(rs.getInt("smjSendkbn"));
                mdl.setSmsMark(rs.getInt("smsMark"));
                mdl.setSmsTitle(rs.getString("smsTitle"));
                mdl.setSmsBody(rs.getString("smsBody"));
                mdl.setSmsSdate(UDate.getInstanceTimestamp(rs.getTimestamp("smsSdate")));
                mdl.setSmsSize(rs.getLong("smsSize"));
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(rs.getString("sacName"));
                mdl.setAccountJkbn(rs.getInt("sacJkbn"));
                mdl.setUsrJkbn(rs.getInt("usrJkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setBinCnt(rs.getInt("binCnt"));
                mdl.setBinFileSid(rs.getLong("binSid"));
                mdl.setPhotoFileDsp(rs.getInt("usiPictKf"));
                mdl.setReturnKbn(rs.getInt("smjRtnKbn"));
                mdl.setFwKbn(rs.getInt("smjFwKbn"));
                mdl.setDate(UDate.getInstanceTimestamp(rs.getTimestamp("smsSdate")));
                mailList.add(mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailList;
    }

    /**
    * <br>[機  能] フィルタテスト用のフィルタ条件一覧を作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータ情報
    * @return SmlFilterModel一覧
    * @throws SQLException SQL実行例外
    */
   private List<MailFilterConditionModel> __getFilterConditionList(
       Sml340ParamModel paramMdl) throws SQLException {

       List<MailFilterConditionModel> conditionList = new ArrayList<MailFilterConditionModel>();

       if (String.valueOf(GSConstSmail.CONDITION_ONE).equals(paramMdl.getSml340condition1())) {
           conditionList.add(__createFilterConditionModel(
                           paramMdl.getSml340conditionType1(),
                           paramMdl.getSml340conditionExs1(),
                           paramMdl.getSml340conditionText1()));
       }

       if (String.valueOf(GSConstSmail.CONDITION_TWO).equals(paramMdl.getSml340condition2())) {
           conditionList.add(__createFilterConditionModel(
                           paramMdl.getSml340conditionType2(),
                           paramMdl.getSml340conditionExs2(),
                           paramMdl.getSml340conditionText2()));
       }

       if (String.valueOf(GSConstSmail.CONDITION_THREE).equals(paramMdl.getSml340condition3())) {
           conditionList.add(__createFilterConditionModel(
                           paramMdl.getSml340conditionType3(),
                           paramMdl.getSml340conditionExs3(),
                           paramMdl.getSml340conditionText3()));
       }

       if (String.valueOf(GSConstSmail.CONDITION_FOUR).equals(paramMdl.getSml340condition4())) {
           conditionList.add(__createFilterConditionModel(
                           paramMdl.getSml340conditionType4(),
                           paramMdl.getSml340conditionExs4(),
                           paramMdl.getSml340conditionText4()));
       }

       if (String.valueOf(GSConstSmail.CONDITION_FIVE).equals(paramMdl.getSml340condition5())) {
           conditionList.add(__createFilterConditionModel(
                           paramMdl.getSml340conditionType5(),
                           paramMdl.getSml340conditionExs5(),
                           paramMdl.getSml340conditionText5()));
       }

       return conditionList;
   }
   /**
    * <br>[機  能] フィルター条件Modelを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param type 種別
    * @param exs 条件式
    * @param text 条件
    * @return フィルター条件Model
    */
   private MailFilterConditionModel __createFilterConditionModel(
                                                               String type, String exs,
                                                               String text) {
       MailFilterConditionModel sfcMdl = new MailFilterConditionModel();
       sfcMdl.setType(Integer.parseInt(type));
       sfcMdl.setExpression(Integer.parseInt(exs));
       sfcMdl.setText(text);
       return sfcMdl;
   }
   /**
    * <br>[機  能] フィルタテスト用のフィルタモデルを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータ情報
    * @return フィルタモデル
    */
   private MailFilterModel __createFilterMdl(Sml340ParamModel paramMdl) {
       //フィルター情報
       MailFilterModel filterMdl = new MailFilterModel();
       filterMdl.setTempFile(Integer.parseInt(paramMdl.getSml340tempFile())
                               == GSConstSmail.FILTER_TEMPFILE_YES);
       filterMdl.setLabel(Integer.parseInt(paramMdl.getSml340actionLabel())
                               == GSConstSmail.FILTER_LABEL_SETLABEL);
       if (paramMdl.getSml340actionLabel().equals("1")) {
           filterMdl.setLabelSid(Integer.parseInt(paramMdl.getSml340actionLabelValue()));
       }
       filterMdl.setReaded(Integer.parseInt(paramMdl.getSml340actionRead())
                               == GSConstSmail.FILTER_READED_SETREADED);
       filterMdl.setDust(Integer.parseInt(paramMdl.getSml340actionDust())
                               == GSConstSmail.FILTER_DUST_MOVEDUST);
       filterMdl.setCondition(paramMdl.getSml340filterType());
       return filterMdl;
   }
}
