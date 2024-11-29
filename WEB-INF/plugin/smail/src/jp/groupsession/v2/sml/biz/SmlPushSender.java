package jp.groupsession.v2.sml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.PushRequestModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountUserDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAccountUserModel;
import jp.groupsession.v2.sml.model.SmlPushRequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

public class SmlPushSender {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(SmlPushSender.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** 宛先リスト TO,CC、BCC毎の配列*/
    private final List<Integer> atesakiList__;
    /** メールタイトル*/
    private final String mailTitle__;
    /** メールSID*/
    private final int mailSid__;
    /** 送信アカウントSID*/
    private final SmlAccountModel sacMdl__;

    /**
     * コンストラクタ
     * @param reqMdl
     * @param con
     * @param atesakiList 宛先リスト
     * @param mailTitle メールタイトル
     * @param mailSid メールSID
     * @param sacMdl　送信アカウント
     */
    public SmlPushSender(RequestModel reqMdl, Connection con,
            List<Integer> atesakiList, String mailTitle,
            int mailSid, SmlAccountModel sacMdl) {
        reqMdl__ = reqMdl;
        con__ = con;
        atesakiList__ = atesakiList;
        mailTitle__ = mailTitle;
        this.mailSid__ = mailSid;
        sacMdl__ = sacMdl;
    }

    /**
     * 登録済みのメールからPush通知を送信する
     * フィルタ実行などによってゴミ箱に保管されているメールは通知されない
     * @throws NumberFormatException
     * @throws SQLException
     */
    public void sendPush() throws NumberFormatException, SQLException {
        if (atesakiList__ == null
                    || atesakiList__.isEmpty()
                    || mailSid__ <= 0) {
            return;
        }

        //フィルタによって削除されていないメールを取得する
        SmlJmeisDao smjDao = new SmlJmeisDao(con__);
        List<Integer> toAccSidList =
                smjDao.getMailSidInAccount(
                atesakiList__,
                mailSid__);

        IPushServiceOperator psOpe = PushServiceOperator.getInstance(
                con__, reqMdl__.getDomain());
        if (psOpe.isUseable()) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            final GsMessage psGsMsg = gsMsg;
            UserBiz userBiz = new UserBiz();
            SmlAccountUserDao sauDao = new SmlAccountUserDao(con__);
  
            ArrayList<PushRequestModel> pushMdlList = new ArrayList<PushRequestModel>();

            //PUSH通知を行わないユーザ、アカウントを取得する
            for (Integer account : toAccSidList) {
                //accountSidを元に使用者ユーザSID経由で端末トークン取得
                ArrayList<String> tokenList = new ArrayList<String>();
                ArrayList<Integer> userSidList = new ArrayList<Integer>();
                List<SmlAccountUserModel> smlAccountUserList = sauDao.select(
                        account);
                for (SmlAccountUserModel sauMdl : smlAccountUserList) {
                    if (sauMdl.getUsrSid() != 0 && sauMdl.getUsrSid() != -1) {
                        //ショートメールのPUSH通知を行わないユーザは除外
                        if (!userSidList.contains(sauMdl.getUsrSid())) {
                            userSidList.add(sauMdl.getUsrSid());
                        }
                    } else if (sauMdl.getGrpSid() != -1) {
                        //グループに所属しているユーザの情報を取得する
                        List<UsrLabelValueBean> belongList =
                                userBiz.getUserLabelListNoSysUser(
                                        con__, psGsMsg, sauMdl.getGrpSid());
                        int belongUsrSid;
                        for (UsrLabelValueBean usrLabel : belongList) {
                            belongUsrSid = Integer.parseInt(usrLabel.getValue());
                            if (!userSidList.contains(belongUsrSid)) {
                                userSidList.add(Integer.parseInt(
                                        usrLabel.getValue()));
                            }
                        }
                    }
                }

                SmlAccountDao saDao = new SmlAccountDao(con__);
                SmlAccountModel targetAccountMdl = saDao.select(account);
                String pushTitle = gsMsg.getMessage(
                    "sml.push.title", new String[] {sacMdl__.getSacName()});
                for (int userSid : userSidList) {
                    SmlPushRequestModel pushMdl = new SmlPushRequestModel();
                    pushMdl.setAppId(GSConst.APP_GS_MOBILE);
                    pushMdl.setAccountSid(account);
                    pushMdl.setPushTitle(pushTitle);
                    pushMdl.setPushMessage(mailTitle__);
                    Map<String, String> pushParamMap = new HashMap<>();
                    pushParamMap.put("cmd", "detail");
                    pushParamMap.put("account", String.valueOf(account));
                    pushParamMap.put("sid", String.valueOf(mailSid__));
                    pushParamMap.put("plugin", GSConst.PLUGINID_SML);
                    pushMdl.setPushParam(pushParamMap);
                    pushMdl.setPushUser(userSid);
                    pushMdlList.add(pushMdl);
                }

                log__.info("通知対象アカウント名：" + targetAccountMdl.getSacName());
                log__.info("通知対象アカウントSID：" + targetAccountMdl.getSacSid());
                log__.info("送信元アカウントSID：" + sacMdl__.getSacSid());
                log__.info("ショートメールSID：" + mailSid__ + " タイトル：" + mailTitle__);
                log__.info("通知内容：" + String.format("%s%n%s", pushTitle, mailTitle__));
                log__.info("端末トークン：" + String.join(",", tokenList));
            }
            
            psOpe.sendMessage(con__, reqMdl__, pushMdlList, GSConst.PLUGINID_SML);
        }

    }
}
