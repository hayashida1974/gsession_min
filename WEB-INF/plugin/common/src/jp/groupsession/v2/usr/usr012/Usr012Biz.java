package jp.groupsession.v2.usr.usr012;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.dao.SltUserPerGroupDao;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;

/**
 * <br>[機  能] メイン 管理者設定 グループマネージャー（所属ユーザ一覧）画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr012Biz {

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Usr012ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setUsr012List(
            Connection con,
            Usr012ParamModel paramMdl) throws SQLException {

        //インスタンスの生成
        SltUserPerGroupDao supgDao = new SltUserPerGroupDao(con);

        //グループSID、ソートキー、オーダーキーを取得
        int grpSid = paramMdl.getUsr010grpSid();
        int sortKey = paramMdl.getUsr012SortKey();
        int orderKey = paramMdl.getUsr012OrderKey();

        //グループSIDの検索結果をListに格納
        List<SltUserPerGroupModel> grpNmList =
            supgDao.selectGroupListSort(grpSid, sortKey, orderKey);

        //検索結果をセット
        paramMdl.setUsr012ModelList(grpNmList);
    }
}