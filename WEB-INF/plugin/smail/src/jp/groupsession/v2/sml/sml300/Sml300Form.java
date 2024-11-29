package jp.groupsession.v2.sml.sml300;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.sml290.Sml290Form;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml300Form extends Sml290Form {
    /** ラベル名 */
    private String sml300LabelName__ = null;
    /** 初期表示区分 */
    private int sml300initKbn__ = 0;

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

        GSValidateSmail.validateTextBoxInput(errors, sml300LabelName__,
            "sml300LabelName",
            getInterMessage(req, GSConstSmail.TEXT_LABEL),
            GSConstSmail.MAXLEN_SEARCH_KEYWORD, true);

        return errors;
    }

    /**
     * <p>sml300LabelName を取得します。
     * @return sml300LabelName
     */
    public String getSml300LabelName() {
        return sml300LabelName__;
    }

    /**
     * <p>sml300LabelName をセットします。
     * @param sml300LabelName sml300LabelName
     */
    public void setSml300LabelName(String sml300LabelName) {
        sml300LabelName__ = sml300LabelName;
    }

    /**
     * <p>sml300initKbn を取得します。
     * @return sml300initKbn
     */
    public int getSml300initKbn() {
        return sml300initKbn__;
    }

    /**
     * <p>sml300initKbn をセットします。
     * @param sml300initKbn sml300initKbn
     */
    public void setSml300initKbn(int sml300initKbn) {
        sml300initKbn__ = sml300initKbn;
    }

}
