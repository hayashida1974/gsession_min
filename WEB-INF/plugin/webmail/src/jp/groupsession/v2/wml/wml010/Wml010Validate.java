package jp.groupsession.v2.wml.wml010;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] WEBメール メール一覧画面の入力チェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010Validate {

    /**
     * <br>[機  能] テキストボックス/テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param target チェック対象
     * @param targetName チェック対象名称
     * @param maxLength 最大入力文字数
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param reqMdl リクエスト情報
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    public static String[] validateText(
            String target,
            String targetName,
            int maxLength,
            boolean checkNoInput,
            RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroString(target)) {
            if (!checkNoInput) {
                return null;
            }

            //未入力チェック
            return new String[]{gsMsg.getMessage("cmn.plz.input", new String[] {targetName})};
        }

        if (maxLength > 0 && target.length() > maxLength) {
            //MAX桁チェック
            String[] params = {targetName, String.valueOf(maxLength)};
            return new String[]{gsMsg.getMessage("wml.224", params)};
        }

        if (ValidateUtil.isSpace(target)) {
            //スペースのみ
            return new String[]{gsMsg.getMessage("wml.220", new String[] {targetName})};
        }

        if (ValidateUtil.isSpaceStart(target)) {
            //先頭スペース
            return new String[]{gsMsg.getMessage("wml.223", new String[] {targetName})};
        }

        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(target)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(target);
            return new String[]{gsMsg.getMessage("wml.222", new String[] {targetName}),
                    gsMsg.getMessage("wml.119") + nstr};
        }

        //入力エラー無し
        return null;
    }
    
}