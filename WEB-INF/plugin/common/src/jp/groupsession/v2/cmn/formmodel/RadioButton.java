package jp.groupsession.v2.cmn.formmodel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] ラジオ要素 モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RadioButton extends SimpleSelectBase {
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.radio;
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean empty = StringUtil.isNullZeroString(getSelected());
        if (empty) {
            msg = new ActionMessage("error.select.required.text",
                    info.outputName(gsMsg));
            errors.add(info.getPath(), msg);
        }

        super.validateCheck(errors, reqMdl, info);
    }
}
