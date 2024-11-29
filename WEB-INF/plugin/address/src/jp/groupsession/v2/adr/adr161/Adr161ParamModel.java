package jp.groupsession.v2.adr.adr161;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr160.Adr160ParamModel;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr161ParamModel extends Adr160ParamModel {

    /** タイトル */
    private String adr161title__ = null;
    /** マーク */
    private int adr161Mark__ = -1;
    /** コンタクト日時From */
    private String adr161ContactFrom__ = null;
    /** コンタクト日時To */
    private String adr161ContactTo__ = null;
    /** プロジェクト  */
    private String adr161enterContactProject__ = null;
    /** 備考 */
    private String adr161biko__ = null;
    /** 同時登録ユーザ */
    private String[] adr161saveUser__ = null;
    /** 同時登録ユーザ(表示用) */
    private ArrayList<AdrAddressModel> adr161DoujiUser__ = null;

    /** プラグインID */
    private String adr161pluginId__ = GSConstAddress.PLUGIN_ID_ADDRESS;

    /** コンタクト履歴 登録対象者情報 会社名 */
    private String adr161ContactUserComName__ = null;
    /** コンタクト履歴 登録対象者情報 氏名 */
    private String adr161ContactUserName__ = null;

    /** プロジェクトSID */
    private String[] adr161ProjectSid__ = null;
    /** プロジェクトリスト */
    List <LabelValueBean> adr161ProjectList__ = null;
    /** プロジェクトプラグイン使用有無 0=使用 1=未使用*/
    private int projectPluginKbn__ = -1;

    /** ダウンロード時添付ファイルＩＤ */
    private String adr161TmpFileId__;
    /** 添付情報 */
    private List<CmnBinfModel> tmpFileList__ = null;

    /** 編集可能フラグ 0:編集可 1:編集不可*/
    private int adr161EditFlg__ = GSConstAddress.CONTACT_EDIT;

    /** 遷移フラグ */
    private String seniFlg__ = null;
    /**
     * @return adr161title
     */
    public String getAdr161title() {
        return adr161title__;
    }
    /**
     * @param adr161title セットする adr161title
     */
    public void setAdr161title(String adr161title) {
        adr161title__ = adr161title;
    }
    /**
     * @return adr161Mark
     */
    public int getAdr161Mark() {
        return adr161Mark__;
    }
    /**
     * @param adr161Mark セットする adr161Mark
     */
    public void setAdr161Mark(int adr161Mark) {
        adr161Mark__ = adr161Mark;
    }
    /**
     * @return adr161ContactFrom
     */
    public String getAdr161ContactFrom() {
        return adr161ContactFrom__;
    }
    /**
     * @param adr161ContactFrom セットする adr161ContactFrom
     */
    public void setAdr161ContactFrom(String adr161ContactFrom) {
        adr161ContactFrom__ = adr161ContactFrom;
    }
    /**
     * @return adr161ContactTo
     */
    public String getAdr161ContactTo() {
        return adr161ContactTo__;
    }
    /**
     * @param adr161ContactTo セットする adr161ContactTo
     */
    public void setAdr161ContactTo(String adr161ContactTo) {
        adr161ContactTo__ = adr161ContactTo;
    }
    /**
     * @return adr161enterContactProject
     */
    public String getAdr161enterContactProject() {
        return adr161enterContactProject__;
    }
    /**
     * @param adr161enterContactProject セットする adr161enterContactProject
     */
    public void setAdr161enterContactProject(String adr161enterContactProject) {
        adr161enterContactProject__ = adr161enterContactProject;
    }
    /**
     * @return adr161biko
     */
    public String getAdr161biko() {
        return adr161biko__;
    }
    /**
     * @param adr161biko セットする adr161biko
     */
    public void setAdr161biko(String adr161biko) {
        adr161biko__ = adr161biko;
    }
    /**
     * @return adr161pluginId
     */
    public String getAdr161pluginId() {
        return adr161pluginId__;
    }
    /**
     * @param adr161pluginId セットする adr161pluginId
     */
    public void setAdr161pluginId(String adr161pluginId) {
        adr161pluginId__ = adr161pluginId;
    }
    /**
     * @return adr161ContactUserComName
     */
    public String getAdr161ContactUserComName() {
        return adr161ContactUserComName__;
    }
    /**
     * @param adr161ContactUserComName セットする adr161ContactUserComName
     */
    public void setAdr161ContactUserComName(String adr161ContactUserComName) {
        adr161ContactUserComName__ = adr161ContactUserComName;
    }
    /**
     * @return adr161ContactUserName
     */
    public String getAdr161ContactUserName() {
        return adr161ContactUserName__;
    }
    /**
     * @param adr161ContactUserName セットする adr161ContactUserName
     */
    public void setAdr161ContactUserName(String adr161ContactUserName) {
        adr161ContactUserName__ = adr161ContactUserName;
    }
    /**
     * @return adr161ProjectSid
     */
    public String[] getAdr161ProjectSid() {
        return adr161ProjectSid__;
    }
    /**
     * @param adr161ProjectSid セットする adr161ProjectSid
     */
    public void setAdr161ProjectSid(String[] adr161ProjectSid) {
        adr161ProjectSid__ = adr161ProjectSid;
    }
    /**
     * @return adr161ProjectList
     */
    public List<LabelValueBean> getAdr161ProjectList() {
        return adr161ProjectList__;
    }
    /**
     * @param adr161ProjectList セットする adr161ProjectList
     */
    public void setAdr161ProjectList(List<LabelValueBean> adr161ProjectList) {
        adr161ProjectList__ = adr161ProjectList;
    }
    /**
     * @return projectPluginKbn
     */
    public int getProjectPluginKbn() {
        return projectPluginKbn__;
    }
    /**
     * @param projectPluginKbn セットする projectPluginKbn
     */
    public void setProjectPluginKbn(int projectPluginKbn) {
        projectPluginKbn__ = projectPluginKbn;
    }
    /**
     * @return adr161TmpFileId
     */
    public String getAdr161TmpFileId() {
        return adr161TmpFileId__;
    }
    /**
     * @param adr161TmpFileId セットする adr161TmpFileId
     */
    public void setAdr161TmpFileId(String adr161TmpFileId) {
        adr161TmpFileId__ = adr161TmpFileId;
    }
    /**
     * @return tmpFileList
     */
    public List<CmnBinfModel> getTmpFileList() {
        return tmpFileList__;
    }
    /**
     * @param tmpFileList セットする tmpFileList
     */
    public void setTmpFileList(List<CmnBinfModel> tmpFileList) {
        tmpFileList__ = tmpFileList;
    }
    /**
     * @return seniFlg
     */
    public String getSeniFlg() {
        return seniFlg__;
    }
    /**
     * @param seniFlg セットする seniFlg
     */
    public void setSeniFlg(String seniFlg) {
        seniFlg__ = seniFlg;
    }
    /**
     * @return adr161saveUser
     */
    public String[] getAdr161saveUser() {
        return adr161saveUser__;
    }
    /**
     * @param adr161saveUser セットする adr161saveUser
     */
    public void setAdr161saveUser(String[] adr161saveUser) {
        adr161saveUser__ = adr161saveUser;
    }
    /**
     * @return adr161DoujiUser
     */
    public ArrayList<AdrAddressModel> getAdr161DoujiUser() {
        return adr161DoujiUser__;
    }
    /**
     * @param adr161DoujiUser セットする adr161DoujiUser
     */
    public void setAdr161DoujiUser(ArrayList<AdrAddressModel> adr161DoujiUser) {
        adr161DoujiUser__ = adr161DoujiUser;
    }
    /**
     * <p>adr161EditFlg を取得します。
     * @return adr161EditFlg
     * @see jp.groupsession.v2.adr.adr161.Adr161ParamModel#adr161EditFlg__
     */
    public int getAdr161EditFlg() {
        return adr161EditFlg__;
    }
    /**
     * <p>adr161EditFlg をセットします。
     * @param adr161EditFlg adr161EditFlg
     * @see jp.groupsession.v2.adr.adr161.Adr161ParamModel#adr161EditFlg__
     */
    public void setAdr161EditFlg(int adr161EditFlg) {
        adr161EditFlg__ = adr161EditFlg;
    }

}