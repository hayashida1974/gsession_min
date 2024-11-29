package jp.groupsession.v2.wml.wml040;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.wml.wml020.Wml020ParamModel;

/**
 * <br>[機  能] WEBメール アカウント登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml040ParamModel extends Wml020ParamModel {
    /** メール受信サーバの種類 POP */
    public static final int RSERVERTYPE_POP = 0;
    /** メール受信サーバの種類 IMAP */
    public static final int RSERVERTYPE_IMAP = 1;

    /** ディクス容量 無制限 */
    public static final int DISKSIZE_UNLIMITED = 0;
    /** ディクス容量 制限する */
    public static final int DISKSIZE_LIMIT = 1;
    /** ディクス容量 特例設定 設定 */
    public static final int DISK_SPS_SET = 1;
    /** 使用者 グループを指定 */
    public static final int USERKBN_GROUP = 0;
    /** 使用者 ユーザを指定 */
    public static final int USERKBN_USER = 1;
    /** 受信時削除 削除しない */
    public static final int DELRECEIVE_NO = 0;
    /** 受信時削除 削除する */
    public static final int DELRECEIVE_YES = 1;
    /** 受信済みでも受信 受信しない */
    public static final int RERECEIVE_NO = 0;
    /** 受信済みでも受信 受信する */
    public static final int RERECEIVE_YES = 1;
    /** APOP OFF */
    public static final int APOP_OFF = 0;
    /** APOP ON */
    public static final int APOP_ON = 1;
    /** TOPコマンド 有効 */
    public static final int TOP_COMMAND_ENABLE = 0;
    /** TOPコマンド 無効 */
    public static final int TOP_COMMAND_DISABLE = 1;
    /** SMTP認証 OFF */
    public static final int SMTPAUTH_OFF = 0;
    /** SMTP認証 ON */
    public static final int SMTPAUTH_ON = 1;
    /** 送信前POP認証 OFF */
    public static final int POPBSMTP_OFF = 0;
    /** 送信前POP認証 ON */
    public static final int POPBSMTP_ON = 1;
    /** 送信文字コード ISO-2022-JP */
    public static final int ENCODE_SEND_2022JP = 0;
    /** 送信文字コード Unicode(UTF-8) */
    public static final int ENCODE_SEND_UNICODE = 1;
    /** 自動受信 自動受信しない */
    public static final int AUTORESV_OFF = 0;
    /** 自動受信 自動受信する */
    public static final int AUTORESV_ON = 1;
    /** 送信メール形式 標準 */
    public static final int SEND_MAIL_NORMAL = 0;
    /** 送信メール形式 HTMLメール */
    public static final int SEND_MAIL_HTML = 1;

    /** アカウントID */
    private String wml040accountId__ = null;
    /** アカウント名 */
    private String wml040name__ = null;
    /** メールアドレス */
    private String wml040mailAddress__ = null;

    /** 認証形式 */
    private int wml040authMethod__ = 0;
    /** プロバイダ */
    private int wml040provider__ = 0;
    /** OAuth認証 アカウントID */
    private String wml040authAccount__ = null;
    /** OAuth認証済みフラグ */
    private boolean wml040oauthCompFlg__ = false;
    /** OAuth認証情報トークンSID */
    private int wml040cotSid__ = 0;
    /** プロバイダ一覧 */
    private List<LabelValueBean> wml040providerList__ = null;

    /** メール受信サーバ */
    private String wml040receiveServer__ = null;
    /** メール受信サーバ ポート番号 */
    private String wml040receiveServerPort__ = null;
    /** メール受信サーバ SSL */
    private int wml040receiveServerEncrypt__ = 0;
    /** メール受信サーバの種類 */
    private int wml040receiveServerType__ = RSERVERTYPE_POP;
    /** メール受信サーバ ユーザID */
    private String wml040receiveServerUser__ = null;
    /** メール受信サーバ パスワード */
    private String wml040receiveServerPassword__ = null;
    /** メール送信サーバ */
    private String wml040sendServer__ = null;
    /** メール送信サーバ名 ポート番号 */
    private String wml040sendServerPort__ = null;
    /** メール受信サーバ SSL */
    private int wml040sendServerEncrypt__ = 0;
    /** メール送信サーバ ユーザ名 */
    private String wml040sendServerUser__ = null;
    /** メール送信サーバ名 パスワード */
    private String wml040sendServerPassword__ = null;
    /** ディスク容量 */
    private int wml040diskSize__ = DISKSIZE_UNLIMITED;
    /** ディスク容量 最大値 */
    private String wml040diskSizeLimit__ = null;
    /** ディスク容量 特例設定 */
    private int wml040diskSps__ = 0;
    /** ディスク容量 管理者強制設定  0:制限なし 1:制限あり(一般ユーザ&&強制制限あり)*/
    private int wml040diskSizeComp__ = GSConstWebmail.WAC_DISK_NO_ADM_COMP;
    /** 管理者設定 ディスク容量設定区分 */
    private int wml040admDisk__ = GSConstWebmail.WAC_DISK_UNLIMITED;
    /** 管理者設定 ディスク容量 */
    private int wml040admDiskSize__ = 0;
    /** 備考 */
    private String wml040biko__ = null;
    /** 使用者 */
    private int wml040userKbn__ = USERKBN_USER;
    /** 使用者 グループ */
    private String[] wml040userKbnGroup__ = null;
    /** 使用者 ユーザ */
    private String[] wml040userKbnUser__ = null;
    /** 代理人 */
    private String[] wml040proxyUser__ = null;
    /** 組織名 */
    private String wml040organization__ = null;
    /** 署名 */
    private String wml040sign__ = null;
    /** 署名 */
    private int wml040signNo__ = 0;
    /** 返信時署名表示 */
    private int wml040receiveDsp__ = GSConstWebmail.SIGN_RECEIVE_DSP;
    /** 返信時署名位置 */
    private int wml040signPoint__ = GSConstWebmail.SIGN_POINT_BOTTOM;
    /** 署名 自動挿入 */
    private int wml040signAuto__ = 0;
    /** 自動TO */
    private String wml040autoTo__ = null;
    /** 自動CC */
    private String wml040autoCc__ = null;
    /** 自動BCC */
    private String wml040autoBcc__ = null;
    /** 受信時削除 */
    private int wml040delReceive__ = DELRECEIVE_NO;
    /** 受信済みでも受信 */
    private int wml040reReceive__ = RERECEIVE_NO;
    /** APOPのON/OFF */
    private int wml040apop__ = APOP_OFF;
    /** TOPコマンドの有効/無効 */
    private int wml040topCmd__ = TOP_COMMAND_ENABLE;
    /** SMTP認証ON/OFF */
    private int wml040smtpAuth__ = SMTPAUTH_OFF;
    /** 送信前POP認証 */
    private int wml040popBSmtp__ = POPBSMTP_OFF;
    /** 送信文字コード */
    private int wml040encodeSend__ = ENCODE_SEND_2022JP;
    /** 自動受信 */
    private int wml040autoResv__ = AUTORESV_ON;
    /** 送信メール形式 */
    private int wml040sendType__ = SEND_MAIL_NORMAL;
    /** 宛先の確認 */
    private int wml040checkAddress__ = 0;
    /** 添付ファイルの確認 */
    private int wml040checkFile__ = 0;
    /** 添付ファイル自動圧縮 */
    private int wml040compressFile__ = 0;
    /** 添付ファイル自動圧縮 初期値 */
    private int wml040compressFileDef__ = 0;
    /** 時間差送信 */
    private int wml040timeSent__ = 0;
    /** 時間差送信 初期値 */
    private int wml040timeSentDef__ = 0;
    /** 自動保存 */
    private String wml040autoSaveMin__ = null;
    /** テーマ */
    private int wml040theme__ = 0;
    /** 引用符 */
    private int wml040quotes__ = 0;

    /** 初期表示フラグ */
    private int wml040initFlg__ = 0;

    /** 権限区分 0:管理者一括 1:各アカウント */
    private int wml040PermitKbn__ = GSConstWebmail.PERMIT_ACCOUNT;
    /** 自動受信設定時間 */
    private int wml040AutoReceiveTime__ = GSConstWebmail.AUTO_RECEIVE_5;
    /** 自動受信設定時間 ラベル */
    private List<LabelValueBean> wml040AutoRsvKeyLabel__ = null;

    /** 使用者 ユーザ グループ */
    private String wml040userKbnUserGroup__ = null;
    /** 使用者 選択グループ */
    private List<LabelValueBean> userKbnGroupSelectCombo__  = null;
    /** 使用者 選択ユーザ */
    private List<UsrLabelValueBean> userKbnUserSelectCombo__  = null;
    /** 使用者 グループ UI */
    private UserGroupSelector wml040userKbnGroupUI__ = null;
    /** 使用者 ユーザ UI */
    private UserGroupSelector wml040userKbnUserUI__ = null;

    /** 代理人 許可 */
    private boolean wml040proxyUserFlg__ = false;
    /** 代理人 グループ */
    private String wml040proxyUserGroup__ = null;
    /** 代理人 選択ユーザ */
    private List<UsrLabelValueBean> proxyUserSelectCombo__  = null;
    /** 代理人 UI */
    private UserGroupSelector wml040proxyUserUI__ = null;

    /** 署名 一覧 */
    private List<LabelValueBean> wml040signList__ = null;
    /** テーマ 一覧 */
    private List<LabelValueBean> wml040themeList__ = null;
    /** 引用符 一覧 */
    private List<LabelValueBean> wml040quotesList__ = null;

    /** 暗号化プロトコル 一覧 */
    private List<LabelValueBean> wml040AngoProtocolCombo__ = null;
    /** 自動保存リスト */
    private List<LabelValueBean> wml040autoSaveList__ = null;

    //--------- アカウントマネージャー画面のパラメータ
    /** 検索キーワード */
    private String wml030keyword__ = null;
    /** グループ */
    private int wml030group__ = -1;
    /** ユーザ */
    private int wml030user__ = -1;
    /** ページ上段 */
    private int wml030pageTop__ = 0;
    /** ページ下段 */
    private int wml030pageBottom__ = 0;

    /** 検索キーワード(検索条件保持) */
    private String wml030svKeyword__ = null;
    /** グループ(検索条件保持) */
    private int wml030svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int wml030svUser__ = -1;

    /** ソートキー */
    private int wml030sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int wml030order__ = GSConstWebmail.ORDER_ASC;

    /** 検索実行フラグ */
    private int wml030searchFlg__ = 0;

    /** 表示判定 */
    private int wml040elementKbn__ = 0;

    /** セッションユーザ情報 */
    private BaseUserModel wml040sessionUserData__ = null;
    /** WEBメール管理者フラグ */
    private boolean wml040webmailAdmin__ = false;

    /**
     * <p>wml040apop を取得します。
     * @return wml040apop
     */
    public int getWml040apop() {
        return wml040apop__;
    }
    /**
     * <p>wml040apop をセットします。
     * @param wml040apop wml040apop
     */
    public void setWml040apop(int wml040apop) {
        wml040apop__ = wml040apop;
    }
    /**
     * <p>wml040topCmd を取得します。
     * @return wml040topCmd
     */
    public int getWml040topCmd() {
        return wml040topCmd__;
    }
    /**
     * <p>wml040topCmd をセットします。
     * @param wml040topCmd wml040topCmd
     */
    public void setWml040topCmd(int wml040topCmd) {
        wml040topCmd__ = wml040topCmd;
    }
    /**
     * <p>wml040autoBcc を取得します。
     * @return wml040autoBcc
     */
    public String getWml040autoBcc() {
        return wml040autoBcc__;
    }
    /**
     * <p>wml040autoBcc をセットします。
     * @param wml040autoBcc wml040autoBcc
     */
    public void setWml040autoBcc(String wml040autoBcc) {
        wml040autoBcc__ = wml040autoBcc;
    }
    /**
     * <p>wml040autoCc を取得します。
     * @return wml040autoCc
     */
    public String getWml040autoCc() {
        return wml040autoCc__;
    }
    /**
     * <p>wml040autoCc をセットします。
     * @param wml040autoCc wml040autoCc
     */
    public void setWml040autoCc(String wml040autoCc) {
        wml040autoCc__ = wml040autoCc;
    }
    /**
     * <p>wml040autoTo を取得します。
     * @return wml040autoTo
     */
    public String getWml040autoTo() {
        return wml040autoTo__;
    }
    /**
     * <p>wml040autoTo をセットします。
     * @param wml040autoTo wml040autoTo
     */
    public void setWml040autoTo(String wml040autoTo) {
        wml040autoTo__ = wml040autoTo;
    }
    /**
     * <p>wml040biko を取得します。
     * @return wml040biko
     */
    public String getWml040biko() {
        return wml040biko__;
    }
    /**
     * <p>wml040biko をセットします。
     * @param wml040biko wml040biko
     */
    public void setWml040biko(String wml040biko) {
        wml040biko__ = wml040biko;
    }
    /**
     * <p>wml040delReceive を取得します。
     * @return wml040delReceive
     */
    public int getWml040delReceive() {
        return wml040delReceive__;
    }
    /**
     * <p>wml040delReceive をセットします。
     * @param wml040delReceive wml040delReceive
     */
    public void setWml040delReceive(int wml040delReceive) {
        wml040delReceive__ = wml040delReceive;
    }
    /**
     * <p>wml040diskSize を取得します。
     * @return wml040diskSize
     */
    public int getWml040diskSize() {
        return wml040diskSize__;
    }
    /**
     * <p>wml040diskSize をセットします。
     * @param wml040diskSize wml040diskSize
     */
    public void setWml040diskSize(int wml040diskSize) {
        wml040diskSize__ = wml040diskSize;
    }
    /**
     * <p>wml040diskSizeLimit を取得します。
     * @return wml040diskSizeLimit
     */
    public String getWml040diskSizeLimit() {
        return wml040diskSizeLimit__;
    }
    /**
     * <p>wml040diskSizeLimit をセットします。
     * @param wml040diskSizeLimit wml040diskSizeLimit
     */
    public void setWml040diskSizeLimit(String wml040diskSizeLimit) {
        wml040diskSizeLimit__ = wml040diskSizeLimit;
    }
    /**
     * <p>wml040diskSps を取得します。
     * @return wml040diskSps
     */
    public int getWml040diskSps() {
        return wml040diskSps__;
    }
    /**
     * <p>wml040diskSps をセットします。
     * @param wml040diskSps wml040diskSps
     */
    public void setWml040diskSps(int wml040diskSps) {
        wml040diskSps__ = wml040diskSps;
    }
    /**
     * <p>wml040encodeSend を取得します。
     * @return wml040encodeSend
     */
    public int getWml040encodeSend() {
        return wml040encodeSend__;
    }
    /**
     * <p>wml040encodeSend をセットします。
     * @param wml040encodeSend wml040encodeSend
     */
    public void setWml040encodeSend(int wml040encodeSend) {
        wml040encodeSend__ = wml040encodeSend;
    }
    /**
     * <p>wml040mailAddress を取得します。
     * @return wml040mailAddress
     */
    public String getWml040mailAddress() {
        return wml040mailAddress__;
    }
    /**
     * <p>wml040mailAddress をセットします。
     * @param wml040mailAddress wml040mailAddress
     */
    public void setWml040mailAddress(String wml040mailAddress) {
        wml040mailAddress__ = wml040mailAddress;
    }
    /**
     * <p>wml040accountId を取得します。
     * @return wml040accountId
     */
    public String getWml040accountId() {
        return wml040accountId__;
    }
    /**
     * <p>wml040accountId をセットします。
     * @param wml040accountId wml040accountId
     */
    public void setWml040accountId(String wml040accountId) {
        wml040accountId__ = wml040accountId;
    }
    /**
     * <p>wml040name を取得します。
     * @return wml040name
     */
    public String getWml040name() {
        return wml040name__;
    }
    /**
     * <p>wml040name をセットします。
     * @param wml040name wml040name
     */
    public void setWml040name(String wml040name) {
        wml040name__ = wml040name;
    }
    /**
     * <p>wml040organization を取得します。
     * @return wml040organization
     */
    public String getWml040organization() {
        return wml040organization__;
    }
    /**
     * <p>wml040organization をセットします。
     * @param wml040organization wml040organization
     */
    public void setWml040organization(String wml040organization) {
        wml040organization__ = wml040organization;
    }
    /**
     * <p>wml040popBSmtp を取得します。
     * @return wml040popBSmtp
     */
    public int getWml040popBSmtp() {
        return wml040popBSmtp__;
    }
    /**
     * <p>wml040popBSmtp をセットします。
     * @param wml040popBSmtp wml040popBSmtp
     */
    public void setWml040popBSmtp(int wml040popBSmtp) {
        wml040popBSmtp__ = wml040popBSmtp;
    }
    /**
     * <p>wml040receiveServer を取得します。
     * @return wml040receiveServer
     */
    public String getWml040receiveServer() {
        return wml040receiveServer__;
    }
    /**
     * <p>wml040receiveServer をセットします。
     * @param wml040receiveServer wml040receiveServer
     */
    public void setWml040receiveServer(String wml040receiveServer) {
        wml040receiveServer__ = wml040receiveServer;
    }
    /**
     * <p>wml040receiveServerPassword を取得します。
     * @return wml040receiveServerPassword
     */
    public String getWml040receiveServerPassword() {
        return wml040receiveServerPassword__;
    }
    /**
     * <p>wml040receiveServerPassword をセットします。
     * @param wml040receiveServerPassword wml040receiveServerPassword
     */
    public void setWml040receiveServerPassword(String wml040receiveServerPassword) {
        wml040receiveServerPassword__ = wml040receiveServerPassword;
    }
    /**
     * <p>wml040receiveServerPort を取得します。
     * @return wml040receiveServerPort
     */
    public String getWml040receiveServerPort() {
        return wml040receiveServerPort__;
    }
    /**
     * <p>wml040receiveServerPort をセットします。
     * @param wml040receiveServerPort wml040receiveServerPort
     */
    public void setWml040receiveServerPort(String wml040receiveServerPort) {
        wml040receiveServerPort__ = wml040receiveServerPort;
    }
    /**
     * <p>wml040receiveServerType を取得します。
     * @return wml040receiveServerType
     */
    public int getWml040receiveServerType() {
        return wml040receiveServerType__;
    }
    /**
     * <p>wml040receiveServerType をセットします。
     * @param wml040receiveServerType wml040receiveServerType
     */
    public void setWml040receiveServerType(int wml040receiveServerType) {
        wml040receiveServerType__ = wml040receiveServerType;
    }
    /**
     * <p>wml040receiveServerUser を取得します。
     * @return wml040receiveServerUser
     */
    public String getWml040receiveServerUser() {
        return wml040receiveServerUser__;
    }
    /**
     * <p>wml040receiveServerUser をセットします。
     * @param wml040receiveServerUser wml040receiveServerUser
     */
    public void setWml040receiveServerUser(String wml040receiveServerUser) {
        wml040receiveServerUser__ = wml040receiveServerUser;
    }
    /**
     * <p>wml040reReceive を取得します。
     * @return wml040reReceive
     */
    public int getWml040reReceive() {
        return wml040reReceive__;
    }
    /**
     * <p>wml040reReceive をセットします。
     * @param wml040reReceive wml040reReceive
     */
    public void setWml040reReceive(int wml040reReceive) {
        wml040reReceive__ = wml040reReceive;
    }
    /**
     * <p>wml040sendServer を取得します。
     * @return wml040sendServer
     */
    public String getWml040sendServer() {
        return wml040sendServer__;
    }
    /**
     * <p>wml040sendServer をセットします。
     * @param wml040sendServer wml040sendServer
     */
    public void setWml040sendServer(String wml040sendServer) {
        wml040sendServer__ = wml040sendServer;
    }
    /**
     * <p>wml040sendServerPassword を取得します。
     * @return wml040sendServerPassword
     */
    public String getWml040sendServerPassword() {
        return wml040sendServerPassword__;
    }
    /**
     * <p>wml040sendServerPassword をセットします。
     * @param wml040sendServerPassword wml040sendServerPassword
     */
    public void setWml040sendServerPassword(String wml040sendServerPassword) {
        wml040sendServerPassword__ = wml040sendServerPassword;
    }
    /**
     * <p>wml040sendServerPort を取得します。
     * @return wml040sendServerPort
     */
    public String getWml040sendServerPort() {
        return wml040sendServerPort__;
    }
    /**
     * <p>wml040sendServerPort をセットします。
     * @param wml040sendServerPort wml040sendServerPort
     */
    public void setWml040sendServerPort(String wml040sendServerPort) {
        wml040sendServerPort__ = wml040sendServerPort;
    }
    /**
     * <p>wml040sendServerUser を取得します。
     * @return wml040sendServerUser
     */
    public String getWml040sendServerUser() {
        return wml040sendServerUser__;
    }
    /**
     * <p>wml040sendServerUser をセットします。
     * @param wml040sendServerUser wml040sendServerUser
     */
    public void setWml040sendServerUser(String wml040sendServerUser) {
        wml040sendServerUser__ = wml040sendServerUser;
    }
    /**
     * <p>wml040sign を取得します。
     * @return wml040sign
     */
    public String getWml040sign() {
        return wml040sign__;
    }
    /**
     * <p>wml040sign をセットします。
     * @param wml040sign wml040sign
     */
    public void setWml040sign(String wml040sign) {
        wml040sign__ = wml040sign;
    }
    /**
     * <p>wml040signAuto を取得します。
     * @return wml040signAuto
     */
    public int getWml040signAuto() {
        return wml040signAuto__;
    }
    /**
     * <p>wml040signAuto をセットします。
     * @param wml040signAuto wml040signAuto
     */
    public void setWml040signAuto(int wml040signAuto) {
        wml040signAuto__ = wml040signAuto;
    }
    /**
     * <p>wml040signNo を取得します。
     * @return wml040signNo
     */
    public int getWml040signNo() {
        return wml040signNo__;
    }
    /**
     * <p>wml040signNo をセットします。
     * @param wml040signNo wml040signNo
     */
    public void setWml040signNo(int wml040signNo) {
        wml040signNo__ = wml040signNo;
    }
    /**
     * <p>wml040smtpAuth を取得します。
     * @return wml040smtpAuth
     */
    public int getWml040smtpAuth() {
        return wml040smtpAuth__;
    }
    /**
     * <p>wml040smtpAuth をセットします。
     * @param wml040smtpAuth wml040smtpAuth
     */
    public void setWml040smtpAuth(int wml040smtpAuth) {
        wml040smtpAuth__ = wml040smtpAuth;
    }
    /**
     * <p>wml040userKbn を取得します。
     * @return wml040userKbn
     */
    public int getWml040userKbn() {
        return wml040userKbn__;
    }
    /**
     * <p>wml040userKbn をセットします。
     * @param wml040userKbn wml040userKbn
     */
    public void setWml040userKbn(int wml040userKbn) {
        wml040userKbn__ = wml040userKbn;
    }
    /**
     * <p>wml040userKbnGroup を取得します。
     * @return wml040userKbnGroup
     */
    public String[] getWml040userKbnGroup() {
        return wml040userKbnGroup__;
    }
    /**
     * <p>wml040userKbnGroup をセットします。
     * @param wml040userKbnGroup wml040userKbnGroup
     */
    public void setWml040userKbnGroup(String[] wml040userKbnGroup) {
        wml040userKbnGroup__ = wml040userKbnGroup;
    }
    /**
     * <p>wml040userKbnUser を取得します。
     * @return wml040userKbnUser
     */
    public String[] getWml040userKbnUser() {
        return wml040userKbnUser__;
    }
    /**
     * <p>wml040userKbnUser をセットします。
     * @param wml040userKbnUser wml040userKbnUser
     */
    public void setWml040userKbnUser(String[] wml040userKbnUser) {
        wml040userKbnUser__ = wml040userKbnUser;
    }
    /**
     * <p>wml040userKbnGroupUI を取得します。
     * @return wml040userKbnGroupUI
     */
    public UserGroupSelector getWml040userKbnGroupUI() {
        return wml040userKbnGroupUI__;
    }
    /**
     * <p>wml040userKbnGroupUI をセットします。
     * @param wml040userKbnGroupUI wml040userKbnGroupUI
     */
    public void setWml040userKbnGroupUI(UserGroupSelector wml040userKbnGroupUI) {
        wml040userKbnGroupUI__ = wml040userKbnGroupUI;
    }
    /**
     * <p>wml040userKbnUserUI を取得します。
     * @return wml040userKbnUserUI
     */
    public UserGroupSelector getWml040userKbnUserUI() {
        return wml040userKbnUserUI__;
    }
    /**
     * <p>wml040userKbnUserUI をセットします。
     * @param wml040userKbnUserUI wml040userKbnUserUI
     */
    public void setWml040userKbnUserUI(UserGroupSelector wml040userKbnUserUI) {
        wml040userKbnUserUI__ = wml040userKbnUserUI;
    }
    /**
     * <p>wml040initFlg を取得します。
     * @return wml040initFlg
     */
    public int getWml040initFlg() {
        return wml040initFlg__;
    }
    /**
     * <p>wml040initFlg をセットします。
     * @param wml040initFlg wml040initFlg
     */
    public void setWml040initFlg(int wml040initFlg) {
        wml040initFlg__ = wml040initFlg;
    }

    /**
     * <p>wml040userKbnUserGroup を取得します。
     * @return wml040userKbnUserGroup
     */
    public String getWml040userKbnUserGroup() {
        return wml040userKbnUserGroup__;
    }
    /**
     * <p>wml040userKbnUserGroup をセットします。
     * @param wml040userKbnUserGroup wml040userKbnUserGroup
     */
    public void setWml040userKbnUserGroup(String wml040userKbnUserGroup) {
        wml040userKbnUserGroup__ = wml040userKbnUserGroup;
    }
    /**
     * <p>userKbnGroupSelectCombo を取得します。
     * @return userKbnGroupSelectCombo
     */
    public List<LabelValueBean> getUserKbnGroupSelectCombo() {
        return userKbnGroupSelectCombo__;
    }
    /**
     * <p>userKbnGroupSelectCombo をセットします。
     * @param userKbnGroupSelectCombo userKbnGroupSelectCombo
     */
    public void setUserKbnGroupSelectCombo(
            List<LabelValueBean> userKbnGroupSelectCombo) {
        userKbnGroupSelectCombo__ = userKbnGroupSelectCombo;
    }
    /**
     * <p>userKbnUserSelectCombo を取得します。
     * @return userKbnUserSelectCombo
     */
    public List<UsrLabelValueBean> getUserKbnUserSelectCombo() {
        return userKbnUserSelectCombo__;
    }
    /**
     * <p>userKbnUserSelectCombo をセットします。
     * @param userKbnUserSelectCombo userKbnUserSelectCombo
     */
    public void setUserKbnUserSelectCombo(
            List<UsrLabelValueBean> userKbnUserSelectCombo) {
        userKbnUserSelectCombo__ = userKbnUserSelectCombo;
    }
    /**
     * <p>wml030group を取得します。
     * @return wml030group
     */
    public int getWml030group() {
        return wml030group__;
    }
    /**
     * <p>wml030group をセットします。
     * @param wml030group wml030group
     */
    public void setWml030group(int wml030group) {
        wml030group__ = wml030group;
    }
    /**
     * <p>wml030keyword を取得します。
     * @return wml030keyword
     */
    public String getWml030keyword() {
        return wml030keyword__;
    }
    /**
     * <p>wml030keyword をセットします。
     * @param wml030keyword wml030keyword
     */
    public void setWml030keyword(String wml030keyword) {
        wml030keyword__ = wml030keyword;
    }
    /**
     * <p>wml030order を取得します。
     * @return wml030order
     */
    public int getWml030order() {
        return wml030order__;
    }
    /**
     * <p>wml030order をセットします。
     * @param wml030order wml030order
     */
    public void setWml030order(int wml030order) {
        wml030order__ = wml030order;
    }
    /**
     * <p>wml030pageBottom を取得します。
     * @return wml030pageBottom
     */
    public int getWml030pageBottom() {
        return wml030pageBottom__;
    }
    /**
     * <p>wml030pageBottom をセットします。
     * @param wml030pageBottom wml030pageBottom
     */
    public void setWml030pageBottom(int wml030pageBottom) {
        wml030pageBottom__ = wml030pageBottom;
    }
    /**
     * <p>wml030pageTop を取得します。
     * @return wml030pageTop
     */
    public int getWml030pageTop() {
        return wml030pageTop__;
    }
    /**
     * <p>wml030pageTop をセットします。
     * @param wml030pageTop wml030pageTop
     */
    public void setWml030pageTop(int wml030pageTop) {
        wml030pageTop__ = wml030pageTop;
    }
    /**
     * <p>wml030searchFlg を取得します。
     * @return wml030searchFlg
     */
    public int getWml030searchFlg() {
        return wml030searchFlg__;
    }
    /**
     * <p>wml030searchFlg をセットします。
     * @param wml030searchFlg wml030searchFlg
     */
    public void setWml030searchFlg(int wml030searchFlg) {
        wml030searchFlg__ = wml030searchFlg;
    }
    /**
     * <p>wml030sortKey を取得します。
     * @return wml030sortKey
     */
    public int getWml030sortKey() {
        return wml030sortKey__;
    }
    /**
     * <p>wml030sortKey をセットします。
     * @param wml030sortKey wml030sortKey
     */
    public void setWml030sortKey(int wml030sortKey) {
        wml030sortKey__ = wml030sortKey;
    }
    /**
     * <p>wml030svGroup を取得します。
     * @return wml030svGroup
     */
    public int getWml030svGroup() {
        return wml030svGroup__;
    }
    /**
     * <p>wml030svGroup をセットします。
     * @param wml030svGroup wml030svGroup
     */
    public void setWml030svGroup(int wml030svGroup) {
        wml030svGroup__ = wml030svGroup;
    }
    /**
     * <p>wml030svKeyword を取得します。
     * @return wml030svKeyword
     */
    public String getWml030svKeyword() {
        return wml030svKeyword__;
    }
    /**
     * <p>wml030svKeyword をセットします。
     * @param wml030svKeyword wml030svKeyword
     */
    public void setWml030svKeyword(String wml030svKeyword) {
        wml030svKeyword__ = wml030svKeyword;
    }
    /**
     * <p>wml030svUser を取得します。
     * @return wml030svUser
     */
    public int getWml030svUser() {
        return wml030svUser__;
    }
    /**
     * <p>wml030svUser をセットします。
     * @param wml030svUser wml030svUser
     */
    public void setWml030svUser(int wml030svUser) {
        wml030svUser__ = wml030svUser;
    }
    /**
     * <p>wml030user を取得します。
     * @return wml030user
     */
    public int getWml030user() {
        return wml030user__;
    }
    /**
     * <p>wml030user をセットします。
     * @param wml030user wml030user
     */
    public void setWml030user(int wml030user) {
        wml030user__ = wml030user;
    }
    /**
     * <p>wml040elementKbn を取得します。
     * @return wml040elementKbn
     */
    public int getWml040elementKbn() {
        return wml040elementKbn__;
    }

    /**
     * <p>wml040elementKbn をセットします。
     * @param wml040elementKbn wml040elementKbn
     */
    public void setWml040elementKbn(int wml040elementKbn) {
        wml040elementKbn__ = wml040elementKbn;
    }

    /**
     * <p>wml040autoResv を取得します。
     * @return wml040autoResv
     */
    public int getWml040autoResv() {
        return wml040autoResv__;
    }

    /**
     * <p>wml040autoResv をセットします。
     * @param wml040autoResv wml040autoResv
     */
    public void setWml040autoResv(int wml040autoResv) {
        wml040autoResv__ = wml040autoResv;
    }

    /**
     * <p>wml040sendType を取得します。
     * @return wml040sendType
     */
    public int getWml040sendType() {
        return wml040sendType__;
    }

    /**
     * <p>wml040sendType をセットします。
     * @param wml040sendType wml040sendType
     */
    public void setWml040sendType(int wml040sendType) {
        wml040sendType__ = wml040sendType;
    }

    /**
     * <p>wml040receiveDsp を取得します。
     * @return wml040receiveDsp
     */
    public int getWml040receiveDsp() {
        return wml040receiveDsp__;
    }

    /**
     * <p>wml040receiveDsp をセットします。
     * @param wml040receiveDsp wml040receiveDsp
     */
    public void setWml040receiveDsp(int wml040receiveDsp) {
        wml040receiveDsp__ = wml040receiveDsp;
    }

    /**
     * <p>wml040signPoint を取得します。
     * @return wml040signPoint
     */
    public int getWml040signPoint() {
        return wml040signPoint__;
    }

    /**
     * <p>wml040signPoint をセットします。
     * @param wml040signPoint wml040signPoint
     */
    public void setWml040signPoint(int wml040signPoint) {
        wml040signPoint__ = wml040signPoint;
    }

    /**
     * <p>wml040AutoReceiveTime を取得します。
     * @return wml040AutoReceiveTime
     */
    public int getWml040AutoReceiveTime() {
        return wml040AutoReceiveTime__;
    }

    /**
     * <p>wml040AutoReceiveTime をセットします。
     * @param wml040AutoReceiveTime wml040AutoReceiveTime
     */
    public void setWml040AutoReceiveTime(int wml040AutoReceiveTime) {
        wml040AutoReceiveTime__ = wml040AutoReceiveTime;
    }

    /**
     * <p>wml040AutoRsvKeyLabel を取得します。
     * @return wml040AutoRsvKeyLabel
     */
    public List<LabelValueBean> getWml040AutoRsvKeyLabel() {
        return wml040AutoRsvKeyLabel__;
    }

    /**
     * <p>wml040AutoRsvKeyLabel をセットします。
     * @param wml040AutoRsvKeyLabel wml040AutoRsvKeyLabel
     */
    public void setWml040AutoRsvKeyLabel(List<LabelValueBean> wml040AutoRsvKeyLabel) {
        wml040AutoRsvKeyLabel__ = wml040AutoRsvKeyLabel;
    }

    /**
     * <p>wml040PermitKbn を取得します。
     * @return wml040PermitKbn
     */
    public int getWml040PermitKbn() {
        return wml040PermitKbn__;
    }

    /**
     * <p>wml040PermitKbn をセットします。
     * @param wml040PermitKbn wml040PermitKbn
     */
    public void setWml040PermitKbn(int wml040PermitKbn) {
        wml040PermitKbn__ = wml040PermitKbn;
    }

    /**
     * <p>wml040diskSizeComp を取得します。
     * @return wml040diskSizeComp
     */
    public int getWml040diskSizeComp() {
        return wml040diskSizeComp__;
    }

    /**
     * <p>wml040diskSizeComp をセットします。
     * @param wml040diskSizeComp wml040diskSizeComp
     */
    public void setWml040diskSizeComp(int wml040diskSizeComp) {
        wml040diskSizeComp__ = wml040diskSizeComp;
    }
    /**
     * <p>wml040admDisk を取得します。
     * @return wml040admDisk
     */
    public int getWml040admDisk() {
        return wml040admDisk__;
    }
    /**
     * <p>wml040admDisk をセットします。
     * @param wml040admDisk wml040admDisk
     */
    public void setWml040admDisk(int wml040admDisk) {
        wml040admDisk__ = wml040admDisk;
    }
    /**
     * <p>wml040admDiskSize を取得します。
     * @return wml040admDiskSize
     */
    public int getWml040admDiskSize() {
        return wml040admDiskSize__;
    }
    /**
     * <p>wml040admDiskSize をセットします。
     * @param wml040admDiskSize wml040admDiskSize
     */
    public void setWml040admDiskSize(int wml040admDiskSize) {
        wml040admDiskSize__ = wml040admDiskSize;
    }
    /**
     * <p>wml040theme を取得します。
     * @return wml040theme
     */
    public int getWml040theme() {
        return wml040theme__;
    }

    /**
     * <p>wml040theme をセットします。
     * @param wml040theme wml040theme
     */
    public void setWml040theme(int wml040theme) {
        wml040theme__ = wml040theme;
    }

    /**
     * <p>wml040checkAddress を取得します。
     * @return wml040checkAddress
     */
    public int getWml040checkAddress() {
        return wml040checkAddress__;
    }

    /**
     * <p>wml040checkAddress をセットします。
     * @param wml040checkAddress wml040checkAddress
     */
    public void setWml040checkAddress(int wml040checkAddress) {
        wml040checkAddress__ = wml040checkAddress;
    }

    /**
     * <p>wml040checkFile を取得します。
     * @return wml040checkFile
     */
    public int getWml040checkFile() {
        return wml040checkFile__;
    }

    /**
     * <p>wml040checkFile をセットします。
     * @param wml040checkFile wml040checkFile
     */
    public void setWml040checkFile(int wml040checkFile) {
        wml040checkFile__ = wml040checkFile;
    }

    /**
     * <p>wml040compressFile を取得します。
     * @return wml040compressFile
     */
    public int getWml040compressFile() {
        return wml040compressFile__;
    }

    /**
     * <p>wml040compressFile をセットします。
     * @param wml040compressFile wml040compressFile
     */
    public void setWml040compressFile(int wml040compressFile) {
        wml040compressFile__ = wml040compressFile;
    }

    /**
     * <p>wml040timeSent を取得します。
     * @return wml040timeSent
     */
    public int getWml040timeSent() {
        return wml040timeSent__;
    }

    /**
     * <p>wml040timeSent をセットします。
     * @param wml040timeSent wml040timeSent
     */
    public void setWml040timeSent(int wml040timeSent) {
        wml040timeSent__ = wml040timeSent;
    }

    /**
     * <p>wml040quotes を取得します。
     * @return wml040quotes
     */
    public int getWml040quotes() {
        return wml040quotes__;
    }
    /**
     * <p>wml040quotes をセットします。
     * @param wml040quotes wml040quotes
     */
    public void setWml040quotes(int wml040quotes) {
        wml040quotes__ = wml040quotes;
    }
    /**
     * <p>wml040compressFileDef を取得します。
     * @return wml040compressFileDef
     */
    public int getWml040compressFileDef() {
        return wml040compressFileDef__;
    }
    /**
     * <p>wml040compressFileDef をセットします。
     * @param wml040compressFileDef wml040compressFileDef
     */
    public void setWml040compressFileDef(int wml040compressFileDef) {
        wml040compressFileDef__ = wml040compressFileDef;
    }
    /**
     * <p>wml040timeSentDef を取得します。
     * @return wml040timeSentDef
     */
    public int getWml040timeSentDef() {
        return wml040timeSentDef__;
    }
    /**
     * <p>wml040timeSentDef をセットします。
     * @param wml040timeSentDef wml040timeSentDef
     */
    public void setWml040timeSentDef(int wml040timeSentDef) {
        wml040timeSentDef__ = wml040timeSentDef;
    }
    /**
     * <p>wml040autoSaveMin を取得します。
     * @return wml040autoSaveMin
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040autoSaveMin__
     */
    public String getWml040autoSaveMin() {
        return wml040autoSaveMin__;
    }
    /**
     * <p>wml040autoSaveMin をセットします。
     * @param wml040autoSaveMin wml040autoSaveMin
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040autoSaveMin__
     */
    public void setWml040autoSaveMin(String wml040autoSaveMin) {
        wml040autoSaveMin__ = wml040autoSaveMin;
    }
    /**
     * <p>wml040themeList を取得します。
     * @return wml040themeList
     */
    public List<LabelValueBean> getWml040themeList() {
        return wml040themeList__;
    }

    /**
     * <p>wml040themeList をセットします。
     * @param wml040themeList wml040themeList
     */
    public void setWml040themeList(List<LabelValueBean> wml040themeList) {
        wml040themeList__ = wml040themeList;
    }
    /**
     * <p>wml040quotesList を取得します。
     * @return wml040quotesList
     */
    public List<LabelValueBean> getWml040quotesList() {
        return wml040quotesList__;
    }
    /**
     * <p>wml040quotesList をセットします。
     * @param wml040quotesList wml040quotesList
     */
    public void setWml040quotesList(List<LabelValueBean> wml040quotesList) {
        wml040quotesList__ = wml040quotesList;
    }
    /**
     * <p>wml040signList を取得します。
     * @return wml040signList
     */
    public List<LabelValueBean> getWml040signList() {
        return wml040signList__;
    }
    /**
     * <p>wml040signList をセットします。
     * @param wml040signList wml040signList
     */
    public void setWml040signList(List<LabelValueBean> wml040signList) {
        wml040signList__ = wml040signList;
    }
    /**
     * <p>wml040sessionUserData を取得します。
     * @return wml040sessionUserData
     */
    public BaseUserModel getWml040sessionUserData() {
        return wml040sessionUserData__;
    }
    /**
     * <p>wml040sessionUserData をセットします。
     * @param wml040sessionUserData wml040sessionUserData
     */
    public void setWml040sessionUserData(BaseUserModel wml040sessionUserData) {
        wml040sessionUserData__ = wml040sessionUserData;
    }
    /**
     * <p>wml040webmailAdmin を取得します。
     * @return wml040webmailAdmin
     */
    public boolean isWml040webmailAdmin() {
        return wml040webmailAdmin__;
    }
    /**
     * <p>wml040webmailAdmin をセットします。
     * @param wml040webmailAdmin wml040webmailAdmin
     */
    public void setWml040webmailAdmin(boolean wml040webmailAdmin) {
        wml040webmailAdmin__ = wml040webmailAdmin;
    }
    /**
     * <p>wml040proxyUserFlg を取得します。
     * @return wml040proxyUserFlg
     */
    public boolean isWml040proxyUserFlg() {
        return wml040proxyUserFlg__;
    }

    /**
     * <p>wml040proxyUserFlg をセットします。
     * @param wml040proxyUserFlg wml040proxyUserFlg
     */
    public void setWml040proxyUserFlg(boolean wml040proxyUserFlg) {
        wml040proxyUserFlg__ = wml040proxyUserFlg;
    }

    /**
     * <p>wml040proxyUser を取得します。
     * @return wml040proxyUser
     */
    public String[] getWml040proxyUser() {
        return wml040proxyUser__;
    }

    /**
     * <p>wml040proxyUser をセットします。
     * @param wml040proxyUser wml040proxyUser
     */
    public void setWml040proxyUser(String[] wml040proxyUser) {
        wml040proxyUser__ = wml040proxyUser;
    }
    /**
     * <p>wml040proxyUserGroup を取得します。
     * @return wml040proxyUserGroup
     */
    public String getWml040proxyUserGroup() {
        return wml040proxyUserGroup__;
    }
    /**
     * <p>wml040proxyUserGroup をセットします。
     * @param wml040proxyUserGroup wml040proxyUserGroup
     */
    public void setWml040proxyUserGroup(String wml040proxyUserGroup) {
        wml040proxyUserGroup__ = wml040proxyUserGroup;
    }
    /**
     * <p>proxyUserSelectCombo を取得します。
     * @return proxyUserSelectCombo
     */
    public List<UsrLabelValueBean> getProxyUserSelectCombo() {
        return proxyUserSelectCombo__;
    }
    /**
     * <p>proxyUserSelectCombo をセットします。
     * @param proxyUserSelectCombo proxyUserSelectCombo
     */
    public void setProxyUserSelectCombo(
            List<UsrLabelValueBean> proxyUserSelectCombo) {
        proxyUserSelectCombo__ = proxyUserSelectCombo;
    }
    /**
     * <p>wml040proxyUserUI を取得します。
     * @return wml040proxyUserUI
     */
    public UserGroupSelector getWml040proxyUserUI() {
        return wml040proxyUserUI__;
    }
    /**
     * <p>wml040proxyUserUI をセットします。
     * @param wml040proxyUserUI wml040proxyUserUI
     */
    public void setWml040proxyUserUI(UserGroupSelector wml040proxyUserUI) {
        wml040proxyUserUI__ = wml040proxyUserUI;
    }
    /**
     * <p>wml040AngoProtocolCombo を取得します。
     * @return wml040AngoProtocolCombo
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040AngoProtocolCombo__
     */
    public List<LabelValueBean> getWml040AngoProtocolCombo() {
        return wml040AngoProtocolCombo__;
    }
    /**
     * <p>wml040AngoProtocolCombo をセットします。
     * @param wml040AngoProtocolCombo wml040AngoProtocolCombo
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040AngoProtocolCombo__
     */
    public void setWml040AngoProtocolCombo(
            List<LabelValueBean> wml040AngoProtocolCombo) {
        wml040AngoProtocolCombo__ = wml040AngoProtocolCombo;
    }
    /**
     * <p>wml040autoSaveList を取得します。
     * @return wml040autoSaveList
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040autoSaveList__
     */
    public List<LabelValueBean> getWml040autoSaveList() {
        return wml040autoSaveList__;
    }
    /**
     * <p>wml040autoSaveList をセットします。
     * @param wml040autoSaveList wml040autoSaveList
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040autoSaveList__
     */
    public void setWml040autoSaveList(List<LabelValueBean> wml040autoSaveList) {
        wml040autoSaveList__ = wml040autoSaveList;
    }
    /**
     * <p>wml040receiveServerEncrypt を取得します。
     * @return wml040receiveServerEncrypt
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040receiveServerEncrypt__
     */
    public int getWml040receiveServerEncrypt() {
        return wml040receiveServerEncrypt__;
    }
    /**
     * <p>wml040receiveServerEncrypt をセットします。
     * @param wml040receiveServerEncrypt wml040receiveServerEncrypt
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040receiveServerEncrypt__
     */
    public void setWml040receiveServerEncrypt(int wml040receiveServerEncrypt) {
        wml040receiveServerEncrypt__ = wml040receiveServerEncrypt;
    }
    /**
     * <p>wml040sendServerEncrypt を取得します。
     * @return wml040sendServerEncrypt
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040sendServerEncrypt__
     */
    public int getWml040sendServerEncrypt() {
        return wml040sendServerEncrypt__;
    }
    /**
     * <p>wml040sendServerEncrypt をセットします。
     * @param wml040sendServerEncrypt wml040sendServerEncrypt
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040sendServerEncrypt__
     */
    public void setWml040sendServerEncrypt(int wml040sendServerEncrypt) {
        wml040sendServerEncrypt__ = wml040sendServerEncrypt;
    }
    /**
     * <p>wml040authMethod を取得します。
     * @return wml040authMethod
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040authMethod__
     */
    public int getWml040authMethod() {
        return wml040authMethod__;
    }
    /**
     * <p>wml040authMethod をセットします。
     * @param wml040authMethod wml040authMethod
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040authMethod__
     */
    public void setWml040authMethod(int wml040authMethod) {
        wml040authMethod__ = wml040authMethod;
    }
    /**
     * <p>wml040provider を取得します。
     * @return wml040provider
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040provider__
     */
    public int getWml040provider() {
        return wml040provider__;
    }
    /**
     * <p>wml040provider をセットします。
     * @param wml040provider wml040provider
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040provider__
     */
    public void setWml040provider(int wml040provider) {
        wml040provider__ = wml040provider;
    }
    /**
     * <p>wml040authAccount を取得します。
     * @return wml040authAccount
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040authAccount__
     */
    public String getWml040authAccount() {
        return wml040authAccount__;
    }
    /**
     * <p>wml040authAccount をセットします。
     * @param wml040authAccount wml040authAccount
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040authAccount__
     */
    public void setWml040authAccount(String wml040authAccount) {
        wml040authAccount__ = wml040authAccount;
    }
    /**
     * <p>wml040oauthCompFlg を取得します。
     * @return wml040oauthCompFlg
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040oauthCompFlg__
     */
    public boolean isWml040oauthCompFlg() {
        return wml040oauthCompFlg__;
    }
    /**
     * <p>wml040oauthCompFlg をセットします。
     * @param wml040oauthCompFlg wml040oauthCompFlg
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040oauthCompFlg__
     */
    public void setWml040oauthCompFlg(boolean wml040oauthCompFlg) {
        wml040oauthCompFlg__ = wml040oauthCompFlg;
    }
    /**
     * <p>wml040cotSid を取得します。
     * @return wml040cotSid
     * @see jp.groupsession.v2.wml.wml040.Wml040Form#wml040cotSid__
     */
    public int getWml040cotSid() {
        return wml040cotSid__;
    }
    /**
     * <p>wml040cotSid をセットします。
     * @param wml040cotSid wml040cotSid
     * @see jp.groupsession.v2.wml.wml040.Wml040Form#wml040cotSid__
     */
    public void setWml040cotSid(int wml040cotSid) {
        wml040cotSid__ = wml040cotSid;
    }
    /**
     * <p>wml040providerList を取得します。
     * @return wml040providerList
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040providerList__
     */
    public List<LabelValueBean> getWml040providerList() {
        return wml040providerList__;
    }
    /**
     * <p>wml040providerList をセットします。
     * @param wml040providerList wml040providerList
     * @see jp.groupsession.v2.wml.wml040.Wml040ParamModel#wml040providerList__
     */
    public void setWml040providerList(List<LabelValueBean> wml040providerList) {
        wml040providerList__ = wml040providerList;
    }
}