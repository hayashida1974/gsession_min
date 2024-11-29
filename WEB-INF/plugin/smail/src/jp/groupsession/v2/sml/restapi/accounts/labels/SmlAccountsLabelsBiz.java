package jp.groupsession.v2.sml.restapi.accounts.labels;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.sml310.Sml310Biz;
import jp.groupsession.v2.sml.sml310.Sml310ParamModel;
import jp.groupsession.v2.sml.sml320kn.Sml320knBiz;
import jp.groupsession.v2.sml.sml320kn.Sml320knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsLabelsBiz extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] アカウント 使用可能判定
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param accountSid アカウントSID
    * @param sessionUserSid セッションユーザSID
    * @throws SQLException
    */
    public void validateAccount(Connection con, RequestModel reqMdl,
           int accountSid, int sessionUserSid) throws SQLException {

        // 選択されているアカウントが使用可能かを判定する
        SmailDao smlDao = new SmailDao(con);
        if (accountSid <= 0 || !smlDao.canUseCheckAccount(sessionUserSid, accountSid)) {
            // アカウントが存在しない場合
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.account")
                    );
        }
    }
    /**
    *
    * <br>[機  能] ラベル登録処理
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param accountSid アカウントSID
    * @param sessionUserSid セッションユーザSID
    * @param labelName ラベル名
    * @return ラベルSID
    * @throws Exception
    * @throws SQLException
    * @throws IOToolsException
    */
    public int addLabel(Connection con, RequestModel reqMdl,
           int accountSid, int sessionUserSid, String labelName)
                   throws Exception, SQLException, IOToolsException {
        int labelSid = -1;
        MlCountMtController cntCon = new MlCountMtController();
        try {
            cntCon = getCountMtController(reqMdl);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        Sml320knParamModel paramMdl = new Sml320knParamModel();
        paramMdl.setSmlAccountSid(accountSid);
        paramMdl.setSml320LabelName(labelName);
        paramMdl.setSmlLabelCmdMode(GSConstSmail.CMDMODE_ADD); // 登録で固定
        Sml320knBiz sml320Biz = new Sml320knBiz(con);
        labelSid = sml320Biz.doAddEdit(paramMdl, sessionUserSid, cntCon);
        return labelSid;
    }
    /**
    *
    * <br>[機  能] ラベル削除処理
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param accountSid アカウントSID
    * @param sessionUserSid セッションユーザSID
    * @param labelSid ラベルSID
    * @throws SQLException
    * @throws IOToolsException
    */
    public void delLabel(Connection con, RequestModel reqMdl,
           int accountSid, int sessionUserSid, int labelSid) throws SQLException {

        // 使用可能なラベル一覧の中からラベルSIDが一致するデータを検索
        SmlLabelDao  smllblDao = new SmlLabelDao(con);
        List<SmlLabelModel> labelList = smllblDao.getLabelList(accountSid);
        SmlLabelModel labelMdl = null;
        if (labelList != null) {
            for (SmlLabelModel mdl : labelList) {
                if (mdl.getSlbSid() == labelSid) {
                    labelMdl = mdl;
                    break;
                }
            }
        }

        // 一致するデータがない為、削除権限なし
        if (labelMdl == null) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound",
                    new GsMessage(reqMdl).getMessage("cmn.label")
                    );
        }

        //ラベルを削除する
        Sml310ParamModel paramMdl = new Sml310ParamModel();
        paramMdl.setSmlEditLabelId(labelSid);
        Sml310Biz sml310Biz = new Sml310Biz();
        sml310Biz.deleteLabel(con, paramMdl);
    }
}
