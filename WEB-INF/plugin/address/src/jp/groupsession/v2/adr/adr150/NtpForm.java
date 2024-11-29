package jp.groupsession.v2.adr.adr150;

import jp.groupsession.v2.adr.adr020.Adr020Form;

/**
 * <br>[機  能] アドレス帳 会社選択を日報プラグインから使用する場合のパラメータを保持するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpForm extends Adr020Form {
    //案件検索画面パラメータ
    /** 案件SID */
    private int ntp060NanSid__;
    /** 処理モード */
    private String ntp060ProcMode__;
    /** 初期化フラグ */
    private int ntp060InitFlg__;
    /** ページ */
    private int ntp060Page__;
    /** ページ上段 */
    private int ntp060PageTop__;
    /** ページ下段 */
    private int ntp060PageBottom__;
    /** ソートキー１ */
    private int ntp060SortKey1__;
    /** オーダキー１ */
    private int ntp060OrderKey1__;
    /** ソートキー２ */
    private int ntp060SortKey2__;
    /** オーダキー２ */
    private int ntp060OrderKey2__;
    /** 企業コード */
    private String ntp060AcoCode__;
    /** 企業名 */
    private String ntp060AcoName__;
    /** 企業名カナ */
    private String ntp060AcoNameKana__;
    /** 拠点名 */
    private String ntp060AbaName__;
    /** 商品名 */
    private String ntp060ShohinName__;
    /** 業務内容SID */
    private int ntp060NgySid__;
    /** プロセスSID */
    private int ntp060NgpSid__;
    /** コンタクトSID */
    private int ntp060NcnSid__;
    /** 見込度 */
    private String[] ntp060Mikomi__;
    /** 商談結果 */
    private String[] ntp060Syodan__;
    /** 案件コード */
    private String ntp060NanCode__;
    /** 案件名 */
    private String ntp060NanName__;
    /** 見積金額 */
    private String ntp060NanKinMitumori__;
    /** 受注金額 */
    private String ntp060NanKinJutyu__;
    /** 見積金額区分 */
    private int ntp060NanKinMitumoriKbn__;
    /** 受注金額区分 */
    private int ntp060NanKinJutyuKbn__;
    /** 案件登録 開始年 */
    private String ntp060FrYear__;
    /** 案件登録 開始月 */
    private String ntp060FrMonth__;
    /** 案件登録 開始日 */
    private String ntp060FrDay__;
    /** 案件登録 終了年 */
    private String ntp060ToYear__;
    /** 案件登録 終了月 */
    private String ntp060ToMonth__;
    /** 案件登録 終了日 */
    private String ntp060ToDay__;

    //案件登録画面パラメータ
    /** 遷移元 */
    private String ntp061ReturnPage__;
    /** 初期化フラグ */
    private int ntp061InitFlg__;
    /** 登録者名 */
    private String ntp061TourokuName__;
    /** 案件コード */
    private String ntp061NanCode__;
    /** 案件名 */
    private String ntp061NanName__;
    /** 案件詳細 */
    private String ntp061NanSyosai__;
    /** 見積金額 */
    private String ntp061NanKinMitumori__;
    /** 受注金額 */
    private String ntp061NanKinJutyu__;
    /** 見込度 */
    private int ntp061NanMikomi__;
    /** 商談結果 */
    private int ntp061NanSyodan__;
    /** 業務内容SID */
    private int ntp061NgySid__;
    /** プロセスSID */
    private int ntp061NgpSid__;
    /** コンタクトSID */
    private int ntp061NcnSid__;
    /** 案件登録 開始年 */
    private String ntp061FrYear__;
    /** 案件登録 開始月 */
    private String ntp061FrMonth__;
    /** 案件登録 開始日 */
    private String ntp061FrDay__;
    /** 企業コード */
    private String ntp061AcoCode__;
    /** 選択商品チェックボックスリスト */
    private String[] ntp061ChkShohinSidList__;
    /**
     * <p>ntp060NanSid を取得します。
     * @return ntp060NanSid
     */
    public int getNtp060NanSid() {
        return ntp060NanSid__;
    }
    /**
     * <p>ntp060NanSid をセットします。
     * @param ntp060NanSid ntp060NanSid
     */
    public void setNtp060NanSid(int ntp060NanSid) {
        ntp060NanSid__ = ntp060NanSid;
    }
    /**
     * <p>ntp060ProcMode を取得します。
     * @return ntp060ProcMode
     */
    public String getNtp060ProcMode() {
        return ntp060ProcMode__;
    }
    /**
     * <p>ntp060ProcMode をセットします。
     * @param ntp060ProcMode ntp060ProcMode
     */
    public void setNtp060ProcMode(String ntp060ProcMode) {
        ntp060ProcMode__ = ntp060ProcMode;
    }
    /**
     * <p>ntp060AbaName を取得します。
     * @return ntp060AbaName
     */
    public String getNtp060AbaName() {
        return ntp060AbaName__;
    }
    /**
     * <p>ntp060AbaName をセットします。
     * @param ntp060AbaName ntp060AbaName
     */
    public void setNtp060AbaName(String ntp060AbaName) {
        ntp060AbaName__ = ntp060AbaName;
    }
    /**
     * <p>ntp060AcoCode を取得します。
     * @return ntp060AcoCode
     */
    public String getNtp060AcoCode() {
        return ntp060AcoCode__;
    }
    /**
     * <p>ntp060AcoCode をセットします。
     * @param ntp060AcoCode ntp060AcoCode
     */
    public void setNtp060AcoCode(String ntp060AcoCode) {
        ntp060AcoCode__ = ntp060AcoCode;
    }
    /**
     * <p>ntp060AcoName を取得します。
     * @return ntp060AcoName
     */
    public String getNtp060AcoName() {
        return ntp060AcoName__;
    }
    /**
     * <p>ntp060AcoName をセットします。
     * @param ntp060AcoName ntp060AcoName
     */
    public void setNtp060AcoName(String ntp060AcoName) {
        ntp060AcoName__ = ntp060AcoName;
    }
    /**
     * <p>ntp060AcoNameKana を取得します。
     * @return ntp060AcoNameKana
     */
    public String getNtp060AcoNameKana() {
        return ntp060AcoNameKana__;
    }
    /**
     * <p>ntp060AcoNameKana をセットします。
     * @param ntp060AcoNameKana ntp060AcoNameKana
     */
    public void setNtp060AcoNameKana(String ntp060AcoNameKana) {
        ntp060AcoNameKana__ = ntp060AcoNameKana;
    }
    /**
     * <p>ntp060FrDay を取得します。
     * @return ntp060FrDay
     */
    public String getNtp060FrDay() {
        return ntp060FrDay__;
    }
    /**
     * <p>ntp060FrDay をセットします。
     * @param ntp060FrDay ntp060FrDay
     */
    public void setNtp060FrDay(String ntp060FrDay) {
        ntp060FrDay__ = ntp060FrDay;
    }
    /**
     * <p>ntp060FrMonth を取得します。
     * @return ntp060FrMonth
     */
    public String getNtp060FrMonth() {
        return ntp060FrMonth__;
    }
    /**
     * <p>ntp060FrMonth をセットします。
     * @param ntp060FrMonth ntp060FrMonth
     */
    public void setNtp060FrMonth(String ntp060FrMonth) {
        ntp060FrMonth__ = ntp060FrMonth;
    }
    /**
     * <p>ntp060FrYear を取得します。
     * @return ntp060FrYear
     */
    public String getNtp060FrYear() {
        return ntp060FrYear__;
    }
    /**
     * <p>ntp060FrYear をセットします。
     * @param ntp060FrYear ntp060FrYear
     */
    public void setNtp060FrYear(String ntp060FrYear) {
        ntp060FrYear__ = ntp060FrYear;
    }
    /**
     * <p>ntp060InitFlg を取得します。
     * @return ntp060InitFlg
     */
    public int getNtp060InitFlg() {
        return ntp060InitFlg__;
    }
    /**
     * <p>ntp060InitFlg をセットします。
     * @param ntp060InitFlg ntp060InitFlg
     */
    public void setNtp060InitFlg(int ntp060InitFlg) {
        ntp060InitFlg__ = ntp060InitFlg;
    }
    /**
     * <p>ntp060Mikomi を取得します。
     * @return ntp060Mikomi
     */
    public String[] getNtp060Mikomi() {
        return ntp060Mikomi__;
    }
    /**
     * <p>ntp060Mikomi をセットします。
     * @param ntp060Mikomi ntp060Mikomi
     */
    public void setNtp060Mikomi(String[] ntp060Mikomi) {
        ntp060Mikomi__ = ntp060Mikomi;
    }
    /**
     * <p>ntp060NanCode を取得します。
     * @return ntp060NanCode
     */
    public String getNtp060NanCode() {
        return ntp060NanCode__;
    }
    /**
     * <p>ntp060NanCode をセットします。
     * @param ntp060NanCode ntp060NanCode
     */
    public void setNtp060NanCode(String ntp060NanCode) {
        ntp060NanCode__ = ntp060NanCode;
    }
    /**
     * <p>ntp060NanKinJutyu を取得します。
     * @return ntp060NanKinJutyu
     */
    public String getNtp060NanKinJutyu() {
        return ntp060NanKinJutyu__;
    }
    /**
     * <p>ntp060NanKinJutyu をセットします。
     * @param ntp060NanKinJutyu ntp060NanKinJutyu
     */
    public void setNtp060NanKinJutyu(String ntp060NanKinJutyu) {
        ntp060NanKinJutyu__ = ntp060NanKinJutyu;
    }
    /**
     * <p>ntp060NanKinJutyuKbn を取得します。
     * @return ntp060NanKinJutyuKbn
     */
    public int getNtp060NanKinJutyuKbn() {
        return ntp060NanKinJutyuKbn__;
    }
    /**
     * <p>ntp060NanKinJutyuKbn をセットします。
     * @param ntp060NanKinJutyuKbn ntp060NanKinJutyuKbn
     */
    public void setNtp060NanKinJutyuKbn(int ntp060NanKinJutyuKbn) {
        ntp060NanKinJutyuKbn__ = ntp060NanKinJutyuKbn;
    }
    /**
     * <p>ntp060NanKinMitumori を取得します。
     * @return ntp060NanKinMitumori
     */
    public String getNtp060NanKinMitumori() {
        return ntp060NanKinMitumori__;
    }
    /**
     * <p>ntp060NanKinMitumori をセットします。
     * @param ntp060NanKinMitumori ntp060NanKinMitumori
     */
    public void setNtp060NanKinMitumori(String ntp060NanKinMitumori) {
        ntp060NanKinMitumori__ = ntp060NanKinMitumori;
    }
    /**
     * <p>ntp060NanKinMitumoriKbn を取得します。
     * @return ntp060NanKinMitumoriKbn
     */
    public int getNtp060NanKinMitumoriKbn() {
        return ntp060NanKinMitumoriKbn__;
    }
    /**
     * <p>ntp060NanKinMitumoriKbn をセットします。
     * @param ntp060NanKinMitumoriKbn ntp060NanKinMitumoriKbn
     */
    public void setNtp060NanKinMitumoriKbn(int ntp060NanKinMitumoriKbn) {
        ntp060NanKinMitumoriKbn__ = ntp060NanKinMitumoriKbn;
    }
    /**
     * <p>ntp060NanName を取得します。
     * @return ntp060NanName
     */
    public String getNtp060NanName() {
        return ntp060NanName__;
    }
    /**
     * <p>ntp060NanName をセットします。
     * @param ntp060NanName ntp060NanName
     */
    public void setNtp060NanName(String ntp060NanName) {
        ntp060NanName__ = ntp060NanName;
    }
    /**
     * <p>ntp060NcnSid を取得します。
     * @return ntp060NcnSid
     */
    public int getNtp060NcnSid() {
        return ntp060NcnSid__;
    }
    /**
     * <p>ntp060NcnSid をセットします。
     * @param ntp060NcnSid ntp060NcnSid
     */
    public void setNtp060NcnSid(int ntp060NcnSid) {
        ntp060NcnSid__ = ntp060NcnSid;
    }
    /**
     * <p>ntp060NgpSid を取得します。
     * @return ntp060NgpSid
     */
    public int getNtp060NgpSid() {
        return ntp060NgpSid__;
    }
    /**
     * <p>ntp060NgpSid をセットします。
     * @param ntp060NgpSid ntp060NgpSid
     */
    public void setNtp060NgpSid(int ntp060NgpSid) {
        ntp060NgpSid__ = ntp060NgpSid;
    }
    /**
     * <p>ntp060NgySid を取得します。
     * @return ntp060NgySid
     */
    public int getNtp060NgySid() {
        return ntp060NgySid__;
    }
    /**
     * <p>ntp060NgySid をセットします。
     * @param ntp060NgySid ntp060NgySid
     */
    public void setNtp060NgySid(int ntp060NgySid) {
        ntp060NgySid__ = ntp060NgySid;
    }
    /**
     * <p>ntp060OrderKey1 を取得します。
     * @return ntp060OrderKey1
     */
    public int getNtp060OrderKey1() {
        return ntp060OrderKey1__;
    }
    /**
     * <p>ntp060OrderKey1 をセットします。
     * @param ntp060OrderKey1 ntp060OrderKey1
     */
    public void setNtp060OrderKey1(int ntp060OrderKey1) {
        ntp060OrderKey1__ = ntp060OrderKey1;
    }
    /**
     * <p>ntp060OrderKey2 を取得します。
     * @return ntp060OrderKey2
     */
    public int getNtp060OrderKey2() {
        return ntp060OrderKey2__;
    }
    /**
     * <p>ntp060OrderKey2 をセットします。
     * @param ntp060OrderKey2 ntp060OrderKey2
     */
    public void setNtp060OrderKey2(int ntp060OrderKey2) {
        ntp060OrderKey2__ = ntp060OrderKey2;
    }
    /**
     * <p>ntp060Page を取得します。
     * @return ntp060Page
     */
    public int getNtp060Page() {
        return ntp060Page__;
    }
    /**
     * <p>ntp060Page をセットします。
     * @param ntp060Page ntp060Page
     */
    public void setNtp060Page(int ntp060Page) {
        ntp060Page__ = ntp060Page;
    }
    /**
     * <p>ntp060PageBottom を取得します。
     * @return ntp060PageBottom
     */
    public int getNtp060PageBottom() {
        return ntp060PageBottom__;
    }
    /**
     * <p>ntp060PageBottom をセットします。
     * @param ntp060PageBottom ntp060PageBottom
     */
    public void setNtp060PageBottom(int ntp060PageBottom) {
        ntp060PageBottom__ = ntp060PageBottom;
    }
    /**
     * <p>ntp060PageTop を取得します。
     * @return ntp060PageTop
     */
    public int getNtp060PageTop() {
        return ntp060PageTop__;
    }
    /**
     * <p>ntp060PageTop をセットします。
     * @param ntp060PageTop ntp060PageTop
     */
    public void setNtp060PageTop(int ntp060PageTop) {
        ntp060PageTop__ = ntp060PageTop;
    }
    /**
     * <p>ntp060ShohinName を取得します。
     * @return ntp060ShohinName
     */
    public String getNtp060ShohinName() {
        return ntp060ShohinName__;
    }
    /**
     * <p>ntp060ShohinName をセットします。
     * @param ntp060ShohinName ntp060ShohinName
     */
    public void setNtp060ShohinName(String ntp060ShohinName) {
        ntp060ShohinName__ = ntp060ShohinName;
    }
    /**
     * <p>ntp060SortKey1 を取得します。
     * @return ntp060SortKey1
     */
    public int getNtp060SortKey1() {
        return ntp060SortKey1__;
    }
    /**
     * <p>ntp060SortKey1 をセットします。
     * @param ntp060SortKey1 ntp060SortKey1
     */
    public void setNtp060SortKey1(int ntp060SortKey1) {
        ntp060SortKey1__ = ntp060SortKey1;
    }
    /**
     * <p>ntp060SortKey2 を取得します。
     * @return ntp060SortKey2
     */
    public int getNtp060SortKey2() {
        return ntp060SortKey2__;
    }
    /**
     * <p>ntp060SortKey2 をセットします。
     * @param ntp060SortKey2 ntp060SortKey2
     */
    public void setNtp060SortKey2(int ntp060SortKey2) {
        ntp060SortKey2__ = ntp060SortKey2;
    }
    /**
     * <p>ntp060Syodan を取得します。
     * @return ntp060Syodan
     */
    public String[] getNtp060Syodan() {
        return ntp060Syodan__;
    }
    /**
     * <p>ntp060Syodan をセットします。
     * @param ntp060Syodan ntp060Syodan
     */
    public void setNtp060Syodan(String[] ntp060Syodan) {
        ntp060Syodan__ = ntp060Syodan;
    }
    /**
     * <p>ntp060ToDay を取得します。
     * @return ntp060ToDay
     */
    public String getNtp060ToDay() {
        return ntp060ToDay__;
    }
    /**
     * <p>ntp060ToDay をセットします。
     * @param ntp060ToDay ntp060ToDay
     */
    public void setNtp060ToDay(String ntp060ToDay) {
        ntp060ToDay__ = ntp060ToDay;
    }
    /**
     * <p>ntp060ToMonth を取得します。
     * @return ntp060ToMonth
     */
    public String getNtp060ToMonth() {
        return ntp060ToMonth__;
    }
    /**
     * <p>ntp060ToMonth をセットします。
     * @param ntp060ToMonth ntp060ToMonth
     */
    public void setNtp060ToMonth(String ntp060ToMonth) {
        ntp060ToMonth__ = ntp060ToMonth;
    }
    /**
     * <p>ntp060ToYear を取得します。
     * @return ntp060ToYear
     */
    public String getNtp060ToYear() {
        return ntp060ToYear__;
    }
    /**
     * <p>ntp060ToYear をセットします。
     * @param ntp060ToYear ntp060ToYear
     */
    public void setNtp060ToYear(String ntp060ToYear) {
        ntp060ToYear__ = ntp060ToYear;
    }
    /**
     * <p>ntp061AcoCode を取得します。
     * @return ntp061AcoCode
     */
    public String getNtp061AcoCode() {
        return ntp061AcoCode__;
    }
    /**
     * <p>ntp061AcoCode をセットします。
     * @param ntp061AcoCode ntp061AcoCode
     */
    public void setNtp061AcoCode(String ntp061AcoCode) {
        ntp061AcoCode__ = ntp061AcoCode;
    }
    /**
     * <p>ntp061ChkShohinSidList を取得します。
     * @return ntp061ChkShohinSidList
     */
    public String[] getNtp061ChkShohinSidList() {
        return ntp061ChkShohinSidList__;
    }
    /**
     * <p>ntp061ChkShohinSidList をセットします。
     * @param ntp061ChkShohinSidList ntp061ChkShohinSidList
     */
    public void setNtp061ChkShohinSidList(String[] ntp061ChkShohinSidList) {
        ntp061ChkShohinSidList__ = ntp061ChkShohinSidList;
    }

    /**
     * <p>ntp061FrDay を取得します。
     * @return ntp061FrDay
     */
    public String getNtp061FrDay() {
        return ntp061FrDay__;
    }
    /**
     * <p>ntp061FrDay をセットします。
     * @param ntp061FrDay ntp061FrDay
     */
    public void setNtp061FrDay(String ntp061FrDay) {
        ntp061FrDay__ = ntp061FrDay;
    }
    /**
     * <p>ntp061FrMonth を取得します。
     * @return ntp061FrMonth
     */
    public String getNtp061FrMonth() {
        return ntp061FrMonth__;
    }
    /**
     * <p>ntp061FrMonth をセットします。
     * @param ntp061FrMonth ntp061FrMonth
     */
    public void setNtp061FrMonth(String ntp061FrMonth) {
        ntp061FrMonth__ = ntp061FrMonth;
    }
    /**
     * <p>ntp061FrYear を取得します。
     * @return ntp061FrYear
     */
    public String getNtp061FrYear() {
        return ntp061FrYear__;
    }
    /**
     * <p>ntp061FrYear をセットします。
     * @param ntp061FrYear ntp061FrYear
     */
    public void setNtp061FrYear(String ntp061FrYear) {
        ntp061FrYear__ = ntp061FrYear;
    }
    /**
     * <p>ntp061InitFlg を取得します。
     * @return ntp061InitFlg
     */
    public int getNtp061InitFlg() {
        return ntp061InitFlg__;
    }
    /**
     * <p>ntp061InitFlg をセットします。
     * @param ntp061InitFlg ntp061InitFlg
     */
    public void setNtp061InitFlg(int ntp061InitFlg) {
        ntp061InitFlg__ = ntp061InitFlg;
    }
    /**
     * <p>ntp061NanCode を取得します。
     * @return ntp061NanCode
     */
    public String getNtp061NanCode() {
        return ntp061NanCode__;
    }
    /**
     * <p>ntp061NanCode をセットします。
     * @param ntp061NanCode ntp061NanCode
     */
    public void setNtp061NanCode(String ntp061NanCode) {
        ntp061NanCode__ = ntp061NanCode;
    }
    /**
     * <p>ntp061NanKinJutyu を取得します。
     * @return ntp061NanKinJutyu
     */
    public String getNtp061NanKinJutyu() {
        return ntp061NanKinJutyu__;
    }
    /**
     * <p>ntp061NanKinJutyu をセットします。
     * @param ntp061NanKinJutyu ntp061NanKinJutyu
     */
    public void setNtp061NanKinJutyu(String ntp061NanKinJutyu) {
        ntp061NanKinJutyu__ = ntp061NanKinJutyu;
    }
    /**
     * <p>ntp061NanKinMitumori を取得します。
     * @return ntp061NanKinMitumori
     */
    public String getNtp061NanKinMitumori() {
        return ntp061NanKinMitumori__;
    }
    /**
     * <p>ntp061NanKinMitumori をセットします。
     * @param ntp061NanKinMitumori ntp061NanKinMitumori
     */
    public void setNtp061NanKinMitumori(String ntp061NanKinMitumori) {
        ntp061NanKinMitumori__ = ntp061NanKinMitumori;
    }
    /**
     * <p>ntp061NanMikomi を取得します。
     * @return ntp061NanMikomi
     */
    public int getNtp061NanMikomi() {
        return ntp061NanMikomi__;
    }
    /**
     * <p>ntp061NanMikomi をセットします。
     * @param ntp061NanMikomi ntp061NanMikomi
     */
    public void setNtp061NanMikomi(int ntp061NanMikomi) {
        ntp061NanMikomi__ = ntp061NanMikomi;
    }
    /**
     * <p>ntp061NanName を取得します。
     * @return ntp061NanName
     */
    public String getNtp061NanName() {
        return ntp061NanName__;
    }
    /**
     * <p>ntp061NanName をセットします。
     * @param ntp061NanName ntp061NanName
     */
    public void setNtp061NanName(String ntp061NanName) {
        ntp061NanName__ = ntp061NanName;
    }
    /**
     * <p>ntp061NanSyodan を取得します。
     * @return ntp061NanSyodan
     */
    public int getNtp061NanSyodan() {
        return ntp061NanSyodan__;
    }
    /**
     * <p>ntp061NanSyodan をセットします。
     * @param ntp061NanSyodan ntp061NanSyodan
     */
    public void setNtp061NanSyodan(int ntp061NanSyodan) {
        ntp061NanSyodan__ = ntp061NanSyodan;
    }
    /**
     * <p>ntp061NanSyosai を取得します。
     * @return ntp061NanSyosai
     */
    public String getNtp061NanSyosai() {
        return ntp061NanSyosai__;
    }
    /**
     * <p>ntp061NanSyosai をセットします。
     * @param ntp061NanSyosai ntp061NanSyosai
     */
    public void setNtp061NanSyosai(String ntp061NanSyosai) {
        ntp061NanSyosai__ = ntp061NanSyosai;
    }
    /**
     * <p>ntp061NcnSid を取得します。
     * @return ntp061NcnSid
     */
    public int getNtp061NcnSid() {
        return ntp061NcnSid__;
    }
    /**
     * <p>ntp061NcnSid をセットします。
     * @param ntp061NcnSid ntp061NcnSid
     */
    public void setNtp061NcnSid(int ntp061NcnSid) {
        ntp061NcnSid__ = ntp061NcnSid;
    }
    /**
     * <p>ntp061NgpSid を取得します。
     * @return ntp061NgpSid
     */
    public int getNtp061NgpSid() {
        return ntp061NgpSid__;
    }
    /**
     * <p>ntp061NgpSid をセットします。
     * @param ntp061NgpSid ntp061NgpSid
     */
    public void setNtp061NgpSid(int ntp061NgpSid) {
        ntp061NgpSid__ = ntp061NgpSid;
    }
    /**
     * <p>ntp061NgySid を取得します。
     * @return ntp061NgySid
     */
    public int getNtp061NgySid() {
        return ntp061NgySid__;
    }
    /**
     * <p>ntp061NgySid をセットします。
     * @param ntp061NgySid ntp061NgySid
     */
    public void setNtp061NgySid(int ntp061NgySid) {
        ntp061NgySid__ = ntp061NgySid;
    }
    /**
     * <p>ntp061ReturnPage を取得します。
     * @return ntp061ReturnPage
     */
    public String getNtp061ReturnPage() {
        return ntp061ReturnPage__;
    }
    /**
     * <p>ntp061ReturnPage をセットします。
     * @param ntp061ReturnPage ntp061ReturnPage
     */
    public void setNtp061ReturnPage(String ntp061ReturnPage) {
        ntp061ReturnPage__ = ntp061ReturnPage;
    }
    /**
     * <p>ntp061TourokuName を取得します。
     * @return ntp061TourokuName
     */
    public String getNtp061TourokuName() {
        return ntp061TourokuName__;
    }
    /**
     * <p>ntp061TourokuName をセットします。
     * @param ntp061TourokuName ntp061TourokuName
     */
    public void setNtp061TourokuName(String ntp061TourokuName) {
        ntp061TourokuName__ = ntp061TourokuName;
    }

}