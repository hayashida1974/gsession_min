package jp.groupsession.v2.adr.adr150;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.AbstractAddressAction;
import jp.groupsession.v2.adr.adr150.model.Adr150SearchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] アドレス帳 会社選択のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr150Action extends AbstractAddressAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr150Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START");

        ActionForward forward = null;

        Adr150Form thisForm = (Adr150Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("*****戻り先：" + thisForm.getAdr150ReturnPage());
        if (cmd.equals("search")) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("backInputAddress")) {
            log__.debug("戻るボタンクリック");
//            forward = map.findForward("inputAddress");
            forward = __doBack(map, thisForm, req, res);
        } else if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setAdr150page(thisForm.getAdr150page() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setAdr150page(thisForm.getAdr150page() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Adr150Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Adr150Biz biz = new Adr150Biz(getRequestModel(req));

        Adr150ParamModel paramMdl = new Adr150ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(con, paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 戻り処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doBack(
        ActionMapping map,
        Adr150Form form,
        HttpServletRequest req,
        HttpServletResponse res) {

        ActionForward forward = map.findForward("inputAddress");
        if (form.getAdr150ReturnPage().equals("ntp061")) {
            forward = map.findForward("ntp061");
        } else if (form.getAdr150ReturnPage().equals("ntp060")) {
            forward = map.findForward("ntp060");
        }

        return forward;
    }
    /**
     * <br>[機  能] 検索ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doSearch(ActionMapping map,
                                    Adr150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {


        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        GsMessage gsMsg = new GsMessage();
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {

            //検索結果の件数を確認する
            Adr150Biz biz = new Adr150Biz(getRequestModel(req));
            Adr150SearchModel searchMdl = new Adr150SearchModel();
            //企業コード
            searchMdl.setCoCode(form.getAdr150code());
            //会社名
            searchMdl.setCoName(form.getAdr150coName());
            //会社名カナ
            searchMdl.setCoNameKn(form.getAdr150coNameKn());
            //支店・営業所名
            searchMdl.setCoBaseName(form.getAdr150coBaseName());
            //業種
            searchMdl.setAtiSid(form.getAdr150atiSid());
            //都道府県
            searchMdl.setTdfk(form.getAdr150tdfk());
            //備考
            searchMdl.setBiko(form.getAdr150biko());

            //検索結果件数の確認
            con.setAutoCommit(true);
            int searchCount = biz.getSearchCount(con, searchMdl);
            con.setAutoCommit(false);
            if (searchCount <= 0) {
                ActionMessage msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage(req, "address.118"));
                StrutsUtil.addMessage(errors, msg, "companySearch");
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            //企業コード
            form.setAdr150svCode(form.getAdr150code());
            //会社名
            form.setAdr150svCoName(form.getAdr150coName());
            //会社名カナ
            form.setAdr150svCoNameKn(form.getAdr150coNameKn());
            //支店・営業所名
            form.setAdr150svCoBaseName(form.getAdr150coBaseName());
            //業種
            form.setAdr150svAtiSid(form.getAdr150atiSid());
            //都道府県
            form.setAdr150svTdfk(form.getAdr150tdfk());
            //備考
            form.setAdr150svBiko(form.getAdr150biko());

            //ページ
            form.setAdr150page(1);
            form.setAdr150searchFlg(1);

        }

        return __doInit(map, form, req, res, con);
    }

}
