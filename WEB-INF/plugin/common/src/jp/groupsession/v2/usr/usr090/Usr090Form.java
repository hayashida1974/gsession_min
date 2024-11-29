package jp.groupsession.v2.usr.usr090;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 固体識別番号 入力履歴画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr090Form  extends AbstractGsForm {

    /** 1ページ表示件数 */
    public static final int PAGE_MAX_DATA_CMT = 10;

    /** 検索対象 ユーザSID */
    private int targetUsrSid__;
    /** 変更対象 固体識別番号 */
    private int targetUidNumber__;

    /** ソートキー */
    private int usr090SortKey__ = GSConstUser.SORT_DATE;
    /** オーダーキー */
    private int usr090OrderKey__ = GSConst.ORDER_KEY_DESC;
    /** ページ(上) */
    private int usr090PageTop__ = 1;
    /** ページ(下) */
    private int usr090PageBottom__ = 1;
    /** ページリスト */
    private List<LabelValueBean> usr090PageList__;

    /** 固体識別番号 履歴一覧 */
    private ArrayList<Usr090Model> usr090UidList__;

    /**
     * <p>targetUsrSid を取得します。
     * @return targetUsrSid
     */
    public int getTargetUsrSid() {
        return targetUsrSid__;
    }
    /**
     * <p>targetUsrSid をセットします。
     * @param targetUsrSid targetUsrSid
     */
    public void setTargetUsrSid(int targetUsrSid) {
        targetUsrSid__ = targetUsrSid;
    }
    /**
     * <p>targetUidNumber を取得します。
     * @return targetUidNumber
     */
    public int getTargetUidNumber() {
        return targetUidNumber__;
    }
    /**
     * <p>targetUidNumber をセットします。
     * @param targetUidNumber targetUidNumber
     */
    public void setTargetUidNumber(int targetUidNumber) {
        targetUidNumber__ = targetUidNumber;
    }
    /**
     * <p>usr090SortKey を取得します。
     * @return usr090SortKey
     */
    public int getUsr090SortKey() {
        return usr090SortKey__;
    }
    /**
     * <p>usr090SortKey をセットします。
     * @param usr090SortKey usr090SortKey
     */
    public void setUsr090SortKey(int usr090SortKey) {
        usr090SortKey__ = usr090SortKey;
    }
    /**
     * <p>usr090OrderKey を取得します。
     * @return usr090OrderKey
     */
    public int getUsr090OrderKey() {
        return usr090OrderKey__;
    }
    /**
     * <p>usr090OrderKey をセットします。
     * @param usr090OrderKey usr090OrderKey
     */
    public void setUsr090OrderKey(int usr090OrderKey) {
        usr090OrderKey__ = usr090OrderKey;
    }
    /**
     * <p>usr090PageTop を取得します。
     * @return usr090PageTop
     */
    public int getUsr090PageTop() {
        return usr090PageTop__;
    }
    /**
     * <p>usr090PageTop をセットします。
     * @param usr090PageTop usr090PageTop
     */
    public void setUsr090PageTop(int usr090PageTop) {
        usr090PageTop__ = usr090PageTop;
    }
    /**
     * <p>usr090PageBottom を取得します。
     * @return usr090PageBottom
     */
    public int getUsr090PageBottom() {
        return usr090PageBottom__;
    }
    /**
     * <p>usr090PageBottom をセットします。
     * @param usr090PageBottom usr090PageBottom
     */
    public void setUsr090PageBottom(int usr090PageBottom) {
        usr090PageBottom__ = usr090PageBottom;
    }
    /**
     * <p>usr090PageList を取得します。
     * @return usr090PageList
     */
    public List<LabelValueBean> getUsr090PageList() {
        return usr090PageList__;
    }
    /**
     * <p>usr090PageList をセットします。
     * @param usr090PageList usr090PageList
     */
    public void setUsr090PageList(List<LabelValueBean> usr090PageList) {
        usr090PageList__ = usr090PageList;
    }
    /**
     * <p>usr090UidList を取得します。
     * @return usr090UidList
     */
    public ArrayList<Usr090Model> getUsr090UidList() {
        return usr090UidList__;
    }
    /**
     * <p>usr090UidList をセットします。
     * @param usr090UidList usr090UidList
     */
    public void setUsr090UidList(ArrayList<Usr090Model> usr090UidList) {
        usr090UidList__ = usr090UidList;
    }
}