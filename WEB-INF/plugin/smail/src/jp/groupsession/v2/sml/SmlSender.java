package jp.groupsession.v2.sml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlPushSender;
import jp.groupsession.v2.sml.biz.SmlReceiveFilter;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailSendModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlBinModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlSenderModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;

/**
 * <br>[機  能] ショートメール送信処理クラス
 * <br>[解  説]
 * <br>[備  考] システム通知メールを送る際に使用します
 *
 * @author JTS
 */
public class SmlSender {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlSender.class);
    /** コネクション */
    private Connection con__ = null;
    /** 採番用コネクション*/
    private MlCountMtController cntCon__ = null;
    /** メール送信情報Model */
    private SmlSenderModel senderMdl__ = null;
    /** PluginConfig */
    private PluginConfig pluginConfig__ = null;
    /** アプリケーションルートパス */
    private String appRootPath__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon 採番用コネクション
     * @param senderMdl メール送信情報Model
     * @param pluginConfig PluginConfig
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     */
    public SmlSender(
        Connection con,
        MlCountMtController cntCon,
        SmlSenderModel senderMdl,
        PluginConfig pluginConfig,
        String appRootPath,
        RequestModel reqMdl) {

        con__ = con;
        cntCon__ = cntCon;
        senderMdl__ = senderMdl;
        pluginConfig__ = pluginConfig;
        appRootPath__ = appRootPath;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] メインメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @return sendCnt メール送信件数
     * @throws Exception 実行時例外
     */
    public int execute() throws Exception {

        log__.debug("メール送信処理開始");

        List<SmailSendModel> sendMdlList = null;

        //宛先からショートメールプラグイン使用不可のユーザを除外する
        List<Integer> toUserSid = senderMdl__.getSendToUsrSidArray();
        if (toUserSid != null && !toUserSid.isEmpty()) {
            CommonBiz cmnBiz = new CommonBiz();
            senderMdl__.setSendToUsrSidArray(cmnBiz.getCanUseSmailUser(con__, toUserSid));
            cmnBiz = null;
        }

        int sendCnt = 0;
        con__.setAutoCommit(false);
        boolean commitFlg = false;

        try {

            sendMdlList = __sendExecute();
            commitFlg = true;

            if (sendMdlList != null
                    && sendMdlList.isEmpty()) {
                sendCnt = sendMdlList.size();
            }

        } catch (ClassNotFoundException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("リスナー起動に失敗しました。", e);
            throw e;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }

        log__.debug("メール送信処理終了：送信件数 = " + sendCnt + "件");

        if (sendMdlList != null && !sendMdlList.isEmpty()) {
            for (SmailSendModel sendMdl : sendMdlList) {
                //フィルタ処理
                new SmlReceiveFilter(con__, sendMdl)
                    .doFilterJmail();
            }
        }
        SmlAccountDao sacDao = new SmlAccountDao(con__);
        SmlAccountModel sacMdl = sacDao.select(senderMdl__.getSendUsid());

        if (sendMdlList != null && !sendMdlList.isEmpty()) {
            for (SmailSendModel sendMdl : sendMdlList) {
                if (sendMdl == null
                            || sendMdl.getAccountSidList() == null
                            || sendMdl.getAccountSidList().isEmpty()
                            || sendMdl.getSmjSid() <= 0) {
                    continue;
                }
                //GSショートメールアプリ使用者にPush通知
                SmlPushSender pushSender = new SmlPushSender(reqMdl__, con__,
                        sendMdl.getAccountSidList(),
                        senderMdl__.getSendTitle(),
                        sendMdl.getSmjSid(),
                        sacMdl
                        );
                pushSender.sendPush();
            }
        }

        return sendCnt;
    }

    /**
     * <br>[機  能] メール送信処理
     * <br>[解  説]
     * <br>[備  考]
     * @return sendCnt メール送信件数
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private List<SmailSendModel> __sendExecute()
        throws
        ClassNotFoundException,
        IllegalAccessException,
        InstantiationException,
        IOToolsException,
        IOException,
        SQLException,
        TempFileException {

        List<SmailSendModel> sendMdlList = null;

        //送信情報の入力チェック
        if (!__canMailSend()) {
            //送信情報にエラーがあり送信不可
            return sendMdlList;
        }

        //メール送信処理開始
        sendMdlList = __sendMail();

        return sendMdlList;
    }

    /**
     * <br>[機  能] 送信テーブル、受信テーブルにメールデータを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @return sendCnt メール送信件数
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private List<SmailSendModel> __sendMail()
        throws
        ClassNotFoundException,
        IllegalAccessException,
        InstantiationException,
        IOToolsException,
        IOException,
        SQLException,
        TempFileException {

        List<SmailSendModel> sendMdlList = new ArrayList<SmailSendModel>();
        SmailSendModel sendMdl = null;

        UDate now = new UDate();

        //差出人USID
        int sendUsid = senderMdl__.getSendUsid();
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl__);
        SmlAdminModel adminConf = smlCmnBiz.getSmailAdminConf(sendUsid, con__);
        SmlBinModel sbMdl = null;
        SmlBinDao sbDao = new SmlBinDao(con__);
        CommonBiz cmnBiz = new CommonBiz();

        //送信先ユーザのSIDを取得し、デッドロックが発生しないよう降順にする
        SmlCommonBiz smlBiz = new SmlCommonBiz();
        List<Integer> userSidArray = senderMdl__.getSendToUsrSidArray();
        userSidArray = smlBiz.setOrderBySidDesc(userSidArray);

        List<Integer> sendMailSidList = new ArrayList<Integer>();
        List<Integer> targetSacSidList = new ArrayList<Integer>();
        long sendDataSize = 0;

        for (int userSid : userSidArray) {

            //ユーザのデフォルトアカウントを取得
            SmlAccountDao sacDao = new SmlAccountDao(con__);
            SmlAccountModel sacMdl = null;
//            SmlAccountForwardModel safMdl = null;
            sacMdl = sacDao.selectFromUsrSid(userSid);
            
            if (sacMdl != null) {
                //SmlUserModel userConf = smlCmnBiz.getSmailUserConf(userSid, con__);
                //SID採番
                int mailSid =
                    (int) cntCon__.getSaibanNumber(
                            GSConstSmail.SAIBAN_SML_SID,
                            GSConstSmail.SAIBAN_SUB_MAIL_SID,
                            sendUsid);

                CommonBiz biz = new CommonBiz();

                //メールサイズ取得
                Long titile_byte = Long.valueOf(0);
                Long body_byte = Long.valueOf(0);
                Long file_byte = Long.valueOf(0);

                try {
                    if (senderMdl__.getSendTitle().getBytes("UTF-8").length != 0) {
                        titile_byte = Long.valueOf(
                                senderMdl__.getSendTitle().getBytes("UTF-8").length);
                    }
                } catch (UnsupportedEncodingException e) {
                    log__.error("文字のバイト数取得に失敗");
                    titile_byte = Long.valueOf(
                            senderMdl__.getSendTitle().getBytes().length);
                }

                try {
                    if (senderMdl__.getSendBody().getBytes("UTF-8").length != 0) {
                        body_byte = Long.valueOf(
                                senderMdl__.getSendBody().getBytes("UTF-8").length);
                    }
                } catch (UnsupportedEncodingException e) {
                    log__.error("文字のバイト数取得に失敗");
                    body_byte = Long.valueOf(
                            senderMdl__.getSendBody().getBytes().length);
                }

                if (senderMdl__.isTempFile()) {
                    if (!StringUtil.isNullZeroStringSpace(senderMdl__.getSaveFulPath())) {
                        file_byte = biz.getTempFileSize(senderMdl__.getSaveFulPath());
                    }
                }

                //送信テーブルにデータ作成
                SmlSmeisModel sparam = new SmlSmeisModel();
                sparam.setSacSid(sendUsid);
                sparam.setSmsSid(mailSid);
                sparam.setSmsSdate(now);
                sparam.setSmsTitle(senderMdl__.getSendTitle());
                sparam.setSmsMark(senderMdl__.getSendMark());
                sparam.setSmsBody(senderMdl__.getSendBody());
                sparam.setSmsBodyPlain("");
                sparam.setSmsType(senderMdl__.getSendType());
                sparam.setSmsJkbn(GSConst.JTKBN_TOROKU);
                sparam.setSmsSize(titile_byte + body_byte + file_byte);
                sparam.setSmsAuid(sendUsid);
                sparam.setSmsAdate(now);
                sparam.setSmsEuid(sendUsid);
                sparam.setSmsEdate(now);
                SmlSmeisDao sdao = new SmlSmeisDao(con__);
                sdao.insert(sparam);
                sendMailSidList.add(mailSid);

                sendDataSize = titile_byte + body_byte + file_byte;

                //受信テーブルにデータ作成
                SmlJmeisModel jparam = new SmlJmeisModel();
                jparam.setSacSid(sacMdl.getSacSid());
                jparam.setSmjSid(mailSid);

                //添付ファイルが存在する場合の処理
                if (senderMdl__.isTempFile()) {
                    List <String> binList =  cmnBiz.insertBinInfo(
                         con__, senderMdl__.getSaveFulPath(),
                         appRootPath__, cntCon__, userSid, now);

                    if (binList != null && binList.size() > 0) {
                        for (String binSid : binList) {
                            //ショートメール添付ファイル情報を登録する
                            sbMdl = new SmlBinModel();
                            sbMdl.setSmlSid(mailSid);
                            sbMdl.setBinSid(NullDefault.getLong(binSid, 0));
                            sbDao.insert(sbMdl);

                        }

                    }
                }

                //メール転送有無を判定
//                safMdl = smlCmnBiz.getSmailAccountForward(sacMdl.getSacSid(), -1, con__);
//                if (safMdl != null && smlCmnBiz.isSmailForwardOk(
//                        sacMdl.getSacSid(), safMdl, adminConf, con__)) {
//                    jparam.setSmjOpkbn(safMdl.getSafSmailOp());
//                } else {
//                    jparam.setSmjOpkbn(GSConstSmail.OPKBN_UNOPENED);
//                }
                jparam.setSmjOpkbn(GSConstSmail.OPKBN_UNOPENED);
                jparam.setSmjOpdate(null);
                jparam.setSmjJkbn(GSConst.JTKBN_TOROKU);
                jparam.setSmjAuid(sendUsid);
                jparam.setSmjAdate(now);
                jparam.setSmjEuid(sendUsid);
                jparam.setSmjEdate(now);
                SmlJmeisDao jdao = new SmlJmeisDao(con__);
                jdao.insert(jparam);

                //受信メールの集計データを登録する
                if (sendUsid == GSConst.SYSTEM_USER_MAIL) {
                    smlCmnBiz.regJmeisLogCntSystem(con__, sacMdl.getSacSid(), now);
                } else {
                    smlCmnBiz.regJmeisLogCnt(
                            con__, sacMdl.getSacSid(), GSConstSmail.SML_SEND_KBN_ATESAKI, now);
                }

                List<Integer> sacSidList = new ArrayList<Integer>();
                sacSidList.add(sacMdl.getSacSid());
                sendMdl = new SmailSendModel();
                sendMdl.setSmjSid(mailSid);
                sendMdl.setAccountSidList(sacSidList);
                sendMdl.setSacMdl(sacMdl);
                sendMdlList.add(sendMdl);

                //転送設定を取得し必要に応じてE-mailにて転送
                List<TempFileModel> fileList = new ArrayList<TempFileModel>();
                SmailDao smaildao = new SmailDao(con__);
                ArrayList<SmailDetailModel> sdList =
                    smaildao.selectSmeisDetailFromSid(sparam.getSmsSid());
                smlCmnBiz.sendSmailForward(
                        sparam,
                        sdList,
                        sacMdl.getSacSid(),
                        fileList,
                        adminConf,
                        pluginConfig__,
                        con__);
                targetSacSidList.add(sacMdl.getSacSid());
            }
        }

        //送信メールの容量を登録
        smlCmnBiz.updateAccountDiskSize(con__, sendUsid, sendDataSize * userSidArray.size());

        //受信メールの容量を登録
        for (int sacSid : targetSacSidList) {
            smlCmnBiz.updateAccountDiskSize(con__, sacSid, sendDataSize);
        }

        //ショートメール情報(送信)のデータ使用量を登録
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con__);
        usedDataBiz.insertSendDataSize(sendMailSidList, true);

        return sendMdlList;
    }

    /**
     * <br>[機  能] メール送信情報の値をチェックし送信可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true：送信可  false:送信不可
     * @throws SQLException SQL実行時例外
     */
    private boolean __canMailSend() throws SQLException {

        //送信先のチェック
        String atesakiErrMsg =
            GSValidateSmail.validateAtesakiUser(
                    senderMdl__.getSendToUsrSidArray(), con__, reqMdl__);
        if (!atesakiErrMsg.equals("")) {
            log__.debug("メール送信不可 原因：" + atesakiErrMsg);
            return false;
        }

        //メールタイトルのチェック
        String titleErrMsg =
            GSValidateSmail.validateSmlTitle(senderMdl__.getSendTitle(), reqMdl__);
        if (!titleErrMsg.equals("")) {
            log__.debug("メール送信不可 原因：" + titleErrMsg);
            return false;
        }

        //メールマークのチェック
        String markErrMsg =
            GSValidateSmail.validateSmlMark(senderMdl__.getSendMark(), reqMdl__);
        if (!markErrMsg.equals("")) {
            log__.debug("メール送信不可 原因：" + markErrMsg);
            return false;
        }

        //メール本文のチェック
        String bodyErrMsg =
            GSValidateSmail.validateSmlBody(senderMdl__.getSendBody(), reqMdl__);
        if (!bodyErrMsg.equals("")) {
            log__.debug("メール送信不可 原因：" + bodyErrMsg);
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 保存用CSVファイルパスを取得する
     * <br>[解  説] 例）2010/02/03/16
     * <br>[備  考]
     * @param saveDate 日付
     * @param binSid バイナリSID
     * @return 添付ファイル保存用のパス
     */
    public String getSavePathForCsvFile(UDate saveDate, Long binSid) {

        StringBuilder saveDir = new StringBuilder("");
        saveDir.append(saveDate.getStrYear());
        saveDir.append("/");
        saveDir.append(saveDate.getStrMonth());
        saveDir.append("/");
        saveDir.append(saveDate.getStrDay());
        saveDir.append("/");
        saveDir.append(binSid);

        String savePath = IOTools.replaceFileSep(saveDir.toString());
        return savePath;
    }

}