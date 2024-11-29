package jp.groupsession.v2.wml.wml040;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;

/**
 * <br>[機  能] WEBメール アカウント登録画面の[署名]情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml040SignModel extends AbstractModel {
    /** 署名情報一覧 */
    List<WmlAccountSignModel> signList__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Wml040SignModel() {
        signList__ = new ArrayList<WmlAccountSignModel>();
    }

    /**
     * <p>signList を取得します。
     * @return signList
     */
    public List<WmlAccountSignModel> getSignList() {
        return signList__;
    }
    /**
     * <p>signList をセットします。
     * @param signList signList
     */
    public void setSignList(List<WmlAccountSignModel> signList) {
        signList__ = signList;
    }
}