package jp.groupsession.v2.sml.sml090;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール詳細検索画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml090ParamModel extends Sml010ParamModel {
    /** 検索実行 switch */
    private int searchFlg__ = GSConstSmail.SEARCH_EXECUTE_FALSE;
    /** 処理モード（sml010ProcMode）のセーブ値 */
    private String sml090ProcModeSave__ = GSConstSmail.TAB_DSP_MODE_JUSIN;

    //ページコンボ
    /** ページコンボ上 */
    private int sml090page1__ = 0;
    /** ページコンボ下 */
    private int sml090page2__ = 0;

    /** 宛先SIDリスト */
    private String[] cmn120userSid__;

    /** 宛先情報 */
    private SmailModel sml090AtesakiModel__ = null;

    /** マーク */
    private String sml090MailMark__ = String.valueOf(GSConstSmail.MARK_KBN_ALL);
    /** キーワード検索区分 */
    private String sml090KeyWordkbn__ = String.valueOf(GSConstSmail.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] sml090SearchTarget__ = null;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int sml090searchUse__ = GSConst.PLUGIN_USE;

    /** ページラベル */
    private ArrayList<LabelValueBean> smlPageLabel__;

    /** 削除チェック */
    private String[] sml090DelSid__;
    /** 削除チェック (選択済) */
    private ArrayList<String> sml090SelectedDelSid__;

/*-- SVパラメータ start ----------------------------------------------------------*/
    /** 宛先SIDリスト */
    private String[] cmn120SvuserSid__;

    /** グループ */
    private String sml090SvSltGroup__ = null;
    /** ユーザ */
    private String sml090SvSltUser__ = null;

    /** 宛先 */
    private String[] sml090SvAtesaki__ = null;

    /** メール種別 */
    private String sml090SvMailSyubetsu__ = null;
    /** マーク */
    private String sml090SvMailMark__ = String.valueOf(GSConstSmail.MARK_KBN_ALL);
    /** キーワード */
    private String sml090SvKeyWord__ = null;
    /** キーワード検索区分 */
    private String sml090SvKeyWordkbn__ = String.valueOf(GSConstSmail.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] sml090SvSearchTarget__ = SmlCommonBiz.getDefultSearchTarget();

    //ソート順
    /** オーダーキー1 */
    private String sml090SvSearchOrderKey1__ = String.valueOf(GSConstSmail.MSG_SORT_KEY_DATE);
    /** ソートキー1 */
    private String sml090SvSearchSortKey1__ = String.valueOf(GSConstSmail.ORDER_KEY_ASC);
    /** オーダーキー2 */
    private String sml090SvSearchOrderKey2__ = String.valueOf(GSConstSmail.MSG_SORT_KEY_MARK);
    /** ソートキー2 */
    private String sml090SvSearchSortKey2__ = String.valueOf(GSConstSmail.ORDER_KEY_ASC);
/*-- SVパラメータ end ----------------------------------------------------------*/

    /** WEB検索ワード */
    private String sml090WebSearchWord__ = "";

    //検索結果
    /** 検索結果リスト */
    private List<SmailModel> sml090SearchResultList__ = null;

    /**
     * <p>searchFlg を取得します。
     * @return searchFlg
     */
    public int getSearchFlg() {
        return searchFlg__;
    }

    /**
     * <p>searchFlg をセットします。
     * @param searchFlg searchFlg
     */
    public void setSearchFlg(int searchFlg) {
        searchFlg__ = searchFlg;
    }

    /**
     * <p>sml090ProcModeSave を取得します。
     * @return sml090ProcModeSave
     */
    public String getSml090ProcModeSave() {
        return sml090ProcModeSave__;
    }

    /**
     * <p>sml090ProcModeSave をセットします。
     * @param sml090ProcModeSave sml090ProcModeSave
     */
    public void setSml090ProcModeSave(String sml090ProcModeSave) {
        sml090ProcModeSave__ = sml090ProcModeSave;
    }

    /**
     * <p>sml090page1 を取得します。
     * @return sml090page1
     */
    public int getSml090page1() {
        return sml090page1__;
    }

    /**
     * <p>sml090page1 をセットします。
     * @param sml090page1 sml090page1
     */
    public void setSml090page1(int sml090page1) {
        sml090page1__ = sml090page1;
    }

    /**
     * <p>sml090page2 を取得します。
     * @return sml090page2
     */
    public int getSml090page2() {
        return sml090page2__;
    }

    /**
     * <p>sml090page2 をセットします。
     * @param sml090page2 sml090page2
     */
    public void setSml090page2(int sml090page2) {
        sml090page2__ = sml090page2;
    }

    /**
     * <p>cmn120userSid を取得します。
     * @return cmn120userSid
     */
    public String[] getCmn120userSid() {
        return cmn120userSid__;
    }

    /**
     * <p>cmn120userSid をセットします。
     * @param cmn120userSid cmn120userSid
     */
    public void setCmn120userSid(String[] cmn120userSid) {
        cmn120userSid__ = cmn120userSid;
    }

    /**
     * <p>sml090AtesakiModel を取得します。
     * @return sml090AtesakiModel
     */
    public SmailModel getSml090AtesakiModel() {
        return sml090AtesakiModel__;
    }

    /**
     * <p>sml090AtesakiModel をセットします。
     * @param sml090AtesakiModel sml090AtesakiModel
     */
    public void setSml090AtesakiModel(SmailModel sml090AtesakiModel) {
        sml090AtesakiModel__ = sml090AtesakiModel;
    }

    /**
     * <p>sml090MailMark を取得します。
     * @return sml090MailMark
     */
    public String getSml090MailMark() {
        return sml090MailMark__;
    }

    /**
     * <p>sml090MailMark をセットします。
     * @param sml090MailMark sml090MailMark
     */
    public void setSml090MailMark(String sml090MailMark) {
        sml090MailMark__ = sml090MailMark;
    }

    /**
     * <p>sml090KeyWordkbn を取得します。
     * @return sml090KeyWordkbn
     */
    public String getSml090KeyWordkbn() {
        return sml090KeyWordkbn__;
    }

    /**
     * <p>sml090KeyWordkbn をセットします。
     * @param sml090KeyWordkbn sml090KeyWordkbn
     */
    public void setSml090KeyWordkbn(String sml090KeyWordkbn) {
        sml090KeyWordkbn__ = sml090KeyWordkbn;
    }

    /**
     * <p>sml090SearchTarget を取得します。
     * @return sml090SearchTarget
     */
    public String[] getSml090SearchTarget() {
        return sml090SearchTarget__;
    }

    /**
     * <p>sml090SearchTarget をセットします。
     * @param sml090SearchTarget sml090SearchTarget
     */
    public void setSml090SearchTarget(String[] sml090SearchTarget) {
        sml090SearchTarget__ = sml090SearchTarget;
    }

    /**
     * <p>sml090searchUse を取得します。
     * @return sml090searchUse
     */
    public int getSml090searchUse() {
        return sml090searchUse__;
    }

    /**
     * <p>sml090searchUse をセットします。
     * @param sml090searchUse sml090searchUse
     */
    public void setSml090searchUse(int sml090searchUse) {
        sml090searchUse__ = sml090searchUse;
    }

    /**
     * <p>smlPageLabel を取得します。
     * @return smlPageLabel
     */
    public ArrayList<LabelValueBean> getSmlPageLabel() {
        return smlPageLabel__;
    }

    /**
     * <p>smlPageLabel をセットします。
     * @param smlPageLabel smlPageLabel
     */
    public void setSmlPageLabel(ArrayList<LabelValueBean> smlPageLabel) {
        smlPageLabel__ = smlPageLabel;
    }

    /**
     * <p>sml090DelSid を取得します。
     * @return sml090DelSid
     */
    public String[] getSml090DelSid() {
        return sml090DelSid__;
    }

    /**
     * <p>sml090DelSid をセットします。
     * @param sml090DelSid sml090DelSid
     */
    public void setSml090DelSid(String[] sml090DelSid) {
        sml090DelSid__ = sml090DelSid;
    }

    /**
     * <p>sml090SelectedDelSid を取得します。
     * @return sml090SelectedDelSid
     */
    public ArrayList<String> getSml090SelectedDelSid() {
        return sml090SelectedDelSid__;
    }

    /**
     * <p>sml090SelectedDelSid をセットします。
     * @param sml090SelectedDelSid sml090SelectedDelSid
     */
    public void setSml090SelectedDelSid(ArrayList<String> sml090SelectedDelSid) {
        sml090SelectedDelSid__ = sml090SelectedDelSid;
    }

    /**
     * <p>cmn120SvuserSid を取得します。
     * @return cmn120SvuserSid
     */
    public String[] getCmn120SvuserSid() {
        return cmn120SvuserSid__;
    }

    /**
     * <p>cmn120SvuserSid をセットします。
     * @param cmn120SvuserSid cmn120SvuserSid
     */
    public void setCmn120SvuserSid(String[] cmn120SvuserSid) {
        cmn120SvuserSid__ = cmn120SvuserSid;
    }

    /**
     * <p>sml090SvSltGroup を取得します。
     * @return sml090SvSltGroup
     */
    public String getSml090SvSltGroup() {
        return sml090SvSltGroup__;
    }

    /**
     * <p>sml090SvSltGroup をセットします。
     * @param sml090SvSltGroup sml090SvSltGroup
     */
    public void setSml090SvSltGroup(String sml090SvSltGroup) {
        sml090SvSltGroup__ = sml090SvSltGroup;
    }

    /**
     * <p>sml090SvSltUser を取得します。
     * @return sml090SvSltUser
     */
    public String getSml090SvSltUser() {
        return sml090SvSltUser__;
    }

    /**
     * <p>sml090SvSltUser をセットします。
     * @param sml090SvSltUser sml090SvSltUser
     */
    public void setSml090SvSltUser(String sml090SvSltUser) {
        sml090SvSltUser__ = sml090SvSltUser;
    }

    /**
     * <p>sml090SvAtesaki を取得します。
     * @return sml090SvAtesaki
     */
    public String[] getSml090SvAtesaki() {
        return sml090SvAtesaki__;
    }

    /**
     * <p>sml090SvAtesaki をセットします。
     * @param sml090SvAtesaki sml090SvAtesaki
     */
    public void setSml090SvAtesaki(String[] sml090SvAtesaki) {
        sml090SvAtesaki__ = sml090SvAtesaki;
    }

    /**
     * <p>sml090SvMailSyubetsu を取得します。
     * @return sml090SvMailSyubetsu
     */
    public String getSml090SvMailSyubetsu() {
        return sml090SvMailSyubetsu__;
    }

    /**
     * <p>sml090SvMailSyubetsu をセットします。
     * @param sml090SvMailSyubetsu sml090SvMailSyubetsu
     */
    public void setSml090SvMailSyubetsu(String sml090SvMailSyubetsu) {
        sml090SvMailSyubetsu__ = sml090SvMailSyubetsu;
    }

    /**
     * <p>sml090SvMailMark を取得します。
     * @return sml090SvMailMark
     */
    public String getSml090SvMailMark() {
        return sml090SvMailMark__;
    }

    /**
     * <p>sml090SvMailMark をセットします。
     * @param sml090SvMailMark sml090SvMailMark
     */
    public void setSml090SvMailMark(String sml090SvMailMark) {
        sml090SvMailMark__ = sml090SvMailMark;
    }

    /**
     * <p>sml090SvKeyWord を取得します。
     * @return sml090SvKeyWord
     */
    public String getSml090SvKeyWord() {
        return sml090SvKeyWord__;
    }

    /**
     * <p>sml090SvKeyWord をセットします。
     * @param sml090SvKeyWord sml090SvKeyWord
     */
    public void setSml090SvKeyWord(String sml090SvKeyWord) {
        sml090SvKeyWord__ = sml090SvKeyWord;
    }

    /**
     * <p>sml090SvKeyWordkbn を取得します。
     * @return sml090SvKeyWordkbn
     */
    public String getSml090SvKeyWordkbn() {
        return sml090SvKeyWordkbn__;
    }

    /**
     * <p>sml090SvKeyWordkbn をセットします。
     * @param sml090SvKeyWordkbn sml090SvKeyWordkbn
     */
    public void setSml090SvKeyWordkbn(String sml090SvKeyWordkbn) {
        sml090SvKeyWordkbn__ = sml090SvKeyWordkbn;
    }

    /**
     * <p>sml090SvSearchTarget を取得します。
     * @return sml090SvSearchTarget
     */
    public String[] getSml090SvSearchTarget() {
        return sml090SvSearchTarget__;
    }

    /**
     * <p>sml090SvSearchTarget をセットします。
     * @param sml090SvSearchTarget sml090SvSearchTarget
     */
    public void setSml090SvSearchTarget(String[] sml090SvSearchTarget) {
        sml090SvSearchTarget__ = sml090SvSearchTarget;
    }

    /**
     * <p>sml090SvSearchOrderKey1 を取得します。
     * @return sml090SvSearchOrderKey1
     */
    public String getSml090SvSearchOrderKey1() {
        return sml090SvSearchOrderKey1__;
    }

    /**
     * <p>sml090SvSearchOrderKey1 をセットします。
     * @param sml090SvSearchOrderKey1 sml090SvSearchOrderKey1
     */
    public void setSml090SvSearchOrderKey1(String sml090SvSearchOrderKey1) {
        sml090SvSearchOrderKey1__ = sml090SvSearchOrderKey1;
    }

    /**
     * <p>sml090SvSearchSortKey1 を取得します。
     * @return sml090SvSearchSortKey1
     */
    public String getSml090SvSearchSortKey1() {
        return sml090SvSearchSortKey1__;
    }

    /**
     * <p>sml090SvSearchSortKey1 をセットします。
     * @param sml090SvSearchSortKey1 sml090SvSearchSortKey1
     */
    public void setSml090SvSearchSortKey1(String sml090SvSearchSortKey1) {
        sml090SvSearchSortKey1__ = sml090SvSearchSortKey1;
    }

    /**
     * <p>sml090SvSearchOrderKey2 を取得します。
     * @return sml090SvSearchOrderKey2
     */
    public String getSml090SvSearchOrderKey2() {
        return sml090SvSearchOrderKey2__;
    }

    /**
     * <p>sml090SvSearchOrderKey2 をセットします。
     * @param sml090SvSearchOrderKey2 sml090SvSearchOrderKey2
     */
    public void setSml090SvSearchOrderKey2(String sml090SvSearchOrderKey2) {
        sml090SvSearchOrderKey2__ = sml090SvSearchOrderKey2;
    }

    /**
     * <p>sml090SvSearchSortKey2 を取得します。
     * @return sml090SvSearchSortKey2
     */
    public String getSml090SvSearchSortKey2() {
        return sml090SvSearchSortKey2__;
    }

    /**
     * <p>sml090SvSearchSortKey2 をセットします。
     * @param sml090SvSearchSortKey2 sml090SvSearchSortKey2
     */
    public void setSml090SvSearchSortKey2(String sml090SvSearchSortKey2) {
        sml090SvSearchSortKey2__ = sml090SvSearchSortKey2;
    }

    /**
     * <p>sml090WebSearchWord を取得します。
     * @return sml090WebSearchWord
     */
    public String getSml090WebSearchWord() {
        return sml090WebSearchWord__;
    }

    /**
     * <p>sml090WebSearchWord をセットします。
     * @param sml090WebSearchWord sml090WebSearchWord
     */
    public void setSml090WebSearchWord(String sml090WebSearchWord) {
        sml090WebSearchWord__ = sml090WebSearchWord;
    }

    /**
     * <p>sml090SearchResultList を取得します。
     * @return sml090SearchResultList
     */
    public List<SmailModel> getSml090SearchResultList() {
        return sml090SearchResultList__;
    }

    /**
     * <p>sml090SearchResultList をセットします。
     * @param sml090SearchResultList sml090SearchResultList
     */
    public void setSml090SearchResultList(List<SmailModel> sml090SearchResultList) {
        sml090SearchResultList__ = sml090SearchResultList;
    }
}