package jp.groupsession.v2.bbs.bbs160;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsAdmConfDao;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] 掲示板 管理者設定 ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs160Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs160Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Bbs160ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {
        log__.debug("START");

        //掲示板管理者情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsAdmConfModel admMdl = bbsBiz.getBbsAdminData(con, userSid);

        paramMdl.setBbs160smlNtf(admMdl.getBacSmlNtf());
        paramMdl.setBbs160smlNtfKbn(admMdl.getBacSmlNtfKbn());

        log__.debug("End");
    }

    /**
     * <br>[機  能] 掲示板 管理者設定のショートメール通知設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void updateBbsSmailSetting(Bbs160ParamModel paramMdl, BaseUserModel umodel, Connection con)
    throws SQLException {
        log__.debug("START");


        //掲示板管理者情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        int userSid = umodel.getUsrsid();
        BbsAdmConfModel admMdl = bbsBiz.getBbsAdminData(con, userSid);

        UDate now = new UDate();
        admMdl.setBacEuid(userSid);
        admMdl.setBacEdate(now);
        admMdl.setBacSmlNtf(paramMdl.getBbs160smlNtf());
        admMdl.setBacSmlNtfKbn(paramMdl.getBbs160smlNtfKbn());
        if (admMdl.getBacSmlNtf() == GSConstBulletin.BAC_SML_NTF_USER) {
            admMdl.setBacSmlNtfKbn(GSConstBulletin.BAC_SML_NTF_KBN_YES);
        }

        BbsAdmConfDao admConfDao = new BbsAdmConfDao(con);
        if (admConfDao.updateSmailSetting(admMdl) == 0)  {
            admMdl.setBacAuid(userSid);
            admMdl.setBacAdate(now);
            admConfDao.insert(admMdl);
        }

        log__.debug("End");
    }
}
