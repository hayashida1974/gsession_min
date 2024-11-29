package jp.groupsession.v2.cmn.cmn110;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル添付ポップアップの入力チェック結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn110ValidateResultModel extends AbstractModel {
    /** エラー種別 停止 */
    public static final int ERRORTYPE_STOP = 1;

    /** ActionErrors */
    private ActionErrors errors__ = null;
    /** エラーメッセージ一覧 */
    private List<String> errMessageList__ = null;
    /** エラー対象ファイルNo */
    private List<Integer> errFileNoList__ = null;
    /** エラー種別 */
    private int errorType__ = 0;
    /**
     * <p>コンストラクタ
     */
    public Cmn110ValidateResultModel() {
        errors__ = new ActionErrors();
        errMessageList__ = new ArrayList<String>();
        errFileNoList__ = new ArrayList<Integer>();
    }
    /**
     * <p>errMessageList を取得します。
     * @return errMessageList
     */
    public List<String> getErrMessageList() {
        return errMessageList__;
    }
    /**
     * <p>errMessageList をセットします。
     * @param errMessageList errMessageList
     */
    public void setErrMessageList(List<String> errMessageList) {
        errMessageList__ = errMessageList;
    }
    /**
     * <p>errors を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }
    /**
     * <p>errors をセットします。
     * @param errors errors
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
    }

    /**
     * <br>[機  能] 入力エラーの追加を行なう
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー
     * @param msg ActionMessage
     */
    public void addError(String key, ActionMessage msg) {
        errors__.add(key, msg);
    }
    /**
     * <br>[機  能] エラーメッセージの追加を行なう
     * <br>[解  説]
     * <br>[備  考]
     * @param message エラーメッセージ
     */
    public void addErrMessage(String message) {
        errMessageList__.add(message);
    }

    /**
     * <p>errFileNoList を取得します。
     * @return errFileNoList
     * @see jp.groupsession.v2.cmn.cmn110.Cmn110ValidateResultModel#errFileNoList__
     */
    public List<Integer> getErrFileNoList() {
        return errFileNoList__;
    }

    /**
     * <p>errFileNoList をセットします。
     * @param errFileNoList errFileNoList
     * @see jp.groupsession.v2.cmn.cmn110.Cmn110ValidateResultModel#errFileNoList__
     */
    public void setErrFileNoList(List<Integer> errFileNoList) {
        errFileNoList__ = errFileNoList;
    }

    /**
     * <p>errorType を取得します。
     * @return errorType
     * @see jp.groupsession.v2.cmn.cmn110.Cmn110ValidateResultModel#errorType__
     */
    public int getErrorType() {
        return errorType__;
    }
    /**
     * <p>errorType をセットします。
     * @param errorType errorType
     * @see jp.groupsession.v2.cmn.cmn110.Cmn110ValidateResultModel#errorType__
     */
    public void setErrorType(int errorType) {
        errorType__ = errorType;
    }
    /**
     * <br>[機  能] 入力エラー、エラーメッセージの追加を行なう
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param key キー
     * @param msgKey エラーメッセージキー
     * @param fileName ファイル名
     * @param fileNo ファイル番号
     */
    public void addErrors(HttpServletRequest req, String key, String msgKey,
                            int fileNo, String fileName) {
        addErrors(req, key, msgKey, fileNo, fileName, null);
    }

    /**
     * <br>[機  能] 入力エラー、エラーメッセージの追加を行なう
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param key キー
     * @param msgKey エラーメッセージキー
     * @param fileNo ファイル番号
     * @param fileName ファイル名
     * @param msgParams メッセージパラメータ
     */
    public void addErrors(HttpServletRequest req, String key, String msgKey,
                        int fileNo, String fileName,
                        String[] msgParams) {

        ActionMessage msg = null;
        String errMessage = null;
        GsMessage gsMsg = new GsMessage();

        if (msgParams != null && msgParams.length > 0) {
            msg = new ActionMessage(key, msgParams);
            errMessage = gsMsg.getMessage(req, msgKey, msgParams);
        } else {
            msg = new ActionMessage(key);
            errMessage = gsMsg.getMessage(req, msgKey);
        }

        if (!StringUtil.isNullZeroString(fileName)) {
            errMessage += "\r\n" + gsMsg.getMessage(req, "cmn.cmn110.8", fileName);
        }

        addError("file" + fileNo + "." + key, msg);
        addErrMessage(errMessage);

        if (!errFileNoList__.contains(fileNo)) {
            errFileNoList__.add(fileNo);
        }
    }
}