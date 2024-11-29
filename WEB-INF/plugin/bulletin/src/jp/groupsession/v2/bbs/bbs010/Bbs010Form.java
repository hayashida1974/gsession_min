package jp.groupsession.v2.bbs.bbs010;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.AbstractForm;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinNewBbsModel;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 掲示板 フォーラム一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs010Form extends AbstractForm {

    /** 検索キーワード */
    private String s_key__ = null;
    /** ページコンボ上 */
    private int bbs010page1__ = 0;
    /** ページコンボ下 */
    private int bbs010page2__ = 0;
    /** フォーラムSID */
    private int bbs010forumSid__ = 0;

    /** 管理者フラグ */
    private int bbs010AdminFlg__ = 0;
    /** フォーラム一覧 */
    private List < BulletinDspModel > forumList__ = null;
    /** ページラベル */
    private ArrayList<LabelValueBean> bbsPageLabel__;

    /** 遷移元 メイン個人メニュー:2 メイン管理者メニュー:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** フォーラムSID(画像バイナリSIDのチェック用) */
    private int bbs010ForSid__;
    /** バイナリSID */
    private Long bbs010BinSid__ = new Long(0);

    /** 新着スレッド一覧リスト */
    private ArrayList<BulletinNewBbsModel> shinThredList__;

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
     * @return bbs010forumSid を戻します。
     */
    public int getBbs010forumSid() {
        return bbs010forumSid__;
    }
    /**
     * @param bbs010forumSid 設定する bbs01forumSid。
     */
    public void setBbs010forumSid(int bbs010forumSid) {
        bbs010forumSid__ = bbs010forumSid;
    }
    /**
     * @return bbs010page1 を戻します。
     */
    public int getBbs010page1() {
        return bbs010page1__;
    }
    /**
     * @param bbs010page1 設定する bbs010page1。
     */
    public void setBbs010page1(int bbs010page1) {
        bbs010page1__ = bbs010page1;
    }
    /**
     * @return bbs010page2 を戻します。
     */
    public int getBbs010page2() {
        return bbs010page2__;
    }
    /**
     * @param bbs010page2 設定する bbs010page2。
     */
    public void setBbs010page2(int bbs010page2) {
        bbs010page2__ = bbs010page2;
    }
    /**
     * @return s_key を戻します。
     */
    public String getS_key() {
        return s_key__;
    }
    /**
     * @param skey 設定する s_key。
     */
    public void setS_key(String skey) {
        s_key__ = skey;
    }

    /**
     * @return forumList を戻します。
     */
    public List < BulletinDspModel > getForumList() {
        return forumList__;
    }
    /**
     * @param forumList 設定する forumList。
     */
    public void setForumList(List < BulletinDspModel > forumList) {
        forumList__ = forumList;
    }
    /**
     * @return bbsPageLabel を戻します。
     */
    public ArrayList<LabelValueBean> getBbsPageLabel() {
        return bbsPageLabel__;
    }
    /**
     * @param bbsPageLabel 設定する bbsPageLabel。
     */
    public void setBbsPageLabel(ArrayList<LabelValueBean> bbsPageLabel) {
        bbsPageLabel__ = bbsPageLabel;
    }
    /**
     * @return bbs010AdminFlg を戻します。
     */
    public int getBbs010AdminFlg() {
        return bbs010AdminFlg__;
    }
    /**
     * @param bbs010AdminFlg 設定する bbs010AdminFlg。
     */
    public void setBbs010AdminFlg(int bbs010AdminFlg) {
        bbs010AdminFlg__ = bbs010AdminFlg;
    }

    /**
     * <p>bbs010BinSid を取得します。
     * @return bbs010BinSid
     */
    public Long getBbs010BinSid() {
        return bbs010BinSid__;
    }

    /**
     * <p>bbs010BinSid をセットします。
     * @param bbs010BinSid bbs010BinSid
     */
    public void setBbs010BinSid(Long bbs010BinSid) {
        bbs010BinSid__ = bbs010BinSid;
    }

    /**
     * <p>bbs010ForSid を取得します。
     * @return bbs010ForSid
     */
    public int getBbs010ForSid() {
        return bbs010ForSid__;
    }

    /**
     * <p>bbs010ForSid をセットします。
     * @param bbs010ForSid bbs010ForSid
     */
    public void setBbs010ForSid(int bbs010ForSid) {
        bbs010ForSid__ = bbs010ForSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String textSearchKey = gsMsg.getMessage(req, "cmn.search.keyword");

        //未入力チェック
        if (StringUtil.isNullZeroString(s_key__)) {
            msg = new ActionMessage("error.input.required.text", textSearchKey);
            StrutsUtil.addMessage(errors, msg, "s_key");
            return errors;
        }

        //スペースのみチェック
        if (ValidateUtil.isSpace(s_key__)) {
            msg = new ActionMessage("error.input.spase.only", textSearchKey);
            StrutsUtil.addMessage(errors, msg, "s_key");
        }
        //先頭スペースチェック
        if (ValidateUtil.isSpaceStart(s_key__)) {
            msg = new ActionMessage("error.input.spase.start", textSearchKey);
            StrutsUtil.addMessage(errors, msg, "s_key");
        }
        //JIS第2水準チェック
        if (!GSValidateUtil.isGsJapaneaseString(s_key__)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(s_key__);
            msg = new ActionMessage("error.input.njapan.text", textSearchKey,
                                    nstr);
            StrutsUtil.addMessage(errors, msg, "s_key");
        }

        return errors;
    }

    /**
     * @return shinThredList
     */
    public ArrayList<BulletinNewBbsModel> getShinThredList() {
        return shinThredList__;
    }

    /**
     * @param shinThredList セットする shinThredList
     */
    public void setShinThredList(ArrayList<BulletinNewBbsModel> shinThredList) {
        shinThredList__ = shinThredList;
    }

}
