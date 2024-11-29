package jp.groupsession.v2.wml.restapi.accounts.configs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDiskDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSignDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel.SignArray;
import jp.groupsession.v2.wml.restapi.biz.WmlRestDirectoryBiz;

/**
 * <br>[機  能] WEBメールアカウント一覧 取得用ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsConfigsBiz {
    
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlAccountsConfigsBiz.class);

    /** RestApiコンテキスト */
    private final RestApiContext ctx__;
    /** 実行結果 */
    private List<WmlAccountsConfigsResultModel> result__ = new ArrayList<WmlAccountsConfigsResultModel>();

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param param WEBメールラベル一覧 取得用モデル
     * @param ctx RestApiコンテキスト
     */
    public WmlAccountsConfigsBiz(RestApiContext ctx) {
        ctx__ = ctx;
    }

    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsBiz#result__
     */
    public List<WmlAccountsConfigsResultModel> getResult() {
        return result__;
    }


    /**
     * <br>[機  能] アカウント一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     */
    protected void _execute() throws SQLException {

        Connection con = ctx__.getCon();
        int userSid = ctx__.getRequestUserSid();

        //アカウント一覧の取得
        log__.info("アカウント一覧の取得");
        WmlAccountDao accountDao = new WmlAccountDao(con);
        List<WmlAccountModel> accountList = accountDao.getAccountList(userSid);
        if (accountList == null || accountList.isEmpty()) {
            return;
        }

        //アカウントSIDを取得
        int[] accountSidList = accountList.stream()
            .mapToInt(mdl -> mdl.getWacSid())
            .toArray();

        //アカウントごとに署名の取得
        log__.info("アカウントごとに署名情報の取得");
        WmlAccountSignDao signDao = new WmlAccountSignDao(con);
        List<WmlAccountSignModel> signList = signDao.getSignList(accountSidList);
        
        //結果の取得
        log__.info("結果の取得");
        __getResult(accountList, signList);
        
    }

    /**
     * <br>[機  能] アカウント一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __getResult(List<WmlAccountModel> accountList,
        List<WmlAccountSignModel> signList) throws SQLException {

        Connection con = ctx__.getCon();
        RequestModel reqMdl = ctx__.getRequestModel();

        //結果の作成
        WmlAccountsConfigsResultModel resultModel = null;
        //デフォルトアカウントフラグ
        int defaultFlg = 1;

        //ループ内で使用する変数
        String themeName = "";
        String quote = "";
        long accountMidokuCount = 0;
        long useDiskSize = 0;
        long maxDiskSize = 0;
        List<SignArray> signArrayList;

        WmlBiz wmlBiz = new WmlBiz();
        WmlRestDirectoryBiz dirBiz = new WmlRestDirectoryBiz();
        WmlMaildataDao mailDao = new WmlMaildataDao(con);
        WmlAccountDiskDao diskDao = new WmlAccountDiskDao(con);

        WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
        WmlAdmConfModel admConfMdl = admConfDao.selectAdmData();

        for (WmlAccountModel wacMdl : accountList) {
            resultModel = new WmlAccountsConfigsResultModel();
            resultModel.setId(wacMdl.getWacAccountId());
            resultModel.setName(wacMdl.getWacName());
            resultModel.setDefaultFlg(defaultFlg);
            resultModel.setMemoText(wacMdl.getWacBiko());
            resultModel.setThemeType(wacMdl.getWacTheme());
            //テーマ区分からテーマ名を取得
            themeName = __getThemeName(wacMdl.getWacTheme());
            resultModel.setThemeName(themeName);
            //全てのメールボックスの未読件数の取得
            int wacSid = wacMdl.getWacSid();
            accountMidokuCount = mailDao.getNotReadCount(wacSid);
            resultModel.setNonReadCountNum((int) accountMidokuCount);

            //ディスク使用量の取得
            useDiskSize = diskDao.getUseDiskSize(wacSid);
            //ディスクの最大使用可能量の取得
            maxDiskSize = wmlBiz.getDiskLimitSize(con, wacSid);
            if (maxDiskSize < 0) {
                maxDiskSize = 0;
            }
            maxDiskSize = maxDiskSize * 1024 * 1024;

            resultModel.setDiskSizeUseLimitFlg(__getDiskLimitFlg(maxDiskSize));
            //警告を出すかの判定
            resultModel.setDiskSizeAlertFlg(__getAlertFlg(admConfMdl, useDiskSize, maxDiskSize));
            resultModel.setUseDiskSizeNum(useDiskSize);
            resultModel.setMaxDiskSizeNum(maxDiskSize);

            if (!StringUtil.isNullZeroString(wacMdl.getWacAutoto())) {
                resultModel.setDefaultToArray(
                    Arrays.stream(wacMdl.getWacAutoto().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()));
            }
            if (!StringUtil.isNullZeroString(wacMdl.getWacAutocc())) {
                resultModel.setDefaultCcArray(
                    Arrays.stream(wacMdl.getWacAutocc().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()));
            }
            if (!StringUtil.isNullZeroString(wacMdl.getWacAutobcc())) {
                resultModel.setDefaultBccArray(
                    Arrays.stream(wacMdl.getWacAutobcc().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList()));
            }

            if (admConfMdl.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
                //権限設定 > 管理者設定
                resultModel.setNeedAddressCheckFlg(admConfMdl.getWadCheckAddress());
                resultModel.setNeedAttachmentCheckFlg(admConfMdl.getWadCheckFile());
                resultModel.setAbleSelectDelaySendFlg(admConfMdl.getWadTimesent());
                //時間差送信 初期値が"時間差送信"であれば1を、"即時送信"であれば0を返す
                if (admConfMdl.getWadTimesentDef() == GSConstWebmail.WAC_TIMESENT_DEF_LATER) {
                    resultModel.setDefaultDelaySendType(1);
                } else {
                    resultModel.setDefaultDelaySendType(0);
                }
                //添付ファイル自動圧縮が"作成時に選択"の場合は1を返す。"圧縮する"，"圧縮しない"の場合は0を返す
                resultModel.setAbleSelectCompressFileFlg(admConfMdl.getWadCompressFile());
                //添付ファイル自動圧縮 初期値が"圧縮する"であれば1を、"圧縮しない"であれば0を返す
                if (admConfMdl.getWadCompressFileDef() == GSConstWebmail.WAC_COMPRESS_FILE_DEF_COMPRESS) {
                    resultModel.setDefaultCompressFileFlg(1);
                } else {
                    resultModel.setDefaultCompressFileFlg(0);
                }
            } else {
                //権限設定 > アカウント単位
                resultModel.setNeedAddressCheckFlg(wacMdl.getWacCheckAddress());
                resultModel.setNeedAttachmentCheckFlg(wacMdl.getWacCheckFile());
                resultModel.setAbleSelectDelaySendFlg(wacMdl.getWacTimesent());
                //時間差送信 初期値が"時間差送信"であれば1を、"即時送信"であれば0を返す
                if (wacMdl.getWacTimesentDef() == GSConstWebmail.WAC_TIMESENT_DEF_LATER) {
                    resultModel.setDefaultDelaySendType(1);
                } else {
                    resultModel.setDefaultDelaySendType(0);
                }
                //添付ファイル自動圧縮が"作成時に選択"の場合は1を返す。"圧縮する"，"圧縮しない"の場合は0を返す
                resultModel.setAbleSelectCompressFileFlg(wacMdl.getWacCompressFile());
                //添付ファイル自動圧縮 初期値が"圧縮する"であれば1を、"圧縮しない"であれば0を返す
                if (wacMdl.getWacCompressFileDef() == GSConstWebmail.WAC_COMPRESS_FILE_DEF_COMPRESS) {
                    resultModel.setDefaultCompressFileFlg(1);
                } else {
                    resultModel.setDefaultCompressFileFlg(0);
                }
            }
            
            //引用符区分から引用符を取得
            quote = __getQuoteString(wacMdl.getWacQuotes());
            resultModel.setQuoteText(quote);
            
            resultModel.setSignOrganizationText(wacMdl.getWacOrganization());
            resultModel.setSignPositionType(wacMdl.getWacSignPointKbn());
            //署名自動挿入が"挿入する"の場合は1を返す。"挿入しない"の場合は0を返す。
            if (wacMdl.getWacSignAuto() == GSConstWebmail.WAC_SIGN_AUTO_INSERT) {
                resultModel.setAutoSignFlg(1);
            } else {
                resultModel.setAutoSignFlg(0);
            }
            resultModel.setAutoSignReplyFlg(wacMdl.getWacSignDspKbn());

            //署名情報の設定
            signArrayList = signList.stream()
                .filter(mdl -> mdl.getWacSid() == wacSid)
                .map(mdl -> new SignArray(mdl))
                .collect(Collectors.toList());
            resultModel.setSignArray(signArrayList);

            //メールボックス情報の取得
            resultModel.setDirectoryArray(dirBiz.getDirectoryData(con, reqMdl, wacSid));

            //デフォルトアカウントフラグを変更(デフォルトアカウントは最初の1つのみ)
            defaultFlg = 0;

            result__.add(resultModel);
        }
    }

    /**
     * <br>[機  能] テーマ区分からテーマ名を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param themeType テーマ区分
     */
    private String __getThemeName(int themeType) {
        String themeName = "";
        GsMessage msgRes = new GsMessage(ctx__.getRequestModel());
        switch (themeType) {
            case GSConstWebmail.WAC_THEME_THEME1:
                themeName = msgRes.getMessage("cmn.default")
                    + msgRes.getMessage("cmn.theme.classic");
                break;
            case GSConstWebmail.WAC_THEME_THEME2:
                themeName = msgRes.getMessage("cmn.theme.gray")
                    + msgRes.getMessage("cmn.theme.classic");
                break;
            case GSConstWebmail.WAC_THEME_THEME3:
                themeName = msgRes.getMessage("cmn.theme.green")
                    + msgRes.getMessage("cmn.theme.classic");
                break;
            case GSConstWebmail.WAC_THEME_THEME4:
                themeName = msgRes.getMessage("cmn.theme.red")
                    + msgRes.getMessage("cmn.theme.classic");
                break;
            case GSConstWebmail.WAC_THEME_THEME5:
                themeName = msgRes.getMessage("cmn.theme.pink")
                    + msgRes.getMessage("cmn.theme.classic");
                break;
            case GSConstWebmail.WAC_THEME_THEME6:
                themeName = msgRes.getMessage("cmn.theme.yellow")
                    + msgRes.getMessage("cmn.theme.classic");
                break;
            case GSConstWebmail.WAC_THEME_THEME7:
                themeName = msgRes.getMessage("cmn.theme.original");
                break;
            case GSConstWebmail.WAC_THEME_THEME8:
                themeName = msgRes.getMessage("cmn.theme.sougen");
                break;
            case GSConstWebmail.WAC_THEME_THEME9:
                themeName = msgRes.getMessage("cmn.theme.sunset");
                break;
            case GSConstWebmail.WAC_THEME_THEME10:
                themeName = msgRes.getMessage("cmn.theme.sakura");
                break;
            case GSConstWebmail.WAC_THEME_THEME11:
                themeName = msgRes.getMessage("cmn.theme.sky");
                break;
            case GSConstWebmail.WAC_THEME_THEME12:
                themeName = msgRes.getMessage("cmn.theme.city");
                break;
            case GSConstWebmail.WAC_THEME_THEME13:
                themeName = msgRes.getMessage("cmn.theme.dark");
                break;
            case GSConstWebmail.WAC_THEME_THEME14:
                themeName = msgRes.getMessage("cmn.theme.mokume");
                break;
            case GSConstWebmail.WAC_THEME_THEME15:
                themeName = msgRes.getMessage("cmn.theme.darkblue");
                break;
            default:
                themeName = msgRes.getMessage("cmn.notset");
                break;
        }
        return themeName;
    }

    /**
     * <br>[機  能] 引用符区分から引用符文字列を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param quoteKbn 引用符区分
     */
    private String __getQuoteString(int quoteKbn) {
        String quote = "";
        GsMessage msgRes = new GsMessage(ctx__.getRequestModel());
        switch (quoteKbn) {
            case GSConstWebmail.WAC_QUOTES_DEF:
                quote = ">";
                break;
            case GSConstWebmail.WAC_QUOTES_NONE:
                quote = msgRes.getMessage("cmn.no");
                break;
            case GSConstWebmail.WAC_QUOTES_2:
                quote = ">>";
                break;
            case GSConstWebmail.WAC_QUOTES_3:
                quote = "<";
                break;
            case GSConstWebmail.WAC_QUOTES_4:
                quote = "<<";
                break;
            case GSConstWebmail.WAC_QUOTES_5:
                quote = "|";
                break;
            default:
                quote = ">";
                break;
        }
        return quote;
    }

    /**
     * <br>[機  能] ディスク最大容量，ディスク使用量を元に、ディスク容量警告を出すかを判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param admConfMdl 管理者設定情報
     * @param useDiskSize ディスク使用量
     * @param maxDiskSize ディスク最大容量
     * @return 0:ディスク容量警告を出さない, 1:ディスク容量警告を出す
     */
    private int __getAlertFlg(WmlAdmConfModel admConfMdl, long useDiskSize, long maxDiskSize) {
        if (admConfMdl.getWadWarnDisk() == GSConstWebmail.WAD_WARN_DISK_YES) {

            //管理者設定 ディスク容量警告 閾値
            int diskWarnTh = admConfMdl.getWadWarnDiskTh();
            BigDecimal diskLimit = new BigDecimal(maxDiskSize);
            if (diskLimit.longValue() <= 0) {
                return GSConstWebmail.WARN_DISK_NO;
            }
            BigDecimal diskUseSize = new BigDecimal(useDiskSize);
            diskLimit = diskLimit.multiply(new BigDecimal(diskWarnTh));
            diskLimit = diskLimit.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

            if (diskUseSize.compareTo(diskLimit) >= 0) {
                return GSConstWebmail.WARN_DISK_YES;
            }
            return GSConstWebmail.WARN_DISK_NO;
        }
        return GSConstWebmail.WARN_DISK_NO;
    }

    /**
     * <br>[機  能] ディスク最大容量から、ディスク容量制限の有無を判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param maxDiskSize ディスク最大容量
     * @return 0:ディスク容量制限 制限なし, 1:ディスク容量制限 制限あり
     */
    private int __getDiskLimitFlg(long maxDiskSize) {
        if (maxDiskSize <= 0) {
            return GSConstWebmail.WAC_DISK_UNLIMITED;
        }
        return GSConstWebmail.WAC_DISK_LIMIT;
        
    }
}
