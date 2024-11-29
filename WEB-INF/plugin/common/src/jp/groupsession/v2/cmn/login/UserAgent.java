package jp.groupsession.v2.cmn.login;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.BrowserUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 *
 * <br>[機  能] ユーザエージェントから情報を構造化する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserAgent extends AbstractModel {
    /** クライアントタイプ 選択していない */
    public static final int CLIENT_TYPE_NOSEL = -1;
    /** クライアントタイプ その他 */
    public static final int CLIENT_TYPE_OTHER = 0;
    /** クライアントタイプ CrossRide */
    public static final int CLIENT_TYPE_CROSSRIDE = 1;
    /** クライアントタイプ GSモバイル */
    public static final int CLIENT_TYPE_GSMOBILE = 2;

    /** 個体識別番号*/
    private String cuid__;
    /** キャリア区分*/
    private int clhKbn__;
    /** ユーザエージェント*/
    private String agentStr__;
    /** クライアントタイプ*/
    private int client__;

    /**
     *
     * コンストラクタ
     */
    private UserAgent() {
    }
    /**
     *
     * コンストラクタ
     * @param req リクエスト
     */
    public UserAgent(HttpServletRequest req) {
        this();
        try {
            init(req);
        } catch (Exception e__) {

            //リクエスト解析による例外は
            //エラー画面の表示で再度例外を起こして無限ループするため
            //ハンドリングしない
        }
    }
    /**
     *
     * <br>[機  能] リクエストから初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public void init(HttpServletRequest req) {
        String agent = NullDefault.getString(
                req.getHeader(BrowserUtil.USER_AGENT_KEYWORD), "");
        setAgentStr(agent);
        String uid = "";
        int client = CLIENT_TYPE_OTHER;
        //固体識別番号判別
        // クライアント = SoftBank
        if (BrowserUtil.isVodafone(req) || BrowserUtil.isSoftBank(req)) {
            // SoftBankはエージェントから固体識別番号抽出
            String[] splitAgent = agent.split("/");
            if (splitAgent != null && splitAgent.length >= 0) {
                for (int i = 0; i < splitAgent.length; i++) {
                    if (splitAgent[i].startsWith("SN")) {
                        uid = splitAgent[i];
                        if (uid.indexOf(" ") > 0) {
                            uid = uid.substring(0, uid.indexOf(" "));
                        }
                    }
                }
            }
            // クライアント ＝ AU
        } else if (BrowserUtil.isAu(req)) {
            uid = req.getHeader("X-Up-Subno");
            // クライアント ＝ docomo
        } else if (BrowserUtil.isDocomo(req)) {
            uid = req.getHeader("user-agent");
            int begin = uid.indexOf("ser");
            if (begin > 0) {
                String str = uid.substring(begin);
                int end = -1;
                if (str.length() > 17) {
                    end = begin + 18;
                }
                uid = uid.substring(begin, end);
            }
        //クライアント　＝　GSモバイルアシスト
        } else if (BrowserUtil.isMobileApps(req)) {
            client = CLIENT_TYPE_GSMOBILE;
            uid = req.getHeader("user-agent");
            int begin = uid.indexOf("SN(",
                                    uid.indexOf(BrowserUtil.BROWSER_KEYWORD_GSMBA));
            if (begin > 0) {
                begin += 3;
                int end = uid.indexOf(")", begin);
                if (end > begin && end <= uid.length()) {
                    uid = uid.substring(begin, end);
                } else {
                    uid = "";
                }
            } else {
                uid = "";
            }
        //クライアント　＝　GSモバイルアシスト
        } else if (BrowserUtil.isCrossRide(req)) {
            client = CLIENT_TYPE_CROSSRIDE;
            uid = "";
        } else {
            uid = "";
        }
        setCuid(uid);

        //キャリア判別
        int clhKbn = 0;
        // キャリア DoCoMo
        if (BrowserUtil.isDocomo(req)) {
            clhKbn = GSConstCommon.CAR_KBN_DOCOMO;

            // キャリア KDDI
        } else if (BrowserUtil.isAuWap20(req)
                || agent.indexOf("BREW-Applet") >= 0) {
            clhKbn = GSConstCommon.CAR_KBN_KDDI;

            // キャリア SoftBank
        } else if (BrowserUtil.isVodafone(req) || BrowserUtil.isSoftBank(req)) {
            clhKbn = GSConstCommon.CAR_KBN_SOFTBANK;
        }
        setClhKbn(clhKbn);
        setClient(client);

    }
    /**
     * <p>cuid を取得します。
     * @return cuid
     * @see jp.groupsession.v2.cmn.login.UserAgent#cuid__
     */
    public String getCuid() {
        return cuid__;
    }
    /**
     * <p>cuid をセットします。
     * @param cuid cuid
     * @see jp.groupsession.v2.cmn.login.UserAgent#cuid__
     */
    public void setCuid(String cuid) {
        cuid__ = cuid;
    }
    /**
     * <p>clhKbn を取得します。
     * @return clhKbn
     * @see jp.groupsession.v2.cmn.login.UserAgent#clhKbn__
     */
    public int getClhKbn() {
        return clhKbn__;
    }
    /**
     * <p>clhKbn をセットします。
     * @param clhKbn clhKbn
     * @see jp.groupsession.v2.cmn.login.UserAgent#clhKbn__
     */
    public void setClhKbn(int clhKbn) {
        clhKbn__ = clhKbn;
    }
    /**
     * <p>agentStr を取得します。
     * @return agentStr
     * @see jp.groupsession.v2.cmn.login.UserAgent#agentStr__
     */
    public String getAgentStr() {
        return agentStr__;
    }
    /**
     * <p>agentStr をセットします。
     * @param agentStr agentStr
     * @see jp.groupsession.v2.cmn.login.UserAgent#agentStr__
     */
    public void setAgentStr(String agentStr) {
        agentStr__ = agentStr;
    }
    /**
     * <p>client を取得します。
     * @return client
     * @see jp.groupsession.v2.cmn.login.UserAgent#client__
     */
    public int getClient() {
        return client__;
    }
    /**
     * <p>client をセットします。
     * @param client client
     * @see jp.groupsession.v2.cmn.login.UserAgent#client__
     */
    public void setClient(int client) {
        client__ = client;
    }
}
