package jp.groupsession.v2.adr.adr050;

import java.sql.Connection;

import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 個人設定メニュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr050Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr050Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr050ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Adr050ParamModel paramMdl,
            Connection con) throws Exception {

        log__.debug("setInitData START");

        //管理者設定取得
        AdrAconfDao dao = new AdrAconfDao(con);
        AdrAconfModel model = dao.selectAconf();
        if (model != null) {
            paramMdl.setAdr050initFlg(model.getAacVrmEdit());
        }
    }
}
