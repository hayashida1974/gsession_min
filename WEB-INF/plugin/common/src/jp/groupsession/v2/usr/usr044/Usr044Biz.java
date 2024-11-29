package jp.groupsession.v2.usr.usr044;

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
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;

/**
 * <br>[機  能] ユーザ情報 ラベル一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr044Biz {

    /** 並び順分割文字列 */
    private static final String SORT_SEPARATE = "-";
    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr044Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr044ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Usr044ParamModel paramMdl,
                            Connection con) throws SQLException {
        //ラベル一覧を取得
        CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
        ArrayList<CmnLabelUsrModel> labelList = dao.select(paramMdl.getUsr043EditSid());

        ArrayList<CmnLabelUsrModel> list = new ArrayList<CmnLabelUsrModel>();
        //ラベル情報をセット
        for (CmnLabelUsrModel model : labelList) {
            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getLabBiko()), "");
            CmnLabelUsrModel usrLabMdl = new CmnLabelUsrModel();

            usrLabMdl.setLabSid(model.getLabSid());
            usrLabMdl.setLabName(model.getLabName());
            usrLabMdl.setLabBiko(biko);
            usrLabMdl.setLucSid(model.getLucSid());
            usrLabMdl.setLauValue(String.valueOf(model.getLabSid()));

            list.add(usrLabMdl);
        }

        //カテゴリ情報取得
        int editSid = paramMdl.getUsr043EditSid();
        CmnLabelUsrCategoryDao catDao = new CmnLabelUsrCategoryDao(con);
        CmnLabelUsrCategoryModel catMdl = catDao.select(editSid);
        if (catMdl == null) {
            return;
        }
        //カテゴリ情報を画面にセット
        paramMdl.setUsr044CatName(catMdl.getLucName());

        paramMdl.setLabelList(list);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getUsr044SortRadio())
        && labelList.size() > 0) {
            CmnLabelUsrModel model = labelList.get(0);
            paramMdl.setUsr044SortRadio(String.valueOf(model.getLabSid()));
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Usr044ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return 最上位または最下位時ソートが行われないフラグ
     * @throws SQLException SQL実行時例外
     */
    public int updateSort(Connection con, Usr044ParamModel paramMdl, int changeKbn)
        throws SQLException {

        //画面表示全キーリスト取得
        String[] keyList = paramMdl.getUsr044KeyList();
        if (keyList == null || keyList.length < 1) {
            return 1;
        }

        //ラジオ選択値取得
        String selectSid = paramMdl.getUsr044SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return 1;
        }
        final int motoSid = NullDefault.getInt(selectSid, -1);
        if (motoSid < 0) {
            return 1;
        }

        CmnLabelUsrDao labelDao = new CmnLabelUsrDao(con);
        SortResult<CmnLabelUsrModel> result = null;
        SortChangeBiz<CmnLabelUsrModel> sortBiz =
                SortChangeBiz.<CmnLabelUsrModel> builder()
                .setFuncTargetList(() -> {
                    return labelDao.select(paramMdl.getUsr043EditSid());
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getLabSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getLabSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getLabSid() == m2.getLabSid()) {
                        return 0;
                    } else {
                        return (m1.getLabSid() - m2.getLabSid())
                                / Math.abs(m1.getLabSid() - m2.getLabSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setLabSort(newSort);
                    labelDao.delete(m.getLabSid());
                    labelDao.insert(m);
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
     * <br>[機  能] ラベル名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @return ラベル名
     * @throws SQLException SQLException
     */
    public String getTargetName(Usr044ParamModel paramMdl, Connection con)
            throws SQLException {

        CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
        CmnLabelUsrModel mdl = new CmnLabelUsrModel();
        int sid = Integer.parseInt(paramMdl.getUsr044SortRadio());
        mdl = dao.selectOneLabel(sid);
        String rtn = "";
        if (mdl != null) {
            rtn = mdl.getLabName();
        }
        return rtn;
    }


}
