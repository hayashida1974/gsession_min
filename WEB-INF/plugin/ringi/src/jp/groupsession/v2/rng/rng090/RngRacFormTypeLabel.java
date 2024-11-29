package jp.groupsession.v2.rng.rng090;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formmodel.BlockList;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.SimpleUserSelect;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.rng.model.EnumApiConnectParamType;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] API連携設定用 ラベルモデル
 * <br>[解  説] フォームタイプ、表要素内判定を扱える
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngRacFormTypeLabel extends LabelValueBean {
    /** フォームタイプラベル 列挙値の識別用 接頭子 */
    public static final String FT_ENUM_HEAD = "FT";
    /** FORM_VALUE時の識別用 接頭子 */
    public static final String FV_ENUM_HEAD = "FV";
    /** LIST_BODY時の識別用 接頭子 */
    public static final String FL_ENUM_HEAD = "FL";

    /** フォームタイプ*/
    private String formType__;
    /** 表ボディ要素内判定*/
    private boolean inListBody__;
    /** 表要素 親SID*/
    private int parentListSid__;
    /** 複数選択判定*/
    private boolean multisel__;



    /**
     * コンストラクタ
     * @param label
     * @param value
     * @param formType
     * @param inListBody
     * @param multisel
     * @param parentListSid
     */
    public RngRacFormTypeLabel(
            String label,
            String value,
            String formType,
            boolean inListBody,
            boolean multisel,
            int parentListSid) {
        super(label, value);
        formType__ = formType;
        inListBody__ = inListBody;
        parentListSid__ = parentListSid;
        multisel__ = multisel;
    }



    /**
     * <p>formType を取得します。
     * @return formType
     * @see jp.groupsession.v2.rng.rng090.RngRacFormTypeLabel#formType__
     */
    public String getFormType() {
        return formType__;
    }



    /**
     * <p>inList を取得します。
     * @return inList
     * @see jp.groupsession.v2.rng.rng090.RngRacFormTypeLabel#inListBody__
     */
    public boolean isInListBody() {
        return inListBody__;
    }

    /**
     * <p>parentSid を取得します。
     * @return parentSid
     * @see jp.groupsession.v2.rng.rng090.RngRacFormTypeLabel#parentListSid__
     */
    public int getParentListSid() {
        return parentListSid__;
    }



    /**
     *
     * <br>[機  能] Api連携設定 パラメータ選択値用 ラベルリストを生成
     * <br>[解  説] EnumApiConnectParamTypeと入力フォームリストを結合して生成する
     * <br>[備  考] Form_IDがない入力フォームは除外される
     * @param locale
     * @param chkFb
     * @return Api連携設定 選択値用 ラベルリスト
     */
    public static Collection<RngRacFormTypeLabel> createRacFormTypeSet(
            Locale locale,
            FormBuilder chkFb) {
        GsMessage gsMsg = new GsMessage(locale);
        return Stream.of(
           EnumSet.allOf(EnumApiConnectParamType.class).stream()
           .filter(num -> num != EnumApiConnectParamType.FORM_VALUE
                           && num != EnumApiConnectParamType.LIST_BODY)
           .map(num -> {
               return new RngRacFormTypeLabel(
                   gsMsg.getMessage(
                      num.getLabelMsgKey()
                   ),
                   String.format("%s%d",
                           FT_ENUM_HEAD,
                           num.getValue()),
                   num.getFormType(), false,
                   EnumSet.of(
                           EnumApiConnectParamType.SELECT_GROUP,
                           EnumApiConnectParamType.SELECT_USER).contains(num),
                   -1);
               }
           ),
           chkFb.getFormCellList()
           .stream()
           .filter(cell -> (
                       cell.getType() != EnumFormModelKbn.blocklist
                       && cell.getType() != EnumFormModelKbn.block)
                   )
           .filter(cell -> {
               return !StringUtil.isNullZeroString(cell.getFormID());
               })
           .map(cell -> {
               return new RngRacFormTypeLabel(
                       cell.getFormID(),
                       String.format("%s%d",
                               FV_ENUM_HEAD,
                               cell.getSid()),
                       cell.getType().name(),
                       BlockList.isBodyCell(cell),
                       isMultiSelForm(cell),
                       BlockList.getParentSid(cell));
               }
           ),
           chkFb.getFormMap().values()
           .stream()
           .filter(cell -> (
                       cell.getType() == EnumFormModelKbn.blocklist)
                   )
           .filter(cell -> {
               return !StringUtil.isNullZeroString(cell.getFormID());
               })
           .sorted(Comparator.comparingInt(FormCell::getSid))
           .map(cell -> {
               return new RngRacFormTypeLabel(
                       cell.getFormID(),
                       String.format("%s%d",
                               FL_ENUM_HEAD,
                               cell.getSid()),
                       cell.getType().name(),
                       BlockList.isBodyCell(cell),
                       false,
                       BlockList.getParentSid(cell));
               }
           )
        ).flatMap(str -> str)
        .collect(Collectors.toList());
    }
    /**
     * <br>[機  能] 条件設定 フォームID選択一覧生成
     * <br>[解  説]
     * <br>[備  考]
     * @param fb フォームビルダ
     * @param listFsid 依存表要素SID -1で依存なし
     * @return フォームID選択一覧
     */
    public static List<RngRacFormTypeLabel> createCondFormIdSelect(FormBuilder fb, int listFsid) {
        List<RngRacFormTypeLabel> formIdSelList = fb.getFormCellList()
            .stream()
            .filter(cell -> (
                       cell.getType() != EnumFormModelKbn.blocklist
                       && cell.getType() != EnumFormModelKbn.label
                       && cell.getType() != EnumFormModelKbn.block
                       && cell.getType() != EnumFormModelKbn.file)
                   )
            .filter(cell -> {
               return !StringUtil.isNullZeroString(cell.getFormID());
               })
            .map(cell -> {
               return new RngRacFormTypeLabel(
                       cell.getFormID(),
                       cell.getFormID(),
                       cell.getType().name(),
                       BlockList.isBodyCell(cell),
                       RngRacFormTypeLabel.isMultiSelForm(cell),
                       BlockList.getParentSid(cell));
               }
            )
           .filter(racLbl -> (
                   listFsid == -1
                   || racLbl.getParentListSid() == -1
                   || racLbl.getParentListSid() == listFsid))
            .collect(Collectors.toList());
        return formIdSelList;
    }

    /**
     *
     * <br>[機  能] cell情報から複数選択かを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param cell
     * @return true 複数選択要素 false 単体選択要素
     */
    public static boolean isMultiSelForm(FormCell cell) {

        EnumFormModelKbn kbn = cell.getType();
        switch (kbn) {
          case check: return true;
          case user:
              return (((SimpleUserSelect) cell.getBody(kbn.name())).getMultiFlg()
                      == UserGroupSelectModel.FLG_MULTI_ON);
          case group:
              return (((GroupComboModel) cell.getBody(kbn.name())).getMultiFlg()
                      == UserGroupSelectModel.FLG_MULTI_ON);
          default: return false;
        }
    }



    /**
     *
     * <br>[機  能] Api連携設定 パラメータ選択値用 ラベルリストを生成
     * <br>[解  説] EnumApiConnectParamTypeと入力フォームリストを結合して生成する
     * <br>[備  考] Form_IDがない入力フォームは除外される
     * @param locale
     * @param chkFb
     * @param listFsid 依存表リストSID
     * @return Api連携設定 選択値用 ラベルリスト
     */
    public static Collection<RngRacFormTypeLabel> createParameterTypeSet(
            Locale locale,
            FormBuilder chkFb,
            int listFsid) {
        GsMessage gsMsg = new GsMessage(locale);
        return Stream.concat(
           EnumSet.allOf(EnumApiConnectParamType.class).stream()
           .filter(num -> num != EnumApiConnectParamType.FORM_VALUE
                           && num != EnumApiConnectParamType.OBJECT
                           && num != EnumApiConnectParamType.LIST_BODY)
           .map(num -> {
               return new RngRacFormTypeLabel(
                   gsMsg.getMessage(
                      num.getLabelMsgKey()
                   ),
                   String.format("%s%d",
                           FT_ENUM_HEAD,
                           num.getValue()),
                   num.getFormType(), false,
                   EnumSet.of(
                           EnumApiConnectParamType.SELECT_GROUP,
                           EnumApiConnectParamType.SELECT_USER).contains(num),
                   -1);
               }
           ),
           chkFb.getFormCellList()
           .stream()
           .filter(cell -> (
                       cell.getType() != EnumFormModelKbn.blocklist
                       && cell.getType() != EnumFormModelKbn.block)
                   )
           .filter(cell -> {
               return !StringUtil.isNullZeroString(cell.getFormID());
               })
           .map(cell -> {
               return new RngRacFormTypeLabel(
                       cell.getFormID(),
                       String.format("%s%d",
                               FV_ENUM_HEAD,
                               cell.getSid()),
                       cell.getType().name(),
                       BlockList.isBodyCell(cell),
                       isMultiSelForm(cell),
                       BlockList.getParentSid(cell));
               }
           )
           .filter(racLbl -> (
                   listFsid == -1
                   || racLbl.getParentListSid() == -1
                   || racLbl.getParentListSid() == listFsid))
        ).collect(Collectors.toList());
    }

    /**
     *
     * <br>[機  能] Api連携設定 入れ子配列選択値用 ラベルリストを生成
     * <br>[解  説] EnumApiConnectParamTypeと入力フォームリストを結合して生成する
     * <br>[備  考] Form_IDがない入力フォームは除外される
     * @param locale
     * @param chkFb
     * @param listFsid 依存表リストSID
     * @return Api連携設定 選択値用 ラベルリスト
     */
    public static Collection<RngRacFormTypeLabel> createIrekoArrayTypeSet(
            Locale locale,
            FormBuilder chkFb,
            int listFsid) {
        GsMessage gsMsg = new GsMessage(locale);
        return Stream.concat(
           Stream.of(EnumApiConnectParamType.OBJECT)
           .map(num -> {
               return new RngRacFormTypeLabel(
                   gsMsg.getMessage(
                      num.getLabelMsgKey()
                   ),
                   String.format("%s%d",
                           FT_ENUM_HEAD,
                           num.getValue()),
                   num.getFormType(), false, false, -1);
               }
           ),
           chkFb.getFormCellList()
           .stream()
           .filter(cell -> (
                       cell.getType() == EnumFormModelKbn.blocklist)
                   )
           .filter(cell -> {
               return !StringUtil.isNullZeroString(cell.getFormID());
               })
           .map(cell -> {
               return new RngRacFormTypeLabel(
                       cell.getFormID(),
                       String.format("%s%d",
                               FL_ENUM_HEAD,
                               cell.getSid()),
                       cell.getType().name(),
                       BlockList.isBodyCell(cell),
                       false,
                       BlockList.getParentSid(cell));
               }
           )
           .filter(racLbl -> (listFsid == -1))

        ).collect(Collectors.toList());
    }
    public static Map<String, RngRacFormTypeLabel> createIrekoArrayTypeMap(
            Locale locale,
            FormBuilder chkFb,
            int listFsid) {
        return createIrekoArrayTypeSet(locale, chkFb, listFsid).stream()
                .collect(Collectors.toMap(
                        RngRacFormTypeLabel::getValue,
                        type -> type));

    }


    /**
     * <p>multisel を取得します。
     * @return multisel
     * @see jp.groupsession.v2.rng.rng090.RngRacFormTypeLabel#multisel__
     */
    public boolean isMultisel() {
        return multisel__;
    }



    /**
     * <p>multisel をセットします。
     * @param multisel multisel
     * @see jp.groupsession.v2.rng.rng090.RngRacFormTypeLabel#multisel__
     */
    public void setMultisel(boolean multisel) {
        multisel__ = multisel;
    }



}
