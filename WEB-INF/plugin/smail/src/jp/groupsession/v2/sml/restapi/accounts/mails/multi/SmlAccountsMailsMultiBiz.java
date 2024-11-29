package jp.groupsession.v2.sml.restapi.accounts.mails.multi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlWmeisModel;

/**
 * <br>[機  能] メール状態変更API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsMailsMultiBiz {
    /**
    *
    * <br>[機  能] ゴミ箱メール削除
    * <br>[解  説]
    * <br>[備  考]
    * @param inboxSidList 受信メールSID
    * @param sentSidList 送信メールSID
    * @param draftSidList 草稿メールSID
    * @param con コネクション
    * @param accountSid アカウントSID
    * @param sessionUserSid セッションユーザSID
    * @throws SQLException
    */
    public void deleteTrashMail(int[] inboxSidList, int[] sentSidList, int[] draftSidList,
                        Connection con, int accountSid, int sessionUserSid) throws SQLException {
        //アカウントディスク使用量の再計算を行う
        SmlCommonBiz smlBiz = new SmlCommonBiz();
        long sum = 0;

        //受信メッセージ(論理削除)
        if (inboxSidList != null && inboxSidList.length > 0) {
            SmlJmeisDao jdao = new SmlJmeisDao(con);
            String[] sidArray = new String[inboxSidList.length];
            for (int i = 0; i < inboxSidList.length; i++) {
                // 後のロジックで1文字目を除外する為、回避用に1文字追加
                sidArray[i] = "0" + String.valueOf(inboxSidList[i]);
            }
            jdao.moveJmeis(
                    sessionUserSid,
                    accountSid,
                    GSConstSmail.SML_JTKBN_DELETE,
                    new UDate(),
                    sidArray);

            List<Integer> desSidListInt = Stream.of(sidArray)
                    .map(sid -> Integer.parseInt(sid.substring(1)))
                    .collect(Collectors.toList());
            Map<Integer, Long> delMailList = jdao.getDeleteMail(
                    desSidListInt, 2, desSidListInt.size(), 0);

            for (Map.Entry<Integer, Long> map : delMailList.entrySet()) {
                sum += map.getValue();
            }
        }
        //送信メッセージ(論理削除)
        if (sentSidList != null && sentSidList.length > 0) {
            SmlSmeisDao sdao = new SmlSmeisDao(con);
            String[] sidArray = new String[sentSidList.length];
            for (int i = 0; i < sentSidList.length; i++) {
                // 後のロジックで1文字目を除外する為、回避用に1文字追加
                sidArray[i] = "0" + String.valueOf(sentSidList[i]);
            }
            sdao.moveSmeis(
                    sessionUserSid,
                    accountSid,
                    GSConstSmail.SML_JTKBN_DELETE,
                    new UDate(),
                    sidArray);

            List<Integer> desSidListInt = Stream.of(sidArray)
                    .map(sid -> Integer.parseInt(sid.substring(1)))
                    .collect(Collectors.toList());
            Map<String, Long> delMailList = sdao.getDeleteMail(
                    desSidListInt, 2, desSidListInt.size(), 0);

            for (Map.Entry<String, Long> map : delMailList.entrySet()) {
                sum += map.getValue();
            }
        }
        //草稿メッセージ(物理削除)
        if (draftSidList != null && draftSidList.length > 0) {

            //ショートメール情報(草稿)のデータ使用量を登録(削除対象のデータ使用量を減算)
            List<Integer> smwSidList = new ArrayList<Integer>();
            String[] sidArray = new String[draftSidList.length];
            for (int i = 0; i < draftSidList.length; i++) {
                // 後のロジックで1文字目を除外する為、回避用に1文字追加
                sidArray[i] = "0" + String.valueOf(draftSidList[i]);
            }
            for (String delSid : sidArray) {
                smwSidList.add(Integer.parseInt(delSid.substring(1)));
            }
            SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
            usedDataBiz.insertSoukouDataSize(smwSidList, false);

            //ラベル
            SmailDao smlDao = new SmailDao(con);
            smlDao.deleteListLabel(accountSid, sidArray);
            //添付情報(論理削除)
            SmlWmeisDao wdao = new SmlWmeisDao(con);
            List<Integer> desSidListInt = Stream.of(sidArray)
                    .map(sid -> Integer.parseInt(sid.substring(1)))
                    .collect(Collectors.toList());
            Map<SmlWmeisModel, Long> delMailList = wdao.getDeleteMail(
                    desSidListInt, 2, desSidListInt.size(), 0);

            for (Map.Entry<SmlWmeisModel, Long> map : delMailList.entrySet()) {
                sum += map.getValue();
            }

            wdao.deleteSoukouBin(accountSid, sidArray);
            //草稿
            wdao.deleteMsgButuri(accountSid, sidArray);
            SmlAsakDao adao = new SmlAsakDao(con);
            adao.deleteMsgButuri(accountSid, sidArray);
        }

        //データ容量の計算
        smlBiz.updateAccountDiskSize(con, accountSid, -sum);
    }
}
