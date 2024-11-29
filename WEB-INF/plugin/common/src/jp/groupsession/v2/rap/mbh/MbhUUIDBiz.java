package jp.groupsession.v2.rap.mbh;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] モバイル端末用個体識別番号制御ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MbhUUIDBiz implements IMbhUUIDBiz {
    /**
    *
    * <br>[機  能] インスタンス取得
    * <br>[解  説]
    * <br>[備  考]
    * @return インスタンス
    */
   public static IMbhUUIDBiz getInstance() {
      IMbhUUIDBiz instance = null;
      String clsName = "jp.groupsession.v3.mbh.uuid.MbhUUIDBiz";

      if (!StringUtil.isNullZeroString(clsName)) {
          try {
              @SuppressWarnings("unchecked")
              Class<PushServiceOperator> cls = (Class<PushServiceOperator>) Class.forName(clsName);
              Method m = cls.getMethod("getInstance");
              instance = (IMbhUUIDBiz) m.invoke(cls);
          } catch (IllegalAccessException  | ClassNotFoundException
                  | IllegalArgumentException | InvocationTargetException
                  | NoSuchMethodException | SecurityException e) {
          }
          if (instance != null) {
              return instance;
          }
      }
      instance = new MbhUUIDBiz();
      return instance;
  }

   @Override
   public boolean checkUid(BaseUserModel smodel,
           UserAgent agent,
           Connection con, boolean isLogin) throws SQLException {
       return false;
   }

    @Override
    public RestApiPermissionException createRestApiUUIDException(GsMessage gsMsg) {
        throw new RuntimeException("create failed");
    }

}
