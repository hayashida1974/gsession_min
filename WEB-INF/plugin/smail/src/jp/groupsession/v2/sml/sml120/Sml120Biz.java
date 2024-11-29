package jp.groupsession.v2.sml.sml120;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.model.SmlAdminModel;

/**
 * <br>[機  能] ショートメール 個人設定メニュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml120Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml120ParamModel paramMdl, Connection con)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        SmlCommonBiz cmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel admConf = cmnBiz.getSmailAdminConf(sessionUsrSid, con);

        //転送管理者設定
        paramMdl.setSml120MailFwAdminConf(admConf.getSmaMailfw());

        //管理者設定の表示設定
        int dspStype = GSConstSmail.DISP_CONF_USER;
        if (admConf.getSmaMaxDspStype() == GSConstSmail.DISP_CONF_ADMIN
                && admConf.getSmaReloadDspStype() == GSConstSmail.DISP_CONF_ADMIN
                && admConf.getSmaPhotoDspStype() == GSConstSmail.DISP_CONF_ADMIN
                && admConf.getSmaAttachDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //「管理者が設定する」のみの場合
            dspStype = GSConstSmail.DISP_CONF_ADMIN;
        }
        paramMdl.setSml120DispAdminConf(dspStype);

        //自動削除設定
        paramMdl.setSml120MailDelAdminConf(admConf.getSmaAutoDelKbn());

    }
}