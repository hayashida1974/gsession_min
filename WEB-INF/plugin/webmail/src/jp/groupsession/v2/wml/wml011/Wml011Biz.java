package jp.groupsession.v2.wml.wml011;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.wml.dao.base.WmlHeaderDataDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;
import jp.groupsession.v2.wml.wml010.Wml010Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール メールヘッダ情報(ポップアップ)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml011Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml011Biz.class);

    /**
     * <br>[機  能] メールヘッダ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Wml011ParamModel paramMdl)
        throws SQLException {

        //メール番号を取得
        int mailNum = paramMdl.getWml011mailNum();
        log__.debug("MailNumber = " + mailNum);

        //メールヘッダ情報取得
        WmlHeaderDataDao whdao = new WmlHeaderDataDao(con);
        paramMdl.setWml011MailHeaderData(whdao.select(mailNum));

        //登録日時を設定
        WmlMaildataDao maildataDao = new WmlMaildataDao(con);
        WmlMaildataModel mailData = maildataDao.select(mailNum);
        UDate entryDate = mailData.getWmdAdate();
        if (entryDate != null) {
            paramMdl.setWml011EntryDate(
                    UDateUtil.getSlashYYMD(entryDate)
                    + " " + UDateUtil.getSeparateHMS(entryDate));
        } else {
            paramMdl.setWml011EntryDate("");
        }

        //表示フラグを設定
        paramMdl.setWml011viewFlg(
                !paramMdl.getWml011MailHeaderData().isEmpty()
                || entryDate != null);

        //アカウントのテーマを設定
        Wml010Biz biz010 = new Wml010Biz();
        biz010.setAccountTheme(con, paramMdl, mailData.getWacSid());
    }
}
