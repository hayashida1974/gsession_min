package jp.groupsession.v2.rng.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.rng.dao.RngBinDao;
import jp.groupsession.v2.rng.dao.RngDatausedSumDao;
import jp.groupsession.v2.rng.dao.RngFormdataDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.dao.RngTemplateBinDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplateFormDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao;
import jp.groupsession.v2.rng.model.RngDatausedSumModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <br>[機  能] 回覧板プラグインの使用データサイズを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngUsedDataBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Default Constructor
     * @param con コネクション
     */
    public RngUsedDataBiz(Connection con) {
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
     * <br>[機  能] 稟議申請情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertSinseiDataSize(int rngSid, boolean entryType)
    throws SQLException {
        if (rngSid <= 0) {
            return;
        }

        insertSinseiDataSize(Arrays.asList(rngSid), entryType);
    }

    /**
     * <br>[機  能] 稟議申請情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertSinseiDataSize(List<Integer> rngSidList, boolean entryType)
    throws SQLException {

        if (rngSidList == null || rngSidList.isEmpty()) {
            return;
        }

        long sinseiDataSize = 0;

        RngFormdataDao formDataDao = new RngFormdataDao(con__);
        RngSingiDao singiDao = new RngSingiDao(con__);
        RngBinDao rngBinDao = new RngBinDao(con__);

        int fromIdx = 0;
        List<Integer> subList = null;
        for (int idx = 0; idx < rngSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == rngSidList.size()) {
                subList = rngSidList.subList(fromIdx, idx + 1);
                //稟議フォーム入力値情報のサイズを取得
                sinseiDataSize += formDataDao.getTotalInputDataSize(subList);

                //稟議審議情報のサイズを取得
                sinseiDataSize += singiDao.getDataSize(subList);

                //稟議添付ファイルのファイルサイズを取得
                sinseiDataSize += rngBinDao.getTotalFileSize(subList);

                fromIdx = idx + 1;
            }
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            sinseiDataSize *= -1;
        }

        //使用データサイズを登録
        __insertDatausedSum(sinseiDataSize, 0);
    }

    /**
     * <br>[機  能] 稟議テンプレート情報の使用データサイズを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSidList 稟議テンプレート情報
     * @param entryType 登録種別 true: 加算、false: 減算
     * @throws SQLException SQL実行例外
     */
    public void insertTemplateDataSize(List<RngTemplateModel> rtpSidList, boolean entryType)
    throws SQLException {

        if (rtpSidList == null || rtpSidList.isEmpty()) {
            return;
        }

        long templateDataSize = 0;

        RngTemplateDao templateDao = new RngTemplateDao(con__);
        RngTemplateFormDao templateFormDao = new RngTemplateFormDao(con__);
        RngTemplateKeiroDao templateKeiroDao = new RngTemplateKeiroDao(con__);
        RngTemplateBinDao rngBinDao = new RngTemplateBinDao(con__);

        int fromIdx = 0;
        List<RngTemplateModel> subList = null;
        for (int idx = 0; idx < rtpSidList.size(); idx++) {
            if (idx % 1000 == 999 || idx + 1 == rtpSidList.size()) {
                subList = rtpSidList.subList(fromIdx, idx + 1);
                //稟議テンプレート情報のサイズを取得
                templateDataSize += templateDao.getTotalFormDataSize(subList);

                //稟議テンプレートフォーム情報のサイズを取得
                templateDataSize += templateFormDao.getTemplateFormDataSize(subList);

                //経路テンプレートステップ情報のサイズを取得
                templateDataSize += templateKeiroDao.getTemplateKeiroCount(subList);

                //稟議テンプレートの添付ファイルサイズを取得
                templateDataSize += rngBinDao.getTotalFileSize(subList);

                fromIdx = idx + 1;
            }
        }

        //減算の場合はデータサイズを負数に反転する
        if (!entryType) {
            templateDataSize *= -1;
        }

        //使用データサイズを登録
        __insertDatausedSum(0, templateDataSize);
    }


    /**
     * <br>[機  能] 使用データサイズ集計データの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sinseiDataSize 稟議申請情報のサイズ
     * @param templateSize 稟議テンプレート情報のサイズ
     * @throws SQLException SQL実行例外
     */
    private void __insertDatausedSum(long sinseiDataSize, long templateSize)
    throws SQLException {

        //使用データサイズを登録
        RngDatausedSumModel dataUsedMdl = new RngDatausedSumModel();
        dataUsedMdl.setSumType(GSConst.USEDDATA_SUMTYPE_DIFF);
        dataUsedMdl.setRngRndataDiskSize(sinseiDataSize);
        dataUsedMdl.setRngTemplateDiskSize(templateSize);
        RngDatausedSumDao dataUsedDao = new RngDatausedSumDao(con__);
        dataUsedDao.insert(dataUsedMdl);
    }
}
