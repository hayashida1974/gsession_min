package jp.groupsession.v2.man.man200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 パスワードルール設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man200Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Man200ParamModel paramMdl)
    throws SQLException {

        CmnPswdConfDao dao = new CmnPswdConfDao(con);
        CmnPswdConfModel model = dao.select();

        if (model != null) {

            paramMdl.setMan200CoeKbn(model.getPwcCoe());
            paramMdl.setMan200UppercaseKbn(model.getPwcUppercase());
            paramMdl.setMan200UidPswdKbn(model.getPwcUidPswd());
            paramMdl.setMan200OldPswdKbn(model.getPwcOldPswd());

            int limit = model.getPwcLimitDay();

            if (limit == -1) {
                paramMdl.setMan200LimitDay("");
                paramMdl.setMan200LimitKbn(GSConstMain.PWC_LIMITKBN_OFF);
            } else {
                paramMdl.setMan200LimitDay(String.valueOf(limit));
                paramMdl.setMan200LimitKbn(GSConstMain.PWC_LIMITKBN_ON);
            }

            paramMdl.setMan200Digit(model.getPwcDigit());

        } else {
            paramMdl.setMan200CoeKbn(GSConstMain.PWC_COEKBN_OFF);
            paramMdl.setMan200UppercaseKbn(GSConstMain.PWC_UPPERCASE_OFF);
            paramMdl.setMan200LimitKbn(GSConstMain.PWC_LIMITKBN_OFF);
            paramMdl.setMan200UidPswdKbn(GSConstMain.PWC_UIDPSWDKBN_OFF);
            paramMdl.setMan200OldPswdKbn(GSConstMain.PWC_OLDPSWDKBN_OFF);
            paramMdl.setMan200LimitDay("");
            paramMdl.setMan200Digit(GSConstMain.DEFAULT_DIGIT);
        }

        //パスワード編集区分取得
        MainCommonBiz manCmnBiz = new MainCommonBiz();
        CmnPconfEditModel pconfEditMdl = new CmnPconfEditModel();
        pconfEditMdl = manCmnBiz.getCpeConf(0, con);
        paramMdl.setManPasswordKbn(pconfEditMdl.getCpePasswordKbn());
    }

    /**
     * <br>[機  能] 常に表示する値を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(RequestModel reqMdl, Man200ParamModel paramMdl) throws SQLException {

        // 桁数コンボ設定
        paramMdl.setMan200DigitLabelList(__getDigitLabel(reqMdl));
    }

    /**
     * <br>[機  能] 桁数コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * <br>
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  桁数コンボ
     */
    private ArrayList<LabelValueBean> __getDigitLabel(RequestModel reqMdl) {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (int digit : GSConstMain.PSWD_DIGIT_COMBO) {
            labelList.add(
                new LabelValueBean(
                    gsMsg.getMessage("cmn.digit.more", new String[] {String.valueOf(digit)}),
                    String.valueOf(digit)));
        }
        return labelList;
    }
}