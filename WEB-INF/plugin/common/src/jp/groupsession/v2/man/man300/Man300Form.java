package jp.groupsession.v2.man.man300;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man320.Man320Form;

/**
 * <br>[機  能] メイン インフォメーション 管理者設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man300Form extends Man320Form implements ISelectorUseForm {
    //入力項目
    /** グループSID */
    private int man300groupSid__ = Integer.parseInt(GSConstMain.GROUP_COMBO_VALUE);
    /** 公開対象者SID */
    private String[] man300memberSid__ = new String[0];
    /** メンバーSID UI */
    private UserGroupSelector man300memberSidUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.admin", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(
                        Select.builder()
                        .chainParameterName(
                                "man300memberSid")
                    )
                .chainGroupSelectionParamName("man300groupSid")
                .build();

    /** 遷移元 メイン個人設定メニュー:2 メイン管理者設定メニュー:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * @return the man300memberSid
     */
    public String[] getMan300memberSid() {
        return man300memberSid__;
    }
    /**
     * @param man300memberSid the man300memberSid to set
     */
    public void setMan300memberSid(String[] man300memberSid) {
        man300memberSid__ = man300memberSid;
    }
    /**
     * @return the man300groupSid
     */
    public int getMan300groupSid() {
        return man300groupSid__;
    }
    /**
     * @param man300groupSid the man300groupSid to set
     */
    public void setMan300groupSid(int man300groupSid) {
        man300groupSid__ = man300groupSid;
    }
    /**
     * <p>man300memberSidUI を取得します。
     * @return man300memberSidUI
     */
    public UserGroupSelector getMan300memberSidUI() {
        return man300memberSidUI__;
    }
    /**
     * <p>man300memberSidUI をセットします。
     * @param man300memberSidUI man300memberSidUI
     */
    public void setMan300memberSidUI(UserGroupSelector man300memberSidUI) {
        man300memberSidUI__ = man300memberSidUI;
    }
}
