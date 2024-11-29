package jp.groupsession.v2.adr.adr320;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
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
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr320Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr320Biz.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Adr320Biz() {
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr320ParamModel
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Adr320ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con) throws Exception {

        log__.debug("setInitData START");

        if (paramMdl.getAdr320initFlg() != 1) {
            paramMdl.setAdr320initFlg(1);


            // 管理者設定を取得
            AdrAconfDao aconfDao = new AdrAconfDao(con);
            AdrAconfModel aconf = aconfDao.selectAconf(true);
            paramMdl.setAdr320AacArestKbn(aconf.getAacArestKbn());

            // 登録権限者を取得
            AdrArestDao dao = new AdrArestDao(con);
            List<AdrArestModel> getList = dao.select();

            UserGroupSelectBiz usrSelBiz = new UserGroupSelectBiz();

            // 登録権限者の一覧を作成
            String[] makeUser = null;
            List<String> senderList = new ArrayList<String>();
            for (AdrArestModel mdl : getList) {
                if (mdl.getUsrSid() >= 0 && usrSelBiz.checkUser(con, mdl.getUsrSid())) {
                    senderList.add(String.valueOf(mdl.getUsrSid()));
                }
                if (mdl.getGrpSid() >= 0 && usrSelBiz.checkGroup(con, mdl.getGrpSid())) {
                    senderList.add(
                            UserGroupSelectBiz.GROUP_PREFIX
                            + String.valueOf(mdl.getGrpSid()));
                }

            }
            if (!senderList.isEmpty()) {
                makeUser = (String[]) senderList.toArray(new String[senderList.size()]);
            }
            paramMdl.setAdr320AdrArestList(makeUser);
        }

        log__.debug("setInitData END");

    }
    /**
     *
     * <br>[機  能] 登録する登録者制限モデルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param arestSidList Sidリスト
     * @return 登録者リスト
     * @throws SQLException SQL実行時エラー
     */
    public List<AdrArestModel> createArestList(RequestModel reqMdl,
            Connection con,
            String[] arestSidList) throws SQLException {

        ArrayList<AdrArestModel> ret = new ArrayList<AdrArestModel>();

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
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        for (GroupModel group : glist) {
            AdrArestModel mdl = new AdrArestModel();
            mdl.setGrpSid(group.getGroupSid());
            ret.add(mdl);
        }

        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (BaseUserModel user : ulist) {
            AdrArestModel mdl = new AdrArestModel();
            mdl.setUsrSid(user.getUsrsid());
            ret.add(mdl);
        }
        return ret;
    }
}
