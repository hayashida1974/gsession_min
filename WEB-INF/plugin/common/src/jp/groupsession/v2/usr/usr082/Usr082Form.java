package jp.groupsession.v2.usr.usr082;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr080.Usr080Form;

/**
 * <br>[機  能] ユーザ情報 管理者設定 デフォルト表示順設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr082Form extends Usr080Form {

    /** 管理者権限 ソートキー1 */
    private int usr082AdSortKey1__ = GSConstUser.USER_SORT_YKSK;
    /** 管理者権限 ソートキー1オーダー */
    private int usr082AdSortOrder1__ = GSConst.ORDER_KEY_ASC;
    /** 管理者権限 ソートキー2 */
    private int usr082AdSortKey2__ = GSConstUser.USER_SORT_NAME;
    /** 管理者権限 ソートキー2オーダー */
    private int usr082AdSortOrder2__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー ラベル */
    private List<LabelValueBean> usr082SortKeyLabel__ = null;
    /**QRコード表示区分*/
    private int usr082QrDisp__ = -1;
    /** 初期表示区分 */
    private int usr082initKbn__ = 0;
    /** デフォルト表示順設定区分 */
    private int usr082DefoDspKbn__ = -1;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Usr082Form() {
//        //ソートキーラベル
//        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
//        for (int i = 0; i < GSConstUser.LIST_SORT_KEY_USR.length; i++) {
//            String label = GSConstUser.LIST_SORT_KEY_USR_TEXT[i];
//            String value = Integer.toString(GSConstUser.LIST_SORT_KEY_USR[i]);
//            sortLabel.add(new LabelValueBean(label, value));
//        }
//        usr082SortKeyLabel__ = sortLabel;
    }

    /**
     * @return usr082AdSortKey1
     */
    public int getUsr082AdSortKey1() {
        return usr082AdSortKey1__;
    }

    /**
     * @param usr082AdSortKey1 設定する usr082AdSortKey1
     */
    public void setUsr082AdSortKey1(int usr082AdSortKey1) {
        usr082AdSortKey1__ = usr082AdSortKey1;
    }

    /**
     * @return usr082AdSortKey2
     */
    public int getUsr082AdSortKey2() {
        return usr082AdSortKey2__;
    }

    /**
     * @param usr082AdSortKey2 設定する usr082AdSortKey2
     */
    public void setUsr082AdSortKey2(int usr082AdSortKey2) {
        usr082AdSortKey2__ = usr082AdSortKey2;
    }

    /**
     * @return usr082AdSortOrder1
     */
    public int getUsr082AdSortOrder1() {
        return usr082AdSortOrder1__;
    }

    /**
     * @param usr082AdSortOrder1 設定する usr082AdSortOrder1
     */
    public void setUsr082AdSortOrder1(int usr082AdSortOrder1) {
        usr082AdSortOrder1__ = usr082AdSortOrder1;
    }

    /**
     * @return usr082AdSortOrder2
     */
    public int getUsr082AdSortOrder2() {
        return usr082AdSortOrder2__;
    }

    /**
     * @param usr082AdSortOrder2 設定する usr082AdSortOrder2
     */
    public void setUsr082AdSortOrder2(int usr082AdSortOrder2) {
        usr082AdSortOrder2__ = usr082AdSortOrder2;
    }

    /**
     * @return usr082DefoDspKbn
     */
    public int getUsr082DefoDspKbn() {
        return usr082DefoDspKbn__;
    }

    /**
     * @param usr082DefoDspKbn 設定する usr082DefoDspKbn
     */
    public void setUsr082DefoDspKbn(int usr082DefoDspKbn) {
        usr082DefoDspKbn__ = usr082DefoDspKbn;
    }

    /**
     * @return usr082SortKeyLabel
     */
    public List<LabelValueBean> getUsr082SortKeyLabel() {
        return usr082SortKeyLabel__;
    }

    /**
     * @param usr082SortKeyLabel 設定する usr082SortKeyLabel
     */
    public void setUsr082SortKeyLabel(List<LabelValueBean> usr082SortKeyLabel) {
        usr082SortKeyLabel__ = usr082SortKeyLabel;
    }

    /**
     * <p>usr082initKbn を取得します。
     * @return usr082initKbn
     */
    public int getUsr082initKbn() {
        return usr082initKbn__;
    }

    /**
     * <p>usr082initKbn をセットします。
     * @param usr082initKbn usr082initKbn
     */
    public void setUsr082initKbn(int usr082initKbn) {
        usr082initKbn__ = usr082initKbn;
    }

    /**
     * <p>usr082QrDisp を取得します。
     * @return usr082QrDisp
     */
    public int getUsr082QrDisp() {
        return usr082QrDisp__;
    }

    /**
     * <p>usr082QrDisp をセットします。
     * @param usr082QrDisp usr082QrDisp
     */
    public void setUsr082QrDisp(int usr082QrDisp) {
        usr082QrDisp__ = usr082QrDisp;
    }

    /**
     *
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] デフォルト表示順チェック・QRコード表示設定の入力値チェック
     * @param req リクエストモデル
     * @return ActionErrors アクションエラー
     */
    public ActionErrors validateCheck(
            HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        String message = null;
        int qrDisp = getUsr082QrDisp();
        GsMessage gsMsg = new GsMessage(req);

        //ソートキー1、ソートキー2の重複チェックを行う
        if (usr082AdSortKey1__ == usr082AdSortKey2__) {
            String eprefix = "sortkey.";
            String dispName = gsMsg.getMessage("cmn.default.display.order.setting");
            String sortKey1 = gsMsg.getMessage("cmn.first.key");
            String sortKey2 = gsMsg.getMessage("cmn.second.key");
            ActionMessage msg = new ActionMessage(
                    "error.select.dup.list3", dispName, sortKey1, sortKey2);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        }

        if (qrDisp != GSConstUser.QR_CODE_DISP && qrDisp != GSConstUser.QR_CODE_NOT_DISP) {
            StringBuilder qrDispConf = new StringBuilder();
            qrDispConf.append(gsMsg.getMessage("cmn.cmn100.4"));
            qrDispConf.append(gsMsg.getMessage("cmn.display.settings"));
            message = "error.select3.required.text";
            ActionMessage errorMessage = new ActionMessage(message, qrDispConf.toString());
            errors.add(qrDispConf.toString(), errorMessage);
        }

        return errors;
    }
}