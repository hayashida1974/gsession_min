package jp.groupsession.v2.man.biz;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.usedsize.UsedSizeConst;
import jp.groupsession.v2.man.dao.base.ManDatausedSumDao;
import jp.groupsession.v2.man.model.base.ManDatausedSumModel;

/**
 * <br>[機  能] アンケートプラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MainUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public MainUsedDataBiz(Connection con) {
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
     * <br>[機  能] オペレーションログ情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertOpLogDataSize(boolean entryType)
    throws SQLException {
        insertOpLogDataSize(1, entryType);
    }

    /**
     * <br>[機  能] オペレーションログ情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param dataCount データ件数
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertOpLogDataSize(int dataCount, boolean entryType)
    throws SQLException {

        if (dataCount == 0) {
            return;
        }

        long dataSize = UsedSizeConst.SIZE_CMN_LOG * dataCount;

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            dataSize *= -1;
        }

        __insertManDataSize(dataSize, 0);
    }

    /**
     * <br>[機  能] ログイン履歴情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @param usrSid ユーザSID
     * @param ip IPアドレス
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertLoginHistoryDataSize(UDate date, int usrSid, String ip, boolean entryType)
    throws SQLException {

        //ログイン履歴情報のデータサイズを取得
        CmnLoginHistoryDao loginHistoryDao = new CmnLoginHistoryDao(con__);
        long dataSize = loginHistoryDao.getLoginHistoryDataSize(date, usrSid, ip);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            dataSize *= -1;
        }

        __insertManDataSize(0, dataSize);
    }

    /**
     * <br>[機  能] ログイン履歴情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考] 指定した日時より前に登録されたログイン履歴のデータサイズを集計する
     * @param date 日時
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertLoginHistoryDataSize(UDate date, boolean entryType)
    throws SQLException {

        //ログイン履歴情報のデータサイズを取得
        CmnLoginHistoryDao loginHistoryDao = new CmnLoginHistoryDao(con__);
        long dataSize = loginHistoryDao.getLoginHistoryDataSize(date);

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            dataSize *= -1;
        }

        __insertManDataSize(0, dataSize);
    }

    /**
     * <br>[機  能] メイン 使用データサイズ集計の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param opLogSize オペレーションログサイズ
     * @param loginHistorySize ログイン履歴サイズ
     * @throws SQLException SQL実行時例外
     */
    private void __insertManDataSize(long opLogSize, long loginHistorySize)
    throws SQLException {

        //使用データサイズを登録
        ManDatausedSumModel dataUsedMdl = new ManDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setCmnLogSize(opLogSize);
        dataUsedMdl.setCmnLoginHistorySize(loginHistorySize);
        ManDatausedSumDao dataUsedDao = new ManDatausedSumDao(con__);

        dataUsedDao.insert(dataUsedMdl);
    }
}
