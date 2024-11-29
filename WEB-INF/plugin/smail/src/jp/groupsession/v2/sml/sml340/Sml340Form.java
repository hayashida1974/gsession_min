package jp.groupsession.v2.sml.sml340;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.sml330.Sml330Form;

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
public class Sml340Form extends Sml330Form {

    /** フィルター区分 全ての条件に一致 */
    public static final int SML340_FILKBN_ALL = 0;
    /** フィルター区分 いずれかの条件に一致 */
    public static final int SML340_FILTKBN_ANY = 1;

    /** フィルタリング実行 実行しない */
    public static final int SML340_DOFILTER_NO = 0;
    /** フィルタリング実行 実行する */
    public static final int SML340_DOFILTER_YES = 1;

    /** メール一覧 ソートキー 日時 */
    public static final int SML340_SKEY_DATE = 0;
    /** メール一覧 ソートキー 差出人 */
    public static final int SML340_SKEY_FROM = 1;
    /** メール一覧 ソートキー 件名 */
    public static final int SML340_SKEY_SUBJECT = 2;
    /** メール一覧 並び順 昇順 */
    public static final int SML340_ORDER_ASC = 0;
    /** メール一覧 並び順 降順 */
    public static final int SML340_ORDER_DESC = 1;

    /** ラベルリスト */
    private List<LabelValueBean> lbList__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList1__ = null;
    /** 条件リスト */
    private List<LabelValueBean> conditionList2__ = null;

    /** フィルター名 */
    private String sml340FilterName__ = null;
    /** フィルター区分 */
    private int sml340filterType__ = SML340_FILKBN_ALL;
    /** 初期表示区分 */
    private int sml340initFlg__ = 0;
    /** 選択 ラベル */
    private String sml340actionLabelValue__ = null;

    /** 条件１ 区分 */
    private String sml340condition1__ = null;
    /** 条件１ 種別 */
    private String sml340conditionType1__ = null;
    /** 条件１ 式 */
    private String sml340conditionExs1__ = null;
    /** 条件１ 条件 */
    private String sml340conditionText1__ = null;
    /** 条件２ 区分 */
    private String sml340condition2__ = null;
    /** 条件２ 種別 */
    private String sml340conditionType2__ = null;
    /** 条件２ 式 */
    private String sml340conditionExs2__ = null;
    /** 条件２ 条件 */
    private String sml340conditionText2__ = null;
    /** 条件３ 区分 */
    private String sml340condition3__ = null;
    /** 条件３ 種別 */
    private String sml340conditionType3__ = null;
    /** 条件３ 式 */
    private String sml340conditionExs3__ = null;
    /** 条件３ 条件 */
    private String sml340conditionText3__ = null;
    /** 条件４ 区分 */
    private String sml340condition4__ = null;
    /** 条件４ 種別 */
    private String sml340conditionType4__ = null;
    /** 条件４ 式 */
    private String sml340conditionExs4__ = null;
    /** 条件４ 条件 */
    private String sml340conditionText4__ = null;
    /** 条件５ 区分 */
    private String sml340condition5__ = null;
    /** 条件５ 種別 */
    private String sml340conditionType5__ = null;
    /** 条件５ 式 */
    private String sml340conditionExs5__ = null;
    /** 条件５ 条件 */
    private String sml340conditionText5__ = null;
    /** 添付ファイル有無 */
    private String sml340tempFile__ = "0";

    /** 動作 ラベルを付ける */
    private String sml340actionLabel__ = "0";
    /** 動作 既読にする */
    private String sml340actionRead__ = "0";
    /** 動作 ゴミ箱に移動する */
    private String sml340actionDust__ = "0";

    /** フィルタリング実行 */
    private int sml340doFilter__ = SML340_DOFILTER_NO;

