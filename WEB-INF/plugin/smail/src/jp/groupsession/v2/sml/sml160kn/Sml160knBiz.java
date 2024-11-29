package jp.groupsession.v2.sml.sml160kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.sml240.Sml240AccountModel;
import jp.groupsession.v2.sml.sml240.Sml240Dao;
import jp.groupsession.v2.sml.sml240.Sml240SearchModel;

/**
 * <br>[機  能] ショートメール 管理者設定 手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml160knBiz {

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Sml160knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(RequestModel reqMdl,
                                       Sml160knParamModel paramMdl)
        throws SQLException {


        //アカウントSID
        List<Sml240AccountModel> accountList = new ArrayList<Sml240AccountModel>();

        if (paramMdl.getSml160SelKbn() == GSConstSmail.ACCOUNT_SEL) {
            //指定アカウント
            Sml240AccountModel smlMdl = new Sml240AccountModel(reqMdl);
            smlMdl.setAccountSid(paramMdl.getSml160AccountSid());
            smlMdl.setAccountName(paramMdl.getSml160AccountName());
            accountList.add(smlMdl);
            paramMdl.setSml160knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);
            paramMdl.setSml160knAccountList(accountList);

        }
    }

    /**
     * <br>[機  能] ショートメール手動削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void updateSyudoDelSetting(Sml160knParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        //アカウントSID
        List<Sml240AccountModel> accountList = new ArrayList<Sml240AccountModel>();

        if (paramMdl.getSml160SelKbn() == GSConstSmail.ACCOUNT_SEL) {
            //指定アカウント
            Sml240AccountModel smlMdl = new Sml240AccountModel(reqMdl);
            smlMdl.setAccountSid(paramMdl.getSml160AccountSid());
            smlMdl.setAccountName(paramMdl.getSml160AccountName());
            accountList.add(smlMdl);
            paramMdl.setSml160knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);

            paramMdl.setSml160knAccountList(accountList);

        }

        ArrayList<SmlAdelModel> jdelList = new ArrayList<SmlAdelModel>();
        ArrayList<SmlAdelModel> sdelList = new ArrayList<SmlAdelModel>();
        ArrayList<SmlAdelModel> wdelList = new ArrayList<SmlAdelModel>();
        ArrayList<SmlAdelModel> ddelList = new ArrayList<SmlAdelModel>();

        SmlJmeisDao jmsDao = new SmlJmeisDao(con__);
        SmlSmeisDao smsDao = new SmlSmeisDao(con__);
        SmlWmeisDao wmsDao = new SmlWmeisDao(con__);

        int jdelKbn =
            NullDefault.getInt(paramMdl.getSml160JdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (jdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml160knAccountList()) {
                SmlAdelModel jmdl = new SmlAdelModel();
                jmdl.setSadJdelYear(Integer.parseInt(paramMdl.getSml160JYear()));
                jmdl.setSadJdelMonth(Integer.parseInt(paramMdl.getSml160JMonth()));
                jmdl.setSacSid(mdl.getAccountSid());
                jdelList.add(jmdl);
            }

        }

        int sdelKbn =
            NullDefault.getInt(paramMdl.getSml160SdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (sdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml160knAccountList()) {
                SmlAdelModel smdl = new SmlAdelModel();
                smdl.setSadSdelYear(Integer.parseInt(paramMdl.getSml160SYear()));
                smdl.setSadSdelMonth(Integer.parseInt(paramMdl.getSml160SMonth()));
                smdl.setSacSid(mdl.getAccountSid());
                sdelList.add(smdl);
            }
        }

        int wdelKbn =
            NullDefault.getInt(paramMdl.getSml160WdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (wdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml160knAccountList()) {
                SmlAdelModel wmdl = new SmlAdelModel();
                wmdl.setSadWdelYear(Integer.parseInt(paramMdl.getSml160WYear()));
                wmdl.setSadWdelMonth(Integer.parseInt(paramMdl.getSml160WMonth()));
                wmdl.setSacSid(mdl.getAccountSid());
                wdelList.add(wmdl);
            }
        }

        int ddelKbn =
            NullDefault.getInt(paramMdl.getSml160DdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (ddelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml160knAccountList()) {
                SmlAdelModel dmdl = new SmlAdelModel();
                dmdl.setSadJdelYear(Integer.parseInt(paramMdl.getSml160DYear()));
                dmdl.setSadJdelMonth(Integer.parseInt(paramMdl.getSml160DMonth()));
                dmdl.setSadSdelYear(Integer.parseInt(paramMdl.getSml160DYear()));
                dmdl.setSadSdelMonth(Integer.parseInt(paramMdl.getSml160DMonth()));
                dmdl.setSadWdelYear(Integer.parseInt(paramMdl.getSml160DYear()));
                dmdl.setSadWdelMonth(Integer.parseInt(paramMdl.getSml160DMonth()));
                dmdl.setSadDdelYear(Integer.parseInt(paramMdl.getSml160DYear()));
                dmdl.setSadDdelMonth(Integer.parseInt(paramMdl.getSml160DMonth()));
                dmdl.setSacSid(mdl.getAccountSid());
                ddelList.add(dmdl);
            }
        }

        SmlCommonBiz smlBiz = new SmlCommonBiz();
        
        //ゴミ箱データ削除
        if (!ddelList.isEmpty()) {
            
            //受送信データの容量計算を実施
            for (SmlAdelModel dMdl : ddelList) {
                Map<SmlJmeisModel, Long> jMap = jmsDao.getDeleteMail(
                        dMdl, GSConstSmail.DEL_KBN_DELETED, ddelList.size(), 0);
                long delSize = 0;
                for (Map.Entry<SmlJmeisModel, Long> map : jMap.entrySet()) {
                    delSize += map.getValue();
                }
                Map<SmlSmeisModel, Long> sMap = smsDao.getDeleteMail(
                        dMdl, GSConstSmail.DEL_KBN_DELETED, ddelList.size(), 0);
                for (Map.Entry<SmlSmeisModel, Long> map : sMap.entrySet()) {
                    delSize += map.getValue();
                }
                
                Map<SmlWmeisModel, Long> wMap = wmsDao.getDeleteMail(
                        dMdl, GSConstSmail.DEL_KBN_DELETED, ddelList.size(), 0);
                for (Map.Entry<SmlWmeisModel, Long> map : wMap.entrySet()) {
                    delSize += map.getValue();
                }
                
                smlBiz.updateAccountDiskSize(con__, dMdl.getSacSid(), -delSize);
            }
            
            jmsDao.delete(ddelList, 2);
            smsDao.delete(ddelList, 2);
            wmsDao.delete(ddelList, 2);
        }

        if (!jdelList.isEmpty()) {
            for (SmlAdelModel jMdl : jdelList) {
                Map<SmlJmeisModel, Long> jMap =
                        jmsDao.getDeleteMail(jMdl, GSConstSmail.DEL_KBN_NORMAL, jdelList.size(), 0);
                long jdelSize = 0;
                for (Map.Entry<SmlJmeisModel, Long> map : jMap.entrySet()) {
                    jdelSize += map.getValue();
                }
                smlBiz.updateAccountDiskSize(con__, jMdl.getSacSid(), -jdelSize);
            }
                    
            //受信タブデータ削除
            jmsDao.delete(jdelList, 1);
        }

        if (!sdelList.isEmpty()) {
            for (SmlAdelModel sMdl : sdelList) {
                Map<SmlSmeisModel, Long> sMap =
                        smsDao.getDeleteMail(sMdl, GSConstSmail.DEL_KBN_NORMAL, sdelList.size(), 0);
                long sdelSize = 0;
                for (Map.Entry<SmlSmeisModel, Long> map : sMap.entrySet()) {
                    sdelSize += map.getValue();
                }
                smlBiz.updateAccountDiskSize(con__, sMdl.getSacSid(), -sdelSize);
            }
            
            //送信タブデータ削除
            smsDao.delete(sdelList, 1);
        }

        if (!wdelList.isEmpty()) {
            //草稿タブデータ削除
            wmsDao.delete(wdelList, 1);
        }
    }

    /**
     * <br>[機  能] 指定ユーザの利用可能な全アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return accountList
     */
    public List<Sml240AccountModel> getAllUseAccount(
                                 RequestModel reqMdl,
                                 int usrSid,
                                 Connection con)
        throws SQLException {

        Sml240SearchModel searchMdl = new Sml240SearchModel();
        searchMdl.setMaxCount(-1);
        searchMdl.setGrpSid(-1);
        searchMdl.setUserSid(-1);
        Sml240Dao dao = new Sml240Dao(con);

        List<Sml240AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);

        return accountList;
    }

    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel Sml160knParamModel
     */
    public void updateEgnoreYearMonth(Sml160knParamModel paramModel) {
        if (!ValidateUtil.isNumber(paramModel.getSml160JYear())) {
            paramModel.setSml160JYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160JMonth())) {
            paramModel.setSml160JMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160SYear())) {
            paramModel.setSml160SYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160SMonth())) {
            paramModel.setSml160SMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160WYear())) {
            paramModel.setSml160WYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160WMonth())) {
            paramModel.setSml160WMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160DYear())) {
            paramModel.setSml160DYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml160DMonth())) {
            paramModel.setSml160DMonth("");
        }
    }
}