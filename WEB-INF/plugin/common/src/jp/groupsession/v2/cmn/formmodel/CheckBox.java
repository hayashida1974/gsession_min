package jp.groupsession.v2.cmn.formmodel;

import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;

/**
 *
 * <br>[機  能] チェックボックス要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CheckBox extends SimpleMultiSelect {
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.check;
    }
}
