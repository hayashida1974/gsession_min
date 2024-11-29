package jp.groupsession.v2.sml.sml340kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlFilterConditionDao;
import jp.groupsession.v2.sml.dao.SmlFilterDao;
import jp.groupsession.v2.sml.dao.SmlFilterSortDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.MailFilterConditionModel;
import jp.groupsession.v2.sml.model.MailFilterModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlFilterConditionModel;
import jp.groupsession.v2.sml.model.SmlFilterModel;
import jp.groupsession.v2.sml.model.SmlFilterSortModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.sml340.Sml340Biz;
import jp.groupsession.v2.sml.sml340.Sml340Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール 管理者設定 フィルタ登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml340knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml340knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Sml340knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Sml340knBiz(Connection con) {
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
                            Sml340knParamModel paramMdl, int userSid) throws SQLException {
        //アカウント名取得
        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = sacDao.select(paramMdl.getSmlAccountSid());
        paramMdl.setSml330accountName(sacMdl.getSacName());

        //フィルター条件
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (paramMdl.getSml340filterType() == 0) {
            paramMdl.setSml340filterTypeView(gsMsg.getMessage(GSConstSmail.ALL_CONDITION));
        } else {
            paramMdl.setSml340filterTypeView(gsMsg.getMessage(GSConstSmail.ANY_CONDITION));
        }

        //条件１
        if (paramMdl.getSml340condition1() != null) {
            paramMdl.setSml340conditionType1View(
                    __getViewCondition(paramMdl.getSml340conditionType1(), gsMsg));

            paramMdl.setSml340conditionExs1View(
                    __getViewConditionExs(paramMdl.getSml340conditionExs1(), gsMsg));
        }

        //条件２
        if (paramMdl.getSml340condition2() != null) {
            paramMdl.setSml340conditionType2View(
                    __getViewCondition(paramMdl.getSml340conditionType2(), gsMsg));

            paramMdl.setSml340conditionExs2View(
                    __getViewConditionExs(paramMdl.getSml340conditionExs2(), gsMsg));
        }

        //条件３
        if (paramMdl.getSml340condition3() != null) {
            paramMdl.setSml340conditionType3View(
                    __getViewCondition(paramMdl.getSml340conditionType3(), gsMsg));

            paramMdl.setSml340conditionExs3View(
                    __getViewConditionExs(paramMdl.getSml340conditionExs3(), gsMsg));
        }

        //条件４
        if (paramMdl.getSml340condition4() != null) {
            paramMdl.setSml340conditionType4View(
                    __getViewCondition(paramMdl.getSml340conditionType4(), gsMsg));

            paramMdl.setSml340conditionExs4View(
                    __getViewConditionExs(paramMdl.getSml340conditionExs4(), gsMsg));
        }

        //条件５
        if (paramMdl.getSml340condition5() != null) {
            paramMdl.setSml340conditionType5View(
                    __getViewCondition(paramMdl.getSml340conditionType5(), gsMsg));

            paramMdl.setSml340conditionExs5View(
                    __getViewConditionExs(paramMdl.getSml340conditionExs5(), gsMsg));
        }

        //ラベル名設定
        if (!(paramMdl.getSml340actionLabel().equals("0"))) {
            int wlbSid = Integer.parseInt(paramMdl.getSml340actionLabelValue());
            SmlLabelDao labelDao = new SmlLabelDao(con);
            SmlLabelModel labelData = labelDao.select(wlbSid);
            paramMdl.setSml340LabelView(labelData.getSlbName());
        }

        //フィルタリング対象のメール一覧
        if (paramMdl.getSml340doFilter() == Sml340Form.SML340_DOFILTER_YES) {
            Sml340Biz biz140 = new Sml340Biz();
            biz140.setMailList(con, paramMdl, userSid);
        }
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void doAddEdit(
        Sml340knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon,
        RequestModel reqMdl) throws SQLException, IOToolsException {

        boolean commitFlg = false;
        try {
            con__.setAutoCommit(false);

            SmlFilterModel wftMdl = null;
            //処理モード(新規登録）
            if (paramMdl.getSmlFilterCmdMode() == GSConstSmail.CMDMODE_ADD) {
                //新規登録
                log__.debug("新規登録");
                wftMdl = doInsert(paramMdl, userSid, cntCon);

            //編集
            } else if (paramMdl.getSmlFilterCmdMode() == GSConstSmail.CMDMODE_EDIT) {
                log__.debug("編集");
                wftMdl = doUpdate(paramMdl, userSid);
            }

            if (paramMdl.getSml340doFilter() == Sml340knForm.SML340_DOFILTER_YES) {

                //フィルター情報
                MailFilterModel filterMdl = new MailFilterModel();
                filterMdl.setSftSid(wftMdl.getSftSid());
                filterMdl.setTempFile(wftMdl.getSftTempfile()
                                        == GSConstSmail.FILTER_TEMPFILE_YES);
                filterMdl.setLabel(wftMdl.getSftActionLabel()
                                        == GSConstSmail.FILTER_LABEL_SETLABEL);
                filterMdl.setLabelSid(wftMdl.getSlbSid());
                filterMdl.setReaded(wftMdl.getSftActionRead()
                                        == GSConstSmail.FILTER_READED_SETREADED);
                filterMdl.setDust(wftMdl.getSftActionDust()
                                        == GSConstSmail.FILTER_DUST_MOVEDUST);
                filterMdl.setCondition(wftMdl.getSftCondition());

                //フィルター条件
                SmlFilterConditionDao conditionDao = new SmlFilterConditionDao(con__);
                List<SmlFilterConditionModel> wfcList = conditionDao.select(wftMdl.getSftSid());
                List<MailFilterConditionModel> conditionList
                    = new ArrayList<MailFilterConditionModel>();
                for (SmlFilterConditionModel wfcData : wfcList) {
                    MailFilterConditionModel conditionMdl = new MailFilterConditionModel();
                    conditionMdl.setType(wfcData.getSfcType());
                    conditionMdl.setExpression(wfcData.getSfcExpression());
                    conditionMdl.setText(wfcData.getSfcText());

                    conditionList.add(conditionMdl);
                }

                //メールのフィルタリングを行う
                SmailDao smailDao = new SmailDao(con__);
                smailDao.updateFilterlingMail(
                        filterMdl, conditionList, paramMdl.getSmlAccountSid());
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
    public SmlFilterModel doInsert(
        Sml340knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException {

        //フィルターSID採番
        int wftSid = (int) cntCon.getSaibanNumber(
                GSConstSmail.PLUGIN_ID_SMAIL,
                GSConstSmail.SBNSID_SUB_FILTER,
                userSid);

        //フィルターを登録する
        SmlFilterModel sfMdl  = __getFilterInsertMdl(paramMdl, userSid, wftSid);
        SmlFilterDao sfDao = new SmlFilterDao(con__);
        sfDao.insert(sfMdl);

        //フィルター_転送指定アドレスを登録する
//        __entryFwAddress(paramMdl, sfMdl.getSftSid());

        //フィルター条件を登録する
        SmlFilterConditionDao wfcDao = new SmlFilterConditionDao(con__);
        List<SmlFilterConditionModel> conditionList = __getFilterConditionList(paramMdl, wftSid);
        for (SmlFilterConditionModel conditionMdl : conditionList) {
            wfcDao.insert(conditionMdl);
        }

        //フィルター適用順を登録する
        SmlFilterSortDao sfsDao = new SmlFilterSortDao(con__);
        SmlFilterSortModel sfsMdl  = __getFilterSortInsertMdl(paramMdl, wftSid);
        sfsDao.insert(sfsMdl);

        return sfMdl;
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
    public SmlFilterModel doUpdate(
        Sml340knParamModel paramMdl,
        int userSid) throws SQLException {

        List<SmlFilterConditionModel> conditionList = null;

        int wftSid = paramMdl.getSmlEditFilterId();
        con__.setAutoCommit(false);

        SmlFilterDao wfdao = new SmlFilterDao(con__);

        //フィルター更新Model
        SmlFilterModel wfMdl  = __getFilterUpdateMdl(paramMdl, userSid);
        //フィルターを更新する
        wfdao.update(wfMdl);

        //フィルター_転送指定アドレスを登録する
//        __entryFwAddress(paramMdl, wfMdl.getSftSid());

        //フィルター条件を削除する
        Sml340knDao dao = new Sml340knDao(con__);
        dao.deleteFilter(wftSid);

        //フィルター条件を登録する
        SmlFilterConditionDao wfcDao = new SmlFilterConditionDao(con__);
        conditionList = __getFilterConditionList(paramMdl, wftSid);
        for (SmlFilterConditionModel conditionMdl : conditionList) {
            wfcDao.insert(conditionMdl);
        }

        return wfMdl;
    }

    /**
     * <br>[機  能] 新規登録用のSmlFilterModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @param wftSid フィルターSID
     * @return SmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private SmlFilterModel __getFilterInsertMdl(
        Sml340knParamModel paramMdl,
        int userSid,
        int wftSid) throws SQLException {

        SmlFilterModel sfMdl = new SmlFilterModel();

        sfMdl.setSftSid(wftSid);
        __setFilterEntryMdl(sfMdl, paramMdl, userSid);

        sfMdl.setSacSid(paramMdl.getSmlAccountSid());

        return sfMdl;
    }

    /**
     * <br>[機  能] 更新用のSmlFilterModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @return SmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private SmlFilterModel __getFilterUpdateMdl(
        Sml340knParamModel paramMdl,
        int userSid) throws SQLException {

        SmlFilterModel sfMdl = new SmlFilterModel();

        sfMdl.setSftSid(paramMdl.getSmlEditFilterId());
        __setFilterEntryMdl(sfMdl, paramMdl, userSid);

        //アカウントSID
        sfMdl.setSacSid(paramMdl.getSmlAccountSid());

        return sfMdl;
    }

    /**
     * <br>[機  能] フィルター情報Modelへパラメータ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sfMdl フィルター情報Model
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     */
    private void __setFilterEntryMdl(SmlFilterModel sfMdl, Sml340knParamModel paramMdl,
                                                    int userSid) {
        sfMdl.setUsrSid(userSid);
        sfMdl.setSftName(paramMdl.getSml340FilterName());
        sfMdl.setSftType(GSConstSmail.SFT_TYPE_ONE);
        sfMdl.setSftCondition(paramMdl.getSml340filterType());
        sfMdl.setSftTempfile(Integer.parseInt(paramMdl.getSml340tempFile()));
        sfMdl.setSftActionLabel(Integer.parseInt(paramMdl.getSml340actionLabel()));
        sfMdl.setSftActionRead(Integer.parseInt(paramMdl.getSml340actionRead()));
        sfMdl.setSftActionDust(Integer.parseInt(paramMdl.getSml340actionDust()));

        //ラベルを設定する場合
        if (paramMdl.getSml340actionLabel().equals("1")) {
            sfMdl.setSlbSid(Integer.parseInt(paramMdl.getSml340actionLabelValue()));
        }
    }

    /**
     * <br>[機  能] 新規登録用のSmlFilterModel一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sftSid フィルターSID
     * @return SmlFilterModel一覧
     * @throws SQLException SQL実行例外
     */
    private List<SmlFilterConditionModel> __getFilterConditionList(
        Sml340knParamModel paramMdl, int sftSid) throws SQLException {

        List<SmlFilterConditionModel> conditionList = new ArrayList<SmlFilterConditionModel>();

        if (paramMdl.getSml340condition1().equals(String.valueOf(GSConstSmail.CONDITION_ONE))) {
            conditionList.add(__createFilterConditionModel(sftSid,
                            GSConstSmail.CONDITION_ONE,
                            paramMdl.getSml340conditionType1(),
                            paramMdl.getSml340conditionExs1(),
                            paramMdl.getSml340conditionText1()));
        }

        if (paramMdl.getSml340condition2().equals(String.valueOf(GSConstSmail.CONDITION_TWO))) {
            conditionList.add(__createFilterConditionModel(sftSid,
                            GSConstSmail.CONDITION_TWO,
                            paramMdl.getSml340conditionType2(),
                            paramMdl.getSml340conditionExs2(),
                            paramMdl.getSml340conditionText2()));
        }

        if (paramMdl.getSml340condition3().equals(String.valueOf(GSConstSmail.CONDITION_THREE))) {
            conditionList.add(__createFilterConditionModel(sftSid,
                            GSConstSmail.CONDITION_THREE,
                            paramMdl.getSml340conditionType3(),
                            paramMdl.getSml340conditionExs3(),
                            paramMdl.getSml340conditionText3()));
        }

        if (paramMdl.getSml340condition4().equals(String.valueOf(GSConstSmail.CONDITION_FOUR))) {
            conditionList.add(__createFilterConditionModel(sftSid,
                            GSConstSmail.CONDITION_FOUR,
                            paramMdl.getSml340conditionType4(),
                            paramMdl.getSml340conditionExs4(),
                            paramMdl.getSml340conditionText4()));
        }

        if (paramMdl.getSml340condition5().equals(String.valueOf(GSConstSmail.CONDITION_FIVE))) {
            conditionList.add(__createFilterConditionModel(sftSid,
                            GSConstSmail.CONDITION_FIVE,
                            paramMdl.getSml340conditionType5(),
                            paramMdl.getSml340conditionExs5(),
                            paramMdl.getSml340conditionText5()));
        }

        return conditionList;
    }

    /**
     * <br>[機  能] フィルター条件Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sftSid フィルターSID
     * @param number 番号
     * @param type 種別
     * @param exs 条件式
     * @param text 条件
     * @return フィルター条件Model
     */
    private SmlFilterConditionModel __createFilterConditionModel(int sftSid, int number,
                                                                String type, String exs,
                                                                String text) {

        SmlFilterConditionModel sfcMdl = new SmlFilterConditionModel();
        sfcMdl.setSftSid(sftSid);
        sfcMdl.setSfcNum(number);
        sfcMdl.setSfcType(Integer.parseInt(type));
        sfcMdl.setSfcExpression(Integer.parseInt(exs));
        sfcMdl.setSfcText(text);

        return sfcMdl;
    }

    /**
     * <br>[機  能] 新規登録用のSmlFilterSortModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param fiSaiSid 採番SID
     * @return SmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private SmlFilterSortModel __getFilterSortInsertMdl(
        Sml340knParamModel paramMdl,
        int fiSaiSid) throws SQLException {

        SmlFilterSortModel sfsMdl = new SmlFilterSortModel();
        SmlFilterSortDao sfsdao = new SmlFilterSortDao(con__);

        sfsMdl.setSacSid(paramMdl.getSmlAccountSid());
        sfsMdl.setSftSid(fiSaiSid);

        //アカウントSID
        sfsMdl.setSfsSort(sfsdao.maxSortNumber() + 1);

        return sfsMdl;
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
        if (conditionType.equals(String.valueOf(GSConstSmail.FILTER_TYPE_TITLE))) {
           viewCondition = gsMsg.getMessage(GSConstSmail.FILTER_TITLE);

        } else if (conditionType.equals(String.valueOf(GSConstSmail.FILTER_TYPE_ADDRESS))) {
            viewCondition = gsMsg.getMessage(GSConstSmail.FILTER_ADDRESS);

        } else if (conditionType.equals(String.valueOf(GSConstSmail.FILTER_TYPE_CC))) {
            viewCondition = "CC";

        } else if (conditionType.equals(String.valueOf(GSConstSmail.FILTER_TYPE_SEND))) {
            viewCondition = gsMsg.getMessage(GSConstSmail.FILTER_SEND);

        } else if (conditionType.equals(String.valueOf(GSConstSmail.FILTER_TYPE_MAIN))) {
            viewCondition = gsMsg.getMessage(GSConstSmail.FILTER_MAIN);
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
        if (conditionExs.equals(String.valueOf(GSConstSmail.FILTER_TYPE_INCLUDE))) {
            viewConditionExs = gsMsg.getMessage(GSConstSmail.FILTER_INCLUDE);

        } else if (conditionExs.equals(String.valueOf(GSConstSmail.FILTER_TYPE_EXCLUDE))) {
            viewConditionExs = gsMsg.getMessage(GSConstSmail.FILTER_EXCLUDE);
        }

        return viewConditionExs;
    }

}
