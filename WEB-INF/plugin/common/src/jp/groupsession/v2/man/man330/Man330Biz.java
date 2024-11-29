package jp.groupsession.v2.man.man330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man330.model.Man330CsvGroupDataModel;
import jp.groupsession.v2.man.man330.model.Man330CsvModel;
import jp.groupsession.v2.man.man330.model.Man330ExpModel;

/**
 * <br>[機  能] メイン 所属情報一括設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man330Biz.class);

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Man330Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] CSV出力対象がNULLの場合、CSV出力対象のデフォルト値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setDefultCsvOut(Man330ParamModel paramMdl) {
        //CSV出力対象
        if (paramMdl.getMan330CsvOutField() == null) {
            paramMdl.setMan330CsvOutField(Man330Biz.getDefultCsvOut());
        }
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考] インポートタブ専用
     *
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitDataImport(Man330ParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        }
        paramMdl.setMan330FileLabelList(fileLblList);
    }

    /**
     *
     * <br>[機  能] ユーザ所属グループデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return ScheduleListSearchModel 検索条件モデル
     * @throws SQLException SQL実行時例外
     */
    public List<Man330CsvModel> getCsvOutModel(Man330ParamModel paramMdl)
    throws SQLException {

        //ユーザ所属情報を取得
        UserSearchDao usDao = new UserSearchDao(con__);
        List<Man330ExpModel> expList = usDao.getUserDataBelongm();

        //変数を定義
        List<Man330CsvModel> outMdlList = new ArrayList<Man330CsvModel>();
        List<Man330CsvGroupDataModel> grpDataMdlList = new ArrayList<Man330CsvGroupDataModel>();
        Man330CsvModel csvMdl = new Man330CsvModel();
        int usrSid = 0;
        int roopCnt = 0;
        boolean addFlg = false;

        //登録ユーザが１名かつ所属グループが１つの場合
        if (expList.size() == 1) {
            //ユーザ情報セット
            csvMdl.setUserId(expList.get(0).getUserId());
            csvMdl.setUsrName(expList.get(0).getUsrName());
            csvMdl.setUsrNameKana(expList.get(0).getUsrNameKana());

            //グループ情報セット
            Man330CsvGroupDataModel oneGrpDataMdl = new Man330CsvGroupDataModel();
            oneGrpDataMdl.setGroupId(expList.get(0).getGroupId());
            oneGrpDataMdl.setGroupName(expList.get(0).getGroupName());
            oneGrpDataMdl.setGroupNameKana(expList.get(0).getGroupNameKana());
            grpDataMdlList.add(oneGrpDataMdl);
            csvMdl.setGrpDataList(grpDataMdlList);

            outMdlList.add(csvMdl);
            return outMdlList;
        }

        for (Man330ExpModel model : expList) {
            Man330CsvGroupDataModel grpDataMdl = new Man330CsvGroupDataModel();

            if (usrSid != model.getUserSid()) {
                addFlg = true;

                if (addFlg && roopCnt > 0) {
                    //外側のリストにセット
                    csvMdl.setGrpDataList(grpDataMdlList);
                    outMdlList.add(csvMdl);

                    //初期化
                    addFlg = false;
                    grpDataMdlList = new ArrayList<Man330CsvGroupDataModel>();
                    csvMdl = new Man330CsvModel();

                } else if (addFlg && roopCnt == 0) {
                    //初回ループ時
                    addFlg = false;
                }

                //ユーザ情報セット
                csvMdl.setUserId(model.getUserId());
                csvMdl.setUsrName(model.getUsrName());
                csvMdl.setUsrNameKana(model.getUsrNameKana());
                usrSid = model.getUserSid();
            }

            //グループ情報セット
            grpDataMdl.setGroupId(model.getGroupId());
            grpDataMdl.setGroupName(model.getGroupName());
            grpDataMdl.setGroupNameKana(model.getGroupNameKana());
            grpDataMdlList.add(grpDataMdl);

            if (roopCnt == expList.size() - 1) {
                //最後の行
                csvMdl.setGrpDataList(grpDataMdlList);
                outMdlList.add(csvMdl);
            }
            roopCnt++;
        }

        return outMdlList;
    }

    /**
     * <br>[機  能] 全グループIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return cgMdlList 全グループID
     * @throws SQLException SQL実行例外
     */
    public List<String> getAllGrpIdList() throws SQLException {

        List<String> cgMdlList = new ArrayList<String>();
        CmnGroupmDao cgDao = new CmnGroupmDao(con__);
        cgMdlList = cgDao.getGroupIdList(CmnGroupmDao.GRP_JKBN_LIVING);

        return cgMdlList;
    }

    /**
     * <br>[機  能] 全ユーザIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return cgMdlList 全ユーザID
     * @throws SQLException SQL実行例外
     */
    public List<String> getAllUsrIdList() throws SQLException {

        List<String> cuMdlList = new ArrayList<String>();
        CmnUsrmDao cuDao = new CmnUsrmDao(con__);
        cuMdlList = cuDao.getUsrAllId();

        return cuMdlList;
    }

    /**
     * <br>[機  能] 所属するグループが一番多いユーザのグループ数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param outMdlList ユーザデータ
     * @return maxGrpCnt 最大所属グループ数
     */
    public int getMaxGrpCnt(List<Man330CsvModel> outMdlList) {
        int grpCnt = 0;
        int maxGrpCnt = 0;

        for (Man330CsvModel model : outMdlList) {
            grpCnt = model.getGrpDataList().size();

            if (grpCnt > maxGrpCnt) {
                maxGrpCnt = grpCnt;
            }
        }

        return maxGrpCnt;
    }

    /**
     * <br>[機  能] CSV出力のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトCSV出力項目
     */
    public static String[] getDefultCsvOut() {
        String[] csvOut = {
                GSConstMain.CSV_OUT_USER_ID,
                GSConstMain.CSV_OUT_GROUP_ID
        };
        return csvOut;
    }

}
