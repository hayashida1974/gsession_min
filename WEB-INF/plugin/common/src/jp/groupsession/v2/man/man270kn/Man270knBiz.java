package jp.groupsession.v2.man.man270kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnLoginConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLoginConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 ログイン設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man270knBiz {

    /**
     * <br>[機  能] ログイン設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateLoginConf(Connection con, Man270knParamModel paramMdl, int userSid)
    throws SQLException {

        UDate now = new UDate();
        CmnLoginConfModel confData = new CmnLoginConfModel();

        confData.setLlcLockoutKbn(paramMdl.getMan270lockKbn());
        confData.setLlcFailCnt(paramMdl.getMan270failCount());
        confData.setLlcLockAge(paramMdl.getMan270lockTime());
        confData.setLlcFailAge(paramMdl.getMan270failTime());
        confData.setLlcAuid(userSid);
        confData.setLlcAdate(now);
        confData.setLlcEuid(userSid);
        confData.setLlcEdate(now);

        CmnLoginConfDao confDao = new CmnLoginConfDao(con);
        if (confDao.updateLoginConf(confData) == 0) {
            confDao.insertLoginConf(confData);
        }
    }

    /**
     * <br>[機  能] ログイン設定完了時のオペレーションログ内容を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @return オペレーションログ内容
     * @throws SQLException SQL実行時例外
     */
    public String getOpLog(Man270knParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        StringBuilder sb = new StringBuilder();
        sb.append(gsMsg.getMessage("main.man270.1"));
        sb.append(" : ");
        if (paramMdl.getMan270lockKbn() == 1) {
            sb.append(gsMsg.getMessage("main.man270.2"));
            sb.append("\n");
            sb.append(gsMsg.getMessage("cmn.number.failure"));
            sb.append(" : ");
            sb.append(gsMsg.getMessage("main.man270.4",
                    new String[] {String.valueOf(paramMdl.getMan270failCount())}));
            sb.append("\n");
            sb.append(gsMsg.getMessage("main.lockout.time"));
            sb.append(" : ");
            sb.append(paramMdl.getMan270lockTime());
            sb.append(gsMsg.getMessage("cmn.minute"));
            sb.append("\n");
            sb.append(gsMsg.getMessage("cmn.reset.number.failure"));
            sb.append(" : ");
            sb.append(paramMdl.getMan270failTime());
            sb.append(gsMsg.getMessage("main.man270.9"));
        } else {
            sb.append(gsMsg.getMessage("cmn.noset"));
        }

        return sb.toString();
    }

}