package jp.groupsession.v2.usr.usr200;

import java.sql.Connection;
import java.util.ArrayList;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ラベル選択のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr200Biz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr200Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr200ParamModel
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @param catSid カテゴリSID
     * @throws Exception 実行例外
     */
    public void setInitData(
            Usr200ParamModel paramMdl, Connection con, int sessionUserSid, int catSid)
    throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //全て
        String textAll = gsMsg.getMessage("cmn.all");
        //カテゴリコンボを設定
        CmnLabelUsrCategoryDao categoryDao = new CmnLabelUsrCategoryDao(con);
        ArrayList<LabelValueBean> categoryList = new ArrayList<LabelValueBean>();
        categoryList.add(new LabelValueBean(textAll, "-1"));

        ArrayList<CmnLabelUsrCategoryModel> catMdlList = categoryDao.select();

        for (int i = 0; i < catMdlList.size(); i++) {
            CmnLabelUsrCategoryModel model = new CmnLabelUsrCategoryModel();
            model = catMdlList.get(i);
            categoryList.add(
                    new LabelValueBean(model.getLucName(), String.valueOf(model.getLucSid())));
        }

        paramMdl.setCategoryList(categoryList);

        //ラベルを設定
        CmnLabelUsrDao labelDao = new CmnLabelUsrDao(con);
        if (catSid == -1) {
            paramMdl.setLabelList(labelDao.select());
        } else {
            paramMdl.setLabelList(labelDao.select(catSid));
        }
    }
}