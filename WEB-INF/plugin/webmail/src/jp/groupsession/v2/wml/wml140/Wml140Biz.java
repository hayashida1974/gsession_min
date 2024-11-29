package jp.groupsession.v2.wml.wml140;

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
import jp.groupsession.v2.wml.biz.WmlLabelBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlFilterConditionModel;
import jp.groupsession.v2.wml.model.base.WmlFilterModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.wml010.Wml010Biz;

/**
 * <br>[機  能] WEBメール フィルタ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml140Biz {

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
    public void setInitData(Connection con, Wml140ParamModel paramMdl, int userSid,
                            RequestModel reqMdl)
    throws SQLException {

        //アカウント名取得
        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(paramMdl.getWml130accountSid());
        paramMdl.setWml130account(wacMdl.getWacName());

        WmlBiz comonBiz = new WmlBiz();

        //ラベルリスト取得
        WmlLabelDao wlbDao = new WmlLabelDao(con);
        List<WmlLabelModel> wldMdlList = new ArrayList<WmlLabelModel>();
        wldMdlList = wlbDao.getLabelList(paramMdl.getWml130accountSid());

        WmlLabelBiz labelBiz = new WmlLabelBiz();
        List<LabelDataModel> labelDataList = labelBiz.getLabelDataList(wldMdlList);
        paramMdl.setLbList(comonBiz.getLbCombo(reqMdl, labelDataList));

        //条件コンボをセットする
        paramMdl.setConditionList1(getConditionComb1(reqMdl));
        paramMdl.setConditionList2(getConditionComb2(reqMdl));

        //初期表示　編集
        if (paramMdl.getWml140initFlg() == GSConstWebmail.DSP_FIRST
                && paramMdl.getWmlFilterCmdMode() == GSConstWebmail.CMDMODE_EDIT) {

            //フィルター情報設定
            int wfiSid = paramMdl.getWmlEditFilterId();
            WmlFilterDao filterDao = new WmlFilterDao(con);
            WmlFilterModel filterData = filterDao.select(wfiSid);

            paramMdl.setWml140tempFile(String.valueOf(filterData.getWftTempfile()));
            paramMdl.setWml140actionLabel(String.valueOf(filterData.getWftActionLabel()));

            if (filterData.getWftActionLabel() == 1) {
                paramMdl.setWml140actionLabelValue(String.valueOf(filterData.getWlbSid()));
            }

            paramMdl.setWml140actionRead(String.valueOf(filterData.getWftActionRead()));
            paramMdl.setWml140actionSend(String.valueOf(filterData.getWftActionForward()));

            if (filterData.getWftActionFolder() != GSConstWebmail.FILTER_MOVE_NOSET) {
                paramMdl.setWml140actionMove(String.valueOf(Wml140Form.WML140_MOVEFOLDER_DO));
                paramMdl.setWml140actionFolderValue(
                        String.valueOf(filterData.getWftActionFolder()));
            }



            if (filterData.getWftActionForward() == 1) {
                String[] fwAddress = new String[1];

                WmlFilterFwaddressDao fwAddressDao = new WmlFilterFwaddressDao(con);
                List<String> addressList = fwAddressDao.getAddressList(filterData.getWftSid());
                if (!addressList.isEmpty()) {
                    fwAddress = addressList.toArray(new String[addressList.size()]);
                }
                paramMdl.setWml140actionSendValue(fwAddress);
            }

            paramMdl.setWml140FilterName(filterData.getWftName());
            paramMdl.setWml140filterType(filterData.getWftCondition());

            //フィルター条件設定
            List<WmlFilterConditionModel> wfcMdlList = new ArrayList<WmlFilterConditionModel>();
            Wml140Dao dao = new Wml140Dao(con);
            wfcMdlList = dao.selectFilCon(wfiSid);

            for (int i = 0; i < wfcMdlList.size(); i++) {

                //条件１
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_ONE) {
                    paramMdl.setWml140condition1(String.valueOf(GSConstWebmail.CONDITION_ONE));
                    paramMdl.setWml140conditionType1(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml140conditionExs1(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml140conditionText1(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件２
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_TWO) {
                    paramMdl.setWml140condition2(String.valueOf(GSConstWebmail.CONDITION_TWO));
                    paramMdl.setWml140conditionType2(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml140conditionExs2(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml140conditionText2(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件３
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_THREE) {
                    paramMdl.setWml140condition3(String.valueOf(GSConstWebmail.CONDITION_THREE));
                    paramMdl.setWml140conditionType3(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml140conditionExs3(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml140conditionText3(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件４
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_FOUR) {
                    paramMdl.setWml140condition4(String.valueOf(GSConstWebmail.CONDITION_FOUR));
                    paramMdl.setWml140conditionType4(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml140conditionExs4(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml140conditionText4(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }

                //条件５
                if (wfcMdlList.get(i).getWfcNum() == GSConstWebmail.CONDITION_FIVE) {
                    paramMdl.setWml140condition5(String.valueOf(GSConstWebmail.CONDITION_FIVE));
                    paramMdl.setWml140conditionType5(
                            String.valueOf(wfcMdlList.get(i).getWfcType()));
                    paramMdl.setWml140conditionExs5(String.valueOf(
                            wfcMdlList.get(i).getWfcExpression()));
                    paramMdl.setWml140conditionText5(
                            String.valueOf(wfcMdlList.get(i).getWfcText()));
                }
            }

            //初期表示完了
            paramMdl.setWml140initFlg(GSConstWebmail.DSP_ALREADY);
        }

        if (paramMdl.getWml140viewMailList() == 1) {
            setMailList(con, paramMdl, userSid);
        }

        //メール転送許可フラグを設定
        paramMdl.setWml140fwLimitFlg(getFwLimitFlg(con));
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
    public void setMailList(Connection con, Wml140ParamModel paramMdl, int userSid)
    throws SQLException {
        int page = paramMdl.getWml140mailListPageTop();

        if (page <= 0) {
            page = 1;
        }
        int start = PageUtil.getRowNumber(page, Wml010Biz.MAILLIST_MAXCOUNT);
        Wml140Dao dao140 = new Wml140Dao(con);
        long maxCount = dao140.getMailCount(paramMdl, userSid);
        int maxPageNum = PageUtil.getPageCount(maxCount, Wml010Biz.MAILLIST_MAXCOUNT);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, Wml010Biz.MAILLIST_MAXCOUNT);
        if (maxPageStartRow < start) {
            page = maxPageNum;
            start = maxPageStartRow;
        }

        if (maxPageNum > 1) {
            paramMdl.setWml140mailListPageCombo(
                        PageUtil.createPageOptions(maxCount, Wml010Biz.MAILLIST_MAXCOUNT));
        }

        paramMdl.setWml140mailList(
                dao140.getMailList(paramMdl, userSid, start, Wml010Biz.MAILLIST_MAXCOUNT));
    }

    /**
     * <br>[機  能] メール転送許可フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return メール転送許可フラグ
     * @throws SQLException SQL実行時例外
     */
    public int getFwLimitFlg(Connection con) throws SQLException {
        int fwLimitFlg = 0;

        //メール転送許可フラグを設定
        WmlAdmConfDao adminDao = new WmlAdmConfDao(con);
        WmlAdmConfModel adminMdl = adminDao.selectAdmData();
        if (adminMdl != null && adminMdl.getWadFwlimit() == GSConstWebmail.WAD_FWLIMIT_PROHIBITED) {
            fwLimitFlg = Wml140ParamModel.FWLIMIT_PROHIBITED;
        }

        return fwLimitFlg;
    }

}
