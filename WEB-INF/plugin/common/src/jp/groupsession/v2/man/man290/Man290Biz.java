package jp.groupsession.v2.man.man290;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.base.CmnInfoBinDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoTagModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン インフォメーション登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man290Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param appRoot ROOTパス
     * @param tempDir テンポラリDIR
     * @param con コネクション
     * @param domain ドメイン
     * @param cmd 操作種別パラメータ
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル処理例外
     * @throws TempFileException 添付ファイル処理例外
     * @throws IOException  添付ファイル処理例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public void setInitData(Man290ParamModel paramMdl,
                           String appRoot, String tempDir, Connection con, String domain,
                           String cmd)
    throws SQLException, IOToolsException, TempFileException, IOException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        CommonBiz cmnBiz = new CommonBiz();
        int imsSid = NullDefault.getInt(paramMdl.getMan320SelectedSid(), -1);
        CmnInfoMsgDao infoDao = new CmnInfoMsgDao(con);
        CmnInfoMsgModel infoMdl = infoDao.select(imsSid);

        UDate dspDate = new UDate();
        if (infoMdl != null) {
            dspDate = infoMdl.getImsFrDate();
        }
        UDate now = new UDate();
        int minDiffYear = now.getYear() - dspDate.getYear();
        
        //年コンボが登録年-1 ～ 登録年 + 3になるように設定 
        paramMdl.setMan290YearRangeMin(minDiffYear + 1);
        paramMdl.setMan290YearRangeMax(3 - minDiffYear);
        
        //新規モード
        UDate frDate = null;
        UDate toDate = null;
        frDate = new UDate();
        toDate = new UDate();
        frDate.setHour(GSConstMain.DF_FROM_HOUR);
        frDate.setMinute(GSConstMain.DF_FROM_MINUTES);
        toDate.setHour(GSConstMain.DF_TO_HOUR);
        toDate.setMinute(GSConstMain.DF_TO_MINUTES);

        if (cmd.equals("man320edit") && infoMdl != null) { //編集モード
            int man290FrYear = infoMdl.getImsFrDate().getYear();
            int man290FrMonth = infoMdl.getImsFrDate().getMonth();
            int man290FrDay = infoMdl.getImsFrDate().getIntDay();
            int man290ToYear = infoMdl.getImsToDate().getYear();
            int man290ToMonth = infoMdl.getImsToDate().getMonth();
            int man290ToDay = infoMdl.getImsToDate().getIntDay();

            paramMdl.setMan290FrYear(
                    NullDefault.getString(String.valueOf(man290FrYear),
                            String.valueOf(frDate.getYear())));
            paramMdl.setMan290FrMonth(
                    NullDefault.getString(String.valueOf(man290FrMonth),
                            String.valueOf(frDate.getMonth())));
            paramMdl.setMan290FrDay(
                    NullDefault.getString(String.valueOf(man290FrDay),
                            String.valueOf(frDate.getIntDay())));

            paramMdl.setMan290ToYear(
                    NullDefault.getString(String.valueOf(man290ToYear),
                            String.valueOf(toDate.getYear())));
            paramMdl.setMan290ToMonth(
                    NullDefault.getString(String.valueOf(man290ToMonth),
                            String.valueOf(toDate.getMonth())));
            paramMdl.setMan290ToDay(
                    NullDefault.getString(String.valueOf(man290ToDay),
                            String.valueOf(toDate.getIntDay())));

            //時間
            int man290FrHour = infoMdl.getImsFrDate().getIntHour();
            int man290FrMin = infoMdl.getImsFrDate().getIntMinute();
            int man290ToHour = infoMdl.getImsToDate().getIntHour();
            int man290ToMin = infoMdl.getImsToDate().getIntMinute();

            paramMdl.setMan290FrHour(
                    NullDefault.getString(String.valueOf(man290FrHour),
                    String.valueOf(frDate.getIntHour())));
            paramMdl.setMan290FrMin(
                    NullDefault.getString(String.valueOf(man290FrMin),
                            String.valueOf(frDate.getIntMinute())));
            paramMdl.setMan290ToHour(
                    NullDefault.getString(String.valueOf(man290ToHour),
                            String.valueOf(toDate.getIntHour())));
            paramMdl.setMan290ToMin(
                    NullDefault.getString(String.valueOf(man290ToMin),
                            String.valueOf(toDate.getIntMinute())));

            //メッセージ
            paramMdl.setMan290Msg(infoMdl.getImsMsg());
            //内容
            paramMdl.setMan290Value(infoMdl.getImsValue());

            CmnInfoTagDao tagDao = new CmnInfoTagDao(con);
            ArrayList<CmnInfoTagModel> tagMdlList = null;
            ArrayList<String> list = new ArrayList<String>();
            tagMdlList = tagDao.select(imsSid);

            for (int i = 0; i < tagMdlList.size(); i++) {
                if (tagMdlList.get(i).getUsrSid() != -1) {
                    list.add(String.valueOf(tagMdlList.get(i).getUsrSid()));
                }

                if (tagMdlList.get(i).getGrpSid() != -1) {
                    list.add(String.valueOf("G"
                                            + tagMdlList.get(i).getGrpSid()));
                }

            }
            String[] usrGrpSids = (String[]) list.toArray(new String[list.size()]);
            paramMdl.setMan290memberSid(usrGrpSids);

            //拡張区分
            paramMdl.setMan290ExtKbn(
                    NullDefault.getString(paramMdl.getMan290ExtKbn(),
                            String.valueOf(infoMdl.getImsKbn())));
            if (infoMdl.getImsKbn() > 1) {
                paramMdl.setMan290elementKbn(1);
            }
            paramMdl.setMan290HolKbn(infoMdl.getImsHolkbn());

            //週
            paramMdl.setMan290Week(
                    NullDefault.getString(paramMdl.getMan290Week(),
                            String.valueOf(infoMdl.getImsWeek())));
            //日
            paramMdl.setMan290Day(
                    NullDefault.getString(paramMdl.getMan290Day(),
                            String.valueOf(infoMdl.getImsDay())));
            //曜日
            if (paramMdl.getMan290Dweek() == null
            || paramMdl.getMan290Dweek().length <= 0) {
                __setDayWeekToForm(paramMdl, infoMdl);
            }

            CmnInfoBinDao binDao = new CmnInfoBinDao(con);
            ArrayList<CmnBinfModel> tmpFileList = binDao.getBinList(infoMdl.getImsSid());

            //添付ファイルをテンポラリディレクトリへ移動する
            String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
            if (tmpFileList != null && tmpFileList.size() > 0) {
                String[] binSids = new String[tmpFileList.size()];

                //バイナリSIDの取得
                for (int i = 0; i < tmpFileList.size(); i++) {
                    binSids[i] = String.valueOf(tmpFileList.get(i).getBinSid());
                }

                //取得したバイナリSIDからバイナリ情報を取得
                List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, domain);

                int fileNum = 1;
                for (CmnBinfModel binData : binList) {
                    cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                    fileNum++;
                }
            }

        } else { //新規
            //日付
            paramMdl.setMan290FrYear(
                    NullDefault.getString(paramMdl.getMan290FrYear(),
                            String.valueOf(frDate.getYear())));
            paramMdl.setMan290FrMonth(
                    NullDefault.getString(paramMdl.getMan290FrMonth(),
                            String.valueOf(frDate.getMonth())));
            paramMdl.setMan290FrDay(
                    NullDefault.getString(paramMdl.getMan290FrDay(),
                            String.valueOf(frDate.getIntDay())));

            paramMdl.setMan290ToYear(
                    NullDefault.getString(paramMdl.getMan290ToYear(),
                            String.valueOf(toDate.getYear())));
            paramMdl.setMan290ToMonth(
                    NullDefault.getString(paramMdl.getMan290ToMonth(),
                            String.valueOf(toDate.getMonth())));
            paramMdl.setMan290ToDay(
                    NullDefault.getString(paramMdl.getMan290ToDay(),
                            String.valueOf(toDate.getIntDay())));
            //時間
            paramMdl.setMan290FrHour(
                    NullDefault.getString(paramMdl.getMan290FrHour(),
                    String.valueOf(frDate.getIntHour())));
            paramMdl.setMan290FrMin(
                    NullDefault.getString(paramMdl.getMan290FrMin(),
                            String.valueOf(frDate.getIntMinute())));
            paramMdl.setMan290ToHour(
                    NullDefault.getString(paramMdl.getMan290ToHour(),
                            String.valueOf(toDate.getIntHour())));
            paramMdl.setMan290ToMin(
                    NullDefault.getString(paramMdl.getMan290ToMin(),
                            String.valueOf(toDate.getIntMinute())));

            //拡張区分
            paramMdl.setMan290ExtKbn(
                    NullDefault.getString(paramMdl.getMan290ExtKbn(),
                            String.valueOf(GSConstMain.INFO_KBN_DAY)));
            //週
            paramMdl.setMan290Week(
                    NullDefault.getString(paramMdl.getMan290Week(),
                            String.valueOf(-1)));
            //日
            paramMdl.setMan290Day(
                    NullDefault.getString(paramMdl.getMan290Day(),
                            String.valueOf(-1)));
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getMan290FrDate() == null) {
            dateBiz.setDateParam(paramMdl, "man290FrDate",
                    "man290FrYear", "man290FrMonth", "man290FrDay", null);
        }
        
        if (paramMdl.getMan290FrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "man290FrTime", "man290FrHour", "man290FrMin", null);
        }
        
        if (paramMdl.getMan290ToDate() == null) {
            dateBiz.setDateParam(paramMdl, "man290ToDate",
                    "man290ToYear", "man290ToMonth", "man290ToDay", null);
        }
        
        if (paramMdl.getMan290ToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "man290ToTime", "man290ToHour", "man290ToMin", null);
        }

        //添付ファイル一覧を設定
        paramMdl.setMan290FileLabelList(cmnBiz.getTempFileLabelList(tempDir));

        //共通項目
        //拡張設定 日コンボを作成
        paramMdl.setMan290ExDayLabel(getDayLabel(true));
        //拡張設定 週コンボを作成
        paramMdl.setMan290WeekLabel(getWeekLabel());
    }

    /**
     * <br>[機  能] 日コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param exFlg 拡張フラグ
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayLabel(boolean exFlg) {
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (exFlg) {
            labelList.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.noset2"), String.valueOf(0)));
        }
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + gsMsg.getMessage("cmn.day"),
                            String.valueOf(day)));
            day++;
        }

        if (exFlg) {
            labelList.add(
                    new LabelValueBean(gsMsg.getMessage("tcd.tcd050kn.01"),
                                    Integer.toString(GSConstCommon.LAST_DAY_OF_MONTH)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 週コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  週コンボ
     */
    public ArrayList<LabelValueBean> getWeekLabel() {
        int week = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.noset2"), String.valueOf(0)));
        for (int i = 0; i < 5; i++) {
            labelList.add(
                    new LabelValueBean(
                            MaintenanceUtil.getWeek(week, reqMdl__),
                            String.valueOf(week)));
            week++;
        }
        return labelList;
    }

    /**
     *
     * <br>[機  能] 曜日指定パラメータを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param model ScheduleExSearchModel
     */
    private void __setDayWeekToForm(Man290ParamModel paramMdl, CmnInfoMsgModel model) {

        ArrayList<String> dWeekList = new ArrayList<String>();
        if (model.getImsDweek1() == 1) {
            dWeekList.add("1");
        }
        if (model.getImsDweek2() == 1) {
            dWeekList.add("2");
        }
        if (model.getImsDweek3() == 1) {
            dWeekList.add("3");
        }
        if (model.getImsDweek4() == 1) {
            dWeekList.add("4");
        }
        if (model.getImsDweek5() == 1) {
            dWeekList.add("5");
        }
        if (model.getImsDweek6() == 1) {
            dWeekList.add("6");
        }
        if (model.getImsDweek7() == 1) {
            dWeekList.add("7");
        }
        paramMdl.setMan290Dweek((String[]) dWeekList.toArray(new String[dWeekList.size()]));
    }
}
