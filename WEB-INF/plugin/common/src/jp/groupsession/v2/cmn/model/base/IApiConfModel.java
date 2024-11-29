package jp.groupsession.v2.cmn.model.base;
/**
 *
 * <br>[機  能] WEBAPI基本設定 DAOモデル インタフェース定義
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IApiConfModel {

    /**
     * <p>get APC_TOAKEN_USE value
     * @return APC_TOAKEN_USE value
     */
    int getApcToakenUse();

    /**
     * <p>set APC_TOAKEN_USE value
     * @param apcToakenUse APC_TOAKEN_USE value
     */
    void setApcToakenUse(int apcToakenUse);

    /**
     * <p>get APC_TOAKEN_IP value
     * @return APC_TOAKEN_IP value
     */
    String getApcToakenIp();

    /**
     * <p>set APC_TOAKEN_IP value
     * @param apcToakenIp APC_TOAKEN_IP value
     */
    void setApcToakenIp(String apcToakenIp);

    /**
     * <p>get APC_TOAKEN_LIFE value
     * @return APC_TOAKEN_LIFE value
     */
    int getApcToakenLife();

    /**
     * <p>set APC_TOAKEN_LIFE value
     * @param apcToakenLife APC_TOAKEN_LIFE value
     */
    void setApcToakenLife(int apcToakenLife);

    /**
     * <p>get APC_BASIC_USE value
     * @return APC_BASIC_USE value
     */
    int getApcBasicUse();

    /**
     * <p>set APC_BASIC_USE value
     * @param apcBasicUse APC_BASIC_USE value
     */
    void setApcBasicUse(int apcBasicUse);

    /**
     * <p>get APC_BASIC_IP value
     * @return APC_BASIC_IP value
     */
    String getApcBasicIp();

    /**
     * <p>set APC_BASIC_IP value
     * @param apcBasicIp APC_BASIC_IP value
     */
    void setApcBasicIp(String apcBasicIp);
    
    int getApcAutoDel();
    
    /**
     * <p>set APC_AUTO_DEL value
     * @param apcAutoDel APC_AUTO_DEL value
     */
    void setApcAutoDel(int apcAutoDel);

}