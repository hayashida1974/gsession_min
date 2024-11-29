package jp.groupsession.v2.sml.sml360;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.sml340.Sml340MailModel;
import jp.groupsession.v2.sml.sml350.Sml350Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 個人設定 フィルタ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml360Form extends Sml350Form {

    /** フィルター区分 全ての条件に一致 */
    public static final int SML360_FILKBN_ALL = 0;
    /** フィルター区分 いずれかの条件に一致 */
    public static final int SML360_FILTKBN_ANY = 1;

    /** フィルタリング実行 実行しない */
    public static final int SML360_DOFILTER_NO = 0;
    /** フィルタリング実行 実行する */
    public static final int SML360_DOFILTER_YES = 1;

    /** メール一覧 ソートキー 日時 */
    public static final int SML360_SKEY_DATE = 0;
    /** メール一覧 ソートキー 差出人 */
    public static final int SML360_SKEY_FROM = 1;
    /** メール一覧 ソートキー 件名 */
    public static final int SML360_SKEY_SUBJECT = 2;
    /** メール一覧 並び順 昇順 */
    public static final int SML360_ORDER_ASC = 0;
    /** メール一覧 並び順 降順 */
    public static final int SML360_ORDER_DESC = 1;

    /** ラベルリスト */
    private List<LabelValueBean> lbList__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList1__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList2__ = null;

    /** フィルター名 */
    private String sml360FilterName__ = null;
    /** フィルター区分 */
    private int sml360filterType__ = SML360_FILKBN_ALL;
    /** 初期表示区分 */
    private int sml360initFlg__ = 0;
    /** 選択 ラベル */
    private String sml360actionLabelValue__ = null;

    /** 条件１ 区分 */
    private String sml360condition1__ = null;
    /** 条件１ 種別 */
    private String sml360conditionType1__ = null;
    /** 条件１ 式 */
    private String sml360conditionExs1__ = null;
    /** 条件１ 条件 */
    private String sml360conditionText1__ = null;
    /** 条件２ 区分 */
    private String sml360condition2__ = null;
    /** 条件２ 種別 */
    private String sml360conditionType2__ = null;
    /** 条件２ 式 */
    private String sml360conditionExs2__ = null;
    /** 条件２ 条件 */
    private String sml360conditionText2__ = null;
    /** 条件３ 区分 */
    private String sml360condition3__ = null;
    /** 条件３ 種別 */
    private String sml360conditionType3__ = null;
    /** 条件３ 式 */
    private String sml360conditionExs3__ = null;
    /** 条件３ 条件 */
    private String sml360conditionText3__ = null;
    /** 条件４ 区分 */
    private String sml360condition4__ = null;
    /** 条件４ 種別 */
    private String sml360conditionType4__ = null;
    /** 条件４ 式 */
    private String sml360conditionExs4__ = null;
    /** 条件４ 条件 */
    private String sml360conditionText4__ = null;
    /** 条件５ 区分 */
    private String sml360condition5__ = null;
    /** 条件５ 種別 */
    private String sml360conditionType5__ = null;
    /** 条件５ 式 */
    private String sml360conditionExs5__ = null;
    /** 条件５ 条件 */
    private String sml360conditionText5__ = null;
    /** 添付ファイル有無 */
    private String sml360tempFile__ = "0";

    /** 動作 ラベルを付ける */
    private String sml360actionLabel__ = "0";
    /** 動作 既読にする */
    private String sml360actionRead__ = "0";
    /** 動作 ゴミ箱に移動する */
    private String sml360actionDust__ = "0";
    /** フィルタリング実行 */
    private int sml360doFilter__ = SML360_DOFILTER_NO;

    /** メール一覧表示フラグ */
    private int sml360viewMailList__ = 0;
    /** メール一覧 ソートキー */
    private int sml360mailListSortKey__ = SML360_SKEY_DATE;
    /** メール一覧 並び順 */
    private int sml360mailListOrder__ = SML360_ORDER_ASC;
    /** フィルター区分(保持) */
    private int sml360svFilterType__ = SML360_FILKBN_ALL;
    /** 条件１ 区分(保持) */
    private String sml360svCondition1__ = null;
    /** 条件１ 種別(保持) */
    private String sml360svConditionType1__ = null;
    /** 条件１ 式(保持) */
    private String sml360svConditionExs1__ = null;
    /** 条件１ 条件(保持) */
    private String sml360svConditionText1__ = null;
    /** 条件２ 区分(保持) */
    private String sml360svCondition2__ = null;
    /** 条件２ 種別(保持) */
    private String sml360svConditionType2__ = null;
    /** 条件２ 式(保持) */
    private String sml360svConditionExs2__ = null;
    /** 条件２ 条件(保持) */
    private String sml360svConditionText2__ = null;
    /** 条件３ 区分(保持) */
    private String sml360svCondition3__ = null;
    /** 条件３ 種別(保持) */
    private String sml360svConditionType3__ = null;
    /** 条件３ 式(保持) */
    private String sml360svConditionExs3__ = null;
    /** 条件３ 条件(保持) */
    private String sml360svConditionText3__ = null;
    /** 条件４ 区分(保持) */
    private String sml360svCondition4__ = null;
    /** 条件４ 種別(保持) */
    private String sml360svConditionType4__ = null;
    /** 条件４ 式(保持) */
    private String sml360svConditionExs4__ = null;
    /** 条件４ 条件(保持) */
    private String sml360svConditionText4__ = null;
    /** 条件５ 区分(保持) */
    private String sml360svCondition5__ = null;
    /** 条件５ 種別(保持) */
    private String sml360svConditionType5__ = null;
    /** 条件５ 式(保持) */
    private String sml360svConditionExs5__ = null;
    /** 条件５ 条件(保持) */
    private String sml360svConditionText5__ = null;
    /** 添付ファイル有無(保持) */
    private String sml360svTempFile__ = "0";

    /** メール一覧 ページ(上段) */
    private int sml360mailListPageTop__ = 0;
    /** メール一覧 ページ(下段) */
    private int sml360mailListPageBottom__ = 0;
    /** メール一覧 */
    private List<Sml340MailModel> sml360mailList__ = null;
    /** メール一覧 ページコンボ */
    private List<LabelValueBean> sml360mailListPageCombo__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) throws Exception {
        ActionErrors errors = new ActionErrors();

        //フィルター名入力チェック
        GSValidateSmail.validateTextBoxInput(errors, sml360FilterName__,
                "sml360FilterName",
                getInterMessage(req, GSConstSmail.TEXT_FILTER),
                GSConstSmail.MAXLEN_SEARCH_KEYWORD, true);

        //条件未選択
        if (sml360condition1__ == null
            && sml360condition2__ == null
            && sml360condition3__ == null
            && sml360condition4__ == null
            && sml360condition5__ == null
            && sml360tempFile__.equals("0")) {
            String msgKey = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgKey,
                                        getInterMessage(req, GSConstSmail.TEXT_CONDITION));
            StrutsUtil.addMessage(
                    errors, msg, "wml230condition." + msgKey);

        }

        //条件チェック
        validateCondition(errors, req);

        //ラベル選択チェック
        if (sml360actionLabel__.equals("1")
                && sml360actionLabelValue__.equals("-1")) {
            String msgKey = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgKey,
                    getInterMessage(req, GSConstSmail.TEXT_SEL_LABEL));
            StrutsUtil.addMessage(
                    errors, msg, "wml230selLabel." + msgKey);
        }
        return errors;
    }


    /**
     * <br>[機  能] 条件1～5の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param req リクエスト
     */
    public void validateCondition(ActionErrors errors, HttpServletRequest req) {

        int errSize = errors.size();

        //条件チェック
        errSize = conditionCheck(sml360condition1__, sml360conditionText1__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION1));
        errSize = conditionCheck(sml360condition2__, sml360conditionText2__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION2));
        errSize = conditionCheck(sml360condition3__, sml360conditionText3__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION3));
        errSize = conditionCheck(sml360condition4__, sml360conditionText4__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION4));
        errSize = conditionCheck(sml360condition5__, sml360conditionText5__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION5));
    }

    /**
     * <br>[機  能] 条件の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param condition 条件No
     * @param conditionText 条件テキスト
     * @param errSize エラーサイズ
     * @param errors ActionErrors
     * @param textCondition テキスト条件
     * @return エラー数
     */
    public int conditionCheck(String condition,
                               String conditionText,
                               int errSize,
                               ActionErrors errors,
                               String textCondition) {

        //条件
        if (condition != null) {

            GSValidateSmail.validateTextBoxInput(errors, conditionText,
                    "conditionText" + condition,
                    textCondition,
                    GSConstSmail.MAXLEN_CONDITION_KEYWORD, true);
        }
        errSize = errors.size();
        return errSize;
    }


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
     * <p>sml360FilterName を取得します。
     * @return sml360FilterName
     */
    public String getSml360FilterName() {
        return sml360FilterName__;
    }


    /**
     * <p>sml360FilterName をセットします。
     * @param sml360FilterName sml360FilterName
     */
    public void setSml360FilterName(String sml360FilterName) {
        sml360FilterName__ = sml360FilterName;
    }


    /**
     * <p>sml360filterType を取得します。
     * @return sml360filterType
     */
    public int getSml360filterType() {
        return sml360filterType__;
    }


    /**
     * <p>sml360filterType をセットします。
     * @param sml360filterType sml360filterType
     */
    public void setSml360filterType(int sml360filterType) {
        sml360filterType__ = sml360filterType;
    }


    /**
     * <p>sml360initFlg を取得します。
     * @return sml360initFlg
     */
    public int getSml360initFlg() {
        return sml360initFlg__;
    }


    /**
     * <p>sml360initFlg をセットします。
     * @param sml360initFlg sml360initFlg
     */
    public void setSml360initFlg(int sml360initFlg) {
        sml360initFlg__ = sml360initFlg;
    }


    /**
     * <p>sml360actionLabelValue を取得します。
     * @return sml360actionLabelValue
     */
    public String getSml360actionLabelValue() {
        return sml360actionLabelValue__;
    }


    /**
     * <p>sml360actionLabelValue をセットします。
     * @param sml360actionLabelValue sml360actionLabelValue
     */
    public void setSml360actionLabelValue(String sml360actionLabelValue) {
        sml360actionLabelValue__ = sml360actionLabelValue;
    }


    /**
     * <p>sml360condition1 を取得します。
     * @return sml360condition1
     */
    public String getSml360condition1() {
        return sml360condition1__;
    }


    /**
     * <p>sml360condition1 をセットします。
     * @param sml360condition1 sml360condition1
     */
    public void setSml360condition1(String sml360condition1) {
        sml360condition1__ = sml360condition1;
    }


    /**
     * <p>sml360conditionType1 を取得します。
     * @return sml360conditionType1
     */
    public String getSml360conditionType1() {
        return sml360conditionType1__;
    }


    /**
     * <p>sml360conditionType1 をセットします。
     * @param sml360conditionType1 sml360conditionType1
     */
    public void setSml360conditionType1(String sml360conditionType1) {
        sml360conditionType1__ = sml360conditionType1;
    }


    /**
     * <p>sml360conditionExs1 を取得します。
     * @return sml360conditionExs1
     */
    public String getSml360conditionExs1() {
        return sml360conditionExs1__;
    }


    /**
     * <p>sml360conditionExs1 をセットします。
     * @param sml360conditionExs1 sml360conditionExs1
     */
    public void setSml360conditionExs1(String sml360conditionExs1) {
        sml360conditionExs1__ = sml360conditionExs1;
    }


    /**
     * <p>sml360conditionText1 を取得します。
     * @return sml360conditionText1
     */
    public String getSml360conditionText1() {
        return sml360conditionText1__;
    }


    /**
     * <p>sml360conditionText1 をセットします。
     * @param sml360conditionText1 sml360conditionText1
     */
    public void setSml360conditionText1(String sml360conditionText1) {
        sml360conditionText1__ = sml360conditionText1;
    }


    /**
     * <p>sml360condition2 を取得します。
     * @return sml360condition2
     */
    public String getSml360condition2() {
        return sml360condition2__;
    }


    /**
     * <p>sml360condition2 をセットします。
     * @param sml360condition2 sml360condition2
     */
    public void setSml360condition2(String sml360condition2) {
        sml360condition2__ = sml360condition2;
    }


    /**
     * <p>sml360conditionType2 を取得します。
     * @return sml360conditionType2
     */
    public String getSml360conditionType2() {
        return sml360conditionType2__;
    }


    /**
     * <p>sml360conditionType2 をセットします。
     * @param sml360conditionType2 sml360conditionType2
     */
    public void setSml360conditionType2(String sml360conditionType2) {
        sml360conditionType2__ = sml360conditionType2;
    }


    /**
     * <p>sml360conditionExs2 を取得します。
     * @return sml360conditionExs2
     */
    public String getSml360conditionExs2() {
        return sml360conditionExs2__;
    }


    /**
     * <p>sml360conditionExs2 をセットします。
     * @param sml360conditionExs2 sml360conditionExs2
     */
    public void setSml360conditionExs2(String sml360conditionExs2) {
        sml360conditionExs2__ = sml360conditionExs2;
    }


    /**
     * <p>sml360conditionText2 を取得します。
     * @return sml360conditionText2
     */
    public String getSml360conditionText2() {
        return sml360conditionText2__;
    }


    /**
     * <p>sml360conditionText2 をセットします。
     * @param sml360conditionText2 sml360conditionText2
     */
    public void setSml360conditionText2(String sml360conditionText2) {
        sml360conditionText2__ = sml360conditionText2;
    }


    /**
     * <p>sml360condition3 を取得します。
     * @return sml360condition3
     */
    public String getSml360condition3() {
        return sml360condition3__;
    }


    /**
     * <p>sml360condition3 をセットします。
     * @param sml360condition3 sml360condition3
     */
    public void setSml360condition3(String sml360condition3) {
        sml360condition3__ = sml360condition3;
    }


    /**
     * <p>sml360conditionType3 を取得します。
     * @return sml360conditionType3
     */
    public String getSml360conditionType3() {
        return sml360conditionType3__;
    }


    /**
     * <p>sml360conditionType3 をセットします。
     * @param sml360conditionType3 sml360conditionType3
     */
    public void setSml360conditionType3(String sml360conditionType3) {
        sml360conditionType3__ = sml360conditionType3;
    }


    /**
     * <p>sml360conditionExs3 を取得します。
     * @return sml360conditionExs3
     */
    public String getSml360conditionExs3() {
        return sml360conditionExs3__;
    }


    /**
     * <p>sml360conditionExs3 をセットします。
     * @param sml360conditionExs3 sml360conditionExs3
     */
    public void setSml360conditionExs3(String sml360conditionExs3) {
        sml360conditionExs3__ = sml360conditionExs3;
    }


    /**
     * <p>sml360conditionText3 を取得します。
     * @return sml360conditionText3
     */
    public String getSml360conditionText3() {
        return sml360conditionText3__;
    }


    /**
     * <p>sml360conditionText3 をセットします。
     * @param sml360conditionText3 sml360conditionText3
     */
    public void setSml360conditionText3(String sml360conditionText3) {
        sml360conditionText3__ = sml360conditionText3;
    }


    /**
     * <p>sml360condition4 を取得します。
     * @return sml360condition4
     */
    public String getSml360condition4() {
        return sml360condition4__;
    }


    /**
     * <p>sml360condition4 をセットします。
     * @param sml360condition4 sml360condition4
     */
    public void setSml360condition4(String sml360condition4) {
        sml360condition4__ = sml360condition4;
    }


    /**
     * <p>sml360conditionType4 を取得します。
     * @return sml360conditionType4
     */
    public String getSml360conditionType4() {
        return sml360conditionType4__;
    }


    /**
     * <p>sml360conditionType4 をセットします。
     * @param sml360conditionType4 sml360conditionType4
     */
    public void setSml360conditionType4(String sml360conditionType4) {
        sml360conditionType4__ = sml360conditionType4;
    }


    /**
     * <p>sml360conditionExs4 を取得します。
     * @return sml360conditionExs4
     */
    public String getSml360conditionExs4() {
        return sml360conditionExs4__;
    }


    /**
     * <p>sml360conditionExs4 をセットします。
     * @param sml360conditionExs4 sml360conditionExs4
     */
    public void setSml360conditionExs4(String sml360conditionExs4) {
        sml360conditionExs4__ = sml360conditionExs4;
    }


    /**
     * <p>sml360conditionText4 を取得します。
     * @return sml360conditionText4
     */
    public String getSml360conditionText4() {
        return sml360conditionText4__;
    }


    /**
     * <p>sml360conditionText4 をセットします。
     * @param sml360conditionText4 sml360conditionText4
     */
    public void setSml360conditionText4(String sml360conditionText4) {
        sml360conditionText4__ = sml360conditionText4;
    }


    /**
     * <p>sml360condition5 を取得します。
     * @return sml360condition5
     */
    public String getSml360condition5() {
        return sml360condition5__;
    }


    /**
     * <p>sml360condition5 をセットします。
     * @param sml360condition5 sml360condition5
     */
    public void setSml360condition5(String sml360condition5) {
        sml360condition5__ = sml360condition5;
    }


    /**
     * <p>sml360conditionType5 を取得します。
     * @return sml360conditionType5
     */
    public String getSml360conditionType5() {
        return sml360conditionType5__;
    }


    /**
     * <p>sml360conditionType5 をセットします。
     * @param sml360conditionType5 sml360conditionType5
     */
    public void setSml360conditionType5(String sml360conditionType5) {
        sml360conditionType5__ = sml360conditionType5;
    }


    /**
     * <p>sml360conditionExs5 を取得します。
     * @return sml360conditionExs5
     */
    public String getSml360conditionExs5() {
        return sml360conditionExs5__;
    }


    /**
     * <p>sml360conditionExs5 をセットします。
     * @param sml360conditionExs5 sml360conditionExs5
     */
    public void setSml360conditionExs5(String sml360conditionExs5) {
        sml360conditionExs5__ = sml360conditionExs5;
    }


    /**
     * <p>sml360conditionText5 を取得します。
     * @return sml360conditionText5
     */
    public String getSml360conditionText5() {
        return sml360conditionText5__;
    }


    /**
     * <p>sml360conditionText5 をセットします。
     * @param sml360conditionText5 sml360conditionText5
     */
    public void setSml360conditionText5(String sml360conditionText5) {
        sml360conditionText5__ = sml360conditionText5;
    }


    /**
     * <p>sml360tempFile を取得します。
     * @return sml360tempFile
     */
    public String getSml360tempFile() {
        return sml360tempFile__;
    }


    /**
     * <p>sml360tempFile をセットします。
     * @param sml360tempFile sml360tempFile
     */
    public void setSml360tempFile(String sml360tempFile) {
        sml360tempFile__ = sml360tempFile;
    }


    /**
     * <p>sml360actionLabel を取得します。
     * @return sml360actionLabel
     */
    public String getSml360actionLabel() {
        return sml360actionLabel__;
    }


    /**
     * <p>sml360actionLabel をセットします。
     * @param sml360actionLabel sml360actionLabel
     */
    public void setSml360actionLabel(String sml360actionLabel) {
        sml360actionLabel__ = sml360actionLabel;
    }


    /**
     * <p>sml360actionRead を取得します。
     * @return sml360actionRead
     */
    public String getSml360actionRead() {
        return sml360actionRead__;
    }


    /**
     * <p>sml360actionRead をセットします。
     * @param sml360actionRead sml360actionRead
     */
    public void setSml360actionRead(String sml360actionRead) {
        sml360actionRead__ = sml360actionRead;
    }


    /**
     * <p>sml360actionDust を取得します。
     * @return sml360actionDust
     */
    public String getSml360actionDust() {
        return sml360actionDust__;
    }


    /**
     * <p>sml360actionDust をセットします。
     * @param sml360actionDust sml360actionDust
     */
    public void setSml360actionDust(String sml360actionDust) {
        sml360actionDust__ = sml360actionDust;
    }


    /**
     * <p>sml360doFilter を取得します。
     * @return sml360doFilter
     */
    public int getSml360doFilter() {
        return sml360doFilter__;
    }


    /**
     * <p>sml360doFilter をセットします。
     * @param sml360doFilter sml360doFilter
     */
    public void setSml360doFilter(int sml360doFilter) {
        sml360doFilter__ = sml360doFilter;
    }


    /**
     * <p>sml360viewMailList を取得します。
     * @return sml360viewMailList
     */
    public int getSml360viewMailList() {
        return sml360viewMailList__;
    }


    /**
     * <p>sml360viewMailList をセットします。
     * @param sml360viewMailList sml360viewMailList
     */
    public void setSml360viewMailList(int sml360viewMailList) {
        sml360viewMailList__ = sml360viewMailList;
    }


    /**
     * <p>sml360mailListSortKey を取得します。
     * @return sml360mailListSortKey
     */
    public int getSml360mailListSortKey() {
        return sml360mailListSortKey__;
    }


    /**
     * <p>sml360mailListSortKey をセットします。
     * @param sml360mailListSortKey sml360mailListSortKey
     */
    public void setSml360mailListSortKey(int sml360mailListSortKey) {
        sml360mailListSortKey__ = sml360mailListSortKey;
    }


    /**
     * <p>sml360mailListOrder を取得します。
     * @return sml360mailListOrder
     */
    public int getSml360mailListOrder() {
        return sml360mailListOrder__;
    }


    /**
     * <p>sml360mailListOrder をセットします。
     * @param sml360mailListOrder sml360mailListOrder
     */
    public void setSml360mailListOrder(int sml360mailListOrder) {
        sml360mailListOrder__ = sml360mailListOrder;
    }


    /**
     * <p>sml360svFilterType を取得します。
     * @return sml360svFilterType
     */
    public int getSml360svFilterType() {
        return sml360svFilterType__;
    }


    /**
     * <p>sml360svFilterType をセットします。
     * @param sml360svFilterType sml360svFilterType
     */
    public void setSml360svFilterType(int sml360svFilterType) {
        sml360svFilterType__ = sml360svFilterType;
    }


    /**
     * <p>sml360svCondition1 を取得します。
     * @return sml360svCondition1
     */
    public String getSml360svCondition1() {
        return sml360svCondition1__;
    }


    /**
     * <p>sml360svCondition1 をセットします。
     * @param sml360svCondition1 sml360svCondition1
     */
    public void setSml360svCondition1(String sml360svCondition1) {
        sml360svCondition1__ = sml360svCondition1;
    }


    /**
     * <p>sml360svConditionType1 を取得します。
     * @return sml360svConditionType1
     */
    public String getSml360svConditionType1() {
        return sml360svConditionType1__;
    }


    /**
     * <p>sml360svConditionType1 をセットします。
     * @param sml360svConditionType1 sml360svConditionType1
     */
    public void setSml360svConditionType1(String sml360svConditionType1) {
        sml360svConditionType1__ = sml360svConditionType1;
    }


    /**
     * <p>sml360svConditionExs1 を取得します。
     * @return sml360svConditionExs1
     */
    public String getSml360svConditionExs1() {
        return sml360svConditionExs1__;
    }


    /**
     * <p>sml360svConditionExs1 をセットします。
     * @param sml360svConditionExs1 sml360svConditionExs1
     */
    public void setSml360svConditionExs1(String sml360svConditionExs1) {
        sml360svConditionExs1__ = sml360svConditionExs1;
    }


    /**
     * <p>sml360svConditionText1 を取得します。
     * @return sml360svConditionText1
     */
    public String getSml360svConditionText1() {
        return sml360svConditionText1__;
    }


    /**
     * <p>sml360svConditionText1 をセットします。
     * @param sml360svConditionText1 sml360svConditionText1
     */
    public void setSml360svConditionText1(String sml360svConditionText1) {
        sml360svConditionText1__ = sml360svConditionText1;
    }


    /**
     * <p>sml360svCondition2 を取得します。
     * @return sml360svCondition2
     */
    public String getSml360svCondition2() {
        return sml360svCondition2__;
    }


    /**
     * <p>sml360svCondition2 をセットします。
     * @param sml360svCondition2 sml360svCondition2
     */
    public void setSml360svCondition2(String sml360svCondition2) {
        sml360svCondition2__ = sml360svCondition2;
    }


    /**
     * <p>sml360svConditionType2 を取得します。
     * @return sml360svConditionType2
     */
    public String getSml360svConditionType2() {
        return sml360svConditionType2__;
    }


    /**
     * <p>sml360svConditionType2 をセットします。
     * @param sml360svConditionType2 sml360svConditionType2
     */
    public void setSml360svConditionType2(String sml360svConditionType2) {
        sml360svConditionType2__ = sml360svConditionType2;
    }


    /**
     * <p>sml360svConditionExs2 を取得します。
     * @return sml360svConditionExs2
     */
    public String getSml360svConditionExs2() {
        return sml360svConditionExs2__;
    }


    /**
     * <p>sml360svConditionExs2 をセットします。
     * @param sml360svConditionExs2 sml360svConditionExs2
     */
    public void setSml360svConditionExs2(String sml360svConditionExs2) {
        sml360svConditionExs2__ = sml360svConditionExs2;
    }


    /**
     * <p>sml360svConditionText2 を取得します。
     * @return sml360svConditionText2
     */
    public String getSml360svConditionText2() {
        return sml360svConditionText2__;
    }


    /**
     * <p>sml360svConditionText2 をセットします。
     * @param sml360svConditionText2 sml360svConditionText2
     */
    public void setSml360svConditionText2(String sml360svConditionText2) {
        sml360svConditionText2__ = sml360svConditionText2;
    }


    /**
     * <p>sml360svCondition3 を取得します。
     * @return sml360svCondition3
     */
    public String getSml360svCondition3() {
        return sml360svCondition3__;
    }


    /**
     * <p>sml360svCondition3 をセットします。
     * @param sml360svCondition3 sml360svCondition3
     */
    public void setSml360svCondition3(String sml360svCondition3) {
        sml360svCondition3__ = sml360svCondition3;
    }


    /**
     * <p>sml360svConditionType3 を取得します。
     * @return sml360svConditionType3
     */
    public String getSml360svConditionType3() {
        return sml360svConditionType3__;
    }


    /**
     * <p>sml360svConditionType3 をセットします。
     * @param sml360svConditionType3 sml360svConditionType3
     */
    public void setSml360svConditionType3(String sml360svConditionType3) {
        sml360svConditionType3__ = sml360svConditionType3;
    }


    /**
     * <p>sml360svConditionExs3 を取得します。
     * @return sml360svConditionExs3
     */
    public String getSml360svConditionExs3() {
        return sml360svConditionExs3__;
    }


    /**
     * <p>sml360svConditionExs3 をセットします。
     * @param sml360svConditionExs3 sml360svConditionExs3
     */
    public void setSml360svConditionExs3(String sml360svConditionExs3) {
        sml360svConditionExs3__ = sml360svConditionExs3;
    }


    /**
     * <p>sml360svConditionText3 を取得します。
     * @return sml360svConditionText3
     */
    public String getSml360svConditionText3() {
        return sml360svConditionText3__;
    }


    /**
     * <p>sml360svConditionText3 をセットします。
     * @param sml360svConditionText3 sml360svConditionText3
     */
    public void setSml360svConditionText3(String sml360svConditionText3) {
        sml360svConditionText3__ = sml360svConditionText3;
    }


    /**
     * <p>sml360svCondition4 を取得します。
     * @return sml360svCondition4
     */
    public String getSml360svCondition4() {
        return sml360svCondition4__;
    }


    /**
     * <p>sml360svCondition4 をセットします。
     * @param sml360svCondition4 sml360svCondition4
     */
    public void setSml360svCondition4(String sml360svCondition4) {
        sml360svCondition4__ = sml360svCondition4;
    }


    /**
     * <p>sml360svConditionType4 を取得します。
     * @return sml360svConditionType4
     */
    public String getSml360svConditionType4() {
        return sml360svConditionType4__;
    }


    /**
     * <p>sml360svConditionType4 をセットします。
     * @param sml360svConditionType4 sml360svConditionType4
     */
    public void setSml360svConditionType4(String sml360svConditionType4) {
        sml360svConditionType4__ = sml360svConditionType4;
    }


    /**
     * <p>sml360svConditionExs4 を取得します。
     * @return sml360svConditionExs4
     */
    public String getSml360svConditionExs4() {
        return sml360svConditionExs4__;
    }


    /**
     * <p>sml360svConditionExs4 をセットします。
     * @param sml360svConditionExs4 sml360svConditionExs4
     */
    public void setSml360svConditionExs4(String sml360svConditionExs4) {
        sml360svConditionExs4__ = sml360svConditionExs4;
    }


    /**
     * <p>sml360svConditionText4 を取得します。
     * @return sml360svConditionText4
     */
    public String getSml360svConditionText4() {
        return sml360svConditionText4__;
    }


    /**
     * <p>sml360svConditionText4 をセットします。
     * @param sml360svConditionText4 sml360svConditionText4
     */
    public void setSml360svConditionText4(String sml360svConditionText4) {
        sml360svConditionText4__ = sml360svConditionText4;
    }


    /**
     * <p>sml360svCondition5 を取得します。
     * @return sml360svCondition5
     */
    public String getSml360svCondition5() {
        return sml360svCondition5__;
    }


    /**
     * <p>sml360svCondition5 をセットします。
     * @param sml360svCondition5 sml360svCondition5
     */
    public void setSml360svCondition5(String sml360svCondition5) {
        sml360svCondition5__ = sml360svCondition5;
    }


    /**
     * <p>sml360svConditionType5 を取得します。
     * @return sml360svConditionType5
     */
    public String getSml360svConditionType5() {
        return sml360svConditionType5__;
    }


    /**
     * <p>sml360svConditionType5 をセットします。
     * @param sml360svConditionType5 sml360svConditionType5
     */
    public void setSml360svConditionType5(String sml360svConditionType5) {
        sml360svConditionType5__ = sml360svConditionType5;
    }


    /**
     * <p>sml360svConditionExs5 を取得します。
     * @return sml360svConditionExs5
     */
    public String getSml360svConditionExs5() {
        return sml360svConditionExs5__;
    }


    /**
     * <p>sml360svConditionExs5 をセットします。
     * @param sml360svConditionExs5 sml360svConditionExs5
     */
    public void setSml360svConditionExs5(String sml360svConditionExs5) {
        sml360svConditionExs5__ = sml360svConditionExs5;
    }


    /**
     * <p>sml360svConditionText5 を取得します。
     * @return sml360svConditionText5
     */
    public String getSml360svConditionText5() {
        return sml360svConditionText5__;
    }


    /**
     * <p>sml360svConditionText5 をセットします。
     * @param sml360svConditionText5 sml360svConditionText5
     */
    public void setSml360svConditionText5(String sml360svConditionText5) {
        sml360svConditionText5__ = sml360svConditionText5;
    }


    /**
     * <p>sml360svTempFile を取得します。
     * @return sml360svTempFile
     */
    public String getSml360svTempFile() {
        return sml360svTempFile__;
    }


    /**
     * <p>sml360svTempFile をセットします。
     * @param sml360svTempFile sml360svTempFile
     */
    public void setSml360svTempFile(String sml360svTempFile) {
        sml360svTempFile__ = sml360svTempFile;
    }


    /**
     * <p>sml360mailListPageTop を取得します。
     * @return sml360mailListPageTop
     */
    public int getSml360mailListPageTop() {
        return sml360mailListPageTop__;
    }


    /**
     * <p>sml360mailListPageTop をセットします。
     * @param sml360mailListPageTop sml360mailListPageTop
     */
    public void setSml360mailListPageTop(int sml360mailListPageTop) {
        sml360mailListPageTop__ = sml360mailListPageTop;
    }


    /**
     * <p>sml360mailListPageBottom を取得します。
     * @return sml360mailListPageBottom
     */
    public int getSml360mailListPageBottom() {
        return sml360mailListPageBottom__;
    }


    /**
     * <p>sml360mailListPageBottom をセットします。
     * @param sml360mailListPageBottom sml360mailListPageBottom
     */
    public void setSml360mailListPageBottom(int sml360mailListPageBottom) {
        sml360mailListPageBottom__ = sml360mailListPageBottom;
    }


    /**
     * <p>sml360mailList を取得します。
     * @return sml360mailList
     */
    public List<Sml340MailModel> getSml360mailList() {
        return sml360mailList__;
    }


    /**
     * <p>sml360mailList をセットします。
     * @param sml360mailList sml360mailList
     */
    public void setSml360mailList(List<Sml340MailModel> sml360mailList) {
        sml360mailList__ = sml360mailList;
    }


    /**
     * <p>sml360mailListPageCombo を取得します。
     * @return sml360mailListPageCombo
     */
    public List<LabelValueBean> getSml360mailListPageCombo() {
        return sml360mailListPageCombo__;
    }


    /**
     * <p>sml360mailListPageCombo をセットします。
     * @param sml360mailListPageCombo sml360mailListPageCombo
     */
    public void setSml360mailListPageCombo(
            List<LabelValueBean> sml360mailListPageCombo) {
        sml360mailListPageCombo__ = sml360mailListPageCombo;
    }



}
