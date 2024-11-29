package jp.groupsession.v2.man.man330kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.man.man330kn.model.Man330knCsvModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 所属情報CSVファイルの取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330knCsvImport extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man330knCsvImport.class);

    /** モード */
    private int mode__ = 0;
    /** コネクション */
    private Connection con__ = null;
    /** セッションユーザ */
    private int sessionUser__;
    /** システム日付 */
    private UDate sysUd__;
    /** 取込みデータリスト */
    private List<Man330knCsvModel> importList__;
    /** ユーザリスナー */
    private IUserGroupListener[] lis__ = null;

    /**
     * @return con__ を戻す。
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * @param con con__ をセット。
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * @return sessionUser__ を戻す。
     */
    public int getSessionUser() {
        return sessionUser__;
    }
    /**
     * @param sessionUser sessionUser__ をセット。
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
    }
    /**
     * @return sysUd__ を戻す。
     */
    public UDate getSysUd() {
        return sysUd__;
    }
    /**
     * @param sysUd sysUd__ をセット。
     */
    public void setSysUd(UDate sysUd) {
        sysUd__ = sysUd;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param mode モード 0:登録件数取得 1:登録実行
     * @param con コネクション
     */
    public Man330knCsvImport(int mode, Connection con) {
        setCon(con);
        setMode(mode);
        setSysUd(new UDate());
        importList__ = new ArrayList<Man330knCsvModel>();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param mode モード 0:登録件数取得 1:登録実行
     * @param usrSid ユーザSID
     * @param con コネクション
     */
    public Man330knCsvImport(int mode, int usrSid, Connection con) {
        setCon(con);
        setMode(mode);
        setSysUd(new UDate());
        setSessionUser(usrSid);
        importList__ = new ArrayList<Man330knCsvModel>();
    }

    /**
     * <br>[機  能] CSVファイルを取り込む
     * <br>[解  説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return userInfList__
     * @throws  Exception   実行時例外
     */
    public List<Man330knCsvModel> importCsv(String tmpFileDir)
        throws Exception {

        //テンポラリディレクトリにあるファイル名称を取得
        String saveFileName = "";
        List<String> fileList = IOTools.getFileNames(tmpFileDir);
        for (int i = 0; i < fileList.size(); i++) {
            //ファイル名を取得
            String fileName = fileList.get(i);
            if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                continue;
            }

            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tmpFileDir, fileName);
            Object fObj = objFile.load();
            if (fObj == null) {
                continue;
            }
            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            saveFileName = fMdl.getSaveFileName();
        }
        String csvFile = tmpFileDir + saveFileName;

        //ファイル取込
        readFile(new File(csvFile), Encoding.WINDOWS_31J);

        //登録・登録予定内容を設定
        return importList__;
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        if (getMode() == 1) {
            //登録実行
            __importData(num, lineStr);
        } else {
            //登録データ件数取得
            __getData(num, lineStr);
        }
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __importData(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //ユーザ情報
        Man330knCsvModel model = new Man330knCsvModel();
        List<String> grpDataList = new ArrayList<String>();
        List<String> cntList = new ArrayList<String>();

        String defGrpId = "";

        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //ユーザID
            if (j == 1) {
                model.setUserId(NullDefault.getString(buff, ""));

            } else if (j == 2) {
                //デフォルトグループID
                defGrpId = NullDefault.getString(buff, "");
                cntList.add(defGrpId);

            } else {
                //グループID
                if (!StringUtil.isNullZeroString(buff)) {
                    grpDataList.add(buff);
                    cntList.add(buff);
                }
            }
        }
        model.setGrpDataList(cntList);
        importList__.add(model);

        //ユーザSIDを取得
        CmnUsrmDao cuDao = new CmnUsrmDao(con__);
        CmnUsrmModel cuMdl = cuDao.select(model.getUserId());
        int userSid = cuMdl.getUsrSid();

        //ユーザ所属
        CmnBelongmDao bdao = new CmnBelongmDao(con__);

        //登録ユーザが管理者かどうか
        CmnBelongmModel adminMdl = bdao.select(userSid, GSConstUser.SID_ADMIN);

        //変更前の所属グループを取得する
        List<Integer> pastGsidList = bdao.selectUserBelongGroupSid(userSid);

        //所属グループ情報を削除する。
        if (adminMdl != null) {
            //管理者権限を持つユーザ
            //管理者グループ以外を全削除
            bdao.delGrp(GSConstUser.SID_ADMIN, userSid);

        } else {
            //一般ユーザ
            bdao.deleteUserBelongGroup(userSid);
        }

        //グループ情報
        CmnGroupmDao grpDao = new CmnGroupmDao(con__);
        String[] grpIds =
            (String[]) grpDataList.toArray(new String[grpDataList.size()]);

        //デフォルトグループのSIDを取得
        CmnGroupmModel defoGrpMdl = grpDao.getGroupInf(defGrpId);
        int dgsid = 0;
        int gsid = 0;
        dgsid = defoGrpMdl.getGrpSid();
        gsid = dgsid;
        //デフォルトグループを登録する
        CmnBelongmModel defoGrpModel = __createCmnBelongmModel(dgsid, gsid,
                userSid, sessionUser__, sysUd__);
        if (gsid != GSConstUser.SID_ADMIN) {
            bdao.insert(defoGrpModel);
        }

        if (grpIds.length > 0) {

            //通常グループのSIDを取得
            List<CmnGroupmModel> grpMdlList = grpDao.selectGrpData(grpIds,
                    CmnGroupmDao.GRP_JKBN_LIVING);
            //通常グループを登録する
            for (int i = 0; i < grpMdlList.size(); i++) {
                gsid = grpMdlList.get(i).getGrpSid();
                CmnBelongmModel bmodel = __createCmnBelongmModel(dgsid, gsid,
                userSid, sessionUser__, sysUd__);

                if (gsid != GSConstUser.SID_ADMIN) {
                    bdao.insert(bmodel);
                }
            }
        }

        //更新前所属グループ
        int[] pastGsids = new int[pastGsidList.size()];
        for (int gidIndex = 0; gidIndex < pastGsidList.size(); gidIndex++) {
            pastGsids[gidIndex] = pastGsidList.get(gidIndex);
        }
        //更新後所属グループ
        List<Integer> belongGsidList = bdao.selectUserBelongGroupSid(userSid);
        int[] belongGsids = new int[belongGsidList.size()];
        for (int gidIndex = 0; gidIndex < belongGsidList.size(); gidIndex++) {
            belongGsids[gidIndex] = belongGsidList.get(gidIndex);
        }

        for (int i = 0; i < lis__.length; i++) {
            lis__[i].changeBelong(con__, userSid, pastGsids, belongGsids, sessionUser__);
        }
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __getData(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //ユーザ情報
        Man330knCsvModel model = new Man330knCsvModel();
        List<String> grpDataList = new ArrayList<String>();

        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //ユーザID
            if (j == 1) {
                model.setUserId(NullDefault.getString(buff, ""));
            } else {
                //グループID(デフォルトグループも含む)
                if (!StringUtil.isNullZeroString(buff)) {
                    grpDataList.add(buff);
                }
            }
        }
        model.setGrpDataList(grpDataList);
        importList__.add(model);
    }

    /**
     * <br>[機  能] ユーザ所属マスタの項目の共通セットを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param dgSid デフォルトグループSID
     * @param gSid グループSID
     * @param uSid ユーザSID
     * @param sessionUser セッションユーザID
     * @param now タイムスタンプ
     * @return CmnBelongmModel
     */
    private CmnBelongmModel __createCmnBelongmModel(int dgSid,
                                                     int gSid,
                                                     int uSid,
                                                     int sessionUser,
                                                     UDate now) {

        CmnBelongmModel bmodel = new CmnBelongmModel();

        bmodel.setGrpSid(gSid);
        bmodel.setUsrSid(uSid);
        //システム項目
        bmodel.setBegAuid(sessionUser);
        bmodel.setBegAdate(now);
        bmodel.setBegEuid(sessionUser);
        bmodel.setBegEdate(now);
        if (gSid == dgSid) {
            log__.debug(">>>デフォルトグループ");
            bmodel.setBegDefgrp(CmnBelongmModel.DEFGRP_FLG_DEFAULT);
        } else {
            log__.debug(">>>所属グループ");
            bmodel.setBegDefgrp(CmnBelongmModel.DEFGRP_FLG_NORMAL);
        }

        return bmodel;
    }

    /**
     * <p>importList を取得します。
     * @return importList
     */
    public List<Man330knCsvModel> getImportList() {
        return importList__;
    }
    /**
     * <p>importList をセットします。
     * @param importList importList
     */
    public void setImportList(List<Man330knCsvModel> importList) {
        importList__ = importList;
    }
    /**
     * <p>mode を取得します。
     * @return mode
     */
    public int getMode() {
        return mode__;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(int mode) {
        mode__ = mode;
    }
    /**
     * <p>lis を取得します。
     * @return lis
     */
    public IUserGroupListener[] getLis() {
        return lis__;
    }
    /**
     * <p>lis をセットします。
     * @param lis lis
     */
    public void setLis(IUserGroupListener[] lis) {
        lis__ = lis;
    }
}