package jp.groupsession.v2.sml.sml030;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメール内容画面のページごとの情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考] 全データ中の位置を把握するために使用
 *
 * @author JTS
 */
public class Sml030Model extends AbstractModel {

    /** メールSID */
    private int mailSid__;
    /** ページ番号 */
    private int pageNum__;
    /** メール区分 */
    private String mailKbn__;

    /**
     * @return mailSid を戻します。
     */
    public int getMailSid() {
        return mailSid__;
    }
    /**
     * @param mailSid 設定する mailSid。
     */
    public void setMailSid(int mailSid) {
        mailSid__ = mailSid;
    }
    /**
     * @return pageNum を戻します。
     */
    public int getPageNum() {
        return pageNum__;
    }
    /**
     * @param pageNum 設定する pageNum。
     */
    public void setPageNum(int pageNum) {
        pageNum__ = pageNum;
    }
    /**
     * <p>mailKbn を取得します。
     * @return mailKbn
     */
    public String getMailKbn() {
        return mailKbn__;
    }
    /**
     * <p>mailKbn をセットします。
     * @param mailKbn mailKbn
     */
    public void setMailKbn(String mailKbn) {
        mailKbn__ = mailKbn;
    }
}