package jp.groupsession.v2.usr.usr083;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrAdmSortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ユーザ情報 管理者設定 権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr083Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr083Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr083Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr083ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Usr083ParamModel paramMdl,
            Connection con) throws SQLException {

        if (paramMdl.getUsr083initKbn() == 0) {
            //DBより現在の設定を取得する。(なければデフォルト)
            UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
            CmnLabelUsrConfModel labelConf = biz.getLabelConfModel(con);

            paramMdl.setUsr083Pow1(labelConf.getLufEdit());
            paramMdl.setUsr083Pow2(labelConf.getLufSet());

            //初期表示区分を更新
            paramMdl.setUsr083initKbn(1);
        }
    }

    /**
     * <br>[機  能] 管理者ソート設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr083ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setSortAdmConfig(Usr083ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより現在の設定を取得する。(なければデフォルト)
        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        CmnUsrAdmSortConfModel sortAdmconf = biz.getSortAdmConfModel(con);

        //データを設定
        sortAdmconf.setUasSortKbn(paramMdl.getUsr083DefoDspKbn());
        sortAdmconf.setUasEuid(umodel.getUsrsid());
        UDate now = new UDate();
        sortAdmconf.setUasEdate(now);

        //DB更新
        boolean commitFlg = false;
        try {
            CmnUsrAdmSortConfDao dao = new CmnUsrAdmSortConfDao(con);
            int count = dao.updateSortConfig(sortAdmconf);
            if (count <= 0) {
                sortAdmconf.setUasAuid(umodel.getUsrsid());
                sortAdmconf.setUasAdate(now);
                dao.insert(sortAdmconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("管理者ソート設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}
