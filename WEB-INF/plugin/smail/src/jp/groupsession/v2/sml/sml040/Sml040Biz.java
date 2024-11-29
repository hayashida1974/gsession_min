package jp.groupsession.v2.sml.sml040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlUserDao;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 個人設定 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml040Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     *@param reqMdl リクエスト情報
     */
    public Sml040Biz(RequestModel reqMdl) {
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
    public void setInitData(RequestModel reqMdl, Sml040ParamModel paramMdl, Connection con)
        throws SQLException {

        log__.debug("初期表示データセット");

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
        SmlUserModel result = smlCmnBiz.getSmailUserConf(sessionUsrSid, con);

        //表示件数を取得
        paramMdl.setSml040ViewCnt(result.getSmlMaxDsp());

        //自動リロード時間を取得
        paramMdl.setSml040ReloadTime(
                NullDefault.getString(
                        String.valueOf(result.getSmlReload()), paramMdl.getSml040ReloadTime()));
        paramMdl.setSml040TimeLabelList(__getTimeLabel());

        //写真表示設定を取得
        paramMdl.setSml040PhotoDsp(Integer.toString(result.getSmlPhotoDsp()));

        //添付画像区分設定を取得
        paramMdl.setSml040ImageTempDsp(result.getSmlTempDsp());

        //管理者設定から設定項目の設定種別を取得
        SmlAdminModel admConf = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);
        paramMdl.setSml040MaxDspStype(admConf.getSmaMaxDspStype());
        paramMdl.setSml040ReloadTimeStype(admConf.getSmaReloadDspStype());
        paramMdl.setSml040PhotoDspStype(admConf.getSmaPhotoDspStype());
        paramMdl.setSml040AttachImgDspStype(admConf.getSmaAttachDspStype());

        //メイン表示設定を取得
        paramMdl.setSml040kidokuKbn(Integer.toString(result.getSmlMainKbn()));
        paramMdl.setSml040mainDsp(Integer.toString(result.getSmlMainCnt()));
        paramMdl.setSml040mainSort(Integer.toString(result.getSmlMainSort()));

        //コンボ作成
        ArrayList<LabelValueBean> dspList = new ArrayList<LabelValueBean>();
        dspList.add(new LabelValueBean("5", "5"));
        for (int i = 10; i <= 50; i = i + 10) {
            dspList.add(new LabelValueBean(Integer.toString(i),
                                           Integer.toString(i)));
        }
        paramMdl.setSml040mainDspList(dspList);
    }

    /**
     * <br>[機  能] 表示件数コンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    public List<LabelValueBean> getDspCntLavel() {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int cnt = 10; cnt <= 50; cnt += 10) {
            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 表示件数の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateDspCount(RequestModel reqMdl,
                                Sml040ParamModel paramMdl,
                                Connection con)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        SmlUserModel smlMdl = new SmlUserModel();
        smlMdl.setUsrSid(sessionUsrSid);
        smlMdl.setSmlMaxDsp(paramMdl.getSml040ViewCnt());

        smlMdl.setSmlReload(NullDefault.getInt(paramMdl.getSml040ReloadTime(), 0));

        smlMdl.setSmlEuid(sessionUsrSid);
        smlMdl.setSmlEdate(nowDate);
        smlMdl.setSmlPhotoDsp(Integer.parseInt(paramMdl.getSml040PhotoDsp()));
        smlMdl.setSmlTempDsp(paramMdl.getSml040ImageTempDsp());

        smlMdl.setSmlMainKbn(NullDefault.getInt(
                paramMdl.getSml040kidokuKbn(), GSConstSmail.SML_MAIN_KBN_MIDOKU));
        smlMdl.setSmlMainCnt(NullDefault.getInt(
                paramMdl.getSml040mainDsp(), GSConstSmail.SML_MAIN_CNT_10));
        smlMdl.setSmlMainSort(NullDefault.getInt(
                paramMdl.getSml040mainSort(), GSConstSmail.SML_MAIN_SORT_KOUJYUN));

        SmlUserDao smlDao = new SmlUserDao(con);
        int updateCnt = smlDao.updateDsp(smlMdl);

        //更新件数が0件の場合は追加
        if (updateCnt == 0) {
            smlMdl.setSmlAuid(sessionUsrSid);
            smlMdl.setSmlAdate(nowDate);
            smlDao.insertSmlUser(smlMdl);
        }
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getTimeLabel() {
        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String minite_1 = gsMsg.getMessage("cmn.1minute");
        String minite_3 = 3 + gsMsg.getMessage("cmn.minute");
        String minite_5 = 5 + gsMsg.getMessage("cmn.minute");
        String minite_10 = 10 + gsMsg.getMessage("cmn.minute");
        String minite_20 = 20 + gsMsg.getMessage("cmn.minute");
        String minite_30 = 30 + gsMsg.getMessage("cmn.minute");
        String minite_40 = 40 + gsMsg.getMessage("cmn.minute");
        String minite_50 = 50 + gsMsg.getMessage("cmn.minute");
        String minite_60 = 60 + gsMsg.getMessage("cmn.minute");
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
    /**
     * <br>[機  能]オペレーションログ出力用設定内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ格納モデル
     * @return オペレーションログ表示内容
     */
    public String getOpLog(Sml040ParamModel paramMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder opLog = new StringBuilder();
        //表示
        String display = gsMsg.getMessage("api.cmn.view");
        //非表示
        String nodispaly = gsMsg.getMessage("cmn.hide");

        //*表示件数*//
        if (paramMdl.getSml040MaxDspStype() == GSConstSmail.DISP_CONF_USER) {
            opLog.append(__opLogValue(gsMsg.getMessage("cmn.number.display")));
            opLog.append(paramMdl.getSml040ViewCnt());
            opLog.append(gsMsg.getMessage("cmn.number"));
            opLog.append("\n");
        }

        //*自動リロード時間*//
        if (paramMdl.getSml040ReloadTimeStype() == GSConstSmail.DISP_CONF_USER) {
            String minute = gsMsg.getMessage("cmn.minute");
            opLog.append(__opLogValue(gsMsg.getMessage("cmn.auto.reload.time")));
            //自動リロード時間の変換
            switch (NullDefault.getInt(paramMdl.getSml040ReloadTime(), 0)) {
            case 60000:
                opLog.append(gsMsg.getMessage("cmn.1minute"));
                break;
            case 180000:
                opLog.append("3");
                opLog.append(minute);
                break;
            case 300000:
                opLog.append("5");
                opLog.append(minute);
                break;
            case 600000:
                opLog.append("10");
                opLog.append(minute);
                break;
            case 1200000:
                opLog.append("20");
                opLog.append(minute);
                break;
            case 1800000:
                opLog.append("30");
                opLog.append(minute);
                break;
            case 2400000:
                opLog.append("40");
                opLog.append(minute);
                break;
            case 3000000:
                opLog.append("50");
                opLog.append(minute);
                break;
            case 3600000:
                opLog.append("60");
                opLog.append(minute);
                break;
            default:
                opLog.append(gsMsg.getMessage("cmn.without.reloading"));
                break;
            }
            opLog.append("\n");
        }

        //*写真の表示設定*//
        if (paramMdl.getSml040PhotoDspStype() == GSConstSmail.DISP_CONF_USER) {
            int picture = NullDefault.getInt(paramMdl.getSml040PhotoDsp(), 0);
            opLog.append(__opLogValue(gsMsg.getMessage("sml.sml040.05")));
            if (picture == GSConstSmail.SML_PHOTO_DSP_DSP) {
                opLog.append(display);
            } else if (picture != GSConstSmail.SML_PHOTO_DSP_DSP) {
                opLog.append(nodispaly);
            }
            opLog.append("\n");
        }

        //*添付画像表示設定*//
        if (paramMdl.getSml040AttachImgDspStype() == GSConstSmail.DISP_CONF_USER) {
            int image = paramMdl.getSml040ImageTempDsp();
            opLog.append(__opLogValue(gsMsg.getMessage("sml.sml040.07")));
            if (image == GSConstSmail.SML_IMAGE_TEMP_DSP) {
                opLog.append(display);
            } else if (image == GSConstSmail.SML_IMAGE_TEMP_NOT_DSP) {
                opLog.append(nodispaly);
            }
            opLog.append("\n");
        }

        //*メイン表示件数*//
        opLog.append(__opLogValue(gsMsg.getMessage("sml.sml190.02")));
        opLog.append(paramMdl.getSml040mainDsp());
        opLog.append(gsMsg.getMessage("cmn.number"));
        opLog.append("\n");

        //*既読メール*//
        int readMail = NullDefault.getInt(paramMdl.getSml040kidokuKbn(), 0);
        opLog.append(__opLogValue(gsMsg.getMessage("sml.sml190.03")));
        if (readMail == GSConstCommon.MAIN_NOT_DSP) {
            opLog.append(nodispaly);
        } else if (readMail == GSConstCommon.MAIN_DSP) {
            opLog.append(display);
        }
        opLog.append("\n");

        //*表示順*//
        int value = NullDefault.getInt(paramMdl.getSml040mainSort(), 0);
        opLog.append(__opLogValue(gsMsg.getMessage("cmn.sort")));
        if (value == GSConstSmail.ORDER_ASC) {
            opLog.append(gsMsg.getMessage("cmn.order.desc"));
        } else if (value == GSConstSmail.ORDER_DESC) {
            opLog.append(gsMsg.getMessage("cmn.order.asc"));
        }
        return opLog.toString();
    }
    /**
     * <br>[機  能]オペレーションログ出力項目まとめロジック
     * <br>[解  説]
     * <br>[備  考]
     * @param value 設定項目
     * @return [設定項目名]
     */
    private String __opLogValue(String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(value);
        sb.append("] ");
        return sb.toString();
    }
}