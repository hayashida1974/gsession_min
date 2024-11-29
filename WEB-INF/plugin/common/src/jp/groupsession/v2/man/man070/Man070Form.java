package jp.groupsession.v2.man.man070;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070Form extends AbstractGsForm {

    /** プロキシサーバ ユーザ認証 区分 ユーザ認証を使用する */
    public static final int MAN070_AUTH_USE = 1;

    /** プロキシサーバ使用区分 */
    private int man070pxyUseKbn__ = GSConstMain.PROXY_SERVER_NOT_USE;
    /** プロキシサーバアドレス */
    private String man070address__;
    /** プロキシサーバ使用ポート番号 */
    private String man070portnum__;

    /** プロキシサーバ ユーザ認証 区分 */
    private int man070Auth__ = 0;
    /** プロキシサーバ ユーザ認証 ユーザ */
    private String man070AuthUser__ = null;
    /** プロキシサーバ ユーザ認証 パスワード */
    private String man070AuthPassword__ = null;

    /** プロキシサーバ プロキシサーバを使用しないアドレス */
    private String man070NoProxyAddress__ = null;

    /**
     * <p>man070address__ を取得します。
     * @return man070address
     */
    public String getMan070address() {
        return man070address__;
    }
    /**
     * <p>man070address__ をセットします。
     * @param man070address man070address__
     */
    public void setMan070address(String man070address) {
        man070address__ = man070address;
    }
    /**
     * <p>man070portnum__ を取得します。
     * @return man070portnum
     */
    public String getMan070portnum() {
        return man070portnum__;
    }
    /**
     * <p>man070portnum__ をセットします。
     * @param man070portnum man070portnum__
     */
    public void setMan070portnum(String man070portnum) {
        man070portnum__ = man070portnum;
    }
    /**
     * <p>man070pxyUseKbn__ を取得します。
     * @return man070pxyUseKbn
     */
    public int getMan070pxyUseKbn() {
        return man070pxyUseKbn__;
    }
    /**
     * <p>man070pxyUseKbn__ をセットします。
     * @param man070pxyUseKbn man070pxyUseKbn__
     */
    public void setMan070pxyUseKbn(int man070pxyUseKbn) {
        man070pxyUseKbn__ = man070pxyUseKbn;
    }

    /**
     * <p>man070Auth を取得します。
     * @return man070Auth
     */
    public int getMan070Auth() {
        return man070Auth__;
    }
    /**
     * <p>man070Auth をセットします。
     * @param man070Auth man070Auth
     */
    public void setMan070Auth(int man070Auth) {
        man070Auth__ = man070Auth;
    }

    /**
     * <p>man070AuthPassword を取得します。
     * @return man070AuthPassword
     */
    public String getMan070AuthPassword() {
        return man070AuthPassword__;
    }
    /**
     * <p>man070AuthPassword をセットします。
     * @param man070AuthPassword man070AuthPassword
     */
    public void setMan070AuthPassword(String man070AuthPassword) {
        man070AuthPassword__ = man070AuthPassword;
    }
    /**
     * <p>man070AuthUser を取得します。
     * @return man070AuthUser
     */
    public String getMan070AuthUser() {
        return man070AuthUser__;
    }
    /**
     * <p>man070AuthUser をセットします。
     * @param man070AuthUser man070AuthUser
     */
    public void setMan070AuthUser(String man070AuthUser) {
        man070AuthUser__ = man070AuthUser;
    }
    /**
     * <p>man070NoProxyAddress を取得します。
     * @return man070NoProxyAddress
     */
    public String getMan070NoProxyAddress() {
        return man070NoProxyAddress__;
    }
    /**
     * <p>man070NoProxyAddress をセットします。
     * @param man070NoProxyAddress man070NoProxyAddress
     */
    public void setMan070NoProxyAddress(String man070NoProxyAddress) {
        man070NoProxyAddress__ = man070NoProxyAddress;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //プロキシサーバ使用区分
        if (man070pxyUseKbn__ != GSConstMain.PROXY_SERVER_NOT_USE
            && man070pxyUseKbn__ != GSConstMain.PROXY_SERVER_USE) {

            msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, GSConstMain.TEXT_PROXY_USE_KBN));
            errors.add("man070pxyUseKbn.error.select.required.text", msg);
        }

        if (man070pxyUseKbn__ == GSConstMain.PROXY_SERVER_USE) {

            //アドレス
            errors = __validateText(req, errors,
                                    man070address__, "man070address",
                                    GSConstMain.TEXT_PROXY_ADDRESS,
                                    GSConstMain.MAX_LENGTH_PROXY_ADDRESS, true);

            //ポート番号 入力
            if (StringUtil.isNullZeroString(man070portnum__)) {
                msg = new ActionMessage("error.input.required.text",
                        getInterMessage(req, GSConstMain.TEXT_PROXY_PORTNUM));
                errors.add("man070portnum.error.input.required.text", msg);
            //ポート番号 文字数
            } else if (man070portnum__.length() > GSConstMain.MAX_LENGTH_PROXY_PORTNUM) {
                msg = new ActionMessage("error.input.length.text",
                        getInterMessage(req, GSConstMain.TEXT_PROXY_PORTNUM),
                        GSConstMain.MAX_LENGTH_PROXY_PORTNUM);
                errors.add("man070portnum.error.input.length.text", msg);
            //ポート番号 半角数字
            } else if (!ValidateUtil.isNumber(man070portnum__)) {
                msg = new ActionMessage("error.input.length.number2",
                        getInterMessage(req, GSConstMain.TEXT_PROXY_PORTNUM),
                        GSConstMain.MAX_LENGTH_PROXY_PORTNUM);
                errors.add("man070portnum.error.input.comp.text", msg);
            //ポート番号 最大値
            } else if (Integer.parseInt(man070portnum__)
                    > GSConstMain.MAX_NUMBER_PROXY_PORTNUM) {
                msg = new ActionMessage("error.input.number.under",
                        getInterMessage(req, GSConstMain.TEXT_PROXY_PORTNUM),
                        GSConstMain.MAX_NUMBER_PROXY_PORTNUM);
                errors.add("man070portnum.error.input.comp.text", msg);
            }
        }

        if (man070Auth__ == Man070Form.MAN070_AUTH_USE) {
            //プロキシサーバ ユーザ認証 ユーザ
            errors = __validateText(req, errors,
                    man070AuthUser__, "man070AuthUser",
                    "main.src.man070.2",
                    GSConstMain.MAX_LENGTH_PROXY_USERAUTH_USER, true);

            //プロキシサーバ ユーザ認証 パスワード
            errors = __validateText(req, errors,
                    man070AuthPassword__, "man070AuthPassword",
                    "main.src.man070.3",
                    GSConstMain.MAX_LENGTH_PROXY_USERAUTH_PASSWORD, true);
        }

        //プロキシサーバ プロキシサーバを使用しないアドレス
        Man070ParamModel paramMdl = new Man070ParamModel();
        paramMdl.setParam(this);
        String[] addressList = paramMdl.getNoProxyAddressList();
        if (addressList.length > 0) {
            int errSize = errors.size();
            for (String address : addressList) {
                errors = __validateText(req, errors,
                        address, "man070NoProxyAddress",
                        "main.src.man070.4",
                        GSConstMain.MAX_LENGTH_PROXY_USERAUTH_PASSWORD, true);

                if (errSize < errors.size()) {
                    break;
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] テキストボックスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param errors ActionErrors
     * @param value 値
     * @param name パラメータ名
     * @param msgKey 項目名に対応するメッセージキー
     * @param maxLength 最大長
     * @param noInput true:入力必須, false:任意の入力
     * @return ActionErrors
     */
    private ActionErrors __validateText(HttpServletRequest req, ActionErrors errors,
                                        String value, String name, String msgKey,
                                        int maxLength, boolean noInput) {

        ActionMessage msg = null;
        //入力必須チェック
        if (StringUtil.isNullZeroString(value)) {
            if (noInput) {
                msg = new ActionMessage("error.input.required.text",
                        getInterMessage(req, msgKey));
                errors.add(name + ".error.input.required.text", msg);
            }
        //アドレス 文字数
        } else if (value.length() > maxLength) {
            msg = new ActionMessage("error.input.length.text",
                    getInterMessage(req, msgKey),
                    maxLength);
            errors.add(name + ".error.input.length.text", msg);
        //アドレス 使用文字
        } else if (!GSValidateUtil.isGsJapaneaseString(value)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseString(value);
            msg = new ActionMessage("error.input.njapan.text",
                    getInterMessage(req, msgKey),
                    nstr);
            errors.add(name + ".error.input.njapan.text", msg);
        }

        return errors;
    }
}