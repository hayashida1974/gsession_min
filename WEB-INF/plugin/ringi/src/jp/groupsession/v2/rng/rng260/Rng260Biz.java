package jp.groupsession.v2.rng.rng260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.model.RngDairiUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議 管理者設定 代理人設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng260Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng260Biz.class);


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Rng260Biz() {
    }


    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void setInitData(Rng260ParamModel paramMdl,
            Connection con,
            int userSid,
            RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //稟議個人情報を取得
        UDate now = new UDate();
        paramMdl.setRng260DairiStart(now.getDateString("/"));
        GroupBiz grpBiz = new GroupBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        paramMdl.setRng260GroupCombo(grpBiz.getGroupLabelList(con, gsMsg));
        paramMdl.setRng260GrpSid(-1);
        UserBiz usrBiz = new UserBiz();
        paramMdl.setRng260UserCombo(
                usrBiz.getNormalAllUserLabelList(con, gsMsg, -1, null, true));
        reload(paramMdl, con, reqMdl);

        log__.debug("End");
    }

    /**
     * <br>[機  能] リロードユーザー
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void reloadUser(Rng260ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //選択 ユーザ
        int selectUsr = paramMdl.getRng260User();
        RngDairiUserDao dDao = new RngDairiUserDao(con);
        List<RngDairiUserModel> dMdlList = dDao.select(selectUsr);
        String[] usrSid = new String[dMdlList.size()];
        for (int idx = 0; idx < dMdlList.size(); idx++) {
            usrSid[idx] = String.valueOf(dMdlList.get(idx).getUsrSidDairi());
        }

        if (dMdlList.size() > 0) {
            String dairiFinishDate = null;
            int dairiLastFlg = RngConst.RNG_DAIRI_NOT_KIKAN;

            UDate sDate = dMdlList.get(0).getRduStart();
            paramMdl.setRng260DairiStart(sDate.getDateString("/"));
            UDate eDate = dMdlList.get(0).getRduEnd();

            if (eDate != null) {
                dairiLastFlg = RngConst.RNG_DAIRI_KIKAN;
                dairiFinishDate = eDate.getDateString("/");
            }
            paramMdl.setRng260DairiFinish(dairiFinishDate);
            paramMdl.setRng260DairiLast(dairiLastFlg);
        } else {
            UDate sDate = new UDate();
            paramMdl.setRng260DairiStart(sDate.getDateString("/"));
        }

        HashMap<String, String[]> dairi =  new HashMap<String, String[]>();
        dairi.put("target", usrSid);
        paramMdl.getUsrgroupselect().setSelected(dairi);
        reload(paramMdl, con, reqMdl);

        log__.debug("End");
    }

    /**
     * <br>[機  能] コンボユーザをリロードする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void reload(Rng260ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //選択 ユーザ
        UserGroupSelectModel select = paramMdl.getUsrgroupselect();
        GroupBiz grpBiz = new GroupBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        UserGroupSelectBiz uBiz = new UserGroupSelectBiz();

        // 選択ユーザ自身は代理人対象から除外
        String[] banUser = null;
        if (paramMdl.getRng260User() > 0) {
            banUser = new String[1];
            banUser[0] = String.valueOf(paramMdl.getRng260User());
        }

        select.init(con, reqMdl, "",
                uBiz.convertLabelValueList(grpBiz.getGroupLabelList(con, gsMsg)), banUser);

        __comboreload(paramMdl, con, reqMdl);
        log__.debug("End");
    }

    /**
     * <br>[機  能] コンボボックスリロード
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    private void __comboreload(Rng260ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl)
    throws Exception {

        GroupBiz grpBiz = new GroupBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        paramMdl.setRng260GroupCombo(grpBiz.getGroupLabelList(con, gsMsg));
        int groupSid = paramMdl.getRng260GrpSid();
        int userSid  = -1;
        UserBiz usrBiz = new UserBiz();

        List<UsrLabelValueBean> memberList
            = usrBiz.getNormalAllUserLabelList(con, gsMsg, groupSid, null, true);

        paramMdl.setRng260UserCombo(memberList);

        // グループメンバー一覧に指定したユーザSID存在しない場合、-1を指定
        if (paramMdl.getRng260User() > 0 && memberList != null) {
            String selectUser = String.valueOf(paramMdl.getRng260User());
            for (UsrLabelValueBean bean : memberList) {
                if (!StringUtil.isNullZeroString(bean.getValue())
                 && selectUser.equals(bean.getValue())) {
                    userSid = paramMdl.getRng260User();
                    break;
                }
            }
        }
        paramMdl.setRng260User(userSid);
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param pMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException Exception
     */
    public void update(Rng260ParamModel pMdl, Connection con) throws SQLException {

        RngDairiUserDao dDao = new RngDairiUserDao(con);
        RngDairiUserModel dMdl = new RngDairiUserModel();
        int usrSid = pMdl.getRng260User();
        dMdl.setUsrSid(usrSid);
        UDate start = new UDate();
        start.setDate(pMdl.getRng260DairiStart().replaceAll("/", ""));
        dMdl.setRduStart(start);

        if (pMdl.getRng260DairiLast() == RngConst.RNG_DAIRI_KIKAN) {
            // 終了期間を設定する → 終了期間日付を保存
            UDate finish = new UDate();
            finish.setDate(pMdl.getRng260DairiFinish().replaceAll("/", ""));
            dMdl.setRduEnd(finish);
        } else if (pMdl.getRng260DairiLast() == RngConst.RNG_DAIRI_NOT_KIKAN) {
            // 終了期間を設定しない → 終了期間日付にNULLを設定
            dMdl.setRduEnd(null);
        }

        HashMap<String, String[]> dairi =  pMdl.getUsrgroupselect().getSelected();
        dDao.delete(usrSid);

        String[] dairiSids = dairi.get("target");
        if (dairiSids != null) {
            // 代理人ユーザ一覧に選択ユーザが存在する場合、除外する
            int index = ArrayUtils.indexOf(dairiSids, String.valueOf(dMdl.getUsrSid()));
            if (index >= 0) {
                dairiSids = (String[]) ArrayUtils.remove(dairiSids, index);
            }

            if (dairiSids.length > 0) {
                dDao.insert(dMdl, dairiSids);
            }
        }
    }
}
