package jp.groupsession.v2.adr.adr241;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr241.dao.Adr241Dao;

/**
 * <br>[機  能] アドレス帳 会社選択画面 担当者一覧のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr241Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr241ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Adr241ParamModel paramMdl, int sessionUsrSid)
    throws SQLException {

        //アドレス帳一覧を取得する
        int acoSid = NullDefault.getInt(paramMdl.getAdr240CompanySid(), 0);
        int abaSid = NullDefault.getInt(paramMdl.getAdr240CompanyBaseSid(), 0);

        Adr010SearchModel searchMdl = new Adr010SearchModel();
        searchMdl.setSessionUser(sessionUsrSid);
        searchMdl.setCompanySid(acoSid);
        searchMdl.setCompanyBaseSid(abaSid);

        Adr241Dao dao = new Adr241Dao(con);
        paramMdl.setAddressList(dao.getAddressList(searchMdl));
    }
}