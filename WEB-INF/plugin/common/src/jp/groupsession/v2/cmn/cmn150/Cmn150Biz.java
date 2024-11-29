package jp.groupsession.v2.cmn.cmn150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GsResourceBundle;
import jp.groupsession.v2.cmn.biz.MainDspBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnMdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnMdispWeatherDao;
import jp.groupsession.v2.cmn.dao.base.CmnWeatherAreaDao;
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;
import jp.groupsession.v2.cmn.model.base.CmnMdispWeatherModel;
import jp.groupsession.v2.cmn.model.base.CmnWeatherAreaModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 個人設定 メイン画面表示設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn150Biz.class);
    /** GSメッセージ */
    public GsMessage gsMsg__ = new GsMessage();

    /**
     * <p>コンストラクタ
     * @param gsMsg GsMessage
     */
    public Cmn150Biz(GsMessage gsMsg) {
        gsMsg__ = gsMsg;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn150Model パラメータ格納モデル
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Cmn150ParamModel cmn150Model,
            BaseUserModel umodel,
            Connection con) throws SQLException {

        HashMap<String, String> confMap = null;
        boolean commit = true;
        try {
            //DBより設定情報を取得。なければデフォルト値とする。
            MainDspBiz biz = new MainDspBiz();
            confMap = biz.getMainDspConfMap(umodel.getUsrsid(), con);
            con.commit();
            commit = true;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        con.setAutoCommit(true);

        //表示項目1(時計)
        if (cmn150Model.getCmn150Dsp1() == GSConstCommon.NUM_INIT) {
            cmn150Model.setCmn150Dsp1(
                    NullDefault.getInt(confMap.get(GSConstCommon.MAIN_DSPVALUE_CLOCK),
                                    GSConstCommon.MAIN_DSP));
        } else {
            cmn150Model.setCmn150Dsp1(cmn150Model.getCmn150Dsp1());
        }
        //表示項目2(最終ログイン時間)
        if (cmn150Model.getCmn150Dsp2() == GSConstCommon.NUM_INIT) {
            cmn150Model.setCmn150Dsp2(
                    NullDefault.getInt(confMap.get(GSConstCommon.MAIN_DSPVALUE_LOGINHISTORY),
                            GSConstCommon.MAIN_DSP));
        } else {
            cmn150Model.setCmn150Dsp2(cmn150Model.getCmn150Dsp2());
        }
        //表示項目3(自動リロード時間)
        if (cmn150Model.getCmn150Dsp3() == GSConstCommon.MAIN_DSPRELOAD) {
            cmn150Model.setCmn150Dsp3(
                    NullDefault.getInt(confMap.get(GSConstCommon.MAIN_DSPVALUE_AUTORELOAD),
                            GSConstCommon.MAIN_DSP));
        } else {
            cmn150Model.setCmn150Dsp3(cmn150Model.getCmn150Dsp3());
        }

        //表示ステータスを設定
        cmn150Model.setCmn150MainStatus(
        NullDefault.getString(GsResourceBundle.getString(GSConst.MAIN_STATUS), ""));

        //表示項目4(天気予報)、天気予報 地域
        if (cmn150Model.getCmn150Dsp4() == GSConstCommon.NUM_INIT) {
            String weatherDsp = confMap.get(GSConstCommon.MAIN_DSPVALUE_WEATHER);
            if (!StringUtil.isNullZeroString(weatherDsp)) {
                cmn150Model.setCmn150Dsp4(Integer.parseInt(weatherDsp));

                CmnMdispWeatherDao weatherDao = new CmnMdispWeatherDao(con);
                List<CmnMdispWeatherModel> weatherList = weatherDao.select(umodel.getUsrsid());
                if (!weatherList.isEmpty()) {
                    String[] areaSidList = new String[weatherList.size()];
                    for (int index = 0; index < weatherList.size(); index++) {
                        areaSidList[index] = String.valueOf(weatherList.get(index).getMdwArea());
                    }
                    cmn150Model.setCmn150Dsp4Area(areaSidList);
                }
            } else {
                cmn150Model.setCmn150Dsp4(GSConstCommon.MAIN_DSP);
            }

            if (cmn150Model.getCmn150Dsp4Area() == null
                    || cmn150Model.getCmn150Dsp4Area().length == 0) {
                cmn150Model.setCmn150Dsp4Area(
                        new String[] {GSConstCommon.MAIN_DSP_WEATHER_INIT_AREA});
            }

        } else {
            cmn150Model.setCmn150Dsp4(cmn150Model.getCmn150Dsp4());
        }

        //表示項目6(ニュース)
        if (cmn150Model.getCmn150Dsp6() == GSConstCommon.NUM_INIT) {
            String newsDsp = confMap.get(GSConstCommon.MAIN_DSPVALUE_NEWS);
            if (!StringUtil.isNullZeroString(newsDsp)) {
                cmn150Model.setCmn150Dsp6(Integer.parseInt(newsDsp));
            } else {
                cmn150Model.setCmn150Dsp6(GSConstCommon.MAIN_DSP);
            }
        } else {
            cmn150Model.setCmn150Dsp6(cmn150Model.getCmn150Dsp6());
        }

        //自動リロード時間のコンボを設定する。
        cmn150Model.setCmn150DspLabelList(__getCmn150DspLabelList());

        //天気予報 地域のコンボを設定する。
        __setWeatherAreaCombo(cmn150Model, con, umodel.getUsrsid());

        con.setAutoCommit(false);

    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn150Model パラメータ格納モデル
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Cmn150ParamModel cmn150Model,
            BaseUserModel umodel, Connection con) throws SQLException {

        //
        UDate now = new UDate();
        MainDspBiz mainBiz = new MainDspBiz();
        boolean commitFlg = false;
        try {
            CmnMdispDao dao = new CmnMdispDao(con);
            ArrayList<CmnMdispModel> confs = mainBiz.getMainDspConfList(umodel.getUsrsid(), con);
            boolean existWeather = false;
            boolean existNews = false;
            for (CmnMdispModel conf : confs) {
                if (conf.getMdpPid().equals(GSConstCommon.MAIN_DSPVALUE_CLOCK)) {
                    conf.setMdpDsp(cmn150Model.getCmn150Dsp1());
                    conf.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
                } else if (conf.getMdpPid().equals(GSConstCommon.MAIN_DSPVALUE_LOGINHISTORY)) {
                    conf.setMdpDsp(cmn150Model.getCmn150Dsp2());
                    conf.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
                } else if (conf.getMdpPid().equals(GSConstCommon.MAIN_DSPVALUE_WEATHER)) {
                    conf.setMdpDsp(cmn150Model.getCmn150Dsp4());
                    conf.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
                    existWeather = true;
                } else if (conf.getMdpPid().equals(GSConstCommon.MAIN_DSPVALUE_NEWS)) {
                    conf.setMdpDsp(cmn150Model.getCmn150Dsp6());
                    conf.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
                    existNews = true;
                } else {
                    conf.setMdpReload(cmn150Model.getCmn150Dsp3());
                }
                conf.setMdpEuid(umodel.getUsrsid());
                conf.setMdpEdate(now);
                dao.update(conf);
            }

            //天気予報
            if (!existWeather) {
                CmnMdispModel dspWeatherMdl = new CmnMdispModel();
                dspWeatherMdl.setUsrSid(umodel.getUsrsid());
                dspWeatherMdl.setMdpPid(GSConstCommon.MAIN_DSPVALUE_WEATHER);
                dspWeatherMdl.setMdpDsp(cmn150Model.getCmn150Dsp4());
                dspWeatherMdl.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
                dspWeatherMdl.setMdpAuid(umodel.getUsrsid());
                dspWeatherMdl.setMdpAdate(now);
                dspWeatherMdl.setMdpEuid(umodel.getUsrsid());
                dspWeatherMdl.setMdpEdate(now);
                dao.insert(dspWeatherMdl);
            }

            //天気予報 地域
            CmnMdispWeatherDao weatherDao = new CmnMdispWeatherDao(con);
            weatherDao.delete(umodel.getUsrsid());

            String[] areaList = cmn150Model.getCmn150Dsp4Area();
            if (areaList != null && areaList.length > 0) {
                CmnMdispWeatherModel weatherModel = new CmnMdispWeatherModel();
                weatherModel.setUsrSid(umodel.getUsrsid());
                weatherModel.setMdwAuid(umodel.getUsrsid());
                weatherModel.setMdwAdate(now);
                weatherModel.setMdwEuid(umodel.getUsrsid());
                weatherModel.setMdwEdate(now);

                for (int areaSort = 1; areaSort <= areaList.length; areaSort++) {
                    weatherModel.setMdwArea(Integer.parseInt(areaList[areaSort - 1]));
                    weatherModel.setMdwSort(areaSort);
                    weatherDao.insert(weatherModel);
                }
            }

            //ニュース
            if (!existNews) {
                CmnMdispModel dspNewsMdl = new CmnMdispModel();
                dspNewsMdl.setUsrSid(umodel.getUsrsid());
                dspNewsMdl.setMdpPid(GSConstCommon.MAIN_DSPVALUE_NEWS);
                dspNewsMdl.setMdpDsp(cmn150Model.getCmn150Dsp6());
                dspNewsMdl.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
                dspNewsMdl.setMdpAuid(umodel.getUsrsid());
                dspNewsMdl.setMdpAdate(now);
                dspNewsMdl.setMdpEuid(umodel.getUsrsid());
                dspNewsMdl.setMdpEdate(now);
                dao.insert(dspNewsMdl);
            }

            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getCmn150DspLabelList() {
        //分
        String textMin = gsMsg__.getMessage("cmn.minute");
        //リロードしない
        String textWithoutReloading = gsMsg__.getMessage("cmn.without.reloading");


        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();
        labelList.add(new LabelValueBean("1" + textMin, "60000"));
        labelList.add(new LabelValueBean("3" + textMin, "180000"));
        labelList.add(new LabelValueBean("5" + textMin, "300000"));
        labelList.add(new LabelValueBean("10" + textMin, "600000"));
        labelList.add(new LabelValueBean("20" + textMin, "1200000"));
        labelList.add(new LabelValueBean("30" + textMin, "1800000"));
        labelList.add(new LabelValueBean("40" + textMin, "2400000"));
        labelList.add(new LabelValueBean("50" + textMin, "3000000"));
        labelList.add(new LabelValueBean("60" + textMin, "3600000"));
        labelList.add(new LabelValueBean(textWithoutReloading, "0"));
        return labelList;
    }

    /**
     * <br>[機  能] 天気予報 地域コンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn150Model パラメータ格納モデル
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __setWeatherAreaCombo(Cmn150ParamModel cmn150Model, Connection con, int userSid)
    throws SQLException {

        List <LabelValueBean> selectAreaCombo = new ArrayList<LabelValueBean>();
        List <LabelValueBean> noSelectAreaCombo = new ArrayList<LabelValueBean>();

        CmnWeatherAreaDao weatherAreaDao = new CmnWeatherAreaDao(con);
        List<CmnWeatherAreaModel> areaList = weatherAreaDao.select();

        if (cmn150Model.getCmn150Dsp4Area() != null && cmn150Model.getCmn150Dsp4Area().length > 0) {
            for (String selectArea : cmn150Model.getCmn150Dsp4Area()) {
                int selectAreaIndex = 0;
                int selectAreaSid = Integer.parseInt(selectArea);

                for (CmnWeatherAreaModel areaData : areaList) {
                    if (areaData.getCwaSid() == selectAreaSid) {
                        break;
                    }
                    selectAreaIndex++;
                }

                if (selectAreaIndex < areaList.size()) {
                    selectAreaCombo.add(
                            new LabelValueBean(areaList.get(selectAreaIndex).getCwaName(),
                                        String.valueOf(areaList.get(selectAreaIndex).getCwaSid())));
                    areaList.remove(selectAreaIndex);
                }
            }
        }

        for (CmnWeatherAreaModel areaData : areaList) {
            noSelectAreaCombo.add(new LabelValueBean(areaData.getCwaName(),
                                String.valueOf(areaData.getCwaSid())));
        }

        cmn150Model.setSelectWeatherAreaCombo(selectAreaCombo);
        cmn150Model.setNoSelectWeatherAreaCombo(noSelectAreaCombo);
    }

    /**
     * <br>[機  能] 選択中の地域の順序を1つ繰り上げる
     * <br>[解  説]
     * <br>[備  考]
     * @param areaSid 地域リスト
     * @param upSelectSid コンボで選択中の値
     * @return 並び替え済みの地域リスト
     */
    public String[] getUpArea(String[] areaSid, String[] upSelectSid) {

        if (upSelectSid == null || upSelectSid.length < 1
        || areaSid == null || upSelectSid.length >= areaSid.length) {
            return areaSid;
        }

        ArrayList<String> userList = new ArrayList<String>();
        userList.addAll(Arrays.asList(areaSid));

        for (String userSid : upSelectSid) {
            int index = userList.indexOf(userSid);
            if (index > 0) {
                userList.remove(index);
                userList.add(index - 1, userSid);
            }
        }

        return userList.toArray(new String[userList.size()]);
    }

    /**
     * <br>[機  能] 選択中の地域の順序を1つ繰り下げる
     * <br>[解  説]
     * <br>[備  考]
     * @param areaSid 地域リスト
     * @param downSelectSid コンボで選択中の値
     * @return 並び替え済みの地域リスト
     */
    public String[] getDownArea(String[] areaSid, String[] downSelectSid) {

        if (downSelectSid == null || downSelectSid.length < 1
        || areaSid == null || downSelectSid.length >= areaSid.length) {
            return areaSid;
        }

        ArrayList<String> userList = new ArrayList<String>();
        userList.addAll(Arrays.asList(areaSid));
        int lastIndex = userList.size() - 1;

        for (int i = downSelectSid.length - 1; i >= 0; i--) {
            String userSid = downSelectSid[i];

            int index = userList.indexOf(userSid);
            if (index < lastIndex) {
                userList.remove(index);
                userList.add(index + 1, userSid);
            }
        }

        return userList.toArray(new String[userList.size()]);
    }

    /**
     * <br>[機  能]オペレーションログ出力用設定内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ格納モデル
     * @param con コネクション
     * @return オペレーションログ表示内容
     * @throws SQLException SQL実行エラー
     */
    public String getOpLog(Cmn150ParamModel paramMdl, Connection con)
            throws SQLException {

        StringBuilder sb = new StringBuilder();

        //表示
        String display = gsMsg__.getMessage("api.cmn.view");
        //非表示
        String nodispaly = gsMsg__.getMessage("cmn.hide");

        //*時計表示*//
        int clock = paramMdl.getCmn150Dsp1();
        sb.append(__opLogValue(gsMsg__.getMessage("cmn.cmn150.1")));
        if (clock == GSConstCommon.MAIN_NOT_DSP) {
            sb.append(nodispaly);
        } else if (clock == GSConstCommon.MAIN_DSP) {
            sb.append(display);
        }
        sb.append("\n");

        //*最終ログイン時間*//
        int login = paramMdl.getCmn150Dsp2();
        sb.append(__opLogValue(gsMsg__.getMessage("user.usr090.2")));
        if (login == GSConstCommon.MAIN_NOT_DSP) {
            sb.append(nodispaly);
        } else if (login == GSConstCommon.MAIN_DSP) {
            sb.append(display);
        }
        sb.append("\n");

        //*自動リロード時間*//
        int rTime = paramMdl.getCmn150Dsp3();
        //分
        String minute = gsMsg__.getMessage("cmn.minute");
        sb.append(__opLogValue(gsMsg__.getMessage("cmn.auto.reload.time")));
        //自動リロード時間の変換
        switch (rTime) {
        case 60000:
            sb.append(gsMsg__.getMessage("cmn.1minute"));
            break;
        case 180000:
            sb.append("3");
            sb.append(minute);
            break;
        case 300000:
            sb.append("5");
            sb.append(minute);
            break;
        case 600000:
            sb.append("10");
            sb.append(minute);
            break;
        case 1200000:
            sb.append("20");
            sb.append(minute);
            break;
        case 1800000:
            sb.append("30");
            sb.append(minute);
            break;
        case 2400000:
            sb.append("40");
            sb.append(minute);
            break;
        case 3000000:
            sb.append("50");
            sb.append(minute);
            break;
        case 3600000:
            sb.append("60");
            sb.append(minute);
            break;
        default:
            sb.append(gsMsg__.getMessage("cmn.without.reloading"));
            break;
        }
        sb.append("\n");

        //*天気予報*//
        int weather = paramMdl.getCmn150Dsp4();
        sb.append(__opLogValue(gsMsg__.getMessage("main.src.man001.1")));
        if (weather == GSConstCommon.MAIN_NOT_DSP) {
            sb.append(nodispaly);
        } else if (weather == GSConstCommon.MAIN_DSP) {
            sb.append(display);
        }
        sb.append("\n");

        //*天気予報 地域*//
        CmnWeatherAreaDao weatherAreaDao = new CmnWeatherAreaDao(con);
        List<CmnWeatherAreaModel> weaMdlList =
                weatherAreaDao.getWeatherArea(paramMdl.getCmn150Dsp4Area());
        sb.append(__opLogValue(gsMsg__.getMessage("cmn.cmn150.2")));
        //取得したweaMdlListの要素をCmnWeatherAreaModelに格納し、地域名を取得する
        for (CmnWeatherAreaModel areaMdl : weaMdlList) {
            String areaName = areaMdl.getCwaName();
            sb.append(areaName);
            sb.append("　");
        }
        sb.append("\n");

        //*ニュース*//
        int daynews = paramMdl.getCmn150Dsp6();
        sb.append(__opLogValue(gsMsg__.getMessage("cmn.news")));
        if (daynews == GSConstCommon.MAIN_NOT_DSP) {
            sb.append(nodispaly);
        } else if (daynews == GSConstCommon.MAIN_DSP) {
            sb.append(display);
        }
        return sb.toString();
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
