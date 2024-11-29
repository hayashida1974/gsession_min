package jp.groupsession.v2.man.man060kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnDiskadminDao;
import jp.groupsession.v2.cmn.model.base.CmnDiskadminModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 ディスク容量管理確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man060knBiz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setInitData(Man060knParamModel paramMdl) {

        String warnStr = null;
        int capacity = paramMdl.getMan060capacity();
        if (capacity < 1000) {
            warnStr = String.valueOf(capacity).concat("MB");
        } else {
            warnStr = String.valueOf(capacity / 1000).concat("GB");
        }

        paramMdl.setMan060knwarnStr(warnStr);
    }

    /**
     * <br>[機  能] ディスク容量管理情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void insertDiskAdmin(Man060knParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        CmnDiskadminDao diskDao = new CmnDiskadminDao(con);
        diskDao.delete();

        CmnDiskadminModel diskMdl = new CmnDiskadminModel();
        diskMdl.setDskType(GSConstMain.DISKWARN_TYPE_CAPACITY);
        diskMdl.setDskValue(paramMdl.getMan060capacity());
        diskMdl.setDskAuid(userSid);
        diskMdl.setDskAdate(new UDate());
        diskMdl.setDskEuid(userSid);
        diskMdl.setDskEdate(diskMdl.getDskAdate());

        diskDao.insert(diskMdl);
    }

}
