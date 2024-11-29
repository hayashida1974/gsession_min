package jp.groupsession.v2.man.man510kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定確認 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man510knBiz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Man510knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException 
     */
    public void setDspData(Connection con, Man510knParamModel paramMdl) throws SQLException {
        //表示用設定
        MailEncryptBiz proBiz = new MailEncryptBiz(reqMdl__);
        paramMdl.setMan510knSendEncrypt(
                proBiz.getProtocolName(paramMdl.getMan510SmtpEncrypt()));
        
        if (paramMdl.getMan510authType() == GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) {
            CmnOauthDao coDao = new CmnOauthDao(con);
            CmnOauthModel coMdl = coDao.select(paramMdl.getMan510provider());
            paramMdl.setMan510SendProvider(coMdl.getCouProvider());
        }

        //ユーザ選択要素の描画設定
        UserGroupSelectModel userSel = paramMdl.getMan510targetUserList();
        userSel.setSelected(UserGroupSelectModel.KEY_DEFAULT, paramMdl.getMan510targetUser());
        String listHead;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (paramMdl.getMan510otpUserKbn() == GSConstMain.OTP_USRTYPE_USE) {
            listHead = gsMsg.getMessage("main.man510.13");
        } else {
            listHead = gsMsg.getMessage("main.man510.14");
        }
        userSel.init(con, reqMdl__,  new String[] {UserGroupSelectModel.KEY_DEFAULT},
                new String[] { listHead },
                String.valueOf(UserGroupSelectBiz.GROUP_GRPLIST),
                null,
                null);
        paramMdl.setMan510targetUserList(userSel);

    }
}
