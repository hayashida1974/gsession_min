package jp.groupsession.v2.rng.rng110keiro;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.formbuilder.EnumCompOpr;
import jp.groupsession.v2.cmn.formmodel.SingleSelectModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.rng.ui.parts.InCondGroupSelector;
import jp.groupsession.v2.rng.ui.parts.InCondPostSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 経路使用条件モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class KeiroInCondition {
    /**入力値 フォームID 制限文字数*/
    public static final int MAXTEXTSIZE_INCOND_FORMID = 20;
    /**入力値 入力値 制限文字数*/
    public static final int MAXTEXTSIZE_INCOND_VALUE = 20;

    /** 経路使用条件区分 選択値*/
    private SingleSelectModel condKbn__ = new SingleSelectModel();
    /** 経路使用条件 入力値 フォームID*/
    private String formId__;
    /** 経路使用条件 入力値 値*/
    private String formValue__;
    /** 経路使用条件 比較子*/
    private SingleSelectModel comp__ = new SingleSelectModel();
    /** 経路使用条件 所属グループ*/
    private InCondGroupSelector selGrp__ = InCondGroupSelector.builder()
            .chainSelect(
                    Select.builder()
                        .chainParameterName("selected")
                        )
            .build();
    /** 経路使用条件 役職*/
    private InCondPostSelector selPos__ = InCondPostSelector.builder()
            .chainSelect(
                    Select.builder()
                        .chainParameterName("selected")
                        )
            .build();
    /** 経路使用条件 選択済み表示 所属グループ*/
    private List<LabelValueBean> selGrpLabelList__ = List.of();
    /** 経路使用条件 選択済み表示 役職*/
    private List<LabelValueBean> selPosLabelList__ = List.of();

    /**
     * <p>formId を取得します。
     * @return formId
     */
    public String getFormId() {
        return formId__;
    }

    /**
     * <p>formId をセットします。
     * @param formId formId
     */
    public void setFormId(String formId) {
        formId__ = formId;
    }

    /**
     * <p>formValue を取得します。
     * @return formValue
     */
    public String getFormValue() {
        return formValue__;
    }

    /**
     * <p>formValue をセットします。
     * @param formValue formValue
     */
    public void setFormValue(String formValue) {
        formValue__ = formValue;
    }

    /**
     * <p>comp を取得します。
     * @return comp
     */
    public SingleSelectModel getComp() {
        return comp__;
    }

    /**
     * <p>comp をセットします。
     * @param comp comp
     */
    public void setComp(SingleSelectModel comp) {
        comp__ = comp;
    }


    /**
     * <p>condKbn を取得します。
     * @return condKbn
     */
    public SingleSelectModel getCondKbn() {
        return condKbn__;
    }

    /**
     * <p>condKbn をセットします。
     * @param condKbn condKbn
     */
    public void setCondKbn(SingleSelectModel condKbn) {
        condKbn__ = condKbn;
    }


    /**
     * <p>selGrp を取得します。
     * @return selGrp
     */
    public InCondGroupSelector getSelGrp() {
        return selGrp__;
    }

    /**
     * <p>selGrp をセットします。
     * @param selGrp selGrp
     */
    public void setSelGrp(InCondGroupSelector selGrp) {
        selGrp__ = selGrp;
    }

    /**
     * <p>selPos を取得します。
     * @return selPos
     */
    public InCondPostSelector getSelPos() {
        return selPos__;
    }

    /**
     * <p>selPos をセットします。
     * @param selPos selPos
     */
    public void setSelPos(InCondPostSelector selPos) {
        selPos__ = selPos;
    }
    /**
     * <p>selGrpLabelList を取得します。
     * @return selGrpLabelList
     * @see jp.groupsession.v2.rng.rng110keiro.KeiroInCondition#selGrpLabelList__
     */
    public List<LabelValueBean> getSelGrpLabelList() {
        return selGrpLabelList__;
    }

    /**
     * <p>selGrpLabelList をセットします。
     * @param selGrpLabelList selGrpLabelList
     * @see jp.groupsession.v2.rng.rng110keiro.KeiroInCondition#selGrpLabelList__
     */
    public void setSelGrpLabelList(List<LabelValueBean> selGrpLabelList) {
        selGrpLabelList__ = selGrpLabelList;
    }

    /**
     * <p>selPosLabelList を取得します。
     * @return selPosLabelList
     * @see jp.groupsession.v2.rng.rng110keiro.KeiroInCondition#selPosLabelList__
     */
    public List<LabelValueBean> getSelPosLabelList() {
        return selPosLabelList__;
    }

    /**
     * <p>selPosLabelList をセットします。
     * @param selPosLabelList selPosLabelList
     * @see jp.groupsession.v2.rng.rng110keiro.KeiroInCondition#selPosLabelList__
     */
    public void setSelPosLabelList(List<LabelValueBean> selPosLabelList) {
        selPosLabelList__ = selPosLabelList;
    }
    /**
     *
     * <br>[機  能] 進行条件モデル表示 初期化用 クラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class DspInitter {
        /**グループ選択 全件*/
        private List<LabelValueBean> allGrpLabelList__;
        /**役職選択 全件*/
        private List<LabelValueBean> allPosLabelList__;
        /** メッセージクラス*/
        private GsMessage gsMsg__;
        /**比較子 Util*/
        private EnumUtil<EnumCompOpr> enumCompUtil = new EnumUtil<EnumCompOpr>(EnumCompOpr.class);
        /**進行条件区分 Util*/
        private EnumUtil<EnumKeiroInConditionKbn> enumKbnUtil =
                new EnumUtil<EnumKeiroInConditionKbn>(EnumKeiroInConditionKbn.class);
        /**
         * コンストラクタ
         * @param allGrpLabelList グループ選択 全件
         * @param allPosLabelList 役職選択 全件
         * @param gsMsg メッセージクラス
         */
        public DspInitter(List<LabelValueBean> allGrpLabelList,
                List<LabelValueBean> allPosLabelList,
                GsMessage gsMsg) {
            super();
            this.gsMsg__ = gsMsg;
            this.allGrpLabelList__ = allGrpLabelList;
            this.allPosLabelList__ = allPosLabelList;
        }
        /**
         *
         * <br>[機  能] 表示要素の初期化
         * <br>[解  説]
         * <br>[備  考]
         * @param inCond 初期化対象 経路条件モデル
         */
        public void dspInit(KeiroInCondition inCond) {
            //経路条件区分
            inCond.getCondKbn().setDspSelectData(enumKbnUtil.getSelectList(gsMsg__));
            //グループ選択
            List<String> selGrpSid = Arrays.asList(inCond.getSelGrp().getSelected());
            inCond.setSelGrpLabelList(
                    allGrpLabelList__.stream()
                        .filter(lbl -> selGrpSid.contains(lbl.getValue()))
                        .collect(Collectors.toList())
                    );
            
            //役職選択
            List<String> selPosSid = Arrays.asList(inCond.getSelPos().getSelected());
            inCond.setSelPosLabelList(
                    allPosLabelList__.stream()
                        .filter(lbl -> selPosSid.contains(lbl.getValue()))
                        .collect(Collectors.toList())
                    );
            //フォーム値条件
            inCond.getComp().setDspSelectData(enumCompUtil.getSelectList(gsMsg__));
        }
    }
    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comp__ == null) ? 0 : comp__.hashCode());
        result = prime * result
                + ((condKbn__ == null) ? 0 : condKbn__.hashCode());
        result = prime * result
                + ((formId__ == null) ? 0 : formId__.hashCode());
        result = prime * result
                + ((formValue__ == null) ? 0 : formValue__.hashCode());
        result = prime * result
                + ((selGrp__ == null) ? 0 : selGrp__.hashCode());
        result = prime * result
                + ((selPos__ == null) ? 0 : selPos__.hashCode());
        return result;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof KeiroInCondition)) {
            return false;
        }
        KeiroInCondition other = (KeiroInCondition) obj;
        if (condKbn__ == null) {
            if (other.condKbn__ != null) {
                return false;
            }
        } else if (!condKbn__.equals(other.condKbn__)) {
            return false;
        }
        try {
            switch (
                    EnumKeiroInConditionKbn.valueOf(
                            Integer.parseInt(condKbn__.getSelected()))) {
                case CONDKBN_GROUP:
                    if (selGrp__ == null) {
                        if (other.selGrp__ != null) {
                            return false;
                        }
                    } else if (!selGrp__.equals(other.selGrp__)) {
                        return false;
                    }
                    break;
                case CONDKBN_POS:
                    if (selPos__ == null) {
                        if (other.selPos__ != null) {
                            return false;
                        }
                    } else if (!selPos__.equals(other.selPos__)) {
                        return false;
                    }
                    break;
                case CONDKBN_INPUT:
                    if (comp__ == null) {
                        if (other.comp__ != null) {
                            return false;
                        }
                    } else if (!comp__.equals(other.comp__)) {
                        return false;
                    }
                    if (StringUtil.isNullZeroString(formId__)) {
                        if (!StringUtil.isNullZeroString(other.formId__)) {
                            return false;
                        }
                    } else if (!formId__.equals(other.formId__)) {
                        return false;
                    }
                    if (StringUtil.isNullZeroString(formValue__)) {
                        if (!StringUtil.isNullZeroString(other.formValue__)) {
                            return false;
                        }
                    } else if (!formValue__.equals(other.formValue__)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        } catch (EnumOutRangeException e) {

        }
        return true;
    }

}