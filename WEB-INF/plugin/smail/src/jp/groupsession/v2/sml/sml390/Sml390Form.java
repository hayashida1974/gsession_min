package jp.groupsession.v2.sml.sml390;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sml.GSValidateSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.sml380.Sml380Form;
import jp.groupsession.v2.sml.ui.parts.account.SharedAccountSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] 送信先制限設定 追加編集画面　フォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml390Form extends Sml380Form {
    /** 制限送信先制御コンボ(選択用)のグループコンボに設定する値 役職一覧のVALUE */
    public static final int ACCOUNT_COMBO_VALUE = -10;
    /** ユーザ情報 or アクセス制御コンボ(選択用)のグループコンボに設定する値 グループ一覧のVALUE */
    public static final int GROUP_COMBO_VALUE = -9;
    /** 送信先リスト 備考 MAX文字数 */
    public static final int MAXLEN_DESTLIST_BIKO = 1000;

    /** 初期表示 */
    private int sml390initFlg__ = 0;
    /** 送信先リスト名 */
    private String sml390sbcName__ = null;
    /** 備考 */
    private String sml390biko__ = null;
    /** 禁止送信先 ユーザ/グループ */
    private String[] sml390sbdTarget__ = null;
    /** 禁止送信先 選択グループ */
    private int sml390banGroup__  = -1;
    /** 禁止送信先 グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** 禁止送信先 ユーザ/グループ選択コンボ */
    private List<UsrLabelValueBean> sml390sbdTargetSelectCombo__  = null;
    /** 禁止送信先 選択UI */
    private UserGroupSelector sml390sbdTargetSelector__ =
            UserGroupSelector.builder()
            .chainType(EnumSelectType.USERGROUP)
            .chainGrpType(EnumGroupSelectType.GROUPONLY)
            .chainGroupSelectionParamName("sml390banGroup")
            .chainSelect(Select.builder()
                            .chainParameterName("sml390sbdTarget"))
            .build();


    /** 禁止送信先 アカウント */
    private String[] sml390sbdTargetAcc__ = null;
    /** 禁止送信先 アカウント選択コンボ */
    private List<LabelValueBean> sml390sbdTargetAccSelectCombo__  = null;
    /** 禁止送信先 アカウント選択UI */
    private SharedAccountSelector sml390sbdTargetAccSelector__ =
            SharedAccountSelector.builder()
            .chainSelect(Select.builder()
                            .chainParameterName("sml390sbdTargetAcc"))
            .build();

    /** 許可ユーザ・グループ */
    private String[] sml390sbpTarget__ = null;

    /** 許可ユーザ 選択グループ */
    private int sml390ableGroup__  = -1;

    /** 許可ユーザ 選択コンボ */
    private List<UsrLabelValueBean> sml390sbpTargetSelectCombo__  = null;
    /** 許可ユーザ 選択UI */
    private UserGroupSelector sml390sbpTargetSelector__ =
            UserGroupSelector.builder()
            .chainType(EnumSelectType.USERGROUP)
            .chainGrpType(EnumGroupSelectType.GROUPONLY)
            .chainGroupSelectionParamName("sml390ableGroup")
            .chainSelect(Select.builder()
                            .chainParameterName("sml390sbpTarget"))
            .build();

    /** 役職 */
    private int sml390post__  = -1;
    /** 役職コンボ */
    private List<LabelValueBean> postCombo__ = null;

    /**
     * <p>sml390initFlg を取得します。
     * @return sml390initFlg
     */
    public int getSml390initFlg() {
        return sml390initFlg__;
    }
    /**
     * <p>sml390initFlg をセットします。
     * @param sml390initFlg sml390initFlg
     */
    public void setSml390initFlg(int sml390initFlg) {
        sml390initFlg__ = sml390initFlg;
    }
    /**
     * <p>sml390sbcName を取得します。
     * @return sml390sbcName
     */
    public String getSml390sbcName() {
        return sml390sbcName__;
    }
    /**
     * <p>sml390sbcName をセットします。
     * @param sml390sbcName sml390sbcName
     */
    public void setSml390sbcName(String sml390sbcName) {
        sml390sbcName__ = sml390sbcName;
    }
    /**
     * <p>sml390biko を取得します。
     * @return sml390biko
     */
    public String getSml390biko() {
        return sml390biko__;
    }
    /**
     * <p>sml390biko をセットします。
     * @param sml390biko sml390biko
     */
    public void setSml390biko(String sml390biko) {
        sml390biko__ = sml390biko;
    }
    /**
     * <p>sml390sbdTarget を取得します。
     * @return sml390sbdTarget
     */
    public String[] getSml390sbdTarget() {
        return sml390sbdTarget__;
    }
    /**
     * <p>sml390sbdTarget をセットします。
     * @param sml390sbdTarget sml390sbdTarget
     */
    public void setSml390sbdTarget(String[] sml390sbdTarget) {
        sml390sbdTarget__ = sml390sbdTarget;
    }
    /**
     * <p>sml390sbdTargetSelectCombo を取得します。
     * @return sml390sbdTargetSelectCombo
     */
    public List<UsrLabelValueBean> getSml390sbdTargetSelectCombo() {
        return sml390sbdTargetSelectCombo__;
    }
    /**
     * <p>sml390sbdTargetSelectCombo をセットします。
     * @param sml390sbdTargetSelectCombo sml390sbdTargetSelectCombo
     */
    public void setSml390sbdTargetSelectCombo(
            List<UsrLabelValueBean> sml390sbdTargetSelectCombo) {
        sml390sbdTargetSelectCombo__ = sml390sbdTargetSelectCombo;
    }
    /**
     * <p>sml390sbdTargetSelector__ を取得します。
     * @return sml390sbdTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390Form#sml390sbdTargetSelector__
     */
    public UserGroupSelector getSml390sbdTargetSelector() {
        return sml390sbdTargetSelector__;
    }
    /**
     * <p>sml390sbdTargetSelector__ をセットします。
     * @param sml390sbdTargetSelector sml390sbdTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390Form#sml390sbdTargetSelector__
     */
    public void setSml390sbdTargetSelector(UserGroupSelector sml390sbdTargetSelector) {
        this.sml390sbdTargetSelector__ = sml390sbdTargetSelector;
    }
    /**
     * <p>sml390sbdTargetAcc を取得します。
     * @return sml390sbdTargetAcc
     */
    public String[] getSml390sbdTargetAcc() {
        return sml390sbdTargetAcc__;
    }
    /**
     * <p>sml390sbdTargetAcc をセットします。
     * @param sml390sbdTargetAcc sml390sbdTargetAcc
     */
    public void setSml390sbdTargetAcc(String[] sml390sbdTargetAcc) {
        sml390sbdTargetAcc__ = sml390sbdTargetAcc;
    }
    /**
     * <p>sml390sbdTargetAccSelectCombo を取得します。
     * @return sml390sbdTargetAccSelectCombo
     */
    public List<LabelValueBean> getSml390sbdTargetAccSelectCombo() {
        return sml390sbdTargetAccSelectCombo__;
    }
    /**
     * <p>sml390sbdTargetAccSelectCombo をセットします。
     * @param sml390sbdTargetAccSelectCombo sml390sbdTargetAccSelectCombo
     */
    public void setSml390sbdTargetAccSelectCombo(
            List<LabelValueBean> sml390sbdTargetAccSelectCombo) {
        sml390sbdTargetAccSelectCombo__ = sml390sbdTargetAccSelectCombo;
    }
    /**
     * <p>sml390sbdTargetAccSelector__ を取得します。
     * @return sml390sbdTargetAccSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390Form#sml390sbdTargetAccSelector__
     */
    public SharedAccountSelector getSml390sbdTargetAccSelector() {
        return sml390sbdTargetAccSelector__;
    }
    /**
     * <p>sml390sbdTargetAccSelector__ をセットします。
     * @param sml390sbdTargetAccSelector sml390sbdTargetAccSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390Form#sml390sbdTargetAccSelector__
     */
    public void setSml390sbdTargetAccSelector(
            SharedAccountSelector sml390sbdTargetAccSelector) {
        this.sml390sbdTargetAccSelector__ = sml390sbdTargetAccSelector;
    }
    /**
     * <p>sml390sbpTarget を取得します。
     * @return sml390sbpTarget
     */
    public String[] getSml390sbpTarget() {
        return sml390sbpTarget__;
    }
    /**
     * <p>sml390sbpTarget をセットします。
     * @param sml390sbpTarget sml390sbpTarget
     */
    public void setSml390sbpTarget(String[] sml390sbpTarget) {
        sml390sbpTarget__ = sml390sbpTarget;
    }
    /**
     * <p>sml390ableGroup を取得します。
     * @return sml390ableGroup
     */
    public int getSml390ableGroup() {
        return sml390ableGroup__;
    }
    /**
     * <p>sml390ableGroup をセットします。
     * @param sml390ableGroup sml390ableGroup
     */
    public void setSml390ableGroup(int sml390ableGroup) {
        sml390ableGroup__ = sml390ableGroup;
    }
    /**
     * <p>sml390banGroup を取得します。
     * @return sml390banGroup
     */
    public int getSml390banGroup() {
        return sml390banGroup__;
    }
    /**
     * <p>sml390banGroup をセットします。
     * @param sml390banGroup sml390banGroup
     */
    public void setSml390banGroup(int sml390banGroup) {
        sml390banGroup__ = sml390banGroup;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>sml390sbpTargetSelectCombo を取得します。
     * @return sml390sbpTargetSelectCombo
     */
    public List<UsrLabelValueBean> getSml390sbpTargetSelectCombo() {
        return sml390sbpTargetSelectCombo__;
    }
    /**
     * <p>sml390sbpTargetSelectCombo をセットします。
     * @param sml390sbpTargetSelectCombo sml390sbpTargetSelectCombo
     */
    public void setSml390sbpTargetSelectCombo(
            List<UsrLabelValueBean> sml390sbpTargetSelectCombo) {
        sml390sbpTargetSelectCombo__ = sml390sbpTargetSelectCombo;
    }
    /**
     * <p>sml390sbpTargetSelector__ を取得します。
     * @return sml390sbpTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390Form#sml390sbpTargetSelector__
     */
    public UserGroupSelector getSml390sbpTargetSelector() {
        return sml390sbpTargetSelector__;
    }
    /**
     * <p>sml390sbpTargetSelector__ をセットします。
     * @param sml390sbpTargetSelector sml390sbpTargetSelector__
     * @see jp.groupsession.v2.sml.sml390.Sml390Form#sml390sbpTargetSelector__
     */
    public void setSml390sbpTargetSelector(UserGroupSelector sml390sbpTargetSelector) {
        this.sml390sbpTargetSelector__ = sml390sbpTargetSelector;
    }
    /**
     * <p>sml390post を取得します。
     * @return sml390post
     */
    public int getSml390post() {
        return sml390post__;
    }
    /**
     * <p>sml390post をセットします。
     * @param sml390post sml390post
     */
    public void setSml390post(int sml390post) {
        sml390post__ = sml390post;
    }
    /**
     * <p>postCombo を取得します。
     * @return postCombo
     */
    public List<LabelValueBean> getPostCombo() {
        return postCombo__;
    }
    /**
     * <p>postCombo をセットします。
     * @param postCombo postCombo
     */
    public void setPostCombo(List<LabelValueBean> postCombo) {
        postCombo__ = postCombo;
    }
    @Override
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("sml380EditBan", getSml380EditBan());
        msgForm.addHiddenParam("sml390initFlg", sml390initFlg__);
        msgForm.addHiddenParam("sml390sbcName", sml390sbcName__);
        msgForm.addHiddenParam("sml390banGroup", sml390banGroup__);
        msgForm.addHiddenParam("sml390sbdTarget", sml390sbdTarget__);
        msgForm.addHiddenParam("sml390sbdTargetAcc", sml390sbdTargetAcc__);
        msgForm.addHiddenParam("sml390post", sml390post__);
        msgForm.addHiddenParam("sml390ableGroup", sml390ableGroup__);
        msgForm.addHiddenParam("sml390sbpTarget", sml390sbpTarget__);
        msgForm.addHiddenParam("sml390biko", sml390biko__);
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateInputCheck(Connection con, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        _validateSid(errors, con, reqMdl);

        //送信制限名
        GSValidateSmail.validateTextBoxInput(errors, sml390sbcName__,
                "sml390sbcName",
                gsMsg.getMessage("sml.sml380.03"),
                MAXLEN_DESTLIST_NAME, true);
        //制限先
        __validateTargetCheck(errors, con, reqMdl);
        //送信許可
        __validatePermitCheck(errors, con, reqMdl);

        //備考
        GSValidateSmail.validateTextarea(errors, sml390biko__,
                "sml390biko",
                gsMsg.getMessage("cmn.memo"),
                MAXLEN_DESTLIST_BIKO, false);
        return errors;
    }
    /**
     *
     * <br>[機  能] 制限先入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    private void __validateTargetCheck(ActionErrors errors, Connection con, RequestModel reqMdl)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        List<String> usrSids = new ArrayList<String>();
        if (sml390sbdTarget__ != null) {
            for (String target : sml390sbdTarget__) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
        }
        int count = 0;
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
        if (glist != null) {
            count += glist.size();
        }
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        if (ulist != null) {
            count += ulist.size();
        }
        SmlAccountDao adao = new SmlAccountDao(con);
        List<LabelValueBean> alist = adao.selectSacSids(sml390sbdTargetAcc__,
                SmlAccountDao.JKBN_LIV);
        if (alist != null) {
            count += alist.size();
        }
        if (count == 0) {
            String msgKey = "error.input.selectoen.check";
            ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("sml.sml380.04"));
            StrutsUtil.addMessage(
                    errors, msg, "sml390sbdTarget." + msgKey);

        }

    }
    /**
     *
     * <br>[機  能] 許可入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    private void __validatePermitCheck(ActionErrors errors, Connection con, RequestModel reqMdl)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        List<String> usrSids = new ArrayList<String>();
        if (sml390sbpTarget__ != null) {
            for (String target : sml390sbpTarget__) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
        }
        int count = 0;
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
        if (glist != null) {
            count += glist.size();
        }
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        if (ulist != null) {
            count += ulist.size();
        }
        CmnPositionDao pdao = new CmnPositionDao(con);
        if (sml390post__ > 0 && pdao.getPosInfo(sml390post__) != null) {
            count++;
        }

        if (count == 0) {
            String msgKey = "error.input.selectoen.check";
            ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("sml.sml390.03"));
            StrutsUtil.addMessage(
                    errors, msg, "sml390sbpTarget." + msgKey);

        }

    }

}
