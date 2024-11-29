package jp.groupsession.v2.wml.wml080;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlMailBodyDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlMailBodyModel;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.wml260.Wml260Dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール メール詳細ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml080Biz.class);

    /**
     * <br>[機  能] メール送受信ログ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Wml080ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        WmlMaildataDao wmdao = new WmlMaildataDao(con);

        int mailNum = paramMdl.getWml080mailNum();
        log__.debug("MailNumber = " + mailNum);

        //選択したメールが存在するかチェック
        int count = 0;
        count = wmdao.selectMailCnt(mailNum);

        //メール詳細情報取得
        WmlMaildataModel wmMdl = null;
        if (paramMdl.getWml080mailType() == Wml080Form.MAILTYPE_RESERVATION) {
            Wml260Dao dao260 = new Wml260Dao(con);
            wmMdl = dao260.getReservationMailData(mailNum);
        } else {
            wmMdl = wmdao.select(mailNum);
        }

        //選択したメールが存在する場合
        if (count == 1) {
            //データセット
            paramMdl.setWml080Title(wmMdl.getWmdTitle());
            paramMdl.setWml080From(wmMdl.getWmdFrom());
            paramMdl.setWml080Sdate(UDateUtil.getSlashYYMD(wmMdl.getWmdSdate()));
            paramMdl.setWml080Stime(UDateUtil.getSeparateHMS(wmMdl.getWmdSdate()));
            paramMdl.setWml080BodyFlg("1");

            WmlMailBodyDao mailBodyDao = new WmlMailBodyDao(con);
            WmlMailBodyModel mailBodyMdl = mailBodyDao.select(mailNum);
            paramMdl.setWml080Body(
                    StringUtilHtml.transToHTml(
                            NullDefault.getString(mailBodyMdl.getWmbBody(), "")));

            WebmailDao wmlDao = new WebmailDao(con);
            paramMdl.setTempFileList(wmlDao.getTempFileList(mailNum));
        //メールが存在しない場合
        } else {
            GsMessage gsMsg = new GsMessage(reqMdl);
            paramMdl.setWml080Body(gsMsg.getMessage("wml.135"));
        }
    }
}
