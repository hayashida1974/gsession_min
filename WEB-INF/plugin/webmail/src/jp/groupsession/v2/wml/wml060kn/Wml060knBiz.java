package jp.groupsession.v2.wml.wml060kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.WmlMailDeleteModel;

/**
 * <br>[機  能] WEBメール 手動データ削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml060knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml060knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml060knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml060knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setInitData(
            RequestModel reqMdl,
            Wml060knParamModel paramMdl)
    throws Exception {

        log__.debug("START");

        GsMessage gsMsg = new GsMessage(reqMdl);
        String txt_setupNo = gsMsg.getMessage(GSConstWebmail.MANU_SETUP_NO);
        String txt_setupOk = gsMsg.getMessage(GSConstWebmail.MANU_SETUP_OK);

        //ゴミ箱手動削除設定
        //削除しない
        if (paramMdl.getWml060delKbn1().equals(String.valueOf(GSConstWebmail.MANU_DEL_NO))) {
            paramMdl.setManuDustDelSetUp(txt_setupNo);

        //手動削除
        } else {
            int yearDust = paramMdl.getWml060delYear1();
            paramMdl.setManuDustDelSetUp(txt_setupOk);
            paramMdl.setManuDustDelSetUpYear(String.valueOf(yearDust));
            paramMdl.setManuDustDelSetUpMonth(String.valueOf(paramMdl.getWml060delMonth1()));
            paramMdl.setManuDustDelSetUpDay(String.valueOf(paramMdl.getWml060delDay1()));
        }

        //送信済み手動削除設定
        //削除しない
        if (paramMdl.getWml060delKbn2().equals(String.valueOf(GSConstWebmail.MANU_DEL_NO))) {
            paramMdl.setManuSendDelSetUp(txt_setupNo);

        //手動削除
        } else {
            int yearSend = paramMdl.getWml060delYear2();
            paramMdl.setManuSendDelSetUp(txt_setupOk);
            paramMdl.setManuSendDelSetUpYear(String.valueOf(yearSend));
            paramMdl.setManuSendDelSetUpMonth(String.valueOf(paramMdl.getWml060delMonth2()));
            paramMdl.setManuSendDelSetUpDay(String.valueOf(paramMdl.getWml060delDay2()));
        }

        //草稿手動削除設定
        //削除しない
        if (paramMdl.getWml060delKbn3().equals(String.valueOf(GSConstWebmail.MANU_DEL_NO))) {
            paramMdl.setManuDraftDelSetUp(txt_setupNo);

        //手動削除
        } else {
            int yearDrift = paramMdl.getWml060delYear3();
            paramMdl.setManuDraftDelSetUp(txt_setupOk);
            paramMdl.setManuDraftDelSetUpYear(String.valueOf(yearDrift));
            paramMdl.setManuDraftDelSetUpMonth(String.valueOf(paramMdl.getWml060delMonth3()));
            paramMdl.setManuDraftDelSetUpDay(String.valueOf(paramMdl.getWml060delDay3()));
        }

        //受信箱手動削除設定
        //削除しない
        if (paramMdl.getWml060delKbn4().equals(String.valueOf(GSConstWebmail.MANU_DEL_NO))) {
            paramMdl.setManuResvDelSetUp(txt_setupNo);

        //手動削除
        } else {
            int yearResv = paramMdl.getWml060delYear4();
            paramMdl.setManuResvDelSetUp(txt_setupOk);
            paramMdl.setManuResvDelSetUpYear(String.valueOf(yearResv));
            paramMdl.setManuResvDelSetUpMonth(String.valueOf(paramMdl.getWml060delMonth4()));
            paramMdl.setManuResvDelSetUpDay(String.valueOf(paramMdl.getWml060delDay4()));
        }

        //保管手動削除設定
        //削除しない
        if (paramMdl.getWml060delKbn5().equals(String.valueOf(GSConstWebmail.MANU_DEL_NO))) {
            paramMdl.setManuKeepDelSetUp(txt_setupNo);

        //手動削除
        } else {
            int yearResv = paramMdl.getWml060delYear5();
            paramMdl.setManuKeepDelSetUp(txt_setupOk);
            paramMdl.setManuKeepDelSetUpYear(String.valueOf(yearResv));
            paramMdl.setManuKeepDelSetUpMonth(String.valueOf(paramMdl.getWml060delMonth5()));
            paramMdl.setManuKeepDelSetUpDay(String.valueOf(paramMdl.getWml060delDay5()));
        }

        log__.debug("END");
    }

    /**
     * <br>[機  能] メール手動削除情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     * @return List
     */
    public List<WmlMailDeleteModel> setData(
            RequestModel reqMdl,
            Wml060knParamModel paramMdl,
            int userSid)
    throws SQLException {

        boolean commitFlg = false;
        List<WmlMailDeleteModel> wmMdlList = null;
        try {

            con__.setAutoCommit(false);

            //登録情報をModelに設定する
            wmMdlList = new ArrayList<WmlMailDeleteModel>();

            //ゴミ箱
            __addDeleteModel(wmMdlList,
                            paramMdl.getWml060delKbn1(),
                            String.valueOf(paramMdl.getWml060delYear1()),
                            String.valueOf(paramMdl.getWml060delMonth1()),
                            String.valueOf(paramMdl.getWml060delDay1()),
                            GSConstWebmail.DIR_TYPE_DUST);

            //送信済み
            __addDeleteModel(wmMdlList,
                            paramMdl.getWml060delKbn2(),
                            String.valueOf(paramMdl.getWml060delYear2()),
                            String.valueOf(paramMdl.getWml060delMonth2()),
                            String.valueOf(paramMdl.getWml060delDay2()),
                            GSConstWebmail.DIR_TYPE_SENDED);

            //草稿
            __addDeleteModel(wmMdlList,
                            paramMdl.getWml060delKbn3(),
                            String.valueOf(paramMdl.getWml060delYear3()),
                            String.valueOf(paramMdl.getWml060delMonth3()),
                            String.valueOf(paramMdl.getWml060delDay3()),
                            GSConstWebmail.DIR_TYPE_DRAFT);

            //受信箱
            __addDeleteModel(wmMdlList,
                            paramMdl.getWml060delKbn4(),
                            String.valueOf(paramMdl.getWml060delYear4()),
                            String.valueOf(paramMdl.getWml060delMonth4()),
                            String.valueOf(paramMdl.getWml060delDay4()),
                            GSConstWebmail.DIR_TYPE_RECEIVE);

            //保管
            __addDeleteModel(wmMdlList,
                            paramMdl.getWml060delKbn5(),
                            String.valueOf(paramMdl.getWml060delYear5()),
                            String.valueOf(paramMdl.getWml060delMonth5()),
                            String.valueOf(paramMdl.getWml060delDay5()),
                            GSConstWebmail.DIR_TYPE_STORAGE);


            //全てのアカウントSIDを取得する
            WmlAccountDao accountDao = new WmlAccountDao(con__);
            List<Integer> delWacSidList = accountDao.getExistAccountSidList();

            //手動削除を行う
            WmlBiz wmlBiz = new WmlBiz();
            for (WmlMailDeleteModel delMdl : wmMdlList) {
                for (int delWacSid : delWacSidList) {
                    delMdl.setWacSid(delWacSid);
                    wmlBiz.deleteMailData(con__, delMdl, userSid);
                }
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
        throw e;

        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }

        return wmMdlList;
    }

    /**
     * <br>[機  能] 指定したListに生成したメール情報削除条件Modelを追加する
     * <br>[解  説] 削除区分が手動削除の場合、メール情報削除条件Modelを追加する
     * <br>[備  考]
     * @param delList List
     * @param delKbn 削除区分
     * @param year 年
     * @param month 月
     * @param day 日
     * @param dirType 日付
     * @return List
     */
    private List<WmlMailDeleteModel> __addDeleteModel(List<WmlMailDeleteModel> delList,
                                                    String delKbn, String year,
                                                    String month, String day, int dirType) {

        if (delKbn.equals(String.valueOf(GSConstWebmail.MANU_DEL_OK))) {
            WmlMailDeleteModel wmMdl = new WmlMailDeleteModel();
            wmMdl.setManuDelYear(Integer.parseInt(year));
            wmMdl.setManuDelMonth(Integer.parseInt(month));
            wmMdl.setManuDelDay(Integer.parseInt(day));
            wmMdl.setManuDelDir(dirType);
            wmMdl.setUseDate(true);
            wmMdl.setDelAllAccount(true);

            delList.add(wmMdl);
        }

        return delList;
    }
}
