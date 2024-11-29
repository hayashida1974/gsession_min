package jp.groupsession.v2.man.man280kn;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.man.man280.Man280ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン使用制限確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man280knParamModel extends Man280ParamModel {
    /** メンバー(グループ)の名前一覧 */
    private ArrayList<LabelValueBean> man280knMemGroupNameList__ = null;
    /** メンバー(ユーザ)の名前一覧 */
    private ArrayList<UsrLabelValueBean> man280knMemUserNameList__ = null;

    /** 管理者(グループ)の名前一覧 */
    private ArrayList<LabelValueBean> man280knAdmGroupNameList__ = null;
    /** 管理者(ユーザ)の名前一覧 */
    private ArrayList<UsrLabelValueBean> man280knAdmUserNameList__ = null;

    /**
     * <p>man280knMemGroupNameList を取得します。
     * @return man280knMemGroupNameList
     */
    public ArrayList<LabelValueBean> getMan280knMemGroupNameList() {
        return man280knMemGroupNameList__;
    }
    /**
     * <p>man280knMemGroupNameList をセットします。
     * @param man280knMemGroupNameList man280knMemGroupNameList
     */
    public void setMan280knMemGroupNameList(
            ArrayList<LabelValueBean> man280knMemGroupNameList) {
        man280knMemGroupNameList__ = man280knMemGroupNameList;
    }
    /**
     * <p>man280knMemUserNameList を取得します。
     * @return man280knMemUserNameList
     */
    public ArrayList<UsrLabelValueBean> getMan280knMemUserNameList() {
        return man280knMemUserNameList__;
    }
    /**
     * <p>man280knMemUserNameList をセットします。
     * @param man280knMemUserNameList man280knMemUserNameList
     */
    public void setMan280knMemUserNameList(
            ArrayList<UsrLabelValueBean> man280knMemUserNameList) {
        man280knMemUserNameList__ = man280knMemUserNameList;
    }
    /**
     * <p>man280knAdmGroupNameList を取得します。
     * @return man280knAdmGroupNameList
     */
    public ArrayList<LabelValueBean> getMan280knAdmGroupNameList() {
        return man280knAdmGroupNameList__;
    }
    /**
     * <p>man280knAdmGroupNameList をセットします。
     * @param man280knAdmGroupNameList man280knAdmGroupNameList
     */
    public void setMan280knAdmGroupNameList(
            ArrayList<LabelValueBean> man280knAdmGroupNameList) {
        man280knAdmGroupNameList__ = man280knAdmGroupNameList;
    }
    /**
     * <p>man280knAdmUserNameList を取得します。
     * @return man280knAdmUserNameList
     */
    public ArrayList<UsrLabelValueBean> getMan280knAdmUserNameList() {
        return man280knAdmUserNameList__;
    }
    /**
     * <p>man280knAdmUserNameList をセットします。
     * @param man280knAdmUserNameList man280knAdmUserNameList
     */
    public void setMan280knAdmUserNameList(
            ArrayList<UsrLabelValueBean> man280knAdmUserNameList) {
        man280knAdmUserNameList__ = man280knAdmUserNameList;
    }
}