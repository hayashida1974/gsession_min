package jp.groupsession.v2.cmn.login;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] ログイン処理インターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ILogin {

    /**
     * <br>[機  能] idpass認証処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 認証処理に使用する各情報
     * @return 認証結果
     * @throws Exception 認証処理時に例外発生
     */
    public LoginResultModel idpassAuth(LoginModel model) throws Exception;
    /**
     * <br>[機  能] Api 用 idpass認証処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 認証処理に使用する各情報
     * @return 認証結果
     * @throws Exception 認証処理時に例外発生
     */
    public LoginResultModel idpassAuthApi(LoginModel model) throws Exception;


    /**
     * <br>[機  能] ログイン処理を行う
     * <br>[解  説]
     * <br>[備  考] ワンタイムパスワードに対応しない
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel login(LoginModel model) throws Exception;

    /**
     * <br>[機  能] ログイン処理を行う
     * <br>[解  説]　Apiアクセス(BASIC認証)時に使用
     * <br>[備  考] Apiアクセス(トークン認証)では使用されません
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel login2(LoginModel model) throws Exception;

    /**
     * <br>[機  能] ログイン処理を行う
     * <br>[解  説] 自動ログイン時に使用
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel autoLogin(LoginModel model) throws Exception;

    /**
     * <br>[機  能] モバイル用ログイン処理を行う
     * <br>[解  説] 自動ログイン時に使用
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel autoLoginMbl(LoginModel model) throws Exception;

    /**
     * <br>[機  能] モバイル用ログイン処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel loginMbl(LoginModel model) throws Exception;


    /**
     * <br>[機  能] モバイル用ログイン処理を行う
     * <br>[解  説] Apiアクセス時に使用
     * <br>[備  考]
     * @param model ログイン処理に使用する各情報
     * @return ログイン結果
     * @throws Exception ログイン処理時に例外発生
     */
    public LoginResultModel loginMblApi(LoginModel model) throws Exception;

    /**
     * <br>[機  能] パスワード変更の可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return true:変更可能 false:変更不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canChangePassword(Connection con, int userSid) throws SQLException;

    /**
     * <br>[機  能] パスワードのフォーマットを行う
     * <br>[解  説] データベースに登録時はこのメソッドでフォーマットした
     * <br>         パスワードが使用される
     * <br>[備  考]
     * @param password パスワード
     * @return パスワード
     */
    public String formatPassword(String password);

    /**
     * <br>[機  能] 該当ユーザの有無を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param loginId コネクション
     * @param password パスワード
     * @return true:存在する false:存在しない
     * @throws Exception 例外発生
     */
    public boolean isExistsUser(Connection con, String loginId, String password) throws Exception;

    /**
     * <br>[機  能] 別ウィンドウで表示するかを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return true: 別ウィンドウ false: 通常
     */
    public boolean isPopup();

    /**
     * <br>[機  能] 自動ログイン処理を行うかを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return true: 自動ログイン処理を行う false: 自動ログイン処理を行わない
     */
    public boolean isAutoLogin();

    /**
     * <br>[機  能] 画面遷移先を返す(frame:メイン画面、pswdLimit:初回ログイン時変更、pswdUpdat:期限切れ時変更)
     * <br>[解  説]
     * <br>[備  考]
     * @param model セッションユーザモデル
     * @param con コネクション
     * @return forword 画面遷移先
     * @throws Exception ログイン処理時に例外発生
     */
    public String checkForwordPassowrd(BaseUserModel model, Connection con) throws Exception;

    /**
     * <br>[機  能] 自動ログイン失敗時に遷移する画面を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @return ActionForward
     */
    public ActionForward getAutoLoginFailPage(ActionMapping map);
    /**
     * <br>[機  能] ワンタイムパスワード認証の使用可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:使用可 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseOnetimePassword();

    /**
     * <br>[機  能] ユーザ毎の利用ログインタイプ文字列を返す
     * <br>[解  説] 
     * <br>[備  考]
     * @param model ログインユーザモデル
     * @param con コネクション
     * @return ログインタイプ文字列 normal：通常
     * @throws SQLException SQL実行時例外
     */
    public String getLoginType(BaseUserModel model, Connection con) throws SQLException;


}
