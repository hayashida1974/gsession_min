package jp.groupsession.v2.rap.mbh.push;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.PushRequestModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rap.mbh.push.api.IPushApiRequest;

/**
 * <br>
 * [機 能] 中継サーバ間で行う処理用 <br>
 * [解 説] <br>
 * [備 考]
 *
 * @author JTS
 */
public class PushServiceOperator implements IPushServiceOperator {

    /**
     *
     * デフォルトコンストラクタ
     */
    private PushServiceOperator() {

    }

    /**
     *
     * <br>
     * [機 能] インスタンス取得 <br>
     * [解 説] <br>
     * [備 考]
     * 
     * @param con
     *            Connection
     * @param domain
     *            String
     * @return インスタンス
     */
    public static IPushServiceOperator getInstance(Connection con,
            String domain) {
        IPushServiceOperator instance = null;
        String clsName = "jp.groupsession.v3.mbh.push.PushServiceOperator";

        if (!StringUtil.isNullZeroString(clsName)) {
            try {
                @SuppressWarnings("unchecked")
                Class<PushServiceOperator> cls = (Class<PushServiceOperator>) Class
                        .forName(clsName);
                Method m = cls.getMethod("getInstance", Connection.class,
                        String.class);
                instance = (IPushServiceOperator) m.invoke(cls, con, domain);
            } catch (IllegalAccessException | ClassNotFoundException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
            }
            if (instance != null) {
                return instance;
            }
        }
        instance = new PushServiceOperator();
        return instance;
    }

    @Override
    public boolean isUseable() {
        return false;
    }

    @Override
    public String api(IPushApiRequest apiRequest) {
        return null;
    }

    @Override
    public IPushServiceOperator doOperation(IPushOperationConsumer function) {
        return this;
    }

    @Override
    public void removeJwt(Connection con) throws SQLException {
    }
    @Override
    public void sendMessage(Connection con, RequestModel reqMdl,
            List<PushRequestModel> pushMdlList, String pluginId) {
    }

}
