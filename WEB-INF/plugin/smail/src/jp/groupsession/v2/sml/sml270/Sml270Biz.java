package jp.groupsession.v2.sml.sml270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountSortDao;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.model.SmlAccountSortModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;

/**
 * <br>[機  能] ショートメール アカウントの管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml270Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml270Biz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                             Sml270ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {

        //アカウント情報を取得する
        Sml270Dao dao = new Sml270Dao(con);
        List<AccountDataModel> accountList = dao.getAccountList(userSid);

        //アカウント並び順再登録
        SmlAccountSortDao sortDao = new SmlAccountSortDao(con);
        sortDao.deleteAllSort(userSid);
        SmlAccountSortModel mdl = null;
        for (int i = 0; i < accountList.size(); i++) {
            AccountDataModel aMdl = accountList.get(i);
            mdl = new SmlAccountSortModel();
            mdl.setSacSid(aMdl.getAccountSid());
            mdl.setUsrSid(userSid);
            mdl.setSasSort(i);
            sortDao.insert(mdl);
        }

        List<Sml270AccountDataDspModel> acDspList = new ArrayList<Sml270AccountDataDspModel>();
        Sml270AccountDataDspModel acDatadspMdl = null;
        accountList = dao.getAccountList(userSid);
        for (AccountDataModel acDataMdl : accountList) {
            acDatadspMdl = new Sml270AccountDataDspModel();

            acDatadspMdl.setAccountSid(acDataMdl.getAccountSid());
            acDatadspMdl.setAccountName(acDataMdl.getAccountName());
            acDatadspMdl.setAccountType(acDataMdl.getAccountType());
            acDatadspMdl.setAccountBiko(
                    StringUtilHtml.transToHTmlPlusAmparsant(acDataMdl.getAccountBiko()));
            acDatadspMdl.setAccountSort((int) acDataMdl.getAccountSort());
            acDatadspMdl.setAcValue(String.valueOf(acDatadspMdl.getAccountSid()));
            acDatadspMdl.setUsrUkoFlg(acDataMdl.getUsrUkoFlg());
            acDspList.add(acDatadspMdl);
        }

        paramMdl.setAccountList(acDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getSml270sortAccount())
            && accountList.size() > 0) {

            Sml270AccountDataDspModel addspMdl = acDspList.get(0);
            paramMdl.setSml270sortAccount(String.valueOf(addspMdl.getAccountSid()));
        }

        //非管理者でもアカウントを登録できるかどうか
        SmlCommonBiz cmnBiz = new SmlCommonBiz(con, reqMdl);
        SmlAdminModel samMdl = cmnBiz.getSmailAdminConf(userSid, con);

        if (samMdl.getSmaAcntMake() == GSConstSmail.KANRI_USER_ONLY) {
            paramMdl.setSml270MakeAcntHnt(GSConstSmail.ACCOUNT_ADD_NG);
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Sml270ParamModel paramMdl, int userSid, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getSml270sortLabel();
        if (keyList == null || keyList.length < 1) {
            return;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getSml270sortAccount();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        final int motoSid = Integer.parseInt(selectSid);
        if (motoSid <= 0) {
            return;
        }
        SmlAccountSortDao sortDao = new SmlAccountSortDao(con);
        SortChangeBiz<SmlAccountSortModel> sortBiz =
                SortChangeBiz.<SmlAccountSortModel> builder()
                .setFuncTargetList(() -> {
                    return sortDao.selectList(userSid);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getSacSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return (int) m.getSasSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getSacSid() == m2.getSacSid()) {
                        return 0;
                    } else {
                        return (m1.getSacSid() - m2.getSacSid())
                                / Math.abs(m1.getSacSid() - m2.getSacSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setSasSort(newSort);
                    sortDao.delete(m.getSacSid(), userSid);
                    sortDao.insert(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
