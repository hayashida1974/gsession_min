package jp.groupsession.v2.wml.wml270;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.wml.biz.WmlBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 送信先リスト管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml270Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml270Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml270Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 送信先リストの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return アカウント情報の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Wml270DestListModel> getDestListList(Wml270SearchModel searchMdl,
                                                        RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Wml270DestListModel> ret = new ArrayList<Wml270DestListModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_DESTLIST.WDL_SID as WDL_SID,");
            sql.addSql("   WML_DESTLIST.WDL_NAME as WDL_NAME,");
            sql.addSql("   WML_DESTLIST.WDL_BIKO as WDL_BIKO,");
            sql.addSql("   DESTLIST_USER_COUNT.USR_COUNT as USR_COUNT");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         DESTLIST_USERS.WDL_SID,");
            sql.addSql("         count(DESTLIST_USERS.WDL_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         (");
            sql.addSql("           select");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF.WDL_SID as WDL_SID,");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF.WLA_USR_SID as USR_SID");
            sql.addSql("           from");
            sql.addSql("             CMN_USRM,");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF");
            sql.addSql("           where");
            sql.addSql("             CMN_USRM.USR_JKBN = ?");
            sql.addSql("           and");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("           and");
            sql.addSql("             CMN_USRM.USR_SID = WML_DESTLIST_ACCESS_CONF.WLA_USR_SID");
            sql.addSql("         union");
            sql.addSql("           select");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF.WDL_SID as WDL_SID,");
            sql.addSql("             CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("           from");
            sql.addSql("             CMN_USRM,");
            sql.addSql("             CMN_GROUPM,");
            sql.addSql("             CMN_BELONGM,");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF");
            sql.addSql("           where");
            sql.addSql("             CMN_USRM.USR_JKBN = ?");
            sql.addSql("           and");
            sql.addSql("             CMN_GROUPM.GRP_JKBN = ?");
            sql.addSql("           and");
            sql.addSql("             WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("           and");
            sql.addSql("             CMN_GROUPM.GRP_SID = WML_DESTLIST_ACCESS_CONF.WLA_USR_SID");
            sql.addSql("           and");
            sql.addSql("             CMN_GROUPM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("           and");
            sql.addSql("             CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("        ) DESTLIST_USERS");
            sql.addSql("      group by");
            sql.addSql("        DESTLIST_USERS.WDL_SID");
            sql.addSql("     ) DESTLIST_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     WML_DESTLIST.WDL_SID = DESTLIST_USER_COUNT.WDL_SID");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstWebmail.WDA_TYPE_USER);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstWebmail.WDA_TYPE_ADDRESS);

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            String order = "";
            if (searchMdl.getOrder() == GSConstWebmail.ORDER_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            if (searchMdl.getSortKey() == GSConstWebmail.SKEY_USER) {
                sql.addSql("   USR_COUNT" + order);
            } else {
                sql.addSql("   WDL_NAME" + order);
            }
            sql.setPagingValue(searchMdl.getStart() - 1, searchMdl.getLimit());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            WmlBiz wmlBiz = new WmlBiz();
            while (rs.next()) {

                Wml270DestListModel model = new Wml270DestListModel();
                model.setDestListSid(rs.getInt("WDL_SID"));
                model.setDestListName(rs.getString("WDL_NAME"));
                model.setDestListBiko(rs.getString("WDL_BIKO"));
                model.setUserCount(rs.getInt("USR_COUNT"));
                if (searchMdl.getUserSid() > 0) {
                    model.setEditFlg(wmlBiz.canEditDestlist(con, model.getDestListSid(),
                                                                        searchMdl.getUserSid()));
                } else {
                    model.setEditFlg(true);
                }
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
     * <br>[機  能] レコード件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  searchMdl 検索パラメータモデル
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int recordCount(Wml270SearchModel searchMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

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
     * <br>[機  能] SqlBufferに検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Wml270SearchModel searchMdl) {
        if (searchMdl.getUserSid() > 0) {
            sql.addSql(" where");
            sql.addSql("   WML_DESTLIST.WDL_SID in (");
            sql.addSql("      select WDL_SID from WML_DESTLIST_ACCESS_CONF");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      or");
            sql.addSql("        (");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          WML_DESTLIST_ACCESS_CONF.WLA_USR_SID in (");
            sql.addSql("           select GRP_SID from CMN_BELONGM");
            sql.addSql("            where CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addIntValue(GSConstWebmail.WLA_KBN_USER);
            sql.addIntValue(searchMdl.getUserSid());
            sql.addIntValue(GSConstWebmail.WLA_KBN_GROUP);
            sql.addIntValue(searchMdl.getUserSid());
        }

        if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
            if (searchMdl.getUserSid() > 0) {
                sql.addSql(" and");
            } else {
                sql.addSql(" where");
            }
            sql.addSql("   WML_DESTLIST.WDL_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        return sql;
    }

    /**
     * <br>[機  能] 送信先リスト名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sendListSidList 送信先リストSIDリスト
     * @return 送信先リスト名の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getSendListNameList(String[] sendListSidList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDL_NAME");
            sql.addSql(" from ");
            sql.addSql("   WML_DESTLIST");
            sql.addSql(" where ");
            sql.addSql("   WDL_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(sendListSidList[0]));
            for (String sendListSid :sendListSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(sendListSid));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("WDL_NAME"));
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
