package jp.groupsession.v2.wml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.wml.dao.base.WmlDatausedSumDao;
import jp.groupsession.v2.wml.dao.base.WmlMailLogDao;
import jp.groupsession.v2.wml.dao.base.WmlMailLogSendDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.base.WmlDatausedSumModel;

/**
 * <br>[機  能] WEBメールプラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public WmlUsedDataBiz(Connection con) {
        con__ = con;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] メールテンプレート情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSid メールテンプレートSID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertTemplateSize(int wtpSid, boolean entryType)
    throws SQLException {
        if (wtpSid <= 0) {
            return;
        }

        insertTemplateSize(Arrays.asList(wtpSid), entryType);
    }

    /**
     * <br>[機  能] メールテンプレート情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSidList メールテンプレートSID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertTemplateSize(List<Integer> wtpSidList, boolean entryType)
    throws SQLException {

        if (wtpSidList == null || wtpSidList.isEmpty()) {
            return;
        }

        //メールテンプレート情報のサイズを取得
        WmlMailTemplateDao templateDao = new WmlMailTemplateDao(con__);
        long templateDataSize = templateDao.getTotalDataSize(wtpSidList);

        //メールテンプレート情報の添付ファイルサイズを取得
        WmlMailTemplateFileDao templateFileDao = new WmlMailTemplateFileDao(con__);
        templateDataSize += templateFileDao.getTotalFileSize(wtpSidList);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            templateDataSize *= -1;
        }

        //使用データサイズを登録
        __insertDatausedSum(templateDataSize, 0);
    }

    /**
     * <br>[機  能] メールログの使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param messageNum メッセージ番号
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertMaillogSize(long messageNum, boolean entryType)
    throws SQLException {
        insertMaillogSize(Arrays.asList(messageNum), entryType);
    }

    /**
     * <br>[機  能] メールログの使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param messageNumList メッセージ番号
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertMaillogSize(List<Long> messageNumList, boolean entryType)
    throws SQLException {

        if (messageNumList == null || messageNumList.isEmpty()) {
            return;
        }

        long maillogSize = 0;

        //※1000件ずつ処理
        List<Long> subList = null;
        WmlMailLogDao mailLogDao = new WmlMailLogDao(con__);
        WmlMailLogSendDao mailLogSendDao = new WmlMailLogSendDao(con__);
        int fromIdx = 0;
        for (int idx = 0; idx < messageNumList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == messageNumList.size()) {

                subList = messageNumList.subList(fromIdx, idx + 1);

                //メールログのサイズを取得
                maillogSize += mailLogDao.getTotalDataSize(subList);

                //メールログ_送信先のサイズを取得
                maillogSize += mailLogSendDao.getTotalDataSize(subList);

                fromIdx = idx + 1;
            }
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            maillogSize *= -1;
        }

        //使用データサイズを登録
        __insertDatausedSum(0, maillogSize);
    }

    /**
     * <br>[機  能] 全メールログの使用データサイズを登録(減算)する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void removeAllMailLogSize() throws SQLException {

        //メールログのサイズを取得
        WmlMailLogDao mailLogDao = new WmlMailLogDao(con__);
        long maillogSize = mailLogDao.getAllDataSize();

        //メールログ_送信先のサイズを取得
        WmlMailLogSendDao mailLogSendDao = new WmlMailLogSendDao(con__);
        maillogSize += mailLogSendDao.getAllDataSize();

        //データサイズを負数に反転する(メールログの削除を想定)
        maillogSize *= -1;

        //使用データサイズを登録
        __insertDatausedSum(0, maillogSize);
    }

    /**
     * <br>[機  能] 使用データサイズ集計データの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param templateDataSize メールテンプレート情報のサイズ
     * @param mailLogSize メールログのサイズ
     * @throws SQLException SQL実行例外
     */
    private void __insertDatausedSum(long templateDataSize, long mailLogSize)
    throws SQLException {

        //使用データサイズを登録
        WmlDatausedSumModel dataUsedMdl = new WmlDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setWacDiscsizeSum(0);
        dataUsedMdl.setWtpDiscsizeSum(templateDataSize);
        dataUsedMdl.setWlgDiscsizeSum(mailLogSize);
        WmlDatausedSumDao dataUsedDao = new WmlDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}
