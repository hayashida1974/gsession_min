package jp.groupsession.v2.sml.sml260;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.sml240.Sml240Form;

/**
 * <br>[機  能] ショートメール アカウントインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260Form extends Sml240Form {

    /** 初期表示フラグ */
    private int sml260initFlg__ = 0;
    /** プラグインID */
    private String sml260pluginId__ = GSConstSmail.PLUGIN_ID_SMAIL;

    /** 添付ファイル(コンボで選択中) */
    private String[] sml260selectFiles__ = null;
    /** ファイルコンボ */
    private  List<LabelValueBean> sml260FileLabelList__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param tempDir テンポラリディレクトリ
     * @return エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req, String tempDir)
    throws Exception {
        ActionErrors errors = new ActionErrors();

        String eprefix = "inputFile.";

        SmlCommonBiz sBiz = new SmlCommonBiz();
        List<Cmn110FileModel> fileDataList = sBiz.getFileData(tempDir);

        //ファイルが存在しない場合
        if (fileDataList == null || fileDataList.isEmpty()) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text",
                                getInterMessage(req, "cmn.capture.file"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {

            boolean csvError = false;
            String fileName = null;
            //複数選択エラー
            if (fileDataList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file",
                                    getInterMessage(req, "cmn.capture.file"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                fileName = fileDataList.get(0).getFileName();
                String strExt = StringUtil.getExtension(fileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage("error.select.required.text",
                                        getInterMessage(req, "cmn.csv.file.format"));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }
            String fullPath = tempDir + fileDataList.get(0).getSaveFileName();
            SmailCsvReader csvReader = new SmailCsvReader();
            if (!csvError) {
                if (csvReader.isOverRowCount(new File(fullPath),
                        Encoding.WINDOWS_31J,
                        AbstractCsvRecordReader.MAX_ROW_COUNT_X5)) {
                    ActionMessage msg =
                            new ActionMessage("error.over.row.csvdata",
                                    getInterMessage(req, "cmn.capture.file"),
                                    String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT_X5));
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.over.row.csvdata");

                    csvError = true;
                }
            }

            //入力チェック
            if (!csvError) {
                csvReader.readCsvFile(fullPath);
                List<SmailCsvModel> accountList = csvReader.getSmailList();

                int accountCount = 0;
                for (SmailCsvModel accountData : accountList) {
                    int errorCnt = errors.size();
                    String rowNum = String.valueOf(accountData.getRowNum());
                    String rowStr = getInterMessage(req, "cmn.line2", rowNum);
                    //アカウント名入力チェック
                    GSValidateSmail.validateTextBoxInput(errors, accountData.getAccountName(),
                            "acntName" + rowNum,
                            rowStr + getInterMessage(req, GSConstSmail.TEXT_ACCOUNT),
                            GSConstSmail.MAXLEN_ACCOUNT, true);

                    int errSize = errors.size();

                    //備考
                    GSValidateSmail.validateTextarea(errors,
                            accountData.getBiko(),
                            "acntBiko" + rowNum,
                            rowStr + getInterMessage(req, GSConstSmail.TEXT_BIKO),
                            GSConstSmail.MAXLEN_ACCOUNT_BIKO,
                            false);

                    //送信メール形式
                    GSValidateSmail.validateCsvAccountFlg(req, errors,
                                                            accountData.getSndMailType(),
                            "acntSndMailPatn" + rowNum,
                            rowStr + getInterMessage(req, GSConstSmail.TEXT_SEND_TYPE),
                            GSConstSmail.SAC_SEND_MAILTYPE_NORMAL,
                            GSConstSmail.SAC_SEND_MAILTYPE_HTML);


                    //使用ユーザ
                    boolean user1 = StringUtil.isNullZeroString(accountData.getUser1());
                    boolean user2 = StringUtil.isNullZeroString(accountData.getUser2());
                    boolean user3 = StringUtil.isNullZeroString(accountData.getUser3());
                    boolean user4 = StringUtil.isNullZeroString(accountData.getUser4());
                    boolean user5 = StringUtil.isNullZeroString(accountData.getUser5());
                    //使用グループ
                    boolean group1 = StringUtil.isNullZeroString(accountData.getGroup1());
                    boolean group2 = StringUtil.isNullZeroString(accountData.getGroup2());
                    boolean group3 = StringUtil.isNullZeroString(accountData.getGroup3());
                    boolean group4 = StringUtil.isNullZeroString(accountData.getGroup4());
                    boolean group5 = StringUtil.isNullZeroString(accountData.getGroup5());

                    String text_useUser = getInterMessage(req, GSConstSmail.TEXT_USE_USER);
                    String text_group = getInterMessage(req, GSConstSmail.TEXT_USE_GROUP);
                    String dot = getInterMessage(req, "wml.231");
                    if (user1 && user2 && user3 && user4 && user5
                            && group1 && group2 && group3 && group4 && group5) {
                        //未入力チェック
                        String chkFlgName = "acntUseUser" + rowNum;
                        eprefix = chkFlgName + ".";
                        String targetJp = rowStr + text_useUser + dot + text_group;
                        ActionMessage msg = new ActionMessage("error.input.required.text",
                                targetJp);
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

                    } else {
                        errSize = errors.size();
                        //ユーザ1
                        GSValidateSmail.validateCsvUserId(errors, accountData.getUser1(),
                                                           "acntUser1" + rowNum,
                                                            rowStr + text_useUser,
                                                            1);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateCsvUserIdExist(errors, accountData.getUser1(),
                                   "acntExUser1" + rowNum,
                                    rowStr + text_useUser,
                                    con, 1);
                        }
                        errSize = errors.size();

                        //ユーザ2
                        GSValidateSmail.validateCsvUserId(errors, accountData.getUser2(),
                                                           "acntUser2" + rowNum,
                                                            rowStr + text_useUser,
                                                            2);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateCsvUserIdExist(errors, accountData.getUser2(),
                                    "acntExUser2" + rowNum,
                                     rowStr + text_useUser,
                                     con, 2);
                        }
                        errSize = errors.size();

                        //ユーザ3
                        GSValidateSmail.validateCsvUserId(errors, accountData.getUser3(),
                                                           "acntUser3" + rowNum,
                                                            rowStr + text_useUser,
                                                            3);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateCsvUserIdExist(errors, accountData.getUser3(),
                                    "acntExUser3" + rowNum,
                                    rowStr + text_useUser,
                                    con, 3);
                        }
                        errSize = errors.size();

                        //ユーザ4
                        GSValidateSmail.validateCsvUserId(errors, accountData.getUser4(),
                                                           "acntUser4" + rowNum,
                                                            rowStr + text_useUser,
                                                            4);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateCsvUserIdExist(errors, accountData.getUser4(),
                                    "acntExUser4" + rowNum,
                                    rowStr + text_useUser,
                                    con, 4);
                        }
                        errSize = errors.size();

                        //ユーザ5
                        GSValidateSmail.validateCsvUserId(errors, accountData.getUser5(),
                                                           "acntUser5" + rowNum,
                                                            rowStr + text_useUser,
                                                            5);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateCsvUserIdExist(errors, accountData.getUser5(),
                                    "acntExUser5" + rowNum,
                                    rowStr + text_useUser,
                                    con, 5);
                        }
                        errSize = errors.size();

                        //グループ1
                        GSValidateSmail.validateGroupId(errors, accountData.getGroup1(),
                                "acntGroup1" + rowNum, rowStr + text_group, 1);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateGroupExist(
                                    errors, accountData.getGroup1(), "acntExGroup1" + rowNum,
                                    rowStr + text_group, 1, con);
                        }
                        errSize = errors.size();

                        //グループ2
                        GSValidateSmail.validateGroupId(errors, accountData.getGroup2(),
                                "acntGroup2" + rowNum, rowStr + text_group, 2);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateGroupExist(
                                    errors, accountData.getGroup2(), "acntExGroup2" + rowNum,
                                    rowStr + text_group, 2, con);
                        }
                        errSize = errors.size();

                        //グループ3
                        GSValidateSmail.validateGroupId(errors, accountData.getGroup3(),
                                "acntGroup3" + rowNum, rowStr + text_group, 3);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateGroupExist(
                                    errors, accountData.getGroup3(), "acntExGroup3" + rowNum,
                                    rowStr + text_group, 3, con);
                        }
                        errSize = errors.size();

                        //グループ4
                        GSValidateSmail.validateGroupId(errors, accountData.getGroup4(),
                                "acntGroup4" + rowNum, rowStr + text_group, 4);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateGroupExist(
                                    errors, accountData.getGroup4(), "acntExGroup4" + rowNum,
                                    rowStr + text_group, 4, con);
                        }
                        errSize = errors.size();

                        //グループ5
                        GSValidateSmail.validateGroupId(errors, accountData.getGroup5(),
                                "acntGroup5" + rowNum, rowStr + text_group, 5);
                        if (errSize == errors.size()) {
                            GSValidateSmail.validateGroupExist(
                                    errors, accountData.getGroup5(), "acntExGroup5" + rowNum,
                                    rowStr + text_group, 5, con);
                        }

                    }
                    if (errorCnt == errors.size()) {
                        accountCount++;
                    }
                }

                if (accountCount == 0) {
                    String msgText = getInterMessage(req, "cmn.capture.file");
                    ActionMessage msg =
                        new ActionMessage("search.notfound.data", msgText);
                    StrutsUtil.addMessage(errors, msg, "inputFile.search.notfound.data");
                }
            }
        }
        return errors;
    }

    /**
     * <p>sml260initFlg を取得します。
     * @return sml260initFlg
     */
    public int getSml260initFlg() {
        return sml260initFlg__;
    }

    /**
     * <p>sml260initFlg をセットします。
     * @param sml260initFlg sml260initFlg
     */
    public void setSml260initFlg(int sml260initFlg) {
        sml260initFlg__ = sml260initFlg;
    }

    /**
     * <p>sml260FileLabelList を取得します。
     * @return sml260FileLabelList
     */
    public List<LabelValueBean> getSml260FileLabelList() {
        return sml260FileLabelList__;
    }
    /**
     * <p>sml260FileLabelList をセットします。
     * @param sml260FileLabelList sml260FileLabelList
     */
    public void setSml260FileLabelList(List<LabelValueBean> sml260FileLabelList) {
        sml260FileLabelList__ = sml260FileLabelList;
    }
    /**
     * <p>sml260pluginId を取得します。
     * @return sml260pluginId
     */
    public String getSml260pluginId() {
        return sml260pluginId__;
    }
    /**
     * <p>sml260pluginId をセットします。
     * @param sml260pluginId sml260pluginId
     */
    public void setSml260pluginId(String sml260pluginId) {
        sml260pluginId__ = sml260pluginId;
    }
    /**
     * <p>sml260selectFiles を取得します。
     * @return sml260selectFiles
     */
    public String[] getSml260selectFiles() {
        return sml260selectFiles__;
    }
    /**
     * <p>sml260selectFiles をセットします。
     * @param sml260selectFiles sml260selectFiles
     */
    public void setSml260selectFiles(String[] sml260selectFiles) {
        sml260selectFiles__ = sml260selectFiles;
    }
}
