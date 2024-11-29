package jp.groupsession.v2.man.man320;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man320.model.Man320DspModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン インフォメーション一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man320Form extends AbstractGsForm {

    /** 処理モード */
    private String cmd__ = null;
    /** オーダーキー1 */
    private int man320OrderKey__ = GSConst.ORDER_KEY_DESC;
    /** ソートキー1 */
    private int man320SortKey__ = Man320Biz.SORT_KEY_FRDATE;

    /** 管理者設定ボタン表示有無 0=非常時 1=表示 */
    private int man320FormAdminConfBtn__ = 0;
    /** 上ページ */
    private int man320SltPage1__ = 1;
    /** 下ページ */
    private int man320SltPage2__ = 1;
    /** 表示ページ */
    private int man320PageNum__ = 1;
    /** ページラベル */
    private ArrayList<LabelValueBean> man320PageLabel__;
    /** インフォメーション選択multibox */
    private String[] selectMsg__;

    //表示項目
    /** インフォメーションリスト（一覧表示） */
    private ArrayList<Man320DspModel> man320DspList__ = null;

    /** 編集対象のインフォメーションSID */
    private String man320SelectedSid__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateMan320Check(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if ((getSelectMsg() == null) || getSelectMsg().length < 1) {
            //チェックボックスがチェックされていない
            msg = new ActionMessage("error.select.required.text",
                    gsMsg.getMessage(GSConstMain.TEXT_INFO));
            errors.add("error.select.required.text", msg);
        }
        return errors;
    }

    /**
     * @return the selectMsg
     */
    public String[] getSelectMsg() {
        return selectMsg__;
    }


    /**
     * @param selectMsg the selectMsg to set
     */
    public void setSelectMsg(String[] selectMsg) {
        selectMsg__ = selectMsg;
    }


    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd__;
    }


    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }

    /**
     * @return the man320OrderKey
     */
    public int getMan320OrderKey() {
        return man320OrderKey__;
    }

    /**
     * @param man320OrderKey the man320OrderKey to set
     */
    public void setMan320OrderKey(int man320OrderKey) {
        man320OrderKey__ = man320OrderKey;
    }

    /**
     * @return the man320SortKey
     */
    public int getMan320SortKey() {
        return man320SortKey__;
    }

    /**
     * @param man320SortKey the man320SortKey to set
     */
    public void setMan320SortKey(int man320SortKey) {
        man320SortKey__ = man320SortKey;
    }

    /**
     * @return the man320FormAdminConfBtn
     */
    public int getMan320FormAdminConfBtn() {
        return man320FormAdminConfBtn__;
    }

    /**
     * @param man320FormAdminConfBtn the man320FormAdminConfBtn to set
     */
    public void setMan320FormAdminConfBtn(int man320FormAdminConfBtn) {
        man320FormAdminConfBtn__ = man320FormAdminConfBtn;
    }

    /**
     * @return the man320SltPage1
     */
    public int getMan320SltPage1() {
        return man320SltPage1__;
    }

    /**
     * @param man320SltPage1 the man320SltPage1 to set
     */
    public void setMan320SltPage1(int man320SltPage1) {
        man320SltPage1__ = man320SltPage1;
    }

    /**
     * @return the man320SltPage2
     */
    public int getMan320SltPage2() {
        return man320SltPage2__;
    }

    /**
     * @param man320SltPage2 the man320SltPage2 to set
     */
    public void setMan320SltPage2(int man320SltPage2) {
        man320SltPage2__ = man320SltPage2;
    }

    /**
     * @return the man320PageNum
     */
    public int getMan320PageNum() {
        return man320PageNum__;
    }

    /**
     * @param man320PageNum the man320PageNum to set
     */
    public void setMan320PageNum(int man320PageNum) {
        man320PageNum__ = man320PageNum;
    }

    /**
     * @return the man320PageLabel
     */
    public ArrayList<LabelValueBean> getMan320PageLabel() {
        return man320PageLabel__;
    }

    /**
     * @param man320PageLabel the man320PageLabel to set
     */
    public void setMan320PageLabel(ArrayList<LabelValueBean> man320PageLabel) {
        man320PageLabel__ = man320PageLabel;
    }

    /**
     * @return the man320DspList
     */
    public ArrayList<Man320DspModel> getMan320DspList() {
        return man320DspList__;
    }

    /**
     * @param man320DspList the man320DspList to set
     */
    public void setMan320DspList(ArrayList<Man320DspModel> man320DspList) {
        man320DspList__ = man320DspList;
    }

    /**
     * @return the man320SelectedSid
     */
    public String getMan320SelectedSid() {
        return man320SelectedSid__;
    }

    /**
     * @param man320SelectedSid the man320SelectedSid to set
     */
    public void setMan320SelectedSid(String man320SelectedSid) {
        man320SelectedSid__ = man320SelectedSid;
    }

}
