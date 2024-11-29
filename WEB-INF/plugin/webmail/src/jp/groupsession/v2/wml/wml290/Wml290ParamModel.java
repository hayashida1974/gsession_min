package jp.groupsession.v2.wml.wml290;

import java.util.List;

import jp.groupsession.v2.wml.wml280.Wml280ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール 送信先リスト選択画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml290ParamModel extends Wml280ParamModel {
    /** 初期表示フラグ */
    private int wml290initFlg__ = 0;
    /** 設定アドレス */
    private List<String> wml290destAddressList__ = null;
    /** Webメール メールアドレス */
    private String wml290webmailAddress__ = null;
    /** 送信先リストコンボ */
    private List<LabelValueBean> wml290destlistCombo__ = null;
    /**
     * <p>wml290initFlg を取得します。
     * @return wml290initFlg
     */
    public int getWml290initFlg() {
        return wml290initFlg__;
    }
    /**
     * <p>wml290initFlg をセットします。
     * @param wml290initFlg wml290initFlg
     */
    public void setWml290initFlg(int wml290initFlg) {
        wml290initFlg__ = wml290initFlg;
    }
    /**
     * <p>wml290destAddressList を取得します。
     * @return wml290destAddressList
     */
    public List<String> getWml290destAddressList() {
        return wml290destAddressList__;
    }
    /**
     * <p>wml290destAddressList をセットします。
     * @param wml290destAddressList wml290destAddressList
     */
    public void setWml290destAddressList(List<String> wml290destAddressList) {
        wml290destAddressList__ = wml290destAddressList;
    }
    /**
     * <p>wml290webmailAddress を取得します。
     * @return wml290webmailAddress
     */
    public String getWml290webmailAddress() {
        return wml290webmailAddress__;
    }
    /**
     * <p>wml290webmailAddress をセットします。
     * @param wml290webmailAddress wml290webmailAddress
     */
    public void setWml290webmailAddress(String wml290webmailAddress) {
        wml290webmailAddress__ = wml290webmailAddress;
    }
    /**
     * <p>wml290destlistCombo を取得します。
     * @return wml290destlistCombo
     */
    public List<LabelValueBean> getWml290destlistCombo() {
        return wml290destlistCombo__;
    }
    /**
     * <p>wml290destlistCombo をセットします。
     * @param wml290destlistCombo wml290destlistCombo
     */
    public void setWml290destlistCombo(List<LabelValueBean> wml290destlistCombo) {
        wml290destlistCombo__ = wml290destlistCombo;
    }
}
