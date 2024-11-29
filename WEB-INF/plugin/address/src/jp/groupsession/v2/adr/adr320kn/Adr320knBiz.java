package jp.groupsession.v2.adr.adr320kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr320.Adr320Biz;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrArestDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrArestModel;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 *
 * <br>[機  能] アドレス帳 管理者設定 権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr320knBiz extends Adr320Biz {
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr320ParamModel
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Adr320knParamModel paramMdl,
            RequestModel reqMdl,
            Connection con) throws Exception {

        super.setInitData(paramMdl, reqMdl, con);

        // 対象者をセット
        UserGroupSelectBiz usrSelBiz = new UserGroupSelectBiz();
        paramMdl.setAdr320knAdrArestViewList(
                usrSelBiz.getSelectedLabel(paramMdl.getAdr320AdrArestList(), con));
    }

    /**
     *
     * <br>[機  能] 確定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param arestKbn 登録者制限区分
     * @param arest 制限設定SID
     * @throws SQLException SQL実行時例外
     */
    public void comitData(RequestModel reqMdl,
            Connection con,
            int arestKbn,
            String[] arest) throws SQLException {
        // 管理者設定を取得
        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconf = aconfDao.selectAconf();
        boolean create = false;
        if (aconf == null) {
            create = true;
        }
        int sessionUid = reqMdl.getSmodel().getUsrsid();

        aconf = __makeAconfModel(sessionUid, aconf, arestKbn);

        if (create) {
            aconfDao.insert(aconf);
        } else {
            aconfDao.updateArestKbn(arestKbn);
        }

        AdrArestDao arestDao = new AdrArestDao(con);
        arestDao.deleteAll();

        if (arestKbn == GSConstAddress.ARESTKBN_NONE) {
            return;
        }

        List<AdrArestModel> arestList = createArestList(reqMdl, con, arest);
        for (AdrArestModel arestModel : arestList) {
            arestDao.insert(arestModel);
        }
    }
    /**
     *
     * <br>[機  能] 登録情報を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUid ユーザSID
     * @param aconf 管理者設定モデル
     * @param arestKbn 登録者制限区分
     * @return 登録用 管理者設定モデル
     */
    private AdrAconfModel __makeAconfModel(int sessionUid, AdrAconfModel aconf, int arestKbn) {
        if (aconf == null) {
            aconf = new AdrAconfModel();
            aconf.init();
        }
        UDate now = new UDate();
        aconf.setAacAdate(now);
        aconf.setAacEdate(now);
        aconf.setAacAuid(sessionUid);
        aconf.setAacEuid(sessionUid);

        aconf.setAacArestKbn(arestKbn);

        return aconf;
    }
    /**
    *
    * <br>[機  能] ログメッセージ取得
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエストモデル
    * @param con コネクション
    * @param arest SIDリスト
    * @return 登録者リスト
     * @throws SQLException SQLエラー
    */
   public String getLogMessage(
           RequestModel reqMdl,
           Connection con,
           String[] arest) throws SQLException {

       String msg = getArestList(reqMdl, con, arest);

       return msg;
   }
   /**
   *
   * <br>[機  能] 名前リスト作成
   * <br>[解  説]
   * <br>[備  考]
   * @param reqMdl リクエストモデル
   * @param con コネクション
   * @param arestSidList Sidリスト
   * @return 登録者リスト
   * @throws SQLException SQL実行時エラー
   */
  private String getArestList(RequestModel reqMdl,
          Connection con,
          String[] arestSidList) throws SQLException {

      String ret = "";

      ArrayList<Integer> grpSids = new ArrayList<Integer>();
      List<String> usrSids = new ArrayList<String>();
      if (arestSidList != null) {
          for (String target : arestSidList) {
              if (target.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                  grpSids.add(NullDefault.getInt(
                          target.substring(1), -1));
              } else {
                  if (NullDefault.getInt(
                          target, -1) > GSConstUser.USER_RESERV_SID) {
                      usrSids.add(target);
                  }
              }
          }
      }

      boolean multiFlg = false;
      GsMessage gsMsg = new GsMessage(reqMdl);
      UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
      ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
      for (GroupModel group : glist) {
          if (multiFlg) {
              ret += "\r\n";
          } else {
              ret += "[" + gsMsg.getMessage("address.adr320.3") + "]";
              multiFlg = true;
          }
          ret += group.getGroupName();
      }

      UserBiz userBiz = new UserBiz();
      ArrayList<BaseUserModel> ulist
              = userBiz.getBaseUserList(con,
                                      (String[]) usrSids.toArray(new String[usrSids.size()]));
      for (BaseUserModel user : ulist) {
          if (multiFlg) {
              ret += "\r\n";
          } else {
              ret += "[" + gsMsg.getMessage("address.adr320.3") + "]";
              multiFlg = true;
          }
          ret += user.getUsisei() + " " + user.getUsimei();
      }
      return ret;
  }

}
