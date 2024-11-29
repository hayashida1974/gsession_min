package jp.groupsession.v2.man.man250;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man250.model.Man250DspModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ検索画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man250ParamModel extends AbstractParamModel {
    //入力項目
    /** 選択グループ */
    private String man250SltGroup__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 選択ユーザ */
    private String man250SltUser__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 開始年 */
    private String man250SltYearFr__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 開始月 */
    private String man250SltMonthFr__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 開始日 */
    private String man250SltDayFr__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 開始時 */
    private String man250SltHourFr__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 開始分 */
    private String man250SltMinFr__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 終了年 */
    private String man250SltYearTo__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 終了月 */
    private String man250SltMonthTo__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 終了日 */
    private String man250SltDayTo__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 終了時 */
    private String man250SltHourTo__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 終了分 */
    private String man250SltMinTo__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 実行日時 開始年月日 */
    private String man250SltDateFr__ = null;
    /** 実行日時 開始時間 */
    private String man250SltTimeFr__ = null;
    /** 実行日時 終了年月日 */
    private String man250SltDateTo__ = null;
    /** 実行日時 終了時間 */
    private String man250SltTimeTo__ = null;
    /** プラグインID */
    private String man250SltPlugin__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** ログレベル エラー */
    private String man250SltLogError__ = "0";
    /** ログレベル 警告 */
    private String man250SltLogWarn__ = "0";
    /** ログレベル 重要情報 */
    private String man250SltLogInfo__ = "0";
    /** ログレベル トレース */
    private String man250SltLogTrace__ = "0";
    /** オーダーキー1 */
    private int man250OrderKey1__ = GSConst.ORDER_KEY_DESC;
    /** ソートキー1 */
    private int man250SortKey1__ = 0;
    /** オーダーキー2 */
    private int man250OrderKey2__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int man250SortKey2__ = 1;
    /** 上ページ */
    private int man250SltPage1__ = 1;
    /** 下ページ */
    private int man250SltPage2__ = 1;
    /** 表示ページ */
    private int man250PageNum__ = 1;
    /** ページラベル */
    private ArrayList<LabelValueBean> man250PageLabel__;
    /** 検索キーワード */
    private String man250KeyWord__ = null;
    /** キーワード検索区分 */
    private int man250KeyWordKbn__ = GSConstMain.KEY_WORD_KBN_AND;
    /** 検索対象 */
    private String[] man250SearchTarget__ = null;

    //セーブ
    /** 選択グループ */
    private String man250SvSltGroup__ = null;
    /** 選択ユーザ */
    private String man250SvSltUser__ = null;
    /** 実行日時 開始年 */
    private String man250SvSltYearFr__ = null;
    /** 実行日時 開始月 */
    private String man250SvSltMonthFr__ = null;
    /** 実行日時 開始日 */
    private String man250SvSltDayFr__ = null;
    /** 実行日時 開始時 */
    private String man250SvSltHourFr__ = null;
    /** 実行日時 開始分 */
    private String man250SvSltMinFr__ = null;
    /** 実行日時 終了年 */
    private String man250SvSltYearTo__ = null;
    /** 実行日時 終了月 */
    private String man250SvSltMonthTo__ = null;
    /** 実行日時 終了日 */
    private String man250SvSltDayTo__ = null;
    /** 実行日時 終了時 */
    private String man250SvSltHourTo__ = null;
    /** 実行日時 終了分 */
    private String man250SvSltMinTo__ = null;
    /** 実行日時 開始年月日 */
    private String man250SvSltDateFr__ = null;
    /** 実行日時 開始時間 */
    private String man250SvSltTimeFr__ = null;
    /** 実行日時 終了年月日 */
    private String man250SvSltDateTo__ = null;
    /** 実行日時 終了時間 */
    private String man250SvSltTimeTo__ = null;
    /** プラグインID */
    private String man250SvSltPlugin__ = null;
    /** ログレベル エラー */
    private String man250SvSltLogError__ = null;
    /** ログレベル 警告 */
    private String man250SvSltLogWarn__ = null;
    /** ログレベル 重要情報 */
    private String man250SvSltLogInfo__ = null;
    /** ログレベル トレース */
    private String man250SvSltLogTrace__ = null;
    /** オーダーキー1 */
    private int man250SvOrderKey1__ = 1;
    /** ソートキー1 */
    private int man250SvSortKey1__ = 1;
    /** オーダーキー2 */
    private int man250SvOrderKey2__ = 1;
    /** ソートキー2 */
    private int man250SvSortKey2__ = 1;
    /** 検索キーワード */
    private String man250SvKeyWord__ = null;
    /** キーワード検索区分 */
    private int man250SvKeyWordKbn__ = GSConstMain.KEY_WORD_KBN_AND;
    /** 検索対象 */
    private String[] man250SvSearchTarget__ = null;

    //表示項目
    /** プラグインコンボ */
    private List<LabelValueBean> plgLabel__ = null;
    /** グループ名コンボ */
    private List<LabelValueBean> grpLabel__ = null;
    /** ユーザ名コンボ */
    private List<UsrLabelValueBean> usrLabel__ = null;
    /** ソートコンボ */
    private List<LabelValueBean> sortLabel__ = null;

    /** オペレーションログリスト（一覧表示） */
    private ArrayList<Man250DspModel> man250DspList__ = null;

    /**
     * @return grpLabel
     */
    public List<LabelValueBean> getGrpLabel() {
        return grpLabel__;
    }
    /**
     * @param grpLabel 設定する grpLabel
     */
    public void setGrpLabel(List<LabelValueBean> grpLabel) {
        grpLabel__ = grpLabel;
    }
    /**
     * @return man250SltDayFr
     */
    public String getMan250SltDayFr() {
        return man250SltDayFr__;
    }
    /**
     * @param man250SltDayFr 設定する man250SltDayFr
     */
    public void setMan250SltDayFr(String man250SltDayFr) {
        man250SltDayFr__ = man250SltDayFr;
    }
    /**
     * @return man250SltDayTo
     */
    public String getMan250SltDayTo() {
        return man250SltDayTo__;
    }
    /**
     * @param man250SltDayTo 設定する man250SltDayTo
     */
    public void setMan250SltDayTo(String man250SltDayTo) {
        man250SltDayTo__ = man250SltDayTo;
    }
    /**
     * @return man250SltGroup
     */
    public String getMan250SltGroup() {
        return man250SltGroup__;
    }
    /**
     * @param man250SltGroup 設定する man250SltGroup
     */
    public void setMan250SltGroup(String man250SltGroup) {
        man250SltGroup__ = man250SltGroup;
    }
    /**
     * @return man250SltHourFr
     */
    public String getMan250SltHourFr() {
        return man250SltHourFr__;
    }
    /**
     * @param man250SltHourFr 設定する man250SltHourFr
     */
    public void setMan250SltHourFr(String man250SltHourFr) {
        man250SltHourFr__ = man250SltHourFr;
    }
    /**
     * @return man250SltHourTo
     */
    public String getMan250SltHourTo() {
        return man250SltHourTo__;
    }
    /**
     * @param man250SltHourTo 設定する man250SltHourTo
     */
    public void setMan250SltHourTo(String man250SltHourTo) {
        man250SltHourTo__ = man250SltHourTo;
    }
    /**
     * @return man250SltLogError
     */
    public String getMan250SltLogError() {
        return man250SltLogError__;
    }
    /**
     * @param man250SltLogError 設定する man250SltLogError
     */
    public void setMan250SltLogError(String man250SltLogError) {
        man250SltLogError__ = man250SltLogError;
    }
    /**
     * @return man250SltLogInfo
     */
    public String getMan250SltLogInfo() {
        return man250SltLogInfo__;
    }
    /**
     * @param man250SltLogInfo 設定する man250SltLogInfo
     */
    public void setMan250SltLogInfo(String man250SltLogInfo) {
        man250SltLogInfo__ = man250SltLogInfo;
    }
    /**
     * @return man250SltLogTrace
     */
    public String getMan250SltLogTrace() {
        return man250SltLogTrace__;
    }
    /**
     * @param man250SltLogTrace 設定する man250SltLogTrace
     */
    public void setMan250SltLogTrace(String man250SltLogTrace) {
        man250SltLogTrace__ = man250SltLogTrace;
    }
    /**
     * @return man250SltLogWarn
     */
    public String getMan250SltLogWarn() {
        return man250SltLogWarn__;
    }
    /**
     * @param man250SltLogWarn 設定する man250SltLogWarn
     */
    public void setMan250SltLogWarn(String man250SltLogWarn) {
        man250SltLogWarn__ = man250SltLogWarn;
    }
    /**
     * @return man250SltMinFr
     */
    public String getMan250SltMinFr() {
        return man250SltMinFr__;
    }
    /**
     * @param man250SltMinFr 設定する man250SltMinFr
     */
    public void setMan250SltMinFr(String man250SltMinFr) {
        man250SltMinFr__ = man250SltMinFr;
    }
    /**
     * @return man250SltMinTo
     */
    public String getMan250SltMinTo() {
        return man250SltMinTo__;
    }
    /**
     * @param man250SltMinTo 設定する man250SltMinTo
     */
    public void setMan250SltMinTo(String man250SltMinTo) {
        man250SltMinTo__ = man250SltMinTo;
    }
    /**
     * @return man250SltMonthFr
     */
    public String getMan250SltMonthFr() {
        return man250SltMonthFr__;
    }
    /**
     * @param man250SltMonthFr 設定する man250SltMonthFr
     */
    public void setMan250SltMonthFr(String man250SltMonthFr) {
        man250SltMonthFr__ = man250SltMonthFr;
    }
    /**
     * @return man250SltMonthTo
     */
    public String getMan250SltMonthTo() {
        return man250SltMonthTo__;
    }
    /**
     * @param man250SltMonthTo 設定する man250SltMonthTo
     */
    public void setMan250SltMonthTo(String man250SltMonthTo) {
        man250SltMonthTo__ = man250SltMonthTo;
    }
    /**
     * @return man250SltPlugin
     */
    public String getMan250SltPlugin() {
        return man250SltPlugin__;
    }
    /**
     * @param man250SltPlugin 設定する man250SltPlugin
     */
    public void setMan250SltPlugin(String man250SltPlugin) {
        man250SltPlugin__ = man250SltPlugin;
    }
    /**
     * @return man250SltUser
     */
    public String getMan250SltUser() {
        return man250SltUser__;
    }
    /**
     * @param man250SltUser 設定する man250SltUser
     */
    public void setMan250SltUser(String man250SltUser) {
        man250SltUser__ = man250SltUser;
    }
    /**
     * @return man250SltYearFr
     */
    public String getMan250SltYearFr() {
        return man250SltYearFr__;
    }
    /**
     * @param man250SltYearFr 設定する man250SltYearFr
     */
    public void setMan250SltYearFr(String man250SltYearFr) {
        man250SltYearFr__ = man250SltYearFr;
    }
    /**
     * @return man250SltYearTo
     */
    public String getMan250SltYearTo() {
        return man250SltYearTo__;
    }
    /**
     * @param man250SltYearTo 設定する man250SltYearTo
     */
    public void setMan250SltYearTo(String man250SltYearTo) {
        man250SltYearTo__ = man250SltYearTo;
    }
    /**
     * <p>man250SltDateFr を取得します。
     * @return man250SltDateFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltDateFr__
     */
    public String getMan250SltDateFr() {
        return man250SltDateFr__;
    }
    /**
     * <p>man250SltDateFr をセットします。
     * @param man250SltDateFr man250SltDateFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltDateFr__
     */
    public void setMan250SltDateFr(String man250SltDateFr) {
        man250SltDateFr__ = man250SltDateFr;
    }
    /**
     * <p>man250SltTimeFr を取得します。
     * @return man250SltTimeFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltTimeFr__
     */
    public String getMan250SltTimeFr() {
        return man250SltTimeFr__;
    }
    /**
     * <p>man250SltTimeFr をセットします。
     * @param man250SltTimeFr man250SltTimeFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltTimeFr__
     */
    public void setMan250SltTimeFr(String man250SltTimeFr) {
        man250SltTimeFr__ = man250SltTimeFr;
    }
    /**
     * <p>man250SltDateTo を取得します。
     * @return man250SltDateTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltDateTo__
     */
    public String getMan250SltDateTo() {
        return man250SltDateTo__;
    }
    /**
     * <p>man250SltDateTo をセットします。
     * @param man250SltDateTo man250SltDateTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltDateTo__
     */
    public void setMan250SltDateTo(String man250SltDateTo) {
        man250SltDateTo__ = man250SltDateTo;
    }
    /**
     * <p>man250SltTimeTo を取得します。
     * @return man250SltTimeTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltTimeTo__
     */
    public String getMan250SltTimeTo() {
        return man250SltTimeTo__;
    }
    /**
     * <p>man250SltTimeTo をセットします。
     * @param man250SltTimeTo man250SltTimeTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SltTimeTo__
     */
    public void setMan250SltTimeTo(String man250SltTimeTo) {
        man250SltTimeTo__ = man250SltTimeTo;
    }
    /**
     * @return man250SvSltDayFr
     */
    public String getMan250SvSltDayFr() {
        return man250SvSltDayFr__;
    }
    /**
     * @param man250SvSltDayFr 設定する man250SvSltDayFr
     */
    public void setMan250SvSltDayFr(String man250SvSltDayFr) {
        man250SvSltDayFr__ = man250SvSltDayFr;
    }
    /**
     * @return man250SvSltDayTo
     */
    public String getMan250SvSltDayTo() {
        return man250SvSltDayTo__;
    }
    /**
     * @param man250SvSltDayTo 設定する man250SvSltDayTo
     */
    public void setMan250SvSltDayTo(String man250SvSltDayTo) {
        man250SvSltDayTo__ = man250SvSltDayTo;
    }
    /**
     * @return man250SvSltGroup
     */
    public String getMan250SvSltGroup() {
        return man250SvSltGroup__;
    }
    /**
     * @param man250SvSltGroup 設定する man250SvSltGroup
     */
    public void setMan250SvSltGroup(String man250SvSltGroup) {
        man250SvSltGroup__ = man250SvSltGroup;
    }
    /**
     * @return man250SvSltHourFr
     */
    public String getMan250SvSltHourFr() {
        return man250SvSltHourFr__;
    }
    /**
     * @param man250SvSltHourFr 設定する man250SvSltHourFr
     */
    public void setMan250SvSltHourFr(String man250SvSltHourFr) {
        man250SvSltHourFr__ = man250SvSltHourFr;
    }
    /**
     * @return man250SvSltHourTo
     */
    public String getMan250SvSltHourTo() {
        return man250SvSltHourTo__;
    }
    /**
     * @param man250SvSltHourTo 設定する man250SvSltHourTo
     */
    public void setMan250SvSltHourTo(String man250SvSltHourTo) {
        man250SvSltHourTo__ = man250SvSltHourTo;
    }
    /**
     * @return man250SvSltLogError
     */
    public String getMan250SvSltLogError() {
        return man250SvSltLogError__;
    }
    /**
     * @param man250SvSltLogError 設定する man250SvSltLogError
     */
    public void setMan250SvSltLogError(String man250SvSltLogError) {
        man250SvSltLogError__ = man250SvSltLogError;
    }
    /**
     * @return man250SvSltLogInfo
     */
    public String getMan250SvSltLogInfo() {
        return man250SvSltLogInfo__;
    }
    /**
     * @param man250SvSltLogInfo 設定する man250SvSltLogInfo
     */
    public void setMan250SvSltLogInfo(String man250SvSltLogInfo) {
        man250SvSltLogInfo__ = man250SvSltLogInfo;
    }
    /**
     * @return man250SvSltLogTrace
     */
    public String getMan250SvSltLogTrace() {
        return man250SvSltLogTrace__;
    }
    /**
     * @param man250SvSltLogTrace 設定する man250SvSltLogTrace
     */
    public void setMan250SvSltLogTrace(String man250SvSltLogTrace) {
        man250SvSltLogTrace__ = man250SvSltLogTrace;
    }
    /**
     * @return man250SvSltLogWarn
     */
    public String getMan250SvSltLogWarn() {
        return man250SvSltLogWarn__;
    }
    /**
     * @param man250SvSltLogWarn 設定する man250SvSltLogWarn
     */
    public void setMan250SvSltLogWarn(String man250SvSltLogWarn) {
        man250SvSltLogWarn__ = man250SvSltLogWarn;
    }
    /**
     * @return man250SvSltMinFr
     */
    public String getMan250SvSltMinFr() {
        return man250SvSltMinFr__;
    }
    /**
     * @param man250SvSltMinFr 設定する man250SvSltMinFr
     */
    public void setMan250SvSltMinFr(String man250SvSltMinFr) {
        man250SvSltMinFr__ = man250SvSltMinFr;
    }
    /**
     * @return man250SvSltMinTo
     */
    public String getMan250SvSltMinTo() {
        return man250SvSltMinTo__;
    }
    /**
     * @param man250SvSltMinTo 設定する man250SvSltMinTo
     */
    public void setMan250SvSltMinTo(String man250SvSltMinTo) {
        man250SvSltMinTo__ = man250SvSltMinTo;
    }
    /**
     * @return man250SvSltMonthFr
     */
    public String getMan250SvSltMonthFr() {
        return man250SvSltMonthFr__;
    }
    /**
     * @param man250SvSltMonthFr 設定する man250SvSltMonthFr
     */
    public void setMan250SvSltMonthFr(String man250SvSltMonthFr) {
        man250SvSltMonthFr__ = man250SvSltMonthFr;
    }
    /**
     * @return man250SvSltMonthTo
     */
    public String getMan250SvSltMonthTo() {
        return man250SvSltMonthTo__;
    }
    /**
     * @param man250SvSltMonthTo 設定する man250SvSltMonthTo
     */
    public void setMan250SvSltMonthTo(String man250SvSltMonthTo) {
        man250SvSltMonthTo__ = man250SvSltMonthTo;
    }
    /**
     * @return man250SvSltPlugin
     */
    public String getMan250SvSltPlugin() {
        return man250SvSltPlugin__;
    }
    /**
     * @param man250SvSltPlugin 設定する man250SvSltPlugin
     */
    public void setMan250SvSltPlugin(String man250SvSltPlugin) {
        man250SvSltPlugin__ = man250SvSltPlugin;
    }
    /**
     * @return man250SvSltUser
     */
    public String getMan250SvSltUser() {
        return man250SvSltUser__;
    }
    /**
     * @param man250SvSltUser 設定する man250SvSltUser
     */
    public void setMan250SvSltUser(String man250SvSltUser) {
        man250SvSltUser__ = man250SvSltUser;
    }
    /**
     * @return man250SvSltYearFr
     */
    public String getMan250SvSltYearFr() {
        return man250SvSltYearFr__;
    }
    /**
     * @param man250SvSltYearFr 設定する man250SvSltYearFr
     */
    public void setMan250SvSltYearFr(String man250SvSltYearFr) {
        man250SvSltYearFr__ = man250SvSltYearFr;
    }
    /**
     * @return man250SvSltYearTo
     */
    public String getMan250SvSltYearTo() {
        return man250SvSltYearTo__;
    }
    /**
     * @param man250SvSltYearTo 設定する man250SvSltYearTo
     */
    public void setMan250SvSltYearTo(String man250SvSltYearTo) {
        man250SvSltYearTo__ = man250SvSltYearTo;
    }
    /**
     * <p>man250SvSltDateFr を取得します。
     * @return man250SvSltDateFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltDateFr__
     */
    public String getMan250SvSltDateFr() {
        return man250SvSltDateFr__;
    }
    /**
     * <p>man250SvSltDateFr をセットします。
     * @param man250SvSltDateFr man250SvSltDateFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltDateFr__
     */
    public void setMan250SvSltDateFr(String man250SvSltDateFr) {
        man250SvSltDateFr__ = man250SvSltDateFr;
    }
    /**
     * <p>man250SvSltTimeFr を取得します。
     * @return man250SvSltTimeFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltTimeFr__
     */
    public String getMan250SvSltTimeFr() {
        return man250SvSltTimeFr__;
    }
    /**
     * <p>man250SvSltTimeFr をセットします。
     * @param man250SvSltTimeFr man250SvSltTimeFr
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltTimeFr__
     */
    public void setMan250SvSltTimeFr(String man250SvSltTimeFr) {
        man250SvSltTimeFr__ = man250SvSltTimeFr;
    }
    /**
     * <p>man250SvSltDateTo を取得します。
     * @return man250SvSltDateTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltDateTo__
     */
    public String getMan250SvSltDateTo() {
        return man250SvSltDateTo__;
    }
    /**
     * <p>man250SvSltDateTo をセットします。
     * @param man250SvSltDateTo man250SvSltDateTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltDateTo__
     */
    public void setMan250SvSltDateTo(String man250SvSltDateTo) {
        man250SvSltDateTo__ = man250SvSltDateTo;
    }
    /**
     * <p>man250SvSltTimeTo を取得します。
     * @return man250SvSltTimeTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltTimeTo__
     */
    public String getMan250SvSltTimeTo() {
        return man250SvSltTimeTo__;
    }
    /**
     * <p>man250SvSltTimeTo をセットします。
     * @param man250SvSltTimeTo man250SvSltTimeTo
     * @see jp.groupsession.v2.man.man250.Man250ParamModel#man250SvSltTimeTo__
     */
    public void setMan250SvSltTimeTo(String man250SvSltTimeTo) {
        man250SvSltTimeTo__ = man250SvSltTimeTo;
    }
    /**
     * @return plgLabel
     */
    public List<LabelValueBean> getPlgLabel() {
        return plgLabel__;
    }
    /**
     * @param plgLabel 設定する plgLabel
     */
    public void setPlgLabel(List<LabelValueBean> plgLabel) {
        plgLabel__ = plgLabel;
    }
    /**
     * @return sortLabel
     */
    public List<LabelValueBean> getSortLabel() {
        return sortLabel__;
    }
    /**
     * @param sortLabel 設定する sortLabel
     */
    public void setSortLabel(List<LabelValueBean> sortLabel) {
        sortLabel__ = sortLabel;
    }
    /**
     * @return usrLabel
     */
    public List<UsrLabelValueBean> getUsrLabel() {
        return usrLabel__;
    }
    /**
     * @param usrLabel 設定する usrLabel
     */
    public void setUsrLabel(List<UsrLabelValueBean> usrLabel) {
        usrLabel__ = usrLabel;
    }
    /**
     * <p>man250OrderKey1 を取得します。
     * @return man250OrderKey1
     */
    public int getMan250OrderKey1() {
        return man250OrderKey1__;
    }
    /**
     * <p>man250OrderKey1 をセットします。
     * @param man250OrderKey1 man250OrderKey1
     */
    public void setMan250OrderKey1(int man250OrderKey1) {
        man250OrderKey1__ = man250OrderKey1;
    }
    /**
     * <p>man250SortKey1 を取得します。
     * @return man250SortKey1
     */
    public int getMan250SortKey1() {
        return man250SortKey1__;
    }
    /**
     * <p>man250SortKey1 をセットします。
     * @param man250SortKey1 man250SortKey1
     */
    public void setMan250SortKey1(int man250SortKey1) {
        man250SortKey1__ = man250SortKey1;
    }
    /**
     * <p>man250OrderKey2 を取得します。
     * @return man250OrderKey2
     */
    public int getMan250OrderKey2() {
        return man250OrderKey2__;
    }
    /**
     * <p>man250OrderKey2 をセットします。
     * @param man250OrderKey2 man250OrderKey2
     */
    public void setMan250OrderKey2(int man250OrderKey2) {
        man250OrderKey2__ = man250OrderKey2;
    }
    /**
     * <p>man250SortKey2 を取得します。
     * @return man250SortKey2
     */
    public int getMan250SortKey2() {
        return man250SortKey2__;
    }
    /**
     * <p>man250SortKey2 をセットします。
     * @param man250SortKey2 man250SortKey2
     */
    public void setMan250SortKey2(int man250SortKey2) {
        man250SortKey2__ = man250SortKey2;
    }
    /**
     * <p>man250SltPage1 を取得します。
     * @return man250SltPage1
     */
    public int getMan250SltPage1() {
        return man250SltPage1__;
    }
    /**
     * <p>man250SltPage1 をセットします。
     * @param man250SltPage1 man250SltPage1
     */
    public void setMan250SltPage1(int man250SltPage1) {
        man250SltPage1__ = man250SltPage1;
    }
    /**
     * <p>man250SltPage2 を取得します。
     * @return man250SltPage2
     */
    public int getMan250SltPage2() {
        return man250SltPage2__;
    }
    /**
     * <p>man250SltPage2 をセットします。
     * @param man250SltPage2 man250SltPage2
     */
    public void setMan250SltPage2(int man250SltPage2) {
        man250SltPage2__ = man250SltPage2;
    }
    /**
     * <p>man250PageNum を取得します。
     * @return man250PageNum
     */
    public int getMan250PageNum() {
        return man250PageNum__;
    }
    /**
     * <p>man250PageNum をセットします。
     * @param man250PageNum man250PageNum
     */
    public void setMan250PageNum(int man250PageNum) {
        man250PageNum__ = man250PageNum;
    }

    /**
     * <p>man250SvOrderKey1 を取得します。
     * @return man250SvOrderKey1
     */
    public int getMan250SvOrderKey1() {
        return man250SvOrderKey1__;
    }

    /**
     * <p>man250SvOrderKey1 をセットします。
     * @param man250SvOrderKey1 man250SvOrderKey1
     */
    public void setMan250SvOrderKey1(int man250SvOrderKey1) {
        man250SvOrderKey1__ = man250SvOrderKey1;
    }

    /**
     * <p>man250SvSortKey1 を取得します。
     * @return man250SvSortKey1
     */
    public int getMan250SvSortKey1() {
        return man250SvSortKey1__;
    }

    /**
     * <p>man250SvSortKey1 をセットします。
     * @param man250SvSortKey1 man250SvSortKey1
     */
    public void setMan250SvSortKey1(int man250SvSortKey1) {
        man250SvSortKey1__ = man250SvSortKey1;
    }

    /**
     * <p>man250SvOrderKey2 を取得します。
     * @return man250SvOrderKey2
     */
    public int getMan250SvOrderKey2() {
        return man250SvOrderKey2__;
    }

    /**
     * <p>man250SvOrderKey2 をセットします。
     * @param man250SvOrderKey2 man250SvOrderKey2
     */
    public void setMan250SvOrderKey2(int man250SvOrderKey2) {
        man250SvOrderKey2__ = man250SvOrderKey2;
    }

    /**
     * <p>man250SvSortKey2 を取得します。
     * @return man250SvSortKey2
     */
    public int getMan250SvSortKey2() {
        return man250SvSortKey2__;
    }

    /**
     * <p>man250SvSortKey2 をセットします。
     * @param man250SvSortKey2 man250SvSortKey2
     */
    public void setMan250SvSortKey2(int man250SvSortKey2) {
        man250SvSortKey2__ = man250SvSortKey2;
    }

    /**
     * <p>man250DspList を取得します。
     * @return man250DspList
     */
    public ArrayList<Man250DspModel> getMan250DspList() {
        return man250DspList__;
    }

    /**
     * <p>man250DspList をセットします。
     * @param man250DspList man250DspList
     */
    public void setMan250DspList(ArrayList<Man250DspModel> man250DspList) {
        man250DspList__ = man250DspList;
    }

    /**
     * <p>man250PageLabel を取得します。
     * @return man250PageLabel
     */
    public ArrayList<LabelValueBean> getMan250PageLabel() {
        return man250PageLabel__;
    }

    /**
     * <p>man250PageLabel をセットします。
     * @param man250PageLabel man250PageLabel
     */
    public void setMan250PageLabel(ArrayList<LabelValueBean> man250PageLabel) {
        man250PageLabel__ = man250PageLabel;
    }
    /**
     * <p>man250KeyWord を取得します。
     * @return man250KeyWord
     */
    public String getMan250KeyWord() {
        return man250KeyWord__;
    }
    /**
     * <p>man250KeyWord をセットします。
     * @param man250KeyWord man250KeyWord
     */
    public void setMan250KeyWord(String man250KeyWord) {
        man250KeyWord__ = man250KeyWord;
    }
    /**
     * <p>man250KeyWordKbn を取得します。
     * @return man250KeyWordKbn
     */
    public int getMan250KeyWordKbn() {
        return man250KeyWordKbn__;
    }
    /**
     * <p>man250KeyWordKbn をセットします。
     * @param man250KeyWordKbn man250KeyWordKbn
     */
    public void setMan250KeyWordKbn(int man250KeyWordKbn) {
        man250KeyWordKbn__ = man250KeyWordKbn;
    }
    /**
     * <p>man250SearchTarget を取得します。
     * @return man250SearchTarget
     */
    public String[] getMan250SearchTarget() {
        return man250SearchTarget__;
    }
    /**
     * <p>man250SearchTarget をセットします。
     * @param man250SearchTarget man250SearchTarget
     */
    public void setMan250SearchTarget(String[] man250SearchTarget) {
        man250SearchTarget__ = man250SearchTarget;
    }
    /**
     * <p>man250SvKeyWord を取得します。
     * @return man250SvKeyWord
     */
    public String getMan250SvKeyWord() {
        return man250SvKeyWord__;
    }
    /**
     * <p>man250SvKeyWord をセットします。
     * @param man250SvKeyWord man250SvKeyWord
     */
    public void setMan250SvKeyWord(String man250SvKeyWord) {
        man250SvKeyWord__ = man250SvKeyWord;
    }
    /**
     * <p>man250SvKeyWordKbn を取得します。
     * @return man250SvKeyWordKbn
     */
    public int getMan250SvKeyWordKbn() {
        return man250SvKeyWordKbn__;
    }
    /**
     * <p>man250SvKeyWordKbn をセットします。
     * @param man250SvKeyWordKbn man250SvKeyWordKbn
     */
    public void setMan250SvKeyWordKbn(int man250SvKeyWordKbn) {
        man250SvKeyWordKbn__ = man250SvKeyWordKbn;
    }
    /**
     * <p>man250SvSearchTarget を取得します。
     * @return man250SvSearchTarget
     */
    public String[] getMan250SvSearchTarget() {
        return man250SvSearchTarget__;
    }
    /**
     * <p>man250SvSearchTarget をセットします。
     * @param man250SvSearchTarget man250SvSearchTarget
     */
    public void setMan250SvSearchTarget(String[] man250SvSearchTarget) {
        man250SvSearchTarget__ = man250SvSearchTarget;
    }

}