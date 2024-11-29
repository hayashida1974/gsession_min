package jp.groupsession.v2.man.man090;

import java.util.ArrayList;

import jp.co.sjts.util.struts.AbstractForm;

/**
 * <br>[機  能] メイン 管理者設定 アプリケーションログ一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man090Form extends AbstractForm {

    /** ログファイル名リスト */
    private ArrayList<DspAppLogModel> logList__;
    /** ダウンロードファイル名 */
    private String logName__;

    /**
     * <p>ログファイル名リストを取得します。
     * @return ログファイル名リストを戻します。
     */
    public ArrayList<DspAppLogModel> getLogList() {
        return logList__;
    }
    /**
     * <p>ログファイル名リストをセットします。
     * @param logList ログファイル名リストを設定。
     */
    public void setLogList(ArrayList<DspAppLogModel> logList) {
        logList__ = logList;
    }
    /**
     * <p>ダウンロードファイル名を取得します。
     * @return ダウンロードファイル名を戻します。
     */
    public String getLogName() {
        return logName__;
    }
    /**
     * <p>ダウンロードファイル名をセットします。
     * @param logName ダウンロードファイル名を設定。
     */
    public void setLogName(String logName) {
        logName__ = logName;
    }
}
