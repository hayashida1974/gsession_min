package jp.groupsession.v2.man.man112;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.GSValidatePositionCsv;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 役職CSVファイルのチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PositionCsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PositionCsvCheck.class);

    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** 役職コード重複チェック用MAP */
    private HashMap<String, String> posCodeMap__;
    /** 役職名重複チェック用MAP */
    private HashMap<String, String> posNameMap__;
    /** 既存のユーザ情報更新フラグ */
    private int updateFlg__ = 0;

    /**
     * @return errors を戻します。
     */
    public ActionErrors getErrors() {
        return errors__;
    }

    /**
     * @param errors 設定する errors。
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
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
     * @return con を戻します。
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * @param con 設定する con。
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * @return errorFlg を戻します。
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }

    /**
     * @param errorFlg 設定する errorFlg。
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }

    /**
     * @return count を戻します。
     */
    public int getCount() {
        return count__;
    }

    /**
     * @param count 設定する count。
     */
    public void setCount(int count) {
        count__ = count;
    }

    /**
     * <br>[機 能] コンストラクタ
     * <br>[解 説]
     * <br>[備 考]
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param updateFlg 上書きフラグ
     */
    public PositionCsvCheck(ActionErrors error, RequestModel reqMdl,
            Connection con, int updateFlg) {
        setErrors(error);
        setReqMdl(reqMdl);
        setCon(con);
        setPosCodeMap(new HashMap<String, String>());
        setPosNameMap(new HashMap<String, String>());
        setUpdateFlg(updateFlg);
    }

    /**
     * <br>[機 能] CSVファイルのチェックを行なう
     * <br>[解 説]
     * <br>[備 考]
     * @param reqMdl リクエスト情報
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(RequestModel reqMdl, String csvFile) throws Exception {

         boolean ret = false;
         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
             GsMessage gsMsg = new GsMessage(reqMdl);
             String eprefix = "inputFile.";
             String textCaptureFile = gsMsg.getMessage("reserve.110");
             ActionMessage msg =
                 new ActionMessage("error.over.row.csvdata",
                         textCaptureFile,
                         String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
             StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             ret = true;
             return ret;
         }

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数==" + getCount());

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             GsMessage gsMsg = new GsMessage(reqMdl);
             String eprefix = "inputFile.";
             String msgText = gsMsg.getMessage("reserve.110");

             ActionMessage msg =
                 new ActionMessage("search.notfound.data", msgText);
             StrutsUtil.addMessage(errors__, msg, eprefix + "search.notfound.data");
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

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        try {

            GsMessage gsMsg = new GsMessage(reqMdl__);
            int j = 0;
            String buff;
            String eprefix = "inputFile.";
            int ecnt = errors__.size();
            String posCode = null;
            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            log__.debug("項目数=" + stringTokenizer.length());
            //4項目である必要がある
            if (stringTokenizer.length() != 4) {
                ActionMessage msg =
                    new ActionMessage(
                            "error.input.format.file",
                            gsMsg.getMessage("reserve.110"),
                            gsMsg.getMessage("cmn.csv.number.items")
                            + "(" + gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)})
                            + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {

                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();

                    //役職コード
                    if (j == 1) {
                        GSValidatePositionCsv.validateCsvPosCode(errors__, reqMdl__, buff, num);

                        //重複チェック
                        if (getUpdateFlg() != GSConstUser.IMPORT_MODE_UPDATE) {
                            GSValidatePositionCsv.validateCsvPosCodeDouble(
                                    errors__, reqMdl__, buff, num, con__);
                        }

                        //CSVファイル内重複チェック
                        if (!StringUtil.isNullZeroString(buff)) {
                            if (posCodeMap__.containsKey(buff)) {
                                String dupIndex = (String) posCodeMap__.get(buff);

                                ActionMessage msg = new ActionMessage(
                                    "error.select.dup.list2",
                                    gsMsg.getMessage("cmn.line2",
                                            new String[] {String.valueOf(num)})
                                    + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE),
                                            gsMsg.getMessage("cmn.line2", new String[] {dupIndex})
                                    + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE));
                                StrutsUtil.addMessage(
                                    errors__,
                                    msg,
                                    "posCode." + num + "error.select.dup.list2");
                            } else {
                                posCodeMap__.put(buff, String.valueOf(num));
                                posCode = buff.toString();
                            }
                        }
                    }

                    //役職名
                    if (j == 2) {
                        GSValidatePositionCsv.validateCsvPosName(errors__, reqMdl__, buff, num);

                        if (getUpdateFlg() != GSConstUser.IMPORT_MODE_UPDATE) {
                            //重複チェック
                            GSValidatePositionCsv.validateCsvPosDouble(
                                    errors__, reqMdl__, buff, num, con__);
                        }

                        //CSVファイル内重複チェック
                        if (!StringUtil.isNullZeroString(buff)) {
                            if (posNameMap__.containsKey(buff)) {
                                String dupIndex = (String) posNameMap__.get(buff);

                                ActionMessage msg = new ActionMessage(
                                    "error.select.dup.list2",
                                    gsMsg.getMessage("cmn.line2",
                                            new String[] {String.valueOf(num)})
                                    + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME),
                                            gsMsg.getMessage("cmn.line2", new String[] {dupIndex})
                                    + gsMsg.getMessage(GSConstMain.TEXT_POS_NAME));
                                StrutsUtil.addMessage(
                                    errors__,
                                    msg,
                                    "posName." + num + "error.select.dup.list2");
                            } else {

                                //「既存の役職情報を上書きする」場合
                                //「役職コードが重複、かつ役職名が異なる」役職が存在するかをチェック
                                if (getUpdateFlg() == GSConstUser.IMPORT_MODE_UPDATE
                                && posCode != null) {
                                    CmnPositionDao positionDao = new CmnPositionDao(con__);
                                    String posName = positionDao.getPositionName(posCode);
                                    positionDao = null;
                                    if (posName != null && !posName.equals(buff)) {
                                        ActionMessage msg = new ActionMessage(
                                                "error.input.timecard.exist",
                                                gsMsg.getMessage("cmn.line2",
                                                            new String[] {String.valueOf(num)})
                                                + gsMsg.getMessage(GSConstMain.TEXT_POS_CODE));
                                        StrutsUtil.addMessage(errors__, msg,
                                                    "posCode." + num
                                                    + "error.input.timecard.exist.poscode");
                                    }
                                }

                                posNameMap__.put(buff, String.valueOf(num));
                            }
                        }
                    }
                    //表示順
                    if (j == 3) {
                        GSValidatePositionCsv.validatePosSort(errors__, reqMdl__, buff, num);
                    }

                    //備考
                    if (j == 4) {
                        GSValidatePositionCsv.validateBiko(
                                errors__, buff,
                                gsMsg.getMessage(GSConstMain.TEXT_POS_CMT), num, reqMdl__);
                    }
                }
            }

            //エラー有り
            if (ecnt < errors__.size()) {
                //エラー存在フラグON
                setErrorFlg(true);
            } else {
                //明細データ以降
                if (num >= 2) {
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
     * <p>posNameMap を取得します。
     * @return posNameMap
     */
    public HashMap<String, String> getPosNameMap() {
        return posNameMap__;
    }

    /**
     * <p>posNameMap をセットします。
     * @param posNameMap posNameMap
     */
    public void setPosNameMap(HashMap<String, String> posNameMap) {
        posNameMap__ = posNameMap;
    }

    /**
     * <p>posCodeMap を取得します。
     * @return posCodeMap
     */
    public HashMap<String, String> getPosCodeMap() {
        return posCodeMap__;
    }

    /**
     * <p>posCodeMap をセットします。
     * @param posCodeMap posCodeMap
     */
    public void setPosCodeMap(HashMap<String, String> posCodeMap) {
        posCodeMap__ = posCodeMap;
    }
}