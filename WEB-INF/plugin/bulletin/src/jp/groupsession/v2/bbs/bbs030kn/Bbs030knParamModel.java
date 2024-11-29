package jp.groupsession.v2.bbs.bbs030kn;

import java.util.ArrayList;

import jp.groupsession.v2.bbs.bbs030.Bbs030ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラム登録確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs030knParamModel extends Bbs030ParamModel {
    /** コメント(表示用) */
    private String bbs030viewcomment__ = null;
    /** 編集メンバーの名前一覧 */
    private ArrayList<UsrLabelValueBean> bbs030knMemNameList__ = null;
    /** 閲覧メンバーの名前一覧 */
    private ArrayList<UsrLabelValueBean> bbs030knMemNameListRead__ = null;
    /** 管理者メンバーの名前一覧 */
    private ArrayList<UsrLabelValueBean> bbs030knMemNameListAdm__ = null;
    /** スレッドテンプレート(表示用) */
    private String bbs030viewTemplate__ = null;
    /** 親フォーラム名 */
    private String bbs030knForumName__ = null;
    /** 親フォーラムバイナリSID */
    private Long bbs030knParentBinSid__ = new Long(0);

    /**
     * @return bbs030viewcomment を戻します。
     */
    public String getBbs030viewcomment() {
        return bbs030viewcomment__;
    }

    /**
     * @param bbs030viewcomment 設定する bbs030viewcomment。
     */
    public void setBbs030viewcomment(String bbs030viewcomment) {
        bbs030viewcomment__ = bbs030viewcomment;
    }

    /**
     * @return bbs030knMemNameList を戻します。
     */
    public ArrayList<UsrLabelValueBean> getBbs030knMemNameList() {
        return bbs030knMemNameList__;
    }

    /**
     * @param bbs030knMemNameList 設定する bbs030knMemNameList。
     */
    public void setBbs030knMemNameList(ArrayList<UsrLabelValueBean> bbs030knMemNameList) {
        bbs030knMemNameList__ = bbs030knMemNameList;
    }

    /**
     * <p>bbs030knMemNameListRead を取得します。
     * @return bbs030knMemNameListRead
     */
    public ArrayList<UsrLabelValueBean> getBbs030knMemNameListRead() {
        return bbs030knMemNameListRead__;
    }

    /**
     * <p>bbs030knMemNameListRead をセットします。
     * @param bbs030knMemNameListRead bbs030knMemNameListRead
     */
    public void setBbs030knMemNameListRead(
            ArrayList<UsrLabelValueBean> bbs030knMemNameListRead) {
        bbs030knMemNameListRead__ = bbs030knMemNameListRead;
    }

    /**
     * <p>bbs030knMemNameListAdm を取得します。
     * @return bbs030knMemNameListAdm
     */
    public ArrayList<UsrLabelValueBean> getBbs030knMemNameListAdm() {
        return bbs030knMemNameListAdm__;
    }

    /**
     * <p>bbs030knMemNameListAdm をセットします。
     * @param bbs030knMemNameListAdm bbs030knMemNameListAdm
     */
    public void setBbs030knMemNameListAdm(
            ArrayList<UsrLabelValueBean> bbs030knMemNameListAdm) {
        bbs030knMemNameListAdm__ = bbs030knMemNameListAdm;
    }

    /**
     * <p>bbs030viewTemplate を取得します。
     * @return bbs030viewTemplate
     */
    public String getBbs030viewTemplate() {
        return bbs030viewTemplate__;
    }

    /**
     * <p>bbs030viewTemplate をセットします。
     * @param bbs030viewTemplate bbs030viewTemplate
     */
    public void setBbs030viewTemplate(String bbs030viewTemplate) {
        bbs030viewTemplate__ = bbs030viewTemplate;
    }

    /**
     * <p>bbs030knForumName を取得します。
     * @return bbs030knForumName
     */
    public String getBbs030knForumName() {
        return bbs030knForumName__;
    }

    /**
     * <p>bbs030knForumName をセットします。
     * @param bbs030knForumName bbs030knForumName
     */
    public void setBbs030knForumName(String bbs030knForumName) {
        bbs030knForumName__ = bbs030knForumName;
    }

    /**
     * <p>bbs030knParentBinSid を取得します。
     * @return bbs030knParentBinSid
     */
    public Long getBbs030knParentBinSid() {
        return bbs030knParentBinSid__;
    }

    /**
     * <p>bbs030knParentBinSid をセットします。
     * @param bbs030knParentBinSid bbs030knParentBinSid
     */
    public void setBbs030knParentBinSid(Long bbs030knParentBinSid) {
        bbs030knParentBinSid__ = bbs030knParentBinSid;
    }

}