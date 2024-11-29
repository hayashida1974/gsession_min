package jp.groupsession.v2.wml.wml100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountSortDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.model.base.AccountDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAccountSortModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;

/**
 * <br>[機  能] WEBメール アカウントの管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml100Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml100Biz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                             Wml100ParamModel paramMdl,
                             int userSid) throws SQLException {

        //アカウント情報を取得する
        Wml100Dao dao = new Wml100Dao(con);
        List<AccountDataModel> accountList = dao.getAccountList(userSid);
        List<Wml100AccountDataDspModel> acDspList = new ArrayList<Wml100AccountDataDspModel>();
        Wml100AccountDataDspModel acDatadspMdl = null;

        for (AccountDataModel acDataMdl : accountList) {
            acDatadspMdl = new Wml100AccountDataDspModel();

            acDatadspMdl.setAccountSid(acDataMdl.getAccountSid());
            acDatadspMdl.setAccountUid(acDataMdl.getAccountUid());
            acDatadspMdl.setAccountName(acDataMdl.getAccountName());
            acDatadspMdl.setAccountType(acDataMdl.getAccountType());
            acDatadspMdl.setAccountAddress(acDataMdl.getAccountAddress());
            acDatadspMdl.setAccountBiko(acDataMdl.getAccountBiko());
            acDatadspMdl.setAccountSort((int) acDataMdl.getAccountSort());
            acDatadspMdl.setAcValue(String.valueOf(acDatadspMdl.getAccountSid()));
            if (acDataMdl.getReceiveDate() != null) {
                String dateString = UDateUtil.getSlashYYMD(acDataMdl.getReceiveDate());
                dateString += " " + UDateUtil.getSeparateHMS(acDataMdl.getReceiveDate());
                acDatadspMdl.setReceiveDate(dateString);
            }

            //アカウント使用ユーザかを判定
            WmlBiz wmlBiz = new WmlBiz();
            List<Integer> useUserList
                = wmlBiz.getAccountUserList(con, acDataMdl.getAccountSid(), false);
            acDatadspMdl.setAccountUseUser(useUserList.contains(userSid));

            acDspList.add(acDatadspMdl);
        }
        paramMdl.setAccountList(acDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getWml100sortAccount())
            && accountList.size() > 0) {

            Wml100AccountDataDspModel addspMdl = acDspList.get(0);
            paramMdl.setWml100sortAccount(String.valueOf(addspMdl.getAccountSid()));
        }

        //非管理者でもアカウントを登録できるかどうか
        WmlAdmConfDao wacDao = new WmlAdmConfDao(con);
        WmlAdmConfModel wacMdl = new WmlAdmConfModel();
        wacMdl = wacDao.selectAdmData();
        paramMdl.setWml100MakeAcntHnt(wacMdl.getWadAcntMake());
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return updateフラグ
     * @throws SQLException SQL実行時例外
     */
    public boolean updateSort(Connection con, Wml100ParamModel paramMdl,
            int userSid, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getWml100sortLabel();
        if (keyList == null || keyList.length < 1) {
            return false;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getWml100sortAccount();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return false;
        }
        final int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return false;
        }

        Wml100Dao wmlDao = new Wml100Dao(con);
        WmlAccountSortDao sortDao = new WmlAccountSortDao(con);
        SortResult<WmlAccountSortModel> result = null;
        SortChangeBiz<WmlAccountSortModel> sortBiz =
                SortChangeBiz.<WmlAccountSortModel> builder()
                .setFuncTargetList(() -> {
                    List<Integer> sids = new ArrayList<Integer>();
                    WmlBiz wmlBiz = new WmlBiz();
                    boolean proxyUserFlg = wmlBiz.isProxyUserAllowed(con);
                    sids = wmlDao.getAccountSidList(userSid, proxyUserFlg);
                    return sortDao.select(userSid, sids);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getWacSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return (int) m.getWasSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getWacSid() == m2.getWacSid()) {
                        return 0;
                    } else {
                        return (m1.getWacSid() - m2.getWacSid())
                                / Math.abs(m1.getWacSid() - m2.getWacSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setWasSort(newSort);
                    sortDao.delete(m.getWacSid(), userSid);
                    sortDao.insert(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] アカウント名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSid アカウントSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    public String getAccountName(Connection con,
                             int accountSid) throws SQLException {

        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(accountSid);
        return wacMdl.getWacName();
    }
}
