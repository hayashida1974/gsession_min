package jp.groupsession.v2.sml.sml250;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml240.Sml240ParamModel;
import jp.groupsession.v2.sml.ui.parts.account.AccountSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] WEBメール アカウント登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml250ParamModel extends Sml240ParamModel {

    /** グループSID: グループ一覧 */
    public static final int GRP_SID_GRPLIST = -9;
    /** 使用者 グループを指定 */
    public static final int USERKBN_GROUP = 0;
    /** 使用者 ユーザを指定 */
    public static final int USERKBN_USER = 1;

    /** 送信メール形式 標準 */
    public static final int SEND_MAIL_NORMAL = 0;
    /** 送信メール形式 HTMLメール */
    public static final int SEND_MAIL_HTML = 1;

    /** 選択タブ  */
    private int sml250SelTab__ = GSConstSmail.SEL_TAB_NORMAL;

    /** アカウント区分  */
    private int sml250AccountKbn__ = GSConstSmail.ACNT_DEF;

    /** アカウント名 */
    private String sml250name__ = null;
    /** デフォルトアカウント ユーザSID  */
    private int sml250DefActUsrSid__ = 0;

    /** 削除可能フラグ  */
    private int sml250CanDelFlg__ = GSConstSmail.ACCOUNT_DELETE_OK;

    /** 備考 */
    private String sml250biko__ = null;

    /** 使用者 ユーザ */
    private String[] sml250userKbnUser__ = null;

    /** 送信メール形式 */
    private int sml250sendType__ = SEND_MAIL_NORMAL;

    /** テーマ */
    private int sml250theme__ = 0;
    /** 引用符 */
    private int sml250quotes__ = 0;

    /** 初期表示フラグ */
    private int sml250initFlg__ = 0;

    /** 編集グループコンボ */
    private List<LabelValueBean> userKbnGroupCombo__ = null;

    /** 使用者 設定フラグ */
    private boolean sml250acntUserFlg__ = false;

    /** 使用者 ユーザ グループ */
    private String sml250userKbnUserGroup__ = null;
    /** 使用者 ユーザ(選択用) コンボ */
    private List<UsrLabelValueBean> userKbnUserSelectCombo__  = null;
    /** 使用者 ユーザ 選択UI*/
    private UserGroupSelector sml250userKbnUserUI__ = null;

    /** テーマ 一覧 */
    private List<LabelValueBean> sml250themeList__ = null;
    /** 引用符 一覧 */
    private List<LabelValueBean> sml250quotesList__ = null;

    //--------- アカウントマネージャー画面のパラメータ
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

    /** 表示判定 */
    private int sml250elementKbn__ = 0;


    /** 自動削除区分 */
    private int sml250autoDelKbn__ = GSConstSmail.AUTO_DEL_ADM;
    /** 受信タブ 処理区分 */
    private String sml250JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String sml250SdelKbn__ = null;
    /** 草稿タブ 処理区分 */
    private String sml250WdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String sml250DdelKbn__ = null;

    /** 年リスト */
    private ArrayList<LabelValueBean> sml250YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> sml250MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String sml250JYear__ = null;
    /** 受信タブ 月選択 */
    private String sml250JMonth__ = null;
    /** 送信タブ 年選択 */
    private String sml250SYear__ = null;
    /** 送信タブ 月選択 */
    private String sml250SMonth__ = null;
    /** 草稿タブ 年選択 */
    private String sml250WYear__ = null;
    /** 草稿タブ 月選択 */
    private String sml250WMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String sml250DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String sml250DMonth__ = null;

    //自動送信先設定
    /** 宛先 選択UI共通 選択値*/
    private String[] sml250AutoDestTmpUserSid__;
    /** 宛先 選択UI共通 グループ選択値*/
    private String sml250AutoDestGroupSel__;
    /** 宛先UI 選択UI共通*/
    /** 宛先UI 選択UI共通*/
    private AccountSelector sml250AtesakiUI__ = null;
    /**自動送信先設定 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] sml250AutoDestToUsrSid__ = null;
    /**自動送信先設定 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] sml250AutoDestCcUsrSid__ = null;
    /**自動送信先設定 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] sml250AutoDestBccUsrSid__ = null;
    /**自動送信先設定  現在選択中のToコンボ */
    private List<UsrLabelValueBean> sml250AutoDestToLabelList__ = null;
    /**自動送信先設定  現在選択中のCcコンボ */
    private List<UsrLabelValueBean> sml250AutoDestCcLabelList__ = null;
    /**自動送信先設定  現在選択中のBccコンボ */
    private List<UsrLabelValueBean> sml250AutoDestBccLabelList__ = null;

    //メール転送設定
    /** メール転送区分 */
    private int sml250tensoKbn__ = GSConstSmail.MAIL_FORWARD_NG;
    /** メール転送一括設定区分 */
    private int sml250tensoSetKbn__ = GSConstSmail.MAIL_FORWARD_NO_SET;
    /** 対象区分 全=0 指定=1*/
    private int sml250ObjKbn__ = 0;
    /** アドレス未登録ユーザパス区分 0:エラーとする 1:登録しない */
    private int sml250PassKbn__ = 0;
    /** グループ */
    private String sml250groupSid__ = null;
    /** 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] sml250userSid__ = null;
    /** 宛先UI 選択UI共通*/
    private Sml250TensoTargetSelector sml250tensoTargetUI__ = null;

    /** メール転送設定の利用有無 */
    private String sml250MailFw__ = "0";
    /** メール転送デフォルトメールアドレス */
    private String sml250MailDf__;
    /** デフォルトメールアドレス登録済みメールアドレスコンボの選択値 */
    private String sml250MailDfSelected__ = "1";
    /** メール転送後のショートメール開封状況 */
    private String sml250SmailOp__ = "0";

    /** 在席状況毎にメール振分有無区分 */
    private String sml250HuriwakeKbn__ = "0";
    /** メール転送在席メールアドレスTEXT */
    private String sml250Zmail1__;
    /** メール転送不在メールアドレスTEXT */
    private String sml250Zmail2__;
    /** メール転送その他メールアドレスTEXT */
    private String sml250Zmail3__;

    /** 登録済み在席メールアドレスコンボの選択値 */
    private String sml250Zmail1Selected__ = "1";
    /** 登録済み不在メールアドレスコンボの選択値 */
    private String sml250Zmail2Selected__ = "1";
    /** 登録済みその他メールアドレスコンボの選択値 */
    private String sml250Zmail3Selected__ = "1";

    /** 登録済みメールアドレスコンボ */
    private List < LabelValueBean > sml250MailList__ = null;

    /** 在席管理有効フラグ */
    private int sml250ZaisekiPlugin__ = GSConst.PLUGIN_USE;


    /**
     * <p>sml250biko を取得します。
     * @return sml250biko
     */
    public String getSml250biko() {
        return sml250biko__;
    }
    /**
     * <p>sml250biko をセットします。
     * @param sml250biko sml250biko
     */
    public void setSml250biko(String sml250biko) {
        sml250biko__ = sml250biko;
    }

    /**
     * <p>sml250name を取得します。
     * @return sml250name
     */
    public String getSml250name() {
        return sml250name__;
    }
    /**
     * <p>sml250name をセットします。
     * @param sml250name sml250name
     */
    public void setSml250name(String sml250name) {
        sml250name__ = sml250name;
    }
//
//    /**
//     * <p>sml250userKbn を取得します。
//     * @return sml250userKbn
//     */
//    public int getSml250userKbn() {
//        return sml250userKbn__;
//    }
//    /**
//     * <p>sml250userKbn をセットします。
//     * @param sml250userKbn sml250userKbn
//     */
//    public void setSml250userKbn(int sml250userKbn) {
//        sml250userKbn__ = sml250userKbn;
//    }
//    /**
//     * <p>sml250userKbnGroup を取得します。
//     * @return sml250userKbnGroup
//     */
//    public String[] getSml250userKbnGroup() {
//        return sml250userKbnGroup__;
//    }
//    /**
//     * <p>sml250userKbnGroup をセットします。
//     * @param sml250userKbnGroup sml250userKbnGroup
//     */
//    public void setSml250userKbnGroup(String[] sml250userKbnGroup) {
//        sml250userKbnGroup__ = sml250userKbnGroup;
//    }
    /**
     * <p>sml250userKbnUser を取得します。
     * @return sml250userKbnUser
     */
    public String[] getSml250userKbnUser() {
        return sml250userKbnUser__;
    }
    /**
     * <p>sml250userKbnUser をセットします。
     * @param sml250userKbnUser sml250userKbnUser
     */
    public void setSml250userKbnUser(String[] sml250userKbnUser) {
        sml250userKbnUser__ = sml250userKbnUser;
    }

    /**
     * <p>userKbnGroupCombo を取得します。
     * @return userKbnGroupCombo
     */
    public List<LabelValueBean> getUserKbnGroupCombo() {
        return userKbnGroupCombo__;
    }
    /**
     * <p>userKbnGroupCombo をセットします。
     * @param userKbnGroupCombo userKbnGroupCombo
     */
    public void setUserKbnGroupCombo(List<LabelValueBean> userKbnGroupCombo) {
        userKbnGroupCombo__ = userKbnGroupCombo;
    }

    /**
     * <p>sml250initFlg を取得します。
     * @return sml250initFlg
     */
    public int getSml250initFlg() {
        return sml250initFlg__;
    }
    /**
     * <p>sml250initFlg をセットします。
     * @param sml250initFlg sml250initFlg
     */
    public void setSml250initFlg(int sml250initFlg) {
        sml250initFlg__ = sml250initFlg;
    }

    /**
     * <p>userKbnUserSelectCombo を取得します。
     * @return userKbnUserSelectCombo
     */
    public List<UsrLabelValueBean> getUserKbnUserSelectCombo() {
        return userKbnUserSelectCombo__;
    }
    /**
     * <p>userKbnUserSelectCombo をセットします。
     * @param userKbnUserSelectCombo userKbnUserSelectCombo
     */
    public void setUserKbnUserSelectCombo(
            List<UsrLabelValueBean> userKbnUserSelectCombo) {
        userKbnUserSelectCombo__ = userKbnUserSelectCombo;
    }
    /**
     * <p>sml250userKbnUserGroup を取得します。
     * @return sml250userKbnUserGroup
     */
    public String getSml250userKbnUserGroup() {
        return sml250userKbnUserGroup__;
    }
    /**
     * <p>sml250userKbnUserGroup をセットします。
     * @param sml250userKbnUserGroup sml250userKbnUserGroup
     */
    public void setSml250userKbnUserGroup(String sml250userKbnUserGroup) {
        sml250userKbnUserGroup__ = sml250userKbnUserGroup;
    }
    /**
     * <p>sml240group を取得します。
     * @return sml240group
     */
    public int getWml240group() {
        return sml240group__;
    }
    /**
     * <p>sml240group をセットします。
     * @param sml240group sml240group
     */
    public void setWml240group(int sml240group) {
        sml240group__ = sml240group;
    }
    /**
     * <p>sml240keyword を取得します。
     * @return sml240keyword
     */
    public String getWml240keyword() {
        return sml240keyword__;
    }
    /**
     * <p>sml240keyword をセットします。
     * @param sml240keyword sml240keyword
     */
    public void setWml240keyword(String sml240keyword) {
        sml240keyword__ = sml240keyword;
    }
    /**
     * <p>sml240order を取得します。
     * @return sml240order
     */
    public int getWml240order() {
        return sml240order__;
    }
    /**
     * <p>sml240order をセットします。
     * @param sml240order sml240order
     */
    public void setWml240order(int sml240order) {
        sml240order__ = sml240order;
    }
    /**
     * <p>sml240pageBottom を取得します。
     * @return sml240pageBottom
     */
    public int getWml240pageBottom() {
        return sml240pageBottom__;
    }
    /**
     * <p>sml240pageBottom をセットします。
     * @param sml240pageBottom sml240pageBottom
     */
    public void setWml240pageBottom(int sml240pageBottom) {
        sml240pageBottom__ = sml240pageBottom;
    }
    /**
     * <p>sml240pageTop を取得します。
     * @return sml240pageTop
     */
    public int getWml240pageTop() {
        return sml240pageTop__;
    }
    /**
     * <p>sml240pageTop をセットします。
     * @param sml240pageTop sml240pageTop
     */
    public void setWml240pageTop(int sml240pageTop) {
        sml240pageTop__ = sml240pageTop;
    }
    /**
     * <p>sml240searchFlg を取得します。
     * @return sml240searchFlg
     */
    public int getWml240searchFlg() {
        return sml240searchFlg__;
    }
    /**
     * <p>sml240searchFlg をセットします。
     * @param sml240searchFlg sml240searchFlg
     */
    public void setWml240searchFlg(int sml240searchFlg) {
        sml240searchFlg__ = sml240searchFlg;
    }
    /**
     * <p>sml240sortKey を取得します。
     * @return sml240sortKey
     */
    public int getWml240sortKey() {
        return sml240sortKey__;
    }
    /**
     * <p>sml240sortKey をセットします。
     * @param sml240sortKey sml240sortKey
     */
    public void setWml240sortKey(int sml240sortKey) {
        sml240sortKey__ = sml240sortKey;
    }
    /**
     * <p>sml240svGroup を取得します。
     * @return sml240svGroup
     */
    public int getWml240svGroup() {
        return sml240svGroup__;
    }
    /**
     * <p>sml240svGroup をセットします。
     * @param sml240svGroup sml240svGroup
     */
    public void setWml240svGroup(int sml240svGroup) {
        sml240svGroup__ = sml240svGroup;
    }
    /**
     * <p>sml240svKeyword を取得します。
     * @return sml240svKeyword
     */
    public String getWml240svKeyword() {
        return sml240svKeyword__;
    }
    /**
     * <p>sml240svKeyword をセットします。
     * @param sml240svKeyword sml240svKeyword
     */
    public void setWml240svKeyword(String sml240svKeyword) {
        sml240svKeyword__ = sml240svKeyword;
    }
    /**
     * <p>sml240svUser を取得します。
     * @return sml240svUser
     */
    public int getWml240svUser() {
        return sml240svUser__;
    }
    /**
     * <p>sml240svUser をセットします。
     * @param sml240svUser sml240svUser
     */
    public void setWml240svUser(int sml240svUser) {
        sml240svUser__ = sml240svUser;
    }
    /**
     * <p>sml240user を取得します。
     * @return sml240user
     */
    public int getWml240user() {
        return sml240user__;
    }
    /**
     * <p>sml240user をセットします。
     * @param sml240user sml240user
     */
    public void setWml240user(int sml240user) {
        sml240user__ = sml240user;
    }
    /**
     * <p>sml250elementKbn を取得します。
     * @return sml250elementKbn
     */
    public int getSml250elementKbn() {
        return sml250elementKbn__;
    }

    /**
     * <p>sml250elementKbn をセットします。
     * @param sml250elementKbn sml250elementKbn
     */
    public void setSml250elementKbn(int sml250elementKbn) {
        sml250elementKbn__ = sml250elementKbn;
    }


    /**
     * <p>sml250sendType を取得します。
     * @return sml250sendType
     */
    public int getSml250sendType() {
        return sml250sendType__;
    }

    /**
     * <p>sml250sendType をセットします。
     * @param sml250sendType sml250sendType
     */
    public void setSml250sendType(int sml250sendType) {
        sml250sendType__ = sml250sendType;
    }


    /**
     * <p>sml250theme を取得します。
     * @return sml250theme
     */
    public int getSml250theme() {
        return sml250theme__;
    }

    /**
     * <p>sml250theme をセットします。
     * @param sml250theme sml250theme
     */
    public void setSml250theme(int sml250theme) {
        sml250theme__ = sml250theme;
    }


    /**
     * <p>sml250quotes を取得します。
     * @return sml250quotes
     */
    public int getSml250quotes() {
        return sml250quotes__;
    }
    /**
     * <p>sml250quotes をセットします。
     * @param sml250quotes sml250quotes
     */
    public void setSml250quotes(int sml250quotes) {
        sml250quotes__ = sml250quotes;
    }
    /**
     * <p>sml250themeList を取得します。
     * @return sml250themeList
     */
    public List<LabelValueBean> getSml250themeList() {
        return sml250themeList__;
    }

    /**
     * <p>sml250themeList をセットします。
     * @param sml250themeList sml250themeList
     */
    public void setSml250themeList(List<LabelValueBean> sml250themeList) {
        sml250themeList__ = sml250themeList;
    }
    /**
     * <p>sml250quotesList を取得します。
     * @return sml250quotesList
     */
    public List<LabelValueBean> getSml250quotesList() {
        return sml250quotesList__;
    }
    /**
     * <p>sml250quotesList をセットします。
     * @param sml250quotesList sml250quotesList
     */
    public void setSml250quotesList(List<LabelValueBean> sml250quotesList) {
        sml250quotesList__ = sml250quotesList;
    }
    /**
     * <p>sml250AccountKbn を取得します。
     * @return sml250AccountKbn
     */
    public int getSml250AccountKbn() {
        return sml250AccountKbn__;
    }
    /**
     * <p>sml250AccountKbn をセットします。
     * @param sml250AccountKbn sml250AccountKbn
     */
    public void setSml250AccountKbn(int sml250AccountKbn) {
        sml250AccountKbn__ = sml250AccountKbn;
    }
    /**
     * <p>sml250DefActUsrSid を取得します。
     * @return sml250DefActUsrSid
     */
    public int getSml250DefActUsrSid() {
        return sml250DefActUsrSid__;
    }
    /**
     * <p>sml250DefActUsrSid をセットします。
     * @param sml250DefActUsrSid sml250DefActUsrSid
     */
    public void setSml250DefActUsrSid(int sml250DefActUsrSid) {
        sml250DefActUsrSid__ = sml250DefActUsrSid;
    }
    /**
     * <p>sml250JdelKbn を取得します。
     * @return sml250JdelKbn
     */
    public String getSml250JdelKbn() {
        return sml250JdelKbn__;
    }
    /**
     * <p>sml250JdelKbn をセットします。
     * @param sml250JdelKbn sml250JdelKbn
     */
    public void setSml250JdelKbn(String sml250JdelKbn) {
        sml250JdelKbn__ = sml250JdelKbn;
    }
    /**
     * <p>sml250SdelKbn を取得します。
     * @return sml250SdelKbn
     */
    public String getSml250SdelKbn() {
        return sml250SdelKbn__;
    }
    /**
     * <p>sml250SdelKbn をセットします。
     * @param sml250SdelKbn sml250SdelKbn
     */
    public void setSml250SdelKbn(String sml250SdelKbn) {
        sml250SdelKbn__ = sml250SdelKbn;
    }
    /**
     * <p>sml250WdelKbn を取得します。
     * @return sml250WdelKbn
     */
    public String getSml250WdelKbn() {
        return sml250WdelKbn__;
    }
    /**
     * <p>sml250WdelKbn をセットします。
     * @param sml250WdelKbn sml250WdelKbn
     */
    public void setSml250WdelKbn(String sml250WdelKbn) {
        sml250WdelKbn__ = sml250WdelKbn;
    }
    /**
     * <p>sml250DdelKbn を取得します。
     * @return sml250DdelKbn
     */
    public String getSml250DdelKbn() {
        return sml250DdelKbn__;
    }
    /**
     * <p>sml250DdelKbn をセットします。
     * @param sml250DdelKbn sml250DdelKbn
     */
    public void setSml250DdelKbn(String sml250DdelKbn) {
        sml250DdelKbn__ = sml250DdelKbn;
    }
    /**
     * <p>sml250YearLabelList を取得します。
     * @return sml250YearLabelList
     */
    public ArrayList<LabelValueBean> getSml250YearLabelList() {
        return sml250YearLabelList__;
    }
    /**
     * <p>sml250YearLabelList をセットします。
     * @param sml250YearLabelList sml250YearLabelList
     */
    public void setSml250YearLabelList(ArrayList<LabelValueBean> sml250YearLabelList) {
        sml250YearLabelList__ = sml250YearLabelList;
    }
    /**
     * <p>sml250MonthLabelList を取得します。
     * @return sml250MonthLabelList
     */
    public ArrayList<LabelValueBean> getSml250MonthLabelList() {
        return sml250MonthLabelList__;
    }
    /**
     * <p>sml250MonthLabelList をセットします。
     * @param sml250MonthLabelList sml250MonthLabelList
     */
    public void setSml250MonthLabelList(
            ArrayList<LabelValueBean> sml250MonthLabelList) {
        sml250MonthLabelList__ = sml250MonthLabelList;
    }
    /**
     * <p>sml250JYear を取得します。
     * @return sml250JYear
     */
    public String getSml250JYear() {
        return sml250JYear__;
    }
    /**
     * <p>sml250JYear をセットします。
     * @param sml250jYear sml250JYear
     */
    public void setSml250JYear(String sml250jYear) {
        sml250JYear__ = sml250jYear;
    }
    /**
     * <p>sml250JMonth を取得します。
     * @return sml250JMonth
     */
    public String getSml250JMonth() {
        return sml250JMonth__;
    }
    /**
     * <p>sml250JMonth をセットします。
     * @param sml250jMonth sml250JMonth
     */
    public void setSml250JMonth(String sml250jMonth) {
        sml250JMonth__ = sml250jMonth;
    }
    /**
     * <p>sml250SYear を取得します。
     * @return sml250SYear
     */
    public String getSml250SYear() {
        return sml250SYear__;
    }
    /**
     * <p>sml250SYear をセットします。
     * @param sml250sYear sml250SYear
     */
    public void setSml250SYear(String sml250sYear) {
        sml250SYear__ = sml250sYear;
    }
    /**
     * <p>sml250SMonth を取得します。
     * @return sml250SMonth
     */
    public String getSml250SMonth() {
        return sml250SMonth__;
    }
    /**
     * <p>sml250SMonth をセットします。
     * @param sml250sMonth sml250SMonth
     */
    public void setSml250SMonth(String sml250sMonth) {
        sml250SMonth__ = sml250sMonth;
    }
    /**
     * <p>sml250WYear を取得します。
     * @return sml250WYear
     */
    public String getSml250WYear() {
        return sml250WYear__;
    }
    /**
     * <p>sml250WYear をセットします。
     * @param sml250wYear sml250WYear
     */
    public void setSml250WYear(String sml250wYear) {
        sml250WYear__ = sml250wYear;
    }
    /**
     * <p>sml250WMonth を取得します。
     * @return sml250WMonth
     */
    public String getSml250WMonth() {
        return sml250WMonth__;
    }
    /**
     * <p>sml250WMonth をセットします。
     * @param sml250wMonth sml250WMonth
     */
    public void setSml250WMonth(String sml250wMonth) {
        sml250WMonth__ = sml250wMonth;
    }
    /**
     * <p>sml250DYear を取得します。
     * @return sml250DYear
     */
    public String getSml250DYear() {
        return sml250DYear__;
    }
    /**
     * <p>sml250DYear をセットします。
     * @param sml250dYear sml250DYear
     */
    public void setSml250DYear(String sml250dYear) {
        sml250DYear__ = sml250dYear;
    }
    /**
     * <p>sml250DMonth を取得します。
     * @return sml250DMonth
     */
    public String getSml250DMonth() {
        return sml250DMonth__;
    }
    /**
     * <p>sml250DMonth をセットします。
     * @param sml250dMonth sml250DMonth
     */
    public void setSml250DMonth(String sml250dMonth) {
        sml250DMonth__ = sml250dMonth;
    }
    /**
     * <p>sml250autoDelKbn を取得します。
     * @return sml250autoDelKbn
     */
    public int getSml250autoDelKbn() {
        return sml250autoDelKbn__;
    }
    /**
     * <p>sml250autoDelKbn をセットします。
     * @param sml250autoDelKbn sml250autoDelKbn
     */
    public void setSml250autoDelKbn(int sml250autoDelKbn) {
        sml250autoDelKbn__ = sml250autoDelKbn;
    }
    /**
     * <p>sml250ObjKbn を取得します。
     * @return sml250ObjKbn
     */
    public int getSml250ObjKbn() {
        return sml250ObjKbn__;
    }
    /**
     * <p>sml250ObjKbn をセットします。
     * @param sml250ObjKbn sml250ObjKbn
     */
    public void setSml250ObjKbn(int sml250ObjKbn) {
        sml250ObjKbn__ = sml250ObjKbn;
    }
    /**
     * <p>sml250PassKbn を取得します。
     * @return sml250PassKbn
     */
    public int getSml250PassKbn() {
        return sml250PassKbn__;
    }
    /**
     * <p>sml250PassKbn をセットします。
     * @param sml250PassKbn sml250PassKbn
     */
    public void setSml250PassKbn(int sml250PassKbn) {
        sml250PassKbn__ = sml250PassKbn;
    }
    /**
     * <p>sml250groupSid を取得します。
     * @return sml250groupSid
     */
    public String getSml250groupSid() {
        return sml250groupSid__;
    }
    /**
     * <p>sml250groupSid をセットします。
     * @param sml250groupSid sml250groupSid
     */
    public void setSml250groupSid(String sml250groupSid) {
        sml250groupSid__ = sml250groupSid;
    }
    /**
     * <p>sml250userSid を取得します。
     * @return sml250userSid
     */
    public String[] getSml250userSid() {
        return sml250userSid__;
    }
    /**
     * <p>sml250userSid をセットします。
     * @param sml250userSid sml250userSid
     */
    public void setSml250userSid(String[] sml250userSid) {
        sml250userSid__ = sml250userSid;
    }
    /**
     * <p>sml250MailFw を取得します。
     * @return sml250MailFw
     */
    public String getSml250MailFw() {
        return sml250MailFw__;
    }
    /**
     * <p>sml250MailFw をセットします。
     * @param sml250MailFw sml250MailFw
     */
    public void setSml250MailFw(String sml250MailFw) {
        sml250MailFw__ = sml250MailFw;
    }
    /**
     * <p>sml250MailDf を取得します。
     * @return sml250MailDf
     */
    public String getSml250MailDf() {
        return sml250MailDf__;
    }
    /**
     * <p>sml250MailDf をセットします。
     * @param sml250MailDf sml250MailDf
     */
    public void setSml250MailDf(String sml250MailDf) {
        sml250MailDf__ = sml250MailDf;
    }
    /**
     * <p>sml250MailDfSelected を取得します。
     * @return sml250MailDfSelected
     */
    public String getSml250MailDfSelected() {
        return sml250MailDfSelected__;
    }
    /**
     * <p>sml250MailDfSelected をセットします。
     * @param sml250MailDfSelected sml250MailDfSelected
     */
    public void setSml250MailDfSelected(String sml250MailDfSelected) {
        sml250MailDfSelected__ = sml250MailDfSelected;
    }
    /**
     * <p>sml250SmailOp を取得します。
     * @return sml250SmailOp
     */
    public String getSml250SmailOp() {
        return sml250SmailOp__;
    }
    /**
     * <p>sml250SmailOp をセットします。
     * @param sml250SmailOp sml250SmailOp
     */
    public void setSml250SmailOp(String sml250SmailOp) {
        sml250SmailOp__ = sml250SmailOp;
    }
    /**
     * <p>sml250HuriwakeKbn を取得します。
     * @return sml250HuriwakeKbn
     */
    public String getSml250HuriwakeKbn() {
        return sml250HuriwakeKbn__;
    }
    /**
     * <p>sml250HuriwakeKbn をセットします。
     * @param sml250HuriwakeKbn sml250HuriwakeKbn
     */
    public void setSml250HuriwakeKbn(String sml250HuriwakeKbn) {
        sml250HuriwakeKbn__ = sml250HuriwakeKbn;
    }
    /**
     * <p>sml250Zmail1 を取得します。
     * @return sml250Zmail1
     */
    public String getSml250Zmail1() {
        return sml250Zmail1__;
    }
    /**
     * <p>sml250Zmail1 をセットします。
     * @param sml250Zmail1 sml250Zmail1
     */
    public void setSml250Zmail1(String sml250Zmail1) {
        sml250Zmail1__ = sml250Zmail1;
    }
    /**
     * <p>sml250Zmail2 を取得します。
     * @return sml250Zmail2
     */
    public String getSml250Zmail2() {
        return sml250Zmail2__;
    }
    /**
     * <p>sml250Zmail2 をセットします。
     * @param sml250Zmail2 sml250Zmail2
     */
    public void setSml250Zmail2(String sml250Zmail2) {
        sml250Zmail2__ = sml250Zmail2;
    }
    /**
     * <p>sml250Zmail3 を取得します。
     * @return sml250Zmail3
     */
    public String getSml250Zmail3() {
        return sml250Zmail3__;
    }
    /**
     * <p>sml250Zmail3 をセットします。
     * @param sml250Zmail3 sml250Zmail3
     */
    public void setSml250Zmail3(String sml250Zmail3) {
        sml250Zmail3__ = sml250Zmail3;
    }
    /**
     * <p>sml250Zmail1Selected を取得します。
     * @return sml250Zmail1Selected
     */
    public String getSml250Zmail1Selected() {
        return sml250Zmail1Selected__;
    }
    /**
     * <p>sml250Zmail1Selected をセットします。
     * @param sml250Zmail1Selected sml250Zmail1Selected
     */
    public void setSml250Zmail1Selected(String sml250Zmail1Selected) {
        sml250Zmail1Selected__ = sml250Zmail1Selected;
    }
    /**
     * <p>sml250Zmail2Selected を取得します。
     * @return sml250Zmail2Selected
     */
    public String getSml250Zmail2Selected() {
        return sml250Zmail2Selected__;
    }
    /**
     * <p>sml250Zmail2Selected をセットします。
     * @param sml250Zmail2Selected sml250Zmail2Selected
     */
    public void setSml250Zmail2Selected(String sml250Zmail2Selected) {
        sml250Zmail2Selected__ = sml250Zmail2Selected;
    }
    /**
     * <p>sml250Zmail3Selected を取得します。
     * @return sml250Zmail3Selected
     */
    public String getSml250Zmail3Selected() {
        return sml250Zmail3Selected__;
    }
    /**
     * <p>sml250Zmail3Selected をセットします。
     * @param sml250Zmail3Selected sml250Zmail3Selected
     */
    public void setSml250Zmail3Selected(String sml250Zmail3Selected) {
        sml250Zmail3Selected__ = sml250Zmail3Selected;
    }
    /**
     * <p>sml250MailList を取得します。
     * @return sml250MailList
     */
    public List<LabelValueBean> getSml250MailList() {
        return sml250MailList__;
    }
    /**
     * <p>sml250MailList をセットします。
     * @param sml250MailList sml250MailList
     */
    public void setSml250MailList(List<LabelValueBean> sml250MailList) {
        sml250MailList__ = sml250MailList;
    }
    /**
     * <p>sml250ZaisekiPlugin を取得します。
     * @return sml250ZaisekiPlugin
     */
    public int getSml250ZaisekiPlugin() {
        return sml250ZaisekiPlugin__;
    }
    /**
     * <p>sml250ZaisekiPlugin をセットします。
     * @param sml250ZaisekiPlugin sml250ZaisekiPlugin
     */
    public void setSml250ZaisekiPlugin(int sml250ZaisekiPlugin) {
        sml250ZaisekiPlugin__ = sml250ZaisekiPlugin;
    }
    /**
     * <p>sml250tensoKbn を取得します。
     * @return sml250tensoKbn
     */
    public int getSml250tensoKbn() {
        return sml250tensoKbn__;
    }
    /**
     * <p>sml250tensoKbn をセットします。
     * @param sml250tensoKbn sml250tensoKbn
     */
    public void setSml250tensoKbn(int sml250tensoKbn) {
        sml250tensoKbn__ = sml250tensoKbn;
    }
    /**
     * <p>sml250tensoSetKbn を取得します。
     * @return sml250tensoSetKbn
     */
    public int getSml250tensoSetKbn() {
        return sml250tensoSetKbn__;
    }
    /**
     * <p>sml250tensoSetKbn をセットします。
     * @param sml250tensoSetKbn sml250tensoSetKbn
     */
    public void setSml250tensoSetKbn(int sml250tensoSetKbn) {
        sml250tensoSetKbn__ = sml250tensoSetKbn;
    }
    /**
     * <p>sml250SelTab を取得します。
     * @return sml250SelTab
     */
    public int getSml250SelTab() {
        return sml250SelTab__;
    }
    /**
     * <p>sml250SelTab をセットします。
     * @param sml250SelTab sml250SelTab
     */
    public void setSml250SelTab(int sml250SelTab) {
        sml250SelTab__ = sml250SelTab;
    }
    /**
     * <p>sml250CanDelFlg を取得します。
     * @return sml250CanDelFlg
     */
    public int getSml250CanDelFlg() {
        return sml250CanDelFlg__;
    }
    /**
     * <p>sml250CanDelFlg をセットします。
     * @param sml250CanDelFlg sml250CanDelFlg
     */
    public void setSml250CanDelFlg(int sml250CanDelFlg) {
        sml250CanDelFlg__ = sml250CanDelFlg;
    }
    /**
     * <p>sml250acntUserFlg を取得します。
     * @return sml250acntUserFlg
     */
    public boolean isSml250acntUserFlg() {
        return sml250acntUserFlg__;
    }
    /**
     * <p>sml250acntUserFlg をセットします。
     * @param sml250acntUserFlg sml250acntUserFlg
     */
    public void setSml250acntUserFlg(boolean sml250acntUserFlg) {
        sml250acntUserFlg__ = sml250acntUserFlg;
    }
    /**
     * <p>sml250AutoDestToUsrSid を取得します。
     * @return sml250AutoDestToUsrSid
     */
    public String[] getSml250AutoDestToUsrSid() {
        return sml250AutoDestToUsrSid__;
    }
    /**
     * <p>sml250AutoDestToUsrSid をセットします。
     * @param sml250AutoDestToUsrSid sml250AutoDestToUsrSid
     */
    public void setSml250AutoDestToUsrSid(String[] sml250AutoDestToUsrSid) {
        sml250AutoDestToUsrSid__ = sml250AutoDestToUsrSid;
    }
    /**
     * <p>sml250AutoDestCcUsrSid を取得します。
     * @return sml250AutoDestCcUsrSid
     */
    public String[] getSml250AutoDestCcUsrSid() {
        return sml250AutoDestCcUsrSid__;
    }
    /**
     * <p>sml250AutoDestCcUsrSid をセットします。
     * @param sml250AutoDestCcUsrSid sml250AutoDestCcUsrSid
     */
    public void setSml250AutoDestCcUsrSid(String[] sml250AutoDestCcUsrSid) {
        sml250AutoDestCcUsrSid__ = sml250AutoDestCcUsrSid;
    }
    /**
     * <p>sml250AutoDestBccUsrSid を取得します。
     * @return sml250AutoDestBccUsrSid
     */
    public String[] getSml250AutoDestBccUsrSid() {
        return sml250AutoDestBccUsrSid__;
    }
    /**
     * <p>sml250AutoDestBccUsrSid をセットします。
     * @param sml250AutoDestBccUsrSid sml250AutoDestBccUsrSid
     */
    public void setSml250AutoDestBccUsrSid(String[] sml250AutoDestBccUsrSid) {
        sml250AutoDestBccUsrSid__ = sml250AutoDestBccUsrSid;
    }

    /**
     * <p>sml250AutoDestToLabelList を取得します。
     * @return sml250AutoDestToLabelList
     */
    public List<UsrLabelValueBean> getSml250AutoDestToLabelList() {
        return sml250AutoDestToLabelList__;
    }


    /**
     * <p>sml250AutoDestToLabelList をセットします。
     * @param sml250AutoDestToLabelList sml250AutoDestToLabelList
     */
    public void setSml250AutoDestToLabelList(
            List<UsrLabelValueBean> sml250AutoDestToLabelList) {
        sml250AutoDestToLabelList__ = sml250AutoDestToLabelList;
    }


    /**
     * <p>sml250AutoDestCcLabelList を取得します。
     * @return sml250AutoDestCcLabelList
     */
    public List<UsrLabelValueBean> getSml250AutoDestCcLabelList() {
        return sml250AutoDestCcLabelList__;
    }


    /**
     * <p>sml250AutoDestCcLabelList をセットします。
     * @param sml250AutoDestCcLabelList sml250AutoDestCcLabelList
     */
    public void setSml250AutoDestCcLabelList(
            List<UsrLabelValueBean> sml250AutoDestCcLabelList) {
        sml250AutoDestCcLabelList__ = sml250AutoDestCcLabelList;
    }


    /**
     * <p>sml250AutoDestBccLabelList を取得します。
     * @return sml250AutoDestBccLabelList
     */
    public List<UsrLabelValueBean> getSml250AutoDestBccLabelList() {
        return sml250AutoDestBccLabelList__;
    }

    /**
     * <p>sml250AutoDestBccLabelList をセットします。
     * @param sml250AutoDestBccLabelList sml250AutoDestBccLabelList
     */
    public void setSml250AutoDestBccLabelList(
            List<UsrLabelValueBean> sml250AutoDestBccLabelList) {
        sml250AutoDestBccLabelList__ = sml250AutoDestBccLabelList;
    }
    /**
     * <p>sml250userKbnUserUI を取得します。
     * @return sml250userKbnUserUI
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250userKbnUserUI__
     */
    public UserGroupSelector getSml250userKbnUserUI() {
        return sml250userKbnUserUI__;
    }
    /**
     * <p>sml250userKbnUserUI をセットします。
     * @param sml250userKbnUserUI sml250userKbnUserUI
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250userKbnUserUI__
     */
    public void setSml250userKbnUserUI(UserGroupSelector sml250userKbnUserUI) {
        sml250userKbnUserUI__ = sml250userKbnUserUI;
    }
    /**
     * <p>sml250AutoDestTmpUserSid を取得します。
     * @return sml250AutoDestTmpUserSid
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250AutoDestTmpUserSid__
     */
    public String[] getSml250AutoDestTmpUserSid() {
        return sml250AutoDestTmpUserSid__;
    }
    /**
     * <p>sml250AutoDestTmpUserSid をセットします。
     * @param sml250AutoDestTmpUserSid sml250AutoDestTmpUserSid
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250AutoDestTmpUserSid__
     */
    public void setSml250AutoDestTmpUserSid(String[] sml250AutoDestTmpUserSid) {
        sml250AutoDestTmpUserSid__ = sml250AutoDestTmpUserSid;
    }
    /**
     * <p>sml250AutoDestGroupSel を取得します。
     * @return sml250AutoDestGroupSel
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250AutoDestGroupSel__
     */
    public String getSml250AutoDestGroupSel() {
        return sml250AutoDestGroupSel__;
    }
    /**
     * <p>sml250AutoDestGroupSel をセットします。
     * @param sml250AutoDestGroupSel sml250AutoDestGroupSel
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250AutoDestGroupSel__
     */
    public void setSml250AutoDestGroupSel(String sml250AutoDestGroupSel) {
        sml250AutoDestGroupSel__ = sml250AutoDestGroupSel;
    }
    /**
     * <p>sml250tensoTargetUI を取得します。
     * @return sml250tensoTargetUI
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250tensoTargetUI__
     */
    public Sml250TensoTargetSelector getSml250tensoTargetUI() {
        return sml250tensoTargetUI__;
    }
    /**
     * <p>sml250tensoTargetUI をセットします。
     * @param sml250tensoTargetUI sml250tensoTargetUI
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250tensoTargetUI__
     */
    public void setSml250tensoTargetUI(Sml250TensoTargetSelector sml250tensoTargetUI) {
        sml250tensoTargetUI__ = sml250tensoTargetUI;
    }
    /**
     * <p>sml250AtesakiUI を取得します。
     * @return sml250AtesakiUI
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250AtesakiUI__
     */
    public AccountSelector getSml250AtesakiUI() {
        return sml250AtesakiUI__;
    }
    /**
     * <p>sml250AtesakiUI をセットします。
     * @param sml250AtesakiUI sml250AtesakiUI
     * @see jp.groupsession.v2.sml.sml250.Sml250ParamModel#sml250AtesakiUI__
     */
    public void setSml250AtesakiUI(AccountSelector sml250AtesakiUI) {
        sml250AtesakiUI__ = sml250AtesakiUI;
    }

}