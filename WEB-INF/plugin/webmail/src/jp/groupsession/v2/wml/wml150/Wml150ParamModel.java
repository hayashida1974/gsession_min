package jp.groupsession.v2.wml.wml150;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.wml020.Wml020ParamModel;

/**
 * <br>[機  能] WEBメール アカウント基本設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150ParamModel extends Wml020ParamModel {

    /** 初期表示区分 */
    private int wml150initFlg__ = GSConstWebmail.DSP_FIRST;
    /** アカウントの作成区分 */
    private int wml150acntMakeKbn__ = GSConstWebmail.KANRI_USER_NO;
    /** 送信メール形式 */
    private int wml150acntSendFormat__ = GSConstWebmail.ACNT_SENDFORMAT_NOSET;
    /** 送受信ログ登録 */
    private int wml150acntLogRegist__ = GSConstWebmail.ACNT_LOG_REGIST_REGIST;

    /** 権限区分 */
    private int wml150permitKbn__ = GSConstWebmail.PERMIT_ACCOUNT;
    /** ディスク容量 */
    private int wml150diskSize__ = GSConstWebmail.WAC_DISK_UNLIMITED;
    /** ディスク容量 最大値 */
    private String wml150diskSizeLimit__ = null;
    /** ディスク容量 管理者強制設定 */
    private boolean wml150diskSizeComp__ = false;
    /** ディスク容量警告 */
    private int wml150warnDisk__ = GSConstWebmail.WAD_WARN_DISK_NO;
    /** ディスク容量警告 閾値*/
    private int wml150warnDiskThreshold__ = 0;
    /** 受信時削除 */
    private int wml150delReceive__ = GSConstWebmail.WAC_DELRECEIVE_NO;
    /** 自動受信 */
    private int wml150autoResv__ = GSConstWebmail.MAIL_AUTO_RSV_ON;
    /** 自動受信設定時間 */
    private int wml150AutoReceiveTime__ = GSConstWebmail.AUTO_RECEIVE_5;
    /** 自動受信設定時間 ラベル */
    private List<LabelValueBean> wml150AutoRsvKeyLabel__ = null;
    /** 送信メールサイズ制限 */
    private int wml150sendMaxSizeKbn__ = GSConstWebmail.WAD_SEND_LIMIT_UNLIMITED;
    /** 送信メールサイズ制限 上限サイズ */
    private String wml150sendMaxSize__ = null;
    /** 転送先制限 */
    private int wml150FwLimit__ = GSConstWebmail.WAD_FWLIMIT_UNLIMITED;
    /** 転送先制限 許可する文字列 */
    private String wml150FwLimitText__ = null;
    /** 転送先制限 許可する文字列(保存用) */
    private String wml150svFwLimitText__ = null;
    /** 転送先制限 チェックフラグ */
    private int wml150FwLimitCheckFlg__ = 0;
    /** 転送先制限 フィルターの転送設定を削除する */
    private int wml150FwLimitDelete__ = 0;
    /** 表示判定 */
    private int wml150elementKbn__ = 0;
    /** BCC強制変換 */
    private int wml150bcc__ = GSConstWebmail.WAD_BCC_UNLIMITED;
    /** BCC強制変換 閾値 */
    private int wml150bccThreshold__ = 0;
    /** 代理人 */
    private int wml150proxyUser__ = GSConstWebmail.WAD_PROXY_USER_NO;
    /** サーバ情報の設定 */
    private int wml150settingServer__ = GSConstWebmail.WAD_SETTING_SERVER_NO;
    /** リンク制限*/
    private int wml150linkLimit__ = GSConstWebmail.WAD_LINK_UNLIMITED;
    /** TLD制限   */
    private int wml150TldLimit__ = GSConstWebmail.WAD_TLD_UNLIMITED;
    /** TLD制限 許可する文字列 */
    private String wml150TldLimitText__ = null;
    /** TLD制限 許可する文字列(保存用) */
    private String wml150svTldLimitText__ = null;

    /** メール受信サーバ */
    private String wml150receiveServer__ = null;
    /** メール受信サーバ ポート番号 */
    private String wml150receiveServerPort__ = null;
    /** メール受信サーバ SSL */
    private int wml150receiveServerEncrypt__ = 0;
    /** メール送信サーバ */
    private String wml150sendServer__ = null;
    /** メール送信サーバ名 ポート番号 */
    private String wml150sendServerPort__ = null;
    /** メール受信サーバ SSL */
    private int wml150sendServerEncrypt__ = 0;
    /** 宛先の確認 */
    private int wml150checkAddress__ = 0;
    /** 添付ファイルの確認 */
    private int wml150checkFile__ = 0;
    /** 添付ファイル自動圧縮 */
    private int wml150compressFile__ = 0;
    /** 添付ファイル自動圧縮 初期値 */
    private int wml150compressFileDef__ = 0;
    /** 時間差送信 */
    private int wml150timeSent__ = 0;
    /** 時間差送信 初期値 */
    private int wml150timeSentDef__ = 0;

    /** ディスク容量警告 閾値 選択値 */
    private List<LabelValueBean> warnDiskThresholdList__ = null;
    /** BCC強制変換 閾値 選択値 */
    private List<LabelValueBean> bccThresholdList__ = null;
    /** メール転送制限 不正なフィルター  */
    private List<Wml150FwCheckModel> fwCheckList__ = null;
    /** 暗号化プロトコル 一覧 */
    private List<LabelValueBean> wml150AngoProtocolCombo__ = null;

    /**
     * <p>wml150acntMakeKbn を取得します。
     * @return wml150acntMakeKbn
     */
    public int getWml150acntMakeKbn() {
        return wml150acntMakeKbn__;
    }
    /**
     * <p>wml150acntMakeKbn をセットします。
     * @param wml150acntMakeKbn wml150acntMakeKbn
     */
    public void setWml150acntMakeKbn(int wml150acntMakeKbn) {
        wml150acntMakeKbn__ = wml150acntMakeKbn;
    }
    /**
     * <p>wml150acntSendFormat を取得します。
     * @return wml150acntSendFormat
     */
    public int getWml150acntSendFormat() {
        return wml150acntSendFormat__;
    }
    /**
     * <p>wml150acntSendFormat をセットします。
     * @param wml150acntSendFormat wml150acntSendFormat
     */
    public void setWml150acntSendFormat(int wml150acntSendFormat) {
        wml150acntSendFormat__ = wml150acntSendFormat;
    }
    /**
     * <p>wml150initFlg を取得します。
     * @return wml150initFlg
     */
    public int getWml150initFlg() {
        return wml150initFlg__;
    }
    /**
     * <p>wml150initFlg をセットします。
     * @param wml150initFlg wml150initFlg
     */
    public void setWml150initFlg(int wml150initFlg) {
        wml150initFlg__ = wml150initFlg;
    }
    /**
     * <p>wml150acntLogRegist を取得します。
     * @return wml150acntLogRegist
     */
    public int getWml150acntLogRegist() {
        return wml150acntLogRegist__;
    }
    /**
     * <p>wml150acntLogRegist をセットします。
     * @param wml150acntLogRegist wml150acntLogRegist
     */
    public void setWml150acntLogRegist(int wml150acntLogRegist) {
        wml150acntLogRegist__ = wml150acntLogRegist;
    }
    /**
     * <p>wml150AutoReceiveTime を取得します。
     * @return wml150AutoReceiveTime
     */
    public int getWml150AutoReceiveTime() {
        return wml150AutoReceiveTime__;
    }
    /**
     * <p>wml150AutoReceiveTime をセットします。
     * @param wml150AutoReceiveTime wml150AutoReceiveTime
     */
    public void setWml150AutoReceiveTime(int wml150AutoReceiveTime) {
        wml150AutoReceiveTime__ = wml150AutoReceiveTime;
    }
    /**
     * <p>wml150autoResv を取得します。
     * @return wml150autoResv
     */
    public int getWml150autoResv() {
        return wml150autoResv__;
    }
    /**
     * <p>wml150autoResv をセットします。
     * @param wml150autoResv wml150autoResv
     */
    public void setWml150autoResv(int wml150autoResv) {
        wml150autoResv__ = wml150autoResv;
    }
    /**
     * <p>wml150AutoRsvKeyLabel を取得します。
     * @return wml150AutoRsvKeyLabel
     */
    public List<LabelValueBean> getWml150AutoRsvKeyLabel() {
        return wml150AutoRsvKeyLabel__;
    }
    /**
     * <p>wml150AutoRsvKeyLabel をセットします。
     * @param wml150AutoRsvKeyLabel wml150AutoRsvKeyLabel
     */
    public void setWml150AutoRsvKeyLabel(List<LabelValueBean> wml150AutoRsvKeyLabel) {
        wml150AutoRsvKeyLabel__ = wml150AutoRsvKeyLabel;
    }
    /**
     * <p>wml150delReceive を取得します。
     * @return wml150delReceive
     */
    public int getWml150delReceive() {
        return wml150delReceive__;
    }
    /**
     * <p>wml150delReceive をセットします。
     * @param wml150delReceive wml150delReceive
     */
    public void setWml150delReceive(int wml150delReceive) {
        wml150delReceive__ = wml150delReceive;
    }
    /**
     * <p>wml150diskSize を取得します。
     * @return wml150diskSize
     */
    public int getWml150diskSize() {
        return wml150diskSize__;
    }
    /**
     * <p>wml150diskSize をセットします。
     * @param wml150diskSize wml150diskSize
     */
    public void setWml150diskSize(int wml150diskSize) {
        wml150diskSize__ = wml150diskSize;
    }
    /**
     * <p>wml150diskSizeLimit を取得します。
     * @return wml150diskSizeLimit
     */
    public String getWml150diskSizeLimit() {
        return wml150diskSizeLimit__;
    }
    /**
     * <p>wml150diskSizeLimit をセットします。
     * @param wml150diskSizeLimit wml150diskSizeLimit
     */
    public void setWml150diskSizeLimit(String wml150diskSizeLimit) {
        wml150diskSizeLimit__ = wml150diskSizeLimit;
    }
    /**
     * <p>wml150permitKbn を取得します。
     * @return wml150permitKbn
     */
    public int getWml150permitKbn() {
        return wml150permitKbn__;
    }
    /**
     * <p>wml150permitKbn をセットします。
     * @param wml150permitKbn wml150permitKbn
     */
    public void setWml150permitKbn(int wml150permitKbn) {
        wml150permitKbn__ = wml150permitKbn;
    }
    /**
     * <p>wml150elementKbn を取得します。
     * @return wml150elementKbn
     */
    public int getWml150elementKbn() {
        return wml150elementKbn__;
    }
    /**
     * <p>wml150elementKbn をセットします。
     * @param wml150elementKbn wml150elementKbn
     */
    public void setWml150elementKbn(int wml150elementKbn) {
        wml150elementKbn__ = wml150elementKbn;
    }
    /**
     * <p>wml150bcc を取得します。
     * @return wml150bcc
     */
    public int getWml150bcc() {
        return wml150bcc__;
    }
    /**
     * <p>wml150bcc をセットします。
     * @param wml150bcc wml150bcc
     */
    public void setWml150bcc(int wml150bcc) {
        wml150bcc__ = wml150bcc;
    }
    /**
     * <p>wml150proxyUser を取得します。
     * @return wml150proxyUser
     */
    public int getWml150proxyUser() {
        return wml150proxyUser__;
    }
    /**
     * <p>wml150proxyUser をセットします。
     * @param wml150proxyUser wml150proxyUser
     */
    public void setWml150proxyUser(int wml150proxyUser) {
        wml150proxyUser__ = wml150proxyUser;
    }
    /**
     * <p>wml150bccThreshold を取得します。
     * @return wml150bccThreshold
     */
    public int getWml150bccThreshold() {
        return wml150bccThreshold__;
    }
    /**
     * <p>wml150bccThreshold をセットします。
     * @param wml150bccThreshold wml150bccThreshold
     */
    public void setWml150bccThreshold(int wml150bccThreshold) {
        wml150bccThreshold__ = wml150bccThreshold;
    }
    /**
     * <p>wml150receiveServer を取得します。
     * @return wml150receiveServer
     */
    public String getWml150receiveServer() {
        return wml150receiveServer__;
    }
    /**
     * <p>wml150receiveServer をセットします。
     * @param wml150receiveServer wml150receiveServer
     */
    public void setWml150receiveServer(String wml150receiveServer) {
        wml150receiveServer__ = wml150receiveServer;
    }
    /**
     * <p>wml150receiveServerPort を取得します。
     * @return wml150receiveServerPort
     */
    public String getWml150receiveServerPort() {
        return wml150receiveServerPort__;
    }
    /**
     * <p>wml150receiveServerPort をセットします。
     * @param wml150receiveServerPort wml150receiveServerPort
     */
    public void setWml150receiveServerPort(String wml150receiveServerPort) {
        wml150receiveServerPort__ = wml150receiveServerPort;
    }

    /**
     * <p>wml150sendServer を取得します。
     * @return wml150sendServer
     */
    public String getWml150sendServer() {
        return wml150sendServer__;
    }
    /**
     * <p>wml150sendServer をセットします。
     * @param wml150sendServer wml150sendServer
     */
    public void setWml150sendServer(String wml150sendServer) {
        wml150sendServer__ = wml150sendServer;
    }
    /**
     * <p>wml150sendServerPort を取得します。
     * @return wml150sendServerPort
     */
    public String getWml150sendServerPort() {
        return wml150sendServerPort__;
    }
    /**
     * <p>wml150sendServerPort をセットします。
     * @param wml150sendServerPort wml150sendServerPort
     */
    public void setWml150sendServerPort(String wml150sendServerPort) {
        wml150sendServerPort__ = wml150sendServerPort;
    }

    /**
     * <p>wml150diskSizeComp を取得します。
     * @return wml150diskSizeComp
     */
    public boolean isWml150diskSizeComp() {
        return wml150diskSizeComp__;
    }
    /**
     * <p>wml150diskSizeComp をセットします。
     * @param wml150diskSizeComp wml150diskSizeComp
     */
    public void setWml150diskSizeComp(boolean wml150diskSizeComp) {
        wml150diskSizeComp__ = wml150diskSizeComp;
    }
    /**
     * <p>wml150checkAddress を取得します。
     * @return wml150checkAddress
     */
    public int getWml150checkAddress() {
        return wml150checkAddress__;
    }
    /**
     * <p>wml150checkAddress をセットします。
     * @param wml150checkAddress wml150checkAddress
     */
    public void setWml150checkAddress(int wml150checkAddress) {
        wml150checkAddress__ = wml150checkAddress;
    }
    /**
     * <p>wml150checkFile を取得します。
     * @return wml150checkFile
     */
    public int getWml150checkFile() {
        return wml150checkFile__;
    }
    /**
     * <p>wml150checkFile をセットします。
     * @param wml150checkFile wml150checkFile
     */
    public void setWml150checkFile(int wml150checkFile) {
        wml150checkFile__ = wml150checkFile;
    }
    /**
     * <p>wml150compressFile を取得します。
     * @return wml150compressFile
     */
    public int getWml150compressFile() {
        return wml150compressFile__;
    }
    /**
     * <p>wml150compressFile をセットします。
     * @param wml150compressFile wml150compressFile
     */
    public void setWml150compressFile(int wml150compressFile) {
        wml150compressFile__ = wml150compressFile;
    }
    /**
     * <p>wml150timeSent を取得します。
     * @return wml150timeSent
     */
    public int getWml150timeSent() {
        return wml150timeSent__;
    }
    /**
     * <p>wml150timeSent をセットします。
     * @param wml150timeSent wml150timeSent
     */
    public void setWml150timeSent(int wml150timeSent) {
        wml150timeSent__ = wml150timeSent;
    }
    /**
     * <p>wml150sendMaxSizeKbn を取得します。
     * @return wml150sendMaxSizeKbn
     */
    public int getWml150sendMaxSizeKbn() {
        return wml150sendMaxSizeKbn__;
    }
    /**
     * <p>wml150sendMaxSizeKbn をセットします。
     * @param wml150sendMaxSizeKbn wml150sendMaxSizeKbn
     */
    public void setWml150sendMaxSizeKbn(int wml150sendMaxSizeKbn) {
        wml150sendMaxSizeKbn__ = wml150sendMaxSizeKbn;
    }
    /**
     * <p>wml150sendMaxSize を取得します。
     * @return wml150sendMaxSize
     */
    public String getWml150sendMaxSize() {
        return wml150sendMaxSize__;
    }
    /**
     * <p>wml150sendMaxSize をセットします。
     * @param wml150sendMaxSize wml150sendMaxSize
     */
    public void setWml150sendMaxSize(String wml150sendMaxSize) {
        wml150sendMaxSize__ = wml150sendMaxSize;
    }
    /**
     * <p>wml150FwLimit を取得します。
     * @return wml150FwLimit
     */
    public int getWml150FwLimit() {
        return wml150FwLimit__;
    }
    /**
     * <p>wml150FwLimit をセットします。
     * @param wml150FwLimit wml150FwLimit
     */
    public void setWml150FwLimit(int wml150FwLimit) {
        wml150FwLimit__ = wml150FwLimit;
    }
    /**
     * <p>wml150FwLimitDelete を取得します。
     * @return wml150FwLimitDelete
     */
    public int getWml150FwLimitDelete() {
        return wml150FwLimitDelete__;
    }
    /**
     * <p>wml150FwLimitDelete をセットします。
     * @param wml150FwLimitDelete wml150FwLimitDelete
     */
    public void setWml150FwLimitDelete(int wml150FwLimitDelete) {
        wml150FwLimitDelete__ = wml150FwLimitDelete;
    }
    /**
     * <p>wml150FwLimitText を取得します。
     * @return wml150FwLimitText
     */
    public String getWml150FwLimitText() {
        return wml150FwLimitText__;
    }
    /**
     * <p>wml150FwLimitText をセットします。
     * @param wml150FwLimitText wml150FwLimitText
     */
    public void setWml150FwLimitText(String wml150FwLimitText) {
        wml150FwLimitText__ = wml150FwLimitText;
    }
    /**
     * <p>fwCheckList を取得します。
     * @return fwCheckList
     */
    public List<Wml150FwCheckModel> getFwCheckList() {
        return fwCheckList__;
    }
    /**
     * <p>fwCheckList をセットします。
     * @param fwCheckList fwCheckList
     */
    public void setFwCheckList(List<Wml150FwCheckModel> fwCheckList) {
        fwCheckList__ = fwCheckList;
    }
    /**
     * <p>wml150svFwLimitText を取得します。
     * @return wml150svFwLimitText
     */
    public String getWml150svFwLimitText() {
        return wml150svFwLimitText__;
    }
    /**
     * <p>wml150svFwLimitText をセットします。
     * @param wml150svFwLimitText wml150svFwLimitText
     */
    public void setWml150svFwLimitText(String wml150svFwLimitText) {
        wml150svFwLimitText__ = wml150svFwLimitText;
    }
    /**
     * <p>wml150FwLimitCheckFlg を取得します。
     * @return wml150FwLimitCheckFlg
     */
    public int getWml150FwLimitCheckFlg() {
        return wml150FwLimitCheckFlg__;
    }
    /**
     * <p>wml150FwLimitCheckFlg をセットします。
     * @param wml150FwLimitCheckFlg wml150FwLimitCheckFlg
     */
    public void setWml150FwLimitCheckFlg(int wml150FwLimitCheckFlg) {
        wml150FwLimitCheckFlg__ = wml150FwLimitCheckFlg;
    }
    /**
     * <p>wml150warnDisk を取得します。
     * @return wml150warnDisk
     */
    public int getWml150warnDisk() {
        return wml150warnDisk__;
    }
    /**
     * <p>wml150warnDisk をセットします。
     * @param wml150warnDisk wml150warnDisk
     */
    public void setWml150warnDisk(int wml150warnDisk) {
        wml150warnDisk__ = wml150warnDisk;
    }
    /**
     * <p>wml150warnDiskThreshold を取得します。
     * @return wml150warnDiskThreshold
     */
    public int getWml150warnDiskThreshold() {
        return wml150warnDiskThreshold__;
    }
    /**
     * <p>wml150warnDiskThreshold をセットします。
     * @param wml150warnDiskThreshold wml150warnDiskThreshold
     */
    public void setWml150warnDiskThreshold(int wml150warnDiskThreshold) {
        wml150warnDiskThreshold__ = wml150warnDiskThreshold;
    }
    /**
     * <p>wml150settingServer を取得します。
     * @return wml150settingServer
     */
    public int getWml150settingServer() {
        return wml150settingServer__;
    }
    /**
     * <p>wml150settingServer をセットします。
     * @param wml150settingServer wml150settingServer
     */
    public void setWml150settingServer(int wml150settingServer) {
        wml150settingServer__ = wml150settingServer;
    }
    /**
     * <p>wml150compressFileDef を取得します。
     * @return wml150compressFileDef
     */
    public int getWml150compressFileDef() {
        return wml150compressFileDef__;
    }
    /**
     * <p>wml150compressFileDef をセットします。
     * @param wml150compressFileDef wml150compressFileDef
     */
    public void setWml150compressFileDef(int wml150compressFileDef) {
        wml150compressFileDef__ = wml150compressFileDef;
    }
    /**
     * <p>wml150timeSentDef を取得します。
     * @return wml150timeSentDef
     */
    public int getWml150timeSentDef() {
        return wml150timeSentDef__;
    }
    /**
     * <p>wml150timeSentDef をセットします。
     * @param wml150timeSentDef wml150timeSentDef
     */
    public void setWml150timeSentDef(int wml150timeSentDef) {
        wml150timeSentDef__ = wml150timeSentDef;
    }
    /**
     * <p>bccThresholdList を取得します。
     * @return bccThresholdList
     */
    public List<LabelValueBean> getBccThresholdList() {
        return bccThresholdList__;
    }
    /**
     * <p>bccThresholdList をセットします。
     * @param bccThresholdList bccThresholdList
     */
    public void setBccThresholdList(List<LabelValueBean> bccThresholdList) {
        bccThresholdList__ = bccThresholdList;
    }
    /**
     * <p>warnDiskThresholdList を取得します。
     * @return warnDiskThresholdList
     */
    public List<LabelValueBean> getWarnDiskThresholdList() {
        return warnDiskThresholdList__;
    }
    /**
     * <p>warnDiskThresholdList をセットします。
     * @param warnDiskThresholdList warnDiskThresholdList
     */
    public void setWarnDiskThresholdList(List<LabelValueBean> warnDiskThresholdList) {
        warnDiskThresholdList__ = warnDiskThresholdList;
    }
    /**
     * <p>wml150linkLimit を取得します。
     * @return wml150linkLimit
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150linkLimit__
     */
    public int getWml150linkLimit() {
        return wml150linkLimit__;
    }
    /**
     * <p>wml150linkLimit をセットします。
     * @param wml150linkLimit wml150linkLimit
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150linkLimit__
     */
    public void setWml150linkLimit(int wml150linkLimit) {
        wml150linkLimit__ = wml150linkLimit;
    }
    /**
     * <p>wml150TldLimit を取得します。
     * @return wml150TldLimit
     * @see jp.groupsession.v2.wml.wml150.Wml150Form#wml150TldLimit__
     */
    public int getWml150TldLimit() {
        return wml150TldLimit__;
    }
    /**
     * <p>wml150TldLimit をセットします。
     * @param wml150TldLimit wml150TldLimit
     * @see jp.groupsession.v2.wml.wml150.Wml150Form#wml150TldLimit__
     */
    public void setWml150TldLimit(int wml150TldLimit) {
        wml150TldLimit__ = wml150TldLimit;
    }
    /**
     * <p>wml150TldLimitText を取得します。
     * @return wml150TldLimitText
     */
    public String getWml150TldLimitText() {
        return wml150TldLimitText__;
    }
    /**
     * <p>wml150TldLimitText をセットします。
     * @param wml150TldLimitText wml150TldLimitText
     */
    public void setWml150TldLimitText(String wml150TldLimitText) {
        wml150TldLimitText__ = wml150TldLimitText;
    }
    /**
     * <p>wml150svTldLimitText を取得します。
     * @return wml150svTldLimitText
     */
    public String getWml150svTldLimitText() {
        return wml150svTldLimitText__;
    }
    /**
     * <p>wml150svTldLimitText をセットします。
     * @param wml150svTldLimitText wml150svTldLimitText
     */
    public void setWml150svTldLimitText(String wml150svTldLimitText) {
        wml150svTldLimitText__ = wml150svTldLimitText;
    }
    /**
     * <p>wml150receiveServerEncrypt を取得します。
     * @return wml150receiveServerEncrypt
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150receiveServerEncrypt__
     */
    public int getWml150receiveServerEncrypt() {
        return wml150receiveServerEncrypt__;
    }
    /**
     * <p>wml150receiveServerEncrypt をセットします。
     * @param wml150receiveServerEncrypt wml150receiveServerEncrypt
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150receiveServerEncrypt__
     */
    public void setWml150receiveServerEncrypt(int wml150receiveServerEncrypt) {
        wml150receiveServerEncrypt__ = wml150receiveServerEncrypt;
    }
    /**
     * <p>wml150sendServerEncrypt を取得します。
     * @return wml150sendServerEncrypt
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150sendServerEncrypt__
     */
    public int getWml150sendServerEncrypt() {
        return wml150sendServerEncrypt__;
    }
    /**
     * <p>wml150sendServerEncrypt をセットします。
     * @param wml150sendServerEncrypt wml150sendServerEncrypt
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150sendServerEncrypt__
     */
    public void setWml150sendServerEncrypt(int wml150sendServerEncrypt) {
        wml150sendServerEncrypt__ = wml150sendServerEncrypt;
    }
    /**
     * <p>wml150AngoProtocolCombo を取得します。
     * @return wml150AngoProtocolCombo
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150AngoProtocolCombo__
     */
    public List<LabelValueBean> getWml150AngoProtocolCombo() {
        return wml150AngoProtocolCombo__;
    }
    /**
     * <p>wml150AngoProtocolCombo をセットします。
     * @param wml150AngoProtocolCombo wml150AngoProtocolCombo
     * @see jp.groupsession.v2.wml.wml150.Wml150ParamModel#wml150AngoProtocolCombo__
     */
    public void setWml150AngoProtocolCombo(
            List<LabelValueBean> wml150AngoProtocolCombo) {
        wml150AngoProtocolCombo__ = wml150AngoProtocolCombo;
    }
}