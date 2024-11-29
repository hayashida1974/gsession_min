package jp.groupsession.v2.bbs.bbs200;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.dao.BbsAdmConfDao;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 掲示板 管理者設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs200Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bbs200Biz(RequestModel reqMdl) {
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
            Bbs200ParamModel paramMdl,
            Connection con,
            int userSid)
                    throws SQLException {
        if (paramMdl.getBbs200Init() != 0) {
            return;
        }
        BbsBiz bbsBiz = new BbsBiz(con);
        BbsAdmConfModel bacMdl = bbsBiz.getBbsAdminData(con, userSid);
        paramMdl.setBbs200IniPostTypeKbn(bacMdl.getBacIniPostTypeKbn());
        paramMdl.setBbs200IniPostType(bacMdl.getBacIniPostType());
        paramMdl.setBbs200Init(1);
    }

    /**
     * <br>[機  能] 初期値設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void entryKbn(
            Connection con,
            Bbs200ParamModel paramMdl,
            int userSid)
                    throws SQLException {

        UDate now = new UDate();
        BbsBiz bbsBiz = new BbsBiz(con);
        BbsAdmConfModel admMdl = bbsBiz.getBbsAdminData(con, userSid);

        admMdl.setBacEuid(userSid);
        admMdl.setBacEdate(now);
        admMdl.setBacIniPostTypeKbn(paramMdl.getBbs200IniPostTypeKbn());
        admMdl.setBacIniPostType(paramMdl.getBbs200IniPostType());

        BbsAdmConfDao admConfDao = new BbsAdmConfDao(con);
        if (admConfDao.updateInitSetting(admMdl) == 0)  {
            admMdl.setBacAuid(userSid);
            admMdl.setBacAdate(now);
            admConfDao.insert(admMdl);
        }

    }
}
