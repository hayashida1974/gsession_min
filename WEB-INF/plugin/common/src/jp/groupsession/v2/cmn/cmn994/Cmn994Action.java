package jp.groupsession.v2.cmn.cmn994;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.cache.FileCache;
import jp.co.sjts.util.cache.FileCacheConfig;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ファイルキャッシュのリセット、及び設定再読み込み画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn994Action extends AdminAction {
    /** ファイルキャッシュ 設定ファイル名 */
    private static final String CONF_FILENAME__ = "ehcache.xml";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn994Action.class);

    /**
     * <p>セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                     org.apache.struts.action.ActionForm,
     *                     javax.servlet.http.HttpServletRequest,
     *                     javax.servlet.http.HttpServletResponse,
     *                     java.sql.Connection)
    */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        log__.info("ファイルキャッシュのリセット、及び設定再読み込み");

        FileCache.reset();
        FileCache.setConfFile(__getConfFilePath());
        FileCacheConfig.readConfig(getAppRootPath());

        ActionForward forward = map.findForward("gf_msg");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        //URL
        urlForward = map.findForward("gf_menu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        //データベース設定の再読込
        String textDataBaseSettei = gsMsg.getMessage(req, "cmn.cmn994.1");

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("cmn.kanryo.object", textDataBaseSettei));

        req.setAttribute("cmn999Form", cmn999Form);


        return forward;
    }

    /**
     * <br>[機  能] ファイルキャッシュ設定ファイルのファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 設定ファイルのファイルパス
     */
    private static String __getConfFilePath() {
        String appRootPath = (String) GroupSession.getContext().get(GSContext.APP_ROOT_PATH);
        String confFilePath = IOTools.setEndPathChar(appRootPath);
        confFilePath += "/WEB-INF/conf/";
        confFilePath += CONF_FILENAME__;
        return confFilePath;
    }
}