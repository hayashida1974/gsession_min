package jp.groupsession.v2.wml.wml250;

import java.util.List;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.wml240.Wml240Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール メールテンプレート登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml250Form extends Wml240Form {
    /** メールテンプレート名 */
    private String wml250TemplateName__ = null;
    /** 件名 */
    private String wml250Title__ = null;
    /** 内容 */
    private String wml250Content__ = null;
    /** 添付ファイル */
    private String[] wml250files__ = null;

    /** 初期表示区分 */
    private int wml250initKbn__ = 0;
    /** 内容 最大長 */
    private int contentMaxLen__ = 0;
    /** 添付ファイル情報ファイル */
    private List<LabelValueBean> fileList__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl, String appRootPath) throws Exception {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //テンプレート名
        GSValidateWebmail.validateTextBoxInput(errors, wml250TemplateName__,
            "wml250TemplateName",
            gsMsg.getMessage("anp.anp100.02"),
            GSConstWebmail.MAXLEN_SEARCH_KEYWORD, true);

        //件名
        GSValidateWebmail.validateTextBoxInput(errors, wml250Title__,
                "wml250Title",
                gsMsg.getMessage(GSConstWebmail.TEXT_NAME),
                GSConstWebmail.MAXLEN_MAIL_SUBJECT, false);

        //本文
        int bodyLimit = WmlBiz.getBodyLimitLength(appRootPath);
        if (bodyLimit == GSConstWebmail.MAILBODY_LIMIT_NOLIMIT) {
            bodyLimit = -1;
        }
        GSValidateWebmail.validateTextarea(errors, wml250Content__,
                "wml250Content",
                gsMsg.getMessage("cmn.body"),
                bodyLimit, false);

        return errors;
    }

    /**
     * <br>[機  能] 添付ファイル削除時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateFileDelete(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (wml250files__ == null || wml250files__.length <= 0) {
            ActionMessage msg
                = new ActionMessage("error.select.required.text",
                                                gsMsg.getMessage("cmn.attach.file"));
            StrutsUtil.addMessage(errors, msg, "wml250files");
        }

        return errors;
    }

    /**
     * <p>wml250TemplateName を取得します。
     * @return wml250TemplateName
     */
    public String getWml250TemplateName() {
        return wml250TemplateName__;
    }

    /**
     * <p>wml250TemplateName をセットします。
     * @param wml250TemplateName wml250TemplateName
     */
    public void setWml250TemplateName(String wml250TemplateName) {
        wml250TemplateName__ = wml250TemplateName;
    }

    /**
     * <p>wml250Content を取得します。
     * @return wml250Content
     */
    public String getWml250Content() {
        return wml250Content__;
    }

    /**
     * <p>wml250Content をセットします。
     * @param wml250Content wml250Content
     */
    public void setWml250Content(String wml250Content) {
        wml250Content__ = wml250Content;
    }

    /**
     * <p>wml250Title を取得します。
     * @return wml250Title
     */
    public String getWml250Title() {
        return wml250Title__;
    }

    /**
     * <p>wml250Title をセットします。
     * @param wml250Title wml250Title
     */
    public void setWml250Title(String wml250Title) {
        wml250Title__ = wml250Title;
    }

    /**
     * <p>wml250files を取得します。
     * @return wml250files
     */
    public String[] getWml250files() {
        return wml250files__;
    }

    /**
     * <p>wml250files をセットします。
     * @param wml250files wml250files
     */
    public void setWml250files(String[] wml250files) {
        wml250files__ = wml250files;
    }

    /**
     * <p>wml250initKbn を取得します。
     * @return wml250initKbn
     */
    public int getWml250initKbn() {
        return wml250initKbn__;
    }

    /**
     * <p>wml250initKbn をセットします。
     * @param wml250initKbn wml250initKbn
     */
    public void setWml250initKbn(int wml250initKbn) {
        wml250initKbn__ = wml250initKbn;
    }

    /**
     * <p>contentMaxLen を取得します。
     * @return contentMaxLen
     */
    public int getContentMaxLen() {
        return contentMaxLen__;
    }

    /**
     * <p>contentMaxLen をセットします。
     * @param contentMaxLen contentMaxLen
     */
    public void setContentMaxLen(int contentMaxLen) {
        contentMaxLen__ = contentMaxLen;
    }

    /**
     * <p>fileList を取得します。
     * @return fileList
     */
    public List<LabelValueBean> getFileList() {
        return fileList__;
    }

    /**
     * <p>fileList をセットします。
     * @param fileList fileList
     */
    public void setFileList(List<LabelValueBean> fileList) {
        fileList__ = fileList;
    }
}
