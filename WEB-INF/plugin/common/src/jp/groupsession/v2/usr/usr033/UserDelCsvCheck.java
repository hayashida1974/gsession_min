package jp.groupsession.v2.usr.usr033;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUserCsv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] ユーザ一括削除 取込みファイル(CSV)のチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UserDelCsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UserDelCsvCheck.class);

    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** 存在しないユーザ数 */
    private int vacantCount__ = 0;
    /** ログインID重複チェック用リスト */
    private ArrayList<String> loginIdList__;

    /**
     * <p>errors を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }

    /**
     * <p>errors をセットします。
     * @param errors errors
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
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
     * <p>count を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }

    /**
     * <p>count をセットします。
     * @param count count
     */
    public void setCount(int count) {
        count__ = count;
    }

    /**
     * <p>errorFlg を取得します。
     * @return errorFlg
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }

    /**
     * <p>errorFlg をセットします。
     * @param errorFlg errorFlg
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }

    /**
     * <p>vacantCount を取得します。
     * @return vacantCount
     */
    public int getVacantCount() {
        return vacantCount__;
    }

    /**
     * <p>vacantCount をセットします。
     * @param vacantCount vacantCount
     */
    public void setVacantCount(int vacantCount) {
        vacantCount__ = vacantCount;
    }

    /**
     * <p>loginIdList を取得します。
     * @return loginIdList
     */
    public ArrayList<String> getLoginIdList() {
        return loginIdList__;
    }

    /**
     * <p>loginIdList をセットします。
     * @param loginIdList loginIdList
     */
    public void setLoginIdList(ArrayList<String> loginIdList) {
        loginIdList__ = loginIdList;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param error アクションエラー
     * @param con コネクション
     * @param reqMdl RequestModel
     */
     public UserDelCsvCheck(ActionErrors error,
                         Connection con,
                         RequestModel reqMdl
                         ) {
        setErrors(error);
        setCon(con);
        setLoginIdList(new ArrayList<String>());
        setReqMdl(reqMdl);
    }

    /**
     * <br>[機  能] CSVファイルのチェックを行なう
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile) throws Exception {
         boolean ret = false;
         GsMessage gsMsg = new GsMessage(reqMdl__);
         //取込みファイル
         String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT_X5)) {
             if (errors__ != null) {
                 String eprefix = "inputFile.";
                 ActionMessage msg =
                     new ActionMessage("error.over.row.csvdata",
                             textCaptureFile,
                             String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT_X5));
                 StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             }
             ret = true;
             return ret;
         }

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ数==" + getCount());

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             String eprefix = "inputFile.";
             ActionMessage msg =
                     new ActionMessage("search.notfound.data", textCaptureFile);
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
    * @param buff 読込み文字列
    * @throws Exception csv読込時例外
    * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
    */
    protected void processedLine(
                     long num,
                     String buff) throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        try {

            //ログインIDの入力チェックを行う
            validateBuff(num, buff);

        } catch (Exception e) {

            log__.error("CSVファイル読込み時例外");
            throw e;

        }
    }

    /**
     * <br>[機  能] ログインIDの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param num 行番号
     * @param buff 読込み文字列
     * @return true:エラーあり false:エラーなし
     * @throws SQLException SQL実行時例外
     */
    public boolean validateBuff(
            long num, String buff)
                    throws SQLException {
        //エラー判定
        boolean errFlg = false;
        //エラー数カウント
        int errCnt = errors__.size();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        //行目の
        String textLine2 = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});


        CmnUsrmDao usrmDao = new CmnUsrmDao(con__);
        GSValidateUserCsv gsValidateUserCsv = new GSValidateUserCsv(reqMdl__);

        //フォーマットチェック
        gsValidateUserCsv.validateCsvUserId(errors__, buff, num);

        if (!StringUtil.isNullZeroString(buff)) {

            //存在チェック
            CmnUsrmModel usrmMdl = usrmDao.select(buff);
            if (usrmMdl == null) {
                vacantCount__++;
                ActionMessage msg =
                        new ActionMessage("error.edit.no.userid", textLine2 + textUserId);
                StrutsUtil.addMessage(
                        errors__, msg, "userid." + num + "error.edit.no.userid");

            } else {
                //予約済みユーザSIDでないかチェック
                int userSid = usrmMdl.getUsrSid();
                if (userSid <= GSConstUser.USER_RESERV_SID) {
                    ActionMessage msg =
                            new ActionMessage(
                                    "error.common.no.delete", textLine2 + textUserId);
                    StrutsUtil.addMessage(
                            errors__, msg, "userid." + num + "error.common.no.delete");
                }
            }

            //CSVファイル内重複チェック
            if (loginIdList__.contains(buff)) {
                //重複行のうち、先に読み込んだ方の行番号
                String dupIndex = String.valueOf(loginIdList__.indexOf(buff) + 2);
                //行目の
                String dupLine = gsMsg.getMessage("cmn.line2", new String[] {dupIndex});

                ActionMessage msg = new ActionMessage(
                    "error.select.dup.list2",
                    textLine2 + textUserId,
                    dupLine + textUserId);
                StrutsUtil.addMessage(
                    errors__,
                    msg,
                    "userid." + num + "error.select.dup.list2");
            }

            //ログインID重複チェック用リストに追加
            loginIdList__.add(buff);

        }

        //エラー有り
        if (errCnt < errors__.size()) {
            //エラー存在フラグON
            setErrorFlg(true);
            errFlg = true;
        } else {
            //明細データ以降
            if (num >= 2) {
                //有効データ件数カウントアップ
                int cnt = getCount();
                cnt += 1;
                setCount(cnt);
            }
        }

        return errFlg;

    }
}