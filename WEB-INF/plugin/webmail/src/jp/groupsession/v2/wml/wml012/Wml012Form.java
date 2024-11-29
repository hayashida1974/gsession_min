package jp.groupsession.v2.wml.wml012;

import java.util.List;

import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.wml010.Wml010Form;

/**
 * <br>[機  能] WEBメール 送信メール確認(ポップアップ)画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml012Form extends Wml010Form {

    /** 添付ファイル自動圧縮 圧縮する */
    public static final int COMPRESS_TEMPFILE = 1;

    /** 宛先一覧 */
    private List<Wml012AddressModel> wml012ToList__;
    /** CC一覧 */
    private List<Wml012AddressModel> wml012CcList__;
    /** BCC一覧 */
    private List<Wml012AddressModel> wml012BccList__;

    /** 送信予定日時 */
    private String wml012SendPlanDate__;
    /** 添付ファイル一覧 */
    private List<MailTempFileModel> wml012TempfileList__;
    /** 添付ファイル自動圧縮 */
    private int wml012compressTempfile__ = 0;

    /** 本文(表示用) */
    private String wml012ViewBody__ = null;

    /** テンポラリディレクトリID */
    private String wml012tempDirId__ = null;

    /**
     * <p>wml012compressTempfile を取得します。
     * @return wml012compressTempfile
     */
    public int getWml012compressTempfile() {
        return wml012compressTempfile__;
    }

    /**
     * <p>wml012compressTempfile をセットします。
     * @param wml012compressTempfile wml012compressTempfile
     */
    public void setWml012compressTempfile(int wml012compressTempfile) {
        wml012compressTempfile__ = wml012compressTempfile;
    }

    /**
     * <p>wml012ToList を取得します。
     * @return wml012ToList
     */
    public List<Wml012AddressModel> getWml012ToList() {
        return wml012ToList__;
    }

    /**
     * <p>wml012ToList をセットします。
     * @param wml012ToList wml012ToList
     */
    public void setWml012ToList(List<Wml012AddressModel> wml012ToList) {
        wml012ToList__ = wml012ToList;
    }

    /**
     * <p>wml012CcList を取得します。
     * @return wml012CcList
     */
    public List<Wml012AddressModel> getWml012CcList() {
        return wml012CcList__;
    }

    /**
     * <p>wml012CcList をセットします。
     * @param wml012CcList wml012CcList
     */
    public void setWml012CcList(List<Wml012AddressModel> wml012CcList) {
        wml012CcList__ = wml012CcList;
    }

    /**
     * <p>wml012BccList を取得します。
     * @return wml012BccList
     */
    public List<Wml012AddressModel> getWml012BccList() {
        return wml012BccList__;
    }

    /**
     * <p>wml012BccList をセットします。
     * @param wml012BccList wml012BccList
     */
    public void setWml012BccList(List<Wml012AddressModel> wml012BccList) {
        wml012BccList__ = wml012BccList;
    }

    /**
     * <p>wml012SendPlanDate を取得します。
     * @return wml012SendPlanDate
     */
    public String getWml012SendPlanDate() {
        return wml012SendPlanDate__;
    }

    /**
     * <p>wml012SendPlanDate をセットします。
     * @param wml012SendPlanDate wml012SendPlanDate
     */
    public void setWml012SendPlanDate(String wml012SendPlanDate) {
        wml012SendPlanDate__ = wml012SendPlanDate;
    }

    /**
     * <p>wml012TempfileList を取得します。
     * @return wml012TempfileList
     */
    public List<MailTempFileModel> getWml012TempfileList() {
        return wml012TempfileList__;
    }

    /**
     * <p>wml012TempfileList をセットします。
     * @param wml012TempfileList wml012TempfileList
     */
    public void setWml012TempfileList(List<MailTempFileModel> wml012TempfileList) {
        wml012TempfileList__ = wml012TempfileList;
    }

    /**
     * <p>wml012ViewBody を取得します。
     * @return wml012ViewBody
     */
    public String getWml012ViewBody() {
        return wml012ViewBody__;
    }

    /**
     * <p>wml012ViewBody をセットします。
     * @param wml012ViewBody wml012ViewBody
     */
    public void setWml012ViewBody(String wml012ViewBody) {
        wml012ViewBody__ = wml012ViewBody;
    }

    /**
     * <p>wml012ToList のサイズを取得します。
     * @return wml012ToListのサイズ
     */
    public int getWml012ToListSize() {
        if (wml012ToList__ == null) {
            return 0;
        }
        return wml012ToList__.size();
    }

    /**
     * <p>wml012CcList のサイズを取得します。
     * @return wml012CcListのサイズ
     */
    public int getWml012CcListSize() {
        if (wml012BccList__ == null) {
            return 0;
        }
        return wml012CcList__.size();
    }

    /**
     * <p>wml012BccList のサイズを取得します。
     * @return wml012BccListのサイズ
     */
    public int getWml012BccListSize() {
        if (wml012BccList__ == null) {
            return 0;
        }
        return wml012BccList__.size();
    }

    /**
     * <p>wml012tempDirId を取得します。
     * @return wml012tempDirId
     */
    public String getWml012tempDirId() {
        return wml012tempDirId__;
    }

    /**
     * <p>wml012tempDirId をセットします。
     * @param wml012tempDirId wml012tempDirId
     */
    public void setWml012tempDirId(String wml012tempDirId) {
        wml012tempDirId__ = wml012tempDirId;
    }

}
