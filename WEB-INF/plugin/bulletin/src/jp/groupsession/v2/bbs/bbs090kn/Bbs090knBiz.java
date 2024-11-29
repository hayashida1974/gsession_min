package jp.groupsession.v2.bbs.bbs090kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs070.Bbs070Biz;
import jp.groupsession.v2.bbs.dao.BbsBinDao;
import jp.groupsession.v2.bbs.dao.BbsBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsBinModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.bbs.model.BbsWriteInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinSmlModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] 掲示板 投稿登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs090knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs090knBiz.class);

    /** DBコネクション */
    public Connection con__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     */
    public Bbs090knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    @SuppressWarnings("unchecked")
    public void setInitData(Bbs090knParamModel paramMdl, RequestModel reqMdl, String tempDir)
    throws Exception {
        log__.debug("START");

        //フォーラム名、スレッド名称を設定
        BbsBiz biz = new BbsBiz();
        BulletinDspModel bbsMdl = biz.getThreadData(con__, paramMdl.getThreadSid());
        paramMdl.setBbs090forumName(bbsMdl.getBfiName());
        paramMdl.setBbs090threTitle(bbsMdl.getBtiTitle());



        //投稿者を設定
        int contributor = paramMdl.getBbs090contributor();
        if (contributor > 0) {
            int registUser = reqMdl.getSmodel().getUsrsid();
            GroupDao grpDao = new GroupDao(con__);
            if (!grpDao.isBelong(registUser, contributor)) {
                BulletinDspModel bbsWriteMdl
                    = biz.getWriteData(con__, paramMdl.getBbs060postSid());
                if (bbsWriteMdl == null) {
                    bbsWriteMdl = new BulletinDspModel();
                }
                contributor = bbsWriteMdl.getGrpSid();
                paramMdl.setBbs090contributor(contributor);
            }
            CmnGroupmDao cmnGrpDao = new CmnGroupmDao(con__);
            CmnGroupmModel grpMdl = cmnGrpDao.selectGroup(contributor);
            if (grpMdl != null) {
                paramMdl.setBbs090contributorJKbn(grpMdl.getGrpJkbn());
                paramMdl.setBbs090knviewContributor(
                        grpMdl.getGrpName());
            }
        } else {
            int registUser = reqMdl.getSmodel().getUsrsid();
            if (paramMdl.getBbs090cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
                //編集の場合、新規登録時の登録ユーザを投稿者とする
                BbsWriteInfDao writeDao = new BbsWriteInfDao(con__);
                registUser = writeDao.getWriteAuid(paramMdl.getBbs060postSid());
            }
            CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con__);
            CmnUsrmInfModel usiMdl = usiDao.selectUserNameAndJtkbn(registUser);
            String name = NullDefault.getString(usiMdl.getUsiSei(), "")
                    + "　" + NullDefault.getString(usiMdl.getUsiMei(), "");

            paramMdl.setBbs090knviewContributor(name);
            paramMdl.setBbs090contributorJKbn(usiMdl.getUsrJkbn());

        }

        if (paramMdl.getBbs090valueType() == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //コメントを設定
            String viewValue = StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                    NullDefault.getString(paramMdl.getBbs090value(), "")); 
            paramMdl.setBbs090knviewvalue(viewValue);
        }

        if (paramMdl.getBbs090valueType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
            //内容から不正なタグを除去
            paramMdl.setBbs090valueHtml(
                    StringUtilHtml.removeIllegalTag(paramMdl.getBbs090valueHtml()));
        }

        //添付ファイル一覧を設定
        CommonBiz cmnBiz = new CommonBiz();

        List<LabelValueBean> sortList = cmnBiz.getTempFileLabelList(tempDir);
        Collections.sort(sortList);
        paramMdl.setBbs090FileLabelList(sortList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 投稿情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void insertWriteData(Bbs090knParamModel paramMdl,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();

        int bfiSid = paramMdl.getBbs010forumSid();
        int btiSid = paramMdl.getThreadSid();

        //投稿SID採番
        int bwiSid = (int) cntCon.getSaibanNumber(GSConstBulletin.SBNSID_BULLETIN,
                                                    GSConstBulletin.SBNSID_SUB_BULLETIN_WRITE,
                                                    userSid);
        paramMdl.setBbs060postSid(bwiSid);

        //本文のタイプ
        int valueType = paramMdl.getBbs090valueType();
        //本文
        String postValue = null;

        CommonBiz cmnBiz = new CommonBiz();

        if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //本文がプレーンテキストの場合
            postValue = paramMdl.getBbs090value();

        } else {
            //本文がHTMLテキストの場合
            postValue = paramMdl.getBbs090valueHtml();

            //本文内ファイルのIDを取得し、表示用のタグに変換。
            Bbs070Biz bbs070Biz = new Bbs070Biz();
            List <String> bodyFileList = new ArrayList<String>();
            postValue = StringUtilHtml.replaceString(postValue, "&", "&amp;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;amp;", "&amp;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;lt;", "&lt;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;gt;", "&gt;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;quot;", "&quot;");
            postValue = bbs070Biz.getBbsBodyFileList(
                    paramMdl.getBbs060postSid(), postValue, bodyFileList, false);

            //本文から投稿本文添付情報を取得し、バイナリー情報に登録
            List <String> bodyBinSidList = new ArrayList<String>();
            String bodyFileTempDir = null;
            for (String bodyFileId : bodyFileList) {
                bodyFileTempDir = tempDir + GSConstCommon.EDITOR_BODY_FILE
                        + File.separator + bodyFileId + File.separator;
                List <String> bodyBinSid = cmnBiz.insertBinInfo(
                        con__, bodyFileTempDir, appRootPath, cntCon, userSid, now);
                if (bodyBinSid != null && bodyBinSid.size() > 0) {
                    bodyBinSidList.add(bodyBinSid.get(0));
                }
            }

            //投稿本文添付情報テーブルへの登録
            BbsBodyBinDao bbsBodyBinDao = new BbsBodyBinDao(con__);
            bbsBodyBinDao.insertBbsBodyBinData(bwiSid, bodyFileList, bodyBinSidList);
        }

        //投稿情報の登録
        BbsWriteInfModel bbsWriteInfMdl = new BbsWriteInfModel();
        bbsWriteInfMdl.setBwiSid(bwiSid);
        bbsWriteInfMdl.setBfiSid(bfiSid);
        bbsWriteInfMdl.setBtiSid(btiSid);
        bbsWriteInfMdl.setBwiType(valueType);
        if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //本文がプレーンテキストの場合
            bbsWriteInfMdl.setBwiValue(postValue);
            bbsWriteInfMdl.setBwiValuePlain("");

        } else {
            //本文がHTMLテキストの場合
            bbsWriteInfMdl.setBwiValue(postValue);
            //BRタグを\r\nに変換後、タグを除去し、プレーンテキストとして登録
            String postValuePlain = StringUtilHtml.replaceString(postValue, "<br/>", "<BR>");
            postValuePlain = StringUtilHtml.transBRtoCRLF(postValuePlain);
            postValuePlain = StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(postValuePlain);
            postValuePlain = StringEscapeUtils.unescapeHtml(postValuePlain);
            bbsWriteInfMdl.setBwiValuePlain(postValuePlain);
        }
        bbsWriteInfMdl.setBwiAuid(userSid);
        bbsWriteInfMdl.setBwiAdate(now);
        bbsWriteInfMdl.setBwiEuid(userSid);
        bbsWriteInfMdl.setBwiEdate(now);
        int contributor = 0;
        if (paramMdl.getBbs090contributor() > 0) {
            contributor = paramMdl.getBbs090contributor();
        }
        bbsWriteInfMdl.setBwiAgid(contributor);
        bbsWriteInfMdl.setBwiEgid(contributor);
        bbsWriteInfMdl.setBwiParentFlg(0);

        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con__);
        bbsWriteInfDao.insert(bbsWriteInfMdl);

        //添付ファイルをバイナリー情報の登録
        List <String> binSid = cmnBiz.insertBinInfo(con__, tempDir, appRootPath,
                                                    cntCon, userSid, now);

        //投稿添付情報の登録
        BbsBinModel bbsBinMdl = new BbsBinModel();
        bbsBinMdl.setBwiSid(bwiSid);
        bbsBinMdl.setBinSid(Long.valueOf(0));
        BbsBinDao bbsBinDao = new BbsBinDao(con__);
        bbsBinDao.insertBbsBinData(bwiSid, binSid);

        //スレッド閲覧情報の更新
        BbsThreViewModel threViewMdl = new BbsThreViewModel();
        threViewMdl.setBtiSid(btiSid);
        threViewMdl.setBivViewKbn(GSConstBulletin.BBS_THRE_VIEW_NO);
        threViewMdl.setBivEuid(userSid);
        threViewMdl.setBivEdate(now);
        BbsThreViewDao threViewDao = new BbsThreViewDao(con__);
        threViewDao.updateAllUserViewKbn(threViewMdl);

        //スレッド情報の更新(投稿者)
        BbsThreInfDao bbsThreInfDao = new BbsThreInfDao(con__);
        BbsThreInfModel bbsThreInfMdl = new BbsThreInfModel();
        bbsThreInfMdl.setBtiSid(btiSid);
        bbsThreInfMdl.setBtiEuid(userSid);
        bbsThreInfMdl.setBtiEdate(now);
        bbsThreInfMdl.setBtiEgid(contributor);
        bbsThreInfDao.updateContributor(bbsThreInfMdl);

        //スレッド集計情報の更新
        BbsBiz bbsBiz = new BbsBiz();
        bbsBiz.updateBbsThreSum(con__, btiSid, userSid, now, now);

        //フォーラム集計情報の更新
        bbsBiz.updateBbsForSum(con__, bfiSid, userSid, now);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 投稿情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void updateWriteData(Bbs090knParamModel paramMdl,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();

        int bfiSid = paramMdl.getBbs010forumSid();
        int btiSid = paramMdl.getThreadSid();
        int bwiSid = paramMdl.getBbs060postSid();

        //登録済みのバイナリー情報の削除
        BulletinDao bbsDao = new BulletinDao(con__);
        bbsDao.deleteBinfForWrite(bwiSid);

        //登録済みの投稿本文添付情報の削除
        BbsBodyBinDao bbsBodyBinDao = new BbsBodyBinDao(con__);
        bbsBodyBinDao.delete(bwiSid);

        //本文のタイプ
        int valueType = paramMdl.getBbs090valueType();
        //本文
        String postValue = null;

        CommonBiz cmnBiz = new CommonBiz();

        if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //本文がプレーンテキストの場合
            postValue = paramMdl.getBbs090value();

        } else {
            //本文がHTMLテキストの場合
            postValue = paramMdl.getBbs090valueHtml();

            //本文内ファイルのIDを取得し、表示用のタグに変換。
            Bbs070Biz bbs070Biz = new Bbs070Biz();
            List <String> bodyFileList = new ArrayList<String>();
            postValue = StringUtilHtml.replaceString(postValue, "&", "&amp;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;amp;", "&amp;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;lt;", "&lt;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;gt;", "&gt;");
            postValue = StringUtilHtml.replaceString(postValue, "&amp;quot;", "&quot;");
            postValue = bbs070Biz.getBbsBodyFileList(
                    paramMdl.getBbs060postSid(), postValue, bodyFileList, false);

            //本文から投稿本文添付情報を取得し、バイナリー情報に登録
            List <String> bodyBinSidList = new ArrayList<String>();
            String bodyFileTempDir = null;
            for (String bodyFileId : bodyFileList) {
                bodyFileTempDir =
                        tempDir + GSConstCommon.EDITOR_BODY_FILE
                        + File.separator + bodyFileId + File.separator;
                List <String> bodyBinSid = cmnBiz.insertBinInfo(
                        con__, bodyFileTempDir, appRootPath, cntCon, userSid, now);
                if (bodyBinSid != null && bodyBinSid.size() > 0) {
                    bodyBinSidList.add(bodyBinSid.get(0));
                }
            }

            //投稿本文添付情報テーブルへの登録
            bbsBodyBinDao.insertBbsBodyBinData(bwiSid, bodyFileList, bodyBinSidList);
        }

        //投稿情報の更新
        BbsWriteInfModel bbsWriteInfMdl = new BbsWriteInfModel();
        bbsWriteInfMdl.setBwiSid(bwiSid);
        bbsWriteInfMdl.setBwiType(valueType);
        if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //本文がプレーンテキストの場合
            bbsWriteInfMdl.setBwiValue(postValue);
            bbsWriteInfMdl.setBwiValuePlain("");

        } else {
            //本文がHTMLテキストの場合
            bbsWriteInfMdl.setBwiValue(postValue);
            //BRタグを\r\nに変換後、タグを除去し、プレーンテキストとして登録
            String postValuePlain = StringUtilHtml.replaceString(postValue, "<br/>", "<BR>");
            postValuePlain = StringUtilHtml.transBRtoCRLF(postValuePlain);
            postValuePlain = StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(postValuePlain);
            postValuePlain = StringEscapeUtils.unescapeHtml(postValuePlain);
            bbsWriteInfMdl.setBwiValuePlain(postValuePlain);
        }
        bbsWriteInfMdl.setBwiEuid(userSid);
        bbsWriteInfMdl.setBwiEdate(now);
        bbsWriteInfMdl.setBwiParentFlg(0);

        int contributor = 0;
        if (paramMdl.getBbs090contributor() > 0) {
            contributor = paramMdl.getBbs090contributor();
            GroupDao grpDao = new GroupDao(con__);
            if (!grpDao.isBelong(userSid, contributor)) {
                BbsBiz bbsBiz = new BbsBiz();
                BulletinDspModel bbsWriteMdl
                    = bbsBiz.getWriteData(con__, paramMdl.getBbs060postSid());
                if (bbsWriteMdl == null) {
                    bbsWriteMdl = new BulletinDspModel();
                }
                contributor = bbsWriteMdl.getGrpSid();
            }
        }

        bbsWriteInfMdl.setBwiEgid(contributor);
        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con__);
        bbsWriteInfDao.updateWriteInf(bbsWriteInfMdl);

        //添付ファイルをバイナリー情報に登録
        List < String > binSid = cmnBiz.insertBinInfo(con__, tempDir, appRootPath,
                                                    cntCon, userSid, now);

        //投稿添付情報の登録
        BbsBinModel bbsBinMdl = new BbsBinModel();
        bbsBinMdl.setBwiSid(bwiSid);
        bbsBinMdl.setBinSid(Long.valueOf(0));
        BbsBinDao bbsBinDao = new BbsBinDao(con__);
        bbsBinDao.deleteWriteBin(bwiSid);
        bbsBinDao.insertBbsBinData(bwiSid, binSid);

        //スレッド閲覧情報の更新
        BbsThreViewModel threViewMdl = new BbsThreViewModel();
        threViewMdl.setBtiSid(btiSid);
        threViewMdl.setBivViewKbn(GSConstBulletin.BBS_THRE_VIEW_NO);
        threViewMdl.setBivEuid(userSid);
        threViewMdl.setBivEdate(now);
        BbsThreViewDao threViewDao = new BbsThreViewDao(con__);
        threViewDao.updateAllUserViewKbn(threViewMdl);

        //スレッド情報の更新(投稿者)
        BbsThreInfDao bbsThreInfDao = new BbsThreInfDao(con__);
        BbsThreInfModel bbsThreInfMdl = new BbsThreInfModel();
        bbsThreInfMdl.setBtiSid(btiSid);
        bbsThreInfMdl.setBtiEuid(userSid);
        bbsThreInfMdl.setBtiEdate(now);
        bbsThreInfMdl.setBtiEgid(contributor);
        bbsThreInfDao.updateContributor(bbsThreInfMdl);

        //スレッド集計情報の更新
        BbsBiz bbsBiz = new BbsBiz();
        bbsBiz.updateBbsThreSum(con__, btiSid, userSid, now, now);

        //フォーラム集計情報の更新
        bbsBiz.updateBbsForSum(con__, bfiSid, userSid, now);

        log__.debug("End");
    }

    /**
     * <br>[機  能] ショートメールで通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void sendSmail(
        Bbs090knParamModel paramMdl,
        MlCountMtController cntCon,
        int userSid,
        String appRootPath,
        String tempDir,
        PluginConfig pluginConfig,
        RequestModel reqMdl) throws Exception {

        BbsBiz biz = new BbsBiz();
        BulletinSmlModel bsmlModel = new BulletinSmlModel();
        //フォーラムSID
        bsmlModel.setBfiSid(paramMdl.getBbs010forumSid());
        //投稿者
        bsmlModel.setUserSid(userSid);
        //投稿者グループSID
        bsmlModel.setGrpSid(paramMdl.getBbs090contributor());
        //スレッドタイトル
        BulletinDspModel bbsMdl = biz.getThreadData(con__, paramMdl.getThreadSid());
        bsmlModel.setBtiTitle(bbsMdl.getBtiTitle());
        //投稿日時
        bsmlModel.setBwiAdate(new UDate());
        //添付ファイル名
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> files = cmnBiz.getTempFileLabelList(tempDir);
        bsmlModel.setFileLabelList(files);
        //投稿内容
        String bwiValue = null;
        if (paramMdl.getBbs090valueType() == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //プレーンテキストの場合
            bwiValue = paramMdl.getBbs090value();
        } else  {
            //HTMLテキストの場合
            bwiValue = StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                    StringUtilHtml.transToText(paramMdl.getBbs090valueHtml()));
        }
        bsmlModel.setBwiValue(bwiValue);
        //スレッドURL
        bsmlModel.setThreadUrl(
                biz.createThreadUrl(reqMdl, paramMdl.getBbs010forumSid(), paramMdl.getThreadSid()));

        //送信
        biz.sendSmail(con__, cntCon, bsmlModel, appRootPath, pluginConfig, reqMdl);
    }
}