package jp.groupsession.v2.bbs.bbs220;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Form;
import jp.groupsession.v2.bbs.dao.BbsSoukouDao;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 草稿一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs220Form extends Bbs060Form {

    /** 遷移先：フォーラム一覧*/
    public static final String BACK_FORUM = "backForum";
    /** 遷移先：スレッド一覧*/
    public static final String BACK_THREAD = "backThread";
    /** 遷移先：投稿一覧*/
    public static final String BACK_TOKO = "backToko";
    /** 草稿一覧 */
    private List<BulletinSoukouModel> bbs220SoukouList__ = null;
    /** 選択草稿区分 */
    private int bbs220SelectType__ = -1;
    /** 選択草稿情報 */
    private BulletinSoukouModel bbs220SelectSoukoInfo__ = null;
    /** 選択草稿バイナリSID */
    private Long bbs220SelectBinSid__ = new Long(0);
    /** 投稿本文ファイルSID */
    private int bbs220BodyFileId__ = 0;
    /** 削除対象SID */
    private String[] bbs220delInfSid__;




    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateDeleteBbs220(
            RequestModel reqMdl,
            Connection con,
            int usrSid) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // 入力チェック
        if (bbs220delInfSid__ == null || bbs220delInfSid__.length < 1) {
            String msgKey = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cmn.draft"));
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }
        // 草稿存在確認
        BbsSoukouDao dao = new BbsSoukouDao(con);
        for (String target:bbs220delInfSid__) {
            int sid = NullDefault.getInt(target, -1);
            boolean chk = dao.existSoukou(sid, usrSid);
            if (!chk) {
                String msgKey = "search.data.notfound";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cmn.draft"));
                StrutsUtil.addMessage(errors, msg, msgKey);
                break;
            }
        }
        return errors;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateEditBbs220(
            RequestModel reqMdl,
            Connection con,
            int usrSid) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // 草稿タイプチェック
        if (bbs220SelectType__ != GSConstBulletin.SOUKOU_TYPE_THREAD
                && bbs220SelectType__ != GSConstBulletin.SOUKOU_TYPE_TOUKOU) {
            String msgKey = "search.data.notfound";
            ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cmn.draft"));
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }
        // 草稿存在確認
        BbsSoukouDao dao = new BbsSoukouDao(con);
        boolean chk = dao.existSoukou(this.getSoukouSid(), usrSid);
        if (!chk) {
            String msgKey = "search.data.notfound";
            ActionMessage msg = new ActionMessage(msgKey,
                    gsMsg.getMessage("cmn.draft"));
            StrutsUtil.addMessage(errors, msg, msgKey);
        }
        return errors;
    }


    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     */
    public void setHiddenParamBbs220(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("bbs220SortKey", this.getBbs220SortKey());
        cmn999Form.addHiddenParam("bbs220OrderKey", this.getBbs220OrderKey());
        cmn999Form.addHiddenParam("bbs220BackDsp", this.getBbs220BackDsp());
        for (String delSid: this.getBbs220delInfSid()) {
            cmn999Form.addHiddenParam("bbs220delInfSid", delSid);
        }
        cmn999Form.addHiddenParam("bbs010page1", this.getBbs010page1());
        cmn999Form.addHiddenParam("bbs010forumSid", this.getBbs010forumSid());
        cmn999Form.addHiddenParam("threadSid", this.getThreadSid());
        cmn999Form.addHiddenParam("bbsmainFlg", this.getBbsmainFlg());
        cmn999Form.addHiddenParam("bbs060sortKey", this.getBbs060sortKey());
        cmn999Form.addHiddenParam("bbs060orderKey", this.getBbs060orderKey());
        cmn999Form.addHiddenParam("bbs060page1", this.getBbs060page1());
        cmn999Form.addHiddenParam("bbs060page2", this.getBbs060page2());
        cmn999Form.addHiddenParam("searchDspID", this.getSearchDspID());
        cmn999Form.addHiddenParam("bbs040forumSid", this.getBbs040forumSid());
        cmn999Form.addHiddenParam("bbs040keyKbn", this.getBbs040keyKbn());
        cmn999Form.addHiddenParam("bbs040taisyouThread", this.getBbs040taisyouThread());
        cmn999Form.addHiddenParam("bbs040taisyouNaiyou", this.getBbs040taisyouNaiyou());
        cmn999Form.addHiddenParam("bbs040userName", this.getBbs040userName());
        cmn999Form.addHiddenParam("bbs040readKbn", this.getBbs040readKbn());
        cmn999Form.addHiddenParam("bbs040dateNoKbn", this.getBbs040dateNoKbn());
        cmn999Form.addHiddenParam("bbs040fromYear", this.getBbs040fromYear());
        cmn999Form.addHiddenParam("bbs040fromMonth", this.getBbs040fromMonth());
        cmn999Form.addHiddenParam("bbs040fromDay", this.getBbs040fromDay());
        cmn999Form.addHiddenParam("bbs040toYear", this.getBbs040toYear());
        cmn999Form.addHiddenParam("bbs040toMonth", this.getBbs040toMonth());
        cmn999Form.addHiddenParam("bbs040toDay", this.getBbs040toDay());
        cmn999Form.addHiddenParam("bbs041page1", this.getBbs041page1());
        cmn999Form.addHiddenParam("bbs060postOrderKey", this.getBbs060postOrderKey());
        cmn999Form.addHiddenParam("bbs060postPage1", this.getBbs060postPage1());
        cmn999Form.addHiddenParam("bbs060reply", this.getBbs060reply());
        cmn999Form.addHiddenParam("bbs060showThreBtn", this.getBbs060showThreBtn());
        cmn999Form.addHiddenParam("bbs060postBinSid", this.getBbs060postBinSid());
        cmn999Form.addHiddenParam("bbs060postSid", this.getBbs060postSid());
    }


    /**
     * <p>bbs220SoukouList を取得します。
     * @return bbs220SoukouList
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SoukouList__
     */
    public List<BulletinSoukouModel> getBbs220SoukouList() {
        return bbs220SoukouList__;
    }


    /**
     * <p>bbs220SoukouList をセットします。
     * @param bbs220SoukouList bbs220SoukouList
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SoukouList__
     */
    public void setBbs220SoukouList(List<BulletinSoukouModel> bbs220SoukouList) {
        bbs220SoukouList__ = bbs220SoukouList;
    }

    /**
     * <p>bbs220SelectType を取得します。
     * @return bbs220SelectType
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SelectType__
     */
    public int getBbs220SelectType() {
        return bbs220SelectType__;
    }


    /**
     * <p>bbs220SelectType をセットします。
     * @param bbs220SelectType bbs220SelectType
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SelectType__
     */
    public void setBbs220SelectType(int bbs220SelectType) {
        bbs220SelectType__ = bbs220SelectType;
    }


    /**
     * <p>bbs220SelectSoukoInfo を取得します。
     * @return bbs220SelectSoukoInfo
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SelectSoukoInfo__
     */
    public BulletinSoukouModel getBbs220SelectSoukoInfo() {
        return bbs220SelectSoukoInfo__;
    }


    /**
     * <p>bbs220SelectSoukoInfo をセットします。
     * @param bbs220SelectSoukoInfo bbs220SelectSoukoInfo
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SelectSoukoInfo__
     */
    public void setBbs220SelectSoukoInfo(
            BulletinSoukouModel bbs220SelectSoukoInfo) {
        bbs220SelectSoukoInfo__ = bbs220SelectSoukoInfo;
    }


    /**
     * <p>bbs220SelectBinSid を取得します。
     * @return bbs220SelectBinSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SelectBinSid__
     */
    public Long getBbs220SelectBinSid() {
        return bbs220SelectBinSid__;
    }


    /**
     * <p>bbs220SelectBinSid をセットします。
     * @param bbs220SelectBinSid bbs220SelectBinSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220SelectBinSid__
     */
    public void setBbs220SelectBinSid(Long bbs220SelectBinSid) {
        bbs220SelectBinSid__ = bbs220SelectBinSid;
    }


    /**
     * <p>bbs220BodyFileId を取得します。
     * @return bbs220BodyFileId
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220BodyFileId__
     */
    public int getBbs220BodyFileId() {
        return bbs220BodyFileId__;
    }


    /**
     * <p>bbs220BodyFileId をセットします。
     * @param bbs220BodyFileId bbs220BodyFileId
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220BodyFileId__
     */
    public void setBbs220BodyFileId(int bbs220BodyFileId) {
        bbs220BodyFileId__ = bbs220BodyFileId;
    }


    /**
     * <p>bbs220delInfSid を取得します。
     * @return bbs220delInfSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220delInfSid__
     */
    public String[] getBbs220delInfSid() {
        return bbs220delInfSid__;
    }


    /**
     * <p>bbs220delInfSid をセットします。
     * @param bbs220delInfSid bbs220delInfSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220Form#bbs220delInfSid__
     */
    public void setBbs220delInfSid(String[] bbs220delInfSid) {
        bbs220delInfSid__ = bbs220delInfSid;
    }



}