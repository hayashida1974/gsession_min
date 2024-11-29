package jp.groupsession.v2.man.man120;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] プラグイン使用制限情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man120PluginControlModel extends AbstractModel {

    /** プラグイン名称 */
    private String pluginName__ = null;
    /** プラグイン区分 */
    private int pluginKbn__;
    /** バイナリSID */
    private Long pluginBinSid__ = new Long(0);
    /** 使用制限区分 */
    private int useKbn__ = GSConstMain.PCT_KBN_ALLOK;
    /** グループ名一覧 */
    private List<String> groupNameList__ = null;
    /** ユーザ名一覧 */
    private List<String> userNameList__ = null;

    /** 管理者一覧 */
    private List<LabelValueBean> kanriList__ = null;
    /** 制限方法 */
    private int pctType__ = 0;
    /** 制限対象 */
    private String taisyo__ = null;
    /** プラグインID */
    private String pluginId__ = null;
    /** 管理者グループリスト */
    private ArrayList<LabelValueBean> admGroupNameList__ = null;
    /** 管理者ユーザリスト */
    private ArrayList<UsrLabelValueBean> admUserNameList__ = null;
    /** アイコンURL */
    private String icon__ = null;
    /** アイコンURL（クラシック） */
    private String iconClassic__ = null;

    /**
     * <p>groupNameList を取得します。
     * @return groupNameList
     */
    public List<String> getGroupNameList() {
        return groupNameList__;
    }
    /**
     * <p>pluginName を取得します。
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }
    /**
     * <p>groupNameList をセットします。
     * @param groupNameList groupNameList
     */
    public void setGroupNameList(List<String> groupNameList) {
        groupNameList__ = groupNameList;
    }
    /**
     * <p>useKbn を取得します。
     * @return useKbn
     */
    public int getUseKbn() {
        return useKbn__;
    }
    /**
     * <p>useKbn をセットします。
     * @param useKbn useKbn
     */
    public void setUseKbn(int useKbn) {
        useKbn__ = useKbn;
    }
    /**
     * <p>userNameList を取得します。
     * @return userNameList
     */
    public List<String> getUserNameList() {
        return userNameList__;
    }
    /**
     * <p>userNameList をセットします。
     * @param userNameList userNameList
     */
    public void setUserNameList(List<String> userNameList) {
        userNameList__ = userNameList;
    }
    /**
     * <p>kanriList を取得します。
     * @return kanriList
     */
    public List<LabelValueBean> getKanriList() {
        return kanriList__;
    }
    /**
     * <p>kanriList をセットします。
     * @param kanriList kanriList
     */
    public void setKanriList(List<LabelValueBean> kanriList) {
        kanriList__ = kanriList;
    }
    /**
     * <p>pctType を取得します。
     * @return pctType
     */
    public int getPctType() {
        return pctType__;
    }
    /**
     * <p>pctType をセットします。
     * @param pctType pctType
     */
    public void setPctType(int pctType) {
        pctType__ = pctType;
    }
    /**
     * <p>taisyo を取得します。
     * @return taisyo
     */
    public String getTaisyo() {
        return taisyo__;
    }
    /**
     * <p>taisyo をセットします。
     * @param taisyo taisyo
     */
    public void setTaisyo(String taisyo) {
        taisyo__ = taisyo;
    }
    /**
     * <p>pluginId を取得します。
     * @return pluginId
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>admGroupNameList を取得します。
     * @return admGroupNameList
     */
    public ArrayList<LabelValueBean> getAdmGroupNameList() {
        return admGroupNameList__;
    }
    /**
     * <p>admGroupNameList をセットします。
     * @param admGroupNameList admGroupNameList
     */
    public void setAdmGroupNameList(ArrayList<LabelValueBean> admGroupNameList) {
        admGroupNameList__ = admGroupNameList;
    }
    /**
     * <p>admUserNameList を取得します。
     * @return admUserNameList
     */
    public ArrayList<UsrLabelValueBean> getAdmUserNameList() {
        return admUserNameList__;
    }
    /**
     * <p>admUserNameList をセットします。
     * @param admUserNameList admUserNameList
     */
    public void setAdmUserNameList(ArrayList<UsrLabelValueBean> admUserNameList) {
        admUserNameList__ = admUserNameList;
    }
    /**
     * <p>pluginKbn を取得します。
     * @return pluginKbn
     */
    public int getPluginKbn() {
        return pluginKbn__;
    }
    /**
     * <p>pluginKbn をセットします。
     * @param pluginKbn pluginKbn
     */
    public void setPluginKbn(int pluginKbn) {
        pluginKbn__ = pluginKbn;
    }
    /**
     * <p>pluginBinSid を取得します。
     * @return pluginBinSid
     */
    public Long getPluginBinSid() {
        return pluginBinSid__;
    }
    /**
     * <p>pluginBinSid をセットします。
     * @param pluginBinSid pluginBinSid
     */
    public void setPluginBinSid(Long pluginBinSid) {
        pluginBinSid__ = pluginBinSid;
    }
    /**
     * <p>icon を取得します。
     * @return icon
     */
    public String getIcon() {
        return icon__;
    }
    /**
     * <p>icon をセットします。
     * @param icon icon
     */
    public void setIcon(String icon) {
        icon__ = icon;
    }
    /**
     * <p>iconClassic を取得します。
     * @return iconClassic
     */
    public String getIconClassic() {
        return iconClassic__;
    }
    /**
     * <p>iconClassic をセットします。
     * @param iconClassic iconClassic
     */
    public void setIconClassic(String iconClassic) {
        iconClassic__ = iconClassic;
    }
}
