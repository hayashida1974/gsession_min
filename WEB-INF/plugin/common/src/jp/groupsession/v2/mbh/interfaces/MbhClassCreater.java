package jp.groupsession.v2.mbh.interfaces;

import java.lang.reflect.InvocationTargetException;

import jp.groupsession.v2.mbh.interfaces.api.login.IMbhLoginHistoryBiz;
import jp.groupsession.v2.mbh.interfaces.api.login.IMbhUUIDSaveBiz;

/**
 * <br>[機  能] モバイル用インタフェースインスタンス作成処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MbhClassCreater {
    /**
     *
     * <br>[機  能] モバイル端末個体識別保管処理 Bizクラス取得
     * <br>[解  説]
     * <br>[備  考]
     * @return モバイル端末個体識別保管処理 Bizクラス
     *          gsmobile.jarがない場合はnull
     */
    public IMbhUUIDSaveBiz createUUIDSaveBiz() {
        IMbhUUIDSaveBiz instance = null;

        String clsName = "jp.groupsession.v3.mbh.api.login.MbhUUIDSaveBiz";
        try {
            Object insObj = Class.forName(clsName)
                    .getDeclaredConstructor().newInstance();
            if (insObj instanceof IMbhUUIDSaveBiz) {
                instance = (IMbhUUIDSaveBiz) insObj;
            }
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException
                | NullPointerException e) {

        }
        return instance;

    }
    
   /**
    *
    * <br>[機  能] モバイルアプリログイン処理 ビジネスロジッククラス取得
    * <br>[解  説]
    * <br>[備  考]
    * @return モバイルアプリログイン処理 ビジネスロジッククラス
    *          gsmobile.jarがない場合はnull
    */
   public IMbhLoginHistoryBiz createMbhLoginHistoryBiz() {
       IMbhLoginHistoryBiz instance = null;

       String clsName = "jp.groupsession.v3.mbh.api.login.MbhApiAppLoginBiz";
       try {
           Object insObj = Class.forName(clsName)
                   .getDeclaredConstructor().newInstance();
           if (insObj instanceof IMbhLoginHistoryBiz) {
               instance = (IMbhLoginHistoryBiz) insObj;
           }
       } catch (InstantiationException | IllegalAccessException
               | IllegalArgumentException | InvocationTargetException
               | NoSuchMethodException | SecurityException | ClassNotFoundException
               | NullPointerException e) {

       }
       return instance;

   }
}
