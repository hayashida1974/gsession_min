package jp.groupsession.v2.rng.rng140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngValidate;
import jp.groupsession.v2.rng.rng150.Rng150Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;


/**
 * <br>[機  能] 稟議 テンプレートカテゴリ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140Form extends Rng150Form {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng140Form.class);
    //入力項目
    /** カテゴリ名 */
    private String rng140CatName__ = "";

    /** カテゴリSID */
    private int rng140CatSid__ = 0;
    /** 状態区分 0=登録  1=編集 */
    private int rng140ProcMode__ = 0;
    /** 遷移元フラグ */
    private int rng140SeniFlg__ = 0;

    //管理者権限
    /** カテゴリ管理者ユーザ */
    private UserGroupSelectModel rng140UserAdmList__ = new UserGroupSelectModel();
    /** カテゴリ管理者ユーザ 選択UI */
    private UserGroupSelector rng140UserAdmSelector__ =
            UserGroupSelector.builder()
                .chainGroupSelectionParamName(
                        "rng140UserAdmList.group.selectedSingle"
                                )
                .chainType(EnumSelectType.USERGROUP)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainSelect(Select.builder()
                        .chainParameterName("rng140UserAdmList.selected("
                                + RngConst.CATEGORY_ADMIN
                                + ")"
                                )
                        )
                .build();

    //使用制限
    /** カテゴリ使用制限 */
    private int rng140UserLimit__ = RngConst.LIMIT_NOT_USE;

    //使用制限ユーザ・グループ
    /** 制限するユーザを指定：0 許可するユーザを指定：1 */
    private int rng140UserLimitType__ = RngConst.LIMIT_TYPE_LIMIT;
    /** 使用制限ユーザ */
    private UserGroupSelectModel rng140UserLimitList__ = new UserGroupSelectModel();
    /** 使用制限ユーザ 選択UI*/
    private UserGroupSelector rng140UserLimitSelector__ =
                UserGroupSelector.builder()
                .chainGroupSelectionParamName(
                        "rng140UserLimitList.group.selectedSingle"
                                )
                .chainType(EnumSelectType.USERGROUP)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainSelect(Select.builder()
                        .chainParameterName("rng140UserLimitList.selected("
                                + RngConst.CATEGORY_USE_LIMIT
                                + ")"
                                ))
                .build();

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateRng140(
            RequestModel reqMdl, Connection con, Rng140Form form) throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String categoryMsg = gsMsg.getMessage("cmn.category.name");

        //カテゴリ名
        errors = RngValidate.validateCmnFieldText(errors,
                categoryMsg,
                rng140CatName__,
                "rng140CatName__",
                20,
                true);

        //個人テンプレートの場合はカテゴリ名だけをチェック
        if (form.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            return errors;
        }
        HashMap<String, String[]> adminSelected = rng140UserAdmList__.getSelected();

        //カテゴリ管理者の入力チェック
        String[] categoryAdminList = adminSelected.get(RngConst.CATEGORY_ADMIN);
        if (categoryAdminList != null) {
            for (String adminSid:categoryAdminList) {
                boolean errorFlg = false;
                errorFlg = __existGrpUser(con, adminSid);

                if (errorFlg) {
                    String categoryAdmin
                    = gsMsg.getMessage("cmn.category") + gsMsg.getMessage("cmn.admin");
                    ActionMessage msg =  new ActionMessage("search.data.notfound", categoryAdmin);
                    String eprefix = "categoryAdmin";
                    StrutsUtil.addMessage(errors, msg, eprefix);
                    break;
                }
            }
        }

        //使用制限
        if (rng140UserLimit__ != RngConst.LIMIT_NOT_USE
         && rng140UserLimit__ != RngConst.LIMIT_USE) {
            ActionMessage msg
            =  new ActionMessage("error.input.notvalidate.data", gsMsg.getMessage("rng.05"));
            String eprefix = "ringiLimit";
            StrutsUtil.addMessage(errors, msg, eprefix);
        }

        //制限対象
        if (rng140UserLimit__ == RngConst.LIMIT_USE) {

            if (rng140UserLimitType__ != RngConst.LIMIT_TYPE_LIMIT
             && rng140UserLimitType__ != RngConst.LIMIT_TYPE_ACCEPT) {
                ActionMessage msg =  new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("schedule.sch240.05"));
                String eprefix = "ringiType";
                StrutsUtil.addMessage(errors, msg, eprefix);
            }

            //制限ユーザ
            HashMap<String, String[]> limitSelected = rng140UserLimitList__.getSelected();
            String[] limitList = limitSelected.get( RngConst.CATEGORY_USE_LIMIT);

            if (limitList == null || limitList.length == 0) {
                // 選択されていない場合 → エラーメッセージ
                ActionMessage msg =  new ActionMessage("error.select.required.text",
                        gsMsg.getMessage("schedule.sch240.05"));
                String eprefix = "limitNotUser";
                StrutsUtil.addMessage(errors, msg, eprefix);
            } else {
                // 選択されている場合 → ユーザが存在するかチェック
                for (String limitSid:limitList) {
                    boolean errorFlg = false;
                    errorFlg = __existGrpUser(con, limitSid);
                    if (errorFlg) {
                        String limitUser = gsMsg.getMessage("schedule.sch240.05");
                        ActionMessage msg
                        =  new ActionMessage("error.input.notvalidate.data", limitUser);
                        String eprefix = "limitUser";
                        StrutsUtil.addMessage(errors, msg, eprefix);
                        break;
                    }
                }
            }
        }

        return errors;
    }

    /**
     *
     * <br>[機  能]グループ・ユーザの存在を判定
     * <br>[解  説]
     * <br>[備  考]
     *@param con コネクション
     *@param  strSid 判定するSID
     *@return 存在可否(true:存在する)
     *@throws SQLException SQL実行時例外
     */
    private boolean __existGrpUser(Connection con, String strSid) throws SQLException {

        if (strSid.startsWith("G")) {
            int sid = NullDefault.getInt(strSid.substring(1), -1);
            if (!GSValidateUser.existGroup(sid, con)) {
                return true;
            }

        } else {
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            int sid = NullDefault.getInt(strSid, -1);
            if (!cuDao.isExistUser(sid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 削除チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws Exception 例外発生
     */
    public ActionErrors deleteCheck(Connection con,
                                    int userSid,
                                    RequestModel reqMdl) throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String fieldfix;

        Rng140ParamModel paramMdl = new Rng140ParamModel();
        paramMdl.setParam(this);
        Rng140Biz biz = new Rng140Biz(reqMdl);
        ArrayList<String> list = biz.getDeleteTmpList(paramMdl, con, userSid);
        paramMdl.setFormData(this);

        //カテゴリにテンプレートなし
        if (list.isEmpty()) {
            return errors;
        }

        log__.debug("エラー有り");
        log__.debug("rng140CatSid == " + rng140CatSid__);
        fieldfix = "tmpBelongCat" + ".";
        String msgKey = "error.ringi.delete.template.category";
        msg = new ActionMessage(msgKey);
        errors.add(fieldfix, msg);
        return errors;
    }

    /**
     * @return rng140CatName
     */
    public String getRng140CatName() {
        return rng140CatName__;
    }

    /**
     * @param rng140CatName セットする rng140CatName
     */
    public void setRng140CatName(String rng140CatName) {
        rng140CatName__ = rng140CatName;
    }

    /**
     * @return rng140CatSid
     */
    public int getRng140CatSid() {
        return rng140CatSid__;
    }

    /**
     * @param rng140CatSid セットする rng140CatSid
     */
    public void setRng140CatSid(int rng140CatSid) {
        rng140CatSid__ = rng140CatSid;
    }

    /**
     * @return rng140ProcMode
     */
    public int getRng140ProcMode() {
        return rng140ProcMode__;
    }

    /**
     * @param rng140ProcMode 設定する rng140ProcMode
     */
    public void setRng140ProcMode(int rng140ProcMode) {
        rng140ProcMode__ = rng140ProcMode;
    }

    /**
     * @return rng140SeniFlg
     */
    public int getRng140SeniFlg() {
        return rng140SeniFlg__;
    }

    /**
     * @param rng140SeniFlg 設定する rng140SeniFlg
     */
    public void setRng140SeniFlg(int rng140SeniFlg) {
        rng140SeniFlg__ = rng140SeniFlg;
    }

    /**
     * <p>rng140UserAdmList を取得します。
     * @return rng140UserAdmList
     */
    public UserGroupSelectModel getRng140UserAdmList() {
        return rng140UserAdmList__;
    }

    /**
     * <p>rng140UserAdmList をセットします。
     * @param rng140UserAdmList rng140UserAdmList
     */
    public void setRng140UserAdmList(UserGroupSelectModel rng140UserAdmList) {
        rng140UserAdmList__ = rng140UserAdmList;
    }

    /**
     * <p>rmg140UserLimit を取得します。
     * @return rmg140UserLimit
     */
    public int getRng140UserLimit() {
        return rng140UserLimit__;
    }

    /**
     * <p>rmg140UserLimit をセットします。
     * @param rng140UserLimit rng140UserLimit
     */
    public void setRng140UserLimit(int rng140UserLimit) {
        this.rng140UserLimit__ = rng140UserLimit;
    }

    /**
     * <p>rng140UserLimitType を取得します。
     * @return rng140UserLimitType
     */
    public int getRng140UserLimitType() {
        return rng140UserLimitType__;
    }

    /**
     * <p>rng140UserLimitType をセットします。
     * @param rng140UserLimitType rng140UserLimitType
     */
    public void setRng140UserLimitType(int rng140UserLimitType) {
        rng140UserLimitType__ = rng140UserLimitType;
    }

    /**
     * <p>rng140UserLimitList を取得します。
     * @return rng140UserLimitList
     */
    public UserGroupSelectModel getRng140UserLimitList() {
        return rng140UserLimitList__;
    }

    /**
     * <p>rng140UserLimitList をセットします。
     * @param rng140UserLimitList rng140UserLimitList
     */
    public void setRng140UserLimitList(UserGroupSelectModel rng140UserLimitList) {
        rng140UserLimitList__ = rng140UserLimitList;
    }
    /**
     * <p>rng140UserAdmSelector を取得します。
     * @return rng140UserAdmSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140Form#rng140UserAdmSelector
     */
    public UserGroupSelector getRng140UserAdmSelector() {
        return rng140UserAdmSelector__;
    }

    /**
     * <p>rng140UserAdmSelector をセットします。
     * @param rng140UserAdmSelector rng140UserAdmSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140Form#rng140UserAdmSelector
     */
    public void setRng140UserAdmSelector(UserGroupSelector rng140UserAdmSelector) {
        this.rng140UserAdmSelector__ = rng140UserAdmSelector;
    }
    /**
     * <p>rng140UserLimitSelector を取得します。
     * @return rng140UserLimitSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140Form#rng140UserLimitSelector
     */
    public UserGroupSelector getRng140UserLimitSelector() {
        return rng140UserLimitSelector__;
    }

    /**
     * <p>rng140UserLimitSelector をセットします。
     * @param rng140UserLimitSelector rng140UserLimitSelector
     * @see jp.groupsession.v2.rng.rng140.Rng140Form#rng140UserLimitSelector
     */
    public void setRng140UserLimitSelector(
            UserGroupSelector rng140UserLimitSelector) {
        this.rng140UserLimitSelector__ = rng140UserLimitSelector;
    }
}
