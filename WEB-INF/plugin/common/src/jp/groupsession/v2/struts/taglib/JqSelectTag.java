package jp.groupsession.v2.struts.taglib;

import org.apache.struts.taglib.html.SelectTag;

/**
 * <br>[機  能] linkタグを作成する
 * <br>[解  説] 読み込むCSSファイルを設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public class JqSelectTag extends SelectTag {
    /** テーマパス */
    private String dataInline__ = null;

    /**
     * @param handlers StringBuffer
     */
    protected void prepareOtherAttributes(StringBuffer handlers) {
        prepareAttribute(handlers, "data-inline", getDataInline());
    }
    /**
     * <p>dataInline を取得します。
     * @return dataInline
     */
    public String getDataInline() {
        return dataInline__;
    }
    /**
     * <p>dataInline をセットします。
     * @param dataInline dataInline
     */
    public void setDataInline(String dataInline) {
        dataInline__ = dataInline;
    }

}
