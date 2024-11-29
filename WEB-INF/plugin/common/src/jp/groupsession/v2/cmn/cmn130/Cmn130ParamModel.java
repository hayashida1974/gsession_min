package jp.groupsession.v2.cmn.cmn130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.MyGroupModel;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] メイン 個人設定 マイグループ設定画面のパラメータ、出力値を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn130ParamModel extends AbstractParamModel {

    //入力項目
    /** マイグループSID */
    private String[] cmn130delGroupSid__;

    //表示項目
    /** マイグループリスト */
    private List<MyGroupModel> cmn130GroupList__ = null;
    /** 共有マイグループリスト */
    private List<MyGroupModel> cmn130SharedGroupList__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException
     */
    public ActionErrors validateCheck(
        HttpServletRequest req,
        Connection con,
        int userSid) throws SQLException {

        ActionErrors errors = new ActionErrors();

        //削除するマイグループSID
        GSValidateCommon.validateDeleteMyGroup(errors, cmn130delGroupSid__, req, con, userSid);

        return errors;
    }

    /**
     * <p>cmn130GroupList を取得します。
     * @return cmn130GroupList
     */
    public List<MyGroupModel> getCmn130GroupList() {
        return cmn130GroupList__;
    }

    /**
     * <p>cmn130GroupList をセットします。
     * @param cmn130GroupList cmn130GroupList
     */
    public void setCmn130GroupList(List<MyGroupModel> cmn130GroupList) {
        cmn130GroupList__ = cmn130GroupList;
    }

    /**
     * <p>cmn130delGroupSid を取得します。
     * @return cmn130delGroupSid
     */
    public String[] getCmn130delGroupSid() {
        return cmn130delGroupSid__;
    }

    /**
     * <p>cmn130delGroupSid をセットします。
     * @param cmn130delGroupSid cmn130delGroupSid
     */
    public void setCmn130delGroupSid(String[] cmn130delGroupSid) {
        cmn130delGroupSid__ = cmn130delGroupSid;
    }

    /**
     * <p>cmn130SharedGroupList を取得します。
     * @return cmn130SharedGroupList
     */
    public List<MyGroupModel> getCmn130SharedGroupList() {
        return cmn130SharedGroupList__;
    }

    /**
     * <p>cmn130SharedGroupList をセットします。
     * @param cmn130SharedGroupList cmn130SharedGroupList
     */
    public void setCmn130SharedGroupList(List<MyGroupModel> cmn130SharedGroupList) {
        cmn130SharedGroupList__ = cmn130SharedGroupList;
    }
}