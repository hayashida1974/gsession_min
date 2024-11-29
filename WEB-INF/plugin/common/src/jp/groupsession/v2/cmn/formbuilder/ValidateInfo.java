package jp.groupsession.v2.cmn.formbuilder;


import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.beanutils.BeanUtils;


/**
 *
 * <br>[機  能] バリデート結果メッセージの表示情報を扱う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ValidateInfo implements Cloneable {
    /**Sid*/
    int sid__;
    /** Path */
    private String path__ = "";
    /** title */
    private String title__ = "";
    /** フォームID */
    private String formID__;
    /** 要素種類 */
    private EnumFormModelKbn type__;
    /** 入力必須フラグ */
    private int require__;
    /** 行番号*/
    int rowNo__ = 0;
    /** 草稿モード */
    private int sokoMode__ = 0;

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
     * <p>path に文字列を追加します。
     * @param path path
     */
    public void addPath(String path) {
        path__ = path__ + " " + path;
    }
    /**
     * <p>title に行番号を追加します。
     * @param rowNo 行番号
     * @param gsMsg GsMessage
     */
    public void addPathRowNo(int rowNo, GsMessage gsMsg) {
        rowNo__ = rowNo;
        addPath(gsMsg.getMessageVal0("cmn.line", String.valueOf(rowNo)));
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
    @Override
    public ValidateInfo clone() {
        ValidateInfo ret = new ValidateInfo();
        try {
            BeanUtils.copyProperties(ret, this);
        } catch (Exception e) {
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 子階層用のValidateInfoの生成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 子要素
     */
    public ValidateInfo child() {
        ValidateInfo ret = this.clone();
        ret.addPath(this.getTitle());
        ret.title__ = "";
        return ret;
    }
    /**
     * <p>path を取得します。
     * @return path
     */
    public String getPath() {
        return path__;
    }
    /**
     * <p>path をセットします。
     * @param path path
     */
    public void setPath(String path) {
        path__ = path;
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
     * <br>[機  能] バリデート時に表示要素名を出力
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @return 表示名
     */
    public String outputName(GsMessage gsMsg) {
        StringBuilder sb = new StringBuilder();
        String name = getTitle();
        EnumFormModelKbn type = getType();
        if (StringUtil.isNullZeroString(name) && type != null) {
            name = gsMsg.getMessage(type.getMsgKey());
        }
        String path = getPath();
        path = path.trim();
        sb.append(path);
        if (path.length() > 0) {
            sb.append(" ");
        }
        sb.append(name);
        return sb.toString();
    }
    /**
    *
    * <br>[機  能] バリデート時の要素コード（非表示）を出力
    * <br>[解  説]
    * <br>[備  考]
    * @return コード
    */
    public String outputCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(sid__);
        sb.append("_");
        sb.append(rowNo__);
        sb.append("_form");
        return sb.toString();
    }
    /**
     * <p>sokoMode を取得します。
     * @return sokoMode
     * @see jp.groupsession.v2.cmn.formbuilder.ValidateInfo#sokoMode__
     */
    public int getSokoMode() {
        return sokoMode__;
    }
    /**
     * <p>sokoMode をセットします。
     * @param sokoMode sokoMode
     * @see jp.groupsession.v2.cmn.formbuilder.ValidateInfo#sokoMode__
     */
    public void setSokoMode(int sokoMode) {
        sokoMode__ = sokoMode;
    }
    /**
     *
     * <br>[機  能] 草稿モードと必須フラグで入力チェックの必要性を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 入力必須チェックの必要性
     */
    public boolean chkRequire() {
        if (getSokoMode() == 1) {
            return false;
        }
        return (getRequire() == 1);
    }
}
