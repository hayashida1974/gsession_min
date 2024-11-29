package jp.groupsession.v2.sml.sml280;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウント基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml280Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml280Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                              Sml280ParamModel paramMdl,
                              RequestModel reqMdl) throws SQLException {
        log__.debug("START");


        //初期表示かどうか
        if (paramMdl.getSml280initFlg() == GSConstSmail.DSP_FIRST) {

            SmlCommonBiz smlBiz = new SmlCommonBiz();
            SmlAdminModel sacMdl = new SmlAdminModel();
            sacMdl = smlBiz.getSmailAdminConf(0, con);

            //データをセットする
            paramMdl.setSml280acntMakeKbn(sacMdl.getSmaAcntMake());
            paramMdl.setSml280autoDelKbn(sacMdl.getSmaAutoDelKbn());
            paramMdl.setSml280acntUser(sacMdl.getSmaAcntUser());

            paramMdl.setSml280initFlg(GSConstSmail.DSP_ALREADY);
        }

        log__.debug("END");
    }

}
