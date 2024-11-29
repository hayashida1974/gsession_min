package jp.groupsession.v2.sml.sml020kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ショートメール作成確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml020knForm extends Sml020Form {
    /** 添付ファイルリスト */
    private ArrayList<CmnBinfModel> sml020knFileList__ = null;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String sml020knBinSid__ = null;
    /** 表示用内容*/
    private String sml020knSmsBody__ = null;
    /** 写真 ファイルSid  */
    private int sml020knBinFileSid__ = 0;
    /** 写真 ファイル有無 */
    private int sml020knPhotoFileDsp__ = GSConstUser.INDIVIDUAL_INFO_CLOSE;

    /** 表示用差出人アカウント*/
    private String sml020knSendAccount__ = null;
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mode チェックモード
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param tempPath テンポラリディレクトリパス
     * @param appRootPath アプリケーションルートパス
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public ActionErrors validateCheck020kn(int mode,
                                         Connection con,
                                         RequestModel reqMdl,
                                         String tempPath,
                                         String appRootPath)
    throws SQLException, IOToolsException {

        ActionErrors errors = validateCheck020(mode, con, reqMdl, appRootPath);

        //添付ファイル
        CommonBiz cmnBiz = new CommonBiz();
        List<TempFileModel> tempfileList = cmnBiz.getTempFiles(tempPath);
        for (TempFileModel fileData : tempfileList) {
            if (!fileData.getFile().exists()) {
                ActionMessage msg =
                    new ActionMessage("error.deleted.timeover.file");
                StrutsUtil.addMessage(errors, msg, "smlTempFile");
                break;
            }
        }

        return errors;
    }

    /**
     * <p>sml020knSmsBody を取得します。
     * @return sml020knSmsBody
     */
    public String getSml020knSmsBody() {
        return sml020knSmsBody__;
    }
    /**
     * <p>sml020knSmsBody をセットします。
     * @param sml020knSmsBody sml020knSmsBody
     */
    public void setSml020knSmsBody(String sml020knSmsBody) {
        sml020knSmsBody__ = sml020knSmsBody;
    }
    /**
     * <p>sml020knBinSid を取得します。
     * @return sml020knBinSid
     */
    public String getSml020knBinSid() {
        return sml020knBinSid__;
    }
    /**
     * <p>sml020knBinSid をセットします。
     * @param sml020knBinSid sml020knBinSid
     */
    public void setSml020knBinSid(String sml020knBinSid) {
        sml020knBinSid__ = sml020knBinSid;
    }
    /**
     * <p>sml020knFileList を取得します。
     * @return sml020knFileList
     */
    public ArrayList<CmnBinfModel> getSml020knFileList() {
        return sml020knFileList__;
    }
    /**
     * <p>sml020knFileList をセットします。
     * @param sml020knFileList sml020knFileList
     */
    public void setSml020knFileList(ArrayList<CmnBinfModel> sml020knFileList) {
        sml020knFileList__ = sml020knFileList;
    }
    /**
     * <p>sml020knBinFileSid を取得します。
     * @return sml020knBinFileSid
     */
    public int getSml020knBinFileSid() {
        return sml020knBinFileSid__;
    }
    /**
     * <p>sml020knBinFileSid をセットします。
     * @param sml020knBinFileSid sml020knBinFileSid
     */
    public void setSml020knBinFileSid(int sml020knBinFileSid) {
        sml020knBinFileSid__ = sml020knBinFileSid;
    }
    /**
     * <p>sml020knPhotoFileDsp を取得します。
     * @return sml020knPhotoFileDsp
     */
    public int getSml020knPhotoFileDsp() {
        return sml020knPhotoFileDsp__;
    }
    /**
     * <p>sml020knPhotoFileDsp をセットします。
     * @param sml020knPhotoFileDsp sml020knPhotoFileDsp
     */
    public void setSml020knPhotoFileDsp(int sml020knPhotoFileDsp) {
        sml020knPhotoFileDsp__ = sml020knPhotoFileDsp;
    }

    /**
     * <p>sml020knSendAccount を取得します。
     * @return sml020knSendAccount
     * @see jp.groupsession.v2.sml.sml020kn.Sml020knForm#sml020knSendAccount__
     */
    public String getSml020knSendAccount() {
        return sml020knSendAccount__;
    }

    /**
     * <p>sml020knSendAccount をセットします。
     * @param sml020knSendAccount sml020knSendAccount
     * @see jp.groupsession.v2.sml.sml020kn.Sml020knForm#sml020knSendAccount__
     */
    public void setSml020knSendAccount(String sml020knSendAccount) {
        sml020knSendAccount__ = sml020knSendAccount;
    }

}