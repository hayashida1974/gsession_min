package jp.groupsession.v2.cmn.formmodel;

import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;

/**
 *
 * <br>[機  能] コンボボックス要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ComboBox extends SimpleSelectBase {
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.combo;
    }

}
