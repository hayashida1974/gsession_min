package jp.groupsession.v2.man.man520;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.lang.ClassUtil;
import jp.groupsession.v2.cmn.biz.DataUsedSizeBiz;
import jp.groupsession.v2.cmn.config.GSConfigConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnDatausedModel;
import jp.groupsession.v2.cmn.usedsize.IUsedDetailListener;
/**
 *
 * <br>[機  能] データ使用量一覧画面 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man520Biz {

    /**
     *
     * <br>[機  能] 初期表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param param パラメータモデル
     * @param reqMdl リクエスト情報
     * @param pconfig PluginConfig
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, RequestModel reqMdl,
                            Man520ParamModel param, PluginConfig pconfig)
    throws Exception {

        List<Man520DataModel> dataList = new ArrayList<Man520DataModel>();
        List<String> addedPluginList = new ArrayList<String>();

        DataUsedSizeBiz dataUsedBiz = new DataUsedSizeBiz(con);
        List<CmnDatausedModel> dataUsedList = dataUsedBiz.getDataUsedList();
        long sum = dataUsedBiz.getTotalByteSize();

        for (CmnDatausedModel dataUsedMdl : dataUsedList) {
            String id = dataUsedMdl.getCduPlugin();

            addedPluginList.add(id);

            //プラグインのディスク使用量操作リスナーを取得
            String[] listenerNameList = null;
            if (pconfig.getPlugin(id) != null) {
                List<String> pluginIdList = Arrays.asList(id);
                listenerNameList = pconfig.getListeners(pluginIdList,
                        GSConfigConst.NAME_USEDDATA_LISTENER);
            }

            //ディスク使用量操作リスナーが設定されている場合
            //プラグインのディスク使用量を取得する
            if (listenerNameList != null && listenerNameList.length > 0) {

                IUsedDetailListener usedDetailListener
                    = (IUsedDetailListener) ClassUtil.getDefConstractorObject(listenerNameList[0]);

                //画面表示用Modelを生成
                Man520DataModel dataMdl = __createDataModel(con, id, pconfig, reqMdl,
                                                            usedDetailListener);

                //使用データサイズ合計を設定
                long totalSize = dataUsedMdl.getCduSize();
                dataMdl.setTotalSize(totalSize);


                //プラグイン独自の集計マップを使用するかを判定
                boolean senyoDetailFlg = usedDetailListener.isUseSenyoDetails(con);
                dataMdl.setSenyoMapFlg(senyoDetailFlg);
                if (senyoDetailFlg) {
                    dataMdl.setSenyoMapHtml(usedDetailListener.getUseSenyoHtml(con, reqMdl));
                }

                dataList.add(dataMdl);
            }
        }
        //全プラグインの合計データをモデルに設定
        param.setSumValue(DataUsedSizeBiz.changeSizeString(sum));
        param.setSumUnit(DataUsedSizeBiz.sizeUnit(sum));


        //プラグイン別データ使用量集計が登録されていない場合
        //データサイズ = 0Byteとして画面表示用Modelを生成する
        List<String> pluginIdList = pconfig.getPluginIdList();
        for (String id : pluginIdList) {

            //下記条件の場合は表示しない
            //・画面表示用Model生成済み
            if (addedPluginList.contains(id)) {
                continue;
            }

            //プラグインのディスク使用量操作リスナーが存在しない場合
            //画面表示対象外とする
            String[] listenerNameList = pconfig.getListeners(Arrays.asList(id),
                    GSConfigConst.NAME_USEDDATA_LISTENER);

            if (listenerNameList == null || listenerNameList.length <= 0) {
                continue;
            }

            //画面表示用Modelを生成
            Man520DataModel dataMdl = __createDataModel(con, id, pconfig, reqMdl,
                                                        null);

            //使用データサイズ合計を設定
            dataMdl.setTotalSize(0);
            dataList.add(dataMdl);
        }

        param.setDataList(dataList);
    }

    /**
     * <br>[機  能] 画面表示用Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param id プラグインID
     * @param pconfig PluginConfig
     * @param reqMdl リクエスト情報
     * @param usedDetailListener データ使用量取得リスナー
     * @return 画面表示用Model
     * @throws SQLException SQL実行時例外
     */
    private Man520DataModel __createDataModel(Connection con, String id,
                                            PluginConfig pconfig, RequestModel reqMdl,
                                            IUsedDetailListener usedDetailListener)
    throws SQLException, Exception {
        Man520DataModel dataMdl = new Man520DataModel();

        //プラグインID、プラグイン名称、アイコン画像パスを設定
        dataMdl.setPluginId(id);
        dataMdl.setPluginName(pconfig.getPlugin(id).getName(reqMdl));
        dataMdl.setIconOriginal(pconfig.getPlugin(id).getPrivateSettingInfo()
                                .getIcon());
        dataMdl.setIconClassic(pconfig.getPlugin(id).getPrivateSettingInfo()
                                .getIconClassic());
        if (dataMdl.getIconOriginal() == null) {
            dataMdl.setIconOriginal(pconfig.getPlugin(id).getAdminSettingInfo()
                                    .getIcon());
            dataMdl.setIconClassic(pconfig.getPlugin(id).getAdminSettingInfo()
                                    .getIconClassic());
        }

        //使用データサイズの明細を設定
        if (usedDetailListener != null) {

            List<String> nameList = new ArrayList<String>();
            Map<String, Long> sizeMap = usedDetailListener.getDatails(con);
            nameList.addAll(sizeMap.keySet());

            dataMdl.setNameList(nameList);
            dataMdl.setSizeMap(sizeMap);
        }

        return dataMdl;
    }
}
