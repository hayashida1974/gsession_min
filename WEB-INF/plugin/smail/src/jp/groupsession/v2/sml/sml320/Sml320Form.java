package jp.groupsession.v2.sml.sml320;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.sml310.Sml310Form;


import org.apache.struts.action.ActionErrors;


/**
 * <br>[機  能] WEBメール ラベル登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml320Form extends Sml310Form {
    /** ラベル名 */
    private String sml320LabelName__ = null;
    /** 初期表示区分 */
    private int sml320initKbn__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) throws Exception {
        ActionErrors errors = new ActionErrors();

        GSValidateSmail.validateTextBoxInput(errors, sml320LabelName__,
            "sml320LabelName",
            getInterMessage(req, GSConstSmail.TEXT_LABEL),
            GSConstSmail.MAXLEN_SEARCH_KEYWORD, true);

        return errors;
    }

    /**
     * <p>sml320LabelName を取得します。
     * @return sml320LabelName
     */
    public String getSml320LabelName() {
        return sml320LabelName__;
    }

    /**
     * <p>sml320LabelName をセットします。
     * @param sml320LabelName sml320LabelName
     */
    public void setSml320LabelName(String sml320LabelName) {
        sml320LabelName__ = sml320LabelName;
    }

    /**
     * <p>sml320initKbn を取得します。
     * @return sml320initKbn
     */
    public int getSml320initKbn() {
        return sml320initKbn__;
    }

    /**
     * <p>sml320initKbn をセットします。
     * @param sml320initKbn sml320initKbn
     */
    public void setSml320initKbn(int sml320initKbn) {
        sml320initKbn__ = sml320initKbn;
    }
}
