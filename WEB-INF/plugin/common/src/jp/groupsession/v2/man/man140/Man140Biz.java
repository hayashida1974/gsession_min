package jp.groupsession.v2.man.man140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 セッション保持時間設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man140Biz {

    /** DBコネクション */
    private Connection con__ = null;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl リクエスト情報
     */
    public Man140Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man140ParamModel paramMdl) throws SQLException {

        CmnContmDao dao = new CmnContmDao(con__);
        CmnContmModel mdl = dao.select();

        //セッション保持時間を設定する。
        paramMdl.setMan140SessionTime(mdl.getCntSessionTime());
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void getDspData(Man140ParamModel paramMdl) {

        //セッション保持時間ラベル
        paramMdl.setMan140SessionTimeLabel(__getSessionTimeLabel());
    }

    /**
     * <br>[機  能] セッション保持時間ラベル（時間）を生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return List (in LabelValueBean)  セッション保持時間ラベル
     */
    private List<LabelValueBean> __getSessionTimeLabel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        String msgMin = gsMsg.getMessage("cmn.minute");

        labelList.add(new LabelValueBean("5" + msgMin, "5"));
        labelList.add(new LabelValueBean("10" + msgMin, "10"));
        labelList.add(new LabelValueBean("30" + msgMin, "30"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"1"}), "60"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"2"}), "120"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"3"}), "180"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"5"}), "300"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"8"}), "480"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"10"}), "600"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"12"}), "720"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"15"}), "900"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"20"}), "1200"));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"24"}), "1440"));

        return labelList;
    }
}
