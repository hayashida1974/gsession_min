package jp.groupsession.v2.sml.sml020;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;

/**
 * <br>[機  能] ショートメール作成画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml020ParamModel extends Sml010ParamModel {
    /** 入力チェックモード 送信時 */
    public static final int VALIDATE_MODE_SOUSIN = 1;
    /** 入力チェックモード 草稿保存時 */
    public static final int VALIDATE_MODE_SAVE = 2;
//    /** 処理モード */
//    private String sml020ProcMode__ = GSConstSmail.MSG_CREATE_MODE_NEW;
    /** ユーザSIDリスト */
    private String[] cmn120userSid__;
    /** 宛先SIDリスト */
    private String[] sml020userSid__;
    /** CCSIDリスト */
    private String[] sml020userSidCc__;
    /** BCCSIDリスト */
    private String[] sml020userSidBcc__;
    /** 宛先一覧 */
    private SmailModel sml020Atesaki__;
    /** CC一覧 */
    private SmailModel sml020AtesakiCc__;
    /** BCC一覧 */
    private SmailModel sml020AtesakiBcc__;
    /** 件名 */
    private String sml020Title__;
    /** マーク */
    private int sml020Mark__;
    /** 本文 */
    private String sml020Body__;
    /** 本文  html */
    private String sml020BodyHtml__;

    /** メール形式 */
    private int sml020MailType__ = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;

    /** グループ一覧 */
    private List<CmnLabelValueModel> sml020groupList__ = null;
    /** 追加用ユーザ一覧 */
    private List<CmnUsrmInfModel> sml020userList__ = null;

    //スケジュール画面パラメータ
    /** 表示開始日付 */
    private String sch010DspDate__;
    /** 表示グループSID */
    private String sch010DspGpSid__;
    /** 表示開始時間(HH) */
    private String sch030FromHour__;

    //日報画面パラメータ
    /** 表示開始日付 */
    private String ntp010DspDate__;
    /** 表示グループSID */
    private String ntp010DspGpSid__;
    /** 表示開始時間(HH) */
    private String ntp030FromHour__;

    /** 処理モード */
    private String sml050ProcMode__;
    /** ひな形オーダーキー */
    private int sml050Order_key__;
    /** ひな形ソートキー */
    private int sml050Sort_key__;
    /** ひな形ページ数 */
    private int sml050PageNum__;

    /** 添付ファイル(コンボで選択中) */
    private String[] sml020selectFiles__ = null;
    /** ファイルコンボ */
    private List<LabelValueBean> sml020FileLabelList__ = null;
    /** プラグインID */
    private String sml020pluginId__ = GSConstSmail.PLUGIN_ID_SMAIL;

    /** 検索結果(ユーザリスト) */
    List<SmlHinaModel> sml020HinaList__ = null;
    /** 検索結果(ユーザリスト) */
    List<SmlHinaModel> sml020HinaListKjn__ = null;
    /** 選択されたひな形 */
    private int sml020SelectHinaId__ = 0;

    /** 全返信処理フラグ 0:全返信以外 1:全返信 */
    private int sml020AllUserReFlg__ = 0;

    /** 送信区分 0:宛先 1:CC 2:BCC */
    private int sml020SendKbn__ = GSConstSmail.SML_SEND_KBN_ATESAKI;

    /** 削除ユーザSID */
    private int sml020DelUsrSid__;
    /** 削除ユーザSID */
    private int sml020DelSendKbn__ = GSConstSmail.SML_SEND_KBN_ATESAKI;
    /** 宛先が強制除去された場合のメッセージ*/
    private String sml020AtesakiDeletedMessage__ = null;
    
    /** 本文ファイルの一時保存ディレクトリID */
    private String sml020TempSaveId__ = null;


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

