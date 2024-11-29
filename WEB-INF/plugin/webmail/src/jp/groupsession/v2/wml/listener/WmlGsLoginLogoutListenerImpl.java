package jp.groupsession.v2.wml.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.ILoginLogoutListener;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteDao;
import jp.groupsession.v2.wml.model.WmlMailDeleteModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] Servlet init() 又はdestroy()実行時に実行されるリスナーを実装
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlGsLoginLogoutListenerImpl implements ILoginLogoutListener {

    /** Logging インスタンス */
    static Log log__ = LogFactory.getLog(WmlGsLoginLogoutListenerImpl.class);

    /**
     * <br>[機  能] ログイン時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doLogin(Connection con, int usid) throws SQLException {
    }

    /**
     * <br>[機  能] ログアウト時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param session セッション
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     */
    public void doLogout(HttpSession session, Connection con,
                            int usid, String domain) throws SQLException {

        //自動削除設定を取得する
        WmlAutodeleteDao autoDeleteDao = new WmlAutodeleteDao(con);
        WmlAutodeleteModel autoDeleteMdl = autoDeleteDao.getAutoDelSetUp();
        if (autoDeleteMdl == null) {
            return;
        }

        //自動削除区分を取得する
        List<Integer> delDirTypeList = new ArrayList<Integer>();
        if (autoDeleteMdl.getWadDustKbn() == GSConstWebmail.WAD_DUST_LOGOUT) {
            delDirTypeList.add(GSConstWebmail.DIR_TYPE_DUST);
        }
        if (autoDeleteMdl.getWadSendKbn() == GSConstWebmail.WAD_SEND_LOGOUT) {
            delDirTypeList.add(GSConstWebmail.DIR_TYPE_SENDED);
        }
        if (autoDeleteMdl.getWadDraftKbn() == GSConstWebmail.WAD_DRAFT_LOGOUT) {
            delDirTypeList.add(GSConstWebmail.DIR_TYPE_DRAFT);
        }
        if (autoDeleteMdl.getWadResvKbn() == GSConstWebmail.WAD_RESV_LOGOUT) {
            delDirTypeList.add(GSConstWebmail.DIR_TYPE_RECEIVE);
        }
        if (autoDeleteMdl.getWadKeepKbn() == GSConstWebmail.WAD_KEEP_LOGOUT) {
            delDirTypeList.add(GSConstWebmail.DIR_TYPE_STORAGE);
        }

        if (delDirTypeList.isEmpty()) {
            return;
        }

        WmlAccountDao accountDao = new WmlAccountDao(con);
        List<WmlAccountModel> accountList = accountDao.getAccountList(usid);

        if (accountList != null) {

            WmlBiz wmlBiz = new WmlBiz();
            for (WmlAccountModel accountData : accountList) {
                long deleteTime = System.currentTimeMillis();
                //メール情報の削除を行う
                WmlMailDeleteModel delMdl = new WmlMailDeleteModel();
                delMdl.setWacSid(accountData.getWacSid());
                delMdl.setManuDelDirList(delDirTypeList);
                delMdl.setUseDate(false);
                delMdl.setDelAllAccount(false);
                wmlBiz.deleteMailData(con, delMdl, usid);

                log__.debug(accountData.getWacName()  + " : ログアウト時削除 実行時間 = "
                        + (System.currentTimeMillis() - deleteTime));
            }
        }

    }
}