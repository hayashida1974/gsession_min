package jp.groupsession.v2.wml.wml160;

import java.util.Comparator;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール アカウントインポート 取込みファイル(CSV)の情報を格納するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class WebmailCsvModel extends AbstractModel
implements Comparator<WebmailCsvModel> {

    /** SSLの使用 使用しない */
    public static final int SSL_NOTUSE = 0;
    /** SSLの使用 使用する */
    public static final int SSL_USE = 1;

    /** メール自動受信間隔 5分 */
    public static final int AUTO_RSVTIME_5 = 1;
    /** メール自動受信間隔 10分 */
    public static final int AUTO_RSVTIME_10 = 2;
    /** メール自動受信間隔 30分 */
    public static final int AUTO_RSVTIME_30 = 3;
    /** メール自動受信間隔 60分 */
    public static final int AUTO_RSVTIME_60 = 4;

    /** 署名自動挿入 挿入する */
    public static final int SIGN_AUTO_INSERT = 0;
    /** 署名自動挿入 挿入しない */
    public static final int SIGN_AUTO_NO = 1;

    /** 署名表示区分 表示 */
    public static final int SIGN_DSP_KBN_DSP = 0;
    /** 署名表示区分 非表示 */
    public static final int SIGN_DSP_KBN_NODSP = 1;

    /** 使用ユーザ/グループ区分 ユーザ */
    public static final int USERKBN_USER = 0;
    /** 使用ユーザ/グループ区分 グループ */
    public static final int USERKBN_GROUP = 1;

    /** 行番号 */
    private long rowNum__ = 0;
    /** 項目数 */
    private int elementCount__ = 0;
    /** アカウントID */
    private String accountId__ = null;
    /** アカウント名 */
    private String accountName__ = null;
    /** 認証形式 */
    private String authType__ = null; 
    /** プロバイダ */
    private String provider__ = null;
    /** メールアドレス */
    private String mail__ = null;
    /** メール受信サーバ */
    private String mailRsvServer__ = null;
    /** 受信サーバ ポート番号 */
    private String mailRsvPort__ = null;
    /** 受信サーバ SSL */
    private String mailRsvSsl__ = null;
    /** 受信サーバ ユーザ名 */
    private String mailRsvUser__ = null;
    /** 受信サーバ パスワード */
    private String mailRsvPass__ = null;
    /** メール自動受信 */
    private String mailAutoRsv__ = null;
    /** メール自動受信間隔 */
    private String mailAutoRsvTime__ = null;
    /** メール送信サーバ */
    private String mailSndServer__ = null;
    /** メール送信サーバ ポート番号 */
    private String mailSndPort__ = null;
    /** メール送信サーバ Ssl */
    private String mailSndSsl__ = null;
    /** SMTP認証ON/OFF */
    private String smtpNinsyo__ = null;
    /** メール送信サーバ ユーザ名 */
    private String mailSndUser__ = null;
    /** メール送信サーバ パスワード */
    private String mailSndPass__ = null;
    /** ディスク容量 */
    private String diskCapa__ = null;
    /** ディスク容量 サイズ */
    private String diskCapaSize__ = null;
    /** ディスク容量 特例修正 */
    private String diskCapaSps__ = null;
    /** 備考 */
    private String biko__ = null;
    /** 組織名 */
    private String organization__ = null;
    /** 署名 */
    private String sign__ = null;
    /** 署名自動挿入 */
    private String signAuto__ = null;
    /** 返信時署名位置 */
    private String signPoint__ = null;
    /** 返信時署名表示 */
    private String signDsp__ = null;
    /** 自動TO */
    private String autoTo__ = null;
    /** 自動CC */
    private String autoCc__ = null;
    /** 自動BCC */
    private String autoBcc__ = null;
    /** 受信時削除 */
    private String rsvDelete__ = null;
    /** 受信済みでも受信 */
    private String rsvOk__ = null;
    /** APOPのON/OFF */
    private String apop__ = null;
    /** TOPコマンドの使用 */
    private String topCmd__ = null;
    /** 送信前POP認証 */
    private String beSndPopNinsyo__ = null;
    /** 送信文字コード */
    private String sndWordCode__ = null;
    /** 送信メール形式 */
    private String sndMailType__ = null;
    /** 宛先の確認 */
    private String checkAddress__ = null;
    /** 添付ファイルの確認 */
    private String checkFile__ = null;
    /** 添付ファイル自動圧縮 */
    private String compressFile__ = null;
    /** 添付ファイル自動圧縮 初期値 */
    private String compressFileDef__ = null;
    /** 時間差送信 */
    private String timeSent__ = null;
    /** 時間差送信 初期値 */
    private String timeSentDef__ = null;
    /** テーマ */
    private String theme__ = null;
    /** 引用符 */
    private String quotes__ = null;
    /** 自動保存 */
    private String autoSave__ = null;

    /** 使用ユーザ/グループ区分 */
    private String userKbn__ = null;
    /** 使用ユーザ/グループ1 */
    private String user1__ = null;
    /** 使用ユーザ/グループ2 */
    private String user2__ = null;
    /** 使用ユーザ/グループ3 */
    private String user3__ = null;
    /** 使用ユーザ/グループ4 */
    private String user4__ = null;
    /** 使用ユーザ/グループ5 */
    private String user5__ = null;

    /** 代理人1 */
    private String proxyUser1__ = null;
    /** 代理人2 */
    private String proxyUser2__ = null;
    /** 代理人3 */
    private String proxyUser3__ = null;
    /** 代理人4 */
    private String proxyUser4__ = null;
    /** 代理人5 */
    private String proxyUser5__ = null;

    /**
     * <p>accountId を取得します。
     * @return accountId
     */
    public String getAccountId() {
        return accountId__;
    }
    /**
     * <p>accountId をセットします。
     * @param accountId accountId
     */
    public void setAccountId(String accountId) {
        accountId__ = accountId;
    }
    /**
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }
    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }
    /**
     * <p>apop を取得します。
     * @return apop
     */
    public String getApop() {
        return apop__;
    }
    /**
     * <p>apop をセットします。
     * @param apop apop
     */
    public void setApop(String apop) {
        apop__ = apop;
    }
    /**
     * <p>topCmd を取得します。
     * @return topCmd
     */
    public String getTopCmd() {
        return topCmd__;
    }
    /**
     * <p>topCmd をセットします。
     * @param topCmd topCmd
     */
    public void setTopCmd(String topCmd) {
        topCmd__ = topCmd;
    }
    /**
     * <p>autoBcc を取得します。
     * @return autoBcc
     */
    public String getAutoBcc() {
        return autoBcc__;
    }
    /**
     * <p>autoBcc をセットします。
     * @param autoBcc autoBcc
     */
    public void setAutoBcc(String autoBcc) {
        autoBcc__ = autoBcc;
    }
    /**
     * <p>autoCc を取得します。
     * @return autoCc
     */
    public String getAutoCc() {
        return autoCc__;
    }
    /**
     * <p>autoCc をセットします。
     * @param autoCc autoCc
     */
    public void setAutoCc(String autoCc) {
        autoCc__ = autoCc;
    }
    /**
     * <p>autoTo を取得します。
     * @return autoTo
     */
    public String getAutoTo() {
        return autoTo__;
    }
    /**
     * <p>autoTo をセットします。
     * @param autoTo autoTo
     */
    public void setAutoTo(String autoTo) {
        autoTo__ = autoTo;
    }
    /**
     * <p>beSndPopNinsyo を取得します。
     * @return beSndPopNinsyo
     */
    public String getBeSndPopNinsyo() {
        return beSndPopNinsyo__;
    }
    /**
     * <p>beSndPopNinsyo をセットします。
     * @param beSndPopNinsyo beSndPopNinsyo
     */
    public void setBeSndPopNinsyo(String beSndPopNinsyo) {
        beSndPopNinsyo__ = beSndPopNinsyo;
    }
    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
    /**
     * <p>diskCapa を取得します。
     * @return diskCapa
     */
    public String getDiskCapa() {
        return diskCapa__;
    }
    /**
     * <p>diskCapa をセットします。
     * @param diskCapa diskCapa
     */
    public void setDiskCapa(String diskCapa) {
        diskCapa__ = diskCapa;
    }
    /**
     * <p>diskCapaSize を取得します。
     * @return diskCapaSize
     */
    public String getDiskCapaSize() {
        return diskCapaSize__;
    }
    /**
     * <p>diskCapaSize をセットします。
     * @param diskCapaSize diskCapaSize
     */
    public void setDiskCapaSize(String diskCapaSize) {
        diskCapaSize__ = diskCapaSize;
    }
    /**
     * <p>diskCapaSps を取得します。
     * @return diskCapaSps
     */
    public String getDiskCapaSps() {
        return diskCapaSps__;
    }
    /**
     * <p>diskCapaSps をセットします。
     * @param diskCapaSps diskCapaSps
     */
    public void setDiskCapaSps(String diskCapaSps) {
        diskCapaSps__ = diskCapaSps;
    }
    /**
     * <p>elementCount を取得します。
     * @return elementCount
     */
    public int getElementCount() {
        return elementCount__;
    }
    /**
     * <p>elementCount をセットします。
     * @param elementCount elementCount
     */
    public void setElementCount(int elementCount) {
        elementCount__ = elementCount;
    }
    /**
     * <p>mail を取得します。
     * @return mail
     */
    public String getMail() {
        return mail__;
    }
    /**
     * <p>mail をセットします。
     * @param mail mail
     */
    public void setMail(String mail) {
        mail__ = mail;
    }
    /**
     * <p>mailAutoRsv を取得します。
     * @return mailAutoRsv
     */
    public String getMailAutoRsv() {
        return mailAutoRsv__;
    }
    /**
     * <p>mailAutoRsv をセットします。
     * @param mailAutoRsv mailAutoRsv
     */
    public void setMailAutoRsv(String mailAutoRsv) {
        mailAutoRsv__ = mailAutoRsv;
    }
    /**
     * <p>mailAutoRsvTime を取得します。
     * @return mailAutoRsvTime
     */
    public String getMailAutoRsvTime() {
        return mailAutoRsvTime__;
    }
    /**
     * <p>mailAutoRsvTime をセットします。
     * @param mailAutoRsvTime mailAutoRsvTime
     */
    public void setMailAutoRsvTime(String mailAutoRsvTime) {
        mailAutoRsvTime__ = mailAutoRsvTime;
    }
    /**
     * <p>mailRsvPass を取得します。
     * @return mailRsvPass
     */
    public String getMailRsvPass() {
        return mailRsvPass__;
    }
    /**
     * <p>mailRsvPass をセットします。
     * @param mailRsvPass mailRsvPass
     */
    public void setMailRsvPass(String mailRsvPass) {
        mailRsvPass__ = mailRsvPass;
    }
    /**
     * <p>mailRsvPort を取得します。
     * @return mailRsvPort
     */
    public String getMailRsvPort() {
        return mailRsvPort__;
    }
    /**
     * <p>mailRsvPort をセットします。
     * @param mailRsvPort mailRsvPort
     */
    public void setMailRsvPort(String mailRsvPort) {
        mailRsvPort__ = mailRsvPort;
    }
    /**
     * <p>mailRsvServer を取得します。
     * @return mailRsvServer
     */
    public String getMailRsvServer() {
        return mailRsvServer__;
    }
    /**
     * <p>mailRsvServer をセットします。
     * @param mailRsvServer mailRsvServer
     */
    public void setMailRsvServer(String mailRsvServer) {
        mailRsvServer__ = mailRsvServer;
    }
    /**
     * <p>mailRsvUser を取得します。
     * @return mailRsvUser
     */
    public String getMailRsvUser() {
        return mailRsvUser__;
    }
    /**
     * <p>mailRsvUser をセットします。
     * @param mailRsvUser mailRsvUser
     */
    public void setMailRsvUser(String mailRsvUser) {
        mailRsvUser__ = mailRsvUser;
    }
    /**
     * <p>mailSndPass を取得します。
     * @return mailSndPass
     */
    public String getMailSndPass() {
        return mailSndPass__;
    }
    /**
     * <p>mailSndPass をセットします。
     * @param mailSndPass mailSndPass
     */
    public void setMailSndPass(String mailSndPass) {
        mailSndPass__ = mailSndPass;
    }
    /**
     * <p>mailSndPort を取得します。
     * @return mailSndPort
     */
    public String getMailSndPort() {
        return mailSndPort__;
    }
    /**
     * <p>mailSndPort をセットします。
     * @param mailSndPort mailSndPort
     */
    public void setMailSndPort(String mailSndPort) {
        mailSndPort__ = mailSndPort;
    }
    /**
     * <p>mailSndServer を取得します。
     * @return mailSndServer
     */
    public String getMailSndServer() {
        return mailSndServer__;
    }
    /**
     * <p>mailSndServer をセットします。
     * @param mailSndServer mailSndServer
     */
    public void setMailSndServer(String mailSndServer) {
        mailSndServer__ = mailSndServer;
    }
    /**
     * <p>mailSndUser を取得します。
     * @return mailSndUser
     */
    public String getMailSndUser() {
        return mailSndUser__;
    }
    /**
     * <p>mailSndUser をセットします。
     * @param mailSndUser mailSndUser
     */
    public void setMailSndUser(String mailSndUser) {
        mailSndUser__ = mailSndUser;
    }
    /**
     * <p>organization を取得します。
     * @return organization
     */
    public String getOrganization() {
        return organization__;
    }
    /**
     * <p>organization をセットします。
     * @param organization organization
     */
    public void setOrganization(String organization) {
        organization__ = organization;
    }
    /**
     * <p>rowNum を取得します。
     * @return rowNum
     */
    public long getRowNum() {
        return rowNum__;
    }
    /**
     * <p>rowNum をセットします。
     * @param rowNum rowNum
     */
    public void setRowNum(long rowNum) {
        rowNum__ = rowNum;
    }
    /**
     * <p>rsvDelete を取得します。
     * @return rsvDelete
     */
    public String getRsvDelete() {
        return rsvDelete__;
    }
    /**
     * <p>rsvDelete をセットします。
     * @param rsvDelete rsvDelete
     */
    public void setRsvDelete(String rsvDelete) {
        rsvDelete__ = rsvDelete;
    }
    /**
     * <p>rsvOk を取得します。
     * @return rsvOk
     */
    public String getRsvOk() {
        return rsvOk__;
    }
    /**
     * <p>rsvOk をセットします。
     * @param rsvOk rsvOk
     */
    public void setRsvOk(String rsvOk) {
        rsvOk__ = rsvOk;
    }
    /**
     * <p>sign を取得します。
     * @return sign
     */
    public String getSign() {
        return sign__;
    }
    /**
     * <p>sign をセットします。
     * @param sign sign
     */
    public void setSign(String sign) {
        sign__ = sign;
    }
    /**
     * <p>signAuto を取得します。
     * @return signAuto
     */
    public String getSignAuto() {
        return signAuto__;
    }
    /**
     * <p>signAuto をセットします。
     * @param signAuto signAuto
     */
    public void setSignAuto(String signAuto) {
        signAuto__ = signAuto;
    }
    /**
     * <p>signPoint を取得します。
     * @return signPoint
     */
    public String getSignPoint() {
        return signPoint__;
    }
    /**
     * <p>signPoint をセットします。
     * @param signPoint signPoint
     */
    public void setSignPoint(String signPoint) {
        signPoint__ = signPoint;
    }
    /**
     * <p>signDsp を取得します。
     * @return signDsp
     */
    public String getSignDsp() {
        return signDsp__;
    }
    /**
     * <p>signDsp をセットします。
     * @param signDsp signDsp
     */
    public void setSignDsp(String signDsp) {
        signDsp__ = signDsp;
    }
    /**
     * <p>smtpNinsyo を取得します。
     * @return smtpNinsyo
     */
    public String getSmtpNinsyo() {
        return smtpNinsyo__;
    }
    /**
     * <p>smtpNinsyo をセットします。
     * @param smtpNinsyo smtpNinsyo
     */
    public void setSmtpNinsyo(String smtpNinsyo) {
        smtpNinsyo__ = smtpNinsyo;
    }
    /**
     * <p>sndWordCode を取得します。
     * @return sndWordCode
     */
    public String getSndWordCode() {
        return sndWordCode__;
    }
    /**
     * <p>sndWordCode をセットします。
     * @param sndWordCode sndWordCode
     */
    public void setSndWordCode(String sndWordCode) {
        sndWordCode__ = sndWordCode;
    }
    /**
     * <p>userKbn を取得します。
     * @return userKbn
     */
    public String getUserKbn() {
        return userKbn__;
    }
    /**
     * <p>userKbn をセットします。
     * @param userKbn userKbn
     */
    public void setUserKbn(String userKbn) {
        userKbn__ = userKbn;
    }
    /**
     * <p>user1 を取得します。
     * @return user1
     */
    public String getUser1() {
        return user1__;
    }
    /**
     * <p>user1 をセットします。
     * @param user1 user1
     */
    public void setUser1(String user1) {
        user1__ = user1;
    }
    /**
     * <p>user2 を取得します。
     * @return user2
     */
    public String getUser2() {
        return user2__;
    }
    /**
     * <p>user2 をセットします。
     * @param user2 user2
     */
    public void setUser2(String user2) {
        user2__ = user2;
    }
    /**
     * <p>user3 を取得します。
     * @return user3
     */
    public String getUser3() {
        return user3__;
    }
    /**
     * <p>user3 をセットします。
     * @param user3 user3
     */
    public void setUser3(String user3) {
        user3__ = user3;
    }
    /**
     * <p>user4 を取得します。
     * @return user4
     */
    public String getUser4() {
        return user4__;
    }
    /**
     * <p>user4 をセットします。
     * @param user4 user4
     */
    public void setUser4(String user4) {
        user4__ = user4;
    }
    /**
     * <p>user5 を取得します。
     * @return user5
     */
    public String getUser5() {
        return user5__;
    }
    /**
     * <p>user5 をセットします。
     * @param user5 user5
     */
    public void setUser5(String user5) {
        user5__ = user5;
    }
    /**
     * <p>sndMailType を取得します。
     * @return sndMailType
     */
    public String getSndMailType() {
        return sndMailType__;
    }
    /**
     * <p>sndMailType をセットします。
     * @param sndMailType sndMailType
     */
    public void setSndMailType(String sndMailType) {
        sndMailType__ = sndMailType;
    }
    /**
     * <p>mailRsvSsl を取得します。
     * @return mailRsvSsl
     */
    public String getMailRsvSsl() {
        return mailRsvSsl__;
    }
    /**
     * <p>mailRsvSsl をセットします。
     * @param mailRsvSsl mailRsvSsl
     */
    public void setMailRsvSsl(String mailRsvSsl) {
        mailRsvSsl__ = mailRsvSsl;
    }
    /**
     * <p>mailSndSsl を取得します。
     * @return mailSndSsl
     */
    public String getMailSndSsl() {
        return mailSndSsl__;
    }
    /**
     * <p>mailSndSsl をセットします。
     * @param mailSndSsl mailSndSsl
     */
    public void setMailSndSsl(String mailSndSsl) {
        mailSndSsl__ = mailSndSsl;
    }
    /**
     * <p>checkAddress を取得します。
     * @return checkAddress
     */
    public String getCheckAddress() {
        return checkAddress__;
    }
    /**
     * <p>checkAddress をセットします。
     * @param checkAddress checkAddress
     */
    public void setCheckAddress(String checkAddress) {
        checkAddress__ = checkAddress;
    }
    /**
     * <p>checkFile を取得します。
     * @return checkFile
     */
    public String getCheckFile() {
        return checkFile__;
    }
    /**
     * <p>checkFile をセットします。
     * @param checkFile checkFile
     */
    public void setCheckFile(String checkFile) {
        checkFile__ = checkFile;
    }
    /**
     * <p>compressFile を取得します。
     * @return compressFile
     */
    public String getCompressFile() {
        return compressFile__;
    }
    /**
     * <p>compressFile をセットします。
     * @param compressFile compressFile
     */
    public void setCompressFile(String compressFile) {
        compressFile__ = compressFile;
    }
    /**
     * <p>compressFileDef を取得します。
     * @return compressFileDef
     */
    public String getCompressFileDef() {
        return compressFileDef__;
    }
    /**
     * <p>compressFileDef をセットします。
     * @param compressFileDef compressFileDef
     */
    public void setCompressFileDef(String compressFileDef) {
        compressFileDef__ = compressFileDef;
    }
    /**
     * <p>timeSent を取得します。
     * @return timeSent
     */
    public String getTimeSent() {
        return timeSent__;
    }
    /**
     * <p>timeSent をセットします。
     * @param timeSent timeSent
     */
    public void setTimeSent(String timeSent) {
        timeSent__ = timeSent;
    }
    /**
     * <p>timeSentDef を取得します。
     * @return timeSentDef
     */
    public String getTimeSentDef() {
        return timeSentDef__;
    }
    /**
     * <p>timeSentDef をセットします。
     * @param timeSentDef timeSentDef
     */
    public void setTimeSentDef(String timeSentDef) {
        timeSentDef__ = timeSentDef;
    }
    /**
     * <p>theme を取得します。
     * @return theme
     */
    public String getTheme() {
        return theme__;
    }
    /**
     * <p>theme をセットします。
     * @param theme theme
     */
    public void setTheme(String theme) {
        theme__ = theme;
    }
    /**
     * <p>quotes を取得します。
     * @return quotes
     */
    public String getQuotes() {
        return quotes__;
    }
    /**
     * <p>quotes をセットします。
     * @param quotes quotes
     */
    public void setQuotes(String quotes) {
        quotes__ = quotes;
    }
    /**
     * <p>authType を取得します。
     * @return authType
     * @see jp.groupsession.v2.wml.wml160.WebmailCsvModel#authType__
     */
    public String getAuthType() {
        return authType__;
    }
    /**
     * <p>authType をセットします。
     * @param authType authType
     * @see jp.groupsession.v2.wml.wml160.WebmailCsvModel#authType__
     */
    public void setAuthType(String authType) {
        authType__ = authType;
    }
    /**
     * <p>provider を取得します。
     * @return provider
     * @see jp.groupsession.v2.wml.wml160.WebmailCsvModel#provider__
     */
    public String getProvider() {
        return provider__;
    }
    /**
     * <p>provider をセットします。
     * @param provider provider
     * @see jp.groupsession.v2.wml.wml160.WebmailCsvModel#provider__
     */
    public void setProvider(String provider) {
        provider__ = provider;
    }
    /**
     * <p>autoSave を取得します。
     * @return autoSave
     * @see jp.groupsession.v2.wml.wml160.WebmailCsvModel#autoSave__
     */
    public String getAutoSave() {
        return autoSave__;
    }
    /**
     * <p>autoSave をセットします。
     * @param autoSave autoSave
     * @see jp.groupsession.v2.wml.wml160.WebmailCsvModel#autoSave__
     */
    public void setAutoSave(String autoSave) {
        autoSave__ = autoSave;
    }
    /**
     * <p>proxyUser1 を取得します。
     * @return proxyUser1
     */
    public String getProxyUser1() {
        return proxyUser1__;
    }
    /**
     * <p>proxyUser1 をセットします。
     * @param proxyUser1 proxyUser1
     */
    public void setProxyUser1(String proxyUser1) {
        proxyUser1__ = proxyUser1;
    }
    /**
     * <p>proxyUser2 を取得します。
     * @return proxyUser2
     */
    public String getProxyUser2() {
        return proxyUser2__;
    }
    /**
     * <p>proxyUser2 をセットします。
     * @param proxyUser2 proxyUser2
     */
    public void setProxyUser2(String proxyUser2) {
        proxyUser2__ = proxyUser2;
    }
    /**
     * <p>proxyUser3 を取得します。
     * @return proxyUser3
     */
    public String getProxyUser3() {
        return proxyUser3__;
    }
    /**
     * <p>proxyUser3 をセットします。
     * @param proxyUser3 proxyUser3
     */
    public void setProxyUser3(String proxyUser3) {
        proxyUser3__ = proxyUser3;
    }
    /**
     * <p>proxyUser4 を取得します。
     * @return proxyUser4
     */
    public String getProxyUser4() {
        return proxyUser4__;
    }
    /**
     * <p>proxyUser4 をセットします。
     * @param proxyUser4 proxyUser4
     */
    public void setProxyUser4(String proxyUser4) {
        proxyUser4__ = proxyUser4;
    }
    /**
     * <p>proxyUser5 を取得します。
     * @return proxyUser5
     */
    public String getProxyUser5() {
        return proxyUser5__;
    }
    /**
     * <p>proxyUser5 をセットします。
     * @param proxyUser5 proxyUser5
     */
    public void setProxyUser5(String proxyUser5) {
        proxyUser5__ = proxyUser5;
    }
    /**
     * <br>[機  能] 順序付けのために 2 つの引数を比較する。
     * <br>[解  説]
     * <br>[備  考]
     * @param obj1 比較対象の最初のオブジェクト
     * @param obj2 比較対象の2番目のオブジェクト
     * @return 最初の引数が 2 番目の引数より小さい場合は負の整数、両方が等しい場合は 0、
     *          最初の引数が 2 番目の引数より大きい場合は正の整数
     */
    public int compare(WebmailCsvModel obj1, WebmailCsvModel obj2) {
        return (int) (obj1.getRowNum() - obj2.getRowNum());
    }
}