package jp.groupsession.v2.bbs.bbs050;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BbsUserDao;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 掲示板 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs050Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setInitData(Bbs050ParamModel paramMdl, Connection con, int userSid,
                            RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //掲示板個人情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        paramMdl.setBbs050forumCnt(bUserMdl.getBurForCnt());
        paramMdl.setBbs050threCnt(bUserMdl.getBurThreCnt());
        paramMdl.setBbs050writeCnt(bUserMdl.getBurWrtCnt());
        paramMdl.setBbs050newCnt(bUserMdl.getBurNewCnt());
        paramMdl.setBbs050wrtOrder(bUserMdl.getBurWrtlistOrder());
        paramMdl.setBbs050threImage(bUserMdl.getBurThreImage());
        paramMdl.setBbs050tempImage(bUserMdl.getBurTempImage());
        paramMdl.setBbs050threadFlg(bUserMdl.getBurSubNewThre());
        paramMdl.setBbs050forumFlg(bUserMdl.getBurSubForum());
        paramMdl.setBbs050midokuTrdFlg(bUserMdl.getBurSubUnchkThre());
        paramMdl.setBbs050threMainCnt(bUserMdl.getBurThreMainCnt());
        paramMdl.setBbs050mainChkedDsp(bUserMdl.getBurMainChkedDsp());

        paramMdl.setNewCntLabel(reqMdl);
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
    public void updateBbsUserConf(Bbs050ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();

        //掲示板個人情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        BbsUserModel bUserMdl = bbsBiz.getBbsUserData(con, userSid);

        int forumCnt = paramMdl.getBbs050forumCnt();
        int threCnt = paramMdl.getBbs050threCnt();
        int writeCnt = paramMdl.getBbs050writeCnt();
        int newCnt = paramMdl.getBbs050newCnt();
        int smlNtf = bUserMdl.getBurSmlNtf();
        int threMainCnt = paramMdl.getBbs050threMainCnt();
        int wrtListOrder = paramMdl.getBbs050wrtOrder();
        int imageDsp = paramMdl.getBbs050threImage();
        int imageTempDsp = paramMdl.getBbs050tempImage();
        int chkedDsp = paramMdl.getBbs050mainChkedDsp();

        int newThredFlg = paramMdl.getBbs050threadFlg();
        int forumIchiranFlg = paramMdl.getBbs050forumFlg();
        int uncheckThredFlg = paramMdl.getBbs050midokuTrdFlg();

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
        bbsUserMdl.setBurTempImage(imageTempDsp);
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
