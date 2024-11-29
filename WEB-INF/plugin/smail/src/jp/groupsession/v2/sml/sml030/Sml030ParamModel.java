package jp.groupsession.v2.sml.sml030;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;

/**
 * <br>[機  能] ショートメール 内容確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml030ParamModel extends Sml010ParamModel {
    /** 選択中行番号 */
    private int sml030SelectedRowNum__;
    /** 前へ次への移動が可能か  0:移動可  1:移動不可*/
    private int sml030PrevNextFlg__;
    /** ショートメール内容 */
    private ArrayList<SmailDetailModel> sml030SmlList__;
    
    //tinyMCE用
    /** 選択中ショートメールSID*/
    private int sml030SmlSid__;
    /** ボディ内容ファイルSID*/
    private int sml030BodyFileSid__;
    
    //前画面(メインメニュー)
    /** 週間スケジュール 表示日付 */
    private String schWeekDate__;
    /** 日間スケジュール 表示日付 */
    private String schDailyDate__;

    /** 添付ファイルリスト */
    private ArrayList<CmnBinfModel> sml030FileList__ = null;
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String sml030binSid__ = null;
    /** 送信先も含めて削除可能か*/
    private boolean sml030DeleteAllOk__ = false;
    /** 返信・全返信ボタン表示フラグ */
    private boolean sml030HensinDspFlg__ = true;

    /** 送信モードフラグ */
    private boolean sml030SosinFlg__ = false;

    //--------------------------------------------------
    // sml090検索パラメータ
    //--------------------------------------------------
    /** 処理モード（sml010ProcMode）のセーブ値 */
    private String sml090ProcModeSave__ = GSConstSmail.TAB_DSP_MODE_JUSIN;
    /** 検索画面戻り値 */
    private String sml090BackParm__ = null;

    /** 検索実行 switch */
    private int searchFlg__ = GSConstSmail.SEARCH_EXECUTE_FALSE;

    //ページコンボ
    /** ページコンボ上 */
    private int sml090page1__ = 0;
    /** ページコンボ下 */
    private int sml090page2__ = 0;

    /** 宛先SIDリスト */
    private String[] cmn120userSid__;
    /** 宛先SIDセーブリスト */
    private String[] sml090userSid__;
    /** グループ */
    private String sml090SltGroup__ = null;
    /** ユーザ */
    private String sml090SltUser__ = null;
    /** メール種別 */
    private String sml090MailSyubetsu__ = null;
    /** マーク */
    private String sml090MailMark__ = String.valueOf(GSConstSmail.MARK_KBN_ALL);
    /** キーワード検索区分 */
    private String sml090KeyWordkbn__ = String.valueOf(GSConstSmail.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] sml090SearchTarget__ = SmlCommonBiz.getDefultSearchTarget();
    /** 削除チェック */
    private String[] sml090DelSid__;
    /** 削除チェック (選択済) */
    private ArrayList<String> sml090SelectedDelSid__;

    //ソート順
    /** ソートキー1 */
    private String sml090SearchSortKey1__ = String.valueOf(GSConstSmail.MSG_SORT_KEY_DATE);
    /** オーダーキー1 */
    private String sml090SearchOrderKey1__ = String.valueOf(GSConstSmail.ORDER_KEY_ASC);
    /** ソートキー2 */
    private String sml090SearchSortKey2__ = String.valueOf(GSConstSmail.MSG_SORT_KEY_MARK);
    /** オーダーキー2 */
    private String sml090SearchOrderKey2__ = String.valueOf(GSConstSmail.ORDER_KEY_ASC);
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
    //--------------------------------------------------

    /**
     * <p>sml030SelectedRowNum を取得します。
     * @return sml030SelectedRowNum
     */
    public int getSml030SelectedRowNum() {
        return sml030SelectedRowNum__;
    }
    /**
     * <p>sml030SelectedRowNum をセットします。
     * @param sml030SelectedRowNum sml030SelectedRowNum
     */
    public void setSml030SelectedRowNum(int sml030SelectedRowNum) {
        sml030SelectedRowNum__ = sml030SelectedRowNum;
    }
    /**
     * <p>sml030PrevNextFlg を取得します。
     * @return sml030PrevNextFlg
     */
    public int getSml030PrevNextFlg() {
        return sml030PrevNextFlg__;
    }
    /**
     * <p>sml030PrevNextFlg をセットします。
     * @param sml030PrevNextFlg sml030PrevNextFlg
     */
    public void setSml030PrevNextFlg(int sml030PrevNextFlg) {
        sml030PrevNextFlg__ = sml030PrevNextFlg;
    }
    /**
     * <p>sml030SmlList を取得します。
     * @return sml030SmlList
     */
    public ArrayList<SmailDetailModel> getSml030SmlList() {
        return sml030SmlList__;
    }
    /**
     * <p>sml030SmlList をセットします。
     * @param sml030SmlList sml030SmlList
     */
    public void setSml030SmlList(ArrayList<SmailDetailModel> sml030SmlList) {
        sml030SmlList__ = sml030SmlList;
    }
    /**
     * <p>schWeekDate を取得します。
     * @return schWeekDate
     */
    public String getSchWeekDate() {
        return schWeekDate__;
    }
    /**
     * <p>schWeekDate をセットします。
     * @param schWeekDate schWeekDate
     */
    public void setSchWeekDate(String schWeekDate) {
        schWeekDate__ = schWeekDate;
    }
    /**
     * <p>schDailyDate を取得します。
     * @return schDailyDate
     */
    public String getSchDailyDate() {
        return schDailyDate__;
    }
    /**
     * <p>schDailyDate をセットします。
     * @param schDailyDate schDailyDate
     */
    public void setSchDailyDate(String schDailyDate) {
        schDailyDate__ = schDailyDate;
    }
    /**
     * <p>sml030FileList を取得します。
     * @return sml030FileList
     */
    public ArrayList<CmnBinfModel> getSml030FileList() {
        return sml030FileList__;
    }
    /**
     * <p>sml030FileList をセットします。
     * @param sml030FileList sml030FileList
     */
    public void setSml030FileList(ArrayList<CmnBinfModel> sml030FileList) {
        sml030FileList__ = sml030FileList;
    }
    /**
     * <p>sml030binSid を取得します。
     * @return sml030binSid
     */
    public String getSml030binSid() {
        return sml030binSid__;
    }
    /**
     * <p>sml030binSid をセットします。
     * @param sml030binSid sml030binSid
     */
    public void setSml030binSid(String sml030binSid) {
        sml030binSid__ = sml030binSid;
    }
    /**
     * <p>sml030DeleteAllOk を取得します。
     * @return sml030DeleteAllOk
     */
    public boolean isSml030DeleteAllOk() {
        return sml030DeleteAllOk__;
    }
    /**
     * <p>sml030DeleteAllOk をセットします。
     * @param sml030DeleteAllOk sml030DeleteAllOk
     */
    public void setSml030DeleteAllOk(boolean sml030DeleteAllOk) {
        sml030DeleteAllOk__ = sml030DeleteAllOk;
    }
    /**
     * <p>sml030HensinDspFlg を取得します。
     * @return sml030HensinDspFlg
     */
    public boolean isSml030HensinDspFlg() {
        return sml030HensinDspFlg__;
    }
    /**
     * <p>sml030HensinDspFlg をセットします。
     * @param sml030HensinDspFlg sml030HensinDspFlg
     */
    public void setSml030HensinDspFlg(boolean sml030HensinDspFlg) {
        sml030HensinDspFlg__ = sml030HensinDspFlg;
    }
    /**
     * <p>sml030SosinFlg を取得します。
     * @return sml030SosinFlg
     */
    public boolean isSml030SosinFlg() {
        return sml030SosinFlg__;
    }
    /**
     * <p>sml030SosinFlg をセットします。
     * @param sml030SosinFlg sml030SosinFlg
     */
    public void setSml030SosinFlg(boolean sml030SosinFlg) {
        sml030SosinFlg__ = sml030SosinFlg;
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
     * <p>sml090BackParm を取得します。
     * @return sml090BackParm
     */
    public String getSml090BackParm() {
        return sml090BackParm__;
    }
    /**
     * <p>sml090BackParm をセットします。
     * @param sml090BackParm sml090BackParm
     */
    public void setSml090BackParm(String sml090BackParm) {
        sml090BackParm__ = sml090BackParm;
    }
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
     * <p>sml090userSid を取得します。
     * @return sml090userSid
     */
    public String[] getSml090userSid() {
        return sml090userSid__;
    }
    /**
     * <p>sml090userSid をセットします。
     * @param sml090userSid sml090userSid
     */
    public void setSml090userSid(String[] sml090userSid) {
        sml090userSid__ = sml090userSid;
    }
    /**
     * <p>sml090SltGroup を取得します。
     * @return sml090SltGroup
     */
    public String getSml090SltGroup() {
        return sml090SltGroup__;
    }
    /**
     * <p>sml090SltGroup をセットします。
     * @param sml090SltGroup sml090SltGroup
     */
    public void setSml090SltGroup(String sml090SltGroup) {
        sml090SltGroup__ = sml090SltGroup;
    }
    /**
     * <p>sml090SltUser を取得します。
     * @return sml090SltUser
     */
    public String getSml090SltUser() {
        return sml090SltUser__;
    }
    /**
     * <p>sml090SltUser をセットします。
     * @param sml090SltUser sml090SltUser
     */
    public void setSml090SltUser(String sml090SltUser) {
        sml090SltUser__ = sml090SltUser;
    }
    /**
     * <p>sml090MailSyubetsu を取得します。
     * @return sml090MailSyubetsu
     */
    public String getSml090MailSyubetsu() {
        return sml090MailSyubetsu__;
    }
    /**
     * <p>sml090MailSyubetsu をセットします。
     * @param sml090MailSyubetsu sml090MailSyubetsu
     */
    public void setSml090MailSyubetsu(String sml090MailSyubetsu) {
        sml090MailSyubetsu__ = sml090MailSyubetsu;
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
     * <p>sml090SearchSortKey1 を取得します。
     * @return sml090SearchSortKey1
     */
    public String getSml090SearchSortKey1() {
        return sml090SearchSortKey1__;
    }
    /**
     * <p>sml090SearchSortKey1 をセットします。
     * @param sml090SearchSortKey1 sml090SearchSortKey1
     */
    public void setSml090SearchSortKey1(String sml090SearchSortKey1) {
        sml090SearchSortKey1__ = sml090SearchSortKey1;
    }
    /**
     * <p>sml090SearchOrderKey1 を取得します。
     * @return sml090SearchOrderKey1
     */
    public String getSml090SearchOrderKey1() {
        return sml090SearchOrderKey1__;
    }
    /**
     * <p>sml090SearchOrderKey1 をセットします。
     * @param sml090SearchOrderKey1 sml090SearchOrderKey1
     */
    public void setSml090SearchOrderKey1(String sml090SearchOrderKey1) {
        sml090SearchOrderKey1__ = sml090SearchOrderKey1;
    }
    /**
     * <p>sml090SearchSortKey2 を取得します。
     * @return sml090SearchSortKey2
     */
    public String getSml090SearchSortKey2() {
        return sml090SearchSortKey2__;
    }
    /**
     * <p>sml090SearchSortKey2 をセットします。
     * @param sml090SearchSortKey2 sml090SearchSortKey2
     */
    public void setSml090SearchSortKey2(String sml090SearchSortKey2) {
        sml090SearchSortKey2__ = sml090SearchSortKey2;
    }
    /**
     * <p>sml090SearchOrderKey2 を取得します。
     * @return sml090SearchOrderKey2
     */
    public String getSml090SearchOrderKey2() {
        return sml090SearchOrderKey2__;
    }
    /**
     * <p>sml090SearchOrderKey2 をセットします。
     * @param sml090SearchOrderKey2 sml090SearchOrderKey2
     */
    public void setSml090SearchOrderKey2(String sml090SearchOrderKey2) {
        sml090SearchOrderKey2__ = sml090SearchOrderKey2;
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
     * <p>sml030SmlSid を取得します。
     * @return sml030SmlSid
     * @see jp.groupsession.v2.sml.sml030.Sml030ParamModel#sml030SmlSid__
     */
    public int getSml030SmlSid() {
        return sml030SmlSid__;
    }
    /**
     * <p>sml030SmlSid をセットします。
     * @param sml030SmlSid sml030SmlSid
     * @see jp.groupsession.v2.sml.sml030.Sml030ParamModel#sml030SmlSid__
     */
    public void setSml030SmlSid(int sml030SmlSid) {
        sml030SmlSid__ = sml030SmlSid;
    }
    /**
     * <p>sml090SvSearchSortKey2 をセットします。
     * @param sml090SvSearchSortKey2 sml090SvSearchSortKey2
     */
    public void setSml090SvSearchSortKey2(String sml090SvSearchSortKey2) {
        sml090SvSearchSortKey2__ = sml090SvSearchSortKey2;
    }
    /**
     * <p>sml030BodyFileSid を取得します。
     * @return sml030BodyFileSid
     * @see jp.groupsession.v2.sml.sml030.Sml030ParamModel#sml030BodyFileSid__
     */
    public int getSml030BodyFileSid() {
        return sml030BodyFileSid__;
    }
    /**
     * <p>sml030BodyFileSid をセットします。
     * @param sml030BodyFileSid sml030BodyFileSid
     * @see jp.groupsession.v2.sml.sml030.Sml030ParamModel#sml030BodyFileSid__
     */
    public void setSml030BodyFileSid(int sml030BodyFileSid) {
        sml030BodyFileSid__ = sml030BodyFileSid;
    }
}