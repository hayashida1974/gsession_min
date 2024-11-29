package jp.groupsession.v2.rng.rng180kn;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngAconfDao;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RngAconfModel;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng180knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng180knBiz.class);

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
            Rng180knParamModel paramMdl,
            Connection con)  throws Exception {

        log__.debug("start");

        //稟議 申請ID一覧からタイトルを取得
        RngIdDao idDao = new RngIdDao(con);
        RingiIdModel rngIdMdl = idDao.selectData(paramMdl.getRng180defaultId());
        paramMdl.setRsv180knDefaultIdDsp(rngIdMdl.getRngTitle());
    }

    /**
     * <br>[機  能] 設定値をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void entryAdmConf(
            Connection con,
            Rng180knParamModel paramMdl,
            int userSid) throws Exception {

        UDate now = new UDate();
        RngAconfModel aconfMdl = new RngAconfModel();
        aconfMdl.initData();
        aconfMdl.setRarAuid(userSid);
        aconfMdl.setRarAdate(now);
        aconfMdl.setRarEuid(userSid);
        aconfMdl.setRarEdate(now);

        if (paramMdl.getRng180delKbn() == RngConst.RAR_DEL_AUTH_ADM) {
            aconfMdl.setRarDelAuth(RngConst.RAR_DEL_AUTH_ADM);
        } else {
            aconfMdl.setRarDelAuth(RngConst.RAR_DEL_AUTH_UNRESTRICTED);
        }

        if (paramMdl.getRng180HanyoFlg() == RngConst.RAR_HANYO_FLG_YES) {
            aconfMdl.setRarHanyoFlg(RngConst.RAR_HANYO_FLG_YES);
        } else {
            aconfMdl.setRarHanyoFlg(RngConst.RAR_HANYO_FLG_NO);
        }

        if (paramMdl.getRng180TemplatePFlg() == RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES) {
            aconfMdl.setRarTemplatePersonalFlg(RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES);
        } else {
            aconfMdl.setRarTemplatePersonalFlg(RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO);
        }

        if (paramMdl.getRng180KeiroPFlg() == RngConst.RAR_KEIRO_PERSONAL_FLG_YES) {
            aconfMdl.setRarKeiroPersonalFlg(RngConst.RAR_KEIRO_PERSONAL_FLG_YES);
        } else {
            aconfMdl.setRarKeiroPersonalFlg(RngConst.RAR_KEIRO_PERSONAL_FLG_NO);
        }
        // 申請ID設定
        aconfMdl.setRarRngid(paramMdl.getRng180sinseiKbn());
        aconfMdl.setRarRngidDefSid(paramMdl.getRng180defaultId());
        aconfMdl.setRarOverlap(paramMdl.getRng180Overlap());

        RngAconfDao aconfDao = new RngAconfDao(con);
        if (aconfDao.updateDeleteAuth(aconfMdl) <= 0) {
            aconfDao.insert(aconfMdl);
        }
    }

}
