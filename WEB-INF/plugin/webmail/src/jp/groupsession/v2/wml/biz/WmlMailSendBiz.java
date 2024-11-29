package jp.groupsession.v2.wml.biz;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.RandomPassword;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrLangDao;
import jp.groupsession.v2.cmn.dao.base.WmlTempfileDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.OauthMailServerModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrLangModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlHeaderDataDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelRelationDao;
import jp.groupsession.v2.wml.dao.base.WmlMailBodyDao;
import jp.groupsession.v2.wml.dao.base.WmlMailSendplanDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.dao.base.WmlSendaddressDao;
import jp.groupsession.v2.wml.exception.WmlDiskSizeOverException;
import jp.groupsession.v2.wml.exception.WmlMailServerConnectException;
import jp.groupsession.v2.wml.exception.WmlMailSizeOverException;
import jp.groupsession.v2.wml.exception.WmlTempDirNoneException;
import jp.groupsession.v2.wml.exception.WmlTempFileNameException;
import jp.groupsession.v2.wml.model.WmlMailModel;
import jp.groupsession.v2.wml.model.WmlSendResultModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;
import jp.groupsession.v2.wml.smtp.WmlSmtpSendBiz;
import jp.groupsession.v2.wml.smtp.WmlSmtpSender;
import jp.groupsession.v2.wml.smtp.model.SmtpModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;
import jp.groupsession.v2.wml.wml010.Wml010Dao;

public class WmlMailSendBiz {

