package jp.groupsession.v2.convert.convert202.db;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.convert.convert202.dao.ConvContmDao;
import jp.groupsession.v2.convert.convert202.dao.ConvTableDao;
import jp.groupsession.v2.convert.convert202.model.ConvContmModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] DBのコンバータ
 * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class DBConverter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(DBConverter.class);

    /**
     * デフォルトコンストラクタ
     */
    public DBConverter() {
    }

    /**
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException 例外
     */
    public void convert(Connection con) throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //新規テーブルのcreate、insertを行う
        ConvTableDao ctDao = new ConvTableDao(con);
        ctDao.createTable();

        //alter tableなどのDBの編集を行う
        ctDao.alterTable();

        //コントロールマスタが無い場合はinsert
        ConvContmDao ccDao = new ConvContmDao(con);
        ConvContmModel ccMdl = ccDao.select();

        if (ccMdl == null) {
            ConvContmModel ccUpMdl = new ConvContmModel();
            ccUpMdl.setCntPxyUse(GSConstMain.PROXY_SERVER_NOT_USE);
            ccUpMdl.setCntPxyUrl(null);
            ccUpMdl.setCntPxyPort(0);
            ccUpMdl.setCntMenuStatic(GSConstMain.MENU_STATIC_NOT_USE);
            ccDao.insert(ccUpMdl);
        }

        log__.debug("-- DBコンバート終了 --");
    }

}
