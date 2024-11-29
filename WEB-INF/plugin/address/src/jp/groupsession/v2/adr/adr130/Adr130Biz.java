package jp.groupsession.v2.adr.adr130;

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
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 ラベル一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr130Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr130Biz.class);

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
    public Adr130Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr130ParamModel
     * @param con コネクション
     * @return Adr130Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Adr130ParamModel getInitData(Adr130ParamModel paramMdl, Connection con)
    throws SQLException {

        //ラベル一覧を取得
        AdrLabelDao dao = new AdrLabelDao(con);
        List<AdrLabelModel> labelList = dao.getLabelInCategory(paramMdl.getAdr280EditSid());

        //ラベル情報を画面表示用に加工する
        List<Adr130LabelModel> atiList = new ArrayList<Adr130LabelModel>();
        int count = 0;
        for (AdrLabelModel labelMdl : labelList) {

            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(labelMdl.getAlbBiko()), "");

            Adr130LabelModel atiMdl = new Adr130LabelModel();

            atiMdl.setAlbSid(labelMdl.getAlbSid());
            atiMdl.setAlbValue(String.valueOf(labelMdl.getAlbSid()));
            atiMdl.setAlbName(labelMdl.getAlbName());
            atiMdl.setAlbBiko(biko);
            atiList.add(atiMdl);
            count++;
        }
        paramMdl.setAdr130LabelList(atiList);

        //カテゴリ情報取得
        int catSid = paramMdl.getAdr280EditSid();
        AdrLabelCategoryDao catDao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel catMdl = catDao.select(catSid);
        String catName = NullDefault.getString(catMdl.getAlcName(), "");
        //カテゴリ情報を画面にセット
        paramMdl.setAdr130CatName(catName);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getAdr130SortRadio())
        && labelList.size() > 0) {
            AdrLabelModel model = labelList.get(0);
            paramMdl.setAdr130SortRadio(String.valueOf(model.getAlbSid()));
        }

        return paramMdl;
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr130ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Adr130ParamModel paramMdl, int changeKbn)
        throws SQLException {
        
        final AdrLabelDao dao = new AdrLabelDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getAdr130SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<AdrLabelModel> sortBiz =
                SortChangeBiz.<AdrLabelModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getLabelInCategory(paramMdl.getAdr280EditSid());
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getAlbSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getAlbSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getAlbSid() == m2.getAlbSid()) {
                        return 0;
                    } else {
                        return (m1.getAlbSid() - m2.getAlbSid()) / Math.abs(m1.getAlbSid() - m2.getAlbSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setAlbSort(newSort);
                    dao.updateCatMove(m);
                })
                .build();
        
        if (changeKbn == SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == SORT_DOWN) {
            sortBiz.down();
        }
    }
}
