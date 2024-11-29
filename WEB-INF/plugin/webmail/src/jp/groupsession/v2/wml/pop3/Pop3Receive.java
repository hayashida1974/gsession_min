package jp.groupsession.v2.wml.pop3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.pop3.POP3Message;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.ContentType;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.StreamTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.mail.MailUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.GSAttachFileNotExistException;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlUsedDataBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvdataDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvlockDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountRcvsvrDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlFwlimitDao;
import jp.groupsession.v2.wml.dao.base.WmlHeaderDataDao;
import jp.groupsession.v2.wml.dao.base.WmlMailBodyDao;
import jp.groupsession.v2.wml.dao.base.WmlMailLogDao;
import jp.groupsession.v2.wml.dao.base.WmlMailLogSendDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.dao.base.WmlManageNoticeDao;
import jp.groupsession.v2.wml.dao.base.WmlSendaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlUidlDao;
import jp.groupsession.v2.wml.model.MailData;
import jp.groupsession.v2.wml.model.MailFilterConditionModel;
import jp.groupsession.v2.wml.model.MailFilterModel;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.WmlReceiveMailModel;
import jp.groupsession.v2.wml.model.WmlReceiveServerModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvlockModel;
import jp.groupsession.v2.wml.model.base.WmlAccountRcvsvrModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlHeaderDataModel;
import jp.groupsession.v2.wml.model.base.WmlMailBodyModel;
import jp.groupsession.v2.wml.model.base.WmlMailLogModel;
import jp.groupsession.v2.wml.model.base.WmlMailLogSendModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.model.base.WmlManageNoticeModel;
import jp.groupsession.v2.wml.model.base.WmlUidlModel;
import jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel;
import jp.groupsession.v2.wml.pop3.model.Pop3ReceiveResultModel;
import jp.groupsession.v2.wml.smtp.WmlSmtpSender;
import jp.groupsession.v2.wml.smtp.model.SmtpModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>
 * [機 能] メールの受信を行う <br>
 * [解 説] <br>
 * [備 考]
 *
 * @author JTS
 */
