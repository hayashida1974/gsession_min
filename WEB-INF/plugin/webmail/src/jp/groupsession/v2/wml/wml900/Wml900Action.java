package jp.groupsession.v2.wml.wml900;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.util.WmlConfigBundle;

/**
 * <br>[機  能] WEBメール設定ファイル再読み込みのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml900Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml900Action.class);

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

        log__.debug("-- WEBメール設定ファイルの再読み込み開始 --");

        String appRootPath = getAppRootPath();
        WmlConfigBundle.readConfig(appRootPath);

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
        //メッセージ
        cmn999Form.setMessage(getInterMessage(req, "wml.129")
                //メール自動受信時の最大スレッド数
                + getInterMessage(req, "wml.230")
                + WmlBiz.getMaxReceiveThreadCount(getAppRootPath())
                //一度に受信できるメールの最大件数
                + getInterMessage(req, "wml.117")
                + WmlBiz.getMaxReceiveMailCount(getAppRootPath())
                //受信サーバーへの接続タイムアウト時間(秒)
                + "<br>" + getInterMessage(req, "wml.255") + " : "
                + WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_RECEIVE_CONNECTTIMEOUT,
                        GSConstWebmail.RECEIVE_CONNECTTIMEOUT_DEFAULT)
                //メール受信のタイムアウト時間(秒)
                + "<br>" + getInterMessage(req, "wml.256") + " : "
                + WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_RECEIVE_TIMEOUT,
                        GSConstWebmail.RECEIVE_TIMEOUT_DEFAULT)
                //メール受信 受信サイズ比較基準時間(分)
                + "<br>" + getInterMessage(req, "wml.257") + " : "
                + WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_MAILRECEIVE_RCVSVR_CHECKTIME,
                        GSConstWebmail.RECEIVE_RCVSVR_CHECKTIME_DEFAULT)
                //受信メール、送信メール本文の最大文字数
                + "<br>" + getInterMessage(req, "wml.258") + " : "
                + WmlBiz.getBodyLimitLength(appRootPath)
                //メール情報一括削除の最大スレッド数
                + "<br>" + getInterMessage(req, "wml.317") + " : "
                + WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_DELETE_THREAD_MAXCOUNT,
                        GSConstWebmail.DELETE_THREAD_MAXCOUNT_DEFAULT)
                //メール情報一括削除の削除件数上限
                + "<br>" + getInterMessage(req, "wml.318") + " : "
                + WmlBiz.getConfValue(appRootPath,
                        GSConstWebmail.MAILCONF_DELETE_MAIL_LIMIT,
                        GSConstWebmail.DELETE_MAIL_LIMIT_DEFAULT)
            );

        req.setAttribute("cmn999Form", cmn999Form);
        log__.debug("-- WEBメール設定ファイルの再読み込み完了 --");

        return forward;
    }
}
