package jp.groupsession.v2.sml.sml010;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmailUsrModel;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.model.SmlParamModel;
import jp.groupsession.v2.sml.ui.parts.account.AccountSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] ショートメール一覧画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml010ParamModel extends SmlParamModel {

    /** 表示アカウント */
    private int smlViewAccount__ = 0;

    /** アカウント一覧 */
    private List<AccountDataModel> sml010AccountList__;

    /** アカウントテーマ */
    private String sml010AccountTheme__;
    /** アカウント送信メール形式 */
    private int sml010AccountSendMailType__ = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;
    /** アカウントディスク使用量 */
    private String sml010AccountDisk__;

    /** 最大メール文字数*/
    private int sml010MailBodyLimit__;

    /** 処理モード */
    private String sml010ProcMode__ = GSConstSmail.TAB_DSP_MODE_JUSIN;
    /** ページ数 */
    private int sml010PageNum__ = 1;
    /** ページ1 */
    private int sml010Slt_page1__;
    /** ページ2 */
    private int sml010Slt_page2__;
    /** ページラベル */
    ArrayList<LabelValueBean> sml010PageLabel__;
    /** オーダーキー */
    private int sml010Order_key__ = GSConstSmail.ORDER_KEY_DESC;
    /** ソートキー */
    private int sml010Sort_key__ = GSConstSmail.MSG_SORT_KEY_DATE;
    /** メッセージ一覧リスト */
    private ArrayList<SmailModel> sml010SmlList__;
    /** 削除チェック */
    private String[] sml010DelSid__;
    /** 削除チェック (選択済) */
    private ArrayList<String> sml010SelectedDelSid__;
    /** 削除チェック  ラベル選択時*/
    private String[] sml010LabelDelSid__;
    /** 選択メールSID */
    private int sml010SelectedSid__;
    /** 編集元メールSID */
    private int sml010EditSid__;
    /** メール区分 */
    private String sml010SelectedMailKbn__;
    /** 自動リロード時間 */
    private int sml010Reload__ = GSConstSmail.MAIL_RELOAD_10MIN;


    /** 自動削除区分 */
    private int sml010autoDelKbn__ = GSConstSmail.SML_AUTO_DEL_NO;
    /** 自動削除 年 */
    private int sml010autoDelYear__ = 0;
    /** 自動削除 月 */
    private int sml010autoDelMonth__ = 0;

    /** グループ一覧 */
    private List<CmnLabelValueModel> sml010groupList__ = null;
    /** 追加用ユーザ一覧 */
    private List<SmailUsrModel> sml010userList__ = null;
    /** グループSID */
    private String sml010groupSid__ = null;
    /** ユーザSID */
    private int sml010usrSid__ = 0;
    /** ユーザ追加チェック */
    private String[] sml010usrSids__;

    /** 検索結果(ユーザリスト) */
    List<SmlHinaModel> sml010HinaList__ = null;
    /** 検索結果(ユーザリスト) */
    List<SmlHinaModel> sml010HinaListKjn__ = null;
    /** 選択されたひな形 */
    private int sml010SelectHinaId__ = 0;

    /** 社員画像表示フラグ */
    private int photoDspFlg__;

    /** 添付画像表示区分 */
    private int tempDspFlg__;
    /** 宛先 選択UI共通*/
    private String[] sml010AtesakiSid__;
    /** 宛先UI 選択UI共通*/
    private AccountSelector sml010AtesakiUI__ = null;


    /** 送信アカウント*/
    private int sml020SendAccount__;
    /** 宛先 TO*/
    private String[] sml020userSid__;
    /** 宛先 CC*/
    private String[] sml020userSidCc__;
    /** 宛先 BCC*/
    private String[] sml020userSidBcc__;


    /* 検索機能 */
    /** 社員画像表示フラグ */
    private int photoSearchDspFlg__;
    /** 検索ワード */
    private String sml090KeyWord__ = null;
    /** グループ */
    private String sml090SltGroup__ = null;
    /** ユーザ */
    private String sml090SltUser__ = null;
    /** 宛先 */
    private String[] sml090Atesaki__ = null;
    //ラベルリスト
    /** グループラベルリスト */
    private List<LabelValueBean> sml090GroupLabel__ = null;
    /** ユーザラベルリスト */
    private List<UsrLabelValueBean> sml090UserLabel__ = null;
    /** ソートキーラベルリスト */
    private List<LabelValueBean> sml090SortKeyLabelList__ = null;
    /** メール種別 */
    private String sml090MailSyubetsu__ = null;

    //ソート順
    /** ソートキー1 */
    private String sml090SearchSortKey1__ = String.valueOf(GSConstSmail.MSG_SORT_KEY_DATE);
    /** オーダーキー1 */
    private String sml090SearchOrderKey1__ = String.valueOf(GSConstSmail.ORDER_KEY_DESC);
    /** ソートキー2 */
    private String sml090SearchSortKey2__ = String.valueOf(GSConstSmail.MSG_SORT_KEY_MARK);
    /** オーダーキー2 */
    private String sml090SearchOrderKey2__ = String.valueOf(GSConstSmail.ORDER_KEY_ASC);



    /** マーク */
    private String sml090MailMark__ = String.valueOf(GSConstSmail.MARK_KBN_ALL);
    /** キーワード検索区分 */
    private String sml090KeyWordkbn__ = String.valueOf(GSConstSmail.KEY_WORD_KBN_AND);
    /** 検索対象 */
    private String[] sml090SearchTarget__ = null;

    /** WEB検索ワード */
    private String sml090WebSearchWord__ = "";

    /*-- SVパラメータ start ----------------------------------------------------------*/


    /** グループ */
    private String sml090SvSltGroup__ = null;
    /** ユーザ */
    private String sml090SvSltUser__ = null;

    /** 宛先 */
    private String[] sml090SvAtesaki__ = null;


//    /** 宛先情報 */
//    private SmailModel sml090SvAtesakiModel__ = null;

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

    /** ひな形区分 */
    private int sml050HinaKbn__ = GSConstSmail.HINA_KBN_PRI;

    //管理者権限
    /** 管理者権限有無 */
    private int adminFlg__ = GSConst.USER_NOT_ADMIN;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 未読メール件数 */
    private int sml010MidokuCnt__ = 0;

    /** 草稿メール件数 */
    private int sml010SokoCnt__ = 0;

    /** 未読メール件数(ゴミ箱) */
    private int sml010GomiMidokuCnt__ = 0;

    /** メッセージリスト */
    private List<String> messageList__;

    /** エラー */
    private List<String> errorsList__ = null;

    /** 管理者ユーザフラグ */
    private boolean sml010adminUser__ = false;

    /** スクリプト実行フラグ(他プラグインから遷移時) */
    private int sml010scriptFlg__ = GSConstSmail.SCRIPT_FIG_FALSE;
    /** スクリプト区分 */
    private int sml010scriptKbn__ = GSConstSmail.SCRIPT_NO_SET;
    /** スクリプト 選択ユーザSID */
    private String sml010scriptSelUsrSid__;
    /** スクリプト 選択ユーザ名 */
    private String sml010scriptSelUsrName__;
    /** スクリプト 選択ユーアカウントザSID */
    private String sml010scriptSelSacSid__;

    /** ラベルコンボ */
    private List<LabelValueBean> labelCombo__ = null;

    /** ラベル追加種別 0:既存のラベルを付加、1:新規登録したラベルを追加 */
    private int sml010addLabelType__ = GSConstSmail.ADDLABEL_NORMAL;

    /** 選択ラベルSID */
    private int sml010SelectLabelSid__ = 0;
    /** 追加ラベルSID */
    private int sml010addLabel__ = 0;
    /** 追加ラベル名(ラベル新規登録) */
    private String sml010addLabelName__ = null;
    /** 削除ラベルSID */
    private int sml010delLabel__ = 0;

    /** ラベル一覧 */
    private List<SmlLabelModel> sml010LabelList__ = null;

    /** 出力メール情報 */
    private List<Sml010ExportFileModel> sml010ExportMailList__ = null;

    /** WEBメール 連携判定 */
    private int sml020webmail__ = 0;
    /** WEBメール 対象メール */
    private long sml020webmailId__ = 0;
    /** メール作成時の状態区分(0:新規 1:返信 2:全返信 3:転送) */
    private String sml020ProcMode__ = GSConstSmail.MSG_CREATE_MODE_NEW;

    /** WEBメール共有　表示フラグ*/
    private int sml010webmailShareFlg__ = 1;


    /**
     * <p>sml010ProcMode を取得します。
     * @return sml010ProcMode
     */
    public String getSml010ProcMode() {
        return sml010ProcMode__;
    }

    /**
     * <p>sml010ProcMode をセットします。
     * @param sml010ProcMode sml010ProcMode
     */
    public void setSml010ProcMode(String sml010ProcMode) {
        sml010ProcMode__ = sml010ProcMode;
    }

    /**
     * <p>sml010PageNum を取得します。
     * @return sml010PageNum
     */
    public int getSml010PageNum() {
        return sml010PageNum__;
    }

    /**
     * <p>sml010PageNum をセットします。
     * @param sml010PageNum sml010PageNum
     */
    public void setSml010PageNum(int sml010PageNum) {
        sml010PageNum__ = sml010PageNum;
    }

    /**
     * <p>sml010Slt_page1 を取得します。
     * @return sml010Slt_page1
     */
    public int getSml010Slt_page1() {
        return sml010Slt_page1__;
    }

    /**
     * <p>sml010Slt_page1 をセットします。
     * @param sml010SltPage1 sml010Slt_page1
     */
    public void setSml010Slt_page1(int sml010SltPage1) {
        sml010Slt_page1__ = sml010SltPage1;
    }

    /**
     * <p>sml010Slt_page2 を取得します。
     * @return sml010Slt_page2
     */
    public int getSml010Slt_page2() {
        return sml010Slt_page2__;
    }

    /**
     * <p>sml010Slt_page2 をセットします。
     * @param sml010SltPage2 sml010Slt_page2
     */
    public void setSml010Slt_page2(int sml010SltPage2) {
        sml010Slt_page2__ = sml010SltPage2;
    }

    /**
     * <p>sml010PageLabel を取得します。
     * @return sml010PageLabel
     */
    public ArrayList<LabelValueBean> getSml010PageLabel() {
        return sml010PageLabel__;
    }

    /**
     * <p>sml010PageLabel をセットします。
     * @param sml010PageLabel sml010PageLabel
     */
    public void setSml010PageLabel(ArrayList<LabelValueBean> sml010PageLabel) {
        sml010PageLabel__ = sml010PageLabel;
    }

    /**
     * <p>sml010Order_key を取得します。
     * @return sml010Order_key
     */
    public int getSml010Order_key() {
        return sml010Order_key__;
    }

    /**
     * <p>sml010Order_key をセットします。
     * @param sml010OrderKey sml010Order_key
     */
    public void setSml010Order_key(int sml010OrderKey) {
        sml010Order_key__ = sml010OrderKey;
    }

    /**
     * <p>sml010Sort_key を取得します。
     * @return sml010Sort_key
     */
    public int getSml010Sort_key() {
        return sml010Sort_key__;
    }

    /**
     * <p>sml010Sort_key をセットします。
     * @param sml010SortKey sml010Sort_key
     */
    public void setSml010Sort_key(int sml010SortKey) {
        sml010Sort_key__ = sml010SortKey;
    }

    /**
     * <p>sml010SmlList を取得します。
     * @return sml010SmlList
     */
    public ArrayList<SmailModel> getSml010SmlList() {
        return sml010SmlList__;
    }

    /**
     * <p>sml010SmlList をセットします。
     * @param sml010SmlList sml010SmlList
     */
    public void setSml010SmlList(ArrayList<SmailModel> sml010SmlList) {
        sml010SmlList__ = sml010SmlList;
    }

    /**
     * <p>sml010DelSid を取得します。
     * @return sml010DelSid
     */
    public String[] getSml010DelSid() {
        return sml010DelSid__;
    }

    /**
     * <p>sml010DelSid をセットします。
     * @param sml010DelSid sml010DelSid
     */
    public void setSml010DelSid(String[] sml010DelSid) {
        sml010DelSid__ = sml010DelSid;
    }

    /**
     * <p>sml010SelectedDelSid を取得します。
     * @return sml010SelectedDelSid
     */
    public ArrayList<String> getSml010SelectedDelSid() {
        return sml010SelectedDelSid__;
    }

    /**
     * <p>sml010SelectedDelSid をセットします。
     * @param sml010SelectedDelSid sml010SelectedDelSid
     */
    public void setSml010SelectedDelSid(ArrayList<String> sml010SelectedDelSid) {
        sml010SelectedDelSid__ = sml010SelectedDelSid;
    }

    /**
     * <p>sml010SelectedSid を取得します。
     * @return sml010SelectedSid
     */
    public int getSml010SelectedSid() {
        return sml010SelectedSid__;
    }

    /**
     * <p>sml010SelectedSid をセットします。
     * @param sml010SelectedSid sml010SelectedSid
     */
    public void setSml010SelectedSid(int sml010SelectedSid) {
        sml010SelectedSid__ = sml010SelectedSid;
    }

    /**
     * <p>sml010EditSid を取得します。
     * @return sml010EditSid
     */
    public int getSml010EditSid() {
        return sml010EditSid__;
    }

    /**
     * <p>sml010EditSid をセットします。
     * @param sml010EditSid sml010EditSid
     */
    public void setSml010EditSid(int sml010EditSid) {
        sml010EditSid__ = sml010EditSid;
    }

    /**
     * <p>sml010SelectedMailKbn を取得します。
     * @return sml010SelectedMailKbn
     */
    public String getSml010SelectedMailKbn() {
        return sml010SelectedMailKbn__;
    }

    /**
     * <p>sml010SelectedMailKbn をセットします。
     * @param sml010SelectedMailKbn sml010SelectedMailKbn
     */
    public void setSml010SelectedMailKbn(String sml010SelectedMailKbn) {
        sml010SelectedMailKbn__ = sml010SelectedMailKbn;
    }

    /**
     * <p>sml010Reload を取得します。
     * @return sml010Reload
     */
    public int getSml010Reload() {
        return sml010Reload__;
    }

    /**
     * <p>sml010Reload をセットします。
     * @param sml010Reload sml010Reload
     */
    public void setSml010Reload(int sml010Reload) {
        sml010Reload__ = sml010Reload;
    }

    /**
     * <p>sml010autoDelKbn を取得します。
     * @return sml010autoDelKbn
     */
    public int getSml010autoDelKbn() {
        return sml010autoDelKbn__;
    }

    /**
     * <p>sml010autoDelKbn をセットします。
     * @param sml010autoDelKbn sml010autoDelKbn
     */
    public void setSml010autoDelKbn(int sml010autoDelKbn) {
        sml010autoDelKbn__ = sml010autoDelKbn;
    }

    /**
     * <p>sml010autoDelYear を取得します。
     * @return sml010autoDelYear
     */
    public int getSml010autoDelYear() {
        return sml010autoDelYear__;
    }

    /**
     * <p>sml010autoDelYear をセットします。
     * @param sml010autoDelYear sml010autoDelYear
     */
    public void setSml010autoDelYear(int sml010autoDelYear) {
        sml010autoDelYear__ = sml010autoDelYear;
    }

    /**
     * <p>sml010autoDelMonth を取得します。
     * @return sml010autoDelMonth
     */
    public int getSml010autoDelMonth() {
        return sml010autoDelMonth__;
    }

    /**
     * <p>sml010autoDelMonth をセットします。
     * @param sml010autoDelMonth sml010autoDelMonth
     */
    public void setSml010autoDelMonth(int sml010autoDelMonth) {
        sml010autoDelMonth__ = sml010autoDelMonth;
    }

    /**
     * <p>sml010groupList を取得します。
     * @return sml010groupList
     */
    public List<CmnLabelValueModel> getSml010groupList() {
        return sml010groupList__;
    }

    /**
     * <p>sml010groupList をセットします。
     * @param sml010groupList sml010groupList
     */
    public void setSml010groupList(List<CmnLabelValueModel> sml010groupList) {
        sml010groupList__ = sml010groupList;
    }

    /**
     * <p>sml010groupSid を取得します。
     * @return sml010groupSid
     */
    public String getSml010groupSid() {
        return sml010groupSid__;
    }

    /**
     * <p>sml010groupSid をセットします。
     * @param sml010groupSid sml010groupSid
     */
    public void setSml010groupSid(String sml010groupSid) {
        sml010groupSid__ = sml010groupSid;
    }

    /**
     * <p>sml010usrSid を取得します。
     * @return sml010usrSid
     */
    public int getSml010usrSid() {
        return sml010usrSid__;
    }

    /**
     * <p>sml010usrSid をセットします。
     * @param sml010usrSid sml010usrSid
     */
    public void setSml010usrSid(int sml010usrSid) {
        sml010usrSid__ = sml010usrSid;
    }

    /**
     * <p>sml010usrSids を取得します。
     * @return sml010usrSids
     */
    public String[] getSml010usrSids() {
        return sml010usrSids__;
    }

    /**
     * <p>sml010usrSids をセットします。
     * @param sml010usrSids sml010usrSids
     */
    public void setSml010usrSids(String[] sml010usrSids) {
        sml010usrSids__ = sml010usrSids;
    }

    /**
     * <p>photoDspFlg を取得します。
     * @return photoDspFlg
     */
    public int getPhotoDspFlg() {
        return photoDspFlg__;
    }

    /**
     * <p>photoDspFlg をセットします。
     * @param photoDspFlg photoDspFlg
     */
    public void setPhotoDspFlg(int photoDspFlg) {
        photoDspFlg__ = photoDspFlg;
    }

    /**
     * <p>tempDspFlg を取得します。
     * @return tempDspFlg
     */
    public int getTempDspFlg() {
        return tempDspFlg__;
    }

    /**
     * <p>tempDspFlg をセットします。
     * @param tempDspFlg tempDspFlg
     */
    public void setTempDspFlg(int tempDspFlg) {
        tempDspFlg__ = tempDspFlg;
    }

    /**
     * <p>sml090KeyWord を取得します。
     * @return sml090KeyWord
     */
    public String getSml090KeyWord() {
        return sml090KeyWord__;
    }

    /**
     * <p>sml090KeyWord をセットします。
     * @param sml090KeyWord sml090KeyWord
     */
    public void setSml090KeyWord(String sml090KeyWord) {
        sml090KeyWord__ = sml090KeyWord;
    }

    /**
     * <p>sml050HinaKbn を取得します。
     * @return sml050HinaKbn
     */
    public int getSml050HinaKbn() {
        return sml050HinaKbn__;
    }

    /**
     * <p>sml050HinaKbn をセットします。
     * @param sml050HinaKbn sml050HinaKbn
     */
    public void setSml050HinaKbn(int sml050HinaKbn) {
        sml050HinaKbn__ = sml050HinaKbn;
    }

    /**
     * <p>adminFlg を取得します。
     * @return adminFlg
     */
    public int getAdminFlg() {
        return adminFlg__;
    }

    /**
     * <p>adminFlg をセットします。
     * @param adminFlg adminFlg
     */
    public void setAdminFlg(int adminFlg) {
        adminFlg__ = adminFlg;
    }

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
     * <p>sml010MidokuCnt を取得します。
     * @return sml010MidokuCnt
     */
    public int getSml010MidokuCnt() {
        return sml010MidokuCnt__;
    }

    /**
     * <p>sml010MidokuCnt をセットします。
     * @param sml010MidokuCnt sml010MidokuCnt
     */
    public void setSml010MidokuCnt(int sml010MidokuCnt) {
        sml010MidokuCnt__ = sml010MidokuCnt;
    }

    /**
     * <p>sml010SokoCnt を取得します。
     * @return sml010SokoCnt
     */
    public int getSml010SokoCnt() {
        return sml010SokoCnt__;
    }

    /**
     * <p>sml010SokoCnt をセットします。
     * @param sml010SokoCnt sml010SokoCnt
     */
    public void setSml010SokoCnt(int sml010SokoCnt) {
        sml010SokoCnt__ = sml010SokoCnt;
    }

    /**
     * <p>sml010HinaList を取得します。
     * @return sml010HinaList
     */
    public List<SmlHinaModel> getSml010HinaList() {
        return sml010HinaList__;
    }

    /**
     * <p>sml010HinaList をセットします。
     * @param sml010HinaList sml010HinaList
     */
    public void setSml010HinaList(List<SmlHinaModel> sml010HinaList) {
        sml010HinaList__ = sml010HinaList;
    }

    /**
     * <p>sml010HinaListKjn を取得します。
     * @return sml010HinaListKjn
     */
    public List<SmlHinaModel> getSml010HinaListKjn() {
        return sml010HinaListKjn__;
    }

    /**
     * <p>sml010HinaListKjn をセットします。
     * @param sml010HinaListKjn sml010HinaListKjn
     */
    public void setSml010HinaListKjn(List<SmlHinaModel> sml010HinaListKjn) {
        sml010HinaListKjn__ = sml010HinaListKjn;
    }

    /**
     * <p>sml010SelectHinaId を取得します。
     * @return sml010SelectHinaId
     */
    public int getSml010SelectHinaId() {
        return sml010SelectHinaId__;
    }

    /**
     * <p>sml010SelectHinaId をセットします。
     * @param sml010SelectHinaId sml010SelectHinaId
     */
    public void setSml010SelectHinaId(int sml010SelectHinaId) {
        sml010SelectHinaId__ = sml010SelectHinaId;
    }

    /**
     * <p>sml090GroupLabel を取得します。
     * @return sml090GroupLabel
     */
    public List<LabelValueBean> getSml090GroupLabel() {
        return sml090GroupLabel__;
    }

    /**
     * <p>sml090GroupLabel をセットします。
     * @param sml090GroupLabel sml090GroupLabel
     */
    public void setSml090GroupLabel(List<LabelValueBean> sml090GroupLabel) {
        sml090GroupLabel__ = sml090GroupLabel;
    }

    /**
     * <p>sml090UserLabel を取得します。
     * @return sml090UserLabel
     */
    public List<UsrLabelValueBean> getSml090UserLabel() {
        return sml090UserLabel__;
    }

    /**
     * <p>sml090UserLabel をセットします。
     * @param sml090UserLabel sml090UserLabel
     */
    public void setSml090UserLabel(List<UsrLabelValueBean> sml090UserLabel) {
        sml090UserLabel__ = sml090UserLabel;
    }

    /**
     * <p>sml090SortKeyLabelList を取得します。
     * @return sml090SortKeyLabelList
     */
    public List<LabelValueBean> getSml090SortKeyLabelList() {
        return sml090SortKeyLabelList__;
    }

    /**
     * <p>sml090SortKeyLabelList をセットします。
     * @param sml090SortKeyLabelList sml090SortKeyLabelList
     */
    public void setSml090SortKeyLabelList(
            List<LabelValueBean> sml090SortKeyLabelList) {
        sml090SortKeyLabelList__ = sml090SortKeyLabelList;
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
     * <p>photoSearchDspFlg を取得します。
     * @return photoSearchDspFlg
     */
    public int getPhotoSearchDspFlg() {
        return photoSearchDspFlg__;
    }

    /**
     * <p>photoSearchDspFlg をセットします。
     * @param photoSearchDspFlg photoSearchDspFlg
     */
    public void setPhotoSearchDspFlg(int photoSearchDspFlg) {
        photoSearchDspFlg__ = photoSearchDspFlg;
    }

    /**
     * <p>sml010adminUser を取得します。
     * @return sml010adminUser
     */
    public boolean isSml010adminUser() {
        return sml010adminUser__;
    }

    /**
     * <p>sml010adminUser をセットします。
     * @param sml010adminUser sml010adminUser
     */
    public void setSml010adminUser(boolean sml010adminUser) {
        sml010adminUser__ = sml010adminUser;
    }

    /**
     * <p>sml010AccountList を取得します。
     * @return sml010AccountList
     */
    public List<AccountDataModel> getSml010AccountList() {
        return sml010AccountList__;
    }

    /**
     * <p>sml010AccountList をセットします。
     * @param sml010AccountList sml010AccountList
     */
    public void setSml010AccountList(List<AccountDataModel> sml010AccountList) {
        sml010AccountList__ = sml010AccountList;
    }

    /**
     * <p>sml010AccountTheme を取得します。
     * @return sml010AccountTheme
     */
    public String getSml010AccountTheme() {
        return sml010AccountTheme__;
    }

    /**
     * <p>sml010AccountTheme をセットします。
     * @param sml010AccountTheme sml010AccountTheme
     */
    public void setSml010AccountTheme(String sml010AccountTheme) {
        sml010AccountTheme__ = sml010AccountTheme;
    }

    /**
     * <p>sml010AccountDisk を取得します。
     * @return sml010AccountDisk
     */
    public String getSml010AccountDisk() {
        return sml010AccountDisk__;
    }

    /**
     * <p>sml010AccountDisk をセットします。
     * @param sml010AccountDisk sml010AccountDisk
     */
    public void setSml010AccountDisk(String sml010AccountDisk) {
        sml010AccountDisk__ = sml010AccountDisk;
    }

    /**
     * <p>smlViewAccount を取得します。
     * @return smlViewAccount
     */
    public int getSmlViewAccount() {
        return smlViewAccount__;
    }

    /**
     * <p>smlViewAccount をセットします。
     * @param smlViewAccount smlViewAccount
     */
    public void setSmlViewAccount(int smlViewAccount) {
        smlViewAccount__ = smlViewAccount;
    }

    /**
     * <p>sml010userList を取得します。
     * @return sml010userList
     */
    public List<SmailUsrModel> getSml010userList() {
        return sml010userList__;
    }

    /**
     * <p>sml010userList をセットします。
     * @param sml010userList sml010userList
     */
    public void setSml010userList(List<SmailUsrModel> sml010userList) {
        sml010userList__ = sml010userList;
    }

    /**
     * <p>sml010scriptFlg を取得します。
     * @return sml010scriptFlg
     */
    public int getSml010scriptFlg() {
        return sml010scriptFlg__;
    }

    /**
     * <p>sml010scriptFlg をセットします。
     * @param sml010scriptFlg sml010scriptFlg
     */
    public void setSml010scriptFlg(int sml010scriptFlg) {
        sml010scriptFlg__ = sml010scriptFlg;
    }

    /**
     * <p>sml010scriptKbn を取得します。
     * @return sml010scriptKbn
     */
    public int getSml010scriptKbn() {
        return sml010scriptKbn__;
    }

    /**
     * <p>sml010scriptKbn をセットします。
     * @param sml010scriptKbn sml010scriptKbn
     */
    public void setSml010scriptKbn(int sml010scriptKbn) {
        sml010scriptKbn__ = sml010scriptKbn;
    }

    /**
     * <p>sml010scriptSelUsrSid を取得します。
     * @return sml010scriptSelUsrSid
     */
    public String getSml010scriptSelUsrSid() {
        return sml010scriptSelUsrSid__;
    }

    /**
     * <p>sml010scriptSelUsrSid をセットします。
     * @param sml010scriptSelUsrSid sml010scriptSelUsrSid
     */
    public void setSml010scriptSelUsrSid(String sml010scriptSelUsrSid) {
        sml010scriptSelUsrSid__ = sml010scriptSelUsrSid;
    }

    /**
     * <p>sml010scriptSelUsrName を取得します。
     * @return sml010scriptSelUsrName
     */
    public String getSml010scriptSelUsrName() {
        return sml010scriptSelUsrName__;
    }

    /**
     * <p>sml010scriptSelUsrName をセットします。
     * @param sml010scriptSelUsrName sml010scriptSelUsrName
     */
    public void setSml010scriptSelUsrName(String sml010scriptSelUsrName) {
        sml010scriptSelUsrName__ = sml010scriptSelUsrName;
    }

    /**
     * <p>sml010AccountSendMailType を取得します。
     * @return sml010AccountSendMailType
     */
    public int getSml010AccountSendMailType() {
        return sml010AccountSendMailType__;
    }

    /**
     * <p>sml010AccountSendMailType をセットします。
     * @param sml010AccountSendMailType sml010AccountSendMailType
     */
    public void setSml010AccountSendMailType(int sml010AccountSendMailType) {
        sml010AccountSendMailType__ = sml010AccountSendMailType;
    }

    /**
     * <p>labelCombo を取得します。
     * @return labelCombo
     */
    public List<LabelValueBean> getLabelCombo() {
        return labelCombo__;
    }

    /**
     * <p>labelCombo をセットします。
     * @param labelCombo labelCombo
     */
    public void setLabelCombo(List<LabelValueBean> labelCombo) {
        labelCombo__ = labelCombo;
    }

    /**
     * <p>sml010addLabelType を取得します。
     * @return sml010addLabelType
     */
    public int getSml010addLabelType() {
        return sml010addLabelType__;
    }

    /**
     * <p>sml010addLabelType をセットします。
     * @param sml010addLabelType sml010addLabelType
     */
    public void setSml010addLabelType(int sml010addLabelType) {
        sml010addLabelType__ = sml010addLabelType;
    }

    /**
     * <p>sml010addLabel を取得します。
     * @return sml010addLabel
     */
    public int getSml010addLabel() {
        return sml010addLabel__;
    }

    /**
     * <p>sml010addLabel をセットします。
     * @param sml010addLabel sml010addLabel
     */
    public void setSml010addLabel(int sml010addLabel) {
        sml010addLabel__ = sml010addLabel;
    }

    /**
     * <p>sml010addLabelName を取得します。
     * @return sml010addLabelName
     */
    public String getSml010addLabelName() {
        return sml010addLabelName__;
    }

    /**
     * <p>sml010addLabelName をセットします。
     * @param sml010addLabelName sml010addLabelName
     */
    public void setSml010addLabelName(String sml010addLabelName) {
        sml010addLabelName__ = sml010addLabelName;
    }

    /**
     * <p>sml010delLabel を取得します。
     * @return sml010delLabel
     */
    public int getSml010delLabel() {
        return sml010delLabel__;
    }

    /**
     * <p>sml010delLabel をセットします。
     * @param sml010delLabel sml010delLabel
     */
    public void setSml010delLabel(int sml010delLabel) {
        sml010delLabel__ = sml010delLabel;
    }

    /**
     * <p>messageList を取得します。
     * @return messageList
     */
    public List<String> getMessageList() {
        return messageList__;
    }

    /**
     * <p>messageList をセットします。
     * @param messageList messageList
     */
    public void setMessageList(List<String> messageList) {
        messageList__ = messageList;
    }

    /**
     * <p>errorsList を取得します。
     * @return errorsList
     */
    public List<String> getErrorsList() {
        return errorsList__;
    }

    /**
     * <p>errorsList をセットします。
     * @param errorsList errorsList
     */
    public void setErrorsList(List<String> errorsList) {
        errorsList__ = errorsList;
    }

    /**
     * <p>sml010LabelList を取得します。
     * @return sml010LabelList
     */
    public List<SmlLabelModel> getSml010LabelList() {
        return sml010LabelList__;
    }

    /**
     * <p>sml010LabelList をセットします。
     * @param sml010LabelList sml010LabelList
     */
    public void setSml010LabelList(List<SmlLabelModel> sml010LabelList) {
        sml010LabelList__ = sml010LabelList;
    }

    /**
     * <p>sml010SelectLabelSid を取得します。
     * @return sml010SelectLabelSid
     */
    public int getSml010SelectLabelSid() {
        return sml010SelectLabelSid__;
    }

    /**
     * <p>sml010SelectLabelSid をセットします。
     * @param sml010SelectLabelSid sml010SelectLabelSid
     */
    public void setSml010SelectLabelSid(int sml010SelectLabelSid) {
        sml010SelectLabelSid__ = sml010SelectLabelSid;
    }

    /**
     * <p>sml010LabelDelSid を取得します。
     * @return sml010LabelDelSid
     */
    public String[] getSml010LabelDelSid() {
        return sml010LabelDelSid__;
    }

    /**
     * <p>sml010LabelDelSid をセットします。
     * @param sml010LabelDelSid sml010LabelDelSid
     */
    public void setSml010LabelDelSid(String[] sml010LabelDelSid) {
        sml010LabelDelSid__ = sml010LabelDelSid;
    }

    /**
     * <p>sml010ExportMailList を取得します。
     * @return sml010ExportMailList
     */
    public List<Sml010ExportFileModel> getSml010ExportMailList() {
        return sml010ExportMailList__;
    }

    /**
     * <p>sml010ExportMailList をセットします。
     * @param sml010ExportMailList sml010ExportMailList
     */
    public void setSml010ExportMailList(
            List<Sml010ExportFileModel> sml010ExportMailList) {
        sml010ExportMailList__ = sml010ExportMailList;
    }

    /**
     * <p>sml020webmail を取得します。
     * @return sml020webmail
     */
    public int getSml020webmail() {
        return sml020webmail__;
    }

    /**
     * <p>sml020webmail をセットします。
     * @param sml020webmail sml020webmail
     */
    public void setSml020webmail(int sml020webmail) {
        sml020webmail__ = sml020webmail;
    }

    /**
     * <p>sml020webmailId を取得します。
     * @return sml020webmailId
     */
    public long getSml020webmailId() {
        return sml020webmailId__;
    }

    /**
     * <p>sml020webmailId をセットします。
     * @param sml020webmailId sml020webmailId
     */
    public void setSml020webmailId(long sml020webmailId) {
        sml020webmailId__ = sml020webmailId;
    }

    /**
     * <p>sml010scriptSelSacSid を取得します。
     * @return sml010scriptSelSacSid
     */
    public String getSml010scriptSelSacSid() {
        return sml010scriptSelSacSid__;
    }

    /**
     * <p>sml010scriptSelSacSid をセットします。
     * @param sml010scriptSelSacSid sml010scriptSelSacSid
     */
    public void setSml010scriptSelSacSid(String sml010scriptSelSacSid) {
        sml010scriptSelSacSid__ = sml010scriptSelSacSid;
    }

    /**
     * <p>sml010GomiMidokuCnt を取得します。
     * @return sml010GomiMidokuCnt
     */
    public int getSml010GomiMidokuCnt() {
        return sml010GomiMidokuCnt__;
    }

    /**
     * <p>sml010GomiMidokuCnt をセットします。
     * @param sml010GomiMidokuCnt sml010GomiMidokuCnt
     */
    public void setSml010GomiMidokuCnt(int sml010GomiMidokuCnt) {
        sml010GomiMidokuCnt__ = sml010GomiMidokuCnt;
    }

    /**
     * <p>sml020ProcMode を取得します。
     * @return sml020ProcMode
     */
    public String getSml020ProcMode() {
        return sml020ProcMode__;
    }

    /**
     * <p>sml020ProcMode をセットします。
     * @param sml020ProcMode sml020ProcMode
     */
    public void setSml020ProcMode(String sml020ProcMode) {
        sml020ProcMode__ = sml020ProcMode;
    }

    /**
     * <p>sml010webmailShareFlg を取得します。
     * @return sml010webmailShareFlg
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010webmailShareFlg__
     */
    public int getSml010webmailShareFlg() {
        return sml010webmailShareFlg__;
    }

    /**
     * <p>sml010webmailShareFlg をセットします。
     * @param sml010webmailShareFlg sml010webmailShareFlg
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010webmailShareFlg__
     */
    public void setSml010webmailShareFlg(int sml010webmailShareFlg) {
        sml010webmailShareFlg__ = sml010webmailShareFlg;
    }

    /**
     * <p>sml020SendAccount を取得します。
     * @return sml020SendAccount
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020SendAccount__
     */
    public int getSml020SendAccount() {
        return sml020SendAccount__;
    }

    /**
     * <p>sml020SendAccount をセットします。
     * @param sml020SendAccount sml020SendAccount
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020SendAccount__
     */
    public void setSml020SendAccount(int sml020SendAccount) {
        sml020SendAccount__ = sml020SendAccount;
    }

    /**
     * <p>sml010MailBodyLimit を取得します。
     * @return sml010MailBodyLimit
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010MailBodyLimit__
     */
    public int getSml010MailBodyLimit() {
        return sml010MailBodyLimit__;
    }

    /**
     * <p>sml010MailBodyLimit をセットします。
     * @param sml010MailBodyLimit sml010MailBodyLimit
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010MailBodyLimit__
     */
    public void setSml010MailBodyLimit(int sml010MailBodyLimit) {
        sml010MailBodyLimit__ = sml010MailBodyLimit;
    }

    /**
     * <p>sml020userSid を取得します。
     * @return sml020userSid
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020userSid__
     */
    public String[] getSml020userSid() {
        return sml020userSid__;
    }

    /**
     * <p>sml020userSid をセットします。
     * @param sml020userSid sml020userSid
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020userSid__
     */
    public void setSml020userSid(String[] sml020userSid) {
        sml020userSid__ = sml020userSid;
    }

    /**
     * <p>sml020userSidCc を取得します。
     * @return sml020userSidCc
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020userSidCc__
     */
    public String[] getSml020userSidCc() {
        return sml020userSidCc__;
    }

    /**
     * <p>sml020userSidCc をセットします。
     * @param sml020userSidCc sml020userSidCc
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020userSidCc__
     */
    public void setSml020userSidCc(String[] sml020userSidCc) {
        sml020userSidCc__ = sml020userSidCc;
    }

    /**
     * <p>sml020userSidBcc を取得します。
     * @return sml020userSidBcc
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020userSidBcc__
     */
    public String[] getSml020userSidBcc() {
        return sml020userSidBcc__;
    }

    /**
     * <p>sml020userSidBcc をセットします。
     * @param sml020userSidBcc sml020userSidBcc
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml020userSidBcc__
     */
    public void setSml020userSidBcc(String[] sml020userSidBcc) {
        sml020userSidBcc__ = sml020userSidBcc;
    }

    /**
     * <p>sml090Atesaki を取得します。
     * @return sml090Atesaki
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml090Atesaki__
     */
    public String[] getSml090Atesaki() {
        return sml090Atesaki__;
    }

    /**
     * <p>sml090Atesaki をセットします。
     * @param sml090Atesaki sml090Atesaki
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml090Atesaki__
     */
    public void setSml090Atesaki(String[] sml090Atesaki) {
        sml090Atesaki__ = sml090Atesaki;
    }

    /**
     * <p>sml010AtesakiSid を取得します。
     * @return sml010AtesakiSid
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010AtesakiSid__
     */
    public String[] getSml010AtesakiSid() {
        return sml010AtesakiSid__;
    }

    /**
     * <p>sml010AtesakiSid をセットします。
     * @param sml010AtesakiSid sml010AtesakiSid
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010AtesakiSid__
     */
    public void setSml010AtesakiSid(String[] sml010AtesakiSid) {
        sml010AtesakiSid__ = sml010AtesakiSid;
    }

    /**
     * <p>sml010AtesakiUI を取得します。
     * @return sml010AtesakiUI
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010AtesakiUI__
     */
    public AccountSelector getSml010AtesakiUI() {
        return sml010AtesakiUI__;
    }

    /**
     * <p>sml010AtesakiUI をセットします。
     * @param sml010AtesakiUI sml010AtesakiUI
     * @see jp.groupsession.v2.sml.sml010.Sml010ParamModel#sml010AtesakiUI__
     */
    public void setSml010AtesakiUI(AccountSelector sml010AtesakiUI) {
        sml010AtesakiUI__ = sml010AtesakiUI;
    }

}