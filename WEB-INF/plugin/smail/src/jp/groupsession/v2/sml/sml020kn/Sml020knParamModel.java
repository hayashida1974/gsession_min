package jp.groupsession.v2.sml.sml020kn;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.sml020.Sml020ParamModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ショートメール作成確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml020knParamModel extends Sml020ParamModel {
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
     * @see jp.groupsession.v2.sml.sml020kn.Sml020knParamModel#sml020knSendAccount__
     */
    public String getSml020knSendAccount() {
        return sml020knSendAccount__;
    }
    /**
     * <p>sml020knSendAccount をセットします。
     * @param sml020knSendAccount sml020knSendAccount
     * @see jp.groupsession.v2.sml.sml020kn.Sml020knParamModel#sml020knSendAccount__
     */
    public void setSml020knSendAccount(String sml020knSendAccount) {
        sml020knSendAccount__ = sml020knSendAccount;
    }
}