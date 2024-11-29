package jp.groupsession.v2.man.man220;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 グループ・ユーザ並び順設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man220Form extends AbstractGsForm {

    /** グループ 階層区分 選択 */
    public static final int GRPSORTKBN_SELECT = 1;

    /** ユーザ ソートキー */
    public static final int[] SORTKEY_USER =
        {GSConst.USERCMB_SKEY_NAME, GSConst.USERCMB_SKEY_SNO,
        GSConst.USERCMB_SKEY_POSITION, GSConst.USERCMB_SKEY_BDATE,
        GSConst.USERCMB_SKEY_SORTKEY1, GSConst.USERCMB_SKEY_SORTKEY2};
    /** グループ ソートキー */
    public static final int[] SORTKEY_GROUP =
        {GSConst.GROUPCMB_SKEY_GRPID, GSConst.GROUPCMB_SKEY_NAME};

    /** ユーザ ソートキー ソートキーと名称のMapping */
    public static Map<String, String> sortKeyUserMap = null;
    /** グループ ソートキー ソートキーと名称のMapping */
    public static Map<String, String> sortKeyGroupMap = null;

    /** ユーザ ソートキー1 */
    private int man220UserSortKey1__ = GSConst.USERCMB_SKEY_NOSET;
    /** ユーザ ソートキー2 */
    private int man220UserSortKey2__ = GSConst.USERCMB_SKEY_NOSET;
    /** ユーザ ソートキー1 オーダー */
    private int man220UserSortOrder1__ = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
    /** ユーザ ソートキー2 オーダー */
    private int man220UserSortOrder2__ = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;

    /** グループ 階層区分 */
    private int man220GroupSortKbn__ = 0;
    /** グループ ソートキー1 */
    private int man220GroupSortKey1__ = GSConst.GROUPCMB_SKEY_NOSET;
    /** グループ ソートキー2 */
    private int man220GroupSortKey2__ = GSConst.GROUPCMB_SKEY_NOSET;
    /** グループ ソートキー1 オーダー */
    private int man220GroupSortOrder1__ = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
    /** グループ ソートキー2 オーダー */
    private int man220GroupSortOrder2__ = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;

    /** 初期表示フラグ */
    private int man220initFlg__ = 0;

    /** ユーザ ソートキー ラベル */
    private List<LabelValueBean> userSortKeyLabel__ = null;
    /** グループ ソートキー ラベル */
    private List<LabelValueBean> groupSortKeyLabel__ = null;

    /**
     * <p>groupSortKeyLabel を取得します。
     * @return groupSortKeyLabel
     */
    public List<LabelValueBean> getGroupSortKeyLabel() {
        return groupSortKeyLabel__;
    }

    /**
     * <p>groupSortKeyLabel をセットします。
     * @param groupSortKeyLabel groupSortKeyLabel
     */
    public void setGroupSortKeyLabel(List<LabelValueBean> groupSortKeyLabel) {
        groupSortKeyLabel__ = groupSortKeyLabel;
    }

    /**
     * <p>man220GroupSortKbn を取得します。
     * @return man220GroupSortKbn
     */
    public int getMan220GroupSortKbn() {
        return man220GroupSortKbn__;
    }

    /**
     * <p>man220GroupSortKbn をセットします。
     * @param man220GroupSortKbn man220GroupSortKbn
     */
    public void setMan220GroupSortKbn(int man220GroupSortKbn) {
        man220GroupSortKbn__ = man220GroupSortKbn;
    }

    /**
     * <p>man220GroupSortKey1 を取得します。
     * @return man220GroupSortKey1
     */
    public int getMan220GroupSortKey1() {
        return man220GroupSortKey1__;
    }

    /**
     * <p>man220GroupSortKey1 をセットします。
     * @param man220GroupSortKey1 man220GroupSortKey1
     */
    public void setMan220GroupSortKey1(int man220GroupSortKey1) {
        man220GroupSortKey1__ = man220GroupSortKey1;
    }

    /**
     * <p>man220GroupSortKey2 を取得します。
     * @return man220GroupSortKey2
     */
    public int getMan220GroupSortKey2() {
        return man220GroupSortKey2__;
    }

    /**
     * <p>man220GroupSortKey2 をセットします。
     * @param man220GroupSortKey2 man220GroupSortKey2
     */
    public void setMan220GroupSortKey2(int man220GroupSortKey2) {
        man220GroupSortKey2__ = man220GroupSortKey2;
    }

    /**
     * <p>man220GroupSortOrder1 を取得します。
     * @return man220GroupSortOrder1
     */
    public int getMan220GroupSortOrder1() {
        return man220GroupSortOrder1__;
    }

    /**
     * <p>man220GroupSortOrder1 をセットします。
     * @param man220GroupSortOrder1 man220GroupSortOrder1
     */
    public void setMan220GroupSortOrder1(int man220GroupSortOrder1) {
        man220GroupSortOrder1__ = man220GroupSortOrder1;
    }

    /**
     * <p>man220GroupSortOrder2 を取得します。
     * @return man220GroupSortOrder2
     */
    public int getMan220GroupSortOrder2() {
        return man220GroupSortOrder2__;
    }

    /**
     * <p>man220GroupSortOrder2 をセットします。
     * @param man220GroupSortOrder2 man220GroupSortOrder2
     */
    public void setMan220GroupSortOrder2(int man220GroupSortOrder2) {
        man220GroupSortOrder2__ = man220GroupSortOrder2;
    }

    /**
     * <p>man220UserSortKey1 を取得します。
     * @return man220UserSortKey1
     */
    public int getMan220UserSortKey1() {
        return man220UserSortKey1__;
    }

    /**
     * <p>man220UserSortKey1 をセットします。
     * @param man220UserSortKey1 man220UserSortKey1
     */
    public void setMan220UserSortKey1(int man220UserSortKey1) {
        man220UserSortKey1__ = man220UserSortKey1;
    }

    /**
     * <p>man220UserSortKey2 を取得します。
     * @return man220UserSortKey2
     */
    public int getMan220UserSortKey2() {
        return man220UserSortKey2__;
    }

    /**
     * <p>man220UserSortKey2 をセットします。
     * @param man220UserSortKey2 man220UserSortKey2
     */
    public void setMan220UserSortKey2(int man220UserSortKey2) {
        man220UserSortKey2__ = man220UserSortKey2;
    }

    /**
     * <p>man220UserSortOrder1 を取得します。
     * @return man220UserSortOrder1
     */
    public int getMan220UserSortOrder1() {
        return man220UserSortOrder1__;
    }

    /**
     * <p>man220UserSortOrder1 をセットします。
     * @param man220UserSortOrder1 man220UserSortOrder1
     */
    public void setMan220UserSortOrder1(int man220UserSortOrder1) {
        man220UserSortOrder1__ = man220UserSortOrder1;
    }

    /**
     * <p>man220UserSortOrder2 を取得します。
     * @return man220UserSortOrder2
     */
    public int getMan220UserSortOrder2() {
        return man220UserSortOrder2__;
    }

    /**
     * <p>man220UserSortOrder2 をセットします。
     * @param man220UserSortOrder2 man220UserSortOrder2
     */
    public void setMan220UserSortOrder2(int man220UserSortOrder2) {
        man220UserSortOrder2__ = man220UserSortOrder2;
    }

    /**
     * <p>userSortKeyLabel を取得します。
     * @return userSortKeyLabel
     */
    public List<LabelValueBean> getUserSortKeyLabel() {
        return userSortKeyLabel__;
    }

    /**
     * <p>userSortKeyLabel をセットします。
     * @param userSortKeyLabel userSortKeyLabel
     */
    public void setUserSortKeyLabel(List<LabelValueBean> userSortKeyLabel) {
        userSortKeyLabel__ = userSortKeyLabel;
    }

    /**
     * <p>man220initFlg を取得します。
     * @return man220initFlg
     */
    public int getMan220initFlg() {
        return man220initFlg__;
    }

    /**
     * <p>man220initFlg をセットします。
     * @param man220initFlg man220initFlg
     */
    public void setMan220initFlg(int man220initFlg) {
        man220initFlg__ = man220initFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //ユーザコンボ表示順:第1キー
        if (Arrays.binarySearch(SORTKEY_USER, man220UserSortKey1__) < 0) {
            msg = new ActionMessage("error.select3.required.text",
                    gsMsg.getMessage("main.src.man220.7"));
            errors.add("man220UserSortKey1" + "error.select3.required.text", msg);
        }

        //ユーザコンボ表示順：第1キー 昇順・降順
        if (man220UserSortOrder1__ != GSConst.ORDER_KEY_ASC
        && man220UserSortOrder1__ != GSConst.ORDER_KEY_DESC) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage("main.src.man220.8"));
            errors.add("man220UserSortOrder1" + "error.select3.required.text", msg);
        }

        //ユーザコンボ表示順：第2キー 昇順・降順
        if (Arrays.binarySearch(SORTKEY_USER, man220UserSortKey2__) >= 0
        && man220UserSortOrder2__ != GSConst.ORDER_KEY_ASC
        && man220UserSortOrder2__ != GSConst.ORDER_KEY_DESC) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage("main.src.man220.9"));
            errors.add("man220UserSortOrder2" + "error.select3.required.text", msg);
        }

        //ユーザコンボ表示順:第1キー と ユーザコンボ表示順：第2キー が重複している場合はエラー
        if (man220UserSortKey1__ == man220UserSortKey2__) {
            msg = new ActionMessage(
                    "error.select.dup.list", gsMsg.getMessage("main.src.man220.10"));
            errors.add("man220UserSortOrder2" + "error.select.dup.list", msg);
        }

        //グループコンボ表示順:第1キー
        if (Arrays.binarySearch(SORTKEY_GROUP, man220GroupSortKey1__) < 0) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage("main.src.man220.11"));
            errors.add("man220GroupSortKey1" + "error.select3.required.text", msg);
        }

        //グループコンボ表示順：第1キー 昇順・降順
        if (man220GroupSortOrder1__ != GSConst.ORDER_KEY_ASC
        && man220GroupSortOrder1__ != GSConst.ORDER_KEY_DESC) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage("main.src.man220.12"));
            errors.add("man220GroupSortOrder1" + "error.select3.required.text", msg);
        }

        //グループコンボ表示順：第2キー 昇順・降順
        if (Arrays.binarySearch(SORTKEY_GROUP, man220GroupSortKey2__) >= 0
        && man220GroupSortOrder2__ != GSConst.ORDER_KEY_ASC
        && man220GroupSortOrder2__ != GSConst.ORDER_KEY_DESC) {
            msg = new ActionMessage(
                    "error.select3.required.text", gsMsg.getMessage("main.src.man220.13"));
            errors.add("man220GroupSortOrder2" + "error.select3.required.text", msg);
        }

        //グループコンボ表示順:第1キー と グループコンボ表示順：第2キー が重複している場合はエラー
        if (man220GroupSortKey1__ == man220GroupSortKey2__) {
            msg = new ActionMessage(
                    "error.select.dup.list", gsMsg.getMessage("main.src.man220.14"));
            errors.add("man220GroupSortOrder2" + "error.select.dup.list", msg);
        }

        return errors;
    }
}
