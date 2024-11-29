package jp.groupsession.v2.dba;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.struts.AbstractGsAction;

import org.apache.struts.action.ActionForm;


/**
 * <br>[機  能] Dbaで使用するアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractDbaAction extends AbstractGsAction {
    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>DBAでは管理者のみ使用可能
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
}