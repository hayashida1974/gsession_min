package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] フィルター条件情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MailFilterConditionModel extends AbstractModel {

    /** 条件種別 */
    private int type__ = 0;
    /** 条件式 */
    private int expression__ = 0;
    /** 条件 */
    private String text__ = null;
    /**
     * <p>expression を取得します。
     * @return expression
     */
    public int getExpression() {
        return expression__;
    }
    /**
     * <p>expression をセットします。
     * @param expression expression
     */
    public void setExpression(int expression) {
        expression__ = expression;
    }
    /**
     * <p>text を取得します。
     * @return text
     */
    public String getText() {
        return text__;
    }
    /**
     * <p>text をセットします。
     * @param text text
     */
    public void setText(String text) {
        text__ = text;
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
}
