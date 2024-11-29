package jp.groupsession.v2.cmn.cmn150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 個人設定 メイン画面表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn150Form extends AbstractGsForm {

    /** 遷移元画面 個人メニュー */
    public static final int BACKPAGE_MENU = 0;
    /** 遷移元画面 メイン */
    public static final int BACKPAGE_MAIN = 1;

    /** 画面項目1(時計)表示有無 */
    private int cmn150Dsp1__ = GSConstCommon.NUM_INIT;
    /** 画面項目2(前回ログイン時間)表示有無 */
    private int cmn150Dsp2__ = GSConstCommon.NUM_INIT;
    /** 画面項目3(自動リロード時間) */
    private int cmn150Dsp3__ = GSConstCommon.MAIN_DSPRELOAD;
    /** 画面項目4(天気予報)表示有無 */
    private int cmn150Dsp4__ = GSConstCommon.NUM_INIT;
    /** 画面項目4(天気予報) 表示地域 */
    private String[] cmn150Dsp4Area__ =  new String[0];
    /** 画面項目6(ニュース) */
    private int cmn150Dsp6__ = GSConstCommon.NUM_INIT;

    /** 天気予報 ニュース 項目の表示設定  0:表示する 1:しない*/
    private String cmn150MainStatus__ = GSConst.MAIN_STATUS_TRUE;

    /** 天気予報 表示地域(選択) */
    private String[] cmn150Dsp4AreaLeft__ = new String[0];
    /**  天気予報 追加用地域(選択) */
    private String[] cmn150Dsp4AreaRight__ = new String[0];

    /** 自動リロード 表示項目 */
    private List <LabelValueBean> cmn150DspLabelList__ = null;

    /** 遷移元画面 */
    private int cmn150backPage__ = BACKPAGE_MENU;

    /** 天気予報 表示地域コンボ */
    private List <LabelValueBean> selectWeatherAreaCombo__ = null;
    /** 天気予報 追加用地域コンボ */
    private List <LabelValueBean> noSelectWeatherAreaCombo__ = null;
    /** 天気予報 選択UI*/
    private Cmn150WeatherSelector cmn150WeatherUI__ =
            Cmn150WeatherSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("cmn.cmn150.2", null))
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "cmn150Dsp4Area")
                        )
                .build();

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        if (cmn150Dsp4__ == GSConstCommon.MAIN_DSP) {
            if (cmn150Dsp4Area__ == null || cmn150Dsp4Area__.length <= 0) {
                GsMessage gsMsg = new GsMessage();
                //地域
                String textTiiki = gsMsg.getMessage(req, "cmn.cmn150.7");

                msg = new ActionMessage("error.select.required.text", textTiiki);
                StrutsUtil.addMessage(errors, msg, "cmn150Dsp4Area");
            }

        }

        return errors;
    }

    /**
     * <p>cmn150Dsp1 を取得します。
     * @return cmn150Dsp1
     */
    public int getCmn150Dsp1() {
        return cmn150Dsp1__;
    }

    /**
     * <p>cmn150Dsp1 をセットします。
     * @param cmn150Dsp1 cmn150Dsp1
     */
    public void setCmn150Dsp1(int cmn150Dsp1) {
        cmn150Dsp1__ = cmn150Dsp1;
    }

    /**
     * <p>cmn150Dsp2 を取得します。
     * @return cmn150Dsp2
     */
    public int getCmn150Dsp2() {
        return cmn150Dsp2__;
    }

    /**
     * <p>cmn150Dsp2 をセットします。
     * @param cmn150Dsp2 cmn150Dsp2
     */
    public void setCmn150Dsp2(int cmn150Dsp2) {
        cmn150Dsp2__ = cmn150Dsp2;
    }

    /**
     * <p>cmn150Dsp3 を取得します。
     * @return cmn150Dsp3
     */
    public int getCmn150Dsp3() {
        return cmn150Dsp3__;
    }

    /**
     * <p>cmn150Dsp3 をセットします。
     * @param cmn150Dsp3 cmn150Dsp3
     */
    public void setCmn150Dsp3(int cmn150Dsp3) {
        cmn150Dsp3__ = cmn150Dsp3;
    }

    /**
     * <p>cmn150Dsp4 を取得します。
     * @return cmn150Dsp4
     */
    public int getCmn150Dsp4() {
        return cmn150Dsp4__;
    }

    /**
     * <p>cmn150Dsp4 をセットします。
     * @param cmn150Dsp4 cmn150Dsp4
     */
    public void setCmn150Dsp4(int cmn150Dsp4) {
        cmn150Dsp4__ = cmn150Dsp4;
    }

    /**
     * <p>cmn150Dsp4Area を取得します。
     * @return cmn150Dsp4Area
     */
    public String[] getCmn150Dsp4Area() {
        return cmn150Dsp4Area__;
    }

    /**
     * <p>cmn150Dsp4Area をセットします。
     * @param cmn150Dsp4Area cmn150Dsp4Area
     */
    public void setCmn150Dsp4Area(String[] cmn150Dsp4Area) {
        cmn150Dsp4Area__ = cmn150Dsp4Area;
    }

    /**
     * <p>cmn150Dsp4AreaLeft を取得します。
     * @return cmn150Dsp4AreaLeft
     */
    public String[] getCmn150Dsp4AreaLeft() {
        return cmn150Dsp4AreaLeft__;
    }

    /**
     * <p>cmn150Dsp4AreaLeft をセットします。
     * @param cmn150Dsp4AreaLeft cmn150Dsp4AreaLeft
     */
    public void setCmn150Dsp4AreaLeft(String[] cmn150Dsp4AreaLeft) {
        cmn150Dsp4AreaLeft__ = cmn150Dsp4AreaLeft;
    }

    /**
     * <p>cmn150Dsp4AreaRight を取得します。
     * @return cmn150Dsp4AreaRight
     */
    public String[] getCmn150Dsp4AreaRight() {
        return cmn150Dsp4AreaRight__;
    }

    /**
     * <p>cmn150Dsp4AreaRight をセットします。
     * @param cmn150Dsp4AreaRight cmn150Dsp4AreaRight
     */
    public void setCmn150Dsp4AreaRight(String[] cmn150Dsp4AreaRight) {
        cmn150Dsp4AreaRight__ = cmn150Dsp4AreaRight;
    }

    /**
     * <p>cmn150Dsp6 を取得します。
     * @return cmn150Dsp6
     */
    public int getCmn150Dsp6() {
        return cmn150Dsp6__;
    }

    /**
     * <p>cmn150Dsp6 をセットします。
     * @param cmn150Dsp6 cmn150Dsp6
     */
    public void setCmn150Dsp6(int cmn150Dsp6) {
        cmn150Dsp6__ = cmn150Dsp6;
    }

    /**
     * <p>cmn150DspLabelList を取得します。
     * @return cmn150DspLabelList
     */
    public List<LabelValueBean> getCmn150DspLabelList() {
        return cmn150DspLabelList__;
    }

    /**
     * <p>cmn150DspLabelList をセットします。
     * @param cmn150DspLabelList cmn150DspLabelList
     */
    public void setCmn150DspLabelList(List<LabelValueBean> cmn150DspLabelList) {
        cmn150DspLabelList__ = cmn150DspLabelList;
    }

    /**
     * <p>noSelectWeatherAreaCombo を取得します。
     * @return noSelectWeatherAreaCombo
     */
    public List<LabelValueBean> getNoSelectWeatherAreaCombo() {
        return noSelectWeatherAreaCombo__;
    }

    /**
     * <p>cmn150backPage を取得します。
     * @return cmn150backPage
     */
    public int getCmn150backPage() {
        return cmn150backPage__;
    }

    /**
     * <p>cmn150backPage をセットします。
     * @param cmn150backPage cmn150backPage
     */
    public void setCmn150backPage(int cmn150backPage) {
        cmn150backPage__ = cmn150backPage;
    }

    /**
     * <p>noSelectWeatherAreaCombo をセットします。
     * @param noSelectWeatherAreaCombo noSelectWeatherAreaCombo
     */
    public void setNoSelectWeatherAreaCombo(
            List<LabelValueBean> noSelectWeatherAreaCombo) {
        noSelectWeatherAreaCombo__ = noSelectWeatherAreaCombo;
    }

    /**
     * <p>selectWeatherAreaCombo を取得します。
     * @return selectWeatherAreaCombo
     */
    public List<LabelValueBean> getSelectWeatherAreaCombo() {
        return selectWeatherAreaCombo__;
    }

    /**
     * <p>selectWeatherAreaCombo をセットします。
     * @param selectWeatherAreaCombo selectWeatherAreaCombo
     */
    public void setSelectWeatherAreaCombo(
            List<LabelValueBean> selectWeatherAreaCombo) {
        selectWeatherAreaCombo__ = selectWeatherAreaCombo;
    }

    /**
     * <p>cmn150MainStatus を取得します。
     * @return cmn150MainStatus
     */
    public String getCmn150MainStatus() {
        return cmn150MainStatus__;
    }

    /**
     * <p>cmn150MainStatus をセットします。
     * @param cmn150MainStatus cmn150MainStatus
     */
    public void setCmn150MainStatus(String cmn150MainStatus) {
        cmn150MainStatus__ = cmn150MainStatus;
    }

    /**
     * <p>cmn150WeatherUI を取得します。
     * @return cmn150WeatherUI
     * @see jp.groupsession.v2.cmn.cmn150.Cmn150Form#cmn150WeatherUI__
     */
    public Cmn150WeatherSelector getCmn150WeatherUI() {
        return cmn150WeatherUI__;
    }

    /**
     * <p>cmn150WeatherUI をセットします。
     * @param cmn150WeatherUI cmn150WeatherUI
     * @see jp.groupsession.v2.cmn.cmn150.Cmn150Form#cmn150WeatherUI__
     */
    public void setCmn150WeatherUI(Cmn150WeatherSelector cmn150WeatherUI) {
        cmn150WeatherUI__ = cmn150WeatherUI;
    }
}
