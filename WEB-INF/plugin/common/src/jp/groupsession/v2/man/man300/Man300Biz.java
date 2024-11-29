package jp.groupsession.v2.man.man300;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.base.CmnInfoUserDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoUserModel;

/**
 * <br>[機  能] メイン インフォメーション 管理者設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man300Biz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param cmd 操作種別パラメータ
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man300ParamModel paramMdl, Connection con,
                            RequestModel reqMdl, String cmd)
    throws SQLException {

        //初期表示
        if (cmd.equals("admconf")) {
            CmnInfoUserDao usrDao = new CmnInfoUserDao(con);
            ArrayList<CmnInfoUserModel> usrMdlList = null;
            ArrayList<String> list = new ArrayList<String>();
            usrMdlList = usrDao.select();

            for (int i = 0; i < usrMdlList.size(); i++) {
                if (usrMdlList.get(i).getUsrSid() != -1) {
                    list.add(String.valueOf(usrMdlList.get(i).getUsrSid()));
                }

                if (usrMdlList.get(i).getGrpSid() != -1) {
                    list.add(String.valueOf("G"
                                            + usrMdlList.get(i).getGrpSid()));
                }

            }
            String[] usrGrpSids = (String[]) list.toArray(new String[list.size()]);
            paramMdl.setMan300memberSid(usrGrpSids);
        }
    }
}