public class Pop3Receive {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Pop3Receive.class);

    /** メールヘッダ内容の最大文字数 */
    public static final int HEADER_MAXLEN = 1000;
    /** メールヘッダ件数の上限 */
    public static final int HEADER_LIMITCOUNT = 1000;

    /** 件数 */
    private static final int COMMIT_COUNT__ = 5;

    /** 名称なしの添付ファイル件数 */
    private int num__ = 0;

    /** 登録件数 */
    private long index__ = 0;

    /** アカウント使用ディスク容量 */
    private long useDiskSize__ = 0;

    /** 登録したメール情報のメッセージ番号 */
    private long[] messageNum__ = new long[COMMIT_COUNT__];

    /** メール情報登録時に使用する各種情報 */
    private Pop3ReceiveModel receiveMdl__ = null;

    /** メール受信情報 */
    private WmlReceiveServerModel receiveServerModel__ = null;

    /** メール添付ファイル バイナリSID */
    private List<Long> binSidList__ = new ArrayList<Long>();

    /** 登録したメールSID */
    private List<Long> mailSidList__ = new ArrayList<Long>();

    /**
     * <br>[機 能] メールの受信を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param server メール受信サーバ
     * @param model 接続情報
     * @param pop3Model アカウント情報
     * @param fileSaveDir 添付ファイル保存先ディレクトリ
     * @param ds DataSource
     * @param msgResource MessageResources
     * @param domain ドメイン
     * @throws Exception メールの受信に失敗
     */
    public synchronized void receive(Pop3Server server,
            WmlReceiveServerModel model, Pop3ReceiveModel pop3Model,
            String fileSaveDir, DataSource ds, MessageResources msgResource,
            String domain) throws Exception {
        receiveMdl__ = new Pop3ReceiveModel();
        try {
            receiveMdl__.setCon(GroupSession.getConnection(domain));
            receiveMdl__.getCon().setAutoCommit(false);

            // アカウントディスク使用量がディスク容量上限を超えていた場合、
            // 受信処理を行わない
            WmlManageNoticeDao wmnDao = new WmlManageNoticeDao(receiveMdl__.getCon());
            WmlManageNoticeModel wmnMdl = wmnDao.select(pop3Model.getWacSid());
            wmnDao = null;
            WmlBiz wmlBiz = new WmlBiz();
            long useDiskSize = wmlBiz.getUseDiskSize(receiveMdl__.getCon(),
                    pop3Model.getWacSid());
            wmlBiz = null;
            long diskLimit = model.getDiskLimitSizeForByte();
            int rcvFlg = wmnMdl.getWmnRcvfailedFlg();

            if (model.isDiskLimit()) {
                // 受信失敗当日にまだ通知を受け取っていなければショートメール通知を行う
                if (diskLimit < useDiskSize
                        && rcvFlg == GSConstWebmail.WML_FAILEDNOTICE_NOTRECEIVED) {
                    log__.info(model.getAccountName()
                            + " : ディスク使用量がディスク容量上限を超えているため受信を行いません。");

                    sendDiskLimitMail(pop3Model.getWacSid(), pop3Model.getAppRootPath(),
                            msgResource, domain, rcvFlg);
                    changeReceiveFlg(pop3Model.getWacSid(), rcvFlg);
                    return;
                }
            // 容量制限を行っていない場合、容量超過通知を受け取り可能にする
            } else if (rcvFlg == GSConstWebmail.WML_FAILEDNOTICE_RECEIVED) {
                changeReceiveFlg(model.getWacSid(), rcvFlg);
            }

            WmlDirectoryDao directoryDao = new WmlDirectoryDao(
                    receiveMdl__.getCon());
            long inboxWdrSid = directoryDao.getReceiveDirSid(pop3Model
                    .getWacSid());
            long dustWdrSid = directoryDao.getDirSid(pop3Model.getWacSid(),
                    GSConstWebmail.DIR_TYPE_DUST);
            long strageWdrSid = directoryDao.getDirSid(pop3Model.getWacSid(),
                    GSConstWebmail.DIR_TYPE_STORAGE);
            directoryDao = null;

            receiveMdl__.setMtCon(pop3Model.getMtCon());
            receiveMdl__.setWacSid(pop3Model.getWacSid());
            receiveMdl__.setAccountString(pop3Model.getAccountString());
            receiveMdl__.setAccountMailAddress(model.getAccountMailAddress());
            receiveMdl__.setSaveWdrSid(inboxWdrSid);
            receiveMdl__.setDustWdrSid(dustWdrSid);
            receiveMdl__.setStrageWdrSid(strageWdrSid);
            receiveMdl__.setAppRootPath(pop3Model.getAppRootPath());
            receiveMdl__.setFileSaveDir(fileSaveDir);
            receiveMdl__.setUserSid(pop3Model.getUserSid());

            receiveServerModel__ = model;

            // 受信処理
            try {
                long time = System.currentTimeMillis();
                server.receiveMessage(model, rcvFlg, this, msgResource, ds, domain, pop3Model.getUserSid());
                log__.info("受信時間:[アカウント" + model.getAccountName() + "]"
                        + (System.currentTimeMillis() - time));

//                // フィルタ
//                if (index__ % COMMIT_COUNT__ > 0) {
//                    doMailFiltering(receiveMdl__.getCon(), domain,
//                            receiveMdl__.getWacSid(),
//                            receiveMdl__.getDustWdrSid(), msgResource);
//                    receiveMdl__.getCon().commit();
//                }
            } catch (Throwable e) {
                log__.error("メール受信、もしくはフィルタ処理に失敗", e);
            } finally {
                // コネクション再接続
                reConnect(ds);
            }

            boolean commit = false;

            // 後処理
            try {
                // アカウントディスク容量を更新
                wmlBiz = new WmlBiz();
                wmlBiz.updateAccountDiskSize(receiveMdl__.getCon(),
                        receiveMdl__.getWacSid());
                wmlBiz = null;

                // 最終受信日時を更新
                WmlAccountRcvdataDao accountRsvDataDao = new WmlAccountRcvdataDao(
                        receiveMdl__.getCon());
                accountRsvDataDao.updateReceiveDate(receiveMdl__.getWacSid(),
                        new UDate());
                accountRsvDataDao = null;

                receiveMdl__.getCon().commit();
                commit = true;
            } catch (Throwable e) {
                log__.error("アカウントのディスク使用量集計に失敗", e);
            } finally {
                if (!commit) {
                    receiveMdl__.getCon().rollback();
                }
            }
        } finally {
            JDBCUtil.closeConnection(receiveMdl__.getCon());
            receiveMdl__ = null;
            if (IOTools.isDirCheck(fileSaveDir, false)) {
                IOTools.deleteDir(fileSaveDir);
            }
        }
    }

    /**
     * <p>指定したアカウントの受信失敗通知受け取りフラグを変更する
     * @param wacSid アカウントSID
     * @param rcvFlg 受信失敗通知受け取りフラグ
     * @throws SQLException SQL実行例外
     * */
    public void changeReceiveFlg(int wacSid, int rcvFlg) throws SQLException {
        boolean commit = false;
        try {
            WmlManageNoticeModel wmnMdl = new WmlManageNoticeModel();
            wmnMdl.setWacSid(wacSid);
            // 受信失敗通知受け取りフラグの変更
            if (rcvFlg == GSConstWebmail.WML_FAILEDNOTICE_RECEIVED) {
                wmnMdl.setWmnRcvfailedFlg(GSConstWebmail.WML_FAILEDNOTICE_NOTRECEIVED);
            } else {
                wmnMdl.setWmnRcvfailedFlg(GSConstWebmail.WML_FAILEDNOTICE_RECEIVED);
            }
            WmlManageNoticeDao wmnDao = new WmlManageNoticeDao(receiveMdl__.getCon());
            wmnDao.update(wmnMdl);
            wmnDao = null;

            receiveMdl__.getCon().commit();
            commit = true;
        } catch (Throwable e) {
            log__.error("通知管理 受信失敗通知受け取りフラグの更新に失敗", e);
        } finally {
            if (!commit) {
                receiveMdl__.getCon().rollback();
            }
        }
    }

    /**
     * <br>[機 能]DBの再接続を行う
     * <br>[解 説]receiveMdl__.getCon()で取得したConnectionをcloseし再取得する。
     * <br>[備 考]
     *
     * @param ds DataSource
     * @throws Exception コネクション取得に失敗
     */
    public void reConnect(DataSource ds) throws Exception {

        if (ds instanceof BasicDataSource) {
            BasicDataSource bds = (BasicDataSource) ds;
            if (bds.isClosed()) {
                return;
            }
        }

        // コネクション再接続
        JDBCUtil.closeConnection(receiveMdl__.getCon());
        receiveMdl__.setCon(GroupSession.getConnection(ds));
        receiveMdl__.getCon().setAutoCommit(false);
    }

    /**
     * <br>[機 能] メール情報の登録を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param domain ドメイン
     * @param receiveMdl メール情報登録時に使用する各種情報
     * @param receiveServerModel 接続サーバ情報
     * @param msg メール情報
     * @param msgResource MessageResources
     * @throws Throwable メールの受信に失敗
     */
    public synchronized void insertMailData(String domain,
            Pop3ReceiveModel receiveMdl,
            WmlReceiveServerModel receiveServerModel, MimeMessage msg,
            MessageResources msgResource) throws Throwable {
        try {
            num__ = 0;
            index__ = 0;
            messageNum__ = null;
            messageNum__ = new long[COMMIT_COUNT__];
            binSidList__ = new ArrayList<Long>();

            receiveMdl__ = receiveMdl;
            receiveServerModel__ = receiveServerModel;

            // アカウントのディスクサイズを取得
            initUseDiskSize();

            // メール情報を登録する
            insertMailData(domain, msg, null, true, false, msgResource, false);

            // メールの振り分けを行う
            doMailFiltering(receiveMdl__, domain,
                    msgResource);

            // アカウントディスク容量を更新
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.updateAccountDiskSize(receiveMdl__.getCon(),
                    receiveMdl__.getWacSid());
            wmlBiz = null;

        } catch (Throwable e) {
            __deleteTempFile();
            throw e;
        } finally {
            num__ = 0;
            index__ = 0;
            messageNum__ = null;
            messageNum__ = new long[COMMIT_COUNT__];
            binSidList__ = new ArrayList<Long>();

            receiveMdl__ = receiveMdl;
            receiveServerModel__ = receiveServerModel;
        }
    }

    /**
     * <br>[機 能] メール情報の登録を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param domain ドメイン
     * @param msg メール情報
     * @param uid UID
     * @param msgResource MessageResources
     * @param lastFlg 受信メール最後の処理か true:最後のメール  false:最後ではない
     * @return true:コミット false:失敗
     * @throws Exception メールの受信に失敗
     */
    protected synchronized Pop3ReceiveResultModel _insertMailData(String domain,
            MimeMessage msg, String uid, MessageResources msgResource, boolean lastFlg)
            throws Exception {
        if (receiveMdl__.getLogRegist() == null) {
            WmlAdmConfDao admConfDao = new WmlAdmConfDao(receiveMdl__.getCon());
            receiveMdl__.setLogRegist(admConfDao.selectAdmData()
                    .getWadAcctLogRegist());
            admConfDao = null;
        }

        return insertMailData(
                domain,
                msg,
                uid,
                false,
                receiveMdl__.getLogRegist().intValue() == GSConstWebmail.ACNT_LOG_REGIST_REGIST,
                msgResource, lastFlg);
    }

    /**
     * <br>[機 能] メール情報の登録を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param domain ドメイン
     * @param msg メール情報
     * @param uid UID
     * @param readed 未読/既読 true:既読 false:未読
     * @param writeLog true:送受信ログを登録する false:送受信ログを登録しない
     * @param msgResource MessageResources
     * @param lastFlg 受信メール最後の処理か true:最後のメール  false:最後ではない
     * @return true:コミット false:失敗
     * @throws Exception メールの受信に失敗
     */
    public synchronized Pop3ReceiveResultModel insertMailData(String domain,
            MimeMessage msg, String uid, boolean readed, boolean writeLog,
            MessageResources msgResource, boolean lastFlg) throws Exception {
        Pop3ReceiveResultModel receiveResultMdl = new Pop3ReceiveResultModel();
        receiveResultMdl.setCommitFlg(false);
        receiveResultMdl.setErrMailFlg(false);
        try {

            WmlReceiveMailModel mailData = new WmlReceiveMailModel();
            mailData.setUid(uid);

            long time = System.currentTimeMillis();

            // String contentType = msg.getContentType();
            // if (contentType.indexOf("boundary") > 0 &&
            // !contentType.endsWith("\"")) {
            // contentType = contentType.substring(0,
            // contentType.lastIndexOf("\"") + 1);
            // msg.setHeader("Content-Type", contentType);
            // }

            num__ = 0;
            // ヘッダ情報解析
            mailData = __readHeader(msg, mailData, msgResource);

            GsMessage gsMsg = new GsMessage();

            // BODY解析
            String tempSaveDir = IOTools.setEndPathChar(receiveMdl__
                    .getFileSaveDir());
            tempSaveDir = IOTools.setEndPathChar(tempSaveDir
                    + String.valueOf(index__));
            tempSaveDir = IOTools.replaceFileSep(tempSaveDir);

            try {

                //ヘッダ部解析時にエラーが発生した場合、メール本文の解析を行わない
                if (!mailData.isHeaderErrFlg()) {
                    long bodyTime = System.currentTimeMillis();
                    mailData = __readBody(msg, mailData, tempSaveDir, msgResource);
                    log__.info("メール BODY部解析時間: "
                            + (System.currentTimeMillis() - bodyTime) + " :MAILNUM"
                            + mailData.getMailNum());
                    time = System.currentTimeMillis();

                    //メール情報のディスク容量を取得(ヘッダー + メール情報)
                    mailData.setDiskSize(__getHeaderSize(msg) + msg.getSize());
                } else {
                    //メール情報のディスク容量を取得(メール情報)
                    mailData.setDiskSize(msg.getSize());
                }

                // アカウントディスク容量を加算
                setUseDiskSize(getUseDiskSize() + mailData.getDiskSize());
            } catch (Throwable e) {
                log__.warn("メール BODY部の解析に失敗したため、本文は正常に登録されません。 ", e);
                mailData.setErrFlg(true);
                ArrayList<String> emsgs = new ArrayList<String>();
                emsgs.add(gsMsg.getMessage(msgResource, "wml.136"));
                mailData.setErrMessage(emsgs);
            }

            log__.info("メール解析時間: " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            // メール情報の登録
            try {
                __insertMailData(receiveMdl__.getCon(), receiveMdl__.getMtCon(),
                        mailData, receiveMdl__.getAppRootPath(),
                        receiveMdl__.getSaveWdrSid(), receiveMdl__.getUserSid(),
                        readed, writeLog);
            } catch (WmlReceiveDummyAttachSetException e) {
                receiveResultMdl.setDammyAttachMail(true);
            }

            log__.info("メール情報のDBへの登録: " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            // メール取得後サーバのメール情報を削除する場合
            // UIDを登録しない
            // ※UIDを登録すると登録したUIDのメールを今後サーバから取得しない
            WmlUidlDao uidlDao = new WmlUidlDao(receiveMdl__.getCon());

            if (!StringUtil.isNullZeroString(uid)
                    && (!receiveServerModel__.isDelReceive()
                            || receiveResultMdl.isDammyAttachMail())
                    && (!receiveServerModel__.isReReceive()
                            || !uidlDao.existUID(
                                    receiveMdl__.getWacSid(),
                                    receiveMdl__.getAccountString(),
                                    mailData.getUid()))) {
                WmlUidlModel uidlMdl = new WmlUidlModel();
                uidlMdl.setWudUid(__cloneString(mailData.getUid()));
                uidlMdl.setWacSid(receiveMdl__.getWacSid());
                uidlMdl.setWudAccount(__cloneString(receiveMdl__
                        .getAccountString()));
                uidlDao.insert(uidlMdl);
                uidlMdl = null;

                log__.info("UIDL登録時間: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
            }
            uidlDao = null;
            tempSaveDir = null;
            mailData = null;

            index__++;

            // 指定件数ごとにコミットする
            // 最後の処理の場合は必ずコミットする
            if (index__ % COMMIT_COUNT__ == 0 || lastFlg) {
                receiveMdl__.getCon().commit();
                log__.info("指定件数ごとのコミット時間: "
                        + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();
                // フィルタリング
                doMailFiltering(receiveMdl__, domain,
                        msgResource);
                receiveMdl__.getCon().commit();
                log__.info("フィルタ実行時間: "
                        + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                messageNum__ = null;
                messageNum__ = new long[COMMIT_COUNT__];
                binSidList__.clear();


                receiveResultMdl.setCommitFlg(true);
                return receiveResultMdl;
            }
        } catch (Throwable e) {
            log__.error("受信メール情報の登録に失敗", e);
            receiveMdl__.getCon().rollback();
            receiveResultMdl.setErrMailFlg(true);
            try {
                __deleteTempFile();
                binSidList__.clear();
            } catch (Throwable thw) {
            }
        }

        return receiveResultMdl;
    }

    /**
     * <br>[機 能] メールヘッダーの解析を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param msg メール情報
     * @param mailData 受信メール情報格納用Model
     * @param msgResource MessageResources
     * @return 受信メール情報格納用Model
     * @throws Exception メールヘッダーの解析に失敗
     */
    private WmlReceiveMailModel __readHeader(MimeMessage msg,
            WmlReceiveMailModel mailData, MessageResources msgResource)
            throws Exception {
        long time = System.currentTimeMillis();

        // サブジェクトを取得
        try {
            String srcSubject = msg.getHeader("Subject", null);
            log__.debug("件名(エンコード前) = " + srcSubject);
            if (!StringUtil.isNullZeroString(srcSubject)) {
//                mailData.setSubject(MailUtil.decodeText(srcSubject));
                try {
                    mailData.setSubject(MailUtil.decodeMimeText(srcSubject));
                } catch (Exception e) {
                    log__.error("デコード失敗: " + srcSubject, e);
                    throw e;
                }
            }
            srcSubject = null;
            log__.debug("件名(エンコード後) " + mailData.getSubject());
        } catch (Throwable e) {
            log__.debug("件名取得時に例外発生");
        } finally {
            mailData.setSubject(NullDefault.getString(mailData.getSubject(), ""));
        }

        // 送信元(FROM)を取得
        boolean fromFlg = false;
        try {
            Address[] from = msg.getFrom();
            if (from != null) {
                for (Address address : from) {
//                    mailData.addFrom(MailUtil.decodeText(address.toString()));
                    mailData.addFrom(MailUtil.decodeMimeText(address.toString()));
                }
                from = null;
            }
            fromFlg = true;
        } catch (Throwable e) {
        }

        // 送信元(FROM)アドレスが不正だった場合、エラー内容を設定する
        if (!fromFlg
                || (!mailData.getFrom().isEmpty() && mailData.getFrom().get(0)
                        .length() > GSConstWebmail.MAXLEN_ACCOUNT_ADDRESS)) {
            GsMessage gsMsg = new GsMessage();
            mailData.setFrom(new ArrayList<String>());
            mailData.addFrom(gsMsg.getMessage(msgResource, "wml.232"));
            mailData.addErrMessage(gsMsg.getMessage(msgResource, "wml.235"));
            mailData.addErrMessage(gsMsg.getMessage(msgResource, "wml.236"));
            mailData.setErrFlg(true);
        }

        // 送信先(TO,CC,BCC)を取得
        __setAddress(msg, mailData, Message.RecipientType.TO);
        __setAddress(msg, mailData, Message.RecipientType.CC);
        __setAddress(msg, mailData, Message.RecipientType.BCC);

        log__.info("サブジェクト、送信元(FROM)、送信先(TO,CC,BCC)取得までの時間: "
                + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        try {
            // 送信日時を取得
            if (msg.getSentDate() != null) {
                mailData.setSendDate(UDate.getInstanceDate(msg.getSentDate()));
            } else {
                mailData.setSendDate(new UDate());
            }

            // メールヘッダー項目を取得
            @SuppressWarnings("all")
            Enumeration headerLines = null;
            if (msg instanceof POP3Message) {
                headerLines = ((POP3Message) msg).getAllHeaderLines();
            } else if (msg instanceof IMAPMessage) {
                headerLines = ((IMAPMessage) msg).getAllHeaderLines();
            } else {
                headerLines = msg.getAllHeaderLines();
            }

            String header = null;
            String key = null;
            boolean headerEnd = false;
            int headerCnt = 0;
            while (headerLines.hasMoreElements()) {
                header = (String) headerLines.nextElement();
                headerEnd = header.trim().length() == 0;
                if (headerEnd) {
                    mailData.setContent(header.toString() + "\r\n"
                            + NullDefault.getString(mailData.getContent(), ""));

                } else {
                    int keyIndex = header.indexOf(":");
                    if (keyIndex > 0
                            && keyIndex <= GSConstWebmail.MAX_LEN_MAILHEADER) {

                        key = header.substring(0, keyIndex);
                        if (mailData.getHeaderKey().indexOf(key) < 0) {
                            mailData.addHeader(__cloneString(key),
                                    msg.getHeader(key));
                            key = null;
                        }
                    } else {
                        mailData.setContent(header.toString() + "\r\n"
                                + NullDefault.getString(mailData.getContent(), ""));
                    }
                }
                header = null;

                //メールヘッダの件数が上限を超えている場合、エラーとする
                headerCnt++;
                if (headerCnt > HEADER_LIMITCOUNT) {
                    mailData.setErrFlg(true);
                    mailData.setHeaderErrFlg(true);

                    //ヘッダー情報をクリアする
                    mailData.getHeaderKey().clear();
                    mailData.getHeaderMap().clear();
                    mailData.setTo(null);
                    mailData.setCc(null);
                    mailData.setBcc(null);
                    GsMessage gsMsg = new GsMessage();
                    mailData.setErrMessage(
                            Arrays.asList(
                                    new String[] {
                                        gsMsg.getMessage(msgResource, "wml.319"),
                                        gsMsg.getMessage(msgResource, "wml.136")
                                    }));

                    break;
                }
            }
            headerLines = null;
        } catch (Exception e) {
            log__.error("メールヘッダの解析に失敗", e);

            //ヘッダー情報をクリアする
            mailData.getHeaderKey().clear();
            mailData.getHeaderMap().clear();
            mailData.setTo(null);
            mailData.setCc(null);
            mailData.setBcc(null);
            GsMessage gsMsg = new GsMessage();
            mailData.setErrMessage(
                    Arrays.asList(
                            new String[] {
                                gsMsg.getMessage(msgResource, "wml.319"),
                                gsMsg.getMessage(msgResource, "wml.136")
                            }));

            mailData.setErrFlg(true);
            mailData.setHeaderErrFlg(true);
        }
        log__.info("メールヘッダー項目取得までの時間: " + (System.currentTimeMillis() - time));

        return mailData;
    }

    /**
     * <br>[機 能] メール本文の解析を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param part メール情報
     * @param mailData 受信メール情報格納用Model
     * @param fileSaveDir 添付ファイル保存先ディレクトリ
     * @param msgResource MessageResources
     * @return 受信メール情報格納用Model
     * @throws Exception メール本文の解析に失敗
     */
    private WmlReceiveMailModel __readBody(Part part,
            WmlReceiveMailModel mailData, String fileSaveDir,
            MessageResources msgResource) throws Exception {

        String contentType = (new Pop3DataSource(part.getDataHandler()
                .getDataSource())).getContentType();
        boolean existContentType = NullDefault.getString(contentType, "")
                .trim().length() > 0;
        // テキストの場合(Content-Typeが未設定の場合、テキストとして扱う)
        if ((!existContentType && part.getFileName() == null)
                || (part.isMimeType("text/plain") && part.getFileName() == null)) {

            if (existContentType) {
                mailData.setCharset(CommonBiz.getHeaderCharset(contentType));
            }
            // charsetが未設置の場合、iso-2022-jpを設定する
            boolean existCharset = true;
            if (StringUtil.isNullZeroString(mailData.getCharset())
                    || mailData.getCharset().equals("null")) {
                mailData.setCharset(Encoding.ISO_2022_JP);
                existCharset = false;
            }
            String bodyContent = (String) Pop3Utility.getContent(part);

            if (!StringUtil.isNullZeroString(bodyContent)) {
                String encode = "";
                if (part.getHeader("Content-Transfer-Encoding") != null
                && part.getHeader("Content-Transfer-Encoding").length > 0) {
                    encode = NullDefault.getString(
                                            part.getHeader("Content-Transfer-Encoding")[0], "");
                    encode = encode.toLowerCase();
                }
                if (part.isMimeType("text/plain") && encode.equals("7bit")
                && mailData.getCharset().toUpperCase().equals(Encoding.ISO_2022_JP)) {
                    InputStream is = null;
                    byte[] bodyBytes = null;
                    String checkStr = null;
                    try {
                        is = part.getInputStream();
                        bodyBytes = StreamTools.readInputStreamToByteArray(is);
                        bodyContent = MailUtil.decodeJis(bodyBytes);
                        bodyBytes = null;

                        checkStr = bodyContent.toString();
                        checkStr = checkStr.replaceAll("\r", "");
                        checkStr = checkStr.replaceAll("\n", "");
                        if (!MailUtil.canDecodeString(checkStr)) {
                            bodyContent = (String) Pop3Utility.getContent(part);
                            bodyContent = __convertBodyWithoutCharset(bodyContent, existCharset);
                        }
                    } catch (Throwable e) {
                        bodyContent = (String) Pop3Utility.getContent(part);
                        bodyContent = __convertBodyWithoutCharset(bodyContent, existCharset);
                    } finally {
                        checkStr = null;
                        bodyBytes = null;
                        is.close();
                        is = null;
                    }
                } else if (!existCharset) {
                    bodyContent = __convertBodyWithoutCharset(bodyContent, existCharset);
                }
                encode = null;
            }

            if (!StringUtil.isNullZeroString(mailData.getContent())) {
                mailData.setContent(mailData.getContent() + bodyContent);
            } else {
                mailData.setContent(bodyContent);
            }

            return mailData;
        }

        // マルチパートの場合、再帰的に探索する。
        if (part.isMimeType("multipart/*")
                || NullDefault.getString(contentType, "").indexOf(
                        "multipart/mixed") >= 0) {
            Multipart mp = (Multipart) Pop3Utility.getContent(part);
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                mailData = __readBody(mp.getBodyPart(i), mailData, fileSaveDir,
                        msgResource);
            }
            mp = null;
            return mailData;
        }

        // その他の場合、添付ファイル名とContent-Typeを表示し保存する。
        // ファイル名がない場合、ファイル名を「添付ファイル」+「番号」とする。
        String fileName = part.getFileName();
        boolean htmlMail = false;
        String txt_attachFile = (new GsMessage()).getMessage(msgResource,
                "cmn.attach.file");
        if (part.isMimeType("message/rfc822")) {

            if (part.getHeader("Subject") != null) {
                fileName = WmlBiz.decodeFileName(part.getHeader("Subject")[0]);
            } else if (fileName != null) {
                fileName = WmlBiz.decodeFileName(fileName);
            } else {
                fileName = txt_attachFile + num__++ + ".eml";
            }

        } else if (part.isMimeType("application/pgp-signature")) {
            //PGP署名
            if (StringUtil.isNullZeroString(fileName)) {
                fileName = "signature.dat";
            }
        } else {

            if (fileName != null) {
                fileName = __repareMultiByteFilename(fileName, part);
                fileName = WmlBiz.decodeFileName(fileName);
            } else {
                if (part.isMimeType("text/html")) {
                    fileName = GSConstWebmail.HTMLMAIL_FILENAME;
                    htmlMail = true;
                } else {
                    fileName = txt_attachFile + num__++;
                }
            }
        }

        // ファイルの保存
        InputStream partInputStream = null;
        BufferedInputStream is = null;
        BufferedOutputStream os = null;
        File filePath = null;
        int fileNum = mailData.getNextFileNum();

        try {
            IOTools.isDirCheck(fileSaveDir, true);
            filePath = new File(fileSaveDir, String.valueOf(fileNum));
            os = new BufferedOutputStream(new FileOutputStream(filePath));
            partInputStream = part.getInputStream();
            is = new BufferedInputStream(partInputStream);

            int size;
            int buffersize = 8 * 1024;
            byte[] buff = new byte[buffersize];
            while ((size = is.read(buff)) != -1) {
                os.write(buff, 0, size);
            }
            os.flush();
            buff = null;

            //    $GSTEMPDIR + "/webmail/batch/" + アカウントSID
            // + "/" + メールの受信順(index__) + "/" + メール添付ファイルの連番(fileNum)
            log__.debug("WEBメール添付ファイル保存先: " + filePath + "");
        } catch (Exception e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
            if (partInputStream != null) {
                partInputStream.close();
                partInputStream = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
        }

        WmlMailFileModel fileMdl = new WmlMailFileModel();
        fileMdl.setFileName(__cloneString(fileName));
        fileMdl.setFilePath(filePath.getPath());
        fileMdl.setContentType(__cloneString(part.getContentType()));
        fileMdl.setHtmlMail(htmlMail);
        mailData.addTempFile(fileMdl);
        fileName = null;
        filePath = null;

        return mailData;
    }

    /**
     * <br>[機  能] メール本文のcharsetが未設定の場合、iso-2022-jpに再変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param bodyContent メール本文
     * @param existCharset true:charsetあり false: charsetなし
     * @return メール本文
     * @throws UnsupportedEncodingException メール本文の変換に失敗
     */
    private String __convertBodyWithoutCharset(String bodyContent, boolean existCharset)
    throws UnsupportedEncodingException {
        if (!existCharset) {
            bodyContent = new String(bodyContent.getBytes(Encoding.ISO8859_1),
                                                    Encoding.ISO_2022_JP);
        }
        return bodyContent;
    }

    /**
     * <br>[機 能] Messageから送信先(TO,CC,BCC)を取得し、メール情報に設定する。
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param msg メール情報
     * @param mailData 受信メール情報格納用Model
     * @param type 種別
     * @throws MessagingException 送信先(TO,CC,BCC)の取得に失敗
     * @throws UnsupportedEncodingException 送信先のdecodeに失敗
     */
    private void __setAddress(Message msg, WmlReceiveMailModel mailData,
            RecipientType type) throws MessagingException,
            UnsupportedEncodingException {

        Address[] addressList = null;
        try {
            addressList = msg.getRecipients(type);
        } catch (Exception e) {
            log__.warn("受信メールの送信先アドレスの取得に失敗", e);
            addressList = null;
            
            try {
                String headerValue = ((MimeMessage) msg).getHeader(type.toString(), ",");
                addressList = InternetAddress.parseHeader(headerValue, false);
            } catch (Exception headerError) {
                log__.warn("受信メールの送信先アドレスの取得(再取得)に失敗", headerError);
                addressList = null;
            }
        }

        if (addressList != null) {
            for (Address address : addressList) {
                try {
//                    __setAddress(mailData,
//                            MailUtil.decodeText(__cloneString(address
//                                    .toString())), type);
                    __setAddress(mailData,
                            MailUtil.decodeMimeText(__cloneString(address
                                    .toString())), type);
                } catch (Exception e) {
                    log__.debug("デコード失敗: " + address, e);
                }
            }
        }
    }

    /**
     * <br>[機 能] メール情報に送信先(TO,CC,BCC)を設定する。
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param mailData 受信メール情報格納用Model
     * @param address 送信先(TO,CC,BCC)
     * @param type 種別(TO or CC or BCC)
     */
    private void __setAddress(WmlReceiveMailModel mailData, String address,
            RecipientType type) {
        if (type.equals(Message.RecipientType.TO)) {
            mailData.addTo(address);
        } else if (type.equals(Message.RecipientType.CC)) {
            mailData.addCc(address);
        } else if (type.equals(Message.RecipientType.BCC)) {
            mailData.addBcc(address);
        }
    }

    /**
     * <br>[機 能] メール情報の登録を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param con コネクション
     * @param mtCon 採番コントローラ
     * @param mailData メール情報
     * @param appRootPath アプリケーションルートパス
     * @param directorySid 保存先ディレクトリSID
     * @param userSid ユーザSID
     * @param readed 未読/既読 true:既読 false:未読
     * @param writeLog true:送受信ログを登録する false:送受信ログを登録しない
     * @return メールSID
     * @throws WmlReceiveDummyAttachSetException 添付ファイルにダミーを使用し処理が継続された
     * @throws Exception 実行時例外
     */
    private long __insertMailData(Connection con, MlCountMtController mtCon,
            WmlReceiveMailModel mailData, String appRootPath,
            long directorySid, int userSid, boolean readed, boolean writeLog)
            throws WmlReceiveDummyAttachSetException, Exception {
        WmlReceiveDummyAttachSetException attachDummy = null;
        int wacSid = receiveMdl__.getWacSid();
        long mailNum = mtCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                GSConstWebmail.SBNSID_SUB_MESSAGE, userSid);
        mailSidList__.add(mailNum);

        WmlMaildataModel receiveData = new WmlMaildataModel();
        receiveData.setWmdMailnum(mailNum);

        long time = System.currentTimeMillis();

        // 件名
        receiveData.setWmdTitle(NullDefault.getString(
                __cloneString(mailData.getSubject()), ""));

        // 件名が1000文字以上の場合は切捨て
        if (receiveData.getWmdTitle().length() > 1000) {
            receiveData.setWmdTitle(receiveData.getWmdTitle()
                    .substring(0, 1000));
        }

        // 送信日時
        receiveData.setWmdSdate(mailData.getSendDate());
        // 送信元
        if (mailData.getFrom() != null && !mailData.getFrom().isEmpty()) {
            receiveData.setWmdFrom(__cloneString(mailData.getFrom().get(0)));
            // 送信元が256文字以上の場合は切捨て
            if (receiveData.getWmdFrom().length() > 256) {
                receiveData.setWmdFrom(
                        receiveData.getWmdFrom().substring(0, 256));
            }
        }
        // 添付ファイルフラグ
        if (!mailData.getTempFileList().isEmpty()) {
            receiveData.setWmdTempflg(GSConstWebmail.TEMPFLG_EXIST);
        } else {
            receiveData.setWmdTempflg(GSConstWebmail.TEMPFLG_NOTHING);
        }
        // 状態,返信フラグ,転送フラグ,未読/既読フラグ
        receiveData.setWmdStatus(GSConstWebmail.WMD_STATUS_NORMAL);
        receiveData.setWmdReply(GSConstWebmail.WMD_REPLY_NORMAL);
        receiveData.setWmdForward(GSConstWebmail.WMD_FORWARD_NORMAL);
        receiveData.setWmdReaded(GSConstWebmail.WMD_READED_NO);
        if (readed) {
            receiveData.setWmdReaded(GSConstWebmail.WMD_READED_YES);
        }
        // 保存先アカウントSID
        receiveData.setWacSid(wacSid);
        // 保存先ディレクトリSID
        receiveData.setWdrSid(directorySid);

        // ディスク容量
        receiveData.setWmdSize(mailData.getDiskSize());

        //登録日時
        receiveData.setWmdAdate(new UDate());

        WmlMaildataDao receiveDao = new WmlMaildataDao(con);
        receiveDao.insert(receiveData);

        receiveData = null;
        receiveDao = null;

        log__.info("メール情報(WML_MAILDATA)登録時間: "
                + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        // メール本文を登録する
        WmlMailBodyModel mailBodyMdl = new WmlMailBodyModel();
        mailBodyMdl.setWmdMailnum(mailNum);
        mailBodyMdl.setWmbBody(__cloneString(mailData.getContent()));
        mailBodyMdl.setWmbCharset(mailData.getCharset());
        mailBodyMdl.setWacSid(wacSid);

        // メール本文の最大文字数を超えているかを判定
        if (receiveServerModel__.getMailBodyLimit() > 0) {
            if (!StringUtil.isNullZeroString(mailBodyMdl.getWmbBody())
                    && mailBodyMdl.getWmbBody().length() > receiveServerModel__
                            .getMailBodyLimit()) {
                mailBodyMdl.setWmbBody(mailBodyMdl.getWmbBody().substring(0,
                        receiveServerModel__.getMailBodyLimit()));
            }
        }

        if (mailData.isErrFlg()) {
            String errMessage = "";
            for (String errMsg : mailData.getErrMessage()) {
                errMessage += errMsg + "\r\n";
            }
            mailBodyMdl.setWmbBody(errMessage + "\r\n"
                    + NullDefault.getString(mailBodyMdl.getWmbBody(), ""));
            errMessage = null;
        }
        WmlMailBodyDao mailBodyDao = new WmlMailBodyDao(con);
        mailBodyDao.insert(mailBodyMdl);
        mailBodyMdl = null;
        mailBodyDao = null;

        log__.info("メール本文(WML_MAIL_BODY)登録時間: "
                + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        // 送信先を登録する
        WmlSendaddressDao sendAddressDao = new WmlSendaddressDao(con);
        sendAddressDao.insertSendAddress(mailNum, wacSid, mailData.getTo(),
                mailData.getCc(), mailData.getBcc(), mailData.getRenban());

        sendAddressDao = null;

        log__.info("送信先(WML_SENDADDRESS)登録時間: "
                + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        // メールヘッダー情報を登録
        WmlBiz wmlBiz = new WmlBiz();
        List<WmlHeaderDataModel> headerDataList = wmlBiz.createHeaderDataList(
                mailNum, mailData, wacSid);

        WmlHeaderDataDao headerDao = new WmlHeaderDataDao(con);
        headerDao.insert(headerDataList);

        headerDataList.clear();
        headerDataList = null;
        headerDao = null;

        log__.info("メールヘッダー情報(WML_HEADER_DATA)登録時間: "
                + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        // 添付ファイル登録
        List<Long> tempBinSidList = null;
        try {
            tempBinSidList = wmlBiz.insertBinInfo(con,
                    mailData.getTempFileList(), appRootPath, mtCon, mailNum,
                    userSid, new UDate());
        } catch (TempFileException e) {
            Throwable cause = GSAttachFileNotExistException.searchCause(e);
            if (cause != null) {
                log__.error("", cause);
//                  ファイル添付に失敗した場合
//              　　テキストデータ「${ファイル名}を保管できませんでした」を代わりに登録する
//              　　ファイル名：添付ファイル保管エラー.txt
                tempBinSidList = new ArrayList<Long>();
                tempBinSidList.add(wmlBiz.insertBinInfoError(con,
                        appRootPath,
                        mtCon,
                        userSid,
                        new UDate(),
                        mailData.getTempFileList(),
                        mailNum));

                attachDummy = new WmlReceiveDummyAttachSetException(e);
            } else {
                throw e;
            }
        }

        if (tempBinSidList != null && !tempBinSidList.isEmpty()) {
            binSidList__.addAll(tempBinSidList);
        }
//        wmlBiz = null;
        tempBinSidList.clear();
        tempBinSidList = null;

        log__.info("添付ファイル(WML_TEMPFILE)登録時間: "
                + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        // 受信ロックの最終受信時間を更新する
        WmlAccountRcvlockModel accountLockModel = new WmlAccountRcvlockModel();
        accountLockModel.setWacSid(wacSid);
        accountLockModel.setWrlRcvedate(new UDate());
        WmlAccountRcvlockDao accountLockDao = new WmlAccountRcvlockDao(con);
        accountLockDao.update(accountLockModel);
        accountLockModel = null;
        accountLockDao = null;

        if (writeLog) {
            // メール履歴を登録する
            WmlMailLogModel mailLogModel = new WmlMailLogModel();
            mailLogModel.setWmdMailnum(mailNum);
            mailLogModel.setWlgTitle(NullDefault.getString(
                    __cloneString(mailData.getSubject()), ""));
            // 件名が1000文字以上の場合は切捨て
            if (mailLogModel.getWlgTitle().length() > 1000) {
                mailLogModel.setWlgTitle(
                        mailLogModel.getWlgTitle().substring(0, 1000));
            }

            mailLogModel.setWlgDate(mailData.getSendDate());
            if (mailData.getFrom() != null && !mailData.getFrom().isEmpty()) {
                mailLogModel
                        .setWlgFrom(__cloneString(mailData.getFrom().get(0)));
                // 送信元が256文字以上の場合は切捨て
                if (mailLogModel.getWlgFrom().length() > 256) {
                    mailLogModel.setWlgFrom(
                            mailLogModel.getWlgFrom().substring(0, 256));
                }
            }
            if (!mailData.getTempFileList().isEmpty()) {
                mailLogModel.setWlgTempflg(GSConstWebmail.TEMPFLG_EXIST);
            } else {
                mailLogModel.setWlgTempflg(GSConstWebmail.TEMPFLG_NOTHING);
            }
            mailLogModel.setWlgMailtype(GSConstWebmail.MAILTYPE_RECEIVE);
            mailLogModel.setWacSid(wacSid);

            WmlMailLogDao mailLogDao = new WmlMailLogDao(con);
            mailLogDao.insert(mailLogModel);
            mailLogDao = null;
            mailLogModel = null;

            log__.info("メール履歴(WML_MAIL_LOG)登録時間: "
                    + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            // メール履歴_送信先を登録する
            WmlMailLogSendModel mailLogSendModel = new WmlMailLogSendModel();
            mailLogSendModel.setWmdMailnum(mailNum);
            mailLogSendModel.setWlsNum(1);
            mailLogSendModel.setWlsType(GSConstWebmail.WSA_TYPE_TO);
            mailLogSendModel.setWlsAddress(__cloneString(receiveMdl__
                    .getAccountMailAddress()));
            mailLogSendModel.setWacSid(wacSid);

            WmlMailLogSendDao mailLogSendDao = new WmlMailLogSendDao(con);
            mailLogSendDao.insert(mailLogSendModel);
            mailLogSendDao = null;
            mailLogSendModel = null;

            log__.info("メール履歴_送信先(WML_MAIL_LOG_SEND)登録時間: "
                    + (System.currentTimeMillis() - time));

            //メール送受信ログのデータ使用量を登録
            WmlUsedDataBiz usedDataBiz = new WmlUsedDataBiz(con);
            usedDataBiz.insertMaillogSize(mailNum, true);
            usedDataBiz = null;
        }

        //統計機能 受信メール集計用データを登録する
        wmlBiz.regJmailLogCnt(con, wacSid, 1, new UDate());
        wmlBiz = null;

        messageNum__[(int) index__ % COMMIT_COUNT__] = mailNum;

        // log__.info("#UID = " + mailData.getUID());
        if (attachDummy != null) {
            throw attachDummy;
        }

        return mailNum;
    }

    /**
     * <br>[機 能] 指定したメールが受信済みかを判定する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param wacSid アカウントSID
     * @param accountString アカウント文字列
     * @param uid UID
     * @return true:受信済み false:未受信
     * @throws SQLException SQL実行時例外
     */
    public boolean isReceived(int wacSid, String accountString, String uid)
            throws SQLException {
        WmlBiz wmlBiz = new WmlBiz();
        return wmlBiz.isReceived(receiveMdl__.getCon(), wacSid, accountString,
                uid);
    }

    /**
     * <br>[機 能] 指定したメールのうち、受信していないメールのUIDを取得する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param wacSid アカウントSID
     * @param accountString アカウント文字列
     * @param uid UID
     * @return UID
     * @throws SQLException SQL実行時例外
     */
    public List<String> getNotReceivedUid(int wacSid, String accountString,
            List<String> uid) throws SQLException {
        WmlUidlDao uidlDao = new WmlUidlDao(receiveMdl__.getCon());
        return uidlDao.getNotExistUIDList(wacSid, accountString, uid);
    }

    /**
     * <br>[機 能] 指定したメールのうち、受信していないメールのUIDを取得する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param wacSid アカウントSID
     * @param accountString アカウント文字列
     * @param uid UID
     * @return UID
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, String> getNotReceivedUidMap(int wacSid,
            String accountString, ArrayList<String> uid) throws SQLException {
        WmlUidlDao uidlDao = new WmlUidlDao(receiveMdl__.getCon());
        return uidlDao.getNotExistUIDMap(wacSid, accountString, uid);
    }

    /**
     * <br>[機 能] メールフィルタリングを行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param rcvMdl 受信モデル
     * @param domain ドメイン
     * @param msgResource MessageResources
     * @throws SQLException SQL実行時例外
     * @throws MessagingException メールの転送に失敗
     * @throws UnsupportedEncodingException メールの送信に失敗
     * @throws TempFileException 添付ファイルの操作に失敗
     * @throws GSException GS用汎実行例外
     */
    public void doMailFiltering(Pop3ReceiveModel rcvMdl,
            String domain, MessageResources msgResource) throws SQLException,
            MessagingException, UnsupportedEncodingException, TempFileException, GSException {
        Connection con = rcvMdl.getCon();
        int wacSid = rcvMdl.getWacSid();

        log__.info("フィルタリング 開始");

        long time = System.currentTimeMillis();

        List<Long> mailNum = new ArrayList<Long>();
        for (int i = 0; i < messageNum__.length; i++) {
            if (messageNum__[i] > 0) {
                mailNum.add(messageNum__[i]);
            }
        }
        if (mailNum.isEmpty()) {
            log__.info("フィルタリング 終了(受信メールなし)");
            return;
        }

        //管理者設定よりメール転送制限を取得する
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        WmlAdmConfModel adminMdl = adminDao.selectAdmData();

        // フィルター情報を取得する
        WebmailDao webmailDao = new WebmailDao(con);
        List<MailFilterModel> filterList = webmailDao.getFilterData(wacSid);

        // メールのフィルタリングを行う
        List<Long> updateMailNum = null;
        List<Long> oldMailNum = new ArrayList<Long>();
        List<MailFilterConditionModel> conditionList = null;
        Map<Integer, List<Long>> labelMap = new HashMap<Integer, List<Long>>();
        WmlFilterFwaddressDao fwAdrDao = new WmlFilterFwaddressDao(con);

        // 転送制限文字列を取得する
        WmlFwlimitDao fwLimitDao = new WmlFwlimitDao(con);
        List<String> fwTextList = fwLimitDao.getFwTextList();

        //転送先メールアドレス
        List<String> fwAddressList = null;

        for (MailFilterModel filterData : filterList) {
            // フィルタ条件の取得
            conditionList = webmailDao.getFilterConditionData(filterData
                    .getWftSid());
            // フィルタリング対象のメール番号を取得
            updateMailNum = webmailDao.getFilteringMailNum(mailNum, filterData,
                    conditionList, wacSid);

            if (!updateMailNum.isEmpty()) {
                if (filterData.getLabelSid() > 0) {
                    if (!labelMap.containsKey(filterData.getLabelSid())) {
                        labelMap.put(filterData.getLabelSid(),
                                new ArrayList<Long>());
                    }

                    for (long lblMailNum : updateMailNum) {
                        if (!labelMap.get(filterData.getLabelSid()).contains(
                                lblMailNum)) {
                            labelMap.get(filterData.getLabelSid()).add(
                                    lblMailNum);
                        }
                    }
                }

                for (long oldNum : oldMailNum) {
                    if (updateMailNum.contains(oldNum)) {
                        updateMailNum.remove(updateMailNum.indexOf(oldNum));
                    }
                }

                if (!updateMailNum.isEmpty()) {
                    webmailDao.updateFilterlingMail(rcvMdl, updateMailNum,
                            filterData);

                    //メール転送制限 転送禁止以外の場合
                    if (adminMdl.getWadFwlimit() != GSConstWebmail.WAD_FWLIMIT_PROHIBITED) {
                        // 指定されたアドレスへ転送
                        if (filterData.isForward()) {
                            log__.debug("WEBメール フィルタリング 転送 開始");
                            fwAddressList = fwAdrDao
                                    .getAddressList(filterData.getWftSid());

                            boolean sendResult =
                                    __sendMail(con, domain, webmailDao, updateMailNum,
                                            fwAddressList, msgResource, fwTextList,
                                            adminMdl.getWadFwlimit());

                            //フィルタリング 転送 オペレーションログ出力
                            if (sendResult && fwAddressList != null && fwAddressList.size() > 0) {
                                //メール情報を取得する
                                List<MailData> recieveMailDataList =
                                        webmailDao.getMailData(updateMailNum);
                                if (recieveMailDataList != null && recieveMailDataList.size() > 0) {
                                    for (MailData recieveMailData: recieveMailDataList) {
                                        //ログ出力内容 生成
                                        String mailFilterLogValue = __createFilterTransferLogValue(
                                                con, domain, msgResource, fwAddressList,
                                                wacSid, recieveMailData, filterData, conditionList);
                                        //ログ出力
                                        GsMessage gsMsg = new GsMessage();
                                        WmlBiz wmlBiz = new WmlBiz();
                                        wmlBiz.outPutBatchLog(con,
                                                domain,
                                                gsMsg.getMessage(msgResource, "cmn.forward"),
                                                getClass().getName(),
                                                gsMsg.getMessage(msgResource, "wml.197"),
                                                GSConstLog.LEVEL_TRACE,
                                                mailFilterLogValue,
                                                "filter sendmail end");

                                        log__.info("WEBメール フィルタリング 転送 :" + mailFilterLogValue);
                                    }
                                }
                            }
                            log__.debug("WEBメール フィルタリング 転送 終了");
                        }
                    }

                    oldMailNum.addAll(updateMailNum);
                }
            }

            updateMailNum = null;
            conditionList = null;
        }

        if (!labelMap.isEmpty()) {
            Iterator<Integer> wlbSidList = labelMap.keySet().iterator();
            int wlbSid = 0;
            while (wlbSidList.hasNext()) {
                wlbSid = wlbSidList.next();
                webmailDao.insertLabelRelation(wacSid, labelMap.get(wlbSid),
                        wlbSid);
            }

            wlbSidList = null;
        }

        labelMap = null;
        oldMailNum = null;
        filterList = null;
        webmailDao = null;
        mailNum = null;

        log__.info("フィルタリング完了までの経過時間" + "[wacSid " + wacSid + "]" + " = "
                + (System.currentTimeMillis() - time));
        log__.info("フィルタリング 終了");
    }

    /**
     * <br>[機  能] フィルタリング転送 ログ出力内容を生成をします。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param domain ドメイン
     * @param msgResource MessageResources
     * @param fwAddressList 転送先メールアドレスリスト
     * @param wacSid アカウントSID
     * @param mailData 送信メール情報
     * @param filterData フィルター情報
     * @param conditionList フィルター条件情報
     * @return ログ出力内容
     * @throws GSException GS用汎実行例外
     * @throws SQLException SQL実行例外
     */
    private String __createFilterTransferLogValue(Connection con, String domain,
            MessageResources msgResource, List<String> fwAddressList,
            int wacSid, MailData mailData, MailFilterModel filterData,
            List<MailFilterConditionModel> conditionList)
            throws GSException, SQLException {
        StringBuilder sb = new StringBuilder();
        //転送メール情報
        sb = __createTransfeMailLogValue(con, msgResource, wacSid, mailData, sb);
        //フィルタリング情報
        sb = __createFilterLogValue(con, msgResource, fwAddressList, filterData, conditionList, sb);

        return sb.toString();
    }

    /**
     * <br>[機  能] フィルタリング転送 ログ出力内容(フィルタ情報)を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param msgResource MessageResources
     * @param fwAddressList 転送先アドレスリスト
     * @param filterData フィルター情報
     * @param conditionList フィルター条件情報
     * @param sb ログ出力内容
     * @return ログ出力内容
     * @throws SQLException SQL実行例外
     */
    private StringBuilder __createFilterLogValue(Connection con, MessageResources msgResource,
            List<String> fwAddressList, MailFilterModel filterData,
            List<MailFilterConditionModel> conditionList, StringBuilder sb) throws SQLException {

        //項目
        GsMessage gsMsg = new GsMessage();
        String filterItem = gsMsg.getMessage(msgResource, "wml.144"); //フィルター
        String filterNameItem = gsMsg.getMessage(msgResource, "wml.84"); //フィルター名
        String fwAddlressItem = gsMsg.getMessage(msgResource, "sml.81"); //転送先メールアドレス

        //フィルタ名取得
        WmlFilterDao wmlFilterDao = new WmlFilterDao(con);
        String filterName = wmlFilterDao.getWmlFilterName(filterData.getWftSid());

        sb.append("\r\n");
        sb.append("【 ");
        sb.append(filterItem);
        sb.append(" 】 ");
        sb.append("\r\n");

        //フィルタ名
        sb.append("[");
        sb.append(filterNameItem);
        sb.append("]");
        sb.append(filterName);
        sb.append("\r\n");

        //転送先メールアドレス
        sb.append("[");
        sb.append(fwAddlressItem);
        sb.append("] ");
        for (int i = 0; i < fwAddressList.size(); i++) {
            sb.append(fwAddressList.get(i));
            if (i != fwAddressList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("\r\n");

        return sb;
    }

    /**
     * <br>[機  能] フィルタリング転送 ログ出力内容(メール情報)を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param msgResource MessageResources
     * @param wacSid アカウントSID
     * @param mailData 送信メール情報
     * @param sb ログ出力内容
     * @return ログ出力内容
     * @throws SQLException SQL実行例外
     */
    private StringBuilder __createTransfeMailLogValue(Connection con, MessageResources msgResource,
            int wacSid, MailData mailData, StringBuilder sb) throws SQLException {

        //出力項目
        GsMessage gsMsg = new GsMessage();
        String mailDetailItem = gsMsg.getMessage(msgResource, "wml.wml080.03"); //メール詳細
        String toAccountNameItem = gsMsg.getMessage(msgResource, "wml.96"); //アカウント名
        String subjectItem = gsMsg.getMessage(msgResource, "cmn.subject"); //件名
        String senderItem = gsMsg.getMessage(msgResource, "cmn.sendfrom"); //差出人
        String sendDateItem = gsMsg.getMessage(msgResource, "cmn.send.date"); //送信日時
        String toMailAddressItem = gsMsg.getMessage(msgResource, "cmn.from"); //宛先

        //宛先アカウント名・メールアドレス取得
        WmlAccountDao wmlAccountDao = new WmlAccountDao(con);
        WmlAccountModel wmlAccountModel = wmlAccountDao.select(wacSid);
        String wacAccountName = wmlAccountModel.getWacName();
        String wacAddress = wmlAccountModel.getWacAddress();

        //送信日時 整形
        String dispSendMailDate = UDateUtil.getSlashYYMD(mailData.getSdate()) + " "
                + UDateUtil.getSeparateHMS(mailData.getSdate());

        sb.append("【 ");
        sb.append(mailDetailItem);
        sb.append(" 】 ");
        sb.append("\r\n");

        //宛先アカウント名
        sb.append("[");
        sb.append(toAccountNameItem);
        sb.append("] ");
        sb.append(wacAccountName);
        sb.append("\r\n");

        //件名
        sb.append("[");
        sb.append(subjectItem);
        sb.append("] ");
        sb.append(mailData.getTitle());
        sb.append("\r\n");

        //差出人
        sb.append("[");
        sb.append(senderItem);
        sb.append("] ");
        sb.append(mailData.getFrom());
        sb.append("\r\n");

        //送信日時
        sb.append("[");
        sb.append(sendDateItem);
        sb.append("] ");
        sb.append(dispSendMailDate);
        sb.append("\r\n");

        //宛先メールアドレス
        sb.append("[");
        sb.append(toMailAddressItem);
        sb.append("] ");
        sb.append(wacAddress);
        sb.append("\r\n");

        return sb;
    }


    /**
     * <br>[機 能] 指定したアカウントの受信済みメールUIDを全て削除する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param wacSid アカウントSID
     * @param accountString アカウント文字列
     * @throws SQLException SQL実行時例外
     */
    public void deleteUidlAll(int wacSid, String accountString)
            throws SQLException {
        WmlUidlDao uidlDao = new WmlUidlDao(receiveMdl__.getCon());
        uidlDao.delete(wacSid, accountString);
    }

    /**
     * <br>[機 能] Stringのディープコピーを行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param value String
     * @return String
     */
    private String __cloneString(String value) {
        if (value != null) {
            return new String(value);
        }

        return null;
    }

    /**
     * <br>[機 能] メールの送信を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param con コネクション
     * @param domain ドメイン
     * @param webmailDao WebmailDao
     * @param mailNumList メッセージ番号
     * @param forwardAddressList 転送先アドレス
     * @param msgResource MessageResources
     * @param fwTextList 転送先制限文字列
     * @param fwLimit メール転送制限区分
     * @return true: 送信成功、false: 送信失敗
     * @throws SQLException メール情報の取得に失敗
     * @throws TempFileException 添付ファイルの操作に失敗
     */
    private boolean __sendMail(Connection con, String domain,
            WebmailDao webmailDao, List<Long> mailNumList,
            List<String> forwardAddressList, MessageResources msgResource,
            List<String> fwTextList, int fwLimit) throws SQLException, TempFileException {
        // 転送先アドレスを転送可能なアドレスのみに選別する
        List<String> sendAddressList = new ArrayList<String>();

        //メール転送制限 転送制限ありの場合
        if (fwLimit == GSConstWebmail.WAD_SEND_LIMIT_LIMITED) {
            for (String forwardAddress : forwardAddressList) {
                for (String fwText : fwTextList) {
                    if (forwardAddress.indexOf(fwText) >= 0) {
                        sendAddressList.add(forwardAddress);
                        break;
                    }
                }
                log__.info("***** 転送制限範囲 TO :" + forwardAddress);
            }
        } else {
            sendAddressList.addAll(forwardAddressList);
        }

        // 転送先アドレスが存在しない場合、処理を終了する
        if (sendAddressList.isEmpty()) {
            sendAddressList = null;
            return false;
        }

        // メール情報を取得
        List<MailData> mailDataList = webmailDao.getMailData(mailNumList);
        WmlSmtpSender sender = null;

        try {
            sender = new WmlSmtpSender();

            SmtpModel smtpData = new SmtpModel();
            smtpData.setSendServer(receiveServerModel__.getSendServer());
            smtpData.setSendPort(receiveServerModel__.getSendServerPort());
            smtpData.setSmtpAuth(receiveServerModel__.isSendServerAuth());
            smtpData.setSendUser(receiveServerModel__.getSendServerUser());
            smtpData.setSendPassword(receiveServerModel__
                    .getSendServerPassword());
            smtpData.setSendEncrypt(receiveServerModel__.getSendServerEncrypt());
            smtpData.setEncode(receiveServerModel__.getSendEncode());
            smtpData.setPopBeforeSmtp(receiveServerModel__.isPopToSmtp());
            smtpData.setPopServer(receiveServerModel__.getHost());
            smtpData.setPopServerPort(receiveServerModel__.getPort());
            smtpData.setPopServerUser(receiveServerModel__.getUser());
            smtpData.setPopServerPassword(receiveServerModel__.getPassword());
            smtpData.setPopServerEncrypt(receiveServerModel__.getReceiveEncrypt());

            smtpData.setAuthType(receiveServerModel__.getAuthType());
            smtpData.setAccessToken(receiveServerModel__.getAccessToken());

            if (log__.isDebugEnabled()) {
                log__.debug("***** SMTP Server :"
                        + receiveServerModel__.getSendServer());
                log__.debug("***** SMTP Port :"
                        + receiveServerModel__.getSendServerPort());
                log__.debug("***** SMTP Auth :"
                        + receiveServerModel__.isSendServerAuth());
                log__.debug("***** SMTP User :"
                        + receiveServerModel__.getSendServerUser());
                log__.debug("***** SMTP Pass :"
                        + receiveServerModel__.getSendServerPassword());
                log__.debug("***** SMTP Encrypt :"
                        + receiveServerModel__.getSendServerEncrypt());
                log__.debug("***** Send Encode :"
                        + receiveServerModel__.getSendEncode());
                log__.debug("***** PopBefore SMTPr :"
                        + receiveServerModel__.isPopToSmtp());
                log__.debug("***** POP Server :"
                        + receiveServerModel__.getHost());
                log__.debug("***** POP Port :" + receiveServerModel__.getPort());
                log__.debug("***** POP User :" + receiveServerModel__.getUser());
                log__.debug("***** POP Password :"
                        + receiveServerModel__.getPassword());
                log__.debug("***** POP Encrypt :" + receiveServerModel__.getReceiveEncrypt());

                log__.debug("***** Auth Type :" + receiveServerModel__.getAuthType());
                log__.debug("***** token :" + !StringUtil.isNullZeroString(
                                                receiveServerModel__.getAccessToken()));
            }
            
            try {
                sender.connect(smtpData);
            } catch (MessagingException e) {
                log__.warn("送信メールサーバの接続に失敗 アカウント["
                            + receiveServerModel__.getAccountName() + "]", e);
                sender.disConnect();
                return false;
            }

            WmlBiz wmlBiz = new WmlBiz();
            List<WmlMailFileModel> fileList = null;
            WmlMailFileModel fileMdl = null;
            SmtpSendModel sendMailData = null;
            for (MailData mailData : mailDataList) {
                if (mailData.getTempFileList() != null
                        && !mailData.getTempFileList().isEmpty()) {
                    fileList = new ArrayList<WmlMailFileModel>();

                    ITempFileUtil tempUtil = (ITempFileUtil) GroupSession
                            .getContext().get(GSContext.TEMP_FILE_UTIL);
                    for (MailTempFileModel tmpFileMdl : mailData
                            .getTempFileList()) {
                        fileMdl = new WmlMailFileModel();
                        fileMdl.setFileName(tmpFileMdl.getFileName());
                        WmlTempfileModel wmlTmpFileMdl = wmlBiz
                                .getTempFileData(con, mailData.getMailNum(),
                                        tmpFileMdl.getBinSid(), domain);
                        fileMdl.setFilePath(tempUtil.getDownloadFileForWebmail(
                                wmlTmpFileMdl, receiveMdl__.getAppRootPath())
                                .getPath());
                        fileMdl.setContentType(ContentType
                                .getContentType(tmpFileMdl.getFileName()));
                        fileList.add(fileMdl);
                    }
                }

                sendMailData = new SmtpSendModel();
                sendMailData.setSubject("Fw: " + mailData.getTitle());
                sendMailData.setFrom(receiveServerModel__
                        .getAccountMailAddress());
                sendMailData.setCc(null);
                sendMailData.setBcc(null);
                sendMailData.setBody(NullDefault.getString(mailData.getBody(),
                        ""));
                sendMailData.setTempFileList(fileList);
                sendMailData.setHtmlMail(false);

                // 送信メールサイズチェック
                boolean isMailSizeOk;
                try {
                    isMailSizeOk = wmlBiz.checkSendmailSize(con,
                    (int) sendMailData.getWacSid(), sendMailData,
                    receiveServerModel__.getSendEncode());
                } catch (UnsupportedEncodingException e) {
                    //転送するメール内容が不正
                    log__.info("***** 転送するメール内容が不正 ", e);
                    continue;
                }
                if (isMailSizeOk) {
                    try {
                        for (String forwardAddress : sendAddressList) {
                            sendMailData.setTo(forwardAddress);
                            try {
                                sender.send(sendMailData);
                            } catch (MessagingException e) {
                                //メール送信に失敗
                                log__.info("***** メール送信に失敗 TO :" + forwardAddress , e);
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        //転送するメール内容が不正
                        log__.info("***** 転送するメール内容が不正 ", e);
                        continue;
                    }
                } else {
                    WmlMaildataModel receiveMailData = new WmlMaildataModel();
                    receiveMailData.setWmdTitle(mailData.getTitle());
                    receiveMailData.setWmdSdate(mailData.getSdate());
                    GsMessage gsMsg = new GsMessage();
                    String sendMailTitle = gsMsg.getMessage(msgResource,
                            "wml.243");
                    try {
                        wmlBiz.sendSmail(con, sendMailTitle, receiveMailData,
                                "info_sizeover.txt",
                                receiveServerModel__.getWacSid(),
                                receiveMdl__.getAppRootPath(), domain);
                    } catch (Throwable th) {
                        log__.error(
                                "送信失敗時のショートメール通知に失敗 : " + mailData.getMailNum(),
                                th);
                    }
                }

                fileList = null;
                sendMailData = null;
            }

        } finally {
            sendAddressList = null;
            if (sender != null) {
                sender.disConnect();
            }
        }

        return true;
    }

    /**
     * @return useDiskSize
     */
    public long getUseDiskSize() {
        return useDiskSize__;
    }

    /**
     * @param useDiskSize 設定する useDiskSize
     */
    public void setUseDiskSize(long useDiskSize) {
        useDiskSize__ = useDiskSize;
    }

    /**
     * @return mailSidList
     */
    public List<Long> getMailSidList() {
        return mailSidList__;
    }

    /**
     * <br>[機 能] アカウントディスク使用量の初期化を行う
     * <br>[解 説]
     * <br>[備 考]
     *
     * @throws SQLException SQL実行時例外
     */
    public void initUseDiskSize() throws SQLException {
        WmlBiz wmlBiz = new WmlBiz();
        setUseDiskSize(wmlBiz.getUseDiskSize(receiveMdl__.getCon(),
                receiveMdl__.getWacSid()));
        wmlBiz = null;
    }

    /**
     * <br>[機 能] アカウント_受信状況情報を取得する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @return アカウント_受信状況情報
     * @throws SQLException SQL実行時例外
     */
    public WmlAccountRcvsvrModel getRsvServerData() throws SQLException {
        WmlAccountRcvsvrDao dao = new WmlAccountRcvsvrDao(receiveMdl__.getCon());
        return dao.select(receiveMdl__.getWacSid());
    }

    /**
     * <br>[機 能] アカウント_受信サーバ情報を登録する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param count メール件数
     * @param size 使用容量
     * @throws SQLException SQL実行時例外
     */
    public void insertRsvServerData(int count, int size) throws SQLException {
        WmlAccountRcvsvrModel model = new WmlAccountRcvsvrModel();
        model.setWacSid(receiveMdl__.getWacSid());
        model.setWrsReceiveCount(count);
        model.setWrsReceiveSize(size);
        model.setWrsEdate(new UDate());

        WmlAccountRcvsvrDao dao = new WmlAccountRcvsvrDao(receiveMdl__.getCon());
        dao.delete(model.getWacSid());
        dao.insert(model);
        model = null;
        dao = null;
    }

    /**
     * <br>[機 能] アカウント_受信サーバ情報の更新日を更新する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param ds DataSource
     * @throws Exception コネクション再取得に失敗
     */
    public void updateRsvServerDate(DataSource ds) throws Exception {
        WmlAccountRcvsvrDao dao = new WmlAccountRcvsvrDao(receiveMdl__.getCon());
        dao.updateEdate(receiveMdl__.getWacSid(), new UDate());

        // コネクションの解放 & 再取得
        receiveMdl__.getCon().commit();
        reConnect(ds);
        dao = null;
    }

    /**
     * <br>[機 能] メール添付ファイル(実体)の削除を行う
     * <br>[解 説] <br>
     * <br>[備 考]
     */
    private void __deleteTempFile() {

        try {
            if (!binSidList__.isEmpty()) {
                ((ITempFileUtil) GroupSession.getContext().get(
                        GSContext.TEMP_FILE_UTIL)).deleteFileForWebmail(
                        receiveMdl__.getCon(), receiveMdl__.getAppRootPath(),
                        binSidList__);
            }
        } catch (Throwable e) {
            log__.error("メール添付ファイル(実体)の削除に失敗", e);
        }
    }

    /**
     * <br>[機 能] 受信対象とするメールのメッセージIDを取得する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param folder Folder
     * @param messages メール情報
     * @param model 接続情報
     * @param ds データソース
     * @param lindexPath Luceneインデックスの作成先
     * @return メッセージID
     * @throws SQLException SQL実行時例外
     * @throws MessagingException メッセージIDの取得に失敗
     * @throws IOException IOエラー
     */
    public List<String> getReceiveMessageId(Folder folder,
            Message[] messages, WmlReceiveServerModel model, DataSource ds,
            String lindexPath) throws SQLException, MessagingException,
            IOException {

        List<String> mlist = null;
        long rtargettime = System.currentTimeMillis();
        WmlBiz wmlBiz = new WmlBiz();

        rtargettime = System.currentTimeMillis();
        mlist = wmlBiz.getReceiveMessageId(receiveMdl__.getCon(), folder,
                messages, model.getWacSid(), model.getAccountString(),
                model.isReReceive(), model.getMaxReceiveMailCount(),
                model.getReceiveType());
        log__.debug("受信対象UIDリスト取得時間(H2): "
                + (System.currentTimeMillis() - rtargettime) + " :WACSID"
                + model.getWacSid());
        return mlist;
    }

    /**
     * <br>[機  能] ディスク容量を超えた時のショートメール通知を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param appRootPath アプリケーションルートパス
     * @param msgResource MessageResources
     * @param domain ドメイン
     * @param rcvFlg 通知受け取り済みフラグ
     * @throws SQLException SQL実行時例外
     * @throws Exception ショートメール送信に失敗
     */
    public void sendDiskLimitMail(int wacSid, String appRootPath,
                                                MessageResources msgResource,
                                                String domain, int rcvFlg)
    throws SQLException, Exception {

        // 同日中すでに通知を送っていた場合、通知は行わない
        if (rcvFlg == GSConstWebmail.WML_FAILEDNOTICE_RECEIVED) {
            return;
        }

        // ショートメール通知
        WmlBiz wmlBiz = new WmlBiz();
        GsMessage gsMsg = new GsMessage();
        String sendMailTitle = gsMsg.getMessage(msgResource,
                "wml.249");
        Map<String, String> bodyParam = new HashMap<String, String>();
        bodyParam.put("ACCOUNT_NAME", wmlBiz.getAccountName(
                receiveMdl__.getCon(), wacSid));
        bodyParam.put("DATE", WmlBiz.getWmlViewDate(new UDate()));

        if (StringUtil.isNullZeroString(appRootPath)) {
            appRootPath = receiveMdl__.getAppRootPath();
        }

        wmlBiz.sendSmail(receiveMdl__.getCon(), sendMailTitle,
                bodyParam, "info_sizeover_receive.txt",
                wacSid, appRootPath,
                domain);
    }

    /**
     * <br>[機  能]  メール情報登録時に使用する各種情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メール情報登録時に使用する各種情報
     */
    protected Pop3ReceiveModel _getReceiveModel() {
        return receiveMdl__;
    }

    /**
     * <br>[機 能] 受信メール ヘッダー部のサイズ(ディスクサイズ)を取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param msg 受信メール
     * @return サイズ
     * @throws MessagingException ヘッダー部のサイズ取得に失敗
     */
    private int __getHeaderSize(MimeMessage msg) throws MessagingException {
        @SuppressWarnings("all")
        Enumeration headEnum = msg.getAllHeaderLines();
        int headerSize = 0;
        while (headEnum.hasMoreElements()) {
            String head = (String) headEnum.nextElement();
            headerSize += head.length() + 2;
        }
        return headerSize;
    }
    /**
     *
     * <br>[機  能] 特定状況下でファイル名をUTF-8でデコードしなおして返す
     * <br>[解  説] MIMEヘッダーのファイル名にマルチバイト文字を使用するRFC規約違反な一部のメーラー対応
     * <br>[備  考] UTF-8以外のマルチバイト文字を使用された場合には非対応
     * @param fileName ファイル名
     * @param part MimePart
     * @return 必要に応じてデコードしなおした文字列。
     *          ヘッダにエンコードされていないマルチバイト文字がなければ、入力されたファイル名を返す
     * @throws MessagingException メッセージ解析例外
     * @throws UnsupportedEncodingException 文字コード不正例外
     */
    private String __repareMultiByteFilename(
            String fileName, Part part
            ) throws MessagingException, UnsupportedEncodingException {
        String ret = fileName;
        String cdsName = null;
        String ctsName = null;
        String[] cdss = part.getHeader("Content-Disposition");
        String cds = null;
        if (cdss != null && cdss.length > 0) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String cd : cdss) {
                if (!first) {
                    sb.append(";");
                }
                first = false;
                sb.append(cd);
            }
            cds = sb.toString();
        }
        if (cds != null) {
            // Parse the header ..
            ContentDisposition cd = new ContentDisposition(cds);
            cdsName = cd.getParameter("filename");
        }
        String cts = part.getContentType();
        if (cts != null) {
            javax.mail.internet.ContentType ct = new javax.mail.internet.ContentType(cts);
            ctsName = ct.getParameter("name");
        }
        boolean nonAscii = false;
        if (fileName.equals(cdsName)) {
            //ヘッダがRFC規約違反(Ascii外文字の使用)
            if (!__isAllAsciiAndSysKey(cds)) {
                nonAscii = true;

            }
        } else if (fileName.equals(ctsName)) {
            //ヘッダがRFC規約違反(Ascii外文字の使用)
            if (!__isAllAsciiAndSysKey(cts)) {
                nonAscii = true;
            }
        }
        if (nonAscii) {
            ret =  new String(fileName.getBytes(Encoding.ISO8859_1), Encoding.UTF_8);
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] すべてAscii文字（制御コード含む）か確認
     * <br>[解  説]
     * <br>[備  考]
     * @param name チェック対象文字列
     * @return true:すべてAscii false:Ascii以外の文字を含む
     */
   private  boolean __isAllAsciiAndSysKey(String name) {
       for (int i = 0; i < name.length(); i++) {
           if (name.charAt(i) > StringUtil.LATIN_HALF_MAX) {
               return false;
           }
       }
       return true;
   }

}