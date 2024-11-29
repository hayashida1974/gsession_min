package jp.groupsession.v2.dba.dba001;

import java.util.ArrayList;

import jp.groupsession.v2.dba.AbstractDbaForm;
import jp.groupsession.v2.dba.dba001.model.Dba001IndexModel;
import jp.groupsession.v2.dba.dba001.model.Dba001Model;

/**
 * <br>[機  能] DBA001フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Dba001Form extends AbstractDbaForm {

    /** テーブル情報一覧 */
    private ArrayList<TableInfoModel> dba001TableInfos__ = new ArrayList<TableInfoModel>();
    /** View情報一覧 */
    private ArrayList<ViewInfoModel> dba001ViewInfos__ = new ArrayList<ViewInfoModel>();
    /** シーケンス情報一覧 */
    private ArrayList<Dba001Model> dba001SeqInfos__ = new ArrayList<Dba001Model>();
    /** インデックス情報一覧 */
    private ArrayList<Dba001IndexModel> dba001IndexInfos__ = new ArrayList<Dba001IndexModel>();

    /** SQL文 */
    private String dba001SqlString__ = null;
    /** 発行したSQLのタイプ select or update */
    private String dba001ExeType__ = null;
    /** updateの場合 更新件数 */
    private int resultCount__ = 0;
    /** 実行結果メッセージ */
    private String resultMessage__ = "";
    /** 実行結果 列名 */
    private ArrayList<String> dba001ResultHeaderName__ = null;
    /** 実行結果 データ */
    private ArrayList<ArrayList> dba001ResultData__ = null;
    /** Dao,Model生成対象のテーブル名 */
    private String dba001GenTableName__ = null;
    /** テーブル名クリック時のアクション */
    private int sqlstrType__ = 0;

    /** SQL実行時間 */
    private String sqlExeTime__ = null;

    /**
     * <p>dba001SqlString を取得します。
     * @return dba001SqlString
     */
    public String getDba001SqlString() {
        return dba001SqlString__;
    }

    /**
     * <p>dba001SqlString をセットします。
     * @param dba001SqlString dba001SqlString
     */
    public void setDba001SqlString(String dba001SqlString) {
        dba001SqlString__ = dba001SqlString;
    }

    /**
     * <p>dba001ExeType を取得します。
     * @return dba001ExeType
     */
    public String getDba001ExeType() {
        return dba001ExeType__;
    }

    /**
     * <p>dba001ExeType をセットします。
     * @param dba001ExeType dba001ExeType
     */
    public void setDba001ExeType(String dba001ExeType) {
        dba001ExeType__ = dba001ExeType;
    }

    /**
     * <p>resultCount を取得します。
     * @return resultCount
     */
    public int getResultCount() {
        return resultCount__;
    }

    /**
     * <p>resultCount をセットします。
     * @param resultCount resultCount
     */
    public void setResultCount(int resultCount) {
        resultCount__ = resultCount;
    }

    /**
     * <p>dba010TableInfos を取得します。
     * @return dba010TableInfos
     */
    public ArrayList<TableInfoModel> getDba001TableInfos() {
        return dba001TableInfos__;
    }

    /**
     * <p>dba010TableInfos をセットします。
     * @param dba001TableInfos dba010TableInfos
     */
    public void setDba001TableInfos(ArrayList<TableInfoModel> dba001TableInfos) {
        dba001TableInfos__ = dba001TableInfos;
    }

    /**
     * <p>resultMessage を取得します。
     * @return resultMessage
     */
    public String getResultMessage() {
        return resultMessage__;
    }

    /**
     * <p>resultMessage をセットします。
     * @param resultMessage resultMessage
     */
    public void setResultMessage(String resultMessage) {
        resultMessage__ = resultMessage;
    }

    /**
     * <p>dba001ResultHeaderName を取得します。
     * @return dba001ResultHeaderName
     */
    public ArrayList<String> getDba001ResultHeaderName() {
        return dba001ResultHeaderName__;
    }

    /**
     * <p>dba001ResultHeaderName をセットします。
     * @param dba001ResultHeaderName dba001ResultHeaderName
     */
    public void setDba001ResultHeaderName(ArrayList<String> dba001ResultHeaderName) {
        dba001ResultHeaderName__ = dba001ResultHeaderName;
    }

    /**
     * <p>dba001ResultData を取得します。
     * @return dba001ResultData
     */
    public ArrayList<ArrayList> getDba001ResultData() {
        return dba001ResultData__;
    }

    /**
     * <p>dba001ResultData をセットします。
     * @param dba001ResultData dba001ResultData
     */
    public void setDba001ResultData(ArrayList<ArrayList> dba001ResultData) {
        dba001ResultData__ = dba001ResultData;
    }

    /**
     * <p>dba001GenTableName を取得します。
     * @return dba001GenTableName
     */
    public String getDba001GenTableName() {
        return dba001GenTableName__;
    }

    /**
     * <p>dba001GenTableName をセットします。
     * @param dba001GenTableName dba001GenTableName
     */
    public void setDba001GenTableName(String dba001GenTableName) {
        dba001GenTableName__ = dba001GenTableName;
    }

    /**
     * <p>sqlstrType を取得します。
     * @return sqlstrType
     */
    public int getSqlstrType() {
        return sqlstrType__;
    }

    /**
     * <p>sqlstrType をセットします。
     * @param sqlstrType sqlstrType
     */
    public void setSqlstrType(int sqlstrType) {
        sqlstrType__ = sqlstrType;
    }

    /**
     * <p>dba001ViewInfos を取得します。
     * @return dba001ViewInfos
     */
    public ArrayList<ViewInfoModel> getDba001ViewInfos() {
        return dba001ViewInfos__;
    }

    /**
     * <p>dba001ViewInfos をセットします。
     * @param dba001ViewInfos dba001ViewInfos
     */
    public void setDba001ViewInfos(ArrayList<ViewInfoModel> dba001ViewInfos) {
        dba001ViewInfos__ = dba001ViewInfos;
    }

    /**
     * <p>dba001SeqInfos を取得します。
     * @return dba001SeqInfos
     */
    public ArrayList<Dba001Model> getDba001SeqInfos() {
        return dba001SeqInfos__;
    }

    /**
     * <p>dba001SeqInfos をセットします。
     * @param dba001SeqInfos dba001SeqInfos
     */
    public void setDba001SeqInfos(ArrayList<Dba001Model> dba001SeqInfos) {
        dba001SeqInfos__ = dba001SeqInfos;
    }

    /**
     * <br>[機  能] dba001IndexInfos を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return dba001IndexInfos
     */
    public ArrayList<Dba001IndexModel> getDba001IndexInfos() {
        return dba001IndexInfos__;
    }

    /**
     * <br>[機  能] dba001IndexInfos を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dba001IndexInfos 設定する dba001IndexInfos
     */
    public void setDba001IndexInfos(ArrayList<Dba001IndexModel> dba001IndexInfos) {
        dba001IndexInfos__ = dba001IndexInfos;
    }

    /**
     * <br>[機  能] sqlExeTime を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return sqlExeTime
     */
    public String getSqlExeTime() {
        return sqlExeTime__;
    }

    /**
     * <br>[機  能] sqlExeTime を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlExeTime 設定する sqlExeTime
     */
    public void setSqlExeTime(String sqlExeTime) {
        sqlExeTime__ = sqlExeTime;
    }


}
