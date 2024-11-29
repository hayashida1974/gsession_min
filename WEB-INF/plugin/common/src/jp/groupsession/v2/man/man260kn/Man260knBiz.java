package jp.groupsession.v2.man.man260kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnLogDelConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogDelConfModel;
import jp.groupsession.v2.man.GSConstMain;


/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man260knBiz {

    /**
     * <br>[機  能] 自動削除設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void updateAuteDelSetting(Connection con, RequestModel reqMdl,
                                      Man260knParamModel paramMdl)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        //オペレーションログ自動削除設定
        CmnLogDelConfModel delMdl = new CmnLogDelConfModel();
        int jdelkbn =
            NullDefault.getInt(
                    paramMdl.getMan260BatchKbn(),
                    GSConstMain.OPE_LOG_NOTCONF);

        delMdl.setLdcAdlKbn(jdelkbn);

        int jdelYear = 0;
        int jdelMonth = 0;

        if (jdelkbn == GSConstMain.OPE_LOG_CONF) {
            jdelYear = NullDefault.getInt(paramMdl.getMan260Year(), 0);
            jdelMonth = NullDefault.getInt(paramMdl.getMan260Month(), 0);
        }

        delMdl.setLdcAdlYear(jdelYear);
        delMdl.setLdcAdlMonth(jdelMonth);


        delMdl.setLdcAuid(sessionUsrSid);
        delMdl.setLdcAdate(nowDate);
        delMdl.setLdcEuid(sessionUsrSid);
        delMdl.setLdcEdate(nowDate);

        CmnLogDelConfDao delDao = new CmnLogDelConfDao(con);
        if (delDao.updateOpLogDel(delMdl) == 0) {
            delDao.insert(delMdl);
        }
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setInitData(Man260knParamModel paramMdl) {
        paramMdl.setMan260Year(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getMan260Year()));
        paramMdl.setMan260Month(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getMan260Month()));
    }
}