    /** メールヘッダ Reference 1行における上限文字数(1行目) */
    public static final int REFERENCE_MAXLENGTH_FIRST = 984;
    /** メールヘッダ Reference 1行における上限文字数(2行目以降) */
    public static final int REFERENCE_MAXLENGTH = 998;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailSendBiz.class);
    /** メールパラメータモデル */
    private MailSendModel paramMdl__;

    /** 送信用添付ファイルリスト */
    List<WmlMailFileModel> tempFileList__;
    
	private static class MailSendModel extends AbstractModel {

		/** 宛先 */
		private String to__ = "";
		/** CC */
		private String cc__ = "";
		/** BCC */
		private String bcc__ = "";
		/** 件名 */
		private String subject__ = "";
		/** 本文 */
		private String content__ = "";

		/** アカウントSID */
		private int wacSid__ = 0;
		/** 返信/編集元メッセージ番号 */
		private long sendMessageNum__ = 0;
		/** 送信種別 */
		private int sendMailType__ = GSConstWebmail.SEND_TYPE_NORMAL;
		/** HTMLメールフラグ */
		private boolean htmlMail__ = false;

		/** 後で送信 */
		private boolean timeSent__ = false;
		/** 後で送信 送信日 */
		private UDate sendPlanDate__ = null;

		/** 送信メール情報 */
		private SmtpSendModel sendData__ = null;
		/** 添付ファイルの圧縮 */
		private boolean archiveMail__ = false;
		/** 添付ファイルの圧縮 パスワード */
		private String archivePassword__ = null;
		/** 添付ファイルの圧縮 画面指定 */
		private int compressFileType__ = 0;

		public String getTo() {
			return to__;
		}

		public String getCc() {
			return cc__;
		}

		public String getBcc() {
			return bcc__;
		}

		public String getSubject() {
			return subject__;
		}

		public String getContent() {
			return content__;
		}

		public int getWacSid() {
			return wacSid__;
		}

		public long getSendMessageNum() {
			return sendMessageNum__;
		}

		public int getSendMailType() {
			return sendMailType__;
		}

		public boolean isHtmlMail() {
			return htmlMail__;
		}

		public boolean isTimeSent() {
			return timeSent__;
		}

		public UDate getSendPlanDate() {
			return sendPlanDate__;
		}

		public SmtpSendModel getSendData() {
			return sendData__;
		}

		public boolean isArchiveMail() {
			return archiveMail__;
		}

		public String getArchivePassword() {
			return archivePassword__;
		}

		public int getCompressFileType() {
			return compressFileType__;
		}
	}
    /**
     * <br>[機  能] ビルダークラス
     * <br>[解  説] ビルダークラスとプライベートコンストラクタによって不用意な継承による悪用を防ぐ
     * <br>[備  考]
     * @param <E> ソート対象モデル
     */
    public static class Builder {

        /** 宛先 */
        private String to__ = "";
        /** CC */
        private String cc__ = "";
        /** BCC */
        private String bcc__ = "";
        /** 件名 */
        private String subject__ = "";
        /** 本文 */
        private String content__ = "";

        /** アカウントSID */
        private int wacSid__ = 0;
        /** 返信/編集元メッセージ番号 */
        private long sendMessageNum__ = 0;
        /** 送信種別 */
        private int sendMailType__ = GSConstWebmail.SEND_TYPE_NORMAL;
        /** HTMLメールフラグ */
        private boolean htmlMail__ = false;

        /** 後で送信 */
        private int timeSentType__ = GSConstWebmail.TIMESENT_NORMAL;
        /** 後で送信 送信日 */
        private UDate sendPlanDate__ = null;

        /** 添付ファイルの圧縮 画面指定 */
        private int compressFileType__ = 0;
        /**
         * デフォルトコンストラクタ
         */
        private Builder() {

        }

        public Builder setTo(String to__) {
            this.to__ = to__;
            return this;
        }

        public Builder setCc(String cc__) {
            this.cc__ = cc__;
            return this;
        }

        public Builder setBcc(String bcc__) {
            this.bcc__ = bcc__;
            return this;
        }

        public Builder setSubject(String subject__) {
            this.subject__ = subject__;
            return this;
        }

        public Builder setContent(String content__) {
            this.content__ = content__;
            return this;
        }

        public Builder setWacSid(int wacSid__) {
            this.wacSid__ = wacSid__;
            return this;
        }

        public Builder setSendMessageNum(long sendMessageNum__) {
            this.sendMessageNum__ = sendMessageNum__;
            return this;
        }

        public Builder setSendMailType(int sendMailType__) {
            this.sendMailType__ = sendMailType__;
            return this;
        }

        public Builder setHtmlMail(boolean htmlMail__) {
            this.htmlMail__ = htmlMail__;
            return this;
        }

        public Builder setTimeSentType(int timeSentType__) {
            this.timeSentType__ = timeSentType__;
            return this;
        }

        public Builder setSendPlanDate(UDate sendPlanDate__) {
            this.sendPlanDate__ = sendPlanDate__;
            return this;
        }

        public Builder setCompressFileType(int compressFileType__) {
            this.compressFileType__ = compressFileType__;
            return this;
        }

        /**
         *
         * <br>[機  能] WEBメール 送信，保存用ビジネスロジックインスタンスを生成
         * <br>[解  説]
         * <br>[備  考]
         * @return WEBメール 送信，保存用ビジネスロジックインスタンス
         * @throws SQLException SQL実行時例外
         * @throws IOToolsException 添付ファイル関連の例外
         * @throws IOException 添付ファイル関連の例外
         */
        public WmlMailSendBiz build(Connection con, GSContext gsContext,
            String appRootPath, String tempDir,
            int userSid) throws SQLException, IOToolsException, IOException {

            MailSendModel ret = new MailSendModel();
            ret.to__ = to__;
            ret.cc__ = cc__;
            ret.bcc__ = bcc__;
            ret.subject__ = subject__;
            ret.content__ = content__;
            ret.wacSid__ = wacSid__;
            //新規送信以外の場合は編集元メールを設定
            if (sendMailType__ != GSConstWebmail.SEND_TYPE_NORMAL) {
                ret.sendMessageNum__ = sendMessageNum__;
            }
            
            ret.sendMailType__ = sendMailType__;
            ret.htmlMail__ = htmlMail__;
            if (timeSentType__ == GSConstWebmail.TIMESENT_FUTURE) {
                //予約送信の場合は、指定された日時を設定
                ret.timeSent__ = true;
                ret.sendPlanDate__ = sendPlanDate__;
            } else {
                WmlBiz wmlBiz = new WmlBiz();
                boolean timeSentInput = wmlBiz.isTimeSentInput(con, wacSid__);
                if (timeSentInput && timeSentType__ == GSConstWebmail.TIMESENT_AFTER) {
                    //時間差送信が作成時に選択可能になっており、作成時に時間差送信が選択されている
                    ret.timeSent__ = true;
                } else {
                    //基本設定にて"管理者が設定する"が選択されており、かつ時間差送信が有効になっているか
                    ret.timeSent__ = wmlBiz.isTimeSent(con, wacSid__);
                }

                if (ret.timeSent__) {
                    //現在日時より5分後に送信する
                    UDate sendDate = new UDate();
                    sendDate.addMinute(5);
                    ret.sendPlanDate__ = sendDate;
                }
            }

            boolean compressFileInput = false;
            WmlAdmConfDao wacAdmConfDao = new WmlAdmConfDao(con);
            WmlAdmConfModel admConfMdl = wacAdmConfDao.selectAdmData();
            boolean admConfFlg = (admConfMdl != null
                && admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN);

            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(wacSid__);

            if (admConfFlg) {
                //基本設定で"管理者が設定する"が選択されており、自動圧縮が作成時に選択するになっているか
                compressFileInput = 
                    admConfMdl.getWadCompressFile() == GSConstWebmail.WAD_COMPRESS_FILE_INPUT;
            } else {
                //基本設定で"各アカウントが設定する"が選択されており、アカウント設定にて自動圧縮が作成時に選択するになっているか
                compressFileInput
                = accountData.getWacCompressFile() == GSConstWebmail.WAD_COMPRESS_FILE_INPUT;
            }

            if (compressFileInput) {
                //画面からの指定
                ret.compressFileType__ = compressFileType__;
            } else {
                ret.compressFileType__ = GSConstWebmail.WSP_COMPRESS_FILE_NOSET;
            }

            WmlBiz wmlBiz = new WmlBiz();
            SmtpSendModel sendData = new SmtpSendModel();
            sendData.setCon(con);
            sendData.setWdrSid(wmlBiz.getDirectorySid(
                con, wacSid__, GSConstWebmail.DIR_TYPE_SENDED));
            sendData.setGsContext(gsContext);
            sendData.setUserSid(userSid);
            sendData.setHtmlMail(ret.isHtmlMail());
            sendData.setSubject(ret.getSubject());
            sendData.setFrom(accountData.getWacAddress());

            //送信先(宛先, CC, BCC)
            __bccConvert(admConfMdl, ret, sendData);

            sendData.setTimeSent(ret.isTimeSent());
            sendData.setSendPlanDate(ret.getSendPlanDate());

            String body = ret.getContent();
            String sendMailEncode = WmlBiz.getSendEncode(accountData);

            if (sendData.isHtmlMail()) {
                body = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
                        + "<html>"
                        + "<head>"
                        + "<meta content=\"text/html; charset=" + sendMailEncode
                        + "\" http-equiv=\"Content-Type\">"
                        + "</head>"
                        + "<body>"
                        + body
                        + "</body>"
                        + "</html>";
            }
            sendData.setBody(body);

            Wml010Dao dao010 = new Wml010Dao(con);
            long mailNum = ret.getSendMessageNum();
            if (ret.getSendMailType() == GSConstWebmail.SEND_TYPE_REPLY
                    || ret.getSendMailType() == GSConstWebmail.SEND_TYPE_REPLY_ALL) {

                List<String> refMsgKey = dao010.getHeaderValue(mailNum, "Message-ID");
                if (refMsgKey != null && !refMsgKey.isEmpty()) {
                    String reference = "";
                    StringBuilder lineStr = new StringBuilder();
                    int maxLength = REFERENCE_MAXLENGTH_FIRST;
                    List<String> referenceList = dao010.getHeaderValue(mailNum, "References");

                    if (referenceList != null && !referenceList.isEmpty()) {
                        for (String beforeReference : referenceList) {
                            if ((lineStr.length() + beforeReference.length()) > maxLength) {
                                reference += lineStr.toString();
                                reference += "\n";
                                maxLength = REFERENCE_MAXLENGTH;
                                lineStr = new StringBuilder();
                            }
                            if (lineStr.length() > 0 || reference.length() > 0) {
                                lineStr.append(" ");
                            }
                            lineStr.append(beforeReference);
                        }
                    }

                    reference += lineStr.toString();
                    if ((lineStr.length() + refMsgKey.get(0).length()) > maxLength) {
                        reference += "\n";
                    }
                    if (reference.length() > 0) {
                        reference += " ";
                    }
                    reference += refMsgKey.get(0);
                    sendData.addHeaderData("References", reference);
                }
            }

            WmlTempFileBiz tempFileBiz = new WmlTempFileBiz();
            List<WmlMailFileModel> tempFileList = tempFileBiz.getTempFileList(tempDir);

            sendData.setTempFileList(tempFileList);
            ret.sendData__ = sendData;

            return new WmlMailSendBiz(ret);
        }

        /**
         * <br>[機  能] 指定したアドレスのBCC強制変換を行う
         * <br>[解  説]
         * <br>[備  考]
         * @param admConfMdl WEBメール管理者設定
         * @param mailData 送信メール情報(パラメータ)
         * @param sendData 送信メール情報
         */
        private void __bccConvert(WmlAdmConfModel admConfMdl,
        MailSendModel mailData,
        SmtpSendModel sendData) {

        String toAddress = mailData.getTo();
        String ccAddress = mailData.getCc();
        String bccAddress = mailData.getBcc();

        int bccTh = admConfMdl.getWadBccTh();
        if (admConfMdl.getWadBcc() == GSConstWebmail.WAD_BCC_CONVERSION
                && __parseSendAddress(toAddress).size()
                    + __parseSendAddress(ccAddress).size() > bccTh) {
            bccAddress = __joinAddress(toAddress, bccAddress);
            bccAddress = __joinAddress(ccAddress, bccAddress);

            toAddress = sendData.getFrom();
            ccAddress = "";
        }

        sendData.setTo(toAddress);
        sendData.setCc(ccAddress);
        sendData.setBcc(bccAddress);
        }

        /**
        * <br>[機  能] 送信メールのアドレス(宛先, CC, BCC)をparseする
        * <br>[解  説]
        * <br>[備  考]
        * @param address メールアドレス
        * @return メールアドレス
        */
        private List<String> __parseSendAddress(String address) {
        List<String> ret = new ArrayList<String>();
        if (!StringUtil.isNullZeroString(address)) {
            try {
                WmlSmtpSendBiz biz = new WmlSmtpSendBiz();
                ret = biz.getAddressList(address);
            } catch (AddressException e) {
                ret = new ArrayList<String>();
            }
        }
        return ret;
        }

        /**
        * <br>[機  能] 指定したメールアドレスを結合する
        * <br>[解  説]
        * <br>[備  考]
        * @param sendAddress メールアドレス(宛先 or CC)
        * @param bccAddress メールアドレス(BCC)
        * @return 結合したメールアドレス
        */
        private String __joinAddress(String sendAddress, String bccAddress) {
        if (!StringUtil.isNullZeroString(sendAddress)) {
            if (bccAddress.length() > 0) {
                bccAddress += ",";
            }
            bccAddress += sendAddress;
        }
        return bccAddress;
        }
        
    }    

    public static Builder builder() {
        return new Builder();
    }
    /**
     * デフォルトコンストラクタ
     * @param mdl
     */
    private WmlMailSendBiz(MailSendModel mdl) {
        paramMdl__ = mdl;
        tempFileList__ = mdl.getSendData().getTempFileList();
    }

    /**
     * <br>[機  能] メールの送信,作成を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param domain ドメイン
     * @param locale Locale
     * @return WmlSendResultModel メール送信実行結果
     * @throws WmlMailSizeOverException メールサイズ上限例外
     * @throws WmlDiskSizeOverException ディスク容量上限例外
     * @throws WmlTempDirNoneException テンポラリディレクトリが不正時例外
     * @throws WmlMailServerConnectException メールサーバ接続時例外
     * @throws WmlTempFileNameException 添付ファイル名不正時例外
     * @throws SQLException SQL実行時例外
     */
    public WmlSendResultModel executeMail(Connection con,GSContext gsContext, int userSid,
        String appRootPath, String tempDir, String domain, Locale locale)
        throws WmlMailSizeOverException, WmlDiskSizeOverException,
            WmlTempDirNoneException, WmlMailServerConnectException,
            WmlTempFileNameException, SQLException {

        //添付ファイルの圧縮
        __compressFile(con, tempDir);

        //メールサイズの確認
        checkMailSize(con, gsContext, userSid, appRootPath, tempDir, domain, locale, 0);

        //メールの送信
        WmlSendResultModel resultModel;
        if (getSendPlanDate() == null) {
            resultModel = __sendMailNow(con, gsContext, userSid, appRootPath, tempDir, domain, locale);
        } else {
            resultModel = __sendMailLater(con, gsContext, userSid, appRootPath, tempDir, domain, locale);
        }

        return resultModel;
    }
    /**
     * <br>[機  能] メールの草稿保管を行う
     * <br>[解  説] メールSIDが設定されている場合、予約送信、草稿メールは編集
     * <br>         その他のメールは新規メールとして保管される
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param domain ドメイン
     * @param locale Locale
     * @throws Exception メールの受信に失敗
     * @return WmlMailModel
     * @throws SQLException 
     * @throws WmlMailSizeOverException 
     * @throws WmlTempDirNoneException 
     * @throws WmlTempFileNameException 
     */
    public WmlSendResultModel executeDraftMail(
        Connection con,GSContext gsContext, int userSid,
        String appRootPath, String tempDir, String domain, Locale locale)
         throws WmlMailSizeOverException, WmlDiskSizeOverException, SQLException,
         TempFileException, IOException, WmlTempDirNoneException, WmlTempFileNameException {
    
        checkMailSize(con, gsContext, userSid, appRootPath, tempDir, domain, locale, 1);
        
        WmlSendResultModel resultModel;
        resultModel = __draftMail(con, gsContext, userSid, appRootPath, tempDir, domain, locale);

        return resultModel;
    }

    /**
     * <br>[機  能] テンポラリディレクトリ内の添付ファイルを圧縮する
     * <br>[解  説]
     * <br>[備  考] 時間差送信，予約送信の場合は処理を実行しない
     * @param con コネクション
     * @param tempDir テンポラリディレクトリ
     */
    private void __compressFile(Connection con, String tempDir) {

        try {
            WmlTempFileBiz tempFileBiz = new WmlTempFileBiz();
            List<WmlMailFileModel> tempFileList = tempFileBiz.getTempFileList(tempDir);

            //時間差送信,予約送信の時は実行しない
            //添付ファイルが存在しない場合は実行しない
            if (paramMdl__.getSendPlanDate() != null
                || tempFileList == null || tempFileList.isEmpty()) {
                return;
            }

            WmlAdmConfDao wacAdmConfDao = new WmlAdmConfDao(con);
            WmlAdmConfModel admConfMdl = wacAdmConfDao.selectAdmData();
            boolean admConfFlg = (admConfMdl != null
                && admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN);

            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(paramMdl__.getWacSid());

            //添付ファイル有り、かつ [添付ファイル自動圧縮 = 圧縮する]、かつ "自動圧縮しない"が未選択 の場合
            //ZIP圧縮したメールを送信する
            boolean archiveMail = false;
            String archivePassword = null;
            String zipFilePath = null;

            if (paramMdl__.getCompressFileType() != GSConstWebmail.WSP_COMPRESS_FILE_NOSET) {
                archiveMail
                = paramMdl__.getCompressFileType() == GSConstWebmail.WSP_COMPRESS_FILE_COMPRESS;
            } else {
                if (admConfFlg) {
                    archiveMail
                    = admConfMdl.getWadCompressFile()
                    == GSConstWebmail.WAD_COMPRESS_FILE_COMPRESS;
                } else {
                    archiveMail
                    = accountData.getWacCompressFile()
                    == GSConstWebmail.WAD_COMPRESS_FILE_COMPRESS;
                }
            }

            if (archiveMail) {
                //String tempDir = getSendTempDir(tempRootDir, tempDirId);
                String compressDir = tempDir + "/compress/";
                IOTools.isDirCheck(compressDir, true);

                String archiveDir = compressDir + "/archive/";
                IOTools.isDirCheck(archiveDir, true);

                ArrayList<File> sourceFileList = new ArrayList<File>();
                for (WmlMailFileModel fileData : tempFileList) {
                    File sourceFile = new File(archiveDir + fileData.getFileName());
                    IOTools.copyBinFile(fileData.getFilePath(), sourceFile.getPath());
                    sourceFileList.add(sourceFile);
                }

                try {
                    UDate now = new UDate();
                    String zipFileName = now.getTimeStamp() + "_attach.zip";
                    List<String> srcFilePathList =  new ArrayList<String>();
                    for (File file : sourceFileList) {
                        srcFilePathList.add(file.getAbsolutePath());
                    }
                    zipFilePath = compressDir + zipFileName;
                    archivePassword = RandomPassword.createPassword();

                    if (IOTools.isFileCheck(compressDir, zipFileName, false)) {
                        IOTools.deleteFile(zipFilePath);
                    }

                    ZipUtil.zipWithPass(
                        srcFilePathList.toArray(new String[0]), zipFilePath, archivePassword);

                    IOTools.deleteDir(archiveDir);
                    WmlMailFileModel archiveFileData = new WmlMailFileModel();
                    archiveFileData.setFileName(zipFileName);
                    archiveFileData.setFilePath(zipFilePath);
                    tempFileList.clear();
                    tempFileList.add(archiveFileData);
                } catch (IOException e) {
                    log__.error("送信メール 添付ファイル圧縮に失敗", e);
                    try {
                        if (zipFilePath != null) {
                            IOTools.deleteFile(zipFilePath);
                        }

                        if (compressDir != null) {
                            IOTools.deleteDir(compressDir);
                        }
                    } catch (Exception zipe) {
                    }
                    throw e;
                }
            }

            paramMdl__.sendData__.setTempFileList(tempFileList);

            //ret.sendData__ = sendData;
            paramMdl__.archiveMail__ = archiveMail;
            paramMdl__.archivePassword__ = archivePassword;

        } catch (IOToolsException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * <br>[機  能] メールサイズのチェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param context GSコンテキスト
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param domain ドメイン
     * @param locale Locale
     * @param type 送信タイプ 0:送信, 1:草稿保存
     * @throws WmlMailSizeOverException 
     * @throws WmlDiskSizeOverException 
     * @throws WmlTempDirNoneException
     */
    public void checkMailSize(Connection con, GSContext gsContext, int userSid,
        String appRootPath, String tempDir, String domain, Locale locale, int type) 
        throws WmlMailSizeOverException, WmlDiskSizeOverException, WmlTempDirNoneException, WmlTempFileNameException {
        
        if (tempDir == null || tempDir.isEmpty()) {
            // テンポラリディレクトリパスがないのでエラーを返す
            throw new WmlTempDirNoneException();
        }

        //添付ファイル名のチェック
        WmlTempFileBiz biz = new WmlTempFileBiz();
        List<WmlMailFileModel> fileList;
        try {
            fileList = biz.getTempFileList(tempDir);
            GsMessage gsMsg = new GsMessage(locale);
            for (WmlMailFileModel fileData : fileList) {
                if (fileData.getFileName().length() > GSConstWebmail.MAXLEN_WTF_FILE_NAME) {
                    
                    String message = gsMsg.getMessage("wml.323",
                            new String[] {gsMsg.getMessage("cmn.file.name"),
                               gsMsg.getMessage("wml.320"), gsMsg.getMessage("cmn.attach.file")});
                    throw new WmlTempFileNameException(message);
                }
                String extension = StringUtil.getExtension(fileData.getFileName());
                if (extension != null
                        && extension.length() > GSConstWebmail.MAXLEN_WTF_FILE_EXTENSION) {
                    String message = gsMsg.getMessage("wml.323",
                            new String[] {gsMsg.getMessage("wml.321"),
                                gsMsg.getMessage("wml.322"), gsMsg.getMessage("cmn.attach.file")});
                    throw new WmlTempFileNameException(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        WmlBiz wmlBiz = new WmlBiz();
        int sendWacSid = paramMdl__.getWacSid();

        //メールサイズのチェック
        if (!__checkSendMailSize(con, gsContext, userSid, appRootPath, tempDir, paramMdl__)) {
            try {
                String limitSize =
                (wmlBiz.getSendMailLimitSize(con, sendWacSid) / (1024 * 1024)) + "MB";
                throw new WmlMailSizeOverException(limitSize);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        //ディスクの最大容量チェック
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        try {
            WmlAdmConfModel adminMdl = adminDao.selectAdmData();
            int diskLimitSize = wmlBiz.getDiskLimitSize(con, sendWacSid, adminMdl);
            if (diskLimitSize <= 0) {
                return;
            }
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(sendWacSid);
            String sendMailEncode = WmlBiz.getSendEncode(accountData);

            boolean diskSizeResult
            = wmlBiz.checkDiskSizeLimitOnSend(con, sendWacSid,
                    paramMdl__.getSendData(),
                    sendMailEncode, diskLimitSize, 0);
            if (!diskSizeResult) {
                GsMessage gsMsg = new GsMessage(locale);
                if (type == 1) {
                    throw new WmlDiskSizeOverException(gsMsg.getMessage("wml.265"));
                } else {
                    throw new WmlDiskSizeOverException(gsMsg.getMessage("wml.264"));
                }
            }
        } catch (UnsupportedEncodingException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * <br>[機  能] 送信メールのメールサイズチェックを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param mailData 送信メール情報
     * @return エラーメッセージ
     * @throws MessagingException 
     * @throws SQLException 
     * @throws UnsupportedEncodingException 
     * @throws Exception 送信メール情報(SmtpSendModel)の作成に失敗
     */
    private boolean __checkSendMailSize(Connection con,
            GSContext gsContext, int userSid,
            String appRootPath, String tempDir, MailSendModel mailData) {

        boolean result = true;

        int sendWacSid = mailData.getWacSid();
        WmlAccountDao accountDao = new WmlAccountDao(con);
        try {
            WmlAccountModel accountData = accountDao.select(sendWacSid);
            String sendMailEncode = WmlBiz.getSendEncode(accountData);

            WmlBiz wmlBiz = new WmlBiz();
            result = wmlBiz.checkSendmailSize(con,
                    sendWacSid,
                    mailData.getSendData(),
                    sendMailEncode);
        }  catch (UnsupportedEncodingException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    /**
     * <br>[機  能] 即時送信の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param sendBiz メール送信ビジネスロジック
     * @param context GSコンテキスト
     * @param tempDir テンポラリディレクトリ
     * @return 実行結果モデル
     * @throws WmlMailSizeOverException メールサイズ上限例外
     * @throws WmlMailServerConnectException メールサーバ接続時例外
     */
    private WmlSendResultModel __sendMailNow(Connection con, GSContext gsContext,
        int userSid, String appRootPath, String tempDir, String domain, Locale locale) 
                throws WmlMailSizeOverException, WmlMailServerConnectException {

        WmlSendResultModel sendResultMdl = null;
        int sendResult = WmlSendResultModel.RESULT_SERVERERROR;
        boolean sentFlg = false;
            //メール送信の実行及び、結果の取得
            sendResultMdl = sendMail(
                con, gsContext, userSid, appRootPath, tempDir, domain, locale);

            sendResult = sendResultMdl.getResult();
            sentFlg = sendResult == WmlSendResultModel.RESULT_SUCCESS;

            if (!sentFlg) {
                //送信失敗
                if (sendResult == WmlSendResultModel.RESULT_SIZEOVER) {
                    String mailSize = Long.toString(
                        sendResultMdl.getMailMaxSize() / (1024 * 1024)) + "MB";
                    throw new WmlMailSizeOverException(mailSize);
                } else {
                    throw new WmlMailServerConnectException();
                }
            }
        
        
        return sendResultMdl;
    }

	/**
	 * <br>[機  能] メールの送信を行う
	 * <br>[解  説]
	 * <br>[備  考]
	 * @param con コネクション
	 * @param gsContext GroupSession共通情報
	 * @param userSid ユーザSID
	 * @param appRootPath アプリケーションルートパス
	 * @param tempDir テンポラリディレクトリ
	 * @param domain ドメイン
	 * @param locale ロケール
     * @return 実行結果モデル
	 * @throws WmlMailServerConnectException 
	 */
	public WmlSendResultModel sendMail(
        Connection con, GSContext gsContext, 
        int userSid, String appRootPath,
        String tempDir, String domain, Locale locale) throws WmlMailServerConnectException {

        WmlSendResultModel sendResult = new WmlSendResultModel();

        try {
            MailSendModel mailData = paramMdl__;

            int wacSid = mailData.getWacSid();
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(wacSid);

            SmtpModel smtpData = new SmtpModel();
            smtpData.setWacSid(wacSid);
            int authType = accountData.getWacAuthType();
            smtpData.setAuthType(authType);
            if (authType == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                //認証形式 = OAuth
                OAuthBiz authBiz = new OAuthBiz();
                OauthMailServerModel serverMdl
                = authBiz.getSendServerData(con, accountData.getCotSid(), appRootPath);
    
                smtpData.setAccessToken(serverMdl.getAccessToken());
                smtpData.setRefreshToken(serverMdl.getRefreshToken());
                smtpData.setClientId(serverMdl.getClientId());
                smtpData.setSecret(serverMdl.getSecret());
                smtpData.setProvider(serverMdl.getProvider());
                smtpData.setSendServer(serverMdl.getHost());
                smtpData.setSendPort(serverMdl.getPort());
    
                smtpData.setSendUser(accountData.getWacSendUser());
    
            } else {
                //認証形式 = 基本認証
                smtpData.setSendServer(accountData.getWacSendHost());
                smtpData.setSendPort(accountData.getWacSendPort());
                smtpData.setSmtpAuth(
                        accountData.getWacSmtpAuth() == GSConstWebmail.WAC_SMTPAUTH_YES);
                smtpData.setSendUser(accountData.getWacSendUser());
                smtpData.setSendPassword(accountData.getWacSendPass());
    
                smtpData.setPopBeforeSmtp(
                        accountData.getWacPopbsmtp() == GSConstWebmail.WAC_POPBSMTP_YES);
                smtpData.setSendEncrypt(accountData.getWacSendSsl());
                smtpData.setPopServer(accountData.getWacReceiveHost());
                smtpData.setPopServerPort(accountData.getWacReceivePort());
                smtpData.setPopServerUser(accountData.getWacReceiveUser());
                smtpData.setPopServerPassword(accountData.getWacReceivePass());
                smtpData.setPopServerEncrypt(accountData.getWacReceiveSsl());
            }
    
            smtpData.setEncode(WmlBiz.getSendEncode(accountData));
            smtpData.setTempFileList(tempFileList__);
    
            WmlBiz wmlBiz = new WmlBiz();
            WmlSmtpSendBiz sendBiz = new WmlSmtpSendBiz();
    
            SmtpSendModel sendData = mailData.getSendData();
            long mailNum = mailData.getSendMessageNum();
            //送信メールの上限チェック
            if (!wmlBiz.checkSendmailSize(con, wacSid, sendData, smtpData.getEncode())) {
                
                sendResult.setResult(WmlSendResultModel.RESULT_SIZEOVER);
                sendResult.setMailMaxSize(wmlBiz.getSendMailLimitSize(con, wacSid));
                sendResult.setMailSize(wmlBiz.getSendMailSize(sendData, smtpData.getEncode()));
    
                return sendResult;
            }
    
            long messageNum = 0;
            messageNum = sendBiz.sendMail(wacSid, accountData.getWacName(),
                    smtpData, sendData, domain);
            sendResult.setMailNum(messageNum);
            //戻り値が0以下の場合、送信失敗と判定
            if (messageNum <= 0) {
                sendResult.setResult(WmlSendResultModel.RESULT_SERVERERROR);
                return sendResult;
            }
    
            //自動で圧縮を行った場合、パスワードを送信する
            WmlMaildataDao mailDataDao = new WmlMaildataDao(con);
            if (mailData.isArchiveMail()) {
                SmtpSendModel archiveSendData = new SmtpSendModel();
                archiveSendData.setCon(con);
                archiveSendData.setWdrSid(sendData.getWdrSid());
                archiveSendData.setGsContext(sendData.getGsContext());
                archiveSendData.setUserSid(sendData.getUserSid());
                archiveSendData.setHtmlMail(false);
    
                //パスワード通知メールの件名を設定
                GsMessage gsMsg = new GsMessage(locale);
                String passwdSubject = NullDefault.getString(sendData.getSubject(), "");
                passwdSubject += " " + gsMsg.getMessage("cmn.password.notification");
                if (passwdSubject.length() > GSConstWebmail.MAXLEN_MAIL_SUBJECT) {
                    passwdSubject = passwdSubject.substring(0, GSConstWebmail.MAXLEN_MAIL_SUBJECT);
                }
                archiveSendData.setSubject(passwdSubject);
    
                archiveSendData.setFrom(sendData.getFrom());
                archiveSendData.setTo(sendData.getTo());
                archiveSendData.setCc(sendData.getCc());
                archiveSendData.setBcc(sendData.getBcc());
    
                String templatePath = IOTools.setEndPathChar(appRootPath);
                templatePath
                = IOTools.replaceSlashFileSep(templatePath
                        + "/WEB-INF/plugin/webmail/mail/");
    
                //添付ファイルパスワードメールのテンプレートを取得
                String lang = "JP";
                CmnUsrLangDao langDao = new CmnUsrLangDao(con);
                CmnUsrLangModel langMdl = langDao.select(userSid);
                if (langMdl != null) {
                    lang = NullDefault.getString(langMdl.getCulCountry(), "JP");
                }
                if (lang.equals("JP")) {
                    templatePath += "tempfile_compress_tsuuchi_ja.txt";
                } else {
                    templatePath += "tempfile_compress_tsuuchi_en.txt";
                }
    
                String tmpBody = IOTools.readText(templatePath, Encoding.UTF_8);
                Map<String, String> map = new HashMap<String, String>();
                map.put("PASSWD", mailData.getArchivePassword());
                map.put("FROM", NullDefault.getString(archiveSendData.getFrom(), " "));
                map.put("TO", NullDefault.getString(archiveSendData.getTo(), " "));
                map.put("CC", NullDefault.getString(archiveSendData.getCc(), " "));
                map.put("SUBJECT", NullDefault.getString(sendData.getSubject(), " "));
    
                UDate sendDate = mailDataDao.getSdate(messageNum);
                if (sendDate != null) {
                    map.put("DATE", UDateUtil.getSlashYYMD(sendDate)
                            + " " + UDateUtil.getSeparateHMS(sendDate));
                } else {
                    map.put("DATE", " ");
                }
    
                String archiveMailBody = StringUtil.merge(tmpBody, map);
                archiveMailBody = _escapeTextAreaInput(archiveMailBody);
                archiveSendData.setBody(archiveMailBody);
    
                sendBiz.sendMail(wacSid, accountData.getWacName(),
                        smtpData, archiveSendData, domain);
            }
            
            switch (mailData.getSendMailType()) {
                case GSConstWebmail.SEND_TYPE_REPLY :
                case GSConstWebmail.SEND_TYPE_REPLY_ALL :
                    mailDataDao.setReply(mailNum);
                    break;
    
                case GSConstWebmail.SEND_TYPE_FORWARD :
                    mailDataDao.setForward(mailNum);
                    break;
    
                case GSConstWebmail.SEND_TYPE_EDIT :
                    //編集元メール情報を取得
                    WmlMaildataModel beforeMailData = null;
                    if (mailNum > 0) {
                        beforeMailData = mailDataDao.select(mailNum);
                    }

                    WmlRestMailDataBiz mailDataBiz = new WmlRestMailDataBiz();
                    if (mailDataBiz.isSoukouEditMail(con, mailNum)) {
                        //編集したメールのディレクトリ種別 = 草稿 or 未送信の場合、メールを削除する
                        __deleteMailData(con, String.valueOf(wacSid), new long[]{mailNum}, userSid);
                        sendData.setSendToDraft(true);
                    }
    
                    //最初に返信 or 転送対象となったメールを返信済 or 転送済 に更新する
                    if (beforeMailData != null) {
                        long originMailNum = beforeMailData.getWmdOrigin();
                        if (originMailNum > 0) {
                            int editType = beforeMailData.getWmdEditType();
                            if (editType == GSConstWebmail.WMD_EDITFLG_REPLY) {
                                mailDataDao.setReply(originMailNum);
                            } else if (editType == GSConstWebmail.WMD_EDITFLG_FORWARD) {
                                mailDataDao.setForward(originMailNum);
                            }
                        }
                    }
                    break;
    
                default:
            }
    
            __deleteSendTempDir(tempDir);
    
            sendResult.setSendMailData(sendData);
        } catch (SQLException e) {
            //ここで発生した例外は、ハンドリングする必要がないためRuntimeExceptionとしてthrowする
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TempFileException e) {
            throw new RuntimeException(e);
        } catch (IOToolsException e) {
            throw new RuntimeException(e);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseErrorException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new WmlMailServerConnectException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

		return sendResult;
	}

    /**
     * <br>[機  能] 時間差送信, 予約送信の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param sendBiz メール送信ビジネスロジック
     * @param context GSコンテキスト
     * @param tempDir テンポラリディレクトリ
     * @throws SQLException 
     * @throws Exception 
     */
    private WmlSendResultModel __sendMailLater(Connection con,
        GSContext gsContext, int userSid,
        String appRootPath, String tempDir,
        String domain, Locale locale) throws SQLException {

        SmtpSendModel sendMailData = null;
        WmlSendResultModel resultModel = null;

        try {
            //時間差送信, 予約送信の実行及び、結果の取得
            WmlMailModel result = __saveMailData(con, gsContext, 
            userSid, tempDir, domain,
            GSConstWebmail.DIR_TYPE_NOSEND);

            resultModel = new WmlSendResultModel();
            resultModel.setMailNum(result.getMailNum());

            //ログ出力用に送信メール情報を設定
            sendMailData = new SmtpSendModel();
            sendMailData.setSubject(result.getSubject());
            sendMailData.setFrom(__joinAddress(result.getFrom()));
            sendMailData.setTo(__joinAddress(result.getTo()));
            sendMailData.setCc(__joinAddress(result.getCc()));
            sendMailData.setBcc(__joinAddress(result.getBcc()));
            sendMailData.setSendPlanDate(result.getSendPlanDate());
            resultModel.setSendMailData(sendMailData);

            //テンポラリディレクトリを削除する
            __deleteSendTempDir(tempDir);

        } catch (Exception e) {
            throw new RuntimeException("メールの送信に失敗", e);
        }

        return resultModel;
    }
    /**
     * <br>[機  能] 草稿へ保管の実行
     * <br>[解  説]
     * <br>[備  考]
     * @param con
     * @param gsContext
     * @param userSid
     * @param appRootPath
     * @param tempDir
     * @param domain
     * @param locale
     * @return
     */
    private WmlSendResultModel __draftMail(Connection con, GSContext gsContext,
            int userSid, String appRootPath, String tempDir, String domain,
            Locale locale) {
        SmtpSendModel sendMailData = null;
        WmlSendResultModel resultModel = null;

        try {
            //時間差送信, 予約送信の実行及び、結果の取得
            WmlMailModel result = __saveMailData(con, gsContext, 
            userSid, tempDir, domain,
            GSConstWebmail.DIR_TYPE_DRAFT);

            resultModel = new WmlSendResultModel();
            resultModel.setMailNum(result.getMailNum());

            //ログ出力用に送信メール情報を設定
            sendMailData = new SmtpSendModel();
            sendMailData.setSubject(result.getSubject());
            sendMailData.setFrom(__joinAddress(result.getFrom()));
            sendMailData.setTo(__joinAddress(result.getTo()));
            sendMailData.setCc(__joinAddress(result.getCc()));
            sendMailData.setBcc(__joinAddress(result.getBcc()));
            sendMailData.setSendPlanDate(result.getSendPlanDate());
            resultModel.setSendMailData(sendMailData);

            //テンポラリディレクトリを削除する
            __deleteSendTempDir(tempDir);

        } catch (Exception e) {
            throw new RuntimeException("メールの送信に失敗", e);
        }

        return resultModel;
    }

    /**
     * <br>[機  能] メールの保存を行う
     * <br>[解  説]
     * <br>[備  考] 草稿、予約送信メールの保存を行う
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param tempDir テンポラリディレクトリ
     * @param domain ドメイン情報
     * @param dirType ディレクトリ種別
     * @param sendType 送信メール表示時の操作(新規作成、返信 など)
     * @return WmlMailModel
     * @throws SQLException 
     */
    private WmlMailModel __saveMailData(Connection con,
            GSContext gsContext,
            int userSid, String tempDir, String domain,
            int dirType) throws SQLException  {

        MailSendModel mailData = paramMdl__;
        
        int sendAccount = mailData.getWacSid();
        WmlBiz wmlBiz = new WmlBiz();
        int sendType = mailData.getSendMailType();
        long wdrSid = wmlBiz.getDirectorySid(con, sendAccount, dirType);
        long beforeMailNum = mailData.getSendMessageNum();

        WmlMailModel sendData = new WmlMailModel();
        sendData.setSendDate(null);
        sendData.setSubject(mailData.getSubject());
        sendData.setContent(mailData.getContent());

        sendData.addFrom(null);
        if (dirType == GSConstWebmail.DIR_TYPE_DRAFT) {
            WmlMailModel data = parseSendAddressDraft(mailData);
            sendData.setTo(data.getTo());
            sendData.setCc(data.getCc());
            sendData.setBcc(data.getBcc());
            sendData.setRenban(data.getRenban());
        } else {
            sendData.setTo(parseSendAddress(mailData.getTo()));
            sendData.setCc(parseSendAddress(mailData.getCc()));
            sendData.setBcc(parseSendAddress(mailData.getBcc()));
        }
        sendData.setTempFileList(tempFileList__);

        SmtpSendModel sendModel = new SmtpSendModel();
        sendModel.setCon(con);
        sendModel.setWdrSid(wdrSid);
        sendModel.setUserSid(userSid);
        sendModel.setGsContext(gsContext);
        sendModel.setHtmlMail(mailData.isHtmlMail());
        sendModel.setTimeSent(mailData.isTimeSent());
        sendModel.setSendPlanDate(mailData.getSendPlanDate());
        sendModel.setSendPlanCompressFileType(mailData.getCompressFileType());

        sendModel.setOriginMailNum(0);
        sendModel.setEditType(GSConstWebmail.WMD_EDITFLG_NOSET);
        WmlMaildataDao mailDataDao = new WmlMaildataDao(con);
        if (beforeMailNum > 0) {
            WmlMaildataModel beforeMailData = mailDataDao.select(beforeMailNum);

            if (beforeMailData != null && beforeMailData.getWmdOrigin() > 0) {
                sendModel.setOriginMailNum(beforeMailData.getWmdOrigin());
                sendModel.setEditType(beforeMailData.getWmdEditType());
            } else {
                sendModel.setOriginMailNum(beforeMailNum);
                if (sendType == GSConstWebmail.SEND_TYPE_REPLY
                        || sendType == GSConstWebmail.SEND_TYPE_REPLY_ALL) {
                    sendModel.setEditType(GSConstWebmail.WMD_EDITFLG_REPLY);
                } else if (sendType == GSConstWebmail.SEND_TYPE_FORWARD) {
                    sendModel.setEditType(GSConstWebmail.WMD_EDITFLG_FORWARD);
                }
            }
        }
        WmlAccountDao accountDao = new WmlAccountDao(con);
        WmlAccountModel accountMdl = accountDao.select(sendAccount);
        String encode = Encoding.ISO_2022_JP;
        if (accountMdl.getWacEncodeSend() == GSConstWebmail.WAC_ENCODE_SEND_UTF8) {
            encode = Encoding.UTF_8;
        }
        WmlSmtpSendBiz sendBiz = new WmlSmtpSendBiz();
        long afterMailNum;
        try {
            afterMailNum = sendBiz.entrySendMailData(sendAccount, sendModel, sendData,
                    WmlSmtpSendBiz.ENTRY_TYPE_INSERT,
                    dirType == GSConstWebmail.DIR_TYPE_DRAFT,
                    encode, domain);
        } catch (SQLException e) {
            throw e;
        } catch (IOToolsException | TempFileException e) {
            throw new RuntimeException(e);
        }
        sendData.setMailNum(afterMailNum);

        if (beforeMailNum > 0) {
            //編集元のメール情報が存在する、かつ編集元メールが予約送信 or 草稿の場合、編集元メールを削除する
            WmlRestMailDataBiz mailDataBiz = new WmlRestMailDataBiz();
            if (mailDataBiz.isSoukouEditMail(con, beforeMailNum)) {
                if (sendType != GSConstWebmail.SEND_TYPE_REPLY
                        && sendType != GSConstWebmail.SEND_TYPE_REPLY_ALL
                        && sendType != GSConstWebmail.SEND_TYPE_FORWARD) {
                    __deleteMailData(con, String.valueOf(sendAccount),
                            new long[]{beforeMailNum}, userSid);
                }
            }

            //予約送信、かつ返信 or 転送の場合、編集元メールの返信フラグ or 転送フラグを更新する
            if (dirType == GSConstWebmail.DIR_TYPE_NOSEND) {
                switch (sendType) {
                case GSConstWebmail.SEND_TYPE_REPLY :
                case GSConstWebmail.SEND_TYPE_REPLY_ALL :
                    mailDataDao.setReply(beforeMailNum);
                    break;

                case GSConstWebmail.SEND_TYPE_FORWARD :
                    mailDataDao.setForward(beforeMailNum);
                    break;
                default:
                }
            }
        }

        List<String> fromList = new ArrayList<String>();
        fromList.add(accountMdl.getWacAddress());
        sendData.setFrom(fromList);
        sendData.setSendPlanDate(mailData.getSendPlanDate());
        return sendData;
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    protected static String _escapeText(String text) {
        return _escapeText(text, true, true, true);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説] 画面に表示する文字列を変換するために使用する
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    protected static String _escapeTextView(String text) {
        return _escapeText(text, true, true, true, true);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    protected static String _escapeTextInput(String text) {
        return _escapeText(text, false, true, true);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説] テキストエリアに設定する文字列を対象とする
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    protected static String _escapeTextAreaInput(String text) {
        return _escapeText(text, false, true, false);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説] テキストボックスに設定する文字列を対象とする
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    protected static String _escapeTextBox(String text) {
        String mailText = NullDefault.getString(text, "");
        mailText = StringUtilHtml.transToHTmlForTextArea(mailText);
        return _escapeTextInput(mailText);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    protected static String _escapeTextAddress(String text) {
        return _escapeText(text, true, true, true, true, true);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考] '\b','\t','\f','\r' についてはサーバ側のJavascriptにてエスケープを行うので
     * <br>         エスケープの対象とはしない。
     * @param text 文字列
     * @param htmlEncode true:HTMLエスケープを行う false:HTMLエスケープを行わない
     * @param input 入力項目(textbox or textarea)か
     * @param newline '"'、改行文字のエスケープ true:する false:しない
     * @return 文字列
     */
    protected static String _escapeText(String text, boolean htmlEncode, boolean input,
            boolean newline) {
        return _escapeText(text, htmlEncode, input, newline, false);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考] '\b','\t','\f','\r' についてはサーバ側のJavascriptにてエスケープを行うので
     * <br>         エスケープの対象とはしない。
     * @param text 文字列
     * @param htmlEncode true:HTMLエスケープを行う false:HTMLエスケープを行わない
     * @param input 入力項目(textbox or textarea)か
     * @param newline '"'、改行文字のエスケープ true:する false:しない
     * @param htmlAmparsant true:文字実体参照のHTMLエスケープを行う false:文字実体参照のHTMLエスケープを行わない
     * @return 文字列
     */
    protected static String _escapeText(String text, boolean htmlEncode, boolean input,
            boolean newline, boolean htmlAmparsant) {
        return _escapeText(text, htmlEncode, input, newline, htmlAmparsant, false);
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param text 文字列
     * @param htmlEncode true:HTMLエスケープを行う false:HTMLエスケープを行わない
     * @param input 入力項目(textbox or textarea)か
     * @param newline '"'、改行文字のエスケープ true:する false:しない
     * @param htmlAmparsant true:文字実体参照のHTMLエスケープを行う false:文字実体参照のHTMLエスケープを行わない
     * @param jsonParse '\b','\t','\f','\r'文字列のエスケープ
     * @return 文字列
     */
    protected static String _escapeText(String text, boolean htmlEncode, boolean input,
            boolean newline, boolean htmlAmparsant, boolean jsonParse) {
        String mailText = NullDefault.getString(text, "");
        if (htmlEncode) {
            if (htmlAmparsant) {
                mailText = StringUtilHtml.transToHTmlPlusAmparsant(mailText);
            } else {
                mailText = StringUtilHtml.transToHTml(mailText);
            }
        }
        if (input && newline) {
            mailText = StringUtilHtml.replaceString(mailText, "\\", "\\\\");
        }

        mailText = StringUtilHtml.replaceString(mailText, "\r\n", "\n");

        if (newline) {
            mailText = StringUtilHtml.replaceString(mailText, "\"", "\\\"");
            mailText = StringUtilHtml.replaceString(mailText, "\n", "\\n");
        }

        if (jsonParse) {
            mailText = StringUtilHtml.replaceString(mailText, "\u0001", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0002", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0003", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0004", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0005", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0006", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0007", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u000b", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u000e", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u000f", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0010", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0011", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0012", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0013", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0014", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0015", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0016", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0017", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0018", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u0019", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u001a", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u001b", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u001c", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u001d", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u001e", "");
            mailText = StringUtilHtml.replaceString(mailText, "\u001f", "");
            mailText = StringUtilHtml.replaceString(mailText, "\b", "\\b");
            mailText = StringUtilHtml.replaceString(mailText, "/", "\\/");
            mailText = StringUtilHtml.replaceString(mailText, "\r", "\\r");
            mailText = StringUtilHtml.replaceString(mailText, "\t", "\\t");

            //ESCを除去
            while (mailText.indexOf('\u001b') >= 0) {
                mailText  = mailText.replace('\u001b', '\u0020');
            }
        }
        return mailText;
    }

    /**
     * <br>[機  能] 指定したメールの物理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param mailNumList 削除するメールのメッセージ番号
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteMailData(Connection con, String wacSid, long[] mailNumList, int userSid)
            throws SQLException {
        WmlSendaddressDao sendDao = new WmlSendaddressDao(con);
        WmlTempfileDao tempFileDao = new WmlTempfileDao(con);
        WmlLabelRelationDao labelRelationDao = new WmlLabelRelationDao(con);
        WmlHeaderDataDao headerDao = new WmlHeaderDataDao(con);
        WmlMailBodyDao bodyDao = new WmlMailBodyDao(con);
        WmlMailSendplanDao sendplanDao = new WmlMailSendplanDao(con);
        WmlMaildataDao mailDataDao = new WmlMaildataDao(con);
        UDate now = new UDate();

        for (long deleteMailNum : mailNumList) {
            sendDao.delete(deleteMailNum);
            tempFileDao.deleteTempFile(deleteMailNum, userSid, now);
            labelRelationDao.deleteToMailNum(deleteMailNum);
            headerDao.delete(deleteMailNum);
            bodyDao.delete(deleteMailNum);
            sendplanDao.delete(deleteMailNum);
            mailDataDao.delete(deleteMailNum);
        }

        if (mailNumList.length > 0) {
            log__.debug("物理削除したメールの件数 : " + mailNumList.length);

            //削除したメールが1件以上存在する場合、アカウントディスク容量の集計を行う
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.updateAccountDiskSize(con, Integer.parseInt(wacSid));
        }
    }

    /**
     * <br>[機  能] 送信メールのテンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     */
    private void __deleteSendTempDir(String tempDir) {
        if (tempDir != null && !tempDir.isEmpty()) {
            File dir = new File(tempDir);
            if (dir.getName().equals("sendmail")) {
                String parentDir = dir.getParent();
                if (parentDir != null && !parentDir.isEmpty()) {
                    IOTools.deleteDir(parentDir);
                }
            }
        }
    }

    /**
     * <br>[機  能] 送信メールのアドレス(宛先, CC, BCC)をparseする
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @return メールアドレス
     */
    public List<String> parseSendAddress(String address) {
        List<String> ret = new ArrayList<String>();
        if (!StringUtil.isNullZeroString(address)) {
            try {
                WmlSmtpSendBiz biz = new WmlSmtpSendBiz();
                ret = biz.getAddressList(address);
            } catch (AddressException e) {
                ret = new ArrayList<String>();
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 送信メールのアドレス(宛先, CC, BCC)をparseする(草稿用)
     * <br>[解  説]
     * <br>[備  考]
     * @param mailData メールデータ
     * @return WmlMailModel
     */
    public WmlMailModel parseSendAddressDraft(MailSendModel mailData) {

        WmlMailModel ret = new WmlMailModel();
        //カンマ分割
        List<String> toList = parseSendAddress(mailData.getTo());
        List<String> ccList = parseSendAddress(mailData.getCc());
        List<String> bccList = parseSendAddress(mailData.getBcc());
        if ((!StringUtil.isNullZeroString(mailData.getTo()) && toList.size() == 0)
                || (!StringUtil.isNullZeroString(mailData.getCc()) && ccList.size() == 0)
                || (!StringUtil.isNullZeroString(mailData.getBcc()) && bccList.size() == 0)) {
            //256文字分割
            toList = __parseSendAddressDraft(mailData.getTo());
            ccList = __parseSendAddressDraft(mailData.getCc());
            bccList = __parseSendAddressDraft(mailData.getBcc());
            ret.setRenban(GSConstWebmail.START_WLS_NUM);
        }
        ret.setTo(toList);
        ret.setCc(ccList);
        ret.setBcc(bccList);
        return ret;
    }

    /**
     * <br>[機  能] 送信メールのアドレス(宛先, CC, BCC)を256文字で分割する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @return メールアドレス
     */
    private List<String> __parseSendAddressDraft(String address) {

        List<String> ret = new ArrayList<String>();
        int idx = 0;
        while (idx < address.length()) {
            ret.add(address.substring(idx, Math.min(idx + 256, address.length())));
            idx += 256;
        }
        return ret;
    }
    /**
     * メールが予約送信かどうかを判定する
     * @return
    */
    public UDate getSendPlanDate() {
        return paramMdl__.getSendPlanDate();
    }
    /**
     * <br>[機  能] 宛先アドレスを結合
     * <br>[解  説] 空の場合は空文字が返る
     * <br>[備  考]
     *
     * @param addressList 宛先アドレス一覧
     * @return 複数のアドレスを結合した文字列
     */
    public static String joinAddress(String[] addressList) {
        StringBuffer ret = new StringBuffer();

        if (addressList != null && addressList.length > 0) {
            for (int i = 0; i < addressList.length; i++) {
                if (addressList[i].length() > 0) {
                    if (ret.length() > 0) {
                        ret.append(",");
                    }
                    ret.append(addressList[i]);
                }
            }
        }
        return ret.toString();
    }
    /**
     * <br>[機  能] 指定されたメールアドレス一覧の結合を行う
     * <br>[解  説] リストが空の場合はnull
     * <br>[備  考]
     * @param addressList メールアドレス一覧
     * @return 結合したメールアドレス
     */
    private String __joinAddress(List<String> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            return null;
        }

        String joinAddress = addressList.get(0);
        for (int idx = 1; idx < addressList.size(); idx++) {
            joinAddress += ", " + addressList.get(idx);
        }

        return joinAddress;
    }
    
    /**
     * <br>[機  能] 整形済みの本文を取得する
     * <br>[解  説] 
     * <br>[備  考]
     * @param body 本文
     * @param bodyLimit 本文の最大文字数
     * @param htmlFlg 0:テキストメール, 1:HTMLメール
     * @return 整形済みの本文
     */
    public static String getFormattedBody(String body, int bodyLimit, int htmlFlg) {

        body = NullDefault.getString(body, "");

        if (htmlFlg == GSConstWebmail.SEND_HTMLMAIL_TEXT) {
            //テキスト形式
            if (bodyLimit == GSConstWebmail.MAILBODY_LIMIT_NOLIMIT) {
                bodyLimit = -1;
            } else if (body.length() > bodyLimit) {
                body = WmlSmtpSender.formatHtmlToText(body);
            }
            //改行コードを"LF"から"CRLF"へ変換(最大長チェックのため)
            if (!StringUtil.isNullZeroString(body)) {
                body = body.replaceAll("\r\n", "\n");
                body = body.replaceAll("\n", "\r\n");
            }
        } else {
            //HTML形式
            if (bodyLimit == GSConstWebmail.MAILBODY_LIMIT_NOLIMIT) {
                bodyLimit = -1;
            }
            body = StringUtilHtml.replaceHtmlForView(body);
        }

        return body;
    }

    /**
     * <br>[機  能] 送信メールのテンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     */
    public static void deleteSendTempDir(String tempDir) {
        if (tempDir != null && !tempDir.isEmpty()) {
            File dir = new File(tempDir);
            if (dir.getName().equals("sendmail")) {
                String parentDir = dir.getParent();
                if (parentDir != null && !parentDir.isEmpty()) {
                    IOTools.deleteDir(parentDir);
                }
            }
        }
    }

    /**
     * <br>[機  能] 送信メール添付ファイルのテンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDirId テンポラリディレクトリID
     * @return テンポラリディレクトリパス
     * @throws IOToolsException テンポラリディレクトリIDが不正
     */
    public static String getSendTempDir(RequestModel reqMdl)
            throws IOToolsException {

        //テンポラリディレクトリIDのチェック
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDirId = tempPathUtil.getTempDirID(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);
        if (tempDirId == null
                || tempDirId.isEmpty()
                || !ValidateUtil.isAlphaNum(tempDirId)) {
            throw new IOToolsException("テンポラリディレクトリIDが不正: " + tempDirId);
        }

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqMdl, tempDirId);
        if (!tempDir.isEmpty()) {
            tempDir += "sendmail/";
        }

        return tempDir;
    }

}
