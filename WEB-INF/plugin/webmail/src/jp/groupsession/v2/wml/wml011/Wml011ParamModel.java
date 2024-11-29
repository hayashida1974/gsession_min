package jp.groupsession.v2.wml.wml011;

import java.util.List;

import jp.groupsession.v2.wml.model.base.WmlHeaderDataModel;
import jp.groupsession.v2.wml.wml010.Wml010ParamModel;

/**
 * <br>[機  能] WEBメール メールヘッダ情報(ポップアップ)画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml011ParamModel extends Wml010ParamModel {

    /** ヘッダ情報 */
    private List<WmlHeaderDataModel> wml011MailHeaderData__ = null;
    /** 登録日時 */
    private String wml011EntryDate__ = null;
    /** ヘッダ情報表示フラグ */
    private boolean wml011viewFlg__ = false;

    /**
     * <p>wml011MailHeaderData を取得します。
     * @return wml011MailHeaderData
     */
    public List<WmlHeaderDataModel> getWml011MailHeaderData() {
        return wml011MailHeaderData__;
    }

    /**
     * <p>wml011MailHeaderData をセットします。
     * @param wml011MailHeaderData wml011MailHeaderData
     */
    public void setWml011MailHeaderData(
            List<WmlHeaderDataModel> wml011MailHeaderData) {
        wml011MailHeaderData__ = wml011MailHeaderData;
    }

    /**
     * <p>wml011EntryDate を取得します。
     * @return wml011EntryDate
     */
    public String getWml011EntryDate() {
        return wml011EntryDate__;
    }

    /**
     * <p>wml011EntryDate をセットします。
     * @param wml011EntryDate wml011EntryDate
     */
    public void setWml011EntryDate(String wml011EntryDate) {
        wml011EntryDate__ = wml011EntryDate;
    }

    /**
     * <p>wml011viewFlg を取得します。
     * @return wml011viewFlg
     */
    public boolean isWml011viewFlg() {
        return wml011viewFlg__;
    }

    /**
     * <p>wml011viewFlg をセットします。
     * @param wml011viewFlg wml011viewFlg
     */
    public void setWml011viewFlg(boolean wml011viewFlg) {
        wml011viewFlg__ = wml011viewFlg;
    }
}