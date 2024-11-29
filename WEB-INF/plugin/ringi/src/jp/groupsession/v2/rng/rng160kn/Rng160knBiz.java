package jp.groupsession.v2.rng.rng160kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngAutodeleteDao;
import jp.groupsession.v2.rng.model.RngAutodeleteModel;
import jp.groupsession.v2.rng.model.RngDeleteModel;

/**
 * <br>[機  能] 稟議 自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng160knBiz {

    /**
     * <p>デフォルトコンストラクター
     */
    public Rng160knBiz() {
    }

    /**
     * <br>[機  能] 稟議 手動削除情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト
     * @param paramMdl 画面パラメータ
     * @throws Exception 実行例外
     * @return 稟議 手動削除情報
     */
    public List<RngDeleteModel> getDelDataList(
            RequestModel reqMdl,
            Rng160knParamModel paramMdl)
    throws Exception {

        List<RngDeleteModel> delList = new ArrayList<RngDeleteModel>();
        //申請中
        __addDeleteModel(delList,
                paramMdl.getRng160pendingKbn(),
                RngDeleteModel.DELTYPE_PENDING,
                paramMdl.getRng160pendingYear(),
                paramMdl.getRng160pendingMonth(),
                paramMdl.getRng160pendingDay());

        //完了
        __addDeleteModel(delList,
                paramMdl.getRng160completeKbn(),
                RngDeleteModel.DELTYPE_COMPLETE,
                paramMdl.getRng160completeYear(),
                paramMdl.getRng160completeMonth(),
                paramMdl.getRng160completeDay());

        //草稿
        __addDeleteModel(delList,
                paramMdl.getRng160draftKbn(),
                RngDeleteModel.DELTYPE_DRAFT,
                paramMdl.getRng160draftYear(),
                paramMdl.getRng160draftMonth(),
                paramMdl.getRng160draftDay());

        return delList;
    }

    /**
     * <br>[機  能] 稟議 自動削除設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl 画面パラメータ
     * @throws SQLException SQL実行時例外
     */
    public void entryAutoDeleteConf(Connection con, RequestModel reqMdl,
                                Rng160knParamModel paramMdl)
    throws SQLException {
        RngAutodeleteModel autoDelMdl = new RngAutodeleteModel();

        if (autoDelMdl != null) {
            //フォームにデータをセット
            autoDelMdl.setRadPendingKbn(paramMdl.getRng160pendingKbn());
            autoDelMdl.setRadPendingYear(paramMdl.getRng160pendingYear());
            autoDelMdl.setRadPendingMonth(paramMdl.getRng160pendingMonth());
            autoDelMdl.setRadPendingDay(paramMdl.getRng160pendingDay());
            autoDelMdl.setRadCompleteKbn(paramMdl.getRng160completeKbn());
            autoDelMdl.setRadCompleteYear(paramMdl.getRng160completeYear());
            autoDelMdl.setRadCompleteMonth(paramMdl.getRng160completeMonth());
            autoDelMdl.setRadCompleteDay(paramMdl.getRng160completeDay());
            autoDelMdl.setRadDraftKbn(paramMdl.getRng160draftKbn());
            autoDelMdl.setRadDraftYear(paramMdl.getRng160draftYear());
            autoDelMdl.setRadDraftMonth(paramMdl.getRng160draftMonth());
            autoDelMdl.setRadDraftDay(paramMdl.getRng160draftDay());

            int userSid = reqMdl.getSmodel().getUsrsid();
            UDate now = new UDate();
            autoDelMdl.setRadAuid(userSid);
            autoDelMdl.setRadAdate(now);
            autoDelMdl.setRadEuid(userSid);
            autoDelMdl.setRadEdate(now);
        }

        RngAutodeleteDao autoDelDao = new RngAutodeleteDao(con);
        if (autoDelDao.update(autoDelMdl) <= 0) {
            autoDelDao.insert(autoDelMdl);
        }
    }

    /**
     * <br>[機  能] 稟議削除条件Modelの追加を行う
     * <br>[解  説] 削除区分 = 削除する の場合のみ追加する
     * <br>[備  考]
     * @param delList 稟議削除条件List
     * @param delKbn 削除区分
     * @param delType 削除種別
     * @param year 年
     * @param month 月
     * @param day 日
     */
    private void __addDeleteModel(List<RngDeleteModel> delList,
                                int delKbn, int delType,
                                int year, int month, int day) {

        if (delKbn == RngConst.MANU_DEL_OK) {
            RngDeleteModel delMdl = new RngDeleteModel();
            delMdl.setDelType(delType);
            delMdl.setDelYear(year);
            delMdl.setDelMonth(month);
            delMdl.setDelDay(day);

            delList.add(delMdl);
        }
    }
}
