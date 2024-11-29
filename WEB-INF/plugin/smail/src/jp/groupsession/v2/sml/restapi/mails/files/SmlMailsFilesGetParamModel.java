package jp.groupsession.v2.sml.restapi.mails.files;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] 添付ファイル情報API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlMailsFilesGetParamModel {
    /** メールSID */
    private int mailSid__;
    /** バイナリSID */
    private long binSid__;
    /**
     * <p>mailSid を取得します。
     * @return mailSid
     * @see jp.groupsession.v2.sml.restapi.mails.files.SmlMailsFilesGetParamModel#mailSid__
     */
    public int getMailSid() {
        return mailSid__;
    }
    /**
     * <p>mailSid をセットします。
     * @param mailSid mailSid
     * @see jp.groupsession.v2.sml.restapi.mails.files.SmlMailsFilesGetParamModel#mailSid__
     */
    public void setMailSid(int mailSid) {
        mailSid__ = mailSid;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     * @see jp.groupsession.v2.sml.restapi.mails.files.SmlMailsFilesGetParamModel#binSid__
     */
    public long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     * @see jp.groupsession.v2.sml.restapi.mails.files.SmlMailsFilesGetParamModel#binSid__
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }
}
