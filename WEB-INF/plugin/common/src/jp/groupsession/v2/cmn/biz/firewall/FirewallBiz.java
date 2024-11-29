package jp.groupsession.v2.cmn.biz.firewall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.biz.SubnetBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnFirewallConfDao;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFirewallConfModel;
import jp.groupsession.v2.rap.mbh.IMbhUUIDBiz;
import jp.groupsession.v2.rap.mbh.MbhUUIDBiz;

public class FirewallBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FirewallBiz.class);

    /** パラメータ名 許可IPアドレス*/
    private static final String PARAMNAME_ARROW_IPADDR = "FIREWALL_ALLOW_IPADDRESS";
    /** 設定ファイル名*/
    private static final String CONF_FILENAME__ = "firewall.conf";
    /** firewall.conf 設定情報*/
    private static Properties properties__;
    /** ファイアウォール設定*/
    private CmnFirewallConfModel dbConf__;
    /** ユーザエージェント*/
    private UserAgent agent__;

    /** 個体識別番号確認の必要フラグ*/
    private boolean isAddibleUidCheck__ = false;

    /** アクセス制限結果区分 */
    private EnumFirewallErrorKbn errorKbn__ = null;


    /**
     *
     * <br>[機  能] 設定情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 設定情報
     */
    private static Properties __getProperties() {
        synchronized (FirewallBiz.class) {
            if (properties__ == null) {
                properties__ = new Properties();
                properties__.setProperty(
                        PARAMNAME_ARROW_IPADDR,
                        NullDefault.getString(
                                ConfigBundle.getValue(PARAMNAME_ARROW_IPADDR),
                                "")
                        );
            }
        }
        return properties__;
    }
    /**
     *
     * <br>[機  能] 設定ファイルのリロード
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションルートパス
     * @throws IOException IO例外
     */
    public static synchronized void loadConf(String appRoot) throws IOException {
        String filePath = IOTools.setEndPathChar(appRoot);
        filePath += "/WEB-INF/conf/";
        filePath += CONF_FILENAME__;
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Path.of(filePath)));
        } catch (IOException e) {
            throw e;
        }

        properties__ = properties;
    }
    /**
     *
     * コンストラクタ
     * <br>[解  説] 同一リクエスト内で生成したFirewallBizはフィールドを共有する
     * <br>[備  考]
     */
    private FirewallBiz() {
    }
    /**
     *
     * <br>[機  能] 同一リクエスト内で生成したFirewallBizを共有する
     * <br>[解  説]
     * <br>[備  考]
     * @return 共有インスタンス
     */
    public static FirewallBiz getInstance() {
        FirewallBiz share;
        if (RequestLocal.containsKey(FirewallBiz.class)) {
            share = RequestLocal.get(FirewallBiz.class, FirewallBiz.class);
            return share;
        }
        share = new FirewallBiz();
        RequestLocal.put(FirewallBiz.class, share);
        return share;

    }
    /**
     *
     * <br>[機  能] アクセス可能なIPアドレスからのアクセスか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param agent
     * @return true アクセス可能、false アクセス不可
     * @throws SQLException
     */
    public boolean isArrowAccess(RequestModel reqMdl,
            Connection con,
            UserAgent agent) throws SQLException {
        CmnFirewallConfModel dbConf = __getDbConf(con);

        agent__ = agent;

        if (isArrowAccessForValidate(reqMdl.getRemoteAddr(), dbConf.getCfcAllowIp())) {
            return true;
        }

        //安否確認回答判定
        if ((reqMdl.getActionPath().equals("/anpi/anp020")
                || reqMdl.getActionPath().equals("/anpi/anp020kn")
                || reqMdl.getActionPath().equals("/anpi/anp999kn"))
                ) {
            //安否確認回答時は個体識別番号チェックは不要
            isAddibleUidCheck__ = true;
            return true;
        }
        //モバイルアプリ判定
        if (agent.getClient() == UserAgent.CLIENT_TYPE_GSMOBILE
                && dbConf.getCfcAllowMbl() == 1
                ) {
            isAddibleUidCheck__ = true;
            //APIはこの時点ではユーザがnullなので
            //additionalCheckForApiを別途呼ぶ
            if (reqMdl.getSmodel() == null) {
                return true;
            }
            return __uniqueClientCheck(reqMdl.getSmodel(), con, false);
        }
        errorKbn__ = EnumFirewallErrorKbn.CANT_ACCESS;
        return false;

    }

    /**
     *
     * <br>[機  能] アクセス可能なIPアドレスからのアクセスか判定する
     * <br>[解  説] 入力チェック用のためモバイル判定と安否確認回答判定による許可は判定されない
     * <br>[備  考]
     * @param ipaddr
     * @param formArrowIp 入力されたアクセス可能なIPアドレス
     * @return true アクセス可能、false アクセス不可
     * @throws SQLException
     */
    public boolean isArrowAccessForValidate(String ipaddr, String formArrowIp) throws SQLException {
        String fileArrowIp = NullDefault.getString(
                __getProperties().getProperty(PARAMNAME_ARROW_IPADDR),
                "");

        if (ValidateUtil.isEmpty(formArrowIp)) {
            return true;
        }
        List<String> ipTextList = new ArrayList<>();
        ipTextList.add("127.0.0.1");
        ipTextList.add("::1");
        ipTextList.add("0:0:0:0:0:0:0:1");
        ipTextList.addAll(
                List.of(formArrowIp.split("\n"))
                );
        ipTextList.addAll(
                List.of(fileArrowIp.split(","))
                );


        return ipTextList.stream()
                .map(txt -> txt.trim())
                .anyMatch(txt -> __isContainAddr(txt, ipaddr));

    }
    /**
     *
     * <br>[機  能] ファイアウォール設定の取得
     * <br>[解  説] キャッシュを保管し、再利用する
     * <br>[備  考]
     * <br>
     * @param con
     * @return ファイアウォール設定
     * @throws SQLException
     */
    private CmnFirewallConfModel __getDbConf(Connection con) throws SQLException {
        if (dbConf__ == null) {

            CmnFirewallConfDao dao = new CmnFirewallConfDao(con);
            dbConf__ = dao.select();
        }
        return dbConf__;
    }
    /**
     *
     * <br>[機  能] txtの表すアドレス範囲内にipaddrが含まれるか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param txt アドレス範囲文字列
     * @param ipaddr ipアドレス
     * @return true txtの表すアドレス範囲内にipaddrが含まれる
     */
    private boolean __isContainAddr(String txt, String ipaddr) {
        boolean ret = false;
        if (ValidateUtil.isEmpty(txt)
                || ValidateUtil.isEmpty(ipaddr)) {
            return ret;
        }
        log__.info("--- IP判定 開始 " + ipaddr + " ⊂ " + txt + "?");
        String chkStr = txt;
        try {
            //プレフィックス or サブネットマスク指定時
            if (txt.indexOf(SubnetBiz.DELIMITER) > 0) {
                SubnetBiz ipBiz = new SubnetBiz();
                return ipBiz.checkIPinRange(txt, ipaddr);
            }

            if (chkStr.indexOf("*") < 0) {
                ret = ipaddr.equals(chkStr);
                return ret;
            }
            chkStr = chkStr.substring(0, chkStr.indexOf("*"));

            if (StringUtil.isNullZeroString(chkStr)) {
                ret = true;
                return ret;
            }
            if (ipaddr.startsWith(chkStr)) {
                return true;
            }

        } finally {
            log__.info("--- IP判定 終了 " + ret);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 実行ユーザ確定後の追加ファイアウォールチェック
     * <br>[解  説] ユーザが特定されている場合かつモバイル端末の場合に個体識別番号をチェックする
     * <br>[備  考] ログイン画面などisArrowAccess時にユーザ情報が取れない画面で使用する
     * @param loginUser
     * @param con
     * @param isLoginApi ログイン用APIか
     * @return true アクセス可能、false アクセス不可
     * @throws SQLException
     */
    public boolean additionalCheckForMbl(
            BaseUserModel loginUser,
            Connection con,
            boolean isLoginApi) throws SQLException {

        return __uniqueClientCheck(loginUser, con, isLoginApi);
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説] モバイル端末の場合に個体識別番号をチェックする
     * <br>[備  考]
     * @param loginUser
     * @param con
     * @param isLoginApi ログイン用APIか
     * @return true アクセス可能、false アクセス不可
     * @throws SQLException
     */
    private boolean __uniqueClientCheck(
            BaseUserModel loginUser,
            Connection con,
            boolean isLoginApi) throws SQLException {
        if (isAddibleUidCheck__ == false) {
            return true;
        }

        if (loginUser == null) {
            return true;
        }
        if (agent__ == null) {
            errorKbn__ = EnumFirewallErrorKbn.CANT_ACCESS;
            //"FirewallBiz.isArrowAccess実行前に使用した"
            return false;

        }

        if (agent__.getClient() != UserAgent.CLIENT_TYPE_GSMOBILE) {
            return true;
        }
        IMbhUUIDBiz uidBiz = MbhUUIDBiz.getInstance();
        boolean ret = uidBiz.checkUid(loginUser, agent__, con, isLoginApi);
        if (ret == false) {
            errorKbn__ = EnumFirewallErrorKbn.NOTALLOW_CLIANT;
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 安否確認回答画面用の追加ファイアウォールチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param loginUser
     * @param con
     * @return true アクセス可能、false アクセス不可
     * @throws SQLException
     */
    public boolean additionalCheckForAnpAncer(BaseUserModel loginUser,
            Connection con) throws SQLException {
        CmnFirewallConfModel dbConf = __getDbConf(con);

        if (dbConf.getCfcAllowAnp() == 1) {
            return true;
        }

        if (isAddibleUidCheck__ == false) {
            return true;
        }

        if (agent__ == null) {
            //"FirewallBiz.isArrowAccess実行前に使用した"
            errorKbn__ = EnumFirewallErrorKbn.CANT_ACCESS;
            return false;

        }
        //モバイルアプリ判定
        if (agent__.getClient() == UserAgent.CLIENT_TYPE_GSMOBILE
                && dbConf.getCfcAllowMbl() == 1) {
            isAddibleUidCheck__ = true;
            return __uniqueClientCheck(loginUser, con, false);
        }
        errorKbn__ = EnumFirewallErrorKbn.CANT_ACCESS;
        return false;

    }
    /**
     * アクセス制限判定結果による処理のハンドリング
     * @param cantAccsessFunc ファイアウォールによるアクセス制限時処理
     * @param notAllowClientFunc 個体識別番号制限による制限時処理
     * @return アクセス制限判定結果による処理の実行結果
     */
    public ActionForward handlingFirewallError(
        Supplier<ActionForward> cantAccsessFunc,
        Supplier<ActionForward> notAllowClientFunc
    ) {
        if (errorKbn__ == EnumFirewallErrorKbn.CANT_ACCESS) {
            return cantAccsessFunc.get();
        }
        if (errorKbn__ == EnumFirewallErrorKbn.NOTALLOW_CLIANT) {
            return notAllowClientFunc.get();
        }
        return null;
    }

}
