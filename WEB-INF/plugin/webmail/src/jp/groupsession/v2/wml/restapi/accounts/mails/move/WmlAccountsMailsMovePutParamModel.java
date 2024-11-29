package jp.groupsession.v2.wml.restapi.accounts.mails.move;

import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsMultiParamModel;

/**
 * <br>[機  能] WEBメール メールを移動する 取得用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class WmlAccountsMailsMovePutParamModel extends WmlAccountsMailsMultiParamModel {

    /** 移動先ディレクトリタイプ */
    @NotBlank
    @Selectable({"inbox", "sent", "future", "draft", "trash", "keep"})
    private String directoryType__ = null;

    public String getDirectoryType() {
        return directoryType__;
    }

    public void setDirectoryType(String directoryType) {
        directoryType__ = directoryType;
    }
}
