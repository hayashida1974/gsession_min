package jp.co.sjts.util.struts;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] Strutsフレームワークに関するユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class StrutsUtil {

    /**
     * <br>[機  能] 重複するエラーキー名が存在しない場合にActionErrorを追加します。
     * <br>[解  説]
     * <br>[備  考] ActionMessageを使用する
     * @param errs エラー追加先1
     * @param msg 追加対象のエラー内容
     * @param propName エラーキー名
     * @return true:追加 false:何もしていない
     */
    public static boolean addMessage(
        ActionErrors errs,
        ActionMessage msg,
        String propName) {

        if (errs.size(propName) < 1) {
            errs.add(propName, msg);
            return true;
        }
        return false;
    }
}
