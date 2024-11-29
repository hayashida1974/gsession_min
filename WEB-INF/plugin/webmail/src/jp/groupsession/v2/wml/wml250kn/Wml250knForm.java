package jp.groupsession.v2.wml.wml250kn;

import jp.groupsession.v2.wml.wml250.Wml250Form;

/**
 * <br>[機  能] WEBメール メールテンプレート登録確認画面で使用するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml250knForm extends Wml250Form {

    /** 内容(表示用) */
    private String wml250knViewContent__ = null;
    /** 添付ファイルID */
    private String wml250knFileId__ = null;

    /**
     * <p>wml250knViewContent を取得します。
     * @return wml250knViewContent
     */
    public String getWml250knViewContent() {
        return wml250knViewContent__;
    }

    /**
     * <p>wml250knViewContent をセットします。
     * @param wml250knViewContent wml250knViewContent
     */
    public void setWml250knViewContent(String wml250knViewContent) {
        wml250knViewContent__ = wml250knViewContent;
    }

    /**
     * <p>wml250knFileId を取得します。
     * @return wml250knFileId
     */
    public String getWml250knFileId() {
        return wml250knFileId__;
    }

    /**
     * <p>wml250knFileId をセットします。
     * @param wml250knFileId wml250knFileId
     */
    public void setWml250knFileId(String wml250knFileId) {
        wml250knFileId__ = wml250knFileId;
    }
}
