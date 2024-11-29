package jp.groupsession.v2.adr.adr160.model;

import jp.groupsession.v2.adr.model.AdrContactModel;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴情報(表示用)を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160ContactModel extends AdrContactModel {

    /** 登録者名 姓(画面表示用) */
    private String adcAdduserDspSei__;
    /** 登録者名 名(画面表示用) */
    private String adcAdduserDspMei__;
    /** 登録者名 削除フラグ */
    private int adcAddusrJkbn__;
    /** 登録者名 ログイン停止フラグ */
    private int adcAddusrUkoFlg__;
    /** コンタクト日時From(画面表示用) */
    private String adcCttimeDsp__;
    /** コンタクト日時To(画面表示用) */
    private String adcCttimeToDsp__;

    /**
     * <p>adcAdduserDspMei を取得します。
     * @return adcAdduserDspMei
     */
    public String getAdcAdduserDspMei() {
        return adcAdduserDspMei__;
    }
    /**
     * <p>adcAdduserDspMei をセットします。
     * @param adcAdduserDspMei adcAdduserDspMei
     */
    public void setAdcAdduserDspMei(String adcAdduserDspMei) {
        adcAdduserDspMei__ = adcAdduserDspMei;
    }
    /**
     * <p>adcAdduserDspSei を取得します。
     * @return adcAdduserDspSei
     */
    public String getAdcAdduserDspSei() {
        return adcAdduserDspSei__;
    }
    /**
     * <p>adcAdduserDspSei をセットします。
     * @param adcAdduserDspSei adcAdduserDspSei
     */
    public void setAdcAdduserDspSei(String adcAdduserDspSei) {
        adcAdduserDspSei__ = adcAdduserDspSei;
    }
    /**
     * <p>adcCttimeDsp を取得します。
     * @return adcCttimeDsp
     */
    public String getAdcCttimeDsp() {
        return adcCttimeDsp__;
    }
    /**
     * <p>adcCttimeDsp をセットします。
     * @param adcCttimeDsp adcCttimeDsp
     */
    public void setAdcCttimeDsp(String adcCttimeDsp) {
        adcCttimeDsp__ = adcCttimeDsp;
    }
    /**
     * <p>adcCttimeToDsp を取得します。
     * @return adcCttimeToDsp
     */
    public String getAdcCttimeToDsp() {
        return adcCttimeToDsp__;
    }
    /**
     * <p>adcCttimeToDsp をセットします。
     * @param adcCttimeToDsp adcCttimeToDsp
     */
    public void setAdcCttimeToDsp(String adcCttimeToDsp) {
        adcCttimeToDsp__ = adcCttimeToDsp;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getAdcAddUsrUkoFlg() {
        return adcAddusrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setAdcAddUsrUkoFlg(int usrUkoFlg) {
        adcAddusrUkoFlg__ = usrUkoFlg;
    }
    /**
     * <p>adcAddusrJkbn を取得します。
     * @return adcAddusrJkbn
     */
    public int getAdcAddusrJkbn() {
        return adcAddusrJkbn__;
    }
    /**
     * <p>adcAddusrJkbn をセットします。
     * @param adcAddusrJkbn adcAddusrJkbn
     */
    public void setAdcAddusrJkbn(int adcAddusrJkbn) {
        adcAddusrJkbn__ = adcAddusrJkbn;
    }
}
