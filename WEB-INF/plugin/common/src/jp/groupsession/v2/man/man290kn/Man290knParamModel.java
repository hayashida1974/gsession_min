package jp.groupsession.v2.man.man290kn;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.man.man290.Man290ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] メイン インフォメーション登録確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290knParamModel extends Man290ParamModel {
    /** 公開設定 from */
    private String man290knKoukaiString__ = null;
    /** 公開開始 */
    private String man290knFrDate__ = null;
    /** 公開終了 */
    private String man290knToDate__ = null;
    /** メッセージ  */
    private String man290knMsg__ = null;
    /** 内容  */
    private String man290knValue__ = null;
    /** 公開対象者リスト */
    private ArrayList <UsrLabelValueBean> man290knKoukaiList__ = null;

    /** 添付ファイル名一覧 */
    private List < String > man290knfilesName__ = null;
    /** 添付ファイルID */
    private String man290knTmpFileId__ = null;

    /** 休日表示設定 from */
    private String man290knSyukuString__ = null;

    /** 登録日付リスト */
    private ArrayList<String> man290knDateList__ = null;

    /**
     * <p>man290knDateList を取得します。
     * @return man290knDateList
     */
    public ArrayList<String> getMan290knDateList() {
        return man290knDateList__;
    }
    /**
     * <p>man290knDateList をセットします。
     * @param man290knDateList man290knDateList
     */
    public void setMan290knDateList(ArrayList<String> man290knDateList) {
        man290knDateList__ = man290knDateList;
    }
    /**
     * @return the man290knfilesName
     */
    public List<String> getMan290knfilesName() {
        return man290knfilesName__;
    }
    /**
     * @param man290knfilesName the man290knfilesName to set
     */
    public void setMan290knfilesName(List<String> man290knfilesName) {
        man290knfilesName__ = man290knfilesName;
    }
    /**
     * @return the man290knTmpFileId
     */
    public String getMan290knTmpFileId() {
        return man290knTmpFileId__;
    }
    /**
     * @param man290knTmpFileId the man290knTmpFileId to set
     */
    public void setMan290knTmpFileId(String man290knTmpFileId) {
        man290knTmpFileId__ = man290knTmpFileId;
    }
    /**
     * @return the man290knKoukaiString
     */
    public String getMan290knKoukaiString() {
        return man290knKoukaiString__;
    }
    /**
     * @param man290knKoukaiString the man290knKoukaiString to set
     */
    public void setMan290knKoukaiString(String man290knKoukaiString) {
        man290knKoukaiString__ = man290knKoukaiString;
    }
    /**
     * @return the man290knFrDate
     */
    public String getMan290knFrDate() {
        return man290knFrDate__;
    }
    /**
     * @param man290knFrDate the man290knFrDate to set
     */
    public void setMan290knFrDate(String man290knFrDate) {
        man290knFrDate__ = man290knFrDate;
    }
    /**
     * @return the man290knToDate
     */
    public String getMan290knToDate() {
        return man290knToDate__;
    }
    /**
     * @param man290knToDate the man290knToDate to set
     */
    public void setMan290knToDate(String man290knToDate) {
        man290knToDate__ = man290knToDate;
    }
    /**
     * @return the man290knMsg
     */
    public String getMan290knMsg() {
        return man290knMsg__;
    }
    /**
     * @param man290knMsg the man290knMsg to set
     */
    public void setMan290knMsg(String man290knMsg) {
        man290knMsg__ = man290knMsg;
    }
    /**
     * @return the man290knValue
     */
    public String getMan290knValue() {
        return man290knValue__;
    }
    /**
     * @param man290knValue the man290knValue to set
     */
    public void setMan290knValue(String man290knValue) {
        man290knValue__ = man290knValue;
    }
    /**
     * @return the man290knKoukaiList
     */
    public ArrayList<UsrLabelValueBean> getMan290knKoukaiList() {
        return man290knKoukaiList__;
    }
    /**
     * @param man290knKoukaiList the man290knKoukaiList to set
     */
    public void setMan290knKoukaiList(ArrayList<UsrLabelValueBean> man290knKoukaiList) {
        man290knKoukaiList__ = man290knKoukaiList;
    }
    /**
     * <p>man290knSyukuString を取得します。
     * @return man290knSyukuString
     */
    public String getMan290knSyukuString() {
        return man290knSyukuString__;
    }
    /**
     * <p>man290knSyukuString をセットします。
     * @param man290knSyukuString man290knSyukuString
     */
    public void setMan290knSyukuString(String man290knSyukuString) {
        man290knSyukuString__ = man290knSyukuString;
    }
}