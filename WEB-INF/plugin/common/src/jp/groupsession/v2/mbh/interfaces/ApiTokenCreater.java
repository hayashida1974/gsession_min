package jp.groupsession.v2.mbh.interfaces;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 中継サーバ間で行う処理用
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiTokenCreater implements IApiTokenCreater {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiTokenCreater.class);

    /**
     *
     * デフォルトコンストラクタ
     */
    private ApiTokenCreater() {

    }

    /**
     *
     * <br>[機  能] インスタンス取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param domain String
     * @return インスタンス
     */
    public static IApiTokenCreater getInstance(Connection con, String domain) {
        IApiTokenCreater instance = null;
       String clsName = "jp.groupsession.v2.api.ApiTokenCreater";

       if (!StringUtil.isNullZeroString(clsName)) {
           try {
               @SuppressWarnings("unchecked")
               Class<ApiTokenCreater> cls = (Class<ApiTokenCreater>) Class.forName(clsName);
               Method m = cls.getMethod("getInstance", Connection.class, String.class);
               instance = (IApiTokenCreater) m.invoke(cls, con, domain);
           } catch (IllegalAccessException  | ClassNotFoundException
                   | IllegalArgumentException | InvocationTargetException
                   | NoSuchMethodException | SecurityException e) {
           }
           if (instance != null) {
               return instance;
           }
       }
       instance = new ApiTokenCreater();
       return instance;
   }

    @Override
    public String doCreateToken(Connection con, 
            HttpServletRequest req, RequestModel reqMdl, int usrSid) {

        return "";
    }
}
