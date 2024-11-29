package jp.groupsession.v2.adr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.adr.dao.AdrContactBinDao;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.dao.AdrDatausedSumDao;
import jp.groupsession.v2.adr.model.AdrDatausedSumModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] アドレス帳プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public AdrUsedDataBiz(Connection con) {
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
     * <br>[機  能] コンタクト履歴情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param adcSidList コンタクト履歴SIDリスト
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertContactSize(List<Integer> adcSidList, boolean entryType)
    throws SQLException {
        if (adcSidList == null || adcSidList.isEmpty()) {
            return;
        }
        //指定されたコンタクト履歴のデータサイズを取得
        AdrContactDao contactDao = new AdrContactDao(con__);
        long contactSize = contactDao.getDataSize(adcSidList);

        //コンタクト履歴の添付ファイルサイズを取得
        AdrContactBinDao contactBinDao = new AdrContactBinDao(con__);
        long fileSize = contactBinDao.getSumFileSize(adcSidList);
        contactSize += fileSize;

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            contactSize *= -1;
        }

        //使用データサイズを登録
        AdrDatausedSumModel dataUsedMdl = new AdrDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setAdrContactSize(contactSize);
        AdrDatausedSumDao dataUsedDao = new AdrDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}
