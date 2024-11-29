package jp.groupsession.v2.bbs.bbs070;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs170.Bbs170ParamModel;
import jp.groupsession.v2.cmn.ITempDirIdUseable;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 掲示板 スレッド登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs070ParamModel extends Bbs170ParamModel  implements ITempDirIdUseable {

    /** 処理モード */
    private int bbs070cmdMode__ = 0;

    /** タイトル */
    private String bbs070title__ = null;
    /** 内容 */
    private String bbs070value__ = null;
    /** 内容(HTML) */
    private String bbs070valueHtml__ = null;
    /** 添付ファイル */
    private String[] bbs070files__ = null;
    /** 掲載期限 無効初期値 */
    private int bbs070limitDisable__ = GSConstBulletin.THREAD_DISABLE;
    /** 掲載期限 例外(無効+掲載期限有り) */
    private int bbs070limitException__ = GSConstBulletin.THREAD_NOEXCEPTION;
    /** 掲載期限の有無 */
    private int bbs070limit__ = GSConstBulletin.THREAD_LIMIT_NO;
    /** 掲載開始 年 */
    private int bbs070limitFrYear__ = -1;
    /** 掲載開始 月 */
    private int bbs070limitFrMonth__ = 1;
    /** 掲載開始 日 */
    private int bbs070limitFrDay__ = 1;
    /** 掲載開始 時 */
    private int bbs070limitFrHour__ = -1;
    /** 掲載開始 分 */
    private int bbs070limitFrMinute__ = -1;
    /** 掲載終了 年 */
    private int bbs070limitYear__ = -1;
    /** 掲載終了 月 */
    private int bbs070limitMonth__ = 1;
    /** 掲載終了 日 */
    private int bbs070limitDay__ = 1;
    /** 掲載終了 時 */
    private int bbs070limitHour__ = -1;
    /** 掲載終了 分 */
    private int bbs070limitMinute__ = -1;
    
    /** 掲載開始 年月日 */
    private String bbs070limitFrDate__ = null;
    /** 掲載開始 時間 */
    private String bbs070limitFrTime__ = null;
    /** 掲載終了 年月日 */
    private String bbs070limitToDate__ = null;
    /** 掲載終了 時間 */
    private String bbs070limitToTime__ = null;
    /** 掲載開始 最古年*/
    private int bbs070oldYear__ = 0;
    /** 掲載 分コンボ 単位 */
    private int bbs070hourDivision__ = 1;
    
    /** 投稿者 */
    private int bbs070contributor__ = 0;
    /** 投稿者コンボ */
    private List <UsrLabelValueBean> bbs070contributorList__ = null;
    /** 投稿者変更権限 */
    private int bbs070contributorEditKbn__ = 1;
    /** 投稿者削除区分 */
    private int bbs070contributorJKbn__ = GSConstUser.USER_JTKBN_ACTIVE;

    /** フォーラム名 */
    private String bbs070forumName__ = null;
    /** ファイルコンボ */
    private List <LabelValueBean> bbs070FileLabelList__ = null;

    /** 掲示予定遷移フラグ */
    private String bbs070BackID__ = null;

    /** 元データ過去・現在⇒未来フラグ */
    private int bbs070changeDateFlg__ = 0;

    /** TempDirId*/
    private String tempDirId__ = null;

    /** 重要度 */
    private int bbs070Importance__ = 0;

    /** 本文ファイルの一時保存ディレクトリID */
    private String bbs070TempSaveId__ = null;

    /** 投稿本文のタイプ */
    private int bbs070valueType__ = GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN;

    /** 無期限表示フラグ*/
    private int bbs070Unlimited__ = GSConstBulletin.BFI_UNLIMITED_YES;

    /**
     * @return bbs070cmdMode を戻します。
     */
    public int getBbs070cmdMode() {
        return bbs070cmdMode__;
    }
    /**
     * @param bbs070cmdMode 設定する bbs070cmdMode。
     */
    public void setBbs070cmdMode(int bbs070cmdMode) {
        bbs070cmdMode__ = bbs070cmdMode;
    }
    /**
     * @return bbs070files を戻します。
     */
    public String[] getBbs070files() {
        return bbs070files__;
    }
    /**
     * @param bbs070files 設定する bbs070files。
     */
    public void setBbs070files(String[] bbs070files) {
        bbs070files__ = bbs070files;
    }
    /**
     * <p>bbs070limitDisable を取得します。
     * @return bbs070limitDisable
     */
    public int getBbs070limitDisable() {
        return bbs070limitDisable__;
    }
    /**
     * <p>bbs070limitDisable をセットします。
     * @param bbs070limitDisable bbs070limitDisable
     */
    public void setBbs070limitDisable(int bbs070limitDisable) {
        bbs070limitDisable__ = bbs070limitDisable;
    }
    /**
     * <p>bbs070limitException を取得します。
     * @return bbs070limitException
     */
    public int getBbs070limitException() {
        return bbs070limitException__;
    }
    /**
     * <p>bbs070limitException をセットします。
     * @param bbs070limitException bbs070limitException
     */
    public void setBbs070limitException(int bbs070limitException) {
        bbs070limitException__ = bbs070limitException;
    }
    /**
     * @return bbs070title を戻します。
     */
    public String getBbs070title() {
        return bbs070title__;
    }
    /**
     * @param bbs070title 設定する bbs070title。
     */
    public void setBbs070title(String bbs070title) {
        bbs070title__ = bbs070title;
    }
    /**
     * @return bbs070value を戻します。
     */
    public String getBbs070value() {
        return bbs070value__;
    }
    /**
     * @param bbs070value 設定する bbs070value。
     */
    public void setBbs070value(String bbs070value) {
        bbs070value__ = bbs070value;
    }
    /**
     * <p>bbs070valueHtml を取得します。
     * @return bbs070valueHtml
     */
    public String getBbs070valueHtml() {
        return bbs070valueHtml__;
    }
    /**
     * <p>bbs070valueHtml をセットします。
     * @param bbs070valueHtml bbs070valueHtml
     */
    public void setBbs070valueHtml(String bbs070valueHtml) {
        bbs070valueHtml__ = bbs070valueHtml;
    }
    /**
     * @return bbs070forumName を戻します。
     */
    public String getBbs070forumName() {
        return bbs070forumName__;
    }
    /**
     * @param bbs070forumName 設定する bbs070forumName。
     */
    public void setBbs070forumName(String bbs070forumName) {
        bbs070forumName__ = bbs070forumName;
    }
    /**
     * <p>bbs070limit を取得します。
     * @return bbs070limit
     */
    public int getBbs070limit() {
        return bbs070limit__;
    }
    /**
     * <p>bbs070limit をセットします。
     * @param bbs070limit bbs070limit
     */
    public void setBbs070limit(int bbs070limit) {
        bbs070limit__ = bbs070limit;
    }
    /**
     * <p>bbs070limitDay を取得します。
     * @return bbs070limitDay
     */
    public int getBbs070limitDay() {
        return bbs070limitDay__;
    }
    /**
     * <p>bbs070limitDay をセットします。
     * @param bbs070limitDay bbs070limitDay
     */
    public void setBbs070limitDay(int bbs070limitDay) {
        bbs070limitDay__ = bbs070limitDay;
    }
    /**
     * <p>bbs070limitMonth を取得します。
     * @return bbs070limitMonth
     */
    public int getBbs070limitMonth() {
        return bbs070limitMonth__;
    }
    /**
     * <p>bbs070limitMonth をセットします。
     * @param bbs070limitMonth bbs070limitMonth
     */
    public void setBbs070limitMonth(int bbs070limitMonth) {
        bbs070limitMonth__ = bbs070limitMonth;
    }
    /**
     * <p>bbs070limitYear を取得します。
     * @return bbs070limitYear
     */
    public int getBbs070limitYear() {
        return bbs070limitYear__;
    }
    /**
     * <p>bbs070limitYear をセットします。
     * @param bbs070limitYear bbs070limitYear
     */
    public void setBbs070limitYear(int bbs070limitYear) {
        bbs070limitYear__ = bbs070limitYear;
    }
    /**
    * <p>bbs070limitHourを取得します。
    * @return  bbs070limitHour
    * */
   public int getBbs070limitHour() {
       return bbs070limitHour__;
   }
   /**
    * <p>bbs070limitHourをセットします。
    * @param bbs070limitHour bbs070limitHour
    * */
   public void setBbs070limitHour(int bbs070limitHour) {
       bbs070limitHour__ = bbs070limitHour;
   }
   /**
    * <p>bbs070limitMinuteを取得します。
    * @return  bbs070limitMinute
    * */
   public int getBbs070limitMinute() {
       return bbs070limitMinute__;
   }
   /**
    * <p>bbs070limitMinuteをセットします。
    * @param bbs070limitMinute bbs070limitMinute
    * */
   public void setBbs070limitMinute(int bbs070limitMinute) {
       bbs070limitMinute__ = bbs070limitMinute;
   }
   /**
    * <p>bbs070limitFrDate を取得します。
    * @return bbs070limitFrDate
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitFrDate__
    */
   public String getBbs070limitFrDate() {
       return bbs070limitFrDate__;
   }
   /**
    * <p>bbs070limitFrDate をセットします。
    * @param bbs070limitFrDate bbs070limitFrDate
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitFrDate__
    */
   public void setBbs070limitFrDate(String bbs070limitFrDate) {
       bbs070limitFrDate__ = bbs070limitFrDate;
   }
   /**
    * <p>bbs070limitFrTime を取得します。
    * @return bbs070limitFrTime
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitFrTime__
    */
   public String getBbs070limitFrTime() {
       return bbs070limitFrTime__;
   }
   /**
    * <p>bbs070limitFrTime をセットします。
    * @param bbs070limitFrTime bbs070limitFrTime
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitFrTime__
    */
   public void setBbs070limitFrTime(String bbs070limitFrTime) {
       bbs070limitFrTime__ = bbs070limitFrTime;
   }
   /**
    * <p>bbs070limitToDate を取得します。
    * @return bbs070limitToDate
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitToDate__
    */
   public String getBbs070limitToDate() {
       return bbs070limitToDate__;
   }
   /**
    * <p>bbs070limitToDate をセットします。
    * @param bbs070limitToDate bbs070limitToDate
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitToDate__
    */
   public void setBbs070limitToDate(String bbs070limitToDate) {
       bbs070limitToDate__ = bbs070limitToDate;
   }
   /**
    * <p>bbs070limitToTime を取得します。
    * @return bbs070limitToTime
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitToTime__
    */
   public String getBbs070limitToTime() {
       return bbs070limitToTime__;
   }
   /**
    * <p>bbs070limitToTime をセットします。
    * @param bbs070limitToTime bbs070limitToTime
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070limitToTime__
    */
   public void setBbs070limitToTime(String bbs070limitToTime) {
       bbs070limitToTime__ = bbs070limitToTime;
   }
   /**
    * <p>bbs070oldYear を取得します。
    * @return bbs070oldYear
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070oldYear__
    */
   public int getBbs070oldYear() {
       return bbs070oldYear__;
   }
   /**
    * <p>bbs070oldYear をセットします。
    * @param bbs070oldYear bbs070oldYear
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070oldYear__
    */
   public void setBbs070oldYear(int bbs070oldYear) {
       bbs070oldYear__ = bbs070oldYear;
   }
   /**
    * <p>bbs070hourDivision を取得します。
    * @return bbs070hourDivision
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070ParamModel#bbs070hourDivision__
    */
   public int getBbs070hourDivision() {
       return bbs070hourDivision__;
   }
   /**
    * <p>bbs070hourDivision をセットします。
    * @param bbs070hourDivision bbs070hourDivision
    * @see jp.groupsession.v2.bbs.bbs070.Bbs070ParamModel#bbs070hourDivision__
    */
   public void setBbs070hourDivision(int bbs070hourDivision) {
       bbs070hourDivision__ = bbs070hourDivision;
   }
    /**
     * @return bbs070FileLabelList を戻します。
     */
    public List < LabelValueBean > getBbs070FileLabelList() {
        return bbs070FileLabelList__;
    }
    /**
     * @param bbs070FileLabelList 設定する bbs070FileLabelList。
     */
    public void setBbs070FileLabelList(List < LabelValueBean > bbs070FileLabelList) {
        bbs070FileLabelList__ = bbs070FileLabelList;
    }
    /**
     * <p>bbs070contributor を取得します。
     * @return bbs070contributor
     */
    public int getBbs070contributor() {
        return bbs070contributor__;
    }
    /**
     * <p>bbs070contributor をセットします。
     * @param bbs070contributor bbs070contributor
     */
    public void setBbs070contributor(int bbs070contributor) {
        bbs070contributor__ = bbs070contributor;
    }
    /**
     * <p>bbs070contributorList を取得します。
     * @return bbs070contributorList
     */
    public List<UsrLabelValueBean> getBbs070contributorList() {
        return bbs070contributorList__;
    }
    /**
     * <p>bbs070contributorList をセットします。
     * @param bbs070contributorList bbs070contributorList
     */
    public void setBbs070contributorList(List<UsrLabelValueBean> bbs070contributorList) {
        bbs070contributorList__ = bbs070contributorList;
    }
    /**
     * <p>bbs070contributorEditKbn を取得します。
     * @return bbs070contributorEditKbn
     */
    public int getBbs070contributorEditKbn() {
        return bbs070contributorEditKbn__;
    }
    /**
     * <p>bbs070contributorEditKbn をセットします。
     * @param bbs070contributorEditKbn bbs070contributorEditKbn
     */
    public void setBbs070contributorEditKbn(int bbs070contributorEditKbn) {
        bbs070contributorEditKbn__ = bbs070contributorEditKbn;
    }
    /**
     * <p>bbs070contributorJKbn を取得します。
     * @return bbs070contributorJKbn
     */
    public int getBbs070contributorJKbn() {
        return bbs070contributorJKbn__;
    }
    /**
     * <p>bbs070contributorJKbn をセットします。
     * @param bbs070contributorJKbn bbs070contributorJKbn
     */
    public void setBbs070contributorJKbn(int bbs070contributorJKbn) {
        bbs070contributorJKbn__ = bbs070contributorJKbn;
    }
    /**
     * <p>bbs070limitFrYear を取得します。
     * @return bbs070limitFrYear
     */
    public int getBbs070limitFrYear() {
        return bbs070limitFrYear__;
    }
    /**
     * <p>bbs070limitFrYear をセットします。
     * @param bbs070limitFrYear bbs070limitFrYear
     */
    public void setBbs070limitFrYear(int bbs070limitFrYear) {
        bbs070limitFrYear__ = bbs070limitFrYear;
    }
    /**
     * <p>bbs070limitFrMonth を取得します。
     * @return bbs070limitFrMonth
     */
    public int getBbs070limitFrMonth() {
        return bbs070limitFrMonth__;
    }
    /**
     * <p>bbs070limitFrMonth をセットします。
     * @param bbs070limitFrMonth bbs070limitFrMonth
     */
    public void setBbs070limitFrMonth(int bbs070limitFrMonth) {
        bbs070limitFrMonth__ = bbs070limitFrMonth;
    }
    /**
     * <p>bbs070limitFrDay を取得します。
     * @return bbs070limitFrDay
     */
    public int getBbs070limitFrDay() {
        return bbs070limitFrDay__;
    }
    /**
     * <p>bbs070limitFrDay をセットします。
     * @param bbs070limitFrDay bbs070limitFrDay
     */
    public void setBbs070limitFrDay(int bbs070limitFrDay) {
        bbs070limitFrDay__ = bbs070limitFrDay;
    }
    /**
     * <p>bbs070limitFrHourを取得します。
     * @return  bbs070limitFrHour
     * */
    public int getBbs070limitFrHour() {
        return bbs070limitFrHour__;
    }
    /**
     * <p>bbs070limitFrHourをセットします。
     * @param bbs070limitFrHour bbs070limitFrHour
     * */
    public void setBbs070limitFrHour(int bbs070limitFrHour) {
        bbs070limitFrHour__ = bbs070limitFrHour;
    }
    /**
     * <p>bbs070limitFrMinuteを取得します。
     * @return  bbs070limitFrMinute
     * */
    public int getBbs070limitFrMinute() {
        return bbs070limitFrMinute__;
    }
    /**
     * <p>bbs070limitFrMinuteをセットします。
     * @param bbs070limitFrMinute bbs070limitFrMinute
     * */
    public void setBbs070limitFrMinute(int bbs070limitFrMinute) {
        bbs070limitFrMinute__ = bbs070limitFrMinute;
    }
    /**
     * <p>bbs070BackID を取得します。
     * @return bbs070BackID
     */
    public String getBbs070BackID() {
        return bbs070BackID__;
    }
    /**
     * <p>bbs070BackID をセットします。
     * @param bbs070BackID bbs070BackID
     */
    public void setBbs070BackID(String bbs070BackID) {
        bbs070BackID__ = bbs070BackID;
    }
    /**
     * <p>bbs070changeDateFlg を取得します。
     * @return bbs070changeDateFlg
     * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070changeDateFlg__
     */
    public int getBbs070changeDateFlg() {
        return bbs070changeDateFlg__;
    }
    /**
     * <p>bbs070changeDateFlg をセットします。
     * @param bbs070changeDateFlg bbs070changeDateFlg
     * @see jp.groupsession.v2.bbs.bbs070.Bbs070Form#bbs070changeDateFlg__
     */
    public void setBbs070changeDateFlg(int bbs070changeDateFlg) {
        bbs070changeDateFlg__ = bbs070changeDateFlg;
    }
    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     */
    public String getTempDirId() {
        return tempDirId__;
    }
    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     */
    public void setTempDirId(String tempDirId) {
        tempDirId__ = tempDirId;
    }
    /**
     * <p>bbs070Importance を取得します。
     * @return bbs070Importance
     */
    public int getBbs070Importance() {
        return bbs070Importance__;
    }
    /**
     * <p>bbs070Importance をセットします。
     * @param bbs070Importance bbs070Importance
     */
    public void setBbs070Importance(int bbs070Importance) {
        bbs070Importance__ = bbs070Importance;
    }
    /**
     * <p>bbs070TempSaveId を取得します。
     * @return bbs070TempSaveId
     */
    public String getBbs070TempSaveId() {
        return bbs070TempSaveId__;
    }
    /**
     * <p>bbs070TempSaveId をセットします。
     * @param bbs070TempSaveId bbs070TempSaveId
     */
    public void setBbs070TempSaveId(String bbs070TempSaveId) {
        bbs070TempSaveId__ = bbs070TempSaveId;
    }
    /**
     * <p>bbs070valueType を取得します。
     * @return bbs070valueType
     */
    public int getBbs070valueType() {
        return bbs070valueType__;
    }
    /**
     * <p>bbs070valueType をセットします。
     * @param bbs070valueType bbs070valueType
     */
    public void setBbs070valueType(int bbs070valueType) {
        bbs070valueType__ = bbs070valueType;
    }
    /**
     * <p>bbs070Unlimited を取得します。
     * @return bbs070Unlimited
     * @see jp.groupsession.v2.bbs.bbs070.Bbs070ParamModel#bbs070Unlimited__
     */
    public int getBbs070Unlimited() {
        return bbs070Unlimited__;
    }
    /**
     * <p>bbs070Unlimited をセットします。
     * @param bbs070Unlimited bbs070Unlimited
     * @see jp.groupsession.v2.bbs.bbs070.Bbs070ParamModel#bbs070Unlimited__
     */
    public void setBbs070Unlimited(int bbs070Unlimited) {
        bbs070Unlimited__ = bbs070Unlimited;
    }
}
