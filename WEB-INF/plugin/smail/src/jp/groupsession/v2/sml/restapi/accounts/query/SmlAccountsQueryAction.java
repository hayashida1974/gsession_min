package jp.groupsession.v2.sml.restapi.accounts.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アカウントリスト情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("smail")
public class SmlAccountsQueryAction extends AbstractRestApiAction {

    /**
     * <br>[機  能] アカウントリスト情報API
     * <br>[解  説] POST
     * <br>[備  考] 設定情報を取得する
     * @param res サーブレットレスポンス
     * @param param パラメータモデル
     * @param ctx APIコンテキスト
     * @throws SQLException
     */
    @Post
    public void doPost(
            HttpServletResponse res,
            SmlAccountsQueryPostParamModel param,
            RestApiContext ctx) throws SQLException {
        boolean smlAccountFlg = false;
        List<UsrLabelValueBean> userList = new ArrayList<UsrLabelValueBean>();
        UserBiz userBiz = new UserBiz();
        SmlAccountsQueryDao queryDao = new SmlAccountsQueryDao(ctx.getCon());
        SmlBanDestDao sbdDao = new SmlBanDestDao(ctx.getCon());

        //ショートメールプラグインアクセス権限確認
        List<String> pluginList = new ArrayList<String>();
        pluginList.add(GSConst.PLUGINID_SML);
        if (!canAccessPlugin(pluginList)) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "error.cant.use.plugin",
                    new GsMessage(ctx.getRequestModel()).getMessage("cmn.shortmail")
                    );
        }
        List<SmlAccountsQueryPostResultModel> accountList
                                = new ArrayList<SmlAccountsQueryPostResultModel>();

        // アクセス不可代表アカウント
        int requestUserSid = ctx.getRequestUserSid();
        List<Integer> banSmlAccountSidList = sbdDao.getBanDestAccSidList(requestUserSid);
        // アクセス不可ユーザアカウント
        List<Integer> banUsrAccountSidList = sbdDao.getBanDestUsrSidList(requestUserSid);

        // 代表アカウントのみ
        if (param.getSharedOnlyFlg() == 1) {
            // 除外リスト
            smlAccountFlg = true;
        }

        // グループID指定
        boolean existsUser = true;
        if (param.getGroupId() != null) {
            //グループIDからSID取得
            CmnGroupmDao dao = new CmnGroupmDao(ctx.getCon());
            CmnGroupmModel mdl = dao.getGroupInf(param.getGroupId());
            if (mdl == null) {
                throw new RestApiPermissionException(
                        EnumError.PARAM_IMPERMISSIBLE,
                        "search.data.notfound",
                        new GsMessage(ctx.getRequestModel()).getMessage("cmn.group.id")
                        );
            }
            userList = userBiz.getUserLabelList(
                    ctx.getCon(), mdl.getGrpSid(), null);
            existsUser = userList.size() > 0;
        }

        // マイグループSID指定
        if (param.getMyGroupSid() != -1) {
            List<UsrLabelValueBean> myGroupUserList = userBiz.getMyGroupUserLabelList(
                    ctx.getCon(), requestUserSid,
                    param.getMyGroupSid(), null);
            if (userList.size() > 0) {
                //グループSID指定 + マイグループSID指定
                //重複するユーザのみ対象とする。
                ArrayList<String> addUserList = new ArrayList<String>();
                for (UsrLabelValueBean usrBean : userList) {
                    addUserList.add(usrBean.getValue());
                }
                userList = new ArrayList<UsrLabelValueBean>();
                for (UsrLabelValueBean usrBean : myGroupUserList) {
                    if (addUserList.contains(usrBean.getValue())) {
                        userList.add(usrBean);
                    }
                }
            } else {
                //マイグループSID指定のみ
                for (UsrLabelValueBean usrBean : myGroupUserList) {
                    userList.add(usrBean);
                }
            }
            existsUser = userList.size() > 0;
        }

        int max = 0;
        if (existsUser) {
            // グループSID指定/マイグループSID指定で取得したユーザ一覧からアクセス不可ユーザを除外する
            for (int i = userList.size() - 1; i >= 0; i--) {
                UsrLabelValueBean bean = userList.get(i);
                if (bean.getValue() != null
                 && banUsrAccountSidList.contains(Integer.valueOf(bean.getValue()))) {
                    userList.remove(bean);
                }
            }
            // ユーザアカウント ソート用モデル
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(ctx.getCon());
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            max = queryDao.count(userList, banSmlAccountSidList,
                    banUsrAccountSidList, param.getAccountSidArray(), smlAccountFlg);
            if (param.getOffset() <= max) {
                accountList = queryDao.smlAccountList(userList, banSmlAccountSidList,
                                        banUsrAccountSidList, param.getAccountSidArray(),
                                        smlAccountFlg, sortMdl,
                                        param.getLimit(), param.getOffset());
            }
        }

        RestApiResponseWriter.builder(res, ctx)
        .setMax(max)
        .addResultList(accountList)
        .build().execute();
    }
}
