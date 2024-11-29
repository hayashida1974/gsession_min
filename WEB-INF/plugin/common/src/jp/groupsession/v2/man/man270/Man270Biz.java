package jp.groupsession.v2.man.man270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnLoginConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLoginConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ログイン設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man270Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Man270ParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {

        if (paramMdl.getMan270InitFlg() == 0) {
            CmnLoginConfDao confDao = new CmnLoginConfDao(con);
            CmnLoginConfModel confData = confDao.getData();
            if (confData != null) {
                paramMdl.setMan270lockKbn(confData.getLlcLockoutKbn());
                paramMdl.setMan270failCount(confData.getLlcFailCnt());
                paramMdl.setMan270failTime(confData.getLlcFailAge());
                paramMdl.setMan270lockTime(confData.getLlcLockAge());
            }

            paramMdl.setMan270InitFlg(1);
        }

        //コンボボックス設定データ(ログイン失敗回数、失敗カウント期間、ロックアウト期間)を設定
        paramMdl.setMan270failCountList(__createCombo2(getFailCountList(), reqMdl));
        paramMdl.setMan270failTimeList(__createCombo(GSConstCommon.LOGIN_FAILTIME_LIST));
        paramMdl.setMan270lockTimeList(__createCombo(GSConstCommon.LOGIN_LOCKTIME_LIST));

    }

    /**
     * <br>[機  能] ログイン失敗回数の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ログイン失敗回数の一覧
     */
    public static int[] getFailCountList() {
        int[] failCountList = new int[GSConstCommon.LOGIN_FAILCOUNT_MAX];
        for (int failCount = 1; failCount <= GSConstCommon.LOGIN_FAILCOUNT_MAX; failCount++) {
            failCountList[failCount - 1] = failCount;
        }
        return failCountList;
    }

    /**
     * <br>[機  能] コンボ情報を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param valueList コンボに設定する値
     * @return コンボ情報
     */
    private List<LabelValueBean> __createCombo(int[] valueList) {
        List<LabelValueBean> comboList = new ArrayList<LabelValueBean>();

        for (int value : valueList) {
            comboList.add(new LabelValueBean(String.valueOf(value), String.valueOf(value)));
        }

        return comboList;
    }

    /**
     * <br>[機  能] コンボ情報を生成する
     * <br>[解  説] ～回を含めた形でコンボを作成する
     * <br>[備  考]
     * @param valueList コンボに設定する値
     * @param reqMdl リクエスト情報
     * @return コンボ情報
     */
    private List<LabelValueBean> __createCombo2(int[] valueList, RequestModel reqMdl) {
        List<LabelValueBean> comboList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        for (int i = 1; i <= valueList.length; i++) {
            comboList.add(
                    new LabelValueBean(
                            gsMsg.getMessage("main.man270.4", new String[] {String.valueOf(i)}),
                                            String.valueOf(i)));
        }

        return comboList;
    }
}