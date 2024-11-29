package jp.groupsession.v2.cmn.formmodel;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
/**
 *
 * <br>[機  能] コメント要素モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Comment
extends AbstractFormModel {
    /**コメント*/
    String value__;

    /**タイトル表示 0:表示する 1:表示しない*/
    int notitle__;

    /**表示寄せ 0:上寄せ 1:下寄せ*/
    int valign__;

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
     * <p>notitle を取得します。
     * @return notitle
     */
    public int getNotitle() {
        return notitle__;
    }

    /**
     * <p>notitle をセットします。
     * @param notitle notitle
     */
    public void setNotitle(int notitle) {
        notitle__ = notitle;
    }

    /**
     * <p>valign を取得します。
     * @return valign
     */
    public int getValign() {
        return valign__;
    }

    /**
     * <p>valign をセットします。
     * @param valign valign
     */
    public void setValign(int valign) {
        valign__ = valign;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        if (json instanceof JSONObject) {
            try {
                setValue(((JSONObject) json).getString("value"));
                setNotitle(((JSONObject) json).getInt("notitle"));
                setValign(((JSONObject) json).getInt("valign"));
            } catch (JSONException e) {

            }
        }

    }

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + notitle__;
        result = prime * result + valign__;
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
        Comment other = (Comment) obj;
        if (notitle__ != other.notitle__) {
            return false;
        }
        if (valign__ != other.valign__) {
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

        return EnumFormModelKbn.label;
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
        if (!(obj instanceof Comment)) {
            return;
        }
        Comment other = (Comment) obj;
        if (StringUtil.isNullZeroString(getValue())) {
            setValue(other.getValue());
        }
    }
    @Override
    public void defaultInit() {

    }
    /**
     *
     * <br>[機  能] value を画面表示用 HTMLとして出力する
     * <br>[解  説]
     * <br>[備  考]
     * @return HTML
     */
    public String dspValueHTML() {
        return StringUtilHtml.transToHTml(
                NullDefault.getString(getValue(), ""));
    }
    @Override
    public String getCalced() {
        return getValue();
    }
}
