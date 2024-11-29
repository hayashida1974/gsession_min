package jp.groupsession.v2.usr.usr046;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.usr044.Usr044ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ユーザ情報 ラベル登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr046ParamModel extends Usr044ParamModel {

    //入力項目
    /** ラベル名 */
    private String usr046LabelName__;
    /** 備考 */
    private String usr046bikou__;
    /** カテゴリSID */
    private int usr046CatSid__ = -1;
    /** カテゴリリスト */
    private ArrayList<LabelValueBean> usr046CatCmbList__;

    /** saveカテゴリSID */
    private int saveUsr046CatSid__ = -1;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateUsr046(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //カテゴリ名
        String textCategoryName = gsMsg.getMessage(req, "cmn.label.name");
        //備考
        String textBiko = gsMsg.getMessage(req, "cmn.memo");
        //ラベル名
        UserUtil.validateTextField(errors,
                                           usr046LabelName__,
                                          "usr045LabelName",
                                          textCategoryName,
                                          30,
                                          true);
        //備考
        UserUtil.validateTextAreaField(errors,
                                              usr046bikou__,
                                             "usr046bikou",
                                             textBiko,
                                              300,
                                              false);
        return errors;
    }

    /**
     * <br>[機  能] 削除チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL例外発生
     */
    public ActionErrors deleteCheck(Connection con, RequestModel reqMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String fieldfix;

        //付加ラベル件数取得
        Usr046Biz biz = new Usr046Biz(reqMdl);
        int count = biz.getBelongCount(con, getLabelEditSid());
        if (count > 0) {
            //ラベル名取得
            CmnLabelUsrDao labDao = new CmnLabelUsrDao(con);
            CmnLabelUsrModel labMdl = labDao.selectOneLabel(getLabelEditSid());
            //メッセージ作成
            fieldfix = "labelEditSid" + ".";
            String msgKey = "error.user.duplication.label";
            msg = new ActionMessage(msgKey,
                    StringUtilHtml.transToHTmlPlusAmparsant(labMdl.getLabName()),
                    count);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
        }
        return errors;
    }

    /**
     * <p>usr046LabelName を取得します。
     * @return usr046LabelName
     */
    public String getUsr046LabelName() {
        return usr046LabelName__;
    }

    /**
     * <p>usr046LabelName をセットします。
     * @param usr046LabelName usr046LabelName
     */
    public void setUsr046LabelName(String usr046LabelName) {
        usr046LabelName__ = usr046LabelName;
    }

    /**
     * <p>usr046bikou を取得します。
     * @return usr046bikou
     */
    public String getUsr046bikou() {
        return usr046bikou__;
    }

    /**
     * <p>usr046bikou をセットします。
     * @param usr046bikou usr046bikou
     */
    public void setUsr046bikou(String usr046bikou) {
        usr046bikou__ = usr046bikou;
    }

    /**
     * @return usr046CatSid
     */
    public int getUsr046CatSid() {
        return usr046CatSid__;
    }

    /**
     * @param usr046CatSid セットする usr046CatSid
     */
    public void setUsr046CatSid(int usr046CatSid) {
        usr046CatSid__ = usr046CatSid;
    }

    /**
     * @return usr046CatCmbList
     */
    public ArrayList<LabelValueBean> getUsr046CatCmbList() {
        return usr046CatCmbList__;
    }

    /**
     * @param usr046CatCmbList セットする usr046CatCmbList
     */
    public void setUsr046CatCmbList(ArrayList<LabelValueBean> usr046CatCmbList) {
        usr046CatCmbList__ = usr046CatCmbList;
    }

    /**
     * @return saveUsr046CatSid
     */
    public int getSaveUsr046CatSid() {
        return saveUsr046CatSid__;
    }

    /**
     * @param saveUsr046CatSid セットする saveUsr046CatSid
     */
    public void setSaveUsr046CatSid(int saveUsr046CatSid) {
        saveUsr046CatSid__ = saveUsr046CatSid;
    }


}
