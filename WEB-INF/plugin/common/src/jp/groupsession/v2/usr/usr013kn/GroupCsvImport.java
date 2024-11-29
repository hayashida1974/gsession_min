package jp.groupsession.v2.usr.usr013kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupClassDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;

/**
 * <br>[機  能] グループ情報ファイル(CSV)の取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GroupCsvImport extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GroupCsvImport.class);
    /** 実行モード*/
    private int mode__;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    private HttpServletRequest req__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** セッションユーザ */
    private int sessionUser__;
    /** システム日付 */
    private UDate sysUd__;
    /** 取込みグループ情報リスト*/
    private ArrayList<CmnGroupmModel> grpmList__ = null;
    /** 既存のグループ情報更新フラグ */
    private int updateFlg__ = 0;
    /** ユーザリスナー */
    private IUserGroupListener[] lis__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param cntCon 採番コントローラ
     * @param userSid セッションユーザSID
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     * @param con コネクション
     * @param updateFlg 既存のユーザ情報更新フラグ
     */
    public GroupCsvImport(HttpServletRequest req,
                          MlCountMtController cntCon,
                          int userSid,
                          int mode,
                          Connection con,
                          int updateFlg) {

        setReq(req);
        setCntCon(cntCon);
        setSessionUser(userSid);
        setMode(mode);
        setCon(con);
        setSysUd(new UDate());
        setGrpmList(new ArrayList<CmnGroupmModel>());
        setUpdateFlg(updateFlg);
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     */
    public GroupCsvImport(HttpServletRequest req, int mode) {

        setReq(req);
        setMode(mode);
        setGrpmList(new ArrayList<CmnGroupmModel>());
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return grpmList__ グループ情報リスト
     * @throws  Exception   実行時例外
     */
    public ArrayList<CmnGroupmModel> importCsv(String tmpFileDir)
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
        return grpmList__;
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

        //処理モード
        if (mode__ == 0) {
            __import(num, lineStr);
        } else {
            //登録処理無し
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
    private void __getData(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //グループ情報
        CmnGroupmModel groupmModel = new CmnGroupmModel();
        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //グループID
            if (j == 1) {
                groupmModel.setGrpId(NullDefault.getString(buff, ""));
            }
            //グループ名
            if (j == 2) {
                groupmModel.setGrpName(NullDefault.getString(buff, ""));
            }
            //グループ名カナ
            if (j == 3) {
                groupmModel.setGrpNameKn(NullDefault.getString(buff, ""));
            }
            //コメント
            if (j == 4) {
                groupmModel.setGrpComment(NullDefault.getString(buff, ""));
            }
        }
        grpmList__.add(groupmModel);
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
    private void __import(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        String parentGpId = "";

        CmnGroupmModel grpmModel = new CmnGroupmModel();
        CmnGroupmDao grpDao = new CmnGroupmDao(con__);
        boolean existFlg = false;

        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //グループID
            if (j == 1) {
                if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE) {
                    existFlg = grpDao.existGroupidEdit(-1, buff);
                }
                grpmModel.setGrpId(NullDefault.getString(buff, ""));
            }
            //グループ名
            if (j == 2) {
                grpmModel.setGrpName(NullDefault.getString(buff, ""));
            }

            //グループ名カナ
            if (j == 3) {
                grpmModel.setGrpNameKn(NullDefault.getString(buff, ""));
            }

            //親グループID
            if (j == 4) {
                if (!StringUtil.isNullZeroStringSpace(buff)) {
                    parentGpId = buff.trim();
                }
            }

            //コメント
            if (j == 5) {
                grpmModel.setGrpComment(NullDefault.getString(buff, ""));
            }

        }
        int gsid = 0;

        //グループマスタ
        grpmModel.setGrpJkbn(CmnGroupmDao.GRP_JKBN_LIVING);
        grpmModel.setGrpAuid(sessionUser__);
        grpmModel.setGrpAdate(sysUd__);
        grpmModel.setGrpEuid(sessionUser__);
        grpmModel.setGrpEdate(sysUd__);

        //表示順
        grpmModel.setGrpSort(0);

        //グループ階層情報を登録する。
        CmnGroupClassDao gclsDao = new CmnGroupClassDao(con__);

        if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE && existFlg) {
            CmnGroupClassModel gclass = new CmnGroupClassModel();
            CmnGroupmModel groupModel = grpDao.getGroupInf(grpmModel.getGrpId());
            if (groupModel != null) {
                gsid = groupModel.getGrpSid();
                grpmModel.setGrpSid(gsid);
                //グループ更新処理
                grpDao.update(grpmModel);
            }

            if (!StringUtil.isNullZeroStringSpace(parentGpId.trim())) {
                GroupDao gDao = new GroupDao(con__);
                CmnGroupmModel parentGroupModel = grpDao.getGroupInf(parentGpId);

                //親階層モデルを取得
                CmnGroupClassModel parModel = gDao.getGroupClassModel(
                                                    parentGroupModel.getGrpSid());
                if (parModel != null) {

                    //移動グループ階層を取得
                    ArrayList<CmnGroupClassModel> gcList = gDao.getUnderGroupClassList(gsid);
                    CmnGroupClassModel gcModel = new CmnGroupClassModel();

                    //グループ階層情報を登録
                    for (int i = 0; i < gcList.size(); i++) {
                        gcModel = (CmnGroupClassModel) gcList.get(i);
                        try {
                            gcModel = __addParentsGroup(parModel, gcModel, gsid);
                        } catch (CloneNotSupportedException e) {
                            log__.error("複製Objectの生成に失敗");
                            throw e;
                        }

                        gcModel.setGclAuid(sessionUser__);
                        gcModel.setGclAdate(sysUd__);
                        gcModel.setGclEuid(sessionUser__);
                        gcModel.setGclEdate(sysUd__);
                        //既存グループ階層を削除
                        gclsDao.delete((CmnGroupClassModel) gcList.get(i));
                        //グループ階層を登録
                        gclsDao.insert(gcModel);
                    }
                    gclsDao.update(gclass);
                }
            }

        } else {
            gsid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_USER,
                    SaibanModel.SBNSID_SUB_GROUP,
                    sessionUser__);
            grpmModel.setGrpSid(gsid);

            //グループ登録処理
            grpDao.insert(grpmModel);

            //親階層モデルを取得
            CmnGroupClassModel classModel = null;

            if (!StringUtil.isNullZeroStringSpace(parentGpId.trim())) {
                GroupDao gDao = new GroupDao(con__);
                CmnGroupmModel groupModel = grpDao.getGroupInf(parentGpId.trim());
                classModel = gDao.getGroupClassModel(groupModel.getGrpSid());
            }

            if (classModel == null) {
                classModel = new CmnGroupClassModel();
            }
            classModel.addUnder(gsid);
            classModel.setGclAuid(sessionUser__);
            classModel.setGclAdate(sysUd__);
            classModel.setGclEuid(sessionUser__);
            classModel.setGclEdate(sysUd__);
            gclsDao.insert(classModel);

        }

        //各プラグインリスナーを呼び出し
        if (updateFlg__ != GSConstUser.IMPORT_MODE_UPDATE || !existFlg) {
            for (int i = 0; i < lis__.length; i++) {
                lis__[i].addGroup(con__, gsid, sessionUser__);
            }
        }

        grpmList__.add(grpmModel);
    }

    /**
     * 移動するグループ階層モデルを親グループ階層モデルへ追加した状態の
     * グループ階層モデルを取得します。
     * @param parModel 親グループ階層モデル
     * @param mvModel 移動するグループ階層モデル
     * @param gSid 移動するグループSID
     * @return GroupClassModel
     * @throws CloneNotSupportedException CloneNotSupportedException
     */
    private CmnGroupClassModel __addParentsGroup(
            CmnGroupClassModel parModel,
            CmnGroupClassModel mvModel,
        int gSid
        ) throws CloneNotSupportedException {
        if (parModel == null) {
            parModel = new CmnGroupClassModel();
        }
        CmnGroupClassModel gcModel = new CmnGroupClassModel();
        gcModel = (CmnGroupClassModel) parModel.clone();


        ArrayList<Integer> list = mvModel.getGroupSids(gSid);
        for (int i = 0; i < list.size(); i++) {
            if (((Integer) list.get(i)).intValue() != -1) {
                log__.debug("移動するグループSID ==>" + (Integer) list.get(i));
                gcModel.addUnder(((Integer) list.get(i)).intValue());
            }
        }
        return gcModel;
    }

    /**
     * <p>updateFlg を取得します。
     * @return updateFlg
     */
    public int getUpdateFlg() {
        return updateFlg__;
    }
    /**
     * <p>updateFlg をセットします。
     * @param updateFlg updateFlg
     */
    public void setUpdateFlg(int updateFlg) {
        updateFlg__ = updateFlg;
    }

    /**
     * <p>cntCon を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }

    /**
     * <p>cntCon をセットします。
     * @param cntCon cntCon
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>grpmList を取得します。
     * @return grpmList
     */
    public ArrayList<CmnGroupmModel> getGrpmList() {
        return grpmList__;
    }

    /**
     * <p>grpmList をセットします。
     * @param grpmList grpmList
     */
    public void setGrpmList(ArrayList<CmnGroupmModel> grpmList) {
        grpmList__ = grpmList;
    }

    /**
     * <p>req を取得します。
     * @return req
     */
    public HttpServletRequest getReq() {
        return req__;
    }

    /**
     * <p>req をセットします。
     * @param req req
     */
    public void setReq(HttpServletRequest req) {
        req__ = req;
    }

    /**
     * <p>sessionUser を取得します。
     * @return sessionUser
     */
    public int getSessionUser() {
        return sessionUser__;
    }

    /**
     * <p>sessionUser をセットします。
     * @param sessionUser sessionUser
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
    }

    /**
     * <p>sysUd を取得します。
     * @return sysUd
     */
    public UDate getSysUd() {
        return sysUd__;
    }

    /**
     * <p>sysUd をセットします。
     * @param sysUd sysUd
     */
    public void setSysUd(UDate sysUd) {
        sysUd__ = sysUd;
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