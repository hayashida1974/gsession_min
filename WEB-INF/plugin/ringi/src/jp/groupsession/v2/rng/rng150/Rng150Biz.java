package jp.groupsession.v2.rng.rng150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ選択画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng150Biz {

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param userSid ユーザSID
     * @throws Exception 例外
     */
    public void init(Rng150ParamModel paramMdl,
                     Connection con,
                     RequestModel reqMdl,
                     int userSid)
                     throws Exception {

        ArrayList<RngTemplateCategoryModel> catList =  new ArrayList<RngTemplateCategoryModel>();

        RngBiz biz = new RngBiz(con);

        int tplMode = paramMdl.getRngTemplateMode();
        if (tplMode == RngConst.RNG_TEMPLATE_SHARE) {
            //共有のカテゴリを取得する
            // 管理者設定画面の場合
            CommonBiz cmnBiz = new CommonBiz();
            BaseUserModel usModel = reqMdl.getSmodel();
            boolean isAdmin = cmnBiz.isPluginAdmin(con, usModel, RngConst.PLUGIN_ID_RINGI);

            catList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                    userSid, isAdmin, RngConst.RTPLIST_MOKUTEKI_KANRI);
        } else if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
            //個人のカテゴリを取得する
            catList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_PRIVATE,
                    userSid, false, RngConst.RTPLIST_MOKUTEKI_KANRI);
        }
        paramMdl.setRng150CatList(catList);

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sortKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param sessionUserSid セッションユーザSID
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public boolean updateSort(Connection con, Rng150ParamModel paramMdl,
                            int sortKbn, int sessionUserSid, RequestModel reqMdl)
        throws SQLException {

        UDate now = new UDate();

        //ラジオ選択値取得
        int tplMode = paramMdl.getRngTemplateMode();
        String sortRtpSid = paramMdl.getRng150SortRadio();
        if (StringUtil.isNullZeroString(sortRtpSid)) {
            return false;
        }
        final int motoSid = NullDefault.getInt(sortRtpSid, -1);
        if (motoSid <= 0) {
            return false;
        }

        RngTemplateCategoryDao categoriDao = new RngTemplateCategoryDao(con);
        RngBiz biz = new RngBiz(con);
        SortResult<RngTemplateCategoryModel> result = null;
        SortChangeBiz<RngTemplateCategoryModel> sortBiz =
                SortChangeBiz.<RngTemplateCategoryModel> builder()
                .setFuncTargetList(() -> {
                    if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
                        //個人のカテゴリを取得する
                        return biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_PRIVATE,
                                sessionUserSid, false, RngConst.RTPLIST_MOKUTEKI_KANRI);
                    } else {
                        //共有のカテゴリを取得する
                        // 管理者設定画面の場合
                        CommonBiz cmnBiz = new CommonBiz();
                        BaseUserModel usModel = reqMdl.getSmodel();
                        boolean isAdmin = cmnBiz.isPluginAdmin(
                                              con, usModel, RngConst.PLUGIN_ID_RINGI);

                        return biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                                sessionUserSid, isAdmin, RngConst.RTPLIST_MOKUTEKI_KANRI);
                    }
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getRtcSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getRtcSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getRtcSid() == m2.getRtcSid()) {
                        return 0;
                    } else {
                        return (m1.getRtcSid() - m2.getRtcSid())
                                / Math.abs(m1.getRtcSid() - m2.getRtcSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    categoriDao.updateSort(m.getRtcSid(), newSort, sessionUserSid, now);
                })
                .build();

        if (sortKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (sortKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能]ソート順変更を行ったカテゴリ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @return カテゴリ名
     * @throws SQLException SQLException
     * @throws NumberFormatException NumberFormatException
     */
    public String getTargetName(Rng150ParamModel paramMdl, Connection con)
            throws NumberFormatException, SQLException {

        RngTemplateCategoryDao rtcDao = new RngTemplateCategoryDao(con);
        RngTemplateCategoryModel mdl =
                rtcDao.select(Integer.parseInt(paramMdl.getRng150SortRadio()));
        return mdl.getRtcName();
    }
}
