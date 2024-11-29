package jp.groupsession.v2.convert.convert430.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.convert.convert430.model.CvtCirAccountModel;
import jp.groupsession.v2.convert.convert430.model.CvtCirAccountUserModel;
import jp.groupsession.v2.convert.convert430.model.CvtCirUserModel;
import jp.groupsession.v2.convert.convert430.model.CvtCirViewModel;
import jp.groupsession.v2.usr.GSConstUser;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 アカウント設定用DAO
 * <br>[解  説] v4.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvCircular430Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvCircular430Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvCircular430Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 登録されているユーザの回覧板アカウントを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void cirConv(MlCountMtController mtCon) throws SQLException {

        try {

            //既存のユーザを取得する
            List<CmnUsrmInfModel> usrList = new ArrayList<CmnUsrmInfModel>();
            CmnUsrmDao udao = new CmnUsrmDao(getCon());
            usrList = udao.getUsrAll();

            if (usrList != null && !usrList.isEmpty()) {
                //ユーザのアカウントを登録
                __doInsertCirAccount(usrList, mtCon);
            }

        } catch (SQLException e) {
            throw e;
        }
    }


    /**
     * <br>[機  能] ユーザのアカウントを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @param usrList List<BaseUserModel>
     * @throws SQLException SQL実行例外
     */
    private void __doInsertCirAccount(List<CmnUsrmInfModel> usrList,
                                      MlCountMtController mtCon)
    throws SQLException {

        log__.info("回覧板   コンバート開始");

        //ユーザのアカウントを登録する
        for (CmnUsrmInfModel bean : usrList) {

            String userName = "";

            if (!StringUtil.isNullZeroStringSpace(bean.getUsiSei())
                    && !StringUtil.isNullZeroStringSpace(bean.getUsiMei())) {
                userName = bean.getUsiSei() + " " + bean.getUsiMei() + " ";
            }

            int cacSaiSid = 0;

            if (bean.getUsrSid() > 1) {
                cacSaiSid = (int) mtCon.getSaibanNumber("circular",
                    "account", -1);

                //削除区分
                int jkbn = 0;
                if (bean.getUsrJkbn() == 9) {
                    jkbn = 1;
                }

                //アカウント登録
                CvtCirAccountModel accountMdl = new CvtCirAccountModel();
                accountMdl.setCacSid(cacSaiSid);
                accountMdl.setUsrSid(bean.getUsrSid());
                accountMdl.setCacName(bean.getUsiSei() + " " + bean.getUsiMei());
                accountMdl.setCacTheme(0);
                accountMdl.setCacType(2);
                accountMdl.setCacBiko("");
                accountMdl.setCacJkbn(jkbn);

                CvtCirUserModel cirUsrMdl = null;
                cirUsrMdl = getCirUserInfo(bean.getUsrSid());
                if (cirUsrMdl != null) {
                    accountMdl.setCacSmlNtf(cirUsrMdl.getCurSmlNtf());
                    if (cirUsrMdl.getCurInitKbn() == 1) {
                        accountMdl.setCacMemoKbn(cirUsrMdl.getCurMemoKbn());
                        accountMdl.setCacMemoDay(cirUsrMdl.getCurMemoKbn());
                        accountMdl.setCacKouKbn(cirUsrMdl.getCurKouKbn());
                        accountMdl.setCacInitKbn(cirUsrMdl.getCurInitKbn());
                    } else {
                        accountMdl.setCacMemoKbn(0);
                        accountMdl.setCacMemoDay(0);
                        accountMdl.setCacKouKbn(0);
                        accountMdl.setCacInitKbn(0);
                    }
                }

                __insertCirAccount(accountMdl);

                //アカウントユーザ登録
                if (bean.getUsrSid() >= GSConstUser.USER_RESERV_SID) {
                    CvtCirAccountUserModel accountUserMdl = new CvtCirAccountUserModel();
                    accountUserMdl.setCacSid(cacSaiSid);
                    accountUserMdl.setUsrSid(bean.getUsrSid());
                    __insertCirAccountUser(accountUserMdl);
                }

                //回覧先情報更新
                log__.info(userName + "回覧先情報更新");
                updateCirView(bean.getUsrSid(), cacSaiSid);
                //自動削除更新
                log__.info(userName + "自動削除更新");
                updateAdel(bean.getUsrSid(), cacSaiSid);
                //回覧先ユーザ添付更新
                log__.info(userName + "回覧先ユーザ添付更新");
                updateUserBin(bean.getUsrSid(), cacSaiSid);
                //回覧板情報登録者ID更新
                log__.info(userName + "回覧先ユーザ添付更新");
                updateCirInfAuid(bean.getUsrSid(), cacSaiSid);
                //回覧板情報更新者ID更新
                log__.info(userName + "回覧先ユーザ添付更新");
                updateCirInfEuid(bean.getUsrSid(), cacSaiSid);

            }
        }
    }


    /**
     * <p>Insert CIR_ACCOUNT Data Bindding JavaBean
     * @param bean CIR_ACCOUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void __insertCirAccount(CvtCirAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME,");
            sql.addSql("   CAC_SML_NTF,");
            sql.addSql("   CAC_MEMO_KBN,");
            sql.addSql("   CAC_MEMO_DAY,");
            sql.addSql("   CAC_KOU_KBN,");
            sql.addSql("   CAC_INIT_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCacName());
            sql.addStrValue(bean.getCacBiko());
            sql.addIntValue(bean.getCacJkbn());
            sql.addIntValue(bean.getCacTheme());
            sql.addIntValue(bean.getCacSmlNtf());
            sql.addIntValue(bean.getCacMemoKbn());
            sql.addIntValue(bean.getCacMemoDay());
            sql.addIntValue(bean.getCacKouKbn());
            sql.addIntValue(bean.getCacInitKbn());
            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert CIR_ACCOUNT_USER Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void __insertCirAccountUser(CvtCirAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT_USER(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   null,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getUsrSid());
            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <br>[機  能] 指定されたユーザの送信メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return ret 受信メッセージ一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CvtCirViewModel> selectCircularList(int userSid,
                                                     int offset,
                                                     int limit)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<CvtCirViewModel> ret = new ArrayList<CvtCirViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CVW_MEMO,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_DSP,");
            sql.addSql("   CVW_AUID,");
            sql.addSql("   CVW_ADATE,");
            sql.addSql("   CVW_EUID,");
            sql.addSql("   CVW_EDATE");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" order by CIR_VIEW.CIF_SID");

            pstmt =
                con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.addIntValue(userSid);

            //ログを出力
            log__.debug(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                CvtCirViewModel mdl = new CvtCirViewModel();
                mdl.setCifSid(rs.getInt("CIF_SID"));
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
     * <p>Update CIR_VIEW Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param cacSid CAC_SID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCirView(int usrSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update CIR_ADEL Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param cacSid CAC_SID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateAdel(int usrSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ADEL");
            sql.addSql(" set ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update CIR_USER_BIN Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param cacSid CAC_SID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateUserBin(int usrSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" set ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update CIR_INF Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param cacSid CAC_SID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateCirInfAuid(int usrSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_AUID=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_AUID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <p>Update CIR_INF Data Bindding JavaBean
     * @param usrSid USR_SID
     * @param cacSid CAC_SID
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateCirInfEuid(int usrSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_EUID=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_EUID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(usrSid);

            log__.debug(sql.toLogString());
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
     * <br>[機  能] ユーザSIDから回覧板個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return CIR_USERModel
     * @throws SQLException SQL実行例外
     */
    public CvtCirUserModel getCirUserInfo(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CvtCirUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUR_MAX_DSP,");
            sql.addSql("   CUR_SML_NTF,");
            sql.addSql("   CUR_RELOAD,");
            sql.addSql("   CUR_AUID,");
            sql.addSql("   CUR_ADATE,");
            sql.addSql("   CUR_EUID,");
            sql.addSql("   CUR_EDATE,");
            sql.addSql("   CUR_MEMO_KBN,");
            sql.addSql("   CUR_MEMO_DAY,");
            sql.addSql("   CUR_KOU_KBN,");
            sql.addSql("   CUR_INIT_KBN");
            sql.addSql(" from");
            sql.addSql("   CIR_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new CvtCirUserModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setCurMaxDsp(rs.getInt("CUR_MAX_DSP"));
                ret.setCurSmlNtf(rs.getInt("CUR_SML_NTF"));
                ret.setCurReload(rs.getInt("CUR_RELOAD"));
                ret.setCurAuid(rs.getInt("CUR_AUID"));
                ret.setCurAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUR_ADATE")));
                ret.setCurEuid(rs.getInt("CUR_EUID"));
                ret.setCurEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUR_EDATE")));
                ret.setCurMemoKbn(rs.getInt("CUR_MEMO_KBN"));
                ret.setCurMemoDay(rs.getInt("CUR_MEMO_DAY"));
                ret.setCurKouKbn(rs.getInt("CUR_KOU_KBN"));
                ret.setCurInitKbn(rs.getInt("CUR_INIT_KBN"));
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