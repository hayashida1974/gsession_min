package jp.groupsession.v2.cmn.restapi.groups;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
/**
 *
 * <br>[機  能] グループ情報API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class CmnGroupsGetParamModel  {
    /** 取得開始日 */
    @Selectable({"0", "1"})
    private int withMygroupFlg__ = 0;

    /**
     * <p>withMygroupFlg を取得します。
     * @return withMygroupFlg
     * @see jp.groupsession.v2.cmn.restapi.groups.CmnGroupsGetParamModel#withMygroupFlg
     */
    public int getWithMygroupFlg() {
        return withMygroupFlg__;
    }

    /**
     * <p>withMygroupFlg をセットします。
     * @param withMygroupFlg withMygroupFlg
     * @see jp.groupsession.v2.cmn.restapi.groups.CmnGroupsGetParamModel#withMygroupFlg
     */
    public void setWithMygroupFlg(int withMygroupFlg) {
        this.withMygroupFlg__ = withMygroupFlg;
    }
}
