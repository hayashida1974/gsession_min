package jp.groupsession.v2.wml.restapi.accounts.configs;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.restapi.model.DirectoryArray;

/**
 * <br>[機  能] WEBメール アカウント一覧取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ResponceModel(targetField = {
    "id",
    "name",
    "defaultFlg",
    "memoText",
    "themeType",
    "themeName",
    "nonReadCountNum",
    "diskSizeUseLimitFlg",
    "diskSizeAlertFlg",
    "useDiskSizeNum",
    "maxDiskSizeNum",
    "defaultToArray",
    "defaultCcArray",
    "defaultBccArray",
    "needAddressCheckFlg",
    "needAttachmentCheckFlg",
    "ableSelectDelaySendFlg",
    "defaultDelaySendType",
    "ableSelectCompressFileFlg",
    "defaultCompressFileFlg",
    "quoteText",
    "signOrganizationText",
    "signPositionType",
    "autoSignFlg",
    "autoSignReplyFlg",
    "signArray",
    "directoryArray"
    })
public class WmlAccountsConfigsResultModel {
    
    /** アカウントID */
    private String id__ = "";
    /** アカウント名 */
    private String name__ = "";
    /** デフォルトアカウントフラグ */
    private int defaultFlg__;
    /** 備考 */
    private String memoText__ = "";
    /** テーマタイプ */
    private int themeType__;
    /** テーマ名称 */
    private String themeName__ = "";
    /** 未読メール件数 */
    private int nonReadCountNum__;
    /** ディスク使用量 制限フラグ */
    private int diskSizeUseLimitFlg__;
    /** ディスク容量警告 */
    private int diskSizeAlertFlg__;
    /** ディスク使用量 */
    private long useDiskSizeNum__;
    /** ディスク使用制限量 */
    private long maxDiskSizeNum__;
    /** 自動TO */
    @ChildElementName("defaultToInfo")
    private List<String> defaultToArray__ = null;
    /** 自動CC */
    @ChildElementName("defaultCcInfo")
    private List<String> defaultCcArray__ = null;
    /** 自動BCC */
    @ChildElementName("defaultBccInfo")
    private List<String> defaultBccArray__ = null;
    /** 送信先確認 */
    private int needAddressCheckFlg__;
    /** 添付ファイル確認 */
    private int needAttachmentCheckFlg__;
    /** 時間差送信	 */
    private int ableSelectDelaySendFlg__;
    /** 時間差送信初期値 */
    private int defaultDelaySendType__;
    /** 添付ファイル自動圧縮 */
    private int ableSelectCompressFileFlg__;
    /** 添付ファイル自動圧縮初期値 */
    private int defaultCompressFileFlg__;
    /** 引用符 */
    private String quoteText__;
    /** 署名組織名 */
    private String signOrganizationText__;
    /** 署名位置 */
    private int signPositionType__;
    /** 署名自動挿入区分 */
    private int autoSignFlg__;
    /** 署名自動挿入区分（返信時） */
    private int autoSignReplyFlg__;
    /** 署名配列*/
    @ChildElementName("signInfo")
    private List<SignArray> signArray__;
    /** ディレクトリ配列 */
    @ChildElementName("directoryInfo")
    private List<DirectoryArray> directoryArray__;

    @ResponceModel(targetField = {
        "title",
        "body",
        "defaultFlg"
        })
    public static class SignArray {
        /** 含有モデル*/
        private final WmlAccountSignModel base__;
        /**
         *
         * コンストラクタ
         * @param base モデル
         */
        public SignArray(WmlAccountSignModel base) {
            base__ = base;
        }
        /** @return title */
        public String getTitle() {
            return base__.getWsiTitle();
        }
        /** @return body */
        public String getBody() {
            return base__.getWsiSign();
        }
        /** @return defaultFlg */
        public long getDefaultFlg() {
            return base__.getWsiDef();
        }
    } 

    /**
     * <p>id を取得します。
     * @return id
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#id__
     */
    public String getId() {
        return id__;
    }

    /**
     * <p>id を設定します。
     * @param id
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#id__
     */
    public void setId(String id) {
        id__ = id;
    }

    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#name__
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>name を設定します。
     * @param name
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>defaultFlg を取得します。
     * @return defaultFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultFlg__
     */
    public int getDefaultFlg() {
        return defaultFlg__;
    }

    /**
     * <p>defaultFlg を設定します。
     * @param defaultFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultFlg__
     */
    public void setDefaultFlg(int defaultFlg) {
        defaultFlg__ = defaultFlg;
    }

    /**
     * <p>memoText を取得します。
     * @return memoText
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#memoText__
     */
    public String getMemoText() {
        return memoText__;
    }

    /**
     * <p>memoText を設定します。
     * @param memoText
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#memoText__
     */
    public void setMemoText(String memoText) {
        memoText__ = memoText;
    }

    /**
     * <p>themeType を取得します。
     * @return themeType
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#themeType__
     */
    public int getThemeType() {
        return themeType__;
    }

    /**
     * <p>themeType を設定します。
     * @param themeType
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#themeType__
     */
    public void setThemeType(int themeType) {
        themeType__ = themeType;
    }

    /**
     * <p>themeName を取得します。
     * @return themeName
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#themeName__
     */
    public String getThemeName() {
        return themeName__;
    }

    /**
     * <p>themeName を設定します。
     * @param themeName
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#themeName__
     */
    public void setThemeName(String themeName) {
        themeName__ = themeName;
    }

    /**
     * <p>nonReadCountNum を取得します。
     * @return nonReadCountNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#nonReadCountNum__
     */
    public int getNonReadCountNum() {
        return nonReadCountNum__;
    }

    /**
     * <p>nonReadCountNum を設定します。
     * @param nonReadCountNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#nonReadCountNum__
     */
    public void setNonReadCountNum(int nonReadCountNum) {
        nonReadCountNum__ = nonReadCountNum;
    }

    /**
     * <p>diskSizeUseLimitFlg を取得します。
     * @return diskSizeUseLimitFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#diskSizeUseLimitFlg__
     */
    public int getDiskSizeUseLimitFlg() {
        return diskSizeUseLimitFlg__;
    }

    /**
     * <p>diskSizeUseLimitFlg を設定します。
     * @param diskSizeUseLimitFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#diskSizeUseLimitFlg__
     */
    public void setDiskSizeUseLimitFlg(int diskSizeUseLimitFlg) {
        diskSizeUseLimitFlg__ = diskSizeUseLimitFlg;
    }

    /**
     * <p>diskSizeAlertFlg を取得します。
     * @return diskSizeAlertFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#diskSizeAlertFlg__
     */
    public int getDiskSizeAlertFlg() {
        return diskSizeAlertFlg__;
    }

    /**
     * <p>diskSizeAlertFlg を設定します。
     * @param diskSizeAlertFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#diskSizeAlertFlg__
     */
    public void setDiskSizeAlertFlg(int diskSizeAlertFlg) {
        diskSizeAlertFlg__ = diskSizeAlertFlg;
    }

    /**
     * <p>useDiskSizeNum を取得します。
     * @return useDiskSizeNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#useDiskSizeNum__
     */
    public long getUseDiskSizeNum() {
        return useDiskSizeNum__;
    }

    /**
     * <p>useDiskSizeNum を設定します。
     * @param useDiskSizeNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#useDiskSizeNum__
     */
    public void setUseDiskSizeNum(long useDiskSizeNum) {
        useDiskSizeNum__ = useDiskSizeNum;
    }

    /**
     * <p>maxDiskSizeNum を取得します。
     * @return maxDiskSizeNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#maxDiskSizeNum__
     */
    public long getMaxDiskSizeNum() {
        return maxDiskSizeNum__;
    }

    /**
     * <p>maxDiskSizeNum を設定します。
     * @param maxDiskSizeNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#maxDiskSizeNum__
     */
    public void setMaxDiskSizeNum(long maxDiskSizeNum) {
        maxDiskSizeNum__ = maxDiskSizeNum;
    }

    /**
     * <p>defaultToArray を取得します。
     * @return defaultToArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultToArray__
     */
    public List<String> getDefaultToArray() {
        return defaultToArray__;
    }

    /**
     * <p>defaultToArray を設定します。
     * @param defaultToArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultToArray__
     */
    public void setDefaultToArray(List<String> defaultToArray) {
        defaultToArray__ = defaultToArray;
    }

    /**
     * <p>defaultCcArray を取得します。
     * @return defaultCcArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultCcArray__
     */
    public List<String> getDefaultCcArray() {
        return defaultCcArray__;
    }

    /**
     * <p>defaultCcArray を設定します。
     * @param defaultCcArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultCcArray__
     */
    public void setDefaultCcArray(List<String> defaultCcArray) {
        defaultCcArray__ = defaultCcArray;
    }

    /**
     * <p>defaultBccArray を取得します。
     * @return defaultBccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultBccArray__
     */
    public List<String> getDefaultBccArray() {
        return defaultBccArray__;
    }

    /**
     * <p>defaultBccArray を設定します。
     * @param defaultBccArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultBccArray__
     */
    public void setDefaultBccArray(List<String> defaultBccArray) {
        defaultBccArray__ = defaultBccArray;
    }

    /**
     * <p>needAddressCheckFlg を取得します。
     * @return needAddressCheckFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#needAddressCheckFlg__
     */
    public int getNeedAddressCheckFlg() {
        return needAddressCheckFlg__;
    }

    /**
     * <p>needAddressCheckFlg を設定します。
     * @param needAddressCheckFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#needAddressCheckFlg__
     */
    public void setNeedAddressCheckFlg(int needAddressCheckFlg) {
        needAddressCheckFlg__ = needAddressCheckFlg;
    }

    /**
     * <p>needAttachmentCheckFlg を取得します。
     * @return needAttachmentCheckFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#needAttachmentCheckFlg__
     */
    public int getNeedAttachmentCheckFlg() {
        return needAttachmentCheckFlg__;
    }

    /**
     * <p>needAttachmentCheckFlg を設定します。
     * @param needAttachmentCheckFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#needAttachmentCheckFlg__
     */
    public void setNeedAttachmentCheckFlg(int needAttachmentCheckFlg) {
        needAttachmentCheckFlg__ = needAttachmentCheckFlg;
    }

    /**
     * <p>ableSelectDelaySendFlg を取得します。
     * @return ableSelectDelaySendFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#ableSelectDelaySendFlg__
     */
    public int getAbleSelectDelaySendFlg() {
        return ableSelectDelaySendFlg__;
    }

    /**
     * <p>ableSelectDelaySendFlg を設定します。
     * @param ableSelectDelaySendFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#ableSelectDelaySendFlg__
     */
    public void setAbleSelectDelaySendFlg(int ableSelectDelaySendFlg) {
        ableSelectDelaySendFlg__ = ableSelectDelaySendFlg;
    }

    /**
     * <p>defaultDelaySendType を取得します。
     * @return defaultDelaySendType
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultDelaySendType__
     */
    public int getDefaultDelaySendType() {
        return defaultDelaySendType__;
    }

    /**
     * <p>defaultDelaySendType を設定します。
     * @param defaultDelaySendType
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultDelaySendType__
     */
    public void setDefaultDelaySendType(int defaultDelaySendType) {
        defaultDelaySendType__ = defaultDelaySendType;
    }

    /**
     * <p>ableSelectCompressFileFlg を取得します。
     * @return ableSelectCompressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#ableSelectCompressFileFlg__
     */
    public int getAbleSelectCompressFileFlg() {
        return ableSelectCompressFileFlg__;
    }

    /**
     * <p>ableSelectCompressFileFlg を設定します。
     * @param ableSelectCompressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#ableSelectCompressFileFlg__
     */
    public void setAbleSelectCompressFileFlg(int ableSelectCompressFileFlg) {
        ableSelectCompressFileFlg__ = ableSelectCompressFileFlg;
    }

    /**
     * <p>defaultCompressFileFlg を取得します。
     * @return defaultCompressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultCompressFileFlg__
     */
    public int getDefaultCompressFileFlg() {
        return defaultCompressFileFlg__;
    }

    /**
     * <p>defaultCompressFileFlg を設定します。
     * @param defaultCompressFileFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#defaultCompressFileFlg__
     */
    public void setDefaultCompressFileFlg(int defaultCompressFileFlg) {
        defaultCompressFileFlg__ = defaultCompressFileFlg;
    }

    /**
     * <p>quoteText を取得します。
     * @return quoteText
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#quoteText__
     */
    public String getQuoteText() {
        return quoteText__;
    }

    /**
     * <p>quoteText を設定します。
     * @param quoteText
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#quoteText__
     */
    public void setQuoteText(String quoteText) {
        quoteText__ = quoteText;
    }

    /**
     * <p>signOrganizationText を取得します。
     * @return signOrganizationText
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#signOrganizationText__
     */
    public String getSignOrganizationText() {
        return signOrganizationText__;
    }

    /**
     * <p>signOrganizationText を設定します。
     * @param signOrganizationText
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#signOrganizationText__
     */
    public void setSignOrganizationText(String signOrganizationText) {
        signOrganizationText__ = signOrganizationText;
    }

    /**
     * <p>signPositionType を取得します。
     * @return signPositionType
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#signPositionType__
     */
    public int getSignPositionType() {
        return signPositionType__;
    }

    /**
     * <p>signPositionType を設定します。
     * @param signPositionType
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#signPositionType__
     */
    public void setSignPositionType(int signPositionType) {
        signPositionType__ = signPositionType;
    }

    /**
     * <p>autoSignFlg を取得します。
     * @return autoSignFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#autoSignFlg__
     */
    public int getAutoSignFlg() {
        return autoSignFlg__;
    }

    /**
     * <p>autoSignFlg を設定します。
     * @param autoSignFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#autoSignFlg__
     */
    public void setAutoSignFlg(int autoSignFlg) {
        autoSignFlg__ = autoSignFlg;
    }

    /**
     * <p>autoSignReplyFlg を取得します。
     * @return autoSignReplyFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#autoSignReplyFlg__
     */
    public int getAutoSignReplyFlg() {
        return autoSignReplyFlg__;
    }

    /**
     * <p>autoSignReplyFlg を設定します。
     * @param autoSignReplyFlg
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#autoSignReplyFlg__
     */
    public void setAutoSignReplyFlg(int autoSignReplyFlg) {
        autoSignReplyFlg__ = autoSignReplyFlg;
    }

    /**
     * <p>signArray を取得します。
     * @return signArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#signArray__
     */
    public List<SignArray> getSignArray() {
        return signArray__;
    }

    /**
     * <p>signArray を設定します。
     * @param signArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#signArray__
     */
    public void setSignArray(List<SignArray> signArray) {
        signArray__ = signArray;
    }

    /**
     * <p>directoryArray を取得します。
     * @return directoryArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#directoryArray__
     */
    public List<DirectoryArray> getDirectoryArray() {
        return directoryArray__;
    }

    /**
     * <p>directoryArray を設定します。
     * @param directoryArray
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel#directoryArray__
     */
    public void setDirectoryArray(List<DirectoryArray> directoryArray) {
        directoryArray__ = directoryArray;
    }
}
