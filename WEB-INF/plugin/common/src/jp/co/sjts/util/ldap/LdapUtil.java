package jp.co.sjts.util.ldap;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.DirContext;

import jp.co.sjts.util.ldap.model.LdapConnectModel;

/**
 * <br>[機  能] LDAPに関するユーティリティークラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LdapUtil {

    /**
     * <br>[機  能] ユーザIDとパスワードを元に、ユーザが存在するか判定
     * <br>[解  説] userIdはドメイン名、DN(識別名)等を含んだ文字列とする
     * <br>         例：test@pri.sjts.co.jp.local
     * <br>         例：cn=test,cn=Users,dc=pri,dc=sjts,dc=co,dc=jp,dc=local
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @param userId ユーザID文字列
     * @param pass パスワード
     * @return boolean true=存在する、false=存在しない
     * @throws NamingException コンテキスト取得時例外
     */
    public static boolean isUserExist(LdapConnectModel connectData,
                                        String userId, String pass) throws NamingException {

        DirContext ctx = null;
        try {
            // コンテキストの作成
//            ctx = LdapConnect.getDirContext(userId, pass);
            ctx = LdapConnect.getDirContext(connectData);

        } catch (AuthenticationException e) {
        } catch (PartialResultException e) {
            //ActiveDirectory環境対策
        }

        if (ctx == null) {
            return false;
        }

        ctx.close();
        return true;
    }

}
