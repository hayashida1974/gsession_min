package jp.groupsession.v2.sml.sml160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 管理者設定 手動削除画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml160Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml160ParamModel paramMdl, Connection con)
        throws SQLException {


        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = new SmlAccountModel();
        int usrSid = reqMdl.getSmodel().getUsrsid();
        if (paramMdl.getSml160AccountSid() > 0) {
            sacMdl = sacDao.select(paramMdl.getSml160AccountSid());
        } else if (usrSid != GSConst.SYSTEM_USER_ADMIN) {
            //デフォルトのアカウントを所得
            sacMdl = sacDao.selectFromUsrSid(usrSid);
        }

        if  (sacMdl != null) {
            paramMdl.setSml160AccountSid(sacMdl.getSacSid());
            paramMdl.setSml160AccountName(sacMdl.getSacName());
        }

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
        paramMdl.setSml160YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setSml160MonthLabelList(monthLabel);

        //受信タブ処理 選択値
        paramMdl.setSml160JdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml160JdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_NO)));

        //受信タブ 年
        paramMdl.setSml160JYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160JYear()),
                        String.valueOf(3)));

        //受信タブ 月
        paramMdl.setSml160JMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160JMonth()),
                        String.valueOf(0)));

        //送信タブ処理 選択値
        paramMdl.setSml160SdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml160SdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_NO)));

        //送信タブ 年
        paramMdl.setSml160SYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160SYear()),
                        String.valueOf(3)));

        //送信タブ 月
        paramMdl.setSml160SMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160SMonth()),
                        String.valueOf(0)));

        //草稿タブ処理 選択値
        paramMdl.setSml160WdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml160WdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_NO)));

        //草稿タブ 年
        paramMdl.setSml160WYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160WYear()),
                        String.valueOf(3)));

        //草稿タブ 月
        paramMdl.setSml160WMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160WMonth()),
                        String.valueOf(0)));

        //ゴミ箱タブ処理 選択値
        paramMdl.setSml160DdelKbn(
                NullDefault.getStringZeroLength(
                        paramMdl.getSml160DdelKbn(),
                        String.valueOf(GSConstSmail.SML_AUTO_DEL_NO)));

        //ゴミ箱タブ 年
        paramMdl.setSml160DYear(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160DYear()),
                        String.valueOf(3)));

        //ゴミ箱タブ 月
        paramMdl.setSml160DMonth(
                NullDefault.getStringZeroLength(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml160DMonth()),
                        String.valueOf(0)));
    }
}