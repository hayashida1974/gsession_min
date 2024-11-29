package jp.groupsession.v2.man.man040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.quartz.JobException;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 バッチ処理起動時間設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man040Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man040Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man040ParamModel paramMdl, Connection con)
    throws SQLException {

        log__.debug("START");
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();


        if (batMdl != null) {
            paramMdl.setMan040FrHour(Integer.toString(batMdl.getBatFrDate()));
        } else {
            paramMdl.setMan040FrHour("0");
        }

        //コンボボックスを設定
        paramMdl.setMan040HourLabel(getHourLabel());

        log__.debug("END");
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setDspData(Man040ParamModel paramMdl) {

        log__.debug("START");

        //コンボボックスを設定
        paramMdl.setMan040HourLabel(getHourLabel());

        log__.debug("END");
    }

    /**
     * <br>[機  能] 時コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  時コンボ
     */
    public ArrayList<LabelValueBean> getHourLabel() {
        int hour = 0;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int i = 0; i < 24; i++) {
            labelList.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.hour.only", new String[] {String.valueOf(hour)}),
                            String.valueOf(hour)));
            hour++;
        }
        return labelList;
    }

    /**
     * <br>[機  能] 分コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  分コンボ
     */
    public ArrayList<LabelValueBean> getMinuteLabel() {
        int min = 0;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        for (int i = 0; i < 4; i++) {
            labelList.add(
                    new LabelValueBean(
                            StringUtil.toDecFormat(min, "00")
                            + gsMsg.getMessage("cmn.minute"), String.valueOf(min)));
            min = min + 15;
        }
        return labelList;
    }

    /**
     * <br>[機  能] バッチジョブ強制実行します。
     * <br>[解  説]
     * <br>[備  考]
     * @param gscontext GSコンテキスト「
     * @param con コネクション
     * @param pluginConfig プラグインコンフィグ
     * @throws SQLException SQL実行時例外
     * @throws JobException Job実行時例外
     */
    public void executeBatchJob(
            GSContext gscontext, Connection con, PluginConfig pluginConfig)
    throws SQLException, JobException {
        log__.debug("バッチ処理強制実行");

        DayJob dayJob = new DayJob();
        dayJob.setGscontext(gscontext);
        dayJob.executeBatchManual(
                reqMdl__.getDomain(), pluginConfig);

        log__.debug("バッチ処理終了");
    }
}
