package jp.groupsession.v2.usr.usr045;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.usr043.Usr043ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] ユーザ情報 カテゴリ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr045ParamModel extends Usr043ParamModel {


    //入力項目
    /** カテゴリ名 */
    private String usr045CategoryName__;
    /** 備考 */
    private String usr045bikou__;
    /** 状態フラグ(カテゴリ内のラベル有無) */
    private int catKbn__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateUsr045(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //ラベル名
        String textLabelName = gsMsg.getMessage(req, "cmn.category.name");
        //備考
        String textBiko = gsMsg.getMessage(req, "cmn.memo");
        //カテゴリ名
        UserUtil.validateTextField(errors,
                                           usr045CategoryName__,
                                          "usr045bikou",
                                          textLabelName,
                                          20,
                                          true);
        //備考
        UserUtil.validateTextAreaField(errors,
                                              usr045bikou__,
                                             "usr045bikou",
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
     * @return エラー
     * @throws SQLException SQL例外発生
     */
    public ActionErrors deleteCheck(Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String fieldfix;

        CmnUsrmLabelDao dao = new CmnUsrmLabelDao(con);
        //ユーザ情報に付加されているラベルSID一覧取得
        ArrayList<CmnUsrmLabelModel> modelList = dao.getCountLabBelongCat(getUsr043EditSid());

        //ユーザ情報に付加ラベルなし
        if (modelList == null) {
            return errors;
        }

        CmnLabelUsrDao labDao = new CmnLabelUsrDao(con);
        for (CmnUsrmLabelModel model : modelList) {
            CmnLabelUsrModel labModel = labDao.selectOneLabel(model.getUsrSid());
            fieldfix = "labBelongCat" + ".";
            String msgKey = "error.user.duplication.label.incategory";
            msg = new ActionMessage(msgKey,
                    StringUtilHtml.transToHTmlPlusAmparsant(getUsr045CategoryName()),
                    StringUtilHtml.transToHTmlPlusAmparsant(labModel.getLabName()),
                    model.getCount());
            errors.add(fieldfix, msg);
        }




        return errors;
    }

    /**
     * <p>usr045CategoryName を取得します。
     * @return usr045CategoryName
     */
    public String getUsr045CategoryName() {
        return usr045CategoryName__;
    }

    /**
     * <p>usr045CategoryName をセットします。
     * @param usr045CategoryName usr045CategoryName
     */
    public void setUsr045CategoryName(String usr045CategoryName) {
        usr045CategoryName__ = usr045CategoryName;
    }

    /**
     * <p>usr045bikou を取得します。
     * @return usr045bikou
     */
    public String getUsr045bikou() {
        return usr045bikou__;
    }

    /**
     * <p>usr045bikou をセットします。
     * @param usr045bikou usr045bikou
     */
    public void setUsr045bikou(String usr045bikou) {
        usr045bikou__ = usr045bikou;
    }

    /**
     * <p>catKbn を取得します。
     * @return catKbn
     */
    public int getCatKbn() {
        return catKbn__;
    }

    /**
     * <p>catKbn をセットします。
     * @param catKbn catKbn
     */
    public void setCatKbn(int catKbn) {
        catKbn__ = catKbn;
    }

}
