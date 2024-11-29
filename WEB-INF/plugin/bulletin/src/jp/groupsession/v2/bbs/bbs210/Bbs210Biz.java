package jp.groupsession.v2.bbs.bbs210;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BbsUserDao;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 掲示板 個人設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs210Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bbs210Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(
            Bbs210ParamModel paramMdl,
            Connection con,
            int userSid)
                    throws SQLException {
        if (paramMdl.getBbs210Init() != 0) {
            return;
        }
        BbsBiz bbsBiz = new BbsBiz(con);
        BbsUserModel buMdl = bbsBiz.getBbsUserData(con, userSid);
        paramMdl.setBbs210IniPostType(buMdl.getBurIniPostType());
        paramMdl.setBbs210Init(1);

    }

    /**
     * <br>[機  能] 初期値設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void entryKbn(
            Connection con,
            Bbs210ParamModel paramMdl,
            int userSid)
                    throws SQLException {

        UDate now = new UDate();
        BbsBiz bbsBiz = new BbsBiz(con);
        BbsUserModel buMdl = bbsBiz.getBbsUserData(con, userSid);

        buMdl.setUsrSid(userSid);
        buMdl.setBurEuid(userSid);
        buMdl.setBurEdate(now);
        buMdl.setBurIniPostType(paramMdl.getBbs210IniPostType());

        BbsUserDao buDao = new BbsUserDao(con);
        if (buDao.update(buMdl) <= 0) {
            buMdl.setBurAuid(userSid);
            buMdl.setBurAdate(now);
            buDao.insert(buMdl);
        }
    }
}
