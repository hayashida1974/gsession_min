package jp.groupsession.v2.wml;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] WEBメールプラグインで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractWebmailAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractWebmailAction.class);

    /** プラグインID */
    private static final String PLUGIN_ID = "webmail";

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * <br>[機  能] 管理者権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考] 権限無しの場合はエラーメッセージページへのパラメータを設定
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @return 権限の有無 true:権限あり false:権限無し
     * @throws Exception 実行例外
     */
    protected boolean _checkAuth(ActionMapping map,
        HttpServletRequest req, Connection con)
        throws Exception {

        log__.debug("閲覧権限チェック START");

        BaseUserModel buMdl = getSessionUserModel(req);

        //管理者の場合は権限あり
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL);

        if (adminUser) {
            return true;
        }

        //権限なしの場合はメッセージページのパラメータを設定する
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("gf_power");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.access.power.user";
        cmn999Form.setMessage(msgRes.getMessage(msgState));

        req.setAttribute("cmn999Form", cmn999Form);

        log__.debug("閲覧権限チェック END");

        return false;
    }
}
