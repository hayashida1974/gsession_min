package jp.groupsession.v2.adr.adr260;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.adr010.Adr010Biz;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 ラベル選択のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr260Biz extends Adr010Biz {
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr260Biz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr260ParamModel
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Adr260ParamModel paramMdl, Connection con, int sessionUserSid)
    throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        //カテゴリコンボを設定
        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);
        ArrayList<LabelValueBean> categoryList = new ArrayList<LabelValueBean>();
        categoryList.add(new LabelValueBean(gsMsg.getMessage("cmn.all"), "-1"));

        List<AdrLabelCategoryModel> catMdlList = categoryDao.select();

        for (AdrLabelCategoryModel model : catMdlList) {
            String category = model.getAlcName();
            String value = String.valueOf(model.getAlcSid());
            categoryList.add(new LabelValueBean(category, value));
        }
        paramMdl.setCategoryList(categoryList);

        //ラベルを設定
        AdrLabelDao labelDao = new AdrLabelDao(con);
        if (paramMdl.getSelectCategory() == -1) {
            paramMdl.setLabelList(labelDao.select());
        } else {
            paramMdl.setLabelList(labelDao.getLabelInCategory(paramMdl.getSelectCategory()));
        }
    }

}
