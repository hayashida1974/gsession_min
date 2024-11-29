package jp.groupsession.v2.cmn.formbuilder;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.model.RequestModel;
/**
 *
 * <br>[機  能] フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FormCell implements JSONString {
    /** 定数 入力必須*/
    public static final int VALUE_REQUIRE = 1;

    /** タイトル */
    private String title__;
    /** フォームSID*/
    private int sid__;
    /** フォームID */
    private String formID__;
    /** 要素種類 */
    private EnumFormModelKbn type__;
    /** 要素オブジェクト */
    private AbstractFormModel body__ = null;
    /** 入力必須フラグ */
    private int require__;
    /** FormCell 配置のroot要素  json出力対象外にすること  */
    private Block root__ = null;
    /** type未定時の仮の入力値 */
    private String[] inputedValue__;

    /**
     * デフォルトコンストラクタ
     */
    public FormCell() {

    }
    /**
     * <p>root を取得します。
     * @return root
     * @see jp.groupsession.v2.cmn.formbuilder.FormCell#root__
     */
    public Block getRoot() {
        return root__;
    }
    /**
     * <p>root をセットします。
     * @param root root
     * @see jp.groupsession.v2.cmn.formbuilder.FormCell#root__
     */
    public void setRoot(Block root) {
        root__ = root;
    }
    /**
     * コンストラクタ キーにEnumFormModelKbnの名称をもつラベルビーンから生成
     * @param label キーにEnumFormModelKbnの名称をもつラベルビーン
     */
    public FormCell(LabelValueBean label) {
        title__ = label.getLabel();
        type__ = EnumFormModelKbn.valueOf(label.getValue());
        body__ = AbstractFormModel.getInstance(type__);
        body__.setCellInfo(this);
    }
    /**
     * json 文字列から 要素を生成
     * @param json 初期化用JSON
     */
    public FormCell(String json) {
        try {
            __mergeJson(JSONObject.fromObject(json));
        } catch (JSONException e) {

        }
    }

    /**
     * json 文字列から 要素を生成
     * @param json 初期化用JSON
     */
    public FormCell(JSONObject json) {
        __mergeJson(json);
    }


    /**
     *
     * <br>[機  能] Jsonでクラスのマージ
     * <br>[解  説]
     * <br>[備  考]
     * @param json JSONObject
     */
    private void __mergeJson(JSONObject json) {
        try {
            title__ = json.getString("title");
        } catch (JSONException e) {

        }
        try {
            formID__ = json.getString("formID");
        } catch (JSONException e) {

        }
        try {
            sid__ = json.getInt("sid");
        } catch (JSONException e) {

        }
        try {
            type__ = EnumFormModelKbn.valueOf(json.getString("type"));
        } catch (JSONException e) {

        }
        _mergeBodyJson(type__, json.getJSONObject("body"));
        /** 入力必須フラグ */
        try {
            setRequire(json.getInt("require"));
        } catch (JSONException e) {

        }

    }
    /**
     *
     * <br>[機  能] body部のJSONのマージ
     * <br>[解  説] 継承することで設定用と保管用でボディオブジェクトを切り替えた実装が可能
     * <br>[備  考]
     * @param type フォーム区分
     * @param json JSON
     */
    protected void _mergeBodyJson(EnumFormModelKbn type, JSONObject json) {
        try {
            if (type != null) {
                body__ = AbstractFormModel.getInstance(type, json);
                body__.setCellInfo(this);
            }
        } catch (JSONException e) {

        }

    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>formID を取得します。
     * @return formID
     */
    public String getFormID() {
        return formID__;
    }
    /**
     * <p>formID をセットします。
     * @param formID formID
     */
    public void setFormID(String formID) {
        formID__ = formID;
    }
    /**
     * <p>type を取得します。
     * @return type
     */
    public EnumFormModelKbn getType() {
        return type__;
    }
    /**
     * <p>type をセットします。
     * @param type type
     */
    public void setType(EnumFormModelKbn type) {
        type__ = type;
    }
    /**
     * <p>body を取得します。
     * @return body
     */
    public AbstractFormModel getBody() {
        return body__;
    }
    /**
     * <p>body を取得します。
     * Bodyが空の場合、typeStrで初期化します
     * @param typeStr 型指定文字列
     * @return body
     */
    public AbstractFormModel getBody(String typeStr) {
        if (body__ == null) {
            type__ = EnumFormModelKbn.valueOf(typeStr);
            body__ = AbstractFormModel.getInstance(type__);
            body__.setCellInfo(this);
        } else if (type__ != null
                && type__ != EnumFormModelKbn.valueOf(typeStr)) {
            //すでに使用済みのformSidへの不正なアクセス
            return AbstractFormModel.getInstance(EnumFormModelKbn.valueOf(typeStr));
        }

        return body__;
    }

    /**
     * <p>body をセットします。
     * @param type type
     * @param body body
     */
    public void setBody(EnumFormModelKbn type, AbstractFormModel body) {
        if (type == null) {
            return;
        }
        type__ = type;
        body__ = body;
        body__.setCellInfo(this);
    }
    /**
     * <p>require を取得します。
     * @return require
     */
    public int getRequire() {
        return require__;
    }

    /**
     * <p>require をセットします。
     * @param require require
     */
    public void setRequire(int require) {
        require__ = require;
    }
    /**
     *
     * <br>[機  能] JSONで返す
     * <br>[解  説]
     * <br>[備  考]
     * @return JSON
     */
    public JSONObject toJson() {

        JSONObject ret = JSONObject.fromObject(this);

        return ret;
    }

    /**
     * <p>sid を取得します。
     * @return sid
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     *
     * <br>[機  能] 表示設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException sql実行時例外
     * @throws IOToolsException ディレクトリ作成時例外
     */
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {
        if (getType() == null) {
            return;
        }
        getBody().dspInit(reqMdl, con);
    }
    /**
    *
    * <br>[機  能] 入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors アクションエラー格納先
    * @param reqMdl リクエストモデル
    */
    public void validate(ActionErrors errors, RequestModel reqMdl) {
        validate(errors, reqMdl, null);
    }
    /**
    *
    * <br>[機  能] 入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors アクションエラー格納先
    * @param reqMdl リクエストモデル
    * @param info 表示用情報
    */
    public void validate(ActionErrors errors, RequestModel reqMdl, ValidateInfo info) {
        if (info == null) {
            info = new ValidateInfo();
        } else {
            info = info.child();
        }
        info.setSid(getSid());
        info.setFormID(getFormID());
        info.setRequire(getRequire());
        info.setTitle(getTitle());
        info.setType(getType());

        if (body__ != null) {
            body__.validateCheck(errors, reqMdl, info);
        }
    }
    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body__ == null) ? 0 : body__.hashCode());
        result = prime * result
                + ((formID__ == null) ? 0 : formID__.hashCode());
        result = prime * result + require__;
        result = prime * result + sid__;
        result = prime * result + ((title__ == null) ? 0 : title__.hashCode());
        result = prime * result + ((type__ == null) ? 0 : type__.hashCode());
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
        if (!(obj instanceof FormCell)) {
            return false;
        }
        FormCell other = (FormCell) obj;
        if (body__ == null) {
            if (other.body__ != null) {
                return false;
            }
        } else if (!body__.equals(other.body__)) {
            return false;
        }
        if (formID__ == null) {
            if (other.formID__ != null) {
                return false;
            }
        } else if (!formID__.equals(other.formID__)) {
            return false;
        }
        if (require__ != other.require__) {
            return false;
        }
        if (sid__ != other.sid__) {
            return false;
        }
        if (title__ == null) {
            if (other.title__ != null) {
                return false;
            }
        } else if (!title__.equals(other.title__)) {
            return false;
        }
        if (type__ != other.type__) {
            return false;
        }
        return true;
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param src   マージする要素
     * @param rowNo 行番号
     */
    public void merge(FormCell src, int rowNo) {
        if (this == src) {
            return;
        }
        if (src == null) {
            return;
        }
        if (!(src instanceof FormCell)) {
            return;
        }
        EnumFormModelKbn srcType = src.getType();
        AbstractFormModel srcBody = src.getBody();

        if (type__ == null && srcType != null && srcBody != null) {
            setBody(srcType, srcBody);
            if (inputedValue__ != null) {
                getBody().merge(inputedValue__);
            }
        }
        if (type__ != null) {
            if (srcBody != null && srcType == type__) {
                getBody().merge(srcBody);
            }
            if (src.inputedValue__ != null) {
                getBody().merge(src.inputedValue__);
            }
        }
    }
    /**
     * <p>inputedValue をセットします。
     * @param inputedValue inputedValue
     */
    public void setInputedValue(String[] inputedValue) {
        inputedValue__ = inputedValue;
    }

    /**
     *
     * <br>[機  能] 利用時に必須入力条件を満たすことができない申請内容がないことをチェックします
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラーメッセージ
     * @param reqMdl リクエストモデル
     * @param info 入力チェック情報保持オブジェクト
     */
    public void chkUnuseableInput(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        if (info == null) {
            info = new ValidateInfo();
        } else {
            info = info.child();
        }
        info.setSid(getSid());
        info.setFormID(getFormID());
        info.setRequire(getRequire());
        info.setTitle(getTitle());
        info.setType(getType());

        if (body__ != null) {
            body__.chkUnuseableInput(errors, reqMdl, info);
        }

    }

    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("sid", getSid());
        obj.put("formID", NullDefault.getString(getFormID(), ""));
        obj.put("title", NullDefault.getString(getTitle(), ""));
        obj.put("type", getType());
        obj.put("body", getBody());
        obj.put("require", getRequire());
        return obj.toString();
    }


}
