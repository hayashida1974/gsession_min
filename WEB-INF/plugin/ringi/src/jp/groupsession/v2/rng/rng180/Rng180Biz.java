package jp.groupsession.v2.rng.rng180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RngAconfModel;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng180Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng180Biz.class);


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Rng180Biz() {
    }


    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(
            RequestModel reqMdl,
            Rng180ParamModel paramMdl,
            Connection con)  throws Exception {

        log__.debug("start");

        //稟議 申請ID一覧からタイトルを取得
        RngIdDao idDao = new RngIdDao(con);
        List<RingiIdModel> idMdl = new ArrayList<RingiIdModel>();
        idMdl = idDao.select();
        paramMdl.setRng180TempList(createComb(idMdl, reqMdl));

        int initFlg = paramMdl.getRng180initFlg();
        if (initFlg == RngConst.DSP_FIRST) {
            //稟議 管理者設定情報を取得
            RngBiz rngBiz = new RngBiz(con);
            RngAconfModel aconfMdl = rngBiz.getRngAconf(con);
            paramMdl.setRng180delKbn(aconfMdl.getRarDelAuth());
            paramMdl.setRng180HanyoFlg(aconfMdl.getRarHanyoFlg());
            paramMdl.setRng180TemplatePFlg(aconfMdl.getRarTemplatePersonalFlg());
            paramMdl.setRng180KeiroPFlg(aconfMdl.getRarKeiroPersonalFlg());
            paramMdl.setRng180initFlg(RngConst.DSP_ALREADY);

            //稟議 管理者設定から稟議削除区分を取得
            paramMdl.setRng180sinseiKbn(aconfMdl.getRarRngid());
            paramMdl.setRng180defaultId((aconfMdl.getRarRngidDefSid()));
            paramMdl.setRng180Overlap(aconfMdl.getRarOverlap());

        }
    }

    /**
     * <br>[機  能] コンボ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param idList 申請ID一覧
     * @param reqMdl リクエストモデル
     * @return カテゴリのコンボ一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> createComb(List<RingiIdModel> idList,
            RequestModel reqMdl)
        throws SQLException {

        ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();

        for (RingiIdModel model : idList) {
            String strName = model.getRngTitle();
            String strSid = Integer.toString(model.getRngSid());
            list.add(new LabelValueBean(strName, strSid));
        }
        return list;
    }
}
