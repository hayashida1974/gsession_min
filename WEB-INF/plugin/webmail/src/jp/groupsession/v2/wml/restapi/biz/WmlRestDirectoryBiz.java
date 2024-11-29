package jp.groupsession.v2.wml.restapi.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.WmlDirectoryCountModel;
import jp.groupsession.v2.wml.restapi.model.DirectoryArray;

/**
 * <br>[機  能] WEBメールのRESTAPI ディレクトリ情報関連のビジネスロジッククラスです
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlRestDirectoryBiz {

    /**
     * <br>[機  能] ラベル情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     */
    public List<DirectoryArray> getDirectoryData(
        Connection con, RequestModel reqMdl, int wacSid) throws SQLException {

        //メールボックス情報の取得
        List<DirectoryArray> directoryArrayList;
        DirectoryArray directoryArray;
        WmlDirectoryDao directoryDao = new WmlDirectoryDao(con);
        List<WmlDirectoryCountModel> directoryList =
                directoryDao.getDirectoryList(reqMdl, wacSid);
        directoryArrayList = new ArrayList<DirectoryArray>();

        WmlMaildataDao maildataDao = new WmlMaildataDao(con);
        for (WmlDirectoryCountModel dirMdl : directoryList) {
            int countNum = maildataDao.selectMailCntInDir(wacSid, dirMdl.getId());
            directoryArray = new DirectoryArray(dirMdl, wacSid);
            directoryArray.setCountNum(countNum);
            directoryArrayList.add(directoryArray);
        }
        return directoryArrayList;
    }

    /**
     * <br>[機  能] 指定したディレクトリ種別に該当するディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param wacSid アカウントSID
     * @param directoryType ディレクトリ種別
     * @return ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public long getDirectorySid(Connection con, int wacSid, String directoryType)
    throws SQLException {

        int wdrType = -1;
        switch (directoryType) {
            case "inbox":
                wdrType = GSConstWebmail.DIR_TYPE_RECEIVE;
                break;
            case "sent":
                wdrType = GSConstWebmail.DIR_TYPE_SENDED;
                break;
            case "future":
                wdrType = GSConstWebmail.DIR_TYPE_NOSEND;
                break;
            case "draft":
                wdrType = GSConstWebmail.DIR_TYPE_DRAFT;
                break;
            case "trash":
                wdrType = GSConstWebmail.DIR_TYPE_DUST;
                break;
            case "keep":
                wdrType = GSConstWebmail.DIR_TYPE_STORAGE;
                break;
            default:
                break;
        }

        WmlDirectoryDao dirDao = new WmlDirectoryDao(con);
        return dirDao.getDirSid(wacSid, wdrType);
    }
}
