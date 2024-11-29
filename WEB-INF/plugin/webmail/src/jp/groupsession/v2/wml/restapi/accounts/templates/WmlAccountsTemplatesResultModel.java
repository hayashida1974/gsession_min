package jp.groupsession.v2.wml.restapi.accounts.templates;

import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.wml.restapi.model.TmpFileInfo;

/**
 * <br>[機  能] WEBメール テンプレート一覧取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsTemplatesResultModel {

    /** テンプレートSID */
    private Integer sid__ = 0;
    /** テンプレートタイプ（未開封：0 開封済み：1） */
    private Integer type__ = 0;
    /** テンプレート名称 */
    private String name__ = null;
    /** 件名 */
    private String subjectText__ = null;
    /** 本文 */
    private String bodyText__ =null;
    /** 添付ファイル情報 */
    @ChildElementName("tmpFileInfo")
    private List<TmpFileInfo> tmpFileArray__ = null;
    
    public Integer getSid() {
        return sid__;
    }
    public void setSid(Integer sid) {
        sid__ = sid;
    }
    public Integer getType() {
        return type__;
    }
    public void setType(Integer type) {
        type__ = type;
    }
    public String getName() {
        return name__;
    }
    public void setName(String name) {
        name__ = name;
    }
    public String getSubjectText() {
        return subjectText__;
    }
    public void setSubjectText(String subjectText) {
        subjectText__ = subjectText;
    }
    public String getBodyText() {
        return bodyText__;
    }
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }
    public List<TmpFileInfo> getTmpFileArray() {
        return tmpFileArray__;
    }
    public void setTmpFileArray(List<TmpFileInfo> tmpFileArray) {
        tmpFileArray__ = tmpFileArray;
    }
}
