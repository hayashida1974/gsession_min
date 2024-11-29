package jp.groupsession.v2.sml.sml130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 個人設定 自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml130Biz {

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Sml130Biz(RequestModel reqMdl, Connection con) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml130ParamModel paramMdl, Connection con)
        throws SQLException {

       /*********************************************************
        *
        * ショートメール自動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年リスト
        for (int i = 0; i <= 5; i++) {
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        yearLabel.add(
                new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {String.valueOf(10)}),
                "10"));
        paramMdl.setSml130YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months" , new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setSml130MonthLabelList(monthLabel);

        SmlAdelDao delDao = new SmlAdelDao(con);
        SmlAdelModel delMdl = delDao.select(sessionUsrSid);
        if (delMdl == null) {
            delMdl = new SmlAdelModel();
            delMdl.setSadJdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadJdelYear(0);
            delMdl.setSadJdelMonth(0);
            delMdl.setSadSdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadSdelYear(0);
            delMdl.setSadSdelMonth(0);
            delMdl.setSadWdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadWdelYear(0);
            delMdl.setSadWdelMonth(0);
            delMdl.setSadDdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
            delMdl.setSadDdelYear(0);
            delMdl.setSadDdelMonth(0);
        }

        //受信タブ処理 選択値
        paramMdl.setSml130JdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml130JdelKbn(),
                        String.valueOf(delMdl.getSadJdelKbn())));

        //受信タブ 年
        paramMdl.setSml130JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130JYear()),
                        String.valueOf(delMdl.getSadJdelYear())));

        //受信タブ 月
        paramMdl.setSml130JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130JMonth()),
                        String.valueOf(delMdl.getSadJdelMonth())));

        //送信タブ処理 選択値
        paramMdl.setSml130SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml130SdelKbn(),
                        String.valueOf(delMdl.getSadSdelKbn())));

        //送信タブ 年
        paramMdl.setSml130SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130SYear()),
                        String.valueOf(delMdl.getSadSdelYear())));

        //送信タブ 月
        paramMdl.setSml130SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130SMonth()),
                        String.valueOf(delMdl.getSadSdelMonth())));

        //草稿タブ処理 選択値
        paramMdl.setSml130WdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml130WdelKbn(),
                        String.valueOf(delMdl.getSadWdelKbn())));

        //草稿タブ 年
        paramMdl.setSml130WYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130WYear()),
                        String.valueOf(delMdl.getSadWdelYear())));

        //草稿タブ 月
        paramMdl.setSml130WMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130WMonth()),
                        String.valueOf(delMdl.getSadWdelMonth())));

        //ゴミ箱タブ処理 選択値
        paramMdl.setSml130DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml130DdelKbn(),
                        String.valueOf(delMdl.getSadDdelKbn())));

        //ゴミ箱タブ 年
        paramMdl.setSml130DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130DYear()),
                        String.valueOf(delMdl.getSadDdelYear())));

        //ゴミ箱タブ 月
        paramMdl.setSml130DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml130DMonth()),
                        String.valueOf(delMdl.getSadDdelMonth())));

        //バッチ処理実行時間を取得
        CmnBatchJobDao batchDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batchMdl = batchDao.select();
        String batchTime = "";
        if (batchMdl != null) {
            batchTime = String.valueOf(batchMdl.getBatFrDate());
        }
        paramMdl.setSml130BatchTime(batchTime);
    }

    /**
     * <br>[機  能] 自動削除設定(個人)が使用可能か判定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret true:使用可能 false:使用不可
     * @throws SQLException SQL実行例外
     */
    public boolean canUseAutoDeleteSetting() throws SQLException {

        boolean ret = true;

        SmlCommonBiz smlBiz = new SmlCommonBiz();
        SmlAdminModel admMdl = new SmlAdminModel();
        admMdl = smlBiz.getSmailAdminConf(reqMdl__.getSmodel().getUsrsid(), con__);

        if (admMdl != null) {
            //自動削除 = 管理者が設定する
            if (admMdl.getSmaAutoDelKbn() == GSConstSmail.SML_DEL_KBN_ADM_SETTING) {
                ret = false;
            }
        }

        return ret;
    }
}