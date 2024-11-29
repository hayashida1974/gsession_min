package jp.groupsession.v2.cmn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] DBUtilのインスタンスを作成するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DBUtilFactory {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(DBUtilFactory.class);

    /**
     * <br>[機  能] DBUtilのインスタンスを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return IDbUtil IDbUtilのインスタンス
     */
    public static IDbUtil getInstance() {
        String strDbUtil = GsResourceBundle.getString("IDbUtil");
        IDbUtil dbutil = null;

        if (strDbUtil != null) {
            try {
                dbutil = (IDbUtil) Class.forName(strDbUtil).newInstance();
            } catch (InstantiationException e) {
                log__.error(e);
            } catch (IllegalAccessException e) {
                log__.error(e);
            } catch (ClassNotFoundException e) {
                log__.error(e);
            }
        } else {
            dbutil = new GSH2Util();
        }
        return dbutil;
    }
}
