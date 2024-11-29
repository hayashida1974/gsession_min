package jp.groupsession.v2.sml.restapi.accounts.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * アカウントリスト情報API
 *
 *
 */
public class SmlAccountsQueryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountsQueryDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAccountsQueryDao(Connection con) {
        super(con);
    }
    /**
     * <p>アクセス可能なアカウントを取得する
     * @param userList 対象ユーザSIDリスト
     * @param banSmlAccountSidList 対象外とする代表アカウントリストSID
     * @param banUsrAccountSidList 対象外とするユーザアカウントリストSID
     * @param accountSidList 取得対象アカウントSIDリスト
     * @param smlAccountFlg 代表アカウントのみ取得
     * @param limit 取得件数
     * @param offset 開始位置
     * @return List in SmlAccountsQueryPostResultModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountsQueryPostResultModel> smlAccountList(
            List<UsrLabelValueBean> userList,
            List<Integer> banSmlAccountSidList,
            List<Integer> banUsrAccountSidList,
            int[] accountSidList,
            boolean smlAccountFlg,
            CmnCmbsortConfModel sortMdl,
            int limit,
            int offset)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAccountsQueryPostResultModel> ret
                                 = new ArrayList<SmlAccountsQueryPostResultModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME as SAC_NAME,");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CASE");
            sql.addSql("     WHEN SML_ACCOUNT.USR_SID is null THEN 1");
            sql.addSql("     ELSE 0");
            sql.addSql("     END ACCOUNT_TYPE,");
            sql.addSql("   CASE");
            sql.addSql("     WHEN CMN_USRM_INF.POS_SID = 0 THEN 1");
            sql.addSql("     ELSE 0");
            sql.addSql("     END YAKUSYOKU_EXIST,");
            sql.addSql("   CASE");
            sql.addSql("     WHEN CMN_USRM_INF.POS_SID = 0 THEN 0");
            sql.addSql("     ELSE (");
            sql.addSql("       select");
            sql.addSql("         POS_SORT");
            sql.addSql("       from");
            sql.addSql("         CMN_POSITION");
            sql.addSql("       where");
            sql.addSql("         CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("     )");
            sql.addSql("     END YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            if (userList.size() > 0
                    || smlAccountFlg
                    || (accountSidList != null && accountSidList.length > 0)) {

                // グループSID指定 or マイグループ指定の対象ユーザアカウント
                if (userList.size() > 0) {
                    sql.addSql(" and ");
                    sql.addSql("   SML_ACCOUNT.USR_SID in (");
                    boolean firstFlg = true;
                    for (UsrLabelValueBean usrBean : userList) {
                        //予約済みアカウントは除外
                        if (usrBean.getValue().equals("0")
                                || usrBean.getValue().equals("1")) {
                            continue;
                        }
                        if (firstFlg) {
                            firstFlg = false;
                        } else {
                            sql.addSql("     ,");
                        }
                        sql.addSql("     ?");
                        sql.addIntValue(Integer.parseInt(usrBean.getValue()));
                    }
                    sql.addSql("   )");
                }
                // 代表アカウント
                if (smlAccountFlg) {
                    sql.addSql(" and ");
                    sql.addSql("   (");
                    sql.addSql("     SML_ACCOUNT.USR_SID is null");
                    if (banSmlAccountSidList.size() > 0) {
                        sql.addSql("     and");
                        sql.addSql("       SML_ACCOUNT.SAC_SID not in (");
                        boolean firstFlg = true;
                        for (int banSid : banSmlAccountSidList) {
                            if (firstFlg) {
                                firstFlg = false;
                            } else {
                                sql.addSql("     ,");
                            }
                            sql.addSql("     ?");
                            sql.addIntValue(banSid);
                        }
                        sql.addSql("   )");
                    }
                    sql.addSql("   )");
                }
                // アカウントSID指定
                if (accountSidList != null && accountSidList.length > 0) {
                    sql.addSql(" and ");
                    sql.addSql("   SML_ACCOUNT.SAC_SID in (");
                    boolean firstFlg = true;
                    for (int sid : accountSidList) {
                        //予約済みアカウントは除外
                        if (sid == 0 || sid == 1) {
                            continue;
                        }
                        if (firstFlg) {
                            firstFlg = false;
                        } else {
                            sql.addSql("     ,");
                        }
                        sql.addSql("     ?");
                        sql.addIntValue(sid);
                    }
                    sql.addSql("   )");

                }
            } else {
                // 全件取得
                sql.addSql(" and ");
                sql.addSql("   SML_ACCOUNT.SAC_SID not in (0, 1 ");
                for (int sid : banSmlAccountSidList) {
                    sql.addSql("     ,");
                    sql.addSql("     ?");
                    sql.addIntValue(sid);
                }
                if (banUsrAccountSidList.size() > 0) {
                    sql.addSql("   ) ");
                    sql.addSql(" and ");
                    sql.addSql("   COALESCE(SML_ACCOUNT.USR_SID, -1) not in ( ");
                    boolean firstFlg = true;
                    for (int sid : banUsrAccountSidList) {
                        if (firstFlg) {
                            firstFlg = false;
                        } else {
                            sql.addSql("     ,");
                        }
                        sql.addSql("     ?");
                        sql.addIntValue(sid);
                    }
                }
                sql.addSql("   ) ");

            }

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_TYPE");
            //ユーザアカウント用ソート処理
            String order = "asc";
            if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
                order = "desc";
            }
            switch (sortMdl.getCscUserSkey1()) {
                case GSConst.USERCMB_SKEY_NAME:
                    sql.addSql("   , USI_SEI_KN " + order);
                    sql.addSql("   , USI_MEI_KN " + order);
                    break;
                case GSConst.USERCMB_SKEY_SNO:
                    sql.addSql("   , case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end " + order);
                    break;
                case GSConst.USERCMB_SKEY_POSITION:
                    sql.addSql("   , YAKUSYOKU_EXIST " + order);
                    sql.addSql("   , YAKUSYOKU_SORT " + order);
                    break;
                case GSConst.USERCMB_SKEY_BDATE:
                    sql.addSql("   , USI_BDATE " + order);
                    break;
                case GSConst.USERCMB_SKEY_SORTKEY1:
                    sql.addSql("   , USI_SORTKEY1 " + order);
                    break;
                case GSConst.USERCMB_SKEY_SORTKEY2:
                    sql.addSql("   , USI_SORTKEY2 " + order);
                    break;
                default:
                    sql.addSql("   , YAKUSYOKU_EXIST asc");
                    sql.addSql("   , YAKUSYOKU_SORT asc");
                    break;
            }
            order = "asc";
            if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
                order = "desc";
            }
            switch (sortMdl.getCscUserSkey2()) {
                case GSConst.USERCMB_SKEY_NAME:
                    sql.addSql("   , USI_SEI_KN " + order);
                    sql.addSql("   , USI_MEI_KN " + order);
                    break;
                case GSConst.USERCMB_SKEY_SNO:
                    sql.addSql("   , case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end " + order);
                    break;
                case GSConst.USERCMB_SKEY_POSITION:
                    sql.addSql("   , YAKUSYOKU_EXIST " + order);
                    sql.addSql("   , YAKUSYOKU_SORT " + order);
                    break;
                case GSConst.USERCMB_SKEY_BDATE:
                    sql.addSql("   , USI_BDATE " + order);
                    break;
                case GSConst.USERCMB_SKEY_SORTKEY1:
                    sql.addSql("   , USI_SORTKEY1 " + order);
                    break;
                case GSConst.USERCMB_SKEY_SORTKEY2:
                    sql.addSql("   , USI_SORTKEY2 " + order);
                    break;
                default:
                    break;
            }
            if (sortMdl.getCscUserSkey1() != GSConst.USERCMB_SKEY_NAME
                    && sortMdl.getCscUserSkey2() != GSConst.USERCMB_SKEY_NAME) {
                sql.addSql("   , USI_SEI_KN");
                sql.addSql("   , USI_MEI_KN");
            }
            if (userList.size() == 0) {
                sql.addSql(" , SML_ACCOUNT.SAC_NAME");
                sql.addSql(" , SML_ACCOUNT.SAC_SID");
            }

            sql.addSql(" limit ?");
            sql.addIntValue(limit);
            sql.addSql(" offset ?");
            sql.addIntValue(offset);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            Map<Integer, SmlAccountsQueryPostResultModel> userAccountMap
                = new HashMap<Integer, SmlAccountsQueryPostResultModel>();
            while (rs.next()) {
                SmlAccountsQueryPostResultModel resMdl = new SmlAccountsQueryPostResultModel();
                resMdl.setSid(rs.getInt("SAC_SID"));
                if (rs.getInt("ACCOUNT_TYPE") == 0) {
                    //代表アカウントではない場合は、アカウント名にユーザ姓名を設定
                    resMdl.setName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    //代表アカウントの場合はアカウント名を使用
                    resMdl.setName(rs.getString("SAC_NAME"));
                }
                if (rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_ACTIVE) {
                    resMdl.setUserDeleteFlg(0);
                } else {
                    resMdl.setUserDeleteFlg(1);
                }
                resMdl.setLoginStopFlg(rs.getInt("USR_UKO_FLG"));

                if (!userList.isEmpty()) {
                    userAccountMap.put(rs.getInt("USR_SID"), resMdl);
                } else {
                    ret.add(resMdl);
                }
            }

            //ユーザアカウント取得の場合、アカウントの並び替えを行う
            if (!userList.isEmpty() && !userAccountMap.isEmpty()) {
                UserBiz usrBiz = new UserBiz();
                List<CmnUsrmInfModel> smlUserList
                    = usrBiz.getUserList(con,
                                        userAccountMap.keySet()
                                        .parallelStream()
                                        .collect(Collectors.toList()));

                for (CmnUsrmInfModel userInf : smlUserList) {
                    ret.add(userAccountMap.get(userInf.getUsrSid()));
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
     * <p>アクセス可能なアカウントを取得する
     * @param userList 対象ユーザSIDリスト
     * @param banSmlAccountSidList 対象外とする代表アカウントリストSID
     * @param banUsrAccountSidList 対象外とするユーザアカウントリストSID
     * @param accountSidList 取得対象アカウントSIDリスト
     * @param smlAccountFlg 代表アカウントのみ取得
     * @return List in SmlAccountsQueryPostResultModel
     * @throws SQLException SQL実行例外
     */
    public int count(
            List<UsrLabelValueBean> userList,
            List<Integer> banSmlAccountSidList,
            List<Integer> banUsrAccountSidList,
            int[] accountSidList,
            boolean smlAccountFlg)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(SML_ACCOUNT.SAC_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM");
            sql.addSql("   on");
            sql.addSql("     SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            if (userList.size() > 0
                    || smlAccountFlg
                    || (accountSidList != null && accountSidList.length > 0)) {

                // グループSID指定 or マイグループ指定の対象ユーザアカウント
                if (userList.size() > 0) {
                    sql.addSql(" and ");
                    sql.addSql("   SML_ACCOUNT.USR_SID in (");
                    boolean firstFlg = true;
                    for (UsrLabelValueBean usrBean : userList) {
                        //予約済みアカウントは除外
                        if (usrBean.getValue().equals("0")
                                || usrBean.getValue().equals("1")) {
                            continue;
                        }
                        if (firstFlg) {
                            firstFlg = false;
                        } else {
                            sql.addSql("     ,");
                        }
                        sql.addSql("     ?");
                        sql.addIntValue(Integer.parseInt(usrBean.getValue()));
                    }
                    sql.addSql("   )");
                }
                // 代表アカウント
                if (smlAccountFlg) {
                    sql.addSql(" and ");
                    sql.addSql("   (");
                    sql.addSql("     SML_ACCOUNT.USR_SID is null");
                    if (banSmlAccountSidList.size() > 0) {
                        sql.addSql("     and");
                        sql.addSql("       SML_ACCOUNT.SAC_SID not in (");
                        boolean firstFlg = true;
                        for (int banSid : banSmlAccountSidList) {
                            if (firstFlg) {
                                firstFlg = false;
                            } else {
                                sql.addSql("     ,");
                            }
                            sql.addSql("     ?");
                            sql.addIntValue(banSid);
                        }
                        sql.addSql("   )");
                    }
                    sql.addSql("   )");
                }
                // アカウントSID指定
                if (accountSidList != null && accountSidList.length > 0) {
                    sql.addSql(" and ");
                    sql.addSql("   SML_ACCOUNT.SAC_SID in (");
                    boolean firstFlg = true;
                    for (int sid : accountSidList) {
                        //予約済みアカウントは除外
                        if (sid == 0 || sid == 1) {
                            continue;
                        }
                        if (firstFlg) {
                            firstFlg = false;
                        } else {
                            sql.addSql("     ,");
                        }
                        sql.addSql("     ?");
                        sql.addIntValue(sid);
                    }
                    sql.addSql("   )");

                }
            } else {
                // 全件取得
                sql.addSql(" and ");
                sql.addSql("   SML_ACCOUNT.SAC_SID not in (0, 1 ");
                for (int sid : banSmlAccountSidList) {
                    sql.addSql("     ,");
                    sql.addSql("     ?");
                    sql.addIntValue(sid);
                }
                if (banUsrAccountSidList.size() > 0) {
                    sql.addSql("   ) ");
                    sql.addSql(" and ");
                    sql.addSql("   COALESCE(SML_ACCOUNT.USR_SID, -1) not in ( ");
                    boolean firstFlg = true;
                    for (int sid : banUsrAccountSidList) {
                        if (firstFlg) {
                            firstFlg = false;
                        } else {
                            sql.addSql("     ,");
                        }
                        sql.addSql("     ?");
                        sql.addIntValue(sid);
                    }
                }
                sql.addSql("   ) ");

            }
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
}
