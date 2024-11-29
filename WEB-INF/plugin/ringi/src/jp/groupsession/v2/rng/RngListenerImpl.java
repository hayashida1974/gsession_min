package jp.groupsession.v2.rng;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngDairiUserModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.SmlSender;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議の申請、承認などが行われた場合に呼ばれるリスナーを実装
 * <br>[解  説] 稟議に関する処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngListenerImpl implements IRingiListener {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngListenerImpl.class);

    /** 稟議ステータス 申請/承認 */
    private static final int STATUS_APPLY_APPROVAL__ = 10;
    /** 稟議ステータス 承認完了(確認通知用) */
    private static final int STATUS_APPRCOMP__ = 11;
    /** 稟議ステータス 差し戻し */
    private static final int STATUS_REFLECTION__ = 12;
    /** 稟議ステータス 後閲*/
    private static final int STATUS_KOETU__ = 13;

    /**
     * <br>[機  能] ショートメール通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param noticeKbn 通知区分
     * @throws Exception 実行例外
     */
    public void sendSmlMain(RingiListenerModel model,
            RequestModel reqMdl, int noticeKbn)
            throws Exception {
        int[] sort = new int[2];
        sendSmlMain(model, reqMdl, noticeKbn, sort);
    }

    /**
     * <br>[機  能] ショートメール通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param noticeKbn 通知区分
     * @param sort [0]：通知開始ソート番号 [1]：通知終了ソート番号
     * @throws Exception 実行例外
     */
    public void sendSmlMain(RingiListenerModel model,
            RequestModel reqMdl, int noticeKbn, int[] sort)
            throws Exception {

        List<Integer> userList = new ArrayList<Integer>();
        //結果通知設定
        userList = __noticeResult(model, reqMdl, noticeKbn);
        //受信通知設定
        __doReceptionNotice(model, reqMdl, noticeKbn, userList, sort);
    }

    /**
     * <br>[機  能]受信通知設定による通知
     * <br>[解  説]
     * <br>[備  考]申請通知、確認通知、差し戻し通知、後閲通知
     * @param model 稟議リスナーパラメsータ
     * @param reqMdl リクエスト情報
     * @param noticeKbn 通知区分
     * @param userList 送信済みユーザリスト
     * @param sort [0]：通知開始ソート番号 [1]：通知終了ソート番号
     * @throws Exception Exception
     */
    private void __doReceptionNotice(RingiListenerModel model, RequestModel reqMdl,
            int noticeKbn, List<Integer> userList, int[] sort) throws Exception {

        RngBiz rngBiz = new RngBiz(model.getCon());
        RngAconfModel aconfMdl = rngBiz.getRngAconf(model.getCon());

        //申請通知の送信
        if (noticeKbn == RngConst.STATUS_SOURCE_APPLY_SML
                || noticeKbn == RngConst.STATUS_SOURCE_KOETU_SML) {
            __doApplySml(model, reqMdl, noticeKbn, userList, aconfMdl);
        }
        //確認通知の送信
        if (noticeKbn == RngConst.STATUS_SOURCE_APPROVAL_COMP_SML) {
            __doApprovalCompSml(model, reqMdl, userList, aconfMdl);
        }
        //差し戻し通知の送信
        if (noticeKbn == RngConst.STATUS_SOURCE_REMAND_SML) {
            __doRemandSml(model, reqMdl, userList, aconfMdl, sort);
        }
        //後閲通知の送信
        if (noticeKbn == RngConst.STATUS_SOURCE_APPLY_SML
                || noticeKbn == RngConst.STATUS_SOURCE_KOETU_SML
                || noticeKbn == RngConst.STATUS_SOURCE_KOETU_SKIP_SML) {
            __doKoetuSml(model, reqMdl, userList, aconfMdl, sort);
        }
    }

    /**
     * <br>[機  能] 申請通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param noticeKbn 通知区分
     * @param userList 送信済みユーザリスト
     * @param aconfMdl 管理者モデルパラメータ
     * @throws Exception Exception
     */
    private void __doApplySml(RingiListenerModel model, RequestModel reqMdl,
            int noticeKbn, List<Integer> userList, RngAconfModel aconfMdl) throws Exception {

        //受信通知用送信先ユーザの生成
        List<Integer> userSendList = new ArrayList<Integer>();
        RingiDao rngDao = new RingiDao(model.getCon());
        userSendList = rngDao.getAppryConfSmlUser(model.getRngSid(), userList, noticeKbn);
        __noticeDairi(model, reqMdl, aconfMdl, userSendList, STATUS_APPLY_APPROVAL__);
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN
                && aconfMdl.getRarSmlJusinKbn() == RngConst.RAR_SML_NTF_KBN_NO) {
            //管理者が設定するかつ受信通知設定を通知しない
            return;
        }
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
            //各ユーザが設定するの場合、各ユーザ毎の受信通知設定を確認する
            RngBiz rngBiz = new RngBiz(model.getCon());
            userSendList = rngBiz.getRngUconfZyusin(model.getCon(), userSendList);
        }
        if (userSendList.size() != 0) {
            __sendSmail(model, userSendList, STATUS_APPLY_APPROVAL__, reqMdl);
        }
    }

    /**
     * <br>[機  能] 確認通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param userList 送信済みユーザリスト
     * @param aconfMdl 管理者モデルパラメータ
     * @throws Exception Exception
     */
    private void __doApprovalCompSml(RingiListenerModel model, RequestModel reqMdl,
            List<Integer> userList, RngAconfModel aconfMdl) throws Exception {

        //受信通知用送信先ユーザの生成
        List<Integer> userSendList = new ArrayList<Integer>();
        RingiDao rngDao = new RingiDao(model.getCon());
        userSendList = rngDao.getAppryConfSmlUser(model.getRngSid(),
                userList, RngConst.STATUS_SOURCE_APPROVAL_COMP_SML);
        __noticeDairi(model, reqMdl, aconfMdl, userSendList, STATUS_APPRCOMP__);
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN
                && aconfMdl.getRarSmlJusinKbn() == RngConst.RAR_SML_NTF_KBN_NO) {
            //管理者が設定するかつ受信通知設定を通知しない
            return;
        }
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
            //各ユーザが設定するの場合、各ユーザ毎の受信通知設定を確認する
            RngBiz rngBiz = new RngBiz(model.getCon());
            userSendList = rngBiz.getRngUconfZyusin(model.getCon(), userSendList);
        }
        if (userSendList.size() != 0) {
            __sendSmail(model, userSendList, STATUS_APPRCOMP__, reqMdl);
        }
    }

    /**
     * <br>[機  能] 差し戻し通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param userList 送信済みユーザリスト
     * @param aconfMdl 管理者モデルパラメータ
     * @param sort [0]：通知開始ソート番号 [1]：通知終了ソート番号
     * @throws Exception Exception
     */
    private void __doRemandSml(RingiListenerModel model, RequestModel reqMdl,
            List<Integer> userList, RngAconfModel aconfMdl, int[] sort) throws Exception {

        //受信通知用送信先ユーザの生成
        List<Integer> userSendList = new ArrayList<Integer>();
        RingiDao rngDao = new RingiDao(model.getCon());
        userSendList = rngDao.getRemandSmlUser(model.getRngSid(), userList, sort);
        __noticeDairi(model, reqMdl, aconfMdl, userSendList, STATUS_REFLECTION__);
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN
                && aconfMdl.getRarSmlJusinKbn() == RngConst.RAR_SML_NTF_KBN_NO) {
            //管理者が設定するかつ受信通知設定を通知しない
            return;
        }
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
            //各ユーザが設定するの場合、各ユーザ毎の受信通知設定を確認する
            RngBiz rngBiz = new RngBiz(model.getCon());
            userSendList = rngBiz.getRngUconfZyusin(model.getCon(), userSendList);
        }
        if (userSendList.size() != 0) {
            __sendSmail(model, userSendList, STATUS_REFLECTION__, reqMdl);
        }
    }

    /**
     * <br>[機  能] 後閲通知
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param userList 送信済みユーザリスト
     * @param aconfMdl 管理者モデルパラメータ
     * @param sort [0]：通知開始ソート番号 [1]：通知終了ソート番号
     * @throws Exception Exception
     */
    private void __doKoetuSml(RingiListenerModel model, RequestModel reqMdl,
            List<Integer> userList, RngAconfModel aconfMdl, int[] sort) throws Exception {

        //受信通知用送信先ユーザの生成
        List<Integer> userSendList = new ArrayList<Integer>();
        RingiDao rngDao = new RingiDao(model.getCon());
        userSendList = rngDao.getKoetuSmlUser(model.getRngSid(), userList, sort);
        __noticeDairi(model, reqMdl, aconfMdl, userSendList, STATUS_KOETU__);
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN
                && aconfMdl.getRarSmlJusinKbn() == RngConst.RAR_SML_NTF_KBN_NO) {
            //管理者が設定するかつ受信通知設定を通知しない
            return;
        }
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
            //各ユーザが設定するの場合、各ユーザ毎の受信通知設定を確認する
            RngBiz rngBiz = new RngBiz(model.getCon());
            userSendList = rngBiz.getRngUconfZyusin(model.getCon(), userSendList);
        }
        if (userSendList.size() != 0) {
            __sendSmail(model, userSendList, STATUS_KOETU__, reqMdl);
        }
    }

    /**
     * 結果通知を送信
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエストモデル
     * @param noticeKbn 通知区分
     * @return 通知済みユーザ
     * @throws Exception 実行例外
     * */
    public List<Integer> __noticeResult(
            RingiListenerModel model, RequestModel reqMdl, int noticeKbn)
    throws Exception {

        RngBiz rngBiz = new RngBiz(model.getCon());
        RngAconfModel aconfMdl = rngBiz.getRngAconf(model.getCon());
        boolean sendApplUser = false;
        RngSingiDao rssDao = new RngSingiDao(model.getCon());
        // 通知対象ユーザを取得
        List<Integer> userList = new ArrayList<Integer>();
        boolean apply = false;
        boolean koetsu = false;
        if (noticeKbn == RngConst.STATUS_SOURCE_REJECT_SML) {
        // 却下
            apply = true;
            koetsu = true;
        } else if (noticeKbn == RngConst.STATUS_SOURCE_APPROVAL_COMP_SML) {
        // 承認完了
            apply = true;
            koetsu = false;
        } else if (noticeKbn == RngConst.STATUS_SOURCE_TORISAGE_SML) {
        // 取り下げ
            apply = false;
            koetsu = false;
        } else {
        // それ以外は結果通知を行わない
            return userList;
        }
        userList = rssDao.selectUsrsCanConfirmRingi(model.getRngSid(), apply, koetsu);

        // 代理人へ通知
        __noticeDairi(model, reqMdl, aconfMdl, userList, noticeKbn);

        //管理者設定ショートメール通知区分「各ユーザが設定」の場合
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
            List<Integer>singiUserList = rngBiz.getRngUconfSinsei(model.getCon(), userList);
            userList = singiUserList;
            // 通知を行うユーザが1件以上
            if (!singiUserList.isEmpty()) {
                __sendSmail(model, singiUserList, noticeKbn, reqMdl);
                sendApplUser = true;
            }
        //管理者設定ショートメール通知区分「管理者が設定」の場合
        } else if (aconfMdl.getRarSmlNtfKbn() == RngConst.RAR_SML_NTF_KBN_YES) {
            // 結果通知が「通知する」の場合は通知を行う
                __sendSmail(model, userList, noticeKbn, reqMdl);
                sendApplUser = true;
        }
        if (!sendApplUser) {
            log__.debug("結果通知の送信を行わない。");
            userList = new ArrayList<Integer>();
            return userList;
        }

        // 通知済みユーザを出力
        return userList;

    }

    /**
     * <br>[機  能] 代理人にショートメールを通知する
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param reqMdl リクエスト情報
     * @param userList 通知済み代理元ユーザリスト
     * @param aconfMdl 管理者設定モデル
     * @param noticeKbn 通知区分
     * @throws Exception 実行時例外
     */
    public void __noticeDairi(
            RingiListenerModel model,
            RequestModel reqMdl,
            RngAconfModel aconfMdl,
            List<Integer> userList,
            int noticeKbn) throws Exception {

        RngBiz rngBiz = new RngBiz(model.getCon());
        boolean sendApplUser = false;

        //管理者設定ショートメール通知区分「各ユーザが設定」の場合
        if (aconfMdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
            List<Integer> singiUserList = rngBiz.getRngUconfDairi(model.getCon(), userList);
            // 通知を行うユーザが1件以上
            if (!singiUserList.isEmpty()) {
                __sendDairiSmail(model, userList, singiUserList, noticeKbn, reqMdl);
                sendApplUser = true;
            }
        //管理者設定ショートメール通知区分「管理者が設定」の場合
        } else if (aconfMdl.getRarSmlDairiKbn() == RngConst.RAR_SML_NTF_KBN_YES) {
            // 代理人通知が「通知する」の場合は通知を行う
            __sendDairiSmail(model, userList, null, noticeKbn, reqMdl);
                sendApplUser = true;
        }
        if (!sendApplUser) {
            log__.debug("代理人にメールの送信を行わない。");
        }
    }

    /**
     * <br>[機  能] ショートメールで更新通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param sendUsers 送信先ユーザ一覧
     * @param status 稟議ステータス 承認 or 却下
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    private void __sendSmail(RingiListenerModel model, List<Integer> sendUsers,
                            int status, RequestModel reqMdl)
    throws Exception {

        if (!model.isSmailPluginFlg()) {
            //ショートメールプラグインが無効の場合は送信を行わない。
            return;
        }

        //稟議SID
        int rngSid = model.getRngSid();

        //稟議情報を取得
        RngRndataDao rngDao = new RngRndataDao(model.getCon());
        RngRndataModel rngData = rngDao.select(rngSid);

        //ショートメールタイトル、状態、日時
        String smTitle = "";
        String rngStatus = "";
        String smDate = __getDateString(rngData.getRngEdate());;
        RngKeiroStepDao rksDao = new RngKeiroStepDao(model.getCon());
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (status == RngConst.STATUS_SOURCE_APPLY_SML
                || status == STATUS_APPLY_APPROVAL__) {
            smTitle = gsMsg.getMessage("rng.100");
            rngStatus = gsMsg.getMessage("rng.46");
            UDate date = rksDao.selectRsvDate(model.getRngSid());
            smDate = __getDateString(date);
        } else if (status == RngConst.STATUS_SOURCE_APPROVAL_COMP_SML) {
            smTitle = gsMsg.getMessage("rng.96");
            rngStatus = gsMsg.getMessage("rng.29");
        } else if (status == STATUS_APPRCOMP__) {
            smTitle = gsMsg.getMessage("rng.127");
            rngStatus = gsMsg.getMessage("rng.128");
        } else if (status == RngConst.STATUS_SOURCE_REJECT_SML) {
            smTitle = gsMsg.getMessage("rng.95");
            rngStatus = gsMsg.getMessage("rng.22");
        } else if (status == RngConst.STATUS_SOURCE_REMAND_SML
                || status == STATUS_REFLECTION__) {
            smTitle = gsMsg.getMessage("rng.98");
            rngStatus = gsMsg.getMessage("rng.rng030.08");
        } else if (status == RngConst.STATUS_SOURCE_TORISAGE_SML) {
            smTitle = gsMsg.getMessage("rng.125");
            rngStatus = gsMsg.getMessage("rng.rng030.15");
        } else if (status == STATUS_KOETU__) {
            smTitle = gsMsg.getMessage("rng.120");
            rngStatus = gsMsg.getMessage("rng.109");
        }
        //タイトル
        String rngTitle = rngData.getRngTitle();
        //申請者名
        CmnUsrmInfDao udao = new CmnUsrmInfDao(model.getCon());
        CmnUsrmInfModel umodel = udao.select(rngData.getRngApplicate());
        String tname = umodel.getUsiSei() + " " + umodel.getUsiMei();
        //申請日時
        UDate ud = rngData.getRngAppldate();
        String tdate = UDateUtil.getSlashYYMD(ud) + " "
        + UDateUtil.getSeparateHMS(ud);
        //添付ファイル
        RingiDao ringiDao = new RingiDao(model.getCon());
        List <CmnBinfModel> fileList = ringiDao.getRingiTmpFileList(rngSid);
        StringBuilder fileLine = new StringBuilder();
        for (CmnBinfModel file : fileList) {
            String fileName = file.getBinFileName();
            if (fileLine.length() != 0) {
                fileLine.append(" , ");
            }
            fileLine.append(fileName);
        }

        //本文
        String tmpPath = __getSmlTemplateFilePath(model.getAppRootPath(), status);
        String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
        Map<String, String> map = new HashMap<String, String>();
        map.put("SMTITLE", smTitle);
        map.put("STATUS", rngStatus);
        map.put("SMDATE", smDate);
        map.put("TITLE", rngTitle);
        map.put("UNAME", tname);
        map.put("DATE", tdate);
        map.put("FILES", fileLine.toString());
        map.put("BODY", " ");
        map.put("URL", model.getRngUrl());
        String bodyml = StringUtil.merge(tmpBody, map);

        String errorMsg = gsMsg.getMessage("cmn.mail.omit");

        if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
            bodyml = errorMsg + "\r\n\r\n" + bodyml;
            bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
        }

        //ショートメール送信用モデルを作成する。
        SmlSenderModel smlModel = new SmlSenderModel();
        //送信者(システムメールを指定)
        smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
        //TO
        smlModel.setSendToUsrSidArray(sendUsers);
        String msg = gsMsg.getMessage("rng.71");
        String msg2 = gsMsg.getMessage("rng.88");

        //タイトル
        String title = msg + " " + rngStatus + msg2;
        if (!StringUtil.isNullZeroString(rngData.getRngTitle())) {
            title += " " + rngData.getRngTitle();
            title = StringUtil.trimRengeString(title,
                                                GSConstCommon.MAX_LENGTH_SMLTITLE);
        }
        smlModel.setSendTitle(title);

        //本文
        smlModel.setSendBody(bodyml);
        //メール形式
        smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
        //マーク
        smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

        //メール送信処理開始
        PluginConfig pluginConfig = model.getPluginConfig();
        SmlSender sender =
            new SmlSender(
                    model.getCon(),
                    model.getCntCon(),
                    smlModel, pluginConfig,
                    model.getAppRootPath(),
                    reqMdl);
        sender.execute();
    }

    /**
     * <br>[機  能] 代理人に対してショートメール通知を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議リスナーパラメータ
     * @param usrList 通知済みユーザリスト
     * @param dairiUsrList 通知対象代理人リスト
     * @param status 稟議ステータス 承認 or 却下
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    private void __sendDairiSmail(RingiListenerModel model, List<Integer> usrList,
                            List<Integer> dairiUsrList,
                            int status, RequestModel reqMdl)
    throws Exception {
        if (!model.isSmailPluginFlg()) {
            //ショートメールプラグインが無効の場合は送信を行わない。
            return;
        }

        //稟議SID
        int rngSid = model.getRngSid();

        //稟議情報を取得
        RngRndataDao rngDao = new RngRndataDao(model.getCon());
        RngRndataModel rngData = rngDao.select(rngSid);

        //ショートメールタイトル、状態、日時
        String smTitle = "";
        String rngStatus = "";
        String smDate = __getDateString(rngData.getRngEdate());;
        RngKeiroStepDao rksDao = new RngKeiroStepDao(model.getCon());
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (status == RngConst.STATUS_SOURCE_APPLY_SML
                || status == STATUS_APPLY_APPROVAL__) {
            smTitle = gsMsg.getMessage("rng.100");
            rngStatus = gsMsg.getMessage("rng.46");
            UDate date = rksDao.selectRsvDate(model.getRngSid());
            smDate = __getDateString(date);
        } else if (status == RngConst.STATUS_SOURCE_APPROVAL_COMP_SML) {
            smTitle = gsMsg.getMessage("rng.96");
            rngStatus = gsMsg.getMessage("rng.29");
        } else if (status == STATUS_APPRCOMP__) {
            smTitle = gsMsg.getMessage("rng.127");
            rngStatus = gsMsg.getMessage("rng.128");
        } else if (status == RngConst.STATUS_SOURCE_REJECT_SML) {
            smTitle = gsMsg.getMessage("rng.95");
            rngStatus = gsMsg.getMessage("rng.22");
        } else if (status == RngConst.STATUS_SOURCE_REMAND_SML
                || status == STATUS_REFLECTION__) {
            smTitle = gsMsg.getMessage("rng.98");
            rngStatus = gsMsg.getMessage("rng.rng030.08");
        } else if (status == RngConst.STATUS_SOURCE_TORISAGE_SML) {
            smTitle = gsMsg.getMessage("rng.125");
            rngStatus = gsMsg.getMessage("rng.rng030.15");
        } else if (status == STATUS_KOETU__) {
            smTitle = gsMsg.getMessage("rng.120");
            rngStatus = gsMsg.getMessage("rng.109");
        }
        //タイトル
        String rngTitle = rngData.getRngTitle();
        //申請者名
        CmnUsrmInfDao udao = new CmnUsrmInfDao(model.getCon());
        CmnUsrmInfModel umodel = udao.select(rngData.getRngApplicate());
        String tname = umodel.getUsiSei() + " " + umodel.getUsiMei();
        //申請日時
        UDate ud = rngData.getRngAppldate();
        String tdate = UDateUtil.getSlashYYMD(ud) + " "
        + UDateUtil.getSeparateHMS(ud);
        //添付ファイル
        RingiDao ringiDao = new RingiDao(model.getCon());
        List <CmnBinfModel> fileList = ringiDao.getRingiTmpFileList(rngSid);
        StringBuilder fileLine = new StringBuilder();
        for (CmnBinfModel file : fileList) {
            String fileName = file.getBinFileName();
            if (fileLine.length() != 0) {
                fileLine.append(" , ");
            }
            fileLine.append(fileName);
        }

        //内容
        String body = rngData.getRngContent();

        // 代理人のユーザへショートメールを送信
        RngDairiUserDao rduDao = new RngDairiUserDao(model.getCon());
        List<RngDairiUserModel> rduList = rduDao.getDairiModel(usrList);
        ArrayList<Integer> subSource = new ArrayList<Integer>();

        // 代理元の情報を取得
        for (RngDairiUserModel rduMdl : rduList) {
            subSource.add(rduMdl.getUsrSid());
        }

        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(model.getCon());
        ArrayList<CmnUsrmInfModel> usrInfList = usrDao.getUserList(subSource);
        HashMap<Integer, String> usrNameMap = new HashMap<Integer, String>();
        for (CmnUsrmInfModel usr : usrInfList) {
            String name = usr.getUsiSei() + usr.getUsiMei();
            usrNameMap.put(usr.getUsrSid(), name);
        }

        // 代理人とその代理元を結びつける
        HashMap<Integer, List<LabelValueBean>> dairiInfo =
                new HashMap<Integer, List<LabelValueBean>>();
        for (RngDairiUserModel rduMdl : rduList) {
            String dairiSid = Integer.toString(rduMdl.getUsrSid());
            String dairiName = usrNameMap.get(rduMdl.getUsrSid());
            LabelValueBean dairiUsr = new LabelValueBean(dairiSid, dairiName);
            if (dairiInfo.containsKey(rduMdl.getUsrSidDairi())) {
                dairiInfo.get(rduMdl.getUsrSidDairi()).add(dairiUsr);
            } else {
                List<LabelValueBean> list = new ArrayList<LabelValueBean>();
                list.add(dairiUsr);
                dairiInfo.put(rduMdl.getUsrSidDairi(), list);
            }
        }

        // 通知の送信
        for (HashMap.Entry<Integer, List<LabelValueBean>> entry : dairiInfo.entrySet()) {
            for (LabelValueBean sub : entry.getValue()) {
                String usrName = sub.getValue();
                String titleAppendDairi = null;
                titleAppendDairi = smTitle + "\n" + gsMsg.getMessage(
                        "rng.rng030.25", new String[] {usrName});

                // URLにアカウントSIDを追記
                String url = model.getRngUrl();
                url += URLEncoder.encode(
                        "&rng010ViewAccount=" + sub.getLabel(), Encoding.UTF_8);

                //本文
                String tmpPath = __getSmlTemplateFilePath(model.getAppRootPath(), status);
                String tmpBody = IOTools.readText(tmpPath, Encoding.UTF_8);
                Map<String, String> map = new HashMap<String, String>();
                map.put("SMTITLE", titleAppendDairi);
                map.put("STATUS", rngStatus);
                map.put("SMDATE", smDate);
                map.put("TITLE", rngTitle);
                map.put("UNAME", tname);
                map.put("DATE", tdate);
                map.put("FILES", fileLine.toString());
                map.put("BODY", body);
                map.put("URL", url);
                String bodyml = StringUtil.merge(tmpBody, map);

                String errorMsg = gsMsg.getMessage("cmn.mail.omit");

                if (bodyml.length() > GSConstCommon.MAX_LENGTH_SMLBODY) {
                    bodyml = errorMsg + "\r\n\r\n" + bodyml;
                    bodyml = bodyml.substring(0, GSConstCommon.MAX_LENGTH_SMLBODY);
                }

                //ショートメール送信用モデルを作成する。
                SmlSenderModel smlModel = new SmlSenderModel();
                //送信者(システムメールを指定)
                smlModel.setSendUsid(GSConst.SYSTEM_USER_MAIL);
                //TO
                List<Integer> toList = new ArrayList<Integer>();
                toList.add(entry.getKey());
                //「代理人通知を行わない」代理人を送信先から除外する
                if (dairiUsrList != null) {
                    List<Integer> sendUserList = new ArrayList<Integer>();
                    for (int toSid : toList) {
                        if (dairiUsrList.contains(toSid)) {
                            sendUserList.add(toSid);
                        }
                    }
                    toList = sendUserList;
                }
                if (toList.isEmpty()) {
                    continue;
                }

                smlModel.setSendToUsrSidArray(toList);
                String msg = gsMsg.getMessage("rng.71");
                String msg2 = gsMsg.getMessage("rng.88");

                //タイトル
                StringBuilder title = new StringBuilder(msg + " " + rngStatus + msg2);
                if (!StringUtil.isNullZeroString(rngData.getRngTitle())) {
                    title.append(" " + rngData.getRngTitle());
                    title.append("(");
                    title.append(usrName);
                    title.append(" " + gsMsg.getMessage("rng.126"));
                    title.append(")");
                    title.setLength(GSConstCommon.MAX_LENGTH_SMLTITLE);
                    title.trimToSize();
                }
                smlModel.setSendTitle(title.toString());

                //本文
                smlModel.setSendBody(bodyml);
                //メール形式
                smlModel.setSendType(GSConstSmail.SAC_SEND_MAILTYPE_NORMAL);
                //マーク
                smlModel.setSendMark(GSConstSmail.MARK_KBN_NONE);

                //メール送信処理開始
                PluginConfig pluginConfig = model.getPluginConfig();
                SmlSender sender =
                    new SmlSender(
                            model.getCon(),
                            model.getCntCon(),
                            smlModel, pluginConfig,
                            model.getAppRootPath(),
                            reqMdl);
                sender.execute();
            }
        }
    }

    /**
     * <br>[機  能] 承認/否認通知メールのテンプレートパスを返す。
     * <br>[解  説] 確認通知の場合、使用テンプレートを変更
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @param status 稟議の状態
     * @return テンプレートファイルのパス文字列
     */
    private String __getSmlTemplateFilePath(String appRootPath, int status) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = null;
        if (status == STATUS_APPRCOMP__) {
            ret = IOTools.replaceSlashFileSep(appRootPath
                    + "/WEB-INF/plugin/ringi/smail/kakunin_tsuuchi.txt");
        } else {
            ret = IOTools.replaceSlashFileSep(appRootPath
                    + "/WEB-INF/plugin/ringi/smail/koushin_tsuuchi.txt");
        }
        return ret;
    }

    /**
     * <br>[機  能] 日時文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @return 日時文字列
     */
    private String __getDateString(UDate date) {
        StringBuilder dateStr = new StringBuilder("");
        dateStr.append(UDateUtil.getSlashYYMD(date));
        dateStr.append(" ");
        dateStr.append(UDateUtil.getSeparateHMS(date));
        return dateStr.toString();
    }

}