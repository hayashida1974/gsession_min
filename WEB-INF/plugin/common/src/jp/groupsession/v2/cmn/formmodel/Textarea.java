package jp.groupsession.v2.cmn.formmodel;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 *
 * <br>[機  能] テキストエリア モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Textarea extends TextInput {

    /**
     *
     */
    public Textarea() {
        super();
        setMaxlength("1000");
    }

    /**
     * @param json json
     */
    public Textarea(JSONObject json) {
        super(json);
        // TODO 自動生成されたコンストラクター・スタブ
    }
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.textarea;
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCommon.validateTextAreaField(errors,
                getValue(),
                info.outputCode(),
                info.outputName(gsMsg),
                Integer.parseInt(getMaxlength()),
                info.chkRequire());
    }

}
