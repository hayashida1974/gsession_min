package jp.co.sjts.util.ldap;

import java.util.ArrayList;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import jp.co.sjts.util.ldap.model.LdapConnectModel;

/**
 * <br>[機  能] jp.co.sjts.util.ldap.LdapControllerのテストクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LdapControllerTest2 {

    /**
     * <br>[機  能] "main"メソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {


        LdapConnectModel connectData = new LdapConnectModel();
        connectData.setAddress("192.168.1.28");
        connectData.setPort("389");
        connectData.setBindDn("CN=Manager,DC=example,DC=com");
        connectData.setPassword("jts6181");

        ArrayList<String> namings = getNamingContextsFromRootDSE(connectData);


        for (String baseDn : namings) {
            System.out.println(baseDn);
            if (doAuth(connectData, baseDn)) {
                System.out.println("ログインOK");
            } else {
                System.out.println("ログイン失敗");
            }
        }
    }

    /**
     * <br>[機  能] ユーザの存在チェック機能をテストする
     * <br>[解  説]
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @param baseDn ベースDN
     * @return チェック結果
     */
    private static boolean doAuth(LdapConnectModel connectData, String baseDn) {
        LdapController lcon = null;
        boolean result = false;
        try {
            connectData.setBaseDn(baseDn);
            connectData.setDnElement("uid");

            lcon = new LdapController(connectData);
            result = lcon.existUser(connectData, "user1", "jts6181");

            if (result) {
                System.out.println("成功");
            } else {
                System.out.println("失敗");
            }

            return true;
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lcon.close();
        }
        return false;
    }

    /**
     * <br>[機  能] 指定したLDAP接続情報の属性情報を取得する機能をテストする
     * <br>[解  説]
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @return 属性情報
     */
    private static ArrayList<String> getNamingContextsFromRootDSE(LdapConnectModel connectData) {
        ArrayList<String> namings = new ArrayList<String>();

        try {
            DirContext ctx = LdapConnect.getDirContext(connectData);
            Attributes attrs = ctx.getAttributes("ldap://" + connectData.getAddress()
                                            + ":" + connectData.getPort() + "/",
                                            new String[] { "namingContexts" });
            @SuppressWarnings({"all", "unchecked" })
            NamingEnumeration enums = attrs.getIDs();
            while (enums != null && enums.hasMore()) {
                String nextattr = (String) enums.next();
                String val = (String) attrs.get(nextattr).get();
                namings.add(val);
            }

            ctx.close();

        } catch (NamingException e) {
            e.printStackTrace();
        }

        return namings;
    }

}
