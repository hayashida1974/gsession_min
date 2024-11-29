package jp.groupsession.v2.sml.sml060;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.sml050.Sml050Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール ひな形登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml060Form extends Sml050Form {

    /**  ひな形一覧のアカウント名 */
    private String sml060AccountName__;

    /** 入力チェックモード */
    public static final int VALIDATE_MODE_HINA = 0;
    /** ひな形名称 */
    private String sml060HinaName__;
    /** 件名 */
    private String sml060HinaTitle__;
    /** マーク */
    private int sml060HinaMark__;
    /** 本文 */
    private String sml060HinaBody__;

    /**
     * @return sml060HinaBody を戻します。
     */
    public String getSml060HinaBody() {
        return sml060HinaBody__;
    }
    /**
     * @param sml060HinaBody 設定する sml060HinaBody。
     */
    public void setSml060HinaBody(String sml060HinaBody) {
        sml060HinaBody__ = sml060HinaBody;
    }
    /**
     * @return sml060HinaMark を戻します。
     */
    public int getSml060HinaMark() {
        return sml060HinaMark__;
    }
    /**
     * @param sml060HinaMark 設定する sml060HinaMark。
     */
    public void setSml060HinaMark(int sml060HinaMark) {
        sml060HinaMark__ = sml060HinaMark;
    }
    /**
     * @return sml060HinaName を戻します。
     */
    public String getSml060HinaName() {
        return sml060HinaName__;
    }
    /**
     * @param sml060HinaName 設定する sml060HinaName。
     */
    public void setSml060HinaName(String sml060HinaName) {
        sml060HinaName__ = sml060HinaName;
    }
    /**
     * @return sml060HinaTitle を戻します。
     */
    public String getSml060HinaTitle() {
        return sml060HinaTitle__;
    }
    /**
     * @param sml060HinaTitle 設定する sml060HinaTitle。
     */
    public void setSml060HinaTitle(String sml060HinaTitle) {
        sml060HinaTitle__ = sml060HinaTitle;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @return errors エラー
     */
    public ActionErrors validateCheck060(RequestModel reqMdl, String appRootPath) {

        ActionErrors errors = new ActionErrors();

        //ひな形名称
        GSValidateSmail.validateHinaName(errors, sml060HinaName__, reqMdl);
        //件名
        GSValidateSmail.validateSmlTitle(errors, sml060HinaTitle__, reqMdl);
        //本文
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateCommon.validateTextAreaField(errors, sml060HinaBody__,
                "body", gsMsg.getMessage("cmn.body"),
                GSConstCommon.MAX_LENGTH_SMLBODY, true);

        return errors;
    }
    /**
     * <p>sml060AccountName を取得します。
     * @return sml060AccountName
     */
    public String getSml060AccountName() {
        return sml060AccountName__;
    }
    /**
     * <p>sml060AccountName をセットします。
     * @param sml060AccountName sml060AccountName
     */
    public void setSml060AccountName(String sml060AccountName) {
        sml060AccountName__ = sml060AccountName;
    }
}