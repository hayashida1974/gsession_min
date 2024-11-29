package jp.groupsession.v2.usr;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.config.PluginConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


/**
 * <br>[機  能] ユーザ情報に関するユーティリティクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserUtil {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UserUtil.class);

    /**
     * <p>CSV形式のStringからint[]でグループSIDを返す
     * @param csv CSV形式のString
     * @return intに変換したグループSID(複数)
     */
    public static int[] toGroupSidFromCsv(String csv) {
        csv = csv.trim();
        String[] tmpgps = csv.split(",");
        int[] gsids = new int[tmpgps.length];

        for (int i = 0; i < tmpgps.length; i++) {
            int sid = Integer.parseInt(tmpgps[i]);
            gsids[i] = sid;
        }
        return gsids;
    }

    /**
     * <p>プラグイン設定ファイルよりユーザリスナー実装クラスのリストを返す
     * @param pconfig ユーザリスナー実装クラス
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @return ユーザリスナー
     */
    public static IUserGroupListener[] getUserListeners(PluginConfig pconfig)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        String[] userListenerClass = pconfig.getUserListeners();
        IUserGroupListener[] lis = getUserListeners(userListenerClass);
        return lis;
    }

    /**
     * <p>ユーザリスナー実装クラスのリストを返す
     * @param userListenerClass ユーザリスナー実装クラス
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @return ユーザリスナー
     */
    @SuppressWarnings("all")
    public static IUserGroupListener[] getUserListeners(String[] userListenerClass)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        //
        IUserGroupListener[] lis = new IUserGroupListener[userListenerClass.length];
        for (int i = 0; i < userListenerClass.length; i++) {
            Class lisClass = Class.forName(userListenerClass[i]);
            lis[i] = (IUserGroupListener) lisClass.newInstance();
        }

        return lis;
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextField(
        ActionErrors errors,
        String value,
        String paramName,
        String paramNameJpn,
        int maxLength,
        boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");

        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペースのみチェック
        if (!validateSpaceOnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }
        //タブスペースチェック
        if (ValidateUtil.isTab(value)) {
            //タイトル
            ActionMessage msg = new ActionMessage("error.input.tab.text",
                    paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName + "error.input.tab.text");
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //カンマチェック
        if (value.indexOf(",") > 0) {
            ActionMessage msg =
                new ActionMessage("error.cantinput.connma", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName);
        }

        //JIS第二水準チェック
        validateGsJapanese(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] テキストエリアの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大文字数
     * @param necessary 入力が必須か true:必須 false:必須ではない
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateTextAreaField(
                                                ActionErrors errors,
                                                String value,
                                                String paramName,
                                                String paramNameJpn,
                                                int maxLength,
                                                boolean necessary) {

        log__.debug(paramNameJpn + " のチェックを行います。");


        int startErrCount = errors.size();

        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (necessary) {
                validateNecessary(errors, value, paramName, paramNameJpn);
                return false;
            }

            return true;
        }

        //スペース、改行のみチェック
        if (!validateSpaceOrBROnly(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //先頭スペースチェック
        if (!validateInitialSpace(errors, value, paramName, paramNameJpn)) {
            return false;
        }

        //最大長チェック
        validateMaxLength(errors, value, paramName, paramNameJpn, maxLength);

        //JIS第二水準チェック
        validateGsJapanese(errors, value, paramName, paramNameJpn);

        return startErrCount == errors.size();
    }

    /**
     * <br>[機  能] スペース、改行のみのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOrBROnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceOrKaigyou(value)) {
            String msgKey = "error.input.spase.cl.only";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateNecessary(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (StringUtil.isNullZeroString(value)) {
            //未入力チェック
            String msgKey = "error.input.required.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] スペースのみチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateSpaceOnly(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpace(value)) {
            String msgKey = "error.input.spase.only";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 先頭スペースチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateInitialSpace(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (ValidateUtil.isSpaceStart(value)) {
            String msgKey = "error.input.spase.start";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 最大長を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param maxLength 最大長
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateMaxLength(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn,
                                int maxLength) {

        String fieldfix = paramName + ".";

        if (value.length() > maxLength) {
            String msgKey = "error.input.length.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, maxLength);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 利用可能文字(JIS第二水準)チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return 入力チェック結果 true : 正常 false : 不正
     */
    public static boolean validateGsJapanese(
                                ActionErrors errors,
                                String value,
                                String paramName,
                                String paramNameJpn) {

        String fieldfix = paramName + ".";

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);

            String msgKey = "error.input.njapan.text";
            ActionMessage msg = new ActionMessage(msgKey, paramNameJpn, nstr);
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
            return false;
        }

        return true;
    }

    /**
     *
     * <br>[機  能] 姓と名を連結した文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sei 姓
     * @param mei 名
     * @return 名前文字列
     */
    public static String makeName(String sei, String mei) {
        return sei + " " + mei;
    }
    /**
     *
     * <br>[機  能] ログイン停止状態用のCSSクラス名を返す
     * <br>[解  説]
     * <br>[備  考] JSPのスクリプトレットにて使用
     * @param ukoFlg ログイン停止フラグ 0:有効 1:停止
     * @return ログイン停止状態用のCSSクラス名
     */
    public static String getCSSClassNameNormal(int ukoFlg) {
        if (ukoFlg == GSConst.YUKOMUKO_MUKO) {
            return "mukoUser";
        }
        return "";
    }
    /**
     *
     * <br>[機  能] ログイン停止状態用のCSSクラス名を返す
     * <br>[解  説]
     * <br>[備  考] オプションタグ用 JSPのスクリプトレットにて使用
     * @param ukoFlg ログイン停止フラグ 0:有効 1:停止
     * @return ログイン停止状態用のCSSクラス名
     */
    public static String getCSSClassNameOption(int ukoFlg) {
        if (ukoFlg == GSConst.YUKOMUKO_MUKO) {
            return "mukoUserOption";
        }
        return "";
    }
}
