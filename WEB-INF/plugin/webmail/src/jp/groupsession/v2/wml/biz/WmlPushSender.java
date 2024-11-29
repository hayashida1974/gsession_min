package jp.groupsession.v2.wml.biz;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.PushRequestModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rap.mbh.push.IPushServiceOperator;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.batch.WmlSendPushThread;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.WmlPushRequestModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSearchModel;
import jp.groupsession.v2.wml.wml010.Wml010Const;

public class WmlPushSender {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WmlPushSender.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** メールSID*/
    private List<Long> mailSidList__ = null;
    /** アカウントSID */
    private int wacSid__;
    /** メール受信 実行者 */
    private int userSid__;

    /**
     * コンストラクタ
     * @param reqMdl
     * @param con
     * @param mailSidList メールSIDリスト
     * @param wacSid アカウントSID
     * @param userSid メール受信の実行者(自動受信などは0を指定)
     */
    public WmlPushSender(RequestModel reqMdl, Connection con,
        List<Long> mailSidList, int wacSid, int userSid) {

        reqMdl__ = reqMdl;
        con__ = con;
        mailSidList__ = mailSidList;
        wacSid__ = wacSid;
        userSid__ = userSid;
    }

    /**
     * 登録済みのメールからPush通知を送信する
     * フィルタ実行などによってゴミ箱に保管されているメールは通知されない
     * @throws SQLException SQL実行に失敗
     * @throws UnsupportedEncodingException 個人名のエンコードに失敗
     * @throws AddressException 構文解析に失敗
     * @throws InterruptedException 
     */
    public void sendPush() throws SQLException, AddressException, UnsupportedEncodingException, InterruptedException {

        //メールSID, アカウントSIDが入力されていない場合は処理を中断
        if (mailSidList__ == null
                    || mailSidList__.isEmpty()
                    || wacSid__ <= 0) {
            return;
        }

        IPushServiceOperator psOpe = PushServiceOperator.getInstance(
                con__, reqMdl__.getDomain());
        
        if (!psOpe.isUseable()) {
            //プッシュ通知が使用できない場合は処理を終了する
            return;
        }

        //フィルタによって既読/削除されていない受信メールを取得する
        WmlMailSearchModel searchModel = new WmlMailSearchModel();
        searchModel.setAccountSid(wacSid__);
        long[] mailNumArray = mailSidList__.stream()
                        .mapToLong(Long::longValue)
                        .toArray();
        searchModel.setMailNumArray(mailNumArray);
        searchModel.setReadKbn(Wml010Const.SEARCH_READKBN_NOREAD);
        WmlMaildataDao mailDao = new WmlMaildataDao(con__);
        List<WmlMailResultModel> mailDataList = mailDao.getMailList(searchModel, -1)
            .stream()
            .filter(mdl -> mdl.getDirType() == GSConstWebmail.DIR_TYPE_RECEIVE)
            .collect(Collectors.toList());

        //プッシュ通知対象のメールが存在しない場合、処理を終了する
        if (mailDataList.isEmpty()) {
            return;
        }
        
        GsMessage gsMsg = new GsMessage(reqMdl__);
        
        //アカウントの使用者を取得する
        WmlBiz wmlBiz = new WmlBiz();
        List<Integer> userList = wmlBiz.getAccountUserList(con__, wacSid__);

        //メール受信の実行者を対象のユーザから除外
        userList.removeIf(sid -> sid == userSid__);

        List<PushRequestModel> pushMdlList = new ArrayList<PushRequestModel>();

        WmlAccountDao wacDao = new WmlAccountDao(con__);
        WmlAccountModel accountMdl = wacDao.select(wacSid__);

        String pushTitle;
        String pushMessage = "To：" + accountMdl.getWacName();
        String mailSid = null;
        if (mailDataList.size() == 1) {
            //メールが1件の場合は、タイトルに差出人に名前を入れる
            WmlMailResultModel mailMdl = mailDataList.get(0);
            pushTitle = gsMsg.getMessageVal0(
                "wml.push.title", __getSenderText(mailMdl.getFrom()));
            
            StringBuilder sb = new StringBuilder();
            sb.append(pushMessage);
            sb.append("\r\n");
            sb.append(mailMdl.getSubject());
            pushMessage = sb.toString();

            mailSid = String.valueOf(mailMdl.getMailNum());
        } else {
            //メールが複数件の場合は、タイトルに新着数を入れる
            String pushCount = gsMsg.getMessageVal0(
                "wml.push.mail.count", String.valueOf(mailDataList.size()));
            pushTitle = gsMsg.getMessageVal0("wml.push.title", pushCount);
        }
            
        WmlPushRequestModel pushMdl;
        for (int userSid : userList) {
            pushMdl = new WmlPushRequestModel();
            pushMdl.setPushUser(userSid);
            pushMdl.setAppId(GSConst.APP_GS_MOBILE);
            pushMdl.setAccountSid(wacSid__);
            pushMdl.setPushTitle(pushTitle);
            pushMdl.setPushMessage(pushMessage);
            Map<String, String> pushParamMap = new HashMap<>();
            pushParamMap.put("account", String.valueOf(accountMdl.getWacAccountId()));
            if (mailSid != null) {
                pushParamMap.put("sid", mailSid);
            }
            pushParamMap.put("plugin", GSConst.PLUGINID_WML);
            pushMdl.setPushParam(pushParamMap);
            pushMdlList.add(pushMdl);
        }
        
        log__.info("WEBメール プッシュ通知の実行");
        WmlSendPushThread.startThread(wacSid__, reqMdl__, pushMdlList);
    }

    /**
     * 送信メールアドレスから、プッシュ通知に使用する差出人文字列を取得する
     * @param address 送信メールアドレス
     * @return プッシュ通知に使用する差出人文字列
     * @throws AddressException 構文解析に失敗
     * @throws UnsupportedEncodingException 個人名のエンコーディングに失敗
     */
    private String __getSenderText(String address) throws AddressException, UnsupportedEncodingException {

        InternetAddress[] addressList = WmlBiz.parseAddressPlus(address, Encoding.ISO_2022_JP);
        if (addressList == null || addressList.length == 0) {
            return "";
        }

        //メールアドレスに差出人名が含まれている場合は、差出人名を返す
        InternetAddress addressMdl = addressList[0];
        if (!StringUtil.isNullZeroString(addressMdl.getPersonal())) {
            return addressMdl.getPersonal();
        }

        //メールアドレスに差出人名が含まれていない場合は、メールアドレスを返す
        return addressMdl.getAddress();
    }
}
