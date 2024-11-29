package jp.groupsession.v2.sml.sml260;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメール アカウントインポート 取込みファイル(CSV)の情報を格納するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class SmailCsvModel extends AbstractModel
implements Comparator<SmailCsvModel> {


    /** 行番号 */
    private long rowNum__ = 0;
    /** 項目数 */
    private int elementCount__ = 0;
    /** アカウント名 */
    private String accountName__ = null;
    /** 備考 */
    private String biko__ = null;
    /** 送信メール形式 */
    private String sndMailType__ = null;

    /** 使用ユーザ1 */
    private String user1__ = null;
    /** 使用ユーザ2 */
    private String user2__ = null;
    /** 使用ユーザ3 */
    private String user3__ = null;
    /** 使用ユーザ4 */
    private String user4__ = null;
    /** 使用ユーザ5 */
    private String user5__ = null;

    /** 使用グループ1 */
    private String group1__ = null;
    /** 使用グループ2 */
    private String group2__ = null;
    /** 使用グループ3 */
    private String group3__ = null;
    /** 使用グループ4 */
    private String group4__ = null;
    /** 使用グループ5 */
    private String group5__ = null;

    /**
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }
    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }

    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }

    /**
     * <p>elementCount を取得します。
     * @return elementCount
     */
    public int getElementCount() {
        return elementCount__;
    }
    /**
     * <p>elementCount をセットします。
     * @param elementCount elementCount
     */
    public void setElementCount(int elementCount) {
        elementCount__ = elementCount;
    }

    /**
     * <p>rowNum を取得します。
     * @return rowNum
     */
    public long getRowNum() {
        return rowNum__;
    }
    /**
     * <p>rowNum をセットします。
     * @param rowNum rowNum
     */
    public void setRowNum(long rowNum) {
        rowNum__ = rowNum;
    }

    /**
     * <p>user1 を取得します。
     * @return user1
     */
    public String getUser1() {
        return user1__;
    }
    /**
     * <p>user1 をセットします。
     * @param user1 user1
     */
    public void setUser1(String user1) {
        user1__ = user1;
    }
    /**
     * <p>user2 を取得します。
     * @return user2
     */
    public String getUser2() {
        return user2__;
    }
    /**
     * <p>user2 をセットします。
     * @param user2 user2
     */
    public void setUser2(String user2) {
        user2__ = user2;
    }
    /**
     * <p>user3 を取得します。
     * @return user3
     */
    public String getUser3() {
        return user3__;
    }
    /**
     * <p>user3 をセットします。
     * @param user3 user3
     */
    public void setUser3(String user3) {
        user3__ = user3;
    }
    /**
     * <p>user4 を取得します。
     * @return user4
     */
    public String getUser4() {
        return user4__;
    }
    /**
     * <p>user4 をセットします。
     * @param user4 user4
     */
    public void setUser4(String user4) {
        user4__ = user4;
    }
    /**
     * <p>user5 を取得します。
     * @return user5
     */
    public String getUser5() {
        return user5__;
    }
    /**
     * <p>user5 をセットします。
     * @param user5 user5
     */
    public void setUser5(String user5) {
        user5__ = user5;
    }
    /**
     * <p>group1 を取得します。
     * @return group1
     */
    public String getGroup1() {
        return group1__;
    }
    /**
     * <p>group1 をセットします。
     * @param group1 group1
     */
    public void setGroup1(String group1) {
        group1__ = group1;
    }
    /**
     * <p>group2 を取得します。
     * @return group2
     */
    public String getGroup2() {
        return group2__;
    }
    /**
     * <p>group2 をセットします。
     * @param group2 group2
     */
    public void setGroup2(String group2) {
        group2__ = group2;
    }
    /**
     * <p>group3 を取得します。
     * @return group3
     */
    public String getGroup3() {
        return group3__;
    }
    /**
     * <p>group3 をセットします。
     * @param group3 group3
     */
    public void setGroup3(String group3) {
        group3__ = group3;
    }
    /**
     * <p>group4 を取得します。
     * @return group4
     */
    public String getGroup4() {
        return group4__;
    }
    /**
     * <p>group4 をセットします。
     * @param group4 group4
     */
    public void setGroup4(String group4) {
        group4__ = group4;
    }
    /**
     * <p>group5 を取得します。
     * @return group5
     */
    public String getGroup5() {
        return group5__;
    }
    /**
     * <p>group5 をセットします。
     * @param group5 group5
     */
    public void setGroup5(String group5) {
        group5__ = group5;
    }
    /**
     * <p>sndMailType を取得します。
     * @return sndMailType
     */
    public String getSndMailType() {
        return sndMailType__;
    }
    /**
     * <p>sndMailType をセットします。
     * @param sndMailType sndMailType
     */
    public void setSndMailType(String sndMailType) {
        sndMailType__ = sndMailType;
    }


    /**
     * <br>[機  能] 順序付けのために 2 つの引数を比較する。
     * <br>[解  説]
     * <br>[備  考]
     * @param obj1 比較対象の最初のオブジェクト
     * @param obj2 比較対象の2番目のオブジェクト
     * @return 最初の引数が 2 番目の引数より小さい場合は負の整数、両方が等しい場合は 0、
     *          最初の引数が 2 番目の引数より大きい場合は正の整数
     */
    public int compare(SmailCsvModel obj1, SmailCsvModel obj2) {
        return (int) (obj1.getRowNum() - obj2.getRowNum());
    }
}