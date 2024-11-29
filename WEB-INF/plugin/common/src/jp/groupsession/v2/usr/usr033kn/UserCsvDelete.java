package jp.groupsession.v2.usr.usr033kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.usr033.UserDelCsvCheck;

/**
 * <br>[機  能] ユーザ一括削除 CSVファイル取り込み処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserCsvDelete extends AbstractCsvRecordReader {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 実行モード*/
    private int mode__;
    /** コネクション */
    private Connection con__ = null;
    /** ログイン処理実行Biz */
    private ILogin loginBiz__ = null;
    /** 取込みユーザ情報リスト*/
    private ArrayList<CmnUsrmInfModel> usrmInfMdlList__ = null;
    /** 削除ユーザIDリスト */
    private ArrayList<Integer> delUserList__ = null;
    /** ユーザリスナー */
    private IUserGroupListener[] lis__ = null;

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
     * <p>usrmInfMdlList を取得します。
     * @return usrmInfMdlList
     */
    public ArrayList<CmnUsrmInfModel> getUsrmInfMdlList() {
        return usrmInfMdlList__;
    }

    /**
     * <p>usrmInfMdlList をセットします。
     * @param usrmInfMdlList usrmInfMdlList
     */
    public void setUsrmInfMdlList(ArrayList<CmnUsrmInfModel> usrmInfMdlList) {
        usrmInfMdlList__ = usrmInfMdlList;
    }

    /**
     * <p>delUserList を取得します。
     * @return delUserList
     */
    public ArrayList<Integer> getDelUserList() {
        return delUserList__;
    }

    /**
     * <p>delUserList をセットします。
     * @param delUserList delUserList
     */
    public void setDelUserList(ArrayList<Integer> delUserList) {
        delUserList__ = delUserList;
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

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param mode 処理モード 0=処理実行 1=表示のみ
     * @param con コネクション
     */
    public UserCsvDelete(
            RequestModel reqMdl, int mode, Connection con) {
        setReqMdl(reqMdl);
        setMode(mode);
        setCon(con);
        setUsrmInfMdlList(new ArrayList<CmnUsrmInfModel>());
        setDelUserList(new ArrayList<Integer>());
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @param loginBiz ログイン処理実行Biz
     * @return ユーザ情報
     * @throws Exception 実行例外
     */
    public ArrayList<CmnUsrmInfModel> importCsv(
            String tmpFileDir, ILogin loginBiz)
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
        //削除実行
        if (mode__ == GSConstUser.CSV_IMPORT_RUN) {
            Usr033knBiz usr033knBiz = new Usr033knBiz(con__, reqMdl__);
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> uinfList = userBiz.getUserList(con__, delUserList__);
            usr033knBiz.executeDel(uinfList, lis__);
        }

        //登録・登録予定内容を設定
        return usrmInfMdlList__;
    }

    /*
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param num 行番号
     * @param buff データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    @Override
    protected void processedLine(long num, String buff) throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        CmnUsrmDao usrmDao = new CmnUsrmDao(con__);

        //ログインIDの入力チェック
        UserDelCsvCheck usrDelCsvCheck =
                new UserDelCsvCheck(new ActionErrors(), con__, reqMdl__);
        if (usrDelCsvCheck.validateBuff(num, buff)) {
            return;
        }

        //ユーザSIDを取得
        CmnUsrmModel usrmMdl = usrmDao.select(buff);
        int userSid = usrmMdl.getUsrSid();

        //処理モード
        if (mode__ == GSConstUser.CSV_IMPORT_RUN) {

            //削除実行モード　ユーザIDを削除ユーザIDリストに追加
            delUserList__.add(userSid);

        } else {

            //表示モード　ユーザ情報を取込みユーザ情報リストに取得
            CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con__);
            CmnUsrmInfModel uifmodel = usrmInfDao.select(userSid);
            usrmInfMdlList__.add(uifmodel);
        }
    }
}
