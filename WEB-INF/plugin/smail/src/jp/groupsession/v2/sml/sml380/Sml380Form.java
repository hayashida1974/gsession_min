package jp.groupsession.v2.sml.sml380;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.dao.SmlBanDestConfDao;
import jp.groupsession.v2.sml.model.SmlBanDestConfModel;
import jp.groupsession.v2.sml.sml100.Sml100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 送信先制限設定 一覧画面　フォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml380Form extends Sml100Form {
    /** 送信先リスト名 MAX文字数 */
    public static final int MAXLEN_DESTLIST_NAME = 50;

    /** 検索キーワード */
    private String sml380keyword__ = null;
    /** ページ上段 */
    private int sml380pageTop__ = 0;
    /** ページ下段 */
    private int sml380pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean sml380pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String sml380svKeyword__ = null;

    /** ソートキー */
    private int sml380sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int sml380order__ = GSConstWebmail.ORDER_ASC;

    /** 検索実行フラグ */
    private int sml380searchFlg__ = 0;

    /** 検索結果一覧 */
    private List<Sml380DataModel> banList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** 送信先リスト(編集対象) */
    private int sml380EditBan__ = 0;
    /** 選択された送信先リスト */
    private String[] sml380selectBanList__;

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("backScreen", getBackScreen());

        msgForm.addHiddenParam("sml380keyword", sml380keyword__);
        msgForm.addHiddenParam("sml380pageTop", sml380pageTop__);
        msgForm.addHiddenParam("sml380pageBottom", sml380pageBottom__);
        msgForm.addHiddenParam("sml380svKeyword", sml380svKeyword__);
        msgForm.addHiddenParam("sml380sortKey", sml380sortKey__);
        msgForm.addHiddenParam("sml380order", sml380order__);
        msgForm.addHiddenParam("sml380searchFlg", sml380searchFlg__);
        msgForm.addHiddenParam("sml380selectBanList", sml380selectBanList__);
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();

        //検索キーワード
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateSmail.validateTextBoxInput(errors, sml380keyword__,
                                        "sml380keyword",
                                        gsMsg.getMessage(GSConstWebmail.TEXT_SEARCH_KEYWORD),
                                        50, false);

        //検索結果件数チェック
        if (errors.isEmpty()) {

            Sml380ParamModel paramMdl = new Sml380ParamModel();
            paramMdl.setParam(this);
            paramMdl.setSml380svKeyword(getSml380keyword());
            paramMdl.setSml380searchFlg(0);

            Sml380Biz biz = new Sml380Biz();
            if (biz.getRecordCount(con, paramMdl, reqMdl) <= 0) {
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                                    gsMsg.getMessage("sml.188"));
                StrutsUtil.addMessage(errors, msg, "sml380keyword");
            }
        }

        return errors;
    }
    /**
     * <br>[機  能] 削除ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateDelete(RequestModel reqMdl, Connection con)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //選択された送信先リスト
        if (sml380selectBanList__ == null || sml380selectBanList__.length < 1) {
            msg = new ActionMessage("error.select.required.text", gsMsg.getMessage("sml.188"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
        } else {
            Sml380Biz biz = new Sml380Biz();
            if (StringUtil.isNullZeroString(biz.getSendListName(con, sml380selectBanList__))) {

                msg = new ActionMessage("error.none.edit.data", gsMsg.getMessage("sml.188"),
                        gsMsg.getMessage("cmn.delete"));
                StrutsUtil.addMessage(errors, msg, "error.none.edit.data");
            }
        }

        return errors;
    }
    /**
     * <br>[機  能] 初期化時のパラメータチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateEditCheck(Connection con, RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        _validateSid(errors, con, reqMdl);

        return errors;
    }
    /**
     *
     * <br>[機  能] 編集時のSIDの有効確認
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param con コネクション
     * @param reqMdl リクエスト
     * @throws SQLException SQL実行例外
     */
    protected void _validateSid(ActionErrors errors, Connection con,
            RequestModel reqMdl) throws SQLException {
        if (getSml380EditBan() > 0) {
            SmlBanDestConfDao sbdDao = new SmlBanDestConfDao(con);
            SmlBanDestConfModel mdl = sbdDao.select(getSml380EditBan());
            if (mdl == null) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                ActionMessage msg = new ActionMessage("error.none.edit.data",
                        gsMsg.getMessage("sml.188"),
                        gsMsg.getMessage("cmn.edit"));
                StrutsUtil.addMessage(errors, msg, "error.none.edit.data");
            }
        }
    }

    /**
     * <p>sml380EditBan を取得します。
     * @return sml380EditBan
     */
    public int getSml380EditBan() {
        return sml380EditBan__;
    }

    /**
     * <p>sml380EditBan をセットします。
     * @param sml380EditBan sml380EditBan
     */
    public void setSml380EditBan(int sml380EditBan) {
        sml380EditBan__ = sml380EditBan;
    }

    /**
     * <p>banList を取得します。
     * @return banList
     */
    public List<Sml380DataModel> getBanList() {
        return banList__;
    }

    /**
     * <p>banList をセットします。
     * @param banList banList
     */
    public void setBanList(List<Sml380DataModel> banList) {
        banList__ = banList;
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
     * <p>sml380keyword を取得します。
     * @return sml380keyword
     */
    public String getSml380keyword() {
        return sml380keyword__;
    }
    /**
     * <p>sml380keyword をセットします。
     * @param sml380keyword sml380keyword
     */
    public void setSml380keyword(String sml380keyword) {
        sml380keyword__ = sml380keyword;
    }
    /**
     * <p>sml380order を取得します。
     * @return sml380order
     */
    public int getSml380order() {
        return sml380order__;
    }
    /**
     * <p>sml380order をセットします。
     * @param sml380order sml380order
     */
    public void setSml380order(int sml380order) {
        sml380order__ = sml380order;
    }
    /**
     * <p>sml380pageBottom を取得します。
     * @return sml380pageBottom
     */
    public int getSml380pageBottom() {
        return sml380pageBottom__;
    }
    /**
     * <p>sml380pageBottom をセットします。
     * @param sml380pageBottom sml380pageBottom
     */
    public void setSml380pageBottom(int sml380pageBottom) {
        sml380pageBottom__ = sml380pageBottom;
    }
    /**
     * <p>sml380pageTop を取得します。
     * @return sml380pageTop
     */
    public int getSml380pageTop() {
        return sml380pageTop__;
    }
    /**
     * <p>sml380pageTop をセットします。
     * @param sml380pageTop sml380pageTop
     */
    public void setSml380pageTop(int sml380pageTop) {
        sml380pageTop__ = sml380pageTop;
    }
    /**
     * <p>sml380sortKey を取得します。
     * @return sml380sortKey
     */
    public int getSml380sortKey() {
        return sml380sortKey__;
    }
    /**
     * <p>sml380sortKey をセットします。
     * @param sml380sortKey sml380sortKey
     */
    public void setSml380sortKey(int sml380sortKey) {
        sml380sortKey__ = sml380sortKey;
    }
    /**
     * <p>sml380searchFlg を取得します。
     * @return sml380searchFlg
     */
    public int getSml380searchFlg() {
        return sml380searchFlg__;
    }
    /**
     * <p>sml380searchFlg をセットします。
     * @param sml380searchFlg sml380searchFlg
     */
    public void setSml380searchFlg(int sml380searchFlg) {
        sml380searchFlg__ = sml380searchFlg;
    }
    /**
     * <p>sml380svKeyword を取得します。
     * @return sml380svKeyword
     */
    public String getSml380svKeyword() {
        return sml380svKeyword__;
    }
    /**
     * <p>sml380svKeyword をセットします。
     * @param sml380svKeyword sml380svKeyword
     */
    public void setSml380svKeyword(String sml380svKeyword) {
        sml380svKeyword__ = sml380svKeyword;
    }

    /**
     * <p>sml380pageDspFlg を取得します。
     * @return sml380pageDspFlg
     */
    public boolean isSml380pageDspFlg() {
        return sml380pageDspFlg__;
    }

    /**
     * <p>sml380pageDspFlg をセットします。
     * @param sml380pageDspFlg sml380pageDspFlg
     */
    public void setSml380pageDspFlg(boolean sml380pageDspFlg) {
        sml380pageDspFlg__ = sml380pageDspFlg;
    }

    /**
     * <p>sml380selectBanList を取得します。
     * @return sml380selectBanList
     */
    public String[] getSml380selectBanList() {
        return sml380selectBanList__;
    }

    /**
     * <p>sml380selectBanList をセットします。
     * @param sml380selectBanList sml380selectBanList
     */
    public void setSml380selectBanList(String[] sml380selectBanList) {
        sml380selectBanList__ = sml380selectBanList;
    }


}
