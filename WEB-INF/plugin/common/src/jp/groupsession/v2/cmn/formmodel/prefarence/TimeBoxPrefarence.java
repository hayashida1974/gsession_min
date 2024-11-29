package jp.groupsession.v2.cmn.formmodel.prefarence;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.TimeBox;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

public class TimeBoxPrefarence extends TimeBox {

  /**
   *
   * <br>[機  能] 入力値のバリデートチェックを行う
   * <br>[解  説]
   * <br>[備  考] dspInitを実行済みである必要がある
   * @param errors エラー格納用アクションエラー
   * @param reqMdl リクエストモデル
   * @param info バリデート情報
   */
  public void validateCheck(ActionErrors errors, RequestModel reqMdl, ValidateInfo info) {
      GsMessage gsMsg = new GsMessage(reqMdl);
      GSValidateCommon.validateTimeFieldText(errors,
            getDefaultValue(),
            info.outputCode(),
            gsMsg.getMessage("ntp.10"),
            false);

  }

}
