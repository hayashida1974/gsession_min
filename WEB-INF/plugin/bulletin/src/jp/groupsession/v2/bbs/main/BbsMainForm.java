package jp.groupsession.v2.bbs.main;

import java.util.List;

import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.bbs.model.BulletinDspModel;

/**
 * <br>[機  能] 掲示板(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsMainForm extends AbstractForm {

    /** 選択スレッドSID */
    private int bbsmainBtiSid__ = 0;

    /** スレッド一覧 */
    private List < BulletinDspModel > threadList__ = null;
    /** 掲示板トップ画面URL */
    private String  bbsTopUrl__;
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
     * <p>bbsmainBtiSid を取得します。
     * @return bbsmainBtiSid
     */
    public int getBbsmainBtiSid() {
        return bbsmainBtiSid__;
    }
    /**
     * <p>bbsmainBtiSid をセットします。
     * @param bbsmainBtiSid bbsmainBtiSid
     */
    public void setBbsmainBtiSid(int bbsmainBtiSid) {
        bbsmainBtiSid__ = bbsmainBtiSid;
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

}
