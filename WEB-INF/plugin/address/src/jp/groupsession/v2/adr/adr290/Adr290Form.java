package jp.groupsession.v2.adr.adr290;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.adr280.Adr280Form;
import jp.groupsession.v2.adr.dao.AdrBelongLabelDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrBelongLabelModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


/**
 * <br>[機  能] アドレス帳 カテゴリ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr290Form extends Adr280Form {


    //入力項目
    /** カテゴリ名 */
    private String adr290CategoryName__;
    /** 備考 */
    private String adr290bikou__;
    /** 状態フラグ(カテゴリ内のラベル有無) */
    private int catKbn__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateAdr290(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //カテゴリ名
        AdrValidateUtil.validateTextField(errors,
                                          adr290CategoryName__,
                                          "adr290CategoryName",
                                          gsMsg.getMessage(req, "cmn.label.name"),
                                          20,
                                          true);
        //備考
        AdrValidateUtil.validateTextAreaField(errors,
                                              adr290bikou__,
                                             "adr290bikou",
                                             gsMsg.getMessage(req, "cmn.memo"),
                                              300,
                                              false);
        return errors;
    }


    /**
     * <p>adr290CategoryName を取得します。
     * @return adr290CategoryName
     */
    public String getAdr290CategoryName() {
        return adr290CategoryName__;
    }

    /**
     * <p>adr290CategoryName をセットします。
     * @param adr290CategoryName adr290CategoryName
     */
    public void setAdr290CategoryName(String adr290CategoryName) {
        adr290CategoryName__ = adr290CategoryName;
    }

    /**
     * <p>adr290bikou を取得します。
     * @return adr290bikou
     */
    public String getAdr290bikou() {
        return adr290bikou__;
    }

    /**
     * <p>adr290bikou をセットします。
     * @param adr290bikou adr290bikou
     */
    public void setAdr290bikou(String adr290bikou) {
        adr290bikou__ = adr290bikou;
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

        AdrBelongLabelDao dao = new AdrBelongLabelDao(con);
        //アドレス帳に付加されているラベルSID一覧取得
        ArrayList<AdrBelongLabelModel> modelList = dao.getCountLabBelongCat(getAdr280EditSid());

        //アドレス帳に付加ラベルなし
        if (modelList == null) {
            return errors;
        }

        AdrLabelDao labDao = new AdrLabelDao(con);
        for (AdrBelongLabelModel model : modelList) {
            AdrLabelModel labModel = labDao.select(model.getAlbSid());
            fieldfix = "labBelongCat" + ".";
            String msgKey = "error.user.duplication.label.incategory";
            msg = new ActionMessage(msgKey,
                    StringUtilHtml.transToHTmlPlusAmparsant(getAdr290CategoryName()),
                    StringUtilHtml.transToHTmlPlusAmparsant(labModel.getAlbName()),
                    model.getCount());
            errors.add(fieldfix, msg);
        }
        return errors;
    }


}
