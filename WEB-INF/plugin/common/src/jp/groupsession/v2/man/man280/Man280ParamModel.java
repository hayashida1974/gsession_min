package jp.groupsession.v2.man.man280;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man120.Man120ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン使用制限画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man280ParamModel extends Man120ParamModel {
    /** プラグイン名称 */
    private String man280pluginName__ = null;
    /** プラグイン区分 */
    private int man280pluginKbn__ = GSConstMain.PLUGIN_GS;
    /** バイナリSID */
    private Long man280BinSid__ = Long.valueOf(0);

    /** 初期表示フラグ */
    private int man280initFlg__ = 0;
    /** プラグイン使用制限区分 */
    private int man280useKbn__ = GSConstMain.PCT_KBN_ALLOK;
    /** 制限方法 */
    private int man280limitType__ = GSConstMain.PCT_TYPE_LIMIT;

    /** グループSID */
    private int man280groupSid__ = Man280Form.GRP_SID_GRPLIST;
    /** メンバーSID */
    private String[] man280memberSid__ = new String[0];
    /** メンバーSID UI */
    private UserGroupSelector man280memberSidUI__ = null;

    /** グループSID（管理者） */
    private int man280groupSidAdmin__ = Man280Form.GRP_SID_GRPLIST;
    /** 管理者SID */
    private String[] man280AdminSid__ = new String[0];
    /** 管理者SID UI */
    private UserGroupSelector man280AdminSidUI__ = null;

    /**
     * <p>man280pluginName を取得します。
     * @return man280pluginName
     */
    public String getMan280pluginName() {
        return man280pluginName__;
    }
    /**
     * <p>man280pluginName をセットします。
     * @param man280pluginName man280pluginName
     */
    public void setMan280pluginName(String man280pluginName) {
        man280pluginName__ = man280pluginName;
    }
    /**
     * <p>man280initFlg を取得します。
     * @return man280initFlg
     */
    public int getMan280initFlg() {
        return man280initFlg__;
    }
    /**
     * <p>man280initFlg をセットします。
     * @param man280initFlg man280initFlg
     */
    public void setMan280initFlg(int man280initFlg) {
        man280initFlg__ = man280initFlg;
    }
    /**
     * <p>man280groupSid を取得します。
     * @return man280groupSid
     */
    public int getMan280groupSid() {
        return man280groupSid__;
    }
    /**
     * <p>man280groupSid をセットします。
     * @param man280groupSid man280groupSid
     */
    public void setMan280groupSid(int man280groupSid) {
        man280groupSid__ = man280groupSid;
    }
    /**
     * <p>man280memberSid を取得します。
     * @return man280memberSid
     */
    public String[] getMan280memberSid() {
        return man280memberSid__;
    }
    /**
     * <p>man280memberSid をセットします。
     * @param man280memberSid man280memberSid
     */
    public void setMan280memberSid(String[] man280memberSid) {
        man280memberSid__ = man280memberSid;
    }
    /**
     * <p>man280memberSidUI を取得します。
     * @return man280memberSidUI
     */
    public UserGroupSelector getMan280memberSidUI() {
        return man280memberSidUI__;
    }
    /**
     * <p>man280memberSidUI をセットします。
     * @param man280memberSidUI man280memberSidUI
     */
    public void setMan280memberSidUI(UserGroupSelector man280memberSidUI) {
        man280memberSidUI__ = man280memberSidUI;
    }
    /**
     * <p>man280useKbn を取得します。
     * @return man280useKbn
     */
    public int getMan280useKbn() {
        return man280useKbn__;
    }
    /**
     * <p>man280useKbn をセットします。
     * @param man280useKbn man280useKbn
     */
    public void setMan280useKbn(int man280useKbn) {
        man280useKbn__ = man280useKbn;
    }
    /**
     * <p>man280groupSidAdmin を取得します。
     * @return man280groupSidAdmin
     */
    public int getMan280groupSidAdmin() {
        return man280groupSidAdmin__;
    }
    /**
     * <p>man280groupSidAdmin をセットします。
     * @param man280groupSidAdmin man280groupSidAdmin
     */
    public void setMan280groupSidAdmin(int man280groupSidAdmin) {
        man280groupSidAdmin__ = man280groupSidAdmin;
    }
    /**
     * <p>man280AdminSid を取得します。
     * @return man280AdminSid
     */
    public String[] getMan280AdminSid() {
        return man280AdminSid__;
    }
    /**
     * <p>man280AdminSid をセットします。
     * @param man280AdminSid man280AdminSid
     */
    public void setMan280AdminSid(String[] man280AdminSid) {
        man280AdminSid__ = man280AdminSid;
    }
    /**
     * <p>man280AdminSidUI を取得します。
     * @return man280AdminSidUI
     */
    public UserGroupSelector getMan280AdminSidUI() {
        return man280AdminSidUI__;
    }
    /**
     * <p>man280AdminSidUI をセットします。
     * @param man280AdminSidUI man280AdminSidUI
     */
    public void setMan280AdminSidUI(UserGroupSelector man280AdminSidUI) {
        man280AdminSidUI__ = man280AdminSidUI;
    }
    /**
     * <p>man280limitType を取得します。
     * @return man280limitType
     */
    public int getMan280limitType() {
        return man280limitType__;
    }
    /**
     * <p>man280limitType をセットします。
     * @param man280limitType man280limitType
     */
    public void setMan280limitType(int man280limitType) {
        man280limitType__ = man280limitType;
    }
    /**
     * <p>man280pluginKbn を取得します。
     * @return man280pluginKbn
     */
    public int getMan280pluginKbn() {
        return man280pluginKbn__;
    }
    /**
     * <p>man280pluginKbn をセットします。
     * @param man280pluginKbn man280pluginKbn
     */
    public void setMan280pluginKbn(int man280pluginKbn) {
        man280pluginKbn__ = man280pluginKbn;
    }
    /**
     * <p>man280BinSid を取得します。
     * @return man280BinSid
     */
    public Long getMan280BinSid() {
        return man280BinSid__;
    }
    /**
     * <p>man280BinSid をセットします。
     * @param man280BinSid man280BinSid
     */
    public void setMan280BinSid(Long man280BinSid) {
        man280BinSid__ = man280BinSid;
    }
}