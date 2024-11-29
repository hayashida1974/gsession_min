package jp.groupsession.v2.adr.adr241;

import java.util.List;

import jp.groupsession.v2.adr.adr240.Adr240Form;
import jp.groupsession.v2.adr.adr241.model.Adr241AddressModel;

/**
 * <br>[機  能] アドレス帳 会社選択画面 担当者一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr241Form extends Adr240Form {

    /** アドレス帳情報一覧 */
    private List<Adr241AddressModel> addressList__ = null;

    /**
     * <p>addressList を取得します。
     * @return addressList
     */
    public List<Adr241AddressModel> getAddressList() {
        return addressList__;
    }

    /**
     * <p>addressList をセットします。
     * @param addressList addressList
     */
    public void setAddressList(List<Adr241AddressModel> addressList) {
        addressList__ = addressList;
    }

}