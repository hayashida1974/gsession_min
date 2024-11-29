package jp.groupsession.v2.sml.sml400;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml400Biz {

//    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Sml400Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *@param reqMdl リクエスト情報
     */
    public Sml400Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報s
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(
            RequestModel reqMdl,
            Sml400ParamModel paramMdl,
            Connection con)
                    throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        //表示設定を取得
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel result = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);

        //表示件数
        paramMdl.setSml400MaxDspStype(result.getSmaMaxDspStype());
        paramMdl.setSml400MaxDsp(result.getSmaMaxDsp());

        //自動リロード時間
        paramMdl.setSml400ReloadTimeStype(result.getSmaReloadDspStype());
        paramMdl.setSml400ReloadTime(String.valueOf(result.getSmaReloadDsp()));

        //写真表示
        paramMdl.setSml400PhotoDspStype(result.getSmaPhotoDspStype());
        paramMdl.setSml400PhotoDsp(result.getSmaPhotoDsp());

        //添付画像表示
        paramMdl.setSml400AttachImgDspStype(result.getSmaAttachDspStype());
        paramMdl.setSml400AttachImgDsp(result.getSmaAttachDsp());
    }

    /**
     * <br>[機  能] 表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setDisplayData(Sml400ParamModel paramMdl) {
        //コンボをセット
        paramMdl.setSml400MaxDspList(__getMaxDspLabel());
        paramMdl.setSml400ReloadTimeList(__getReloadTimeLabel());
    }

    /**
     * <br>[機  能] 表示件数コンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    private List<LabelValueBean> __getMaxDspLabel() {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        int minRecCnt = GSConstSmail.MAX_RECORD_COUNT;
        int maxRecCnt = GSConstSmail.MAX_RECORD_COUNT_MAX;
        for (int cnt = minRecCnt; cnt <= maxRecCnt; cnt += 10) {
            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 自動リロード時間コンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getReloadTimeLabel() {
        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String minite_1 = "1" + gsMsg.getMessage("cmn.minute");
        String minite_3 = "3" + gsMsg.getMessage("cmn.minute");
        String minite_5 = "5" + gsMsg.getMessage("cmn.minute");
        String minite_10 = "10" + gsMsg.getMessage("cmn.minute");
        String minite_20 = "20" + gsMsg.getMessage("cmn.minute");
        String minite_30 = "30" + gsMsg.getMessage("cmn.minute");
        String minite_40 = "40" + gsMsg.getMessage("cmn.minute");
        String minite_50 = "50" + gsMsg.getMessage("cmn.minute");
        String minite_60 = "60" + gsMsg.getMessage("cmn.minute");
        String no_reloard = gsMsg.getMessage("cmn.without.reloading");

        labelList.add(new LabelValueBean(minite_1, "60000"));
        labelList.add(new LabelValueBean(minite_3, "180000"));
        labelList.add(new LabelValueBean(minite_5, "300000"));
        labelList.add(new LabelValueBean(minite_10, "600000"));
        labelList.add(new LabelValueBean(minite_20, "1200000"));
        labelList.add(new LabelValueBean(minite_30, "1800000"));
        labelList.add(new LabelValueBean(minite_40, "2400000"));
        labelList.add(new LabelValueBean(minite_50, "3000000"));
        labelList.add(new LabelValueBean(minite_60, "3600000"));
        labelList.add(new LabelValueBean(no_reloard, "0"));
        return labelList;
    }

}