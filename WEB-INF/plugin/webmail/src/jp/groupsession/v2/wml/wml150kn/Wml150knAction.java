package jp.groupsession.v2.wml.wml150kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWebmailSubAction;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール アカウント基本設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150knAction extends AbstractWebmailSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml150knAction.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Wml150knForm thisForm = (Wml150knForm) form;

        //管理者権限チェック
        if (!_checkAuth(map, req, con)) {
            return map.findForward("gf_power");
        }

        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("decision")) {
            //確定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);

        } else if (cmd.equals("backInput")) {
            //戻るボタンクリック
            forward = map.findForward("backInput");

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Wml150knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Wml150knParamModel paramMdl = new Wml150knParamModel();
        paramMdl.setParam(form);
        Wml150knBiz biz = new Wml150knBiz();
        biz.setInitData(paramMdl, getRequestModel(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doDecision(ActionMapping map, Wml150knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Wml150knParamModel paramMdl = new Wml150knParamModel();
        paramMdl.setParam(form);
        Wml150knBiz biz = new Wml150knBiz(con);
        biz.setData(paramMdl, getSessionUserSid(req));

        //ログ出力
        WmlBiz wmlBiz = new WmlBiz();
        String msg = __createMessage(map, req, form);
        wmlBiz.outPutLog(map, getRequestModel(req), con,
                getInterMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO,
                msg);

        __setCompPageParam(map, req, form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] オペレーションログ メッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return メッセージ
     */
    private String __createMessage(
        ActionMapping map,
        HttpServletRequest req,
        Wml150knForm form) {
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        String msg = "";
        //基本設定
        msg += gsMsg.getMessage("cmn.preferences");
        //アカウントの作成
        msg += "\r\n[" + gsMsg.getMessage("wml.101") + "]";
        if (form.getWml150acntMakeKbn() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150acntMakeKbn() == 1) {
            msg += gsMsg.getMessage("wml.70");
            //サーバ情報の設定
            msg += "\r\n[" + gsMsg.getMessage("wml.253") + "]";
            if (form.getWml150settingServer() == 0) {
                msg += gsMsg.getMessage("cmn.not.permit");
            } else if (form.getWml150settingServer() == 1) {
                msg += gsMsg.getMessage("cmn.permit");
            }
        }
        //送信メール形式
        msg += "\r\n[" + gsMsg.getMessage("cmn.format.") + "]";
        if (form.getWml150acntSendFormat() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150acntSendFormat() == 1) {
            msg += gsMsg.getMessage("wml.104");
        } else if (form.getWml150acntSendFormat() == 2) {
            msg += gsMsg.getMessage("wml.109");
        }
        //送受信ログの登録
        msg += "\r\n[" + gsMsg.getMessage("wml.23") + "]";
        if (form.getWml150acntLogRegist() == 0) {
            msg += gsMsg.getMessage("wml.12");
        } else if (form.getWml150acntLogRegist() == 1) {
            msg += gsMsg.getMessage("cmn.dont.entry");
        }
        //メール受信サーバ
        msg += "\r\n[" + gsMsg.getMessage("wml.154") + "]";
        if (form.getWml150receiveServer().length() > 0) {
            msg += form.getWml150receiveServer() + " ";
        }
        if (form.getWml150receiveServerPort().length() > 0) {
            msg += gsMsg.getMessage("cmn.port.number") + ":"
                       + form.getWml150receiveServerPort();
        }
        if (form.getWml150receiveServerEncrypt() > 0) {
            if (form.getWml150receiveServer().length() > 0
                    || form.getWml150receiveServerPort().length() > 0) {
                msg += "\r\n";
            }
            msg += gsMsg.getMessage("wml.296");
        }
        //メール送信サーバ
        msg += "\r\n[" + gsMsg.getMessage("wml.80") + "]";
        if (form.getWml150sendServer().length() > 0) {
            msg += form.getWml150sendServer() + " ";
        }
        if (form.getWml150sendServerPort().length() > 0) {
            msg += gsMsg.getMessage("cmn.port.number") + ":"
                       + form.getWml150sendServerPort();
        }
        if (form.getWml150sendServerEncrypt() > 0) {
            if (form.getWml150sendServerPort().length() > 0
                    || form.getWml150sendServer().length() > 0) {
                msg += "\r\n";
            }
            msg += gsMsg.getMessage("wml.296");
        }
        //送信メールサイズ制限
        msg += "\r\n[" + gsMsg.getMessage("wml.246") + "]";
        if (form.getWml150sendMaxSizeKbn() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150sendMaxSizeKbn() == 1) {
            msg += gsMsg.getMessage("wml.32");
            if (form.getWml150sendMaxSize().length() > 0) {
                msg += " " + form.getWml150sendMaxSize() + gsMsg.getMessage("wml.wml040.07");
            }
        }
        //BCC強制変換
        msg += "\r\n[" + gsMsg.getMessage("wml.wml150.13") + "]";
        if (form.getWml150bcc() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150bcc() == 1) {
            msg += gsMsg.getMessage("cmn.coerced");
            msg += " " + gsMsg.getMessage("cmn.threshold") + ":"
                       + form.getWml150bccThreshold() + gsMsg.getMessage("cmn.number");
        }
        //代理人
        msg += "\r\n[" + gsMsg.getMessage("cmn.proxyuser") + "]";
        if (form.getWml150proxyUser() == 0) {
            msg += gsMsg.getMessage("cmn.not.permit");
        } else if (form.getWml150proxyUser() == 1) {
            msg += gsMsg.getMessage("cmn.permit");
        }
        //メール転送制限
        msg += "\r\n[" + gsMsg.getMessage("wml.wml150.10") + "]";
        if (form.getWml150FwLimit() == 0) {
            msg += gsMsg.getMessage("wml.transfer.limit.01");
        } else if (form.getWml150FwLimit() == 1) {
            msg += gsMsg.getMessage("wml.transfer.limit.02");
            msg += "\r\n" + form.getWml150FwLimitText();
        } else if (form.getWml150FwLimit() == 2) {
            msg += gsMsg.getMessage("wml.transfer.limit.03");
            if (form.getWml150FwLimitDelete() == 1) {
                msg += "\r\n" + gsMsg.getMessage("wml.transfer.limit.04");
            }
        }
        //リンク制限
        msg += "\r\n[" + gsMsg.getMessage("wml.wml150.18") + "]";
        if (form.getWml150linkLimit() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150linkLimit() == 1) {
            msg += gsMsg.getMessage("wml.32");
        }
        //トップレベルドメイン制限
        msg += "\r\n[" + gsMsg.getMessage("wml.wml150.20") + "]";
        if (form.getWml150TldLimit() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150TldLimit() == 1) {
            msg += gsMsg.getMessage("wml.32");
        }

        //詳細設定
        msg += "\r\n" + gsMsg.getMessage("cmn.detail.setting");
        //権限選択
        msg += "\r\n[" + gsMsg.getMessage("wml.permit.select") + "]";
        if (form.getWml150TldLimit() == 0) {
            msg += gsMsg.getMessage("cmn.set.the.admin");
        } else if (form.getWml150TldLimit() == 1) {
            msg += gsMsg.getMessage("cmn.set.eachaccount");
        }
        //ディスク容量
        msg += "\r\n[" + gsMsg.getMessage("wml.87") + "]";
        if (form.getWml150diskSize() == 0) {
            msg += gsMsg.getMessage("wml.31");
        } else if (form.getWml150diskSize() == 1) {
            msg += gsMsg.getMessage("wml.32");
            if (form.getWml150sendMaxSize().length() > 0) {
                msg += " " + form.getWml150diskSizeLimit() + gsMsg.getMessage("wml.wml040.07");
            }
            if (form.isWml150diskSizeComp()) {
                msg += " " + gsMsg.getMessage("cmn.force");
            }
        }
        //ディスク容量警告
        msg += "\r\n[" + gsMsg.getMessage("wml.wml150.15") + "]";
        if (form.getWml150warnDisk() == 0) {
            msg += gsMsg.getMessage("cmn.notset");
        } else if (form.getWml150warnDisk() == 1) {
            msg += gsMsg.getMessage("cmn.warning2");
            msg += " " + gsMsg.getMessage("cmn.threshold") + ":"
                        + form.getWml150warnDiskThreshold() + "%";
        }
        //受信時削除
        msg += "\r\n[" + gsMsg.getMessage("wml.36") + "]";
        if (form.getWml150delReceive() == 0) {
            msg += gsMsg.getMessage("cmn.dont.delete");
        } else if (form.getWml150delReceive() == 1) {
            msg += gsMsg.getMessage("wml.60");
        }
        //自動受信
        msg += "\r\n[" + gsMsg.getMessage("wml.50") + "]";
        if (form.getWml150autoResv() == 0) {
            msg += gsMsg.getMessage("wml.49");
        } else if (form.getWml150autoResv() == 1) {
            msg += gsMsg.getMessage("wml.48");
            //自動受信間隔
            msg += "\r\n[" + gsMsg.getMessage("wml.auto.receive.time") + "]"
                       + form.getWml150AutoReceiveTime() + gsMsg.getMessage("cmn.minute");
        }
        //宛先の確認
        msg += "\r\n[" + gsMsg.getMessage("wml.238") + "]";
        if (form.getWml150checkAddress() == 0) {
            msg += gsMsg.getMessage("cmn.notcheck");
        } else if (form.getWml150checkAddress() == 1) {
            msg += gsMsg.getMessage("cmn.check.2");
        }
        //添付ファイルの確認
        msg += "\r\n[" + gsMsg.getMessage("wml.239") + "]";
        if (form.getWml150checkFile() == 0) {
            msg += gsMsg.getMessage("cmn.notcheck");
        } else if (form.getWml150checkFile() == 1) {
            msg += gsMsg.getMessage("cmn.check.2");
        }
        //添付ファイル自動圧縮
        msg += "\r\n[" + gsMsg.getMessage("wml.240") + "]";
        if (form.getWml150compressFile() == 0) {
            msg += gsMsg.getMessage("cmn.not.compress");
        } else if (form.getWml150compressFile() == 1) {
            msg += gsMsg.getMessage("cmn.compress");
        } else if (form.getWml150compressFile() == 2) {
            msg += gsMsg.getMessage("cmn.setting.from.screen");
            //添付ファイル自動圧縮 初期値
            msg += "\r\n[" + gsMsg.getMessage("wml.240") + " "
                       + gsMsg.getMessage("wml.wml150.24") + "]";
            if (form.getWml150compressFileDef() == 0) {
                msg += gsMsg.getMessage("cmn.not.compress");
            } else if (form.getWml150compressFileDef() == 1) {
                msg += gsMsg.getMessage("cmn.compress");
            }
        }
        //時間差送信
        msg += "\r\n[" + gsMsg.getMessage("wml.241") + "]";
        if (form.getWml150timeSent() == 0) {
            msg += gsMsg.getMessage("cmn.invalid");
        } else if (form.getWml150timeSent() == 1) {
            msg += gsMsg.getMessage("cmn.effective");
        } else if (form.getWml150timeSent() == 2) {
            msg += gsMsg.getMessage("cmn.setting.from.screen");
            //時間差送信 初期値
            msg += "\r\n[" + gsMsg.getMessage("wml.241") + " "
                         + gsMsg.getMessage("wml.wml150.24") + "]";
            if (form.getWml150timeSentDef() == 0) {
                msg += gsMsg.getMessage("wml.276");
            } else if (form.getWml150timeSentDef() == 1) {
                msg += gsMsg.getMessage("wml.241");
            }
        }
        return msg;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Wml150knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("admTool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        msgState = "touroku.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.preferences")));

        //画面パラメータをセット
        form.setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);
    }
}
