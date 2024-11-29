package jp.groupsession.v2.cmn.restapi.groups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.restapi.response.ResultElement;
/**
 *
 * <br>[機  能] グループ情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnGroupsAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] グループ情報API
    * <br>[解  説] GET
    * <br>[備  考] グループ情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param paramModel パラメータモデル
    * @param ctx RestApiコンテキスト
     * @throws SQLException
    */
   @Get
   public void doGet(HttpServletRequest req,
           HttpServletResponse res,
           CmnGroupsGetParamModel paramModel,
           RestApiContext ctx) throws SQLException {

       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);

       //マイグループ一覧取得
       if (paramModel.getWithMygroupFlg() == GSConstCommon.RESTAPI_GROUPS_MYGROUP_IN) {
           List<CmnMyGroupModel> cmgList = null;
           CmnMyGroupDao cmgDao = new CmnMyGroupDao(ctx.getCon());
           cmgList = cmgDao.getMyGroupList(ctx.getRequestUserModel().getUsrsid());
           for (CmnMyGroupModel cmgModel : cmgList) {
               ResultElement result = new ResultElement("result");
               result.addContent(new ResultElement("groupType").addContent("1"));
               result.addContent(new ResultElement("groupSid").addContent(
                       String.valueOf(cmgModel.getMgpSid())));
               result.addContent(new ResultElement("groupId").addContent(""));
               result.addContent(new ResultElement("groupName").addContent(cmgModel.getMgpName()));
               result.addContent(new ResultElement("defaultGrpFlg").addContent(
                       String.valueOf(GSConstCommon.RESTAPI_GROUPS_DEFAULTGROUP_NO)));
               result.addContent(new ResultElement("classLevelNum").addContent(String.valueOf(1)));
               builder.addResult(result);
           }
       }

       //グループ一覧取得
       ArrayList<GroupModel> grpList = null;
       GroupBiz grpBiz = new GroupBiz();
       grpList = grpBiz.getGroupList(ctx.getCon());

       //デフォルトグループ取得
       String  defGpSid = null;
       GroupBiz gbiz = new GroupBiz();
       int defGroupSid = gbiz.getDefaultGroupSid(
               ctx.getRequestUserModel().getUsrsid(), ctx.getCon());
       if (defGroupSid >= 0) {
           defGpSid = String.valueOf(defGroupSid);
       }

       for (GroupModel grpModel : grpList) {
           ResultElement result = new ResultElement("result");
           result.addContent(new ResultElement("groupType").addContent("0"));
           result.addContent(new ResultElement("groupSid").addContent(
                   String.valueOf(grpModel.getGroupSid())));
           result.addContent(new ResultElement("groupId").addContent(
                   String.valueOf(grpModel.getGroupId())));
           result.addContent(new ResultElement("groupName").addContent(grpModel.getGroupName()));
           if (defGpSid != null && defGpSid.equals(String.valueOf(grpModel.getGroupSid()))) {
               result.addContent(new ResultElement("defaultGrpFlg").addContent(
                       String.valueOf(GSConstCommon.RESTAPI_GROUPS_DEFAULTGROUP_YES)));
           } else {
               result.addContent(new ResultElement("defaultGrpFlg").addContent(
                       String.valueOf(GSConstCommon.RESTAPI_GROUPS_DEFAULTGROUP_NO)));
           }
           result.addContent(new ResultElement("classLevelNum").addContent(
                   String.valueOf(grpModel.getClassLevel())));
           builder.addResult(result);
       }
       builder.build().execute();
   }
}