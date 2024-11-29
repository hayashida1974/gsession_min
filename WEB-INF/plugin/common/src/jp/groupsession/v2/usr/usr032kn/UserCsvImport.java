package jp.groupsession.v2.usr.usr032kn;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmHistoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupClassDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmHistoryModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.convert.ConvertConst;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.usr031kn.Usr031knBiz;
import jp.groupsession.v2.usr.usr032.UserCsvCheck;
import jp.groupsession.v2.usr.usr032.UserCsvCheck.COLNO;

/**
 * <br>[機  能] ユーザインポート CSVファイル取り込み処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserCsvImport extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UserCsvImport.class);

    /** 実行モード*/
    private int mode__;
    /** 取り込みモード*/
    private int torikomiMode__;
    /** 自動インポートモード */
    private int autoImpMode__;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** 所属グループ */
    private List<CmnGroupmModel> glist__ = null;
    /** デフォルトグループ */
    private CmnGroupmModel dfGroup__ = null;
    /** セッションユーザ */
    private int sessionUser__;
    /** システム日付 */
    private UDate sysUd__;
    /** 取込みユーザ情報リスト*/
    private ArrayList<CmnUsrmInfModel> userInfList__ = null;
    /** 未登録役職 */
    private HashMap<String, String> posMap__;
    /** ユーザリスナー */
    private IUserGroupListener[] lis__ = null;
    /** ログイン処理実行Biz */
    private ILogin loginBiz__ = null;
    /** 既存のユーザ情報更新フラグ */
    private int updateFlg__ = 0;
    /** パスワード更新フラグ */
    private int updatePassFlg__;
    /** グループ作成フラグ */
    private int insertFlg__ = 0;
    /** 初回ログイン時、パスワード変更区分 */
    private int pswdKbn__ = GSConstUser.PSWD_UPDATE_OFF;
    /** グループID,名称重複チェック用MAP */
    private HashMap<String, String> groupIdnmMap__;
    /** グループId格納変数） */
    private String groupId__;
    /** グループ名称格納変数 */
    private String groupNm__;

    /** csv列定義実態 モードごとの列の違いが反映された配列*/
    private COLNO[] colArr__;


    /**
     * @return userInfList を戻します。
     */
    public ArrayList<CmnUsrmInfModel> getUserInfList() {
        return userInfList__;
    }
    /**
     * @param userInfList 設定する userInfList。
     */
    public void setUserInfList(ArrayList<CmnUsrmInfModel> userInfList) {
        userInfList__ = userInfList;
    }
    /**
     * @return mode を戻します。
     */
    public int getMode() {
        return mode__;
    }
    /**
     * @param mode 設定する mode。
     */
    public void setMode(int mode) {
        mode__ = mode;
    }
    /**
     * @return mode を戻します。
     */
    public int getTmode() {
        return torikomiMode__;
    }
    /**
     * @param torikomiMode 設定する mode。
     */
    public void setTmode(int torikomiMode) {
        torikomiMode__ = torikomiMode;
    }
    /**
     * @return cntCon を戻します。
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * @param cntCon 設定する cntCon。
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }
    /**
     * @return dfGroup を戻します。
     */
    public CmnGroupmModel getDfGroup() {
        return dfGroup__;
    }
    /**
     * @param dfGroup 設定する dfGroup。
     */
    public void setDfGroup(CmnGroupmModel dfGroup) {
        dfGroup__ = dfGroup;
    }
    /**
     * @return glist を戻します。
     */
    public List<CmnGroupmModel> getGlist() {
        return glist__;
    }
    /**
     * @param glist 設定する glist。
     */
    public void setGlist(List<CmnGroupmModel> glist) {
        glist__ = glist;
    }
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
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
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
     * <p>posMap を取得します。
     * @return posMap
     */
    public HashMap<String, String> getPosMap() {
        return posMap__;
    }
    /**
     * <p>posMap をセットします。
     * @param posMap posMap
     */
    public void setPosMap(HashMap<String, String> posMap) {
        posMap__ = posMap;
    }
    /**
     * <p>groupIdMap を取得します。
     * @return groupIdMap
     */
    public HashMap<String, String> getGroupIdnmMap() {
        return groupIdnmMap__;
    }

    /**
     * <p>groupIdMap をセットします。
     * @param groupIdnmMap groupIdnmMap
     */
    public void setGroupIdnmMap(HashMap<String, String> groupIdnmMap) {
        groupIdnmMap__ = groupIdnmMap;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param cntCon 採番コントローラ
     * @param con コネクション
     * @param model UserCsvImportModel
     * @param torikomiMode 取り込みモード
     */
    public UserCsvImport(RequestModel reqMdl,
                          MlCountMtController cntCon,
                          Connection con,
                          UserCsvImportModel model,
                          int torikomiMode) {

        setReqMdl(reqMdl);
        setCntCon(cntCon);
        setGlist(model.getGlist());
        setDfGroup(model.getDfGroup());
        setSessionUser(model.getUserSid());
        setMode(model.getMode());
        setTmode(torikomiMode);
        setCon(con);
        setSysUd(new UDate());
        setUserInfList(new ArrayList<CmnUsrmInfModel>());
        setPosMap(new HashMap<String, String>());
        setGroupIdnmMap(new HashMap<String, String>());
        setUpdateFlg(model.getUpdateFlg());
        setUpdatePassFlg(model.getUpdatePassFlg());
        setInsertFlg(model.getInsertFlg());
        setPswdKbn(model.getPswdKbn());
        EnumSet<COLNO> eset = UserCsvCheck.getEnumSet(torikomiMode);
        colArr__ = eset.toArray(new COLNO[eset.size()]);

    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     * @param con コネクション
     * @param torikomiMode 取り込みモード
     */
    public UserCsvImport(RequestModel reqMdl,
                          int mode,
                          int torikomiMode,
                          Connection con) {

        setReqMdl(reqMdl);
        setMode(mode);
        setCon(con);
        setTmode(torikomiMode);
        setSysUd(new UDate());
        setUserInfList(new ArrayList<CmnUsrmInfModel>());
        setPosMap(new HashMap<String, String>());
        setGroupIdnmMap(new HashMap<String, String>());
        EnumSet<COLNO> eset = UserCsvCheck.getEnumSet(torikomiMode);
        colArr__ = eset.toArray(new COLNO[eset.size()]);

    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @param loginBiz ログイン処理実行Biz
     * @return userInfList__
     * @throws  Exception   実行時例外
     */
    public ArrayList<CmnUsrmInfModel> importCsv(String tmpFileDir, ILogin loginBiz)
        throws Exception {

        setLoginBiz(loginBiz);

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
        return userInfList__;
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
    private void __import(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        CmnGroupmModel grpmModel = new CmnGroupmModel();
        CmnUsrmModel umodel = new CmnUsrmModel();
        CmnUsrmDao udao = new CmnUsrmDao(con__);
        CmnUsrmInfModel uifmodel = new CmnUsrmInfModel();
        CmnUsrmInfDao uifdao = new CmnUsrmInfDao(con__);
        CmnGroupmDao grpDao = new CmnGroupmDao(con__);
        boolean existorikomiFlg = false;

        //取り込みモード(0:通常モード 1:グループ一括 2:複数グループ)
        int torikomiFlg = -1;
        if (torikomiMode__ == GSConstUser.MODE_NORMAL) {
            //通常モード
            torikomiFlg = 0;
        } else if (torikomiMode__ == GSConstUser.MODE_GROUP_ALL) {
            //グループ一括モード
            torikomiFlg = 1;
            //複数グループ登録、自動インポート
        } else if (torikomiMode__ == GSConstUser.MODE_MULTIPLE_GROUP) {
            torikomiFlg = 2;
        } else if (torikomiMode__ == GSConstUser.MODE_AUTO_IMPORT) {
            torikomiFlg = 3;
        }

        COLNO[] colArr = colArr__;
        //複数グループを保持するためのリスト
        ArrayList<CmnBelongmModel> cmnBeMdlList = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {

            COLNO col = colArr[j];
            buff = stringTokenizer.nextToken();

            switch (col) {
            case IMPORTKBN:
                int impKbn = NullDefault.getInt(buff, -1);
                setAutoImpMode(impKbn);
                CommonBiz cmnBiz = new CommonBiz();
                setLoginBiz(cmnBiz.getLoginInstance());
                if (autoImpMode__ == GSConstUser.AUTO_IMP_INSERT) {
                    setUpdateFlg(GSConstUser.IMPORT_MODE_INSERT);
                } else if (autoImpMode__ == GSConstUser.AUTO_IMP_EDIT) {
                    setUpdateFlg(GSConstUser.IMPORT_MODE_UPDATE);
                }
                break;
            case USERID:
                if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE) {
                    existorikomiFlg = udao.existLoginidEdit(-1, buff);
                }
                break;
            default:
                break;
            }

            __setUsrInfModel(col, umodel, uifmodel, grpmModel, buff, grpDao, cmnBeMdlList);

            if (autoImpMode__ == GSConstUser.AUTO_IMP_DELETE) {
                if (umodel.getUsrLgid() != null) {
                    autoDeleteUser(umodel, udao);
                    return;
                }
            }
            j++;
        }

        int usid = 0;

        //ユーザマスタ
        umodel.setUsrJkbn(GSConstUser.USER_JTKBN_ACTIVE);
        umodel.setUsrAuid(sessionUser__);
        umodel.setUsrAdate(sysUd__);
        umodel.setUsrEuid(sessionUser__);
        umodel.setUsrEdate(sysUd__);
        umodel.setUsrPswdEdate(sysUd__);
        umodel.setUsrPswdKbn(pswdKbn__);

        //ユーザ情報
        uifmodel.setUsiEuid(sessionUser__);
        uifmodel.setUsiEdate(sysUd__);
        uifmodel.setUsiLtlgin(sysUd__);
        uifmodel.setUsiAuid(sessionUser__);
        uifmodel.setUsiAdate(sysUd__);

        //上書きモードorインポート区分編集のとき、ユーザが存在するなら上書きする
        if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE && existorikomiFlg) {
            CmnUsrmModel usrmModel = udao.getUserSid(umodel.getUsrLgid());
            if (usrmModel != null) {
                usid = usrmModel.getUsrSid();
                umodel.setUsrSid(usid);
                uifmodel.setUsrSid(usid);
                //ユーザ登録処理

                int usrPassUpdateFlg = getUpdatePassFlg();
                //パスワード未入力時はパスワードを上書きしない
                if (usrPassUpdateFlg == GSConstUser.PASS_CHANGE_OK
                        && umodel.getUsrPswd().length() == 0) {
                    usrPassUpdateFlg = GSConstUser.PASS_CHANGE_NG;
                }

                udao.updateUserData(umodel, usrPassUpdateFlg);
                //バイナリSID、写真公開フラグは更新対象から除外する
                uifdao.updateCmnUserInfNoBinSid(uifmodel);
            }

        //ユーザが存在しない場合は登録を行う
        } else {
            usid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_USER,
                    SaibanModel.SBNSID_SUB_USER,
                    sessionUser__);
            umodel.setUsrSid(usid);
            uifmodel.setUsrSid(usid);

            //ユーザ登録処理
            udao.insert(umodel);
            uifdao.insert(uifmodel);
        }


        //ユーザ所属
        CmnBelongmDao bdao = new CmnBelongmDao(con__);
        List<Integer> pastGsidList = new ArrayList<Integer>();

        if (torikomiFlg == 0) {
            //通常モード
            //デフォルトグループ
            int dgsid = dfGroup__.getGrpSid();
            log__.debug(">>>デフォルトグループSID: " + dgsid);
            //所属グループ
            int gsid = -1;

            if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE && existorikomiFlg) {
                //変更前の所属グループを取得する
                pastGsidList = bdao.selectUserBelongGroupSid(usid);
                //所属グループ情報を削除する。
                bdao.deleteUserBelongGroup(usid);
            }

            for (int i = 0; i < glist__.size(); i++) {
                gsid = glist__.get(i).getGrpSid();
                CmnBelongmModel bmodel = __createCmnBelongmModel(dgsid,
                                                                  gsid,
                                                                  usid,
                                                         sessionUser__,
                                                               sysUd__);
                bdao.insert(bmodel);
                
                __updateBelongHistory(bmodel, usid);
            }

        } else if (torikomiFlg == 1) {
            int gsid = 0;
            int dgsid = 0;
        //グループ一括モード
            //グループが存在する場合
            //groupsid取得
            CmnGroupmModel groupModel = grpDao.getGroupInf(grpmModel.getGrpId());

            if (groupModel != null) {
                gsid = groupModel.getGrpSid();
                dgsid = gsid;

                if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE && existorikomiFlg) {
                    //変更前の所属グループを取得する
                    pastGsidList = bdao.selectUserBelongGroupSid(usid);
                    //登録ユーザが管理者かどうか
                    CmnBelongmModel adminMdl = bdao.select(usid, GSConstUser.SID_ADMIN);
                    if (adminMdl != null) {
                        //管理者グループ以外の所属グループ情報を削除する。
                        bdao.delGrp(GSConstUser.SID_ADMIN, usid);
                    } else {
                        bdao.deleteUserBelongGroup(usid);
                    }
                }
                glist__ = new ArrayList<CmnGroupmModel>();
                glist__.add(groupModel);

                for (int i = 0; i < glist__.size(); i++) {
                    gsid = glist__.get(i).getGrpSid();
                    CmnBelongmModel bmodel = __createCmnBelongmModel(dgsid, gsid,
                            usid, sessionUser__, sysUd__);
                    bdao.insert(bmodel);
                    
                    __updateBelongHistory(bmodel, usid);
                }
            } else {
                CmnGroupmModel groupModel2 = new CmnGroupmModel();

                //グループが存在しない場合は作成してから登録
                gsid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_USER,
                        SaibanModel.SBNSID_SUB_GROUP,
                        sessionUser__);

                //グループ登録処理
                groupModel2.setGrpSid(gsid);
                dgsid = gsid;
                grpmModel.setGrpSid(gsid);
                grpmModel.setGrpAuid(sessionUser__);
                grpmModel.setGrpAdate(sysUd__);
                grpmModel.setGrpEuid(sessionUser__);
                grpmModel.setGrpEdate(sysUd__);
                grpDao.insert(grpmModel);

                //グループ階層情報を登録する。
                CmnGroupClassDao gclsDao = new CmnGroupClassDao(con__);
                CmnGroupClassModel classModel = new CmnGroupClassModel();
                classModel.addUnder(gsid);
                classModel.setGclAuid(sessionUser__);
                classModel.setGclAdate(sysUd__);
                classModel.setGclEuid(sessionUser__);
                classModel.setGclEdate(sysUd__);
                gclsDao.insert(classModel);

                glist__ = new ArrayList<CmnGroupmModel>();
                glist__.add(groupModel2);

                for (int i = 0; i < glist__.size(); i++) {
                    gsid = glist__.get(i).getGrpSid();
                    CmnBelongmModel bmodel = __createCmnBelongmModel(dgsid, gsid,
                            usid, sessionUser__, sysUd__);
                    bdao.insert(bmodel);
                    
                    __updateBelongHistory(bmodel, usid);
                }
            }
            //複数グループ同時登録
        } else if (torikomiFlg == 2) {

            if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE && existorikomiFlg) {
                //変更前の所属グループを取得する
                pastGsidList = bdao.selectUserBelongGroupSid(usid);
            }

            //登録ユーザが管理者かどうか
            CmnBelongmModel adminMdl = bdao.select(usid, GSConstUser.SID_ADMIN);
            if (adminMdl != null) {
                //管理者グループ以外の所属グループ情報を削除する。
                bdao.delGrp(GSConstUser.SID_ADMIN, usid);
            } else {
                bdao.deleteUserBelongGroup(usid);
            }
            //デフォルトグループの登録
            CmnGroupmModel groupModel = grpDao.getGroupInf(grpmModel.getGrpId());
            CmnBelongmDao cbDao = new CmnBelongmDao(con__);
            if (groupModel != null) {
                CmnBelongmModel defCbMdl = __createCmnBelongmModel(
                        groupModel.getGrpSid(), groupModel.getGrpSid(),
                        usid, reqMdl__.getSmodel().getUsrsid(), new UDate());
                cbDao.insert(defCbMdl);
                
                __updateBelongHistory(defCbMdl, usid);
            }

            //ほかの所属グループの登録
            for (CmnBelongmModel cbMdl:cmnBeMdlList) {
                cbMdl = __createCmnBelongmModel(groupModel.getGrpSid(), cbMdl.getGrpSid(),
                        usid, reqMdl__.getSmodel().getUsrsid(), new UDate());
                cbDao.insert(cbMdl);
                
                __updateBelongHistory(cbMdl, usid);
            }
            //自動インポート時
        } else if (torikomiFlg == 3) {

            if (autoImpMode__ == GSConstUser.AUTO_IMP_EDIT) {
                //変更前の所属グループを取得する
                pastGsidList = bdao.selectUserBelongGroupSid(usid);
            }

            if (grpmModel.getGrpId().length() != 0) {
                //登録ユーザが管理者かどうか
                CmnBelongmModel adminMdl = bdao.select(usid, GSConstUser.SID_ADMIN);
                if (adminMdl != null) {
                    //管理者グループ以外の所属グループ情報を削除する。
                    bdao.delGrp(GSConstUser.SID_ADMIN, usid);
                } else {
                    bdao.deleteUserBelongGroup(usid);
                }
                //デフォルトグループの登録
                CmnGroupmModel groupModel = grpDao.getGroupInf(grpmModel.getGrpId());
                CmnBelongmDao cbDao = new CmnBelongmDao(con__);
                if (groupModel != null) {
                    CmnBelongmModel defCbMdl = __createCmnBelongmModel(
                            groupModel.getGrpSid(), groupModel.getGrpSid(),
                            usid, reqMdl__.getSmodel().getUsrsid(), new UDate());
                    cbDao.insert(defCbMdl);
                    
                    __updateBelongHistory(defCbMdl, usid);
                }
                //ほかの所属グループの登録
                for (CmnBelongmModel cbMdl:cmnBeMdlList) {
                    cbMdl = __createCmnBelongmModel(groupModel.getGrpSid(), cbMdl.getGrpSid(),
                            usid, reqMdl__.getSmodel().getUsrsid(), new UDate());
                    cbDao.insert(cbMdl);
                    
                    __updateBelongHistory(cbMdl, usid);
                }
            }
        }

        //各プラグインリスナーを呼び出し(新規登録時)
        if (updateFlg__ != GSConstUser.IMPORT_MODE_UPDATE || !existorikomiFlg) {
            for (int i = 0; i < lis__.length; i++) {
                lis__[i].addUser(cntCon__, con__, usid, sessionUser__, reqMdl__);
            }
        } else {
            //更新前所属グループ
            int[] pastGsids = new int[pastGsidList.size()];
            for (int gidIndex = 0; gidIndex < pastGsidList.size(); gidIndex++) {
                pastGsids[gidIndex] = pastGsidList.get(gidIndex);
            }
            //更新後所属グループ
            List<Integer> belongGsidList = bdao.selectUserBelongGroupSid(usid);
            int[] belongGsids = new int[belongGsidList.size()];
            for (int gidIndex = 0; gidIndex < belongGsidList.size(); gidIndex++) {
                belongGsids[gidIndex] = belongGsidList.get(gidIndex);
            }

            for (int i = 0; i < lis__.length; i++) {
                lis__[i].changeBelong(con__, usid, pastGsids, belongGsids, sessionUser__);
            }
        }

        userInfList__.add(uifmodel);
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
//        boolean noInputorikomiFlg = false;

        //ユーザ情報
        CmnUsrmInfModel uifmodel = new CmnUsrmInfModel();
        CmnGroupmModel grpmModel = new CmnGroupmModel();
        CmnGroupmDao grpDao = new CmnGroupmDao(con__);
