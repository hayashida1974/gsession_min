package jp.co.sjts.util.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import jp.co.sjts.util.ldap.model.LdapConnectModel;
import jp.groupsession.v2.cmn.model.GroupBaseModel;
import jp.groupsession.v2.cmn.model.UserBaseModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] LDAPに関する操作を行うクラス
 * <br>[解  説] ldap.propertiesにLDAPサーバ情報を設定し使用します。
 * <br>使用後は必ずclose()メソッドを実行すること。
 * <br>[備  考]
 *
 * @author JTS
 */
public class LdapController {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(LdapController.class);

    /** 接続用コンテキスト */
    private DirContext ctx__ = null;
    /** LDAP接続情報 */
    private LdapConnectModel connectData__ = null;
    /** 更新フラグ 登録 */
    private static final int UPDATE_FLG_ADD = 0;
    /** 更新フラグ 更新 */
    private static final int UPDATE_FLG_EDT = 1;

    /**
     * コンストラクタ
     * 設定ファイルをもとにコンテキストを生成します。
     * 検索・更新処理を行う際は本コンストラクタの使用を推奨
     * @throws NamingException コンテキスト取得時例外
     */
    public LdapController() throws NamingException {
        ctx__ = LdapConnect.getInitContext();
        connectData__ = LdapConnect.createConnectData();
    }

    /**
     * コンストラクタ
     * 設定ファイル＋任意のユーザIDとパスワードでコンテキストを生成します。
     * 指定したユーザ権限によって処理の範囲が制限されることに注意してください。
     * @param connectData LDAP接続情報
     * @throws NamingException コンテキスト取得時例外
     */
    public LdapController(LdapConnectModel connectData) throws NamingException {
        ctx__ = LdapConnect.getDirContext(connectData);
        connectData__ = connectData;
    }

    /**
     * <p>ctx を取得します。
     * @return ctx
     */
    public DirContext getCtx() {
        return ctx__;
    }

    /**
     * <p>ctx をセットします。
     * @param ctx ctx
     */
    public void setCtx(DirContext ctx) {
        ctx__ = ctx;
    }

    /**
     * <br>[機  能] ガーベッジ時にコンテキストのcloseを行う
     * <br>[解  説] close忘れ対策
     * <br>[備  考]
     * @throws Throwable Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        LdapConnect.closeCtx(ctx__);
    }

    /**
     * <br>[機  能] コンテキストのcloseを行う
     * <br>[解  説]
     * <br>[備  考]
     */
    public void close() {
        LdapConnect.closeCtx(ctx__);
    }

    /**
     * <br>[機  能] 指定したユーザIDが存在するかを判定する
     * <br>[解  説] objNameには、ユーザの検索範囲としてグループ等のDN(識別名)を設定する
     * <br>         例：cn=Users,dc=pri,dc=sjts,dc=co,dc=jp,dc=local
     * <br>         パスワードは無変換の状態で指定する
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @param userId ユーザID
     * @param password パスワード
     * @return UserBaseModel ユーザ情報
     * @throws NamingException コンテキスト取得時例外
     */
    public boolean existUser(LdapConnectModel connectData, String userId, String password)
    throws NamingException {
        return existUser(connectData, userId, password, true);
    }

