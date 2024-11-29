package jp.groupsession.v2.bbs.main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 掲示板(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsMainBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param buMdl ユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(BbsMainParamModel paramMdl, Connection con, BaseUserModel buMdl)
    throws Exception {
        log__.debug("START");

        //メイン画面スレッド表示件数を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bbsUsrMdl = bbsBiz.getBbsUserData(con, buMdl.getUsrsid());

        //メンバー情報を保持するフォーラムのリスト
        List<Integer> memberForumList = new ArrayList<Integer>();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, buMdl.getUsrsid());
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            memberForumList.add(bfiMdl.getBfiSid());
        }

        //スレッド一覧を設定
        BulletinDao bbsDao = new BulletinDao(con);
        paramMdl.setThreadList(bbsDao.getThreadList(buMdl.getUsrsid(),
                false,
                bbsUsrMdl.getBurThreMainCnt(),
                bbsUsrMdl.getBurNewCnt(),
                bbsUsrMdl.getBurMainChkedDsp(),
                memberForumList
                ));

        log__.debug("End");
    }

}
