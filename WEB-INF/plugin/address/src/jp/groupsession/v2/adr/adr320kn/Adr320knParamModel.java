package jp.groupsession.v2.adr.adr320kn;

import java.util.List;

import jp.groupsession.v2.adr.adr320.Adr320ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr320knParamModel extends Adr320ParamModel {
    /** アンケート登録許可対象者リスト(画面表示用) */
    private List<UsrLabelValueBean> adr320knAdrArestViewList__ = null;

    /**
     * <p>adr320knAdrArestViewList を取得します。
     * @return adr320knAdrArestViewList
     */
    public List<UsrLabelValueBean> getAdr320knAdrArestViewList() {
        return adr320knAdrArestViewList__;
    }

    /**
     * <p>adr320knAdrArestViewList をセットします。
     * @param adr320knAdrArestViewList adr320knAdrArestViewList
     */
    public void setAdr320knAdrArestViewList(
            List<UsrLabelValueBean> adr320knAdrArestViewList) {
        adr320knAdrArestViewList__ = adr320knAdrArestViewList;
    }
}
