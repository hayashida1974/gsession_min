package jp.groupsession.v2.convert.convert430.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.convert.convert430.model.CvtWebmailAccountModel;
import jp.groupsession.v2.convert.convert430.model.CvtWebmailFilterModel;
import jp.groupsession.v2.convert.convert430.model.CvtWebmailLabelModel;
import jp.groupsession.v2.convert.convert430.model.CvtWebmailModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvWebmail430Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvWebmail430Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvWebmail430Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] WEBメールに関するデータのコンバートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException コンバートに失敗
     */
    public void convert(MlCountMtController mtCon) throws SQLException {

        //WEBメール フィルターのコンバート
        __convertFilter(mtCon);

        //WEBメール ラベルのコンバート
        __convertLabel(mtCon);

        //WEBメール メール情報のコンバート
        __convertMailSize();
    }

    /**
     * <br>[機  能] WEBメール フィルターのコンバートを行う
     * <br>[解  説] フィルター種別 = 1:全てのアカウントに適用 に設定されているフィルターを
     * <br> フィルター種別 = 0:アカウント固有 へ変更する
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException フィルターのコンバートに失敗
     */
    private void __convertFilter(MlCountMtController mtCon) throws SQLException {
        //「フィルター種別 = 1:全てのアカウントに適用」に該当するフィルターを取得
        List<CvtWebmailFilterModel> filterList = __getFilterList();

        for (CvtWebmailFilterModel filterData : filterList) {
            int beforeWftSid = filterData.getWftSid();
            List<Integer> wacList = __getUsersAccountList(filterData.getUsrSid());
            for (int wacSid : wacList) {
                //フィルター情報の登録
                int newWftSid = __insertFilter(wacSid, mtCon, beforeWftSid);

                //フィルター条件の登録
                __insertFilterCondition(newWftSid, beforeWftSid);

                //フィルター適用順の登録
                __entryFilterSort(newWftSid, wacSid, beforeWftSid);
            }

            //旧フィルターの削除
            __deleteFilter(beforeWftSid);
        }

    }

    /**
     * <br>[機  能] フィルター情報一覧を取得する
     * <br>[解  説] 「フィルター種別 = 1:全てのアカウントに適用」のフィルターを対象とする
     * <br>[備  考]
     * @return フィルター情報一覧
     * @throws SQLException SQL実行時例外
     */
    private List<CvtWebmailFilterModel> __getFilterList() throws SQLException {
        List<CvtWebmailFilterModel> filterList = new ArrayList<CvtWebmailFilterModel>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER");
            sql.addSql(" where");
            sql.addSql("   WFT_TYPE=?");
            sql.addIntValue(GSConstWebmail.WFT_TYPE_ALL);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CvtWebmailFilterModel filterData = new CvtWebmailFilterModel();
                filterData.setWftSid(rs.getInt("WFT_SID"));
                filterData.setUsrSid(rs.getInt("USR_SID"));
                filterData.setWacSid(rs.getInt("WAC_SID"));

                filterList.add(filterData);
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return filterList;
    }

    /**
     * <br>[機  能] 指定したユーザが使用可能なアカウントの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アカウントSID一覧一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getUsersAccountList(int userSid) throws SQLException {
        List<Integer> wacList = new ArrayList<Integer>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         WML_ACCOUNT.USR_SID = ? ");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         exists ( ");
            sql.addSql("           select WAC_SID from WML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("           GRP_SID in ( ");
            sql.addSql("             select GRP_SID from CMN_BELONGM ");
            sql.addSql("             where USR_SID = ? ");
            sql.addSql("           )");
            sql.addSql("         and ");
            sql.addSql("           WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("         )");
            sql.addSql("      )");
            sql.addSql("      or ");
            sql.addSql("      (");
            sql.addSql("         WML_ACCOUNT.WAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         exists ( ");
            sql.addSql("           select WAC_SID from WML_ACCOUNT_USER ");
            sql.addSql("           where");
            sql.addSql("             USR_SID = ? ");
            sql.addSql("           and ");
            sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID ");
            sql.addSql("         )");
            sql.addSql("      )");
            sql.addSql("   )");

            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_NORMAL);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_GROUP);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstWebmail.WAC_TYPE_USER);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                wacList.add(rs.getInt("WAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return wacList;
    }

    /**
     * <br>[機  能] フィルター情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mtCon 採番コントローラ
     * @param beforeWftSid 更新前のフィルターSID
     * @return 採番したフィルターSID
     * @throws SQLException SQL実行時例外
     */
    private int __insertFilter(int wacSid, MlCountMtController mtCon, int beforeWftSid)
    throws SQLException {
        int newWftSid = 0;

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_FILTER ");
            sql.addSql(" select");
            sql.addSql("   ?,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WFT_NAME,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   WFT_TEMPFILE,");
            sql.addSql("   WFT_ACTION_LABEL,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WFT_ACTION_READ,");
            sql.addSql("   WFT_ACTION_DUST,");
            sql.addSql("   WFT_ACTION_FORWARD,");
            sql.addSql("   WFT_ACTION_FWADDRESS,");
            sql.addSql("   WFT_CONDITION");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER ");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID = ? ");

            newWftSid = (int) mtCon.getSaibanNumber(
                            GSConstWebmail.SBNSID_WEBMAIL,
                            GSConstWebmail.SBNSID_SUB_FILTER,
                            0);
            sql.addIntValue(newWftSid);
            sql.addIntValue(GSConstWebmail.WFT_TYPE_ONE);
            sql.addIntValue(wacSid);
            sql.addIntValue(beforeWftSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return newWftSid;
    }

    /**
     * <br>[機  能] フィルター条件の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param newWftSid フィルターSID
     * @param beforeWftSid 更新前のフィルターSID
     * @throws SQLException SQL実行時例外
     */
    private void __insertFilterCondition(int newWftSid,  int beforeWftSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_FILTER_CONDITION ");
            sql.addSql(" select");
            sql.addSql("   ?,");
            sql.addSql("   WFC_NUM,");
            sql.addSql("   WFC_TYPE,");
            sql.addSql("   WFC_EXPRESSION,");
            sql.addSql("   WFC_TEXT");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_CONDITION ");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID = ? ");

            sql.addIntValue(newWftSid);
            sql.addIntValue(beforeWftSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] フィルター適用順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param newWftSid フィルターSID
     * @param wacSid アカウントSID
     * @param beforeWftSid 更新前のフィルターSID
     * @throws SQLException SQL実行時例外
     */
    private void __entryFilterSort(int newWftSid,  int wacSid, int beforeWftSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //既存のフィルター並び順を更新する
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_FILTER_SORT ");
            sql.addSql(" set");
            sql.addSql("   WFT_SID = ?");
            sql.addSql(" where ");
            sql.addSql("   WFT_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   WAC_SID = ? ");

            sql.addIntValue(newWftSid);
            sql.addIntValue(beforeWftSid);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            int updateCount = pstmt.executeUpdate();

            //更新対象が存在しない場合、新規登録を行う
            if (updateCount <= 0) {
                pstmt.close();

                sql = new SqlBuffer();
                sql.addSql(" insert into WML_FILTER_SORT (");
                sql.addSql("   WAC_SID,");
                sql.addSql("   WFT_SID,");
                sql.addSql("   WFS_SORT");
                sql.addSql(" )");
                sql.addSql(" select");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   max(WFS_SORT) + 1");
                sql.addSql(" from ");
                sql.addSql("   WML_FILTER_SORT");
                sql.addSql(" where ");
                sql.addSql("   WAC_SID = ? ");
                sql.addSql(" group by ");
                sql.addSql("   WAC_SID");

                sql.addIntValue(wacSid);
                sql.addIntValue(newWftSid);
                sql.addIntValue(wacSid);

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 旧フィルターの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param beforeWftSid 更新前のフィルターSID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteFilter(int beforeWftSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //フィルターを削除する
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from WML_FILTER");
            sql.addSql(" where  WFT_SID = ? ");

            sql.addIntValue(beforeWftSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            //フィルター条件を削除する
            pstmt.close();
            sql = new SqlBuffer();
            sql.addSql(" delete from WML_FILTER_CONDITION ");
            sql.addSql(" where  WFT_SID = ? ");

            sql.addIntValue(beforeWftSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] WEBメール ラベルのコンバートを行う
     * <br>[解  説] ラベル区分 = 1:全てのアカウント に設定されているフィルターを
     * <br> ラベル区分 = 0:アカウント固有 へ変更する
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException フィルターのコンバートに失敗
     */
    private void __convertLabel(MlCountMtController mtCon) throws SQLException {
        //「ラベル区分 = 1:全てのアカウント」に該当するラベルを取得
        List<CvtWebmailLabelModel> labelList = __getLabelList();

        for (CvtWebmailLabelModel labelData : labelList) {
            int beforeWlbSid = labelData.getWlbSid();
            List<Integer> wacList = __getUsersAccountList(labelData.getUsrSid());
            for (int wacSid : wacList) {

                //ラベル情報の登録
                int newWlbSid = __insertLabel(wacSid, mtCon, labelData);

                //メール情報表示順
                __updateLabelSid("WML_MAILDATA_SORT", newWlbSid,  wacSid, beforeWlbSid);

                //メール - ラベル
                __updateLabelSid("WML_LABEL_RELATION", newWlbSid,  wacSid, beforeWlbSid);

                //フィルター
                __updateLabelSid("WML_FILTER", newWlbSid,  wacSid, beforeWlbSid);
            }

            //旧ラベルの削除
            __deleteLabel(beforeWlbSid);
        }

        //ラベルの並び順を再設定する
        __updateLabelOrder();
    }

    /**
     * <br>[機  能] ラベル情報一覧を取得する
     * <br>[解  説] 「フィルター種別 = 1:全てのアカウントに適用」のフィルターを対象とする
     * <br>[備  考]
     * @return フィルター情報一覧
     * @throws SQLException SQL実行時例外
     */
    private List<CvtWebmailLabelModel> __getLabelList() throws SQLException {
        List<CvtWebmailLabelModel> labelList = new ArrayList<CvtWebmailLabelModel>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WLB_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   WLB_NAME,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WLB_ORDER");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where");
            sql.addSql("   WLB_TYPE=?");
            sql.addIntValue(GSConstWebmail.LABELTYPE_ALL);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CvtWebmailLabelModel labelData = new CvtWebmailLabelModel();
                labelData.setWlbSid(rs.getInt("WLB_SID"));
                labelData.setUsrSid(rs.getInt("USR_SID"));
                labelData.setWlbName(rs.getString("WLB_NAME"));
                labelData.setWacSid(rs.getInt("WAC_SID"));
                labelData.setWlbOrder(rs.getInt("WLB_ORDER"));

                labelList.add(labelData);
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return labelList;
    }

    /**
     * <br>[機  能] ラベル情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param mtCon 採番コントローラ
     * @param beforeLabelData 更新前のラベル情報
     * @return 採番したラベルSID
     * @throws SQLException SQL実行時例外
     */
    private int __insertLabel(int wacSid, MlCountMtController mtCon,
                                        CvtWebmailLabelModel beforeLabelData)
    throws SQLException {
        int newWftSid = 0;

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into WML_LABEL values (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" );");

            newWftSid = (int) mtCon.getSaibanNumber(
                            GSConstWebmail.SBNSID_WEBMAIL,
                            GSConstWebmail.SBNSID_SUB_LABEL,
                            0);
            sql.addIntValue(newWftSid);
            sql.addIntValue(beforeLabelData.getUsrSid());
            sql.addStrValue(beforeLabelData.getWlbName());
            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);
            sql.addIntValue(wacSid);
            sql.addIntValue(beforeLabelData.getWlbOrder());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return newWftSid;
    }

    /**
     * <br>[機  能] 指定したテーブルのラベルSIDを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName 更新対象のテーブル
     * @param newWlbSid ラベルSID
     * @param wacSid アカウントSID
     * @param beforeWlbSid 更新前のラベルSID
     * @throws SQLException SQL実行時例外
     */
    private void __updateLabelSid(String tableName, int newWlbSid,  int wacSid, int beforeWlbSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update " + tableName);
            sql.addSql(" set");
            sql.addSql("   WLB_SID = ?");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   WAC_SID = ? ");

            sql.addIntValue(newWlbSid);
            sql.addIntValue(beforeWlbSid);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 旧ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param beforeWlbSid 更新前のラベルSID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteLabel(int beforeWlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //ラベルを削除する
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from WML_LABEL");
            sql.addSql(" where  WLB_SID = ? ");

            sql.addIntValue(beforeWlbSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] アカウントSIDとラベルSID一覧のMappingを取得する
     * <br>[解  説] ラベル区分 = 0:アカウント固有 のラベルのみを対象とする
     * <br>[備  考] ラベルSIDの一覧は[アカウントごとの並び順]に並び替えを行う
     * @return アカウントSIDとラベルSID一覧のMapping
     * @throws SQLException SQL実行時例外
     */
    private Map<Integer, List<Integer>> __getLabelMap() throws SQLException {
        Map<Integer, List<Integer>> labelMap = new HashMap<Integer, List<Integer>>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WLB_SID");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where");
            sql.addSql("   WLB_TYPE = ?");
            sql.addSql(" order by");
            sql.addSql("   WLB_ORDER asc");
            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int wacSid = rs.getInt("WAC_SID");
                if (!labelMap.containsKey(wacSid)) {
                    labelMap.put(wacSid, new ArrayList<Integer>());
                }

                labelMap.get(wacSid).add(rs.getInt("WLB_SID"));
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return labelMap;
    }

    /**
     * <br>[機  能] ラベルの並び順を再設定する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __updateLabelOrder() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //ラベルを削除する
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_LABEL");
            sql.addSql(" set WLB_ORDER = ?");
            sql.addSql(" where WLB_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            Map<Integer, List<Integer>> labelMap = __getLabelMap();
            Iterator<Integer> wacIter = labelMap.keySet().iterator();
            while (wacIter.hasNext()) {
                int order = 1;
                int wacSid = wacIter.next();
                List<Integer> wlbSidList = labelMap.get(wacSid);
                for (int wlbSid : wlbSidList) {
                    sql.addIntValue(order++);
                    sql.addIntValue(wlbSid);

                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();

                    sql.clearValue();
                    pstmt.clearParameters();
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] WEBメール メール情報(送信済み or 草稿)のメールサイズを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException メール情報 メールサイズの更新に失敗
     */
    private void __convertMailSize() throws SQLException {
        //アカウント一覧を取得する
        List<CvtWebmailAccountModel> accountList = __getAccountList();

        MailBiz mailBiz = new MailBiz();
        CvtWebmailModel mailData = null;

        int updateCnt = 0;
        //アカウントカウンター
        int countAccount = 1;
        for (CvtWebmailAccountModel accountData : accountList) {

            int wacSid = accountData.getWacSid();
            int mailCount = __getMailCount(wacSid);

            String wacName = accountData.getWacName();
            String wacEncodeSend = accountData.getWacEncodeSend();

            if (mailCount == 0) {
                log__.info(
                        countAccount + "/" + accountList.size() + "人 (" + wacName + ") : メール件数0件");
                countAccount++;
                continue;
            } else {
                log__.info(countAccount + "/" + accountList.size()
                        + "人 (" + wacName + ") : メール件数" + mailCount + "件");
            }

            log__.info(countAccount + "/" + accountList.size() + "人 (" + wacName + ") : 更新開始");

            int offset = 0;
            List<CvtWebmailModel> mailList = __getSendMailList(wacSid);
            while (!mailList.isEmpty()) {
                for (int idx = 0; idx < mailList.size(); idx++) {
                    mailData = mailList.get(idx);
                    long size = __getSendMailSize(mailData, wacEncodeSend, mailBiz);
                    __updateMailSize(mailData.getMailNum(), size);

                    if ((idx + 1) % 50 == 0) {
                        log__.info(wacName + " : 更新中 : " + (offset + idx + 1) + " / " + mailCount);
                    }
                }

                mailList = __getSendMailList(wacSid);
                offset += 100;
                if (offset > mailCount) {
                    offset = mailCount - 1;
                }
            }

            log__.info(countAccount + "/" + accountList.size() + "人 (" + wacName + ") : 更新完了");
            countAccount++;
        }

        updateCnt++;
        if (updateCnt % 100 == 0) {
            getCon().commit();
        }
    }

    /**
     * <br>[機  能] アカウント情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウント情報一覧
     * @throws SQLException SQL実行時例外
     */
    private List<CvtWebmailAccountModel> __getAccountList() throws SQLException {
        List<CvtWebmailAccountModel> accountList = new ArrayList<CvtWebmailAccountModel>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WAC_NAME,");
            sql.addSql("   WAC_ENCODE_SEND");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where");
            sql.addSql("   WAC_JKBN=?");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_NORMAL);
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CvtWebmailAccountModel accountData = new CvtWebmailAccountModel();
                accountData.setWacSid(rs.getInt("WAC_SID"));
                accountData.setWacName(rs.getString("WAC_NAME"));

                int encodeSend = rs.getInt("WAC_ENCODE_SEND");
                if (encodeSend == GSConstWebmail.WAC_ENCODE_SEND_UTF8) {
                    accountData.setWacEncodeSend(Encoding.UTF_8);
                } else {
                    accountData.setWacEncodeSend(Encoding.ISO_2022_JP);
                }

                accountList.add(accountData);
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return accountList;
    }

    /**
     * <br>[機  能] 更新対象メールの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 更新対象メールの件数
     * @throws SQLException SQL実行時例外
     */
    private int __getMailCount(int wacSid) throws SQLException {
        int count = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(WMD_MAILNUM) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            __setMailDataSql(sql, wacSid);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 更新対象メールの情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 更新対象メールの情報
     * @throws SQLException SQL実行時例外
     */
    private List<CvtWebmailModel> __getSendMailList(int wacSid)
    throws SQLException {
        List<CvtWebmailModel> mailList = new ArrayList<CvtWebmailModel>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM as WMD_MAILNUM,");
            sql.addSql("   WML_MAILDATA.WMD_TITLE as WMD_TITLE,");
            sql.addSql("   WML_MAILDATA.WMD_FROM as WMD_FROM,");
            sql.addSql("   WML_MAILDATA.WMD_TEMPFLG as WMD_TEMPFLG,");
            sql.addSql("   WML_MAIL_BODY.WMB_BODY as WMB_BODY,");
            sql.addSql("   WML_MAIL_BODY.WMB_CHARSET as WMB_CHARSET");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_MAIL_BODY");
            __setMailDataSql(sql, wacSid);

            sql.addSql(" and");
            sql.addSql("  WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");
            sql.addSql(" limit 100 offset 0");

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CvtWebmailModel mailData = new CvtWebmailModel();
                mailData.setMailNum(rs.getLong("WMD_MAILNUM"));
                mailData.setSubject(rs.getString("WMD_TITLE"));
                mailData.setBody(rs.getString("WMB_BODY"));
                mailData.setTempFlg(rs.getInt("WMD_TEMPFLG") == GSConstWebmail.TEMPFLG_EXIST);

                mailData.setFrom(rs.getString("WMD_FROM"));
                mailData.setCharset(rs.getString("WMB_CHARSET"));

                //送信先
                mailData.setSendAddress(__getSendAddress(mailData.getMailNum()));

                mailList.add(mailData);
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return mailList;
    }

    /**
     * <br>[機  能] 指定したメールの送信先を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @return 送信先
     * @throws SQLException SQL実行時例外
     */
    private String __getSendAddress(long mailNum) throws SQLException {
        String address = "";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WSA_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   WML_SENDADDRESS");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailNum);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (address.length() > 0) {
                    address += ",";
                }
                address += rs.getString("WSA_ADDRESS");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return address;
    }

    /**
     * <br>[機  能] 指定したメールの添付ファイルサイズを取得する
     * <br>[解  説]
     * <br>[備  考] base64変換した状態でのサイズを返す
     * @param mailNum メッセージ番号
     * @param encode 文字コード
     * @param mailBiz MailBiz
     * @return 添付ファイルサイズ
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException 文字コードが不正
     */
    private long __getTempFileSize(long mailNum, String encode, MailBiz mailBiz)
    throws SQLException, UnsupportedEncodingException {
        long fileSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WTF_FILE_NAME,");
            sql.addSql("   WTF_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_TEMPFILE");
            sql.addSql(" where");
            sql.addSql("   WMD_MAILNUM = ?");
            sql.addLongValue(mailNum);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                fileSize += 120;
                fileSize += mailBiz.mimeEncode(rs.getString("WTF_FILE_NAME"), encode).length();
                fileSize += mailBiz.getBinaryBase64Size(rs.getLong("WTF_FILE_SIZE"));
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <br>[機  能] メールサイズの更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNum メッセージ番号
     * @param size メールサイズ
     * @throws SQLException SQL実行時例外
     */
    private void __updateMailSize(long mailNum, long size) throws SQLException {

        PreparedStatement pstmt = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_MAILDATA");
            sql.addSql(" set WMD_SIZE = ?");
            sql.addSql(" where WMD_MAILNUM = ?");
            sql.addLongValue(size);
            sql.addLongValue(mailNum);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] メール情報の検索条件を設定する
     * <br>[解  説]
     * <br>[備  考] メールサイズ = 0 のメール情報を対象とする
     * @param sql SqlBuffer
     * @param wacSid アカウントSID
     */
    private void __setMailDataSql(SqlBuffer sql, int wacSid) {
        sql.addSql(" where");
        sql.addSql("   WML_MAILDATA.WAC_SID = ?");
        sql.addSql(" and");
        sql.addSql("   WML_MAILDATA.WMD_SIZE = 0");
        sql.addIntValue(wacSid);
    }

    /**
     * <br>[機  能] 送信メール情報のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailData メール情報
     * @param wacEncodeSend アカウント 送信文字コード
     * @param mailBiz MailBiz
     * @return 送信メール情報のサイズ
     * @throws SQLException SQL実行時例外
     */
    private long __getSendMailSize(CvtWebmailModel mailData, String wacEncodeSend, MailBiz mailBiz)
    throws SQLException {

        String sendEncode = mailData.getCharset();
        if (sendEncode == null) {
            sendEncode = wacEncodeSend;
        }
        int encLen = sendEncode.length();

        long mailSize = 0;

        try {
            mailSize += 45; //Date
            mailSize += 20; //MIME-Version
            mailSize += 30; //X-Mailer

            //Content-Type
            mailSize += 29 + encLen;

            //Content-Transfer-Encoding
            mailSize += 45;

            //送信先
            mailSize += mailBiz.getAddressSize(mailData.getSendAddress(), sendEncode);

            //Subject
            mailSize += 10 + NullDefault.getString(
                                            mailBiz.mimeEncode(mailData.getSubject(), sendEncode)
                                            , "").length();

            //本文
            mailSize += mailBiz.getBase64Size(mailData.getBody(), sendEncode);

            //添付ファイル
            if (mailData.isTempFlg()) {
                mailSize += __getTempFileSize(mailData.getMailNum(),
                                                        mailData.getCharset(),
                                                        mailBiz);
            }
        } catch (Throwable e) {
            log__.warn("メールサイズ計算: " + mailData.getMailNum());
        }

        return mailSize;
    }
}