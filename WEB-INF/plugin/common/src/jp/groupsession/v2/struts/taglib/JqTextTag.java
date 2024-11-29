package jp.groupsession.v2.struts.taglib;

import org.apache.struts.taglib.html.TextTag;

/**
 * <br>[機  能] linkタグを作成する
 * <br>[解  説] 読み込むCSSファイルを設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public class JqTextTag extends TextTag {
    /** id */
    private String id__ = null;
    /**
     * @param handlers StringBuffer
     */
    protected void prepareOtherAttributes(StringBuffer handlers) {
        prepareAttribute(handlers, "id", getId());
    }
    /**
     * <p>id を取得します。
     * @return id
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(String id) {
        id__ = id;
    }


}
