package jp.groupsession.v2.sml.sml140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 個人設定 手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml140Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml140ParamModel paramMdl, Connection con)
        throws SQLException {

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = new SmlAccountModel();

        if (paramMdl.getSml140AccountSid() > 0) {
            sacMdl = sacDao.select(paramMdl.getSml140AccountSid());
        } else {
            //デフォルトのアカウントを所得
            sacMdl = sacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        }

        paramMdl.setSml140AccountSid(sacMdl.getSacSid());
        paramMdl.setSml140AccountName(sacMdl.getSacName());

       /*********************************************************
        *
        * ショートメール手動削除設定のリスト・選択値を設定
        *
        *********************************************************/

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年リスト
        for (int i = 0; i <= 10; i++) {
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setSml140YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setSml140MonthLabelList(monthLabel);

        //受信タブ処理 選択値
        paramMdl.setSml140JdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml140JdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_LIMIT)));

        //受信タブ 年
        paramMdl.setSml140JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140JYear()),
                        String.valueOf(3)));

        //受信タブ 月
        paramMdl.setSml140JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140JMonth()),
                        String.valueOf(0)));

        //送信タブ処理 選択値
        paramMdl.setSml140SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml140SdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_LIMIT)));

        //送信タブ 年
        paramMdl.setSml140SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140SYear()),
                        String.valueOf(3)));

        //送信タブ 月
        paramMdl.setSml140SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140SMonth()),
                        String.valueOf(0)));

        //草稿タブ処理 選択値
        paramMdl.setSml140WdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml140WdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_LIMIT)));

        //草稿タブ 年
        paramMdl.setSml140WYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140WYear()),
                        String.valueOf(3)));

        //草稿タブ 月
        paramMdl.setSml140WMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140WMonth()),
                        String.valueOf(0)));

        //ゴミ箱タブ処理 選択値
        paramMdl.setSml140DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml140DdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_LIMIT)));

        //ゴミ箱タブ 年
        paramMdl.setSml140DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140DYear()),
                        String.valueOf(3)));

        //ゴミ箱タブ 月
        paramMdl.setSml140DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml140DMonth()),
                        String.valueOf(0)));
    }
}