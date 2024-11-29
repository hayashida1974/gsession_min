package jp.groupsession.v2.cmn.ui.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;

public class UIValidateException extends Exception {
    /** 入力チェックエラーキー（重複するエラーは表示されない） */
    private final String valErrKey__;
    /** エラー表示メッセージ */
    private final ActionMessage  errMsg__;
    /** 結合エラー */
    private final List<UIValidateException> unionList__  = new ArrayList<>();
    /**
     *
     * コンストラクタ
     * @param valErrKey 入力チェックエラーキー（重複するエラーは表示されない）
     * @param actionMessage エラー表示メッセージ
     */
    public UIValidateException(String valErrKey, ActionMessage actionMessage) {
        valErrKey__ = valErrKey;
        errMsg__ = actionMessage;
    }
    /**
     *
     * <br>[機  能] 入力チェック例外の結合
     * <br>[解  説]
     * <br>[備  考]
     * @param e1 入力チェック例外1
     * @param e2 入力チェック例外2
     * @return 入力チェック例外（内部に入力チェック例外1と入力チェック例外2を保持）
     */
    public static UIValidateException union(UIValidateException e1,
            UIValidateException e2) {
        UIValidateException desc, add = null;
        desc = e1;
        if (e1 == null) {
            desc = e2;
        } else {
            add = e2;
        }
        if (add == null) {
            return desc;
        }

        desc.unionList__.add(add);
        desc.unionList__.addAll(add.unionList__);
        add.unionList__.clear();
        return desc;
    }
    /**
     *
     * <br>[機  能] ActionErrorに入力チェック例外を出力
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionError
     * @param paramName パラメータ名
     */
    public void addError(ActionErrors errors, String paramName) {
        StrutsUtil.addMessage(
                errors, errMsg__,
                String.format("%s.%s",
                        paramName,
                        valErrKey__));
        for (UIValidateException next : unionList__) {
            StrutsUtil.addMessage(
                    errors, next.errMsg__,
                    String.format("%s.%s",
                            paramName,
                            next.valErrKey__));

        }
    }

}
