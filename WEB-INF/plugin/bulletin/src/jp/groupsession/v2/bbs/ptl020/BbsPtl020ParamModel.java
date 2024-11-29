package jp.groupsession.v2.bbs.ptl020;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ポートレット スレッド一覧選択の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsPtl020ParamModel extends AbstractParamModel {
    /** ポータルSID */
    private int ptlPortalSid__ = 0;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";
    /** プラグインポートレットコンボ */
    private List<LabelValueBean> portletTypeCombo__ = null;

    /** ページコンボ上 */
    private int bbsptl020page1__ = 0;
    /** ページコンボ下 */
    private int bbsptl020page2__ = 0;
    /** フォーラムSID */
    private int bbsptl020forumSid__ = 0;

    /** フォーラム一覧 */
    List<BbsForInfModel> forumList__ = null;
    /** ページラベル */
    private ArrayList<LabelValueBean> bbsPageLabel__;

    /** フォーラム選択フラグ */
    private boolean bbsptl020selectFlg__ = false;

    /** フォーラム画像バイナリSID */
    private long bbsptl020binSid__ = 0;

    /** 設定済みのフォーラムリスト */
    private List<Integer> bbsPtl020createdForumList__ = null;

    /**
     * <p>bbsPageLabel を取得します。
     * @return bbsPageLabel
     */
    public ArrayList<LabelValueBean> getBbsPageLabel() {
        return bbsPageLabel__;
    }
    /**
     * <p>bbsPageLabel をセットします。
     * @param bbsPageLabel bbsPageLabel
     */
    public void setBbsPageLabel(ArrayList<LabelValueBean> bbsPageLabel) {
        bbsPageLabel__ = bbsPageLabel;
    }
    /**
     * <p>bbsptl020forumSid を取得します。
     * @return bbsptl020forumSid
     */
    public int getBbsptl020forumSid() {
        return bbsptl020forumSid__;
    }
    /**
     * <p>bbsptl020forumSid をセットします。
     * @param bbsptl020forumSid bbsptl020forumSid
     */
    public void setBbsptl020forumSid(int bbsptl020forumSid) {
        bbsptl020forumSid__ = bbsptl020forumSid;
    }
    /**
     * <p>bbsptl020page1 を取得します。
     * @return bbsptl020page1
     */
    public int getBbsptl020page1() {
        return bbsptl020page1__;
    }
    /**
     * <p>bbsptl020page1 をセットします。
     * @param bbsptl020page1 bbsptl020page1
     */
    public void setBbsptl020page1(int bbsptl020page1) {
        bbsptl020page1__ = bbsptl020page1;
    }
    /**
     * <p>bbsptl020page2 を取得します。
     * @return bbsptl020page2
     */
    public int getBbsptl020page2() {
        return bbsptl020page2__;
    }
    /**
     * <p>bbsptl020page2 をセットします。
     * @param bbsptl020page2 bbsptl020page2
     */
    public void setBbsptl020page2(int bbsptl020page2) {
        bbsptl020page2__ = bbsptl020page2;
    }
    /**
     * <p>forumList を取得します。
     * @return forumList
     */
    public List<BbsForInfModel> getForumList() {
        return forumList__;
    }
    /**
     * <p>forumList をセットします。
     * @param forumList forumList
     */
    public void setForumList(List<BbsForInfModel> forumList) {
        forumList__ = forumList;
    }
    /**
     * <p>portletType を取得します。
     * @return ptl080PluginPortlet
     */
    public String getPtl080PluginPortlet() {
        return ptl080PluginPortlet__;
    }
    /**
     * <p>portletType をセットします。
     * @param ptl080PluginPortlet ptl080PluginPortlet
     */
    public void setPtl080PluginPortlet(String ptl080PluginPortlet) {
        ptl080PluginPortlet__ = ptl080PluginPortlet;
    }
    /**
     * <p>portletTypeCombo を取得します。
     * @return portletTypeCombo
     */
    public List<LabelValueBean> getPortletTypeCombo() {
        return portletTypeCombo__;
    }
    /**
     * <p>portletTypeCombo をセットします。
     * @param portletTypeCombo portletTypeCombo
     */
    public void setPortletTypeCombo(List<LabelValueBean> portletTypeCombo) {
        portletTypeCombo__ = portletTypeCombo;
    }
    /**
     * <p>ptlPortalSid を取得します。
     * @return ptlPortalSid
     */
    public int getPtlPortalSid() {
        return ptlPortalSid__;
    }
    /**
     * <p>ptlPortalSid をセットします。
     * @param ptlPortalSid ptlPortalSid
     */
    public void setPtlPortalSid(int ptlPortalSid) {
        ptlPortalSid__ = ptlPortalSid;
    }
    /**
     * @return bbsptl020selectFlg
     */
    public boolean isBbsptl020selectFlg() {
        return bbsptl020selectFlg__;
    }
    /**
     * @param bbsptl020selectFlg セットする bbsptl020selectFlg
     */
    public void setBbsptl020selectFlg(boolean bbsptl020selectFlg) {
        bbsptl020selectFlg__ = bbsptl020selectFlg;
    }
    /**
     * @return bbsptl020binSid
     */
    public long getBbsptl020binSid() {
        return bbsptl020binSid__;
    }
    /**
     * @param bbsptl020binSid セットする bbsptl020binSid
     */
    public void setBbsptl020binSid(long bbsptl020binSid) {
        bbsptl020binSid__ = bbsptl020binSid;
    }
    /**
     * <p>bbsPtl020createdForumList を取得します。
     * @return bbsPtl020createdForumList
     */
    public List<Integer> getBbsPtl020createdForumList() {
        return bbsPtl020createdForumList__;
    }
    /**
     * <p>bbsPtl020createdForumList をセットします。
     * @param bbsPtl020createdForumList bbsPtl020createdForumList
     */
    public void setBbsPtl020createdForumList(
            List<Integer> bbsPtl020createdForumList) {
        bbsPtl020createdForumList__ = bbsPtl020createdForumList;
    }
}