package jp.groupsession.v2.adr.adr080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 業種一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr080Biz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr080Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr080Biz.class);

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /**
     * <br>[機  能] 初期表示画面情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr080ParamModel
     * @param con コネクション
     * @return Adr080Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Adr080ParamModel getInitData(Adr080ParamModel paramMdl, Connection con)
            throws SQLException {

        //業種一覧を取得
        AdrTypeindustryDao dao = new AdrTypeindustryDao(con);
        List<AdrTypeindustryModel> gyosyuList = dao.select();

        //役職情報を画面表示用に加工する
        List<Adr080TypeindustryModel> atiList = new ArrayList<Adr080TypeindustryModel>();
        for (AdrTypeindustryModel gyosyuMdl : gyosyuList) {

            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(gyosyuMdl.getAtiBiko()), "");

            Adr080TypeindustryModel atiMdl = new Adr080TypeindustryModel();

            atiMdl.setAtiSid(gyosyuMdl.getAtiSid());
            atiMdl.setAtiValue(String.valueOf(gyosyuMdl.getAtiSid()));
            atiMdl.setAtiName(gyosyuMdl.getAtiName());
            atiMdl.setAtiBiko(biko);
            atiList.add(atiMdl);
        }
        paramMdl.setAdr080GyosyuList(atiList);

        //チェックされているラジオがNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getAdr080SortRadio())
                && gyosyuList.size() > 0) {
            AdrTypeindustryModel model = gyosyuList.get(0);
            paramMdl.setAdr080SortRadio(String.valueOf(model.getAtiSid()));
        }

        return paramMdl;
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr080ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Adr080ParamModel paramMdl, int changeKbn)
            throws SQLException {

        final AdrTypeindustryDao dao = new AdrTypeindustryDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getAdr080SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }

        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<AdrTypeindustryModel> sortBiz =
                SortChangeBiz.<AdrTypeindustryModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getAtiSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getAtiSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getAtiSid() == m2.getAtiSid()) {
                        return 0;
                    } else {
                        return (m1.getAtiSid() - m2.getAtiSid()) 
                                / Math.abs(m1.getAtiSid() - m2.getAtiSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setAtiSort(newSort);
                    dao.delete(m.getAtiSid());
                    dao.insert(m);
                })
                .build();
        
        if (changeKbn == SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == SORT_DOWN) {
            sortBiz.down();
        }
    }
}
