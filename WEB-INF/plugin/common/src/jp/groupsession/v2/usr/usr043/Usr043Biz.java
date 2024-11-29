package jp.groupsession.v2.usr.usr043;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;

/**
 * <br>[機  能] ユーザ情報 カテゴリ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr043Biz {

    /** 並び順分割文字列 */
    private static final String SORT_SEPARATE = "-";
    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr043Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr043ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Usr043ParamModel paramMdl, Connection con)
    throws SQLException {
        //カテゴリ一覧を取得
        CmnLabelUsrCategoryDao uLabCatDao = new CmnLabelUsrCategoryDao(con);
        ArrayList<CmnLabelUsrCategoryModel> categoryList = uLabCatDao.select();

        //カテゴリ一覧をセット
        ArrayList<CmnLabelUsrCategoryModel> list = new ArrayList<CmnLabelUsrCategoryModel>();
        for (CmnLabelUsrCategoryModel model : categoryList) {
            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getLucBiko()), "");
            CmnLabelUsrCategoryModel ulcMdl = new CmnLabelUsrCategoryModel();
            ulcMdl.setLucAuid(model.getLucAuid());
            ulcMdl.setLucName(model.getLucName());
            ulcMdl.setLucBiko(biko);
            ulcMdl.setLucSid(model.getLucSid());
            ulcMdl.setUlcValue(String.valueOf(model.getLucSid()));
            list.add(ulcMdl);
        }
        paramMdl.setCatList(list);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getUsr043SortRadio())
        && categoryList.size() > 0) {
            CmnLabelUsrCategoryModel model = categoryList.get(0);
            paramMdl.setUsr043SortRadio(String.valueOf(model.getLucSid()));
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Usr043ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ソート最上位又は最下位につきソートが起きない
     * @throws SQLException SQL実行時例外
     */
    public int updateSort(Connection con, Usr043ParamModel paramMdl, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getUsr043KeyList();
        if (keyList == null || keyList.length < 1) {
            return 1;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getUsr043SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return 1;
        }
        final int motoSid = NullDefault.getInt(selectSid, -1);
        if (motoSid < 0) {
            return 1;
        }

        CmnLabelUsrCategoryDao uLabCatDao = new CmnLabelUsrCategoryDao(con);
        SortResult<CmnLabelUsrCategoryModel> result = null;
        SortChangeBiz<CmnLabelUsrCategoryModel> sortBiz =
                SortChangeBiz.<CmnLabelUsrCategoryModel> builder()
                .setFuncTargetList(() -> {
                    return uLabCatDao.select();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getLucSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getLucSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getLucSid() == m2.getLucSid()) {
                        return 0;
                    } else {
                        return (m1.getLucSid() - m2.getLucSid())
                                / Math.abs(m1.getLucSid() - m2.getLucSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setLucSort(newSort);
                    uLabCatDao.delete(m.getLucSid());
                    uLabCatDao.insert(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }

        if (result != null) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * <br>[機  能]ソート対象のカテゴリ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @return カテゴリ名
     * @throws Exception Exception
     */
    public String getTargetName(Usr043ParamModel paramMdl, Connection con)
            throws Exception {

        CmnLabelUsrCategoryDao dao = new CmnLabelUsrCategoryDao(con);
        CmnLabelUsrCategoryModel clucMdl = new CmnLabelUsrCategoryModel();
        int sid = Integer.parseInt(paramMdl.getUsr043SortRadio());
        String rtn = "";
        clucMdl = dao.select(sid);
        if (clucMdl != null) {
            rtn = clucMdl.getLucName();
        }
        return rtn;
    }

}
