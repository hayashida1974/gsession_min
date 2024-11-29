package jp.groupsession.v2.convert.convert560.dao;

/**
 * <br>[機  能] v5.6.0へのコンバート時に、CHT_FAVORITEからCHT_USER_UCONFへデータを移行する際に使用するモデルです
 * <br>[解  説] 
 * <br>[備  考]
 */
public class ChtUserFavoriteModel {

    /** お気に入り実行ユーザ */
    private int chfUid__ = 0;
    /** お気に入り対象のユーザSID */
    private int chfSid__ = 0;
    /** ペアSID */
    private int cupSid__  = 0;
    
    /**
     * @return the chfUid
     */
    public int getChfUid() {
        return chfUid__;
    }
    /**
     * @param chfUid the chfUid to set
     */
    public void setChfUid(int chfUid) {
        chfUid__ = chfUid;
    }
    /**
     * @return the chfSid
     */
    public int getChfSid() {
        return chfSid__;
    }
    /**
     * @param chfSid the chfSid to set
     */
    public void setChfSid(int chfSid) {
        chfSid__ = chfSid;
    }
    /**
     * @return the cupSid
     */
    public int getCupSid() {
        return cupSid__;
    }
    /**
     * @param cupSid the cupSid to set
     */
    public void setCupSid(int cupSid) {
        cupSid__ = cupSid;
    }



}
