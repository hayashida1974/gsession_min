package jp.groupsession.v2.wml.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.restapi.WmlEnumReasonCode;

/**
 * <br>[機  能] WEBメールのRESTAPI アカウント関連のビジネスロジッククラスです
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlRestAccountBiz {

    /**
     * <br>[機  能] アカウントIDからアカウントSIDを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountId アカウントID
     * @return アカウントSID
     */
    public int getWmlAccountSid(Connection con, String accountId) throws SQLException {

        WmlAccountDao wacDao = new WmlAccountDao(con);
        int ret = wacDao.getAccountSid(accountId);

        return ret;
    }

    /**
     * <br>[機  能] ユーザが指定したアカウントを使用可能かをチェックし、使用不可能であればアカウントエラーをthrowします。
     * <br>[解  説]
     * <br>[備  考] エラーコードはWEBMAIL-101です
     * @param ctx RestApiコンテキスト
     * @param accountId WEBメールアカウントID
     */
    public void validateAccount(RestApiContext ctx, String accountId) throws SQLException {

        boolean useableFlg = canUseAccount(ctx, accountId);

        //使用可能チェック
        if (!useableFlg) {
            throw new RestApiPermissionException(
                WmlEnumReasonCode.RESOURCE_CANT_ACCESS_ACCOUNT,
                "search.data.notfound",
                new GsMessage(ctx.getRequestModel())
                    .getMessage("wml.102")
            );
        }
    }

    /**
     * <br>[機  能] ユーザが指定したアカウントを使用可能かをチェックします
     * <br>[解  説]
     * <br>[備  考] エラーコードはWEBMAIL-101です
     * @param ctx RestApiコンテキスト
     * @param accountId WEBメールアカウントID
     * @return true:使用可能, false:使用不可能
     */
    public boolean canUseAccount(RestApiContext ctx, String accountId) throws SQLException {

        Connection con = ctx.getCon();
        int userSid = ctx.getRequestUserSid();

        //WEBメールアカウントSIDの取得
        int wacSid = getWmlAccountSid(con, accountId);
        WmlDao dao = new WmlDao(con);

        //アカウントを使用可能かの判定
        boolean ret = dao.canUseAccount(wacSid, userSid);

        return ret;
    }

    

}
