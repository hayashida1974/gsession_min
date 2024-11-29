package jp.groupsession.v2.adr.adr010.model;

import java.math.BigDecimal;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] プロジェクトプラグインのプロジェクトやTODO情報を取得する際使用するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010ProjectDataModel extends AbstractModel {

    /** プロジェクトSID */
    private int projectSid__;
    /** マイプロジェクト区分 */
    private int prjMyKbn__;
    /** プロジェクトID */
    private String projectId__;
    /** プロジェクト名称 */
    private String projectName__;
    /** プロジェクト略称 */
    private String projectRyakuName__;
    /** 予算 */
    private int yosan__;
    /** 予算(表示用) */
    private String strYosan__;
    /** 公開区分 */
    private int koukaiKbn__;
    /** 変更履歴SID */
    private int hisSid__;
    /** 状態区分 */
    private int status__;
    /** 状態名称 */
    private String statusName__;
    /** 進捗率 */
    private int rate__;
    /** 進捗率(未消化分) */
    private int notEndRate__;
    /** 目標・目的 */
    private String mokuhyou__;
    /** 内容 */
    private String naiyou__;
    /** TODOSID */
    private int todoSid__;
    /** 管理番号 */
    private int kanriNo__;
    /** 管理番号(表示用) */
    private String strKanriNo__;
    /** TODOタイトル */
    private String todoTitle__;
    /** 重要度 */
    private int juyo__;
    /** 重要度(表示用) */
    private String strJuyo__;
    /** 開始日付 */
    private UDate startDate__;
    /** 開始日付(表示用) */
    private String strStartDate__;
    /** 終了日付 */
    private UDate endDate__;
    /** 終了日付(表示用) */
    private String strEndDate__;
    /** 予定工数 */
    private BigDecimal yoteiKosu__;
    /** 予定工数(表示用) */
    private String strYoteiKosu__;
    /** 開始日付(実績) */
    private UDate startJissekiDate__;
    /** 開始日付(実績)(表示用) */
    private String strStartJissekiDate__;
    /** 終了日付(実績) */
    private UDate endJissekiDate__;
    /** 終了日付(実績)(表示用) */
    private String strEndJissekiDate__;
    /** 実績工数 */
    private BigDecimal jissekiKosu__;
    /** 実績工数(表示用) */
    private String strJissekiKosu__;
    /** 警告区分 */
    private int keikoku__;
    /** 編集権限 */
    private int editKengen__;
    /** カテゴリSID */
    private int categorySid__;
    /** カテゴリ */
    private String category__;
    /** 登録者SID */
    private int addUserSid__;
    /** 登録者姓 */
    private String addUserSei__;
    /** 登録者名 */
    private String addUserMei__;
    /** 登録者状態 */
    private int addUserStatus__;
//    /** メンバー */
//    private List<UserModel> memberList__;
    /** 登録者SID */
    private int prjAuid__;
    /** TODO完了件数 */
    private int prjTodoKanryoCnt__;
    /** TODO未完了件数 */
    private int prjTodoMikanryoCnt__;
    /** TODO進行中件数 */
    private int prjTodoSinkotyuCnt__;
    /** プロジェクトテンプレート名称 */
    private String prtTmpName__;
    /** ショートメール通知先 */
    private int prjMailKbn__;

    /**
     * <p>prjMailKbn を取得します。
     * @return prjMailKbn
     */
    public int getPrjMailKbn() {
        return prjMailKbn__;
    }
    /**
     * <p>prjMailKbn をセットします。
     * @param prjMailKbn prjMailKbn
     */
    public void setPrjMailKbn(int prjMailKbn) {
        prjMailKbn__ = prjMailKbn;
    }
    /**
     * <p>prjAuid を取得します。
     * @return prjAuid
     */
    public int getPrjAuid() {
        return prjAuid__;
    }
    /**
     * <p>prjAuid をセットします。
     * @param prjAuid prjAuid
     */
    public void setPrjAuid(int prjAuid) {
        prjAuid__ = prjAuid;
    }
    /**
     * <p>notEndRate を取得します。
     * @return notEndRate
     */
    public int getNotEndRate() {
        return notEndRate__;
    }
    /**
     * <p>notEndRate をセットします。
     * @param notEndRate notEndRate
     */
    public void setNotEndRate(int notEndRate) {
        notEndRate__ = notEndRate;
    }
    /**
     * <p>projectSid を取得します。
     * @return projectSid
     */
    public int getProjectSid() {
        return projectSid__;
    }
    /**
     * <p>projectSid をセットします。
     * @param projectSid projectSid
     */
    public void setProjectSid(int projectSid) {
        projectSid__ = projectSid;
    }
    /**
     * <p>projectId を取得します。
     * @return projectId
     */
    public String getProjectId() {
        return projectId__;
    }
    /**
     * <p>projectId をセットします。
     * @param projectId projectId
     */
    public void setProjectId(String projectId) {
        projectId__ = projectId;
    }
    /**
     * <p>projectName を取得します。
     * @return projectName
     */
    public String getProjectName() {
        return projectName__;
    }
    /**
     * <p>projectName をセットします。
     * @param projectName projectName
     */
    public void setProjectName(String projectName) {
        projectName__ = projectName;
    }
    /**
     * <p>statusName を取得します。
     * @return statusName
     */
    public String getStatusName() {
        return statusName__;
    }
    /**
     * <p>statusName をセットします。
     * @param statusName statusName
     */
    public void setStatusName(String statusName) {
        statusName__ = statusName;
    }
    /**
     * <p>rate を取得します。
     * @return rate
     */
    public int getRate() {
        return rate__;
    }
    /**
     * <p>rate をセットします。
     * @param rate rate
     */
    public void setRate(int rate) {
        rate__ = rate;
    }
    /**
     * <p>todoSid を取得します。
     * @return todoSid
     */
    public int getTodoSid() {
        return todoSid__;
    }
    /**
     * <p>todoSid をセットします。
     * @param todoSid todoSid
     */
    public void setTodoSid(int todoSid) {
        todoSid__ = todoSid;
    }
    /**
     * <p>kanriNo を取得します。
     * @return kanriNo
     */
    public int getKanriNo() {
        return kanriNo__;
    }
    /**
     * <p>kanriNo をセットします。
     * @param kanriNo kanriNo
     */
    public void setKanriNo(int kanriNo) {
        kanriNo__ = kanriNo;
    }
    /**
     * <p>todoTitle を取得します。
     * @return todoTitle
     */
    public String getTodoTitle() {
        return todoTitle__;
    }
    /**
     * <p>todoTitle をセットします。
     * @param todoTitle todoTitle
     */
    public void setTodoTitle(String todoTitle) {
        todoTitle__ = todoTitle;
    }
    /**
     * <p>juyo を取得します。
     * @return juyo
     */
    public int getJuyo() {
        return juyo__;
    }
    /**
     * <p>juyo をセットします。
     * @param juyo juyo
     */
    public void setJuyo(int juyo) {
        juyo__ = juyo;
    }
    /**
     * <p>startDate を取得します。
     * @return startDate
     */
    public UDate getStartDate() {
        return startDate__;
    }
    /**
     * <p>startDate をセットします。
     * @param startDate startDate
     */
    public void setStartDate(UDate startDate) {
        startDate__ = startDate;
    }
    /**
     * <p>endDate を取得します。
     * @return endDate
     */
    public UDate getEndDate() {
        return endDate__;
    }
    /**
     * <p>endDate をセットします。
     * @param endDate endDate
     */
    public void setEndDate(UDate endDate) {
        endDate__ = endDate;
    }
    /**
     * <p>keikoku を取得します。
     * @return keikoku
     */
    public int getKeikoku() {
        return keikoku__;
    }
    /**
     * <p>keikoku をセットします。
     * @param keikoku keikoku
     */
    public void setKeikoku(int keikoku) {
        keikoku__ = keikoku;
    }
//    /**
//     * <p>memberList を取得します。
//     * @return memberList
//     */
//    public List<UserModel> getMemberList() {
//        return memberList__;
//    }
//    /**
//     * <p>memberList をセットします。
//     * @param memberList memberList
//     */
//    public void setMemberList(List<UserModel> memberList) {
//        memberList__ = memberList;
//    }
    /**
     * <p>strJuyo を取得します。
     * @return strJuyo
     */
    public String getStrJuyo() {
        return strJuyo__;
    }
    /**
     * <p>strJuyo をセットします。
     * @param strJuyo strJuyo
     */
    public void setStrJuyo(String strJuyo) {
        strJuyo__ = strJuyo;
    }
    /**
     * <p>strStartDate を取得します。
     * @return strStartDate
     */
    public String getStrStartDate() {
        return strStartDate__;
    }
    /**
     * <p>strStartDate をセットします。
     * @param strStartDate strStartDate
     */
    public void setStrStartDate(String strStartDate) {
        strStartDate__ = strStartDate;
    }
    /**
     * <p>strEndDate を取得します。
     * @return strEndDate
     */
    public String getStrEndDate() {
        return strEndDate__;
    }
    /**
     * <p>strEndDate をセットします。
     * @param strEndDate strEndDate
     */
    public void setStrEndDate(String strEndDate) {
        strEndDate__ = strEndDate;
    }
    /**
     * <p>strKanriNo を取得します。
     * @return strKanriNo
     */
    public String getStrKanriNo() {
        return strKanriNo__;
    }
    /**
     * <p>strKanriNo をセットします。
     * @param strKanriNo strKanriNo
     */
    public void setStrKanriNo(String strKanriNo) {
        strKanriNo__ = strKanriNo;
    }
    /**
     * <p>projectRyakuName を取得します。
     * @return projectRyakuName
     */
    public String getProjectRyakuName() {
        return projectRyakuName__;
    }
    /**
     * <p>projectRyakuName をセットします。
     * @param projectRyakuName projectRyakuName
     */
    public void setProjectRyakuName(String projectRyakuName) {
        projectRyakuName__ = projectRyakuName;
    }
    /**
     * <p>yosan を取得します。
     * @return yosan
     */
    public int getYosan() {
        return yosan__;
    }
    /**
     * <p>yosan をセットします。
     * @param yosan yosan
     */
    public void setYosan(int yosan) {
        yosan__ = yosan;
    }
    /**
     * <p>koukaiKbn を取得します。
     * @return koukaiKbn
     */
    public int getKoukaiKbn() {
        return koukaiKbn__;
    }
    /**
     * <p>koukaiKbn をセットします。
     * @param koukaiKbn koukaiKbn
     */
    public void setKoukaiKbn(int koukaiKbn) {
        koukaiKbn__ = koukaiKbn;
    }
    /**
     * <p>mokuhyou を取得します。
     * @return mokuhyou
     */
    public String getMokuhyou() {
        return mokuhyou__;
    }
    /**
     * <p>mokuhyou をセットします。
     * @param mokuhyou mokuhyou
     */
    public void setMokuhyou(String mokuhyou) {
        mokuhyou__ = mokuhyou;
    }
    /**
     * <p>naiyou を取得します。
     * @return naiyou
     */
    public String getNaiyou() {
        return naiyou__;
    }
    /**
     * <p>naiyou をセットします。
     * @param naiyou naiyou
     */
    public void setNaiyou(String naiyou) {
        naiyou__ = naiyou;
    }
    /**
     * <p>strYosan を取得します。
     * @return strYosan
     */
    public String getStrYosan() {
        return strYosan__;
    }
    /**
     * <p>strYosan をセットします。
     * @param strYosan strYosan
     */
    public void setStrYosan(String strYosan) {
        strYosan__ = strYosan;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public int getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(int status) {
        status__ = status;
    }
    /**
     * <p>editKengen を取得します。
     * @return editKengen
     */
    public int getEditKengen() {
        return editKengen__;
    }
    /**
     * <p>editKengen をセットします。
     * @param editKengen editKengen
     */
    public void setEditKengen(int editKengen) {
        editKengen__ = editKengen;
    }
    /**
     * <p>category を取得します。
     * @return category
     */
    public String getCategory() {
        return category__;
    }
    /**
     * <p>category をセットします。
     * @param category category
     */
    public void setCategory(String category) {
        category__ = category;
    }
    /**
     * <p>startJissekiDate を取得します。
     * @return startJissekiDate
     */
    public UDate getStartJissekiDate() {
        return startJissekiDate__;
    }
    /**
     * <p>startJissekiDate をセットします。
     * @param startJissekiDate startJissekiDate
     */
    public void setStartJissekiDate(UDate startJissekiDate) {
        startJissekiDate__ = startJissekiDate;
    }
    /**
     * <p>endJissekiDate を取得します。
     * @return endJissekiDate
     */
    public UDate getEndJissekiDate() {
        return endJissekiDate__;
    }
    /**
     * <p>endJissekiDate をセットします。
     * @param endJissekiDate endJissekiDate
     */
    public void setEndJissekiDate(UDate endJissekiDate) {
        endJissekiDate__ = endJissekiDate;
    }
    /**
     * <p>addUserSid を取得します。
     * @return addUserSid
     */
    public int getAddUserSid() {
        return addUserSid__;
    }
    /**
     * <p>addUserSid をセットします。
     * @param addUserSid addUserSid
     */
    public void setAddUserSid(int addUserSid) {
        addUserSid__ = addUserSid;
    }
    /**
     * <p>strStartJissekiDate を取得します。
     * @return strStartJissekiDate
     */
    public String getStrStartJissekiDate() {
        return strStartJissekiDate__;
    }
    /**
     * <p>strStartJissekiDate をセットします。
     * @param strStartJissekiDate strStartJissekiDate
     */
    public void setStrStartJissekiDate(String strStartJissekiDate) {
        strStartJissekiDate__ = strStartJissekiDate;
    }
    /**
     * <p>strEndJissekiDate を取得します。
     * @return strEndJissekiDate
     */
    public String getStrEndJissekiDate() {
        return strEndJissekiDate__;
    }
    /**
     * <p>strEndJissekiDate をセットします。
     * @param strEndJissekiDate strEndJissekiDate
     */
    public void setStrEndJissekiDate(String strEndJissekiDate) {
        strEndJissekiDate__ = strEndJissekiDate;
    }
    /**
     * <p>categorySid を取得します。
     * @return categorySid
     */
    public int getCategorySid() {
        return categorySid__;
    }
    /**
     * <p>categorySid をセットします。
     * @param categorySid categorySid
     */
    public void setCategorySid(int categorySid) {
        categorySid__ = categorySid;
    }
    /**
     * <p>addUserSei を取得します。
     * @return addUserSei
     */
    public String getAddUserSei() {
        return addUserSei__;
    }
    /**
     * <p>addUserSei をセットします。
     * @param addUserSei addUserSei
     */
    public void setAddUserSei(String addUserSei) {
        addUserSei__ = addUserSei;
    }
    /**
     * <p>addUserMei を取得します。
     * @return addUserMei
     */
    public String getAddUserMei() {
        return addUserMei__;
    }
    /**
     * <p>addUserMei をセットします。
     * @param addUserMei addUserMei
     */
    public void setAddUserMei(String addUserMei) {
        addUserMei__ = addUserMei;
    }
    /**
     * <p>addUserStatus を取得します。
     * @return addUserStatus
     */
    public int getAddUserStatus() {
        return addUserStatus__;
    }
    /**
     * <p>addUserStatus をセットします。
     * @param addUserStatus addUserStatus
     */
    public void setAddUserStatus(int addUserStatus) {
        addUserStatus__ = addUserStatus;
    }
    /**
     * <p>hisSid を取得します。
     * @return hisSid
     */
    public int getHisSid() {
        return hisSid__;
    }
    /**
     * <p>hisSid をセットします。
     * @param hisSid hisSid
     */
    public void setHisSid(int hisSid) {
        hisSid__ = hisSid;
    }
    /**
     * <p>strYoteiKosu を取得します。
     * @return strYoteiKosu
     */
    public String getStrYoteiKosu() {
        return strYoteiKosu__;
    }
    /**
     * <p>strYoteiKosu をセットします。
     * @param strYoteiKosu strYoteiKosu
     */
    public void setStrYoteiKosu(String strYoteiKosu) {
        strYoteiKosu__ = strYoteiKosu;
    }
    /**
     * <p>strJissekiKosu を取得します。
     * @return strJissekiKosu
     */
    public String getStrJissekiKosu() {
        return strJissekiKosu__;
    }
    /**
     * <p>strJissekiKosu をセットします。
     * @param strJissekiKosu strJissekiKosu
     */
    public void setStrJissekiKosu(String strJissekiKosu) {
        strJissekiKosu__ = strJissekiKosu;
    }
    /**
     * <p>yoteiKosu を取得します。
     * @return yoteiKosu
     */
    public BigDecimal getYoteiKosu() {
        return yoteiKosu__;
    }
    /**
     * <p>yoteiKosu をセットします。
     * @param yoteiKosu yoteiKosu
     */
    public void setYoteiKosu(BigDecimal yoteiKosu) {
        yoteiKosu__ = yoteiKosu;
    }
    /**
     * <p>jissekiKosu を取得します。
     * @return jissekiKosu
     */
    public BigDecimal getJissekiKosu() {
        return jissekiKosu__;
    }
    /**
     * <p>jissekiKosu をセットします。
     * @param jissekiKosu jissekiKosu
     */
    public void setJissekiKosu(BigDecimal jissekiKosu) {
        jissekiKosu__ = jissekiKosu;
    }
    /**
     * <p>prjMyKbn を取得します。
     * @return prjMyKbn
     */
    public int getPrjMyKbn() {
        return prjMyKbn__;
    }
    /**
     * <p>prjMyKbn をセットします。
     * @param prjMyKbn prjMyKbn
     */
    public void setPrjMyKbn(int prjMyKbn) {
        prjMyKbn__ = prjMyKbn;
    }
    /**
     * <p>prjTodoKanryoCnt を取得します。
     * @return prjTodoKanryoCnt
     */
    public int getPrjTodoKanryoCnt() {
        return prjTodoKanryoCnt__;
    }
    /**
     * <p>prjTodoKanryoCnt をセットします。
     * @param prjTodoKanryoCnt prjTodoKanryoCnt
     */
    public void setPrjTodoKanryoCnt(int prjTodoKanryoCnt) {
        prjTodoKanryoCnt__ = prjTodoKanryoCnt;
    }
    /**
     * <p>prjTodoMikanryoCnt を取得します。
     * @return prjTodoMikanryoCnt
     */
    public int getPrjTodoMikanryoCnt() {
        return prjTodoMikanryoCnt__;
    }
    /**
     * <p>prjTodoMikanryoCnt をセットします。
     * @param prjTodoMikanryoCnt prjTodoMikanryoCnt
     */
    public void setPrjTodoMikanryoCnt(int prjTodoMikanryoCnt) {
        prjTodoMikanryoCnt__ = prjTodoMikanryoCnt;
    }
    /**
     * <p>prjTodoSinkotyuCnt を取得します。
     * @return prjTodoSinkotyuCnt
     */
    public int getPrjTodoSinkotyuCnt() {
        return prjTodoSinkotyuCnt__;
    }
    /**
     * <p>prjTodoSinkotyuCnt をセットします。
     * @param prjTodoSinkotyuCnt prjTodoSinkotyuCnt
     */
    public void setPrjTodoSinkotyuCnt(int prjTodoSinkotyuCnt) {
        prjTodoSinkotyuCnt__ = prjTodoSinkotyuCnt;
    }
    /**
     * <p>prtTmpName を取得します。
     * @return prtTmpName
     */
    public String getPrtTmpName() {
        return prtTmpName__;
    }
    /**
     * <p>prtTmpName をセットします。
     * @param prtTmpName prtTmpName
     */
    public void setPrtTmpName(String prtTmpName) {
        prtTmpName__ = prtTmpName;
    }
}