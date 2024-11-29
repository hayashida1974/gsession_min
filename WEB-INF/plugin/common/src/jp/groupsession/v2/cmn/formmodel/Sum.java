package jp.groupsession.v2.cmn.formmodel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormCalcBiz;
import jp.groupsession.v2.cmn.formmodel.Calc.Format;

/**
 *
 * <br>[機  能] 合計要素 モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sum extends AbstractFormModel implements IAutoCalc {
    /** 計算対象フォームID */
    private List<Format> target__ = new ArrayList<Calc.Format>(Arrays.asList(new Format()));
    /** 単位 */
    private String tanni__;
    /** 計算結果*/
    private String value__;
    /** 小数点以下桁*/
    private String keta__ = "0";
    /** 丸めこみ*/
    private int round__;
    /** 同行内での計算制限*/
    private int table__;
    /** 計算変更フラグ*/
    private boolean calcChangeFlg__ = false;


    /**
     * <p>target を取得します。
     * @return target
     */
    public List<Format> getTarget() {
        return target__;
    }

    /**
     * <p>target をセットします。
     * @param target target
     */
    public void setTarget(List<Format> target) {
        target__ = target;
    }

    /**
     * <p>tanni を取得します。
     * @return tanni
     */
    public String getTanni() {
        return tanni__;
    }

    /**
     * <p>tanni をセットします。
     * @param tanni tanni
     */
    public void setTanni(String tanni) {
        tanni__ = tanni;
    }

    /**
     * <p>keta を取得します。
     * @return keta
     * @see jp.groupsession.v2.cmn.formmodel.Sum#keta__
     */
    public String getKeta() {
        return keta__;
    }

    /**
     * <p>keta をセットします。
     * @param keta keta
     * @see jp.groupsession.v2.cmn.formmodel.Sum#keta__
     */
    public void setKeta(String keta) {
        keta__ = keta;
    }

    /**
     * <p>round を取得します。
     * @return round
     * @see jp.groupsession.v2.cmn.formmodel.Sum#round__
     */
    public int getRound() {
        return round__;
    }

    /**
     * <p>round をセットします。
     * @param round round
     * @see jp.groupsession.v2.cmn.formmodel.Sum#round__
     */
    public void setRound(int round) {
        round__ = round;
    }

    /**
     * <p>table を取得します。
     * @return table
     * @see jp.groupsession.v2.cmn.formmodel.Sum#table__
     */
    public int getTable() {
        return table__;
    }

    /**
     * <p>table をセットします。
     * @param table table
     * @see jp.groupsession.v2.cmn.formmodel.Sum#table__
     */
    public void setTable(int table) {
        table__ = table;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".value", getValue());
    }

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
        }
        if (jsonObj == null) {
            return;
        }

        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
            return;
        }
        try {
            setTanni(jsonObj.getString("tanni"));
        } catch (JSONException e) {

        }
        try {
            setKeta(jsonObj.getString("keta"));
        } catch (JSONException e) {

        }
        try {
            setRound(jsonObj.getInt("round"));
        } catch (JSONException e) {

        }
        try {
            setTable(jsonObj.getInt("table"));
        } catch (JSONException e) {

        }

        try {
            ArrayList<Format> format = new ArrayList<>();
            JSONArray array = jsonObj.getJSONArray("target");

            for (int i = 0; i < array.size(); i++) {
                JSONObject formatObj = array.getJSONObject(i);
                Format formObj = (Format) JSONObject.toBean(formatObj, Format.class);
                format.add(formObj);
            }
            setTarget(format);
        } catch (JSONException e) {

        }

    }


    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (calcChangeFlg__ ? 1231 : 1237);
        result = prime * result + ((keta__ == null) ? 0 : keta__.hashCode());
        result = prime * result + round__;
        result = prime * result + table__;
        result = prime * result + ((tanni__ == null) ? 0 : tanni__.hashCode());
        result = prime * result
                + ((target__ == null) ? 0 : target__.hashCode());
        result = prime * result + ((value__ == null) ? 0 : value__.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        Sum other = (Sum) obj;
        if (calcChangeFlg__ != other.calcChangeFlg__) {
            return false;
        }
        if (keta__ != other.keta__) {
            return false;
        }
        if (round__ != other.round__) {
            return false;
        }
        if (table__ != other.table__) {
            return false;
        }
        if (tanni__ == null) {
            if (other.tanni__ != null) {
                return false;
            }
        } else if (!tanni__.equals(other.tanni__)) {
            return false;
        }
        if (target__ == null) {
            if (other.target__ != null) {
                return false;
            }
        } else if (!target__.equals(other.target__)) {
            return false;
        }
        if (value__ == null) {
            if (other.value__ != null) {
                return false;
            }
        } else if (!value__.equals(other.value__)) {
            return false;
        }
        return true;
    }

    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.sum;
    }
    @Override
    public void merge(AbstractFormModel obj) {
        super.merge(obj);

        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        if (!(obj instanceof Sum)) {
            return;
        }
        Sum other = (Sum) obj;
        if (StringUtil.isNullZeroString(getTanni())) {
            setTanni(other.getTanni());
        }
        if (CollectionUtils.isEmpty(getTarget())) {
            setTarget(other.getTarget());
        }
        int scrollY, otherScY;
        scrollY = getScrollY();
        otherScY = other.getScrollY();
        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }
    }
    @Override
    public void defaultInit() {
    }
    @Override
    public void calc(FormCalcBiz biz) {
        try {
            String ret = biz.sum(getTarget(), Integer.parseInt(getKeta()), getRound(), getTable());
            if (!Objects.equals(ret, getValue())) {
                calcChangeFlg__ = true;
                setValue(ret);
            }
        } catch (NumberFormatException  e) {
            setValue("");
        }

    }
    @Override
    public String getCalced() {
        return getValue();
    }

    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }
    /**
    *
    * <br>[機  能] 表示用にフォーマットされた値を返します
    * <br>[解  説] 3桁カンマ区切りされて返します
    * <br>[備  考]
    * @return カンマフォーマットされた値
    */
    public String dspValueHTML() {
       String value = getValue();
       if (StringUtil.isNullZeroString(value)) {
           return "";
       }
       try {
           BigDecimal bd = new BigDecimal(value);
           NumberFormat nf =  NumberFormat.getNumberInstance();
           nf.setMaximumFractionDigits(bd.scale());
           return nf.format(bd);
       } catch (NumberFormatException e__) {
           return "";
       }
     }
}
