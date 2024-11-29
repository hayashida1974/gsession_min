package jp.groupsession.v2.sml.sml130kn;

import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;

/**
 * <br>[機  能] ショートメール 個人設定 自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml130knBiz {

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
                                      Sml130knParamModel paramMdl)
        throws SQLException {

//        //セッション情報を取得
//        BaseUserModel usModel = reqMdl.getSmodel();
//        int sessionUsrSid = usModel.getUsrsid();
//        UDate nowDate = new UDate();

        //ショートメール自動削除設定
        SmlAdelModel delMdl = new SmlAdelModel();
//
//        delMdl.setSacSid(sessionUsrSid);
//        delMdl.setSadUsrKbn(GSConstSmail.SML_ADEL_USR_KBN_USER);
//        delMdl.setSadDelKbn(GSConstSmail.SML_DEL_KBN_USER_SETTING);
//
//        int jdelkbn =
//            NullDefault.getInt(
//                    paramMdl.getSml130JdelKbn(),
//                    GSConstSmail.SML_AUTO_DEL_NO);
//
//        delMdl.setSadJdelKbn(jdelkbn);
//
//        int jdelYear = 0;
//        int jdelMonth = 0;
//
//        if (jdelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//            jdelYear = NullDefault.getInt(paramMdl.getSml130JYear(), 0);
//            jdelMonth = NullDefault.getInt(paramMdl.getSml130JMonth(), 0);
//        }
//
//        delMdl.setSadJdelYear(jdelYear);
//        delMdl.setSadJdelMonth(jdelMonth);
//
//        int sdelkbn =
//            NullDefault.getInt(
//                    paramMdl.getSml130SdelKbn(),
//                    GSConstSmail.SML_AUTO_DEL_NO);
//
//        delMdl.setSadSdelKbn(sdelkbn);
//
//        int sdelYear = 0;
//        int sdelMonth = 0;
//
//        if (sdelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//            sdelYear = NullDefault.getInt(paramMdl.getSml130SYear(), 0);
//            sdelMonth = NullDefault.getInt(paramMdl.getSml130SMonth(), 0);
//        }
//
//        delMdl.setSadSdelYear(sdelYear);
//        delMdl.setSadSdelMonth(sdelMonth);
//
//        int wdelkbn =
//            NullDefault.getInt(
//                    paramMdl.getSml130WdelKbn(),
//                    GSConstSmail.SML_AUTO_DEL_NO);
//
//        delMdl.setSadWdelKbn(wdelkbn);
//
//        int wdelYear = 0;
//        int wdelMonth = 0;
//
//        if (wdelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//            wdelYear = NullDefault.getInt(paramMdl.getSml130WYear(), 0);
//            wdelMonth = NullDefault.getInt(paramMdl.getSml130WMonth(), 0);
//        }
//
//        delMdl.setSadWdelYear(wdelYear);
//        delMdl.setSadWdelMonth(wdelMonth);
//
//        int ddelkbn =
//            NullDefault.getInt(
//                    paramMdl.getSml130DdelKbn(),
//                    GSConstSmail.SML_AUTO_DEL_NO);
//
//        delMdl.setSadDdelKbn(ddelkbn);
//
//        int ddelYear = 0;
//        int ddelMonth = 0;
//
//        if (ddelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
//            ddelYear = NullDefault.getInt(paramMdl.getSml130DYear(), 0);
//            ddelMonth = NullDefault.getInt(paramMdl.getSml130DMonth(), 0);
//        }
//
//        delMdl.setSadDdelYear(ddelYear);
//        delMdl.setSadDdelMonth(ddelMonth);
//
//        delMdl.setSadAuid(sessionUsrSid);
//        delMdl.setSadAdate(nowDate);
//        delMdl.setSadEuid(sessionUsrSid);
//        delMdl.setSadEdate(nowDate);
//
//        SmlAdelDao delDao = new SmlAdelDao(con__);
//        if (delDao.update(delMdl) == 0) {
//            delDao.insert(delMdl);
//        }
        return delMdl;
    }
}