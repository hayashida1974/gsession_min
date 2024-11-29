package jp.groupsession.v2.wml.wml080;

import java.util.List;

import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.wml070.Wml070ParamModel;

/**
 * <br>[機  能] WEBメール メール詳細ポップアップのパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml080ParamModel extends Wml070ParamModel {
    /** メール種別(送受信ログ or 予約送信) */
    private int wml080mailType__ = Wml080Form.MAILTYPE_MAILLOG;
    /** 件名 */
    private String wml080Title__ = null;
    /** 送信元 */
    private String wml080From__ = null;
    /** 送信日 */
    private String wml080Sdate__ = null;
    /** 送信時間 */
    private String wml080Stime__ = null;
    /** 本文 */
    private String wml080Body__ = null;
    /** 本文フラグ */
    private String wml080BodyFlg__ = null;

    /** 添付ファイルSID */
    private long wml080downloadFileId__ = 0;
    /** 添付ファイル */
    private List<MailTempFileModel> tempFileList__ = null;

    /**
     * <p>wml080From を取得します。
     * @return wml080From
     */
    public String getWml080From() {
        return wml080From__;
    }
    /**
     * <p>wml080From をセットします。
     * @param wml080From wml080From
     */
    public void setWml080From(String wml080From) {
        wml080From__ = wml080From;
    }
    /**
     * <p>wml080mailType を取得します。
     * @return wml080mailType
     */
    public int getWml080mailType() {
        return wml080mailType__;
    }
    /**
     * <p>wml080mailType をセットします。
     * @param wml080mailType wml080mailType
     */
    public void setWml080mailType(int wml080mailType) {
        wml080mailType__ = wml080mailType;
    }
    /**
     * <p>wml080Title を取得します。
     * @return wml080Title
     */
    public String getWml080Title() {
        return wml080Title__;
    }
    /**
     * <p>wml080Title をセットします。
     * @param wml080Title wml080Title
     */
    public void setWml080Title(String wml080Title) {
        wml080Title__ = wml080Title;
    }
    /**
     * <p>wml080Sdate を取得します。
     * @return wml080Sdate
     */
    public String getWml080Sdate() {
        return wml080Sdate__;
    }
    /**
     * <p>wml080Sdate をセットします。
     * @param wml080Sdate wml080Sdate
     */
    public void setWml080Sdate(String wml080Sdate) {
        wml080Sdate__ = wml080Sdate;
    }
    /**
     * <p>wml080Stime を取得します。
     * @return wml080Stime
     */
    public String getWml080Stime() {
        return wml080Stime__;
    }
    /**
     * <p>wml080Stime をセットします。
     * @param wml080Stime wml080Stime
     */
    public void setWml080Stime(String wml080Stime) {
        wml080Stime__ = wml080Stime;
    }
    /**
     * <p>wml080Body を取得します。
     * @return wml080Body
     */
    public String getWml080Body() {
        return wml080Body__;
    }
    /**
     * <p>wml080Body をセットします。
     * @param wml080Body wml080Body
     */
    public void setWml080Body(String wml080Body) {
        wml080Body__ = wml080Body;
    }
    /**
     * <p>wml080BodyFlg を取得します。
     * @return wml080BodyFlg
     */
    public String getWml080BodyFlg() {
        return wml080BodyFlg__;
    }
    /**
     * <p>wml080BodyFlg をセットします。
     * @param wml080BodyFlg wml080BodyFlg
     */
    public void setWml080BodyFlg(String wml080BodyFlg) {
        wml080BodyFlg__ = wml080BodyFlg;
    }
    /**
     * <p>tempFileList を取得します。
     * @return tempFileList
     */
    public List<MailTempFileModel> getTempFileList() {
        return tempFileList__;
    }
    /**
     * <p>tempFileList をセットします。
     * @param tempFileList tempFileList
     */
    public void setTempFileList(List<MailTempFileModel> tempFileList) {
        tempFileList__ = tempFileList;
    }
    /**
     * <p>wml080downloadFileId を取得します。
     * @return wml080downloadFileId
     */
    public long getWml080downloadFileId() {
        return wml080downloadFileId__;
    }
    /**
     * <p>wml080downloadFileId をセットします。
     * @param wml080downloadFileId wml080downloadFileId
     */
    public void setWml080downloadFileId(long wml080downloadFileId) {
        wml080downloadFileId__ = wml080downloadFileId;
    }
}