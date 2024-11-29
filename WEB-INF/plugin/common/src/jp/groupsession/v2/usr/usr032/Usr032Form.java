package jp.groupsession.v2.usr.usr032;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr030.Usr030Form;

/**
 * <br>[機  能] メイン 管理者設定 ユーザインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr032Form extends Usr030Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr032Form.class);

    //非表示項目
    /** 初期表示フラグ */
    private int usr032initFlg__ = 0;
    /** プラグインID */
    private String usr032pluginId__ = GSConstUser.PLUGIN_ID_USER;

    /** 添付ファイル(コンボで選択中) */
    private String[] usr032selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> usr032FileLabelList__ = null;

    /** 選択グループ(CSVが格納される) */
    private String selectgroup__ = null;

    /** 入力処理モード **/
    private String processMode__ = null;

    /** 画面モード 1:通常, 2:グループ一括 **/
    private int usr032cmdMode__ = GSConstUser.MODE_NORMAL;
    /** グループツリー */
    ArrayList<GroupModel> groupList__ = null;
    /** デフォルトグループ */
    private int usr031defgroup__ = -1;
    /** 既存のユーザ情報更新フラグ */
    private int usr032updateFlg__ = 0;
    /** 既存のユーザパスワード更新フラグ */
    private int usr032updatePassFlg__ = 0;
    /** グループ作成フラグ */
    private int usr032createFlg__ = 0;
    /** 初回ログイン時、パスワード変更区分 */
    private int usr032PswdKbn__ = GSConstUser.PSWD_UPDATE_OFF;

    /** パスワード変更の有効・無効 */
    public int changePassword__ = GSConst.CHANGEPASSWORD_PARMIT;

    /**
     * <p>usr032initFlg を取得します。
     * @return usr032initFlg
     */
    public int getUsr032initFlg() {
        return usr032initFlg__;
    }

    /**
     * <p>usr032initFlg をセットします。
     * @param usr032initFlg usr032initFlg
     */
    public void setUsr032initFlg(int usr032initFlg) {
        usr032initFlg__ = usr032initFlg;
    }

    /**
     * @return usr032FileLabelList を戻します。
     */
    public ArrayList<LabelValueBean> getUsr032FileLabelList() {
        return usr032FileLabelList__;
    }

    /**
     * @param usr032FileLabelList 設定する usr032FileLabelList。
     */
    public void setUsr032FileLabelList(ArrayList<LabelValueBean> usr032FileLabelList) {
        usr032FileLabelList__ = usr032FileLabelList;
    }

    /**
     * @return groupList を戻します。
     */
    public ArrayList<GroupModel> getGroupList() {
        return groupList__;
    }

    /**
     * @param groupList 設定する groupList。
     */
    public void setGroupList(ArrayList<GroupModel> groupList) {
        groupList__ = groupList;
    }

    /**
     * @return processMode を戻します。
     */
    public String getProcessMode() {
        return processMode__;
    }

    /**
     * @param processMode 設定する processMode。
     */
    public void setProcessMode(String processMode) {
        processMode__ = processMode;
    }

    /**
     * @return usr032cmdMode を戻します。
     */
    public int getUsr032cmdMode() {
        return usr032cmdMode__;
    }

    /**
     * @param usr032cmdMode 設定する processMode。
     */
    public void setUsr032cmdMode(int usr032cmdMode) {
        usr032cmdMode__ = usr032cmdMode;
    }

    /**
     * @return selectgroup を戻します。
     */
    public String getSelectgroup() {
        return selectgroup__;
    }

    /**
     * @param selectgroup 設定する selectgroup。
     */
    public void setSelectgroup(String selectgroup) {
        selectgroup__ = selectgroup;
    }


    /**
     * @return usr031defgroup を戻します。
     */
    public int getUsr031defgroup() {
        return usr031defgroup__;
    }

    /**
     * @param usr031defgroup 設定する usr031defgroup。
     */
    public void setUsr031defgroup(int usr031defgroup) {
        usr031defgroup__ = usr031defgroup;
    }

    /**
     * @return usr032pluginId を戻します。
     */
    public String getUsr032pluginId() {
        return usr032pluginId__;
    }

    /**
     * @param usr032pluginId 設定する usr032pluginId。
     */
    public void setUsr032pluginId(String usr032pluginId) {
        usr032pluginId__ = usr032pluginId;
    }

    /**
     * @return usr032selectFiles を戻します。
     */
    public String[] getUsr032selectFiles() {
        return usr032selectFiles__;
    }

    /**
     * @param usr032selectFiles 設定する usr032selectFiles。
     */
    public void setUsr032selectFiles(String[] usr032selectFiles) {
        usr032selectFiles__ = usr032selectFiles;
    }

    /**
     * <p>usr032updateFlg を取得します。
     * @return usr032updateFlg
     */
    public int getUsr032updateFlg() {
        return usr032updateFlg__;
    }

    /**
     * <p>usr032updateFlg をセットします。
     * @param usr032updateFlg usr032updateFlg
     */
    public void setUsr032updateFlg(int usr032updateFlg) {
        usr032updateFlg__ = usr032updateFlg;
    }

    /**
     * <p>usr032createFlg を取得します。
     * @return usr032createFlg
     */
    public int getUsr032createFlg() {
        return usr032createFlg__;
    }

    /**
     * <p>usr032createFlg をセットします。
     * @param usr032createFlg usr032createFlg
     */
    public void setUsr032createFlg(int usr032createFlg) {
        usr032createFlg__ = usr032createFlg;
    }

    /**
     * <p>usr032PswdKbn を取得します。
     * @return usr032PswdKbn
     */
    public int getUsr032PswdKbn() {
        return usr032PswdKbn__;
    }

    /**
     * <p>usr032PswdKbn をセットします。
     * @param usr032PswdKbn usr032PswdKbn
     */
    public void setUsr032PswdKbn(int usr032PswdKbn) {
        usr032PswdKbn__ = usr032PswdKbn;
    }
    /**
     * <p>changePassword を取得します。
     * @return changePassword
     */
    public int getChangePassword() {
        return changePassword__;
    }

    /**
     * <p>changePassword をセットします。
     * @param changePassword changePassword
     */
    public void setChangePassword(int changePassword) {
        changePassword__ = changePassword;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @param canChangePassword パスワード変更の有効・無効
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(ActionMapping map, RequestModel reqMdl,
            String tempDir, Connection con, boolean canChangePassword)
                           throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();
        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text", textCaptureFile);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {

            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                saveFileName = fMdl.getSaveFileName();
                baseFileName = fMdl.getFileName();
            }

            //CSV形式のファイル
            String textCsvFile = gsMsg.getMessage("cmn.csv.file.format");
            boolean csvError = false;
            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file", textCaptureFile);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage("error.select.required.text", textCsvFile);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }
            if (!csvError) {
                String fullPath = tempDir + saveFileName;
                log__.debug("FULLPATH==" + fullPath);
                UserCsvCheck csvCheck = new UserCsvCheck(errors,
                                                         usr032cmdMode__,
                                                         con,
                                                         usr032updateFlg__,
                                                         usr032createFlg__,
                                                         reqMdl,
                                                         canChangePassword);
                //CSVチェック
                if (!csvCheck.isCsvDataOk(fullPath)) {
                    int dataCnt = csvCheck.getCount();

                    //ログイン有効ユーザが含まれる場合、ユーザ数制限チェックを行う
                    if (dataCnt > csvCheck.getLoginMukoCount()) {
                        if (csvCheck.getUpdateFlg() == GSConstUser.IMPORT_MODE_UPDATE) {
                            //既存のユーザ情報を上書きする場合、そのユーザをユーザ制限から除外
                            dataCnt -= csvCheck.getOverlabCount();
                            //ログイン無効から有効に変更するユーザ数を加算する
                            CmnUsrmDao usrDao = new CmnUsrmDao(con);
                            dataCnt += usrDao.getMukoUserCount(csvCheck.getLoginUkoUserList());
                            //ログイン有効から無効に変更するユーザを除外する
                            dataCnt -= usrDao.getYukoUserCount(csvCheck.getLoginMukoUserList());
                        }

                        //新規登録するログイン停止ユーザを除外する
                        dataCnt -= csvCheck.getNewLoginMukoCount();

                        UsrCommonBiz ucBiz = new UsrCommonBiz(con, reqMdl);
                        if (ucBiz.isUserCountOver(reqMdl, con, dataCnt)) {
                            //ユーザ数制限エラー
                            IGsResourceManager resourceManager = GroupSession.getResourceManager();
                            ActionMessage msg =
                            new ActionMessage("error.usercount.limit.over",
                                    resourceManager.getUserCountLimit(reqMdl.getDomain()));
                            StrutsUtil.addMessage(errors, msg,
                                                    eprefix + "error.usercount.limit.over");
                        }
                    }
                }

            }
        }

        if (getUsr032cmdMode() == GSConstUser.MODE_NORMAL) {
            GSValidateUser gsValUsr = new GSValidateUser(reqMdl);
            //所属グループ
            int errCnt = errors.size();
            gsValUsr.validateSelectGroup(errors, selectgroup__);
            if (errCnt == errors.size()) {
                //所属グループ 存在チェック
                __checkExistGroup(con, errors, selectgroup__,
                                "selectgroup", gsMsg.getMessage("cmn.affiliation.group"));
            }

            //デフォルトグループ
            errCnt = errors.size();
            gsValUsr.validateDefaultGroup(errors, usr031defgroup__);
            if (errCnt == errors.size()) {
                //デフォルトグループ 存在チェック
                __checkExistGroup(con, errors, String.valueOf(usr031defgroup__),
                                "usr031defgroup", gsMsg.getMessage("user.35"));
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 選択されたグループの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param errors ActionErrors
     * @param group チェックするグループ(SID)
     * @param paramName パラメータ名
     * @param paramNameMsg パラメータ名(表示)
     * @throws SQLException SQL実行時例外
     */
    private void __checkExistGroup(Connection con, ActionErrors errors,
                                    String group, String paramName, String paramNameMsg)
    throws SQLException {
        if (StringUtil.isNullZeroString(group)) {
            return;
        }

        String[] tmpgps = group.trim().split(",");
        for (String grid : tmpgps) {
            boolean grExist = GSValidateUser.existGroup(NullDefault.getInt(grid, -1), con);
            if (!grExist) {
                String eprefix = paramName + ".";
                String msgKey = "error.nothing.selected";
                ActionMessage msg = new ActionMessage(msgKey, paramNameMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + msgKey);
                break;
            }
        }

    }

    /**
     * <p>usr032updatePassFlg を取得します。
     * @return usr032updatePassFlg
     */
    public int getUsr032updatePassFlg() {
        return usr032updatePassFlg__;
    }

    /**
     * <p>usr032updatePassFlg をセットします。
     * @param usr032updatePassFlg usr032updatePassFlg
     */
    public void setUsr032updatePassFlg(int usr032updatePassFlg) {
        usr032updatePassFlg__ = usr032updatePassFlg;
    }
}