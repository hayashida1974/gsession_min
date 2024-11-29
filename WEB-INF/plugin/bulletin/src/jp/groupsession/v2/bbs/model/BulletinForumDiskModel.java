package jp.groupsession.v2.bbs.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] フォーラムのディスク容量に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinForumDiskModel extends AbstractModel {

    /** フォーラムSID */
    private int bfiSid__ = 0;
    /** フォーラム名 */
    private String bfiName__  = null;
    /** フォーラム ディスク使用量 */
    private long bfsSize__ = 0;

    /** フォーラム ディスク容量制限 */
    private int bfiDisk__ = 0;
    /** フォーラム ディスク容量上限 */
    private int bfiDiskSize__ = 0;
    /** フォーラム ディスク容量警告 */
    private int bfiWarnDisk__ = 0;
    /** フォーラム ディスク容量警告 閾値 */
    private int bfiWarnDiskTh__ = 0;

    /**
     * <p>bfiSid を取得します。
     * @return bfiSid
     */
    public int getBfiSid() {
        return bfiSid__;
    }
    /**
     * <p>bfiSid をセットします。
     * @param bfiSid bfiSid
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }
    /**
     * <p>bfiName を取得します。
     * @return bfiName
     */
    public String getBfiName() {
        return bfiName__;
    }
    /**
     * <p>bfiName をセットします。
     * @param bfiName bfiName
     */
    public void setBfiName(String bfiName) {
        bfiName__ = bfiName;
    }
    /**
     * <p>bfiDisk を取得します。
     * @return bfiDisk
     */
    public int getBfiDisk() {
        return bfiDisk__;
    }
    /**
     * <p>bfiDisk をセットします。
     * @param bfiDisk bfiDisk
     */
    public void setBfiDisk(int bfiDisk) {
        bfiDisk__ = bfiDisk;
    }
    /**
     * <p>bfiDiskSize を取得します。
     * @return bfiDiskSize
     */
    public int getBfiDiskSize() {
        return bfiDiskSize__;
    }
    /**
     * <p>bfiDiskSize をセットします。
     * @param bfiDiskSize bfiDiskSize
     */
    public void setBfiDiskSize(int bfiDiskSize) {
        bfiDiskSize__ = bfiDiskSize;
    }
    /**
     * <p>bfiWarnDisk を取得します。
     * @return bfiWarnDisk
     */
    public int getBfiWarnDisk() {
        return bfiWarnDisk__;
    }
    /**
     * <p>bfiWarnDisk をセットします。
     * @param bfiWarnDisk bfiWarnDisk
     */
    public void setBfiWarnDisk(int bfiWarnDisk) {
        bfiWarnDisk__ = bfiWarnDisk;
    }
    /**
     * <p>bfiWarnDiskTh を取得します。
     * @return bfiWarnDiskTh
     */
    public int getBfiWarnDiskTh() {
        return bfiWarnDiskTh__;
    }
    /**
     * <p>bfiWarnDiskTh をセットします。
     * @param bfiWarnDiskTh bfiWarnDiskTh
     */
    public void setBfiWarnDiskTh(int bfiWarnDiskTh) {
        bfiWarnDiskTh__ = bfiWarnDiskTh;
    }
    /**
     * <p>bfsSize を取得します。
     * @return bfsSize
     */
    public long getBfsSize() {
        return bfsSize__;
    }
    /**
     * <p>bfsSize をセットします。
     * @param bfsSize bfsSize
     */
    public void setBfsSize(long bfsSize) {
        bfsSize__ = bfsSize;
    }

}
