package jp.groupsession.v2.wml.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>[機  能] 受信メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlReceiveMailModel extends WmlMailModel {

    /** エラーフラグ */
    private boolean errFlg__ = false;
    /** メールヘッダ―部 エラーフラグ */
    private boolean headerErrFlg__ = false;
    /** エラー内容 */
    private List<String> errMessage__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlReceiveMailModel() {
        super();
    }

    /**
     * <p>errFlg を取得します。
     * @return errFlg
     */
    public boolean isErrFlg() {
        return errFlg__;
    }

    /**
     * <p>errFlg をセットします。
     * @param errFlg errFlg
     */
    public void setErrFlg(boolean errFlg) {
        errFlg__ = errFlg;
    }

    /**
     * <p>headerErrFlg を取得します。
     * @return headerErrFlg
     * @see jp.groupsession.v2.wml.model.WmlReceiveMailModel#headerErrFlg__
     */
    public boolean isHeaderErrFlg() {
        return headerErrFlg__;
    }

    /**
     * <p>headerErrFlg をセットします。
     * @param headerErrFlg headerErrFlg
     * @see jp.groupsession.v2.wml.model.WmlReceiveMailModel#headerErrFlg__
     */
    public void setHeaderErrFlg(boolean headerErrFlg) {
        headerErrFlg__ = headerErrFlg;
    }

    /**
     * <p>errMessage を取得します。
     * @return errMessage
     */
    public List<String> getErrMessage() {
        return errMessage__;
    }

    /**
     * <p>errMessage をセットします。
     * @param errMessage errMessage
     */
    public void setErrMessage(List<String> errMessage) {
        errMessage__ = errMessage;
    }

    /**
     * <br>[機  能] エラー内容を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param errMessage エラー内容
     */
    public void addErrMessage(String errMessage) {
        if (errMessage__ == null) {
            errMessage__ = new ArrayList<String>();
        }

        errMessage__.add(errMessage);
    }
}
