package jp.groupsession.v2.adr.adr190;

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
public class Adr190Biz extends Adr010Biz {
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr190Biz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Adr190Form form,
                             Connection con,
                             int sessionUserSid)
    throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        //カテゴリコンボを設定
        ArrayList<LabelValueBean> categoryList = new ArrayList<LabelValueBean>();
        categoryList.add(new LabelValueBean(gsMsg.getMessage("cmn.all"), "-1"));

        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);
        List<AdrLabelCategoryModel> modelList = categoryDao.select();
        for (AdrLabelCategoryModel model : modelList) {
            String category = model.getAlcName();
            String value = String.valueOf(model.getAlcSid());
            categoryList.add(new LabelValueBean(category, value));
        }
        form.setAdr190category(categoryList);

        //ラベルを設定
        AdrLabelDao labelDao = new AdrLabelDao(con);
        if (form.getAdr190selectCategory() == -1) {
            form.setLabelList(labelDao.select());
        } else {
            form.setLabelList(labelDao.getLabelInCategory(form.getAdr190selectCategory()));
        }

        form.setAdr190selectLabel(form.getAdr190selectLabel());
    }
}
