package jp.groupsession.v2.man.man330;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 所属情報一括設定(インポート)の取込みファイルをチェックする
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man330ImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man330ImportCheck.class);
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** フォーマットエラーフラグ */
    private boolean formatError__ = false;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** グループID重複チェック用MAP */
    private HashMap<String, Integer> grpIdMap__;
    /** ログインID重複チェック用MAP */
    private HashMap<String, String> loginIdMap__;
    /** 全グループIDリスト */
    private List<String> allGpIdList__ = null;
    /** 全ユーザIDリスト */
    private List<String> allUsrIdList__ = null;

    /**
     * <p>count__ を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }
    /**
     * <p>count__ をセットします。
     * @param count count__
     */
    public void setCount(int count) {
        count__ = count;
    }
    /**
     * <p>errorFlg__ を取得します。
     * @return errorFlg
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }
    /**
     * <p>errorFlg__ をセットします。
     * @param errorFlg errorFlg__
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }
    /**
     * <p>errors__ を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }
    /**
     * <p>errors__ をセットします。
     * @param errors errors__
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
    }
    /**
     * <p>formatError__ を取得します。
     * @return formatError
     */
    public boolean isFormatError() {
        return formatError__;
    }
    /**
     * <p>formatError__ をセットします。
     * @param formatError formatError__
     */
    public void setFormatError(boolean formatError) {
        formatError__ = formatError;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param gpIdList 全グループIDリスト
     * @param usrIdList 全ユーザIDリスト
     */
    public Man330ImportCheck(
            ActionErrors error,
            RequestModel reqMdl,
            Connection con,
            List<String> gpIdList,
            List<String> usrIdList) {
        setErrors(error);
        reqMdl__ = reqMdl;
        con__ = con;
        allGpIdList__ = new ArrayList<String>();
        allGpIdList__ = gpIdList;
        allUsrIdList__ = new ArrayList<String>();
        allUsrIdList__ = usrIdList;
        loginIdMap__ = new HashMap<String, String>();
    }

    /**
     * <br>[機　能] CSVファイルのチェックを行なう
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;
         File file = new File(csvFile);
         if (isOverRowCount(file,
                 Encoding.WINDOWS_31J,
                 AbstractCsvRecordReader.MAX_ROW_COUNT_X20)) {
             if (errors__ != null) {
                 GsMessage gsMsg = new GsMessage(reqMdl__);
                 String eprefix = "inputFile.";
                 String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
                 ActionMessage msg =
                     new ActionMessage("error.over.row.csvdata",
                             textCaptureFile,
                             String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT_X20));
                 StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             }
             ret = true;
             return ret;
         }

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数 " + getCount());

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             ret = true;
         }
         return ret;
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

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        if (num > 1) {

            try {

                int j = 0;
                int ecnt = errors__.size();
                grpIdMap__ = new HashMap<String, Integer>();

                log__.debug("項目数=" + stringTokenizer.length());

                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();

                    //ユーザID
                    if (j == 1) {
                        __isOkUsrId(errors__, buff, num);

                    } else {
                        //グループID
                        __isOkGrpId(errors__, buff, num, j);
                    }

                }

                //エラー有り
                if (ecnt < errors__.size()) {
                    //エラー存在フラグON
                    setErrorFlg(true);
                } else {
                    //明細データ以降
                    if (num > 1) {
                        //有効データ件数カウントアップ
                        int cnt = getCount();
                        cnt += 1;
                        setCount(cnt);
                    }
                }

            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }

        }
    }

    /**
     * <br>[機  能] ユーザIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param targetUsrId ユーザID
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkUsrId(ActionErrors errors, String targetUsrId, long num) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        String eprefix = String.valueOf(num) + "usrid";
        //ユーザID
        String usrId = gsMsg.getMessage("cmn.user.id");
        String errorMsg = textLine + usrId;
        String errorMsg2 = textLine;

        //未入力チェック
        boolean errChFlg = false;
        errChFlg = __checkNoInput(errors, targetUsrId, eprefix, errorMsg);
        if (errChFlg) {
            //桁数チェック
            errChFlg = __checkRange(
                    errors,
                    targetUsrId,
                    eprefix,
                    errorMsg,
                    GSConstUser.MAX_LENGTH_USERID);
        }

        if (errChFlg) {
            //先頭スペースチェック
            if (ValidateUtil.isSpaceStart(targetUsrId)) {
                msg = new ActionMessage("error.input.spase.start",
                        errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix);
                errChFlg = false;
            } else {
                errChFlg = __checkJisString(
                                errors,
                                targetUsrId,
                                eprefix,
                                errorMsg);
            }
        }

        if (errChFlg) {
            //ユーザID存在チェック
            __checkUsrIdExist(errors, targetUsrId, eprefix, errorMsg2);
        }

        //CSVファイル内重複チェック
        if (!StringUtil.isNullZeroString(targetUsrId)) {
            if (loginIdMap__.containsKey(targetUsrId)) {
                String dupIndex = (String) loginIdMap__.get(targetUsrId);
                String dupLine = gsMsg.getMessage("cmn.line2", new String[] {dupIndex});

                msg = new ActionMessage(
                    "error.select.dup.list2",
                    num + textLine + usrId,
                    dupLine + usrId);
                StrutsUtil.addMessage(
                    errors,
                    msg,
                    eprefix);
            } else {
                loginIdMap__.put(targetUsrId, String.valueOf(num));
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] グループIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param targetGrpId グループID
     * @param num 行数
     * @param lineNum 列数
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    private ActionErrors __isOkGrpId(ActionErrors errors,
                                      String targetGrpId,
                                      long num,
                                      int lineNum) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        String eprefix = String.valueOf(num) + "grpid";

        //列
        String eprefixRetu = String.valueOf(num) + "grpid" + String.valueOf(lineNum);

        //グループID
        String grpId = gsMsg.getMessage("cmn.group.id");
        String errorMsg = textLine + grpId + (lineNum - 1);

        //未入力チェック
        boolean errChFlg = false;
        //3列目移行は未入力チェックなし
        if (lineNum < 3) {
            errChFlg = __checkNoInput(errors, targetGrpId, eprefix, errorMsg);
        } else {
            errChFlg = true;
        }

        if (!StringUtil.isNullZeroString(targetGrpId)) {
            if (errChFlg) {
                //桁数チェック
                errChFlg = __checkRange(
                        errors,
                        targetGrpId,
                        eprefix,
                        errorMsg,
                        GSConstUser.MAX_LENGTH_GROUPID);
            }

            if (errChFlg) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(targetGrpId)) {
                    msg = new ActionMessage("error.input.spase.start",
                            errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    errChFlg = false;
                } else {
                    errChFlg = __checkJisString(
                                    errors,
                                    targetGrpId,
                                    eprefix,
                                    errorMsg);
                }
            }

            if (errChFlg) {

                //管理グループID値を取得
                CmnGroupmDao cuDao = new CmnGroupmDao(con__);
                CmnGroupmModel admData = cuDao.select(GSConstUser.SID_ADMIN);
                String admGrpId = admData.getGrpId();

                if (targetGrpId.equals(admGrpId)) {
                    //管理者グループのIDが存在する場合
                    msg = new ActionMessage("error.edit.no.usegroup",
                            textLine + grpId + (lineNum - 1));
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    errChFlg = false;
                }
            }

            if (errChFlg) {
                //グループID存在チェック
                __checkGrpIdExist(errors, targetGrpId, eprefix, errorMsg);
            }

            //CSVファイル内同じ行内でのグループID重複チェック
            if (grpIdMap__.containsKey(targetGrpId)) {
                int dupIndex = (Integer) grpIdMap__.get(targetGrpId);
                dupIndex -= 1;
                msg = new ActionMessage(
                    "error.select.dup.list2",
                    textLine + grpId + (lineNum - 1),
                    textLine + grpId + dupIndex);
                StrutsUtil.addMessage(
                    errors,
                    msg,
                    eprefixRetu);
            } else {
                grpIdMap__.put(targetGrpId, lineNum);
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 指定された項目の未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkNoInput(ActionErrors errors,
                                    String value,
                                    String element,
                                    String elementName) {
        boolean result = true;
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text", elementName);
            errors.add(element + "error.input.required.text", msg);
            result = false;
        } else {
            //スペースのみの入力かチェック
            if (ValidateUtil.isSpace(value)) {
                msg = new ActionMessage("error.input.spase.only", elementName);
                errors.add(element + "error.input.spase.only", msg);
                result = false;
            }

        }

        return result;
    }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目がJIS第2水準文字かチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkJisString(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;
        //JIS第2水準文字か
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] ユーザIDの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkUsrIdExist(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;

        //ユーザIDが存在するか
        if (!getAllUsrIdList().contains(value)) {
            msg = new ActionMessage("error.edit.no.userid", elementName);
            errors.add(element + "error.edit.no.userid", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] グループIDの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkGrpIdExist(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;

        //グループIDが存在するか
        if (!getAllGpIdList().contains(value)) {
            msg = new ActionMessage("error.edit.no.group3", elementName);
            errors.add(element + "error.edit.no.group3", msg);
            result = false;
        }
        return result;
    }

    /**
     * <p>allGpIdList を取得します。
     * @return allGpIdList
     */
    public List<String> getAllGpIdList() {
        return allGpIdList__;
    }
    /**
     * <p>allGpIdList をセットします。
     * @param allGpIdList allGpIdList
     */
    public void setAllGpIdList(List<String> allGpIdList) {
        allGpIdList__ = allGpIdList;
    }
    /**
     * <p>allUsrIdList を取得します。
     * @return allUsrIdList
     */
    public List<String> getAllUsrIdList() {
        return allUsrIdList__;
    }
    /**
     * <p>allUsrIdList をセットします。
     * @param allUsrIdList allUsrIdList
     */
    public void setAllUsrIdList(List<String> allUsrIdList) {
        allUsrIdList__ = allUsrIdList;
    }
    /**
     * <p>loginIdMap を取得します。
     * @return loginIdMap
     */
    public HashMap<String, String> getLoginIdMap() {
        return loginIdMap__;
    }
    /**
     * <p>loginIdMap をセットします。
     * @param loginIdMap loginIdMap
     */
    public void setLoginIdMap(HashMap<String, String> loginIdMap) {
        loginIdMap__ = loginIdMap;
    }
    /**
     * <p>grpIdMap を取得します。
     * @return grpIdMap
     */
    public HashMap<String, Integer> getGrpIdMap() {
        return grpIdMap__;
    }
    /**
     * <p>grpIdMap をセットします。
     * @param grpIdMap grpIdMap
     */
    public void setGrpIdMap(HashMap<String, Integer> grpIdMap) {
        grpIdMap__ = grpIdMap;
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
}