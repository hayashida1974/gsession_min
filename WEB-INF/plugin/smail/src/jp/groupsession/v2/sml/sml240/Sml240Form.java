package jp.groupsession.v2.sml.sml240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml020.Sml020Form;
import jp.groupsession.v2.sml.util.SmlValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] ショートメール アカウントマネージャー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml240Form extends Sml020Form {

    /** 検索キーワード */
    private String sml240keyword__ = null;
    /** グループ */
    private int sml240group__ = -1;
    /** ユーザ */
    private int sml240user__ = -1;
    /** ページ上段 */
    private int sml240pageTop__ = 0;
    /** ページ下段 */
    private int sml240pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean sml240pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String sml240svKeyword__ = null;
    /** グループ(検索条件保持) */
    private int sml240svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int sml240svUser__ = -1;

    /** ソートキー */
    private int sml240sortKey__ = GSConstSmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int sml240order__ = GSConstSmail.ORDER_ASC;

    /** 検索実行フラグ */
    private int sml240searchFlg__ = 0;

    /** 検索結果一覧 */
    private List<Sml240AccountModel> accountList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** ユーザコンボ */
    private List<LabelValueBean> userCombo__ = null;

    /** 選択されたアカウント */
    private String[] sml240selectAcount__;

    /** ポップアップ区分 */
    private int sml240popKbn__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(HttpServletRequest req)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

        //検索キーワード
        SmlValidateUtil.validateTextField(errors, sml240keyword__,
                                        "sml240keyword",
                                        getInterMessage(req, GSConstSmail.TEXT_SEARCH_KEYWORD),
                                        GSConstSmail.MAXLEN_SEARCH_KEYWORD, false);
        return errors;
    }

    /**
     * <p>accountList を取得します。
     * @return accountList
     */
    public List<Sml240AccountModel> getAccountList() {
        return accountList__;
    }
    /**
     * <p>accountList をセットします。
     * @param accountList accountList
     */
    public void setAccountList(List<Sml240AccountModel> accountList) {
        accountList__ = accountList;
    }
    /**
     * <p>pageCombo を取得します。
     * @return pageCombo
     */
    public List<LabelValueBean> getPageCombo() {
        return pageCombo__;
    }
    /**
     * <p>pageCombo をセットします。
     * @param pageCombo pageCombo
     */
    public void setPageCombo(List<LabelValueBean> pageCombo) {
        pageCombo__ = pageCombo;
    }
    /**
     * <p>sml240group を取得します。
     * @return sml240group
     */
    public int getSml240group() {
        return sml240group__;
    }
    /**
     * <p>sml240group をセットします。
     * @param sml240group sml240group
     */
    public void setSml240group(int sml240group) {
        sml240group__ = sml240group;
    }
    /**
     * <p>sml240keyword を取得します。
     * @return sml240keyword
     */
    public String getSml240keyword() {
        return sml240keyword__;
    }
    /**
     * <p>sml240keyword をセットします。
     * @param sml240keyword sml240keyword
     */
    public void setSml240keyword(String sml240keyword) {
        sml240keyword__ = sml240keyword;
    }
    /**
     * <p>sml240order を取得します。
     * @return sml240order
     */
    public int getSml240order() {
        return sml240order__;
    }
    /**
     * <p>sml240order をセットします。
     * @param sml240order sml240order
     */
    public void setSml240order(int sml240order) {
        sml240order__ = sml240order;
    }
    /**
     * <p>sml240pageBottom を取得します。
     * @return sml240pageBottom
     */
    public int getSml240pageBottom() {
        return sml240pageBottom__;
    }
    /**
     * <p>sml240pageBottom をセットします。
     * @param sml240pageBottom sml240pageBottom
     */
    public void setSml240pageBottom(int sml240pageBottom) {
        sml240pageBottom__ = sml240pageBottom;
    }
    /**
     * <p>sml240pageTop を取得します。
     * @return sml240pageTop
     */
    public int getSml240pageTop() {
        return sml240pageTop__;
    }
    /**
     * <p>sml240pageTop をセットします。
     * @param sml240pageTop sml240pageTop
     */
    public void setSml240pageTop(int sml240pageTop) {
        sml240pageTop__ = sml240pageTop;
    }
    /**
     * <p>sml240sortKey を取得します。
     * @return sml240sortKey
     */
    public int getSml240sortKey() {
        return sml240sortKey__;
    }
    /**
     * <p>sml240sortKey をセットします。
     * @param sml240sortKey sml240sortKey
     */
    public void setSml240sortKey(int sml240sortKey) {
        sml240sortKey__ = sml240sortKey;
    }
    /**
     * <p>sml240user を取得します。
     * @return sml240user
     */
    public int getSml240user() {
        return sml240user__;
    }
    /**
     * <p>sml240user をセットします。
     * @param sml240user sml240user
     */
    public void setSml240user(int sml240user) {
        sml240user__ = sml240user;
    }
    /**
     * <p>sml240searchFlg を取得します。
     * @return sml240searchFlg
     */
    public int getSml240searchFlg() {
        return sml240searchFlg__;
    }
    /**
     * <p>sml240searchFlg をセットします。
     * @param sml240searchFlg sml240searchFlg
     */
    public void setSml240searchFlg(int sml240searchFlg) {
        sml240searchFlg__ = sml240searchFlg;
    }
    /**
     * <p>sml240svGroup を取得します。
     * @return sml240svGroup
     */
    public int getSml240svGroup() {
        return sml240svGroup__;
    }
    /**
     * <p>sml240svGroup をセットします。
     * @param sml240svGroup sml240svGroup
     */
    public void setSml240svGroup(int sml240svGroup) {
        sml240svGroup__ = sml240svGroup;
    }
    /**
     * <p>sml240svKeyword を取得します。
     * @return sml240svKeyword
     */
    public String getSml240svKeyword() {
        return sml240svKeyword__;
    }
    /**
     * <p>sml240svKeyword をセットします。
     * @param sml240svKeyword sml240svKeyword
     */
    public void setSml240svKeyword(String sml240svKeyword) {
        sml240svKeyword__ = sml240svKeyword;
    }
    /**
     * <p>sml240svUser を取得します。
     * @return sml240svUser
     */
    public int getSml240svUser() {
        return sml240svUser__;
    }
    /**
     * <p>sml240svUser をセットします。
     * @param sml240svUser sml240svUser
     */
    public void setSml240svUser(int sml240svUser) {
        sml240svUser__ = sml240svUser;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>userCombo を取得します。
     * @return userCombo
     */
    public List<LabelValueBean> getUserCombo() {
        return userCombo__;
    }
    /**
     * <p>userCombo をセットします。
     * @param userCombo userCombo
     */
    public void setUserCombo(List<LabelValueBean> userCombo) {
        userCombo__ = userCombo;
    }

    /**
     * <p>sml240pageDspFlg を取得します。
     * @return sml240pageDspFlg
     */
    public boolean isSml240pageDspFlg() {
        return sml240pageDspFlg__;
    }

    /**
     * <p>sml240pageDspFlg をセットします。
     * @param sml240pageDspFlg sml240pageDspFlg
     */
    public void setSml240pageDspFlg(boolean sml240pageDspFlg) {
        sml240pageDspFlg__ = sml240pageDspFlg;
    }

    /**
     * <p>sml240selectAcount を取得します。
     * @return sml240selectAcount
     */
    public String[] getSml240selectAcount() {
        return sml240selectAcount__;
    }

    /**
     * <p>sml240selectAcount をセットします。
     * @param sml240selectAcount sml240selectAcount
     */
    public void setSml240selectAcount(String[] sml240selectAcount) {
        sml240selectAcount__ = sml240selectAcount;
    }


    /**
     * <br>[機  能] 削除ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateAccount(Connection con, HttpServletRequest req)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        //選択されたアカウント
        if (sml240selectAcount__ == null || sml240selectAcount__.length < 1) {
            msg = new ActionMessage("error.select.required.text", gsMsg.getMessage(req, "wml.102"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
        }

        return errors;
    }

    /**
     * <p>sml240popKbn を取得します。
     * @return sml240popKbn
     */
    public int getSml240popKbn() {
        return sml240popKbn__;
    }

    /**
     * <p>sml240popKbn をセットします。
     * @param sml240popKbn sml240popKbn
     */
    public void setSml240popKbn(int sml240popKbn) {
        sml240popKbn__ = sml240popKbn;
    }
}
