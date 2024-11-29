package jp.groupsession.v2.wml.wml050;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteDao;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteModel;

/**
 * <br>[機  能] WEBメール 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml050Biz {

    /**
     * <br>[機  能] 自動削除設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void getAutoDelete(Wml050ParamModel paramMdl, Connection con)
            throws SQLException {

        //メール自動削除設定情報を取得
        WmlAutodeleteDao waDao = new WmlAutodeleteDao(con);
        WmlAutodeleteModel waMdl = waDao.getAutoDelSetUp();

        if (waMdl != null) {
            //フォームにデータをセット
            paramMdl.setWml050dustKbn(String.valueOf(waMdl.getWadDustKbn()));
            paramMdl.setWml050dustYear(waMdl.getWadDustYear());
            paramMdl.setWml050dustMonth(waMdl.getWadDustMonth());
            paramMdl.setWml050dustDay(waMdl.getWadDustDay());
            paramMdl.setWml050sendKbn(String.valueOf(waMdl.getWadSendKbn()));
            paramMdl.setWml050sendYear(waMdl.getWadSendYear());
            paramMdl.setWml050sendMonth(waMdl.getWadSendMonth());
            paramMdl.setWml050sendDay(waMdl.getWadSendDay());
            paramMdl.setWml050draftKbn(String.valueOf(waMdl.getWadDraftKbn()));
            paramMdl.setWml050draftYear(waMdl.getWadDraftYear());
            paramMdl.setWml050draftMonth(waMdl.getWadDraftMonth());
            paramMdl.setWml050draftDay(waMdl.getWadDraftDay());
            paramMdl.setWml050resvKbn(String.valueOf(waMdl.getWadResvKbn()));
            paramMdl.setWml050resvYear(waMdl.getWadResvYear());
            paramMdl.setWml050resvMonth(waMdl.getWadResvMonth());
            paramMdl.setWml050resvDay(waMdl.getWadResvDay());
            paramMdl.setWml050keepKbn(String.valueOf(waMdl.getWadKeepKbn()));
            paramMdl.setWml050keepYear(waMdl.getWadKeepYear());
            paramMdl.setWml050keepMonth(waMdl.getWadKeepMonth());
            paramMdl.setWml050keepDay(waMdl.getWadKeepDay());
        }
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     */
    public void setInitData(Wml050ParamModel paramMdl, RequestModel reqMdl) {
        //年ラベル
        paramMdl.setYearLabelList(WmlBiz.createDelYearCombo(reqMdl));
        //月ラベル
        paramMdl.setMonthLabelList(WmlBiz.createDelMonthCombo(reqMdl));
        //日ラベル
        paramMdl.setDayLabelList(WmlBiz.createDelDayCombo(reqMdl));
    }
}
