package jp.groupsession.v2.usr.usr041;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr042.Usr042Form;

/**
 * <br>[機  能] ユーザ情報 個人設定 表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr041Form extends Usr042Form {
    /** 表示件数 */
    private int usr041DspCnt__ = GSConstUser.DEFAULT_DSP_CNT;
    /** 表示件数コンボ */
    private List < LabelValueBean > usr041DspCntList__ = null;

    /** デフォルト表示順設定区分 */
    private int usr041DefoDspKbn__ = -1;
    /** 管理者権限 ソートキー1 */
    private int usr041SortKey1__ = GSConstUser.USER_SORT_YKSK;
    /** 管理者権限 ソートキー1オーダー */
    private int usr041SortOrder1__ = GSConst.ORDER_KEY_ASC;
    /** 管理者権限 ソートキー2 */
    private int usr041SortKey2__ = GSConstUser.USER_SORT_NAME;
    /** 管理者権限 ソートキー2オーダー */
    private int usr041SortOrder2__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー ラベル */
    private List<LabelValueBean> usr041SortKeyLabel__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Usr041Form() {
    }

    /**
     * <p>usr041DspCnt を取得します。
     * @return usr041DspCnt
     */
    public int getUsr041DspCnt() {
        return usr041DspCnt__;
    }
    /**
     * <p>usr041DspCnt をセットします。
     * @param usr041DspCnt usr041DspCnt
     */
    public void setUsr041DspCnt(int usr041DspCnt) {
        usr041DspCnt__ = usr041DspCnt;
    }
    /**
     * <p>usr041DspCntList を取得します。
     * @return usr041DspCntList
     */
    public List<LabelValueBean> getUsr041DspCntList() {
        return usr041DspCntList__;
    }
    /**
     * <p>usr041DspCntList をセットします。
     * @param usr041DspCntList usr041DspCntList
     */
    public void setUsr041DspCntList(List<LabelValueBean> usr041DspCntList) {
        usr041DspCntList__ = usr041DspCntList;
    }
    /**
     * @return usr041DefoDspKbn
     */
    public int getUsr041DefoDspKbn() {
        return usr041DefoDspKbn__;
    }
    /**
     * @param usr041DefoDspKbn 設定する usr041DefoDspKbn
     */
    public void setUsr041DefoDspKbn(int usr041DefoDspKbn) {
        usr041DefoDspKbn__ = usr041DefoDspKbn;
    }

    /**
     * @return usr041SortKey1
     */
    public int getUsr041SortKey1() {
        return usr041SortKey1__;
    }

    /**
     * @param usr041SortKey1 設定する usr041SortKey1
     */
    public void setUsr041SortKey1(int usr041SortKey1) {
        usr041SortKey1__ = usr041SortKey1;
    }

    /**
     * @return usr041SortKey2
     */
    public int getUsr041SortKey2() {
        return usr041SortKey2__;
    }

    /**
     * @param usr041SortKey2 設定する usr041SortKey2
     */
    public void setUsr041SortKey2(int usr041SortKey2) {
        usr041SortKey2__ = usr041SortKey2;
    }

    /**
     * @return usr041SortOrder1
     */
    public int getUsr041SortOrder1() {
        return usr041SortOrder1__;
    }

    /**
     * @param usr041SortOrder1 設定する usr041SortOrder1
     */
    public void setUsr041SortOrder1(int usr041SortOrder1) {
        usr041SortOrder1__ = usr041SortOrder1;
    }

    /**
     * @return usr041SortOrder2
     */
    public int getUsr041SortOrder2() {
        return usr041SortOrder2__;
    }

    /**
     * @param usr041SortOrder2 設定する usr041SortOrder2
     */
    public void setUsr041SortOrder2(int usr041SortOrder2) {
        usr041SortOrder2__ = usr041SortOrder2;
    }

    /**
     * @return usr041SortKeyLabel
     */
    public List<LabelValueBean> getUsr041SortKeyLabel() {
        return usr041SortKeyLabel__;
    }

    /**
     * @param usr041SortKeyLabel 設定する usr041SortKeyLabel
     */
    public void setUsr041SortKeyLabel(List<LabelValueBean> usr041SortKeyLabel) {
        usr041SortKeyLabel__ = usr041SortKeyLabel;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param con DBコネクション
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(
            HttpServletRequest req)
                    throws Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(req);

        //ソートキー1、ソートキー2の重複チェックを行う
        if (usr041SortKey1__ == usr041SortKey2__) {
            String eprefix = "sortkey.";
            String dispName = gsMsg.getMessage("cmn.default.display.order.setting");
            String sortKey1 = gsMsg.getMessage("cmn.first.key");
            String sortKey2 = gsMsg.getMessage("cmn.second.key");
            ActionMessage msg = new ActionMessage(
                    "error.select.dup.list3", dispName, sortKey1, sortKey2);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        }

        return errors;
    }
}