package jp.groupsession.v2.bbs.ptl010;

import java.util.List;

import jp.groupsession.v2.bbs.bbs010.Bbs010ParamModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;

/**
 * <br>[機  能] ポートレット スレッド一覧の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsPtl010ParamModel extends Bbs010ParamModel {
    /** スレッド一覧 */
    private List < BulletinDspModel > threadList__ = null;
    /** 掲示板トップ画面URL */
    private String  bbsTopUrl__;

    /** フォーラムSID */
    private int bbsPtlBfiSid__ = 0;
    /** ポートレットアイテムID */
    private String bbsPtl010ItemId__ = "";

    /** フォーラム名 */
    private String bbsPtlBfiName__ = "";
    /** ページコンボ上 */
    private int bbsPtl010page1__ = 0;
    /** ページコンボ下 */
    private int bbsPtl010page2__ = 0;

    /**
     * @return bbsTopUrl__ を戻します。
     */
    public String getBbsTopUrl() {
        return bbsTopUrl__;
    }
    /**
     * @param bbsTopUrl 設定する bbsTopUrl__。
     */
    public void setBbsTopUrl(String bbsTopUrl) {
        bbsTopUrl__ = bbsTopUrl;
    }
    /**
     * <p>threadList を取得します。
     * @return threadList
     */
    public List<BulletinDspModel> getThreadList() {
        return threadList__;
    }
    /**
     * <p>threadList をセットします。
     * @param threadList threadList
     */
    public void setThreadList(List<BulletinDspModel> threadList) {
        threadList__ = threadList;
    }
    /**
     * @return bbsPtlBfiSid
     */
    public int getBbsPtlBfiSid() {
        return bbsPtlBfiSid__;
    }
    /**
     * @param bbsPtlBfiSid セットする bbsPtlBfiSid
     */
    public void setBbsPtlBfiSid(int bbsPtlBfiSid) {
        bbsPtlBfiSid__ = bbsPtlBfiSid;
    }
    /**
     * @return bbsPtlBfiName
     */
    public String getBbsPtlBfiName() {
        return bbsPtlBfiName__;
    }
    /**
     * @param bbsPtlBfiName セットする bbsPtlBfiName
     */
    public void setBbsPtlBfiName(String bbsPtlBfiName) {
        bbsPtlBfiName__ = bbsPtlBfiName;
    }
    /**
     * <p>bbsPtl010page1 を取得します。
     * @return bbsPtl010page1
     */
    public int getBbsPtl010page1() {
        return bbsPtl010page1__;
    }
    /**
     * <p>bbsPtl010page1 をセットします。
     * @param bbsPtl010page1 bbsPtl010page1
     */
    public void setBbsPtl010page1(int bbsPtl010page1) {
        bbsPtl010page1__ = bbsPtl010page1;
    }
    /**
     * <p>bbsPtl010page2 を取得します。
     * @return bbsPtl010page2
     */
    public int getBbsPtl010page2() {
        return bbsPtl010page2__;
    }
    /**
     * <p>bbsPtl010page2 をセットします。
     * @param bbsPtl010page2 bbsPtl010page2
     */
    public void setBbsPtl010page2(int bbsPtl010page2) {
        bbsPtl010page2__ = bbsPtl010page2;
    }
    /**
     * <p>bbsPtl010ItemId を取得します。
     * @return bbsPtl010ItemId
     */
    public String getBbsPtl010ItemId() {
        return bbsPtl010ItemId__;
    }
    /**
     * <p>bbsPtl010ItemId をセットします。
     * @param bbsPtl010ItemId bbsPtl010ItemId
     */
    public void setBbsPtl010ItemId(String bbsPtl010ItemId) {
        bbsPtl010ItemId__ = bbsPtl010ItemId;
    }
}