    /** メール一覧表示フラグ */
    private int sml340viewMailList__ = 0;
    /** メール一覧 ソートキー */
    private int sml340mailListSortKey__ = SML340_SKEY_DATE;
    /** メール一覧 並び順 */
    private int sml340mailListOrder__ = SML340_ORDER_ASC;
    /** フィルター区分(保持) */
    private int sml340svFilterType__ = SML340_FILKBN_ALL;
    /** 条件１ 区分(保持) */
    private String sml340svCondition1__ = null;
    /** 条件１ 種別(保持) */
    private String sml340svConditionType1__ = null;
    /** 条件１ 式(保持) */
    private String sml340svConditionExs1__ = null;
    /** 条件１ 条件(保持) */
    private String sml340svConditionText1__ = null;
    /** 条件２ 区分(保持) */
    private String sml340svCondition2__ = null;
    /** 条件２ 種別(保持) */
    private String sml340svConditionType2__ = null;
    /** 条件２ 式(保持) */
    private String sml340svConditionExs2__ = null;
    /** 条件２ 条件(保持) */
    private String sml340svConditionText2__ = null;
    /** 条件３ 区分(保持) */
    private String sml340svCondition3__ = null;
    /** 条件３ 種別(保持) */
    private String sml340svConditionType3__ = null;
    /** 条件３ 式(保持) */
    private String sml340svConditionExs3__ = null;
    /** 条件３ 条件(保持) */
    private String sml340svConditionText3__ = null;
    /** 条件４ 区分(保持) */
    private String sml340svCondition4__ = null;
    /** 条件４ 種別(保持) */
    private String sml340svConditionType4__ = null;
    /** 条件４ 式(保持) */
    private String sml340svConditionExs4__ = null;
    /** 条件４ 条件(保持) */
    private String sml340svConditionText4__ = null;
    /** 条件５ 区分(保持) */
    private String sml340svCondition5__ = null;
    /** 条件５ 種別(保持) */
    private String sml340svConditionType5__ = null;
    /** 条件５ 式(保持) */
    private String sml340svConditionExs5__ = null;
    /** 条件５ 条件(保持) */
    private String sml340svConditionText5__ = null;
    /** 添付ファイル有無(保持) */
    private String sml340svTempFile__ = "0";

    /** メール一覧 ページ(上段) */
    private int sml340mailListPageTop__ = 0;
    /** メール一覧 ページ(下段) */
    private int sml340mailListPageBottom__ = 0;
    /** メール一覧 */
    private List<Sml340MailModel> sml340mailList__ = null;
    /** メール一覧 ページコンボ */
    private List<LabelValueBean> sml340mailListPageCombo__ = null;


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
        GSValidateSmail.validateTextBoxInput(errors, sml340FilterName__,
                "sml340FilterName",
                getInterMessage(req, GSConstSmail.TEXT_FILTER),
                GSConstSmail.MAXLEN_SEARCH_KEYWORD, true);

