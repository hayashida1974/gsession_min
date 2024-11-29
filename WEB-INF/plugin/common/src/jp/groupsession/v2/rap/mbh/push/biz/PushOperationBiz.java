package jp.groupsession.v2.rap.mbh.push.biz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.rap.mbh.push.model.PushTokenModel;

/**
 * <br>[機  能] PUSH通知に関する各種情報を操作する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PushOperationBiz implements IPushOperationBiz {
    /**
     *
     * デフォルトコンストラクタ
     */
    private PushOperationBiz() {
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
    public static IPushOperationBiz getInstance(Connection con, String domain) {
       IPushOperationBiz instance = null;
       String clsName = "jp.groupsession.v3.mbh.push.biz.PushOperationBiz";
       if (!StringUtil.isNullZeroString(clsName)) {
           try {
               @SuppressWarnings("unchecked")
               Class<IPushOperationBiz> cls = (Class<IPushOperationBiz>) Class.forName(clsName);
               Method m = cls.getMethod("getInstance", Connection.class, String.class);
               instance = (IPushOperationBiz) m.invoke(cls, con, domain);
           } catch (IllegalAccessException  | ClassNotFoundException
                   | IllegalArgumentException | InvocationTargetException
                   | NoSuchMethodException | SecurityException e) {
           }
           if (instance != null) {
               return instance;
           }
       }
       instance = new PushOperationBiz();
       return instance;
    }


    @Override
    public List<PushTokenModel> getUserTokenList(Connection con, int usrSid, String uid)
    throws SQLException {
        return new ArrayList<PushTokenModel>();
    }

    @Override
    public void deleteToken(Connection con, List<String> tokenList) throws SQLException {
    }
}
