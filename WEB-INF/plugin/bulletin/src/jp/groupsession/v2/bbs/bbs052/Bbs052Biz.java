package jp.groupsession.v2.bbs.bbs052;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BbsUserDao;
import jp.groupsession.v2.bbs.model.BbsUserModel;

/**
 * <br>[機  能] 掲示板 ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs052Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs052Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Bbs052ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {
        log__.debug("START");

        //掲示板個人情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        paramMdl.setBbs052smlNtf(bUserMdl.getBurSmlNtf());

        log__.debug("End");
    }

    /**
     * <br>[機  能] 掲示板個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void updateBbsUserConf(Bbs052ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {
        log__.debug("START");

        UDate now = new UDate();
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        int forumCnt = bUserMdl.getBurForCnt();
        int threCnt = bUserMdl.getBurThreCnt();
        int writeCnt = bUserMdl.getBurWrtCnt();
        int newCnt = bUserMdl.getBurNewCnt();
        int smlNtf = paramMdl.getBbs052smlNtf();
        int threMainCnt = bUserMdl.getBurThreMainCnt();
        int wrtListOrder = bUserMdl.getBurWrtlistOrder();
        int imageDsp = bUserMdl.getBurThreImage();
        int chkedDsp = bUserMdl.getBurMainChkedDsp();
        int newThredFlg = bUserMdl.getBurSubNewThre();
        int forumIchiranFlg = bUserMdl.getBurSubForum();
        int uncheckThredFlg = bUserMdl.getBurSubUnchkThre();

        int iniPostType = bUserMdl.getBurIniPostType();

        BbsUserModel bbsUserMdl = new BbsUserModel();
        bbsUserMdl.setUsrSid(userSid);
        bbsUserMdl.setBurForCnt(forumCnt);
        bbsUserMdl.setBurThreCnt(threCnt);
        bbsUserMdl.setBurWrtCnt(writeCnt);
        bbsUserMdl.setBurNewCnt(newCnt);
        bbsUserMdl.setBurSmlNtf(smlNtf);
        bbsUserMdl.setBurThreMainCnt(threMainCnt);
        bbsUserMdl.setBurWrtlistOrder(wrtListOrder);
        bbsUserMdl.setBurThreImage(imageDsp);
        bbsUserMdl.setBurMainChkedDsp(chkedDsp);
        bbsUserMdl.setBurEuid(userSid);
        bbsUserMdl.setBurEdate(now);

        bbsUserMdl.setBurSubNewThre(newThredFlg);
        bbsUserMdl.setBurSubForum(forumIchiranFlg);
        bbsUserMdl.setBurSubUnchkThre(uncheckThredFlg);

        bbsUserMdl.setBurIniPostType(iniPostType);

        BbsUserDao bbsUserDao = new BbsUserDao(con);
        if (bbsUserDao.update(bbsUserMdl) <= 0) {
            bbsUserMdl.setBurAuid(userSid);
            bbsUserMdl.setBurAdate(now);

            bbsUserDao.insert(bbsUserMdl);
        }

        log__.debug("End");
    }
}
