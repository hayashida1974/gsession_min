package jp.groupsession.v2.wml.restapi.accounts.mails.open_condition;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsMultiParamModel;

/**
 * <br>[機  能] WEBメール メールを既読または未読にする 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsOpenConditionPutParamModel extends WmlAccountsMailsMultiParamModel {

    /** 開封済みフラグ 未開封 */
    public static final int OPENFLG_NO = 0;
    /** 開封済みフラグ 開封済み */
    public static final int OPENFLG_YES = 1;

    /** 開封済みフラグ */
    @Selectable({"0", "1"})
    private int openFlg__ = OPENFLG_YES;

    public int getOpenFlg() {
        return openFlg__;
    }

    public void setOpenFlg(int openFlg) {
        openFlg__ = openFlg;
    }
}
