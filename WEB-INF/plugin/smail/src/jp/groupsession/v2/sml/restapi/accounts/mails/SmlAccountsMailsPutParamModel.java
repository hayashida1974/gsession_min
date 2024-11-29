package jp.groupsession.v2.sml.restapi.accounts.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.model.SmlBinModel;
/**
 *
 * <br>[機  能] メール作成API パラメータモデル
 * <br>[解  説] PUT
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class SmlAccountsMailsPutParamModel extends SmlAccountsMailsPostParamModel {
    /** 草稿メールSID */
    private int mailSid__;

    /**
     * <p>mailSid を取得します。
     * @return mailSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPutParamModel#mailSid__
     */
    public int getMailSid() {
        return mailSid__;
    }

    /**
     * <p>mailSid をセットします。
     * @param mailSid mailSid
     * @see jp.groupsession.v2.sml.restapi.accounts.mails.SmlAccountsMailsPutParamModel#mailSid__
     */
    public void setMailSid(int mailSid) {
        mailSid__ = mailSid;
    }

    /**
     * <br>[機  能] 元メールの添付ファイル引継ぎ判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException
     */
    public void validateBinArrayCheckPut(Connection con, RequestModel reqMdl) throws SQLException {
        //メールSIDで添付ファイル情報取得
        SmlBinDao dao = new SmlBinDao(con);
        List<SmlBinModel> baseMailBinList = dao.getBinList(mailSid__);
        List<Long> baseBinList = new ArrayList<Long>();
        for (SmlBinModel mdl : baseMailBinList) {
            baseBinList.add(mdl.getBinSid());
        }
        for (long binSid : getBinSidArray()) {
            if (!baseBinList.contains(binSid)) {
                //元メールに存在しないSIDを指定
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "search.data.notfound",
                        GSConstSmail.RESTAPI_PARAM_BIN_SID_ARRAY
                        );
            }
        }
    }
}
