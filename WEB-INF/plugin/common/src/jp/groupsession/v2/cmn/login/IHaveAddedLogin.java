package jp.groupsession.v2.cmn.login;


/**
 *
 * <br>[機  能] ログインBiz機能拡張 専用ログイン画面がある場合用
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IHaveAddedLogin {
    /**
    *
    * <br>[機  能] 追加されたログイン画面を返す
    * <br>[解  説]
    * <br>[備  考]
    * @return 遷移先
    */
   public String getAddedLoginPage();

   /**
   *
   * <br>[機  能] 追加されたログイン画面へリダイレクトが必要か
   * <br>[解  説]
   * <br>[備  考]
   * @return リダイレクトが必要な場合 True
   */
   public boolean isRedirectAddedLogin();
}
