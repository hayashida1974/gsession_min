package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.csv.RngCsvUserModel;
import jp.groupsession.v2.rng.model.RingiChannelDataModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiKeiroNameModel;
import jp.groupsession.v2.rng.model.RingiSearchModel;
import jp.groupsession.v2.rng.model.RngDeleteModel;
import jp.groupsession.v2.rng.model.RngSingiModel;
import jp.groupsession.v2.rng.rng030.Rng030KeiroParam;
import jp.groupsession.v2.rng.rng030.Rng030SingiParam;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 稟議プラグインで共通使用するDAOクラスです。
 * <br>[解  説]
 * <br>[備  考]
 */
public class RingiDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RingiDao.class);

    /**
     * <p>Default Constructor
     */
    public RingiDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RingiDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定された稟議の添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List < CmnBinfModel > getRingiTmpFileList(int rngSid)
    throws SQLException {

        return getRingiTmpFileList(rngSid, -1);
    }

    /**
     * <br>[機  能] 指定された稟議の添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List < CmnBinfModel > getRingiTmpFileList(int rngSid, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   RNG_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0");
            sql.addSql(" and");
            sql.addSql("   RNG_BIN.RNG_SID = ?");
            sql.addIntValue(rngSid);
            if (userSid != -1) {
                sql.addSql(" and");
                sql.addSql("   RNG_BIN.USR_SID = ?");
                sql.addIntValue(userSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                //添付ファイルサイズ設定(表示用)
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
     * <br>[機  能] 指定された稟議の添付ファイル情報一覧を一括で取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SIDリスト
     * @param userSid ユーザSID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap <Integer, List<CmnBinfModel>> getRingiTmpFileList(
            ArrayList<Integer> rngSidList, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap <Integer, List<CmnBinfModel>> map =
                new HashMap <Integer, List<CmnBinfModel>>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE,");
            sql.addSql("   RNG_BIN.RNG_SID as RNG_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   RNG_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0");
            sql.addSql(" and");

            if (rngSidList.size() > 1) {
                sql.addSql("   (");
            }
            boolean firstFlg = false;
            for (int rngSid : rngSidList) {
                if (firstFlg) {
                    sql.addSql(" or ");
                }
                sql.addSql("   RNG_BIN.RNG_SID = ?");
                sql.addIntValue(rngSid);
                firstFlg = true;
            }
            if (rngSidList.size() > 1) {
                sql.addSql("   )");
            }
            if (userSid != -1) {
                sql.addSql(" and");
                sql.addSql("   RNG_BIN.USR_SID = ?");
                sql.addIntValue(userSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer rngSid = Integer.parseInt(rs.getString("RNG_SID"));
                List < CmnBinfModel > modelList = map.get(rngSid);
                if (modelList == null) {
                    modelList = new ArrayList < CmnBinfModel >();
                    map.put(rngSid, modelList);
                }
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                //添付ファイルサイズ設定(表示用)
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                binMdl.setBinFileSizeDsp(strSize);
                modelList.add(binMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return map;
    }

    /**
     * <p>指定した経路IDのユーザの添付情報を返す
     * @param rksSid 経路SID
     * @param userSid ユーザSID
     * @return List in RNG_BINModel
     * @throws SQLException SQL実行例外
     */
    public List < CmnBinfModel > getSingiTemp(int rksSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   RNG_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0");
            sql.addSql(" and");
            sql.addSql("   RNG_BIN.RKS_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_BIN.USR_SID = ?");

            sql.addIntValue(rksSid);
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                //添付ファイルサイズ設定(表示用)
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
     * <br>[機  能] 指定された稟議テンプレートの添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngTmpSid 稟議テンプレートSID
     * @param rngTmpVer 稟議テンプレートバージョン if
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List < CmnBinfModel > getRingiTemplateTmpFileList(int rngTmpSid, int rngTmpVer)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_BIN,");
            sql.addSql("   CMN_BINF");
            if (rngTmpVer == 0) {
                sql.addSql(" ,( ");
                sql.addSql(" select");
                sql.addSql("     RTP_SID,");
                sql.addSql("     RTP_VER");
                sql.addSql(" from");
                sql.addSql("   RNG_TEMPLATE");
                sql.addSql(" where");
                sql.addSql("   RNG_TEMPLATE.RTP_SID = ?");
                sql.addSql("   and RNG_TEMPLATE.RTP_MAXVER_KBN = ?");
                sql.addIntValue(rngTmpSid);
                sql.addIntValue(RngTemplateDao.MAXVER_KBN_ON);
                sql.addSql(" ) MAX_TEMPLATE");
            }
            sql.addSql(" where");
            sql.addSql("   RNG_TEMPLATE_BIN.RTP_SID = ?");
            sql.addIntValue(rngTmpSid);
            if (rngTmpVer == 0) {
                sql.addSql(" and");
                sql.addSql("   RNG_TEMPLATE_BIN.RTP_VER = MAX_TEMPLATE.RTP_VER");
            } else {
                sql.addSql(" and");
                sql.addSql("   RNG_TEMPLATE_BIN.RTP_VER = ?");
                sql.addIntValue(rngTmpVer);
            }
            sql.addSql(" and");
            sql.addSql("   RNG_TEMPLATE_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = 0");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                //添付ファイルサイズ設定(表示用)
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
     * <br>[機  能] 指定された稟議のバイナリー情報の論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param usid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeRngBinData(int rngSid, int usid, UDate date) throws SQLException {

        return removeSelectedRingiBin(rngSid, -1, usid, date);
    }

    /**
     * <br>[機  能] 指定された稟議のバイナリー情報の論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @param usid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeSelectedRingiBin(
            int rngSid, int userSid, int usid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            //バイナリー情報の論理削除

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from RNG_BIN");
            sql.addSql("     where RNG_SID = ?");
            sql.addIntValue(rngSid);
            if (userSid >= 0) {
                sql.addSql("     and USR_SID = ?");
                sql.addIntValue(userSid);
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定された稟議のバイナリー情報の論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param userSid ユーザSID
     * @param usid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeRngBinData(int rksSid, int userSid, int usid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {

            //バイナリー情報の論理削除

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from RNG_BIN");
            sql.addSql("     where RKS_SID = ?");
            sql.addIntValue(rksSid);
            if (userSid >= 0) {
                sql.addSql("     and USR_SID = ?");
                sql.addIntValue(userSid);
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 稟議情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public RingiDataModel getRingiData(int rngSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RingiDataModel model = new RingiDataModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
            sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
            sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");
            sql.addSql("   RNG_RNDATA.RNG_ADATE as RNG_ADATE,");
            sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
            sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
            sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER,");
            sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   KEIROSTEP.RKS_STATUS as RNC_STATUS,");
            sql.addSql("   KEIROSTEP.RKS_ROLL_TYPE as RNC_TYPE,");
            sql.addSql("   KEIROSTEP.RSS_STATUS as RSS_STATUS,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   RNG_RNDATA");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select ");
            sql.addSql("         RNG_KEIRO_STEP.RNG_SID,");
            sql.addSql("         RNG_KEIRO_STEP.RKS_STATUS,");
            sql.addSql("         RNG_KEIRO_STEP.RKS_ROLL_TYPE,");
            sql.addSql("         RNG_SINGI.RSS_STATUS");
            sql.addSql("        from RNG_KEIRO_STEP");
            sql.addSql("        left join RNG_SINGI on ");
            sql.addSql("       RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("        left join RNG_DAIRI_USER on ");
            sql.addSql("       RNG_SINGI.USR_SID = RNG_DAIRI_USER.USR_SID");
            sql.addSql("       where RNG_SINGI.USR_SID = ?");
            sql.addSql("       or RNG_DAIRI_USER.USR_SID_DAIRI = ?");
            sql.addSql("     ) KEIROSTEP on RNG_RNDATA.RNG_SID = KEIROSTEP.RNG_SID");

            sql.addSql(" where");
            sql.addSql("   RNG_RNDATA.RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLICATE = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                model = new RingiDataModel();
                model.setRngSid(rs.getInt("RNG_SID"));
                model.setRngTitle(rs.getString("RNG_TITLE"));
                model.setRngStatus(rs.getInt("RNG_STATUS"));
                model.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                model.setRngAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_ADATE")));
                model.setStrMakeDate(__createDateStr(model.getRngAdate()));
                model.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                model.setRngId(rs.getString("RNG_ID"));
                model.setRtpSid(rs.getInt("RTP_SID"));
                model.setRtpVer(rs.getInt("RTP_VER"));
                model.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                model.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);

                model.setRncStatus(rs.getInt("RNC_STATUS"));
                model.setRncType(rs.getInt("RNC_TYPE"));

                model.setRssStatus(rs.getInt("RSS_STATUS"));
                model.setRngId(rs.getString("RNG_ID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return model;
    }
//
//
//    /**
//     * <br>[機  能] 稟議情報を取得する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rngSid 稟議SID
//     * @param userSid ユーザSID
//     * @param rksSid 経路SID
//     * @return 稟議情報
//     * @throws SQLException SQL実行例外
//     */
//    public RingiDataModel getRingiData(int rngSid, int userSid, int rksSid) throws SQLException {
//        ArrayList<Integer> rngSidList = new ArrayList<Integer>();
//        rngSidList.add(rngSid);
//        ArrayList<Integer> rksSidList = new ArrayList<Integer>();
//        rksSidList.add(rksSid);
//        ArrayList<RingiDataModel> ringiDataModelList
//            = getRingiData(rngSidList, userSid, rksSidList);
//        return ringiDataModelList.get(0);
//    }
//

    /**
     * <br>[機  能] 稟議情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @param rksSid 経路SID
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public RingiDataModel getRingiData(int rngSid, int userSid, int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RingiDataModel model = new RingiDataModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
            sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
            sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");
            sql.addSql("   RNG_RNDATA.RNG_ADATE as RNG_ADATE,");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE as RNG_APPLDATE,");
            sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
            sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
            sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER,");
            sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   KEIROSTEP.RKS_STATUS as RNC_STATUS,");
            sql.addSql("   KEIROSTEP.RKS_ROLL_TYPE as RNC_TYPE,");
            sql.addSql("   KEIROSTEP.RSS_STATUS as RSS_STATUS,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   RNG_RNDATA");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("        select ");
            sql.addSql("          RNG_KEIRO_STEP.RNG_SID,");
            sql.addSql("          RNG_KEIRO_STEP.RKS_STATUS,");
            sql.addSql("          RNG_KEIRO_STEP.RKS_ROLL_TYPE,");
            sql.addSql("          RNG_SINGI.RSS_STATUS");
            sql.addSql("        from");
            sql.addSql("          RNG_KEIRO_STEP");
            sql.addSql("        left join");
            sql.addSql("          RNG_SINGI");
            sql.addSql("        on ");
            sql.addSql("          RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("        left join");
            sql.addSql("          RNG_DAIRI_USER");
            sql.addSql("        on ");
            sql.addSql("          RNG_SINGI.USR_SID = RNG_DAIRI_USER.USR_SID");
            sql.addSql("        where");
            sql.addSql("          RNG_KEIRO_STEP.RKS_SID = ?");
            sql.addSql("        and");
            sql.addSql("          RNG_SINGI.USR_SID = ?");
            sql.addSql("     ) KEIROSTEP on RNG_RNDATA.RNG_SID = KEIROSTEP.RNG_SID");

            sql.addSql(" where");
            sql.addSql("   RNG_RNDATA.RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLICATE = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            sql.addIntValue(rksSid);
            sql.addIntValue(userSid);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                model = new RingiDataModel();
                model.setRngSid(rs.getInt("RNG_SID"));
                model.setRngTitle(rs.getString("RNG_TITLE"));
                model.setRngStatus(rs.getInt("RNG_STATUS"));
                model.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                model.setRngAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_ADATE")));
                model.setStrMakeDate(__createDateStr(model.getRngAdate()));
                model.setStrRngAppldate(__createDateStr(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RNG_APPLDATE"))));
                model.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                model.setRngId(rs.getString("RNG_ID"));
                model.setRtpSid(rs.getInt("RTP_SID"));
                model.setRtpVer(rs.getInt("RTP_VER"));
                model.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                model.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);
                model.setRncStatus(rs.getInt("RNC_STATUS"));
                model.setRncType(rs.getInt("RNC_TYPE"));
                model.setRssStatus(rs.getInt("RSS_STATUS"));
                model.setRngId(rs.getString("RNG_ID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return model;
    }

    /**
     * <br>[機  能] PDF用の稟議情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID
     * @param userSid ユーザSID
     * @param rksSidList 経路SID
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RingiDataModel> getRingiData(ArrayList<Integer> rngSidList,
            int userSid, ArrayList<Integer> rksSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RingiDataModel> modelList = new ArrayList<RingiDataModel>();
        RingiDataModel model = new RingiDataModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
            sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
            sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");
            sql.addSql("   RNG_RNDATA.RNG_ADATE as RNG_ADATE,");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE as RNG_APPLDATE,");
            sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
            sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
            sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER,");
            sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   KEIROSTEP.RKS_STATUS as RNC_STATUS,");
            sql.addSql("   KEIROSTEP.RKS_ROLL_TYPE as RNC_TYPE,");
            sql.addSql("   KEIROSTEP.RSS_STATUS as RSS_STATUS,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   RNG_RNDATA");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("        select ");
            sql.addSql("          RNG_KEIRO_STEP.RNG_SID,");
            sql.addSql("          RNG_KEIRO_STEP.RKS_STATUS,");
            sql.addSql("          RNG_KEIRO_STEP.RKS_ROLL_TYPE,");
            sql.addSql("          RNG_SINGI.RSS_STATUS");
            sql.addSql("        from");
            sql.addSql("          RNG_KEIRO_STEP");
            sql.addSql("        left join");
            sql.addSql("          RNG_SINGI");
            sql.addSql("        on ");
            sql.addSql("          RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            sql.addSql("        where");
            sql.addSql("          (");
            boolean orFlg = false;
            for (int rksSid : rksSidList) {
                if (orFlg) {
                    sql.addSql(" or");
                }
                sql.addSql("          RNG_KEIRO_STEP.RKS_SID = ?");
                sql.addIntValue(rksSid);
                orFlg = true;
            }
            sql.addSql("          )");
            sql.addSql("        and");
            sql.addSql("          RNG_SINGI.USR_SID = ?");
            sql.addIntValue(userSid);
            sql.addSql("     ) KEIROSTEP on RNG_RNDATA.RNG_SID = KEIROSTEP.RNG_SID");

            sql.addSql(" where");
            sql.addSql("   (");
            orFlg = false;
            for (int rngSid : rngSidList) {
                if (orFlg) {
                    sql.addSql(" or");
                }
                sql.addSql("   RNG_RNDATA.RNG_SID = ?");
                sql.addIntValue(rngSid);
                orFlg = true;
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLICATE = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                model = new RingiDataModel();
                model.setRngSid(rs.getInt("RNG_SID"));
                model.setRngTitle(rs.getString("RNG_TITLE"));
                model.setRngStatus(rs.getInt("RNG_STATUS"));
                model.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                model.setRngAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNG_ADATE")));
                model.setStrMakeDate(__createDateStr(model.getRngAdate()));
                model.setStrRngAppldate(__createDateStr(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RNG_APPLDATE"))));
                model.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                model.setRngId(rs.getString("RNG_ID"));
                model.setRtpSid(rs.getInt("RTP_SID"));
                model.setRtpVer(rs.getInt("RTP_VER"));
                model.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                model.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);
                model.setRncStatus(rs.getInt("RNC_STATUS"));
                model.setRncType(rs.getInt("RNC_TYPE"));
                model.setRssStatus(rs.getInt("RSS_STATUS"));
                model.setRngId(rs.getString("RNG_ID"));
                modelList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return modelList;
    }

    /**
     * <br>[機  能] 稟議経路ステップ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public List<Rng030KeiroParam> getKeiroList(int rngSid) throws SQLException {
        ArrayList<Integer> rngSidList = new ArrayList<Integer>();
        rngSidList.add(rngSid);
        return getKeiroList(rngSidList);
    }

    /**
     * <br>[機  能] 稟議経路ステップ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SIDリスト
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public List<Rng030KeiroParam> getKeiroList(ArrayList<Integer> rngSidList) throws SQLException {
        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Rng030KeiroParam> ret = new ArrayList<Rng030KeiroParam>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GROUP_NAME.GRP_NAME as GRP_NAME,");
            sql.addSql("   GROUP_NAME.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   RNG_TEMPLATE_KEIRO.RTK_SID,");
            sql.addSql("   case  when RNG_TEMPLATE_KEIRO.RTK_OUTCONDITION IS NULL then 1 ");
            sql.addSql("         else RNG_TEMPLATE_KEIRO.RTK_OUTCONDITION ");
            sql.addSql("   end RTK_OUTCONDITION , ");
            sql.addSql("   case  when RNG_TEMPLATE_KEIRO.RTK_OUTCOND_BORDER IS NULL then -1 ");
            sql.addSql("         else RNG_TEMPLATE_KEIRO.RTK_OUTCOND_BORDER ");
            sql.addSql("   end RTK_OUTCOND_BORDER,");
            sql.addSql("   RNG_TEMPLATE_KEIRO.RTK_TYPE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_STATUS,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_BELONG_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_KEIRO_KOETU,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_KOETU_SIZI,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ADDSTEP");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" left join");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" on");
            sql.addSql("   RNG_TEMPLATE_KEIRO.RTK_SID = RNG_KEIRO_STEP.RTK_SID");
            sql.addSql(" left join");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       REFINE_BY.RKS_SID as RKS_SID,");
            sql.addSql("       CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("       CMN_GROUPM.GRP_JKBN as GRP_JKBN");
            sql.addSql("     from");
            sql.addSql("       (");
            sql.addSql("        select");
            sql.addSql("          RNG_KEIROSTEP_SELECT.RKS_SID as RKS_SID,");
            sql.addSql("          RNG_KEIROSTEP_SELECT.GRP_SID as GRP_SID");
            sql.addSql("        from");
            sql.addSql("          RNG_KEIRO_STEP");
            sql.addSql("        left join");
            sql.addSql("          RNG_KEIROSTEP_SELECT");
            sql.addSql("        on");
            sql.addSql("          RNG_KEIRO_STEP.RKS_SID = RNG_KEIROSTEP_SELECT.RKS_SID");
            sql.addSql("        left join");
            sql.addSql("          (select");
            sql.addSql("            RNG_KEIROSTEP_SELECT.RKS_SID as RKS_SID,");
            sql.addSql("            count(RNG_KEIROSTEP_SELECT.RKS_SID) as COUNT");
            sql.addSql("           from");
            sql.addSql("             RNG_KEIROSTEP_SELECT");
            sql.addSql("           left join");
            sql.addSql("             RNG_KEIRO_STEP");
            sql.addSql("           on");
            sql.addSql("             RNG_KEIROSTEP_SELECT.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            if (rngSidList.size() != 0) {
                sql.addSql("           where ");
                sql.addSql("             RNG_KEIRO_STEP.RNG_SID IN (");
                boolean multiFlg = false;
                for (int rngSid : rngSidList) {
                    if (multiFlg) {
                        sql.addSql(", ");
                    }
                    sql.addSql("?");
                    sql.addIntValue(rngSid);
                    multiFlg = true;
                }
            }
            sql.addSql("             )");
            sql.addSql("           group by");
            sql.addSql("             RNG_KEIROSTEP_SELECT.RKS_SID");
            sql.addSql("          )COUNT_RKS");
            sql.addSql("        on");
            sql.addSql("          RNG_KEIROSTEP_SELECT.RKS_SID = COUNT_RKS.RKS_SID");
            sql.addSql("        where");
            sql.addSql("          COUNT_RKS.COUNT = 1");
            sql.addSql("        and");
            sql.addSql("          RNG_KEIRO_STEP.RNG_SID IN (");
            if (rngSidList.size() != 0) {
                boolean firstFlg = false;
                for (int rngSid : rngSidList) {
                    if (firstFlg) {
                        sql.addSql(", ");
                    }
                    sql.addSql("?");
                    sql.addIntValue(rngSid);
                    firstFlg = true;
                }
            }
            sql.addSql("          )");
            sql.addSql("       )REFINE_BY");
            sql.addSql("     left join");
            sql.addSql("       CMN_GROUPM");
            sql.addSql("     on");
            sql.addSql("       REFINE_BY.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("   )GROUP_NAME");
            sql.addSql(" on");
            sql.addSql("   GROUP_NAME.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID IN (");
            if (rngSidList.size() != 0) {
                boolean firstFlg = false;
                for (int rngSid : rngSidList) {
                    if (firstFlg) {
                        sql.addSql(", ");
                    }
                    sql.addSql("?");
                    sql.addIntValue(rngSid);
                    firstFlg = true;
                }
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE <> ?");
            sql.addSql(" order by");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT");

            sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Rng030KeiroParam model = new Rng030KeiroParam();
                model.setKeiroStepSid(rs.getInt("RKS_SID"));
                model.setRngSid(rs.getInt("RNG_SID"));
                model.setKeiroName(rs.getString("GRP_NAME"));
                model.setGroupDelFlg(rs.getInt("GRP_JKBN"));
                model.setKeiroProgress(rs.getInt("RTK_OUTCONDITION"));
                model.setKeiroLimit(rs.getInt("RTK_OUTCOND_BORDER"));
                model.setKeiroSort(rs.getInt("RKS_SORT"));
                model.setKeiroStatus(rs.getInt("RKS_STATUS"));
                model.setKeiroSingi(rs.getInt("RKS_ROLL_TYPE"));
                model.setKeiroKoetu(rs.getInt("RKS_KEIRO_KOETU"));
                model.setRtkSid(rs.getInt("RTK_SID"));
                model.setRtkType(rs.getInt("RTK_TYPE"));
                model.setRksBelongSid(rs.getInt("RKS_BELONG_SID"));
                model.setKeiroKoetuSizi(rs.getInt("RKS_KOETU_SIZI"));
                model.setAddibleRtkSid(rs.getInt("RKS_ADDSTEP"));
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
     * <br>[機  能] SqlBufferにorder句を設定するクエリ記述する
     * <br>[解  説] 同ステップ内の審議情報をユーザ並び順設定を反映したソート順を設定する
     * <br>[備  考]
     * @param sql SqlBuffer
     * @return SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __setOrderSQLCmnSort(SqlBuffer sql) throws SQLException {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(getCon());
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   CMN_USRM_INF.USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN asc,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,CMN_USRM_INF.USI_SEI_KN " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,CMN_USRM_INF.USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,CMN_USRM_INF.USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,CMN_USRM_INF.USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }

        return sql;
    }

    /**
     * <br>[機  能] 稟議審議情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param  rngSid 稟議SID
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public List<Rng030SingiParam> getSingiList(int rngSid) throws SQLException {
        ArrayList<Integer> rngSidList = new ArrayList<Integer>();
        rngSidList.add(rngSid);
        return getSingiList(rngSidList);
    }

    /**
     * <br>[機  能] 稟議審議情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param  rngSidList 稟議SIDリスト
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public List<Rng030SingiParam> getSingiList(ArrayList<Integer> rngSidList) throws SQLException {
        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Rng030SingiParam> ret = new ArrayList<Rng030SingiParam>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SINGI.RKS_SID,");
            sql.addSql("   RNG_SINGI.USR_SID,");
            sql.addSql("   RNG_SINGI.RNG_SID,");
            sql.addSql("   CMN_POSITION.POS_NAME,");
            sql.addSql("   CMN_POSITION.POS_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else CMN_POSITION.POS_SORT");
            sql.addSql("    end) as YAKUSYOKU_SORT,");
            sql.addSql("   RNG_SINGI.RSS_COMMENT,");
            sql.addSql("   RNG_SINGI.RSS_CHKDATE,");
            sql.addSql("   RNG_SINGI.RSS_STATUS,");
            sql.addSql("   RNG_SINGI.USR_SID_DAIRI,");
            sql.addSql("   DAIRI.USI_SEI as DAIRI_SEI,");
            sql.addSql("   DAIRI.USI_MEI as DAIRI_MEI,");
            sql.addSql("   KOETU.USI_SEI as KOETU_SEI,");
            sql.addSql("   KOETU.USI_MEI as KOETU_MEI");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" left join");
            sql.addSql("      CMN_USRM_INF KOETU");
            sql.addSql(" on");
            sql.addSql("   KOETU.USR_SID = RNG_SINGI.USR_SID_KOETU");
            sql.addSql(" left join");
            sql.addSql("      CMN_USRM_INF DAIRI");
            sql.addSql(" on");
            sql.addSql("   DAIRI.USR_SID = RNG_SINGI.USR_SID_DAIRI");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" ON");
            sql.addSql("   CMN_USRM_INF.USR_SID = RNG_SINGI.USR_SID");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" ON");
            sql.addSql("   CMN_USRM.USR_SID = RNG_SINGI.USR_SID");
            sql.addSql(" left join ");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" ON");
            sql.addSql("   CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql(" where");
            sql.addSql("   RNG_SINGI.RNG_SID IN (");
            boolean firstFlg = false;
            for (int rngSid : rngSidList) {
                if (firstFlg) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(rngSid);
                firstFlg = true;
            }
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   RNG_SINGI.RKS_SID,");
            __setOrderSQLCmnSort(sql);
            sql.addSql("   ,RNG_SINGI.USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Rng030SingiParam model = new Rng030SingiParam();

                //経路SID
                model.setKeiroSid(rs.getInt("RKS_SID"));
                //ユーザSID
                model.setUserSid(rs.getInt("USR_SID"));
                //ユーザ名
                model.setSingiName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                //ユーザ状態区分
                model.setUserJkbn(rs.getInt("USR_JKBN"));
                //ログイン有効フラグ
                model.setUserUkoFlg(rs.getInt("USR_UKO_FLG"));
                //代理人SID
                model.setDairiSid(rs.getInt("USR_SID_DAIRI"));
                //代理人名
                String sDairi = rs.getString("DAIRI_SEI") + " " + rs.getString("DAIRI_MEI");
                if (sDairi.equals("null null") || (sDairi.equals(""))) {
                    sDairi = "";
                    model.setSingiDairiFlg(1);
                }
                model.setSingiDairi(sDairi);
                //後閲指示者名
                String sKoetu = rs.getString("KOETU_SEI") + " " + rs.getString("KOETU_MEI");
                if (sKoetu.equals("null null") || (sKoetu.equals(""))) {
                    sKoetu = "";
                }
                model.setSingiKoetu(sKoetu);
                //コメント
                model.setSingiComment(rs.getString("RSS_COMMENT"));
                if (StringUtil.isNullZeroString(rs.getString("RSS_COMMENT"))) {
                    model.setSingiCommentFlg(1);
                }
                //確認日

                if (StringUtil.isNullZeroString(rs.getString("RSS_CHKDATE"))) {
                    model.setSingiCheckFlg(1);
                } else {
                    String sDate = rs.getString("RSS_CHKDATE");
                    int nTime = sDate.indexOf(":");
                    nTime -= 2;
                    String slashDate = sDate.substring(0, nTime);
                    slashDate = slashDate.replace("-", "/");
                    model.setSingiDate(slashDate);
                    model.setSingiTime(sDate.substring(nTime, nTime + 5));
                }
                //役職
                if (rs.getInt("POS_SID") == 0
                        || (rs.getInt("POS_SID") != 0 && rs.getString("POS_NAME") == null)) {
                    model.setSingiPosition("");
                } else {
                    model.setSingiPosition(rs.getString("POS_NAME"));
                }

                //状態
                model.setSingiStatus(rs.getInt("RSS_STATUS"));
                ret.add(model);
                //添付情報一覧を設定
                model.setTmpFileList(getSingiTemp(rs.getInt("RKS_SID"),
                                                         rs.getInt("USR_SID")));
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
     * <br>[機  能] 経路ステップ情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSidList ユーザSID一覧
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public HashMap<Integer, RngCsvUserModel> getKeiroUserList(List<Integer> usrSidList)
            throws SQLException {

        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashMap<Integer, RngCsvUserModel> ret = new HashMap<Integer, RngCsvUserModel>();

        if (usrSidList == null || usrSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else CMN_POSITION.POS_SORT");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" left join ");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" ON");
            sql.addSql("   CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM_INF.USR_SID in (");
            StringBuffer strBuf = new StringBuffer();
            for (Integer grpSid :usrSidList) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append(grpSid.intValue());
            }
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");
            sql.addSql(" order by");
            __setOrderSQLCmnSort(sql);
            sql.addSql("   ,CMN_USRM_INF.USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            int sortIdx = 0;
            while (rs.next()) {
                RngCsvUserModel mdl = new RngCsvUserModel();
                mdl.setSid(rs.getInt("USR_SID"));
                mdl.setName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                mdl.setPos(rs.getInt("POS_SID"));
                mdl.setSort(sortIdx);
                ret.put(Integer.valueOf(mdl.getSid()), mdl);
                sortIdx++;
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
     * <br>[機  能] 稟議審議情報件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param  nSid 稟議経路ステップSID
     * @return 稟議情報
     * @throws SQLException SQL実行例外
     */
    public int getSingiCount(int nSid) throws SQLException {
        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" count(*) as COUNT");
            sql.addSql(" from");
            sql.addSql(" RNG_SINGI");
            sql.addSql(" where");
            sql.addSql(" RNG_SINGI.RKS_SID = ?");

            sql.addIntValue(nSid);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("COUNT");
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
     * <br>[機  能] 稟議経路情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return 稟議経路情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<RingiChannelDataModel> getChannelList(int rngSid) throws SQLException {

        Connection con = null;
        con = getCon();

        //稟議プラグインの使用制限を取得
        //プラグインアクセス設定を取得
        CmnPluginControlDao cntrlDao = new CmnPluginControlDao(con);
        CmnPluginControlModel cntrlModel = cntrlDao.select(RngConst.PLUGIN_ID_RINGI);

        //プラグインアクセス制限方法を取得
        boolean rngControl = false;
        int pctType = 0;
        if (cntrlModel != null) {
            if (cntrlModel.getPctKbn() == GSConstMain.PCT_KBN_MEMBER) {
                rngControl = true;
                pctType = cntrlModel.getPctType();
            }
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<RingiChannelDataModel> ret = new ArrayList<RingiChannelDataModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SINGI.USR_SID as USR_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT as RNC_SORT,");
            sql.addSql("   RNG_SINGI.RSS_STATUS as RNC_STATUS,");
            sql.addSql("   RNG_SINGI.RSS_COMMENT as RNC_COMMENT,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_RCVDATE as RNC_RCVDATE,");
            sql.addSql("   RNG_SINGI.RSS_CHKDATE as RNC_CHKDATE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE as RNC_TYPE,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU");
            if (rngControl) {
                sql.addSql("    ,coalesce(RNG_CONTROL_MEMBER.MEMBER_SID, -1) as MEMBER_SID");
            }
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" left join");
            sql.addSql("   RNG_SINGI ");
            sql.addSql(" on");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            if (rngControl) {
                sql.addSql("    left join");
                sql.addSql("      (");
                sql.addSql("        select");
                sql.addSql("          case CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
                sql.addSql("          when -1 then CMN_BELONGM.USR_SID");
                sql.addSql("          else CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
                sql.addSql("          end as MEMBER_SID");
                sql.addSql("        from");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("          left join");
                sql.addSql("            CMN_BELONGM");
                sql.addSql("          on");
                sql.addSql("            CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("        where PCT_PID = ?");
                sql.addSql("        group by MEMBER_SID");
                sql.addSql("      ) RNG_CONTROL_MEMBER");
                sql.addSql("    on");
                sql.addSql("      CMN_USRM_INF.USR_SID = RNG_CONTROL_MEMBER.MEMBER_SID");
                sql.addStrValue(RngConst.PLUGIN_ID_RINGI);
            }
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            sql.addSql(" order by");
            sql.addSql("   RNC_SORT");
            sql.addIntValue(rngSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());


            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RingiChannelDataModel model = new RingiChannelDataModel();
                model.setUsrSid(rs.getInt("USR_SID"));
                model.setRncSort(rs.getInt("RNC_SORT"));
                model.setRncStatus(rs.getInt("RNC_STATUS"));
                String comment = NullDefault.getString(rs.getString("RNC_COMMENT"), "");
                comment = StringUtilHtml.transToHTmlForTextArea(comment);
                comment = StringUtil.transToLink(comment, StringUtil.OTHER_WIN, true);
                comment = StringUtilHtml.returntoBR(comment);
                model.setRncComment(comment);
                model.setRncRcvdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_RCVDATE")));
                model.setRncChkdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_CHKDATE")));
                model.setStrRncChkDate(__createDateStr(model.getRncChkdate()));
                model.setRncType(rs.getInt("RNC_TYPE"));
                model.setDelUser(rs.getInt("USR_JKBN") == GSConst.JTKBN_DELETE);
                model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                model.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                model.setYakusyoku(rs.getString("USI_YAKUSYOKU"));
                if (rngControl) {
                    if (pctType == GSConstMain.PCT_TYPE_LIMIT) {
                        model.setRingiUse(rs.getInt("MEMBER_SID") >= 0);
                    } else {
                        model.setRingiUse(rs.getInt("MEMBER_SID") < 0);
                    }
                } else {
                    model.setRingiUse(true);
                }

                //添付情報一覧を設定
                model.setTmpFileList(getRingiTmpFileList(rngSid, model.getUsrSid()));

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
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 稟議SqlBuffer
     * @param fieldName フィールドネーム
     * @param list List<Integer>
     */
    private void __addWhereInIntList(SqlBuffer sql, String fieldName, List<Integer> list) {
        if (list.size() <= 0) {
            return;
        }
        if (list.size() == 1) {
            sql.addSql(" " + fieldName + " = ? ");
            sql.addIntValue(list.get(0));
            return;
        }
        sql.addSql(" ( ");
        int index = 0;
        for (ListIterator<Integer> it = list.listIterator(); it.hasNext(); index++) {
            Integer val = it.next();
            if (index % 100 == 0) {
                if (index != 0) {
                    sql.addSql(" ) or ");
                }
                sql.addSql(fieldName + " in (");
            } else {
                sql.addSql(" , ");
            }
            sql.addSql(String.valueOf(val));
        }
        sql.addSql(" ) ) ");
    }
    /**
     * <br>[機  能] 稟議経路情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID
     * @return rngSidごとの稟議経路情報一覧マップ
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, List<RingiKeiroNameModel>> getKeiroName(List<Integer> rngSidList)
            throws SQLException {

        Connection con = null;
        con = getCon();


        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, List<RingiKeiroNameModel>> ret =
                new HashMap<Integer, List<RingiKeiroNameModel>>();
        if (rngSidList == null || rngSidList.size() == 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql(" select ");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID, ");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID, ");
            sql.addSql("   RNG_KEIRO_STEP.RKS_STATUS, ");
            sql.addSql("   RNG_SINGI.USR_SID as USR_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT as RNC_SORT,");
            sql.addSql("   RNG_SINGI.RSS_STATUS as RNC_STATUS,");
            sql.addSql("   RNG_SINGI.RSS_COMMENT as RNC_COMMENT,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_RCVDATE as RNC_RCVDATE,");
            sql.addSql("   RNG_SINGI.RSS_CHKDATE as RNC_CHKDATE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE as RNC_TYPE,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN");

            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   RNG_KEIRO_STEP");

            sql.addSql(" where");
            __addWhereInIntList(sql, "RNG_KEIRO_STEP.RNG_SID", rngSidList);
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");

            sql.addSql(" order by");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID, RNC_SORT");
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());


            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RingiKeiroNameModel model = new RingiKeiroNameModel();
                model.setRngSid(rs.getInt("RNG_SID"));
                model.setRksSid(rs.getInt("RKS_SID"));
                model.setRksStatus(rs.getInt("RKS_STATUS"));

                model.setUsrgrpName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));

                model.setRncSort(rs.getInt("RNC_SORT"));
                model.setRncStatus(rs.getInt("RNC_STATUS"));
                model.setUsrSid(rs.getInt("USR_SID"));
                String comment = NullDefault.getString(rs.getString("RNC_COMMENT"), "");
                comment = StringUtilHtml.transToHTmlForTextArea(comment);
                comment = StringUtil.transToLink(comment, StringUtil.OTHER_WIN, true);
                comment = StringUtilHtml.returntoBR(comment);
                model.setRncComment(comment);
                model.setRncRcvdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_RCVDATE")));
                model.setRncChkdate(UDate.getInstanceTimestamp(rs.getTimestamp("RNC_CHKDATE")));
                model.setStrRncChkDate(__createDateStr(model.getRncChkdate()));
                model.setRncType(rs.getInt("RNC_TYPE"));

                model.setDelUser(rs.getInt("USR_JKBN") == GSConst.JTKBN_DELETE);
                model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));

                model.setUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));

                List<RingiKeiroNameModel> keiroList;
                if (ret.containsKey(model.getRngSid())) {
                    keiroList = ret.get(model.getRngSid());
                } else {
                    keiroList = new ArrayList<RingiKeiroNameModel>();
                    ret.put(model.getRngSid(), keiroList);
                }
                keiroList.add(model);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        // 経路一覧に追加情報をセット
        this.setKeiroSelectMap(ret);

        return ret;
    }

    /**
     * <br>[機  能] 稟議経路情報一覧に追加情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param map 稟議経路情報一覧
     * @throws SQLException SQL実行例外
     */
    private void setKeiroSelectMap(Map<Integer, List<RingiKeiroNameModel>> map)
            throws SQLException {

        Connection con = null;
        con = getCon();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 経路一覧から経路ステップSID一覧を取得
        ArrayList<Integer> rksSidList = new ArrayList<Integer>();
        if (map != null && map.size() > 0) {
            for (Entry<Integer, List<RingiKeiroNameModel>> entry : map.entrySet()) {
                List<RingiKeiroNameModel> keiroList = entry.getValue();
                for (RingiKeiroNameModel model : keiroList) {
                    Integer rksSid = Integer.valueOf(model.getRksSid());
                    if (!rksSidList.contains(rksSid)) { // 重複した経路ステップSIDは除外
                        rksSidList.add(rksSid);
                    }
                }
            }
        }

        if (rksSidList.size() == 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   CNT.RKS_SID       as RKS_SID,");
            sql.addSql("   CNT.RKS_COUNT     as RKS_COUNT,");
            sql.addSql("   GRP_NAME.GRP_NAME as GRP_NAME,");
            sql.addSql("   GRP_NAME.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   POS_NAME.POS_NAME as USI_YAKUSYOKU,");
            sql.addSql("   case");
            sql.addSql("     when POS_NAME.POS_SID IS NULL then -1");
            sql.addSql("     else POS_NAME.POS_SID");
            sql.addSql("   end as POS_SID");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RKS_SID,");
            sql.addSql("      count(RKS_SID) as RKS_COUNT");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIROSTEP_SELECT");
            sql.addSql("    where");
            __addWhereInIntList(sql, "RKS_SID", rksSidList);
            sql.addSql("    group by RKS_SID");
            sql.addSql("   ) CNT");
            sql.addSql(" left join");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RNG_KEIROSTEP_SELECT.RKS_SID,");
            sql.addSql("      CMN_GROUPM.GRP_NAME,");
            sql.addSql("      CMN_GROUPM.GRP_JKBN");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIROSTEP_SELECT,");
            sql.addSql("      CMN_GROUPM");
            sql.addSql("    where");
            __addWhereInIntList(sql, "RNG_KEIROSTEP_SELECT.RKS_SID", rksSidList);
            sql.addSql("    and RNG_KEIROSTEP_SELECT.GRP_SID >= 0");
            sql.addSql("    and");
            sql.addSql("      RNG_KEIROSTEP_SELECT.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("   ) GRP_NAME");
            sql.addSql(" on");
            sql.addSql("   CNT.RKS_SID = GRP_NAME.RKS_SID");
            sql.addSql(" left join");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RNG_KEIROSTEP_SELECT.RKS_SID,");
            sql.addSql("      CMN_POSITION.POS_NAME,");
            sql.addSql("      CMN_POSITION.POS_SID");
            sql.addSql("    from");
            sql.addSql("      RNG_KEIROSTEP_SELECT,");
            sql.addSql("      CMN_POSITION");
            sql.addSql("    where");
            __addWhereInIntList(sql, "RNG_KEIROSTEP_SELECT.RKS_SID", rksSidList);
            sql.addSql("    and RNG_KEIROSTEP_SELECT.POS_SID >= 0");
            sql.addSql("    and");
            sql.addSql("      RNG_KEIROSTEP_SELECT.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("   ) POS_NAME");
            sql.addSql(" on");
            sql.addSql("   CNT.RKS_SID = POS_NAME.RKS_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            HashMap<Integer, LabelValueBean> grpNameMap = new HashMap<Integer, LabelValueBean>();
            HashMap<Integer, LabelValueBean> posNameMap = new HashMap<Integer, LabelValueBean>();
            HashMap<Integer, Integer>        rksCntMap  = new HashMap<Integer, Integer>();

            while (rs.next()) {
                Integer key     = rs.getInt("RKS_SID");
                int     posSid  = rs.getInt("POS_SID");
                String  posName = rs.getString("USI_YAKUSYOKU");
                String  grpName = rs.getString("GRP_NAME");
                int     grpJkbn = rs.getInt("GRP_JKBN");

                rksCntMap.put(key, rs.getInt("RKS_COUNT"));
                posNameMap.put(key, new LabelValueBean(String.valueOf(posSid), posName));

                if (!StringUtil.isNullZeroString(grpName)) {
                    grpNameMap.put(key, new LabelValueBean(grpName, String.valueOf(grpJkbn)));
                }
            }

            // 経路に各情報をセット
            for (Entry<Integer, List<RingiKeiroNameModel>> entry : map.entrySet()) {
                List<RingiKeiroNameModel> keiroList = entry.getValue();
                for (RingiKeiroNameModel model : keiroList) {
                    Integer key = Integer.valueOf(model.getRksSid());

                    // 経路件数
                    int rksCount   = 0;
                    if (rksCntMap.containsKey(key)) {
                        rksCount = rksCntMap.get(key).intValue();
                    }
                    model.setRksCount(rksCount);

                    // 役職名＋SID
                    int    posSid  = -1;
                    String posName = null;
                    if (posNameMap.containsKey(key)) {
                        LabelValueBean bean = posNameMap.get(key);
                        posSid  = Integer.valueOf(bean.getLabel()).intValue();
                        posName = bean.getValue();
                    }
                    model.setPosSid(posSid);
                    model.setYakusyoku(posName);

                    // グループ名
                    if (grpNameMap.containsKey(key)) {
                        LabelValueBean bean = grpNameMap.get(key);
                        model.setUsrgrpName("[G]" + bean.getLabel());
                        model.setGroupDelFlg(Integer.valueOf(bean.getValue()).intValue());
                    }

                    // 経路１件以上 or グループ名表示 → ユーザ状態情報を初期化
                    if (model.getRksCount() > 1 || grpNameMap.containsKey(key)) {
                        model.setDelUser(false);
                        model.setUsrUkoFlg(0);
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>指定されたSID分のユーザー情報数を取得します
     * @param usids ユーザーSID 配列
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public String getUsrInfCount(String[] usids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String cnt = "0";
        con = getCon();

        if (usids == null) {
            return cnt;
        }
        if (usids.length < 1) {
            return cnt;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as COUNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF, CMN_POSITION");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID ");
            sql.addSql(" and ");
            sql.addSql("   USR_SID in ( ");

            for (int i = 0; i < usids.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(usids[i]));
            }
            sql.addSql("        )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cnt = rs.getString("COUNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt;
    }

    /**
     * <br>[機  能] 稟議情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param procMode 処理モード
     * @return 稟議情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getRingiDataCount(int userSid, int procMode)
    throws SQLException {

        RingiSearchModel model = new RingiSearchModel();
        model.setKeyword(null);
        model.setGroupSid(-1);
        model.setUserSid(userSid);
        model.setAdminFlg(false);

        return getRingiDataCount(model, procMode);
    }
    /**
     * <br>[機  能] 稟議情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param procMode 処理モード
     * @param categorySid カテゴリSID
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public int getRingiDataCount(int userSid,
            int procMode,
            int categorySid)
    throws SQLException {

        RingiSearchModel model = new RingiSearchModel();
        model.setKeyword(null);
        model.setGroupSid(-1);
        model.setUserSid(userSid);
        model.setAdminFlg(false);
        model.setCategorySid(categorySid);

        return getRingiDataCount(model, procMode);
    }

    /**
     * <br>[機  能] 稟議情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議検索条件
     * @param procMode 処理モード
     * @return 稟議情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getRingiDataCount(RingiSearchModel model, int procMode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(distinct RNDATA.RNG_SID) as RNGCOUNT");

            //処理モードにより検索条件を判断する
            switch (procMode) {
            case RngConst.RNG_MODE_JYUSIN :
                __createSearchJyusinRingiListSql(sql, model);
                break;
            case RngConst.RNG_MODE_SINSEI :
                __createSearchSinseiRingiListSql(sql, model);
                break;
            case RngConst.RNG_MODE_KANRYO :
                __createSearchKanryoRingiListSql(sql, model);
                break;
            case RngConst.RNG_MODE_SOUKOU :
                __createSearchSoukouRingiListSql(sql, model);
                break;
            case RngConst.RNG_MODE_KOETU :
                __createSearchKoetuRingiListSql(sql, model);
                break;
            default :
                return 0;
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("RNGCOUNT");
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
     * <br>[機  能] 稟議情報一覧(申請中)の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getSinseiRingiDataCount(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(distinct RNDATA.RNG_SID) as RNGCOUNT");
            __createSearchSinseiRingiListSql(sql, model);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("RNGCOUNT");
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
     * <br>[機  能] 稟議情報一覧(完了)の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getKanryoRingiDataCount(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(distinct RNDATA.RNG_SID) as RNGCOUNT");
            __createSearchKanryoRingiListSql(sql, model);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("RNGCOUNT");
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
     * <br>[機  能] 稟議情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param procMode 処理モード
     * @param sortKey ソート対象
     * @param orderKey 並び順
     * @param page ページ
     * @param maxCnt ページ毎の最大表示件数
     * @param categorySid カテゴリSID
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getRingiDataList(int userSid, int procMode,
                                                    int sortKey, int orderKey,
                                                    int page, int maxCnt, int categorySid)
    throws SQLException {

        RingiSearchModel model = new RingiSearchModel();
        model.setKeyword(null);
        model.setUserSid(userSid);
        model.setSortKey(sortKey);
        model.setOrderKey(orderKey);
        model.setPage(page);
        model.setMaxCnt(maxCnt);
        model.setAdminFlg(false);
        model.setCategorySid(categorySid);

        //進行中の場合、第２ソートキーに申請日時を設定
        if (procMode == RngConst.RNG_MODE_SINSEI) {
            model.setSortKey2(RngConst.RNG_SORT_DATE);
            model.setOrderKey2(RngConst.RNG_ORDER_DESC);
        }

        return getRingiDataList(model, procMode);
    }

    /**
     * <br>[機  能] 稟議情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @param procMode 処理モード
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getRingiDataList(RingiSearchModel model, int procMode)
    throws SQLException {

        List<RingiDataModel> ringiList = null;

        switch (procMode) {
        case RngConst.RNG_MODE_JYUSIN :
            ringiList = getJyusinRingiDataList(model);
            break;
        case RngConst.RNG_MODE_SINSEI :
            ringiList = getSinseiRingiDataList(model);
            break;
        case RngConst.RNG_MODE_KANRYO :
            ringiList = getKanryoRingiDataList(model);
            break;
        case RngConst.RNG_MODE_SOUKOU :
            ringiList = __getSoukouRingiDataList(model);
            break;
        case RngConst.RNG_MODE_KOETU :
            ringiList = getKoetuRingiDataList(model);
            break;
        default :
            ringiList = new ArrayList<RingiDataModel>();
        }

        return ringiList;
    }

    /**
     * <br>[機  能] 稟議情報一覧(受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getJyusinRingiDataList(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct ");
            sql.addSql("   RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("   RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   USI_SEI,");
            sql.addSql("   USI_MEI,");
            sql.addSql("   USI_SEI_KN,");
            sql.addSql("   USI_MEI_KN,");
            sql.addSql("   USR_JKBN,");
            sql.addSql("   USR_UKO_FLG,");
            sql.addSql("   APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RNC_TYPE,");
            sql.addSql("   RCVDATE");
            __createSearchJyusinRingiListSql(sql, model);
            //ソート
            sql.addSql(" order by");
            sql.addSql(__createJyusinRingiOrderSql(model.getSortKey(), model.getOrderKey()));
            sql.addSql("," + __createJyusinRingiOrderSql(model.getSortKey2(),
                                                            model.getOrderKey2()));
            int page = model.getPage();
            if (page <= 0) {
                page = 1;
            }
            int maxCnt = model.getMaxCnt();
            sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);


            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                RingiDataModel rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                rngMdl.setRngId(rs.getString("RNG_ID"));
                rngMdl.setRngTitle(rs.getString("RNG_TITLE"));
                rngMdl.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                rngMdl.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                rngMdl.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);
                rngMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                rngMdl.setRngAppldate(UDate.getInstanceTimestamp(rs.getTimestamp("APPLDATE")));
                rngMdl.setStrRngAppldate(__createDateStr(rngMdl.getRngAppldate()));
                rngMdl.setRncType(rs.getInt("RNC_TYPE"));
                rngMdl.setRcvDate(UDate.getInstanceTimestamp(rs.getTimestamp("RCVDATE")));
                rngMdl.setStrRcvDate(__createDateStr(rngMdl.getRcvDate()));

                rngMdl.setRngStatus(rs.getInt("RNG_STATUS"));
                rngMdl.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                rngMdl.setRngAdmcomment(rs.getString("RNG_ADMCOMMENT"));
                rngMdl.setRtpSid(rs.getInt("RTP_SID"));
                rngMdl.setRtpVer(rs.getInt("RTP_VER"));

                ret.add(rngMdl);
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
     * <br>[機  能] 稟議情報(受信)のソート部分のSQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortKey ソート対象
     * @param orderKey ソート順
     * @return ソート部分のSQL
     */
    private String __createJyusinRingiOrderSql(int sortKey, int orderKey) {

        StringBuilder sortString = new StringBuilder("");

        switch (sortKey) {
        case RngConst.RNG_SORT_TITLE :
            sortString.append("   RNG_TITLE");
            break;
        case RngConst.RNG_SORT_NAME :
            sortString.append("   USI_SEI_KN");
            if (orderKey == RngConst.RNG_ORDER_DESC) {
                sortString.append(" desc");
            }
            sortString.append(", USI_MEI_KN");
            break;
        case RngConst.RNG_SORT_DATE :
            sortString.append("   APPLDATE");
            break;
        case RngConst.RNG_SORT_JYUSIN :
            sortString.append("   RCVDATE");
            break;
        default :
            sortString.append("   RNG_TITLE");
            break;
        }
        if (orderKey == RngConst.RNG_ORDER_DESC) {
            sortString.append(" desc");
        }
        return sortString.toString();
    }

    /**
     * <br>[機  能] 稟議情報一覧(進行中)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getSinseiRingiDataList(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("   RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   RNG_APPLICATE,");
            sql.addSql("   USI_SEI,");
            sql.addSql("   USI_MEI,");
            sql.addSql("   USI_SEI_KN,");
            sql.addSql("   USI_MEI_KN,");
            sql.addSql("   USR_JKBN,");
            sql.addSql("   USR_UKO_FLG,");
            sql.addSql("   APPLDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   CHKDATE");
            __createSearchSinseiRingiListSql(sql, model);

            //ソート
            sql.addSql(" order by");
            sql.addSql(__createSinseiRingiSortSql(model.getSortKey(), model.getOrderKey()));
            sql.addSql(", " + __createSinseiRingiSortSql(model.getSortKey2(),
                    model.getOrderKey2()));
            int page = model.getPage();
            int maxCnt = model.getMaxCnt();
            if (page <= 0) {
                page = 1;
            }
            sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RingiDataModel rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                rngMdl.setRngId(rs.getString("RNG_ID"));
                rngMdl.setRngTitle(rs.getString("RNG_TITLE"));
                rngMdl.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                rngMdl.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                rngMdl.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);
                rngMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                rngMdl.setRngAppldate(UDate.getInstanceTimestamp(rs.getTimestamp("APPLDATE")));
                rngMdl.setStrRngAppldate(__createDateStr(rngMdl.getRngAppldate()));
                rngMdl.setLastManageDate(UDate.getInstanceTimestamp(rs.getTimestamp("CHKDATE")));
                rngMdl.setStrLastManageDate(__createDateStr(rngMdl.getLastManageDate()));

                rngMdl.setRngStatus(rs.getInt("RNG_STATUS"));
                rngMdl.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                rngMdl.setRngAdmcomment(rs.getString("RNG_ADMCOMMENT"));
                rngMdl.setRtpSid(rs.getInt("RTP_SID"));
                rngMdl.setRtpVer(rs.getInt("RTP_VER"));

                ret.add(rngMdl);
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
     * <br>[機  能] 稟議情報一覧(申請)取得SQLのソート部分SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortKey ソート対象
     * @param orderKey ソート順
     * @return ソート部分SQL
     */
    private String __createSinseiRingiSortSql(int sortKey, int orderKey) {
        StringBuilder sortString = new StringBuilder("");
        switch (sortKey) {
        case RngConst.RNG_SORT_TITLE :
            sortString.append("   RNG_TITLE");
            break;
        case RngConst.RNG_SORT_NAME :
            sortString.append("   USI_SEI_KN");
            if (orderKey == RngConst.RNG_ORDER_DESC) {
                sortString.append(" desc");
            }
            sortString.append(", USI_MEI_KN");
            break;
        case RngConst.RNG_SORT_DATE :
            sortString.append("   APPLDATE");
            break;
        case RngConst.RNG_SORT_KAKUNIN :
            sortString.append("   CHKDATE");
            break;
        default :
            sortString.append("   RNG_TITLE");
            break;
        }
        if (orderKey == RngConst.RNG_ORDER_DESC) {
            sortString.append(" desc");
        }
        return sortString.toString();
    }

    /**
     * <br>[機  能] 稟議情報一覧(完了)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getKanryoRingiDataList(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct ");
            sql.addSql("  RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("  RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("  RNG_TITLE,");
            sql.addSql("  RNG_APPLICATE,");
            sql.addSql("  USI_SEI,");
            sql.addSql("  USI_MEI,");
            sql.addSql("  USI_SEI_KN,");
            sql.addSql("  USI_MEI_KN,");
            sql.addSql("  USR_JKBN,");
            sql.addSql("  USR_UKO_FLG,");
            sql.addSql("  APPLDATE,");
            sql.addSql("  RNG_STATUS,");
            sql.addSql("  RNG_COMPFLG,");
            sql.addSql("  RNG_ADMCOMMENT,");
            sql.addSql("  RTP_SID,");
            sql.addSql("  RTP_VER,");
            sql.addSql("  CHKDATE");

            __createSearchKanryoRingiListSql(sql, model);

            //第1ソート
            sql.addSql(" order by");
            sql.addSql(__createKanryoRingiSortSql(model.getSortKey(), model.getOrderKey()));
            //第2ソート
            sql.addSql(", " + __createKanryoRingiSortSql(model.getSortKey2(),
                    model.getOrderKey2()));

            int page = model.getPage();
            int maxCnt = model.getMaxCnt();
            if (page <= 0) {
                page = 1;
            }
            sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);;
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                RingiDataModel rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                rngMdl.setRngId(rs.getString("RNG_ID"));
                rngMdl.setRngTitle(rs.getString("RNG_TITLE"));
                rngMdl.setRngStatus(rs.getInt("RNG_STATUS"));
                rngMdl.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                rngMdl.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                rngMdl.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);
                rngMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                rngMdl.setRngAppldate(UDate.getInstanceTimestamp(rs.getTimestamp("APPLDATE")));
                rngMdl.setStrRngAppldate(__createDateStr(rngMdl.getRngAppldate()));
                rngMdl.setLastManageDate(UDate.getInstanceTimestamp(rs.getTimestamp("CHKDATE")));
                rngMdl.setStrLastManageDate(__createDateStr(rngMdl.getLastManageDate()));

                rngMdl.setRngStatus(rs.getInt("RNG_STATUS"));
                rngMdl.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                rngMdl.setRngAdmcomment(rs.getString("RNG_ADMCOMMENT"));
                rngMdl.setRtpSid(rs.getInt("RTP_SID"));
                rngMdl.setRtpVer(rs.getInt("RTP_VER"));

                ret.add(rngMdl);
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
     * <br>[機  能] 稟議情報一覧(完了)取得SQLのソート部分SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortKey ソート対象
     * @param orderKey ソート順
     * @return ソート部分SQL
     */
    private String __createKanryoRingiSortSql(int sortKey, int orderKey) {
        StringBuilder sortString = new StringBuilder("");
        switch (sortKey) {
        case RngConst.RNG_SORT_TITLE :
            sortString.append("   RNG_TITLE");
            break;
        case RngConst.RNG_SORT_NAME :
            sortString.append("   USI_SEI_KN");
            if (orderKey == RngConst.RNG_ORDER_DESC) {
                sortString.append(" desc");
            }
            sortString.append(", USI_MEI_KN");
            break;
        case RngConst.RNG_SORT_KEKKA :
            sortString.append("   RNG_STATUS");
            break;
        case RngConst.RNG_SORT_DATE :
            sortString.append("   APPLDATE");
            break;
        case RngConst.RNG_SORT_KAKUNIN :
            sortString.append("   CHKDATE");
            break;
        default :
            sortString.append("   RNG_TITLE");
            break;
        }
        if (orderKey == RngConst.RNG_ORDER_DESC) {
            sortString.append(" desc");
        }

        return sortString.toString();
    }

    /**
     * <br>[機  能] 指定した稟議の申請者がメール送信対象のユーザか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @return true:通知する false:通知しない
     * @throws SQLException SQL実行例外
     */
    public boolean isApplicateSmUser(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = true;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   count(USR_SID) as USRCNT");
            sql.addSql(" from");
            sql.addSql("   RNG_UCONF");
            sql.addSql(" where");
            sql.addSql("   RUR_SML_NTF = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID in (");
            sql.addSql("     select RNG_APPLICATE from RNG_RNDATA");
            sql.addSql("     where RNG_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(RngConst.RNG_SMAIL_NOT_TSUUCHI);
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("USRCNT") == 0) {
                    result = true;
                }
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
     * <br>[機  能] 稟議情報一覧(草稿)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    private List <RingiDataModel> __getSoukouRingiDataList(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   RNDATA.RNG_SID as RNG_SID,");
            sql.addSql("   RNDATA.RNG_ID as RNG_ID,");
            sql.addSql("   RNG_TITLE,");
            sql.addSql("   MAKEDATE,");
            sql.addSql("   RNG_STATUS,");
            sql.addSql("   RNG_COMPFLG,");
            sql.addSql("   RNG_ADMCOMMENT,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER");
            __createSearchSoukouRingiListSql(sql, model);

            //ソート
            sql.addSql(" order by");
            sql.addSql(__createSoukouRingiSortSql(model.getSortKey(), model.getOrderKey()));
            sql.addSql(", " + __createSoukouRingiSortSql(model.getSortKey2(),
                                                        model.getOrderKey2()));
            int page = model.getPage();
            if (page <= 0) {
                page = 1;
            }
            int maxCnt = model.getMaxCnt();
            sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RingiDataModel rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                rngMdl.setRngId(rs.getString("RNG_ID"));
                rngMdl.setRngTitle(rs.getString("RNG_TITLE"));
                rngMdl.setMakeDate(UDate.getInstanceTimestamp(rs.getTimestamp("MAKEDATE")));
                rngMdl.setStrMakeDate(__createDateStr(rngMdl.getMakeDate()));

                rngMdl.setRngStatus(rs.getInt("RNG_STATUS"));
                rngMdl.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                rngMdl.setRngAdmcomment(rs.getString("RNG_ADMCOMMENT"));
                rngMdl.setRtpSid(rs.getInt("RTP_SID"));
                rngMdl.setRtpVer(rs.getInt("RTP_VER"));

                ret.add(rngMdl);
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
     * <br>[機  能] 稟議情報一覧(草稿)取得SQLのソート部分SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortKey ソート対象
     * @param orderKey ソート順
     * @return ソート部分SQL
     */
    private String __createSoukouRingiSortSql(int sortKey, int orderKey) {
        StringBuilder sortString = new StringBuilder("");
        switch (sortKey) {
        case RngConst.RNG_SORT_TITLE :
            sortString.append("   RNG_TITLE");
            break;
        case RngConst.RNG_SORT_TOUROKU :
            sortString.append("   MAKEDATE");
            break;
        default :
            sortString.append("   RNG_TITLE");
        }
        if (orderKey == RngConst.RNG_ORDER_DESC) {
            sortString.append(" desc");
        }

        return sortString.toString();
    }

    /**
     * <br>[機  能] 稟議情報一覧(後閲)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 稟議情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getKoetuRingiDataList(RingiSearchModel model)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select distinct");
            sql.addSql("   RNDATA.RNG_SID as RNG_SID,");               //稟議SID
            sql.addSql("   RNDATA.RNG_ID as RNG_ID,"); //申請ID
            sql.addSql("   RNG_TITLE,");           //タイトル
            sql.addSql("   RNG_APPLICATE,");   //申請者SID
            sql.addSql("   USI_SEI,");             //申請者姓
            sql.addSql("   USI_MEI,");             //申請者名
            sql.addSql("   USI_SEI_KN,");       //申請者姓ｶﾅ
            sql.addSql("   USI_MEI_KN,");       //申請者名ｶﾅ
            sql.addSql("   USR_JKBN,");               //申請者使用フラグ
            sql.addSql("   USR_UKO_FLG,");         //申請者有効フラグ
            sql.addSql("   APPLDATE,");         //申請日
            sql.addSql("   RNG_STATUS,");         //申請状態
            sql.addSql("   RNG_COMPFLG,");       //完了フラグ 0:未定 1:完了
            sql.addSql("   RNG_ADMCOMMENT,"); //管理者コメント
            sql.addSql("   RTP_SID,");               //テンプレートSID
            sql.addSql("   RTP_VER");                //テンプレートバージョン
            __createSearchKoetuRingiListSql(sql, model);

            //ソート
            sql.addSql(" order by");
            sql.addSql(__createKoetuRingiOrderSql(model.getSortKey(), model.getOrderKey()));
            sql.addSql("," + __createKoetuRingiOrderSql(model.getSortKey2(),
                                                        model.getOrderKey2()));
            int page = model.getPage();
            int maxCnt = model.getMaxCnt();
            if (page <= 0) {
                page = 1;
            }
            sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                RingiDataModel rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                rngMdl.setRngId(rs.getString("RNG_ID"));
                rngMdl.setRngTitle(rs.getString("RNG_TITLE"));
                rngMdl.setRngApplicate(rs.getInt("RNG_APPLICATE"));
                rngMdl.setApprUser(
                        rs.getString("USI_SEI").concat(" ").concat(rs.getString("USI_MEI")));
                rngMdl.setApprUserDelFlg(rs.getInt("USR_JKBN") == GSConstUser.USER_JTKBN_DELETE);
                rngMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                rngMdl.setRngAppldate(UDate.getInstanceTimestamp(rs.getTimestamp("APPLDATE")));
                rngMdl.setStrRngAppldate(__createDateStr(rngMdl.getRngAppldate()));
                rngMdl.setRngStatus(rs.getInt("RNG_STATUS"));
                rngMdl.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                rngMdl.setRngAdmcomment(rs.getString("RNG_ADMCOMMENT"));
                rngMdl.setRtpSid(rs.getInt("RTP_SID"));
                rngMdl.setRtpVer(rs.getInt("RTP_VER"));

                ret.add(rngMdl);
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
     * <br>[機  能] 稟議情報(後閲)のソート部分のSQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortKey ソート対象
     * @param orderKey ソート順
     * @return ソート部分のSQL
     */
    private String __createKoetuRingiOrderSql(int sortKey, int orderKey) {

        StringBuilder sortString = new StringBuilder("");

        switch (sortKey) {
        case RngConst.RNG_SORT_TITLE :
            sortString.append("   RNG_TITLE");
            break;
        case RngConst.RNG_SORT_NAME :
            sortString.append("   USI_SEI_KN");
            if (orderKey == RngConst.RNG_ORDER_DESC) {
                sortString.append(" desc");
            }
            sortString.append(", USI_MEI_KN");
            break;
        case RngConst.RNG_SORT_DATE :
            sortString.append("   APPLDATE");
            break;
        default :
            sortString.append("   RNG_TITLE");
        }
        if (orderKey == RngConst.RNG_ORDER_DESC) {
            sortString.append(" desc");
        }
        return sortString.toString();
    }
    /**
     * <br>[機  能] 指定したユーザSID, 稟議SID, ステップSIDが一致する審議情報の有無を確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param rksSid ステップSID
     * @param userSid ユーザSID
     * @return 稟議SID
     * @throws SQLException SQL実行例外
     */
    public boolean chkRngParams(int rngSid, int rksSid, int userSid)
    throws SQLException {
        RngSingiDao rssDao = new RngSingiDao(getCon());
        RngSingiModel rssMdl = rssDao.select(rksSid, userSid);
        if (rssMdl == null) {
            return false;
        }
        return (rssMdl.getRngSid()  == rngSid);
    }

    /**
     * <br>[機  能] 指定したユーザで現在確認中の稟議SIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 稟議SID
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getProgressRingiSidList(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RingiDataModel rngMdl = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    RNG_RNDATA.RNG_SID,");
            sql.addSql("    RNG_RNDATA.RNG_COMPFLG");
            sql.addSql("  from");
            sql.addSql("    RNG_RNDATA");
            sql.addSql("  left join");
            sql.addSql("    RNG_KEIRO_STEP");
            sql.addSql("  on");
            sql.addSql("    RNG_RNDATA.RNG_SID = RNG_KEIRO_STEP.RNG_SID");
            sql.addSql("  left join");
            sql.addSql("    RNG_SINGI");
            sql.addSql("  on");
            sql.addSql("    RNG_RNDATA.RNG_SID = RNG_SINGI.RNG_SID");
            sql.addSql("  where");
            sql.addSql("    RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
            sql.addSql("  and");
            sql.addSql("    RNG_SINGI.USR_SID = ?");
            sql.addSql("  and");
            sql.addSql("    (");
            sql.addSql("       RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("     or");
            sql.addSql("       RNG_SINGI.RSS_STATUS = ?");
            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    RKS_STATUS = ?");

            sql.addIntValue(userSid);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_REAPPLY);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                rngMdl.setRngCompflg(rs.getInt("RNG_COMPFLG"));
                ret.add(rngMdl);
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
     * <br>[機  能] 指定したユーザが最終確認者の稟議SIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 稟議SID
     * @throws SQLException SQL実行例外
     */
    public List <RingiDataModel> getConfirmRingiSidList(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RingiDataModel rngMdl = null;
        List <RingiDataModel> ret = new ArrayList <RingiDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RSS_STATUS=?");
            sql.addSql(" and");
            sql.addSql("   RNG_SID IN ");
            sql.addSql("   ( ");
            sql.addSql("    select ");
            sql.addSql("      RNG_KEIRO_STEP.RNG_SID ");
            sql.addSql("    from ");
            sql.addSql("      RNG_KEIRO_STEP ");
            sql.addSql("    left join ");
            sql.addSql("      RNG_RNDATA ");
            sql.addSql("    on ");
            sql.addSql("      RNG_RNDATA.RNG_SID = RNG_KEIRO_STEP.RNG_SID ");
            sql.addSql("    where ");
            sql.addSql("      RKS_ROLL_TYPE = ? ");
            sql.addSql("    and ");
            sql.addSql("      RNG_STATUS = ? ");
            sql.addSql("   ) ");

            sql.addIntValue(userSid);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
            sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
            sql.addIntValue(RngConst.RNG_STATUS_SETTLED);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                rngMdl = new RingiDataModel();
                rngMdl.setRngSid(rs.getInt("RNG_SID"));
                ret.add(rngMdl);
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
     * <br>[機  能] 指定したユーザの稟議で後閲権限を持っているかを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @return 稟議SID
     * @throws SQLException SQL実行例外
     */
    public boolean getKoetuUserCheck(int rksSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RKS_KOETU_SIZI");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where");
            sql.addSql("   RKS_SID = ?");

            sql.addIntValue(rksSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("RKS_KOETU_SIZI") == 0) {
                    ret = true;
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
     * <br>[機  能] 稟議一覧検索時(受信)の検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model 検索条件
     */
    private void __createSearchJyusinRingiListSql(SqlBuffer sql, RingiSearchModel model) {

        //SQL文
        sql.addSql(" from");
        sql.addSql(" ( select distinct ");
        sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
        sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
        sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");
        sql.addSql("   APPLICATE_INF.USI_SEI as USI_SEI,");
        sql.addSql("   APPLICATE_INF.USI_MEI as USI_MEI,");
        sql.addSql("   APPLICATE_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("   APPLICATE_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("   APPLICATE.USR_JKBN as USR_JKBN,");
        sql.addSql("   APPLICATE.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("   RNG_RNDATA.RNG_APPLDATE as APPLDATE,");
        sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
        sql.addSql("   RNG_RNDATA.RNG_ADMCOMMENT as RNG_ADMCOMMENT,");
        sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
        sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER,");
        sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE as RNC_TYPE,");
        sql.addSql("   RNG_KEIRO_STEP.RKS_RCVDATE as RCVDATE");
        sql.addSql(" from");
        sql.addSql("   RNG_RNDATA,");
        sql.addSql("   RNG_KEIRO_STEP,");
        sql.addSql("   RNG_SINGI,");
        sql.addSql("   CMN_USRM APPLICATE,");
        sql.addSql("   CMN_USRM_INF APPLICATE_INF");

        sql.addSql(" where");
        sql.addSql("   RNG_RNDATA.RNG_STATUS <> ?");
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE = APPLICATE.USR_SID");
        sql.addSql(" and");
        sql.addSql("   APPLICATE.USR_SID = APPLICATE_INF.USR_SID");

        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_SID = RNG_KEIRO_STEP.RNG_SID");
        sql.addSql(" and");
        sql.addSql("   RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");

        sql.addIntValue(RngConst.RNG_STATUS_DRAFT);


        //グループ/ユーザ
        if (model.getUserSid() > 0) {
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID = ?");
            sql.addIntValue(model.getUserSid());
        } else if (model.getGroupSid() > 0) {
            sql.addSql(" and");
            sql.addSql("   RNG_SINGI.USR_SID in (");
            sql.addSql("     select CMN_BELONGM.USR_SID");
            sql.addSql("     from CMN_BELONGM, CMN_USRM");
            sql.addSql("     where CMN_BELONGM.GRP_SID = ?");
            sql.addSql("     and CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(model.getGroupSid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
        }

        //申請日時
        if (model.getApplDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE >= ?");
            UDate date = UDate.getInstance(model.getApplDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (model.getApplDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE <= ?");
            UDate date = UDate.getInstance(model.getApplDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }
        //カテゴリ選択
        if (model.getCategorySid() != -1) {
            if (model.getCategorySid() == 0) {
                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("     RNG_RNDATA.RTP_SID IN");
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          case  when RTP_SID IS NULL then 0 ");
                sql.addSql("                else RTP_SID ");
                sql.addSql("          end RTP_SID");
                sql.addSql("        from");
                sql.addSql("          RNG_TEMPLATE");
                sql.addSql("        where");
                sql.addSql("          RTC_SID = ?");
                sql.addSql("       )");
                sql.addSql("     or");
                sql.addSql("     RNG_RNDATA.RTP_SID = ?");
                sql.addSql("  )");
                sql.addIntValue(model.getCategorySid());
                sql.addIntValue(RngConst.RNG_RTC_SID_NONE);
            } else {
                sql.addSql(" and");
                sql.addSql("  RNG_RNDATA.RTP_SID IN");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       case  when RTP_SID IS NULL then 0 ");
                sql.addSql("             else RTP_SID ");
                sql.addSql("       end RTP_SID");
                sql.addSql("     from");
                sql.addSql("       RNG_TEMPLATE");
                sql.addSql("     where");
                sql.addSql("       RTC_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(model.getCategorySid());
            }
        }

        sql.addSql(" and");
        sql.addSql("   (");
        sql.addSql("     (");
        sql.addSql("         RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_KEIRO_STEP.RKS_STATUS = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_RNDATA.RNG_COMPFLG = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_SINGI.RSS_STATUS = ?");
        sql.addSql("     )");
        sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
        sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
        sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);
        sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
        sql.addSql("     or");
        sql.addSql("     (");
        sql.addSql("         RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_KEIRO_STEP.RKS_STATUS = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_RNDATA.RNG_STATUS = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_RNDATA.RNG_COMPFLG = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_SINGI.RSS_STATUS = ?");
        sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
        sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
        sql.addIntValue(RngConst.RNG_STATUS_SETTLED);
        sql.addIntValue(RngConst.RNG_COMPFLG_COMPLETE);
        sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);

        sql.addSql("     )");
        sql.addSql("     or");
        sql.addSql("     (");
        sql.addSql("         RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_KEIRO_STEP.RKS_STATUS = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_RNDATA.RNG_STATUS = ?");
        sql.addSql("       and");
        sql.addSql("         RNG_RNDATA.RNG_COMPFLG = ?");
        sql.addSql("     )");
        sql.addSql("   )");


        sql.addIntValue(RngConst.RNG_RNCTYPE_APPL);
        sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
        sql.addIntValue(RngConst.RNG_STATUS_REJECT);
        sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);

        sql.addSql(" ) RNDATA");

        //キーワード検索
        __setSearchKeywordSql(sql, model);

    }

    /**
     * <br>[機  能] 稟議一覧検索時(進行中)の検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model 検索条件
     */
    private void __createSearchSinseiRingiListSql(SqlBuffer sql, RingiSearchModel model) {

        //SQL文
        sql.addSql(" from");
        sql.addSql(" ( select distinct ");
        sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
        sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
        sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");
        sql.addSql("   APPLICATE_INF.USI_SEI as USI_SEI,");
        sql.addSql("   APPLICATE_INF.USI_MEI as USI_MEI,");
        sql.addSql("   APPLICATE_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("   APPLICATE_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("   APPLICATE.USR_JKBN as USR_JKBN,");
        sql.addSql("   APPLICATE.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("   RNG_RNDATA.RNG_APPLDATE as APPLDATE,");
        sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
        sql.addSql("   RNG_RNDATA.RNG_ADMCOMMENT as RNG_ADMCOMMENT,");
        sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
        sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER,");
        sql.addSql("   KEIROSTEP.CHKDATE as CHKDATE");
        sql.addSql(" from");
        sql.addSql("   RNG_RNDATA,");
        sql.addSql("   (");
        sql.addSql("     select");
        sql.addSql("       RNG_SID,");
        sql.addSql("       max(RSS_CHKDATE) as CHKDATE");
        sql.addSql("     from");
        sql.addSql("       RNG_SINGI");
        if (model.getUserSid() > 100) {
            sql.addSql("     where");
            sql.addSql("       RNG_SID IN ");
            sql.addSql("       (");
            sql.addSql("        select ");
            sql.addSql("          RNG_SID ");
            sql.addSql("        from ");
            sql.addSql("          RNG_SINGI ");
            sql.addSql("        where ");
            sql.addSql("          USR_SID=?");
            sql.addSql("       )");
            sql.addIntValue(model.getUserSid());
        }
        sql.addSql("     group by");
        sql.addSql("       RNG_SID");
        sql.addSql("   ) KEIROSTEP,");
        sql.addSql("   CMN_USRM APPLICATE,");
        sql.addSql("   CMN_USRM_INF APPLICATE_INF");
        sql.addSql(" where");
        sql.addSql("   RNG_RNDATA.RNG_STATUS <> ?");
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG = ?");
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_SID = KEIROSTEP.RNG_SID");
        sql.addIntValue(RngConst.RNG_STATUS_DRAFT);
        sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);

        int groupSid = model.getGroupSid();
        int userSid = model.getUserSid();
        if (groupSid != -1 || userSid != -1) {
            if (model.isAdminFlg()) {
                sql.addSql(" and");
                if (userSid != -1) {
                    sql.addSql("   RNG_RNDATA.RNG_APPLICATE = ?");
                    sql.addIntValue(userSid);
                } else {
                    sql.addSql("   RNG_RNDATA.RNG_APPLICATE in (");
                    sql.addSql("     select");
                    sql.addSql("       CMN_USRM.USR_SID");
                    sql.addSql("     from");
                    sql.addSql("       CMN_USRM,");
                    sql.addSql("       CMN_BELONGM");
                    sql.addSql("     where");
                    sql.addSql("       CMN_USRM.USR_JKBN = ?");
                    sql.addSql("     and");
                    sql.addSql("       CMN_BELONGM.GRP_SID = ?");
                    sql.addSql("     and");
                    sql.addSql("       CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
                    sql.addSql("     )");
                    sql.addIntValue(GSConst.JTKBN_TOROKU);
                    sql.addIntValue(groupSid);
                }
            } else {
                sql.addSql(" and");
                sql.addSql("   (");
                sql.addSql("        RNG_RNDATA.RNG_APPLICATE = ?");
                sql.addIntValue(userSid);
                sql.addSql("     or");
                sql.addSql("       (");
                sql.addSql("           (");
                sql.addSql("                RNG_RNDATA.RNG_STATUS = ?");
                sql.addSql("              or");
                sql.addSql("                RNG_RNDATA.RNG_STATUS = ?");
                sql.addSql("           )");
                sql.addSql("        and");
                sql.addSql("        RNG_RNDATA.RNG_SID in ");
                sql.addSql("          (");
                sql.addSql("           select");
                sql.addSql("             RNG_KEIRO_STEP.RNG_SID");
                sql.addSql("           from");
                sql.addSql("             RNG_KEIRO_STEP,");
                sql.addSql("             RNG_SINGI,");
                sql.addSql("             CMN_USRM");
                sql.addSql("           where");
                sql.addSql("             RNG_SINGI.USR_SID = ?");
                sql.addSql("           and");
                sql.addSql("             RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
                sql.addSql("           and");
                sql.addSql("             CMN_USRM.USR_JKBN = ?");
                sql.addSql("           and");
                sql.addSql("             RNG_SINGI.USR_SID = CMN_USRM.USR_SID");
                sql.addSql("           and");
                sql.addSql("             RNG_KEIRO_STEP.RKS_SID = RNG_SINGI.RKS_SID");
                sql.addSql("           and");
                sql.addSql("             (");
                sql.addSql("                RNG_KEIRO_STEP.RKS_STATUS = ?");
                sql.addSql("              or");
                sql.addSql("                RNG_KEIRO_STEP.RKS_STATUS = ?");
                sql.addSql("              or");
                sql.addSql("                RNG_KEIRO_STEP.RKS_STATUS = ?");
                sql.addSql("              or");
                sql.addSql("                (");
                sql.addSql("                   RNG_KEIRO_STEP.RKS_STATUS = ?");
                sql.addSql("                 and");
                sql.addSql("                   RNG_SINGI.RSS_STATUS IN (?");
                sql.addSql("                                           ,?");
                sql.addSql("                                           ,?)");
                sql.addSql("                )");
                sql.addSql("             )");
                sql.addSql("          )");
                sql.addSql("        )");
                sql.addSql("   )");


                sql.addIntValue(RngConst.RNG_STATUS_REQUEST);
                sql.addIntValue(RngConst.RNG_STATUS_REJECT);

                sql.addIntValue(userSid);
                sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
                sql.addIntValue(GSConst.JTKBN_TOROKU);


                sql.addIntValue(RngConst.RNG_RNCSTATUS_APPR);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_KOETU);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_SKIP);

                sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_APPR);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_KOETU);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_DENIAL);
            }
        }

        //申請日時
        if (model.getApplDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE >= ?");
            UDate date = UDate.getInstance(model.getApplDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (model.getApplDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE <= ?");
            UDate date = UDate.getInstance(model.getApplDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }

        //最終確認日
        if (model.getLastMagageDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   KEIROSTEP.CHKDATE >= ?");
            UDate date = UDate.getInstance(model.getLastMagageDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (model.getLastMagageDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   KEIROSTEP.CHKDATE <= ?");
            UDate date = UDate.getInstance(model.getLastMagageDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }
        //カテゴリ選択
        if (model.getCategorySid() != -1) {
            if (model.getCategorySid() == 0) {
                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("     RNG_RNDATA.RTP_SID IN");
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          case  when RTP_SID IS NULL then 0 ");
                sql.addSql("                else RTP_SID ");
                sql.addSql("          end RTP_SID");
                sql.addSql("        from");
                sql.addSql("          RNG_TEMPLATE");
                sql.addSql("        where");
                sql.addSql("          RTC_SID = ?");
                sql.addSql("       )");
                sql.addSql("     or");
                sql.addSql("     RNG_RNDATA.RTP_SID = ?");
                sql.addSql("  )");
                sql.addIntValue(model.getCategorySid());
                sql.addIntValue(RngConst.RNG_RTC_SID_NONE);
            } else {
                sql.addSql(" and");
                sql.addSql("  RNG_RNDATA.RTP_SID IN");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       case  when RTP_SID IS NULL then 0 ");
                sql.addSql("             else RTP_SID ");
                sql.addSql("       end RTP_SID");
                sql.addSql("     from");
                sql.addSql("       RNG_TEMPLATE");
                sql.addSql("     where");
                sql.addSql("       RTC_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(model.getCategorySid());
            }
        }
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE = APPLICATE.USR_SID");
        sql.addSql(" and");
        sql.addSql("   APPLICATE.USR_SID = APPLICATE_INF.USR_SID");
        sql.addSql(" ) RNDATA");

        //キーワード検索
        __setSearchKeywordSql(sql, model);


    }

    /**
     * <br>[機  能] 稟議一覧検索時(完了)の検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model 検索条件
     */
    private void __createSearchKanryoRingiListSql(SqlBuffer sql, RingiSearchModel model) {

        //SQL文
        sql.addSql(" from");
        sql.addSql(" ( select distinct ");
        sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
        sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
        sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
        sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");
        sql.addSql("   APPLICATE_INF.USI_SEI as USI_SEI,");
        sql.addSql("   APPLICATE_INF.USI_MEI as USI_MEI,");
        sql.addSql("   APPLICATE_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("   APPLICATE_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("   APPLICATE.USR_JKBN as USR_JKBN,");
        sql.addSql("   APPLICATE.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("   RNG_RNDATA.RNG_APPLDATE as APPLDATE,");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
        sql.addSql("   RNG_RNDATA.RNG_ADMCOMMENT as RNG_ADMCOMMENT,");
        sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
        sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER,");
        sql.addSql("   KEIROSTEP.CHKDATE as CHKDATE");

        sql.addSql(" from");
        sql.addSql("   RNG_RNDATA,");
        sql.addSql("   (");
        sql.addSql("     select");
        sql.addSql("       RNG_SID,");
        sql.addSql("       max(RSS_CHKDATE) as CHKDATE");
        sql.addSql("     from");
        sql.addSql("       RNG_SINGI");
        if (model.getUserSid() > 100) {
            sql.addSql("     where");
            sql.addSql("       RNG_SID IN ");
            sql.addSql("       (");
            sql.addSql("        select ");
            sql.addSql("          RNG_SID ");
            sql.addSql("        from ");
            sql.addSql("          RNG_SINGI ");
            sql.addSql("        where ");
            sql.addSql("          USR_SID=?");
            sql.addSql("       )");
            sql.addIntValue(model.getUserSid());
        }
        sql.addSql("     group by");
        sql.addSql("       RNG_SID");
        sql.addSql("   ) KEIROSTEP,");
        sql.addSql("   CMN_USRM APPLICATE,");
        sql.addSql("   CMN_USRM_INF APPLICATE_INF");

        sql.addSql(" where");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG = ?");
        sql.addIntValue(RngConst.RNG_COMPFLG_COMPLETE);
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_SID = KEIROSTEP.RNG_SID");

        int groupSid = model.getGroupSid();
        int userSid = model.getUserSid();
        if (groupSid != -1 || userSid != -1) {
            if (model.isAdminFlg()) {
                sql.addSql(" and");
                if (userSid != -1) {
                    sql.addSql("   RNG_RNDATA.RNG_APPLICATE = ?");
                    sql.addIntValue(userSid);
                } else {
                    sql.addSql("   RNG_RNDATA.RNG_APPLICATE in (");
                    sql.addSql("     select");
                    sql.addSql("       CMN_USRM.USR_SID");
                    sql.addSql("     from");
                    sql.addSql("       CMN_USRM,");
                    sql.addSql("       CMN_BELONGM");
                    sql.addSql("     where");
                    sql.addSql("       CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
                    sql.addSql("     and");
                    sql.addSql("       CMN_USRM.USR_JKBN = ?");
                    sql.addSql("     and");
                    sql.addSql("       CMN_BELONGM.GRP_SID = ?");
                    sql.addSql("     )");
                    sql.addIntValue(GSConst.JTKBN_TOROKU);
                    sql.addIntValue(groupSid);
                }
            } else {
                sql.addSql(" and");
                sql.addSql("   (");
                sql.addSql("     RNG_RNDATA.RNG_APPLICATE = ?");
                sql.addSql("   or");
                sql.addSql("     RNG_RNDATA.RNG_SID in (");
                sql.addSql("       select");
                sql.addSql("         RNG_SINGI.RNG_SID");
                sql.addSql("       from");
                sql.addSql("         RNG_SINGI,");
                sql.addSql("         RNG_KEIRO_STEP");
                sql.addSql("       where");
                sql.addSql("         RNG_SINGI.USR_SID = ?");
                sql.addSql("       and");
                sql.addSql("         RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID");
                sql.addSql("       and");
                sql.addSql("         (");
                sql.addSql("           (");
                sql.addSql("              RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
                sql.addSql("            and");
                sql.addSql("              (");
                sql.addSql("                 RNG_KEIRO_STEP.RKS_STATUS <> ?");
                sql.addSql("               or");
                sql.addSql("                 RNG_KEIRO_STEP.RKS_KOETU_SIZI = ?");
                sql.addSql("              )");
                sql.addSql("           )");
                sql.addSql("         or");
                sql.addSql("           (");
                sql.addSql("              RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
                sql.addSql("            and");
                sql.addSql("              RNG_SINGI.RSS_STATUS = ?");
                sql.addSql("           )");
                sql.addSql("         )");
                sql.addSql("     )");
                sql.addSql("   )");

                sql.addIntValue(userSid);
                sql.addIntValue(userSid);
                sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_NOSET);
                sql.addIntValue(RngConst.RNG_KOETU_YES);
                sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
                sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRMATION);
            }
        }

        //申請日時
        if (model.getApplDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE >= ?");
            UDate date = UDate.getInstance(model.getApplDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (model.getApplDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE <= ?");
            UDate date = UDate.getInstance(model.getApplDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }

        //最終確認日
        if (model.getLastMagageDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   KEIROSTEP.CHKDATE >= ?");
            UDate date = UDate.getInstance(model.getLastMagageDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (model.getLastMagageDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   KEIROSTEP.CHKDATE <= ?");
            UDate date = UDate.getInstance(model.getLastMagageDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }
        //カテゴリ選択
        if (model.getCategorySid() != -1) {
            if (model.getCategorySid() == 0) {
                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("     RNG_RNDATA.RTP_SID IN");
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          case  when RTP_SID IS NULL then 0 ");
                sql.addSql("                else RTP_SID ");
                sql.addSql("          end RTP_SID");
                sql.addSql("        from");
                sql.addSql("          RNG_TEMPLATE");
                sql.addSql("        where");
                sql.addSql("          RTC_SID = ?");
                sql.addSql("       )");
                sql.addSql("     or");
                sql.addSql("     RNG_RNDATA.RTP_SID = ?");
                sql.addSql("  )");
                sql.addIntValue(model.getCategorySid());
                sql.addIntValue(RngConst.RNG_RTC_SID_NONE);
            } else {
                sql.addSql(" and");
                sql.addSql("  RNG_RNDATA.RTP_SID IN");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       case  when RTP_SID IS NULL then 0 ");
                sql.addSql("             else RTP_SID ");
                sql.addSql("       end RTP_SID");
                sql.addSql("     from");
                sql.addSql("       RNG_TEMPLATE");
                sql.addSql("     where");
                sql.addSql("       RTC_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(model.getCategorySid());
            }
        }
        //状態
        if (model.getStatusId() != -1) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_STATUS = ?");
            sql.addIntValue(model.getStatusId());
        }
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE = APPLICATE.USR_SID");
        sql.addSql(" and");
        sql.addSql("   APPLICATE.USR_SID = APPLICATE_INF.USR_SID");
        sql.addSql(" ) RNDATA");

        //キーワード検索
        __setSearchKeywordSql(sql, model);

    }

    /**
     * <br>[機  能] 更新 or 削除対象の稟議SID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param delMdl 手動削除設定モデル
     * @return 稟議SID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getUpdateRingilList(RngDeleteModel delMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> rngSidList = new ArrayList<Integer>();
        SqlBuffer sql = null;

        try {
            UDate delDate = new UDate();
            delDate.setMaxHhMmSs();
            delDate.addYear((delMdl.getDelYear() * -1));
            delDate.addMonth((delMdl.getDelMonth() * -1));
            delDate.addDay((delMdl.getDelDay() * -1));

            sql = new SqlBuffer();
            sql.addSql(" select RNG_SID from RNG_RNDATA");

            //申請中
            if (delMdl.getDelType() == RngDeleteModel.DELTYPE_PENDING) {
                sql.addSql(" where");
                sql.addSql("   RNG_RNDATA.RNG_STATUS <> ?");
                sql.addSql(" and");
                sql.addSql("   RNG_RNDATA.RNG_COMPFLG = ?");
                sql.addSql(" and RNG_APPLDATE <= ? ");
                sql.addIntValue(RngConst.RNG_STATUS_DRAFT);
                sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);
                sql.addDateValue(delDate);
            }
            //完了
            if (delMdl.getDelType() == RngDeleteModel.DELTYPE_COMPLETE) {
                sql.addSql(" where");
                sql.addSql("   RNG_RNDATA.RNG_COMPFLG = ?");
                sql.addSql(" and");
                sql.addSql("   RNG_APPLDATE <= ? ");
                sql.addIntValue(RngConst.RNG_COMPFLG_COMPLETE);
                sql.addDateValue(delDate);
            }
            //草稿
            if (delMdl.getDelType() == RngDeleteModel.DELTYPE_DRAFT) {
                sql.addSql(" where");
                sql.addSql("   RNG_RNDATA.RNG_STATUS = ?");
                sql.addSql(" and");
                sql.addSql("   RNG_MAKEDATE <= ? ");
                sql.addIntValue(RngConst.RNG_STATUS_DRAFT);
                sql.addDateValue(delDate);
            }

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                rngSidList.add(rs.getInt("RNG_SID"));
            }
        } catch (SQLException e) {
            if (sql != null) {
                log__.error("Error SQL: " + sql.toLogString());
            }
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return rngSidList;
    }

    /**
     * <br>[機  能] 稟議一覧検索時(草稿)の検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model 検索条件
     */
    private void __createSearchSoukouRingiListSql(SqlBuffer sql, RingiSearchModel model) {

        //SQL文
        sql.addSql(" from");
        sql.addSql(" ( select distinct ");
        sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");
        sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");
        sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");
        sql.addSql("   RNG_RNDATA.RNG_ADATE as MAKEDATE,");
        sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");
        sql.addSql("   RNG_RNDATA.RNG_ADMCOMMENT as RNG_ADMCOMMENT,");
        sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");
        sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER");

        sql.addSql(" from");
        sql.addSql("   RNG_RNDATA");
        sql.addSql(" where");
        sql.addSql("   RNG_RNDATA.RNG_STATUS = ?");
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_AUID = ?");
        sql.addIntValue(RngConst.RNG_STATUS_DRAFT);
        sql.addIntValue(model.getUserSid());


        //カテゴリ選択
        if (model.getCategorySid() != -1) {
            if (model.getCategorySid() == 0) {
                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("     RNG_RNDATA.RTP_SID IN");
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          case  when RTP_SID IS NULL then 0 ");
                sql.addSql("                else RTP_SID ");
                sql.addSql("          end RTP_SID");
                sql.addSql("        from");
                sql.addSql("          RNG_TEMPLATE");
                sql.addSql("        where");
                sql.addSql("          RTC_SID = ?");
                sql.addSql("       )");
                sql.addSql("     or");
                sql.addSql("     RNG_RNDATA.RTP_SID = ?");
                sql.addSql("  )");
                sql.addIntValue(model.getCategorySid());
                sql.addIntValue(RngConst.RNG_RTC_SID_NONE);
            } else {
                sql.addSql(" and");
                sql.addSql("  RNG_RNDATA.RTP_SID IN");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       case  when RTP_SID IS NULL then 0 ");
                sql.addSql("             else RTP_SID ");
                sql.addSql("       end RTP_SID");
                sql.addSql("     from");
                sql.addSql("       RNG_TEMPLATE");
                sql.addSql("     where");
                sql.addSql("       RTC_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(model.getCategorySid());
            }
        }
        sql.addSql(" ) RNDATA");
        //キーワード検索
        __setSearchKeywordSql(sql, model);
    }


    /**
     * <br>[機  能] 稟議一覧検索時(後閲)の検索部SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model 検索条件
     */
    private void __createSearchKoetuRingiListSql(SqlBuffer sql, RingiSearchModel model) {

        //SQL文
        sql.addSql(" from");
        sql.addSql(" ( select distinct ");
        sql.addSql("   RNG_RNDATA.RNG_SID as RNG_SID,");               //稟議SID
        sql.addSql("   RNG_RNDATA.RNG_TITLE as RNG_TITLE,");           //タイトル
        sql.addSql("   RNG_RNDATA.RNG_ID as RNG_ID,");                 //申請ID
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE as RNG_APPLICATE,");   //申請者SID
        sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");             //申請者姓
        sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");             //申請者名
        sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");       //申請者姓ｶﾅ
        sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");       //申請者名ｶﾅ
        sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");               //申請者使用フラグ
        sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");         //申請者有効フラグ
        sql.addSql("   RNG_RNDATA.RNG_APPLDATE as APPLDATE,");         //申請日
        sql.addSql("   RNG_RNDATA.RNG_STATUS as RNG_STATUS,");         //申請状態
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG as RNG_COMPFLG,");       //完了フラグ 0:未定 1:完了
        sql.addSql("   RNG_RNDATA.RNG_ADMCOMMENT as RNG_ADMCOMMENT,"); //管理者コメント
        sql.addSql("   RNG_RNDATA.RTP_SID as RTP_SID,");               //テンプレートSID
        sql.addSql("   RNG_RNDATA.RTP_VER as RTP_VER");                //テンプレートバージョン
        sql.addSql(" from");
        sql.addSql("   (   ");
        sql.addSql("    select");
        sql.addSql("      RNG_KEIRO_STEP.RNG_SID as RNG_SID,");
        sql.addSql("      MAX(RNG_KEIRO_STEP.RKS_SORT) as MAX_SORT");
        sql.addSql("    from ");
        sql.addSql("      RNG_KEIRO_STEP");
        sql.addSql("    where");
        sql.addSql("      RNG_KEIRO_STEP.RKS_SID IN");
        sql.addSql("      (");
        sql.addSql("        select");
        sql.addSql("          RKS_SID");
        sql.addSql("        from");
        sql.addSql("          RNG_SINGI");
        sql.addSql("        where");
        //グループ/ユーザ
        if (model.getUserSid() > 0) {
            sql.addSql("   RNG_SINGI.USR_SID = ?");
            sql.addIntValue(model.getUserSid());
        } else if (model.getGroupSid() > 0) {
            sql.addSql("   RNG_SINGI.USR_SID in (");
            sql.addSql("     select CMN_BELONGM.USR_SID");
            sql.addSql("     from CMN_BELONGM, CMN_USRM");
            sql.addSql("     where CMN_BELONGM.GRP_SID = ?");
            sql.addSql("     and CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(model.getGroupSid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
        }
        sql.addSql("      )");
        sql.addSql("    and");
        sql.addSql("      RNG_KEIRO_STEP.RKS_KOETU_SIZI = ?");
        sql.addSql("    and");
        sql.addSql("      RNG_KEIRO_STEP.RKS_STATUS <> ?");
        sql.addSql("    and");
        sql.addSql("      RNG_KEIRO_STEP.RKS_ROLL_TYPE = ?");
        sql.addSql("    group by");
        sql.addSql("      RNG_SID");
        sql.addSql("   ) keiro");
        sql.addIntValue(RngConst.RNG_KOETU_YES);
        sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
        sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
        sql.addSql(" left join");
        sql.addSql("   RNG_RNDATA");
        sql.addSql(" on ");
        sql.addSql("   keiro.RNG_SID = RNG_RNDATA.RNG_SID");
        //----現在確認中の経路ステップ情報を結合  開始-------
        sql.addSql(" left join");
        sql.addSql("   (   ");
        sql.addSql("    select");
        sql.addSql("      RNG_KEIRO_STEP.RNG_SID as RNG_SID,");
        sql.addSql("      RNG_KEIRO_STEP.RKS_SORT as NOW_STEP");
        sql.addSql("    from ");
        sql.addSql("      RNG_KEIRO_STEP");
        sql.addSql("    where");
        sql.addSql("      RNG_KEIRO_STEP.RNG_SID IN");
        sql.addSql("      (");
        sql.addSql("        select");
        sql.addSql("          RNG_SID");
        sql.addSql("        from");
        sql.addSql("          RNG_SINGI");
        sql.addSql("        where");
        //グループ/ユーザ
        if (model.getUserSid() > 0) {
            sql.addSql("   RNG_SINGI.USR_SID = ?");
            sql.addIntValue(model.getUserSid());
        } else if (model.getGroupSid() > 0) {
            sql.addSql("   RNG_SINGI.USR_SID in (");
            sql.addSql("     select CMN_BELONGM.USR_SID");
            sql.addSql("     from CMN_BELONGM, CMN_USRM");
            sql.addSql("     where CMN_BELONGM.GRP_SID = ?");
            sql.addSql("     and CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(model.getGroupSid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
        }
        sql.addSql("      )");
        sql.addSql("    and");
        sql.addSql("      RNG_KEIRO_STEP.RKS_STATUS = ?");
        sql.addSql("   ) keiro_confim");
        sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
        sql.addSql(" on ");
        sql.addSql("   keiro.RNG_SID = keiro_confim.RNG_SID,");
        sql.addSql("   CMN_USRM,");
        sql.addSql("   CMN_USRM_INF");

        //----現在確認中の経路ステップ情報を結合  終了-------

        sql.addSql(" where");
        sql.addSql("   RNG_RNDATA.RNG_STATUS <> ?");
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_APPLICATE = CMN_USRM.USR_SID");
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql(" and");
        sql.addSql("   RNG_RNDATA.RNG_COMPFLG = ?");
        sql.addIntValue(RngConst.RNG_STATUS_DRAFT);
        sql.addIntValue(RngConst.RNG_COMPFLG_UNDECIDED);
        sql.addSql(" and (");
        sql.addSql("   RNG_RNDATA.RNG_STATUS = ?");
        sql.addIntValue(RngConst.RNG_STATUS_REQUEST);
        sql.addSql("   or (RNG_RNDATA.RNG_STATUS = ?");
        sql.addSql("      and keiro_confim.NOW_STEP > ?)");
        sql.addSql("     )");
        sql.addIntValue(RngConst.RNG_STATUS_REJECT);
        sql.addIntValue(0);
        sql.addSql(" and");
        sql.addSql("   keiro.MAX_SORT > keiro_confim.NOW_STEP");

        //申請日時
        if (model.getApplDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE >= ?");
            UDate date = UDate.getInstance(model.getApplDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (model.getApplDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RNG_APPLDATE <= ?");
            UDate date = UDate.getInstance(model.getApplDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }

        //カテゴリ選択
        if (model.getCategorySid() != -1) {
            if (model.getCategorySid() == 0) {
                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("     RNG_RNDATA.RTP_SID IN");
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          case  when RTP_SID IS NULL then 0 ");
                sql.addSql("                else RTP_SID ");
                sql.addSql("          end RTP_SID");
                sql.addSql("        from");
                sql.addSql("          RNG_TEMPLATE");
                sql.addSql("        where");
                sql.addSql("          RTC_SID = ?");
                sql.addSql("       )");
                sql.addSql("     or");
                sql.addSql("     RNG_RNDATA.RTP_SID = ?");
                sql.addSql("  )");
                sql.addIntValue(model.getCategorySid());
                sql.addIntValue(RngConst.RNG_RTC_SID_NONE);
            } else {
                sql.addSql(" and");
                sql.addSql("  RNG_RNDATA.RTP_SID IN");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       case  when RTP_SID IS NULL then 0 ");
                sql.addSql("             else RTP_SID ");
                sql.addSql("       end RTP_SID");
                sql.addSql("     from");
                sql.addSql("       RNG_TEMPLATE");
                sql.addSql("     where");
                sql.addSql("       RTC_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(model.getCategorySid());
            }
        }
        sql.addSql(" ) RNDATA");

        //キーワード検索
        __setSearchKeywordSql(sql, model);

    }

    /**
     * <br>[機  能] 検索部SQLのうち、キーワード検索,申請者検索部分を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param model 検索条件
     */
    private void __setSearchKeywordSql(SqlBuffer sql, RingiSearchModel model) {

        List<String> keyword = model.getKeyword();
        boolean whereFlg = true;

        if (keyword != null
                && !keyword.isEmpty()
                && (model.isTitleSearchFlg()
                || model.isContentSearchFlg()
                || model.isRngIdSearchFlg())) {
            sql.addSql(" left join RNG_FORMDATA on");
            sql.addSql("    RNDATA.RNG_SID = RNG_FORMDATA.RNG_SID");
            sql.addSql(" where ");

            sql.addSql("  (");
            sql.addSql("  (");

            if (model.isTitleSearchFlg()) {
                __setMultiTypeSql(sql, model, "RNDATA.RNG_TITLE");
            }

            if (model.isContentSearchFlg()) {
                if (model.isTitleSearchFlg()) {
                    sql.addSql("  )");
                    sql.addSql("  or");
                    sql.addSql("  (");
                }
                __setMultiTypeSql(sql, model, "RNG_FORMDATA.RFD_VALUE");
            }

            if (model.isRngIdSearchFlg()) {
                if (model.isContentSearchFlg() || model.isTitleSearchFlg()) {
                    sql.addSql("  )");
                    sql.addSql("  or");
                    sql.addSql("  (");
                }
                __setMultiTypeSql(sql, model, "RNDATA.RNG_ID");
            }
            sql.addSql("  )");
            sql.addSql("  )");
            whereFlg = false;
        }
        int applGroupSid = model.getApplGroupSid();
        int applUserSid = model.getApplUserSid();
        //グループ/ユーザ
        if (applGroupSid != -1 || applUserSid != -1) {
            if (whereFlg) {
                sql.addSql(" where");
            } else {
                sql.addSql(" and");
            }
            if (applUserSid != -1) {
                sql.addSql("   RNG_APPLICATE = ?");
                sql.addIntValue(applUserSid);
            } else {
                sql.addSql("   RNG_APPLICATE in (");
                sql.addSql("     select");
                sql.addSql("       CMN_USRM.USR_SID");
                sql.addSql("     from");
                sql.addSql("       CMN_USRM,");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     where");
                sql.addSql("       CMN_USRM.USR_JKBN = ?");
                sql.addSql("     and");
                sql.addSql("       CMN_BELONGM.GRP_SID = ?");
                sql.addSql("     and");
                sql.addSql("       CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
                sql.addSql("     )");
                sql.addIntValue(GSConst.JTKBN_TOROKU);
                sql.addIntValue(applGroupSid);
            }
        }






    }

    /**
     * <br>[機  能] AND OR 条件を使用した検索部SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param model 検索条件
     * @param element 検索対象
     */
    private void __setMultiTypeSql(SqlBuffer sql, RingiSearchModel model, String element) {

        List<String> keywordList = model.getKeyword();
        boolean pluralValue = model.getKeywordType() == RngConst.RNG_SEARCHTYPE_OR
                                && keywordList.size() > 1;

        String searchType = "and";
        if (pluralValue) {
            sql.addSql("   (");
            searchType = "or";
        }

        boolean addSearchType = false;
        for (String keyword : keywordList) {
            if (addSearchType) {
                sql.addSql(" " + searchType);
            }
            sql.addSql("   " + element + " like '%"
                    + JDBCUtil.escapeForLikeSearch(keyword)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            addSearchType = true;
        }

        if (pluralValue) {
            sql.addSql("   )");
        }

    }

    /**
     * 日付文字列を取得する
     * @param date 日付
     * @return 日付文字列
     */
    private String __createDateStr(UDate date) {
        if (date == null) {
            return null;
        }

        StringBuilder strDate = new StringBuilder("");
        strDate.append(UDateUtil.getSlashYYMD(date));
        strDate.append(" ");
        strDate.append(UDateUtil.getSeparateHMS(date));

        return strDate.toString();
    }

    /**
     * <p>指定した稟議の申請者が削除さているかを返す
     * @param rngSid 稟議SID
     * @return boolean false：削除済み true:未削除
     * @throws SQLException SQL実行例外
     */
    public boolean getDelApprUser(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   USR_SID =");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RNG_APPLICATE");
            sql.addSql("    from");
            sql.addSql("      RNG_RNDATA");
            sql.addSql("    where");
            sql.addSql("      RNG_SID = ?");
            sql.addSql("   )");

            sql.addIntValue(rngSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("USR_JKBN") == GSConst.JTKBN_TOROKU) {
                    ret = true;
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
     * <br>[機  能] 申請/確認通知のユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userList 除外ユーザリスト
     * @param noticeKbn 遷移元区分
     * @return userSendList 送信ユーザリスト
     * @throws SQLException SQLException
     */
    public List<Integer> getAppryConfSmlUser(int rngSid,
            List<Integer> userList, int noticeKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> userSendList = new ArrayList<Integer>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   USR_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID IN  ");
            sql.addSql("   (  ");
            sql.addSql("    select ");
            sql.addSql("      USR_SID ");
            sql.addSql("    from ");
            sql.addSql("      RNG_SINGI ");
            sql.addSql("    where ");
            sql.addSql("      RKS_SID IN ");
            sql.addSql("        ( ");
            sql.addSql("         select ");
            sql.addSql("           RKS_SID ");
            sql.addSql("         from ");
            sql.addSql("           RNG_KEIRO_STEP ");
            sql.addSql("         where ");
            sql.addSql("           RNG_SID = ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_ROLL_TYPE = ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_STATUS = ? ");
            sql.addSql("        )");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(rngSid);
            if (noticeKbn == RngConst.STATUS_SOURCE_APPROVAL_COMP_SML) {
                sql.addIntValue(RngConst.RNG_RNCTYPE_CONFIRM);
            } else {
                sql.addIntValue(RngConst.RNG_RNCTYPE_APPR);
            }
            sql.addIntValue(RngConst.RNG_RNCSTATUS_CONFIRM);
            if  (userList.size() > 0) {
                sql.addSql("    and ");
                sql.addSql("      USR_SID NOT IN");
                sql.addSql("        ( ");
                for (int idx = 0; idx < userList.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql("         , ");
                    }
                    sql.addSql("         ? ");
                    sql.addIntValue(userList.get(idx));
                }
                sql.addSql("        ) ");

            }
            sql.addSql("   )");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userSendList.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userSendList;
    }

    /**
     * <br>[機  能] 差し戻し通知のユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userList 除外ユーザリスト
     * @param sort ソート位置0:[開始位置] 1:[終了位置]
     * @return userSendList 送信ユーザリスト
     * @throws SQLException SQLException
     */
    public List<Integer> getRemandSmlUser(int rngSid,
            List<Integer> userList, int[] sort) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> userSendList = new ArrayList<Integer>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   USR_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID IN  ");
            sql.addSql("   (  ");
            sql.addSql("    select ");
            sql.addSql("      USR_SID ");
            sql.addSql("    from ");
            sql.addSql("      RNG_SINGI ");
            sql.addSql("    where ");
            sql.addSql("      RKS_SID IN ");
            sql.addSql("        ( ");
            sql.addSql("         select ");
            sql.addSql("           RKS_SID ");
            sql.addSql("         from ");
            sql.addSql("           RNG_KEIRO_STEP ");
            sql.addSql("         where ");
            sql.addSql("           RNG_SID = ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_SORT >= ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_SORT < ? ");
            sql.addSql("        )");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(rngSid);
            sql.addIntValue(sort[0]);
            sql.addIntValue(sort[1]);
            if  (userList.size() > 0) {
                sql.addSql("    and ");
                sql.addSql("      USR_SID NOT IN");
                sql.addSql("        ( ");
                for (int idx = 0; idx < userList.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql("         , ");
                    }
                    sql.addSql("         ? ");
                    sql.addIntValue(userList.get(idx));
                }
                sql.addSql("        ) ");

            }
            sql.addSql("   )");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userSendList.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userSendList;
    }

    /**
     * <br>[機  能] 後閲通知のユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userList 除外ユーザリスト
     * @param sort ソート位置0:[開始位置] 1:[終了位置]
     * @return userSendList 送信ユーザリスト
     * @throws SQLException SQLException
     */
    public List<Integer> getKoetuSmlUser(int rngSid,
            List<Integer> userList, int[] sort) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> userSendList = new ArrayList<Integer>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   USR_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID IN  ");
            sql.addSql("   (  ");
            sql.addSql("    select ");
            sql.addSql("      USR_SID ");
            sql.addSql("    from ");
            sql.addSql("      RNG_SINGI ");
            sql.addSql("    where ");
            sql.addSql("      RKS_SID IN ");
            sql.addSql("        ( ");
            sql.addSql("         select ");
            sql.addSql("           RKS_SID ");
            sql.addSql("         from ");
            sql.addSql("           RNG_KEIRO_STEP ");
            sql.addSql("         where ");
            sql.addSql("           RNG_SID = ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_SORT >= ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_SORT <= ? ");
            sql.addSql("         and ");
            sql.addSql("           RKS_STATUS = ? ");
            sql.addSql("        )");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(rngSid);
            sql.addIntValue(sort[0]);
            sql.addIntValue(sort[1]);
            sql.addIntValue(RngConst.RNG_RNCSTATUS_KOETU);
            if  (userList.size() > 0) {
                sql.addSql("    and ");
                sql.addSql("      USR_SID NOT IN");
                sql.addSql("        ( ");
                for (int idx = 0; idx < userList.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql("         , ");
                    }
                    sql.addSql("         ? ");
                    sql.addIntValue(userList.get(idx));
                }
                sql.addSql("        ) ");

            }
            sql.addSql("   )");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userSendList.add(rs.getInt("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userSendList;
    }

    /**
     * <br>[機  能] 指定したグループ内に稟議の使用権限があるユーザが存在するグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param banUsrSid 反映させないユーザSID
     * @param mode 0:制限なし 1:制限ユーザ指定 2:許可ユーザ指定
     * @return グループSIDリスト
     * @throws SQLException SQLException
     */
    public ArrayList<Integer> getGroupList(ArrayList<Integer> grpSid, ArrayList<Integer> banUsrSid,
            int mode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();

        if (grpSid == null || grpSid.size() == 0) {
            return ret;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            if (mode == 0) {
                sql.addSql("  select");
                sql.addSql("    GROUP_INF.GRP_SID");
                sql.addSql("  from");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       CMN_BELONGM.GRP_SID,");
                sql.addSql("       CMN_BELONGM.USR_SID");
                sql.addSql("     from");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     left join");
                sql.addSql("       CMN_USRM");
                sql.addSql("     on");
                sql.addSql("       CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
                sql.addSql("     where");
                sql.addSql("       CMN_USRM.USR_JKBN = ?");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                sql.addSql("     and");
                sql.addSql("       CMN_USRM.USR_SID > ?");
                sql.addIntValue(GSConstUser.USER_RESERV_SID);
                sql.addSql("     and");
                sql.addSql("       CMN_BELONGM.GRP_SID in");
                sql.addSql("       (");
                for (int idx = 0; idx < grpSid.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(grpSid.get(idx));
                }
                sql.addSql("       )");
                if (banUsrSid != null && banUsrSid.size() > 0) {
                    sql.addSql("     and");
                    sql.addSql("       CMN_USRM.USR_SID not in");
                    sql.addSql("       (");
                    for (int idx = 0; idx < banUsrSid.size(); idx++) {
                        if (idx != 0) {
                            sql.addSql(" ,");
                        }
                        sql.addSql(" ?");
                        sql.addIntValue(banUsrSid.get(idx));
                    }
                    sql.addSql("       )");
                }
                sql.addSql("    ) GROUP_INF ");
                sql.addSql("  group by ");
                sql.addSql("    GROUP_INF.GRP_SID ");
            } else {
                sql.addSql("  select");
                sql.addSql("    GROUP_INF.GRP_SID");
                sql.addSql("  from");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       CMN_BELONGM.GRP_SID,");
                sql.addSql("       CMN_BELONGM.USR_SID");
                sql.addSql("     from");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     left join");
                sql.addSql("       CMN_USRM");
                sql.addSql("     on");
                sql.addSql("       CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
                sql.addSql("     where");
                sql.addSql("       CMN_USRM.USR_JKBN = ?");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                sql.addSql("     and");
                sql.addSql("       CMN_USRM.USR_SID > ?");
                sql.addIntValue(GSConstUser.USER_RESERV_SID);
                sql.addSql("     and");
                sql.addSql("       CMN_BELONGM.GRP_SID in");
                sql.addSql("       (");
                for (int idx = 0; idx < grpSid.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(grpSid.get(idx));
                }
                sql.addSql("       )");
                if (banUsrSid != null && banUsrSid.size() > 0) {
                    sql.addSql("     and");
                    sql.addSql("       CMN_USRM.USR_SID not in");
                    sql.addSql("       (");
                    for (int idx = 0; idx < banUsrSid.size(); idx++) {
                        if (idx != 0) {
                            sql.addSql(" ,");
                        }
                        sql.addSql(" ?");
                        sql.addIntValue(banUsrSid.get(idx));
                    }
                    sql.addSql("       )");
                }
                sql.addSql("     and");
                if (mode == 1) {
                    sql.addSql("       CMN_USRM.USR_SID not in");
                } else if (mode == 2) {
                    sql.addSql("       CMN_USRM.USR_SID in");
                }
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          USR_SID");
                sql.addSql("        from");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("        where");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER.PCT_PID = ?");
                sql.addStrValue(RngConst.PLUGIN_ID_RINGI);
                sql.addSql("        and");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER.USR_SID <> -1");
                sql.addSql("        UNION");
                sql.addSql("        (");
                sql.addSql("         select");
                sql.addSql("           CMN_BELONGM.USR_SID");
                sql.addSql("         from");
                sql.addSql("           CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("         inner join");
                sql.addSql("           CMN_BELONGM");
                sql.addSql("         on");
                sql.addSql("           CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("         where");
                sql.addSql("           CMN_PLUGIN_CONTROL_MEMBER.PCT_PID = ?");
                sql.addStrValue(RngConst.PLUGIN_ID_RINGI);
                sql.addSql("         and ");
                sql.addSql("           CMN_PLUGIN_CONTROL_MEMBER.GRP_SID <> -1");
                sql.addSql("        )");
                sql.addSql("      )");
                sql.addSql("    ) GROUP_INF ");
                sql.addSql("  group by ");
                sql.addSql("    GROUP_INF.GRP_SID ");
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("GRP_SID"));
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
     * <br>[機  能] 指定したグループ内に稟議の使用権限があるユーザが存在するグループとユーザ数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param mode 0:制限なし 1:制限ユーザ指定 2:許可ユーザ指定
     * @return グループSIDリスト
     * @throws SQLException SQLException
     */
    public Map<String, Integer> getGroupListCnt(ArrayList<String> grpSid, int mode)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<String, Integer> ret = new HashMap<String, Integer>();

        if (grpSid == null || grpSid.size() == 0) {
            return ret;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            if (mode == 0) {
                sql.addSql("  select");
                sql.addSql("    GROUP_INF.GRP_SID,");
                sql.addSql("    COUNT(GROUP_INF.USR_SID) as USR_CNT");
                sql.addSql("  from");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       CMN_BELONGM.GRP_SID,");
                sql.addSql("       CMN_BELONGM.USR_SID");
                sql.addSql("     from");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     left join");
                sql.addSql("       CMN_USRM");
                sql.addSql("     on");
                sql.addSql("       CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
                sql.addSql("     where");
                sql.addSql("       CMN_USRM.USR_JKBN = ?");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                sql.addSql("     and");
                sql.addSql("       CMN_USRM.USR_SID > ?");
                sql.addIntValue(GSConstUser.USER_RESERV_SID);
                sql.addSql("     and");
                sql.addSql("       CMN_BELONGM.GRP_SID in");
                sql.addSql("       (");
                for (int idx = 0; idx < grpSid.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(Integer.parseInt(grpSid.get(idx)));
                }
                sql.addSql("       )");
                sql.addSql("    ) GROUP_INF ");
                sql.addSql("  group by ");
                sql.addSql("    GROUP_INF.GRP_SID ");
            } else {
                sql.addSql("  select");
                sql.addSql("    GROUP_INF.GRP_SID,");
                sql.addSql("    COUNT(GROUP_INF.USR_SID) as USR_CNT");
                sql.addSql("  from");
                sql.addSql("    (");
                sql.addSql("     select");
                sql.addSql("       CMN_BELONGM.GRP_SID,");
                sql.addSql("       CMN_BELONGM.USR_SID");
                sql.addSql("     from");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     left join");
                sql.addSql("       CMN_USRM");
                sql.addSql("     on");
                sql.addSql("       CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
                sql.addSql("     where");
                sql.addSql("       CMN_USRM.USR_JKBN = ?");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                sql.addSql("     and");
                sql.addSql("       CMN_USRM.USR_SID > ?");
                sql.addIntValue(GSConstUser.USER_RESERV_SID);
                sql.addSql("     and");
                sql.addSql("       CMN_BELONGM.GRP_SID in");
                sql.addSql("       (");
                for (int idx = 0; idx < grpSid.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(Integer.parseInt(grpSid.get(idx)));
                }
                sql.addSql("       )");
                sql.addSql("     and");
                if (mode == 1) {
                    sql.addSql("       CMN_USRM.USR_SID not in");
                } else if (mode == 2) {
                    sql.addSql("       CMN_USRM.USR_SID in");
                }
                sql.addSql("       (");
                sql.addSql("        select");
                sql.addSql("          USR_SID");
                sql.addSql("        from");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("        where");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER.PCT_PID = ?");
                sql.addStrValue(RngConst.PLUGIN_ID_RINGI);
                sql.addSql("        and");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER.USR_SID <> -1");
                sql.addSql("        UNION");
                sql.addSql("        (");
                sql.addSql("          select");
                sql.addSql("            CMN_BELONGM.USR_SID");
                sql.addSql("          from");
                sql.addSql("            CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("          inner join");
                sql.addSql("            CMN_BELONGM");
                sql.addSql("          on");
                sql.addSql("            CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("          where");
                sql.addSql("            CMN_PLUGIN_CONTROL_MEMBER.PCT_PID = ?");
                sql.addStrValue(RngConst.PLUGIN_ID_RINGI);
                sql.addSql("          and ");
                sql.addSql("            CMN_PLUGIN_CONTROL_MEMBER.GRP_SID <> -1");
                sql.addSql("        )");
                sql.addSql("      )");
                sql.addSql("    ) GROUP_INF ");
                sql.addSql("  group by ");
                sql.addSql("    GROUP_INF.GRP_SID ");
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(String.valueOf(rs.getInt("GRP_SID")), rs.getInt("USR_CNT"));
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
     * <br>[機  能] 指定したグループと役職に稟議を使用できるユーザが存在するかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param posMap 役職:key グループ:value
     * @param mode 1:制限ユーザ指定 2:許可ユーザ指定
     * @param seigenList ユーザリスト
     * @return Map 役職:key 人数:value
     * @throws SQLException SQLException
     */
    public Map<String, Integer> getGroupListCnt(Map<String, String> posMap,
            int mode, List<Integer> seigenList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<String, Integer> ret = new HashMap<String, Integer>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.POS_SID, ");
            sql.addSql("   COUNT(CMN_USRM_INF.USR_SID) as USR_CNT ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join ");
            sql.addSql("   CMN_BELONGM ");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_BELONGM.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN=?");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            if (mode == 1 && seigenList.size() > 0
                    || mode == 2 && seigenList.size() > 0) {
                sql.addSql(" and ");
                if (mode == 1) {
                    sql.addSql("   CMN_USRM.USR_SID not in");
                } else {
                    sql.addSql("   CMN_USRM.USR_SID in");
                }
                sql.addSql("   (");
                for (int idx = 0; idx < seigenList.size(); idx++) {
                    if (idx > 0) {
                        sql.addSql("   ,");
                    }
                    sql.addSql("   ?");
                    sql.addIntValue(seigenList.get(idx));
                }
                sql.addSql("   )");
            }
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql(" ( ");
            boolean first = true;
            for (Entry<String, String> entry : posMap.entrySet()) {
                if (!first) {
                    sql.addSql("  or ");
                }
                sql.addSql("  ( ");
                sql.addSql("   CMN_USRM_INF.POS_SID = ? ");
                sql.addIntValue(Integer.parseInt(entry.getKey()));
                if (entry.getValue() != null) {
                    sql.addSql(" and ");
                    sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
                    sql.addIntValue(Integer.parseInt(entry.getValue()));
                }
                sql.addSql("  ) ");
                first = false;
            }
            sql.addSql(" ) ");
            sql.addSql(" group by ");
            sql.addSql("   POS_SID ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(String.valueOf(rs.getInt("POS_SID")), rs.getInt("USR_CNT"));
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
