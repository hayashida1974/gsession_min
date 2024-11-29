package jp.groupsession.v2.rng.rng050;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiKeiroNameModel;
import jp.groupsession.v2.rng.rng010.Rng010ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議 管理者設定 申請中案件管理画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng050ParamModel extends Rng010ParamModel {
    //フィールド
    /** キーワード */
    private String rngInputKeyword__;

    //コンボ
    /** 選択グループSID */
    private int sltGroupSid__ = -1;
    /** 選択ユーザSID */
    private int sltUserSid__ = -1;
    /** 種別 */
    private int rng050Type__ = RngConst.RNG_MODE_JYUSIN;

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

    /** 申請日時 年月日 From */
    private String rng050ApplDateFr__;
    /** 申請日時 年月日 To */
    private String rng050ApplDateTo__;
    /** 最終処理日時 年月日 From */
    private String rng050LastManageDateFr__;
    /** 最終処理日時 年月日 To */
    private String rng050LastManageDateTo__;
    /** 稟議 日付選択 年(現在から2000年までの数値) */
    private int rng050MinYear__ = 0;

    /** 表示用グループリスト */
    private List<LabelValueBean> rng050groupList__ = null;
    /** 表示用ユーザーリスト */
    private List<UsrLabelValueBean> rng050userList__ = null;
    /** 稟議情報一覧 */
    private List<RingiDataModel> rng050rngDataList__ = null;
    /** 稟議ユーザ。グループ名情報*/
    private List<RingiKeiroNameModel> rng050keiroNameList__ = null;

    /** キーワード */
    private String rngAdminKeyword__;
    /** グループSID */
    private int rngAdminGroupSid__ = -1;
    /** ユーザSID */
    private int rngAdminUserSid__ = -1;

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

    /** ソートキー */
    private int rngAdminSortKey__ = RngConst.RNG_SORT_KAKUNIN;
    /** オーダーキー */
    private int rngAdminOrderKey__ = RngConst.RNG_ORDER_DESC;
    /** ページ(上) */
    private int rngAdminPageTop__ = 1;
    /** ページ(下) */
    private int rngAdminPageBottom__ = 1;
    /** 検索実行フラグ */
    private int rngAdminSearchFlg__ = 0;
    /** 年月日 */
    private String rng050rngNowDate__ = "";
    /** 出力ディレクトリ用ハッシュ値 */
    private String rng050outPutDirHash__ = "";
    /** 出力件数 */
    private int rng050pdfOutputCnt__ = 0;
    /** 出力全件数 */
    private int rng050pdfOutputMax__ = 0;

    /**
     * <p>rng050groupList を取得します。
     * @return rng050groupList
     */
    public List<LabelValueBean> getRng050groupList() {
        return rng050groupList__;
    }

    /**
     * <p>rng050groupList をセットします。
     * @param rng050groupList rng050groupList
     */
    public void setRng050groupList(List<LabelValueBean> rng050groupList) {
        rng050groupList__ = rng050groupList;
    }

    /**
     * <p>rng050userList を取得します。
     * @return rng050userList
     */
    public List<UsrLabelValueBean> getRng050userList() {
        return rng050userList__;
    }

    /**
     * <p>rng050userList をセットします。
     * @param rng050userList rng050userList
     */
    public void setRng050userList(List<UsrLabelValueBean> rng050userList) {
        rng050userList__ = rng050userList;
    }

    /**
     * <p>rng050rngDataList を取得します。
     * @return rng050rngDataList
     */
    public List<RingiDataModel> getRng050rngDataList() {
        return rng050rngDataList__;
    }

    /**
     * <p>rng050rngDataList をセットします。
     * @param rng050rngDataList rng050rngDataList
     */
    public void setRng050rngDataList(List<RingiDataModel> rng050rngDataList) {
        rng050rngDataList__ = rng050rngDataList;
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
     * <p>rng050Type を取得します。
     * @return rng050Type
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng130Type__
     */
    public int getRng050Type() {
        return rng050Type__;
    }

    /**
     * <p>rng050Type をセットします。
     * @param rng050Type rng050Type
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050Type__
     */
    public void setRng050Type(int rng050Type) {
        rng050Type__ = rng050Type;
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
     * <p>rng050keiroNameList を取得します。
     * @return rng050keiroNameList
     */
    public List<RingiKeiroNameModel> getRng050keiroNameList() {
        return rng050keiroNameList__;
    }

    /**
     * <p>rng050keiroNameList をセットします。
     * @param rng050keiroNameList rng050keiroNameList
     */
    public void setRng050keiroNameList(
            List<RingiKeiroNameModel> rng050keiroNameList) {
        rng050keiroNameList__ = rng050keiroNameList;
    }
    /**
     * <p>rng050rngNowDate を取得します。
     * @return rng050rngNowDate
     * @see jp.groupsession.v2.rng.rng050.Rng050ParamModel#rng050rngNowDate__
     */
    public String getRng050rngNowDate() {
        return rng050rngNowDate__;
    }
    /**
     * <p>rng050rngNowDate をセットします。
     * @param rng050rngNowDate rng050rngNowDate
     * @see jp.groupsession.v2.rng.rng050.Rng050ParamModel#rng050rngNowDate__
     */
    public void setRng050rngNowDate(String rng050rngNowDate) {
        rng050rngNowDate__ = rng050rngNowDate;
    }
    /**
     * <p>rng050outPutDirHash を取得します。
     * @return rng050outPutDirHash
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050outPutDirHash__
     */
    public String getRng050outPutDirHash() {
        return rng050outPutDirHash__;
    }
    /**
     * <p>rng050outPutDirHash をセットします。
     * @param rng050outPutDirHash rng050outPutDirHash
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050outPutDirHash__
     */
    public void setRng050outPutDirHash(String rng050outPutDirHash) {
        rng050outPutDirHash__ = rng050outPutDirHash;
    }

    /**
     * <p>rng050pdfOutputCnt を取得します。
     * @return rng050pdfOutputCnt
     * @see jp.groupsession.v2.rng.rng050.Rng050ParamModel#rng050pdfOutputCnt__
     */
    public int getRng050pdfOutputCnt() {
        return rng050pdfOutputCnt__;
    }

    /**
     * <p>rng050pdfOutputCnt をセットします。
     * @param rng050pdfOutputCnt rng050pdfOutputCnt
     * @see jp.groupsession.v2.rng.rng050.Rng050ParamModel#rng050pdfOutputCnt__
     */
    public void setRng050pdfOutputCnt(int rng050pdfOutputCnt) {
        rng050pdfOutputCnt__ = rng050pdfOutputCnt;
    }

    /**
     * <p>rng050pdfOutputMax を取得します。
     * @return rng050pdfOutputMax
     * @see jp.groupsession.v2.rng.rng050.Rng050ParamModel#rng050pdfOutputMax__
     */
    public int getRng050pdfOutputMax() {
        return rng050pdfOutputMax__;
    }

    /**
     * <p>rng050pdfOutputMax をセットします。
     * @param rng050pdfOutputMax rng050pdfOutputMax
     * @see jp.groupsession.v2.rng.rng050.Rng050ParamModel#rng050pdfOutputMax__
     */
    public void setRng050pdfOutputMax(int rng050pdfOutputMax) {
        rng050pdfOutputMax__ = rng050pdfOutputMax;
    }

    /**
     * <p>rng050ApplDateFr を取得します。
     * @return rng050ApplDateFr
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050ApplDateFr__
     */
    public String getRng050ApplDateFr() {
        return rng050ApplDateFr__;
    }

    /**
     * <p>rng050ApplDateFr をセットします。
     * @param rng050ApplDateFr rng050ApplDateFr
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050ApplDateFr__
     */
    public void setRng050ApplDateFr(String rng050ApplDateFr) {
        rng050ApplDateFr__ = rng050ApplDateFr;
    }

    /**
     * <p>rng050ApplDateTo を取得します。
     * @return rng050ApplDateTo
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050ApplDateTo__
     */
    public String getRng050ApplDateTo() {
        return rng050ApplDateTo__;
    }

    /**
     * <p>rng050ApplDateTo をセットします。
     * @param rng050ApplDateTo rng050ApplDateTo
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050ApplDateTo__
     */
    public void setRng050ApplDateTo(String rng050ApplDateTo) {
        rng050ApplDateTo__ = rng050ApplDateTo;
    }

    /**
     * <p>rng050LastManageDateFr を取得します。
     * @return rng050LastManageDateFr
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050LastManageDateFr__
     */
    public String getRng050LastManageDateFr() {
        return rng050LastManageDateFr__;
    }

    /**
     * <p>rng050LastManageDateFr をセットします。
     * @param rng050LastManageDateFr rng050LastManageDateFr
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050LastManageDateFr__
     */
    public void setRng050LastManageDateFr(String rng050LastManageDateFr) {
        rng050LastManageDateFr__ = rng050LastManageDateFr;
    }

    /**
     * <p>rng050LastManageDateTo を取得します。
     * @return rng050LastManageDateTo
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050LastManageDateTo__
     */
    public String getRng050LastManageDateTo() {
        return rng050LastManageDateTo__;
    }

    /**
     * <p>rng050LastManageDateTo をセットします。
     * @param rng050LastManageDateTo rng050LastManageDateTo
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050LastManageDateTo__
     */
    public void setRng050LastManageDateTo(String rng050LastManageDateTo) {
        rng050LastManageDateTo__ = rng050LastManageDateTo;
    }

    /**
     * <p>rng050MinYear を取得します。
     * @return rng050MinYear
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050MinYear__
     */
    public int getRng050MinYear() {
        return rng050MinYear__;
    }

    /**
     * <p>rng050MinYear をセットします。
     * @param rng050MinYear rng050MinYear
     * @see jp.groupsession.v2.rng.rng050.Rng050Form#rng050MinYear__
     */
    public void setRng050MinYear(int rng050MinYear) {
        rng050MinYear__ = rng050MinYear;
    }
}