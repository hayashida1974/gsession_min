package jp.groupsession.v2.wml.wml230;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlFilterConditionModel;
import jp.groupsession.v2.wml.model.base.WmlFilterModel;
import jp.groupsession.v2.wml.wml010.Wml010Biz;
import jp.groupsession.v2.wml.wml140.Wml140Form;

/**
 * <br>[機  能] WEBメール 管理者設定 フィルタ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml230Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Wml230ParamModel paramMdl, int userSid,
                            RequestModel reqMdl)
    throws SQLException {

        //アカウント名取得
        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(paramMdl.getWmlAccountSid());
        paramMdl.setWml220accountName(wacMdl.getWacName());

        WmlBiz comonBiz = new WmlBiz();

        //ラベルリスト取得
        Wml230Dao dao = new Wml230Dao(con);
        List<LabelDataModel> wldMdlList = dao.getLabelList(paramMdl.getWmlAccountSid());
        paramMdl.setLbList(comonBiz.getLbCombo(reqMdl, wldMdlList));

        //条件コンボをセットする
        paramMdl.setConditionList1(getConditionComb1(reqMdl));
        paramMdl.setConditionList2(getConditionComb2(reqMdl));

        //初期表示　編集
        if (paramMdl.getWml230initFlg() == GSConstWebmail.DSP_FIRST
                && paramMdl.getWmlFilterCmdMode() == GSConstWebmail.CMDMODE_EDIT) {

            //フィルター情報設定
            int wfiSid = paramMdl.getWmlEditFilterId();
            WmlFilterDao filterDao = new WmlFilterDao(con);
            WmlFilterModel filterData = filterDao.select(wfiSid);

            paramMdl.setWml230tempFile(String.valueOf(filterData.getWftTempfile()));
            paramMdl.setWml230actionLabel(String.valueOf(filterData.getWftActionLabel()));

            if (filterData.getWftActionLabel() == 1) {
                paramMdl.setWml230actionLabelValue(String.valueOf(filterData.getWlbSid()));
            }

            paramMdl.setWml230actionRead(String.valueOf(filterData.getWftActionRead()));
            paramMdl.setWml230actionSend(String.valueOf(filterData.getWftActionForward()));

            if (filterData.getWftActionFolder() != GSConstWebmail.FILTER_MOVE_NOSET) {
                paramMdl.setWml230actionMove(String.valueOf(Wml140Form.WML140_MOVEFOLDER_DO));
                paramMdl.setWml230actionFolderValue(
                        String.valueOf(filterData.getWftActionFolder()));
            }

            if (filterData.getWftActionForward() == 1) {
                String[] fwAddress = new String[1];

                WmlFilterFwaddressDao fwAddressDao = new WmlFilterFwaddressDao(con);
                List<String> addressList = fwAddressDao.getAddressList(filterData.getWftSid());
                if (!addressList.isEmpty()) {
                    fwAddress = addressList.toArray(new String[addressList.size()]);
                }
                paramMdl.setWml230actionSendValue(fwAddress);
            }

            paramMdl.setWml230FilterName(filterData.getWftName());
            paramMdl.setWml230filterType(filterData.getWftCondition());

            //フィルター条件設定
            List<WmlFilterConditionModel> wfcMdlList = new ArrayList<WmlFilterConditionModel>();
            wfcMdlList = dao.selectFilCon(wfiSid);


            for (int i = 0; i < wfcMdlList.size(); i++) {

                //条件１
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_ONE) {
                    paramMdl.setWml230condition1(String.valueOf(GSConstWebmail.CONDITION_ONE));
                    paramMdl.setWml230conditionType1(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml230conditionExs1(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml230conditionText1(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件２
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_TWO) {
                    paramMdl.setWml230condition2(String.valueOf(GSConstWebmail.CONDITION_TWO));
                    paramMdl.setWml230conditionType2(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml230conditionExs2(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml230conditionText2(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件３
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_THREE) {
                    paramMdl.setWml230condition3(String.valueOf(GSConstWebmail.CONDITION_THREE));
                    paramMdl.setWml230conditionType3(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml230conditionExs3(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml230conditionText3(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件４
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_FOUR) {
                    paramMdl.setWml230condition4(String.valueOf(GSConstWebmail.CONDITION_FOUR));
                    paramMdl.setWml230conditionType4(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml230conditionExs4(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml230conditionText4(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件５
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_FIVE) {
                    paramMdl.setWml230condition5(String.valueOf(GSConstWebmail.CONDITION_FIVE));
                    paramMdl.setWml230conditionType5(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml230conditionExs5(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml230conditionText5(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }
            }

            //初期表示完了
            paramMdl.setWml230initFlg(GSConstWebmail.DSP_ALREADY);
        }

        if (paramMdl.getWml230viewMailList() == 1) {
            setMailList(con, paramMdl, userSid);
        }
    }

    /**
     * <br>[機  能] 条件コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  条件コンボ
     */
    public List<LabelValueBean> getConditionComb1(RequestModel reqMdl) {
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.subject"),
                                String.valueOf(GSConstWebmail.FILTER_TYPE_TITLE)));
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.from"),
                                String.valueOf(GSConstWebmail.FILTER_TYPE_ADDRESS)));
        labelList.add(new LabelValueBean("CC",
                                String.valueOf(GSConstWebmail.FILTER_TYPE_CC)));
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.sendfrom"),
                                String.valueOf(GSConstWebmail.FILTER_TYPE_SEND)));
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.body"),
                                String.valueOf(GSConstWebmail.FILTER_TYPE_MAIN)));

        return labelList;
    }

    /**
     * <br>[機  能] 条件コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ArrayList (in LabelValueBean)  条件コンボ
     */
    public List<LabelValueBean> getConditionComb2(RequestModel reqMdl) {
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        labelList.add(new LabelValueBean(gsMsg.getMessage("wml.141"), "0"));
        labelList.add(new LabelValueBean(gsMsg.getMessage("wml.140"), "1"));
        return labelList;
    }

    /**
     * <br>[機  能] フィルタリング対象のメール一覧に関する情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setMailList(Connection con, Wml230ParamModel paramMdl, int userSid)
    throws SQLException {
        int page = paramMdl.getWml230mailListPageTop();

        if (page <= 0) {
            page = 1;
        }
        int start = PageUtil.getRowNumber(page, Wml010Biz.MAILLIST_MAXCOUNT);
        Wml230Dao dao230 = new Wml230Dao(con);
        long maxCount = dao230.getMailCount(paramMdl, userSid);
        int maxPageNum = PageUtil.getPageCount(maxCount, Wml010Biz.MAILLIST_MAXCOUNT);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, Wml010Biz.MAILLIST_MAXCOUNT);
        if (maxPageStartRow < start) {
            page = maxPageNum;
            start = maxPageStartRow;
        }

        if (maxPageNum > 1) {
            paramMdl.setWml230mailListPageCombo(
                        PageUtil.createPageOptions(maxCount, Wml010Biz.MAILLIST_MAXCOUNT));
        }

        paramMdl.setWml230mailList(
                dao230.getMailList(paramMdl, userSid, start, Wml010Biz.MAILLIST_MAXCOUNT));
    }
}
