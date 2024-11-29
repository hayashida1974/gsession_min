package jp.groupsession.v2.rng.rng170kn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngDeleteModel;

/**
 * <br>[機  能] 稟議 手動データ削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng170knBiz {

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
            Rng170knParamModel paramMdl)
    throws SQLException {

        List<RngDeleteModel> delList = new ArrayList<RngDeleteModel>();
        //申請中
        __addDeleteModel(delList,
                paramMdl.getRng170pendingKbn(),
                RngDeleteModel.DELTYPE_PENDING,
                paramMdl.getRng170pendingYear(),
                paramMdl.getRng170pendingMonth(),
                paramMdl.getRng170pendingDay());

        //完了
        __addDeleteModel(delList,
                paramMdl.getRng170completeKbn(),
                RngDeleteModel.DELTYPE_COMPLETE,
                paramMdl.getRng170completeYear(),
                paramMdl.getRng170completeMonth(),
                paramMdl.getRng170completeDay());

        //草稿
        __addDeleteModel(delList,
                paramMdl.getRng170draftKbn(),
                RngDeleteModel.DELTYPE_DRAFT,
                paramMdl.getRng170draftYear(),
                paramMdl.getRng170draftMonth(),
                paramMdl.getRng170draftDay());

        return delList;
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
