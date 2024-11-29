package jp.groupsession.v2.convert.convert482.dao;

/**
 *
 * <br>[機  能] v4.9.0へコンバートする際に使用する稟議テンプレートフォームモデルです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TemplateFormWithAttachModel {


    /** テンプレートSID */
    private int templateSid__;
    /** テンプレートバージョン */
    private int templateVer__;
    /** フォームSID */
    private int formSid__;
    /** フォームID */
    private String formId__;
    /** バイナリSID */
    private Long binSid__;


    /**
     * templateSidを取得します。
     * @return templateSid
     * */
    public int getTemplateSid() {
        return templateSid__;
    }
    /**
     * templateSidをセットします。
     * @param templateSid templateSid
     * */
    public void setTemplateSid(int templateSid) {
        templateSid__ = templateSid;
    }

    /**
    * templateVerを取得します。
    * @return templateVer
    * */
   public int getTemplateVer() {
       return templateVer__;
   }
   /**
    * templateVerをセットします。
    * @param templateVer templateVer
    * */
   public void setTemplateVer(int templateVer) {
       templateVer__ = templateVer;
   }


    /**
     * formSidを取得します。
     * @return formSid
     * */
    public int getFormSid() {
        return formSid__;
    }
    /**
     * formSidをセットします。
     * @param formSid formSid
     * */
    public void setFormSid(int formSid) {
        formSid__ = formSid;
    }

    /**
     * formIdを取得します。
     * @return formId
     * */
    public String getFormId() {
        return formId__;
    }
    /**
     * formIdをセットします。
     * @param formId formId
     * */
    public void setFormId(String formId) {
        formId__ = formId;
    }

    /**
     * binSidを取得します。
     * @return binSid
     * */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * binSidをセットします。
     * @param binSid binSid
     * */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

}
