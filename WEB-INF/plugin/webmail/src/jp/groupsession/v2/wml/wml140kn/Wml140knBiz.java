package jp.groupsession.v2.wml.wml140kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterConditionDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterSortDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.MailFilterConditionModel;
import jp.groupsession.v2.wml.model.MailFilterModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlFilterConditionModel;
import jp.groupsession.v2.wml.model.base.WmlFilterFwaddressModel;
import jp.groupsession.v2.wml.model.base.WmlFilterModel;
import jp.groupsession.v2.wml.model.base.WmlFilterSortModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.wml140.Wml140Biz;
import jp.groupsession.v2.wml.wml140.Wml140Form;
import jp.groupsession.v2.wml.wml140.Wml140ParamModel;

/**
 * <br>[機  能] WEBメール フィルタ登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml140knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml140knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml140knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml140knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, RequestModel reqMdl,
                            Wml140knParamModel paramMdl, int userSid) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(paramMdl.getWml130accountSid());
        paramMdl.setWml130account(wacMdl.getWacName());

        //フィルター条件
        if (paramMdl.getWml140filterType() == 0) {
            paramMdl.setWml140filterTypeView(gsMsg.getMessage(GSConstWebmail.ALL_CONDITION));
        } else {
            paramMdl.setWml140filterTypeView(gsMsg.getMessage(GSConstWebmail.ANY_CONDITION));
        }

        //条件１
        if (paramMdl.getWml140condition1() != null) {
            paramMdl.setWml140conditionType1View(
                    __getViewCondition(paramMdl.getWml140conditionType1(), gsMsg));

            paramMdl.setWml140conditionExs1View(
                    __getViewConditionExs(paramMdl.getWml140conditionExs1(), gsMsg));
        }

        //条件２
        if (paramMdl.getWml140condition2() != null) {
            paramMdl.setWml140conditionType2View(
                    __getViewCondition(paramMdl.getWml140conditionType2(), gsMsg));

            paramMdl.setWml140conditionExs2View(
                    __getViewConditionExs(paramMdl.getWml140conditionExs2(), gsMsg));
        }

        //条件３
        if (paramMdl.getWml140condition3() != null) {
            paramMdl.setWml140conditionType3View(
                    __getViewCondition(paramMdl.getWml140conditionType3(), gsMsg));

            paramMdl.setWml140conditionExs3View(
                    __getViewConditionExs(paramMdl.getWml140conditionExs3(), gsMsg));
        }

        //条件４
        if (paramMdl.getWml140condition4() != null) {
            paramMdl.setWml140conditionType4View(
                    __getViewCondition(paramMdl.getWml140conditionType4(), gsMsg));

            paramMdl.setWml140conditionExs4View(
                    __getViewConditionExs(paramMdl.getWml140conditionExs4(), gsMsg));
        }

        //条件５
        if (paramMdl.getWml140condition5() != null) {
            paramMdl.setWml140conditionType5View(
                    __getViewCondition(paramMdl.getWml140conditionType5(), gsMsg));

            paramMdl.setWml140conditionExs5View(
                    __getViewConditionExs(paramMdl.getWml140conditionExs5(), gsMsg));
        }

        //ラベル名設定
        if (!(paramMdl.getWml140actionLabel().equals("0"))) {
            int wlbSid = Integer.parseInt(paramMdl.getWml140actionLabelValue());
            WmlLabelDao labelDao = new WmlLabelDao(con);
            WmlLabelModel labelData = labelDao.select(wlbSid);
            paramMdl.setWml140LabelView(labelData.getWlbName());
        }

        //フィルタリング対象のメール一覧
        Wml140Biz biz140 = new Wml140Biz();
        if (paramMdl.getWml140doFilter() == Wml140Form.WML140_DOFILTER_YES) {
            biz140.setMailList(con, paramMdl, userSid);
        }

        //メール転送許可フラグを設定
        paramMdl.setWml140fwLimitFlg(biz140.getFwLimitFlg(con));
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void doAddEdit(
        Wml140knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException, IOToolsException {

        boolean commitFlg = false;
        try {
            con__.setAutoCommit(false);

            WmlFilterModel wftMdl = null;
            //処理モード(新規登録）
            if (paramMdl.getWmlFilterCmdMode() == GSConstWebmail.CMDMODE_ADD) {
                //新規登録
                log__.debug("新規登録");
                wftMdl = doInsert(paramMdl, userSid, cntCon);

            //編集
            } else if (paramMdl.getWmlFilterCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
                log__.debug("編集");
                wftMdl = doUpdate(paramMdl, userSid);
            }

            if (paramMdl.getWml140doFilter() == Wml140knForm.WML140_DOFILTER_YES) {

                //フィルター情報
                MailFilterModel filterMdl = new MailFilterModel();
                filterMdl.setWftSid(wftMdl.getWftSid());
                filterMdl.setTempFile(wftMdl.getWftTempfile()
                                        == GSConstWebmail.FILTER_TEMPFILE_YES);
                filterMdl.setLabel(wftMdl.getWftActionLabel()
                                        == GSConstWebmail.FILTER_LABEL_SETLABEL);
                filterMdl.setLabelSid(wftMdl.getWlbSid());
                filterMdl.setReaded(wftMdl.getWftActionRead()
                                        == GSConstWebmail.FILTER_READED_SETREADED);

                filterMdl.setMove(wftMdl.getWftActionFolder());
                filterMdl.setCondition(wftMdl.getWftCondition());

                //フィルター条件
                WmlFilterConditionDao conditionDao = new WmlFilterConditionDao(con__);
                List<WmlFilterConditionModel> wfcList = conditionDao.select(wftMdl.getWftSid());
                List<MailFilterConditionModel> conditionList
                    = new ArrayList<MailFilterConditionModel>();
                for (WmlFilterConditionModel wfcData : wfcList) {
                    MailFilterConditionModel conditionMdl = new MailFilterConditionModel();
                    conditionMdl.setType(wfcData.getWfcType());
                    conditionMdl.setExpression(wfcData.getWfcExpression());
                    conditionMdl.setText(wfcData.getWfcText());

                    conditionList.add(conditionMdl);
                }

                //フィルタリング対象のアカウント
                int[] wacSidList = new int[]{paramMdl.getWml130accountSid()};

                //メールのフィルタリングを行う
                WmlBiz wmlBiz = new WmlBiz();
                WebmailDao webmailDao = new WebmailDao(con__);
                for (int wacSid : wacSidList) {
                    long movetoWdrSid = -1;
                    if (filterMdl.getMove() == GSConstWebmail.FILTER_MOVE_DUST) {
                        movetoWdrSid = wmlBiz.getDirectorySid(con__, wacSid,
                                                            GSConstWebmail.DIR_TYPE_DUST);
                    }
                    if (filterMdl.getMove() == GSConstWebmail.FILTER_MOVE_STRAGE) {
                        movetoWdrSid = wmlBiz.getDirectorySid(con__, wacSid,
                                                            GSConstWebmail.DIR_TYPE_STORAGE);
                    }
                    webmailDao.updateFilterlingMail(filterMdl, conditionList,
                            wacSid, movetoWdrSid);

                }
            }

            con__.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 新規登録画面処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @return フィルター情報
     * @throws SQLException SQL実行例外
     */
    public WmlFilterModel doInsert(
        Wml140knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException {

        //フィルターSID採番
        int wftSid = (int) cntCon.getSaibanNumber(
                GSConstWebmail.SBNSID_WEBMAIL,
                GSConstWebmail.SBNSID_SUB_FILTER,
                userSid);

        //フィルターを登録する
        WmlFilterModel wfMdl  = __getFilterInsertMdl(paramMdl, userSid, wftSid);
        WmlFilterDao wfDao = new WmlFilterDao(con__);
        wfDao.insert(wfMdl);

        //フィルター_転送指定アドレスを登録する
        __entryFwAddress(paramMdl, wfMdl.getWftSid());

        //フィルター条件を登録する
        WmlFilterConditionDao wfcDao = new WmlFilterConditionDao(con__);
        List<WmlFilterConditionModel> conditionList = __getFilterConditionList(paramMdl, wftSid);
        for (WmlFilterConditionModel conditionMdl : conditionList) {
            wfcDao.insert(conditionMdl);
        }

        //フィルター適用順を登録する
        WmlFilterSortDao wfsDao = new WmlFilterSortDao(con__);
        WmlFilterSortModel wfsMdl  = __getFilterSortInsertMdl(paramMdl, wftSid);
        wfsDao.insert(wfsMdl);

        return wfMdl;
    }

    /**
     * <br>[機  能] 編集画面登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @return フィルター情報
     * @throws SQLException SQL実行例外
     */
    public WmlFilterModel doUpdate(
        Wml140knParamModel paramMdl,
        int userSid) throws SQLException {

        List<WmlFilterConditionModel> conditionList = null;

        int wftSid = paramMdl.getWmlEditFilterId();
        con__.setAutoCommit(false);

        WmlFilterDao wfdao = new WmlFilterDao(con__);

        //フィルター更新Model
        WmlFilterModel wfMdl  = __getFilterUpdateMdl(paramMdl, userSid);
        //フィルターを更新する
        wfdao.update(wfMdl);

        //フィルター_転送指定アドレスを登録する
        __entryFwAddress(paramMdl, wfMdl.getWftSid());

        //フィルター条件を削除する
        Wml140knDao dao = new Wml140knDao(con__);
        dao.deleteFilter(wftSid);

        //フィルター条件を登録する
        WmlFilterConditionDao wfcDao = new WmlFilterConditionDao(con__);
        conditionList = __getFilterConditionList(paramMdl, wftSid);
        for (WmlFilterConditionModel conditionMdl : conditionList) {
            wfcDao.insert(conditionMdl);
        }

        return wfMdl;
    }

    /**
     * <br>[機  能] 新規登録用のWmlFilterModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @param wftSid フィルターSID
     * @return WmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterModel __getFilterInsertMdl(
        Wml140knParamModel paramMdl,
        int userSid,
        int wftSid) throws SQLException {

        WmlFilterModel wfMdl = new WmlFilterModel();

        wfMdl.setWftSid(wftSid);
        __setFilterEntryMdl(wfMdl, paramMdl, userSid);

        wfMdl.setWacSid(paramMdl.getWml130accountSid());

        return wfMdl;
    }

    /**
     * <br>[機  能] 更新用のWmlFilterModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @return WmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterModel __getFilterUpdateMdl(
        Wml140knParamModel paramMdl,
        int userSid) throws SQLException {

        WmlFilterModel wfMdl = new WmlFilterModel();

        wfMdl.setWftSid(paramMdl.getWmlEditFilterId());
        __setFilterEntryMdl(wfMdl, paramMdl, userSid);

        //アカウントSID
        wfMdl.setWacSid(paramMdl.getWml130accountSid());

        return wfMdl;
    }

    /**
     * <br>[機  能] フィルター情報Modelへパラメータ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param wfMdl フィルター情報Model
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     */
    private void __setFilterEntryMdl(WmlFilterModel wfMdl, Wml140knParamModel paramMdl,
                                                    int userSid) {
        wfMdl.setUsrSid(userSid);
        wfMdl.setWftName(paramMdl.getWml140FilterName());
        wfMdl.setWftType(GSConstWebmail.WFT_TYPE_ONE);
        wfMdl.setWftCondition(paramMdl.getWml140filterType());
        wfMdl.setWftTempfile(Integer.parseInt(paramMdl.getWml140tempFile()));
        wfMdl.setWftActionLabel(Integer.parseInt(paramMdl.getWml140actionLabel()));
        wfMdl.setWftActionRead(Integer.parseInt(paramMdl.getWml140actionRead()));
        if (String.valueOf(GSConstWebmail.FILTER_MOVE_NOSET)
                .equals(paramMdl.getWml140actionMove())) {
            wfMdl.setWftActionFolder(GSConstWebmail.FILTER_MOVE_NOSET);

        } else {
            wfMdl.setWftActionFolder(Integer.parseInt(paramMdl.getWml140actionFolderValue()));
        }
        wfMdl.setWftActionForward(Integer.parseInt(paramMdl.getWml140actionSend()));

        //ラベルを設定する場合
        if (paramMdl.getWml140actionLabel().equals("1")) {
            wfMdl.setWlbSid(Integer.parseInt(paramMdl.getWml140actionLabelValue()));
        }
    }

    /**
     * <br>[機  能] 新規登録用のWmlFilterModel一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param wftSid フィルターSID
     * @return WmlFilterModel一覧
     * @throws SQLException SQL実行例外
     */
    private List<WmlFilterConditionModel> __getFilterConditionList(
        Wml140knParamModel paramMdl, int wftSid) throws SQLException {

        List<WmlFilterConditionModel> conditionList = new ArrayList<WmlFilterConditionModel>();

        if (paramMdl.getWml140condition1().equals(String.valueOf(GSConstWebmail.CONDITION_ONE))) {
            conditionList.add(__createFilterConditionModel(wftSid,
                            GSConstWebmail.CONDITION_ONE,
                            paramMdl.getWml140conditionType1(),
                            paramMdl.getWml140conditionExs1(),
                            paramMdl.getWml140conditionText1()));
        }

        if (paramMdl.getWml140condition2().equals(String.valueOf(GSConstWebmail.CONDITION_TWO))) {
            conditionList.add(__createFilterConditionModel(wftSid,
                            GSConstWebmail.CONDITION_TWO,
                            paramMdl.getWml140conditionType2(),
                            paramMdl.getWml140conditionExs2(),
                            paramMdl.getWml140conditionText2()));
        }

        if (paramMdl.getWml140condition3().equals(String.valueOf(GSConstWebmail.CONDITION_THREE))) {
            conditionList.add(__createFilterConditionModel(wftSid,
                            GSConstWebmail.CONDITION_THREE,
                            paramMdl.getWml140conditionType3(),
                            paramMdl.getWml140conditionExs3(),
                            paramMdl.getWml140conditionText3()));
        }

        if (paramMdl.getWml140condition4().equals(String.valueOf(GSConstWebmail.CONDITION_FOUR))) {
            conditionList.add(__createFilterConditionModel(wftSid,
                            GSConstWebmail.CONDITION_FOUR,
                            paramMdl.getWml140conditionType4(),
                            paramMdl.getWml140conditionExs4(),
                            paramMdl.getWml140conditionText4()));
        }

        if (paramMdl.getWml140condition5().equals(String.valueOf(GSConstWebmail.CONDITION_FIVE))) {
            conditionList.add(__createFilterConditionModel(wftSid,
                            GSConstWebmail.CONDITION_FIVE,
                            paramMdl.getWml140conditionType5(),
                            paramMdl.getWml140conditionExs5(),
                            paramMdl.getWml140conditionText5()));
        }

        return conditionList;
    }

    /**
     * <br>[機  能] フィルター条件Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wftSid フィルターSID
     * @param number 番号
     * @param type 種別
     * @param exs 条件式
     * @param text 条件
     * @return フィルター条件Model
     */
    private WmlFilterConditionModel __createFilterConditionModel(int wftSid, int number,
                                                                String type, String exs,
                                                                String text) {

        WmlFilterConditionModel wfcMdl = new WmlFilterConditionModel();
        wfcMdl.setWftSid(wftSid);
        wfcMdl.setWfcNum(number);
        wfcMdl.setWfcType(Integer.parseInt(type));
        wfcMdl.setWfcExpression(Integer.parseInt(exs));
        wfcMdl.setWfcText(text);

        return wfcMdl;
    }

    /**
     * <br>[機  能] 新規登録用のWmlFilterSortModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param fiSaiSid 採番SID
     * @return WmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private WmlFilterSortModel __getFilterSortInsertMdl(
        Wml140knParamModel paramMdl,
        int fiSaiSid) throws SQLException {

        WmlFilterSortModel wfsMdl = new WmlFilterSortModel();
        WmlFilterSortDao wfsdao = new WmlFilterSortDao(con__);

        wfsMdl.setWacSid(paramMdl.getWml130accountSid());
        wfsMdl.setWftSid(fiSaiSid);
        wfsMdl.setWfsSort(wfsdao.maxSortNumber() + 1);

        return wfsMdl;
    }

    /**
     * <br>[機  能] フィルター条件(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param conditionType フィルター条件
     * @param gsMsg GSMessage
     * @return フィルター条件(表示用)
     */
    private String __getViewCondition(String conditionType, GsMessage gsMsg) {
        String viewCondition = "";
        if (conditionType.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_TITLE))) {
           viewCondition = gsMsg.getMessage(GSConstWebmail.FILTER_TITLE);

        } else if (conditionType.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_ADDRESS))) {
            viewCondition = gsMsg.getMessage(GSConstWebmail.FILTER_ADDRESS);

        } else if (conditionType.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_CC))) {
            viewCondition = "CC";

        } else if (conditionType.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_SEND))) {
            viewCondition = gsMsg.getMessage(GSConstWebmail.FILTER_SEND);

        } else if (conditionType.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_MAIN))) {
            viewCondition = gsMsg.getMessage(GSConstWebmail.FILTER_MAIN);
        }

        return viewCondition;
    }

    /**
     * <br>[機  能] フィルター条件 条件指定(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param conditionExs  条件指定
     * @param gsMsg GSMessage
     * @return 条件指定(表示用)
     */
    private String __getViewConditionExs(String conditionExs, GsMessage gsMsg) {
        String viewConditionExs = "";
        if (conditionExs.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_INCLUDE))) {
            viewConditionExs = gsMsg.getMessage(GSConstWebmail.FILTER_INCLUDE);

        } else if (conditionExs.equals(String.valueOf(GSConstWebmail.FILTER_TYPE_EXCLUDE))) {
            viewConditionExs = gsMsg.getMessage(GSConstWebmail.FILTER_EXCLUDE);
        }

        return viewConditionExs;
    }

    /**
     * <br>[機  能] フィルター_転送指定アドレスを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param wftSid フィルターSID
     * @throws SQLException SQL実行時例外
     */
    private void __entryFwAddress(Wml140knParamModel paramMdl, int wftSid)
    throws SQLException {
        Wml140Biz biz140 = new Wml140Biz();
        if (biz140.getFwLimitFlg(con__) != Wml140ParamModel.FWLIMIT_PROHIBITED) {
            WmlFilterFwaddressDao wfFwadrDao = new WmlFilterFwaddressDao(con__);
            wfFwadrDao.delete(wftSid);
            if (paramMdl.getWml140actionSend().equals("1")) {
                WmlFilterFwaddressModel wfFwadrMdl = new WmlFilterFwaddressModel();
                wfFwadrMdl.setWftSid(wftSid);

                int wfaNo = 1;
                for (String fwAddress : paramMdl.getWml140actionSendValue()) {
                    wfFwadrMdl.setWfaNo(wfaNo++);
                    wfFwadrMdl.setWfaAddress(fwAddress);
                    wfFwadrDao.insert(wfFwadrMdl);
                }
            }
        }
    }
}
