package jp.groupsession.v2.wml.wml230;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.wml.wml140.Wml140Form;
import jp.groupsession.v2.wml.wml220.Wml220ParamModel;

/**
 * <br>[機  能] WEBメール 管理者設定 フィルタ登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml230ParamModel extends Wml220ParamModel {
    /** フィルター区分 全ての条件に一致 */
    public static final int WML230_FILKBN_ALL = 0;
    /** フィルター区分 いずれかの条件に一致 */
    public static final int WML230_FILTKBN_ANY = 1;

    /** フィルタリング実行 実行しない */
    public static final int WML230_DOFILTER_NO = 0;
    /** フィルタリング実行 実行する */
    public static final int WML230_DOFILTER_YES = 1;

    /** メール一覧 ソートキー 日時 */
    public static final int WML230_SKEY_DATE = 0;
    /** メール一覧 ソートキー 差出人 */
    public static final int WML230_SKEY_FROM = 1;
    /** メール一覧 ソートキー 件名 */
    public static final int WML230_SKEY_SUBJECT = 2;
    /** メール一覧 並び順 昇順 */
    public static final int WML230_ORDER_ASC = 0;
    /** メール一覧 並び順 降順 */
    public static final int WML230_ORDER_DESC = 1;

    /** ラベルリスト */
    private List<LabelValueBean> lbList__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList1__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList2__ = null;

    /** フィルター名 */
    private String wml230FilterName__ = null;
    /** フィルター区分 */
    private int wml230filterType__ = WML230_FILKBN_ALL;
    /** 初期表示区分 */
    private int wml230initFlg__ = 0;
    /** 選択 ラベル */
    private String wml230actionLabelValue__ = null;

    /** 条件１ 区分 */
    private String wml230condition1__ = null;
    /** 条件１ 種別 */
    private String wml230conditionType1__ = null;
    /** 条件１ 式 */
    private String wml230conditionExs1__ = null;
    /** 条件１ 条件 */
    private String wml230conditionText1__ = null;
    /** 条件２ 区分 */
    private String wml230condition2__ = null;
    /** 条件２ 種別 */
    private String wml230conditionType2__ = null;
    /** 条件２ 式 */
    private String wml230conditionExs2__ = null;
    /** 条件２ 条件 */
    private String wml230conditionText2__ = null;
    /** 条件３ 区分 */
    private String wml230condition3__ = null;
    /** 条件３ 種別 */
    private String wml230conditionType3__ = null;
    /** 条件３ 式 */
    private String wml230conditionExs3__ = null;
    /** 条件３ 条件 */
    private String wml230conditionText3__ = null;
    /** 条件４ 区分 */
    private String wml230condition4__ = null;
    /** 条件４ 種別 */
    private String wml230conditionType4__ = null;
    /** 条件４ 式 */
    private String wml230conditionExs4__ = null;
    /** 条件４ 条件 */
    private String wml230conditionText4__ = null;
    /** 条件５ 区分 */
    private String wml230condition5__ = null;
    /** 条件５ 種別 */
    private String wml230conditionType5__ = null;
    /** 条件５ 式 */
    private String wml230conditionExs5__ = null;
    /** 条件５ 条件 */
    private String wml230conditionText5__ = null;
    /** 添付ファイル有無 */
    private String wml230tempFile__ = "0";

    /** 動作 ラベルを付ける */
    private String wml230actionLabel__ = "0";
    /** 動作 既読にする */
    private String wml230actionRead__ = "0";
    /** 動作 フォルダに移動する */
    private String wml230actionMove__ = "0";
    /** 選択 フォルダ */
    private String wml230actionFolderValue__ = String.valueOf(Wml140Form.WML140_MOVEFOLDER_DO);
    /** アドレス 転送先 判定*/
    private String wml230actionSend__ = "0";
    /** アドレス 転送先 */
    private String[] wml230actionSendValue__ = null;
    /** フィルタリング実行 */
    private int wml230doFilter__ = WML230_DOFILTER_NO;

    /** メール一覧表示フラグ */
    private int wml230viewMailList__ = 0;
    /** メール一覧 ソートキー */
    private int wml230mailListSortKey__ = WML230_SKEY_DATE;
    /** メール一覧 並び順 */
    private int wml230mailListOrder__ = WML230_ORDER_ASC;
    /** フィルター区分(保持) */
    private int wml230svFilterType__ = WML230_FILKBN_ALL;
    /** 条件１ 区分(保持) */
    private String wml230svCondition1__ = null;
    /** 条件１ 種別(保持) */
    private String wml230svConditionType1__ = null;
    /** 条件１ 式(保持) */
    private String wml230svConditionExs1__ = null;
    /** 条件１ 条件(保持) */
    private String wml230svConditionText1__ = null;
    /** 条件２ 区分(保持) */
    private String wml230svCondition2__ = null;
    /** 条件２ 種別(保持) */
    private String wml230svConditionType2__ = null;
    /** 条件２ 式(保持) */
    private String wml230svConditionExs2__ = null;
    /** 条件２ 条件(保持) */
    private String wml230svConditionText2__ = null;
    /** 条件３ 区分(保持) */
    private String wml230svCondition3__ = null;
    /** 条件３ 種別(保持) */
    private String wml230svConditionType3__ = null;
    /** 条件３ 式(保持) */
    private String wml230svConditionExs3__ = null;
    /** 条件３ 条件(保持) */
    private String wml230svConditionText3__ = null;
    /** 条件４ 区分(保持) */
    private String wml230svCondition4__ = null;
    /** 条件４ 種別(保持) */
    private String wml230svConditionType4__ = null;
    /** 条件４ 式(保持) */
    private String wml230svConditionExs4__ = null;
    /** 条件４ 条件(保持) */
    private String wml230svConditionText4__ = null;
    /** 条件５ 区分(保持) */
    private String wml230svCondition5__ = null;
    /** 条件５ 種別(保持) */
    private String wml230svConditionType5__ = null;
    /** 条件５ 式(保持) */
    private String wml230svConditionExs5__ = null;
    /** 条件５ 条件(保持) */
    private String wml230svConditionText5__ = null;
    /** 添付ファイル有無(保持) */
    private String wml230svTempFile__ = "0";

    /** メール一覧 ページ(上段) */
    private int wml230mailListPageTop__ = 0;
    /** メール一覧 ページ(下段) */
    private int wml230mailListPageBottom__ = 0;
    /** メール一覧 */
    private List<Wml230MailModel> wml230mailList__ = null;
    /** メール一覧 ページコンボ */
    private List<LabelValueBean> wml230mailListPageCombo__ = null;

    /** アドレス 転送先 削除対象インデックス */
    private int wml230actionSendValueDelIdx__ = -1;

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
     * <p>wml230initFlg を取得します。
     * @return wml230initFlg
     */
    public int getWml230initFlg() {
        return wml230initFlg__;
    }

    /**
     * <p>wml230initFlg をセットします。
     * @param wml230initFlg wml230initFlg
     */
    public void setWml230initFlg(int wml230initFlg) {
        wml230initFlg__ = wml230initFlg;
    }

    /**
     * <p>wml230FilterName を取得します。
     * @return wml230FilterName
     */
    public String getWml230FilterName() {
        return wml230FilterName__;
    }

    /**
     * <p>wml230FilterName をセットします。
     * @param wml230FilterName wml230FilterName
     */
    public void setWml230FilterName(String wml230FilterName) {
        wml230FilterName__ = wml230FilterName;
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
     * <p>wml230condition1 を取得します。
     * @return wml230condition1
     */
    public String getWml230condition1() {
        return wml230condition1__;
    }

    /**
     * <p>wml230condition1 をセットします。
     * @param wml230condition1 wml230condition1
     */
    public void setWml230condition1(String wml230condition1) {
        wml230condition1__ = wml230condition1;
    }

    /**
     * <p>wml230condition2 を取得します。
     * @return wml230condition2
     */
    public String getWml230condition2() {
        return wml230condition2__;
    }

    /**
     * <p>wml230condition2 をセットします。
     * @param wml230condition2 wml230condition2
     */
    public void setWml230condition2(String wml230condition2) {
        wml230condition2__ = wml230condition2;
    }

    /**
     * <p>wml230condition3 を取得します。
     * @return wml230condition3
     */
    public String getWml230condition3() {
        return wml230condition3__;
    }

    /**
     * <p>wml230condition3 をセットします。
     * @param wml230condition3 wml230condition3
     */
    public void setWml230condition3(String wml230condition3) {
        wml230condition3__ = wml230condition3;
    }

    /**
     * <p>wml230condition4 を取得します。
     * @return wml230condition4
     */
    public String getWml230condition4() {
        return wml230condition4__;
    }

    /**
     * <p>wml230condition4 をセットします。
     * @param wml230condition4 wml230condition4
     */
    public void setWml230condition4(String wml230condition4) {
        wml230condition4__ = wml230condition4;
    }

    /**
     * <p>wml230condition5 を取得します。
     * @return wml230condition5
     */
    public String getWml230condition5() {
        return wml230condition5__;
    }

    /**
     * <p>wml230condition5 をセットします。
     * @param wml230condition5 wml230condition5
     */
    public void setWml230condition5(String wml230condition5) {
        wml230condition5__ = wml230condition5;
    }

    /**
     * <p>wml230conditionExs1 を取得します。
     * @return wml230conditionExs1
     */
    public String getWml230conditionExs1() {
        return wml230conditionExs1__;
    }

    /**
     * <p>wml230conditionExs1 をセットします。
     * @param wml230conditionExs1 wml230conditionExs1
     */
    public void setWml230conditionExs1(String wml230conditionExs1) {
        wml230conditionExs1__ = wml230conditionExs1;
    }

    /**
     * <p>wml230conditionExs2 を取得します。
     * @return wml230conditionExs2
     */
    public String getWml230conditionExs2() {
        return wml230conditionExs2__;
    }

    /**
     * <p>wml230conditionExs2 をセットします。
     * @param wml230conditionExs2 wml230conditionExs2
     */
    public void setWml230conditionExs2(String wml230conditionExs2) {
        wml230conditionExs2__ = wml230conditionExs2;
    }

    /**
     * <p>wml230conditionExs3 を取得します。
     * @return wml230conditionExs3
     */
    public String getWml230conditionExs3() {
        return wml230conditionExs3__;
    }

    /**
     * <p>wml230conditionExs3 をセットします。
     * @param wml230conditionExs3 wml230conditionExs3
     */
    public void setWml230conditionExs3(String wml230conditionExs3) {
        wml230conditionExs3__ = wml230conditionExs3;
    }

    /**
     * <p>wml230conditionExs4 を取得します。
     * @return wml230conditionExs4
     */
    public String getWml230conditionExs4() {
        return wml230conditionExs4__;
    }

    /**
     * <p>wml230conditionExs4 をセットします。
     * @param wml230conditionExs4 wml230conditionExs4
     */
    public void setWml230conditionExs4(String wml230conditionExs4) {
        wml230conditionExs4__ = wml230conditionExs4;
    }

    /**
     * <p>wml230conditionExs5 を取得します。
     * @return wml230conditionExs5
     */
    public String getWml230conditionExs5() {
        return wml230conditionExs5__;
    }

    /**
     * <p>wml230conditionExs5 をセットします。
     * @param wml230conditionExs5 wml230conditionExs5
     */
    public void setWml230conditionExs5(String wml230conditionExs5) {
        wml230conditionExs5__ = wml230conditionExs5;
    }

    /**
     * <p>wml230conditionText1 を取得します。
     * @return wml230conditionText1
     */
    public String getWml230conditionText1() {
        return wml230conditionText1__;
    }

    /**
     * <p>wml230conditionText1 をセットします。
     * @param wml230conditionText1 wml230conditionText1
     */
    public void setWml230conditionText1(String wml230conditionText1) {
        wml230conditionText1__ = wml230conditionText1;
    }

    /**
     * <p>wml230conditionText2 を取得します。
     * @return wml230conditionText2
     */
    public String getWml230conditionText2() {
        return wml230conditionText2__;
    }

    /**
     * <p>wml230conditionText2 をセットします。
     * @param wml230conditionText2 wml230conditionText2
     */
    public void setWml230conditionText2(String wml230conditionText2) {
        wml230conditionText2__ = wml230conditionText2;
    }

    /**
     * <p>wml230conditionText3 を取得します。
     * @return wml230conditionText3
     */
    public String getWml230conditionText3() {
        return wml230conditionText3__;
    }

    /**
     * <p>wml230conditionText3 をセットします。
     * @param wml230conditionText3 wml230conditionText3
     */
    public void setWml230conditionText3(String wml230conditionText3) {
        wml230conditionText3__ = wml230conditionText3;
    }

    /**
     * <p>wml230conditionText4 を取得します。
     * @return wml230conditionText4
     */
    public String getWml230conditionText4() {
        return wml230conditionText4__;
    }

    /**
     * <p>wml230conditionText4 をセットします。
     * @param wml230conditionText4 wml230conditionText4
     */
    public void setWml230conditionText4(String wml230conditionText4) {
        wml230conditionText4__ = wml230conditionText4;
    }

    /**
     * <p>wml230conditionText5 を取得します。
     * @return wml230conditionText5
     */
    public String getWml230conditionText5() {
        return wml230conditionText5__;
    }

    /**
     * <p>wml230conditionText5 をセットします。
     * @param wml230conditionText5 wml230conditionText5
     */
    public void setWml230conditionText5(String wml230conditionText5) {
        wml230conditionText5__ = wml230conditionText5;
    }

    /**
     * <p>wml230conditionType1 を取得します。
     * @return wml230conditionType1
     */
    public String getWml230conditionType1() {
        return wml230conditionType1__;
    }

    /**
     * <p>wml230conditionType1 をセットします。
     * @param wml230conditionType1 wml230conditionType1
     */
    public void setWml230conditionType1(String wml230conditionType1) {
        wml230conditionType1__ = wml230conditionType1;
    }

    /**
     * <p>wml230conditionType2 を取得します。
     * @return wml230conditionType2
     */
    public String getWml230conditionType2() {
        return wml230conditionType2__;
    }

    /**
     * <p>wml230conditionType2 をセットします。
     * @param wml230conditionType2 wml230conditionType2
     */
    public void setWml230conditionType2(String wml230conditionType2) {
        wml230conditionType2__ = wml230conditionType2;
    }

    /**
     * <p>wml230conditionType3 を取得します。
     * @return wml230conditionType3
     */
    public String getWml230conditionType3() {
        return wml230conditionType3__;
    }

    /**
     * <p>wml230conditionType3 をセットします。
     * @param wml230conditionType3 wml230conditionType3
     */
    public void setWml230conditionType3(String wml230conditionType3) {
        wml230conditionType3__ = wml230conditionType3;
    }

    /**
     * <p>wml230conditionType4 を取得します。
     * @return wml230conditionType4
     */
    public String getWml230conditionType4() {
        return wml230conditionType4__;
    }

    /**
     * <p>wml230conditionType4 をセットします。
     * @param wml230conditionType4 wml230conditionType4
     */
    public void setWml230conditionType4(String wml230conditionType4) {
        wml230conditionType4__ = wml230conditionType4;
    }

    /**
     * <p>wml230conditionType5 を取得します。
     * @return wml230conditionType5
     */
    public String getWml230conditionType5() {
        return wml230conditionType5__;
    }

    /**
     * <p>wml230conditionType5 をセットします。
     * @param wml230conditionType5 wml230conditionType5
     */
    public void setWml230conditionType5(String wml230conditionType5) {
        wml230conditionType5__ = wml230conditionType5;
    }

    /**
     * <p>wml230actionSend を取得します。
     * @return wml230actionSend
     */
    public String getWml230actionSend() {
        return wml230actionSend__;
    }

    /**
     * <p>wml230actionSend をセットします。
     * @param wml230actionSend wml230actionSend
     */
    public void setWml230actionSend(String wml230actionSend) {
        wml230actionSend__ = wml230actionSend;
    }

    /**
     * <p>wml230actionSendValue を取得します。
     * @return wml230actionSendValue
     */
    public String[] getWml230actionSendValue() {
        return wml230actionSendValue__;
    }

    /**
     * <p>wml230actionSendValue をセットします。
     * @param wml230actionSendValue wml230actionSendValue
     */
    public void setWml230actionSendValue(String[] wml230actionSendValue) {
        wml230actionSendValue__ = wml230actionSendValue;
    }

    /**
     * <p>wml230tempFile を取得します。
     * @return wml230tempFile
     */
    public String getWml230tempFile() {
        return wml230tempFile__;
    }

    /**
     * <p>wml230tempFile をセットします。
     * @param wml230tempFile wml230tempFile
     */
    public void setWml230tempFile(String wml230tempFile) {
        wml230tempFile__ = wml230tempFile;
    }

    /**
     * <p>wml230actionMove を取得します。
     * @return wml230actionMove
     */
    public String getWml230actionMove() {
        return wml230actionMove__;
    }

    /**
     * <p>wml230actionMove をセットします。
     * @param wml230actionMove wml230actionMove
     */
    public void setWml230actionMove(String wml230actionMove) {
        wml230actionMove__ = wml230actionMove;
    }

    /**
     * <p>wml230actionFolderValue を取得します。
     * @return wml230actionFolderValue
     * @see jp.groupsession.v2.wml.wml230.Wml230ParamModel#wml230actionFolderValue__
     */
    public String getWml230actionFolderValue() {
        return wml230actionFolderValue__;
    }

    /**
     * <p>wml230actionFolderValue をセットします。
     * @param wml230actionFolderValue wml230actionFolderValue
     * @see jp.groupsession.v2.wml.wml230.Wml230ParamModel#wml230actionFolderValue__
     */
    public void setWml230actionFolderValue(String wml230actionFolderValue) {
        wml230actionFolderValue__ = wml230actionFolderValue;
    }

    /**
     * <p>wml230actionLabel を取得します。
     * @return wml230actionLabel
     */
    public String getWml230actionLabel() {
        return wml230actionLabel__;
    }

    /**
     * <p>wml230actionLabel をセットします。
     * @param wml230actionLabel wml230actionLabel
     */
    public void setWml230actionLabel(String wml230actionLabel) {
        wml230actionLabel__ = wml230actionLabel;
    }

    /**
     * <p>wml230actionLabelValue を取得します。
     * @return wml230actionLabelValue
     */
    public String getWml230actionLabelValue() {
        return wml230actionLabelValue__;
    }

    /**
     * <p>wml230actionLabelValue をセットします。
     * @param wml230actionLabelValue wml230actionLabelValue
     */
    public void setWml230actionLabelValue(String wml230actionLabelValue) {
        wml230actionLabelValue__ = wml230actionLabelValue;
    }

    /**
     * <p>wml230actionRead を取得します。
     * @return wml230actionRead
     */
    public String getWml230actionRead() {
        return wml230actionRead__;
    }

    /**
     * <p>wml230actionRead をセットします。
     * @param wml230actionRead wml230actionRead
     */
    public void setWml230actionRead(String wml230actionRead) {
        wml230actionRead__ = wml230actionRead;
    }

    /**
     * <p>wml230filterType を取得します。
     * @return wml230filterType
     */
    public int getWml230filterType() {
        return wml230filterType__;
    }

    /**
     * <p>wml230filterType をセットします。
     * @param wml230filterType wml230filterType
     */
    public void setWml230filterType(int wml230filterType) {
        wml230filterType__ = wml230filterType;
    }

    /**
     * @return wml230svCondition1
     */
    public String getWml230svCondition1() {
        return wml230svCondition1__;
    }

    /**
     * @param wml230svCondition1 設定する wml230svCondition1
     */
    public void setWml230svCondition1(String wml230svCondition1) {
        wml230svCondition1__ = wml230svCondition1;
    }

    /**
     * @return wml230svCondition2
     */
    public String getWml230svCondition2() {
        return wml230svCondition2__;
    }

    /**
     * @param wml230svCondition2 設定する wml230svCondition2
     */
    public void setWml230svCondition2(String wml230svCondition2) {
        wml230svCondition2__ = wml230svCondition2;
    }

    /**
     * @return wml230svCondition3
     */
    public String getWml230svCondition3() {
        return wml230svCondition3__;
    }

    /**
     * @param wml230svCondition3 設定する wml230svCondition3
     */
    public void setWml230svCondition3(String wml230svCondition3) {
        wml230svCondition3__ = wml230svCondition3;
    }

    /**
     * @return wml230svCondition4
     */
    public String getWml230svCondition4() {
        return wml230svCondition4__;
    }

    /**
     * @param wml230svCondition4 設定する wml230svCondition4
     */
    public void setWml230svCondition4(String wml230svCondition4) {
        wml230svCondition4__ = wml230svCondition4;
    }

    /**
     * @return wml230svCondition5
     */
    public String getWml230svCondition5() {
        return wml230svCondition5__;
    }

    /**
     * @param wml230svCondition5 設定する wml230svCondition5
     */
    public void setWml230svCondition5(String wml230svCondition5) {
        wml230svCondition5__ = wml230svCondition5;
    }

    /**
     * @return wml230svConditionExs1
     */
    public String getWml230svConditionExs1() {
        return wml230svConditionExs1__;
    }

    /**
     * @param wml230svConditionExs1 設定する wml230svConditionExs1
     */
    public void setWml230svConditionExs1(String wml230svConditionExs1) {
        wml230svConditionExs1__ = wml230svConditionExs1;
    }

    /**
     * @return wml230svConditionExs2
     */
    public String getWml230svConditionExs2() {
        return wml230svConditionExs2__;
    }

    /**
     * @param wml230svConditionExs2 設定する wml230svConditionExs2
     */
    public void setWml230svConditionExs2(String wml230svConditionExs2) {
        wml230svConditionExs2__ = wml230svConditionExs2;
    }

    /**
     * @return wml230svConditionExs3
     */
    public String getWml230svConditionExs3() {
        return wml230svConditionExs3__;
    }

    /**
     * @param wml230svConditionExs3 設定する wml230svConditionExs3
     */
    public void setWml230svConditionExs3(String wml230svConditionExs3) {
        wml230svConditionExs3__ = wml230svConditionExs3;
    }

    /**
     * @return wml230svConditionExs4
     */
    public String getWml230svConditionExs4() {
        return wml230svConditionExs4__;
    }

    /**
     * @param wml230svConditionExs4 設定する wml230svConditionExs4
     */
    public void setWml230svConditionExs4(String wml230svConditionExs4) {
        wml230svConditionExs4__ = wml230svConditionExs4;
    }

    /**
     * @return wml230svConditionExs5
     */
    public String getWml230svConditionExs5() {
        return wml230svConditionExs5__;
    }

    /**
     * @param wml230svConditionExs5 設定する wml230svConditionExs5
     */
    public void setWml230svConditionExs5(String wml230svConditionExs5) {
        wml230svConditionExs5__ = wml230svConditionExs5;
    }

    /**
     * @return wml230svConditionText1
     */
    public String getWml230svConditionText1() {
        return wml230svConditionText1__;
    }

    /**
     * @param wml230svConditionText1 設定する wml230svConditionText1
     */
    public void setWml230svConditionText1(String wml230svConditionText1) {
        wml230svConditionText1__ = wml230svConditionText1;
    }

    /**
     * @return wml230svConditionText2
     */
    public String getWml230svConditionText2() {
        return wml230svConditionText2__;
    }

    /**
     * @param wml230svConditionText2 設定する wml230svConditionText2
     */
    public void setWml230svConditionText2(String wml230svConditionText2) {
        wml230svConditionText2__ = wml230svConditionText2;
    }

    /**
     * @return wml230svConditionText3
     */
    public String getWml230svConditionText3() {
        return wml230svConditionText3__;
    }

    /**
     * @param wml230svConditionText3 設定する wml230svConditionText3
     */
    public void setWml230svConditionText3(String wml230svConditionText3) {
        wml230svConditionText3__ = wml230svConditionText3;
    }

    /**
     * @return wml230svConditionText4
     */
    public String getWml230svConditionText4() {
        return wml230svConditionText4__;
    }

    /**
     * @param wml230svConditionText4 設定する wml230svConditionText4
     */
    public void setWml230svConditionText4(String wml230svConditionText4) {
        wml230svConditionText4__ = wml230svConditionText4;
    }

    /**
     * @return wml230svConditionText5
     */
    public String getWml230svConditionText5() {
        return wml230svConditionText5__;
    }

    /**
     * @param wml230svConditionText5 設定する wml230svConditionText5
     */
    public void setWml230svConditionText5(String wml230svConditionText5) {
        wml230svConditionText5__ = wml230svConditionText5;
    }

    /**
     * @return wml230svConditionType1
     */
    public String getWml230svConditionType1() {
        return wml230svConditionType1__;
    }

    /**
     * @param wml230svConditionType1 設定する wml230svConditionType1
     */
    public void setWml230svConditionType1(String wml230svConditionType1) {
        wml230svConditionType1__ = wml230svConditionType1;
    }

    /**
     * @return wml230svConditionType2
     */
    public String getWml230svConditionType2() {
        return wml230svConditionType2__;
    }

    /**
     * @param wml230svConditionType2 設定する wml230svConditionType2
     */
    public void setWml230svConditionType2(String wml230svConditionType2) {
        wml230svConditionType2__ = wml230svConditionType2;
    }

    /**
     * @return wml230svConditionType3
     */
    public String getWml230svConditionType3() {
        return wml230svConditionType3__;
    }

    /**
     * @param wml230svConditionType3 設定する wml230svConditionType3
     */
    public void setWml230svConditionType3(String wml230svConditionType3) {
        wml230svConditionType3__ = wml230svConditionType3;
    }

    /**
     * @return wml230svConditionType4
     */
    public String getWml230svConditionType4() {
        return wml230svConditionType4__;
    }

    /**
     * @param wml230svConditionType4 設定する wml230svConditionType4
     */
    public void setWml230svConditionType4(String wml230svConditionType4) {
        wml230svConditionType4__ = wml230svConditionType4;
    }

    /**
     * @return wml230svConditionType5
     */
    public String getWml230svConditionType5() {
        return wml230svConditionType5__;
    }

    /**
     * @param wml230svConditionType5 設定する wml230svConditionType5
     */
    public void setWml230svConditionType5(String wml230svConditionType5) {
        wml230svConditionType5__ = wml230svConditionType5;
    }

    /**
     * @return wml230svTempFile
     */
    public String getWml230svTempFile() {
        return wml230svTempFile__;
    }

    /**
     * @param wml230svTempFile 設定する wml230svTempFile
     */
    public void setWml230svTempFile(String wml230svTempFile) {
        wml230svTempFile__ = wml230svTempFile;
    }

    /**
     * @return wml230viewMailList
     */
    public int getWml230viewMailList() {
        return wml230viewMailList__;
    }

    /**
     * @param wml230viewMailList 設定する wml230viewMailList
     */
    public void setWml230viewMailList(int wml230viewMailList) {
        wml230viewMailList__ = wml230viewMailList;
    }

    /**
     * @return wml230svFilterType
     */
    public int getWml230svFilterType() {
        return wml230svFilterType__;
    }

    /**
     * @param wml230svFilterType 設定する wml230svFilterType
     */
    public void setWml230svFilterType(int wml230svFilterType) {
        wml230svFilterType__ = wml230svFilterType;
    }

    /**
     * @return wml230mailList
     */
    public List<Wml230MailModel> getWml230mailList() {
        return wml230mailList__;
    }

    /**
     * @param wml230mailList 設定する wml230mailList
     */
    public void setWml230mailList(List<Wml230MailModel> wml230mailList) {
        wml230mailList__ = wml230mailList;
    }

    /**
     * @return wml230mailListPageBottom
     */
    public int getWml230mailListPageBottom() {
        return wml230mailListPageBottom__;
    }

    /**
     * @param wml230mailListPageBottom 設定する wml230mailListPageBottom
     */
    public void setWml230mailListPageBottom(int wml230mailListPageBottom) {
        wml230mailListPageBottom__ = wml230mailListPageBottom;
    }

    /**
     * @return wml230doFilter
     */
    public int getWml230doFilter() {
        return wml230doFilter__;
    }

    /**
     * @param wml230doFilter 設定する wml230doFilter
     */
    public void setWml230doFilter(int wml230doFilter) {
        wml230doFilter__ = wml230doFilter;
    }

    /**
     * @return wml230mailListPageCombo
     */
    public List<LabelValueBean> getWml230mailListPageCombo() {
        return wml230mailListPageCombo__;
    }

    /**
     * @param wml230mailListPageCombo 設定する wml230mailListPageCombo
     */
    public void setWml230mailListPageCombo(
            List<LabelValueBean> wml230mailListPageCombo) {
        wml230mailListPageCombo__ = wml230mailListPageCombo;
    }

    /**
     * @return wml230mailListPageTop
     */
    public int getWml230mailListPageTop() {
        return wml230mailListPageTop__;
    }

    /**
     * @param wml230mailListPageTop 設定する wml230mailListPageTop
     */
    public void setWml230mailListPageTop(int wml230mailListPageTop) {
        wml230mailListPageTop__ = wml230mailListPageTop;
    }

    /**
     * <p>wml230mailListOrder を取得します。
     * @return wml230mailListOrder
     */
    public int getWml230mailListOrder() {
        return wml230mailListOrder__;
    }

    /**
     * <p>wml230mailListOrder をセットします。
     * @param wml230mailListOrder wml230mailListOrder
     */
    public void setWml230mailListOrder(int wml230mailListOrder) {
        wml230mailListOrder__ = wml230mailListOrder;
    }

    /**
     * <p>wml230mailListSortKey を取得します。
     * @return wml230mailListSortKey
     */
    public int getWml230mailListSortKey() {
        return wml230mailListSortKey__;
    }

    /**
     * <p>wml230mailListSortKey をセットします。
     * @param wml230mailListSortKey wml230mailListSortKey
     */
    public void setWml230mailListSortKey(int wml230mailListSortKey) {
        wml230mailListSortKey__ = wml230mailListSortKey;
    }

    /**
     * <p>wml230actionSendValueDelIdx を取得します。
     * @return wml230actionSendValueDelIdx
     */
    public int getWml230actionSendValueDelIdx() {
        return wml230actionSendValueDelIdx__;
    }

    /**
     * <p>wml230actionSendValueDelIdx をセットします。
     * @param wml230actionSendValueDelIdx wml230actionSendValueDelIdx
     */
    public void setWml230actionSendValueDelIdx(int wml230actionSendValueDelIdx) {
        wml230actionSendValueDelIdx__ = wml230actionSendValueDelIdx;
    }
}