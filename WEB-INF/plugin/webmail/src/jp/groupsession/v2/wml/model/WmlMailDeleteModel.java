package jp.groupsession.v2.wml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>[機  能] WEBメール メール情報削除条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMailDeleteModel implements Serializable {

    /** ディレクトリ区分 */
    private List<Integer> manuDelDirList__ = null;
    /** 年 */
    private int manuDelYear__ = 0;
    /** 月 */
    private int manuDelMonth__ = 0;
    /** 日 */
    private int manuDelDay__ = 0;
    /** アカウントSID */
    private int wacSid__ = 0;
    /** 全てのアカウントを対象とするか */
    private boolean delAllAccount__ = false;
    /** 日時を削除条件とするか */
    private boolean useDate__ = true;

    /**
     * <p>コンストラクタ
     */
    public WmlMailDeleteModel() {
        manuDelDirList__ = new ArrayList<Integer>();
    }

    /**
     * <p>manuDelDirList を取得します。
     * @return manuDelDirList
     */
    public List<Integer> getManuDelDirList() {
        return manuDelDirList__;
    }
    /**
     * <p>manuDelDirList をセットします。
     * @param manuDelDirList manuDelDirList
     */
    public void setManuDelDirList(List<Integer> manuDelDirList) {
        manuDelDirList__ = manuDelDirList;
    }
    /**
     * <p>manuDelDir をセットします。
     * @param manuDelDir manuDelDir
     */
    public void setManuDelDir(int manuDelDir) {
        manuDelDirList__.add(manuDelDir);
    }
    /**
     * <p>manuDelDay を取得します。
     * @return manuDelDay
     */
    public int getManuDelDay() {
        return manuDelDay__;
    }
    /**
     * <p>manuDelDay をセットします。
     * @param manuDelDay manuDelDay
     */
    public void setManuDelDay(int manuDelDay) {
        manuDelDay__ = manuDelDay;
    }
    /**
     * <p>manuDelMonth を取得します。
     * @return manuDelMonth
     */
    public int getManuDelMonth() {
        return manuDelMonth__;
    }
    /**
     * <p>manuDelMonth をセットします。
     * @param manuDelMonth manuDelMonth
     */
    public void setManuDelMonth(int manuDelMonth) {
        manuDelMonth__ = manuDelMonth;
    }
    /**
     * <p>manuDelYear を取得します。
     * @return manuDelYear
     */
    public int getManuDelYear() {
        return manuDelYear__;
    }
    /**
     * <p>manuDelYear をセットします。
     * @param manuDelYear manuDelYear
     */
    public void setManuDelYear(int manuDelYear) {
        manuDelYear__ = manuDelYear;
    }
    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * <p>useDate を取得します。
     * @return useDate
     */
    public boolean isUseDate() {
        return useDate__;
    }
    /**
     * <p>useDate をセットします。
     * @param useDate useDate
     */
    public void setUseDate(boolean useDate) {
        useDate__ = useDate;
    }
    /**
     * <p>delAllAccount を取得します。
     * @return delAllAccount
     */
    public boolean isDelAllAccount() {
        return delAllAccount__;
    }
    /**
     * <p>delAllAccount をセットします。
     * @param delAllAccount delAllAccount
     */
    public void setDelAllAccount(boolean delAllAccount) {
        delAllAccount__ = delAllAccount;
    }
}
