package jp.groupsession.v2.cmn.restapi.plugins;

import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;


/**
 * <br>[機  能] メインメニューに表示するプラグイン情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PluginInfoModel {

    /** プラグインID                                      */
    private String plginId__;
    /** プラグイン名                                       */
    private String plginName__;
    /** プラグイン表示フラグ                                       */
    private EnumDispKbn plginDispFlg__;
    /** プラグインURL                                         */
    private String urlText__;
    /** アイコンURL                                      */
    private String iconUrlText__;
    /** 実行時HTTPメソッド                                      */
    private String methodText__;
    /** パラメータ情報                                      */
    @ChildElementName("parameterInfo")
    private List<ParameterInfoModel> parameterArray__ = new ArrayList<>();
    /**
     *
     * <br>[機  能] 列挙型 表示フラグ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static enum EnumDispKbn implements IEnumMsgkeyValue {
        /** 表示 */
        @MsgkeyValue(msgKey = "", value = 1)
        DISP,
        /** 非表示 */
        @MsgkeyValue(msgKey = "", value = 0)
        UNDISP;

        @Override
        public int getValue() {
            return IEnumMsgkeyValue.getValue(this);
        }

        @Override
        public String getMsgKey() {
            return IEnumMsgkeyValue.getMsgKey(this);
        }


    }

    /**
     * <p>plginId を取得します。
     * @return plginId
     * @see PluginInfoModel#plginId__
     */
    public String getPlginId() {
        return plginId__;
    }
    /**
     * <p>plginId をセットします。
     * @param plginId plginId
     * @see PluginInfoModel#plginId__
     */
    public void setPlginId(String plginId) {
        plginId__ = plginId;
    }
    /**
     * <p>plginName を取得します。
     * @return plginName
     * @see PluginInfoModel#plginName__
     */
    public String getPlginName() {
        return plginName__;
    }
    /**
     * <p>plginName をセットします。
     * @param plginName plginName
     * @see PluginInfoModel#plginName__
     */
    public void setPlginName(String plginName) {
        plginName__ = plginName;
    }
    /**
     * <p>plginDispFlg を取得します。
     * @return plginDispFlg
     * @see PluginInfoModel#plginDispFlg__
     */
    public EnumDispKbn getPlginDispFlg() {
        return plginDispFlg__;
    }
    /**
     * <p>plginDispFlg をセットします。
     * @param plginDispFlg plginDispFlg
     * @see PluginInfoModel#plginDispFlg__
     */
    public void setPlginDispFlg(EnumDispKbn plginDispFlg) {
        plginDispFlg__ = plginDispFlg;
    }
    /**
     * <p>urlText を取得します。
     * @return urlText
     * @see PluginInfoModel#urlText__
     */
    public String getUrlText() {
        return urlText__;
    }
    /**
     * <p>urlText をセットします。
     * @param urlText urlText
     * @see PluginInfoModel#urlText__
     */
    public void setUrlText(String urlText) {
        urlText__ = urlText;
    }
    /**
     * <p>iconUrlText を取得します。
     * @return iconUrlText
     * @see PluginInfoModel#iconUrlText__
     */
    public String getIconUrlText() {
        return iconUrlText__;
    }
    /**
     * <p>iconUrlText をセットします。
     * @param iconUrlText iconUrlText
     * @see PluginInfoModel#iconUrlText__
     */
    public void setIconUrlText(String iconUrlText) {
        iconUrlText__ = iconUrlText;
    }
    /**
     * <p>methodText を取得します。
     * @return methodText
     * @see PluginInfoModel#methodText__
     */
    public String getMethodText() {
        return methodText__;
    }
    /**
     * <p>methodText をセットします。
     * @param methodText methodText
     * @see PluginInfoModel#methodText__
     */
    public void setMethodText(String methodText) {
        methodText__ = methodText;
    }
    /**
     * <p>parameterArray を取得します。
     * @return parameterArray
     * @see PluginInfoModel#parameterArray__
     */
    public List<ParameterInfoModel> getParameterArray() {
        return parameterArray__;
    }
    /**
     * <p>parameterArray をセットします。
     * @param parameterArray parameterArray
     * @see PluginInfoModel#parameterArray__
     */
    public void setParameterArray(List<ParameterInfoModel> parameterArray) {
        parameterArray__ = parameterArray;
    }

}
