package jp.groupsession.v2.man.man100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 役職マネージャー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man100Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man100Biz.class);

    /** 処理区分 登録 */
    public static final int MODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int MODE_EDIT = 1;


    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Man100ParamModel paramMdl) throws SQLException {

        //役職一覧を取得する
        CmnPositionDao cpDao = new CmnPositionDao(con);
        List<CmnPositionModel> cpList = cpDao.getPosList(false);

        //役職情報を画面表示用に加工する
        List<Man100PositionModel> posList = new ArrayList<Man100PositionModel>();
        for (CmnPositionModel cpMdl : cpList) {

            String biko = NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(cpMdl.getPosBiko()), "");

            Man100PositionModel posMdl = new Man100PositionModel();
            posMdl.setPosSid(cpMdl.getPosSid());
            posMdl.setPosCode(cpMdl.getPosCode());
            posMdl.setPosValue(String.valueOf(cpMdl.getPosSid()));
            posMdl.setPosName(cpMdl.getPosName());
            posMdl.setPosBiko(biko);
            posList.add(posMdl);
        }
        paramMdl.setPosList(posList);


        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getMan100SortRadio())
        && cpList.size() > 0) {
            CmnPositionModel cpMdl = cpList.get(0);
            paramMdl.setMan100SortRadio(String.valueOf(cpMdl.getPosSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Man100ParamModel paramMdl, int changeKbn)
        throws SQLException {
        
        final CmnPositionDao dao = new CmnPositionDao(con);

        //ラジオ選択値取得
        String selectSid = paramMdl.getMan100SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<CmnPositionModel> sortBiz =
                SortChangeBiz.<CmnPositionModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getPosList(false);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getPosSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getPosSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getPosSid() == m2.getPosSid()) {
                        return 0;
                    } else {
                        return (m1.getPosSid() - m2.getPosSid()) 
                                / Math.abs(m1.getPosSid() - m2.getPosSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updatePosSort(m.getPosSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
