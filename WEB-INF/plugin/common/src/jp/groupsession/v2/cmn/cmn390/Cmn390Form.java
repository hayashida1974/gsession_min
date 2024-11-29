package jp.groupsession.v2.cmn.cmn390;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.biz.SubnetBiz;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] GSファイアウォール設定画面 Formクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn390Form extends AbstractGsForm {
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
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390ipAddrSeigenUseFlg__
     */
    public int getCmn390ipAddrSeigenUseFlg() {
        return cmn390ipAddrSeigenUseFlg__;
    }
    /**
     * <p>cmn390ipAddrSeigenUseFlg をセットします。
     * @param cmn390ipAddrSeigenUseFlg cmn390ipAddrSeigenUseFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390ipAddrSeigenUseFlg__
     */
    public void setCmn390ipAddrSeigenUseFlg(int cmn390ipAddrSeigenUseFlg) {
        cmn390ipAddrSeigenUseFlg__ = cmn390ipAddrSeigenUseFlg;
    }
    /**
     * <p>cmn390arrowIpAddrText を取得します。
     * @return cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowIpAddrText__
     */
    public String getCmn390arrowIpAddrText() {
        return cmn390arrowIpAddrText__;
    }
    /**
     * <p>cmn390arrowIpAddrText をセットします。
     * @param cmn390arrowIpAddrText cmn390arrowIpAddrText
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowIpAddrText__
     */
    public void setCmn390arrowIpAddrText(String cmn390arrowIpAddrText) {
        cmn390arrowIpAddrText__ = cmn390arrowIpAddrText;
    }
    /**
     * <p>cmn390arrowMblFlg を取得します。
     * @return cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowMblFlg__
     */
    public int getCmn390arrowMblFlg() {
        return cmn390arrowMblFlg__;
    }
    /**
     * <p>cmn390arrowMblFlg をセットします。
     * @param cmn390arrowMblFlg cmn390arrowMblFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowMblFlg__
     */
    public void setCmn390arrowMblFlg(int cmn390arrowMblFlg) {
        cmn390arrowMblFlg__ = cmn390arrowMblFlg;
    }
    /**
     * <p>cmn390arrowAnpFlg を取得します。
     * @return cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowAnpFlg__
     */
    public int getCmn390arrowAnpFlg() {
        return cmn390arrowAnpFlg__;
    }
    /**
     * <p>cmn390arrowAnpFlg をセットします。
     * @param cmn390arrowAnpFlg cmn390arrowAnpFlg
     * @see jp.groupsession.v2.cmn.cmn390.Cmn390Form#cmn390arrowAnpFlg__
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

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl,
            Connection con
            ) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (Stream.of(0, 1)
                .anyMatch(v -> v == cmn390ipAddrSeigenUseFlg__) == false) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.cmn390.03")),
                    "cmn390ipAddrSeigenUseFlg");

        }

        if (getCmn390ipAddrSeigenUseFlg() == 1) {
            //許可IPアドレス
            GSValidateCommon.validateTextField(errors,
                    cmn390arrowIpAddrText__,
                    "cmn390arrowIpAddrText",
                    gsMsg.getMessage("cmn.cmn390.04"),
                    0,
                    true);
            //許可IPアドレス(不正チェック)
            if (!StringUtil.isNullZeroString(cmn390arrowIpAddrText__)) {
                SubnetBiz ipBiz = new SubnetBiz();
                errors = ipBiz.validateSubnet(errors, reqMdl, cmn390arrowIpAddrText__,
                                            "cmn390arrowIpAddrText",
                                            gsMsg.getMessage("cmn.cmn390.04"));
            }

            //例外設定 安否確認回答
            if (Stream.of(0, 1)
                    .anyMatch(v -> v == cmn390arrowAnpFlg__) == false) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("cmn.cmn390.05")),
                        "cmn390arrowAnpFlg");
            }

            //例外設定 GSモバイルアプリ
            if (Stream.of(0, 1)
                    .anyMatch(v -> v == cmn390arrowMblFlg__) == false) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("cmn.cmn390.05")),
                        "cmn390arrowMblFlg");

            }

            if (cmn390arrowMblFlg__ == 1) {
                //例外設定 GSモバイルアプリ プラグイン制限
                if (Stream.of(0, 1)
                    .anyMatch(v -> v == cmn390mobilePermissionKbn__) == false) {
                    StrutsUtil.addMessage(errors,
                            new ActionMessage("error.input.notvalidate.data",
                                    gsMsg.getMessage("cmn.cmn390.05")),
                            "cmn390mobilePermissionKbn");
                }

                //例外設定 GSモバイルアプリ アクセス許可
                if (cmn390mobilePermissionKbn__ == 1) {
                    if (cmn390mobilePermissionPluginList__ == null
                    || cmn390mobilePermissionPluginList__.length <= 0) {
                        //未選択チェック
                        StrutsUtil.addMessage(errors,
                        new ActionMessage("error.select.required.text",
                                gsMsg.getMessage("cmn.cmn390.16")),
                        "cmn390mobilePermissionPluginList");
                    } else {
                        //不正な値チェック
                        List<String> pluginIdList = cmn390mobilePermissionPluginUI__.getPluginIdList(reqMdl, con);

                        for (String paramPid : cmn390mobilePermissionPluginList__) {
                            if (!pluginIdList.contains(paramPid)) {
                                StrutsUtil.addMessage(errors,
                                new ActionMessage("error.nothing.selected.plugin",
                                        gsMsg.getMessage("cmn.cmn390.16")),
                                        "cmn390mobilePermissionPluginList");
                                break;
                            }
                        }
                    }
                }
            }

            //許可IPアドレスチェック - 設定後、現在のクライアントからアクセス可能かをチェック
            FirewallBiz biz = FirewallBiz.getInstance();
            if (biz.isArrowAccessForValidate(
                    reqMdl.getRemoteAddr(),
                    cmn390arrowIpAddrText__) == false) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage("errors.free.msg",
                                gsMsg.getMessage("cmn.cmn390.08")),
                        "cmn390arrowAnpFlg");

            }
        }

        return errors;
    }

}
