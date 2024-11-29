package jp.groupsession.v2.sml.sml360;

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
import jp.groupsession.v2.sml.model.MailFilterConditionModel;
import jp.groupsession.v2.sml.model.MailFilterModel;
import jp.groupsession.v2.sml.sml340.Sml340MailModel;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ登録画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml360Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml360Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml360Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] メール情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return メール情報一覧
     * @throws SQLException SQL実行時例外
     */
    public long getMailCount(Sml360ParamModel paramMdl)
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
    public List<Sml340MailModel> getMailList(Sml360ParamModel paramMdl,
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
            if (paramMdl.getSml360mailListOrder() == Sml360Form.SML360_ORDER_DESC) {
                order = "";
            }

            sql.addSql(" order by");
            switch (paramMdl.getSml360mailListSortKey()) {
                case Sml360Form.SML360_SKEY_SUBJECT:
                    sql.addSql("   SML_JMEIS_DETAIL.smsTitle" + order);
                    break;
                case Sml360Form.SML360_SKEY_FROM:
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
                mdl.setUsrSid(rs.getInt("usrSid"));
                mdl.setUsiSei(rs.getString("usiSei"));
                mdl.setUsiMei(rs.getString("usiMei"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
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
        Sml360ParamModel paramMdl) throws SQLException {

        List<MailFilterConditionModel> conditionList = new ArrayList<MailFilterConditionModel>();

        if (String.valueOf(GSConstSmail.CONDITION_ONE).equals(paramMdl.getSml360condition1())) {
            conditionList.add(__createFilterConditionModel(
                            paramMdl.getSml360conditionType1(),
                            paramMdl.getSml360conditionExs1(),
                            paramMdl.getSml360conditionText1()));
        }

        if (String.valueOf(GSConstSmail.CONDITION_TWO).equals(paramMdl.getSml360condition2())) {
            conditionList.add(__createFilterConditionModel(
                            paramMdl.getSml360conditionType2(),
                            paramMdl.getSml360conditionExs2(),
                            paramMdl.getSml360conditionText2()));
        }

        if (String.valueOf(GSConstSmail.CONDITION_THREE).equals(paramMdl.getSml360condition3())) {
            conditionList.add(__createFilterConditionModel(
                            paramMdl.getSml360conditionType3(),
                            paramMdl.getSml360conditionExs3(),
                            paramMdl.getSml360conditionText3()));
        }

        if (String.valueOf(GSConstSmail.CONDITION_FOUR).equals(paramMdl.getSml360condition4())) {
            conditionList.add(__createFilterConditionModel(
                            paramMdl.getSml360conditionType4(),
                            paramMdl.getSml360conditionExs4(),
                            paramMdl.getSml360conditionText4()));
        }

        if (String.valueOf(GSConstSmail.CONDITION_FIVE).equals(paramMdl.getSml360condition5())) {
            conditionList.add(__createFilterConditionModel(
                            paramMdl.getSml360conditionType5(),
                            paramMdl.getSml360conditionExs5(),
                            paramMdl.getSml360conditionText5()));
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
    private MailFilterModel __createFilterMdl(Sml360ParamModel paramMdl) {
        //フィルター情報
        MailFilterModel filterMdl = new MailFilterModel();
        filterMdl.setTempFile(Integer.parseInt(paramMdl.getSml360tempFile())
                                == GSConstSmail.FILTER_TEMPFILE_YES);
        filterMdl.setLabel(Integer.parseInt(paramMdl.getSml360actionLabel())
                                == GSConstSmail.FILTER_LABEL_SETLABEL);
        if (paramMdl.getSml360actionLabel().equals("1")) {
            filterMdl.setLabelSid(Integer.parseInt(paramMdl.getSml360actionLabelValue()));
        }
        filterMdl.setReaded(Integer.parseInt(paramMdl.getSml360actionRead())
                                == GSConstSmail.FILTER_READED_SETREADED);
        filterMdl.setDust(Integer.parseInt(paramMdl.getSml360actionDust())
                                == GSConstSmail.FILTER_DUST_MOVEDUST);
        filterMdl.setCondition(paramMdl.getSml360filterType());
        return filterMdl;
    }

}
