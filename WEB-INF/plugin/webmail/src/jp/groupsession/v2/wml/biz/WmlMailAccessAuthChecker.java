package jp.groupsession.v2.wml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.base.WmlMaildataModel;

public class WmlMailAccessAuthChecker {
    /** コネクション */
    Connection con__;
    /** アカウントID */
    private int accountSid__;
    /** メールSID */
    private long[] sidArray__;

    /**
     * コンストラクタ
     * @param con
     * @param accountSid
     * @param sidArray
     */
    public WmlMailAccessAuthChecker(
        Connection con,
        int accountSid,
         long[] sidArray) {
        con__ = con;
        accountSid__ = accountSid;
        sidArray__ = sidArray;
    }
    /**
     * 指定のメールSIDとアカウントの組み合わせで閲覧できるか確認する
     * @return true 全SIDで閲覧可能 
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessAllMail() throws SQLException {
        WmlMaildataDao wmdDao = new WmlMaildataDao(con__);
        List<WmlMaildataModel> wmdList = wmdDao.getMailDataList(accountSid__, sidArray__);

        if (wmdList.size() != sidArray__.length) {
            return false;
        }
        return true;
    }    

}
