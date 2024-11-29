package jp.groupsession.v2.sml.restapi.accounts.configs;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;

/**
 *
 * <br>[機  能] アカウント設定情報API アカウント設定情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsConfigsConfigInfoModel  {

    /** 本文形式 */
    private int bodyFormatFlg__ = 0;
    /** テーマ */
    private int themeType__ = 0;
    /** 引用符 */
    private String quoteText__ = "";
    /** プッシュ通知有効フラグ */
    private int pushFlg__ = 0;
    /** 自動送信先設定配列 */
    @ChildElementName("autoDestInfo")
    private List<SmlAccountsConfigsAutoDestModel> autoDestArray__
                                    = new ArrayList<SmlAccountsConfigsAutoDestModel>();
    /**
     * <p>bodyFormatFlg を取得します。
     * @return bodyFormatFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#bodyFormatFlg__
     */
    public int getBodyFormatFlg() {
        return bodyFormatFlg__;
    }
    /**
     * <p>bodyFormatFlg をセットします。
     * @param bodyFormatFlg bodyFormatFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#bodyFormatFlg__
     */
    public void setBodyFormatFlg(int bodyFormatFlg) {
        bodyFormatFlg__ = bodyFormatFlg;
    }
    /**
     * <p>themeType を取得します。
     * @return themeType
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#themeType__
     */
    public int getThemeType() {
        return themeType__;
    }
    /**
     * <p>themeType をセットします。
     * @param themeType themeType
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#themeType__
     */
    public void setThemeType(int themeType) {
        themeType__ = themeType;
    }
    /**
     * <p>quoteText を取得します。
     * @return quoteText
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#quoteText__
     */
    public String getQuoteText() {
        return quoteText__;
    }
    /**
     * <p>quoteText をセットします。
     * @param quoteText quoteText
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#quoteText__
     */
    public void setQuoteText(String quoteText) {
        quoteText__ = quoteText;
    }
    /**
     * <p>pushFlg を取得します。
     * @return pushFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#pushFlg__
     */
    public int getPushFlg() {
        return pushFlg__;
    }
    /**
     * <p>pushFlg をセットします。
     * @param pushFlg pushFlg
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#pushFlg__
     */
    public void setPushFlg(int pushFlg) {
        pushFlg__ = pushFlg;
    }
    /**
     * <p>autoDestArray を取得します。
     * @return autoDestArray
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#autoDestArray__
     */
    public List<SmlAccountsConfigsAutoDestModel> getAutoDestArray() {
        return autoDestArray__;
    }
    /**
     * <p>autoDestArray をセットします。
     * @param autoDestArray autoDestArray
     * @see jp.groupsession.v2.sml.restapi.accounts.configs.
     *       SmlAccountsConfigsConfigInfoModel#autoDestArray__
     */
    public void setAutoDestArray(
            List<SmlAccountsConfigsAutoDestModel> autoDestArray) {
        autoDestArray__ = autoDestArray;
    }
}
