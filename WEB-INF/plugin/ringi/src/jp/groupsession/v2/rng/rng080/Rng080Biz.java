package jp.groupsession.v2.rng.rng080;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngAconfModel;

/**
 * <br>[機  能] 稟議 管理者設定 ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng080Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng080Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Rng080Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp085ParamModel
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Rng080ParamModel paramMdl) throws Exception {
        log__.debug("setInitData START");
        RngBiz biz = new RngBiz(con__);
        RngAconfModel mdl = biz.getRngAconf(con__);
        if (mdl != null) {
            if (paramMdl.getRng080SmlFlg() == GSConst.PLUGIN_USE) {
                if (mdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_USER) {
                    paramMdl.setRng080SmlFlg(GSConst.PLUGIN_USE);
                } else if (mdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN) {
                    paramMdl.setRng080SmlFlg(GSConst.PLUGIN_NOT_USE);
                }
            }
            if (mdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
                paramMdl.setRng080TemplatePersonalFlg(RngConst.RAR_HANYO_FLG_NO);
            } else {
                paramMdl.setRng080TemplatePersonalFlg(mdl.getRarTemplatePersonalFlg());
            }
            paramMdl.setRng080KeiroPersonalFlg(mdl.getRarKeiroPersonalFlg());
        }
    }
}
