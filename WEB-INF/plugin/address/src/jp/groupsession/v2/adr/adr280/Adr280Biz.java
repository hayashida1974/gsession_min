package jp.groupsession.v2.adr.adr280;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 カテゴリ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr280Biz {

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr280Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr280Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr280ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Adr280ParamModel paramMdl, Connection con)
    throws SQLException {
        //カテゴリ一覧を取得
        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);
        List<AdrLabelCategoryModel> categoryList = categoryDao.select();

        //カテゴリ一覧をセット
        ArrayList<AdrLabelCategoryModel> list = new ArrayList<AdrLabelCategoryModel>();
        for (AdrLabelCategoryModel model : categoryList) {
            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(model.getAlcBiko()), "");
            AdrLabelCategoryModel ulcMdl = new AdrLabelCategoryModel();
            ulcMdl.setAlcAuid(model.getAlcAuid());
            ulcMdl.setAlcName(model.getAlcName());
            ulcMdl.setAlcBiko(biko);
            ulcMdl.setAlcSid(model.getAlcSid());
            ulcMdl.setAlcValue(String.valueOf(model.getAlcSid()));
            list.add(ulcMdl);
        }
        paramMdl.setCatList(list);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getAdr280SortRadio())
        && categoryList.size() > 0) {
            AdrLabelCategoryModel model = categoryList.get(0);
            paramMdl.setAdr280SortRadio(String.valueOf(model.getAlcSid()));
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr280ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Adr280ParamModel paramMdl, int changeKbn)
        throws SQLException {
        
        final AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getAdr280SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<AdrLabelCategoryModel> sortBiz =
                SortChangeBiz.<AdrLabelCategoryModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getAlcSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getAlcSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getAlcSid() == m2.getAlcSid()) {
                        return 0;
                    } else {
                        return (m1.getAlcSid() - m2.getAlcSid()) 
                                / Math.abs(m1.getAlcSid() - m2.getAlcSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setAlcSort(newSort);
                    dao.updateOnlySort(m);
                })
                .build();
        
        if (changeKbn == SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == SORT_DOWN) {
            sortBiz.down();
        }
    }

}
