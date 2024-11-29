package jp.groupsession.v2.man.man260;

import java.sql.Connection;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.dao.base.CmnLogDelConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.model.base.CmnLogDelConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man260Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man260Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行エラー
     */
    public void setInitData(Man260ParamModel paramMdl, Connection con)
    throws Exception {

        //年、月コンボを取得
        getDateCombo(paramMdl);

        //DBから設定状態を取得する
        CmnLogDelConfDao dao = new CmnLogDelConfDao(con);
        CmnLogDelConfModel delMdl = new CmnLogDelConfModel();

        //日次バッチ処理時刻を取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();

        if (batMdl != null) {
            paramMdl.setMan260BatchFrHour(Integer.toString(batMdl.getBatFrDate()));
        } else {
            paramMdl.setMan260BatchFrHour("0");
        }

        delMdl = dao.getOpeLogDelConf();

        if (delMdl == null) {
            delMdl = new CmnLogDelConfModel();
            delMdl.setLdcAdlKbn(GSConstMain.OPE_LOG_NOTCONF);
            delMdl.setLdcAdlYear(0);
            delMdl.setLdcAdlMonth(0);
        }

        //自動削除 選択値
        paramMdl.setMan260BatchKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getMan260BatchKbn(),
                        String.valueOf(delMdl.getLdcAdlKbn())));

        //自動削除 年
        paramMdl.setMan260Year(
                NullDefault.getStringZeroLength(
                        paramMdl.getMan260Year(),
                        String.valueOf(delMdl.getLdcAdlYear())));

        //自動削除 月
        paramMdl.setMan260Month(
                NullDefault.getStringZeroLength(
                        paramMdl.getMan260Month(),
                        String.valueOf(delMdl.getLdcAdlMonth())));

    }

    /**
     * <br>[機  能] 年、月コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行エラー
     */
    public void getDateCombo(Man260ParamModel paramMdl)
    throws Exception {

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //年リスト
        for (int i = 0; i <= 5; i++) {
            yearLabel.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                                       Integer.toString(i)));
        }
        yearLabel.add(new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {"10"}), "10"));
        paramMdl.setYearLabel(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                                            Integer.toString(i)));
        }
        paramMdl.setMonthLabel(monthLabel);

    }
}
