package jp.groupsession.v2.rng;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議プラグインで共通使用するアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractRingiAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractRingiAction.class);
    /** プラグインID */
    private static final String PLUGIN_ID = RngConst.PLUGIN_ID_RINGI;
    /**
     *
     * <br>[機  能] ディレクトリIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return
     */
    protected String _getTempDirId() {
        return "";
    }

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return テンポラリディレクトリパス
     */
    protected GSTemporaryPathModel _getRingiDir(HttpServletRequest req) {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathModel tempDir = GSTemporaryPathModel.getInstance(getRequestModel(req), getPluginId(), _getTempDirId());
        log__.debug("テンポラリディレクトリ = " + tempDir.getTempPath());

        return tempDir;
    }

    /**
     * <p>エクセプションを元に遷移する画面を取得する。
     * @param map マップ
     * @param req リクエスト
     * @param e エクセプション
     * @param backForward 戻り先
     * @param cmn999Form メッセージ画面フォーム
     * @return ActionForward フォワード
     */
    public ActionForward getCatchExceptionPage(ActionMapping map, HttpServletRequest req,
            Exception e, ActionForward backForward, Cmn999Form cmn999Form) {
        ActionForward forward = null;
        ActionForward urlForward = backForward;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setUrlOK(urlForward.getPath());
        req.setAttribute("cmn999Form", cmn999Form);

        GsMessage gsMsg = new GsMessage();
        //稟議が存在しない
        if (e instanceof RngNotfoundException) {
            String msg = gsMsg.getMessage(req, "rng.62");
            //メッセージセット
            cmn999Form.setMessage(msgRes.getMessage("search.data.notfound", msg));
            forward = map.findForward("gf_msg");
        //稟議テンプレートが存在しない
        } else if (e instanceof RtpNotfoundException) {
            String msg = gsMsg.getMessage(req, "rng.92");
            //メッセージセット
            cmn999Form.setMessage(msgRes.getMessage("search.data.notfound", msg));
            forward = map.findForward("gf_msg");
        //稟議カテゴリの権限がない
        } else if (e instanceof RngCategoriCantAccessException) {
            RngCategoriCantAccessException rcaException = (RngCategoriCantAccessException) e;
            cmn999Form.setMessage(msgRes.getMessage("error.edit.power.user",
                    gsMsg.getMessage(rcaException.getMsgKeySeigenKbn()),
                    rcaException.getCantActionStr()
                    ));
            forward = map.findForward("gf_msg");
        } else if (e instanceof RngUnuseableKeiroException) {
            RngUnuseableKeiroException rukException = (RngUnuseableKeiroException) e;
            cmn999Form.setMessage(msgRes.getMessage("error.keiro.unuseable.soukatu",
                    rukException.getReason()
                    ));
            forward = map.findForward("gf_msg");
        } else if (e instanceof RtpUnuseableInputException) {
            RtpUnuseableInputException rukException = (RtpUnuseableInputException) e;
            cmn999Form.setMessage(msgRes.getMessage("error.unuseable.rtp.input.soukatu",
                    rukException.getReason()
                    ));
            forward = map.findForward("gf_msg");
        } else if (e instanceof RngMoveConfimationKeiroException) {
            cmn999Form.setMessage(msgRes.getMessage("error.move.connfimation.ringi"));
            forward = map.findForward("gf_msg");
        } else if (e instanceof RngNotDairiAccountAccessException) {
            cmn999Form.setMessage(msgRes.getMessage("error.dairi.access.kengen"));
            forward = map.findForward("gf_msg");
        // テンプレート使用制限がかかっている
        } else if (e instanceof RngNotAcceptTemplateException) {
            cmn999Form.setMessage(msgRes.getMessage("error.not.accept.template"));
            forward = map.findForward("gf_msg");
        } else {
            cmn999Form.setIcon(Cmn999Form.ICON_INFO);
            forward = map.findForward("gf_submit");
        }
        return forward;
    }

}