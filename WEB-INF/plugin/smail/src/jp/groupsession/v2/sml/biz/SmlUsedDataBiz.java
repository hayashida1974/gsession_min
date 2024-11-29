package jp.groupsession.v2.sml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlBodyBinDao;
import jp.groupsession.v2.sml.dao.SmlDatausedSumDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlDatausedSumModel;

/**
 * <br>[機  能] ショートメールプラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public SmlUsedDataBiz(Connection con) {
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
     * <br>[機  能] ショートメール明細(送信)の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param smsSid メールSID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertSendDataSize(int smsSid, boolean entryType)
    throws SQLException {
        if (smsSid <= 0) {
            return;
        }

        insertSendDataSize(Arrays.asList(smsSid), entryType);
    }

    /**
     * <br>[機  能] ショートメール明細(送信)の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param smsSidList メールSID一覧
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertSendDataSize(List<Integer> smsSidList, boolean entryType)
    throws SQLException {

        if (smsSidList == null || smsSidList.isEmpty()) {
            return;
        }

        SmlSmeisDao smeisDao = new SmlSmeisDao(con__);

        long mailDataSize = 0;
        SmlBinDao smlBinDao = new SmlBinDao(con__);
        SmlBodyBinDao sbbDao = new SmlBodyBinDao(con__);

        int fromIdx = 0;
        List<Integer> subList = null;
        for (int idx = 0; idx < smsSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == smsSidList.size()) {
                subList = smsSidList.subList(fromIdx, idx + 1);

                if (subList != null && !subList.isEmpty()) {
                    //ショートメール情報(送信)のデータサイズ取得
                    mailDataSize += smeisDao.getTotalDataSize(subList);

                    //添付ファイルサイズ取得
                    mailDataSize += smlBinDao.getTotalFileSize(subList);
                    
                    //本文内の添付ファイルサイズ取得
                    mailDataSize += sbbDao.getTotalFileSize(smsSidList);
                }
                fromIdx = idx + 1;
            }
        }


        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            mailDataSize *= -1;
        }

        __insertDatausedSum(mailDataSize, 0);
    }

    /**
     * <br>[機  能] ショートメール明細(下書き)の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param smwSidList メールSID一覧
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertSoukouDataSize(List<Integer> smwSidList, boolean entryType)
    throws SQLException {

        if (smwSidList == null || smwSidList.isEmpty()) {
            return;
        }

        SmlWmeisDao wmeisDao = new SmlWmeisDao(con__);
        SmlBinDao smlBinDao = new SmlBinDao(con__);
        SmlBodyBinDao sbbDao = new SmlBodyBinDao(con__);

        //メール情報 行毎のデータサイズ取得
        long mailDataSize = 0;

        int fromIdx = 0;
        List<Integer> subList = null;
        for (int idx = 0; idx < smwSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == smwSidList.size()) {
                subList = smwSidList.subList(fromIdx, idx + 1);

                if (subList != null && !subList.isEmpty()) {
                    //ショートメール草稿情報のデータサイズ取得
                    mailDataSize += wmeisDao.getTotalDataSize(subList);

                    //添付ファイルサイズ取得
                    mailDataSize += smlBinDao.getTotalFileSize(subList);
                    
                    //本文内の添付ファイルサイズ取得
                    mailDataSize += sbbDao.getTotalFileSize(subList);
                }

                fromIdx = idx + 1;
            }
        }


        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            mailDataSize *= -1;
        }

        __insertDatausedSum(mailDataSize, 0);
    }

    /**
     * <br>[機  能] 使用データサイズ集計データの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailDataSize メール情報データサイズ
     * @param accountDataSize アカウントデータサイズ
     * @throws SQLException SQL実行例外
     */
    private void __insertDatausedSum(long mailDataSize, long accountDataSize)
    throws SQLException {

        //使用データサイズを登録
        SmlDatausedSumModel dataUsedMdl = new SmlDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setSmlMailSize(mailDataSize);
        dataUsedMdl.setSacDiscsizeSum(accountDataSize);
        SmlDatausedSumDao dataUsedDao = new SmlDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}
