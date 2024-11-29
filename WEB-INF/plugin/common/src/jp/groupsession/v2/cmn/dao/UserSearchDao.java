package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.BelongUserSearchModel;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.cmn.model.UserCsvModel;
import jp.groupsession.v2.cmn.model.UserQueryModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.man.man330.model.Man330ExpModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.EnumSeibetu;
import jp.groupsession.v2.usr.usr030.UsrCsvRecordListenerImpl;
import jp.groupsession.v2.usr.usr040.ShainSearchModel;
import jp.groupsession.v2.usr.usr040.UsrCsvRecordListenerIppanImpl;

/**
 * <br>[機  能] ユーザ検索に使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserSearchDao  extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UserSearchDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public UserSearchDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public UserSearchDao(Connection con) {
        super(con);
    }



    /**
     * <p>ユーザ情報で使用する、五十音にヒットしたユーザ情報のSQLを取得する。
     * @param kana 検索対象の五十音カナ
     * @param sqlType true:select, false:count
     * @param sortKey 第一ソート項目
     * @param orderKey 第一ソートオーダー
     * @param sortKey2 第二ソート項目
     * @param orderKey2 第二ソートオーダー
     * @param label 検索ラベル
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getUserKanaIndexSql(String kana, boolean sqlType,
            int sortKey, int orderKey,
            int sortKey2, int orderKey2,
            String[] label) throws SQLException {

        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (sqlType) {
            //SELECT
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2,");
            //            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE,");
            //            sql.addSql("   case");
            //            sql.addSql("   when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
            //            sql.addSql("   else 0");
            //            sql.addSql("   end YAKUSYOKU_LEN");
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

        } else {
            //COUNT
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   CMN_USRM,");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" where");
        if (label != null && label.length != 0) {
            sql.addSql("   exists (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM_LABEL");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_LABEL.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and");
            if (label.length >= 2) {
                sql.addSql("     LAB_SID in ( ?");
                sql.addIntValue(Integer.parseInt(label[0]));
                for (int i = 1; i < label.length; i++) {
                    sql.addSql(",?");
                    sql.addIntValue(Integer.parseInt(label[i]));
                }
                sql.addSql("       )");
            } else {
                sql.addSql("       LAB_SID=? ");
                sql.addIntValue(Integer.parseInt(label[0]));
            }
            sql.addSql("       )");
            sql.addSql(" and");
        }

        sql.addSql("   CMN_USRM_INF.USI_SINI=?");
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_JKBN<>?");
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
        //ユーザSID < 100は除外
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID>?");
        if (sqlType) {
            //第一キーの設定
            String orderStr = "";
            //オーダー
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else {
                orderStr = "  desc";
            }

            sql.addSql(" order by");
            log__.debug("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr);
                break;
                //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr);
                break;
                //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("   YAKUSYOKU_EXIST");
                sql.addSql(orderStr);
                sql.addSql("  ,");
                sql.addSql("   YAKUSYOKU_SORT");
                sql.addSql(orderStr);
                break;
                //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr);
                break;
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr);
                break;
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr);
                break;
            default:
                break;
            }

            sql.addSql("   ,");

            //第二キーの設定
            String orderStr2 = "";
            //オーダー
            if (orderKey2 == GSConst.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else {
                orderStr2 = "  desc";
            }
            log__.debug("sortkey2 = " + sortKey2);

            //ソートカラム
            switch (sortKey2) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr2);
                break;
                //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                //                    sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO");
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("   YAKUSYOKU_EXIST");
                sql.addSql(orderStr2);
                sql.addSql("  ,");
                sql.addSql("   YAKUSYOKU_SORT");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //ソートキー1
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //ソートキー2
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
            default:
                break;
            }
        }
        //
        sql.addStrValue(kana);
        sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        return sql;
    }
    /**
     * <p>ユーザ情報で使用する、五十音にヒットしたユーザ情報を取得する。
     * <br>公開フラグが「公開しない」になっているものは、nullをセットした値を返す。
     * @param kana 検索対象の五十音カナ
     * @param label 検索ラベル
     * @return ユーザデータのカウント
     * @throws SQLException SQL実行例外
     */
    public int getUserKanaIndex2Count(String kana, String[] label) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            SqlBuffer sql = __getUserKanaIndexSql(kana, false, 0, 0, 0, 0, label);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>ユーザ情報CSVを出力する。(カナ検索)
     * @param kana 検索するカナ
     * @param rl UsrCsvRecordListenerImpl
     * @param sortKey 第一ソート項目
     * @param orderKey 第一ソートオーダー
     * @param sortKey2 第二ソート項目
     * @param orderKey2 第二ソートオーダー
     * @param label 検索ラベル
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createUserInfoKanaForCsv(String kana,
            UsrCsvRecordListenerIppanImpl rl, int sortKey, int orderKey,
            int sortKey2, int orderKey2, String[] label)
                    throws SQLException, CSVException {
        //
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        //        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getUserKanaIndexSql(kana, true, sortKey, orderKey,
                    sortKey2, orderKey2, label);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel model = getCmnUsrmInfFromRsKoukai(rs);
                setUsrCsvRecordFromCmnUsrmInfModel(model, rl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }
    /**
     * <p>ユーザ情報で使用する、五十音にヒットしたユーザ情報を取得する。
     * <br>公開フラグが「公開しない」になっているものは、nullをセットした値を返す。
     * @param kana 検索対象の五十音カナ
     * @param sortKey 第一ソート項目
     * @param orderKey 第一ソートオーダー
     * @param sortKey2 第二ソート項目
     * @param orderKey2 第二ソートオーダー
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @param label ラベル検索
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getUserKanaIndex2(
            String kana,
            int sortKey,
            int orderKey,
            int sortKey2,
            int orderKey2,
            int start,
            int limit,
            String[] label) throws SQLException {
        ArrayList<CmnUsrmInfModel> ret =
                getUserKanaIndex(kana, sortKey, orderKey,
                        sortKey2, orderKey2, start, limit, label);
        //ラベル名設定
        getLabelName(ret);
        return ret;

    }
    /**
     * <p>五十音にヒットしたユーザ情報を取得する。
     * @param kana 検索対象の五十音カナ
     * @return 検索にヒットしたユーザデータ
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUserKanaIndex(
            String kana) throws SQLException {
        return getUserKanaIndex(kana, GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC,
                GSConstUser.USER_SORT_NAME, GSConst.ORDER_KEY_ASC, 0, 0, null);
    }
    /**
     * <p>ユーザ情報で使用する、五十音にヒットしたユーザ情報を取得する。
     * <br>公開フラグが「公開しない」になっているものは、nullをセットした値を返す。
     * @param kana 検索対象の五十音カナ
     * @param sortKey 第一ソート項目
     * @param orderKey 第一ソートオーダー
     * @param sortKey2 第二ソート項目
     * @param orderKey2 第二ソートオーダー
     * @param start 取得開始位置
     * @param limit 取得件数(上限値) 0で全件取得
     * @param label ラベル検索
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUserKanaIndex(
            String kana,
            int sortKey,
            int orderKey,
            int sortKey2,
            int orderKey2,
            int start,
            int limit,
            String[] label) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getUserKanaIndexSql(kana, true, sortKey, orderKey,
                    sortKey2, orderKey2, label);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (limit <= 0) {
                while (rs.next()) {
                    ret.add(getCmnUsrmInfFromRsKoukai(rs));
                }
            } else {
                if (start > 1) {
                    rs.absolute(start - 1);
                }

                for (int i = 0; rs.next() && i < limit; i++) {
                    ret.add(getCmnUsrmInfFromRsKoukai(rs));
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
     * <p>ユーザSIDと状態区分を指定しユーザ情報を取得する。
     * @param usrSid ユーザSID
     * @param jtkb 状態区分
     * @return ユーザ情報(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public UserSearchModel getUserInfoJtkb(int usrSid, int jtkb)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UserSearchModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");

            //            sql.addSql("   COALESCE(CMN_USR_INOUT.UIO_STATUS, '0') as UIO_STATUS,");
            sql.addSql("   case");
            sql.addSql("     when IO.UIO_STATUS is null then 0");
            sql.addSql("     else IO.UIO_STATUS");
            sql.addSql("   end as UIO_STATUS,");
            sql.addSql("   IO.UIO_BIKO,");
            sql.addSql("   USRM.USR_SID,");
            sql.addSql("   USRM.USR_UKO_FLG,");
            sql.addSql("   INF.USI_SEI,");
            sql.addSql("   INF.USI_MEI,");
            sql.addSql("   INF.USI_SEI_KN,");
            sql.addSql("   INF.USI_MEI_KN,");
            sql.addSql("   INF.USI_SINI,");
            sql.addSql("   INF.USI_BDATE,");
            sql.addSql("   INF.USI_ZIP1,");
            sql.addSql("   INF.USI_ZIP2,");
            sql.addSql("   INF.TDF_SID,");
            sql.addSql("   INF.USI_ADDR1,");
            sql.addSql("   INF.USI_ADDR2,");
            sql.addSql("   INF.USI_TEL1,");
            sql.addSql("   INF.USI_TEL2,");
            sql.addSql("   INF.USI_TEL3,");
            sql.addSql("   INF.USI_FAX1,");
            sql.addSql("   INF.USI_FAX2,");
            sql.addSql("   INF.USI_FAX3,");
            sql.addSql("   INF.USI_MAIL1,");
            sql.addSql("   INF.USI_MAIL2,");
            sql.addSql("   INF.USI_MAIL3,");
            sql.addSql("   INF.USI_SYAIN_NO,");
            sql.addSql("   INF.USI_SYOZOKU,");
            sql.addSql("   INF.USI_YAKUSYOKU,");
            sql.addSql("   INF.USI_SEIBETU,");
            sql.addSql("   INF.USI_ENTRANCE_DATE,");
            sql.addSql("   INF.USI_SORTKEY1,");
            sql.addSql("   INF.USI_SORTKEY2,");
            sql.addSql("   INF.USI_BIKO,");
            sql.addSql("   INF.USI_LTLGIN,");
            sql.addSql("   INF.USI_AUID,");
            sql.addSql("   INF.USI_ADATE,");
            sql.addSql("   INF.USI_EUID,");
            sql.addSql("   INF.USI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ((CMN_USRM USRM");
            sql.addSql("     left join CMN_USRM_INF INF on USRM.USR_SID = INF.USR_SID)");
            sql.addSql("     left join CMN_USR_INOUT IO on USRM.USR_SID = IO.UIO_SID)");
            sql.addSql(" where");
            sql.addSql("   USRM.USR_SID = ?");
            sql.addIntValue(usrSid);
            if (jtkb != -1) {
                sql.addSql(" and");
                sql.addSql("   USRM.USR_JKBN = ?");
                sql.addIntValue(jtkb);
            }
            sql.addSql(" and");
            sql.addSql("   USRM.USR_SID=INF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = getUserSearchModelFromRs(rs);
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
     * <p>ユーザSIDと状態区分を指定しユーザ情報を取得する。
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<UserSearchModel> getBelongUserInfoJtkb(int gpSid,
            ArrayList<Integer> usrSids, int sortKey,
            int orderKey, int sortKey2, int orderKey2) throws SQLException {

        ArrayList<UserSearchModel> ret = new ArrayList<UserSearchModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = __getBelongUserJtkbSql(gpSid, usrSids, true, sortKey,
                    orderKey, sortKey2, orderKey2);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserSearchModel umodel = null;
                //生データ
                umodel = getUserSearchModelFromRs(rs);

                ret.add(umodel);
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
     * <p>ユーザSIDと状態区分を指定しユーザ情報を取得する。
     * @param gpSids グループSID
     * @param usrSids 除外するユーザSID
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getBelongUserSids(ArrayList<Integer> gpSids,
            ArrayList<Integer> usrSids) throws SQLException {

        ArrayList<Integer> ret = new ArrayList<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = __getBelongUserSidSql(gpSids, usrSids);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <p>ユーザSIDと状態区分を指定しユーザ情報を取得する。
     * @param gpSid グループSID
     * @param usrSid セッションユーザSID
     * @param usrSids 除外するユーザSID
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<UserSearchModel> getMyGroupBelongUserInfoJtkb(int gpSid,
            int usrSid, ArrayList<Integer> usrSids, int sortKey,
            int orderKey, int sortKey2, int orderKey2) throws SQLException {

        ArrayList<UserSearchModel> ret = new ArrayList<UserSearchModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            BelongUserSearchModel model = new BelongUserSearchModel();
            model.setGpSid(gpSid);
            model.setSessionUsrSid(usrSid);
            model.setUsrSids(usrSids);
            model.setSqlType(true);
            model.setSortKey(sortKey);
            model.setOrderKey(orderKey);
            model.setSortKey2(sortKey2);
            model.setOrderKey2(orderKey2);
            SqlBuffer sql = __getMyGroupBelongUserJtkbSql(model);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserSearchModel umodel = null;
                //生データ
                umodel = getUserSearchModelFromRs(rs);

                ret.add(umodel);
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
     * <p>ユーザSIDリストと状態区分を指定しユーザ情報を取得する。
     * @param usrSids ユーザSID
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<UserSearchModel> getUsersInfoJtkb(ArrayList<Integer> usrSids, int sortKey,
            int orderKey, int sortKey2, int orderKey2) throws SQLException {

        ArrayList<UserSearchModel> ret = new ArrayList<UserSearchModel>();
        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = __getUsersJtkbSql(
                    usrSids, true, sortKey, orderKey, sortKey2, orderKey2);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserSearchModel umodel = null;
                //生データ
                umodel = getUserSearchModelBinFromRs(rs);

                ret.add(umodel);
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
     * <p>ユーザ情報CSVを出力する。
     * @param rl UsrCsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createAllUserInfoForCsv(UsrCsvRecordListenerImpl rl)
            throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        //        ArrayList<UserCsvModel> ret = new ArrayList<UserCsvModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_LGID,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2,");
            //            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else CMN_POSITION.POS_NAME");
            sql.addSql("    end) as USI_YAKUSYOKU,");

            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else CMN_POSITION.POS_SORT");
            sql.addSql("    end) as YAKUSYOKU_SORT,");

            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.USI_OTPSEND_ADDRESS");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("   CMN_USRM ");
            sql.addSql("     left join CMN_USRM_INF on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("   )");
            sql.addSql("   left join CMN_POSITION on CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");

            sql.addSql(" order by");
            sql.addSql("   YAKUSYOKU_EXIST asc, ");
            sql.addSql("   YAKUSYOKU_SORT asc,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                setUsrCsvRecordFromRs(rs, rl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }
    //
    //    /**
    //     * <p>グループに所属するユーザ情報一覧を取得する。
    //     * @param gpSid グループSID
    //     * @param usrSids 除外するユーザSID
    //     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
    //     * @throws SQLException SQL実行例外
    //     */
    //    public ArrayList<CmnUsrmInfModel> getBelongUserList(int gpSid,
    //            ArrayList<Integer> usrSids) throws SQLException {
    //        return getBelongUserList(gpSid, usrSids, false,
    //                GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);
    //    }

    /**
     * <p>グループに所属するユーザ情報のカウントを取得する。(ユーザ情報以外で使用)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @return 検索にヒットしたユーザデータのカウント
     * @throws SQLException SQL実行例外
     */
    public int getBelongUserCount(int gpSid,
            ArrayList<Integer> usrSids) throws SQLException {
        return getBelongUserCount(gpSid, usrSids, null);
    }

    /**
     * <p>グループに所属するユーザ情報のカウントを取得する。
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param label 検索ラベル
     * @return 検索にヒットしたユーザデータのカウント
     * @throws SQLException SQL実行例外
     */
    public int getBelongUserCount(int gpSid,
            ArrayList<Integer> usrSids, String[] label) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;

        con = getCon();
        try {
            SqlBuffer sql = __getBelongUserSql(gpSid, usrSids, false, 0, 0, -1, -1, label);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可・ユーザ情報以外で使用)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param kfFlg 個人情報公開フラグを適用(非公開情報はNull)するかどうか,
     *         true:適用する(非公開情報はNull), false:適用しない(入力情報そのまま)
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(int gpSid,
            ArrayList<Integer> usrSids, boolean kfFlg, int sortKey,
            int orderKey, int sortKey2, int orderKey2)
                    throws SQLException {

        return getBelongUserList(gpSid, usrSids, kfFlg,
                sortKey, orderKey, sortKey2, orderKey2, null);
    }

    /**
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可・ユーザ情報以外で使用)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param kfFlg 個人情報公開フラグを適用(非公開情報はNull)するかどうか,
     *         true:適用する(非公開情報はNull), false:適用しない(入力情報そのまま)
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongAllUserList(int gpSid,
            ArrayList<Integer> usrSids, boolean kfFlg, int sortKey,
            int orderKey, int sortKey2, int orderKey2)
                    throws SQLException {

        return getBelongAllUserList(gpSid, usrSids, kfFlg,
                sortKey, orderKey, sortKey2, orderKey2, null);
    }

    /**
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param kfFlg 個人情報公開フラグを適用(非公開情報はNull)するかどうか,
     *         true:適用する(非公開情報はNull), false:適用しない(入力情報そのまま)
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @param label 検索ラベル
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(int gpSid,
            ArrayList<Integer> usrSids, boolean kfFlg, int sortKey,
            int orderKey, int sortKey2, int orderKey2, String[] label)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getBelongUserSql(gpSid, usrSids, true, sortKey,
                    orderKey, sortKey2, orderKey2, label);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel umodel = null;
                if (kfFlg) {
                    //個人情報非公開フラグ適用
                    umodel = getCmnUsrmInfFromRsKoukai(rs);
                } else {
                    //生データ
                    umodel = getCmnUsrmInfModelFromRs2(rs);
                }
                ret.add(umodel);
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
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param kfFlg 個人情報公開フラグを適用(非公開情報はNull)するかどうか,
     *         true:適用する(非公開情報はNull), false:適用しない(入力情報そのまま)
     * @param sortKey ソート項目1
     * @param orderKey ソートオーダー1
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @param label 検索ラベル
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongAllUserList(int gpSid,
            ArrayList<Integer> usrSids, boolean kfFlg, int sortKey,
            int orderKey, int sortKey2, int orderKey2, String[] label)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getBelongAllUserSql(gpSid, usrSids, true, sortKey,
                    orderKey, sortKey2, orderKey2, label);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel umodel = null;
                if (kfFlg) {
                    //個人情報非公開フラグ適用
                    umodel = getCmnUsrmInfFromRsKoukai(rs);
                } else {
                    //生データ
                    umodel = getCmnUsrmInfModelFromRs2(rs);
                }
                ret.add(umodel);
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
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可)
     * @param model 検索条件
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getMyGroupBelongUserList(BelongUserSearchModel model)
            throws SQLException {
        //
        //    public ArrayList<CmnUsrmInfModel> getMyGroupBelongUserList(int gpSid,
        //            int sessionUsrSid, ArrayList<Integer> usrSids, boolean kfFlg, int sortKey,
        //            int orderKey, int sortKey2, int orderKey2)
        //            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            model.setSqlType(true);
            SqlBuffer sql = __getMyGroupBelongUserSql(model);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel umodel = null;
                if (model.isKfFlg()) {
                    //個人情報非公開フラグ適用
                    umodel = getCmnUsrmInfFromRsKoukai(rs);
                } else {
                    //生データ
                    umodel = getCmnUsrmInfModelFromRs(rs);
                    umodel.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                }
                ret.add(umodel);
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
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param kfFlg 個人情報公開フラグを適用(非公開情報はNull)するかどうか,
     *         true:適用する(非公開情報はNull), false:適用しない(入力情報そのまま)
     * @param searchMdl 検索モデル
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserSearchList(int gpSid,
            ArrayList<Integer> usrSids, boolean kfFlg,
            ShainSearchModel searchMdl,
            int start, int limit)
                    throws SQLException {

        return getBelongUserSearchList(gpSid, usrSids, kfFlg, searchMdl, start, limit, null);
    }

    /**
     * <p>グループに所属するユーザ情報一覧を取得する。(個人情報公開フラグの適用を選択可)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param kfFlg 個人情報公開フラグを適用(非公開情報はNull)するかどうか,
     *         true:適用する(非公開情報はNull), false:適用しない(入力情報そのまま)
     * @param searchMdl 検索モデル
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @param label 検索ラベル
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserSearchList(int gpSid,
            ArrayList<Integer> usrSids, boolean kfFlg,
            ShainSearchModel searchMdl,
            int start, int limit, String[] label)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getBelongUserSql(gpSid, usrSids, true,
                    searchMdl.getSortKey(), searchMdl.getSortOrder(),
                    searchMdl.getSortKey2(), searchMdl.getSortOrder2(),
                    label);

            int offset = start - 1;
            if (limit >= 1 && offset >= 0) {
                sql.setPagingValue(offset, limit);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnUsrmInfModel umodel = null;
                if (kfFlg) {
                    //個人情報非公開フラグ適用
                    umodel = getCmnUsrmInfFromRsKoukai(rs);
                } else {
                    //生データ
                    umodel = getCmnUsrmInfModelFromRs(rs);
                    umodel.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                }
                ret.add(umodel);
            }

            //ラベル名設定
            getLabelName(ret);
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>ユーザ情報CSVを出力する。(グループ検索)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param rl UsrCsvRecordListenerIppanImpl
     * @param sortKey ソート項目
     * @param orderKey ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createUserInfoGroupForCsv(int gpSid,
            ArrayList<Integer> usrSids, UsrCsvRecordListenerIppanImpl rl,
            int sortKey, int orderKey, int sortKey2, int orderKey2)
                    throws SQLException, CSVException {
        createUserInfoGroupForCsv(gpSid, usrSids, rl, sortKey, orderKey, sortKey2, orderKey2, null);
        return;
    }

    /**
     * <p>ユーザ情報CSVを出力する。(グループ検索)
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param rl UsrCsvRecordListenerIppanImpl
     * @param sortKey ソート項目
     * @param orderKey ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @param label 検索ラベル
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createUserInfoGroupForCsv(int gpSid,
            ArrayList<Integer> usrSids, UsrCsvRecordListenerIppanImpl rl,
            int sortKey, int orderKey, int sortKey2, int orderKey2, String[] label)
                    throws SQLException, CSVException {
        //
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        //        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getBelongUserSql(gpSid, usrSids, true, sortKey,
                    orderKey, sortKey2, orderKey2, label);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel model = getCmnUsrmInfFromRsKoukai(rs);
                setUsrCsvRecordFromCmnUsrmInfModel(model, rl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <br>[機  能] ユーザ情報と所属グループを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ret 取得結果
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Man330ExpModel> getUserDataBelongm()
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Man330ExpModel> ret = new ArrayList<Man330ExpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_GROUPM.GRP_ID as GRP_ID,");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_NAME_KN as GRP_NAME_KN,");
            sql.addSql("   CMN_BELONGM.BEG_DEFGRP as BEG_DEFGRP");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_GROUPM,");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");

            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);

            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_GROUPM.GRP_SID = CMN_BELONGM.GRP_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = ? ");
            sql.addIntValue(CmnGroupmDao.GRP_JKBN_LIVING);
            sql.addSql(" order by");
            sql.addSql(" CMN_USRM_INF.USR_SID asc,");
            sql.addSql(" CMN_BELONGM.BEG_DEFGRP desc");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Man330ExpModel mdl = new Man330ExpModel();

                //ユーザSID
                mdl.setUserSid(rs.getInt("USR_SID"));
                //ログインID
                mdl.setUserId(rs.getString("USR_LGID"));
                //氏名
                String usrName = rs.getString("USI_SEI")
                        + " "
                        + rs.getString("USI_MEI");
                mdl.setUsrName(usrName);
                //氏名カナ
                String usrNameKn = rs.getString("USI_SEI_KN")
                        + " "
                        + rs.getString("USI_MEI_KN");
                mdl.setUsrNameKana(usrNameKn);
                //グループID
                mdl.setGroupId(rs.getString("GRP_ID"));
                //グループ名
                mdl.setGroupName(rs.getString("GRP_NAME"));
                //グループ名カナ
                mdl.setGroupNameKana(rs.getString("GRP_NAME_KN"));

                ret.add(mdl);
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
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param prjSid プロジェクトSID
     * @return ret 取得結果
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<PrjMemberEditModel> getPrjBelongUser(int gpSid,
            ArrayList<Integer> usrSids,
            int prjSid)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjMemberEditModel> ret = new ArrayList<PrjMemberEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
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
            sql.addSql("    end) as YAKUSYOKU_SORT,");
            sql.addSql("   PRJ_MEMBERS.PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRJ_MEMBERS.PRM_MEM_KEY");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   PRJ_MEMBERS ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = PRJ_MEMBERS.USR_SID ");
            sql.addSql(" and");
            sql.addSql("   PRJ_MEMBERS.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_MEMBERS.PRM_EMPLOYEE_KBN = ?");

            sql.addIntValue(prjSid);
            sql.addIntValue(0);

            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addIntValue(gpSid);
            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   CMN_USRM.USR_SID not in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            sql.addSql(" union");

            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
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
            sql.addSql("    end) as YAKUSYOKU_SORT,");
            sql.addSql("   0 as PRM_EMPLOYEE_KBN,");
            sql.addSql("   null as PRM_MEM_KEY");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_BELONGM ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and not exists (");
            sql.addSql("   select * ");
            sql.addSql("     from");
            sql.addSql("       PRJ_MEMBERS");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM.USR_SID = PRJ_MEMBERS.USR_SID");
            sql.addSql("     and");
            sql.addSql("       PRJ_MEMBERS.PRJ_SID = ?");
            sql.addSql("     and");
            sql.addSql("       PRJ_MEMBERS.PRM_EMPLOYEE_KBN = ?");
            sql.addSql(" )");

            sql.addIntValue(prjSid);
            sql.addIntValue(0);

            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addIntValue(gpSid);
            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   CMN_USRM.USR_SID not in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }


            //ソート条件取得
            __createOrderSql(sql, GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                PrjMemberEditModel mdl = new PrjMemberEditModel();
                mdl.setUsrSid(rs.getInt("USR_SID"));
                mdl.setUsiSei(rs.getString("USI_SEI"));
                mdl.setUsiMei(rs.getString("USI_MEI"));
                mdl.setMemberKey(rs.getString("PRM_MEM_KEY"));
                ret.add(mdl);
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
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param spUsrKeyMap ユーザキーハッシュ
     * @return ret 取得結果
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<PrjMemberEditModel> getNewPrjBelongUser(int gpSid,
            ArrayList<Integer> usrSids,
            HashMap<String, String> spUsrKeyMap)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjMemberEditModel> ret = new ArrayList<PrjMemberEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
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
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");

            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addIntValue(gpSid);
            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   CMN_USRM.USR_SID not in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            //ソート条件取得
            __createOrderSql(sql, GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrjMemberEditModel mdl = new PrjMemberEditModel();
                int usrSid = rs.getInt("USR_SID");
                mdl.setUsrSid(usrSid);
                mdl.setUsiSei(rs.getString("USI_SEI"));
                mdl.setUsiMei(rs.getString("USI_MEI"));
                mdl.setMemberKey(spUsrKeyMap.get(String.valueOf(usrSid)));
                ret.add(mdl);
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
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sqlType true:select, false:count
     * @param sortKey1 ソート項目
     * @param orderKey1 ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @param label 検索ラベル
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getBelongUserSql(int gpSid, ArrayList<Integer> usrSids,
            boolean sqlType, int sortKey1, int orderKey1, int sortKey2,
            int orderKey2, String[] label) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (sqlType) {
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1 as USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1 as USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2 as USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2 as USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3 as USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3 as USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1 as USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2 as USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3 as USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1 as USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2 as USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3 as USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            //            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            //            sql.addSql("   case");
            //            sql.addSql("     when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
            //            sql.addSql("     else 0");
            //            sql.addSql("   end YAKUSYOKU_LEN");
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

        } else {
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   CMN_USRM, ");
        sql.addSql("   CMN_USRM_INF, ");
        sql.addSql("   CMN_BELONGM ");
        sql.addSql(" where ");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql(" and");
        //ラベル
        if (label != null && label.length != 0) {
            sql.addSql("   exists (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM_LABEL");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_LABEL.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and");
            if (label.length >= 2) {
                sql.addSql("     LAB_SID in ( ?");
                sql.addIntValue(Integer.parseInt(label[0]));
                for (int i = 1; i < label.length; i++) {
                    sql.addSql(",?");
                    sql.addIntValue(Integer.parseInt(label[i]));
                }
                sql.addSql("       )");
            } else {
                sql.addSql("       LAB_SID=? ");
                sql.addIntValue(Integer.parseInt(label[0]));
            }
            sql.addSql("       )");
            sql.addSql(" and");
        }

        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
        sql.addIntValue(gpSid);
        if (usrSids != null && usrSids.size() > 0) {
            List<Integer> exeList = new ArrayList<Integer>();
            Iterator<Integer> itr = usrSids.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500 && itr.hasNext()) {
                    continue;
                }
                sql.addSql(" and ");
                sql.addSql("   CMN_USRM.USR_SID not in( ");
                String notAccessUsers = String.join(",", exeList.stream()
                                                            .map(u -> u.toString())
                                                            .collect(Collectors.toList()));
                sql.addSql(notAccessUsers);
                sql.addSql("  ) ");
                exeList.clear();
            }
        }
        if (sqlType) {
            //ソート条件取得
            __createOrderSql(sql, sortKey1, orderKey1, sortKey2, orderKey2);
        }
        return sql;
    }

    /**
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sqlType true:select, false:count
     * @param sortKey1 ソート項目
     * @param orderKey1 ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @param label 検索ラベル
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getBelongAllUserSql(int gpSid, ArrayList<Integer> usrSids,
            boolean sqlType, int sortKey1, int orderKey1, int sortKey2,
            int orderKey2, String[] label) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (sqlType) {
            sql.addSql("   distinct(CMN_USRM_INF.USR_SID) as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1 as USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1 as USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2 as USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2 as USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3 as USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3 as USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1 as USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2 as USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3 as USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1 as USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2 as USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3 as USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
            sql.addSql("      else CMN_USRM_INF.USI_SYAIN_NO");
            sql.addSql("    end) as SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            //            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            //            sql.addSql("   case");
            //            sql.addSql("     when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
            //            sql.addSql("     else 0");
            //            sql.addSql("   end YAKUSYOKU_LEN");
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
            sql.addSql("    end) as YAKUSYOKU_SORT,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG");

        } else {
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   CMN_USRM, ");
        sql.addSql("   CMN_USRM_INF, ");
        sql.addSql("   CMN_BELONGM ");
        sql.addSql(" where ");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql(" and");
        //ラベル
        if (label != null && label.length != 0) {
            sql.addSql("   exists (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM_LABEL");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_LABEL.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and");
            if (label.length >= 2) {
                sql.addSql("     LAB_SID in ( ?");
                sql.addStrValue(label[0]);
                for (int i = 1; i < label.length; i++) {
                    sql.addSql(",?");
                    sql.addStrValue(label[i]);
                }
                sql.addSql("       )");
            } else {
                sql.addSql("       LAB_SID=? ");
                sql.addIntValue(Integer.parseInt(label[0]));
            }
            sql.addSql("       )");
            sql.addSql(" and");
        }

        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");

        if (gpSid != GSConstUser.USER_RESERV_SID) {
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addIntValue(gpSid);
        }
        if (usrSids != null && usrSids.size() > 0) {
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in( ");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(usrSids.get(i).intValue());
            }
            sql.addSql("  ) ");
        }
        if (sqlType) {
            //ソート条件取得
            __createOrderSql(sql, sortKey1, orderKey1, sortKey2, orderKey2);
        }
        return sql;
    }

    /**
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getMyGroupBelongUserSql(BelongUserSearchModel model
            //            int gpSid, int sessionUsrSid, ArrayList<Integer> usrSids,
            //            boolean sqlType, int sortKey1, int orderKey1, int sortKey2,
            //            int orderKey2
            ) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (model.isSqlType()) {
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1 as USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1 as USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2 as USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2 as USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3 as USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3 as USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1 as USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2 as USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3 as USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1 as USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2 as USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3 as USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            //            sql.addSql("   case");
            //            sql.addSql("     when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
            //            sql.addSql("     else 0");
            //            sql.addSql("   end YAKUSYOKU_LEN");
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

        } else {
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   CMN_USRM, ");
        sql.addSql("   CMN_USRM_INF, ");
        sql.addSql("   CMN_MY_GROUP_MS ");
        sql.addSql(" where ");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        //ユーザSID < 100は除外
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_MY_GROUP_MS.MGM_SID = CMN_USRM.USR_SID ");
        sql.addSql(" and ");
        sql.addSql("   CMN_MY_GROUP_MS.MGP_SID = ? ");
        sql.addSql(" and ");
        sql.addIntValue(model.getGpSid());
        sql.addSql("   (");
        sql.addSql("   CMN_MY_GROUP_MS.USR_SID = ?");
        sql.addIntValue(model.getSessionUsrSid());
        sql.addSql("   or");
        sql.addSql("   CMN_MY_GROUP_MS.MGP_SID in (");
        sql.addSql("     select MGP_SID");
        sql.addSql("     from CMN_MY_GROUP_SHARE");
        sql.addSql("     where ");
        sql.addSql("       MGS_USR_SID = ?");
        sql.addSql("       or (MGS_USR_SID = -1 and MGS_GRP_SID in (");
        sql.addSql("         select GRP_SID from CMN_BELONGM");
        sql.addSql("         where USR_SID=?");
        sql.addSql("         )");
        sql.addSql("       )");
        sql.addSql("     )");
        sql.addIntValue(model.getSessionUsrSid());
        sql.addIntValue(model.getSessionUsrSid());
        sql.addSql("   )");

        if (model.getUsrSids() != null && model.getUsrSids().size() > 0) {
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in( ");
            for (int i = 0; i < model.getUsrSids().size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(model.getUsrSids().get(i).intValue());
            }
            sql.addSql("  ) ");
        }
        if (model.isSqlType()) {
            //ソート条件取得
            __createOrderSql(
                    sql,
                    model.getSortKey(),
                    model.getOrderKey(),
                    model.getSortKey2(),
                    model.getOrderKey2());
        }
        return sql;
    }
    /**
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す(在席情報も取得)
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sqlType true:select, false:count
     * @param sortKey1 ソート項目
     * @param orderKey1 ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getBelongUserJtkbSql(int gpSid, ArrayList<Integer> usrSids,
            boolean sqlType, int sortKey1, int orderKey1, int sortKey2,
            int orderKey2) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (sqlType) {
            sql.addSql("   case");
            sql.addSql("     when IO.UIO_STATUS is null then 0");
            sql.addSql("     else IO.UIO_STATUS");
            sql.addSql("   end as UIO_STATUS,");
            sql.addSql("   IO.UIO_BIKO,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   case");
            sql.addSql("     when CMN_USRM_INF.USI_BDATE_KF = 1 then null");
            sql.addSql("     else CMN_USRM_INF.USI_BDATE");
            sql.addSql("   end as USI_BDATE,");
            //            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            //            sql.addSql("   case");
            //            sql.addSql("     when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
            //            sql.addSql("     else 0");
            //            sql.addSql("   end YAKUSYOKU_LEN");
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

        } else {
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   ((CMN_USRM CMN_USRM");
        sql.addSql("   left join CMN_USRM_INF CMN_USRM_INF"
                + " on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID)");
        sql.addSql("   left join CMN_USR_INOUT IO on CMN_USRM.USR_SID = IO.UIO_SID),");
        sql.addSql("   CMN_BELONGM ");
        sql.addSql(" where ");
        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
        sql.addIntValue(gpSid);
        if (usrSids != null && usrSids.size() > 0) {
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in( ");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(usrSids.get(i).intValue());
            }
            sql.addSql("  ) ");
        }
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");

        if (sqlType) {
            //ソート条件取得
            __createOrderSql(sql, sortKey1, orderKey1, sortKey2, orderKey2);
        }
        return sql;
    }



    /**
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSids グループSID
     * @param usrSids 除外するユーザSID
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getBelongUserSidSql(ArrayList<Integer> gpSids, ArrayList<Integer> usrSids) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID");
        sql.addSql(" from");
        sql.addSql("   ((CMN_USRM CMN_USRM");
        sql.addSql("   left join CMN_USRM_INF CMN_USRM_INF"
                + " on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID)");
        sql.addSql("   left join CMN_USR_INOUT IO on CMN_USRM.USR_SID = IO.UIO_SID),");
        sql.addSql("   CMN_BELONGM ");
        sql.addSql(" where ");
        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.GRP_SID in (");
        int lasIdx = gpSids.size() - 1;
        for (int idx = 0; idx < lasIdx; idx++) {
            sql.addSql("     ?,");
            sql.addIntValue(gpSids.get(idx));
        }
        sql.addSql("     ?");
        sql.addIntValue(gpSids.get(lasIdx));
        sql.addSql("   )");

        if (usrSids != null && usrSids.size() > 0) {
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in( ");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(usrSids.get(i).intValue());
            }
            sql.addSql("  ) ");
        }
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
        sql.addSql(" group by CMN_USRM_INF.USR_SID");

        return sql;
    }




    /**
     * <br>[機  能] グループに所属するユーザを取得するSQLを返す(在席情報も取得)
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getMyGroupBelongUserJtkbSql(
            BelongUserSearchModel model) {
        //            int gpSid,
        //            int usrSid,
        //            ArrayList<Integer> usrSids,
        //            boolean sqlType, int sortKey1, int orderKey1, int sortKey2,
        //            int orderKey2) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (model.isSqlType()) {
            sql.addSql("   case");
            sql.addSql("     when IO.UIO_STATUS is null then 0");
            sql.addSql("     else IO.UIO_STATUS");
            sql.addSql("   end as UIO_STATUS,");
            sql.addSql("   IO.UIO_BIKO as UIO_BIKO,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   case");
            sql.addSql("     when CMN_USRM_INF.USI_BDATE_KF = 1 then null");
            sql.addSql("     else CMN_USRM_INF.USI_BDATE");
            sql.addSql("   end as USI_BDATE,");
            //            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            //            sql.addSql("   case");
            //            sql.addSql("     when LENGTH(CMN_USRM_INF.USI_YAKUSYOKU) = 0 then 1");
            //            sql.addSql("     else 0");
            //            sql.addSql("   end YAKUSYOKU_LEN");
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

        } else {
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   ((CMN_USRM CMN_USRM");
        sql.addSql("   left join CMN_USRM_INF CMN_USRM_INF"
                + " on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID)");
        sql.addSql("   left join CMN_USR_INOUT IO on CMN_USRM.USR_SID = IO.UIO_SID),");
        sql.addSql("   CMN_MY_GROUP_MS ");
        sql.addSql(" where ");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        //ユーザSID < 100は除外
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID > ?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_MY_GROUP_MS.MGM_SID = CMN_USRM.USR_SID ");
        sql.addSql(" and ");
        sql.addSql("   CMN_MY_GROUP_MS.MGP_SID = ? ");
        sql.addSql(" and ");
        sql.addIntValue(model.getGpSid());
        sql.addSql("   (");
        sql.addSql("   CMN_MY_GROUP_MS.USR_SID = ?");
        sql.addIntValue(model.getSessionUsrSid());
        sql.addSql("   or");
        sql.addSql("   CMN_MY_GROUP_MS.MGP_SID in (");
        sql.addSql("     select MGP_SID");
        sql.addSql("     from CMN_MY_GROUP_SHARE");
        sql.addSql("     where ");
        sql.addSql("       MGS_USR_SID = ?");
        sql.addSql("       or (MGS_USR_SID = -1 and MGS_GRP_SID in (");
        sql.addSql("         select GRP_SID from CMN_BELONGM");
        sql.addSql("         where USR_SID=?");
        sql.addSql("         )");
        sql.addSql("       )");
        sql.addSql("     )");
        sql.addIntValue(model.getSessionUsrSid());
        sql.addIntValue(model.getSessionUsrSid());
        sql.addSql("   )");

        if (model.getUsrSids() != null && model.getUsrSids().size() > 0) {
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in ( ");
            for (int i = 0; i < model.getUsrSids().size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(model.getUsrSids().get(i).intValue());
            }
            sql.addSql("  ) ");
        }
        if (model.isSqlType()) {
            //ソート条件取得
            __createOrderSql(
                    sql,
                    model.getSortKey(),
                    model.getOrderKey(),
                    model.getSortKey2(),
                    model.getOrderKey2());
        }
        return sql;
    }

    /**
     * <br>[機  能] 指定したユーザを取得するSQLを返す(在席情報も取得)
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSids 取得するユーザSID
     * @param sqlType true:select, false:count
     * @param sortKey1 ソート項目
     * @param orderKey1 ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getUsersJtkbSql(ArrayList<Integer> usrSids,
            boolean sqlType, int sortKey1, int orderKey1, int sortKey2,
            int orderKey2) {
        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (sqlType) {
            sql.addSql("   case");
            sql.addSql("     when IO.UIO_STATUS is null then 0");
            sql.addSql("     else IO.UIO_STATUS");
            sql.addSql("   end as UIO_STATUS,");
            sql.addSql("   IO.UIO_BIKO as UIO_BIKO,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   case");
            sql.addSql("     when CMN_USRM_INF.USI_BDATE_KF = 1 then null");
            sql.addSql("     else CMN_USRM_INF.USI_BDATE");
            sql.addSql("   end as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            sql.addSql("   BIN.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   BIN.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   BIN.BIN_JKBN as BIN_JKBN,");
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

        } else {
            sql.addSql("   count(*) as CNT");
        }
        sql.addSql(" from");
        sql.addSql("   CMN_USRM");
        sql.addSql("   left join CMN_USRM_INF"
                + " on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("   left join CMN_USR_INOUT IO on CMN_USRM.USR_SID = IO.UIO_SID");
        sql.addSql("   left join CMN_BINF BIN on CMN_USRM_INF.BIN_SID = BIN.BIN_SID");

        sql.addSql(" where ");
        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM.USR_SID > ?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        if (usrSids != null && usrSids.size() > 0) {
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID in( ");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(usrSids.get(i).intValue());
            }
            sql.addSql("  ) ");
        }
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");

        if (sqlType) {
            //ソート条件取得
            __createOrderSql(sql, sortKey1, orderKey1, sortKey2, orderKey2);
        }
        return sql;
    }

    /**
     * <br>[機  能] ユーザ情報取得時の並び順を指定されたSqlBufferへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortKey1 ソートキー1
     * @param orderKey1 ソートキー1オーダー
     * @param sortKey2 ソートキー2
     * @param orderKey2 ソートキー2オーダー
     * @return SqlBuffer
     */
    public SqlBuffer createOrderSql(SqlBuffer sql, int sortKey1,
            int orderKey1, int sortKey2, int orderKey2) {

        __createOrderSql(sql, sortKey1, orderKey1);
        if (sortKey2 > 0) {
            if (sortKey1 > 0) {
                sql.addSql("   ,");
            }
            __createOrderSql(sql, sortKey2, orderKey2);
        }
        sql.addSql(", CMN_USRM_INF.USR_SID");
        return sql;
    }

    /**
     * SQLのオーダ文を作成する
     * @param sql SQL
     * @param sortKey1 ソートキー1
     * @param orderKey1 ソートキー1オーダー
     * @param sortKey2 ソートキー2
     * @param orderKey2 ソートキー2オーダー
     * @return SqlBuffer
     */
    private SqlBuffer __createOrderSql(SqlBuffer sql, int sortKey1,
            int orderKey1, int sortKey2, int orderKey2) {
        log__.info("sortkey1 = " + sortKey1);
        log__.info("sortkey2 = " + sortKey2);

        sql.addSql(" order by");
        return createOrderSql(sql, sortKey1, orderKey1, sortKey2, orderKey2);
    }

    /**
     * SQLのオーダ文を作成する
     * @param sql SQL
     * @param sortKey ソートキー
     * @param orderKey ソートオーダー
     * @return SqlBuffer
     */
    private SqlBuffer __createOrderSql(SqlBuffer sql, int sortKey, int orderKey) {
        //SELECTの場合のみ指定
        String orderStr = "";
        //オーダー
        if (orderKey == GSConst.ORDER_KEY_ASC) {
            orderStr = "  asc";
        } else {
            orderStr = "  desc";
        }

        //ソートカラム
        switch (sortKey) {
        //氏名
        case GSConstUser.USER_SORT_NAME:
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
            sql.addSql(orderStr);
            sql.addSql("   ,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
            sql.addSql(orderStr);
            break;
            //社員/職員番号
        case GSConstUser.USER_SORT_SNO:
            sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
            sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
            sql.addSql(orderStr);
            break;
            //役職
        case GSConstUser.USER_SORT_YKSK:
            sql.addSql("  YAKUSYOKU_EXIST");
            sql.addSql(orderStr);
            sql.addSql("  ,");
            sql.addSql("  YAKUSYOKU_SORT");
            sql.addSql(orderStr);
            //                sql.addSql("  ,");
            //                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
            //                sql.addSql(orderStr);
            //                sql.addSql("   ,");
            //                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
            //                sql.addSql(orderStr);

            break;
            //生年月日
        case GSConstUser.USER_SORT_BDATE:
            sql.addSql("  CMN_USRM_INF.USI_BDATE");
            sql.addSql(orderStr);

            break;

            //ソートキー1
        case GSConstUser.USER_SORT_SORTKEY1:
            sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
            sql.addSql(orderStr);

            break;
            //ソートキー2
        case GSConstUser.USER_SORT_SORTKEY2:
            sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
            sql.addSql(orderStr);

            break;

            //在席状況
        case GSConstUser.USER_SORT_UIO:
            sql.addSql("  UIO_STATUS");
            sql.addSql(orderStr);
            break;
            //在席コメント
        case GSConstUser.USER_SORT_COMM:
            sql.addSql("  IO.UIO_BIKO");
            sql.addSql(orderStr);
            break;
        default:
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
            sql.addSql(orderStr);
            sql.addSql("   ,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
            sql.addSql(orderStr);
            break;
        }
        return sql;
    }

    /**
     * <p>詳細検索
     * @param searchModel 検索条件
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @return List in QueryTableModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getSyousaiSearchList(ShainSearchModel searchModel,
            int start, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __getSyousaiSearchSql(searchModel, true);
            log__.info(sql.toLogString());

            if (limit > 0 && start > 1) {
                pstmt = con.prepareStatement(sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            } else {
                pstmt = con.prepareStatement(sql.toSqlString());
            }

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (limit > 0) {
                if (start > 1) {
                    rs.absolute(start - 1);
                }

                for (int i = 0; rs.next() && i < limit; i++) {
                    ret.add(getCmnUsrmInfFromRsKoukai(rs));
                }
            } else {
                while (rs.next()) {
                    ret.add(getCmnUsrmInfFromRsKoukai(rs));
                }
            }

            //ラベル名設定
            getLabelName(ret);
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     * <p>詳細検索(カウント)
     * @param searchModel 検索条件
     * @return 検索条件に該当する件数
     * @throws SQLException SQL実行例外
     */
    public int getSyousaiSearchCount(ShainSearchModel searchModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = __getSyousaiSearchSql(searchModel, false);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>ユーザ情報CSVを出力する。(詳細検索)
     * @param searchModel 検索条件
     * @param rl UsrCsvRecordListenerIppanImpl
     * @throws CSVException CSV出力時例外
     * @throws SQLException SQL実行例外
     */
    public void createUserInfoSyousaiForCsv(ShainSearchModel searchModel,
            UsrCsvRecordListenerIppanImpl rl) throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        //        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __getSyousaiSearchSql(searchModel, true);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel model = getCmnUsrmInfFromRsKoukai(rs);
                setUsrCsvRecordFromCmnUsrmInfModel(model, rl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <p>詳細検索で使用するSQLを取得する。
     * @param searchModel 検索条件
     * @param sqlType true:select, false:count
     * @return List in QueryTableModel
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getSyousaiSearchSql(ShainSearchModel searchModel,
            boolean sqlType) throws SQLException {
        //SQL文
        SqlBuffer sql = new SqlBuffer();

        if (sqlType == true) {
            //SELECT
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE,");
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

            sql.addSql(" from (");
        } else {
            //COUNT
            sql.addSql(" select count(*) as CNT from (");
        }
        sql.addSql(" select");
        sql.addSql("   IUSER.USR_SID");
        sql.addSql(" from");
        sql.addSql("   (");
        sql.addSql("   select");
        sql.addSql("     CMN_USRM.USR_LGID as USR_LGID,");
        sql.addSql("     CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("     CMN_USRM_INF.USR_SID as USR_SID,");
        sql.addSql("     CMN_USRM_INF.USI_SEI as USI_SEI,");
        sql.addSql("     CMN_USRM_INF.USI_MEI as USI_MEI,");
        sql.addSql("     CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("     CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("     CMN_USRM_INF.USI_SINI as USI_SINI,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_BDATE_KF=0 then CMN_USRM_INF.USI_BDATE");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_BDATE,");
        sql.addSql("     CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
        sql.addSql("     CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_TDF_KF=0 then CMN_USRM_INF.TDF_SID");
        sql.addSql("     else");
        sql.addSql("       0");
        sql.addSql("     end TDF_SID,");
        sql.addSql("     CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
        sql.addSql("     CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
        sql.addSql("     CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
        sql.addSql("     CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
        sql.addSql("     CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
        sql.addSql("     CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
        sql.addSql("     CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
        sql.addSql("     CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL1_KF=0 then CMN_USRM_INF.USI_MAIL1");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL1,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL2_KF=0 then CMN_USRM_INF.USI_MAIL2");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL3_KF=0 then CMN_USRM_INF.USI_MAIL3");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL3,");
        sql.addSql("     CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
        sql.addSql("     CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
        sql.addSql("     CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
        sql.addSql("     CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
        sql.addSql("     CMN_USRM_INF.POS_SID as POS_SID,");
        sql.addSql("     CMN_USRM_INF.USI_BIKO as USI_BIKO,");
        sql.addSql("     CMN_USRM_INF.BIN_SID as BIN_SID");
        sql.addSql("   from");
        sql.addSql("     CMN_USRM");
        sql.addSql("     left join");
        sql.addSql("        CMN_USRM_INF ");
        sql.addSql("     on");
        sql.addSql("        CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
        sql.addSql("   where ");
        sql.addSql("     CMN_USRM.USR_JKBN=0");
        sql.addSql("  ) as IUSER,");
        sql.addSql("   (");
        sql.addSql("     select");
        sql.addSql("       CMN_GROUPM.GRP_SID,");
        sql.addSql("       CMN_BELONGM.USR_SID");
        sql.addSql("     from");
        sql.addSql("       CMN_GROUPM,");
        sql.addSql("       CMN_BELONGM");
        sql.addSql("     where ");
        sql.addSql("       CMN_GROUPM.GRP_SID=CMN_BELONGM.GRP_SID");
        sql.addSql("     and");
        sql.addSql("       CMN_GROUPM.GRP_JKBN=0");
        sql.addSql("   ) as BELONG");
        sql.addSql(" where");
        sql.addSql("   IUSER.USR_SID = BELONG.USR_SID");

        if (searchModel.isExcludeSysUser()) {
            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   IUSER.USR_SID>?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
        }

        //ユーザID
        String userId = searchModel.getUserId();
        if (!StringUtil.isNullZeroString(userId)) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USR_LGID like '%"
                    + JDBCUtil.escapeForLikeSearch(userId)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }
        //ログイン停止フラグ
        if (searchModel.getUsrUkoFlg() != GSConstUser.USER_SEARCH_USRUKOFLG_ALL) {
            sql.addSql(" and");
            sql.addSql("   IUSER.USR_UKO_FLG=?");
            sql.addIntValue(searchModel.getUsrUkoFlg());
        }
        //キーワード（社員/職員番号、氏名、氏名カナ、メールアドレス）
        String keyword = searchModel.getKeyword();

        if (!StringUtil.isNullZeroString(keyword)) {
            sql.addSql(" and");
            __setKeywordSearchSql(sql, searchModel, "IUSER");

        } else {
            //氏名 姓
            String sei = searchModel.getSei();
            if (!StringUtil.isNullZeroString(sei)) {
                sql.addSql(" and");
                sql.addSql("       IUSER.USI_SEI like '%"
                        + JDBCUtil.escapeForLikeSearch(sei)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            //氏名 名
            String mei = searchModel.getMei();
            if (!StringUtil.isNullZeroString(mei)) {
                sql.addSql(" and");
                sql.addSql("       IUSER.USI_MEI like '%"
                        + JDBCUtil.escapeForLikeSearch(mei)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            //氏名 セイ
            String seiKn = searchModel.getSeikn();
            if (!StringUtil.isNullZeroString(seiKn)) {
                sql.addSql(" and");
                sql.addSql("       IUSER.USI_SEI_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(seiKn)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            //氏名 メイ
            String meiKn = searchModel.getMeikn();
            if (!StringUtil.isNullZeroString(meiKn)) {
                sql.addSql(" and");
                sql.addSql("       IUSER.USI_MEI_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(meiKn)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            //社員/職員番号
            String syainno = searchModel.getShainno();
            if (!StringUtil.isNullZeroString(syainno)) {
                sql.addSql(" and");
                sql.addSql("       IUSER.USI_SYAIN_NO like '%"
                        + JDBCUtil.escapeForLikeSearch(syainno)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            //メールアドレス
            String mail = searchModel.getMail();
            if (!StringUtil.isNullZeroString(mail)) {
                sql.addSql(" and");
                sql.addSql("       (IUSER.USI_MAIL1 like '%"
                        + JDBCUtil.escapeForLikeSearch(mail)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("     or");
                sql.addSql("       IUSER.USI_MAIL2 like '%"
                        + JDBCUtil.escapeForLikeSearch(mail)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("     or");
                sql.addSql("       IUSER.USI_MAIL3 like '%"
                        + JDBCUtil.escapeForLikeSearch(mail)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("   )");
            }
        }


        //性別
        String seibetu = searchModel.getSeibetu();
        if (!StringUtil.isNullZeroString(seibetu)) {
            int seibetuId = NullDefault.getInt(seibetu, 0);
            if (seibetuId >= 0) {
                sql.addSql(" and");
                sql.addSql("   IUSER.USI_SEIBETU=?");
                sql.addIntValue(seibetuId);
            }
        }


        //役職
        int yakushoku = searchModel.getYakushoku();
        if (yakushoku != GSConstCommon.NUM_INIT) {
            sql.addSql(" and");
            sql.addSql("   IUSER.POS_SID = ?");
            sql.addIntValue(yakushoku);
        }


        //都道府県
        String tdfk = searchModel.getTdfkCd();
        if (!StringUtil.isNullZeroString(tdfk)) {
            int tdfsid = NullDefault.getInt(tdfk, 0);
            if (tdfsid > 0) {
                sql.addSql(" and");
                sql.addSql("   IUSER.TDF_SID=?");
                sql.addIntValue(tdfsid);
            }
        }

        //ラベル
        String[] label = searchModel.getLabelSid();
        if (label != null && label.length != 0) {
            sql.addSql(" and");
            sql.addSql("   exists (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM_LABEL");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_LABEL.USR_SID = IUSER.USR_SID");
            sql.addSql("     and");
            if (label.length >= 2) {
                sql.addSql("     LAB_SID in ( ?");
                sql.addIntValue(Integer.parseInt(label[0]));
                for (int i = 1; i < label.length; i++) {
                    sql.addSql(",?");
                    sql.addIntValue(Integer.parseInt(label[i]));
                }
                sql.addSql("       )");
            } else {
                sql.addSql("       LAB_SID=? ");
                sql.addIntValue(Integer.parseInt(label[0]));
            }
            sql.addSql("       )");
        }


        //年齢
        String ageFrom = searchModel.getAgefrom();
        String ageTo = searchModel.getAgeto();
        if (!StringUtil.isNullZeroString(ageFrom)
                && StringUtil.isNullZeroString(ageTo)) {
            //Fromのみ入力の場合
            int iage = NullDefault.getInt(ageFrom, 0);
            UDate bdate = new UDate();
            log__.debug("iage = " + iage);
            //基準年を作成
            bdate.addYear(-iage);
            bdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE <= ?");
            sql.addDateValue(bdate);
        } else if (StringUtil.isNullZeroString(ageFrom)
                && !StringUtil.isNullZeroString(ageTo)) {
            //Toのみ入力の場合
            int iage = NullDefault.getInt(ageTo, 0);
            iage = iage + 1;
            UDate bdate = new UDate();
            log__.debug("iage = " + iage);
            bdate.addYear(-iage);
            bdate.addDay(1);
            bdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE >= ?");
            sql.addDateValue(bdate);
        } else if (!StringUtil.isNullZeroString(ageFrom)
                && !StringUtil.isNullZeroString(ageTo)) {
            //両方入力の場合
            //FROM
            int fage = NullDefault.getInt(ageFrom, 0);
            UDate fdate = new UDate();
            fdate.addYear(-fage);
            fdate.setZeroHhMmSs();

            //TO
            int tage = NullDefault.getInt(ageTo, 0);
            tage = tage + 1;
            UDate tdate = new UDate();
            tdate.addYear(-tage);
            tdate.addDay(1);
            tdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE");
            sql.addSql("     between");
            sql.addSql("       ?");
            sql.addSql("     and");
            sql.addSql("       ?");

            sql.addDateValue(tdate);
            sql.addDateValue(fdate);
        }

        //入社年月日
        UDate entranceFrom = searchModel.getEntranceDateFr();
        UDate entranceTo = searchModel.getEntranceDateTo();
        if (entranceFrom == null
                && entranceTo != null) {
            //toのみ入力の場合
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_ENTRANCE_DATE <= ?");
            sql.addDateValue(entranceTo);
        } else if (entranceFrom != null
                && entranceTo == null) {
            //Fromのみ入力の場合
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_ENTRANCE_DATE >= ?");
            sql.addDateValue(entranceFrom);
        } else if (entranceFrom != null
                && entranceTo != null) {
            //両方入力の場合
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_ENTRANCE_DATE");
            sql.addSql("     between");
            sql.addSql("       ?");
            sql.addSql("     and");
            sql.addSql("       ?");

            sql.addDateValue(entranceFrom);
            sql.addDateValue(entranceTo);
        }


        //所属グループ
        int gsid = searchModel.getSelectgsid();
        if (gsid >= 0) {
            sql.addSql(" and");
            sql.addSql("   BELONG.GRP_SID=?");
            sql.addIntValue(gsid);
        }

        sql.addSql(" group by");
        sql.addSql("   IUSER.USR_SID");

        sql.addSql(" ) as SIDLIST , CMN_USRM_INF, CMN_USRM");
        sql.addSql(" where SIDLIST.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql(" and SIDLIST.USR_SID = CMN_USRM.USR_SID");

        //ソート(selectの場合のみ)
        if (sqlType == true) {
            //第一ソート
            String orderStr = "";
            int orderKey = searchModel.getSortOrder();
            //オーダー
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else {
                orderStr = "  desc";
            }

            sql.addSql(" order by");
            int sortKey = searchModel.getSortKey();
            log__.debug("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr);
                break;
                //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr);
                break;
                //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("  YAKUSYOKU_EXIST");
                sql.addSql(orderStr);
                sql.addSql("  ,");
                sql.addSql("  YAKUSYOKU_SORT");
                sql.addSql(orderStr);
                break;
                //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr);
                break;
                //ソートキー1
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr);
                break;
                //ソートキー2
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr);
                break;
            default:
                break;
            }

            sql.addSql("   ,");

            //第二ソート
            String orderStr2 = "";
            int orderKey2 = searchModel.getSortOrder2();
            //オーダー
            if (orderKey2 == GSConst.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else {
                orderStr2 = "  desc";
            }

            int sortKey2 = searchModel.getSortKey2();
            log__.debug("sortkey2 = " + sortKey2);
            //ソートカラム
            switch (sortKey2) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr2);
                break;
                //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("  YAKUSYOKU_EXIST");
                sql.addSql(orderStr2);
                sql.addSql("  ,");
                sql.addSql("  YAKUSYOKU_SORT");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //ソートキー1
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //ソートキー2
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
            default:
                break;
            }
        }
        log__.info(sql.toLogString());
        return sql;
    }


    /**
     * <p>ユーザ選択POPUPの検索SQLを取得する
     * @param filterUser フィルタ->ユーザ
     * @param filterPost フィルタ->役職
     * @param filterLabel フィルタ->ラベル
     * @return ユーザSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getMultiselectorTargetUser(List<String> filterUser,
            List<Integer> filterPost, List<Integer> filterLabel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        //SQL文
        SqlBuffer sql = new SqlBuffer();

        sql.addSql(" select");
        sql.addSql("   CMN_USRM_INF.USR_SID");
        sql.addSql(" from ");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql("   left join");
        sql.addSql("     CMN_USRM");
        sql.addSql("   on");
        sql.addSql("     CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_USRM_LABEL");
        sql.addSql("   on");
        sql.addSql("     CMN_USRM_INF.USR_SID = CMN_USRM_LABEL.USR_SID");
        sql.addSql(" where");
        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM_INF.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_JKBN = ?");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

        //キーワード検索
        if (filterUser.size() > 0) {
            sql.addSql(" and");
            sql.addSql(" (");
            for (int i = 0; i < filterUser.size(); i++) {
                if (i > 0) {
                    sql.addSql(" and");
                }
                sql.addSql("   (");
                //氏名 姓
                sql.addSql("   CMN_USRM_INF.USI_SEI like '%"
                        + JDBCUtil.escapeForLikeSearch(filterUser.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("   or");

                //氏名 名
                sql.addSql("   CMN_USRM_INF.USI_MEI like '%"
                        + JDBCUtil.escapeForLikeSearch(filterUser.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("   or");

                //氏名 姓カナ
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(filterUser.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("   or");

                //氏名 名カナ
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(filterUser.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("   or");

                //社員/職員番号
                sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO like '%"
                        + JDBCUtil.escapeForLikeSearch(filterUser.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql("   )");
            }
            sql.addSql(" )");
        }
        if (filterPost.size() > 0) {
            sql.addSql(" and");
            sql.addSql(" (");
            for (int i = 0; i < filterPost.size(); i++) {
                if (i > 0) {
                    sql.addSql(" and");
                }
                sql.addSql("   (");
                //役職
                sql.addSql("   CMN_USRM_INF.POS_SID = ?");
                sql.addIntValue(filterPost.get(i));
                sql.addSql("   )");
            }
            sql.addSql(" )");
        }
        if (filterLabel.size() > 0) {
            sql.addSql(" and");
            sql.addSql(" (");
            for (int i = 0; i < filterLabel.size(); i++) {
                if (i > 0) {
                    sql.addSql(" and");
                }
                sql.addSql("   (");
                //役職
                sql.addSql("   CMN_USRM_LABEL.LAB_SID = ?");
                sql.addIntValue(filterLabel.get(i));
                sql.addSql("   )");
            }
            sql.addSql(" )");
        }

        pstmt = con.prepareStatement(sql.toSqlString());
        sql.setParameter(pstmt);
        log__.info(sql.toLogString());
        rs = pstmt.executeQuery();
        while (rs.next()) {
            ret.add(rs.getInt("USR_SID"));
        }
        return ret;
    }

    /**
     * <p>結果セットからBaseUserModelを取得する。
     * @param rs 結果セット
     * @return BaseUserModel
     * @throws SQLException SQL実行例外
     */
    public static BaseUserModel getBaseUserModelFromRs(ResultSet rs) throws SQLException {
        BaseUserModel ret = new BaseUserModel();
        //ユーザID
        ret.setUsrsid(rs.getInt("USR_SID"));
        //ログイン停止フラグ
        ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        //姓
        ret.setUsisei(NullDefault.getString(rs.getString("USI_SEI"), ""));
        //名
        ret.setUsimei(NullDefault.getString(rs.getString("USI_MEI"), ""));
        return ret;
    }

    /**
     * <p>結果セットからCmnUsrmInfModelを取得する。
     * @param rs 結果セット
     * @return CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public static CmnUsrmInfModel getCmnUsrmInfModelFromRs(ResultSet rs) throws SQLException {
        CmnUsrmInfModel bean = new CmnUsrmInfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
                UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));
        return bean;
    }

    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public static CmnUsrmInfModel getCmnUsrmInfModelFromRs2(ResultSet rs) throws SQLException {
        CmnUsrmInfModel bean = getCmnUsrmInfModelFromRs(rs);
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setPosSid(rs.getInt("POS_SID"));
        bean.setUsiTelNai1(rs.getString("USI_TEL_NAI1"));
        bean.setUsiTelCmt1(rs.getString("USI_TEL_CMT1"));
        bean.setUsiTelNai2(rs.getString("USI_TEL_NAI2"));
        bean.setUsiTelCmt2(rs.getString("USI_TEL_CMT2"));
        bean.setUsiTelNai3(rs.getString("USI_TEL_NAI3"));
        bean.setUsiTelCmt3(rs.getString("USI_TEL_CMT3"));
        bean.setUsiFaxCmt1(rs.getString("USI_FAX_CMT1"));
        bean.setUsiFaxCmt2(rs.getString("USI_FAX_CMT2"));
        bean.setUsiFaxCmt3(rs.getString("USI_FAX_CMT3"));
        bean.setUsiMailCmt1(rs.getString("USI_MAIL_CMT1"));
        bean.setUsiMailCmt2(rs.getString("USI_MAIL_CMT2"));
        bean.setUsiMailCmt3(rs.getString("USI_MAIL_CMT3"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsiPictKf(rs.getInt("USI_PICT_KF"));
        bean.setUsiBdateKf(rs.getInt("USI_BDATE_KF"));
        bean.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
        bean.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
        bean.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
        bean.setUsiZipKf(rs.getInt("USI_ZIP_KF"));
        bean.setUsiTdfKf(rs.getInt("USI_TDF_KF"));
        bean.setUsiAddr1Kf(rs.getInt("USI_ADDR1_KF"));
        bean.setUsiAddr2Kf(rs.getInt("USI_ADDR2_KF"));
        bean.setUsiTel1Kf(rs.getInt("USI_TEL1_KF"));
        bean.setUsiTel2Kf(rs.getInt("USI_TEL2_KF"));
        bean.setUsiTel3Kf(rs.getInt("USI_TEL3_KF"));
        bean.setUsiFax1Kf(rs.getInt("USI_FAX1_KF"));
        bean.setUsiFax2Kf(rs.getInt("USI_FAX2_KF"));
        bean.setUsiFax3Kf(rs.getInt("USI_FAX3_KF"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));
        return bean;
    }
    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * <br>[備  考] 公開フラグを判定し、非公開情報をnullにする。
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public static CmnUsrmInfModel getCmnUsrmInfFromRsKoukai(ResultSet rs) throws SQLException {
        CmnUsrmInfModel model = CmnUsrmInfDao.getCmnUsrmInfFromRsKoukai(rs);
        model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return model;
    }
    /**
     * <p>結果セットからUserSearchModelを取得する。(バイナリー情報を含む）
     * @param rs 結果セット
     * @return CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public static UserSearchModel getUserSearchModelBinFromRs(ResultSet rs) throws SQLException {
        UserSearchModel bean = new UserSearchModel();
        bean.setUsrJkbn(rs.getInt("USR_JKBN"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
                UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));
        bean.setUioStatus(rs.getInt("UIO_STATUS"));
        bean.setUioComment(rs.getString("UIO_BIKO"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
        bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        bean.setUsiPictKf(rs.getInt("USI_PICT_KF"));
        return bean;
    }

    /**
     * <p>結果セットからUserSearchModelを取得する。
     * @param rs 結果セット
     * @return CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public static UserSearchModel getUserSearchModelFromRs(ResultSet rs) throws SQLException {
        UserSearchModel bean = new UserSearchModel();
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
                UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));
        bean.setUioStatus(rs.getInt("UIO_STATUS"));
        bean.setUioComment(rs.getString("UIO_BIKO"));
        return bean;
    }

    /**
     * <p>結果セットからUserCsvModelを取得する。
     * @param rs 結果セット
     * @param rl UsrCsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setUsrCsvRecordFromRs(ResultSet rs, UsrCsvRecordListenerImpl rl)
            throws SQLException, CSVException {

        UserCsvModel bean = new UserCsvModel();
        bean.setUsrLgid(rs.getString("USR_LGID"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
                UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiBdateKf(rs.getInt("USI_BDATE_KF"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMailCmt1(rs.getString("USI_MAIL_CMT1"));
        bean.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMailCmt2(rs.getString("USI_MAIL_CMT2"));
        bean.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiMailCmt3(rs.getString("USI_MAIL_CMT3"));
        bean.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setUsiZipKf(rs.getInt("USI_ZIP_KF"));
        if (rs.getInt("TDF_SID") >= 0) {
            bean.setTdfSid(rs.getInt("TDF_SID"));
        } else {
            bean.setTdfSid(0);
        }
        bean.setUsiTdfKf(rs.getInt("USI_TDF_KF"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr1Kf(rs.getInt("USI_ADDR1_KF"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiAddr2Kf(rs.getInt("USI_ADDR2_KF"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTelNai1(rs.getString("USI_TEL_NAI1"));
        bean.setUsiTelCmt1(rs.getString("USI_TEL_CMT1"));
        bean.setUsiTel1Kf(rs.getInt("USI_TEL1_KF"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTelNai2(rs.getString("USI_TEL_NAI2"));
        bean.setUsiTelCmt2(rs.getString("USI_TEL_CMT2"));
        bean.setUsiTel2Kf(rs.getInt("USI_TEL2_KF"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiTelNai3(rs.getString("USI_TEL_NAI3"));
        bean.setUsiTelCmt3(rs.getString("USI_TEL_CMT3"));
        bean.setUsiTel3Kf(rs.getInt("USI_TEL3_KF"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFaxCmt1(rs.getString("USI_FAX_CMT1"));
        bean.setUsiFax1Kf(rs.getInt("USI_FAX1_KF"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFaxCmt2(rs.getString("USI_FAX_CMT2"));
        bean.setUsiFax2Kf(rs.getInt("USI_FAX2_KF"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiFaxCmt3(rs.getString("USI_FAX_CMT3"));
        bean.setUsiFax3Kf(rs.getInt("USI_FAX3_KF"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setUsiOtpsendAddress(rs.getString("USI_OTPSEND_ADDRESS"));
        rl.setRecord(bean);
    }

    /**
     * <p>CmnUsrmInfModelからUserCsvModelを作成し、UsrCsvRecordListenerImplにセットする。
     * @param model CmnUsrmInfModel
     * @param rl UsrCsvRecordListenerIppanImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setUsrCsvRecordFromCmnUsrmInfModel(
            CmnUsrmInfModel model, UsrCsvRecordListenerIppanImpl rl)
                    throws SQLException, CSVException {

        UserCsvModel bean = new UserCsvModel();
        bean.setUsiSyainNo(model.getUsiSyainNo());
        bean.setUsiSei(model.getUsiSei());
        bean.setUsiMei(model.getUsiMei());
        bean.setUsiSeiKn(model.getUsiSeiKn());
        bean.setUsiMeiKn(model.getUsiMeiKn());
        bean.setUsiSyozoku(model.getUsiSyozoku());
        bean.setUsiYakusyoku(model.getUsiYakusyoku());
        bean.setUsiSeibetu(model.getUsiSeibetu());
        bean.setUsiEntranceDate(model.getUsiEntranceDate());
        bean.setUsiBdate(model.getUsiBdate());
        bean.setUsiBdateKf(model.getUsiBdateKf());
        bean.setUsiMail1(model.getUsiMail1());
        bean.setUsiMailCmt1(model.getUsiMailCmt1());
        bean.setUsiMail1Kf(model.getUsiMail1Kf());
        bean.setUsiMail2(model.getUsiMail2());
        bean.setUsiMailCmt2(model.getUsiMailCmt2());
        bean.setUsiMail2Kf(model.getUsiMail2Kf());
        bean.setUsiMail3(model.getUsiMail3());
        bean.setUsiMailCmt3(model.getUsiMailCmt3());
        bean.setUsiMail3Kf(model.getUsiMail3Kf());
        bean.setUsiZip1(model.getUsiZip1());
        bean.setUsiZip2(model.getUsiZip2());
        bean.setUsiZipKf(model.getUsiZipKf());
        bean.setTdfSid(model.getTdfSid());
        bean.setUsiTdfKf(model.getUsiTdfKf());
        bean.setUsiAddr1(model.getUsiAddr1());
        bean.setUsiAddr1Kf(model.getUsiAddr1Kf());
        bean.setUsiAddr2(model.getUsiAddr2());
        bean.setUsiAddr2Kf(model.getUsiAddr2Kf());
        bean.setUsiTel1(model.getUsiTel1());
        bean.setUsiTelNai1(model.getUsiTelNai1());
        bean.setUsiTelCmt1(model.getUsiTelCmt1());
        bean.setUsiTel1Kf(model.getUsiTel1Kf());
        bean.setUsiTel2(model.getUsiTel2());
        bean.setUsiTelNai2(model.getUsiTelNai2());
        bean.setUsiTelCmt2(model.getUsiTelCmt2());
        bean.setUsiTel2Kf(model.getUsiTel2Kf());
        bean.setUsiTel3(model.getUsiTel3());
        bean.setUsiTelNai3(model.getUsiTelNai3());
        bean.setUsiTelCmt3(model.getUsiTelCmt3());
        bean.setUsiTel3Kf(model.getUsiTel3Kf());
        bean.setUsiFax1(model.getUsiFax1());
        bean.setUsiFaxCmt1(model.getUsiFaxCmt1());
        bean.setUsiFax1Kf(model.getUsiFax1Kf());
        bean.setUsiFax2(model.getUsiFax2());
        bean.setUsiFaxCmt2(model.getUsiFaxCmt2());
        bean.setUsiFax2Kf(model.getUsiFax2Kf());
        bean.setUsiFax3(model.getUsiFax3());
        bean.setUsiFaxCmt3(model.getUsiFaxCmt3());
        bean.setUsiFax3Kf(model.getUsiFax3Kf());
        bean.setUsiBiko(model.getUsiBiko());
        rl.setRecord(bean);
    }
    /**
     * <p>ログインIDを元にユーザSIDを取得する
     * @param lgid ユーザID(ログインＩＤ)
     * @throws SQLException SQL実行例外
     * @return SID
     */
    public int selectLoginId(String lgid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_LGID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(lgid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("USR_SID");
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
     * <p>ログインIDを元にユーザ情報を取得する
     * @param lgid ユーザID(ログインＩＤ)
     * @throws SQLException SQL実行例外
     * @return ユーザ情報
     */
    public CmnUsrmInfModel getUsrData(String lgid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  CMN_USRM_INF.USR_SID,");
            sql.addSql("  CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("  CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_LGID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(lgid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setUsiSei(rs.getString("USI_SEI"));
                ret.setUsiMei(rs.getString("USI_MEI"));
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
     * <p>指定されたユーザ情報に付与されているラベルの名称を設定する
     * @param ret ユーザ情報一覧
     * @throws SQLException SQL実行例外
     */
    public void getLabelName(ArrayList<CmnUsrmInfModel> ret) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        //ユーザ情報に付与されているラベルの名称を取得する
        if (!ret.isEmpty()) {
            try {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   CMN_LABEL_USR.LAB_NAME as LAB_NAME");
                sql.addSql(" from");
                sql.addSql("   CMN_LABEL_USR,");
                sql.addSql("   CMN_USRM_LABEL");
                sql.addSql(" where");
                sql.addSql("   CMN_USRM_LABEL.USR_SID = ?");
                sql.addSql(" and");
                sql.addSql("   CMN_USRM_LABEL.LAB_SID = CMN_LABEL_USR.LAB_SID");
                sql.addSql(" order by");
                sql.addSql("   CMN_LABEL_USR.LAB_SID");

                pstmt = con.prepareStatement(sql.toSqlString());

                for (int idx = 0; idx < ret.size(); idx++) {
                    int usrSid = ret.get(idx).getUsrSid();
                    sql.clearValue();
                    sql.addIntValue(usrSid);
                    sql.toLogString();

                    List<String> labelNameList = new ArrayList<String>();
                    pstmt.setInt(1, usrSid);

                    JDBCUtil.closeResultSet(rs);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        labelNameList.add(rs.getString("LAB_NAME"));
                    }
                    ret.get(idx).setLabelNameList(labelNameList);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        }
    }
    /**
     * <p>詳細検索(カウント)
     * @param qmdl 検索条件
     * @return 検索条件に該当する件数
     * @throws SQLException SQL実行例外
     */
    public int getSyousaiSearchCount(UserQueryModel qmdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT from (");

            __writeSyousaiSearchSql(sql, qmdl);

            sql.addSql(" ) as SIDLIST , CMN_USRM_INF, CMN_USRM");
            sql.addSql(" where SIDLIST.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and SIDLIST.USR_SID = CMN_USRM.USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>詳細検索
     * @param qmdl 検索条件
     * @return 検索結果
     * @throws SQLException SQL実行例外
     */
    public List<CmnUsrmInfModel> getSyousaiSearchList(UserQueryModel qmdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE,");
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

            sql.addSql(" from (");

            __writeSyousaiSearchSql(sql, qmdl);

            sql.addSql(" ) as SIDLIST , CMN_USRM_INF, CMN_USRM");
            sql.addSql(" where SIDLIST.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and SIDLIST.USR_SID = CMN_USRM.USR_SID");

            //ソート(selectの場合のみ)
            //第一ソート
            String orderStr = "";
            int orderKey = qmdl.getSortOrderFlg();
            //オーダー
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else {
                orderStr = "  desc";
            }

            sql.addSql(" order by");
            int sortKey = qmdl.getSortKeyType();
            log__.debug("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr);
                break;
                //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr);
                break;
                //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("  YAKUSYOKU_EXIST");
                sql.addSql(orderStr);
                sql.addSql("  ,");
                sql.addSql("  YAKUSYOKU_SORT");
                sql.addSql(orderStr);
                break;
                //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr);
                break;
                //ソートキー1
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr);
                break;
                //ソートキー2
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr);
                break;
            default:
                break;
            }

            sql.addSql("   ,");

            //第二ソート
            String orderStr2 = "";
            int orderKey2 = qmdl.getSortOrder2Flg();
            //オーダー
            if (orderKey2 == GSConst.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else {
                orderStr2 = "  desc";
            }

            int sortKey2 = qmdl.getSortKey2Type();
            log__.debug("sortkey2 = " + sortKey2);
            //ソートカラム
            switch (sortKey2) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr2);
                break;
                //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("  YAKUSYOKU_EXIST");
                sql.addSql(orderStr2);
                sql.addSql("  ,");
                sql.addSql("  YAKUSYOKU_SORT");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //ソートキー1
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
                //ソートキー2
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr2);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql("    asc");
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql("    asc");
                break;
            default:
                break;
            }
            if (qmdl.getLimit() != null) {
                sql.setPagingValue(qmdl.getOffset(), qmdl.getLimit());
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(getCmnUsrmInfFromRsKoukai(rs));
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
     *
     * <br>[機  能] ユーザ検索 絞り込み条件部を行う
     * <br>[解  説] 絞り込み条件部の記述を共通化する
     * <br>[備  考]
     * @param sql 書き込み対象
     * @param qmdl 検索モデル
     */
    private void __writeSyousaiSearchSql(SqlBuffer sql, UserQueryModel qmdl) {
        sql.addSql(" select");
        sql.addSql("   IUSER.USR_SID");
        sql.addSql(" from");
        sql.addSql("   (");
        sql.addSql("   select");
        sql.addSql("     CMN_USRM.USR_LGID as USR_LGID,");
        sql.addSql("     CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("     CMN_USRM_INF.USR_SID as USR_SID,");
        sql.addSql("     CMN_USRM_INF.USI_SEI as USI_SEI,");
        sql.addSql("     CMN_USRM_INF.USI_MEI as USI_MEI,");
        sql.addSql("     CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("     CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("     CMN_USRM_INF.USI_SINI as USI_SINI,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_BDATE_KF=0 then CMN_USRM_INF.USI_BDATE");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_BDATE,");
        sql.addSql("     CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
        sql.addSql("     CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_TDF_KF=0 then CMN_USRM_INF.TDF_SID");
        sql.addSql("     else");
        sql.addSql("       0");
        sql.addSql("     end TDF_SID,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_ADDR1_KF=0 then CMN_USRM_INF.USI_ADDR1");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_ADDR1,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_ADDR2_KF=0 then CMN_USRM_INF.USI_ADDR2");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_ADDR2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_TEL1_KF=0 then CMN_USRM_INF.USI_TEL1");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_TEL1,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_TEL2_KF=0 then CMN_USRM_INF.USI_TEL2");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_TEL2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_TEL3_KF=0 then CMN_USRM_INF.USI_TEL3");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_TEL3,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_FAX1_KF=0 then CMN_USRM_INF.USI_FAX1");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_FAX1,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_FAX2_KF=0 then CMN_USRM_INF.USI_FAX2");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_FAX2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_FAX3_KF=0 then CMN_USRM_INF.USI_FAX3");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_FAX3,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL1_KF=0 then CMN_USRM_INF.USI_MAIL1");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL1,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL2_KF=0 then CMN_USRM_INF.USI_MAIL2");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL3_KF=0 then CMN_USRM_INF.USI_MAIL3");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL3,");
        sql.addSql("     CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
        sql.addSql("     CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
        sql.addSql("     CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
        sql.addSql("     CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
        sql.addSql("     CMN_USRM_INF.POS_SID as POS_SID,");
        sql.addSql("     CMN_USRM_INF.USI_BIKO as USI_BIKO,");
        sql.addSql("     CMN_USRM_INF.BIN_SID as BIN_SID");
        sql.addSql("   from");
        sql.addSql("     CMN_USRM");
        sql.addSql("     left join");
        sql.addSql("        CMN_USRM_INF ");
        sql.addSql("     on");
        sql.addSql("        CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
        sql.addSql("   where ");
        sql.addSql("     CMN_USRM.USR_JKBN=0");
        sql.addSql("  ) as IUSER");

        //グループID検索
        if (qmdl.getGroupId() != null || qmdl.getGroupSid() != null) {
            sql.addSql("   inner join (");
            sql.addSql("     select");
            sql.addSql("       CMN_GROUPM.GRP_ID,");
            sql.addSql("       CMN_GROUPM.GRP_SID,");
            sql.addSql("       CMN_BELONGM.USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_GROUPM,");
            sql.addSql("       CMN_BELONGM");
            sql.addSql("     where ");
            sql.addSql("       CMN_GROUPM.GRP_SID=CMN_BELONGM.GRP_SID");
            sql.addSql("     and");
            sql.addSql("       CMN_GROUPM.GRP_JKBN=0");
            if (qmdl.getGroupId() != null) {
                sql.addSql(" and");
                sql.addSql("   CMN_GROUPM.GRP_ID=?");
                sql.addStrValue(qmdl.getGroupId());
            }
            if (qmdl.getGroupSid() != null) {
                sql.addSql(" and");
                sql.addSql("   CMN_GROUPM.GRP_SID=?");
                sql.addIntValue(qmdl.getGroupSid());
            }
            sql.addSql("   ) as BELONG");
            sql.addSql("   on IUSER.USR_SID=BELONG.USR_SID");

        }

        //マイグループSID検索
        if (qmdl.getMyGroupSid() != null) {
            sql.addSql("   inner join (");
            sql.addSql("     select");
            sql.addSql("       CMN_MY_GROUP_MS.MGP_SID,");
            sql.addSql("       CMN_MY_GROUP_MS.MGM_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_MY_GROUP_MS");
            sql.addSql("     where CMN_MY_GROUP_MS.MGP_SID = ?");
            sql.addSql("   ) as BELONG_MYG");
            sql.addSql("   on IUSER.USR_SID=BELONG_MYG.MGM_SID");
            sql.addIntValue(qmdl.getMyGroupSid());

        }
        sql.addSql(" where");

        //ユーザSID < 100は除外
        sql.addSql("   IUSER.USR_SID > ?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);

        //カナ順取得開始位置
        String kanaStartText = qmdl.getKanaStartOffsetText();
        if (!StringUtil.isNullZeroString(kanaStartText)) {
            //ソート1キー = 氏名 の場合のみ、有効とする
            if (qmdl.getSortKeyType() == GSConstUser.USER_SORT_NAME) {
                sql.addSql(" and");

                if (qmdl.getSortOrderFlg() == GSConst.ORDER_KEY_ASC) {
                    //ソート1昇順降順が昇順の場合、指定カナ以降のカナで始まるデータを検索する
                    sql.addSql("       IUSER.USI_SINI >= ?");
                } else {
                    //ソート1昇順降順が降順の場合、指定カナより前のカナで始まるデータを検索する
                    sql.addSql("       IUSER.USI_SINI < ?");
                }
                sql.addStrValue(kanaStartText);
            }
        }

        //イニシャル検索
        if (!StringUtil.isNullZeroString(qmdl.getSeimeiKnStartText())) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SINI = ?");
            sql.addStrValue(qmdl.getSeimeiKnStartText());
        }

        //キーワード検索
        if (!StringUtil.isNullZeroString(qmdl.getKeywordText())) {
            sql.addSql(" and");
            __setKeywordSearchSql(sql, qmdl.getKeywordText(), "IUSER");
        }

        //キーワード区分 社員/職員番号
        if (!StringUtil.isNullZeroString(qmdl.getShainNoText())) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SYAIN_NO like '%"
                    + JDBCUtil.escapeForLikeSearch(qmdl.getShainNoText())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //キーワード区分 氏名 (姓＋名)
        if (!StringUtil.isNullZeroString(qmdl.getSeimeiText())) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SEI || IUSER.USI_MEI like '%"
                    + JDBCUtil.escapeForLikeSearch(qmdl.getSeimeiText())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }
        //キーワード区分 氏名カナ
        if (!StringUtil.isNullZeroString(qmdl.getSeimeiKnText())) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SEI_KN || IUSER.USI_MEI_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(qmdl.getSeimeiKnText())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //キーワード区分 E-MAIL
        if (!StringUtil.isNullZeroString(qmdl.getMailText())) {
            sql.addSql(" and");
            sql.addSql("       (IUSER.USI_MAIL1 like '%"
                    + JDBCUtil.escapeForLikeSearch(qmdl.getMailText())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("     or");
            sql.addSql("       IUSER.USI_MAIL2 like '%"
                    + JDBCUtil.escapeForLikeSearch(qmdl.getMailText())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("     or");
            sql.addSql("       IUSER.USI_MAIL3 like '%"
                    + JDBCUtil.escapeForLikeSearch(qmdl.getMailText())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   )");
        }

        //キーワード区分 電話番号
        if (!StringUtil.isNullZeroString(qmdl.getTelText())) {
            String keyWordTel = qmdl.getTelText().replace("-", "");
            sql.addSql(" and");
            sql.addSql("      (replace(IUSER.USI_TEL1, '-','') like '%"
                    + JDBCUtil.escapeForLikeSearch(keyWordTel)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("     or");
            sql.addSql("      replace(IUSER.USI_TEL2, '-','') like '%"
                    + JDBCUtil.escapeForLikeSearch(keyWordTel)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("     or");
            sql.addSql("      replace(IUSER.USI_TEL3, '-','') like '%"
                    + JDBCUtil.escapeForLikeSearch(keyWordTel)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   )");
        }


        //性別
        if (qmdl.getSeibetuType() != EnumSeibetu.ALL) {
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_SEIBETU = ?");
            sql.addIntValue(qmdl.getSeibetuType().getValue());
        }


        //役職
        if (qmdl.getPostsSid() != null) {
            sql.addSql(" and");
            sql.addSql("   IUSER.POS_SID = ?");
            sql.addIntValue(qmdl.getPostsSid());
        }

        //都道府県SID
        if (qmdl.getTodofukenSid() != null) {
            int tdfsid = qmdl.getTodofukenSid();
            if (tdfsid > 0) {
                sql.addSql(" and");
                sql.addSql("   IUSER.TDF_SID=?");
                sql.addIntValue(tdfsid);
            }
        }

        //ラベル
        int[] label = qmdl.getLabelSidArray();
        if (label != null && label.length != 0) {
            sql.addSql(" and");
            sql.addSql("   exists (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM_LABEL");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_LABEL.USR_SID = IUSER.USR_SID");
            sql.addSql("     and");
            if (label.length >= 2) {
                sql.addSql("     LAB_SID in ( ?");
                sql.addIntValue(label[0]);
                for (int i = 1; i < label.length; i++) {
                    sql.addSql(",?");
                    sql.addIntValue(label[i]);
                }
                sql.addSql("       )");
            } else {
                sql.addSql("       LAB_SID=? ");
                sql.addIntValue(label[0]);
            }
            sql.addSql("       )");
        }


        //年齢
        Integer ageFrom = qmdl.getAgeFromNum();
        Integer ageTo = qmdl.getAgeToNum();
        if (ageFrom != null
                && ageTo == null) {
            //Fromのみ入力の場合
            UDate bdate = new UDate();
            log__.debug("iage = " + ageFrom);
            //基準年を作成
            bdate.addYear(-ageFrom);
            bdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE <= ?");
            sql.addDateValue(bdate);
        } else if (ageFrom == null
                    && ageTo != null) {
            //Toのみ入力の場合
            ageTo = ageTo + 1;
            UDate bdate = new UDate();
            log__.debug("iage = " + ageTo);
            bdate.addYear(-ageTo);
            bdate.addDay(1);
            bdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE >= ?");
            sql.addDateValue(bdate);
        } else if (ageFrom != null
                && ageTo != null) {
            //両方入力の場合
            //FROM
            int fage = ageFrom;
            UDate fdate = new UDate();
            fdate.addYear(-fage);
            fdate.setZeroHhMmSs();

            //TO
            int tage = ageTo;
            tage = tage + 1;
            UDate tdate = new UDate();
            tdate.addYear(-tage);
            tdate.addDay(1);
            tdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE");
            sql.addSql("     between");
            sql.addSql("       ?");
            sql.addSql("     and");
            sql.addSql("       ?");

            sql.addDateValue(tdate);
            sql.addDateValue(fdate);
        }

        //入社年月日
        UDate entranceFrom = qmdl.getEntranceFromDate();
        UDate entranceTo = qmdl.getEntranceToDate();
        if (entranceFrom == null
                && entranceTo != null) {
            //toのみ入力の場合
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_ENTRANCE_DATE <= ?");
            sql.addDateValue(entranceTo);
        } else if (entranceFrom != null
                && entranceTo == null) {
            //Fromのみ入力の場合
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_ENTRANCE_DATE >= ?");
            sql.addDateValue(entranceFrom);
        } else if (entranceFrom != null
                && entranceTo != null) {
            //両方入力の場合
            sql.addSql(" and");
            sql.addSql("   IUSER.USI_ENTRANCE_DATE");
            sql.addSql("     between");
            sql.addSql("       ?");
            sql.addSql("     and");
            sql.addSql("       ?");

            sql.addDateValue(entranceFrom);
            sql.addDateValue(entranceTo);
        }

        sql.addSql(" group by");
        sql.addSql("   IUSER.USR_SID");
    }

    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名、画像情報を取得します。
     * <br>[備  考] 削除済みユーザも含む
     * @param usids ユーザーSID 配列
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUserModel> getUsersDataList(Collection<Integer> usids) throws SQLException {
        return getUsersDataList(usids, false);
    }

    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名、画像情報を取得します。
     * <br>[備  考] 削除済みユーザも含む
     * @param usids ユーザーSID 配列
     * @param sortFlg true: ユーザ並び順設定に従い並び替え、false: 並び替えを行わない
     * @return List in CmnUserModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUserModel> getUsersDataList(Collection<Integer> usids, boolean sortFlg)
    throws SQLException {
        return getUsersDataList(usids, sortFlg, 0, 0);
    }
    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名、画像情報を取得します。
     * <br>[備  考] 削除済みユーザも含む
     * @param usids ユーザーSID 配列
     * @param sortFlg true: ユーザ並び順設定に従い並び替え、false: 並び替えを行わない
     * @param limit 取得件数
     * @param offset 取得開始位置
     * @return List in CmnUserModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUserModel> getUsersDataList(
        Collection<Integer> usids, boolean sortFlg, int limit, int offset)
    throws SQLException {

        Connection con = null;
        List<CmnUserModel> ret = new ArrayList<>();
        if (usids == null || usids.isEmpty()) {
            return ret;
        }
        con = getCon();

        CmnCmbsortConfModel sortMdl = null;
        if (sortFlg) {
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            sortMdl = sortDao.getCmbSortData();
        }
        SqlBuffer sql = new SqlBuffer();

        sql.addSql(" select");
        //フィールド名宣言部
        if (sortFlg) {
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
            sql.addSql("    end) as YAKUSYOKU_SORT,");
        }
        sql.addSql("   CMN_USRM.USR_LGID, ");
        sql.addSql("   CMN_USRM.USR_SID, ");
        sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
        sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
        sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
        sql.addSql("   CMN_USRM_INF.USI_SEI, ");
        sql.addSql("   CMN_USRM_INF.USI_MEI, ");
        sql.addSql("   CMN_USRM_INF.USI_SEI_KN, ");
        sql.addSql("   CMN_USRM_INF.USI_MEI_KN, ");
        sql.addSql("   CMN_USRM_INF.USI_SINI, ");
        sql.addSql("   CMN_USRM_INF.USI_BDATE, ");
        sql.addSql("   CMN_USRM_INF.USI_ZIP1, ");
        sql.addSql("   CMN_USRM_INF.USI_ZIP2, ");
        sql.addSql("   CMN_USRM_INF.TDF_SID, ");
        sql.addSql("   CMN_USRM_INF.USI_ADDR1, ");
        sql.addSql("   CMN_USRM_INF.USI_ADDR2, ");
        sql.addSql("   CMN_USRM_INF.USI_TEL1, ");
        sql.addSql("   CMN_USRM_INF.USI_TEL2, ");
        sql.addSql("   CMN_USRM_INF.USI_TEL3, ");
        sql.addSql("   CMN_USRM_INF.USI_FAX1, ");
        sql.addSql("   CMN_USRM_INF.USI_FAX2, ");
        sql.addSql("   CMN_USRM_INF.USI_FAX3, ");
        sql.addSql("   CMN_USRM_INF.USI_MAIL1, ");
        sql.addSql("   CMN_USRM_INF.USI_MAIL2, ");
        sql.addSql("   CMN_USRM_INF.USI_MAIL3, ");
        sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO, ");
        sql.addSql("   CMN_USRM_INF.USI_SYOZOKU, ");
        sql.addSql("   CMN_USRM_INF.USI_SEIBETU,");
        sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
        sql.addSql("   CMN_USRM_INF.USI_SORTKEY1, ");
        sql.addSql("   CMN_USRM_INF.USI_SORTKEY2, ");
        sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU, ");
        sql.addSql("   CMN_USRM_INF.USI_BIKO, ");
        sql.addSql("   CMN_USRM_INF.USI_LTLGIN, ");
        sql.addSql("   CMN_USRM_INF.USI_AUID, ");
        sql.addSql("   CMN_USRM_INF.USI_ADATE, ");
        sql.addSql("   CMN_USRM_INF.USI_EUID, ");
        sql.addSql("   CMN_USRM_INF.USI_EDATE, ");
        sql.addSql("   CMN_USRM_INF.USI_MBL_USE ");
        sql.addSql("from");
        sql.addSql("  CMN_USRM, ");
        sql.addSql("  CMN_USRM_INF ");
        sql.addSql("where ");
        sql.addSql("  CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql("and (");

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = usids.iterator();
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }
            //500件毎に実行
            sql.addSql(" CMN_USRM.USR_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql(String.valueOf(exeItr.next()));

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            exeList.clear();

            if (itr.hasNext()) {
                sql.addSql(" or ");
            }
        }

        sql.addSql(" ) ");

        if (sortFlg) {
            sql = __createOrderSql(sql,
                                UserBiz.getSortKey(sortMdl.getCscUserSkey1()),
                                sortMdl.getCscUserOrder1(),
                                UserBiz.getSortKey(sortMdl.getCscUserSkey2()),
                                sortMdl.getCscUserOrder2());
        }
        if (limit > 0) {
            sql.setPagingValue(offset, limit);
        }
        try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    CmnUsrmInfModel model1 = getCmnUsrmInfModelFromRs(rs);
                    CmnUsrmModel model2 = new CmnUsrmModel();
                    CmnUserModel mdl = new CmnUserModel(model1, model2);
                    model1.setUsiMblUse(rs.getInt("USI_MBL_USE"));
                    model1.setUsiPictKf(rs.getInt("USI_PICT_KF"));
                    model1.setBinSid(rs.getLong("BIN_SID"));
                    ret.add(mdl);


                    model2.setUsrSid(model1.getUsrSid());
                    model2.setUsrJkbn(rs.getInt("USR_JKBN"));
                    model2.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                    model2.setUsrLgid(rs.getString("USR_LGID"));
                }

            }

        }
        return ret;
    }
    /**
     * <br>[機  能] 指定されたSID分のユーザー情報を取得します。
     * <br>[解  説] ユーザ情報とユーザの役職名を取得します。
     * <br>[備  考] 削除済みユーザは含まない
     * @param uidArr ユーザーSID 配列
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnUserModel> getUsersDataList(String[] uidArr) throws SQLException {
        Connection con = null;
        List<CmnUserModel> ret = new ArrayList<>();
        if (uidArr == null) {
            return ret;
        }
        Collection<String> uids =
                Arrays.stream(uidArr)
                    .collect(Collectors.toSet());

        if (uids == null || uids.isEmpty()) {
            return ret;
        }

        List<String> exeList = new ArrayList<>();
        Iterator<String> itr = uids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        //フィールド名宣言部
        sb.append("   CMN_USRM.USR_LGID, ");
        sb.append("   CMN_USRM.USR_SID, ");
        sb.append("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sb.append("   CMN_USRM.USR_JKBN as USR_JKBN,");
        sb.append("   CMN_USRM_INF.USI_SEI, ");
        sb.append("   CMN_USRM_INF.USI_MEI, ");
        sb.append("   CMN_USRM_INF.USI_SEI_KN, ");
        sb.append("   CMN_USRM_INF.USI_MEI_KN, ");
        sb.append("   CMN_USRM_INF.USI_SINI, ");
        sb.append("   CMN_USRM_INF.USI_BDATE, ");
        sb.append("   CMN_USRM_INF.USI_ZIP1, ");
        sb.append("   CMN_USRM_INF.USI_ZIP2, ");
        sb.append("   CMN_USRM_INF.TDF_SID, ");
        sb.append("   CMN_USRM_INF.USI_ADDR1, ");
        sb.append("   CMN_USRM_INF.USI_ADDR2, ");
        sb.append("   CMN_USRM_INF.USI_TEL1, ");
        sb.append("   CMN_USRM_INF.USI_TEL2, ");
        sb.append("   CMN_USRM_INF.USI_TEL3, ");
        sb.append("   CMN_USRM_INF.USI_FAX1, ");
        sb.append("   CMN_USRM_INF.USI_FAX2, ");
        sb.append("   CMN_USRM_INF.USI_FAX3, ");
        sb.append("   CMN_USRM_INF.USI_MAIL1, ");
        sb.append("   CMN_USRM_INF.USI_MAIL2, ");
        sb.append("   CMN_USRM_INF.USI_MAIL3, ");
        sb.append("   CMN_USRM_INF.USI_SYAIN_NO, ");
        sb.append("   CMN_USRM_INF.USI_SYOZOKU, ");
        sb.append("   CMN_USRM_INF.USI_SEIBETU,");
        sb.append("   CMN_USRM_INF.USI_ENTRANCE_DATE,");
        sb.append("   CMN_USRM_INF.USI_SORTKEY1, ");
        sb.append("   CMN_USRM_INF.USI_SORTKEY2, ");
        sb.append("   CMN_USRM_INF.USI_YAKUSYOKU, ");
        sb.append("   CMN_USRM_INF.USI_BIKO, ");
        sb.append("   CMN_USRM_INF.USI_LTLGIN, ");
        sb.append("   CMN_USRM_INF.USI_AUID, ");
        sb.append("   CMN_USRM_INF.USI_ADATE, ");
        sb.append("   CMN_USRM_INF.USI_EUID, ");
        sb.append("   CMN_USRM_INF.USI_EDATE, ");
        sb.append("   CMN_USRM_INF.USI_MBL_USE ");
        sb.append("from");
        sb.append("  CMN_USRM, ");
        sb.append("  CMN_USRM_INF ");
        sb.append("where ");
        sb.append("  CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sb.append("and ");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql("  CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql("and ");
            sql.addSql(" CMN_USRM.USR_LGID in (");

            Iterator<String> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addStrValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        CmnUsrmInfModel model1 = getCmnUsrmInfModelFromRs(rs);
                        CmnUsrmModel model2 = new CmnUsrmModel();
                        CmnUserModel mdl = new CmnUserModel(model1, model2);
                        model1.setUsiMblUse(rs.getInt("USI_MBL_USE"));
                        ret.add(mdl);


                        model2.setUsrSid(model1.getUsrSid());
                        model2.setUsrJkbn(rs.getInt("USR_JKBN"));
                        model2.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                        model2.setUsrLgid(rs.getString("USR_LGID"));
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }


    private void __setKeywordSearchSql(SqlBuffer sql, String keyword, String utableName) {
        //キーワード未入力の場合、検索SQLを追加しない
        if (StringUtil.isNullZeroString(keyword)) {
            return;
        }

        ShainSearchModel searchMdl = new ShainSearchModel();
        searchMdl.setKeyword(keyword);

        //ユーザ情報 キーワード検索の対象項目を全て指定する
        searchMdl.setKeyKbnShainno(1);
        searchMdl.setKeyKbnName(1);
        searchMdl.setKeyKbnNameKn(1);
        searchMdl.setKeyKbnMail(1);
        searchMdl.setKeyKbnTel(1);

        __setKeywordSearchSql(sql, searchMdl, utableName);
    }

    private void __setKeywordSearchSql(
        SqlBuffer sql, ShainSearchModel searchModel, String utableName) {
        //検索条件、または検索キーワードが未入力の場合、処理を終了
        if (searchModel == null
        || StringUtil.isNullZeroString(searchModel.getKeyword())) {
            return;
        }

        //検索キーワードを分割する
        String[] keywordList = searchModel.getKeyword().split(" ");

        sql.addSql("   (");

        boolean addSqlFlg = false;

        //キーワード区分 社員/職員番号
        if (searchModel.getKeyKbnShainno() == 1) {
            __setLikeSearchSql(sql, keywordList,
                            utableName + "." + "USI_SYAIN_NO"
            );
            addSqlFlg = true;
        }

        //キーワード区分 氏名 (姓＋名)
        if (searchModel.getKeyKbnName() == 1) {
            if (addSqlFlg) {
                sql.addSql("   or");
            }
            __setLikeSearchSql(sql, keywordList,
                            utableName + "." + "USI_SEI"
                            + " || "
                            + utableName + "." + "USI_MEI"
            );
            addSqlFlg = true;
        }
        //キーワード区分 氏名カナ
        if (searchModel.getKeyKbnNameKn() == 1) {
            if (addSqlFlg) {
                sql.addSql("   or");
            }
            __setLikeSearchSql(sql, keywordList,
                            utableName + "." + "USI_SEI_KN"
                            + " || "
                            + utableName + "." + "USI_MEI_KN"
            );
            addSqlFlg = true;
        }

        //キーワード区分 E-MAIL
        if (searchModel.getKeyKbnMail() == 1) {
            if (addSqlFlg) {
                sql.addSql("   or");
            }
            __setLikeSearchSql(sql, keywordList,
                            utableName + "." + "USI_MAIL1"
            );
            sql.addSql("   or");
            __setLikeSearchSql(sql, keywordList,
                            utableName + "." + "USI_MAIL2"
            );
            sql.addSql("   or");
            __setLikeSearchSql(sql, keywordList,
                            utableName + "." + "USI_MAIL3"
            );
            addSqlFlg = true;
        }

        //キーワード区分 電話番号
        if (searchModel.getKeyKbnTel() == 1) {
            //電話番号検索の場合、検索キーワードの"-"を除外した状態で行う
            String[] keywordTelList =
                Stream.of(keywordList)
                .map(keyword -> keyword.replace("-", ""))
                .toArray(String[]::new);

            if (addSqlFlg) {
                sql.addSql("   or");
            }

            __setLikeSearchSql(sql, keywordTelList,
                            "replace(" + utableName + "."
                            + "USI_TEL1, '-','')"
            );
            sql.addSql("   or");
            __setLikeSearchSql(sql, keywordTelList,
                            "replace(" + utableName + "."
                            + "USI_TEL2, '-','')"
            );
            sql.addSql("   or");
            __setLikeSearchSql(sql, keywordTelList,
                            "replace(" + utableName + "."
                            + "USI_TEL3, '-','')"
            );
            addSqlFlg = true;
        }

        sql.addSql("   )");
    }

    private void __setLikeSearchSql(SqlBuffer sql, String[] keywordList, String fieldName) {
        if (keywordList.length > 1) {
            sql.addSql("     (");
        }

        for (int idx = 0; idx < keywordList.length; idx++) {
            if (idx > 0) {
                sql.addSql("     and");
            }
            sql.addSql("      "
            + fieldName
            + " like '%"
            + JDBCUtil.escapeForLikeSearch(keywordList[idx])
            + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        if (keywordList.length > 1) {
            sql.addSql("     )");
        }
    }
}