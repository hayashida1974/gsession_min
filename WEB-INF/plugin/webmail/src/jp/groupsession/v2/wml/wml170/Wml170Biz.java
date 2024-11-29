package jp.groupsession.v2.wml.wml170;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteLogDao;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteLogModel;

/**
 * <br>[機  能] WEBメール 送受信ログ自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml170Biz {

    /**
     * <br>[機  能] 送受信ログ自動削除設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void getAutoDelete(Wml170ParamModel paramMdl, Connection con)
            throws SQLException {
        WmlAutodeleteLogDao waDao = new WmlAutodeleteLogDao(con);
        WmlAutodeleteLogModel waMdl = new WmlAutodeleteLogModel();

        //メール自動削除設定情報を取得
        waMdl = waDao.select();

        //フォームにデータをセット
        if (waMdl != null) {
            paramMdl.setWml170delKbn(String.valueOf(waMdl.getWalDelKbn()));
            paramMdl.setWml170delYear(waMdl.getWalDelYear());
            paramMdl.setWml170delMonth(waMdl.getWalDelMonth());
            paramMdl.setWml170delDay(waMdl.getWalDelDay());
        }
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Wml170ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {
        //年ラベル
        paramMdl.setYearLabelList(WmlBiz.createDelYearCombo(reqMdl));
        //月ラベル
        paramMdl.setMonthLabelList(WmlBiz.createDelMonthCombo(reqMdl));
        //日ラベル
        paramMdl.setDayLabelList(WmlBiz.createDelDayCombo(reqMdl));

        //送受信ログの登録 を設定
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setWmlEntryMailLogFlg(wmlBiz.isEntryMailLog(con));
    }
}
