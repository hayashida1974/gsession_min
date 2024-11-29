package jp.groupsession.v2.cmn.cmn360;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

/**
 * <br>[機  能] 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn360Biz {
    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Cmn360Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 役職一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param filterList フィルターリスト
     * @return 役職一覧
     * @throws SQLException SQL実行エラー
     */
    public List<CmnPositionModel> getFilterPositionList(String[] filterList)
            throws SQLException {
        List<Integer> sidList = __getFilterSidList(filterList, "post.");

        CmnPositionDao dao = new CmnPositionDao(con__);
        List<CmnPositionModel> positionList = dao.getPosList(sidList);

        return positionList;
    }

    /**
     * <br>[機  能] ラベル一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param filterList フィルターリスト
     * @return ラベル一覧
     * @throws SQLException SQL実行エラー
     */
    public List<CmnLabelUsrModel> getFilterLabelList(String[] filterList)
            throws SQLException {
        List<Integer> sidList = __getFilterSidList(filterList, "label.");

        CmnLabelUsrDao dao = new CmnLabelUsrDao(con__);
        List<CmnLabelUsrModel> labelList = dao.select(sidList);

        return labelList;

    }

    /**
     * <br>[機  能] フィルターからSID取得
     * <br>[解  説]
     * <br>[備  考]
     * @param filterList フィルターリスト
     * @param target 対象
     * @return SIDリスト
     * @throws SQLException SQL実行エラー
     */
    public List<Integer> __getFilterSidList(String[] filterList, String target)
            throws SQLException {
        List<Integer> sidList = new ArrayList<Integer>();
        if (filterList != null) {
            for (String filter : filterList) {
                if (filter.contains(target)) {
                    sidList.add(NullDefault.getInt(filter.substring(target.length()), -1));
                }
            }
        }
        return sidList;
    }
}
