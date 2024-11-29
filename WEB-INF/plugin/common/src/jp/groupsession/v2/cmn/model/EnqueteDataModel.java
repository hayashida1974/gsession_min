package jp.groupsession.v2.cmn.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] アンケート情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqueteDataModel extends AbstractModel {
    /** アンケートSID */
    private long enqSid__ = 0;
    /** 重要度 */
    private int priority__ = 0;
    /** 状態 */
    private int status__ = 0;
    /** 発信者 */
    private String sender__ = null;
    /** 種類 */
    private String typeName__ = null;
    /** タイトル */
    private String title__ = null;
    /** 公開 */
    private boolean public__ = false;
    /** 公開期限 開始 */
    private UDate pubStartDate__ = null;
    /** 公開期限 終了 */
    private UDate pubEndDate__ = null;
    /** 公開期限 終了 指定 */
    private int pubEndKbn__ = 0;
    /** 回答期限 */
    private UDate ansLimitDate__ = null;
    /** 回答公開期限 開始 */
    private UDate ansPubStartDate__ = null;
    /** 匿名 */
    private int annoy__ = 0;
    /** 対象者 */
    private List<String> answerList__ = null;
    /**
     * <p>enqSid を取得します。
     * @return enqSid
     */
    public long getEnqSid() {
        return enqSid__;
    }
    /**
     * <p>enqSid をセットします。
     * @param enqSid enqSid
     */
    public void setEnqSid(long enqSid) {
        enqSid__ = enqSid;
    }
    /**
     * <p>priority を取得します。
     * @return priority
     */
    public int getPriority() {
        return priority__;
    }
    /**
     * <p>priority をセットします。
     * @param priority priority
     */
    public void setPriority(int priority) {
        priority__ = priority;
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
     * <p>sender を取得します。
     * @return sender
     */
    public String getSender() {
        return sender__;
    }
    /**
     * <p>sender をセットします。
     * @param sender sender
     */
    public void setSender(String sender) {
        sender__ = sender;
    }
    /**
     * <p>typeName を取得します。
     * @return typeName
     */
    public String getTypeName() {
        return typeName__;
    }
    /**
     * <p>typeName をセットします。
     * @param typeName typeName
     */
    public void setTypeName(String typeName) {
        typeName__ = typeName;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>public を取得します。
     * @return public
     */
    public boolean isPublic() {
        return public__;
    }
    /**
     * <p>public をセットします。
     * @param public1 public
     */
    public void setPublic(boolean public1) {
        public__ = public1;
    }
    /**
     * <p>pubStartDate を取得します。
     * @return pubStartDate
     */
    public UDate getPubStartDate() {
        return pubStartDate__;
    }
    /**
     * <p>pubStartDate をセットします。
     * @param pubStartDate pubStartDate
     */
    public void setPubStartDate(UDate pubStartDate) {
        pubStartDate__ = pubStartDate;
    }
    /**
     * <p>pubEndDate を取得します。
     * @return pubEndDate
     */
    public UDate getPubEndDate() {
        return pubEndDate__;
    }
    /**
     * <p>pubEndDate をセットします。
     * @param pubEndDate pubEndDate
     */
    public void setPubEndDate(UDate pubEndDate) {
        pubEndDate__ = pubEndDate;
    }
    /**
     * <p>pubEndKbn を取得します。
     * @return pubEndKbn
     */
    public int getPubEndKbn() {
        return pubEndKbn__;
    }
    /**
     * <p>pubEndKbn をセットします。
     * @param pubEndKbn pubEndKbn
     */
    public void setPubEndKbn(int pubEndKbn) {
        pubEndKbn__ = pubEndKbn;
    }
    /**
     * <p>ansLimitDate を取得します。
     * @return ansLimitDate
     */
    public UDate getAnsLimitDate() {
        return ansLimitDate__;
    }
    /**
     * <p>ansLimitDate をセットします。
     * @param ansLimitDate ansLimitDate
     */
    public void setAnsLimitDate(UDate ansLimitDate) {
        ansLimitDate__ = ansLimitDate;
    }
    /**
     * <p>ansPubStartDate を取得します。
     * @return ansPubStartDate
     */
    public UDate getAnsPubStartDate() {
        return ansPubStartDate__;
    }
    /**
     * <p>ansPubStartDate をセットします。
     * @param ansPubStartDate ansPubStartDate
     */
    public void setAnsPubStartDate(UDate ansPubStartDate) {
        ansPubStartDate__ = ansPubStartDate;
    }
    /**
     * <p>annoy を取得します。
     * @return annoy
     */
    public int getAnnoy() {
        return annoy__;
    }
    /**
     * <p>annoy をセットします。
     * @param annoy annoy
     */
    public void setAnnoy(int annoy) {
        annoy__ = annoy;
    }
    /**
     * <p>answerList を取得します。
     * @return answerList
     */
    public List<String> getAnswerList() {
        return answerList__;
    }
    /**
     * <p>answerList をセットします。
     * @param answerList answerList
     */
    public void setAnswerList(List<String> answerList) {
        answerList__ = answerList;
    }
}
