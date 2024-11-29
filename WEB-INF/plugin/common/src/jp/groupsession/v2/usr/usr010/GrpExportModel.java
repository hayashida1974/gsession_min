package jp.groupsession.v2.usr.usr010;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 *
 * <br>[機  能] グループ情報一覧の出力用Model
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GrpExportModel extends AbstractModel {

    /** グループID */
    private String grpId__;
    /** グループ名 */
    private String grpName__;
    /** グループ名カナ */
    private String grpNameKn__;
    /** 親グループID */
    private String parentGpId__;
    /** コメント */
    private String grpComment__;
    /**
     * <p>grpId を取得します。
     * @return grpId
     */
    public String getGrpId() {
        return grpId__;
    }
    /**
     * <p>grpId をセットします。
     * @param grpId grpId
     */
    public void setGrpId(String grpId) {
        grpId__ = grpId;
    }
    /**
     * <p>grpName を取得します。
     * @return grpName
     */
    public String getGrpName() {
        return grpName__;
    }
    /**
     * <p>grpName をセットします。
     * @param grpName grpName
     */
    public void setGrpName(String grpName) {
        grpName__ = grpName;
    }
    /**
     * <p>grpNameKn を取得します。
     * @return grpNameKn
     */
    public String getGrpNameKn() {
        return grpNameKn__;
    }
    /**
     * <p>grpNameKn をセットします。
     * @param grpNameKn grpNameKn
     */
    public void setGrpNameKn(String grpNameKn) {
        grpNameKn__ = grpNameKn;
    }
    /**
     * <p>parentGpId を取得します。
     * @return parentGpId
     */
    public String getParentGpId() {
        return parentGpId__;
    }
    /**
     * <p>parentGpId をセットします。
     * @param parentGpId parentGpId
     */
    public void setParentGpId(String parentGpId) {
        parentGpId__ = parentGpId;
    }
    /**
     * <p>grpComment を取得します。
     * @return grpComment
     */
    public String getGrpComment() {
        return grpComment__;
    }
    /**
     * <p>grpComment をセットします。
     * @param grpComment grpComment
     */
    public void setGrpComment(String grpComment) {
        grpComment__ = grpComment;
    }

}