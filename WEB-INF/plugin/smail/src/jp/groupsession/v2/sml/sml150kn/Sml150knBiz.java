package jp.groupsession.v2.sml.sml150kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.model.SmlAdelModel;

/**
 * <br>[機  能] ショートメール 管理者設定 自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml150knBiz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Sml150knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 自動削除設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @return delMdl SmlAdelModel
     * @throws SQLException SQL実行例外
     */
    public SmlAdelModel updateAuteDelSetting(RequestModel reqMdl,
                                      Sml150knParamModel paramMdl)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        //ショートメール自動削除設定
        SmlAdelModel delMdl = new SmlAdelModel();

        delMdl.setSacSid(GSConstSmail.SML_ADEL_USR_KBN_ADM);
        delMdl.setSadUsrKbn(GSConstSmail.SML_ADEL_USR_KBN_ADM);
        delMdl.setSadDelKbn(
                NullDefault.getInt(
                        paramMdl.getSml150DelKbn(),
                        GSConstSmail.SML_ADEL_USR_KBN_ADM));

        int jdelKbn = 0;
        int jdelYear = 0;
        int jdelMonth = 0;
        int sdelKbn = 0;
        int sdelYear = 0;
        int sdelMonth = 0;
        int wdelKbn = 0;
        int wdelYear = 0;
        int wdelMonth = 0;
        int ddelKbn = 0;
        int ddelYear = 0;
        int ddelMonth = 0;

//        //自動削除 = 管理者が設定する
//        if (delMdl.getSadDelKbn() == GSConstSmail.SML_DEL_KBN_ADM_SETTING) {

            jdelKbn =
                NullDefault.getInt(
                        paramMdl.getSml150JdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            if (jdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                jdelYear = NullDefault.getInt(paramMdl.getSml150JYear(), 0);
                jdelMonth = NullDefault.getInt(paramMdl.getSml150JMonth(), 0);
            }

            sdelKbn =
                NullDefault.getInt(
                        paramMdl.getSml150SdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            if (sdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                sdelYear = NullDefault.getInt(paramMdl.getSml150SYear(), 0);
                sdelMonth = NullDefault.getInt(paramMdl.getSml150SMonth(), 0);
            }

            wdelKbn =
                NullDefault.getInt(
                        paramMdl.getSml150WdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            if (wdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                wdelYear = NullDefault.getInt(paramMdl.getSml150WYear(), 0);
                wdelMonth = NullDefault.getInt(paramMdl.getSml150WMonth(), 0);
            }

            ddelKbn =
                NullDefault.getInt(
                        paramMdl.getSml150DdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            if (ddelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                ddelYear = NullDefault.getInt(paramMdl.getSml150DYear(), 0);
                ddelMonth = NullDefault.getInt(paramMdl.getSml150DMonth(), 0);
            }
//        }

        delMdl.setSadJdelKbn(jdelKbn);
        delMdl.setSadJdelYear(jdelYear);
        delMdl.setSadJdelMonth(jdelMonth);
        delMdl.setSadSdelKbn(sdelKbn);
        delMdl.setSadSdelYear(sdelYear);
        delMdl.setSadSdelMonth(sdelMonth);
        delMdl.setSadWdelKbn(wdelKbn);
        delMdl.setSadWdelYear(wdelYear);
        delMdl.setSadWdelMonth(wdelMonth);
        delMdl.setSadDdelKbn(ddelKbn);
        delMdl.setSadDdelYear(ddelYear);
        delMdl.setSadDdelMonth(ddelMonth);

        delMdl.setSadAuid(sessionUsrSid);
        delMdl.setSadAdate(nowDate);
        delMdl.setSadEuid(sessionUsrSid);
        delMdl.setSadEdate(nowDate);

        SmlAdelDao delDao = new SmlAdelDao(con__);
        if (delDao.update(delMdl) == 0) {
            delDao.insert(delMdl);
        }
        return delMdl;
    }
    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel Sml150knParamModel
     */
    public void updateEgnoreYearMonth(Sml150knParamModel paramModel) {
        if (!ValidateUtil.isNumber(paramModel.getSml150JYear())) {
            paramModel.setSml150JYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150JMonth())) {
            paramModel.setSml150JMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150SYear())) {
            paramModel.setSml150SYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150SMonth())) {
            paramModel.setSml150SMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150WYear())) {
            paramModel.setSml150WYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150WMonth())) {
            paramModel.setSml150WMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150DYear())) {
            paramModel.setSml150DYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml150DMonth())) {
            paramModel.setSml150DMonth("");
        }
    }
}