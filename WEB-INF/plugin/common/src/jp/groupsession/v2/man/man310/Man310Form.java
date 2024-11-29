package jp.groupsession.v2.man.man310;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.man320.Man320Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン インフォメーション詳細画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man310Form extends Man320Form {

    /** 対象のインフォメーションSID */
    private String man310SelectedSid__;
    /** メッセージ  */
    private String man310Msg__ = null;
    /** 内容  */
    private String man310Value__ = null;
    /** 公開対象者リスト */
    private ArrayList <LabelValueBean> man310KoukaiList__ = null;
    /** 登録者/更新者ユーザ姓 */
    private String man310NameSei__;
    /** 登録者/更新者ユーザ名 */
    private String man310NameMei__;
    /** 登録者/更新者状態区分 */
    private int man310UsrJkbn__;

    /** 添付ファイルSID */
    private Long man310binSid__ = new Long(0);
    /** 添付ファイル情報 */
    private ArrayList < CmnBinfModel > tmpFileList__ = null;

    /**
     * @return the tmpFileList
     */
    public ArrayList<CmnBinfModel> getTmpFileList() {
        return tmpFileList__;
    }

    /**
     * @param tmpFileList the tmpFileList to set
     */
    public void setTmpFileList(ArrayList<CmnBinfModel> tmpFileList) {
        tmpFileList__ = tmpFileList;
    }

    /**
     * @return the man310binSid
     */
    public Long getMan310binSid() {
        return man310binSid__;
    }

    /**
     * @param man310binSid the man310binSid to set
     */
    public void setMan310binSid(Long man310binSid) {
        man310binSid__ = man310binSid;
    }

    /**
     * @return the man310UsrJkbn
     */
    public int getMan310UsrJkbn() {
        return man310UsrJkbn__;
    }

    /**
     * @param man310UsrJkbn the man310UsrJkbn to set
     */
    public void setMan310UsrJkbn(int man310UsrJkbn) {
        man310UsrJkbn__ = man310UsrJkbn;
    }

    /**
     * @return the man310NameSei
     */
    public String getMan310NameSei() {
        return man310NameSei__;
    }

    /**
     * @param man310NameSei the man310NameSei to set
     */
    public void setMan310NameSei(String man310NameSei) {
        man310NameSei__ = man310NameSei;
    }

    /**
     * @return the man310NameMei
     */
    public String getMan310NameMei() {
        return man310NameMei__;
    }

    /**
     * @param man310NameMei the man310NameMei to set
     */
    public void setMan310NameMei(String man310NameMei) {
        man310NameMei__ = man310NameMei;
    }


    /**
     * @return the man310Msg
     */
    public String getMan310Msg() {
        return man310Msg__;
    }

    /**
     * @param man310Msg the man310Msg to set
     */
    public void setMan310Msg(String man310Msg) {
        man310Msg__ = man310Msg;
    }

    /**
     * @return the man310Value
     */
    public String getMan310Value() {
        return man310Value__;
    }

    /**
     * @param man310Value the man310Value to set
     */
    public void setMan310Value(String man310Value) {
        man310Value__ = man310Value;
    }

    /**
     * @return the man310KoukaiList
     */
    public ArrayList<LabelValueBean> getMan310KoukaiList() {
        return man310KoukaiList__;
    }

    /**
     * @param man310KoukaiList the man310KoukaiList to set
     */
    public void setMan310KoukaiList(ArrayList<LabelValueBean> man310KoukaiList) {
        man310KoukaiList__ = man310KoukaiList;
    }

    /**
     * @return the man310SelectedSid
     */
    public String getMan310SelectedSid() {
        return man310SelectedSid__;
    }

    /**
     * @param man310SelectedSid the man310SelectedSid to set
     */
    public void setMan310SelectedSid(String man310SelectedSid) {
        man310SelectedSid__ = man310SelectedSid;
    }

}
