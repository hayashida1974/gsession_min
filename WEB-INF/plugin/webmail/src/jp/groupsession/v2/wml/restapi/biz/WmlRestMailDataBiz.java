package jp.groupsession.v2.wml.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSearchModel;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.model.LabelInfo;
import jp.groupsession.v2.wml.restapi.model.TmpFileInfo;

/**
 * <br>[機  能] WEBメールのRESTAPI メール情報に関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlRestMailDataBiz {
    /** オペレーションログ メールを削除する(ゴミ箱) */
    public static final int OPLOG_MAILDELETE = 0;
    /** オペレーションログ ラベルを設定する */
    public static final int OPLOG_SETLABEL = 1;
    /** オペレーションログ ラベルを解除する */
    public static final int OPLOG_REMOVELABEL = 2;
    /** オペレーションログ メールの移動 */
    public static final int OPLOG_MOVEMAIL = 3;

    /**
     * RestApi リソースアクセス権限判定
     * @param ctx
     * @param accountId
     * @param mailSids
     * @throws SQLException 
     */
    public void validate(RestApiContext ctx, String accountId, long mailSid) throws SQLException {
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int accountSid = wraBiz.getWmlAccountSid(ctx.getCon(), accountId);

        //実行ユーザがアカウントを利用可能かチェック
        boolean canUseAccount = wraBiz.canUseAccount(ctx, accountId);
        //アカウントが対象メールを利用可能かチェック
        boolean canAccessMail = canAccess(ctx.getCon(), accountSid, mailSid);

        if (!canUseAccount || !canAccessMail ) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_MAIL,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("cmn.mail")
            );
        }

    }
    /**
     * <p> アカウントSIDとメールSIDでアクセス可能か判定する
     * @param con
     * @param accountSid
     * @param mailSid
     * @return
     * @throws SQLException
     */
    public boolean canAccess(Connection con,  int accountSid, long mailSid) throws SQLException {

        //実行アカウントが参照メールSIDにアクセスできない場合
        WmlMaildataDao baseMailDao = new WmlMaildataDao(con);
        WmlMaildataModel mdl = baseMailDao.select(mailSid);
        if (Optional.ofNullable(mdl)
            .filter(m -> (m.getWacSid() == accountSid))
            .isEmpty()) {
                return false;
        }        
        return true;
    }

    /**
     * <p> 指定したメールが草稿または予約送信かを判定する
     * @param con コネクション
     * @param mailSid メールSID
     * @return true:草稿または予約送信メールである, false:草稿または予約送信メールではない
     * @throws SQLException
     */
    public boolean isSoukouEditMail(Connection con, long mailSid) throws SQLException {

        //参照メールSIDが予約送信または草稿メールではない場合は利用不可
        WmlMaildataDao baseMailDao = new WmlMaildataDao(con);
        int dirType = baseMailDao.getDirType(mailSid);
        if (dirType != GSConstWebmail.DIR_TYPE_NOSEND
            && dirType != GSConstWebmail.DIR_TYPE_DRAFT) {
            return false;
        }
        return true;
    }
    
    /**
     * <br>[機  能] 指定したメールSIDに該当するメール情報を取得し、REST API 実行結果モデルに格納して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wacSid アカウントSID
     * @param mailSidArray メールSID
     * @param appRootPath アプリケーションルートパス
     * @return REST API 実行結果モデルに格納したメール情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlAccountsMailsResultModel> getMailsResult(Connection con, int wacSid, long[] mailSidArray, String appRootPath)
    throws SQLException {

        if (mailSidArray == null || mailSidArray.length == 0) {
            return new ArrayList<WmlAccountsMailsResultModel>();
        }

        //メッセージ一覧取得
        WmlMailSearchModel searchMdl = new WmlMailSearchModel();
        searchMdl.setAccountSid(wacSid);
        searchMdl.setMailNumArray(mailSidArray);
 
        WmlMaildataDao maildataDao = new WmlMaildataDao(con);
        List<WmlMailResultModel> mailList = maildataDao.getMailList(searchMdl,
                                        WmlBiz.getBodyLimitLength(appRootPath));
   
        return getMailsResult(con, mailList);
    }

    /**
     * <br>[機  能] 指定したメール情報をREST API 実行結果モデルに格納して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mailList メール情報
     * @return REST API 実行結果モデルに格納したメール情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlAccountsMailsResultModel> getMailsResult(Connection con, List<WmlMailResultModel> mailList)
    throws SQLException {
        List<WmlAccountsMailsResultModel> resultList = new ArrayList<>();

        WmlBiz wmlBiz = new WmlBiz();
        WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
        Map<Long, String> dirNameMap = new HashMap<Long, String>();

        for (WmlMailResultModel mailData : mailList) {
            WmlAccountsMailsResultModel resultMdl = new WmlAccountsMailsResultModel();
            //メールSID
            resultMdl.setSid(mailData.getMailNum());
            //ディレクトリタイプ
            switch (mailData.getDirType()) {
                case GSConstWebmail.DIR_TYPE_RECEIVE:
                    resultMdl.setDirectoryType("inbox");
                    break;
                case GSConstWebmail.DIR_TYPE_SENDED:
                    resultMdl.setDirectoryType("sent");
                    break;
                case GSConstWebmail.DIR_TYPE_NOSEND:
                    resultMdl.setDirectoryType("future");
                    break;
                case GSConstWebmail.DIR_TYPE_DRAFT:
                    resultMdl.setDirectoryType("draft");
                    break;
                case GSConstWebmail.DIR_TYPE_DUST:
                    resultMdl.setDirectoryType("trash");
                    break;
                case GSConstWebmail.DIR_TYPE_STORAGE:
                    resultMdl.setDirectoryType("keep");
                    break;
                default:
                    break;
            }

            //ディレクトリ名
            String dirName = dirNameMap.get(mailData.getDirSid());
            if (dirName == null) {
                dirName = dirDao.getDirName(mailData.getDirSid());
                dirNameMap.put(mailData.getDirSid(), dirName);
            }
            resultMdl.setDirectoryName(dirName);

            //宛先/CC/BCCアドレス
            if (mailData.getSendAddress() != null) {
                //TOアドレス
                resultMdl.setToArray(mailData.getSendAddress().getToList());
                //Ccアドレス
                resultMdl.setCcArray(mailData.getSendAddress().getCcList());
                //Bccアドレス
                resultMdl.setBccArray(mailData.getSendAddress().getBccList());
            }

            //送信元アドレス
            resultMdl.setFromText(mailData.getFrom());

            //件名
            resultMdl.setSubjectText(mailData.getSubject());
            //本文
            resultMdl.setBodyText(mailData.getBody());

            //メール日時
            resultMdl.setDateTime(mailData.getDate());
            //予約送信タイプ
            resultMdl.setSendPlanType(wmlBiz.getSendPlanType(mailData));
            //予約送信日時
            resultMdl.setSendPlanDateTime(mailData.getSendPlanDate());
            //開封済みフラグ
            if (mailData.isReaded()) {
                resultMdl.setOpenFlg(GSConstWebmail.READEDFLG_READED);
            } else {
                resultMdl.setOpenFlg(GSConstWebmail.READEDFLG_NOREAD);
            }

            //添付ファイル配列
            if (mailData.getTempFileList() != null && !mailData.getTempFileList().isEmpty()) {
                resultMdl.setTmpFileArray(
                    mailData.getTempFileList().stream()
                        .map(tempFileData -> {
                            TmpFileInfo apiTempMdl = new TmpFileInfo();
                            apiTempMdl.setBinSid(tempFileData.getBinSid());
                            apiTempMdl.setFileName(tempFileData.getFileName());
                            apiTempMdl.setFileSizeByteNum(tempFileData.getFileSize());
                            return apiTempMdl;
                        })
                        .collect(Collectors.toList())
                );
            }

            //ラベル配列
            if (mailData.getLabelList() != null && !mailData.getLabelList().isEmpty()) {
                resultMdl.setLabelArray(
                    mailData.getLabelList().stream()
                        .map(labelData -> {
                            LabelInfo apiLabelMdl = new LabelInfo();
                            apiLabelMdl.setSid(labelData.getId());
                            apiLabelMdl.setName(labelData.getName());
                            return apiLabelMdl;
                        })
                        .collect(Collectors.toList())
                );
            }

            //返信済みフラグ
            if (mailData.isReply()) {
                resultMdl.setReplyFlg(GSConstWebmail.REPLYFLG_REPLY);
            } else {
                resultMdl.setReplyFlg(GSConstWebmail.REPLYFLG_NORMAL);
            }
            //転送済みフラグ
            if (mailData.isForward()) {
                resultMdl.setForwardFlg(GSConstWebmail.FORWARDFLG_FORWARD);
            } else {
                resultMdl.setForwardFlg(GSConstWebmail.FORWARDFLG_NORMAL);
            }
            //自動圧縮フラグ（RESTAPI用に変換)
            resultMdl.setCompressFileFlg(mailData.getSendPlanCompressFile());
            if (resultMdl.getCompressFileFlg() == GSConstWebmail.WSP_COMPRESS_FILE_COMPRESS) {
                resultMdl.setCompressFileFlg(GSConstWebmail.RESTAPI_COMPRESS_YES);
            } else {
                resultMdl.setCompressFileFlg(GSConstWebmail.RESTAPI_COMPRESS_NO);
            }

            resultList.add(resultMdl);
        }

        return resultList;
    }

    /**
     * <br>[機  能] メール情報操作後のオペレーションログを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param accountId WEBメールアカウントID
     * @param mailSidArray メールSID
     * @param paramList パラメータ
     * @param type 操作
     * @throws SQLException SQL実行時例外
     * @throws GSException オペレーションログの出力に失敗
     */
    public void outputOpLog(Connection con,
            RequestModel reqMdl,
            String accountId,
            long[] mailSidArray,
            String[] paramList,
            int type) throws SQLException, GSException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        String pgId = "";
        String opCode = "";
        String message = "";
        if (type == OPLOG_MAILDELETE) {
            //メールを削除する
            pgId = "RESTAPI_MAIL_DELETE";
            opCode = gsMsg.getMessage("cmn.delete");
            message = gsMsg.getMessage(GSConstWebmail.LOG_VALUE_DELETEMAIL);
        } else if (type == OPLOG_SETLABEL) {
            //ラベルを設定する
            pgId = "RESTAPI_MAIL_LABEL";
            opCode = gsMsg.getMessage("cmn.entry");
            message = gsMsg.getMessage(GSConstWebmail.LOG_VALUE_LABEL);
        } else if (type == OPLOG_REMOVELABEL) {
            //ラベルを削除する
            pgId = "RESTAPI_MAIL_LABEL";
            opCode = gsMsg.getMessage("cmn.delete");
            message = gsMsg.getMessage(GSConstWebmail.LOG_VALUE_LABEL);
        } else if (type == OPLOG_MOVEMAIL) {
            //メールの移動
            pgId = "RESTAPI_MAIL_MOVE";
            opCode = gsMsg.getMessage("cmn.update");
            message = gsMsg.getMessage(GSConstWebmail.LOG_VALUE_MOVEMAIL);
        }
        //アカウント名を出力する
        String accountName = "[" + gsMsg.getMessage("wml.102") + "] ";
        WmlAccountDao accountDao = new WmlAccountDao(con);
        int wacSid = accountDao.getAccountSid(accountId);
        accountName += accountDao.getAccountName(wacSid);
        message += "\r\n" + accountName;

        if (type == OPLOG_MOVEMAIL) {
            //移動先のディレクトリ名を出力する
            String dirName = "[" + gsMsg.getMessage("project.52") + "] ";
            WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
            dirName += dirDao.getDirName(Long.parseLong(paramList[0]));
            message += "\r\n" + dirName;
        } else if (type == OPLOG_SETLABEL || type == OPLOG_REMOVELABEL) {
            //設定 or 削除したラベル名を出力する
            message += "\r\n[" + gsMsg.getMessage("cmn.label.name") + "]" +  __getLabelName(con, Integer.parseInt(paramList[0]));
        }

        //操作したメールの件名を出力する
        message += "\r\n[" + gsMsg.getMessage("cmn.subject") + "] ";
        List<String> mailTitleList = new ArrayList<String>();
        if (type == OPLOG_MAILDELETE) {
            //メール削除の場合、事前に取得したメール件名を使用
            mailTitleList.addAll(Arrays.asList(paramList));
        } else if (mailSidArray != null && mailSidArray.length > 0) {
            //メール削除以外の場合、操作対象メールの件名を取得
            WmlMaildataDao mailDao = new WmlMaildataDao(con);
            mailTitleList.addAll(mailDao.getMailSubject(mailSidArray));
        }

        for (int idx = 0; idx < mailTitleList.size(); idx++) {
            if (idx > 0) {
                message += ",";
            }
            message += mailTitleList.get(idx);
        }

        message = StringUtil.trimRengeString(message, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE);
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.outPutApiLog(
                reqMdl,
                con,
                reqMdl.getSmodel().getUsrsid(),
                pgId,
                opCode,
                GSConstLog.LEVEL_TRACE,
                message);

    }

    private String __getLabelName(Connection con, int wlbSid)
    throws SQLException {
        WmlLabelDao labelDao = new WmlLabelDao(con);
        return labelDao.getLabelName(wlbSid);
    }
}