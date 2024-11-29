package jp.groupsession.v2.man.man090;


/**
 * <br>[機  能] アプリケーションログ一覧に表示する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DspAppLogModel implements Comparable<DspAppLogModel> {

    /** ログファイル名 */
    private String httpLogName__;
    /** ログファイル更新日付 */
    private String logLastUpDate__;
    /** ログファイル名エスケープ */
    private String escHttpLogName__;
    /** ログファイルサイズ */
    private String httpLogSize__;
    /** ログファイル名ハッシュ */
    private String hashHttpLogName__;

    /** 更新日付を比較します
     * @param o 比較対照
     * @return int
     */
    public int compareTo(DspAppLogModel o) {
        return getLogLastUpDate().compareTo(o.getLogLastUpDate());
    }
    /**
     * <p>ログファイル名エスケープを取得します。
     * @return ログファイル名エスケープを戻します。
     */
    public String getEscHttpLogName() {
        return escHttpLogName__;
    }
    /**
     * <p>ログファイル名エスケープをセットします。
     * @param escHttpLogName ログファイル名エスケープを設定。
     */
    public void setEscHttpLogName(String escHttpLogName) {
        escHttpLogName__ = escHttpLogName;
    }
    /**
     * <p>ログファイル名を取得します。
     * @return ログファイル名を戻します。
     */
    public String getHttpLogName() {
        return httpLogName__;
    }
    /**
     * <p>ログファイル名をセットします。
     * @param httpLogName ログファイル名を設定。
     */
    public void setHttpLogName(String httpLogName) {
        httpLogName__ = httpLogName;
    }
    /**
     * <p>logLastUpDate を取得します。
     * @return logLastUpDate
     */
    public String getLogLastUpDate() {
        return logLastUpDate__;
    }
    /**
     * <p>logLastUpDate をセットします。
     * @param logLastUpDate logLastUpDate
     */
    public void setLogLastUpDate(String logLastUpDate) {
        logLastUpDate__ = logLastUpDate;
    }
    /**
     * @return httpLogSize
     */
    public String getHttpLogSize() {
        return httpLogSize__;
    }
    /**
     * @param httpLogSize セットする httpLogSize
     */
    public void setHttpLogSize(String httpLogSize) {
        httpLogSize__ = httpLogSize;
    }
    /**
     * <p>hashHttpLogName を取得します。
     * @return hashHttpLogName
     * @see jp.groupsession.v2.man.man090.DspAppLogModel#hashHttpLogName__
     */
    public String getHashHttpLogName() {
        return hashHttpLogName__;
    }
    /**
     * <p>hashHttpLogName をセットします。
     * @param hashHttpLogName hashHttpLogName
     * @see jp.groupsession.v2.man.man090.DspAppLogModel#hashHttpLogName__
     */
    public void setHashHttpLogName(String hashHttpLogName) {
        hashHttpLogName__ = hashHttpLogName;
    }

}
