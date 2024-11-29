package jp.groupsession.v2.man.man250.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ検索画面 検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man250SearchModel extends AbstractModel {

    /** ユーザSID */
    private int usrSid__;

    /** 昇順降順 */
    private int order__;
    /** ソートKEY */
    private int sortKey__;
    /** 昇順降順 */
    private int order2__;
    /** ソートKEY */
    private int sortKey2__;
    /** オフセット */
    private int offset__;
    /** 最大表示件数 */
    private int limit__;

    //検索条件
    /** グループSID */
    private String sltGroupSid__;
    /** ユーザSID */
    private int sltUserSid__;
    /** 開始日from */
    private UDate dateFr__;
    /** 開始日to */
    private UDate dateTo__;

    /** ログレベル エラー */
    private String logLevelError__;
    /** ログレベル 警告 */
    private String logLevelWarn__;
    /** ログレベル 重要情報 */
    private String logLevelInfo__;
    /** ログレベル トレース*/
    private String logLevelTrace__;

    /** プラグインID */
    private String pluginId__;

    /** グループ所属区分 */
    private int gpBelongKbn__ = 0;

    /** 検索キーワード */
    private List<String> keyWord__;
    /** キーワード検索区分 */
    private int keyWordKbn__;
    /** 検索対象 画面・機能名 */
    private boolean tartgetFunc__ = false;
    /** 検索対象 操作 */
    private boolean targetOperation__ = false;
    /** 検索対象 内容 */
    private boolean targetContent__ = false;
    /** 検索対象 IPアドレス */
    private boolean targetIpaddress__ = false;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }

    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
    }

    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }

    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }

    /**
     * <p>order2 を取得します。
     * @return order2
     */
    public int getOrder2() {
        return order2__;
    }

    /**
     * <p>order2 をセットします。
     * @param order2 order2
     */
    public void setOrder2(int order2) {
        order2__ = order2;
    }

    /**
     * <p>sortKey2 を取得します。
     * @return sortKey2
     */
    public int getSortKey2() {
        return sortKey2__;
    }

    /**
     * <p>sortKey2 をセットします。
     * @param sortKey2 sortKey2
     */
    public void setSortKey2(int sortKey2) {
        sortKey2__ = sortKey2;
    }

    /**
     * <p>offset を取得します。
     * @return offset
     */
    public int getOffset() {
        return offset__;
    }

    /**
     * <p>offset をセットします。
     * @param offset offset
     */
    public void setOffset(int offset) {
        offset__ = offset;
    }

    /**
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }

    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }

    /**
     * <p>sltGroupSid を取得します。
     * @return sltGroupSid
     */
    public String getSltGroupSid() {
        return sltGroupSid__;
    }

    /**
     * <p>sltGroupSid をセットします。
     * @param sltGroupSid sltGroupSid
     */
    public void setSltGroupSid(String sltGroupSid) {
        sltGroupSid__ = sltGroupSid;
    }

    /**
     * <p>sltUserSid を取得します。
     * @return sltUserSid
     */
    public int getSltUserSid() {
        return sltUserSid__;
    }

    /**
     * <p>sltUserSid をセットします。
     * @param sltUserSid sltUserSid
     */
    public void setSltUserSid(int sltUserSid) {
        sltUserSid__ = sltUserSid;
    }

    /**
     * <p>dateFr を取得します。
     * @return dateFr
     */
    public UDate getDateFr() {
        return dateFr__;
    }

    /**
     * <p>dateFr をセットします。
     * @param dateFr dateFr
     */
    public void setDateFr(UDate dateFr) {
        dateFr__ = dateFr;
    }

    /**
     * <p>dateTo を取得します。
     * @return dateTo
     */
    public UDate getDateTo() {
        return dateTo__;
    }

    /**
     * <p>dateTo をセットします。
     * @param dateTo dateTo
     */
    public void setDateTo(UDate dateTo) {
        dateTo__ = dateTo;
    }

    /**
     * <p>gpBelongKbn を取得します。
     * @return gpBelongKbn
     */
    public int getGpBelongKbn() {
        return gpBelongKbn__;
    }

    /**
     * <p>gpBelongKbn をセットします。
     * @param gpBelongKbn gpBelongKbn
     */
    public void setGpBelongKbn(int gpBelongKbn) {
        gpBelongKbn__ = gpBelongKbn;
    }

    /**
     * <p>logLevelError を取得します。
     * @return logLevelError
     */
    public String getLogLevelError() {
        return logLevelError__;
    }

    /**
     * <p>logLevelError をセットします。
     * @param logLevelError logLevelError
     */
    public void setLogLevelError(String logLevelError) {
        logLevelError__ = logLevelError;
    }

    /**
     * <p>logLevelWarn を取得します。
     * @return logLevelWarn
     */
    public String getLogLevelWarn() {
        return logLevelWarn__;
    }

    /**
     * <p>logLevelWarn をセットします。
     * @param logLevelWarn logLevelWarn
     */
    public void setLogLevelWarn(String logLevelWarn) {
        logLevelWarn__ = logLevelWarn;
    }

    /**
     * <p>logLevelInfo を取得します。
     * @return logLevelInfo
     */
    public String getLogLevelInfo() {
        return logLevelInfo__;
    }

    /**
     * <p>logLevelInfo をセットします。
     * @param logLevelInfo logLevelInfo
     */
    public void setLogLevelInfo(String logLevelInfo) {
        logLevelInfo__ = logLevelInfo;
    }

    /**
     * <p>logLevelTrace を取得します。
     * @return logLevelTrace
     */
    public String getLogLevelTrace() {
        return logLevelTrace__;
    }

    /**
     * <p>logLevelTrace をセットします。
     * @param logLevelTrace logLevelTrace
     */
    public void setLogLevelTrace(String logLevelTrace) {
        logLevelTrace__ = logLevelTrace;
    }

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     */
    public String getPluginId() {
        return pluginId__;
    }

    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }

    /**
     * <p>keyWord を取得します。
     * @return keyWord
     */
    public List<String> getKeyWord() {
        return keyWord__;
    }

    /**
     * <p>keyWord をセットします。
     * @param keyWord keyWord
     */
    public void setKeyWord(List<String> keyWord) {
        keyWord__ = keyWord;
    }

    /**
     * <p>keyWordKbn を取得します。
     * @return keyWordKbn
     */
    public int getKeyWordKbn() {
        return keyWordKbn__;
    }

    /**
     * <p>keyWordKbn をセットします。
     * @param keyWordKbn keyWordKbn
     */
    public void setKeyWordKbn(int keyWordKbn) {
        keyWordKbn__ = keyWordKbn;
    }

    /**
     * <p>tartgetFunc を取得します。
     * @return tartgetFunc
     */
    public boolean isTartgetFunc() {
        return tartgetFunc__;
    }

    /**
     * <p>tartgetFunc をセットします。
     * @param tartgetFunc tartgetFunc
     */
    public void setTartgetFunc(boolean tartgetFunc) {
        tartgetFunc__ = tartgetFunc;
    }

    /**
     * <p>targetOperation を取得します。
     * @return targetOperation
     */
    public boolean isTargetOperation() {
        return targetOperation__;
    }

    /**
     * <p>targetOperation をセットします。
     * @param targetOperation targetOperation
     */
    public void setTargetOperation(boolean targetOperation) {
        targetOperation__ = targetOperation;
    }

    /**
     * <p>targetContent を取得します。
     * @return targetContent
     */
    public boolean isTargetContent() {
        return targetContent__;
    }

    /**
     * <p>targetContent をセットします。
     * @param targetContent targetContent
     */
    public void setTargetContent(boolean targetContent) {
        targetContent__ = targetContent;
    }

    /**
     * <p>targetIpaddress を取得します。
     * @return targetIpaddress
     */
    public boolean isTargetIpaddress() {
        return targetIpaddress__;
    }

    /**
     * <p>targetIpaddress をセットします。
     * @param targetIpaddress targetIpaddress
     */
    public void setTargetIpaddress(boolean targetIpaddress) {
        targetIpaddress__ = targetIpaddress;
    }


 }
