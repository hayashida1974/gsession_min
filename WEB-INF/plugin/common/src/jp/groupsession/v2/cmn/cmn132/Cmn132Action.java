package jp.groupsession.v2.cmn.cmn132;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MyGroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 個人設定 共有マイグループ確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn132Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn132Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception その他例外
     * @return ActionForward
     */
    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, Exception {
        log__.debug("START_Cmn132");
        ActionForward forward = null;
        Cmn132Form thisForm = (Cmn132Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("backToInput")) {
            log__.debug("戻る");
            forward = map.findForward("backToInput");
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cmn132kn");
        return forward;

    }
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 例外
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cmn132Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        //参照権限確認
        MyGroupDao mgDao = new MyGroupDao(con);
        if (!mgDao.isAbleViewMyGroup(
                NullDefault.getInt(form.getCmn130selectGroupSid(), 0), sessionUsrSid)
                ) {
            return __doNoneDataError(map, form, req, res, con);
        }
        //初期表示情報を画面にセットする
        Cmn132Biz biz = new Cmn132Biz(new GsMessage(reqMdl));
        Cmn132ParamModel paramModel = new Cmn132ParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con, sessionUsrSid);
        paramModel.setFormData(form);

        return map.getInputForward();
    }
    /**
     * <br>編集対象が無い場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doNoneDataError(ActionMapping map, Cmn132Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = map.findForward("backToList");
        GsMessage gsMsg = new GsMessage(req);

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        //マイグループ
        String textName = gsMsg.getMessage("cmn.mygroup");
        //参照
        String textChange = gsMsg.getMessage("cmn.reading");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("error.none.edit.data",
                textName, textChange));
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

}
