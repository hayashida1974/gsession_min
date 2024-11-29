package jp.groupsession.v2.adr.adr210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳 役職一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr210Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr210Biz.class);
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
    public Adr210Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr210ParamModel
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr210ParamModel paramMdl) throws SQLException {

        //役職一覧を取得する
        AdrPositionDao apDao = new AdrPositionDao(con);
        List<AdrPositionModel> apList = apDao.selectPositionList();

        //役職情報を画面表示用に加工する
        List<Adr210PositionModel> posList = new ArrayList<Adr210PositionModel>();
        for (AdrPositionModel apMdl : apList) {

            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(apMdl.getApsBiko()), "");

            Adr210PositionModel posMdl = new Adr210PositionModel();
            posMdl.setApsSid(apMdl.getApsSid());
            posMdl.setPosValue(String.valueOf(apMdl.getApsSid()));
            posMdl.setApsName(apMdl.getApsName());
            posMdl.setApsBiko(biko);
            posList.add(posMdl);
        }
        paramMdl.setPosList(posList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getAdr210SortRadio())
            && apList.size() > 0) {

            AdrPositionModel apMdl = apList.get(0);
            paramMdl.setAdr210SortRadio(String.valueOf(apMdl.getApsSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr210ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Adr210ParamModel paramMdl, int changeKbn)
        throws SQLException {
        
        SortResult<AdrPositionModel> result = null;
        final AdrPositionDao dao = new AdrPositionDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getAdr210SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<AdrPositionModel> sortBiz =
                SortChangeBiz.<AdrPositionModel> builder()
                .setFuncTargetList(() -> {
                    return dao.selectPositionList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getApsSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getApsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getApsSid() == m2.getApsSid()) {
                        return 0;
                    } else {
                        return (m1.getApsSid() - m2.getApsSid()) 
                                / Math.abs(m1.getApsSid() - m2.getApsSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setApsSort(newSort);
                    dao.delete(m.getApsSid());
                    dao.insert(m);
                })
                .build();
        
        if (changeKbn == SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == SORT_DOWN) {
            result = sortBiz.down();
        }
    }
}