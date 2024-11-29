package jp.groupsession.v2.adr.adr140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.adr130.Adr130ParamModel;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 ラベル登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr140ParamModel extends Adr130ParamModel {

    //入力項目
    /** ラベル名 */
    private String adr140albName__;
    /** カテゴリSID */
    private int adr140CatSid__ = -1;
    /** 備考 */
    private String adr140bikou__;

    /** カテゴリリスト */
    private ArrayList<LabelValueBean> adr140catList__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateAdr140(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //ラベル名
        AdrValidateUtil.validateTextField(errors,
                                          adr140albName__,
                                          "adr140albName",
                                          gsMsg.getMessage(req, "cmn.label.name"),
                                          30,
                                          true);
        //備考
        AdrValidateUtil.validateTextAreaField(errors,
                                              adr140bikou__,
                                             "adr140bikou",
                                              gsMsg.getMessage(req, "cmn.memo"),
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
    public ActionErrors deleteCheck(
            Connection con, RequestModel reqMdl) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        String fieldfix;

        //付加ラベル件数取得
        Adr140Biz biz = new Adr140Biz(reqMdl);
        int count = biz.getBelongCount(con, getAdr130EditAlbSid());
        if (count > 0) {
            //ラベル名取得
            AdrLabelDao albDao = new AdrLabelDao(con);
            AdrLabelModel albMdl = albDao.select(getAdr130EditAlbSid());
            //メッセージ作成
            fieldfix = "adr080EditAtiSid" + ".";
            String msgKey = "error.duplication.label";
            msg = new ActionMessage(msgKey,
                    StringUtilHtml.transToHTmlPlusAmparsant(albMdl.getAlbName()), count);
            StrutsUtil.addMessage(errors, msg, fieldfix + msgKey);
        }
        return errors;
    }

    /**
     * <p>adr140albName を取得します。
     * @return adr140albName
     */
    public String getAdr140albName() {
        return adr140albName__;
    }

    /**
     * <p>adr140albName をセットします。
     * @param adr140albName adr140albName
     */
    public void setAdr140albName(String adr140albName) {
        adr140albName__ = adr140albName;
    }

    /**
     * @return adr140CatSid
     */
    public int getAdr140CatSid() {
        return adr140CatSid__;
    }

    /**
     * @param adr140CatSid セットする adr140CatSid
     */
    public void setAdr140CatSid(int adr140CatSid) {
        adr140CatSid__ = adr140CatSid;
    }

    /**
     * <p>adr140bikou を取得します。
     * @return adr140bikou
     */
    public String getAdr140bikou() {
        return adr140bikou__;
    }

    /**
     * <p>adr140bikou をセットします。
     * @param adr140bikou adr140bikou
     */
    public void setAdr140bikou(String adr140bikou) {
        adr140bikou__ = adr140bikou;
    }

    /**
     * @return adr140catList
     */
    public ArrayList<LabelValueBean> getAdr140catList() {
        return adr140catList__;
    }

    /**
     * @param adr140catList セットする adr140catList
     */
    public void setAdr140catList(ArrayList<LabelValueBean> adr140catList) {
        adr140catList__ = adr140catList;
    }
}
