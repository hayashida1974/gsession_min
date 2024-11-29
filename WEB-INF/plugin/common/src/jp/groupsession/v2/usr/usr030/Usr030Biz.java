package jp.groupsession.v2.usr.usr030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr030Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr030Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 画面に常に表示する値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr030ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(Usr030ParamModel paramMdl, Connection con)
    throws SQLException {

        //グループリスト
        setGroupList(paramMdl, con);
        //都道府県ラベル
        setTdfkList(paramMdl, con);
        //役職ラベル
        setPosList(paramMdl, con);
        //性別ラベル
        setSeibetuList(paramMdl, con);
        //年月日コンボを設定
        paramMdl.setUsr030entranceYearFrLabel(__getYearLabelList(reqMdl__));   //年
        paramMdl.setUsr030entranceMonthFrLabel(__getMonthLabelList(reqMdl__)); //月
        paramMdl.setUsr030entranceDayFrLabel(__getDayLabelList(reqMdl__));     //日

    }

    /**
     * <br>[機  能] 都道府県リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr030ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setTdfkList(Usr030ParamModel paramMdl,
                             Connection con)
        throws SQLException {

        //都道府県ラベル
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setTdfkLabelList(cmnBiz.getTdfkLabelList(con, gsMsg));
    }

    /**
     * <br>[機  能] 役職リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr030ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setPosList(Usr030ParamModel paramMdl, Connection con)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //役職ラベル
        PosBiz pBiz = new PosBiz();
        paramMdl.setPosLabelList(pBiz.getPosSearchLabelList(con, gsMsg));
    }

    /**
     * <br>[機  能] 性別リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setSeibetuList(Usr030ParamModel paramMdl, Connection con)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("ntp.166"),
                        String.valueOf("-1")));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.notset"),
                        String.valueOf(GSConstUser.SEIBETU_UNSET)));

        labelList.add(
                new LabelValueBean(gsMsg.getMessage("user.124"),
                        String.valueOf(GSConstUser.SEIBETU_MAN)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("user.125"),
                        String.valueOf(GSConstUser.SEIBETU_WOMAN)));

        //性別ラベル
        paramMdl.setSeibetuLabelList(labelList);
    }

    /**
     * <br>[機  能] グループコンボのリストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr030ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setGroupList(Usr030ParamModel paramMdl,
            Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz biz = new GroupBiz(); //
        ArrayList < LabelValueBean > grpLabel = biz.getGroupCombLabelList(con, true, gsMsg);
        paramMdl.setGrpLabelList(grpLabel);
    }

    /**
     * 年コンボを作成する
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getYearLabelList(RequestModel reqMdl) {

        int sysYear = (new UDate()).getYear();
        int start = sysYear - 50;
        int end = sysYear + 0;
        GsMessage gsMsg = new GsMessage(reqMdl);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        labelList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.notset"), ""));

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                            gsMsg.getMessage(
                                    "cmn.year",
                                    new String[] {String.valueOf(value)}), String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * 月コンボを作成する
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getMonthLabelList(RequestModel reqMdl) {

        int start = 1;
        int end = 12;
        GsMessage gsMsg = new GsMessage(reqMdl);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        labelList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.notset"), ""));

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                            value + gsMsg.getMessage("cmn.month"), String.valueOf(value)));
        }
        return labelList;
    }
    /**
     * 日コンボを作成する
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getDayLabelList(RequestModel reqMdl) {

        int start = 1;
        int end = 31;

        GsMessage gsMsg = new GsMessage();
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        labelList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.notset"), ""));

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                             value + gsMsg.getMessage("cmn.day"), String.valueOf(value)));
        }
        return labelList;
    }
}