    /**
     * <br>[機  能] 指定したユーザIDが存在するかを判定する
     * <br>[解  説] objNameには、ユーザの検索範囲としてグループ等のDN(識別名)を設定する
     * <br>         例：cn=Users,dc=pri,dc=sjts,dc=co,dc=jp,dc=local
     * <br>         パスワードは無変換の状態で指定する
     * <br>[備  考]
     * @param connectData LDAP接続情報
     * @param userId ユーザID
     * @param password パスワード
     * @param usePassword true: 認証時にパスワードを使用する false: パスワードを使用しない
     * @return UserBaseModel ユーザ情報
     * @throws NamingException コンテキスト取得時例外
     */
    public boolean existUser(LdapConnectModel connectData, String userId, String password,
                                        boolean usePassword)
    throws NamingException {

        boolean result = false;

        DirContext context = null;
        try {
            //ユーザIDの存在チェック
            context = LdapConnect.getDirContext(connectData);
            SearchControls cons = new SearchControls();
            cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> searchResult
                = context.search(connectData.getBaseDn(),
                                "(" + connectData.getDnElement() + "=" + userId + ")",
                                cons);

            if (searchResult.hasMore()) {
                if (!usePassword) {
                    //ユーザIDの存在チェックのみ
                    result = true;
                } else {
                    //ユーザIDとパスワードを使用してLDAP認証を行う
                    LdapConnectModel cloneConnectData = connectData__.cloneModel();
                    connectData.setPassword(password);

                    while (searchResult.hasMore()) {
                        SearchResult srhResult = searchResult.next();
                        connectData.setBindDn(srhResult.getName()
                                + "," + cloneConnectData.getBaseDn());

                        try {
                            context = LdapConnect.getDirContext(connectData);
                            result = true;
                            break;
                        } catch (AuthenticationException e) {
                            //
                            log__.warn("AD/LDAPサーバで認証に失敗しました。(発生箇所①)");
                        } catch (PartialResultException e) {
                            //ActiveDirectory環境対策
                            log__.warn("認証処理中にAD/LDAPサーバでエラーが発生しました。(発生箇所①)");
                        }
                    }
                }
            } else {
                log__.warn("AD/LDAPサーバにユーザが存在しません。");
            }

        } catch (AuthenticationException e) {
            //
            log__.warn("AD/LDAPサーバで認証に失敗しました。(発生箇所②)");
        } catch (PartialResultException e) {
            //ActiveDirectory環境対策
            log__.warn("認証処理中にAD/LDAPサーバでエラーが発生しました。(発生箇所②)");
        } finally {
            if (context != null) {
                context.close();
            }
        }

        return result;
    }

