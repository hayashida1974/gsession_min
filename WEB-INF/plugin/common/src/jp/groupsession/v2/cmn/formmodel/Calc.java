package jp.groupsession.v2.cmn.formmodel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Objects;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormCalcBiz;
/**
 *
 * <br>[機  能] 集計要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Calc extends AbstractFormModel implements IAutoCalc {
    /** 計算式 書式  [${フォームID} +-/*  0-9]  */
    private Format format__ = new Format();

    /**計算式 文字列*/
    private String siki__;
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
     * <p>format を取得します。
     * @return format
     * @see jp.groupsession.v2.cmn.formmodel.Calc#format__
     */
    public Format getFormat() {
        return format__;
    }

    /**
     * <p>format をセットします。
     * @param format format
     * @see jp.groupsession.v2.cmn.formmodel.Calc#format__
     */
    public void setFormat(Format format) {
        format__ = format;
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
     * @see jp.groupsession.v2.cmn.formmodel.Calc#keta__
     */
    public String getKeta() {
        return keta__;
    }

    /**
     * <p>keta をセットします。
     * @param keta keta
     * @see jp.groupsession.v2.cmn.formmodel.Calc#keta__
     */
    public void setKeta(String keta) {
        keta__ = keta;
    }

    /**
     * <p>round を取得します。
     * @return round
     * @see jp.groupsession.v2.cmn.formmodel.Calc#round__
     */
    public int getRound() {
        return round__;
    }

    /**
     * <p>round をセットします。
     * @param round round
     * @see jp.groupsession.v2.cmn.formmodel.Calc#round__
     */
    public void setRound(int round) {
        round__ = round;
    }

    /**
     * <p>table を取得します。
     * @return table
     * @see jp.groupsession.v2.cmn.formmodel.Calc#table__
     */
    public int getTable() {
        return table__;
    }

    /**
     * <p>table をセットします。
     * @param table table
     * @see jp.groupsession.v2.cmn.formmodel.Calc#table__
     */
    public void setTable(int table) {
        table__ = table;
    }

    /**
     * <p>siki を取得します。
     * @return siki
     * @see jp.groupsession.v2.cmn.formmodel.Calc#siki__
     */
    public String getSiki() {
        return siki__;
    }

    /**
     * <p>siki をセットします。
     * @param siki siki
     * @see jp.groupsession.v2.cmn.formmodel.Calc#siki__
     */
    public void setSiki(String siki) {
        siki__ = siki;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".value", getValue());
        msgForm.addHiddenParam(paramName + ".siki", getSiki());
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
            setSiki(jsonObj.getString("siki"));
        } catch (JSONException e) {

        }
        try {
            JSONObject formatObj = jsonObj.getJSONObject("format");
            Format formObj = (Format) JSONObject.toBean(formatObj, Format.class);
            setFormat(formObj);
        } catch (JSONException e) {

        }

    }
    /**
     *
     * <br>[機  能] 計算式保管用 クラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Format {
        /** 計算式 +-×÷ 定数 ＋*/
        public static final String OPR_PLUS = "+";
        /** 計算式 +-×÷ 定数 ー*/
        public static final String OPR_MINUS = "-";
        /** 計算式 +-×÷ 定数 ＊*/
        public static final String OPR_MUL = "*";
        /** 計算式 +-×÷ 定数 /*/
        public static final String OPR_PER = "/";
        /** 計算式 () 定数*/
        public static final String OPR_LEFT = "(";
        /** 計算式 () 定数*/
        public static final String OPR_RIGHT = ")";
        /** 計算式 +-×÷*/
        private String siki__;
        /** 1 <：フォームID or -1：定数*/
        private int type__ = -1;
        /** 値*/
        private String value__;
        /**
         * <p>siki を取得します。
         * @return siki
         */
        public String getSiki() {
            return siki__;
        }
        /**
         * <p>siki をセットします。
         * @param siki siki
         */
        public void setSiki(String siki) {
            siki__ = siki;
        }
        /**
         * <p>type を取得します。
         * @return type
         */
        public int getType() {
            return type__;
        }
        /**
         * <p>type をセットします。
         * @param type type
         */
        public void setType(int type) {
            type__ = type;
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
        /* (非 Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((siki__ == null) ? 0 : siki__.hashCode());
            result = prime * result + type__;
            result = prime * result
                    + ((value__ == null) ? 0 : value__.hashCode());
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
            if (!(obj instanceof Format)) {
                return false;
            }
            Format other = (Format) obj;
            if (siki__ == null) {
                if (other.siki__ != null) {
                    return false;
                }
            } else if (!siki__.equals(other.siki__)) {
                return false;
            }
            if (type__ != other.type__) {
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

    }



    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (calcChangeFlg__ ? 1231 : 1237);
        result = prime * result
                + ((format__ == null) ? 0 : format__.hashCode());
        result = prime * result + ((keta__ == null) ? 0 : keta__.hashCode());
        result = prime * result + round__;
        result = prime * result + ((siki__ == null) ? 0 : siki__.hashCode());
        result = prime * result + table__;
        result = prime * result + ((tanni__ == null) ? 0 : tanni__.hashCode());
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
        Calc other = (Calc) obj;
        if (calcChangeFlg__ != other.calcChangeFlg__) {
            return false;
        }
        if (format__ == null) {
            if (other.format__ != null) {
                return false;
            }
        } else if (!format__.equals(other.format__)) {
            return false;
        }
        if (keta__ != other.keta__) {
            return false;
        }
        if (round__ != other.round__) {
            return false;
        }
        if (siki__ == null) {
            if (other.siki__ != null) {
                return false;
            }
        } else if (!siki__.equals(other.siki__)) {
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
        return EnumFormModelKbn.calc;
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
        if (!(obj instanceof Calc)) {
            return;
        }
        Calc other = (Calc) obj;
        if (getFormat() == null) {
            setFormat(other.getFormat());
        }
        if (StringUtil.isNullZeroString(getTanni())) {
            setTanni(other.getTanni());
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
    @Override
    public String getCalced() {
        return getValue();
    }
    @Override
    public void calc(FormCalcBiz biz) {
        try {
            String ret = biz.calc(getSiki(), Integer.parseInt(getKeta()), getRound(), getTable());
            if (!Objects.equals(ret, getValue())) {
                calcChangeFlg__ = true;
                setValue(ret);
            }
        } catch (NumberFormatException e) {
            setValue("");
        }
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
