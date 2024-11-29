package jp.groupsession.v2.sml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.model.MailFilterConditionModel;
import jp.groupsession.v2.sml.model.MailFilterModel;
import jp.groupsession.v2.sml.model.SmailSendModel;
/** メール受信のフィルタを実行 Biz*/
public class SmlReceiveFilter {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlReceiveFilter.class);    /**コネクション */
    private final Connection con__;
    /**メール送信結果モデル */
    private final SmailSendModel sendMdl__;
    /**
     * コンストラクタ
     * @param con
     * @param sendMdl
     */
    public SmlReceiveFilter(Connection con, SmailSendModel sendMdl) {
        con__ = con;
        sendMdl__ = sendMdl;
    }

    
    /**
     * メール受信のフィルタを実行します。
     * @throws SQLException
     */
    public void doFilterJmail() throws SQLException {
        if (sendMdl__ == null
                || sendMdl__.getAccountSidList() == null
                || sendMdl__.getAccountSidList().isEmpty()
                || sendMdl__.getSmjSid() <= 0) {
        }
        SmailDao smailDao = new SmailDao(con__);

        for (int sendSacSid : sendMdl__.getAccountSidList()) {
            boolean commitFlg = false;
            try {
                con__.setAutoCommit(false);
                commitFlg = false;

                //フィルター情報を取得する
                List<MailFilterModel> filterList = smailDao.getFilterData(
                        sendSacSid);

                if (filterList != null && !filterList.isEmpty()) {
                    for (MailFilterModel filterData : filterList) {
                        //フィルタ条件の取得
                        List<MailFilterConditionModel> conditionList
                        = smailDao.getFilterConditionData(filterData.getSftSid());
                        smailDao.setFilterMailSid(
                                filterData, conditionList, sendSacSid, sendMdl__.getSmjSid());
                    }
                }
                con__.commit();
                commitFlg = true;
            } catch (SQLException e) {
                log__.error(e + "フィルタ処理に失敗:" + sendSacSid);
                throw e;
            } finally {
                if (commitFlg == false) {
                    JDBCUtil.rollback(con__);
                }
            }
        }
    }    
}


