package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 単体選択用 共通抽象クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class SimpleSelectBase
extends AbstractFormModel
implements JSONString {

    /**選択対象*/
    private List<String> list__ = new ArrayList<>();
    /**初期選択*/
    private String defaultValue__;
    /** 入力値*/
    private String selected__;
    /**
     * デフォルトコンストラクタ
     */
    public SimpleSelectBase() {
        super();
    }

    /**
     * デフォルトコンストラクタ
     * @param json 初期化json
     */
    public SimpleSelectBase(JSONObject json) {
        super(json);
    }

    /**
     * <p>list を取得します。
     * @return list
     */
    public List<String> getList() {
        return list__;
    }

    /**
     * <p>list をセットします。
     * @param list list
     */
    public void setList(List<String> list) {
        list__ = list;
    }

    /**
     * <p>defaultValue を取得します。
     * @return defaultValue
     */
    public String getDefaultValue() {
        return defaultValue__;
    }

    /**
     * <p>defaultValue をセットします。
     * @param defaultValue defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        defaultValue__ = defaultValue;
    }

    /**
     * <p>value を取得します。
     * @return value
     */
    public String getSelected() {
        return selected__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setSelected(String value) {
        selected__ = value;
    }
    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".init", 0);
        msgForm.addHiddenParam(paramName + ".selected", getSelected());
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
        try {
            setSelected(jsonObj.getString("selected"));
        } catch (JSONException e) {

        }
        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
            return;
        }
        try {
            setDefaultValue(jsonObj.getString("defaultValue"));
        } catch (JSONException e) {

        }
        try {
            List<String> value = new ArrayList<>();
            for (Object valueJSON : jsonObj.getJSONArray("list")) {
                value.add(valueJSON.toString());
            }
            setList(value);
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
        /**選択対象*/
        List<String> list = getList();
        /**初期選択*/
        String defaultValue = getDefaultValue();
        /** 入力値*/
        String selected = getSelected();

        result = prime * result
                + ((defaultValue == null) ? 0 : defaultValue.hashCode());
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        result = prime * result
                + ((selected == null) ? 0 : selected.hashCode());
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
        if (!(obj instanceof SimpleSelectBase)) {
            return false;
        }
        SimpleSelectBase other = (SimpleSelectBase) obj;
        if (!Objects.equals(getDefaultValue(),
                other.getDefaultValue())) {
            return false;
        }
        if (!Objects.equals(getList(),
                other.getList())) {
            return false;
        }
        if (!Objects.equals(getSelected(),
                other.getSelected())) {
            return false;
        }
        return true;
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {

        super.dspInit(reqMdl, con);
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
        if (!(obj instanceof SimpleSelectBase)) {
            return;
        }
        SimpleSelectBase other = (SimpleSelectBase) obj;
        if (StringUtil.isNullZeroString(getDefaultValue())) {
            setDefaultValue(other.getDefaultValue());
        }
        if (CollectionUtils.isEmpty(getList())) {
            setList(other.getList());
        }
        if (StringUtil.isNullZeroString(getSelected())) {
            setSelected(other.getSelected());
        }
        int scrollY, otherScY;
        scrollY = getScrollY();
        otherScY = other.getScrollY();
        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }


    }
    @Override
    public void merge(String[] values) {
        if (getSelected() == null && !ArrayUtils.isEmpty(values)) {
            setSelected(values[0]);
        }
    }

    @Override
    public void defaultInit() {
        setSelected(getDefaultValue());
    }
    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("defaultValue", getDefaultValue());
        obj.put("selected", getSelected());
        obj.put("list", getList());
        return obj.toString();
    }
    @Override
    public String getCalced() {
        return getSelected();
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean empty = StringUtil.isNullZeroString(getSelected());
        if (info.chkRequire()) {
            //未入力チェック
            if (empty) {
                msg = new ActionMessage("error.select.required.text",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
            }
        }
        if (!empty) {
            List<String> list = getList();
            if (list != null && !list.isEmpty()) {
                if (!list.contains(getSelected())) {
                    msg = new ActionMessage("search.data.notfound",
                            info.outputName(gsMsg));
                    errors.add(info.getPath(), msg);
                    return;
                }
            }
        }
    }
}