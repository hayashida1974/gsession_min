package jp.groupsession.v2.wml.wml140;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.wml.wml130.Wml130ParamModel;

/**
 * <br>[機  能] WEBメール フィルタ登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml140ParamModel extends Wml130ParamModel {
    /** フィルター区分 全ての条件に一致 */
    public static final int WML140_FILKBN_ALL = 0;
    /** フィルター区分 いずれかの条件に一致 */
    public static final int WML140_FILTKBN_ANY = 1;

    /** フィルタリング実行 実行しない */
    public static final int WML140_DOFILTER_NO = 0;
    /** フィルタリング実行 実行する */
    public static final int WML140_DOFILTER_YES = 1;

    /** メール一覧 ソートキー 日時 */
    public static final int WML140_SKEY_DATE = 0;
    /** メール一覧 ソートキー 差出人 */
    public static final int WML140_SKEY_FROM = 1;
    /** メール一覧 ソートキー 件名 */
    public static final int WML140_SKEY_SUBJECT = 2;
    /** メール一覧 並び順 昇順 */
    public static final int WML140_ORDER_ASC = 0;
    /** メール一覧 並び順 降順 */
    public static final int WML140_ORDER_DESC = 1;

    /** メール転送フラグ 転送禁止 */
    public static final int FWLIMIT_PROHIBITED = 1;

    /** ラベルリスト */
    private List<LabelValueBean> lbList__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList1__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList2__ = null;

    /** フィルター名 */
    private String wml140FilterName__ = null;
    /** フィルター区分 */
    private int wml140filterType__ = WML140_FILKBN_ALL;
    /** 初期表示区分 */
    private int wml140initFlg__ = 0;
    /** 選択 ラベル */
    private String wml140actionLabelValue__ = null;

    /** 条件１ 区分 */
    private String wml140condition1__ = null;
    /** 条件１ 種別 */
    private String wml140conditionType1__ = null;
    /** 条件１ 式 */
    private String wml140conditionExs1__ = null;
    /** 条件１ 条件 */
    private String wml140conditionText1__ = null;
    /** 条件２ 区分 */
    private String wml140condition2__ = null;
    /** 条件２ 種別 */
    private String wml140conditionType2__ = null;
    /** 条件２ 式 */
    private String wml140conditionExs2__ = null;
    /** 条件２ 条件 */
    private String wml140conditionText2__ = null;
    /** 条件３ 区分 */
    private String wml140condition3__ = null;
    /** 条件３ 種別 */
    private String wml140conditionType3__ = null;
    /** 条件３ 式 */
    private String wml140conditionExs3__ = null;
    /** 条件３ 条件 */
    private String wml140conditionText3__ = null;
    /** 条件４ 区分 */
    private String wml140condition4__ = null;
    /** 条件４ 種別 */
    private String wml140conditionType4__ = null;
    /** 条件４ 式 */
    private String wml140conditionExs4__ = null;
    /** 条件４ 条件 */
    private String wml140conditionText4__ = null;
    /** 条件５ 区分 */
    private String wml140condition5__ = null;
    /** 条件５ 種別 */
    private String wml140conditionType5__ = null;
    /** 条件５ 式 */
    private String wml140conditionExs5__ = null;
    /** 条件５ 条件 */
    private String wml140conditionText5__ = null;
    /** 添付ファイル有無 */
    private String wml140tempFile__ = "0";

    /** 動作 ラベルを付ける */
    private String wml140actionLabel__ = "0";
    /** 動作 既読にする */
    private String wml140actionRead__ = "0";
    /** 動作 フォルダに移動する */
    private String wml140actionMove__ = "0";
    /** 選択 フォルダ */
    private String wml140actionFolderValue__ = String.valueOf(Wml140Form.WML140_MOVEFOLDER_DO);
    /** アドレス 転送先 判定*/
    private String wml140actionSend__ = "0";
    /** アドレス 転送先 */
    private String[] wml140actionSendValue__ = null;
    /** メール転送許可フラグ */
    private int wml140fwLimitFlg__ = 0;
    /** フィルタリング実行 */
    private int wml140doFilter__ = WML140_DOFILTER_NO;

    /** メール一覧表示フラグ */
    private int wml140viewMailList__ = 0;
    /** メール一覧 ソートキー */
    private int wml140mailListSortKey__ = WML140_SKEY_DATE;
    /** メール一覧 並び順 */
    private int wml140mailListOrder__ = WML140_ORDER_ASC;
    /** フィルター区分(保持) */
    private int wml140svFilterType__ = WML140_FILKBN_ALL;
    /** 条件１ 区分(保持) */
    private String wml140svCondition1__ = null;
    /** 条件１ 種別(保持) */
    private String wml140svConditionType1__ = null;
    /** 条件１ 式(保持) */
    private String wml140svConditionExs1__ = null;
    /** 条件１ 条件(保持) */
    private String wml140svConditionText1__ = null;
    /** 条件２ 区分(保持) */
    private String wml140svCondition2__ = null;
    /** 条件２ 種別(保持) */
    private String wml140svConditionType2__ = null;
    /** 条件２ 式(保持) */
    private String wml140svConditionExs2__ = null;
    /** 条件２ 条件(保持) */
    private String wml140svConditionText2__ = null;
    /** 条件３ 区分(保持) */
    private String wml140svCondition3__ = null;
    /** 条件３ 種別(保持) */
    private String wml140svConditionType3__ = null;
    /** 条件３ 式(保持) */
    private String wml140svConditionExs3__ = null;
    /** 条件３ 条件(保持) */
    private String wml140svConditionText3__ = null;
    /** 条件４ 区分(保持) */
    private String wml140svCondition4__ = null;
    /** 条件４ 種別(保持) */
    private String wml140svConditionType4__ = null;
    /** 条件４ 式(保持) */
    private String wml140svConditionExs4__ = null;
    /** 条件４ 条件(保持) */
    private String wml140svConditionText4__ = null;
    /** 条件５ 区分(保持) */
    private String wml140svCondition5__ = null;
    /** 条件５ 種別(保持) */
    private String wml140svConditionType5__ = null;
    /** 条件５ 式(保持) */
    private String wml140svConditionExs5__ = null;
    /** 条件５ 条件(保持) */
    private String wml140svConditionText5__ = null;
    /** 添付ファイル有無(保持) */
    private String wml140svTempFile__ = "0";

    /** メール一覧 ページ(上段) */
    private int wml140mailListPageTop__ = 0;
    /** メール一覧 ページ(下段) */
    private int wml140mailListPageBottom__ = 0;
    /** メール一覧 */
    private List<Wml140MailModel> wml140mailList__ = null;
    /** メール一覧 ページコンボ */
    private List<LabelValueBean> wml140mailListPageCombo__ = null;

    /** アドレス 転送先 削除対象インデックス */
    private int wml140actionSendValueDelIdx__ = -1;

    /**
     * <p>lbList を取得します。
     * @return lbList
     */
    public List<LabelValueBean> getLbList() {
        return lbList__;
    }

    /**
     * <p>lbList をセットします。
     * @param lbList lbList
     */
    public void setLbList(List<LabelValueBean> lbList) {
        lbList__ = lbList;
    }

    /**
     * <p>wml140initFlg を取得します。
     * @return wml140initFlg
     */
    public int getWml140initFlg() {
        return wml140initFlg__;
    }

    /**
     * <p>wml140initFlg をセットします。
     * @param wml140initFlg wml140initFlg
     */
    public void setWml140initFlg(int wml140initFlg) {
        wml140initFlg__ = wml140initFlg;
    }

    /**
     * <p>wml140FilterName を取得します。
     * @return wml140FilterName
     */
    public String getWml140FilterName() {
        return wml140FilterName__;
    }

    /**
     * <p>wml140FilterName をセットします。
     * @param wml140FilterName wml140FilterName
     */
    public void setWml140FilterName(String wml140FilterName) {
        wml140FilterName__ = wml140FilterName;
    }

    /**
     * <p>conditionList1 を取得します。
     * @return conditionList1
     */
    public List<LabelValueBean> getConditionList1() {
        return conditionList1__;
    }

    /**
     * <p>conditionList1 をセットします。
     * @param conditionList1 conditionList1
     */
    public void setConditionList1(List<LabelValueBean> conditionList1) {
        conditionList1__ = conditionList1;
    }

    /**
     * <p>conditionList2 を取得します。
     * @return conditionList2
     */
    public List<LabelValueBean> getConditionList2() {
        return conditionList2__;
    }

    /**
     * <p>conditionList2 をセットします。
     * @param conditionList2 conditionList2
     */
    public void setConditionList2(List<LabelValueBean> conditionList2) {
        conditionList2__ = conditionList2;
    }

    /**
     * <p>wml140condition1 を取得します。
     * @return wml140condition1
     */
    public String getWml140condition1() {
        return wml140condition1__;
    }

    /**
     * <p>wml140condition1 をセットします。
     * @param wml140condition1 wml140condition1
     */
    public void setWml140condition1(String wml140condition1) {
        wml140condition1__ = wml140condition1;
    }

    /**
     * <p>wml140condition2 を取得します。
     * @return wml140condition2
     */
    public String getWml140condition2() {
        return wml140condition2__;
    }

    /**
     * <p>wml140condition2 をセットします。
     * @param wml140condition2 wml140condition2
     */
    public void setWml140condition2(String wml140condition2) {
        wml140condition2__ = wml140condition2;
    }

    /**
     * <p>wml140condition3 を取得します。
     * @return wml140condition3
     */
    public String getWml140condition3() {
        return wml140condition3__;
    }

    /**
     * <p>wml140condition3 をセットします。
     * @param wml140condition3 wml140condition3
     */
    public void setWml140condition3(String wml140condition3) {
        wml140condition3__ = wml140condition3;
    }

    /**
     * <p>wml140condition4 を取得します。
     * @return wml140condition4
     */
    public String getWml140condition4() {
        return wml140condition4__;
    }

    /**
     * <p>wml140condition4 をセットします。
     * @param wml140condition4 wml140condition4
     */
    public void setWml140condition4(String wml140condition4) {
        wml140condition4__ = wml140condition4;
    }

    /**
     * <p>wml140condition5 を取得します。
     * @return wml140condition5
     */
    public String getWml140condition5() {
        return wml140condition5__;
    }

    /**
     * <p>wml140condition5 をセットします。
     * @param wml140condition5 wml140condition5
     */
    public void setWml140condition5(String wml140condition5) {
        wml140condition5__ = wml140condition5;
    }

    /**
     * <p>wml140conditionExs1 を取得します。
     * @return wml140conditionExs1
     */
    public String getWml140conditionExs1() {
        return wml140conditionExs1__;
    }

    /**
     * <p>wml140conditionExs1 をセットします。
     * @param wml140conditionExs1 wml140conditionExs1
     */
    public void setWml140conditionExs1(String wml140conditionExs1) {
        wml140conditionExs1__ = wml140conditionExs1;
    }

    /**
     * <p>wml140conditionExs2 を取得します。
     * @return wml140conditionExs2
     */
    public String getWml140conditionExs2() {
        return wml140conditionExs2__;
    }

    /**
     * <p>wml140conditionExs2 をセットします。
     * @param wml140conditionExs2 wml140conditionExs2
     */
    public void setWml140conditionExs2(String wml140conditionExs2) {
        wml140conditionExs2__ = wml140conditionExs2;
    }

    /**
     * <p>wml140conditionExs3 を取得します。
     * @return wml140conditionExs3
     */
    public String getWml140conditionExs3() {
        return wml140conditionExs3__;
    }

    /**
     * <p>wml140conditionExs3 をセットします。
     * @param wml140conditionExs3 wml140conditionExs3
     */
    public void setWml140conditionExs3(String wml140conditionExs3) {
        wml140conditionExs3__ = wml140conditionExs3;
    }

    /**
     * <p>wml140conditionExs4 を取得します。
     * @return wml140conditionExs4
     */
    public String getWml140conditionExs4() {
        return wml140conditionExs4__;
    }

    /**
     * <p>wml140conditionExs4 をセットします。
     * @param wml140conditionExs4 wml140conditionExs4
     */
    public void setWml140conditionExs4(String wml140conditionExs4) {
        wml140conditionExs4__ = wml140conditionExs4;
    }

    /**
     * <p>wml140conditionExs5 を取得します。
     * @return wml140conditionExs5
     */
    public String getWml140conditionExs5() {
        return wml140conditionExs5__;
    }

    /**
     * <p>wml140conditionExs5 をセットします。
     * @param wml140conditionExs5 wml140conditionExs5
     */
    public void setWml140conditionExs5(String wml140conditionExs5) {
        wml140conditionExs5__ = wml140conditionExs5;
    }

    /**
     * <p>wml140conditionText1 を取得します。
     * @return wml140conditionText1
     */
    public String getWml140conditionText1() {
        return wml140conditionText1__;
    }

    /**
     * <p>wml140conditionText1 をセットします。
     * @param wml140conditionText1 wml140conditionText1
     */
    public void setWml140conditionText1(String wml140conditionText1) {
        wml140conditionText1__ = wml140conditionText1;
    }

    /**
     * <p>wml140conditionText2 を取得します。
     * @return wml140conditionText2
     */
    public String getWml140conditionText2() {
        return wml140conditionText2__;
    }

    /**
     * <p>wml140conditionText2 をセットします。
     * @param wml140conditionText2 wml140conditionText2
     */
    public void setWml140conditionText2(String wml140conditionText2) {
        wml140conditionText2__ = wml140conditionText2;
    }

    /**
     * <p>wml140conditionText3 を取得します。
     * @return wml140conditionText3
     */
    public String getWml140conditionText3() {
        return wml140conditionText3__;
    }

    /**
     * <p>wml140conditionText3 をセットします。
     * @param wml140conditionText3 wml140conditionText3
     */
    public void setWml140conditionText3(String wml140conditionText3) {
        wml140conditionText3__ = wml140conditionText3;
    }

    /**
     * <p>wml140conditionText4 を取得します。
     * @return wml140conditionText4
     */
    public String getWml140conditionText4() {
        return wml140conditionText4__;
    }

    /**
     * <p>wml140conditionText4 をセットします。
     * @param wml140conditionText4 wml140conditionText4
     */
    public void setWml140conditionText4(String wml140conditionText4) {
        wml140conditionText4__ = wml140conditionText4;
    }

    /**
     * <p>wml140conditionText5 を取得します。
     * @return wml140conditionText5
     */
    public String getWml140conditionText5() {
        return wml140conditionText5__;
    }

    /**
     * <p>wml140conditionText5 をセットします。
     * @param wml140conditionText5 wml140conditionText5
     */
    public void setWml140conditionText5(String wml140conditionText5) {
        wml140conditionText5__ = wml140conditionText5;
    }

    /**
     * <p>wml140conditionType1 を取得します。
     * @return wml140conditionType1
     */
    public String getWml140conditionType1() {
        return wml140conditionType1__;
    }

    /**
     * <p>wml140conditionType1 をセットします。
     * @param wml140conditionType1 wml140conditionType1
     */
    public void setWml140conditionType1(String wml140conditionType1) {
        wml140conditionType1__ = wml140conditionType1;
    }

    /**
     * <p>wml140conditionType2 を取得します。
     * @return wml140conditionType2
     */
    public String getWml140conditionType2() {
        return wml140conditionType2__;
    }

    /**
     * <p>wml140conditionType2 をセットします。
     * @param wml140conditionType2 wml140conditionType2
     */
    public void setWml140conditionType2(String wml140conditionType2) {
        wml140conditionType2__ = wml140conditionType2;
    }

    /**
     * <p>wml140conditionType3 を取得します。
     * @return wml140conditionType3
     */
    public String getWml140conditionType3() {
        return wml140conditionType3__;
    }

    /**
     * <p>wml140conditionType3 をセットします。
     * @param wml140conditionType3 wml140conditionType3
     */
    public void setWml140conditionType3(String wml140conditionType3) {
        wml140conditionType3__ = wml140conditionType3;
    }

    /**
     * <p>wml140conditionType4 を取得します。
     * @return wml140conditionType4
     */
    public String getWml140conditionType4() {
        return wml140conditionType4__;
    }

    /**
     * <p>wml140conditionType4 をセットします。
     * @param wml140conditionType4 wml140conditionType4
     */
    public void setWml140conditionType4(String wml140conditionType4) {
        wml140conditionType4__ = wml140conditionType4;
    }

    /**
     * <p>wml140conditionType5 を取得します。
     * @return wml140conditionType5
     */
    public String getWml140conditionType5() {
        return wml140conditionType5__;
    }

    /**
     * <p>wml140conditionType5 をセットします。
     * @param wml140conditionType5 wml140conditionType5
     */
    public void setWml140conditionType5(String wml140conditionType5) {
        wml140conditionType5__ = wml140conditionType5;
    }

    /**
     * <p>wml140actionSend を取得します。
     * @return wml140actionSend
     */
    public String getWml140actionSend() {
        return wml140actionSend__;
    }

    /**
     * <p>wml140actionSend をセットします。
     * @param wml140actionSend wml140actionSend
     */
    public void setWml140actionSend(String wml140actionSend) {
        wml140actionSend__ = wml140actionSend;
    }

    /**
     * <p>wml140fwLimitFlg を取得します。
     * @return wml140fwLimitFlg
     */
    public int getWml140fwLimitFlg() {
        return wml140fwLimitFlg__;
    }

    /**
     * <p>wml140fwLimitFlg をセットします。
     * @param wml140fwLimitFlg wml140fwLimitFlg
     */
    public void setWml140fwLimitFlg(int wml140fwLimitFlg) {
        wml140fwLimitFlg__ = wml140fwLimitFlg;
    }

    /**
     * <p>wml140actionSendValue を取得します。
     * @return wml140actionSendValue
     */
    public String[] getWml140actionSendValue() {
        return wml140actionSendValue__;
    }

    /**
     * <p>wml140actionSendValue をセットします。
     * @param wml140actionSendValue wml140actionSendValue
     */
    public void setWml140actionSendValue(String[] wml140actionSendValue) {
        wml140actionSendValue__ = wml140actionSendValue;
    }

    /**
     * <p>wml140tempFile を取得します。
     * @return wml140tempFile
     */
    public String getWml140tempFile() {
        return wml140tempFile__;
    }

    /**
     * <p>wml140tempFile をセットします。
     * @param wml140tempFile wml140tempFile
     */
    public void setWml140tempFile(String wml140tempFile) {
        wml140tempFile__ = wml140tempFile;
    }

    /**
     * <p>wml140actionMove を取得します。
     * @return wml140actionMove
     */
    public String getWml140actionMove() {
        return wml140actionMove__;
    }

    /**
     * <p>wml140actionMove をセットします。
     * @param wml140actionMove wml140actionMove
     */
    public void setWml140actionMove(String wml140actionMove) {
        wml140actionMove__ = wml140actionMove;
    }

    /**
     * <p>wml140actionFolderValue を取得します。
     * @return wml140actionFolderValue
     * @see jp.groupsession.v2.wml.wml140.Wml140ParamModel#wml140actionFolderValue__
     */
    public String getWml140actionFolderValue() {
        return wml140actionFolderValue__;
    }

    /**
     * <p>wml140actionFolderValue をセットします。
     * @param wml140actionFolderValue wml140actionFolderValue
     * @see jp.groupsession.v2.wml.wml140.Wml140ParamModel#wml140actionFolderValue__
     */
    public void setWml140actionFolderValue(String wml140actionFolderValue) {
        wml140actionFolderValue__ = wml140actionFolderValue;
    }

    /**
     * <p>wml140actionLabel を取得します。
     * @return wml140actionLabel
     */
    public String getWml140actionLabel() {
        return wml140actionLabel__;
    }

    /**
     * <p>wml140actionLabel をセットします。
     * @param wml140actionLabel wml140actionLabel
     */
    public void setWml140actionLabel(String wml140actionLabel) {
        wml140actionLabel__ = wml140actionLabel;
    }

    /**
     * <p>wml140actionLabelValue を取得します。
     * @return wml140actionLabelValue
     */
    public String getWml140actionLabelValue() {
        return wml140actionLabelValue__;
    }

    /**
     * <p>wml140actionLabelValue をセットします。
     * @param wml140actionLabelValue wml140actionLabelValue
     */
    public void setWml140actionLabelValue(String wml140actionLabelValue) {
        wml140actionLabelValue__ = wml140actionLabelValue;
    }

    /**
     * <p>wml140actionRead を取得します。
     * @return wml140actionRead
     */
    public String getWml140actionRead() {
        return wml140actionRead__;
    }

    /**
     * <p>wml140actionRead をセットします。
     * @param wml140actionRead wml140actionRead
     */
    public void setWml140actionRead(String wml140actionRead) {
        wml140actionRead__ = wml140actionRead;
    }

    /**
     * <p>wml140filterType を取得します。
     * @return wml140filterType
     */
    public int getWml140filterType() {
        return wml140filterType__;
    }

    /**
     * <p>wml140filterType をセットします。
     * @param wml140filterType wml140filterType
     */
    public void setWml140filterType(int wml140filterType) {
        wml140filterType__ = wml140filterType;
    }

    /**
     * @return wml140svCondition1
     */
    public String getWml140svCondition1() {
        return wml140svCondition1__;
    }

    /**
     * @param wml140svCondition1 設定する wml140svCondition1
     */
    public void setWml140svCondition1(String wml140svCondition1) {
        wml140svCondition1__ = wml140svCondition1;
    }

    /**
     * @return wml140svCondition2
     */
    public String getWml140svCondition2() {
        return wml140svCondition2__;
    }

    /**
     * @param wml140svCondition2 設定する wml140svCondition2
     */
    public void setWml140svCondition2(String wml140svCondition2) {
        wml140svCondition2__ = wml140svCondition2;
    }

    /**
     * @return wml140svCondition3
     */
    public String getWml140svCondition3() {
        return wml140svCondition3__;
    }

    /**
     * @param wml140svCondition3 設定する wml140svCondition3
     */
    public void setWml140svCondition3(String wml140svCondition3) {
        wml140svCondition3__ = wml140svCondition3;
    }

    /**
     * @return wml140svCondition4
     */
    public String getWml140svCondition4() {
        return wml140svCondition4__;
    }

    /**
     * @param wml140svCondition4 設定する wml140svCondition4
     */
    public void setWml140svCondition4(String wml140svCondition4) {
        wml140svCondition4__ = wml140svCondition4;
    }

    /**
     * @return wml140svCondition5
     */
    public String getWml140svCondition5() {
        return wml140svCondition5__;
    }

    /**
     * @param wml140svCondition5 設定する wml140svCondition5
     */
    public void setWml140svCondition5(String wml140svCondition5) {
        wml140svCondition5__ = wml140svCondition5;
    }

    /**
     * @return wml140svConditionExs1
     */
    public String getWml140svConditionExs1() {
        return wml140svConditionExs1__;
    }

    /**
     * @param wml140svConditionExs1 設定する wml140svConditionExs1
     */
    public void setWml140svConditionExs1(String wml140svConditionExs1) {
        wml140svConditionExs1__ = wml140svConditionExs1;
    }

    /**
     * @return wml140svConditionExs2
     */
    public String getWml140svConditionExs2() {
        return wml140svConditionExs2__;
    }

    /**
     * @param wml140svConditionExs2 設定する wml140svConditionExs2
     */
    public void setWml140svConditionExs2(String wml140svConditionExs2) {
        wml140svConditionExs2__ = wml140svConditionExs2;
    }

    /**
     * @return wml140svConditionExs3
     */
    public String getWml140svConditionExs3() {
        return wml140svConditionExs3__;
    }

    /**
     * @param wml140svConditionExs3 設定する wml140svConditionExs3
     */
    public void setWml140svConditionExs3(String wml140svConditionExs3) {
        wml140svConditionExs3__ = wml140svConditionExs3;
    }

    /**
     * @return wml140svConditionExs4
     */
    public String getWml140svConditionExs4() {
        return wml140svConditionExs4__;
    }

    /**
     * @param wml140svConditionExs4 設定する wml140svConditionExs4
     */
    public void setWml140svConditionExs4(String wml140svConditionExs4) {
        wml140svConditionExs4__ = wml140svConditionExs4;
    }

    /**
     * @return wml140svConditionExs5
     */
    public String getWml140svConditionExs5() {
        return wml140svConditionExs5__;
    }

    /**
     * @param wml140svConditionExs5 設定する wml140svConditionExs5
     */
    public void setWml140svConditionExs5(String wml140svConditionExs5) {
        wml140svConditionExs5__ = wml140svConditionExs5;
    }

    /**
     * @return wml140svConditionText1
     */
    public String getWml140svConditionText1() {
        return wml140svConditionText1__;
    }

    /**
     * @param wml140svConditionText1 設定する wml140svConditionText1
     */
    public void setWml140svConditionText1(String wml140svConditionText1) {
        wml140svConditionText1__ = wml140svConditionText1;
    }

    /**
     * @return wml140svConditionText2
     */
    public String getWml140svConditionText2() {
        return wml140svConditionText2__;
    }

    /**
     * @param wml140svConditionText2 設定する wml140svConditionText2
     */
    public void setWml140svConditionText2(String wml140svConditionText2) {
        wml140svConditionText2__ = wml140svConditionText2;
    }

    /**
     * @return wml140svConditionText3
     */
    public String getWml140svConditionText3() {
        return wml140svConditionText3__;
    }

    /**
     * @param wml140svConditionText3 設定する wml140svConditionText3
     */
    public void setWml140svConditionText3(String wml140svConditionText3) {
        wml140svConditionText3__ = wml140svConditionText3;
    }

    /**
     * @return wml140svConditionText4
     */
    public String getWml140svConditionText4() {
        return wml140svConditionText4__;
    }

    /**
     * @param wml140svConditionText4 設定する wml140svConditionText4
     */
    public void setWml140svConditionText4(String wml140svConditionText4) {
        wml140svConditionText4__ = wml140svConditionText4;
    }

    /**
     * @return wml140svConditionText5
     */
    public String getWml140svConditionText5() {
        return wml140svConditionText5__;
    }

    /**
     * @param wml140svConditionText5 設定する wml140svConditionText5
     */
    public void setWml140svConditionText5(String wml140svConditionText5) {
        wml140svConditionText5__ = wml140svConditionText5;
    }

    /**
     * @return wml140svConditionType1
     */
    public String getWml140svConditionType1() {
        return wml140svConditionType1__;
    }

    /**
     * @param wml140svConditionType1 設定する wml140svConditionType1
     */
    public void setWml140svConditionType1(String wml140svConditionType1) {
        wml140svConditionType1__ = wml140svConditionType1;
    }

    /**
     * @return wml140svConditionType2
     */
    public String getWml140svConditionType2() {
        return wml140svConditionType2__;
    }

    /**
     * @param wml140svConditionType2 設定する wml140svConditionType2
     */
    public void setWml140svConditionType2(String wml140svConditionType2) {
        wml140svConditionType2__ = wml140svConditionType2;
    }

    /**
     * @return wml140svConditionType3
     */
    public String getWml140svConditionType3() {
        return wml140svConditionType3__;
    }

    /**
     * @param wml140svConditionType3 設定する wml140svConditionType3
     */
    public void setWml140svConditionType3(String wml140svConditionType3) {
        wml140svConditionType3__ = wml140svConditionType3;
    }

    /**
     * @return wml140svConditionType4
     */
    public String getWml140svConditionType4() {
        return wml140svConditionType4__;
    }

    /**
     * @param wml140svConditionType4 設定する wml140svConditionType4
     */
    public void setWml140svConditionType4(String wml140svConditionType4) {
        wml140svConditionType4__ = wml140svConditionType4;
    }

    /**
     * @return wml140svConditionType5
     */
    public String getWml140svConditionType5() {
        return wml140svConditionType5__;
    }

    /**
     * @param wml140svConditionType5 設定する wml140svConditionType5
     */
    public void setWml140svConditionType5(String wml140svConditionType5) {
        wml140svConditionType5__ = wml140svConditionType5;
    }

    /**
     * @return wml140svTempFile
     */
    public String getWml140svTempFile() {
        return wml140svTempFile__;
    }

    /**
     * @param wml140svTempFile 設定する wml140svTempFile
     */
    public void setWml140svTempFile(String wml140svTempFile) {
        wml140svTempFile__ = wml140svTempFile;
    }

    /**
     * @return wml140viewMailList
     */
    public int getWml140viewMailList() {
        return wml140viewMailList__;
    }

    /**
     * @param wml140viewMailList 設定する wml140viewMailList
     */
    public void setWml140viewMailList(int wml140viewMailList) {
        wml140viewMailList__ = wml140viewMailList;
    }

    /**
     * @return wml140svFilterType
     */
    public int getWml140svFilterType() {
        return wml140svFilterType__;
    }

    /**
     * @param wml140svFilterType 設定する wml140svFilterType
     */
    public void setWml140svFilterType(int wml140svFilterType) {
        wml140svFilterType__ = wml140svFilterType;
    }

    /**
     * @return wml140mailList
     */
    public List<Wml140MailModel> getWml140mailList() {
        return wml140mailList__;
    }

    /**
     * @param wml140mailList 設定する wml140mailList
     */
    public void setWml140mailList(List<Wml140MailModel> wml140mailList) {
        wml140mailList__ = wml140mailList;
    }

    /**
     * @return wml140mailListPageBottom
     */
    public int getWml140mailListPageBottom() {
        return wml140mailListPageBottom__;
    }

    /**
     * @param wml140mailListPageBottom 設定する wml140mailListPageBottom
     */
    public void setWml140mailListPageBottom(int wml140mailListPageBottom) {
        wml140mailListPageBottom__ = wml140mailListPageBottom;
    }

    /**
     * @return wml140doFilter
     */
    public int getWml140doFilter() {
        return wml140doFilter__;
    }

    /**
     * @param wml140doFilter 設定する wml140doFilter
     */
    public void setWml140doFilter(int wml140doFilter) {
        wml140doFilter__ = wml140doFilter;
    }

    /**
     * @return wml140mailListPageCombo
     */
    public List<LabelValueBean> getWml140mailListPageCombo() {
        return wml140mailListPageCombo__;
    }

    /**
     * @param wml140mailListPageCombo 設定する wml140mailListPageCombo
     */
    public void setWml140mailListPageCombo(
            List<LabelValueBean> wml140mailListPageCombo) {
        wml140mailListPageCombo__ = wml140mailListPageCombo;
    }

    /**
     * @return wml140mailListPageTop
     */
    public int getWml140mailListPageTop() {
        return wml140mailListPageTop__;
    }

    /**
     * @param wml140mailListPageTop 設定する wml140mailListPageTop
     */
    public void setWml140mailListPageTop(int wml140mailListPageTop) {
        wml140mailListPageTop__ = wml140mailListPageTop;
    }

    /**
     * <p>wml140mailListOrder を取得します。
     * @return wml140mailListOrder
     */
    public int getWml140mailListOrder() {
        return wml140mailListOrder__;
    }

    /**
     * <p>wml140mailListOrder をセットします。
     * @param wml140mailListOrder wml140mailListOrder
     */
    public void setWml140mailListOrder(int wml140mailListOrder) {
        wml140mailListOrder__ = wml140mailListOrder;
    }

    /**
     * <p>wml140mailListSortKey を取得します。
     * @return wml140mailListSortKey
     */
    public int getWml140mailListSortKey() {
        return wml140mailListSortKey__;
    }

    /**
     * <p>wml140mailListSortKey をセットします。
     * @param wml140mailListSortKey wml140mailListSortKey
     */
    public void setWml140mailListSortKey(int wml140mailListSortKey) {
        wml140mailListSortKey__ = wml140mailListSortKey;
    }

    /**
     * <p>wml140actionSendValueDelIdx を取得します。
     * @return wml140actionSendValueDelIdx
     */
    public int getWml140actionSendValueDelIdx() {
        return wml140actionSendValueDelIdx__;
    }

    /**
     * <p>wml140actionSendValueDelIdx をセットします。
     * @param wml140actionSendValueDelIdx wml140actionSendValueDelIdx
     */
    public void setWml140actionSendValueDelIdx(int wml140actionSendValueDelIdx) {
        wml140actionSendValueDelIdx__ = wml140actionSendValueDelIdx;
    }
}