package jp.groupsession.v2.adr.adr040;

import java.sql.Connection;

import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr040Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr040Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr040ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Adr040ParamModel paramMdl,
            Connection con) throws Exception {

        log__.debug("setInitData START");

        //DB値取得
        AdrAconfDao dao = new AdrAconfDao(con);
        AdrAconfModel model = dao.selectAconf();

        if (model != null) {
            paramMdl.setAdr040Pow1(model.getAacAtiEdit());
            paramMdl.setAdr040Pow2(model.getAacAcoEdit());
            paramMdl.setAdr040Pow3(model.getAacAlbEdit());
            paramMdl.setAdr040Pow4(model.getAacExport());
            paramMdl.setAdr040Pow5(model.getAacYksEdit());
        }
    }
}
