package jp.groupsession.v2.cmn.formmodel.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
*
* <br>[機  能] ユーザグループ選択ダイアログ アクション
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class GroupSelDialogAction extends AbstractGsAction {

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        GroupSelDialogForm thisForm = (GroupSelDialogForm) form;

        thisForm.getGrpsel().dspInit(getRequestModel(req), con);

        if (!StringUtil.isNullZeroString(thisForm.getResultFlg())) {
            return map.getInputForward();
        }

        if (thisForm.getPickerFlg()
                == 1) {
            return map.findForward("picker");
        }

        return map.getInputForward();
    }

}
