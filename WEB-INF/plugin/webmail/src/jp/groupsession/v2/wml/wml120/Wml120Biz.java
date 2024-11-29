package jp.groupsession.v2.wml.wml120;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

/**
 * <br>[機  能] WEBメール ラベル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml120Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                          Wml120ParamModel paramMdl) throws SQLException {

        //アカウント名取得
        WmlAccountDao waDao = new WmlAccountDao(con);
        WmlAccountModel waMdl = new WmlAccountModel();
        waMdl = waDao.select(paramMdl.getWml110accountSid());
        paramMdl.setWml110account(waMdl.getWacName());

        //初期表示　編集
        if (paramMdl.getWml120initKbn() == GSConstWebmail.DSP_FIRST
        && paramMdl.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_EDIT) {

            //ラベル名設定
            int wlbSid = paramMdl.getWmlEditLabelId();
            WmlLabelDao labelDao = new WmlLabelDao(con);
            WmlLabelModel labelData = labelDao.select(wlbSid);
            paramMdl.setWml120LabelName(labelData.getWlbName());

            //初期表示完了
            paramMdl.setWml120initKbn(GSConstWebmail.DSP_ALREADY);
        }
    }
}
