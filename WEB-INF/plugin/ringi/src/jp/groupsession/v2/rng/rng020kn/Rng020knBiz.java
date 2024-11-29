package jp.groupsession.v2.rng.rng020kn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rng.IRingiListener;
import jp.groupsession.v2.rng.RingiListenerModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.model.RingiChannelDataModel;
import jp.groupsession.v2.rng.rng020.IRng020PeronalParam;
import jp.groupsession.v2.rng.rng020.Rng020Biz;
import jp.groupsession.v2.rng.rng020.Rng020Keiro;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;
import jp.groupsession.v2.rng.rng020.Rng020KeiroKakuninDsp;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議作成確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Rng020knBiz.class);

    /** 確認用添付ファイルダウンロードURL */
    public static final String TEMPFILE_DOWNLOAD_URL =
            "../ringi/rng020kn.do?CMD=fileDownload&rng020BinSid={binSid}&rng020BinDirId={dirId}";

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル*/
    private RequestModel reqMdl__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rng020knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param userMdl セッションユーザ情報
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力処理に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws RtpNotfoundException テンプレート存在例外
     * @throws TempFileException 添付ファイル操作時例外
     * @throws EnumOutRangeException 列挙型不正例外
     */
    public void setInitData(RequestModel reqMdl, IRng020knPersonalParam paramMdl,
                            String appRoot, GSTemporaryPathModel tempDir, BaseUserModel userMdl)
    throws IOException, IOToolsException, SQLException,
    TempFileException, RtpNotfoundException, EnumOutRangeException {

        Rng020Biz rng020Biz = new Rng020Biz(con__, reqMdl__);
        rng020Biz.setDspData(paramMdl, appRoot, tempDir, userMdl, false, TEMPFILE_DOWNLOAD_URL);

        setViewKeiroData(paramMdl, userMdl);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 経路情報(表示用)を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userMdl セッションユーザ情報
     * @throws SQLException SQL実行例外
     * @throws EnumOutRangeException 列挙型不正例外
     */
    public void setViewKeiroData(IRng020knPersonalParam paramMdl, BaseUserModel userMdl)
    throws SQLException, EnumOutRangeException {

        for (Entry<Integer, Rng020KeiroBlock> entryBlock
                : paramMdl.getRng020keiroMap().entrySet()) {
            Rng020KeiroBlock block = entryBlock.getValue();
            for (Entry<Integer, Rng020Keiro> entry : block.getKeiroMap().entrySet()) {
                Rng020Keiro keiro = entry.getValue();
                keiro.dspInitSingiList(userMdl.getUsrsid(), block, con__);
            }
        }
        for (Entry<Integer, Rng020KeiroBlock> entryBlock
                : paramMdl.getRng020kakuninKeiroMap().entrySet()) {
            Rng020KeiroBlock block = entryBlock.getValue();
            for (Entry<Integer, Rng020Keiro> entry : block.getKeiroMap().entrySet()) {
                Rng020Keiro keiro = entry.getValue();
                keiro.dspInitSingiList(userMdl.getUsrsid(), block, con__);
            }
        }
        //確認経路から表示用マップを生成
        Rng020KeiroBlock dspBlock = paramMdl.getRng020kakuninKeiroDsp("0");
        Rng020Keiro dspKakuninKeiro = dspBlock.getKeiro("0");
        dspKakuninKeiro.setKeiroKbn(EnumKeiroKbn.FREESET_VAL);
        dspBlock.setKeiroKbn(EnumKeiroKbn.FREESET_VAL);
        //描画用リストを設定
        List<Rng020KeiroKakuninDsp> dspList = dspKakuninKeiro.getDspSingiList();
        if (dspList == null) {
            dspList = new ArrayList<Rng020KeiroKakuninDsp>();
            dspKakuninKeiro.setDspSingiList(dspList);
        }
        Rng020KeiroKakuninDsp dsp = new Rng020KeiroKakuninDsp();
        dspList.add(dsp);
        dsp.setName("");
        HashSet<String> singiSidSet = new HashSet<String>();
        for (Entry<Integer, Rng020KeiroBlock> entryBlock
                : paramMdl.getRng020kakuninKeiroMap().entrySet()) {
            Rng020KeiroBlock block = entryBlock.getValue();
            if (block.getHidden() == 1) {
                continue;
            }
            for (Entry<Integer, Rng020Keiro> entry : block.getKeiroMap().entrySet()) {
                Rng020Keiro keiro = entry.getValue();
                for (Rng020KeiroKakuninDsp kdsp : keiro.getDspSingiList()) {
                    List<UsrLabelValueBean> singiList = kdsp.getSingi();
                    if (singiList == null) {
                        continue;
                    }
                    for (UsrLabelValueBean user : singiList) {
                        if (!singiSidSet.contains(user.getValue())) {
                            singiSidSet.add(user.getValue());
                        }
                    }
                }
            }
        }

        UserGroupSelectBiz biz = new UserGroupSelectBiz();
        List<UsrLabelValueBean> selectedList = biz.getSelectedLabel(
                singiSidSet.toArray(new String[singiSidSet.size()]),
                reqMdl__.getSmodel().getUsrsid(), con__,
                GSConstUser.USER_JTKBN_ACTIVE);
        dsp.setSingi(selectedList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 表示用のユーザリスト一覧を取得する
     * <br>[解  説] 指定したユーザの表示用ユーザリスト一覧を取得する
     * <br>[備  考]
     * @param userSidList ユーザSID一覧
     * @return 表示用のユーザリスト一覧
     * @throws SQLException SQL実行時例外
     */
    public List < RingiChannelDataModel > __getUserLabelList(String[] userSidList)
    throws SQLException {
        List<RingiChannelDataModel> userLabelList = new ArrayList<RingiChannelDataModel>();
        CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con__);
        List < CmnUsrmInfModel > userInfList
            = uinfDao.getUsersInfList(userSidList);
        RingiChannelDataModel model = new RingiChannelDataModel();

        //承認順のソートを行い、一覧モデルに設定する。
        for (String userSid : userSidList) {
            int intValue = Integer.parseInt(userSid);
            for (CmnUsrmInfModel usrMdl : userInfList) {
                if (intValue == usrMdl.getUsrSid()) {
                    model = new RingiChannelDataModel();
                    model.setUserName(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
                    model.setYakusyoku(usrMdl.getUsiYakusyoku());
                    model.setUsrUkoFlg(usrMdl.getUsrUkoFlg());
                    userLabelList.add(model);
                }
            }
        }
        return userLabelList;
    }
    /**
     *
     * <br>[機  能] 最終確認経路を経路ステップ一つの最終確認経路に変換
     * <br>[解  説]
     * <br>[備  考]
     * @param kakuninKeiro 最終確認経路
     * @return 最終確認経路
     */
    public Map<Integer, Rng020KeiroBlock> changeOneKakuninKeiro(
            Map<Integer, Rng020KeiroBlock> kakuninKeiro) {
        Map<Integer, Rng020KeiroBlock> ret = new HashMap<Integer, Rng020KeiroBlock>();
        Rng020KeiroBlock retBlock = new Rng020KeiroBlock();
        ret.put(0, retBlock);
        Rng020Keiro retKeiro = retBlock.getKeiro("0");
        retKeiro.setKeiroKbn(EnumKeiroKbn.USERTARGET_VAL);
        retBlock.setKeiroKbn(EnumKeiroKbn.USERTARGET_VAL);

        HashSet<String> singiSidSet = new HashSet<String>();
        for (Entry<Integer, Rng020KeiroBlock> entryBlock
                : kakuninKeiro.entrySet()) {
            Rng020KeiroBlock block = entryBlock.getValue();
            if (block.getHidden() == 1) {
                continue;
            }
            for (Entry<Integer, Rng020Keiro> entry : block.getKeiroMap().entrySet()) {
                Rng020Keiro keiro = entry.getValue();
                for (Rng020KeiroKakuninDsp kdsp : keiro.getDspSingiList()) {
                    List<UsrLabelValueBean> singiList = kdsp.getSingi();
                    if (singiList == null) {
                        continue;
                    }
                    for (UsrLabelValueBean user : singiList) {
                        if (!singiSidSet.contains(user.getValue())) {
                            singiSidSet.add(user.getValue());
                        }
                    }
                }
            }
        }
        retKeiro.getUsrgrpSel().setSelected(UserGroupSelectModel.KEY_DEFAULT,
                singiSidSet.toArray(new String[singiSidSet.size()]));

        return ret;

    }

    /**
     *
     * <br>[機  能] 最終確認経路を経路ステップ一つの最終確認経路に変換
     * <br>[解  説]
     * <br>[備  考]
     * @param kakuninKeiro 最終確認経路
     * @return 最終確認経路
     */
    public Map<Integer, Rng020KeiroBlock> getSvCopyKakuninKeiro(
            Map<Integer, Rng020KeiroBlock> kakuninKeiro) {
        Map<Integer, Rng020KeiroBlock> ret = new HashMap<Integer, Rng020KeiroBlock>();

        for (Entry<Integer, Rng020KeiroBlock> entryBlock
                : kakuninKeiro.entrySet()) {
            Integer key = entryBlock.getKey();
            Rng020KeiroBlock block = entryBlock.getValue();
            if (block.getHidden() == 1) {
                continue;
            }
/*
            if (block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL) {
                // 任意設定は単一に経路に書き換える
                Rng020Keiro baseKeiro = null;

                // 新しい経路ブロックを作成
                Rng020KeiroBlock newBlock = new Rng020KeiroBlock();
                newBlock.setKeiroKbn(block.getKeiroKbn());
                newBlock.setRtkSid(block.getRtkSid());
                newBlock.setHidden(block.getHidden());

                HashSet<String> singiSidSet = new HashSet<String>();
                for (Entry<Integer, Rng020Keiro> entry : block.getKeiroMap().entrySet()) {
                    Rng020Keiro keiro = entry.getValue();
                    for (Rng020KeiroKakuninDsp kdsp : keiro.getDspSingiList()) {
                        List<UsrLabelValueBean> singiList = kdsp.getSingi();
                        if (singiList == null) {
                            continue;
                        }
                        for (UsrLabelValueBean user : singiList) {
                            if (!singiSidSet.contains(user.getValue())) {
                                singiSidSet.add(user.getValue());
                            }
                        }
                    }
                    if (baseKeiro == null) {
                        baseKeiro = keiro;
                    }
                }
                if (baseKeiro != null) {
                    // まとめた審議一覧を経路へセット
                    baseKeiro.getUsrgrpSel().setSelected(UserGroupSelectModel.KEY_DEFAULT,
                            singiSidSet.toArray(new String[singiSidSet.size()]));
                    newBlock.getKeiroMap().put(0, baseKeiro); // まとめた任意経路をセットする
                }
                ret.put(key, newBlock);
            } else {
                // 任意経路以外はそのまま保存
                ret.put(key, block);
            }
*/
            ret.put(key, block); // 経路ブロックをそのまま保存
        }

        return ret;

    }

    /**
     * ショートメール通知を実行します
     * @param cntCon MlCountMtController
     * @param paramMdl IRng020PeronalParam
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @throws Exception 例外処理
     * */
    public void executeSendSmail(
            MlCountMtController cntCon,
            IRng020PeronalParam paramMdl,
            String appRootPath,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg) throws Exception {

        int rngSid = paramMdl.getRngSid();

        if (!smailPluginUseFlg) {
            //ショートメールプラグインが無効の場合、ショートメールを送信しない。
            return;
        }
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
        int startSortNo = 1;
        int endSortNo = keiroDao.getSortNo(rngSid);
        if (endSortNo <= 1) {
            startSortNo = -1;
            endSortNo = -1;
        } else {
            endSortNo -= 1;
        }
        int[] sort = new int[2];
        sort[0] = startSortNo;
        sort[1] = endSortNo;
        RngBiz rngBiz = new RngBiz(con__, cntCon);
        rngBiz.getRingiListeners(pluginConfig);
        //リスナーに定義された稟議完了時の処理を行う
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);

        //URLを設定
        String url = rngBiz.createThreadUrl(reqMdl__, rngSid);
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngSid);
        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl__,
                    RngConst.STATUS_SOURCE_KOETU_SKIP_SML, sort);
        }
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathSingi(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/ringi/smail/singi_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathZyusin(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/ringi/smail/zyusin_tsuuchi.txt");
        return ret;
    }
}
