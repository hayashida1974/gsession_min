package jp.groupsession.v2.adr.adr161;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
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
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.adr.model.AdrContactPrjModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] アドレス帳 コンタクト履歴画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr161Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr161Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public Adr161Biz() {
//    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr161Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr161ParamModel
     * @param appRoot アプリケーションのルートパス
     * @param buMdl セッションユーザModel
     * @param pconfig プラグインコンフィグ
     * @throws IOException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void getInitData(Connection con,
                              Adr161ParamModel paramMdl,
                              String appRoot,
                              PluginConfig pconfig,
                              BaseUserModel buMdl)
    throws SQLException, IOToolsException, IOException {

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

        //遷移フラグ
        paramMdl.setSeniFlg("1");

        paramMdl.setAdr161ContactUserComName(targetComName);
        paramMdl.setAdr161ContactUserName(targetUsrName);

        int editSid = paramMdl.getAdr160EditSid();
        UDate now = new UDate();

        //画面初期値セット
        String title = "";
        int type = paramMdl.getAdr161Mark();

        String yearFrom = "";
        String monthFrom = "";
        String dayFrom = "";
        String hourFrom = "";
        String minuteFrom = "";
        String yearTo = "";
        String monthTo = "";
        String dayTo = "";
        String hourTo = "";
        String minuteTo = "";
        String project = "-1";
        String biko = "";
        int adcSid = 0;

        //同時登録ユーザ
        String[] saveUser = paramMdl.getAdr161saveUser();

        //コンタクト履歴情報取得
        AdrContactDao adcDao = new AdrContactDao(con);
        AdrContactModel adcMdl = adcDao.select(editSid);
        if (adcMdl != null) {

            UDate cttime = NullDefault.getUDate(adcMdl.getAdcCttime(), now);
            UDate cttimeto = NullDefault.getUDate(adcMdl.getAdcCttimeTo(), now);

            title = adcMdl.getAdcTitle();
            type = adcMdl.getAdcType();
            yearFrom = String.valueOf(cttime.getStrYear());
            monthFrom = String.valueOf(cttime.getStrMonth());
            dayFrom = String.valueOf(cttime.getStrDay());
            hourFrom = String.valueOf(cttime.getStrHour());
            minuteFrom = String.valueOf(cttime.getStrMinute());
            yearTo = String.valueOf(cttimeto.getStrYear());
            monthTo = String.valueOf(cttimeto.getStrMonth());
            dayTo = String.valueOf(cttimeto.getStrDay());
            hourTo = String.valueOf(cttimeto.getStrHour());
            minuteTo = String.valueOf(cttimeto.getStrMinute());
            project = String.valueOf(adcMdl.getPrjSid());
            biko = adcMdl.getAdcBiko();
            adcSid = adcMdl.getAdcSid();

            //同時登録データがある場合
            int adcGrpSid = adcMdl.getAdcGrpSid();

            if ((saveUser == null  || saveUser.length == 0)
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

        //選択済ユーザ
        AddressBiz adrBiz = new AddressBiz(reqMdl_);
        boolean admFlg = adrBiz.isAdmin(con, buMdl.getUsrsid());
        ArrayList<AdrAddressModel> grpSelectUserList =
            adrDao.selectViewAdrList(saveUser, buMdl.getUsrsid(), admFlg);
        paramMdl.setAdr161DoujiUser(grpSelectUserList);

        //画面値セット
        //タイトル
        paramMdl.setAdr161title(NullDefault.getString(paramMdl.getAdr161title(), title));

        //種別
        if (paramMdl.getAdr161Mark() == -1) {
            paramMdl.setAdr161Mark(type);
        }
        //コンタクト日付From
        String cttime = yearFrom + "/" + monthFrom + "/" + dayFrom
                        + " " + hourFrom + ":" + minuteFrom;
        paramMdl.setAdr161ContactFrom(cttime);
        //コンタクト日付To
        String cttimeTo = yearTo + "/" + monthTo + "/" + dayTo + " " + hourTo + ":" + minuteTo;
        paramMdl.setAdr161ContactTo(cttimeTo);

        //プロジェクト
        paramMdl.setAdr161enterContactProject(NullDefault.getString(
                paramMdl.getAdr161enterContactProject(), project));
        //備考
        paramMdl.setAdr161biko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getAdr161biko(), biko)));

        //同時登録ユーザ
        paramMdl.setAdr161saveUser(saveUser);

        Adr161BinDao binDao = new Adr161BinDao(con);
        //表示用(ファイル名にサイズ追加)
        paramMdl.setTmpFileList(binDao.getAddressTmpFileList(editSid));

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

        //コンタクト履歴編集権限
        if (adcMdl.getAdcAuid() != buMdl.getUsrsid()) {
            if (!adrBiz.isEditAddressData(con, paramMdl.getAdr010EditAdrSid(), buMdl.getUsrsid())) {
                paramMdl.setAdr161EditFlg(GSConstAddress.CONTACT_NOT_EDIT);
            }
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
        String albName = NullDefault.getString(adcMdl.getAdcTitle(), "");

        GsMessage gsMsg = new GsMessage(reqMdl_);
        msg = msgRes.getMessage("sakujo.kakunin.list", gsMsg.getMessage("address.6"),
                StringUtilHtml.transToHTmlPlusAmparsant(albName));

        return msg;
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
     * <br>[機  能] プロジェクト表示リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr161ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setProjectDspList(
            Adr161ParamModel paramMdl, Connection con) throws SQLException {

        String[] prjSids = paramMdl.getAdr161ProjectSid();
        if (prjSids == null || prjSids.length < 1) {
            paramMdl.setAdr161ProjectList(null);
            return;
        }
        AddressDao adrDao = new AddressDao(con);
        List<LabelValueBean> prjList = adrDao.getProjectData(prjSids);

        paramMdl.setAdr161ProjectList(prjList);

    }

    /**
     * <br>[機  能] DBよりプロジェクト情報を取得し、設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr161ParamModel
     * @param con コネクション
     * @param adcSid コンタクトSID
     * @throws SQLException SQL実行例外
     */
    private void __setProjectData(
            Adr161ParamModel paramMdl, Connection con, int adcSid) throws SQLException {

        if (paramMdl.getAdr160ProcMode() == GSConstAddress.PROCMODE_ADD
                || adcSid < 1
                || paramMdl.getProjectPluginKbn() > 0) {
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
            paramMdl.setAdr161ProjectSid(prjSids);
        }

    }
}