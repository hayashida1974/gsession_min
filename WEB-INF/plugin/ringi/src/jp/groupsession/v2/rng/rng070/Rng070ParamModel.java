package jp.groupsession.v2.rng.rng070;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.rng010.Rng010ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議 管理者設定 完了案件管理画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng070ParamModel extends Rng010ParamModel {
    //フィールド
    /** キーワード */
    private String rngInputKeyword__ = null;
    /** 種別 */
    private int rng070Type__ = RngConst.RNG_MODE_JYUSIN;
    /** 結果*/
    private int rng070Kekka__ = -1;


    /** 稟議情報(完了)リスト */
    private List<RingiDataModel> rng070dataList__ = null;

    /** 削除チェックボックス */
    private String[] rng070dellist__ = null;
    //キー値
    /** ソートキー */
    private int rngAdminSortKey__ = RngConst.RNG_SORT_KAKUNIN;
    /** オーダーキー */
    private int rngAdminOrderKey__ = RngConst.RNG_ORDER_DESC;
    /** ページ(上) */
    private int rngAdminPageTop__ = 1;
    /** ページ(下) */
    private int rngAdminPageBottom__ = 1;
    /** ページリスト */
    private List<LabelValueBean> rngAdminPageList__;
    /** 検索実行フラグ */
    private int rngAdminSearchFlg__ = 0;
    /** グループSID */
    private int rngAdminGroupSid__ = -1;
    /** ユーザSID */
    private int rngAdminUserSid__ = -1;
    /** キーワード */
    private String rngAdminKeyword__;
    /** 申請日時 年 From */
    private int rngAdminApplYearFr__ = -1;
    /** 申請日時 月 From */
    private int rngAdminApplMonthFr__ = -1;
    /** 申請日時 日 From */
    private int rngAdminApplDayFr__ = -1;
    /** 申請日時 年 To */
    private int rngAdminApplYearTo__ = -1;
    /** 申請日時 月 To */
    private int rngAdminApplMonthTo__ = -1;
    /** 申請日時 日 To */
    private int rngAdminApplDayTo__ = -1;
    /** 最終処理日時 年 From */
    private int rngAdminLastManageYearFr__ = -1;
    /** 最終処理日時 月 From */
    private int rngAdminLastManageMonthFr__ = -1;
    /** 最終処理日時 日 From */
    private int rngAdminLastManageDayFr__ = -1;
    /** 最終処理日時 年 To */
    private int rngAdminLastManageYearTo__ = -1;
    /** 最終処理日時 月 To */
    private int rngAdminLastManageMonthTo__ = -1;
    /** 最終処理日時 日 To */
    private int rngAdminLastManageDayTo__ = -1;

    /** 申請日時 年月日 From */
    private String rng070ApplDateFr__;
    /** 申請日時 年月日 To */
    private String rng070ApplDateTo__;
    /** 最終処理日時 年月日 From */
    private String rng070LastManageDateFr__;
    /** 最終処理日時 年月日 To */
    private String rng070LastManageDateTo__;
    /** 稟議 日付選択 年(現在から2000年までの数値) */
    private int rng070MinYear__ = 0;


    //コンボ
    /** 選択グループSID */
    private int sltGroupSid__ = -1;
    /** 選択グループSID */
    private int sltUserSid__ = -1;
    /** 選択申請日時 年 From */
    private int sltApplYearFr__ = -1;
    /** 選択申請日時 月 From */
    private int sltApplMonthFr__ = -1;
    /** 選択申請日時 日 From */
    private int sltApplDayFr__ = -1;
    /** 選択申請日時 年 To */
    private int sltApplYearTo__ = -1;
    /** 選択申請日時 月 To */
    private int sltApplMonthTo__ = -1;
    /** 選択申請日時 日 To */
    private int sltApplDayTo__ = -1;
    /** 選択最終処理日時 年 From */
    private int sltLastManageYearFr__ = -1;
    /** 選択最終処理日時 月 From */
    private int sltLastManageMonthFr__ = -1;
    /** 選択最終処理日時 日 From */
    private int sltLastManageDayFr__ = -1;
    /** 選択最終処理日時 年 To */
    private int sltLastManageYearTo__ = -1;
    /** 選択最終処理日時 月 To */
    private int sltLastManageMonthTo__ = -1;
    /** 選択最終処理日時 日 To */
    private int sltLastManageDayTo__ = -1;
    /** 表示用グループリスト */
    private List<LabelValueBean> rng070groupList__ = null;
    /** 表示用ユーザーリスト */
    private List<UsrLabelValueBean> rng070userList__ = null;
    /** 年月日 */
    private String rng070rngNowDate__ = "";
    /** 出力ディレクトリ用ハッシュ値 */
    private String rng070outPutDirHash__ = "";
    /** 出力件数 */
    private int rng070pdfOutputCnt__ = 0;
    /** 出力全件数 */
    private int rng070pdfOutputMax__ = 0;

    /**
     * <p>rng070dataList を取得します。
     * @return rng070dataList
     */
    public List<RingiDataModel> getRng070dataList() {
        return rng070dataList__;
    }

    /**
     * <p>rng070dataList をセットします。
     * @param rng070dataList rng070dataList
     */
    public void setRng070dataList(List<RingiDataModel> rng070dataList) {
        rng070dataList__ = rng070dataList;
    }

    /**
     * <p>rng070groupList を取得します。
     * @return rng070groupList
     */
    public List<LabelValueBean> getRng070groupList() {
        return rng070groupList__;
    }

    /**
     * <p>rng070groupList をセットします。
     * @param rng070groupList rng070groupList
     */
    public void setRng070groupList(List<LabelValueBean> rng070groupList) {
        rng070groupList__ = rng070groupList;
    }

    /**
     * <p>rng070userList を取得します。
     * @return rng070userList
     */
    public List<UsrLabelValueBean> getRng070userList() {
        return rng070userList__;
    }

    /**
     * <p>rng070userList をセットします。
     * @param rng070userList rng070userList
     */
    public void setRng070userList(List<UsrLabelValueBean> rng070userList) {
        rng070userList__ = rng070userList;
    }

    /**
     * <p>rngInputKeyword を取得します。
     * @return rngInputKeyword
     */
    public String getRngInputKeyword() {
        return rngInputKeyword__;
    }

    /**
     * <p>rngInputKeyword をセットします。
     * @param rngInputKeyword rngInputKeyword
     */
    public void setRngInputKeyword(String rngInputKeyword) {
        rngInputKeyword__ = rngInputKeyword;
    }

    /**
     * <p>rng070Type を取得します。
     * @return rng070Type
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070Type__
     */
    public int getRng070Type() {
        return rng070Type__;
    }

    /**
     * <p>rng070Type をセットします。
     * @param rng070Type rng070Type
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070Type__
     */
    public void setRng070Type(int rng070Type) {
        rng070Type__ = rng070Type;
    }

    /**
     * <p>rngAdminGroupSid を取得します。
     * @return rngAdminGroupSid
     */
    public int getRngAdminGroupSid() {
        return rngAdminGroupSid__;
    }

    /**
     * <p>rngAdminGroupSid をセットします。
     * @param rngAdminGroupSid rngAdminGroupSid
     */
    public void setRngAdminGroupSid(int rngAdminGroupSid) {
        rngAdminGroupSid__ = rngAdminGroupSid;
    }

    /**
     * <p>rngAdminUserSid を取得します。
     * @return rngAdminUserSid
     */
    public int getRngAdminUserSid() {
        return rngAdminUserSid__;
    }

    /**
     * <p>rngAdminUserSid をセットします。
     * @param rngAdminUserSid rngAdminUserSid
     */
    public void setRngAdminUserSid(int rngAdminUserSid) {
        rngAdminUserSid__ = rngAdminUserSid;
    }

    /**
     * <p>rngAdminKeyword を取得します。
     * @return rngAdminKeyword
     */
    public String getRngAdminKeyword() {
        return rngAdminKeyword__;
    }

    /**
     * <p>rngAdminKeyword をセットします。
     * @param rngAdminKeyword rngAdminKeyword
     */
    public void setRngAdminKeyword(String rngAdminKeyword) {
        rngAdminKeyword__ = rngAdminKeyword;
    }

    /**
     * <p>rngAdminOrderKey を取得します。
     * @return rngAdminOrderKey
     */
    public int getRngAdminOrderKey() {
        return rngAdminOrderKey__;
    }

    /**
     * <p>rngAdminOrderKey をセットします。
     * @param rngAdminOrderKey rngAdminOrderKey
     */
    public void setRngAdminOrderKey(int rngAdminOrderKey) {
        rngAdminOrderKey__ = rngAdminOrderKey;
    }

    /**
     * <p>rngAdminPageBottom を取得します。
     * @return rngAdminPageBottom
     */
    public int getRngAdminPageBottom() {
        return rngAdminPageBottom__;
    }

    /**
     * <p>rngAdminPageBottom をセットします。
     * @param rngAdminPageBottom rngAdminPageBottom
     */
    public void setRngAdminPageBottom(int rngAdminPageBottom) {
        rngAdminPageBottom__ = rngAdminPageBottom;
    }

    /**
     * <p>rngAdminPageTop を取得します。
     * @return rngAdminPageTop
     */
    public int getRngAdminPageTop() {
        return rngAdminPageTop__;
    }

    /**
     * <p>rngAdminPageTop をセットします。
     * @param rngAdminPageTop rngAdminPageTop
     */
    public void setRngAdminPageTop(int rngAdminPageTop) {
        rngAdminPageTop__ = rngAdminPageTop;
    }

    /**
     * <p>rngAdminSortKey を取得します。
     * @return rngAdminSortKey
     */
    public int getRngAdminSortKey() {
        return rngAdminSortKey__;
    }

    /**
     * <p>rngAdminSortKey をセットします。
     * @param rngAdminSortKey rngAdminSortKey
     */
    public void setRngAdminSortKey(int rngAdminSortKey) {
        rngAdminSortKey__ = rngAdminSortKey;
    }

    /**
     * <p>sltGroupSid を取得します。
     * @return sltGroupSid
     */
    public int getSltGroupSid() {
        return sltGroupSid__;
    }

    /**
     * <p>sltGroupSid をセットします。
     * @param sltGroupSid sltGroupSid
     */
    public void setSltGroupSid(int sltGroupSid) {
        sltGroupSid__ = sltGroupSid;
    }

    /**
     * <p>sltUserSid を取得します。
     * @return sltUserSid
     */
    public int getSltUserSid() {
        return sltUserSid__;
    }

    /**
     * <p>sltUserSid をセットします。
     * @param sltUserSid sltUserSid
     */
    public void setSltUserSid(int sltUserSid) {
        sltUserSid__ = sltUserSid;
    }

    /**
     * <p>rng070dellist を取得します。
     * @return rng070dellist
     */
    public String[] getRng070dellist() {
        return rng070dellist__;
    }

    /**
     * <p>rng070dellist をセットします。
     * @param rng070dellist rng070dellist
     */
    public void setRng070dellist(String[] rng070dellist) {
        rng070dellist__ = rng070dellist;
    }

    /**
     * <p>rngAdminPageList を取得します。
     * @return rngAdminPageList
     */
    public List<LabelValueBean> getRngAdminPageList() {
        return rngAdminPageList__;
    }

    /**
     * <p>rngAdminPageList をセットします。
     * @param rngAdminPageList rngAdminPageList
     */
    public void setRngAdminPageList(List<LabelValueBean> rngAdminPageList) {
        rngAdminPageList__ = rngAdminPageList;
    }

    /**
     * <p>rngAdminSearchFlg を取得します。
     * @return rngAdminSearchFlg
     */
    public int getRngAdminSearchFlg() {
        return rngAdminSearchFlg__;
    }

    /**
     * <p>rngAdminSearchFlg をセットします。
     * @param rngAdminSearchFlg rngAdminSearchFlg
     */
    public void setRngAdminSearchFlg(int rngAdminSearchFlg) {
        rngAdminSearchFlg__ = rngAdminSearchFlg;
    }

    /**
     * <p>rngAdminApplDayFr を取得します。
     * @return rngAdminApplDayFr
     */
    public int getRngAdminApplDayFr() {
        return rngAdminApplDayFr__;
    }

    /**
     * <p>rngAdminApplDayFr をセットします。
     * @param rngAdminApplDayFr rngAdminApplDayFr
     */
    public void setRngAdminApplDayFr(int rngAdminApplDayFr) {
        rngAdminApplDayFr__ = rngAdminApplDayFr;
    }

    /**
     * <p>rngAdminApplDayTo を取得します。
     * @return rngAdminApplDayTo
     */
    public int getRngAdminApplDayTo() {
        return rngAdminApplDayTo__;
    }

    /**
     * <p>rngAdminApplDayTo をセットします。
     * @param rngAdminApplDayTo rngAdminApplDayTo
     */
    public void setRngAdminApplDayTo(int rngAdminApplDayTo) {
        rngAdminApplDayTo__ = rngAdminApplDayTo;
    }

    /**
     * <p>rngAdminApplMonthFr を取得します。
     * @return rngAdminApplMonthFr
     */
    public int getRngAdminApplMonthFr() {
        return rngAdminApplMonthFr__;
    }

    /**
     * <p>rngAdminApplMonthFr をセットします。
     * @param rngAdminApplMonthFr rngAdminApplMonthFr
     */
    public void setRngAdminApplMonthFr(int rngAdminApplMonthFr) {
        rngAdminApplMonthFr__ = rngAdminApplMonthFr;
    }

    /**
     * <p>rngAdminApplMonthTo を取得します。
     * @return rngAdminApplMonthTo
     */
    public int getRngAdminApplMonthTo() {
        return rngAdminApplMonthTo__;
    }

    /**
     * <p>rngAdminApplMonthTo をセットします。
     * @param rngAdminApplMonthTo rngAdminApplMonthTo
     */
    public void setRngAdminApplMonthTo(int rngAdminApplMonthTo) {
        rngAdminApplMonthTo__ = rngAdminApplMonthTo;
    }

    /**
     * <p>rngAdminApplYearFr を取得します。
     * @return rngAdminApplYearFr
     */
    public int getRngAdminApplYearFr() {
        return rngAdminApplYearFr__;
    }

    /**
     * <p>rngAdminApplYearFr をセットします。
     * @param rngAdminApplYearFr rngAdminApplYearFr
     */
    public void setRngAdminApplYearFr(int rngAdminApplYearFr) {
        rngAdminApplYearFr__ = rngAdminApplYearFr;
    }

    /**
     * <p>rngAdminApplYearTo を取得します。
     * @return rngAdminApplYearTo
     */
    public int getRngAdminApplYearTo() {
        return rngAdminApplYearTo__;
    }

    /**
     * <p>rngAdminApplYearTo をセットします。
     * @param rngAdminApplYearTo rngAdminApplYearTo
     */
    public void setRngAdminApplYearTo(int rngAdminApplYearTo) {
        rngAdminApplYearTo__ = rngAdminApplYearTo;
    }

    /**
     * <p>rngAdminLastManageDayFr を取得します。
     * @return rngAdminLastManageDayFr
     */
    public int getRngAdminLastManageDayFr() {
        return rngAdminLastManageDayFr__;
    }

    /**
     * <p>rngAdminLastManageDayFr をセットします。
     * @param rngAdminLastManageDayFr rngAdminLastManageDayFr
     */
    public void setRngAdminLastManageDayFr(int rngAdminLastManageDayFr) {
        rngAdminLastManageDayFr__ = rngAdminLastManageDayFr;
    }

    /**
     * <p>rngAdminLastManageDayTo を取得します。
     * @return rngAdminLastManageDayTo
     */
    public int getRngAdminLastManageDayTo() {
        return rngAdminLastManageDayTo__;
    }

    /**
     * <p>rngAdminLastManageDayTo をセットします。
     * @param rngAdminLastManageDayTo rngAdminLastManageDayTo
     */
    public void setRngAdminLastManageDayTo(int rngAdminLastManageDayTo) {
        rngAdminLastManageDayTo__ = rngAdminLastManageDayTo;
    }

    /**
     * <p>rngAdminLastManageMonthFr を取得します。
     * @return rngAdminLastManageMonthFr
     */
    public int getRngAdminLastManageMonthFr() {
        return rngAdminLastManageMonthFr__;
    }

    /**
     * <p>rngAdminLastManageMonthFr をセットします。
     * @param rngAdminLastManageMonthFr rngAdminLastManageMonthFr
     */
    public void setRngAdminLastManageMonthFr(int rngAdminLastManageMonthFr) {
        rngAdminLastManageMonthFr__ = rngAdminLastManageMonthFr;
    }

    /**
     * <p>rngAdminLastManageMonthTo を取得します。
     * @return rngAdminLastManageMonthTo
     */
    public int getRngAdminLastManageMonthTo() {
        return rngAdminLastManageMonthTo__;
    }

    /**
     * <p>rngAdminLastManageMonthTo をセットします。
     * @param rngAdminLastManageMonthTo rngAdminLastManageMonthTo
     */
    public void setRngAdminLastManageMonthTo(int rngAdminLastManageMonthTo) {
        rngAdminLastManageMonthTo__ = rngAdminLastManageMonthTo;
    }

    /**
     * <p>rngAdminLastManageYearFr を取得します。
     * @return rngAdminLastManageYearFr
     */
    public int getRngAdminLastManageYearFr() {
        return rngAdminLastManageYearFr__;
    }

    /**
     * <p>rngAdminLastManageYearFr をセットします。
     * @param rngAdminLastManageYearFr rngAdminLastManageYearFr
     */
    public void setRngAdminLastManageYearFr(int rngAdminLastManageYearFr) {
        rngAdminLastManageYearFr__ = rngAdminLastManageYearFr;
    }

    /**
     * <p>rngAdminLastManageYearTo を取得します。
     * @return rngAdminLastManageYearTo
     */
    public int getRngAdminLastManageYearTo() {
        return rngAdminLastManageYearTo__;
    }

    /**
     * <p>rngAdminLastManageYearTo をセットします。
     * @param rngAdminLastManageYearTo rngAdminLastManageYearTo
     */
    public void setRngAdminLastManageYearTo(int rngAdminLastManageYearTo) {
        rngAdminLastManageYearTo__ = rngAdminLastManageYearTo;
    }

    /**
     * <p>sltApplDayFr を取得します。
     * @return sltApplDayFr
     */
    public int getSltApplDayFr() {
        return sltApplDayFr__;
    }

    /**
     * <p>sltApplDayFr をセットします。
     * @param sltApplDayFr sltApplDayFr
     */
    public void setSltApplDayFr(int sltApplDayFr) {
        sltApplDayFr__ = sltApplDayFr;
    }

    /**
     * <p>sltApplDayTo を取得します。
     * @return sltApplDayTo
     */
    public int getSltApplDayTo() {
        return sltApplDayTo__;
    }

    /**
     * <p>sltApplDayTo をセットします。
     * @param sltApplDayTo sltApplDayTo
     */
    public void setSltApplDayTo(int sltApplDayTo) {
        sltApplDayTo__ = sltApplDayTo;
    }

    /**
     * <p>sltApplMonthFr を取得します。
     * @return sltApplMonthFr
     */
    public int getSltApplMonthFr() {
        return sltApplMonthFr__;
    }

    /**
     * <p>sltApplMonthFr をセットします。
     * @param sltApplMonthFr sltApplMonthFr
     */
    public void setSltApplMonthFr(int sltApplMonthFr) {
        sltApplMonthFr__ = sltApplMonthFr;
    }

    /**
     * <p>sltApplMonthTo を取得します。
     * @return sltApplMonthTo
     */
    public int getSltApplMonthTo() {
        return sltApplMonthTo__;
    }

    /**
     * <p>sltApplMonthTo をセットします。
     * @param sltApplMonthTo sltApplMonthTo
     */
    public void setSltApplMonthTo(int sltApplMonthTo) {
        sltApplMonthTo__ = sltApplMonthTo;
    }

    /**
     * <p>sltApplYearFr を取得します。
     * @return sltApplYearFr
     */
    public int getSltApplYearFr() {
        return sltApplYearFr__;
    }

    /**
     * <p>sltApplYearFr をセットします。
     * @param sltApplYearFr sltApplYearFr
     */
    public void setSltApplYearFr(int sltApplYearFr) {
        sltApplYearFr__ = sltApplYearFr;
    }

    /**
     * <p>sltApplYearTo を取得します。
     * @return sltApplYearTo
     */
    public int getSltApplYearTo() {
        return sltApplYearTo__;
    }

    /**
     * <p>sltApplYearTo をセットします。
     * @param sltApplYearTo sltApplYearTo
     */
    public void setSltApplYearTo(int sltApplYearTo) {
        sltApplYearTo__ = sltApplYearTo;
    }

    /**
     * <p>sltLastManageDayFr を取得します。
     * @return sltLastManageDayFr
     */
    public int getSltLastManageDayFr() {
        return sltLastManageDayFr__;
    }

    /**
     * <p>sltLastManageDayFr をセットします。
     * @param sltLastManageDayFr sltLastManageDayFr
     */
    public void setSltLastManageDayFr(int sltLastManageDayFr) {
        sltLastManageDayFr__ = sltLastManageDayFr;
    }

    /**
     * <p>sltLastManageDayTo を取得します。
     * @return sltLastManageDayTo
     */
    public int getSltLastManageDayTo() {
        return sltLastManageDayTo__;
    }

    /**
     * <p>sltLastManageDayTo をセットします。
     * @param sltLastManageDayTo sltLastManageDayTo
     */
    public void setSltLastManageDayTo(int sltLastManageDayTo) {
        sltLastManageDayTo__ = sltLastManageDayTo;
    }

    /**
     * <p>sltLastManageMonthFr を取得します。
     * @return sltLastManageMonthFr
     */
    public int getSltLastManageMonthFr() {
        return sltLastManageMonthFr__;
    }

    /**
     * <p>sltLastManageMonthFr をセットします。
     * @param sltLastManageMonthFr sltLastManageMonthFr
     */
    public void setSltLastManageMonthFr(int sltLastManageMonthFr) {
        sltLastManageMonthFr__ = sltLastManageMonthFr;
    }

    /**
     * <p>sltLastManageMonthTo を取得します。
     * @return sltLastManageMonthTo
     */
    public int getSltLastManageMonthTo() {
        return sltLastManageMonthTo__;
    }

    /**
     * <p>sltLastManageMonthTo をセットします。
     * @param sltLastManageMonthTo sltLastManageMonthTo
     */
    public void setSltLastManageMonthTo(int sltLastManageMonthTo) {
        sltLastManageMonthTo__ = sltLastManageMonthTo;
    }

    /**
     * <p>sltLastManageYearFr を取得します。
     * @return sltLastManageYearFr
     */
    public int getSltLastManageYearFr() {
        return sltLastManageYearFr__;
    }

    /**
     * <p>sltLastManageYearFr をセットします。
     * @param sltLastManageYearFr sltLastManageYearFr
     */
    public void setSltLastManageYearFr(int sltLastManageYearFr) {
        sltLastManageYearFr__ = sltLastManageYearFr;
    }

    /**
     * <p>sltLastManageYearTo を取得します。
     * @return sltLastManageYearTo
     */
    public int getSltLastManageYearTo() {
        return sltLastManageYearTo__;
    }

    /**
     * <p>sltLastManageYearTo をセットします。
     * @param sltLastManageYearTo sltLastManageYearTo
     */
    public void setSltLastManageYearTo(int sltLastManageYearTo) {
        sltLastManageYearTo__ = sltLastManageYearTo;
    }

    /**
     * <p>rng070rngNowDate を取得します。
     * @return rng070rngNowDate
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070rngNowDate__
     */
    public String getRng070rngNowDate() {
        return rng070rngNowDate__;
    }
    /**
     * <p>rng070rngNowDate をセットします。
     * @param rng070rngNowDate rng070rngNowDate
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070rngNowDate__
     */
    public void setRng070rngNowDate(String rng070rngNowDate) {
        rng070rngNowDate__ = rng070rngNowDate;
    }
    /**
     * <p>rng070outPutDirHash を取得します。
     * @return rng070outPutDirHash
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070outPutDirHash__
     */
    public String getRng070outPutDirHash() {
        return rng070outPutDirHash__;
    }
    /**
     * <p>rng070outPutDirHash をセットします。
     * @param rng070outPutDirHash rng070outPutDirHash
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070outPutDirHash__
     */
    public void setRng070outPutDirHash(String rng070outPutDirHash) {
        rng070outPutDirHash__ = rng070outPutDirHash;
    }

    /**
     * <p>rng070pdfOutputCnt を取得します。
     * @return rng070pdfOutputCnt
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070pdfOutputCnt__
     */
    public int getRng070pdfOutputCnt() {
        return rng070pdfOutputCnt__;
    }

    /**
     * <p>rng070pdfOutputCnt をセットします。
     * @param rng070pdfOutputCnt rng070pdfOutputCnt
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070pdfOutputCnt__
     */
    public void setRng070pdfOutputCnt(int rng070pdfOutputCnt) {
        rng070pdfOutputCnt__ = rng070pdfOutputCnt;
    }

    /**
     * <p>rng070pdfOutputMax を取得します。
     * @return rng070pdfOutputMax
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070pdfOutputMax__
     */
    public int getRng070pdfOutputMax() {
        return rng070pdfOutputMax__;
    }

    /**
     * <p>rng070pdfOutputMax をセットします。
     * @param rng070pdfOutputMax rng070pdfOutputMax
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070pdfOutputMax__
     */
    public void setRng070pdfOutputMax(int rng070pdfOutputMax) {
        rng070pdfOutputMax__ = rng070pdfOutputMax;
    }

    /**
     * <p>rng070Kekka を取得します。
     * @return rng070Kekka
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070Kekka__
     */
    public int getRng070Kekka() {
        return rng070Kekka__;
    }

    /**
     * <p>rng070Kekka をセットします。
     * @param rng070Kekka rng070Kekka
     * @see jp.groupsession.v2.rng.rng070.Rng070ParamModel#rng070Kekka__
     */
    public void setRng070Kekka(int rng070Kekka) {
        rng070Kekka__ = rng070Kekka;
    }

    /**
     * <p>rng070ApplDateFr を取得します。
     * @return rng070ApplDateFr
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070ApplDateFr__
     */
    public String getRng070ApplDateFr() {
        return rng070ApplDateFr__;
    }

    /**
     * <p>rng070ApplDateFr をセットします。
     * @param rng070ApplDateFr rng070ApplDateFr
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070ApplDateFr__
     */
    public void setRng070ApplDateFr(String rng070ApplDateFr) {
        rng070ApplDateFr__ = rng070ApplDateFr;
    }

    /**
     * <p>rng070ApplDateTo を取得します。
     * @return rng070ApplDateTo
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070ApplDateTo__
     */
    public String getRng070ApplDateTo() {
        return rng070ApplDateTo__;
    }

    /**
     * <p>rng070ApplDateTo をセットします。
     * @param rng070ApplDateTo rng070ApplDateTo
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070ApplDateTo__
     */
    public void setRng070ApplDateTo(String rng070ApplDateTo) {
        rng070ApplDateTo__ = rng070ApplDateTo;
    }

    /**
     * <p>rng070LastManageDateFr を取得します。
     * @return rng070LastManageDateFr
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070LastManageDateFr__
     */
    public String getRng070LastManageDateFr() {
        return rng070LastManageDateFr__;
    }

    /**
     * <p>rng070LastManageDateFr をセットします。
     * @param rng070LastManageDateFr rng070LastManageDateFr
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070LastManageDateFr__
     */
    public void setRng070LastManageDateFr(String rng070LastManageDateFr) {
        rng070LastManageDateFr__ = rng070LastManageDateFr;
    }

    /**
     * <p>rng070LastManageDateTo を取得します。
     * @return rng070LastManageDateTo
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070LastManageDateTo__
     */
    public String getRng070LastManageDateTo() {
        return rng070LastManageDateTo__;
    }

    /**
     * <p>rng070LastManageDateTo をセットします。
     * @param rng070LastManageDateTo rng070LastManageDateTo
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070LastManageDateTo__
     */
    public void setRng070LastManageDateTo(String rng070LastManageDateTo) {
        rng070LastManageDateTo__ = rng070LastManageDateTo;
    }

    /**
     * <p>rng070MinYear を取得します。
     * @return rng070MinYear
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070MinYear__
     */
    public int getRng070MinYear() {
        return rng070MinYear__;
    }

    /**
     * <p>rng070MinYear をセットします。
     * @param rng070MinYear rng070MinYear
     * @see jp.groupsession.v2.rng.rng070.Rng070Form#rng070MinYear__
     */
    public void setRng070MinYear(int rng070MinYear) {
        rng070MinYear__ = rng070MinYear;
    }
}