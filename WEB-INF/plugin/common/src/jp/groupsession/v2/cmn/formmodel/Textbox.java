package jp.groupsession.v2.cmn.formmodel;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 *
 * <br>[機  能] テキストボックス モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Textbox extends TextInput {

    /**
     *
     */
    public Textbox() {
        super();
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /**
     * @param json json
     */
    public Textbox(JSONObject json) {
        super(json);
        // TODO 自動生成されたコンストラクター・スタブ
    }
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.textbox;
    }
    /**
    *
    * <br>[機  能] value を画面表示用 HTMLとして出力する
    * <br>[解  説]
    * <br>[備  考]
    * @return 表示文字列
    */
    public String dspValueHTML() {
       return NullDefault.getString(getValue(), "");
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCommon.validateTextField(errors,
                getValue(),
                info.outputCode(),
                info.outputName(gsMsg),
                Integer.parseInt(getMaxlength()),
                info.chkRequire());
    }

}
