package jp.groupsession.v2.cmn.cmn210;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] グループ選択ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn210Form extends AbstractGsForm {

    /** 選択不可グループ 有効フラグ 有効 */
    protected static final int DISABLE_GROUP_FLG_ON_ = 1;

    /** グループリスト */
    ArrayList<GroupModel> groupList__ = null;
    /** マイグループリスト */
    ArrayList<CmnLabelValueModel> myGroupList__ = null;
    /** マイグループ表示フラグ */
    int myGroupFlg__ = 0;
    /** スクリプト文字列1 */
    String scriptStr__ = null;
    /** スクリプト文字列2 */
    String scriptStr2__ = null;
    /** 親画面ID */
    String parentDspID__ = null;
    /** 親画面セレクトボックス名 */
    String selBoxName__ = null;
    /** 親画面セレクトボックス名 */
    int submitFlg__ = 0;
    /** 選択不可グループSID */
    private String[] cmn210disableGroupSid__ = null;
    /** グループリスト disable属性リスト*/
    List<Integer> groupDisabledKbnList__ = null;
    /** 選択不可グループ 有効フラグ */
    private int cmn210disableGroupFlg__ = 0;

    /**
     * <p>prtPrm を取得します。
     * @return prtPrm
     */
    public String getPrtPrm() {
        return prtPrm__;
    }
    /**
     * <p>prtPrm をセットします。
     * @param prtPrm prtPrm
     */
    public void setPrtPrm(String prtPrm) {
        prtPrm__ = prtPrm;
    }
    /** 親画面部分更新ボタン名 */
    String prtPrm__ = null;

    /**
     * <p>submitFlg を取得します。
     * @return submitFlg
     */
    public int getSubmitFlg() {
        return submitFlg__;
    }
    /**
     * <p>submitFlg をセットします。
     * @param submitFlg submitFlg
     */
    public void setSubmitFlg(int submitFlg) {
        submitFlg__ = submitFlg;
    }
    /**
     * <p>myGroupList を取得します。
     * @return myGroupList
     */
    public ArrayList<CmnLabelValueModel> getMyGroupList() {
        return myGroupList__;
    }
    /**
     * <p>myGroupList をセットします。
     * @param myGroupList myGroupList
     */
    public void setMyGroupList(ArrayList<CmnLabelValueModel> myGroupList) {
        myGroupList__ = myGroupList;
    }
    /**
     * <p>myGroupFlg を取得します。
     * @return myGroupFlg
     */
    public int getMyGroupFlg() {
        return myGroupFlg__;
    }
    /**
     * <p>myGroupFlg をセットします。
     * @param myGroupFlg myGroupFlg
     */
    public void setMyGroupFlg(int myGroupFlg) {
        myGroupFlg__ = myGroupFlg;
    }

    /**
     * <p>scriptStr2 を取得します。
     * @return scriptStr2
     */
    public String getScriptStr2() {
        return scriptStr2__;
    }
    /**
     * <p>scriptStr2 をセットします。
     * @param scriptStr2 scriptStr2
     */
    public void setScriptStr2(String scriptStr2) {
        scriptStr2__ = scriptStr2;
    }
    /**
     * <p>scriptStr を取得します。
     * @return scriptStr
     */
    public String getScriptStr() {
        return scriptStr__;
    }
    /**
     * <p>scriptStr をセットします。
     * @param scriptStr scriptStr
     */
    public void setScriptStr(String scriptStr) {
        scriptStr__ = scriptStr;
    }
    /**
     * <p>parentDspID を取得します。
     * @return parentDspID
     */
    public String getParentDspID() {
        return parentDspID__;
    }
    /**
     * <p>parentDspID をセットします。
     * @param parentDspID parentDspID
     */
    public void setParentDspID(String parentDspID) {
        parentDspID__ = parentDspID;
    }
    /**
     * <p>selBoxName を取得します。
     * @return selBoxName
     */
    public String getSelBoxName() {
        return selBoxName__;
    }
    /**
     * <p>selBoxName をセットします。
     * @param selBoxName selBoxName
     */
    public void setSelBoxName(String selBoxName) {
        selBoxName__ = selBoxName;
    }
    /**
     * <p>groupList を取得します。
     * @return groupList
     */
    public ArrayList<GroupModel> getGroupList() {
        return groupList__;
    }
    /**
     * <p>groupList をセットします。
     * @param groupList groupList
     */
    public void setGroupList(ArrayList<GroupModel> groupList) {
        groupList__ = groupList;
    }
    /**
     * <p>cmn210banGroupSid を取得します。
     * @return cmn210banGroupSid
     */
    public String[] getCmn210disableGroupSid() {
        return cmn210disableGroupSid__;
    }
    /**
     * <p>cmn210disableGroupSid をセットします。
     * @param cmn210disableGroupSid cmn210banGroupSid
     */
    public void setCmn210disableGroupSid(String[] cmn210disableGroupSid) {
        cmn210disableGroupSid__ = cmn210disableGroupSid;
    }
    /**
     * <p>cmn210disableGroupFlg を取得します。
     * @return cmn210disableGroupFlg
     */
    public int getCmn210disableGroupFlg() {
        return cmn210disableGroupFlg__;
    }
    /**
     * <p>cmn210disableGroupFlg をセットします。
     * @param cmn210disableGroupFlg cmn210disableGroupFlg
     */
    public void setCmn210disableGroupFlg(int cmn210disableGroupFlg) {
        cmn210disableGroupFlg__ = cmn210disableGroupFlg;
    }
    /**
     * <p>groupDisabledKbnList を取得します。
     * @return groupDisabledKbnList
     */
    public List<Integer> getGroupDisabledKbnList() {
        return groupDisabledKbnList__;
    }
    /**
     * <p>groupDisabledKbnList をセットします。
     * @param groupDisabledKbnList groupDisabledKbnList
     */
    public void setGroupDisabledKbnList(List<Integer> groupDisabledKbnList) {
        groupDisabledKbnList__ = groupDisabledKbnList;
    }

}