package jp.groupsession.v2.cmn.cmn210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;

/**
 * <br>[機  能] グループ選択ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn210Biz {
    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn210Model パラメータ、出力値を保持するModel
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     * @throws Exception 実行例外
     */
    public void setInitData(
            Cmn210ParamModel cmn210Model, Connection con, int sessionUsrSid)
    throws SQLException, Exception {
        //表示グループ取得
        GroupBiz grpBiz = new GroupBiz();
        ArrayList<GroupModel> tree = grpBiz.getGroupTree(con);

        //マイグループ取得
        if (cmn210Model.getMyGroupFlg() == 1) {
            //ユーザSIDからマイグループ情報を取得する
            CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
            List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(sessionUsrSid);

            //マイグループリストをセット
            ArrayList<CmnLabelValueModel> dspGrpList = new ArrayList<CmnLabelValueModel>();
            for (CmnMyGroupModel cmgMdl : cmgList) {
                dspGrpList.add(
                        new CmnLabelValueModel(cmgMdl.getMgpName(),
                                "M" + String.valueOf(cmgMdl.getMgpSid()), "1"));
            }
            cmn210Model.setMyGroupList(dspGrpList);
        }

        //グループ制限を取得
        List<String> banGrpList = new ArrayList<String>();
        List<Integer> grpDisabledKbnList = new ArrayList<Integer>();
        if (cmn210Model.getCmn210disableGroupSid() != null) {
            banGrpList.addAll(Arrays.asList(cmn210Model.getCmn210disableGroupSid()));
        }

        for (GroupModel grp:tree) {
            if (cmn210Model.getCmn210disableGroupFlg() == Cmn210Form.DISABLE_GROUP_FLG_ON_
            && banGrpList.contains(String.valueOf(grp.getGroupSid()))) {
                grpDisabledKbnList.add(1);
            } else {
                grpDisabledKbnList.add(0);
            }
        }
        cmn210Model.setGroupDisabledKbnList(grpDisabledKbnList);
        cmn210Model.setGroupList(tree);
    }
}
