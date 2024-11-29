package jp.groupsession.v2.adr.adr200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Biz;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アドレス帳 ラベル登録ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr200Biz extends Adr010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr200Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr200Biz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr200ParamModel
     * @param userMdl セッションユーザ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Adr200ParamModel paramMdl,
            BaseUserModel userMdl) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstAddress.PLUGIN_ID_ADDRESS);
        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconfMdl = aconfDao.selectAconf();

        //セッションユーザが管理者、もしくは役職編集権限を設定していない場合、入力フォームと追加ボタンを表示
        if (adminUser || (aconfMdl == null || aconfMdl.getAacAlbEdit() == GSConstAddress.POW_ALL)) {
            paramMdl.setAdr200Admin(GSConstAddress.DSP_ELEMENT);
        }

        if (paramMdl.getAdr200selectCategory() <= 0) {
            paramMdl.setAdr200selectCategory(GSConstAddress.LABEL_CATEGORY_NOSET);
        }

        //カテゴリコンボを設定
        ArrayList<LabelValueBean> categoryList = new ArrayList<LabelValueBean>();
        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);
        List<AdrLabelCategoryModel> modelList = categoryDao.select();
        for (AdrLabelCategoryModel model : modelList) {
            String category = model.getAlcName();
            String value = String.valueOf(model.getAlcSid());
            categoryList.add(new LabelValueBean(category, value));
        }
        paramMdl.setAdr200category(categoryList);
    }

    /**
     * <br>[機  能] ラベル情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr200ParamModel
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     * @return labelCnt ラベル件数
     */
    public int entryLabelData(Connection con, Adr200ParamModel paramMdl, MlCountMtController mtCon,
                                int sessionUserSid)
    throws SQLException {

        log__.debug("START");


        UDate now = new UDate();

        AdrLabelModel labelMdl = new AdrLabelModel();
        labelMdl.setAlbAuid(sessionUserSid);
        labelMdl.setAlbAdate(now);
        labelMdl.setAlbEuid(sessionUserSid);
        labelMdl.setAlbEdate(now);
        labelMdl.setAlcSid(paramMdl.getAdr200selectCategory());

        AdrLabelDao labelDao = new AdrLabelDao(con);
        StringTokenizer labelToken = new StringTokenizer(paramMdl.getAdr200labelName(), ",");

        //登録ラベル件数
        int labelCnt = 0;

        while (labelToken.hasMoreTokens()) {
            String labelName = labelToken.nextToken().trim();

            if (labelName.length() > 0) {
                long albSid = mtCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                    GSConstAddress.SBNSID_SUB_LABEL,
                                                    sessionUserSid);
                labelMdl.setAlbSid((int) albSid);
                labelMdl.setAlbName(labelName);
                labelDao.insertNewLabel(labelMdl);
                labelCnt++;
            }
        }

        log__.debug("End");
        return labelCnt;

    }

    /**
     * <br>[機  能] カテゴリ名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param alcSid カテゴリSID
     * @return カテゴリ名
     * @throws SQLException SQL実行時例外
     */
    public String getCategoryName(Connection con, int alcSid) throws SQLException {

        AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel mdl = dao.select(alcSid);
        return mdl.getAlcName();
    }
}
