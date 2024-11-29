package jp.groupsession.v2.sml.sml300;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;



/**
 * <br>[機  能] ショートメール 管理者設定 ラベル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml300Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                          Sml300ParamModel paramMdl) throws SQLException {

        //アカウント名取得
        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = sacDao.select(paramMdl.getSmlAccountSid());
        paramMdl.setSml290accountName(sacMdl.getSacName());

        //初期表示　編集
        if (paramMdl.getSml300initKbn() == GSConstSmail.DSP_FIRST
        && paramMdl.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_EDIT) {

            //ラベル名設定
            int slbSid = paramMdl.getSmlEditLabelId();
            SmlLabelDao labelDao = new SmlLabelDao(con);
            SmlLabelModel labelData = labelDao.select(slbSid);
            paramMdl.setSml300LabelName(labelData.getSlbName());

            //初期表示完了
            paramMdl.setSml300initKbn(GSConstSmail.DSP_ALREADY);
        }
    }
}