    /**
     * <br>[機  能] ユーザIDからユーザ情報を取得する
     * <br>[解  説] objNameには、ユーザの検索範囲としてグループ等のDN(識別名)を設定する
     * <br>         例：cn=Users,dc=pri,dc=sjts,dc=co,dc=jp,dc=local
     * <br>         searchStrには、検索するユーザを設定する
     * <br>         例：sAMAccountName=test
     * <br>         例：cn=test
     * <br>[備  考]
     * @param objName 検索するオブジェクトの名前
     * @param searchStr 検索するユーザ
     * @return UserBaseModel ユーザ情報
     * @throws NamingException コンテキスト取得時例外
     */
    public UserBaseModel getUserInfo(String objName, String searchStr) throws NamingException {

        UserBaseModel userMdl = null;

        List<Attributes> attrList = getAttributes(objName, searchStr);

        if (attrList.size() < 1) {
            return userMdl;
        }

        Attributes attrs = attrList.get(0);

        userMdl = new UserBaseModel();
        //ユーザID
        userMdl.setUserId(__getStrValue(attrs, "sAMAccountName"));
        //姓
        userMdl.setSei(__getStrValue(attrs, "sn"));
        //名
        userMdl.setMei(__getStrValue(attrs, "givenName"));

        return userMdl;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param objName 検索するオブジェクトの名前
     * @return List in GroupBaseModel
     * @throws NamingException コンテキスト取得時例外
     */
    public List<GroupBaseModel> getGroupList(String objName) throws NamingException {

        ArrayList<GroupBaseModel> groupList = new ArrayList<GroupBaseModel>();
        GroupBaseModel groupMdl = null;

        List<Attributes> attrList = getAttributes(objName, "objectClass=group");

        if (attrList.size() < 1) {
            return groupList;
        }

        for (Attributes attrs : attrList) {
            groupMdl = new GroupBaseModel();
            //グループID
            groupMdl.setGroupId(__getStrValue(attrs, "sAMAccountName"));
            groupList.add(groupMdl);
        }

        return groupList;
    }

    /**
     * <br>[機  能] 検索結果をAttributesのリストで取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param objName 検索するオブジェクトの名前
     * @param searchStr 検索文字列
     * @return List in Attributes
     * @throws NamingException コンテキスト取得時例外
     */
    public List<Attributes> getAttributes(String objName, String searchStr)
    throws NamingException {

        ArrayList<Attributes> attrList = new ArrayList<Attributes>();

        SearchControls cons = new SearchControls();
        // 検索スコープの指定
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 検索実行
        NamingEnumeration<SearchResult> res = ctx__.search(objName, searchStr, cons);

        while (res.hasMore()) {

            SearchResult findEntry = res.next();
            Attributes attrs = findEntry.getAttributes();

            if (attrs == null) {
                continue;
            }
            attrList.add(attrs);
        }

        return attrList;
    }

    /**
     * <br>[機  能] 指定したユーザIDのパスワードを更新する
     * <br>[解  説] userStrはDN(識別名)の形式の文字列とする
     * <br>         例：cn=test,cn=Users,dc=pri,dc=sjts,dc=co,dc=jp,dc=local
     * <br>[備  考]
     * @param userStr ユーザID文字列
     * @param password パスワード
     * @throws NamingException 更新時例外
     */
    public void updatePassword(String userStr, String password) throws NamingException {

        updatePassword(userStr, new PassWord(password));
    }

    /**
     * <br>[機  能] 指定したユーザIDのパスワードを更新する
     * <br>[解  説] userStrはDN(識別名)の形式の文字列とする
     * <br>         例：cn=test,cn=Users,dc=pri,dc=sjts,dc=co,dc=jp,dc=local
     * <br>[備  考]
     * @param userStr ユーザID文字列
     * @param password パスワード
     * @throws NamingException 更新時例外
     */
    public void updatePassword(String userStr, PassWord password) throws NamingException {

        ModificationItem[] mods = new ModificationItem[1];

        mods[0] = new ModificationItem(
                    DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", password.getBytePassword()));

        ctx__.modifyAttributes(userStr, mods);
    }

    /**
     * <br>[機  能] 指定したオブジェクトに対し、各項目を更新する
     * <br>[解  説] objMapのvalueに格納される型はjava.lang.String、または
     * <br>         jp.co.sjts.util.ldap.PassWordである事を前提とする。
     * <br>         これ以外のクラスを使用する場合はdoModifyの「TODO」部分の修正が必要。
     * <br>[備  考] objMapにパスワードを含む場合はvalueを必ずjp.co.sjts.util.ldap.PassWordで
     * <br>         格納する。
     * @param objDn オブジェクトのDN(識別名)
     * @param objMap 更新対象の項目を格納したHashMap
     * @throws NamingException 更新時例外
     */
    @SuppressWarnings("all")
    public void updateCmnInfo(String objDn, HashMap objMap) throws NamingException {

        doModify(objDn, objMap, UPDATE_FLG_EDT);
    }

    /**
     * <br>[機  能] 指定したオブジェクトに対し、各項目を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param objDn オブジェクトのDN(識別名)
     * @param objMap 更新対象に追加する項目を格納したHashMap
     * @throws NamingException 更新時例外
     */
    public void addCmnInfo(String objDn, HashMap<String, String> objMap)
    throws NamingException {

        doModify(objDn, objMap, UPDATE_FLG_ADD);
    }

    /**
     * <br>[機  能] 指定したオブジェクトから、各項目を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param objDn オブジェクトのDN(識別名)
     * @param objKey 削除する項目
     * @throws NamingException 更新時例外
     */
    public void removeCmnInfo(String objDn, String[] objKey) throws NamingException {

        if (objKey == null) {
            return;
        }
        if (objKey.length < 1) {
            return;
        }

        ModificationItem[] mods = new ModificationItem[objKey.length];

        int i = 0;
        for (String key : objKey) {
            mods[i] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(key));
            i++;
        }

        ctx__.modifyAttributes(objDn, mods);
    }

    /**
     * <br>[機  能] 指定したオブジェクトに対し、項目の追加、更新を行う
     * <br>[解  説] objMapのvalueに格納される型はjava.lang.String、または
     * <br>         jp.co.sjts.util.ldap.PassWordである事を前提とする。
     * <br>         これ以外のクラスを使用する場合はdoModifyの「TODO」部分の修正が必要。
     * <br>[備  考] objMapにパスワードを含む場合はvalueを必ずjp.co.sjts.util.ldap.PassWordで
     * <br>         格納する。
     * <br>[備  考]
     * @param objDn オブジェクトのDN(識別名)
     * @param objMap 更新対象の項目を格納したHashMap
     * @param updateFlg 更新フラグ(登録/更新)
     * @throws NamingException 更新時例外
     */
    @SuppressWarnings("all")
    public void doModify(String objDn, HashMap objMap, int updateFlg) throws NamingException {

        if (objMap == null) {
            return;
        }
        if (objMap.size() < 1) {
            return;
        }

        int mode = DirContext.REPLACE_ATTRIBUTE;
        if (updateFlg == UPDATE_FLG_ADD) {
            mode = DirContext.ADD_ATTRIBUTE;
        }

        ModificationItem[] mods = new ModificationItem[objMap.size()];
        Iterator objIt = objMap.entrySet().iterator();

        for (int i = 0; objIt.hasNext(); i++) {
            Map.Entry me = (Map.Entry) objIt.next();
            String key = (String) me.getKey();
            Object value = me.getValue();

            Class cs = value.getClass();
            //TODO
            if (cs.getName().equals("java.lang.String")) {
                mods[i] = new ModificationItem(mode, new BasicAttribute(key, (String) value));
            } else if (cs.getName().equals("jp.co.sjts.util.ldap.PassWord")) {
                mods[i] = new ModificationItem(
                        mode, new BasicAttribute(key, ((PassWord) value).getBytePassword()));
            }
        }

        ctx__.modifyAttributes(objDn, mods);
    }

    /**
     * <br>[機  能] 指定したオブジェクトを登録する(ユーザの追加など)
     * <br>[解  説] objMapのvalueに格納される型はjava.lang.String、または
     * <br>         jp.co.sjts.util.ldap.PassWordである事を前提とする。
     * <br>         これ以外のクラスを使用する場合は「TODO」部分の修正が必要。
     * <br>[備  考] objMapにパスワードを含む場合はvalueを必ずjp.co.sjts.util.ldap.PassWordで
     * <br>         格納する。
     * @param objDn オブジェクトのDN(識別名)
     * @param objMap 登録対象の項目を格納したHashMap
     * @throws NamingException 更新時例外
     */
    @SuppressWarnings("all")
    public void addObject(String objDn, HashMap objMap) throws NamingException {

        if (objMap == null) {
            return;
        }
        if (objMap.size() < 1) {
            return;
        }

        Iterator objIt = objMap.entrySet().iterator();
        Attributes attrs = new BasicAttributes(true);

        for (int i = 0; objIt.hasNext(); i++) {
            Map.Entry me = (Map.Entry) objIt.next();
            String key = (String) me.getKey();
            Object value = me.getValue();

            Class cs = value.getClass();
            //TODO
            if (cs.getName().equals("java.lang.String")) {
                attrs.put(key, (String) value);
            } else if (cs.getName().equals("jp.co.sjts.util.ldap.PassWord")) {
                attrs.put(key, ((PassWord) value).getBytePassword());
            }
        }

        ctx__.createSubcontext(objDn, attrs);
    }

    /**
     * <br>[機  能] 属性IDを指定して、Attributesから値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param attrs Attributes
     * @param name 属性ID
     * @return String 値
     */
    private String __getStrValue(Attributes attrs, String name) {

        String userId = null;
        Attribute attr = attrs.get(name);
        if (attr != null) {
            Object atrObj = null;
            try {
                atrObj = attr.get(0);
            } catch (NamingException e) {
            }
            if (atrObj != null) {
                userId = (String) atrObj;
            }
        }
        return userId;
    }
}
