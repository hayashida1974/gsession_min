package jp.groupsession.v2.adr.adr170;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.AdrUsedDataBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrContactBinDao;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.dao.AdrContactPrjDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrContactBinModel;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.adr.model.AdrContactPrjModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] アドレス帳 コンタクト履歴登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr170Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr170Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;
    /** コンタクト履歴SID */
    private int adcsid__ = 0;
    /** コンタクト履歴SID(同時登録) */
    private ArrayList<Integer> doziAdcSid__ = new ArrayList<Integer>();

//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public Adr170Biz() {
//    }

    /**
     * <p>adcsid を取得します。
     * @return adcsid
     * @see jp.groupsession.v2.adr.adr170.Adr170Biz#adcsid__
     */
    public int getAdcsid() {
        return adcsid__;
    }

    /**
     * <p>adcsid をセットします。
     * @param adcsid adcsid
     * @see jp.groupsession.v2.adr.adr170.Adr170Biz#adcsid__
     */
    public void setAdcsid(int adcsid) {
        adcsid__ = adcsid;
    }

    /**
     * <p>doziAdcSid を取得します。
     * @return doziAdcSid
     * @see jp.groupsession.v2.adr.adr170.Adr170Biz#doziAdcSid__
     */
    public ArrayList<Integer> getDoziAdcSid() {
        return doziAdcSid__;
    }

    /**
     * <p>doziAdcSid をセットします。
     * @param doziAdcSid doziAdcSid
     * @see jp.groupsession.v2.adr.adr170.Adr170Biz#doziAdcSid__
     */
    public void setDoziAdcSid(ArrayList<Integer> doziAdcSid) {
        doziAdcSid__ = doziAdcSid;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr170Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr170ParamModel
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param buMdl セッションユーザModel
     * @param pconfig プラグインコンフィグ
     * @throws IOException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws NoSuchMethodException 日時設定時エラー
     * @throws InvocationTargetException 日時設定時エラー 
     * @throws IllegalAccessException 日時設定時エラー
     */
    public void getInitData(Connection con,
                              Adr170ParamModel paramMdl,
                              String appRoot,
                              String tempDir,
                              BaseUserModel buMdl,
                              PluginConfig pconfig)
    throws SQLException, IOToolsException, IOException,
    IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        String targetComName = "";
        String targetUsrName = "";
        int acoSid = -1;
        AdrCompanyDao comDao = new AdrCompanyDao(con);

        //コンタクト履歴登録対象者情報取得
        AdrAddressDao adrDao = new AdrAddressDao(con);
        AdrAddressModel addressMdl = adrDao.select(paramMdl.getAdr010EditAdrSid());
        if (addressMdl != null) {

            targetUsrName =
                NullDefault.getString(addressMdl.getAdrSei(), "")
                + "  "
                + NullDefault.getString(addressMdl.getAdrMei(), "");

            acoSid = addressMdl.getAcoSid();
            if (acoSid > 0) {
                //会社情報を取得
                AdrCompanyModel companyModel = comDao.select(acoSid);
                if (companyModel != null) {
                    targetComName = companyModel.getAcoName();
                }
            }
        }
        paramMdl.setAdr170ContactUserComName(targetComName);
        paramMdl.setAdr170ContactUserName(targetUsrName);

        //初回は会社コンボの選択値を、対象ユーザの会社とする
        //(※会社に所属していない場合は-1(選択してください)
        if (paramMdl.getAdr170SelectedComComboSid() < -1) {
            paramMdl.setAdr170SelectedComComboSid(acoSid);
        }

        int editSid = paramMdl.getAdr160EditSid();
        UDate now = new UDate();

        //添付ファイルセット
        CommonBiz commonBiz = new CommonBiz();
        paramMdl.setAdr170FileLabelList(commonBiz.getTempFileLabelList(tempDir));

        //画面初期値セット
        String title = "";
        int type = paramMdl.getAdr170Mark();

        String yearFrom = String.valueOf(now.getYear());
        String monthFrom = String.valueOf(now.getMonth());
        String dayFrom = String.valueOf(now.getIntDay());
        String hourFrom = String.valueOf(now.getIntHour());
        String minuteFrom = String.valueOf(now.getIntMinute() - now.getIntMinute() % 5);
        String yearTo = String.valueOf(now.getYear());
        String monthTo = String.valueOf(now.getMonth());
        String dayTo = String.valueOf(now.getIntDay());
        String hourTo = String.valueOf(now.getIntHour());
        String minuteTo = String.valueOf(now.getIntMinute() - now.getIntMinute() % 5);
        String project = "-1";
        String biko = "";
        int adcSid = 0;

        //同時登録ユーザ
        String[] saveUser = paramMdl.getSaveUser();

        //編集の場合、コンタクト履歴情報取得
        if (paramMdl.getAdr160ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            AdrContactDao adcDao = new AdrContactDao(con);
            AdrContactModel adcMdl = adcDao.select(editSid);
            if (adcMdl != null) {

                UDate cttime = NullDefault.getUDate(adcMdl.getAdcCttime(), now);
                UDate cttimeto = NullDefault.getUDate(adcMdl.getAdcCttimeTo(), now);

                title = adcMdl.getAdcTitle();
                type = adcMdl.getAdcType();
                yearFrom = String.valueOf(cttime.getYear());
                monthFrom = String.valueOf(cttime.getMonth());
                dayFrom = String.valueOf(cttime.getIntDay());
                hourFrom = String.valueOf(cttime.getIntHour());
                minuteFrom = String.valueOf(cttime.getIntMinute());
                yearTo = String.valueOf(cttimeto.getYear());
                monthTo = String.valueOf(cttimeto.getMonth());
                dayTo = String.valueOf(cttimeto.getIntDay());
                hourTo = String.valueOf(cttimeto.getIntHour());
                minuteTo = String.valueOf(cttimeto.getIntMinute());
                project = String.valueOf(adcMdl.getPrjSid());
                biko = adcMdl.getAdcBiko();
                adcSid = adcMdl.getAdcSid();

                //同時登録データがある場合
                int adcGrpSid = adcMdl.getAdcGrpSid();

                if ((saveUser == null || saveUser.length == 0)
                        && adcGrpSid > 0) {

                    //同時登録したアドレス帳のSIDを取得
                    ArrayList<Integer> intGrpAdrSid =
                        adcDao.selectGrpAdrSid(adcGrpSid);

                    if (!intGrpAdrSid.isEmpty()) {

                        ArrayList<String> convGrpAdrSid = new ArrayList<String>();
                        int myAdrSid = adcMdl.getAdrSid();

                        for (int grpAdrSid : intGrpAdrSid) {
                            //自分自身は除外する
                            if (myAdrSid != grpAdrSid) {
                                convGrpAdrSid.add(String.valueOf(grpAdrSid));
                            }
                        }

                        if (!convGrpAdrSid.isEmpty()) {
                            saveUser =
                                (String[]) convGrpAdrSid.toArray(
                                        new String[convGrpAdrSid.size()]);
                        }
                    }
                }
            }
        } else {
            type = 1;
        }

        //選択済ユーザ
        AddressBiz adrBiz = new AddressBiz(reqMdl_);
        boolean admFlg = adrBiz.isAdmin(con, buMdl.getUsrsid());
        ArrayList<AdrAddressModel> grpSelectUserList =
            adrDao.selectViewAdrList(saveUser, buMdl.getUsrsid(), admFlg);
        ArrayList<String> convGrpAdrSid = new ArrayList<String>();
        for (AdrAddressModel adrMdl : grpSelectUserList) {
            convGrpAdrSid.add(String.valueOf(adrMdl.getAdrSid()));
        }

        paramMdl.setSaveUser(
                (String[]) convGrpAdrSid.toArray(
                        new String[convGrpAdrSid.size()]));

        GsMessage gsMsg = new GsMessage(reqMdl_);

        //画面値セット
        //タイトル
        paramMdl.setAdr170title(NullDefault.getString(paramMdl.getAdr170title(), title));

        //種別
        if (paramMdl.getAdr170Mark() == -1) {
            paramMdl.setAdr170Mark(type);
        }
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        String contactDateStart =
                gsMsg.getMessage("address.114") + " " + gsMsg.getMessage("cmn.start");
        String contactDateEnd =
                gsMsg.getMessage("address.114") + " " + gsMsg.getMessage("cmn.end");
        
        //コンタクト日付From
        paramMdl.setAdr170enterContactYear(NullDefault.getString(
                paramMdl.getAdr170enterContactYear(), yearFrom));
        paramMdl.setAdr170enterContactMonth(NullDefault.getString(
                paramMdl.getAdr170enterContactMonth(), monthFrom));
        paramMdl.setAdr170enterContactDay(NullDefault.getString(
                paramMdl.getAdr170enterContactDay(), dayFrom));
        
        if (paramMdl.getAdr170enterContactDateFr() == null) {
            dateBiz.setDateParam(paramMdl, "adr170enterContactDateFr",
                    "adr170enterContactYear", "adr170enterContactMonth",
                    "adr170enterContactDay", contactDateStart + gsMsg.getMessage("cmn.date2"));
        }
        
        paramMdl.setAdr170enterContactHour(NullDefault.getString(
                paramMdl.getAdr170enterContactHour(), hourFrom));
        
        paramMdl.setAdr170enterContactMinute(NullDefault.getString(
                paramMdl.getAdr170enterContactMinute(), minuteFrom));
                
        if (paramMdl.getAdr170enterContactTimeFr() == null) {
            dateBiz.setTimeParam(paramMdl, "adr170enterContactTimeFr",
                    "adr170enterContactHour", "adr170enterContactMinute",
                    contactDateStart + gsMsg.getMessage("cmn.time"));
        }
        
        //コンタクト日付To
        paramMdl.setAdr170enterContactYearTo(NullDefault.getString(
                paramMdl.getAdr170enterContactYearTo(), yearTo));
        paramMdl.setAdr170enterContactMonthTo(NullDefault.getString(
                paramMdl.getAdr170enterContactMonthTo(), monthTo));
        paramMdl.setAdr170enterContactDayTo(NullDefault.getString(
                paramMdl.getAdr170enterContactDayTo(), dayTo));
        if (paramMdl.getAdr170enterContactDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "adr170enterContactDateTo",
                    "adr170enterContactYearTo", "adr170enterContactMonthTo",
                    "adr170enterContactDayTo", contactDateEnd + gsMsg.getMessage("cmn.date2"));
        }
        
        paramMdl.setAdr170enterContactHourTo(NullDefault.getString(
                paramMdl.getAdr170enterContactHourTo(), hourTo));
        
        paramMdl.setAdr170enterContactMinuteTo(NullDefault.getString(
                paramMdl.getAdr170enterContactMinuteTo(), minuteTo));
        
        if (paramMdl.getAdr170enterContactTimeTo() == null) {
            dateBiz.setTimeParam(paramMdl, "adr170enterContactTimeTo",
                    "adr170enterContactHourTo", "adr170enterContactMinuteTo",
                    contactDateEnd + gsMsg.getMessage("cmn.time"));
        }
        
        //プロジェクト
        paramMdl.setAdr170enterContactProject(NullDefault.getString(
                paramMdl.getAdr170enterContactProject(), project));
        //備考
        paramMdl.setAdr170biko(NullDefault.getString(paramMdl.getAdr170biko(), biko));

        //プロジェクトプラグイン使用有無
        if (pconfig.getPlugin("project") != null) {
            log__.debug("プロジェクト使用");
            //プロジェクト情報を設定する。
            __setProjectData(paramMdl, con, adcSid);

            //プロジェクト表示リストを設定する。
            __setProjectDspList(paramMdl, con);

            paramMdl.setProjectPluginKbn(GSConst.PLUGIN_USE);

        } else {
            paramMdl.setProjectPluginKbn(GSConst.PLUGIN_NOT_USE);
            log__.debug("プロジェクト使用不可");
        }


    }

     /**
     * <br>[機  能] コンタクト履歴SIDからコンタクト履歴情報を取得し、削除確認メッセージを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param albSid コンタクト履歴SID
     * @param msgRes MessageResources
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeletePosMsg(Connection con,
                                  int albSid,
                                  MessageResources msgRes)
    throws SQLException {

        String msg = "";

        //コンタクト履歴タイトル取得
        AdrContactDao adcDao = new AdrContactDao(con);
        AdrContactModel adcMdl = adcDao.select(albSid);
        String albName = "";
        if (adcMdl != null) {
            albName = NullDefault.getString(adcMdl.getAdcTitle(), "");
        }
        GsMessage gsMsg = new GsMessage(reqMdl_);
        msg = msgRes.getMessage("sakujo.kakunin.list", gsMsg.getMessage("address.6"),
                StringUtilHtml.transToHTmlPlusAmparsant(albName));

        return msg;
    }

    /**
     * <br>[機  能] コンタクト履歴を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr170ParamModel
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteAlb(
            Connection con, Adr170ParamModel paramMdl, int userSid) throws SQLException {

        int editAlbSid = paramMdl.getAdr160EditSid();
        boolean commitFlg = false;

        try {

            AdrAddressDao adrDao = new AdrAddressDao(con);
            AdrContactDao adcDao = new AdrContactDao(con);
            AdrContactModel adcMdl = adcDao.select(editAlbSid);

            int adcGrpSid = -1;
            if (adcMdl != null) {

                adcGrpSid = adcMdl.getAdcGrpSid();

                //コンタクト履歴情報のデータ使用量を登録(削除対象のデータ使用量を減算)
                AdrUsedDataBiz usedDataBiz = new AdrUsedDataBiz(con);
                usedDataBiz.insertContactSize(Arrays.asList(editAlbSid), false);

                //バイナリ情報,コンタクト履歴添付情報を削除
                deleteBin(con, editAlbSid, userSid, adcGrpSid);

                //コンタクト履歴情報を物理削除する
                if (adcGrpSid > 0) {
                    AdrContactDao contactDao = new AdrContactDao(con);
                    ArrayList<AdrContactModel> doziAdrList =
                        contactDao.selectGrpList(-1, adcGrpSid);

                    if (!doziAdrList.isEmpty()) {
                        for (AdrContactModel doziMdl : doziAdrList) {

                            //編集可能ならば
                            int adrSid = doziMdl.getAdrSid();
                            String[] saveUser = new String[1];
                            saveUser[0] = String.valueOf(adrSid);
                            AddressBiz adrBiz = new AddressBiz(reqMdl_);
                            boolean admFlg = adrBiz.isAdmin(con, userSid);
                            ArrayList<AdrAddressModel> doziUser =
                                adrDao.selectAdrList(saveUser, userSid, admFlg);

                            if (!doziUser.isEmpty()) {
                                adcDao.delete(doziMdl.getAdcSid());
                            }
                        }
                    }
                } else {
                    adcDao.delete(editAlbSid);
                }
                //コンタクト履歴プロジェクト情報を削除する。
                AdrContactPrjDao aprjDao = new AdrContactPrjDao(con);
                aprjDao.delete(adcMdl.getAdcSid());
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] コンタクト履歴添付情報を元に添付ファイルを指定したテンポラリディレクトリに作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adcSid コンタクト履歴SID
     * @param userSid ユーザSID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param now 現在日時
     * @param domain ドメイン
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setTempFileData(Connection con,
                                int adcSid,
                                int userSid,
                                String appRoot,
                                String tempDir,
                                UDate now,
                                String domain)
    throws SQLException, IOException, IOToolsException, TempFileException {

        String dateStr = now.getDateString(); //現在日付の文字列(YYYYMMDD)
        AdrContactBinDao binDao = new AdrContactBinDao(con);
        CommonBiz cmnBiz = new CommonBiz();

        String[] binSids = binDao.getTmpFileList(adcSid, userSid);
        if (binSids == null || binSids.length < 1) {
            return;
        }
        List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, domain);

        int fileNum = 1;
        for (CmnBinfModel binData : binList) {
            cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
            fileNum++;
        }
    }

    /**
     * バイナリ情報,コンタクト履歴添付情報を削除
     * @param con コネクション
     * @param adcsid コンタクト履歴SID
     * @param userSid ユーザSID
     * @param adcGrpSid 同時登録グループSID
     * @throws SQLException SQL実行例外
     */
    public void deleteBin(Connection con, int adcsid, int userSid, int adcGrpSid)
        throws SQLException {

        //コンタクト履歴添付情報からバイナリSID一覧取得
        Adr170BinDao cbinDao = new Adr170BinDao(con);
        List<Long> binSidList = cbinDao.selectBinSidList(adcsid);

        //バイナリ情報を論理削除
        CmnBinfDao delbinDao = new CmnBinfDao(con);
        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(userSid);
        cbMdl.setBinUpdate(new UDate());
        delbinDao.updateJKbn(cbMdl, binSidList);

        //コンタクト履歴添付情報を物理削除
        cbinDao.deleteBin(adcsid);

        AdrAddressDao adrDao = new AdrAddressDao(con);

        if (adcGrpSid > 0) {

            AdrContactDao adcDao = new AdrContactDao(con);
            ArrayList<AdrContactModel> doziAdrList =
                adcDao.selectGrpList(adcsid, adcGrpSid);

            if (!doziAdrList.isEmpty()) {
                for (AdrContactModel doziMdl : doziAdrList) {

                    //編集可能ならば
                    int adrSid = doziMdl.getAdrSid();
                    String[] saveUser = new String[1];
                    saveUser[0] = String.valueOf(adrSid);
                    AddressBiz adrBiz = new AddressBiz(reqMdl_);
                    boolean admFlg = adrBiz.isAdmin(con, userSid);
                    ArrayList<AdrAddressModel> doziUser =
                        adrDao.selectAdrList(saveUser, userSid, admFlg);

                    if (!doziUser.isEmpty()) {
                        //コンタクト履歴添付情報からバイナリSID一覧取得
                        List<Long> doziBinSidList =
                            cbinDao.selectBinSidList(doziMdl.getAdcSid());

                        //バイナリ情報を論理削除
                        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
                        cbMdl.setBinUpuser(userSid);
                        cbMdl.setBinUpdate(new UDate());
                        delbinDao.updateJKbn(cbMdl, doziBinSidList);

                        //コンタクト履歴添付情報を物理削除
                        cbinDao.deleteBin(doziMdl.getAdcSid());
                    }
                }
            }
        }
    }

    /**
     * <br>[機  能] プロジェクト表示リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setProjectDspList(
            Adr170ParamModel paramMdl, Connection con) throws SQLException {

        String[] prjSids = paramMdl.getAdr170ProjectSid();
        if (prjSids == null || prjSids.length < 1) {
            paramMdl.setAdr170ProjectList(null);
            return;
        }
        AddressDao adrDao = new AddressDao(con);
        List<LabelValueBean> prjList = adrDao.getProjectData(prjSids);

        paramMdl.setAdr170ProjectList(prjList);

    }

    /**
     * <br>[機  能] DBよりプロジェクト情報を取得し、設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param con コネクション
     * @param adcSid コンタクトSID
     * @throws SQLException SQL実行例外
     */
    private void __setProjectData(
            Adr170ParamModel paramMdl, Connection con, int adcSid) throws SQLException {

        if (paramMdl.getAdr160ProcMode() == GSConstAddress.PROCMODE_ADD
                || adcSid < 1
                || paramMdl.getProjectPluginKbn() > -1) {
            return;
        }
        AdrContactPrjDao aprjDao = new  AdrContactPrjDao(con);
        List<AdrContactPrjModel> prjList = aprjDao.select(adcSid);

        if (prjList != null && prjList.size() > 0) {
            String[] prjSids = new String[prjList.size()];
            int i = 0;
            for (AdrContactPrjModel model : prjList) {
                prjSids[i] = String.valueOf(model.getPrjSid());
                i++;
            }
            paramMdl.setAdr170ProjectSid(prjSids);
        }
    }
    
    /**
     * <br>[機  能] 稟議情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig プラグイン情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void entryData(Adr170ParamModel paramMdl,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir,
                                PluginConfig pluginConfig,
                                Connection con)
    throws Exception {
        log__.debug("START");

        int oldAdcGrpSid = -1;
        AdrUsedDataBiz usedDataBiz = new AdrUsedDataBiz(con);
        if (paramMdl.getAdr160ProcMode() == GSConstAddress.PROCMODE_ADD) {
            //コンタクト履歴情報 登録
            doInsert(paramMdl, cntCon, userSid, con);
        } else if (paramMdl.getAdr160ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            //コンタクト履歴情報のデータ使用量を登録(変更前のデータ使用量を減算)
            usedDataBiz.insertContactSize(Arrays.asList(paramMdl.getAdr160EditSid()), false);

            //コンタクト履歴情報 更新
            oldAdcGrpSid = doUpdate(paramMdl, cntCon, userSid, con);
        }

        //バイナリー情報,コンタクト履歴添付情報 登録
        doInsertBin(paramMdl, tempDir, appRootPath, cntCon, userSid, oldAdcGrpSid, con);

        //コンタクト履歴情報のデータ使用量を登録
        usedDataBiz.insertContactSize(Arrays.asList(adcsid__), true);

        log__.debug("End");
    }

    /**
     * <br>[機  能] コンタクト履歴情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Adr170ParamModel paramMdl,
        MlCountMtController cntCon,
        int userSid,
        Connection con) throws SQLException {

        boolean commitFlg = false;
        int adcGrpSid = -1;

        try {

            con.setAutoCommit(false);

            //コンタクト履歴SID採番
            int adcSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                       GSConst.SBNSID_SUB_CONTACT,
                                                       userSid);

            AddressBiz adrBiz = new AddressBiz(reqMdl_);
            AdrAddressDao adrDao = new AdrAddressDao(con);
            boolean admFlg = adrBiz.isAdmin(con, userSid);
            ArrayList<AdrAddressModel> doziUser =
                adrDao.selectViewAdrList(paramMdl.getSaveUser(), userSid, admFlg);

            if (!doziUser.isEmpty()) {
                //コンタクト履歴グループSID採番
                adcGrpSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                         GSConst.SBNSID_SUB_CONTACT_GRP,
                                                         userSid);
            }

            //登録用Model作成
            AdrContactModel adcMdl = __getUpdateModel(adcSid, adcGrpSid, paramMdl, userSid);

            //insert
            AdrContactDao adcDao = new AdrContactDao(con);
            adcDao.insert(adcMdl);

            if (!doziUser.isEmpty()) {

                ArrayList<Integer> doziAdcSidList = new ArrayList<Integer>();

                for (AdrAddressModel adrMdl : doziUser) {

                    //コンタクト履歴SID採番
                    int doziAdcSid =
                        (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                     GSConst.SBNSID_SUB_CONTACT,
                                                     userSid);

                    adcMdl.setAdcSid(doziAdcSid);
                    adcMdl.setAdrSid(adrMdl.getAdrSid());
                    adcDao.insert(adcMdl);

                    doziAdcSidList.add(doziAdcSid);
                }
                setDoziAdcSid(doziAdcSidList);
            }

            //プロジェクト情報を登録する。
            __insertProject(paramMdl, con, userSid, adcSid);

            //コンタクト履歴SIDセット
            adcsid__ = adcSid;

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] コンタクト履歴情報の更新,バイナリー情報の削除,コンタクト履歴添付情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr170ParamModel
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return oldAdcGrpSid 既存データ同時登録SID
     */
    public int doUpdate(Adr170ParamModel paramMdl,
            MlCountMtController cntCon, int userSid, Connection con)
    throws SQLException {

        boolean commitFlg = false;
        int oldAdcGrpSid = -1;

        try {

            con.setAutoCommit(false);

            //コンタクト履歴SID
            int editAdcSid = paramMdl.getAdr160EditSid();

            AdrContactDao adcDao = new AdrContactDao(con);
            AdrContactModel oldAdcMdl = adcDao.select(editAdcSid);
            adcDao.delete(editAdcSid);

            if (oldAdcMdl != null) {
                oldAdcGrpSid = oldAdcMdl.getAdcGrpSid();
            }

            //コンタクト履歴SID採番
            int adcSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                       GSConst.SBNSID_SUB_CONTACT,
                                                       userSid);

            AddressBiz adrBiz = new AddressBiz(reqMdl_);
            boolean admFlg = adrBiz.isAdmin(con, userSid);

            AdrAddressDao adrDao = new AdrAddressDao(con);
            ArrayList<AdrAddressModel> doziUser =
                adrDao.selectAdrList(paramMdl.getSaveUser(), userSid, admFlg);

            int adcGrpSid = -1;
            if (!doziUser.isEmpty()) {
                //コンタクト履歴グループSID採番
                adcGrpSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                         GSConst.SBNSID_SUB_CONTACT_GRP,
                                                         userSid);
            }

            //登録用Model作成
            AdrContactModel adcMdl = __getUpdateModel(adcSid, adcGrpSid, paramMdl, userSid);

            //insert
            adcDao.insert(adcMdl);

            if (!doziUser.isEmpty()) {

                ArrayList<Integer> doziAdcSidList = new ArrayList<Integer>();

                for (AdrAddressModel adrMdl : doziUser) {

                    //コンタクト履歴SID採番
                    int doziAdcSid =
                        (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                     GSConst.SBNSID_SUB_CONTACT,
                                                     userSid);

                    adcMdl.setAdcSid(doziAdcSid);
                    adcMdl.setAdrSid(adrMdl.getAdrSid());
                    adcDao.insert(adcMdl);

                    doziAdcSidList.add(doziAdcSid);
                }
                setDoziAdcSid(doziAdcSidList);
            }

            //コンタクト履歴プロジェクト情報を更新する。
            __updateProject(paramMdl, con, userSid, adcSid, editAdcSid);

            //コンタクト履歴SIDセット
            adcsid__ = adcSid;

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        return oldAdcGrpSid;
    }

    /**
     * <br>[機  能] コンタクト履歴情報の登録・更新用Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param adcSid コンタクト履歴SID
     * @param adcGrpSid コンタクト履歴グループSID
     * @param paramMdl Adr170ParamModel
     * @param userSid ログインユーザSID
     * @return AdrContactModel 登録・更新用Model
     * @throws SQLException SQL実行例外
     */
    private AdrContactModel __getUpdateModel(int adcSid,
                                              int adcGrpSid,
                                              Adr170ParamModel paramMdl,
                                              int userSid) throws SQLException {

        UDate now = new UDate();

        AdrContactModel mdl = new AdrContactModel();
        //コンタクト履歴SID
        mdl.setAdcSid(adcSid);
        //コンタクト履歴グループSID
        mdl.setAdcGrpSid(adcGrpSid);
        //アドレス帳SID
        mdl.setAdrSid(paramMdl.getAdr010EditAdrSid());
        //タイトル
        mdl.setAdcTitle(NullDefault.getString(paramMdl.getAdr170title(), ""));
        //コンタクト履歴種別
        mdl.setAdcType(paramMdl.getAdr170Mark());
        //コンタクト日時From
        mdl.setAdcCttime(__getCttime(paramMdl));
        //コンタクト日時To
        mdl.setAdcCttimeTo(__getCttimeTo(paramMdl));
//        //プロジェクトSID
//        if (paramMdl.getAdr170enterContactProject().equals("-1")) {
//            mdl.setPrjSid(0);
//        } else {
//            mdl.setPrjSid(Integer.parseInt(paramMdl.getAdr170enterContactProject()));
//        }
        //備考
        mdl.setAdcBiko(NullDefault.getString(paramMdl.getAdr170biko(), ""));

        mdl.setAdcAuid(userSid);
        mdl.setAdcAdate(now);
        mdl.setAdcEuid(userSid);
        mdl.setAdcEdate(now);

        return mdl;
    }

    /**
     * <br>[機  能] コンタクト日時Fromを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @return UDate コンタクト日時
     */
    private UDate __getCttime(Adr170ParamModel paramMdl) {

        UDate ctdate = new UDate();
        ctdate.setTimeStamp(paramMdl.getAdr170enterContactYear()
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactMonth(), "00")
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactDay(), "00")
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactHour(), "00")
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactMinute(), "00")
                          + "00");
        return ctdate;
    }

    /**
     * <br>[機  能] コンタクト日時Toを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @return UDate コンタクト日時
     */
    private UDate __getCttimeTo(Adr170ParamModel paramMdl) {

        UDate ctdate = new UDate();
        ctdate.setTimeStamp(paramMdl.getAdr170enterContactYearTo()
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactMonthTo(), "00")
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactDayTo(), "00")
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactHourTo(), "00")
                          + StringUtil.toDecFormat(paramMdl.getAdr170enterContactMinuteTo(), "00")
                          + "00");
        return ctdate;
    }

    /**
     * <br>[機  能] 添付ファイルの登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param tempDir テンポラリディレクトリ
     * @param appRootPath アプリケーションルート
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param oldGrpSid 既存データ同時登録SID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void doInsertBin(
        Adr170ParamModel paramMdl,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        int oldGrpSid,
        Connection con) throws SQLException, IOToolsException, IOException, TempFileException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);
            UDate now = new UDate();

            //修正モードのときコンタクト履歴、バイナリ情報、コンタクト履歴添付情報を削除
            if (paramMdl.getAdr160ProcMode() == GSConstAddress.PROCMODE_EDIT) {
                Adr170Biz biz = new Adr170Biz(reqMdl_);
                biz.deleteBin(con, paramMdl.getAdr160EditSid(), userSid, oldGrpSid);

                if (oldGrpSid > 0) {
                    AdrContactDao contactDao = new AdrContactDao(con);
                    ArrayList<AdrContactModel> doziAdrList =
                        contactDao.selectGrpList(paramMdl.getAdr160EditSid(), oldGrpSid);
                    AdrAddressDao adrDao = new AdrAddressDao(con);

                    if (!doziAdrList.isEmpty()) {
                        for (AdrContactModel doziMdl : doziAdrList) {

                            //編集可能ならば
                            int adrSid = doziMdl.getAdrSid();
                            String[] saveUser = new String[1];
                            saveUser[0] = String.valueOf(adrSid);

                            AddressBiz adrBiz = new AddressBiz(reqMdl_);
                            boolean admFlg = adrBiz.isAdmin(con, userSid);

                            ArrayList<AdrAddressModel> doziUser =
                                adrDao.selectAdrList(saveUser, userSid, admFlg);

                            if (!doziUser.isEmpty()) {
                                contactDao.delete(doziMdl.getAdcSid());
                            }
                        }
                    }
                }
            }

            //バイナリー情報を登録
            CommonBiz biz = new CommonBiz();
            List<String> binList =
                biz.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);

            //コンタクト履歴添付情報を登録
            Adr170BinDao binDao = new Adr170BinDao(con);
            AdrContactBinModel sparam = new AdrContactBinModel();
            sparam.setAdcSid(adcsid__);
            sparam.setAcbAuid(userSid);
            sparam.setAcbAdate(now);
            sparam.setAcbEuid(userSid);
            sparam.setAcbEdate(now);
            binDao.insertAdrBin(sparam, binList);

            ArrayList<Integer> doziAdcsid = getDoziAdcSid();
            if (!getDoziAdcSid().isEmpty()) {
                for (int doziAdcSid : doziAdcsid) {
                    //バイナリー情報を登録
                    List<String> doziBinList =
                        biz.insertSameBinInfo(
                                con, tempDir, appRootPath, cntCon, userSid, now);

                    //コンタクト履歴添付情報を登録
                    AdrContactBinModel doziSparam = new AdrContactBinModel();
                    doziSparam.setAdcSid(doziAdcSid);
                    doziSparam.setAcbAuid(userSid);
                    doziSparam.setAcbAdate(now);
                    doziSparam.setAcbEuid(userSid);
                    doziSparam.setAcbEdate(now);
                    binDao.insertAdrBin(doziSparam, doziBinList);
                }
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] コンタクト履歴プロジェクト情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adcSid コンタクト履歴SID
     * @throws SQLException SQL実行例外
     */
    private void __insertProject(Adr170ParamModel paramMdl,
            Connection con, int userSid, int adcSid) throws SQLException {

        String[] prjSids = paramMdl.getAdr170ProjectSid();
        if (prjSids == null || prjSids.length < 1) {
            return;
        }

        AdrContactPrjDao aprjDao = new AdrContactPrjDao(con);
        AdrContactPrjModel aprjModel = new AdrContactPrjModel();
        UDate now = new UDate();
        aprjModel.setAdcAuid(userSid);
        aprjModel.setAdcAdate(now);
        aprjModel.setAdcEuid(userSid);
        aprjModel.setAdcEdate(now);
        aprjModel.setAdcSid(adcSid);

        for (String projectSid : prjSids) {
            aprjModel.setPrjSid(Integer.parseInt(projectSid));
            aprjDao.insert(aprjModel);
        }
    }

    /**
     * <br>[機  能] コンタクト履歴プロジェクト情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr170ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adcSid 新コンタクト履歴SID
     * @param editAdcSid コンタクト履歴SID
     * @throws SQLException SQL実行例外
     */
    private void __updateProject(Adr170ParamModel paramMdl,
            Connection con, int userSid, int adcSid, int editAdcSid) throws SQLException {

        AdrContactPrjDao aprjDao = new AdrContactPrjDao(con);

        //コンタクト履歴プロジェクト情報を削除する
        aprjDao.delete(editAdcSid);

        //コンタクト履歴プロジェクト情報を登録する
        __insertProject(paramMdl, con, userSid, adcSid);

    }
}