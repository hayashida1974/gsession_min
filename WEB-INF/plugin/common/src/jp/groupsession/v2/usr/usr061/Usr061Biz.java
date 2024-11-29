package jp.groupsession.v2.usr.usr061;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.usr060.IUsr060Param;
/**
 *
 * <br>[機  能] ワンタイムパスワード通知先メールアドレス設定（GS管理者） Biz
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr061Biz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /**
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Usr061Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
    *
    * <br>[機  能] アドレス更新確定処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param param パラメータ
    * @throws SQLException 実行時例外
    */
   public void doUpdateAddress(IUsr060Param param) throws SQLException {
       //既存情報 取得
       CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
       CmnUsrmInfModel usrInf = usrDao.select(0);
       //更新
       usrInf.setUsiOtpsendAddress(param.getUsr060SendToAddress());
       usrInf.setUsiEdate(new UDate());
       usrInf.setUsiEuid(reqMdl__.getSmodel().getUsrsid());
       usrDao.updateCmnUserInf(usrInf);

   }

}
