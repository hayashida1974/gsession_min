package jp.groupsession.v2.rng.model;

import java.io.Serializable;

/**
 * <br>[機  能] 稟議 自動/手動削除条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngDeleteModel implements Serializable {
    /** 削除種別 申請中 */
    public static final int DELTYPE_PENDING = 1;
    /** 削除種別 完了 */
    public static final int DELTYPE_COMPLETE = 2;
    /** 削除種別 草稿 */
    public static final int DELTYPE_DRAFT = 3;

    /** 削除種別(申請中 or 完了 or 草稿) */
    private int delType__ = 0;
    /** 年 */
    private int delYear__ = 0;
    /** 月 */
    private int delMonth__ = 0;
    /** 日 */
    private int delDay__ = 0;
    /**
     * <p>delType を取得します。
     * @return delType
     */
    public int getDelType() {
        return delType__;
    }
    /**
     * <p>delType をセットします。
     * @param delType delType
     */
    public void setDelType(int delType) {
        delType__ = delType;
    }
    /**
     * <p>delYear を取得します。
     * @return delYear
     */
    public int getDelYear() {
        return delYear__;
    }
    /**
     * <p>delYear をセットします。
     * @param delYear delYear
     */
    public void setDelYear(int delYear) {
        delYear__ = delYear;
    }
    /**
     * <p>delMonth を取得します。
     * @return delMonth
     */
    public int getDelMonth() {
        return delMonth__;
    }
    /**
     * <p>delMonth をセットします。
     * @param delMonth delMonth
     */
    public void setDelMonth(int delMonth) {
        delMonth__ = delMonth;
    }
    /**
     * <p>delDay を取得します。
     * @return delDay
     */
    public int getDelDay() {
        return delDay__;
    }
    /**
     * <p>delDay をセットします。
     * @param delDay delDay
     */
    public void setDelDay(int delDay) {
        delDay__ = delDay;
    }
}
