package jp.groupsession.v2.cmn.cmn100;

import jp.groupsession.v2.cmn.dao.GroupModel;

/**
 * 
 * <br>[機  能]GroupModelにデフォルトグループを付け足した拡張モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn100GroupModel {

    /**ユーザーが所属しているグループのモデル*/
    private GroupModel usrGroup__ = null;
    
    /**デフォルトグループSid*/
    private int defaultGroupSid__ = -1;

    /**
     * <p>usrGroup を取得します。
     * @return usrGroup
     */
    public GroupModel getUsrGroup() {
        return usrGroup__;
    }

    /**
     * <p>usrGroup をセットします。
     * @param usrGroup usrGroup
     */
    public void setUsrGroup(GroupModel usrGroup) {
        this.usrGroup__ = usrGroup;
    }

    /**
     * <p>defaultGroupSid を取得します。
     * @return defaultGroupSid
     */
    public int getDefaultGroupSid() {
        return defaultGroupSid__;
    }

    /**
     * <p>defaultGroupSid をセットします。
     * @param i defaultGroupSid
     */
    public void setDefaultGroupSid(int i) {
        this.defaultGroupSid__ = i;
    }
    
    
    
}