        //条件未選択
        if (sml340condition1__ == null
            && sml340condition2__ == null
            && sml340condition3__ == null
            && sml340condition4__ == null
            && sml340condition5__ == null
            && sml340tempFile__.equals("0")) {
            String msgKey = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgKey,
                                        getInterMessage(req, GSConstSmail.TEXT_CONDITION));
            StrutsUtil.addMessage(
                    errors, msg, "wml230condition." + msgKey);

        }

        //条件チェック
        validateCondition(errors, req);

        //ラベル選択チェック
        if (sml340actionLabel__.equals("1")
                && sml340actionLabelValue__.equals("-1")) {
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
        errSize = conditionCheck(sml340condition1__, sml340conditionText1__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION1));
        errSize = conditionCheck(sml340condition2__, sml340conditionText2__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION2));
        errSize = conditionCheck(sml340condition3__, sml340conditionText3__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION3));
        errSize = conditionCheck(sml340condition4__, sml340conditionText4__,
                errSize, errors, getInterMessage(req, GSConstSmail.TEXT_CONDITION4));
        errSize = conditionCheck(sml340condition5__, sml340conditionText5__,
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
     * <p>sml340FilterName を取得します。
     * @return sml340FilterName
     */
    public String getSml340FilterName() {
        return sml340FilterName__;
    }


    /**
     * <p>sml340FilterName をセットします。
     * @param sml340FilterName sml340FilterName
     */
    public void setSml340FilterName(String sml340FilterName) {
        sml340FilterName__ = sml340FilterName;
    }


    /**
     * <p>sml340filterType を取得します。
     * @return sml340filterType
     */
    public int getSml340filterType() {
        return sml340filterType__;
    }


    /**
     * <p>sml340filterType をセットします。
     * @param sml340filterType sml340filterType
     */
    public void setSml340filterType(int sml340filterType) {
        sml340filterType__ = sml340filterType;
    }


    /**
     * <p>sml340initFlg を取得します。
     * @return sml340initFlg
     */
    public int getSml340initFlg() {
        return sml340initFlg__;
    }


    /**
     * <p>sml340initFlg をセットします。
     * @param sml340initFlg sml340initFlg
     */
    public void setSml340initFlg(int sml340initFlg) {
        sml340initFlg__ = sml340initFlg;
    }


    /**
     * <p>sml340actionLabelValue を取得します。
     * @return sml340actionLabelValue
     */
    public String getSml340actionLabelValue() {
        return sml340actionLabelValue__;
    }


    /**
     * <p>sml340actionLabelValue をセットします。
     * @param sml340actionLabelValue sml340actionLabelValue
     */
    public void setSml340actionLabelValue(String sml340actionLabelValue) {
        sml340actionLabelValue__ = sml340actionLabelValue;
    }


    /**
     * <p>sml340condition1 を取得します。
     * @return sml340condition1
     */
    public String getSml340condition1() {
        return sml340condition1__;
    }


    /**
     * <p>sml340condition1 をセットします。
     * @param sml340condition1 sml340condition1
     */
    public void setSml340condition1(String sml340condition1) {
        sml340condition1__ = sml340condition1;
    }


    /**
     * <p>sml340conditionType1 を取得します。
     * @return sml340conditionType1
     */
    public String getSml340conditionType1() {
        return sml340conditionType1__;
    }


    /**
     * <p>sml340conditionType1 をセットします。
     * @param sml340conditionType1 sml340conditionType1
     */
    public void setSml340conditionType1(String sml340conditionType1) {
        sml340conditionType1__ = sml340conditionType1;
    }


    /**
     * <p>sml340conditionExs1 を取得します。
     * @return sml340conditionExs1
     */
    public String getSml340conditionExs1() {
        return sml340conditionExs1__;
    }


    /**
     * <p>sml340conditionExs1 をセットします。
     * @param sml340conditionExs1 sml340conditionExs1
     */
    public void setSml340conditionExs1(String sml340conditionExs1) {
        sml340conditionExs1__ = sml340conditionExs1;
    }


    /**
     * <p>sml340conditionText1 を取得します。
     * @return sml340conditionText1
     */
    public String getSml340conditionText1() {
        return sml340conditionText1__;
    }


    /**
     * <p>sml340conditionText1 をセットします。
     * @param sml340conditionText1 sml340conditionText1
     */
    public void setSml340conditionText1(String sml340conditionText1) {
        sml340conditionText1__ = sml340conditionText1;
    }


    /**
     * <p>sml340condition2 を取得します。
     * @return sml340condition2
     */
    public String getSml340condition2() {
        return sml340condition2__;
    }


    /**
     * <p>sml340condition2 をセットします。
     * @param sml340condition2 sml340condition2
     */
    public void setSml340condition2(String sml340condition2) {
        sml340condition2__ = sml340condition2;
    }


    /**
     * <p>sml340conditionType2 を取得します。
     * @return sml340conditionType2
     */
    public String getSml340conditionType2() {
        return sml340conditionType2__;
    }


    /**
     * <p>sml340conditionType2 をセットします。
     * @param sml340conditionType2 sml340conditionType2
     */
    public void setSml340conditionType2(String sml340conditionType2) {
        sml340conditionType2__ = sml340conditionType2;
    }


    /**
     * <p>sml340conditionExs2 を取得します。
     * @return sml340conditionExs2
     */
    public String getSml340conditionExs2() {
        return sml340conditionExs2__;
    }


    /**
     * <p>sml340conditionExs2 をセットします。
     * @param sml340conditionExs2 sml340conditionExs2
     */
    public void setSml340conditionExs2(String sml340conditionExs2) {
        sml340conditionExs2__ = sml340conditionExs2;
    }


    /**
     * <p>sml340conditionText2 を取得します。
     * @return sml340conditionText2
     */
    public String getSml340conditionText2() {
        return sml340conditionText2__;
    }


    /**
     * <p>sml340conditionText2 をセットします。
     * @param sml340conditionText2 sml340conditionText2
     */
    public void setSml340conditionText2(String sml340conditionText2) {
        sml340conditionText2__ = sml340conditionText2;
    }


    /**
     * <p>sml340condition3 を取得します。
     * @return sml340condition3
     */
    public String getSml340condition3() {
        return sml340condition3__;
    }


    /**
     * <p>sml340condition3 をセットします。
     * @param sml340condition3 sml340condition3
     */
    public void setSml340condition3(String sml340condition3) {
        sml340condition3__ = sml340condition3;
    }


    /**
     * <p>sml340conditionType3 を取得します。
     * @return sml340conditionType3
     */
    public String getSml340conditionType3() {
        return sml340conditionType3__;
    }


    /**
     * <p>sml340conditionType3 をセットします。
     * @param sml340conditionType3 sml340conditionType3
     */
    public void setSml340conditionType3(String sml340conditionType3) {
        sml340conditionType3__ = sml340conditionType3;
    }


    /**
     * <p>sml340conditionExs3 を取得します。
     * @return sml340conditionExs3
     */
    public String getSml340conditionExs3() {
        return sml340conditionExs3__;
    }


    /**
     * <p>sml340conditionExs3 をセットします。
     * @param sml340conditionExs3 sml340conditionExs3
     */
    public void setSml340conditionExs3(String sml340conditionExs3) {
        sml340conditionExs3__ = sml340conditionExs3;
    }


    /**
     * <p>sml340conditionText3 を取得します。
     * @return sml340conditionText3
     */
    public String getSml340conditionText3() {
        return sml340conditionText3__;
    }


    /**
     * <p>sml340conditionText3 をセットします。
     * @param sml340conditionText3 sml340conditionText3
     */
    public void setSml340conditionText3(String sml340conditionText3) {
        sml340conditionText3__ = sml340conditionText3;
    }


    /**
     * <p>sml340condition4 を取得します。
     * @return sml340condition4
     */
    public String getSml340condition4() {
        return sml340condition4__;
    }


    /**
     * <p>sml340condition4 をセットします。
     * @param sml340condition4 sml340condition4
     */
    public void setSml340condition4(String sml340condition4) {
        sml340condition4__ = sml340condition4;
    }


    /**
     * <p>sml340conditionType4 を取得します。
     * @return sml340conditionType4
     */
    public String getSml340conditionType4() {
        return sml340conditionType4__;
    }


    /**
     * <p>sml340conditionType4 をセットします。
     * @param sml340conditionType4 sml340conditionType4
     */
    public void setSml340conditionType4(String sml340conditionType4) {
        sml340conditionType4__ = sml340conditionType4;
    }


    /**
     * <p>sml340conditionExs4 を取得します。
     * @return sml340conditionExs4
     */
    public String getSml340conditionExs4() {
        return sml340conditionExs4__;
    }


    /**
     * <p>sml340conditionExs4 をセットします。
     * @param sml340conditionExs4 sml340conditionExs4
     */
    public void setSml340conditionExs4(String sml340conditionExs4) {
        sml340conditionExs4__ = sml340conditionExs4;
    }


    /**
     * <p>sml340conditionText4 を取得します。
     * @return sml340conditionText4
     */
    public String getSml340conditionText4() {
        return sml340conditionText4__;
    }


    /**
     * <p>sml340conditionText4 をセットします。
     * @param sml340conditionText4 sml340conditionText4
     */
    public void setSml340conditionText4(String sml340conditionText4) {
        sml340conditionText4__ = sml340conditionText4;
    }


    /**
     * <p>sml340condition5 を取得します。
     * @return sml340condition5
     */
    public String getSml340condition5() {
        return sml340condition5__;
    }


    /**
     * <p>sml340condition5 をセットします。
     * @param sml340condition5 sml340condition5
     */
    public void setSml340condition5(String sml340condition5) {
        sml340condition5__ = sml340condition5;
    }


    /**
     * <p>sml340conditionType5 を取得します。
     * @return sml340conditionType5
     */
    public String getSml340conditionType5() {
        return sml340conditionType5__;
    }


    /**
     * <p>sml340conditionType5 をセットします。
     * @param sml340conditionType5 sml340conditionType5
     */
    public void setSml340conditionType5(String sml340conditionType5) {
        sml340conditionType5__ = sml340conditionType5;
    }


    /**
     * <p>sml340conditionExs5 を取得します。
     * @return sml340conditionExs5
     */
    public String getSml340conditionExs5() {
        return sml340conditionExs5__;
    }


    /**
     * <p>sml340conditionExs5 をセットします。
     * @param sml340conditionExs5 sml340conditionExs5
     */
    public void setSml340conditionExs5(String sml340conditionExs5) {
        sml340conditionExs5__ = sml340conditionExs5;
    }


    /**
     * <p>sml340conditionText5 を取得します。
     * @return sml340conditionText5
     */
    public String getSml340conditionText5() {
        return sml340conditionText5__;
    }


    /**
     * <p>sml340conditionText5 をセットします。
     * @param sml340conditionText5 sml340conditionText5
     */
    public void setSml340conditionText5(String sml340conditionText5) {
        sml340conditionText5__ = sml340conditionText5;
    }


    /**
     * <p>sml340tempFile を取得します。
     * @return sml340tempFile
     */
    public String getSml340tempFile() {
        return sml340tempFile__;
    }


    /**
     * <p>sml340tempFile をセットします。
     * @param sml340tempFile sml340tempFile
     */
    public void setSml340tempFile(String sml340tempFile) {
        sml340tempFile__ = sml340tempFile;
    }


    /**
     * <p>sml340actionLabel を取得します。
     * @return sml340actionLabel
     */
    public String getSml340actionLabel() {
        return sml340actionLabel__;
    }


    /**
     * <p>sml340actionLabel をセットします。
     * @param sml340actionLabel sml340actionLabel
     */
    public void setSml340actionLabel(String sml340actionLabel) {
        sml340actionLabel__ = sml340actionLabel;
    }


    /**
     * <p>sml340actionRead を取得します。
     * @return sml340actionRead
     */
    public String getSml340actionRead() {
        return sml340actionRead__;
    }


    /**
     * <p>sml340actionRead をセットします。
     * @param sml340actionRead sml340actionRead
     */
    public void setSml340actionRead(String sml340actionRead) {
        sml340actionRead__ = sml340actionRead;
    }


    /**
     * <p>sml340actionDust を取得します。
     * @return sml340actionDust
     */
    public String getSml340actionDust() {
        return sml340actionDust__;
    }


    /**
     * <p>sml340actionDust をセットします。
     * @param sml340actionDust sml340actionDust
     */
    public void setSml340actionDust(String sml340actionDust) {
        sml340actionDust__ = sml340actionDust;
    }



    /**
     * <p>sml340doFilter を取得します。
     * @return sml340doFilter
     */
    public int getSml340doFilter() {
        return sml340doFilter__;
    }


    /**
     * <p>sml340doFilter をセットします。
     * @param sml340doFilter sml340doFilter
     */
    public void setSml340doFilter(int sml340doFilter) {
        sml340doFilter__ = sml340doFilter;
    }


    /**
     * <p>sml340viewMailList を取得します。
     * @return sml340viewMailList
     */
    public int getSml340viewMailList() {
        return sml340viewMailList__;
    }


    /**
     * <p>sml340viewMailList をセットします。
     * @param sml340viewMailList sml340viewMailList
     */
    public void setSml340viewMailList(int sml340viewMailList) {
        sml340viewMailList__ = sml340viewMailList;
    }


    /**
     * <p>sml340mailListSortKey を取得します。
     * @return sml340mailListSortKey
     */
    public int getSml340mailListSortKey() {
        return sml340mailListSortKey__;
    }


    /**
     * <p>sml340mailListSortKey をセットします。
     * @param sml340mailListSortKey sml340mailListSortKey
     */
    public void setSml340mailListSortKey(int sml340mailListSortKey) {
        sml340mailListSortKey__ = sml340mailListSortKey;
    }


    /**
     * <p>sml340mailListOrder を取得します。
     * @return sml340mailListOrder
     */
    public int getSml340mailListOrder() {
        return sml340mailListOrder__;
    }


    /**
     * <p>sml340mailListOrder をセットします。
     * @param sml340mailListOrder sml340mailListOrder
     */
    public void setSml340mailListOrder(int sml340mailListOrder) {
        sml340mailListOrder__ = sml340mailListOrder;
    }


    /**
     * <p>sml340svFilterType を取得します。
     * @return sml340svFilterType
     */
    public int getSml340svFilterType() {
        return sml340svFilterType__;
    }


    /**
     * <p>sml340svFilterType をセットします。
     * @param sml340svFilterType sml340svFilterType
     */
    public void setSml340svFilterType(int sml340svFilterType) {
        sml340svFilterType__ = sml340svFilterType;
    }


    /**
     * <p>sml340svCondition1 を取得します。
     * @return sml340svCondition1
     */
    public String getSml340svCondition1() {
        return sml340svCondition1__;
    }


    /**
     * <p>sml340svCondition1 をセットします。
     * @param sml340svCondition1 sml340svCondition1
     */
    public void setSml340svCondition1(String sml340svCondition1) {
        sml340svCondition1__ = sml340svCondition1;
    }


    /**
     * <p>sml340svConditionType1 を取得します。
     * @return sml340svConditionType1
     */
    public String getSml340svConditionType1() {
        return sml340svConditionType1__;
    }


    /**
     * <p>sml340svConditionType1 をセットします。
     * @param sml340svConditionType1 sml340svConditionType1
     */
    public void setSml340svConditionType1(String sml340svConditionType1) {
        sml340svConditionType1__ = sml340svConditionType1;
    }


    /**
     * <p>sml340svConditionExs1 を取得します。
     * @return sml340svConditionExs1
     */
    public String getSml340svConditionExs1() {
        return sml340svConditionExs1__;
    }


    /**
     * <p>sml340svConditionExs1 をセットします。
     * @param sml340svConditionExs1 sml340svConditionExs1
     */
    public void setSml340svConditionExs1(String sml340svConditionExs1) {
        sml340svConditionExs1__ = sml340svConditionExs1;
    }


    /**
     * <p>sml340svConditionText1 を取得します。
     * @return sml340svConditionText1
     */
    public String getSml340svConditionText1() {
        return sml340svConditionText1__;
    }


    /**
     * <p>sml340svConditionText1 をセットします。
     * @param sml340svConditionText1 sml340svConditionText1
     */
    public void setSml340svConditionText1(String sml340svConditionText1) {
        sml340svConditionText1__ = sml340svConditionText1;
    }


    /**
     * <p>sml340svCondition2 を取得します。
     * @return sml340svCondition2
     */
    public String getSml340svCondition2() {
        return sml340svCondition2__;
    }


    /**
     * <p>sml340svCondition2 をセットします。
     * @param sml340svCondition2 sml340svCondition2
     */
    public void setSml340svCondition2(String sml340svCondition2) {
        sml340svCondition2__ = sml340svCondition2;
    }


    /**
     * <p>sml340svConditionType2 を取得します。
     * @return sml340svConditionType2
     */
    public String getSml340svConditionType2() {
        return sml340svConditionType2__;
    }


    /**
     * <p>sml340svConditionType2 をセットします。
     * @param sml340svConditionType2 sml340svConditionType2
     */
    public void setSml340svConditionType2(String sml340svConditionType2) {
        sml340svConditionType2__ = sml340svConditionType2;
    }


    /**
     * <p>sml340svConditionExs2 を取得します。
     * @return sml340svConditionExs2
     */
    public String getSml340svConditionExs2() {
        return sml340svConditionExs2__;
    }


    /**
     * <p>sml340svConditionExs2 をセットします。
     * @param sml340svConditionExs2 sml340svConditionExs2
     */
    public void setSml340svConditionExs2(String sml340svConditionExs2) {
        sml340svConditionExs2__ = sml340svConditionExs2;
    }


    /**
     * <p>sml340svConditionText2 を取得します。
     * @return sml340svConditionText2
     */
    public String getSml340svConditionText2() {
        return sml340svConditionText2__;
    }


    /**
     * <p>sml340svConditionText2 をセットします。
     * @param sml340svConditionText2 sml340svConditionText2
     */
    public void setSml340svConditionText2(String sml340svConditionText2) {
        sml340svConditionText2__ = sml340svConditionText2;
    }


    /**
     * <p>sml340svCondition3 を取得します。
     * @return sml340svCondition3
     */
    public String getSml340svCondition3() {
        return sml340svCondition3__;
    }


    /**
     * <p>sml340svCondition3 をセットします。
     * @param sml340svCondition3 sml340svCondition3
     */
    public void setSml340svCondition3(String sml340svCondition3) {
        sml340svCondition3__ = sml340svCondition3;
    }


    /**
     * <p>sml340svConditionType3 を取得します。
     * @return sml340svConditionType3
     */
    public String getSml340svConditionType3() {
        return sml340svConditionType3__;
    }


    /**
     * <p>sml340svConditionType3 をセットします。
     * @param sml340svConditionType3 sml340svConditionType3
     */
    public void setSml340svConditionType3(String sml340svConditionType3) {
        sml340svConditionType3__ = sml340svConditionType3;
    }


    /**
     * <p>sml340svConditionExs3 を取得します。
     * @return sml340svConditionExs3
     */
    public String getSml340svConditionExs3() {
        return sml340svConditionExs3__;
    }


    /**
     * <p>sml340svConditionExs3 をセットします。
     * @param sml340svConditionExs3 sml340svConditionExs3
     */
    public void setSml340svConditionExs3(String sml340svConditionExs3) {
        sml340svConditionExs3__ = sml340svConditionExs3;
    }


    /**
     * <p>sml340svConditionText3 を取得します。
     * @return sml340svConditionText3
     */
    public String getSml340svConditionText3() {
        return sml340svConditionText3__;
    }


    /**
     * <p>sml340svConditionText3 をセットします。
     * @param sml340svConditionText3 sml340svConditionText3
     */
    public void setSml340svConditionText3(String sml340svConditionText3) {
        sml340svConditionText3__ = sml340svConditionText3;
    }


    /**
     * <p>sml340svCondition4 を取得します。
     * @return sml340svCondition4
     */
    public String getSml340svCondition4() {
        return sml340svCondition4__;
    }


    /**
     * <p>sml340svCondition4 をセットします。
     * @param sml340svCondition4 sml340svCondition4
     */
    public void setSml340svCondition4(String sml340svCondition4) {
        sml340svCondition4__ = sml340svCondition4;
    }


    /**
     * <p>sml340svConditionType4 を取得します。
     * @return sml340svConditionType4
     */
    public String getSml340svConditionType4() {
        return sml340svConditionType4__;
    }


    /**
     * <p>sml340svConditionType4 をセットします。
     * @param sml340svConditionType4 sml340svConditionType4
     */
    public void setSml340svConditionType4(String sml340svConditionType4) {
        sml340svConditionType4__ = sml340svConditionType4;
    }


    /**
     * <p>sml340svConditionExs4 を取得します。
     * @return sml340svConditionExs4
     */
    public String getSml340svConditionExs4() {
        return sml340svConditionExs4__;
    }


    /**
     * <p>sml340svConditionExs4 をセットします。
     * @param sml340svConditionExs4 sml340svConditionExs4
     */
    public void setSml340svConditionExs4(String sml340svConditionExs4) {
        sml340svConditionExs4__ = sml340svConditionExs4;
    }


    /**
     * <p>sml340svConditionText4 を取得します。
     * @return sml340svConditionText4
     */
    public String getSml340svConditionText4() {
        return sml340svConditionText4__;
    }


    /**
     * <p>sml340svConditionText4 をセットします。
     * @param sml340svConditionText4 sml340svConditionText4
     */
    public void setSml340svConditionText4(String sml340svConditionText4) {
        sml340svConditionText4__ = sml340svConditionText4;
    }


    /**
     * <p>sml340svCondition5 を取得します。
     * @return sml340svCondition5
     */
    public String getSml340svCondition5() {
        return sml340svCondition5__;
    }


    /**
     * <p>sml340svCondition5 をセットします。
     * @param sml340svCondition5 sml340svCondition5
     */
    public void setSml340svCondition5(String sml340svCondition5) {
        sml340svCondition5__ = sml340svCondition5;
    }


    /**
     * <p>sml340svConditionType5 を取得します。
     * @return sml340svConditionType5
     */
    public String getSml340svConditionType5() {
        return sml340svConditionType5__;
    }


    /**
     * <p>sml340svConditionType5 をセットします。
     * @param sml340svConditionType5 sml340svConditionType5
     */
    public void setSml340svConditionType5(String sml340svConditionType5) {
        sml340svConditionType5__ = sml340svConditionType5;
    }


    /**
     * <p>sml340svConditionExs5 を取得します。
     * @return sml340svConditionExs5
     */
    public String getSml340svConditionExs5() {
        return sml340svConditionExs5__;
    }


    /**
     * <p>sml340svConditionExs5 をセットします。
     * @param sml340svConditionExs5 sml340svConditionExs5
     */
    public void setSml340svConditionExs5(String sml340svConditionExs5) {
        sml340svConditionExs5__ = sml340svConditionExs5;
    }


    /**
     * <p>sml340svConditionText5 を取得します。
     * @return sml340svConditionText5
     */
    public String getSml340svConditionText5() {
        return sml340svConditionText5__;
    }


    /**
     * <p>sml340svConditionText5 をセットします。
     * @param sml340svConditionText5 sml340svConditionText5
     */
    public void setSml340svConditionText5(String sml340svConditionText5) {
        sml340svConditionText5__ = sml340svConditionText5;
    }


    /**
     * <p>sml340svTempFile を取得します。
     * @return sml340svTempFile
     */
    public String getSml340svTempFile() {
        return sml340svTempFile__;
    }


    /**
     * <p>sml340svTempFile をセットします。
     * @param sml340svTempFile sml340svTempFile
     */
    public void setSml340svTempFile(String sml340svTempFile) {
        sml340svTempFile__ = sml340svTempFile;
    }


    /**
     * <p>sml340mailListPageTop を取得します。
     * @return sml340mailListPageTop
     */
    public int getSml340mailListPageTop() {
        return sml340mailListPageTop__;
    }


    /**
     * <p>sml340mailListPageTop をセットします。
     * @param sml340mailListPageTop sml340mailListPageTop
     */
    public void setSml340mailListPageTop(int sml340mailListPageTop) {
        sml340mailListPageTop__ = sml340mailListPageTop;
    }


    /**
     * <p>sml340mailListPageBottom を取得します。
     * @return sml340mailListPageBottom
     */
    public int getSml340mailListPageBottom() {
        return sml340mailListPageBottom__;
    }


    /**
     * <p>sml340mailListPageBottom をセットします。
     * @param sml340mailListPageBottom sml340mailListPageBottom
     */
    public void setSml340mailListPageBottom(int sml340mailListPageBottom) {
        sml340mailListPageBottom__ = sml340mailListPageBottom;
    }


    /**
     * <p>sml340mailList を取得します。
     * @return sml340mailList
     */
    public List<Sml340MailModel> getSml340mailList() {
        return sml340mailList__;
    }


    /**
     * <p>sml340mailList をセットします。
     * @param sml340mailList sml340mailList
     */
    public void setSml340mailList(List<Sml340MailModel> sml340mailList) {
        sml340mailList__ = sml340mailList;
    }


    /**
     * <p>sml340mailListPageCombo を取得します。
     * @return sml340mailListPageCombo
     */
    public List<LabelValueBean> getSml340mailListPageCombo() {
        return sml340mailListPageCombo__;
    }


    /**
     * <p>sml340mailListPageCombo をセットします。
     * @param sml340mailListPageCombo sml340mailListPageCombo
     */
    public void setSml340mailListPageCombo(
            List<LabelValueBean> sml340mailListPageCombo) {
        sml340mailListPageCombo__ = sml340mailListPageCombo;
    }

}
