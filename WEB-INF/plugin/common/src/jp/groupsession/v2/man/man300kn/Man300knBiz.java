package jp.groupsession.v2.man.man300kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.base.CmnInfoUserDao;
import jp.groupsession.v2.cmn.model.base.CmnInfoUserModel;

/**
 * <br>[機  能] メイン インフォメーション 管理者設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man300knBiz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man300knParamModel paramMdl, Connection con)
    throws SQLException {

        //公開対象生成
        String[] leftFull = paramMdl.getMan300memberSid();
        UserGroupSelectBiz usrSelBiz = new UserGroupSelectBiz();
        paramMdl.setMan300knKoukaiList(usrSelBiz.getSelectedLabel(leftFull, con));

        //シングルコーテーションをエスケープする
        paramMdl.setCmd(StringUtil.toSingleCortationEscape(paramMdl.getCmd()));
    }

    /**
     * <br>[機  能] 管理者設定を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void update(Man300knParamModel paramMdl, Connection con) throws SQLException {
        CmnInfoUserDao usrDao = new CmnInfoUserDao(con);
        CmnInfoUserModel usrMdl = null;
        String[] left = paramMdl.getMan300memberSid();
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<Integer> usrSids = new ArrayList<Integer>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(new Integer(str));
                }
            }
        }
        //事前削除
        usrDao.delete();
        //公開グループを登録
        for (Integer gSid : grpSids) {
            usrMdl = new CmnInfoUserModel();
            usrMdl.setUsrSid(-1);
            usrMdl.setGrpSid(gSid);
            usrDao.insert(usrMdl);
        }
        //公開ユーザを登録
        for (Integer uSid : usrSids) {
            usrMdl = new CmnInfoUserModel();
            usrMdl.setUsrSid(uSid);
            usrMdl.setGrpSid(-1);
            usrDao.insert(usrMdl);
        }

    }

}
