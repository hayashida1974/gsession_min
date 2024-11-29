package jp.groupsession.v2.rng.rng210kn;

import java.sql.Connection;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;

/**
 * <br>[機  能] 稟議 管理者設定 申請フォーマット登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng210knBiz {

    /**
     * <br>[機  能] 申請フォーマットをDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param cntCon コントローラ
     * @throws Exception 実行例外
     */
    public void idEntry(
            Connection con,
            Rng210knParamModel paramMdl,
            int userSid,
            MlCountMtController cntCon) throws Exception {

        RingiIdModel mdl = new RingiIdModel();
        RngIdDao dao = new RngIdDao(con);
        UDate now = new UDate();
        mdl.setRngFormat(paramMdl.getRng210Format());
        mdl.setRngInit(Integer.parseInt(paramMdl.getRng210Init()));
        mdl.setRngManual(paramMdl.getRng210Manual());
        mdl.setRngReset(paramMdl.getRng210Reset());
        mdl.setRngZeroume(Integer.parseInt(paramMdl.getRng210Zeroume()));
        mdl.setRngTitle(paramMdl.getRng210Title());
        mdl.setRidUseDate(now);
        //追加
        if (paramMdl.getRng200Sid() == 0) {
            int newId = (int) cntCon.getSaibanNumber("ringi", "sinseisid", userSid);
            mdl.setRngSid(newId);
            dao.insert(mdl);
        } else {
            //編集
            mdl.setRngSid(paramMdl.getRng200Sid());
            dao.updateEntry(mdl);
        }
    }
}
