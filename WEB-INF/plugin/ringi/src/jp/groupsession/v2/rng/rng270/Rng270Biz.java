package jp.groupsession.v2.rng.rng270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.model.RngDairiUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議個人設定代理人画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng270Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng270Biz.class);

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
    public void setInitData(Rng270ParamModel paramMdl,
            Connection con,
            int userSid,
            RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //稟議個人情報を取得
        UDate now = new UDate();
        paramMdl.setRng270DairiStart(now.getDateString("/"));

        RngDairiUserDao dDao = new RngDairiUserDao(con);
        List<RngDairiUserModel> dMdlList = dDao.select(userSid);
        String[] usrSid = new String[dMdlList.size()];
        for (int idx = 0; idx < dMdlList.size(); idx++) {
            usrSid[idx] = String.valueOf(dMdlList.get(idx).getUsrSidDairi());
        }

        if (dMdlList.size() > 0) {
            String dairiFinishDate = null;
            int dairiLastFlg = RngConst.RNG_DAIRI_NOT_KIKAN;

            UDate sDate = dMdlList.get(0).getRduStart();
            paramMdl.setRng270DairiStart(sDate.getDateString("/"));
            UDate eDate = dMdlList.get(0).getRduEnd();

            if (eDate != null) {
                paramMdl.setRng270DairiFinish(eDate.getDateString("/"));
            }

            if (eDate != null) {
                dairiLastFlg = RngConst.RNG_DAIRI_KIKAN;
                dairiFinishDate = eDate.getDateString("/");
            }
            paramMdl.setRng270DairiFinish(dairiFinishDate);
            paramMdl.setRng270DairiLast(dairiLastFlg);
        }

        HashMap<String, String[]> dairi =  new HashMap<String, String[]>();
        dairi.put("target", usrSid);
        paramMdl.getUsrgroupselect().setSelected(dairi);
        reload(paramMdl, con, reqMdl);
        log__.debug("End");
    }

    /**
     * <br>[機  能] リロードする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void reload(Rng270ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //選択 ユーザ
        GroupBiz grpBiz = new GroupBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        UserGroupSelectBiz uBiz = new UserGroupSelectBiz();
        UserGroupSelectModel select = paramMdl.getUsrgroupselect();

        // ユーザ自身は代理人対象から除外
        String[] banUser = new String[1];
        banUser[0] = String.valueOf(reqMdl.getSmodel().getUsrsid());

        select.init(con, reqMdl, "",
                uBiz.convertLabelValueList(grpBiz.getGroupLabelList(con, gsMsg)), banUser);
        log__.debug("End");
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param pMdl パラメータモデル
     * @param usrSid ユーザーSID
     * @param con コネクション
     * @throws SQLException Exception
     */
    public void update(Rng270ParamModel pMdl, int usrSid, Connection con) throws SQLException {

        RngDairiUserDao dDao = new RngDairiUserDao(con);

        RngDairiUserModel dMdl = new RngDairiUserModel();
        dMdl.setUsrSid(usrSid);
        UDate start = new UDate();
        start.setDate(pMdl.getRng270DairiStart().replaceAll("/", ""));
        dMdl.setRduStart(start);

        if (pMdl.getRng270DairiLast() == RngConst.RNG_DAIRI_KIKAN) {
            // 終了期間を設定する → 終了期間日付を保存
            UDate finish = new UDate();
            finish.setDate(pMdl.getRng270DairiFinish().replaceAll("/", ""));
            dMdl.setRduEnd(finish);
        } else if (pMdl.getRng270DairiLast() == RngConst.RNG_DAIRI_NOT_KIKAN) {
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
