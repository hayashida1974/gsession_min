package jp.groupsession.v2.man.man300kn;

import java.util.ArrayList;

import jp.groupsession.v2.man.man300.Man300Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] メイン インフォメーション 管理者設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man300knForm extends Man300Form {

    /** インフォメーション管理者リスト */
    private ArrayList <UsrLabelValueBean> man300knKoukaiList__ = null;

    /**
     * @return the man300knKoukaiList
     */
    public ArrayList<UsrLabelValueBean> getMan300knKoukaiList() {
        return man300knKoukaiList__;
    }

    /**
     * @param man300knKoukaiList the man300knKoukaiList to set
     */
    public void setMan300knKoukaiList(ArrayList<UsrLabelValueBean> man300knKoukaiList) {
        man300knKoukaiList__ = man300knKoukaiList;
    }

}
