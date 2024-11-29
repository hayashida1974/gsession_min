package jp.groupsession.v2.rng.rng160;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngAutodeleteDao;
import jp.groupsession.v2.rng.model.RngAutodeleteModel;

/**
 * <br>[機  能] 稟議 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng160Biz {

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Rng160ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        //年ラベル
        paramMdl.setYearLabelList(RngBiz.createDelYearCombo(reqMdl));
        //月ラベル
        paramMdl.setMonthLabelList(RngBiz.createDelMonthCombo(reqMdl));
        //日ラベル
        paramMdl.setDayLabelList(RngBiz.createDelDayCombo(reqMdl));

        //初期表示の場合
        if (paramMdl.getRng160initFlg().equals(String.valueOf(RngConst.DSP_FIRST))) {

            //稟議自動削除設定取得
            __setAutoDelete(paramMdl, con);

            //初期表示完了
            paramMdl.setRng160initFlg(String.valueOf(RngConst.DSP_ALREADY));
        }
    }

    /**
     * <br>[機  能] 稟議自動削除設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setAutoDelete(Rng160ParamModel paramMdl, Connection con)
    throws SQLException {

        //稟議自動削除設定情報を取得
        RngAutodeleteDao autoDelDao = new RngAutodeleteDao(con);
        RngAutodeleteModel autoDelMdl = autoDelDao.getData();

        if (autoDelMdl != null) {
            //フォームにデータをセット
            paramMdl.setRng160pendingKbn(autoDelMdl.getRadPendingKbn());
            paramMdl.setRng160pendingYear(autoDelMdl.getRadPendingYear());
            paramMdl.setRng160pendingMonth(autoDelMdl.getRadPendingMonth());
            paramMdl.setRng160pendingDay(autoDelMdl.getRadPendingDay());
            paramMdl.setRng160completeKbn(autoDelMdl.getRadCompleteKbn());
            paramMdl.setRng160completeYear(autoDelMdl.getRadCompleteYear());
            paramMdl.setRng160completeMonth(autoDelMdl.getRadCompleteMonth());
            paramMdl.setRng160completeDay(autoDelMdl.getRadCompleteDay());
            paramMdl.setRng160draftKbn(autoDelMdl.getRadDraftKbn());
            paramMdl.setRng160draftYear(autoDelMdl.getRadDraftYear());
            paramMdl.setRng160draftMonth(autoDelMdl.getRadDraftMonth());
            paramMdl.setRng160draftDay(autoDelMdl.getRadDraftDay());
        }
    }
}
