package jp.groupsession.v2.usr.usr081;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.dao.UsrAconfDao;
import jp.groupsession.v2.usr.model.UsrAconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ユーザ情報 エクスポート制限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr081Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr081Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr081Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr081ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Usr081ParamModel paramMdl,
                            Connection con) throws SQLException {
        //DBより現在の設定を取得する。(なければデフォルト)
        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        UsrAconfModel conf = biz.getAConfModel(con);
        paramMdl.setUsr081CsvExp(conf.getUadExport());

    }

    /**
     * <br>[機  能] 基本設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr081ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setBasicConfig(Usr081ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //既存のデータを取得
        //DBより現在の設定を取得する。(なければデフォルト)
        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        UsrAconfModel conf = biz.getAConfModel(con);
        //データを設定
        conf.setUadExport(paramMdl.getUsr081CsvExp());
        conf.setUadEuid(umodel.getUsrsid());
        UDate now = new UDate();
        conf.setUadEdate(now);
        //DB更新
        boolean commitFlg = false;
        try {
            UsrAconfDao dao = new UsrAconfDao(con);
            int count = dao.updateBasicConfig(conf);
            if (count <= 0) {
                conf.setUadAuid(umodel.getUsrsid());
                conf.setUadAdate(now);
                conf.setUadQrKbn(GSConstUser.QR_CODE_DISP);
                dao.insert(conf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("職員情報基本設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}
