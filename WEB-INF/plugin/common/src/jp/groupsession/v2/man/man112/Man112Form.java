package jp.groupsession.v2.man.man112;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man112Form extends AbstractGsForm {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man112Form.class);

    //非表示項目
    /** プラグインID */
    private String man112pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man112selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man112FileLabelList__ = null;
    /** 既存の役職情報更新フラグ */
    private int man112updateFlg__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tempDir 添付DIR
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            String tempDir, Connection con) throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();
        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        String saveFileName = "";
        String baseFileName = "";
        String eprefix = "inputFile.";
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (fileList == null) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text",
                                gsMsg.getMessage("reserve.110"));
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


            boolean csvError = false;
            //複数選択エラー
            if (fileList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file",
                            gsMsg.getMessage("reserve.110"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage("error.select.required.text",
                                gsMsg.getMessage("cmn.csv.file.format"));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }
            if (!csvError) {
                String fullPath = tempDir + saveFileName;
                log__.debug("FULLPATH==" + fullPath);
                PositionCsvCheck csvCheck
                        = new PositionCsvCheck(errors, reqMdl, con, man112updateFlg__);
                //CSVチェック
                csvCheck.isCsvDataOk(reqMdl, fullPath);
            }
        }

        return errors;
    }

    /**
     * <p>man112FileLabelList を取得します。
     * @return man112FileLabelList
     */
    public ArrayList<LabelValueBean> getMan112FileLabelList() {
        return man112FileLabelList__;
    }

    /**
     * <p>man112FileLabelList をセットします。
     * @param man112FileLabelList man112FileLabelList
     */
    public void setMan112FileLabelList(ArrayList<LabelValueBean> man112FileLabelList) {
        man112FileLabelList__ = man112FileLabelList;
    }

    /**
     * <p>man112pluginId を取得します。
     * @return man112pluginId
     */
    public String getMan112pluginId() {
        return man112pluginId__;
    }

    /**
     * <p>man112pluginId をセットします。
     * @param man112pluginId man112pluginId
     */
    public void setMan112pluginId(String man112pluginId) {
        man112pluginId__ = man112pluginId;
    }

    /**
     * <p>man112selectFiles を取得します。
     * @return man112selectFiles
     */
    public String[] getMan112selectFiles() {
        return man112selectFiles__;
    }

    /**
     * <p>man112selectFiles をセットします。
     * @param man112selectFiles man112selectFiles
     */
    public void setMan112selectFiles(String[] man112selectFiles) {
        man112selectFiles__ = man112selectFiles;
    }

    /**
     * <p>man112updateFlg を取得します。
     * @return man112updateFlg
     */
    public int getMan112updateFlg() {
        return man112updateFlg__;
    }

    /**
     * <p>man112updateFlg をセットします。
     * @param man112updateFlg man112updateFlg
     */
    public void setMan112updateFlg(int man112updateFlg) {
        man112updateFlg__ = man112updateFlg;
    }

}