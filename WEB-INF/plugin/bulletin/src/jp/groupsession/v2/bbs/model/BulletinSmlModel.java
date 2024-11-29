package jp.groupsession.v2.bbs.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 更新通知用ショートメールの情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinSmlModel {

    /** フォーラムSID */
    private int bfiSid__ = 0;
    /** タイトル */
    private String btiTitle__  = null;
    /** 本文 */
    private String bwiValue__ = null;
    /** ユーザSID(投稿者) */
    private int userSid__ = 0;
    /** 投稿者グループSID */
    private int grpSid__ = 0;
    /** 投稿 登録日時 */
    private UDate bwiAdate__ = null;
    /** 添付ファイルリスト */
    List < LabelValueBean > fileLabelList__ = null;
    /** スレッドURL */
    private String threadUrl__ = null;

    /**
     * <p>bfiSid を取得します。
     * @return bfiSid
     */
    public int getBfiSid() {
        return bfiSid__;
    }
    /**
     * <p>bfiSid をセットします。
     * @param bfiSid bfiSid
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }
    /**
     * <p>btiTitle を取得します。
     * @return btiTitle
     */
    public String getBtiTitle() {
        return btiTitle__;
    }
    /**
     * <p>btiTitle をセットします。
     * @param btiTitle btiTitle
     */
    public void setBtiTitle(String btiTitle) {
        btiTitle__ = btiTitle;
    }
    /**
     * <p>bwiAdate を取得します。
     * @return bwiAdate
     */
    public UDate getBwiAdate() {
        return bwiAdate__;
    }
    /**
     * <p>bwiAdate をセットします。
     * @param bwiAdate bwiAdate
     */
    public void setBwiAdate(UDate bwiAdate) {
        bwiAdate__ = bwiAdate;
    }
    /**
     * <p>bwiValue を取得します。
     * @return bwiValue
     */
    public String getBwiValue() {
        return bwiValue__;
    }
    /**
     * <p>bwiValue をセットします。
     * @param bwiValue bwiValue
     */
    public void setBwiValue(String bwiValue) {
        bwiValue__ = bwiValue;
    }
    /**
     * <p>fileLabelList を取得します。
     * @return fileLabelList
     */
    public List<LabelValueBean> getFileLabelList() {
        return fileLabelList__;
    }
    /**
     * <p>fileLabelList をセットします。
     * @param fileLabelList fileLabelList
     */
    public void setFileLabelList(List<LabelValueBean> fileLabelList) {
        fileLabelList__ = fileLabelList;
    }
    /**
     * <p>threadUrl を取得します。
     * @return threadUrl
     */
    public String getThreadUrl() {
        return threadUrl__;
    }
    /**
     * <p>threadUrl をセットします。
     * @param threadUrl threadUrl
     */
    public void setThreadUrl(String threadUrl) {
        threadUrl__ = threadUrl;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }

}
