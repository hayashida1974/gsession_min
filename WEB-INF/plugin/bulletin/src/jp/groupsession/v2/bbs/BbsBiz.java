package jp.groupsession.v2.bbs;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.bbs.bbs060.Bbs060Biz;
import jp.groupsession.v2.bbs.dao.BbsAdmConfDao;
import jp.groupsession.v2.bbs.dao.BbsBinDao;
import jp.groupsession.v2.bbs.dao.BbsForAdminDao;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.bbs.dao.BbsForSumDao;
import jp.groupsession.v2.bbs.dao.BbsLogCountSumDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreRsvDao;
import jp.groupsession.v2.bbs.dao.BbsThreSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsUserDao;
import jp.groupsession.v2.bbs.dao.BbsViewLogCountDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.dao.BbsWriteLogCountDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;
import jp.groupsession.v2.bbs.model.BbsForAdminModel;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsForMemModel;
import jp.groupsession.v2.bbs.model.BbsForSumModel;
import jp.groupsession.v2.bbs.model.BbsLogCountSumModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.bbs.model.BbsThreSumModel;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.bbs.model.BbsViewLogCountModel;
import jp.groupsession.v2.bbs.model.BbsWriteInfModel;
import jp.groupsession.v2.bbs.model.BbsWriteLogCountModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinForumDiskModel;
import jp.groupsession.v2.bbs.model.BulletinSearchModel;
import jp.groupsession.v2.bbs.model.BulletinSmlModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板共通ビジネスロジッククラス
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public BbsBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public BbsBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 掲示板個人設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userId ユーザID
     * @return 掲示板個人設定情報
     * @throws SQLException SQL実行例外
     */
    public BbsUserModel getBbsUserData(Connection con, int userId) throws SQLException {
        BbsUserDao bUserDao = new BbsUserDao(con);
        BbsUserModel buMdl = bUserDao.select(userId);
        if (buMdl == null || buMdl.getUsrSid() <= 0) {
            //取得できなかった場合は初期値を設定する
            buMdl = new BbsUserModel();
            buMdl.setBurForCnt(10);
            buMdl.setBurThreCnt(10);
            buMdl.setBurWrtCnt(50);
            buMdl.setBurNewCnt(1);
            buMdl.setBurSmlNtf(GSConstBulletin.BBS_SMAIL_TSUUCHI);
            buMdl.setBurThreMainCnt(10);
            buMdl.setBurWrtlistOrder(GSConstBulletin.BBS_WRTLIST_ORDER_ASC);
            buMdl.setBurThreImage(GSConstBulletin.BBS_IMAGE_DSP);
            buMdl.setBurMainChkedDsp(1);
            buMdl.setBurSubNewThre(0);
            buMdl.setBurSubForum(0);
            buMdl.setBurSubUnchkThre(0);
            buMdl.setBurTempImage(0);
            buMdl.setBurIniPostType(GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN);
        }

        return buMdl;
    }
    /**
     * <br>[機  能] 掲示板管理者設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUserId セッションユーザSID
     * @return 掲示板管理者設定情報
     * @throws SQLException SQL実行例外
     */
    public BbsAdmConfModel getBbsAdminData(Connection con, int sessionUserId) throws SQLException {
        BbsAdmConfDao admDao = new BbsAdmConfDao(con);
        BbsAdmConfModel baMdl = admDao.select();
        if (baMdl == null) {
            //取得できなかった場合は初期値を設定する
            baMdl = new BbsAdmConfModel();
            baMdl.setBacAtdelFlg(GSConstBulletin.AUTO_DELETE_OFF);
            baMdl.setBacAtdelY(-1);
            baMdl.setBacAtdelM(-1);
            UDate now = new UDate();
            baMdl.setBacAdate(now);
            baMdl.setBacAuid(sessionUserId);
            baMdl.setBacEdate(now);
            baMdl.setBacEuid(sessionUserId);
            baMdl.setBacSmlNtf(GSConstBulletin.BAC_SML_NTF_USER);
            baMdl.setBacSmlNtfKbn(GSConstBulletin.BAC_SML_NTF_KBN_YES);
            baMdl.setBacIniPostTypeKbn(GSConstBulletin.BAC_INI_POST_TYPE_KBN_USER);
            baMdl.setBacIniPostType(GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN);

            boolean commit = false;
            try {
                admDao.insert(baMdl);
                commit = true;
            } catch (SQLException e) {
                log__.error("掲示板-管理者設定の登録に失敗しました");
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }

        return baMdl;
    }

    /**
     * <br>[機  能] フォーラム権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考] 閲覧権限のチェック時は編集権限か閲覧権限があればtrueを返します
     * @param con コネクション
     * @param buMdl ユーザモデル
     * @param bfiSid フォーラムSID
     * @param accessKbn アクセス区分 0:閲覧 1:追加・編集・削除
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public boolean checkForumAuth(
            Connection con, BaseUserModel buMdl, int bfiSid, int accessKbn)
                    throws SQLException {
        if (accessKbn == GSConstBulletin.ACCESS_KBN_WRITE
                && isForumEditAuth(con, bfiSid, buMdl)) {
            //フォーラム編集権限のチェック
            return true;

        } else if (accessKbn == GSConstBulletin.ACCESS_KBN_READ
                && isForumViewAuth(con, bfiSid, buMdl.getUsrsid())) {
            //フォーラム閲覧権限のチェック
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 編集時のアクセスチェックを行う
     * <br>[解  説]
     * <br>[備  考] 編集可能であればtrueを返す
     * @param con コネクション
     * @param buMdl ユーザモデル
     * @param bfiSid フォーラムSID
     * @param bwiSid 投稿SID
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public boolean checkAccessAuth(
            Connection con, BaseUserModel buMdl, int bfiSid, int bwiSid)
                    throws SQLException {

        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con);
        BbsWriteInfModel bbsWriteInfMdl = new BbsWriteInfModel();

        int userSid = buMdl.getUsrsid();

        bbsWriteInfMdl = bbsWriteInfDao.select(bwiSid);

        if (bbsWriteInfMdl.getBwiParentFlg() == GSConstBulletin.BBS_PARENT_FLG_YES
                || bbsWriteInfMdl.getBwiParentFlg() == GSConstBulletin.BBS_PARENT_FLG_NO) {
            //DN[投稿情報.投稿者ID]とログインユーザが同じかを判定する
            if (bbsWriteInfMdl.getBwiAuid() == userSid) {
                return true;
            //フォーラム管理者か判定する
            } else if (isForumAdmin(bfiSid, userSid, con)) {
                return true;
            //システム管理者または掲示板のプラグイン管理者を持つメンバーかを判定する
            } else {
                CommonBiz cmnBiz = new CommonBiz();
                boolean adminFlg = cmnBiz.isPluginAdmin(
                        con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);
                if (adminFlg && isForumViewAuth(con, bfiSid, userSid)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * <br>[機  能] ユーザが指定されたフォーラムの編集権限か閲覧権限があるか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public boolean isForumViewAuth(Connection con, int bfiSid, int userSid)
            throws SQLException {
        //フォーラムSIDからメンバー情報を持つフォーラムのSIDを取得する。
        bfiSid = getBfiSidForMemberInfo(con, bfiSid);

        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        return bbsMemDao.existBbsForMem(bfiSid, userSid);
    }

    /**
     * <br>[機  能] ユーザが指定されたフォーラムアクセス権限区分を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @return フォーラム権限 権限無し=-1
     * @throws SQLException SQL実行例外
     */
    public int getForumAuth(Connection con, int bfiSid, int userSid)
            throws SQLException {
        //フォーラムSIDからメンバー情報を持つフォーラムのSIDを取得する。
        bfiSid = getBfiSidForMemberInfo(con, bfiSid);

        int forAuth = -1;
        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        List<BbsForMemModel> memList = bbsMemDao.getAuthForMem(bfiSid, userSid);
        if (memList != null && memList.size() > 0) {
            for (BbsForMemModel model : memList) {
                if (model.getBfmAuth() == GSConstBulletin.ACCESS_KBN_WRITE) {
                    forAuth = GSConstBulletin.ACCESS_KBN_WRITE;
                    break;
                } else {
                    forAuth = GSConstBulletin.ACCESS_KBN_READ;
                }
            }
        }

        return forAuth;

    }

    /**
     * <br>[機  能] フォーラム編集権限があるか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param buMdl ユーザ情報モデル
     * @return フォーラム権限 true:編集権限有 false:編集権限無
     * @throws SQLException SQL実行例外
     */
    public boolean isForumEditAuth(
            Connection con, int bfiSid, BaseUserModel buMdl)
                    throws SQLException {

        int userSid = buMdl.getUsrsid();

        //システム管理者または掲示板のプラグイン管理者を持つメンバーかを判定する
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminFlg = cmnBiz.isPluginAdmin(
                con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);
        if (adminFlg && isForumViewAuth(con, bfiSid, userSid)) {
            return true;
        }

        //フォーラム管理者か判定する
        if (isForumAdmin(bfiSid, userSid, con)) {
            return true;
        }

        //フォーラムメンバーか判定する
        if (getForumAuth(con, bfiSid, userSid) == GSConstBulletin.ACCESS_KBN_WRITE) {
            return true;
        }

        return false;
    }


    /**
     * <br>[機  能] フォーラム情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @return フォーラム情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getForumData(Connection con, int bfiSid)
    throws SQLException {

        BulletinDao btDao = new BulletinDao(con);
        BulletinDspModel btMdl = btDao.getForumData(bfiSid);

        return btMdl;
    }

    /**
     * <br>[機  能] 指定されたフォーラムに掲示予定のスレッドがあるかチェックします。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param now 現在日時
     * @return true:ある  false:なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckRsvThreadData(Connection con, int bfiSid, UDate now)
            throws SQLException {

        int cnt = 0;

        BulletinDao btDao = new BulletinDao(con);
        cnt = btDao.countRsvThreData(bfiSid, -1, true, now);

        if (0 < cnt) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] 指定されたフォーラムにユーザが編集可能な
     *                    掲示予定のスレッドがあるかチェックします。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param usrSid ユーザSID
     * @param now 現在日時
     * @return true:ある  false:なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckRsvThreadEditData(Connection con, int bfiSid, int usrSid, UDate now)
            throws SQLException {

        int cnt = 0;

        BulletinDao btDao = new BulletinDao(con);
        cnt = btDao.countRsvThreData(bfiSid, usrSid, false, now);

        if (0 < cnt) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] スレッド情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid スレッドSID
     * @return スレッド情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getThreadData(Connection con, int btiSid)
    throws SQLException {

        BulletinDao btDao = new BulletinDao(con);
        BulletinDspModel btMdl = btDao.getThreadData(btiSid);

        return btMdl;
    }

    /**
     * <br>[機  能] 投稿情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bwiSid 投稿SID
     * @return 投稿情報
     * @throws SQLException SQL実行例外
     */
    public BulletinDspModel getWriteData(Connection con, int bwiSid)
    throws SQLException {

        BulletinDao bbsDao = new BulletinDao(con);
        BulletinDspModel bbsWriteMdl = bbsDao.getWriteData(bwiSid);

        return bbsWriteMdl;
    }

    /**
     * <br>[機  能] フォーラム集計情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param now システム日付
     * @throws SQLException SQL実行時例外
     */
    public void updateBbsForSum(Connection con, int bfiSid, int userSid, UDate now)
            throws SQLException {

        //スレッド数をカウント
        BulletinDao bbsDao = new BulletinDao(con);
        int bfsThreCnt = bbsDao.getThreadCount(bfiSid, now);

        //投稿数をカウント
        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con);
        int bfsWrtCnt = bbsWriteInfDao.getWriteCountInForum(bfiSid, now);

        //スレッド容量を集計
        BbsThreSumDao threSumDao = new BbsThreSumDao(con);
        long bfsSize = threSumDao.getTotalThreadSize(bfiSid);

        BbsForSumModel bbsForSumMdl = new BbsForSumModel();
        bbsForSumMdl.setBfiSid(bfiSid);
        bbsForSumMdl.setBfsThreCnt(bfsThreCnt);
        bbsForSumMdl.setBfsWrtCnt(bfsWrtCnt);
        bbsForSumMdl.setBfsWrtDate(now);
        bbsForSumMdl.setBfsEuid(userSid);
        bbsForSumMdl.setBfsEdate(now);
        bbsForSumMdl.setBfsSize(bfsSize);

        BbsForSumDao bbsForSumDao = new BbsForSumDao(con);
        bbsForSumDao.update(bbsForSumMdl);
    }

    /**
     * <br>[機  能] スレッド集計情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid スレッドSID
     * @param userSid ユーザSID
     * @param now システム日付
     * @param writeDate 最終書き込み日時
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException スレッド情報のエンコードに失敗
     */
    public void updateBbsThreSum(Connection con, int btiSid,
            int userSid, UDate now, UDate writeDate)
                    throws SQLException, UnsupportedEncodingException {

        //投稿数をカウント
        BulletinDao bbsDao = new BulletinDao(con);
        int btsWrtCnt = bbsDao.getWriteCount(btiSid);

        BbsThreSumModel bbsThreSumMdl = new BbsThreSumModel();
        bbsThreSumMdl.setBtiSid(btiSid);
        bbsThreSumMdl.setBtsWrtCnt(btsWrtCnt);
        bbsThreSumMdl.setBtsWrtDate(writeDate);
        bbsThreSumMdl.setBtsEuid(userSid);
        bbsThreSumMdl.setBtsEdate(now);
        bbsThreSumMdl.setBtsSize(getThreadDiskSize(con, btiSid));

        //スレッド(内部の投稿を含む)に添付ファイルが指定されているか
        BbsBinDao bbsBinDao = new BbsBinDao(con);
        if (bbsBinDao.existTempFile(btiSid)) {
            bbsThreSumMdl.setBtsTempflg(GSConstBulletin.BTS_TEMPFLG_EXIST);
        } else {
            bbsThreSumMdl.setBtsTempflg(GSConstBulletin.BTS_TEMPFLG_NOTHING);
        }

        BbsThreSumDao bbsThreSumDao = new BbsThreSumDao(con);
        bbsThreSumDao.updateThreSumData(bbsThreSumMdl);
    }

    /**
     * <br>[機  能] 添付ファイルのダウンロードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param tempDir テンポラリディレクトリ
     * @param fileId 添付ファイルID
     * @return fileName ファイル名
     * @throws Exception 添付ファイルのダウンロードに失敗
     */
    public String downloadTempFile(HttpServletRequest req, HttpServletResponse res,
                                String tempDir, String fileId)
    throws Exception {

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);
        return fMdl.getFileName();
    }

    /**
     * <br>[機  能] ショートメールで更新通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param bsmlModel 投稿内容(ショートメール送信用)
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Connection con,
        MlCountMtController cntCon,
        BulletinSmlModel bsmlModel,
        String appRootPath,
        PluginConfig pluginConfig,
        RequestModel reqMdl) throws Exception {

        //フォーラムSID
        int bfiSid = bsmlModel.getBfiSid();
        //フォーラム名
        BbsBiz bbsBiz = new BbsBiz(con);
        BulletinDspModel btDspMdl = bbsBiz.getForumData(con, bfiSid);
        String fname = btDspMdl.getBfiName();
        //タイトル
        String thtitle = bsmlModel.getBtiTitle();
        //投稿者名
        String tname = "";
        int grpSid = bsmlModel.getGrpSid();
        if (grpSid > 0) {
            CmnGroupmDao grpDao = new CmnGroupmDao(con);
            tname = grpDao.select(grpSid).getGrpName();
        } else {
            int userSid = bsmlModel.getUserSid();
            CmnUsrmInfDao udao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel umodel = udao.select(userSid);
            tname = umodel.getUsiSei() + " " + umodel.getUsiMei();
        }
        //投稿日時
        UDate ud = bsmlModel.getBwiAdate();
        String tdate = UDateUtil.getSlashYYMD(ud) + " "
        + UDateUtil.getSeparateHMS(ud);
        //添付ファイル
        List<LabelValueBean> files = bsmlModel.getFileLabelList();
        StringBuilder fileLine = new StringBuilder();
        for (LabelValueBean file : files) {
            String fileName = file.getLabel();
            if (fileLine.length() != 0) {
                fileLine.append(" , ");
            }
            fileLine.append(fileName);
        }

        //投稿内容
        String body = bsmlModel.getBwiValue();
        //フォーラムSIDからメンバー情報を持つフォーラムのSIDを取得する。
        bfiSid = bbsBiz.getBfiSidForMemberInfo(con, bfiSid);
        //宛先(通知を受けるに設定されたユーザSIDを取得する。)
        BbsUserDao buDao = new BbsUserDao(con);
        List<Integer> sidList
            = buDao.getSendSmailList(bfiSid, bbsBiz.canSetSmailConf(con, bsmlModel.getUserSid()));

        //本文
        String tmpPath = getSmlTemplateFilePath(appRootPath); //テンプレートファイルパス取得
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        Map<String, String> map = new HashMap<String, String>();
        map.put("FNAME", fname);
        map.put("TITLE", thtitle);
        map.put("UNAME", tname);
        map.put("DATE", tdate);
        map.put("FILES", fileLine.toString());
        map.put("BODY", body);
        map.put("URL", bsmlModel.getThreadUrl());

        String bodyml = StringUtil.merge(tmpBody, map);
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {

            String textMessage = gsMsg.getMessage("cmn.mail.omit");

            bodyml = textMessage + "\r\n\r\n" + bodyml;

            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);

        }

        //ショートメール送信用モデルを作成する。
        //
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(sidList);
        //タイトル
        String title = gsMsg.getMessage("bbs.29") + " " + thtitle;

        title = StringUtil.trimRengeString(title,
                GSConstCommon.MAX_LENGTH_SMLTITLE);
        smlModel.setSendTitle(title);

        //本文
        smlModel.setSendBody(bodyml);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        SmlSender sender = new SmlSender(con, cntCon, smlModel, pluginConfig, appRootPath, reqMdl);
        sender.execute();
    }

    /**
     *
     * <br>[機  能]ショートメール通知を行う(バッチ処理用)。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param domain ドメイン名
     * @param btiSid スレッドSID
     * @throws Exception 例外処理
     */
    public void sendSmailForBatch(
            Connection con,
            GSContext gsContext,
            String domain,
            int btiSid) throws Exception {

        //採番
        MlCountMtController cntCon =
                GroupSession.getResourceManager().getCountController(domain);
        //アプリケーションのルートパスを取得
        String appRootPath = (String) gsContext.get(GSContext.APP_ROOT_PATH);
        //プラグイン設定情報を取得
        PluginConfig pluginConfig =
                GroupSession.getResourceManager().getPluginConfig(domain);
        //リクエストモデルの取得
        RequestModel reqMdl = new RequestModel();
        reqMdl.setDomain(domain);
        reqMdl.setLocale(Locale.JAPANESE);
        //更新通知用モデルの取得
        BulletinSmlModel bsmModel = new BulletinSmlModel();
        BbsThreInfDao btiDao = new BbsThreInfDao(con);
        BbsThreInfModel btiModel = btiDao.select(btiSid);
        //フォーラムSID
        bsmModel.setBfiSid(btiModel.getBfiSid());
        //投稿者SID
        bsmModel.setUserSid(btiModel.getBtiAuid());
        //グループSID
        bsmModel.setGrpSid(btiModel.getBtiAgid());
        //スレッド名
        bsmModel.setBtiTitle(btiModel.getBtiTitle());
        //投稿日時
        bsmModel.setBwiAdate(btiModel.getBtiLimitFrDate());
        //添付ファイル名を取得
        BbsWriteInfDao bwiDao = new BbsWriteInfDao(con);
        BbsWriteInfModel bwiModel = bwiDao.selectByBti(btiSid);
        BulletinDao bbsDao = new BulletinDao(con);
        List<LabelValueBean> files = bbsDao.getTempName(bwiModel.getBwiSid());
        bsmModel.setFileLabelList(files);
        //内容
        //本文がプレーンテキストの場合
        if (bwiModel.getBwiType() == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            bsmModel.setBwiValue(bwiModel.getBwiValue());
        //本文がhtml形式の場合
        } else {
            String valuePlain = StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                    StringUtilHtml.transToText(bwiModel.getBwiValuePlain()));
            bsmModel.setBwiValue(valuePlain);
        }
        //スレッドURLを取得
        BbsThreRsvDao btrDao = new BbsThreRsvDao(con);
        String threadUrl = btrDao.selectByBti(btiSid).getBtrUrl();
        bsmModel.setThreadUrl(threadUrl);
        //ショートメール送信
       sendSmail(con, cntCon, bsmModel, appRootPath, pluginConfig, reqMdl);
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/bulletin/smail/koushin_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] 基準日以前の掲示板情報を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param delDate 削除対象の基準日
     * @return int 削除件数
     * @throws Exception 掲示板情報バッチ削除に失敗
     */
    public int deleteOldBulletin(Connection con, UDate delDate) throws SQLException {
        int ret = 0;
        log__.debug("基準日以前の掲示板情報を削除します。");
        BbsThreInfDao infDao = new BbsThreInfDao(con);
        //削除対象のスレッド情報を取得

        ArrayList<BbsThreInfModel> infList = infDao.getOldThreData(delDate);
        Bbs060Biz biz = new Bbs060Biz();
        for (BbsThreInfModel infMdl : infList) {
            biz.deleteThreadData(infMdl.getBfiSid(), infMdl.getBtiSid(), con, -1);
        }

        return ret;
    }

    /**
     * <br>[機  能] 掲示板閲覧情報を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid 削除スレッド
     * @throws Exception 掲示板情報バッチ削除に失敗
     */
    public void deleteThreadView(Connection con, int btiSid) throws SQLException {

        //削除情報を取得する。
        BbsThreViewDao bbsThreViewDao = new BbsThreViewDao(con);
        List<BbsThreViewModel> allDelList = bbsThreViewDao.getThreadView(btiSid);

        //削除する。
        if (allDelList == null || allDelList.size() < 1) {
            return;
        }

        int i = 0;
        int delCount = GSConstBulletin.BBS_BATCH_DELETE_COUNT;
        List<BbsThreViewModel> deleteList = new ArrayList<BbsThreViewModel>();
        for (BbsThreViewModel model : allDelList) {

            deleteList.add(model);
            i++;

            if (i >= delCount) {

                //物理削除する。
                bbsThreViewDao.delete(deleteList);

                deleteList = new ArrayList<BbsThreViewModel>();
                i = 0;
            }
        }

        if (deleteList != null && deleteList.size() > 0) {
            //物理削除する。
            bbsThreViewDao.delete(deleteList);
        }
    }


    /**
     * <br>[機  能] 掲示期限を過ぎた掲示板情報を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int 削除件数
     * @throws Exception 掲示板情報バッチ削除に失敗
     */
    public int deleteOverLimitBulletin(Connection con) throws SQLException {
        int ret = 0;
        log__.debug("掲示期限を過ぎた掲示板情報を削除します。");
        BbsThreInfDao infDao = new BbsThreInfDao(con);

        //削除対象のスレッド情報を取得
        UDate now = new UDate();

        BbsForInfDao forDao = new BbsForInfDao(con);
        List<BbsForInfModel> forList = forDao.select();
        ArrayList<BbsThreInfModel> infList = new ArrayList<BbsThreInfModel>();

        //フォーラムごとに保存期間を
        for (BbsForInfModel forMdl : forList) {
            UDate delDate = now.cloneUDate();

            //保存期間設定する
            if (forMdl.getBfiKeep() == GSConstBulletin.THREAD_KEEP_YES) {
                int year = forMdl.getBfiKeepDateY();
                int month = forMdl.getBfiKeepDateM();
                delDate.addYear(-year);
                delDate.addMonth(-month);
            }
            infList.addAll(infDao.getOverLimitThreData(forMdl.getBfiSid(), delDate));
        }
//        ArrayList<BbsThreInfModel> infList = infDao.getOverLimitThreData(delDate);

        Bbs060Biz biz = new Bbs060Biz();
        for (BbsThreInfModel infMdl : infList) {
            biz.deleteThreadData(infMdl.getBfiSid(), infMdl.getBtiSid(), con, -1);
        }

        return ret;
    }

    /**
     * <br>[機  能] スレッドURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @return スレッドURL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createThreadUrl(RequestModel reqMdl, int bfiSid, int btiSid)
    throws UnsupportedEncodingException {

        String threadUrl = null;
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        //スレッドのURLを作成
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);

            paramUrl += "/bulletin/bbs060" + ".do";
            paramUrl += "?bbs010forumSid=" + bfiSid;
            paramUrl += "&threadSid=" + btiSid;

            threadUrl = urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

        return threadUrl;
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     *
     * <br>[解  説] 登録に使用する側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で削除矢印ボタンをクリックした場合、
     *              登録に使用する側のコンボで選択中の値(deleteSelectSid)をメンバーリストから削除する。
     *
     * <br>[備  考] 登録に使用する側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param deleteSelectSid 登録に使用する側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 削除済みのメンバーリスト
     */
    public String[] getDeleteMember(String[] deleteSelectSid, String[] memberSid) {

        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }

        if (memberSid == null) {
            memberSid = new String[0];
        }

        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < memberSid.length; i++) {
            boolean setFlg = true;

            for (int j = 0; j < deleteSelectSid.length; j++) {
                if (memberSid[i].equals(deleteSelectSid[j])) {
                    setFlg = false;
                    break;
                }
            }

            if (setFlg) {
                list.add(memberSid[i]);
            }
        }

        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 指定したユーザがフォーラム管理者か判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return true:フォーラム管理者 false:管理者ではない
     * @throws SQLException SQL実行例外
     */
    public boolean isForumAdmin(int bfiSid, int usrSid, Connection con) throws SQLException {

        boolean forumAdmin = false;

        BbsForAdminDao forAdmDao = new BbsForAdminDao(con);
        BbsForAdminModel model = forAdmDao.select(bfiSid, usrSid);

        if (model != null) {
            forumAdmin = true;
        }

        return forumAdmin;
    }

    /**
     * 掲示板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value) {
        outPutLog(map, reqMdl, opCode, level, value, null, GSConstBulletin.BBS_LOG_FLG_NONE);
    }

    /**
     * 掲示板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルSID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            String fileId,
            int logFlg) {
        outPutLog(map, reqMdl, opCode, level, value, -1, fileId, logFlg);
    }

    /**
     * 掲示板全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param threadSid 投稿ID
     * @param fileId 添付ファイルSID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value,
            int threadSid,
            String fileId,
            int logFlg) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textBbs = gsMsg.getMessage("cmn.bulletin");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstBulletin.PLUGIN_ID_BULLETIN);
        logMdl.setLogPluginName(textBbs);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), reqMdl));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstBulletin.BBS_LOG_FLG_DOWNLOAD) {
            logMdl.setLogCode("binSid：" + fileId);
        } else if (logFlg == GSConstBulletin.BBS_LOG_FLG_PDF) {
            logMdl.setLogCode("PDF出力 threadSid：" + threadSid);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param reqMdl リクエスト情報
     * @return String
     */
    public String getPgName(String id, RequestModel reqMdl) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.bbs.bbs020.Bbs020Action")) {
            String textTitle = gsMsg.getMessage("bbs.14");
            String textAdm = gsMsg.getMessage("cmn.admin.setting");
            return textAdm + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs030kn.Bbs030knAction")) {
            String textTitle = gsMsg.getMessage("bbs.30");
            String textAdm = gsMsg.getMessage("cmn.admin.setting");
            return textAdm + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs050.Bbs050Action")) {
            String textTitle = gsMsg.getMessage("cmn.display.settings");
            String textPri = gsMsg.getMessage("cmn.preferences2");
            return textPri + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs052.Bbs052Action")) {
            String textTitle = gsMsg.getMessage("bbs.bbs052.1");
            String textPri = gsMsg.getMessage("cmn.preferences2");
            return textPri + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs060.Bbs060Action")) {
            String textTitle = gsMsg.getMessage("bbs.9")
                    + gsMsg.getMessage("wml.231") + gsMsg.getMessage("bbs.32");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs061.Bbs061Action")) {
            String textTitle = gsMsg.getMessage("bbs.9")
                    + gsMsg.getMessage("wml.231") + gsMsg.getMessage("bbs.32");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs070.Bbs070Action")) {
            String textTitle = gsMsg.getMessage("bbs.38");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs070kn.Bbs070knAction")) {
            String textTitle = gsMsg.getMessage("bbs.31");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs090.Bbs090Action")) {
            String textTitle = gsMsg.getMessage("bbs.39");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs090kn.Bbs090knAction")) {
            String textTitle = gsMsg.getMessage("bbs.33");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs120.Bbs120Action")) {
            String textTitle = gsMsg.getMessage("cmn.automatic.delete.setting");
            String textAdm = gsMsg.getMessage("cmn.admin.setting");
            return textAdm + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs150kn.Bbs150knAction")) {
            String textTitle = gsMsg.getMessage("cmn.bulletin")
                    + gsMsg.getMessage("cmn.manual.delete");
            String textAdm = gsMsg.getMessage("cmn.admin.setting");
            return textAdm + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs160.Bbs160Action")) {
            String textTitle = gsMsg.getMessage("bbs.bbs052.1");
            String textAdm = gsMsg.getMessage("cmn.admin.setting");
            return textAdm + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs170.Bbs170Action")) {
            String textTitle = "掲示予定一覧";
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs200.Bbs200Action")) {
            String textTitle = gsMsg.getMessage("bbs.bbs200.1");
            String textAdm = gsMsg.getMessage("cmn.admin.setting");
            return textAdm + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs210.Bbs210Action")) {
            String textTitle = gsMsg.getMessage("bbs.bbs200.1");
            String textPri = gsMsg.getMessage("cmn.preferences2");
            return textPri + " " + textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.bbs220.Bbs220Action")) {
            String textTitle = gsMsg.getMessage("bbs.bbs220.1");
            return textTitle;
        }
        if (id.equals("jp.groupsession.v2.bbs.ptl020.BbsPtl020Action")) {
            //ポータル プラグインポートレット
            return gsMsg.getMessage("ptl.ptl040.1");
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したスレッドのディスクサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid スレッドSID
     * @return ディスクサイズ
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException スレッド情報のエンコードに失敗
     */
    public long getThreadDiskSize(Connection con, int btiSid)
    throws SQLException, UnsupportedEncodingException {
        long diskSize = 0;

        //スレッド情報
        BbsThreInfDao threDao = new BbsThreInfDao(con);
        BbsThreInfModel threMdl = threDao.select(btiSid);
        diskSize = getThreadSize(threMdl.getBtiTitle());
        BulletinDao bbsDao = new BulletinDao(con);
        diskSize += bbsDao.getSumThreTempFileSize(btiSid);
        diskSize += bbsDao.getSumThreBodyFileSize(btiSid);

        //投稿情報
        BbsWriteInfDao writeDao = new BbsWriteInfDao(con);
        List<BbsWriteInfModel> writeList = writeDao.getWriteList(btiSid);
        for (BbsWriteInfModel writeData : writeList) {
            diskSize += getWriteSize(writeData.getBwiValue());
            if (writeData.getBwiType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
                diskSize += getWriteSize(writeData.getBwiValuePlain());
            }
        }

        return diskSize;
    }

    /**
     * <br>[機  能] スレッド情報のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiTitle スレッドタイトル
     * @return スレッド情報のサイズ
     * @throws UnsupportedEncodingException スレッド情報のエンコードに失敗
     */
    public long getThreadSize(String btiTitle) throws UnsupportedEncodingException {
        return 20 + btiTitle.getBytes(GSConst.ENCODING).length + 24 + 4;
    }

    /**
     * <br>[機  能] 投稿情報のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bwiValue 投稿内容
     * @return 投稿情報のサイズ
     * @throws UnsupportedEncodingException 投稿情報のエンコードに失敗
     */
    public long getWriteSize(String bwiValue) throws UnsupportedEncodingException {
        if (bwiValue == null) {
            return 0;
        }
        return 20 + bwiValue.getBytes(GSConst.ENCODING).length + 24 + 4;
    }

    /**
     * <br>[機  能] 投稿の添付ファイルサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイルサイズ
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public long getWriteTempFileSize(String tempDir)
    throws IOToolsException {
        long fileSize = 0;

        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(tempDir);
        for (LabelValueBean fileData : fileList) {
            String saveFilePath = IOTools.setEndPathChar(tempDir);
            saveFilePath += fileData.getValue().concat(GSConstCommon.ENDSTR_SAVEFILE);
            fileSize += new File(saveFilePath).length();
        }

        return fileSize;
    }

    /**
     * <br>[機  能] 投稿情報のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bwiSid 投稿情報
     * @return 投稿情報のサイズ
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException 投稿情報のエンコードに失敗
     */
    public long getWriteSize(Connection con, int bwiSid)
    throws SQLException, UnsupportedEncodingException {
        BbsWriteInfDao writeDao = new BbsWriteInfDao(con);
        BbsWriteInfModel writeData = writeDao.select(bwiSid);
        long writeSize = getWriteSize(writeData.getBwiValue());
        if (writeData.getBwiType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
            writeSize += getWriteSize(writeData.getBwiValuePlain());
        }

        BulletinDao bbsDao = new BulletinDao(con);
        writeSize += bbsDao.getSumWriteTempFileSize(bwiSid);
        writeSize += bbsDao.getSumWriteBodyFileSize(bwiSid);

        return writeSize;
    }

    /**
     * <br>[機  能] 指定したフォーラムのディスク容量に関する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @return true: 正常、 false: 警告有り
     * @throws SQLException SQL実行時例外
     */
    public BulletinForumDiskModel getForumDiskData(Connection con, int bfiSid) throws SQLException {
        BulletinDao bbsDao = new BulletinDao(con);
        return bbsDao.getForumDiskData(bfiSid);
    }

    /**
     * <br>[機  能] 指定したフォーラムのディスク容量警告チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @return true: 警告あり、 false: 警告なし
     * @throws SQLException SQL実行時例外
     */
    public boolean checkForumWarnDisk(Connection con, int bfiSid) throws SQLException {
        return checkForumWarnDisk(getForumDiskData(con, bfiSid));
    }

    /**
     * <br>[機  能] 指定したフォーラムのディスク容量警告チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param diskData フォーラムのディスク容量に関する情報
     * @return true: 警告あり、 false: 警告なし
     * @throws SQLException SQL実行時例外
     */
    public boolean checkForumWarnDisk(BulletinForumDiskModel diskData) throws SQLException {

        if (diskData.getBfiDisk() != GSConstBulletin.BFI_DISK_LIMITED
        || diskData.getBfiWarnDisk() != GSConstBulletin.BFI_WARN_DISK_YES) {
            return false;
        }

        int diskSize = diskData.getBfiDiskSize();
        int diskWarnTh = diskData.getBfiWarnDiskTh();
        BigDecimal useDiskSize = new BigDecimal(diskData.getBfsSize());
        BigDecimal limitSize = new BigDecimal(diskWarnTh * 1024 * 1024);
        limitSize = limitSize.divide(new BigDecimal(100), 2,
                                                BigDecimal.ROUND_HALF_UP);
        limitSize = limitSize.multiply(new BigDecimal(diskSize));

        return useDiskSize.compareTo(limitSize) >= 0;
    }

    /**
     * <br>[機  能] 個人設定でショートメール通知設定が可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSetSmailConf(Connection con, int userSid) throws SQLException {
        BbsAdmConfModel admData = getBbsAdminData(con, userSid);
        return admData.getBacSmlNtf() != GSConstBulletin.BAC_SML_NTF_ADMIN;
    }

    /**
     * <br>[機  能] 管理者設定でショートメール通知が許可されているが判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:通知可能 false:通知不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canSendSmail(Connection con, int userSid) throws SQLException {
        BbsAdmConfModel admData = getBbsAdminData(con, userSid);
        return admData.getBacSmlNtf() != GSConstBulletin.BAC_SML_NTF_ADMIN
                || admData.getBacSmlNtfKbn() == GSConstBulletin.BAC_SML_NTF_KBN_YES;
    }

    /**
     * <br>[機  能] 入力されたFrom日付が未来の日付かチェックする。(年月日および時間)
     * <br>[解  説]
     * <br>[備  考]
     * @param limitFrDate From日時
     * @param now 基準日
     * @return true:未来  false:本日 or 過去
     */
    public boolean checkReserveDate(UDate limitFrDate, UDate now) {
        int diff = now.compareDateYMDHM(limitFrDate);
        if (diff == UDate.LARGE) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 入力されたTo日付が過去の日付かチェックする。(年月日および時間)
     * <br>[解  説]
     * <br>[備  考]
     * @param limitFrDate From日時
     * @param now 基準日
     * @return true:未来  false:本日 or 過去
     */
    public boolean checkToDate(UDate limitFrDate, UDate now) {
        int diff = limitFrDate.compareDateYMDHM(now);
        if (diff == UDate.SMALL) {
            return true;
        }
        return false;
    }



    /**
     * <br>[機  能] 指定したフォーラムの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行時例外
     * @return true:存在する  false:存在しない
     */
    public boolean isCheckExistForum(Connection con, int bfiSid) throws SQLException {
        BbsForInfDao dao = new BbsForInfDao(con);
        return dao.countBfi(bfiSid) > 0;
    }
    /**
     * <br>[機  能] 指定したスレッドの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param btiSid スレッドSID
     * @throws SQLException SQL実行時例外
     * @return true:存在する  false:存在しない
     */
    public boolean isCheckExistThread(Connection con, int btiSid) throws SQLException {
        BbsThreInfDao dao = new BbsThreInfDao(con);
        return dao.countBti(btiSid) > 0;
    }

    /**
     * <br>[機  能] 指定した投稿の存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bwiSid 投稿SID
     * @throws SQLException SQL実行時例外
     * @return true:存在する  false:存在しない
     */
    public boolean isCheckExistWrite(Connection con, int bwiSid) throws SQLException {
        BbsWriteInfDao dao = new BbsWriteInfDao(con);
        return dao.countBwi(bwiSid) > 0;
    }

    /**
     * <br>[機  能] 掲示板閲覧時の集計用データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param bfiSid フィールドSID
     * @param btiSid スレッドSID
     * @param date 閲覧日時
     * @throws SQLException SQL実行時例外
     */
    public void regBbsViewLogCnt(
            Connection con, int usrSid, int bfiSid, int btiSid, UDate date)
                    throws SQLException {
        boolean commitFlg = false;
        con.setAutoCommit(false);
        //ロックの解除処理
        try {
            BbsViewLogCountModel mdl = new BbsViewLogCountModel();
            mdl.setUsrSid(usrSid);
            mdl.setBfiSid(bfiSid);
            mdl.setBtiSid(btiSid);
            mdl.setBvlViewDate(date);
            BbsViewLogCountDao dao = new BbsViewLogCountDao(con);
            dao.insert(mdl);

            //ログ集計
            __registLogCntSum(con, GSConstBulletin.BLS_KBN_VIEW, date);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("閲覧時集計用データの登録に失敗" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 掲示板投稿時の集計用データを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param grpSid グループID
     * @param bfiSid フィールドSID
     * @param btiSid スレッドSID
     * @param date 投稿日時
     * @throws SQLException SQL実行時例外
     */
    public void regBbsWriteLogCnt(
            Connection con, int usrSid, int grpSid,
            int bfiSid, int btiSid, UDate date)
                    throws SQLException {
        BbsWriteLogCountModel mdl = new BbsWriteLogCountModel();
        mdl.setUsrSid(usrSid);
        mdl.setGrpSid(grpSid);
        mdl.setBfiSid(bfiSid);
        mdl.setBtiSid(btiSid);
        mdl.setBwlWrtDate(date);
        BbsWriteLogCountDao dao = new BbsWriteLogCountDao(con);
        dao.insert(mdl);

        //ログ集計
        __registLogCntSum(con, GSConstBulletin.BLS_KBN_WRITE, date);
    }

    /**
     * <br>[機  能] 掲示板集計データ_集計結果を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param blsKbn ログ区分
     * @param date 日時
     * @throws SQLException SQL実行時例外
     */
    private void __registLogCntSum(
            Connection con, int blsKbn, UDate date) throws SQLException {

        BbsLogCountSumDao logSumDao = new BbsLogCountSumDao(con);
        BbsLogCountSumModel logSumMdl = logSumDao.getSumLogCount(blsKbn, date);
        if (logSumMdl != null) {
            if (logSumDao.update(logSumMdl) == 0) {
                logSumDao.insert(logSumMdl);
            }
        }
    }

    /**
     * <br>[機  能] 投稿形式の初期値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @param userSid ユーザSID
     * @return 投稿形式 0:プレーンテキスト 1:HTML
     * @throws SQLException SQL実行時例外
     */
    public int getInitPostType(
            Connection con, int forumSid, int userSid)
                    throws SQLException {

        //フォーラムにテンプレートが設定されているか
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        BbsForInfModel bfiMdl = new BbsForInfModel();
        bfiMdl.setBfiSid(forumSid);
        bfiMdl = bfiDao.select(bfiMdl);
        if (bfiMdl.getBfiTemplateKbn() == GSConstBulletin.BBS_THRE_TEMPLATE_YES) {
            //フォーラムのテンプレート設定を適用する
            return bfiMdl.getBfiTemplateType();
        }

        //管理者設定の初期値設定取得
        BbsAdmConfModel bacMdl = getBbsAdminData(con, userSid);
        int postTypeKbn = bacMdl.getBacIniPostTypeKbn();
        if (postTypeKbn == GSConstBulletin.BAC_INI_POST_TYPE_KBN_ADM) {
            //管理者設定の初期値設定を適用する
            return bacMdl.getBacIniPostType();
        }

        //個人設定の初期値設定を適用する
        BbsUserModel buMdl = getBbsUserData(con, userSid);
        return buMdl.getBurIniPostType();
    }

    /**
     * <br>[機  能] ユーザがメンバーに含まれているフォーラムリストを取得します。
     * <br>[解  説]
     * <br>[備  考] 親フォーラムのメンバー設定に準拠するフォーラムも含みます。権限を指定しません。
     * @param con コネクション
     * @param userSid ユーザSID
     * @return ユーザがメンバーに含まれているフォーラムリスト
     * @throws SQLException SQL実行時例外
     */
    public List<BbsForInfModel> getForumListOfUser(Connection con, int userSid)
            throws SQLException {
        int bfmAuth = -1;
        return getForumListOfUser(con, userSid, bfmAuth);
    }

    /**
     * <br>[機  能] ユーザがメンバーに含まれているフォーラムリストを取得します。
     * <br>[解  説]
     * <br>[備  考] 親フォーラムのメンバー設定に準拠するフォーラムも含みます
     * @param con コネクション
     * @param userSid ユーザSID
     * @param bfmAuth メンバー権限区分
     * @return ユーザがメンバーに含まれているフォーラムリスト
     * @throws SQLException SQL実行時例外
     */
    public List<BbsForInfModel> getForumListOfUser(
            Connection con, int userSid, int bfmAuth)
                    throws SQLException {
        List<BbsForInfModel> ret = new  ArrayList<BbsForInfModel>();
        List<BbsForInfModel> bfiMdlList = new ArrayList<BbsForInfModel>();

        //フォーラムメンバーテーブルからフォーラムSIDを取得
        BbsForMemDao bfmDao = new BbsForMemDao(con);
        List<BbsForMemModel> bfmMdlList = bfmDao.getForumOfUser(userSid, bfmAuth);

        //フォーラムSIDからフォーラム情報リストを取得
        List<Integer> bfiSidList = new ArrayList<Integer>();
        for (BbsForMemModel bfmMdl : bfmMdlList) {
            if (bfiSidList.indexOf(bfmMdl.getBfiSid()) < 0) {
                bfiSidList.add(bfmMdl.getBfiSid());
            }
        }
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        bfiMdlList.addAll(bfiDao.select(bfiSidList));

        //親フォーラムのメンバー設定に準拠するフォーラムを取得
        BulletinDao bbsDao = new BulletinDao(con);
        bfiMdlList.addAll(bbsDao.getFollowingForumList(bfiSidList));

        ret = bfiMdlList;

        return ret;
    }

    /**
     * <br>[機  能] フォーラムの件数を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者 false:一般ユーザ
     * @param isHierarchy 階層表示か否か true:階層表示である false:階層表示でない
     * @return フォーラム件数
     * @throws SQLException SQL実行例外
     */
    public int getForumCount(
            Connection con, int userSid, boolean admin, boolean isHierarchy)
                    throws SQLException {
        //ユーザがメンバーに含まれているフォーラムのSIDを取得
        List<BbsForInfModel> bfiMdlList = getForumListOfUser(con, userSid);
        List<Integer> bfiSidList = new ArrayList<Integer>();
        for (BbsForInfModel bfiSid : bfiMdlList) {
            bfiSidList.add(bfiSid.getBfiSid());
        }

        BulletinDao bbsDao = new BulletinDao(con);
        return bbsDao.getForumCount(bfiSidList, admin, isHierarchy);
    }

    /**
     * <br>[機  能] ユーザがメンバーに含まれているフォーラムリストを取得します。
     * <br>[解  説]
     * <br>[備  考] 親フォーラムのメンバー設定に準拠するフォーラムも含みます
     * @param con コネクション
     * @param groupSidList グループSIDリスト
     * @return ユーザがメンバーに含まれているフォーラムリスト
     * @throws SQLException SQL実行時例外
     */
    public List<BbsForInfModel> getForumListOfGroup(Connection con, List<Integer> groupSidList)
            throws SQLException {
        List<BbsForInfModel> ret = new  ArrayList<BbsForInfModel>();
        List<BbsForInfModel> bfiMdlList = new ArrayList<BbsForInfModel>();

        //フォーラムメンバーテーブルからフォーラムSIDを取得
        BbsForMemDao bfmDao = new BbsForMemDao(con);
        List<Integer> bfiSidList = new ArrayList<Integer>();
        bfiSidList.addAll(bfmDao.getForumOfGroup(groupSidList));

        BbsForInfDao bfiDao = new BbsForInfDao(con);
        bfiMdlList.addAll(bfiDao.select(bfiSidList));

        //親フォーラムのメンバー設定に準拠するフォーラムを取得
        BulletinDao bbsDao = new BulletinDao(con);
        bfiMdlList.addAll(bbsDao.getFollowingForumList(bfiSidList));

        List<Integer> checkSidList = new ArrayList<Integer>();
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            if (!checkSidList.contains(bfiMdl.getBfiSid())) {
                ret.add(bfiMdl);
                checkSidList.add(bfiMdl.getBfiSid());
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] フォーラムメンバーを参照する際のフォーラムSIDを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行時例外
     * @return フォーラムメンバーを参照する際のフォーラムSID
     */
    public int getBfiSidForMemberInfo(Connection con, int bfiSid)
            throws SQLException {
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        return bfiDao.getBfiSidForMemberInfo(bfiSid);
    }

    /**
     * <br>[機  能] 指定したフォーラムのメンバー数を取得します。
     * <br>[解  説] 削除されたユーザ(CMN_USRM.USR_JKBN = 9:削除ユーザ)は除く
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @return フォーラムのメンバー数
     * @throws SQLException SQL実行例外
     */
    public int getForumMemberCount(Connection con, int bfiSid) throws SQLException {
        BbsBiz bbsBiz = new BbsBiz();
        bfiSid = bbsBiz.getBfiSidForMemberInfo(con, bfiSid);

        BulletinDao bbsDao = new BulletinDao(con);
        return bbsDao.getForumMemberCount(bfiSid);
    }

    /**
     * <br>[機  能] 指定したフォーラムを親としているフォーラムのフォーラム情報を取得します
     * <br>[解  説]
     * <br>[備  考] 表示順でソートされています
     * @param con コネクション
     * @param parentSid 親フォーラムSID
     * @param adminDisp 管理者設定画面用の表示か否か true:管理者設定用画面 false:通常画面
     * @param userSid ユーザSID
     * @return BBS_FOR_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> getSortedChildForum(
            Connection con, int parentSid, boolean adminDisp, int userSid)
                    throws SQLException {
        //ユーザがメンバーに含まれているフォーラムのSIDを取得
        List<Integer> groupSidList = new ArrayList<Integer>();
        if (!adminDisp) {
            List<BbsForInfModel> bfiMdlList = getForumListOfUser(con, userSid);
            for (BbsForInfModel bfiMdl : bfiMdlList) {
                groupSidList.add(bfiMdl.getBfiSid());
            }
        }

        BulletinDao bbsDao = new BulletinDao(con);
        return bbsDao.getSortedChildForum(parentSid, adminDisp, userSid, groupSidList);
    }

    /**
     * <br>[機  能] フォーラムの件数を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param searchMdl 検索条件
     * @return フォーラム件数
     * @throws SQLException SQL実行例外
     */
    public int getForumSearchCount(Connection con, BulletinSearchModel searchMdl)
            throws SQLException {
        //ユーザがメンバーに含まれているフォーラムのSIDを取得
        List<Integer> bfiSidList = new ArrayList<Integer>();
        List<BbsForInfModel> bfiMdlList = getForumListOfUser(con, searchMdl.getUserSid());
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            bfiSidList.add(bfiMdl.getBfiSid());
        }

        BulletinDao bbsDao = new BulletinDao(con);
        return bbsDao.getForumSearchCount(searchMdl, bfiSidList);
    }

    /**
     * <br>[機  能] 草稿情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param bskSid 草稿SID
     * @return 草稿情報
     * @throws SQLException SQL実行例外
     */
    public BulletinSoukouModel getSoukouData(Connection con, int bskSid, int userSid)
    throws SQLException {

        BbsSoukouDao soDao = new BbsSoukouDao(con);
        BulletinSoukouModel soMdl = soDao.selectSoukouInfo(userSid, bskSid);
        if (soMdl == null) {
            soMdl = new BulletinSoukouModel();
        }
        soMdl.setFilesInfo(soDao.getSoukouTmpFileList(bskSid));
        return soMdl;
    }

    /**
     * <br>[機  能]草稿削除
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukou(int bskSid) throws SQLException {
        BbsSoukouDao dao = new BbsSoukouDao(con__);
            // 草稿削除
            dao.delete(bskSid);
     }


    /**
     * <br>[機  能]草稿添付削除
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukouBin(int bskSid) throws SQLException {
        BbsSoukouBinDao binDao = new BbsSoukouBinDao(con__);
            //バイナリー情報の論理削除
            int binNum = binDao.updateBinfForSoukou(bskSid);
            // 草稿添付の削除
            if (binNum > 0) {
                binDao.deleteSoukouBin(bskSid);
            }
        }

    /**
     * <br>[機  能]草稿本文添付削除
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukouBodyBin(int bskSid) throws SQLException {
        BbsSoukouBodyBinDao binBodyDao = new BbsSoukouBodyBinDao(con__);
            //バイナリー情報の論理削除
            int binBodyNum = binBodyDao.updateBinfForSoukou(bskSid);
            // 草稿本文添付の削除
            if (binBodyNum > 0) {
                binBodyDao.deleteSoukouBodyBin(bskSid);
            }
     }

    /**
     * <br>[機  能] 草稿存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @param userSid ユーザSID
     * @return 草稿存在判定
     * @throws SQLException SQL実行例外
     */
    public boolean existSoukou(
            int bskSid,
            int userSid
            )throws SQLException {
        BbsSoukouDao dao = new BbsSoukouDao(con__);
        boolean exist = dao.existSoukou(bskSid, userSid);
        return exist;
    }

    /**
     * <br>[機  能] 草稿編集可能権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @param soukouType 草稿タイプ
     * @param buMdl BaseUserModel
     * @param adminFlg 管理者フラグ
     * @return true:可能
     * @throws SQLException SQL実行例外
     */
    public boolean canEditSoukou(
            int bfiSid,
            int btiSid,
            int soukouType,
            BaseUserModel buMdl,
            boolean adminFlg
            )throws SQLException {

        if (!isForumEditAuth(con__, bfiSid, buMdl)) {
            return false;
        }

        if (soukouType == GSConstBulletin.SOUKOU_TYPE_TOUKOU) {

            // 返信可能チェック
            BbsForInfDao bfiDao = new BbsForInfDao(con__);
            BbsForInfModel bfiModel = new BbsForInfModel();
            bfiModel.setBfiSid(bfiSid);
            BbsForInfModel rep = bfiDao.select(bfiModel);
            // 返信できない
            if (rep == null
                    || rep.getBfiReply() == GSConstBulletin.BBS_THRE_REPLY_NO) {
                return false;
            }
            BbsThreInfDao thredao = new BbsThreInfDao(con__);
            BbsThreInfModel btiMdl = thredao.select(btiSid);
            if (btiMdl == null) {
                // スレッドが存在しない
                return false;
            }
            UDate now = new UDate();
            UDate limitFrDate = btiMdl.getBtiLimitFrDate();
            UDate limitToDate = btiMdl.getBtiLimitDate();
            // 期限が設定されているかつ掲示期間外の場合
            if (btiMdl.getBtiLimit() == GSConstBulletin.THREAD_LIMIT_YES) {
                if (now.compareDateYMDHM(limitFrDate) == UDate.LARGE) {
                    return false;
                }
                if (now.compareDateYMDHM(limitToDate) == UDate.SMALL) {
                    // 過去の掲示板の場合は、管理者権限を持つなら編集可能
                    if (adminFlg || isForumAdmin(bfiSid, buMdl.getUsrsid(), con__)) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <br>[機  能]草稿本文添付ファイル名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @param fileId ファイル番号
     * @throws SQLException SQL実行例外
     * @return 削除成功判定
     */
    public String getBodyImgFilename(int bskSid, int fileId) throws SQLException {
        BbsSoukouBodyBinDao binBodyDao = new BbsSoukouBodyBinDao(con__);
        return  binBodyDao.selectFileName(bskSid, fileId);
     }

    /**
     * <br>[機  能]親フォーラム名を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param forumSid フォーラムSID
     * @return 親フォーラム名
     * @throws SQLException SQLException
     */
    public String getParentForumName(int forumSid) throws SQLException {

        BbsForInfDao infDao = new BbsForInfDao(con__);
        String parentForum = infDao.getParentForum(forumSid);
        if (parentForum == null || parentForum.equals("")) {
            parentForum = "なし";
        }
        return parentForum;
    }

}
