package jp.groupsession.v2.rng.rng100;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.rng.dao.RngChannelTemplateDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 稟議 経路テンプレート一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng100Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng100Biz.class);

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void initDsp(Connection con, Rng100ParamModel paramMdl, int userSid)
    throws SQLException {
        log__.debug("start");
        //選択SIDを初期化
        paramMdl.setRctSid(0);
        RngChannelTemplateDao dao = new RngChannelTemplateDao(con);
        paramMdl.setRng100keiroTemplateList(dao.getChannelTemplateList(userSid));
        log__.debug("end");
    }
}
