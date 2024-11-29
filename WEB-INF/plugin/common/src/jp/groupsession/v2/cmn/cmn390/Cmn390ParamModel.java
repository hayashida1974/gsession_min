package jp.groupsession.v2.cmn.cmn390;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;

/**
 *
 * <br>[機  能] GSファイアウォール設定画面 ParamModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn390ParamModel extends AbstractParamModel {
    /** IPアドレス制限 0：使用しない 1:使用する*/
    private int cmn390ipAddrSeigenUseFlg__;
    /** 許可IPアドレス*/
    private String cmn390arrowIpAddrText__;
    /** IPアドレス制限 0：使用しない 1:使用する*/
    private int cmn390arrowMblFlg__;
    /** 安否確認制限 0：使用しない 1:使用する*/
    private int cmn390arrowAnpFlg__;

    /** GSモバイルアプリ プラグイン制限 */
    private int cmn390mobilePermissionKbn__ = 0;
    /** GSモバイルアプリ 許可プラグイン */
    private String[] cmn390mobilePermissionPluginList__ = null;
    /** GSモバイルアプリ 許可プラグイン選択UI */
    private Cmn390PluginSelector cmn390mobilePermissionPluginUI__ =
            Cmn390PluginSelector.builder()
                .chainLabel(new GsMessageReq("schedule.sch240.07", null))
                .chainSelect(
                    Select.builder()
                    .chainLabel(new GsMessageReq("schedule.sch240.07", null))
                    .chainParameterName(
                            "cmn390mobilePermissionPluginList")
                    )
                .build();

    /**
     * <p>cmn390ipAddrSeigenUseFlg を取得します。
     * @return cmn390ipAddrSeigenUseFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390ipAddrSeigenUseFlg__
     */
    public int getCmn390ipAddrSeigenUseFlg() {
        return cmn390ipAddrSeigenUseFlg__;
    }
    /**
     * <p>cmn390ipAddrSeigenUseFlg をセットします。
     * @param cmn390ipAddrSeigenUseFlg cmn390ipAddrSeigenUseFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390ipAddrSeigenUseFlg__
     */
    public void setCmn390ipAddrSeigenUseFlg(int cmn390ipAddrSeigenUseFlg) {
        cmn390ipAddrSeigenUseFlg__ = cmn390ipAddrSeigenUseFlg;
    }
    /**
     * <p>cmn390arrowIpAddrText を取得します。
     * @return cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowIpAddrText__
     */
    public String getCmn390arrowIpAddrText() {
        return cmn390arrowIpAddrText__;
    }
    /**
     * <p>cmn390arrowIpAddrText をセットします。
     * @param cmn390arrowIpAddrText cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowIpAddrText__
     */
    public void setCmn390arrowIpAddrText(String cmn390arrowIpAddrText) {
        cmn390arrowIpAddrText__ = cmn390arrowIpAddrText;
    }
    /**
     * <p>cmn390arrowMblFlg を取得します。
     * @return cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowMblFlg__
     */
    public int getCmn390arrowMblFlg() {
        return cmn390arrowMblFlg__;
    }
    /**
     * <p>cmn390arrowMblFlg をセットします。
     * @param cmn390arrowMblFlg cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowMblFlg__
     */
    public void setCmn390arrowMblFlg(int cmn390arrowMblFlg) {
        cmn390arrowMblFlg__ = cmn390arrowMblFlg;
    }
    /**
     * <p>cmn390arrowAnpFlg を取得します。
     * @return cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowAnpFlg__
     */
    public int getCmn390arrowAnpFlg() {
        return cmn390arrowAnpFlg__;
    }
    /**
     * <p>cmn390arrowAnpFlg をセットします。
     * @param cmn390arrowAnpFlg cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390ParamModel#cmn390arrowAnpFlg__
     */
    public void setCmn390arrowAnpFlg(int cmn390arrowAnpFlg) {
        cmn390arrowAnpFlg__ = cmn390arrowAnpFlg;
    }
    /**
     * <p>cmn390mobilePermissionKbn を取得します。
     * @return cmn390mobilePermissionKbn
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390mobilePermissionKbn__
     */
    public int getCmn390mobilePermissionKbn() {
        return cmn390mobilePermissionKbn__;
    }
    /**
     * <p>cmn390mobilePermissionKbn をセットします。
     * @param cmn390mobilePermissionKbn cmn390mobilePermissionKbn
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390mobilePermissionKbn__
     */
    public void setCmn390mobilePermissionKbn(int cmn390mobilePermissionKbn) {
        cmn390mobilePermissionKbn__ = cmn390mobilePermissionKbn;
    }
    /**
     * @return the cmn390mobilePermissionPluginList
     */
    public String[] getCmn390mobilePermissionPluginList() {
        return cmn390mobilePermissionPluginList__;
    }
    /**
     * @param cmn390mobilePermissionPluginList the cmn390mobilePermissionPluginList to set
     */
    public void setCmn390mobilePermissionPluginList(
            String[] cmn390mobilePermissionPluginList) {
        cmn390mobilePermissionPluginList__ = cmn390mobilePermissionPluginList;
    }
    /**
     * @return the cmn390mobilePermissionPluginUI
     */
    public Cmn390PluginSelector getCmn390mobilePermissionPluginUI() {
        return cmn390mobilePermissionPluginUI__;
    }
    /**
     * @param cmn390mobilePermissionPluginUI the cmn390mobilePermissionPluginUI to set
     */
    public void setCmn390mobilePermissionPluginUI(
            Cmn390PluginSelector cmn390mobilePermissionPluginUI) {
        cmn390mobilePermissionPluginUI__ = cmn390mobilePermissionPluginUI;
    }
}