//        CmnUsrmInfDao uifdao = new CmnUsrmInfDao(con__);
        uifmodel.setUsiEuid(sessionUser__);
        uifmodel.setUsiEdate(sysUd__);
        uifmodel.setUsiLtlgin(sysUd__);
        uifmodel.setUsiAuid(sessionUser__);
        uifmodel.setUsiAdate(sysUd__);

        COLNO[] colArr = colArr__;
        //複数グループを保持するためのリスト
        ArrayList<CmnBelongmModel> cmnBeMdlList = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {
            COLNO col = colArr[j];
            buff = stringTokenizer.nextToken();
            __setUsrInfModel(col, null, uifmodel, grpmModel, buff, grpDao, cmnBeMdlList);
            j++;
        }
        userInfList__.add(uifmodel);
    }

    /**
     * <br>[機  能] ユーザ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param col 項目クラス
     * @param umodel CmnUsrmModel
     * @param uifmodel CmnUsrmInfModel
     * @param buff 読込文字列
     * @param grpmModel CmnGroupmModel
     * @param grpDao CmnGroupmDao
     * @param cmnBeMdlList CmnBelongmModel
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード変換時例外
     */
    private void __setUsrInfModel(COLNO col,
            CmnUsrmModel umodel,
            CmnUsrmInfModel uifmodel,
            CmnGroupmModel grpmModel,
            String buff,
            CmnGroupmDao grpDao,
            ArrayList<CmnBelongmModel> cmnBeMdlList
            ) throws SQLException, EncryptionException {


        switch (col) {
        //グループID
        case GROUPID:
            String groupNm = null;
            uifmodel.setUsigpSid(NullDefault.getString(buff, ""));
            grpmModel.setGrpId(NullDefault.getString(buff, ""));
            boolean ret = grpDao.existGroupEdit(0, buff);
            if (ret) {
                groupNm = grpDao.getGrpNm(buff);
                groupIdnmMap__.put(buff, groupNm);
            }
            groupId__ = buff;
            break;
         //グループID2～10
        case GROUPID2:
        case GROUPID3:
        case GROUPID4:
        case GROUPID5:
        case GROUPID6:
        case GROUPID7:
        case GROUPID8:
        case GROUPID9:
        case GROUPID10:
            String grpSid = NullDefault.getString(buff, "");
            CmnGroupmDao cgDao = new CmnGroupmDao(con__);
            if (!grpSid.equals("")) {
                CmnGroupmModel cgMdl = cgDao.getGroupInf(grpSid);
                CmnBelongmModel grpBeMdl = new CmnBelongmModel();
                if (cgMdl != null) {
                    grpBeMdl.setGrpSid(cgMdl.getGrpSid());
                    cmnBeMdlList.add(grpBeMdl);
                }
            }
            break;
            //グループ名
        case GROUPNAME:
            // グループIDが同じで名称が異なるデータがあった場合は、それより前のデータの名称に変更する
            if (groupIdnmMap__.containsKey(groupId__)) {
                groupNm__ = groupIdnmMap__.get(groupId__);
                if (!groupNm__.equals(buff)) {
                    buff = groupNm__;
                    uifmodel.setUsiHenkou(GSConstUser.GROUP_NAME_CHANGE);
                }
            }
            groupIdnmMap__.put(groupId__, buff);
            uifmodel.setUsigpNm(NullDefault.getString(buff, ""));
            grpmModel.setGrpName(NullDefault.getString(buff, ""));
            break;
        //ユーザID
        case USERID:
            if (umodel == null) {
                break;
            }
            //パスワード更新区分を再設定(パスワード項目より先に行うこと)
            CmnUsrmDao usrmDao = new CmnUsrmDao(con__);
            CmnUsrmModel cuMdl = usrmDao.select(buff);
            CommonBiz cmnBiz = new CommonBiz();
            ILogin logBiz = cmnBiz.getLoginInstance();
            int userSid = 0;
            if (cuMdl != null) {
                userSid = cuMdl.getUsrSid();
            }

            //ログイン処理によってパスワード変更が禁止されているか判定する(LDAPを想定)
            if (!logBiz.canChangePassword(con__, userSid)) {
                setUpdatePassFlg(GSConstUser.PASS_CHANGE_NG);
            }

            umodel.setUsrLgid(buff);
            break;
        //パスワード
        case PASSWD:
            if (umodel == null) {
                break;
            }
            if (buff.length() != 0) {
                String password = getLoginBiz().formatPassword(buff);
                umodel.setUsrPswd(GSPassword.getEncryPassword(password)); //暗号化
            } else {
                umodel.setUsrPswd("");
            }

            break;
        //ワンタイムパスワード通知先メール
        case OTP_SENDTO_MAIL:
            uifmodel.setUsiOtpsendAddress(NullDefault.getStringZeroLength(buff, ""));
            break;
        //社員・職員番号
        case CODE:
            uifmodel.setUsiSyainNo(NullDefault.getStringZeroLength(buff, ""));
            break;
        //姓
        case SEI:
            uifmodel.setUsiSei(NullDefault.getString(buff, ""));
            break;
        //名
        case MEI:
            uifmodel.setUsiMei(NullDefault.getString(buff, ""));
            break;
        //姓カナ
        case SEIKN:
            if (buff.length() > 0) {
                buff =
                        StringUtilKana.katakanaHalf2Full(NullDefault.getString(buff, ""));
                        uifmodel.setUsiSeiKn(NullDefault.getString(buff, ""));
                        uifmodel.setUsiSini(StringUtilKana.getInitKanaChar(buff));
            } else {
                uifmodel.setUsiSeiKn("");
                uifmodel.setUsiSini("");
            }
            break;
        //名カナ
        case MEIKN:
            if (buff.length() > 0) {
                buff =
                        StringUtilKana.katakanaHalf2Full(NullDefault.getString(buff, ""));
                        uifmodel.setUsiMeiKn(NullDefault.getString(buff, ""));
            } else {
                uifmodel.setUsiMeiKn("");
            }
            break;
        //ログイン停止
        case LOGINMUKO:
            uifmodel.setUsrUkoFlg(NullDefault.getInt(buff, 0));
            if (umodel != null) {
                umodel.setUsrUkoFlg(NullDefault.getInt(buff, 0));
            }
            break;
        //所属
        case SYOZOKU:
            uifmodel.setUsiSyozoku(NullDefault.getStringZeroLength(buff, ""));
            break;
        //役職
        case YAKUSYOKU:

            //自動連携時のみ役職名から役職Sidを取得しセットする
            if (autoImpMode__ == GSConstUser.AUTO_IMP_INSERT
                    || autoImpMode__ == GSConstUser.AUTO_IMP_EDIT) {
                CmnPositionModel cpMdl = __posDataSetting(buff);
                if (cpMdl != null) {
                    uifmodel.setPosSid(cpMdl.getPosSid());
                }
            } else {
                uifmodel.setUsiYakusyoku(NullDefault.getStringZeroLength(buff, ""));
            }

            break;
        //ソートキー1
        case SORT1:
            uifmodel.setUsiSortkey1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //ソートキー2
        case SORT2:
            uifmodel.setUsiSortkey2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //性別
        case SEX:
            uifmodel.setUsiSeibetu(NullDefault.getInt(buff, 0));
            break;
        //入社年月日
        case NYUSYADAY:
            if (buff != null && buff.length() == 8) {
                UDate entranceDate = new UDate();
                entranceDate.setDate(buff);
                entranceDate.setHour(0);
                entranceDate.setMinute(0);
                entranceDate.setSecond(0);
                entranceDate.setMilliSecond(0);
                uifmodel.setUsiEntranceDate(entranceDate);
            }
            break;
        //生年月日
        case BARTHDAY:
            if (buff != null && buff.length() == 8) {
                UDate bDate = new UDate();
                bDate.setDate(buff);
                bDate.setHour(0);
                bDate.setMinute(0);
                bDate.setSecond(0);
                bDate.setMilliSecond(0);
                uifmodel.setUsiBdate(bDate);
            }
            break;
        //生年月日公開フラグ
        case BARTHDAY_OPN:
            uifmodel.setUsiBdateKf(NullDefault.getInt(buff, 0));
            break;
        //メールアドレス１
        case MAIL1:
            uifmodel.setUsiMail1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //メールアドレスコメント１
        case MAIL1COME:
            uifmodel.setUsiMailCmt1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //メールアドレス１公開フラグ
        case MAIL1OPN:
            uifmodel.setUsiMail1Kf(NullDefault.getInt(buff, 0));
            break;
        //メールアドレス２
        case MAIL2:
            uifmodel.setUsiMail2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //メールアドレスコメント２
        case MAIL2COME:
            uifmodel.setUsiMailCmt2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //メールアドレス２公開フラグ
        case MAIL2OPN:
            uifmodel.setUsiMail2Kf(NullDefault.getInt(buff, 0));
            break;
        //メールアドレス３
        case MAIL3:
            uifmodel.setUsiMail3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //メールアドレスコメント３
        case MAIL3COME:
            uifmodel.setUsiMailCmt3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //メールアドレス３公開フラグ
        case MAIL3OPN:
            uifmodel.setUsiMail3Kf(NullDefault.getInt(buff, 0));
            break;
        //郵便番号
        case YBNCODE:
            if (buff != null && buff.length() == 8) {
                uifmodel.setUsiZip1(buff.substring(0, 3));
                uifmodel.setUsiZip2(buff.substring(4, 8));
            }
            break;
        //郵便番号公開フラグ
        case YBNCODEOPN:
            uifmodel.setUsiZipKf(NullDefault.getInt(buff, 0));
            break;
        //都道府県コード
        case TDFKCODE:
            uifmodel.setTdfSid(NullDefault.getInt(buff,  0));
            break;
        //都道府県公開フラグ
        case TDFKCODEOPN:
            uifmodel.setUsiTdfKf(NullDefault.getInt(buff, 0));
            break;
        //住所１
        case JUSYO1:
            uifmodel.setUsiAddr1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //住所１公開フラグ
        case JUSYO1OPN:
            uifmodel.setUsiAddr1Kf(NullDefault.getInt(buff, 0));
            break;
        //住所２
        case JUSYO2:
            uifmodel.setUsiAddr2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //住所２公開フラグ
        case JUSYO2OPN:
            uifmodel.setUsiAddr2Kf(NullDefault.getInt(buff, 0));
            break;
        //電話番号１
        case TEL1:
            uifmodel.setUsiTel1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号内線１
        case TEL1NAISEN:
            uifmodel.setUsiTelNai1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号コメント１
        case TEL1COME:
            uifmodel.setUsiTelCmt1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号１公開フラグ
        case TEL1OPN:
            uifmodel.setUsiTel1Kf(NullDefault.getInt(buff, 0));
            break;
        //電話番号２
        case TEL2:
            uifmodel.setUsiTel2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号内線２
        case TEL2NAISEN:
            uifmodel.setUsiTelNai2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号コメント２
        case TEL2COME:
            uifmodel.setUsiTelCmt2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号２公開フラグ
        case TEL2OPN:
            uifmodel.setUsiTel2Kf(NullDefault.getInt(buff, 0));
            break;
        //電話番号３
        case TEL3:
            uifmodel.setUsiTel3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号内線３
        case TEL3NAISEN:
            uifmodel.setUsiTelNai3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号コメント３
        case TEL3COME:
            uifmodel.setUsiTelCmt3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //電話番号３公開フラグ
        case TEL3OPN:
            uifmodel.setUsiTel3Kf(NullDefault.getInt(buff, 0));
            break;
        //FAX１
        case FAX1:
            uifmodel.setUsiFax1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //ＦＡＸコメント１
        case FAX1COME:
            uifmodel.setUsiFaxCmt1(NullDefault.getStringZeroLength(buff, ""));
            break;
        //FAX１公開フラグ
        case FAX1OPN:
            uifmodel.setUsiFax1Kf(NullDefault.getInt(buff, 0));
            break;
        //FAX２
        case FAX2:
            uifmodel.setUsiFax2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //ＦＡＸコメント２
        case FAX2COME:
            uifmodel.setUsiFaxCmt2(NullDefault.getStringZeroLength(buff, ""));
            break;
        //FAX２公開フラグ
        case FAX2OPN:
            uifmodel.setUsiFax2Kf(NullDefault.getInt(buff, 0));
            break;
        //FAX３
        case FAX3:
            uifmodel.setUsiFax3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //ＦＡＸコメント３
        case FAX3COME:
            uifmodel.setUsiFaxCmt3(NullDefault.getStringZeroLength(buff, ""));
            break;
        //FAX３公開フラグ
        case FAX3OPN:
            uifmodel.setUsiFax3Kf(NullDefault.getInt(buff, 0));
            break;
        //備考
        case BIKO:
            uifmodel.setUsiBiko(NullDefault.getStringZeroLength(buff, ""));
            break;
        default:
            break;
        }
    }

    /**
     *
     * <br>[機  能]csvファイルの役職名を元に役職の登録とSidの取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param postName 役職名
     * @return 役職情報
     * @throws SQLException SQL実行時例外
     */
    private CmnPositionModel __posDataSetting(String postName) throws SQLException {

        CmnPositionDao cpDao = new CmnPositionDao(con__);
        CmnPositionModel cpMdl = null;
        postName = NullDefault.getStringZeroLength(postName, "");

        try {
            PosBiz pBiz = new PosBiz();
            if (!pBiz.existPosName(con__, postName) && !postName.equals("")) {

                //役職が未登録の場合、登録を行う。
                int posSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_USER,
                                                           SaibanModel.SBNSID_SUB_POS,
                                                           -1);
                //役職コードを取得する
                MainCommonBiz biz = new MainCommonBiz();
                String posCode = biz.getAutoPosCode(con__, posSid);

                //役職登録
                cpMdl = __getUpdateModel(posSid, posCode, postName);
                cpDao.insertPos(cpMdl);

            } else {
                cpMdl = cpDao.getPosInfo(postName);
            }
        } catch (SQLException e) {
            throw e;
        }

        return cpMdl;
    }

    /**
     *
     * <br>[機  能]インポート区分が3(削除)のユーザを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param umodel ユーザモデル
     * @param udao CmnUsrmDao
     * @throws SQLException SQL実行時例外
     */
    private void autoDeleteUser(CmnUsrmModel umodel, CmnUsrmDao udao) throws SQLException {

        //ログインIDが正しいものかチェック
        boolean existUser = udao.existLoginidEdit(-1, umodel.getUsrLgid());
        if (!existUser) {
            return;
        }

        //ログインIDが正しければユーザモデルを取得
        umodel = udao.select(umodel.getUsrLgid());
        //既に削除済みユーザの場合は何もしない
        if (umodel.getUsrJkbn() == GSConst.JTKBN_DELETE || umodel == null) {
            return;
        }

        int usid = umodel.getUsrSid();
        //削除ユーザの設定
        UserBiz userBiz = new UserBiz();
        List <Integer> usrList = new ArrayList<>();
        usrList.add(usid);
        List<CmnUsrmInfModel> uinfList = userBiz.getUserList(con__, usrList);

        //削除実行
        Usr031knBiz delBiz = new Usr031knBiz(con__, reqMdl__);
        delBiz.delUser(uinfList, lis__, reqMdl__);

    }
    
    /**
    * <br>[機  能] 指定したユーザの所属履歴を更新する
    * <br>[解  説] 当日の所属履歴を全て削除した後、所属履歴の登録を行います
    * <br>[備  考]
    * @param bmodel ユーザ所属モデル
    * @param usid ユーザSID
    * @throws SQLException SQL実行時例外
    * @throws IllegalAccessException ユーザ所属モデルから所属履歴モデルへの値の設定に失敗
    * @throws InvocationTargetException ユーザ所属モデルから所属履歴モデルへの値の設定に失敗
    */
    private void __updateBelongHistory(CmnBelongmModel bmodel, int usid)
            throws SQLException, IllegalAccessException, InvocationTargetException {
        
        CmnBelongmHistoryDao cbhDao = new CmnBelongmHistoryDao(con__);
        CmnBelongmHistoryModel cbhMdl = new CmnBelongmHistoryModel();
        UDate now = sysUd__.cloneUDate();
        cbhDao.deleteUserBelongGroup(usid, now);
        BeanUtils.copyProperties(cbhMdl, bmodel);
        now.setZeroHhMmSs();
        cbhMdl.setBegDate(now);
        cbhDao.insert(cbhMdl);
    }

    /**
     * ユーザ所属マスタの項目の共通セットを行います
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
     * <br>[機  能] 役職の登録・更新用Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @param posCode 役職コード
     * @param posName 役職名称
     * @return CmnPositionModel 登録・更新用Model
     */
    private CmnPositionModel __getUpdateModel(int posSid, String posCode, String posName) {

        UDate now = new UDate();

        CmnPositionModel cpMdl = new CmnPositionModel();
        cpMdl.setPosSid(posSid);
        cpMdl.setPosCode(posCode);
        cpMdl.setPosName(posName);
        cpMdl.setPosBiko("");
        cpMdl.setPosSort(posSid);
        cpMdl.setPosAuid(ConvertConst.CONV_USER_SID);
        cpMdl.setPosAdate(now);
        cpMdl.setPosEuid(ConvertConst.CONV_USER_SID);
        cpMdl.setPosEdate(now);

        return cpMdl;
    }

    /**
     * <br>[機  能]ユーザリスナーを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ユーザリスナー
     */
    public IUserGroupListener[] getLis() {
        return lis__;
    }

    /**
     * <br>[機  能]ユーザリスナーをセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param lis ユーザリスナー
     */
    public void setLis(IUserGroupListener[] lis) {
        this.lis__ = lis;
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
     * <p>insertFlg を取得します。
     * @return insertFlg
     */
    public int getInsertFlg() {
        return insertFlg__;
    }
    /**
     * <p>insertFlg をセットします。
     * @param insertFlg insertFlg
     */
    public void setInsertFlg(int insertFlg) {
        insertFlg__ = insertFlg;
    }
    /**
     * <p>pswdKbn を取得します。
     * @return pswdKbn
     */
    public int getPswdKbn() {
        return pswdKbn__;
    }
    /**
     * <p>pswdKbn をセットします。
     * @param pswdKbn pswdKbn
     */
    public void setPswdKbn(int pswdKbn) {
        pswdKbn__ = pswdKbn;
    }
    /**
     * <p>loginBiz を取得します。
     * @return loginBiz
     */
    public ILogin getLoginBiz() {
        return loginBiz__;
    }
    /**
     * <p>loginBiz をセットします。
     * @param loginBiz loginBiz
     */
    public void setLoginBiz(ILogin loginBiz) {
        loginBiz__ = loginBiz;
    }
    /**
     * <p>updatePassFlg を取得します。
     * @return updatePassFlg
     */
    public int getUpdatePassFlg() {
        return updatePassFlg__;
    }
    /**
     * <p>updatePassFlg をセットします。
     * @param updatePassFlg updatePassFlg
     */
    public void setUpdatePassFlg(int updatePassFlg) {
        updatePassFlg__ = updatePassFlg;
    }
    /**
     * <p>autoImpMode を取得します。
     * @return autoImpMode
     */
    public int getAutoImpMode() {
        return autoImpMode__;
    }
    /**
     * <p>autoImpMode をセットします。
     * @param autoImpMode autoImpMode
     */
    public void setAutoImpMode(int autoImpMode) {
        autoImpMode__ = autoImpMode;
    }
}