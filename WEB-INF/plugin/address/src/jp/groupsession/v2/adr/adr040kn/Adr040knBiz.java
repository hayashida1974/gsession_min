package jp.groupsession.v2.adr.adr040kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr040knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr040knBiz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr040knBiz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 権限設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr040knParamModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return AdrAconfModel アドレス管理者設定モデル
     */
    public AdrAconfModel setAdrAconf(Adr040knParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

      boolean commitFlg = false;
      AdrAconfModel model = new AdrAconfModel();
      try {
          //DBの存在確認
          AdrAconfDao dao = new AdrAconfDao(con);
          model = dao.selectAconf();
          //画面値セット
          AdrAconfModel crtMdl = createAdrAconfData(paramMdl, usrSid, model);

          if (model == null) {
              dao.insert(crtMdl);
          } else {
              dao.update(crtMdl);
          }
          commitFlg = true;

      } catch (SQLException e) {
          log__.error("", e);
          throw e;
      } finally {
          if (commitFlg) {
              con.commit();
          }
      }
      return model;
    }

    /**
     * <br>[機  能] 在席個人設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr040knParamModel
     * @param usrSid ユーザSID
     * @param amdl 管理者設定モデル
     * @return KhmSyaikbnModel 社員区分情報
     */
    public AdrAconfModel createAdrAconfData(
            Adr040knParamModel paramMdl, int usrSid, AdrAconfModel amdl) {

        UDate nowDate = new UDate();

        AdrAconfModel mdl = amdl;
        if (mdl == null) {
            mdl = new AdrAconfModel();
            //mdl.init();
        }
        mdl.setAacAtiEdit(paramMdl.getAdr040Pow1());
        mdl.setAacAcoEdit(paramMdl.getAdr040Pow2());
        mdl.setAacAlbEdit(paramMdl.getAdr040Pow3());
        mdl.setAacExport(paramMdl.getAdr040Pow4());
        mdl.setAacYksEdit(paramMdl.getAdr040Pow5());

        mdl.setAacAuid(usrSid);
        mdl.setAacAdate(nowDate);
        mdl.setAacEuid(usrSid);
        mdl.setAacEdate(nowDate);

        return mdl;
    }

}
