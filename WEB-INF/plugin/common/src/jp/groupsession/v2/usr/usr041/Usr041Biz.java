package jp.groupsession.v2.usr.usr041;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPriSortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPriSortConfModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.dao.UsrPconfDao;
import jp.groupsession.v2.usr.model.UsrPconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ユーザ情報 個人設定 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr041Biz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr041Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr041Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr041ParamModel
     * @param con コネクション
     * @param umodel ユーザ基本情報モデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Usr041ParamModel paramMdl,
            Connection con, BaseUserModel umodel) throws SQLException {
        //ユーザSID
        int usid = umodel.getUsrsid();

        //DBより設定情報を取得。なければデフォルト値とする。
        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        //管理者が設定したソート設定値を取得
        CmnUsrAdmSortConfModel aconf = biz.getSortAdmConfModel(con);

        //各ユーザ独自のソート設定値を取得
        CmnUsrPriSortConfModel pconf = biz.getSortPriConfModel(con, umodel.getUsrsid(), aconf);

        int dspCount = getDspCnt(usid, con);
        paramMdl.setUsr041DspCnt(dspCount);
        paramMdl.setUsr041DspCntList(getDspCntLavel());

        //ソート区分
        paramMdl.setUsr041DefoDspKbn(aconf.getUasSortKbn());

        if (aconf.getUasSortKbn() == GSConstUser.DEFO_DSP_USR) {
            //各ユーザが設定したメンバー表示設定を反映
            log__.debug("***ユーザが設定したソート順で表示します***");
            //ソート1
            paramMdl.setUsr041SortKey1(pconf.getUpsSortKey1());
            paramMdl.setUsr041SortOrder1(pconf.getUpsSortOrder1());
            //ソート2
            paramMdl.setUsr041SortKey2(pconf.getUpsSortKey2());
            paramMdl.setUsr041SortOrder2(pconf.getUpsSortOrder2());

        } else {
            //管理者が設定したデフォルトメンバー表示設定を反映

            //ソート1
            paramMdl.setUsr041SortKey1(aconf.getUasSortKey1());
            paramMdl.setUsr041SortOrder1(aconf.getUasSortOrder1());
            //ソート2
            paramMdl.setUsr041SortKey2(aconf.getUasSortKey2());
            paramMdl.setUsr041SortOrder2(aconf.getUasSortOrder2());
        }

    }

    /**
     * <br>[機  能] 表示件数を取得する。登録されていなければデフォルト値を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param usid ユーザSID
     * @param con コネクション
     * @return 表示件数
     * @throws SQLException SQL実行例外
     */
    public int getDspCnt(int usid, Connection con) throws SQLException {
        UsrPconfDao updao = new UsrPconfDao(con);
        UsrPconfModel setting = updao.select(usid);

        //現在の件数をフォームにセット(設定がなければデフォルト件数とする)
        int dspCount = GSConstUser.DEFAULT_DSP_CNT;
        if (setting != null) {
            dspCount = setting.getUpcMaxDsp();
        }
        return dspCount;
    }

    /**
     * <br>[機  能] 表示件数コンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    public List<LabelValueBean> getDspCntLavel() {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int cnt = 10; cnt <= 100; cnt += 10) {
            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 表示件数をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr041ParamModel
     * @param con コネクション
     * @param umodel ユーザ基本情報モデル
     * @throws SQLException SQL実行例外
     */
    public void updateDspCount(Usr041ParamModel paramMdl,
            Connection con, BaseUserModel umodel) throws SQLException {
        //
        UsrPconfDao updao = new UsrPconfDao(con);
        UsrPconfModel setting = new UsrPconfModel();
        setting.setUpcMaxDsp(paramMdl.getUsr041DspCnt());
        setting.setUsrSid(umodel.getUsrsid());
        UDate now = new UDate();
        setting.setUpcAdate(now);
        setting.setUpcAuid(umodel.getUsrsid());
        setting.setUpcEdate(now);
        setting.setUpcEuid(umodel.getUsrsid());
        int count = updao.updateEdit(setting);
        if (count == 0) {
            updao.insert(setting);
        }
    }

    /**
     * <br>[機  能] 各ユーザソート設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr041ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setSortPriConfig(Usr041ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより現在の設定を取得する。(なければデフォルト)
        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        CmnUsrAdmSortConfModel sortAdmconf = biz.getSortAdmConfModel(con);

        if (sortAdmconf.getUasSortKbn() == GSConstUser.DEFO_DSP_ADM) {
            return;
        }

        CmnUsrPriSortConfModel sortPriconf = new CmnUsrPriSortConfModel();

        //データを設定
        sortPriconf.setUsrSid(umodel.getUsrsid());
        sortPriconf.setUpsSortKey1(paramMdl.getUsr041SortKey1());
        sortPriconf.setUpsSortOrder1(paramMdl.getUsr041SortOrder1());
        sortPriconf.setUpsSortKey2(paramMdl.getUsr041SortKey2());
        sortPriconf.setUpsSortOrder2(paramMdl.getUsr041SortOrder2());
        sortPriconf.setUpsEuid(umodel.getUsrsid());
        UDate now = new UDate();
        sortPriconf.setUpsEdate(now);

        //DB更新
        boolean commitFlg = false;
        try {
            CmnUsrPriSortConfDao dao = new CmnUsrPriSortConfDao(con);
            int count = dao.updateSortConfig(sortPriconf);
            if (count <= 0) {
                sortPriconf.setUpsAuid(umodel.getUsrsid());
                sortPriconf.setUpsAdate(now);
                dao.insert(sortPriconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("管理者ソート設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}
