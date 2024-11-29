package jp.groupsession.v2.usr.usr013;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUserCsv;

/**
 * <br>[機  能] グループインポート 取込みファイル(CSV)のチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GroupCsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GroupCsvCheck.class);

    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** グループID重複チェック用MAP */
    private HashMap<String, String> groupIdMap__;
    /** 階層チェック用MAP */
    private HashMap<String, GroupModel> groupLevelMap__;
    /** グループIDリスト */
    List<String> inputGpIdList__;
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
     * <p>groupIdMap を取得します。
     * @return groupIdMap
     */
    public HashMap<String, String> getGroupIdMap() {
        return groupIdMap__;
    }

    /**
     * <p>groupIdMap をセットします。
     * @param groupIdMap groupIdMap
     */
    public void setGroupIdMap(HashMap<String, String> groupIdMap) {
        groupIdMap__ = groupIdMap;
    }

    /**
     * コンストラクタ
     * @param error アクションエラー
     * @param con コネクション
     * @param updateFlg 上書きフラグ
     * @param reqMdl RequestModel
     */
    public GroupCsvCheck(
            ActionErrors error,
            Connection con,
            int updateFlg,
            RequestModel reqMdl) {
        setErrors(error);
        setCon(con);
        setGroupIdMap(new HashMap<String, String>());
        setInputGpIdList(new ArrayList<String>());
        setUpdateFlg(updateFlg);
        setReqMdl(reqMdl);
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
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        File file = new File(csvFile);
        if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
            if (errors__ != null) {
                String eprefix = "inputFile.";
                ActionMessage msg =
                    new ActionMessage("error.over.row.csvdata",
                            textCaptureFile,
                            String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
                StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
            }
            ret = true;
            return ret;
        }


        //階層チェック用ハッシュ
        GroupBiz grpBiz = new GroupBiz();
        ArrayList<GroupModel> grpList = grpBiz.getGroupTree(con__);
        setGroupLevelMap(grpList);

        //ファイル読込み
        readFile(new File(csvFile), Encoding.WINDOWS_31J);
        log__.debug("有効データ件数==" + getCount());

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
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage("cmn.csv.number.items");
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});
        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        try {

            int j = 0;
            String buff;
            String eprefix = "inputFile.";
            int ecnt = errors__.size();
            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            log__.debug("項目数=" + stringTokenizer.length());
            //4項目である必要がある
            if (stringTokenizer.length() < 2 || stringTokenizer.length() > 5) {
                ActionMessage msg =
                        new ActionMessage(
                                "error.input.format.file",
                                textCaptureFile,
                                textCsvitems + "(" + textLine + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {

                String inputGpId = "";

                //グループID
                String textGroupId = gsMsg.getMessage("cmn.group.id");
                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();
                    GSValidateUserCsv gsValidateUserCsv = new GSValidateUserCsv(reqMdl__);

                    //グループID
                    if (j == 1) {

                        inputGpIdList__.add(buff);

                        gsValidateUserCsv.validateCsvGroupId(
                                errors__, buff, num, true, textGroupId, true);

                        inputGpId = buff;
                        if (getUpdateFlg() != GSConstUser.IMPORT_MODE_UPDATE) {
                            gsValidateUserCsv.validateCsvGroupIdDouble(errors__, buff, num, con__);
                        }

                        //CSVファイル内重複チェック
                        if (!StringUtil.isNullZeroString(buff)) {
                            if (groupIdMap__.containsKey(buff)) {
                                String dupIndex = (String) groupIdMap__.get(buff);
                                //行目の
                                String dupLine = gsMsg.getMessage("cmn.line2",
                                        new String[] {dupIndex});

                                ActionMessage msg = new ActionMessage(
                                        "error.select.dup.list2",
                                        textLine + textGroupId,
                                        dupLine + textGroupId);
                                StrutsUtil.addMessage(
                                        errors__,
                                        msg,
                                        "groupid." + num + "error.select.dup.list2");
                            } else {
                                groupIdMap__.put(buff, String.valueOf(num));
                            }
                        }
                    }

                    String inputGrpName = "";
                    //グループ名
                    if (j == 2) {
                        gsValidateUserCsv.validateCsvGroupName(errors__, buff, num);
                        inputGrpName = buff;
                    }
                    //グループ名カナ
                    if (j == 3) {
                        gsValidateUserCsv.validateCsvGroupNameKana(errors__, buff, num);
                    }
                    //親グループID
                    if (j == 4) {

                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            gsValidateUserCsv.validateCsvGroupIdFormat(errors__, buff, num);
                            gsValidateUserCsv.validateCsvParentGroupExist(
                                    errors__, buff, num, con__, inputGpIdList__);
                            if (inputGpId.trim().equals(buff.trim())) {
                                ActionMessage msg = new ActionMessage(
                                        "error.select.dup.list2",
                                        textLine + textGroupId,
                                        "親" + textGroupId);
                                StrutsUtil.addMessage(
                                        errors__,
                                        msg,
                                        "groupid." + num + "error.select.dup.list2");
                            }

                            if (groupLevelMap__.containsKey(buff)) {
                                //親グループが10階層目ではないかチェックする
                                GroupModel grpMdl = (GroupModel) groupLevelMap__.get(buff);

                                //指定した親グループの階層が10階層目以降だった場合エラー
                                if (grpMdl.getClassLevel() >= 10) {
                                    //エラー
                                    ActionMessage msg = new ActionMessage(
                                            "error.select.max.lebel",
                                            textLine + textGroupId);
                                    StrutsUtil.addMessage(
                                            errors__,
                                            msg,
                                            "groupid." + num + "error.select.dup.list2");
                                } else {
                                    //追加するグループ情報を階層チェック用MAPにセットする
                                    GroupModel putGrpMdl = new GroupModel();
                                    putGrpMdl.setGroupId(inputGpId);
                                    putGrpMdl.setGroupName(inputGrpName);
                                    putGrpMdl.setClassLevel(grpMdl.getClassLevel() + 1);

                                    groupLevelMap__.put(inputGpId, putGrpMdl);
                                }
                            }

                            gsValidateUserCsv.validateCsvExsitChild(
                                    errors__, inputGpId, buff, num, con__);

                        } else {
                            //追加するグループ情報を階層チェック用MAPにセットする
                            GroupModel putGrpMdl = new GroupModel();
                            putGrpMdl.setGroupId(inputGpId);
                            putGrpMdl.setGroupName(inputGrpName);
                            putGrpMdl.setClassLevel(1);

                            groupLevelMap__.put(inputGpId, putGrpMdl);
                        }

                    }
                    //コメント
                    if (j == 5) {
                        gsValidateUserCsv.validateCsvGroupComment(errors__, buff, num);
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
     * <p>inputGpIdList を取得します。
     * @return inputGpIdList
     */
    public List<String> getInputGpIdList() {
        return inputGpIdList__;
    }

    /**
     * <p>inputGpIdList をセットします。
     * @param inputGpIdList inputGpIdList
     */
    public void setInputGpIdList(List<String> inputGpIdList) {
        inputGpIdList__ = inputGpIdList;
    }

    /**
     * <p>groupLevelMap を取得します。
     * @return groupLevelMap
     */
    public HashMap<String, GroupModel> getGroupLevelMap() {
        return groupLevelMap__;
    }

    /**
     * <p>groupLevelMap をセットします。
     * @param groupLevelMap groupLevelMap
     */
    public void setGroupLevelMap(HashMap<String, GroupModel> groupLevelMap) {
        groupLevelMap__ = groupLevelMap;
    }

    /**
     * <p>groupLevelMap をセットします。
     * @param grpList グループリスト
     */
    public void setGroupLevelMap(ArrayList<GroupModel> grpList) {

        groupLevelMap__ = new HashMap<String, GroupModel>();

        for (GroupModel grpMdl : grpList) {
            groupLevelMap__.put(grpMdl.getGroupId(), grpMdl);
        }
    }

}