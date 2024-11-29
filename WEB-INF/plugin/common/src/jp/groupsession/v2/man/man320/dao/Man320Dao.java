package jp.groupsession.v2.man.man320.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン インフォメーション一覧画面で使用するDAOクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man320Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man320Dao.class);

    /**
     * <p>Default Constructor
     */
    public Man320Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Man320Dao(Connection con) {
        super(con);
    }
    /**
     * <p>公開対象者の名前一覧を取得します。
     * @param infoSid インフォメーションSID
     * @return List in CMN_INFO_MSGModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getKoukaiTargetString(int infoSid) throws SQLException {

        Connection con = null;
        con = getCon();

        CmnInfoTagDao tagDao = new CmnInfoTagDao(con);
        ArrayList<String> tagList = tagDao.getTargetGroupNames(infoSid);
        ArrayList<String> tagUsList = tagDao.getTargetUserNames(infoSid);
        log__.debug("公開グループ数=>" + tagList.size());
        log__.debug("公開ユーザ数=>" + tagUsList.size());
        for (String tag : tagUsList) {
            tagList.add(tag);
        }
        return tagList;
    }
}