//    /**
//     * <p>sml020ProcMode を取得します。
//     * @return sml020ProcMode
//     */
//    public String getSml020ProcMode() {
//        return sml020ProcMode__;
//    }
//    /**
//     * <p>sml020ProcMode をセットします。
//     * @param sml020ProcMode sml020ProcMode
//     */
//    public void setSml020ProcMode(String sml020ProcMode) {
//        sml020ProcMode__ = sml020ProcMode;
//    }
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
     * <p>sml020userSid を取得します。
     * @return sml020userSid
     */
    public String[] getSml020userSid() {
        return sml020userSid__;
    }
    /**
     * <p>sml020userSid をセットします。
     * @param sml020userSid sml020userSid
     */
    public void setSml020userSid(String[] sml020userSid) {
        sml020userSid__ = sml020userSid;
    }
    /**
     * <p>sml020userSidCc を取得します。
     * @return sml020userSidCc
     */
    public String[] getSml020userSidCc() {
        return sml020userSidCc__;
    }
    /**
     * <p>sml020userSidCc をセットします。
     * @param sml020userSidCc sml020userSidCc
     */
    public void setSml020userSidCc(String[] sml020userSidCc) {
        sml020userSidCc__ = sml020userSidCc;
    }
    /**
     * <p>sml020userSidBcc を取得します。
     * @return sml020userSidBcc
     */
    public String[] getSml020userSidBcc() {
        return sml020userSidBcc__;
    }
    /**
     * <p>sml020userSidBcc をセットします。
     * @param sml020userSidBcc sml020userSidBcc
     */
    public void setSml020userSidBcc(String[] sml020userSidBcc) {
        sml020userSidBcc__ = sml020userSidBcc;
    }
    /**
     * <p>sml020Atesaki を取得します。
     * @return sml020Atesaki
     */
    public SmailModel getSml020Atesaki() {
        return sml020Atesaki__;
    }
    /**
     * <p>sml020Atesaki をセットします。
     * @param sml020Atesaki sml020Atesaki
     */
    public void setSml020Atesaki(SmailModel sml020Atesaki) {
        sml020Atesaki__ = sml020Atesaki;
    }
    /**
     * <p>sml020AtesakiCc を取得します。
     * @return sml020AtesakiCc
     */
    public SmailModel getSml020AtesakiCc() {
        return sml020AtesakiCc__;
    }
    /**
     * <p>sml020AtesakiCc をセットします。
     * @param sml020AtesakiCc sml020AtesakiCc
     */
    public void setSml020AtesakiCc(SmailModel sml020AtesakiCc) {
        sml020AtesakiCc__ = sml020AtesakiCc;
    }
    /**
     * <p>sml020AtesakiBcc を取得します。
     * @return sml020AtesakiBcc
     */
    public SmailModel getSml020AtesakiBcc() {
        return sml020AtesakiBcc__;
    }
    /**
     * <p>sml020AtesakiBcc をセットします。
     * @param sml020AtesakiBcc sml020AtesakiBcc
     */
    public void setSml020AtesakiBcc(SmailModel sml020AtesakiBcc) {
        sml020AtesakiBcc__ = sml020AtesakiBcc;
    }
    /**
     * <p>sml020Title を取得します。
     * @return sml020Title
     */
    public String getSml020Title() {
        return sml020Title__;
    }
    /**
     * <p>sml020Title をセットします。
     * @param sml020Title sml020Title
     */
    public void setSml020Title(String sml020Title) {
        sml020Title__ = sml020Title;
    }
    /**
     * <p>sml020Mark を取得します。
     * @return sml020Mark
     */
    public int getSml020Mark() {
        return sml020Mark__;
    }
    /**
     * <p>sml020Mark をセットします。
     * @param sml020Mark sml020Mark
     */
    public void setSml020Mark(int sml020Mark) {
        sml020Mark__ = sml020Mark;
    }
    /**
     * <p>sml020Body を取得します。
     * @return sml020Body
     */
    public String getSml020Body() {
        return sml020Body__;
    }
    /**
     * <p>sml020Body をセットします。
     * @param sml020Body sml020Body
     */
    public void setSml020Body(String sml020Body) {
        sml020Body__ = sml020Body;
    }
    /**
     * <p>sml020groupList を取得します。
     * @return sml020groupList
     */
    public List<CmnLabelValueModel> getSml020groupList() {
        return sml020groupList__;
    }
    /**
     * <p>sml020groupList をセットします。
     * @param sml020groupList sml020groupList
     */
    public void setSml020groupList(List<CmnLabelValueModel> sml020groupList) {
        sml020groupList__ = sml020groupList;
    }
    /**
     * <p>sml020userList を取得します。
     * @return sml020userList
     */
    public List<CmnUsrmInfModel> getSml020userList() {
        return sml020userList__;
    }
    /**
     * <p>sml020userList をセットします。
     * @param sml020userList sml020userList
     */
    public void setSml020userList(List<CmnUsrmInfModel> sml020userList) {
        sml020userList__ = sml020userList;
    }
    /**
     * <p>sch010DspDate を取得します。
     * @return sch010DspDate
     */
    public String getSch010DspDate() {
        return sch010DspDate__;
    }
    /**
     * <p>sch010DspDate をセットします。
     * @param sch010DspDate sch010DspDate
     */
    public void setSch010DspDate(String sch010DspDate) {
        sch010DspDate__ = sch010DspDate;
    }
    /**
     * <p>sch010DspGpSid を取得します。
     * @return sch010DspGpSid
     */
    public String getSch010DspGpSid() {
        return sch010DspGpSid__;
    }
    /**
     * <p>sch010DspGpSid をセットします。
     * @param sch010DspGpSid sch010DspGpSid
     */
    public void setSch010DspGpSid(String sch010DspGpSid) {
        sch010DspGpSid__ = sch010DspGpSid;
    }
    /**
     * <p>sch030FromHour を取得します。
     * @return sch030FromHour
     */
    public String getSch030FromHour() {
        return sch030FromHour__;
    }
    /**
     * <p>sch030FromHour をセットします。
     * @param sch030FromHour sch030FromHour
     */
    public void setSch030FromHour(String sch030FromHour) {
        sch030FromHour__ = sch030FromHour;
    }
    /**
     * <p>ntp010DspDate を取得します。
     * @return ntp010DspDate
     */
    public String getNtp010DspDate() {
        return ntp010DspDate__;
    }
    /**
     * <p>ntp010DspDate をセットします。
     * @param ntp010DspDate ntp010DspDate
     */
    public void setNtp010DspDate(String ntp010DspDate) {
        ntp010DspDate__ = ntp010DspDate;
    }
    /**
     * <p>ntp010DspGpSid を取得します。
     * @return ntp010DspGpSid
     */
    public String getNtp010DspGpSid() {
        return ntp010DspGpSid__;
    }
    /**
     * <p>ntp010DspGpSid をセットします。
     * @param ntp010DspGpSid ntp010DspGpSid
     */
    public void setNtp010DspGpSid(String ntp010DspGpSid) {
        ntp010DspGpSid__ = ntp010DspGpSid;
    }
    /**
     * <p>ntp030FromHour を取得します。
     * @return ntp030FromHour
     */
    public String getNtp030FromHour() {
        return ntp030FromHour__;
    }
    /**
     * <p>ntp030FromHour をセットします。
     * @param ntp030FromHour ntp030FromHour
     */
    public void setNtp030FromHour(String ntp030FromHour) {
        ntp030FromHour__ = ntp030FromHour;
    }
    /**
     * <p>sml050ProcMode を取得します。
     * @return sml050ProcMode
     */
    public String getSml050ProcMode() {
        return sml050ProcMode__;
    }
    /**
     * <p>sml050ProcMode をセットします。
     * @param sml050ProcMode sml050ProcMode
     */
    public void setSml050ProcMode(String sml050ProcMode) {
        sml050ProcMode__ = sml050ProcMode;
    }
    /**
     * <p>sml050Order_key を取得します。
     * @return sml050Order_key
     */
    public int getSml050Order_key() {
        return sml050Order_key__;
    }
    /**
     * <p>sml050Order_key をセットします。
     * @param sml050Orderkey sml050Order_key
     */
    public void setSml050Order_key(int sml050Orderkey) {
        sml050Order_key__ = sml050Orderkey;
    }
    /**
     * <p>sml050Sort_key を取得します。
     * @return sml050Sort_key
     */
    public int getSml050Sort_key() {
        return sml050Sort_key__;
    }
    /**
     * <p>sml050Sort_key をセットします。
     * @param sml050Sortkey sml050Sort_key
     */
    public void setSml050Sort_key(int sml050Sortkey) {
        sml050Sort_key__ = sml050Sortkey;
    }
    /**
     * <p>sml050PageNum を取得します。
     * @return sml050PageNum
     */
    public int getSml050PageNum() {
        return sml050PageNum__;
    }
    /**
     * <p>sml050PageNum をセットします。
     * @param sml050PageNum sml050PageNum
     */
    public void setSml050PageNum(int sml050PageNum) {
        sml050PageNum__ = sml050PageNum;
    }
    /**
     * <p>sml020selectFiles を取得します。
     * @return sml020selectFiles
     */
    public String[] getSml020selectFiles() {
        return sml020selectFiles__;
    }
    /**
     * <p>sml020selectFiles をセットします。
     * @param sml020selectFiles sml020selectFiles
     */
    public void setSml020selectFiles(String[] sml020selectFiles) {
        sml020selectFiles__ = sml020selectFiles;
    }
    /**
     * <p>sml020FileLabelList を取得します。
     * @return sml020FileLabelList
     */
    public List<LabelValueBean> getSml020FileLabelList() {
        return sml020FileLabelList__;
    }
    /**
     * <p>sml020FileLabelList をセットします。
     * @param sml020FileLabelList sml020FileLabelList
     */
    public void setSml020FileLabelList(List<LabelValueBean> sml020FileLabelList) {
        sml020FileLabelList__ = sml020FileLabelList;
    }
    /**
     * <p>sml020pluginId を取得します。
     * @return sml020pluginId
     */
    public String getSml020pluginId() {
        return sml020pluginId__;
    }
    /**
     * <p>sml020pluginId をセットします。
     * @param sml020pluginId sml020pluginId
     */
    public void setSml020pluginId(String sml020pluginId) {
        sml020pluginId__ = sml020pluginId;
    }
    /**
     * <p>sml020HinaList を取得します。
     * @return sml020HinaList
     */
    public List<SmlHinaModel> getSml020HinaList() {
        return sml020HinaList__;
    }
    /**
     * <p>sml020HinaList をセットします。
     * @param sml020HinaList sml020HinaList
     */
    public void setSml020HinaList(List<SmlHinaModel> sml020HinaList) {
        sml020HinaList__ = sml020HinaList;
    }
    /**
     * <p>sml020HinaListKjn を取得します。
     * @return sml020HinaListKjn
     */
    public List<SmlHinaModel> getSml020HinaListKjn() {
        return sml020HinaListKjn__;
    }
    /**
     * <p>sml020HinaListKjn をセットします。
     * @param sml020HinaListKjn sml020HinaListKjn
     */
    public void setSml020HinaListKjn(List<SmlHinaModel> sml020HinaListKjn) {
        sml020HinaListKjn__ = sml020HinaListKjn;
    }
    /**
     * <p>sml020SelectHinaId を取得します。
     * @return sml020SelectHinaId
     */
    public int getSml020SelectHinaId() {
        return sml020SelectHinaId__;
    }
    /**
     * <p>sml020SelectHinaId をセットします。
     * @param sml020SelectHinaId sml020SelectHinaId
     */
    public void setSml020SelectHinaId(int sml020SelectHinaId) {
        sml020SelectHinaId__ = sml020SelectHinaId;
    }
    /**
     * <p>sml020AllUserReFlg を取得します。
     * @return sml020AllUserReFlg
     */
    public int getSml020AllUserReFlg() {
        return sml020AllUserReFlg__;
    }
    /**
     * <p>sml020AllUserReFlg をセットします。
     * @param sml020AllUserReFlg sml020AllUserReFlg
     */
    public void setSml020AllUserReFlg(int sml020AllUserReFlg) {
        sml020AllUserReFlg__ = sml020AllUserReFlg;
    }
    /**
     * <p>sml020SendKbn を取得します。
     * @return sml020SendKbn
     */
    public int getSml020SendKbn() {
        return sml020SendKbn__;
    }
    /**
     * <p>sml020SendKbn をセットします。
     * @param sml020SendKbn sml020SendKbn
     */
    public void setSml020SendKbn(int sml020SendKbn) {
        sml020SendKbn__ = sml020SendKbn;
    }
    /**
     * <p>sml020DelUsrSid を取得します。
     * @return sml020DelUsrSid
     */
    public int getSml020DelUsrSid() {
        return sml020DelUsrSid__;
    }
    /**
     * <p>sml020DelUsrSid をセットします。
     * @param sml020DelUsrSid sml020DelUsrSid
     */
    public void setSml020DelUsrSid(int sml020DelUsrSid) {
        sml020DelUsrSid__ = sml020DelUsrSid;
    }
    /**
     * <p>sml020DelSendKbn を取得します。
     * @return sml020DelSendKbn
     */
    public int getSml020DelSendKbn() {
        return sml020DelSendKbn__;
    }
    /**
     * <p>sml020DelSendKbn をセットします。
     * @param sml020DelSendKbn sml020DelSendKbn
     */
    public void setSml020DelSendKbn(int sml020DelSendKbn) {
        sml020DelSendKbn__ = sml020DelSendKbn;
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
     * <p>sml090SvSearchSortKey2 をセットします。
     * @param sml090SvSearchSortKey2 sml090SvSearchSortKey2
     */
    public void setSml090SvSearchSortKey2(String sml090SvSearchSortKey2) {
        sml090SvSearchSortKey2__ = sml090SvSearchSortKey2;
    }
    /**
     * <p>sml020BodyHtml を取得します。
     * @return sml020BodyHtml
     */
    public String getSml020BodyHtml() {
        return sml020BodyHtml__;
    }
    /**
     * <p>sml020BodyHtml をセットします。
     * @param sml020BodyHtml sml020BodyHtml
     */
    public void setSml020BodyHtml(String sml020BodyHtml) {
        sml020BodyHtml__ = sml020BodyHtml;
    }
    /**
     * <p>sml020MailType を取得します。
     * @return sml020MailType
     */
    public int getSml020MailType() {
        return sml020MailType__;
    }
    /**
     * <p>sml020MailType をセットします。
     * @param sml020MailType sml020MailType
     */
    public void setSml020MailType(int sml020MailType) {
        sml020MailType__ = sml020MailType;
    }
    /**
     * <p>sml020AtesakiDeletedMessage を取得します。
     * @return sml020AtesakiDeletedMessage
     */
    public String getSml020AtesakiDeletedMessage() {
        return sml020AtesakiDeletedMessage__;
    }
    /**
     * <p>sml020AtesakiDeletedMessage をセットします。
     * @param sml020AtesakiDeletedMessage sml020AtesakiDeletedMessage
     */
    public void setSml020AtesakiDeletedMessage(
            String sml020AtesakiDeletedMessage) {
        this.sml020AtesakiDeletedMessage__ = sml020AtesakiDeletedMessage;
    }
    /**
     * <p>sml020TempSaveId を取得します。
     * @return sml020TempSaveId
     * @see jp.groupsession.v2.sml.sml020.Sml020ParamModel#sml020TempSaveId__
     */
    public String getSml020TempSaveId() {
        return sml020TempSaveId__;
    }
    /**
     * <p>sml020TempSaveId をセットします。
     * @param sml020TempSaveId sml020TempSaveId
     * @see jp.groupsession.v2.sml.sml020.Sml020ParamModel#sml020TempSaveId__
     */
    public void setSml020TempSaveId(String sml020TempSaveId) {
        sml020TempSaveId__ = sml020TempSaveId;
    }
}