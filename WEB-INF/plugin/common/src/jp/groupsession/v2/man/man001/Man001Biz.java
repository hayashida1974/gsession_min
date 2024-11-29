package jp.groupsession.v2.man.man001;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GsResourceBundle;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.MainDspBiz;
import jp.groupsession.v2.cmn.config.MainScreenInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutUserDao;
import jp.groupsession.v2.cmn.dao.base.CmnMdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenConfModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutAdminModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutUserModel;
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.man.MainScreenOfPluginModel;
import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.dao.base.PtlMainConfDao;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlUconfDao;
import jp.groupsession.v2.man.man320.Man320Biz;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;
import jp.groupsession.v2.man.model.base.PtlMainConfModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.man.model.base.PtlUconfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man001Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man001Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man001Biz() { }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man001Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param userSid ユーザSID
     * @param context コンテキスト
     * @throws Exception SQL実行時例外
     */
    public void getInitData(Man001ParamModel paramMdl,
            Connection con,
            PluginConfig pconfig, int userSid, ServletContext context) throws Exception {
        log__.debug("-- getInitData START --");

        BaseUserModel usModel = reqMdl__.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();

        //インフォーメーション情報を取得
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<MainInfoMessageModel> infoList = cmnBiz.getInfoMessageList(
                gsMsg, reqMdl__, con, usModel, pconfig, context);
        paramMdl.setInfoMsgs(infoList);
        Man320Biz biz = new Man320Biz();
        if (biz.isInfoAdminAuth(reqMdl__, con)) {
            paramMdl.setInfoConf(true);
        }

        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_PORTAL, pconfig)) {
            //ポータル一覧を取得
            List<PtlPortalModel> ptlList = new ArrayList<PtlPortalModel>();
            ptlList = __getPortal(con, paramMdl, userSid);
            //ポータルが1つしかない場合(メインのみ)リストを空に
            if (ptlList.size() == 1) {
                ptlList.clear();
            } else {
                __updateDspPortal(con, userSid);
            }
            paramMdl.setPortalList(ptlList);
        }

        //管理者権限区分
        if (usModel.getAdminFlg()) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        //最終ログイン時間
        paramMdl.setMan001lstLogintime(usModel.getLstLogintime());

        //メイン画面パラメータを取得する
        __setMainScreenInfo(paramMdl, con, pconfig, reqMdl__);

        //ポータル管理フラグを設定する。
        paramMdl.setMan001ptlAdminFlg(cmnBiz.isPortalAdmin(con, usModel, pconfig));

        log__.debug("-- getInitData END --");
    }

    /**
     * <br>[機  能] メイン画面位置設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param leftPositionId メイン画面左の画面ID
     * @param rightPositionId メイン画面右の画面ID
     * @param topPositionId メイン画面上の画面ID
     * @param bottomPositionId メイン画面下の画面ID
     * @param centerPositionId メイン画面中の画面ID
     * @throws Exception SQL実行時例外
     */
    public void saveMainScreenPosition(Connection con, int userSid,
                                        String[] leftPositionId,
                                        String[] rightPositionId,
                                        String[] topPositionId,
                                        String[] bottomPositionId,
                                        String[] centerPositionId) throws Exception {
        log__.debug("-- saveMainScreenPosition START --");

        CmnMainscreenConfDao mainScreenDao = new CmnMainscreenConfDao(con);
//        mainScreenDao.delete(userSid);

        CmnMainscreenConfModel model = new CmnMainscreenConfModel();
        model.setUsrSid(userSid);
        model.setMscAuid(userSid);
        model.setMscAdate(new UDate());
        model.setMscEuid(userSid);
        model.setMscEdate(model.getMscAdate());

        __insertMainScreenInfo(mainScreenDao, model,
                            leftPositionId, MainScreenOfPluginModel.POSITION_LEFT);
        __insertMainScreenInfo(mainScreenDao, model,
                            rightPositionId, MainScreenOfPluginModel.POSITION_RIGHT);
        __insertMainScreenInfo(mainScreenDao, model,
                            topPositionId, MainScreenOfPluginModel.POSITION_TOP);
        __insertMainScreenInfo(mainScreenDao, model,
                            bottomPositionId, MainScreenOfPluginModel.POSITION_BOTTOM);
        __insertMainScreenInfo(mainScreenDao, model,
                            centerPositionId, MainScreenOfPluginModel.POSITION_CENTER);

        log__.debug("-- saveMainScreenPosition END --");
    }

    /**
     * <br>[機  能] メイン画面位置設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mainScreenDao メイン画面位置設定DAO
     * @param model メイン画面位置設定情報
     * @param idList 画面ID一覧
     * @param position 表示位置(左 or 右)
     * @throws SQLException SQL実行時例外
     */
    private void __insertMainScreenInfo(CmnMainscreenConfDao mainScreenDao,
                                        CmnMainscreenConfModel model,
                                        String[] idList, int position) throws SQLException {

        if (idList == null) {
            return;
        }

        int order = 1;
        for (String id : idList) {

            if (!StringUtil.isNullZeroString(id)) {
                model.setMscId(id);
                model.setMscPosition(position);
                model.setMscOrder(order++);

                if (mainScreenDao.selectCnt(model)) {
                    mainScreenDao.update(model);
                } else {
                    mainScreenDao.insert(model);
                }
            }
        }
    }

    /**
     * <br>[機  能] メイン画面に表示する各プラグインの画面情報をセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL例外発生
     */
    private void __setMainScreenInfo(Man001ParamModel paramMdl,
                                    Connection con,
                                    PluginConfig pconfig,
                                    RequestModel reqMdl)
    throws SQLException {
        int userSid = reqMdl.getSmodel().getUsrsid();
        List<String> pluginIdList = new ArrayList<String>();

        //コントロールマスタ
        CmnContmDao cntDao = new CmnContmDao(con);
        int menuStatic = cntDao.getMenuStatic();

        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<String> menuPluginIdList = null;
        if (menuStatic == GSConstMain.MENU_STATIC_USE) {
            //固定メニュー設定の場合
            menuPluginIdList =
                tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);
        } else {
            //可変メニュー設定の場合
            //個人メニュー設定
            menuPluginIdList = tdispDao.getMenuPluginIdList(userSid);
            if (menuPluginIdList == null || menuPluginIdList.isEmpty()) {
                //個人設定が無い場合、管理者設定を有効
                menuPluginIdList =
                    tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);
            }
        }

        if (menuPluginIdList == null || menuPluginIdList.isEmpty()) {
            //トップ表示設定が登録されていない場合はプラグイン設定を使用する
            pluginIdList.addAll(pconfig.getPluginIdList());
        } else {
            pluginIdList.addAll(menuPluginIdList);
            List<String> confIdList = pconfig.getPluginIdList();

            for (String confId : confIdList) {
                if (pluginIdList.indexOf(confId) < 0
                && !pconfig.getPlugin(confId).isMenuPlugin()
                && pconfig.getPlugin(confId).isMainScreenPlugin()) {
                    pluginIdList.add(confId);
                }
            }
        }

        //レイアウト情報を設定する。
        __setLayoutInfo(paramMdl, con, userSid);

        List<MainScreenOfPluginModel> mainScreenList = new ArrayList<MainScreenOfPluginModel>();

        CmnMainscreenConfDao mainScreenDao = new CmnMainscreenConfDao(con);
        Map<String, CmnMainscreenConfModel> screenMap = mainScreenDao.getMainScreenMap(userSid);

        for (String pluginId : pluginIdList) {

            log__.debug("プラグイン読込み = " + pluginId);
            Plugin plugin = pconfig.getPlugin(pluginId);
            if (plugin == null) {
                continue;
            }

            ArrayList<MainScreenInfo> screenList = plugin.getMainScreenInfo();

            if (screenList != null && !screenList.isEmpty()) {
                for (int i = 0; i < screenList.size(); i++) {
                    MainScreenInfo screenInfo = screenList.get(i);

                    //メイン画面への設定なし or メニューに表示しないプラグインは
                    //メイン画面へ表示しない
                    if (plugin.isMainScreenPlugin()
                    && !StringUtil.isNullZeroString(screenInfo.getId())
                    && screenInfo.getView().equals("true")) {

                        String id = screenInfo.getId();
                        MainScreenOfPluginModel screenMdl = new MainScreenOfPluginModel();
                        screenMdl.setId(id);
                        screenMdl.setPluginId(pluginId);
                        screenMdl.setPluginName(plugin.getName(reqMdl));

                        screenMdl.setScreenUrl("../" + pluginId + "/" + id + ".do");
                        screenMdl.setStylePath("../" + pluginId + "/css/" + pluginId + ".css");
                        screenMdl.setScriptPath("../" + pluginId + "/js/" + id + ".js");

                        int position = 0;
                        int order = 0;
                        CmnMainscreenConfModel mainScreenModel = screenMap.get(id);


                        if (mainScreenModel == null) {
                            position = NullDefault.getInt(screenInfo.getPosition(),
                                                        MainScreenOfPluginModel.POSITION_LEFT);
                            order = NullDefault.getInt(screenInfo.getOrder(), Integer.MAX_VALUE);

                        } else {
                            position = mainScreenModel.getMscPosition();
                            order = mainScreenModel.getMscOrder();
                        }

                        if (position != MainScreenOfPluginModel.POSITION_LEFT
                                && position != MainScreenOfPluginModel.POSITION_RIGHT
                                && position != MainScreenOfPluginModel.POSITION_TOP
                                && position != MainScreenOfPluginModel.POSITION_BOTTOM
                                && position != MainScreenOfPluginModel.POSITION_CENTER) {
                            position = MainScreenOfPluginModel.POSITION_LEFT;
                        }

                        screenMdl.setOrder(order);
                        screenMdl.setPosition(position);
                        screenMdl.setLoadScript(screenInfo.isLoadScript());
                        mainScreenList.add(screenMdl);
                    }
                }
            }
        }

        //時計、最終ログイン時間を一覧に追加
        MainDspBiz dspBiz = new MainDspBiz();
        ArrayList<CmnMdispModel> mainDspConfList = dspBiz.getMainDspConfList(userSid, con);
        __addMainScreenModel(mainScreenList, GSConstMain.MAINSCREENID_INFORMATION,
                            null, screenMap, mainDspConfList,
                            MainScreenOfPluginModel.POSITION_LEFT, -4);
        __addMainScreenModel(mainScreenList, GSConstMain.MAINSCREENID_DAYTIME,
                            GSConstCommon.MAIN_DSPVALUE[0], screenMap, mainDspConfList,
                            MainScreenOfPluginModel.POSITION_RIGHT, -3);
        __addMainScreenModel(mainScreenList, GSConstMain.MAINSCREENID_LASTLOGIN,
                            GSConstCommon.MAIN_DSPVALUE[1], screenMap, mainDspConfList,
                            MainScreenOfPluginModel.POSITION_RIGHT, -2);

        paramMdl.setMan001mainStatus(0);
        if (NullDefault.getString(GsResourceBundle.getString(GSConst.MAIN_STATUS),
                GSConst.MAIN_STATUS_TRUE).equals(GSConst.MAIN_STATUS_TRUE)) {

            //「ニュース」を一覧に追加
            __addMainScreenList(mainScreenList, mainDspConfList,
                                GSConstCommon.MAIN_DSPVALUE_NEWS,
                                GSConstMain.MAINSCREENID_NEWS,
                                screenMap,
                                "cmn.news", "../common/cmn240.do");

            //天気予報を一覧に追加
            __addMainScreenList(mainScreenList, mainDspConfList,
                                GSConstCommon.MAIN_DSPVALUE_WEATHER,
                                GSConstMain.MAINSCREENID_WEATHER,
                                screenMap,
                                "main.src.man001.1", "../common/cmn180.do");

            paramMdl.setMan001mainStatus(1);
        }

        //表示順を元に並び替えを行う
        Collections.sort(mainScreenList);
        __setScrnInfList(paramMdl, mainScreenList);

    }

    /**
     * <br>[機  能] メイン画面に表示するプラグインを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param mainScreenList 表示する全プラグインリスト
     */
    private void __setScrnInfList(Man001ParamModel paramMdl,
                                List<MainScreenOfPluginModel> mainScreenList) {

        List<MainScreenOfPluginModel> leftScreenList = new ArrayList<MainScreenOfPluginModel>();
        List<MainScreenOfPluginModel> rightScreenList = new ArrayList<MainScreenOfPluginModel>();
        List<MainScreenOfPluginModel> topScreenList = new ArrayList<MainScreenOfPluginModel>();
        List<MainScreenOfPluginModel> bottomScreenList = new ArrayList<MainScreenOfPluginModel>();
        List<MainScreenOfPluginModel> centerScreenList = new ArrayList<MainScreenOfPluginModel>();

        //ポジション非表示の場合のポジション
        int position = MainScreenOfPluginModel.POSITION_LEFT;
        if (paramMdl.getMan001areaLeft() == GSConstMain.LAYOUT_VIEW_ON) {
            position = MainScreenOfPluginModel.POSITION_LEFT;

        } else if (paramMdl.getMan001areaRight() == GSConstMain.LAYOUT_VIEW_ON) {
            position = MainScreenOfPluginModel.POSITION_RIGHT;

        } else if (paramMdl.getMan001areaTop() == GSConstMain.LAYOUT_VIEW_ON) {
            position = MainScreenOfPluginModel.POSITION_TOP;

        } else if (paramMdl.getMan001areaBottom() == GSConstMain.LAYOUT_VIEW_ON) {
            position = MainScreenOfPluginModel.POSITION_BOTTOM;

        } else if (paramMdl.getMan001areaCenter() == GSConstMain.LAYOUT_VIEW_ON) {
            position = MainScreenOfPluginModel.POSITION_CENTER;

        }

        //各ポジションのリストに追加する。
        boolean defPositionFlg = false;
        for (MainScreenOfPluginModel model : mainScreenList) {
            defPositionFlg = false;

            if (model.getPosition() == MainScreenOfPluginModel.POSITION_LEFT) {
                if (paramMdl.getMan001areaLeft() == GSConstMain.LAYOUT_VIEW_ON) {
                    leftScreenList.add(model);
                } else {
                    defPositionFlg = true;
                }

            } else if (model.getPosition() == MainScreenOfPluginModel.POSITION_RIGHT) {
                if (paramMdl.getMan001areaRight() == GSConstMain.LAYOUT_VIEW_ON) {
                    rightScreenList.add(model);
                } else {
                    defPositionFlg = true;
                }

            } else if (model.getPosition() == MainScreenOfPluginModel.POSITION_TOP) {
                if (paramMdl.getMan001areaTop() == GSConstMain.LAYOUT_VIEW_ON) {
                    topScreenList.add(model);
                } else {
                    defPositionFlg = true;
                }

            } else if (model.getPosition() == MainScreenOfPluginModel.POSITION_BOTTOM) {
                if (paramMdl.getMan001areaBottom() == GSConstMain.LAYOUT_VIEW_ON) {
                    bottomScreenList.add(model);
                } else {
                    defPositionFlg = true;
                }

            } else if (model.getPosition() == MainScreenOfPluginModel.POSITION_CENTER) {
                if (paramMdl.getMan001areaCenter() == GSConstMain.LAYOUT_VIEW_ON) {
                    centerScreenList.add(model);
                } else {
                    defPositionFlg = true;
                }
            }

            //登録されているポジションが非表示の場合
            if (defPositionFlg) {
                if (position == MainScreenOfPluginModel.POSITION_LEFT) {
                    leftScreenList.add(model);

                } else if (position == MainScreenOfPluginModel.POSITION_RIGHT) {
                    rightScreenList.add(model);

                } else if (position == MainScreenOfPluginModel.POSITION_TOP) {
                    topScreenList.add(model);

                } else if (position == MainScreenOfPluginModel.POSITION_BOTTOM) {
                    bottomScreenList.add(model);

                } else if (position == MainScreenOfPluginModel.POSITION_CENTER) {
                    centerScreenList.add(model);

                }
            }
        }

        paramMdl.setScreenInfoListLeft(leftScreenList);
        paramMdl.setScreenInfoListRight(rightScreenList);
        paramMdl.setScreenInfoListTop(topScreenList);
        paramMdl.setScreenInfoListBottom(bottomScreenList);
        paramMdl.setScreenInfoListCenter(centerScreenList);
        paramMdl.setScreenInfoList(mainScreenList);
    }

    /**
     * <br>[機  能] 自動リロード時間を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL例外発生
     */
    public void setReloadTime(Man001ParamModel paramMdl,
                                    Connection con,
                                    int userSid)
    throws SQLException {

        CmnMdispModel model = new CmnMdispModel();
        CmnMdispDao dao = new CmnMdispDao(con);
        UDate now = new UDate();

        model.setUsrSid(userSid);
        model.setMdpPid(GSConstCommon.MAIN_DSPVALUE[2]);

        CmnMdispModel mdispModel = dao.select(model);

        if (mdispModel != null) {
            paramMdl.setMan001Reload(mdispModel.getMdpReload());
        } else {
            model.setMdpDsp(GSConstCommon.MAIN_RELOAD);
            model.setMdpReload(paramMdl.getMan001Reload());
            model.setMdpAuid(userSid);
            model.setMdpAdate(now);
            model.setMdpEuid(userSid);
            model.setMdpEdate(now);
            //リロード時間の設定がされていない場合は登録を行う。
            dao.insert(model);
        }
    }

    /**
     * <br>[機  能] メインに表示する画面情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mainScreenList メイン画面表示情報一覧
     * @param id 画面ID
     * @param mdpPid 画面表示項目区分
     * @param screenMap メイン画面位置設定情報Mapping
     * @param mainDspConfList メイン画面表示設定一覧
     * @param defaultPosition デフォルト画面表示位置(左:1 or 右:2)
     * @param defaultOrder デフォルト画面表示順
     */
    private void __addMainScreenModel(List<MainScreenOfPluginModel> mainScreenList,
                                    String id,
                                    String mdpPid,
                                    Map<String, CmnMainscreenConfModel> screenMap,
                                    ArrayList<CmnMdispModel> mainDspConfList,
                                    int defaultPosition,
                                    int defaultOrder) {

        //メイン画面表示設定で非表示に設定されている場合は表示を行わない
        if (mdpPid != null && mainDspConfList != null) {
            for (CmnMdispModel mdispModel : mainDspConfList) {
                if (mdispModel.getMdpPid().equals(mdpPid)
                && mdispModel.getMdpDsp() != GSConstCommon.MAIN_DSP) {
                    return;
                }
            }
        }

        MainScreenOfPluginModel model = new MainScreenOfPluginModel();
        model.setPluginId(id);
        model.setId(id);

        CmnMainscreenConfModel screenConf = screenMap.get(id);
        if (screenConf == null) {
            model.setPosition(defaultPosition);
            model.setOrder(defaultOrder);
        } else {
            model.setPosition(screenConf.getMscPosition());
            model.setOrder(screenConf.getMscOrder());
        }

        mainScreenList.add(model);
    }

    /**
     * <br>[機  能] ポータル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @return list
     * @throws SQLException SQL実行例外
     */
    private List<PtlPortalModel> __getPortal(Connection con, Man001ParamModel paramMdl,
                                                  int usrSid) throws SQLException {

        PtlPortalDao portalDao = new PtlPortalDao(con);
        //ポータル並び順の個人設定が制限されていないか判定
        PtlAdmConfDao adminDao = new PtlAdmConfDao(con);
        PtlAdmConfModel adminMdl = adminDao.select();
        if (adminMdl == null) {
            adminMdl = new PtlAdmConfModel();
        }
        int editKbn = adminMdl.getPacPtlEditkbn();

        //閲覧許可されているポータルを取得する
        List<PtlPortalModel> list = portalDao.getReadPortalList(editKbn, usrSid);
        if (list == null) {
            list = new ArrayList<PtlPortalModel>();
        }

        return list;
    }

    /**
     * <br>[機  能] ポータル遷移フラグを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param usrSid ユーザSID
     * @param paramMdl パラメータ情報
     * @return boolean boolean
     * @throws SQLException SQL実行時例外
     */
    public boolean getPortalConfValue(Connection con,
        PluginConfig pconfig, int usrSid, Man001ParamModel paramMdl) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        boolean usePortal = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_PORTAL, pconfig);

        //ポータル編集区分
        int editKbn = __getAdmConfEditKbn(con);

        //表示できるポータルSID格納リスト
        ArrayList<Integer> sidList = new ArrayList<Integer>();

        //利用可能なポータル一覧を取得する。
        PtlPortalDao ptlDao = new PtlPortalDao(con);
        List<PtlPortalModel> ptlList = ptlDao.getReadPortalList(editKbn, usrSid);
        if (ptlList == null || ptlList.size() <= 1) {
            usePortal = false;
        } else {
            for (PtlPortalModel model : ptlList) {
                sidList.add(model.getPtlSid());
            }
        }

        boolean seniFlg = false;
        int portalSid = -1;

        if (usePortal) {
            //初期表示設定を取得
            PtlAdmConfDao aConfDao = new PtlAdmConfDao(con);
            PtlAdmConfModel model = aConfDao.select();
            if (model == null) {
                model = new PtlAdmConfModel();
            }
            PtlUconfDao uconfDao = new PtlUconfDao(con);
            PtlUconfModel uconfModel = uconfDao.select(usrSid);
            if (uconfModel == null) {
                uconfModel = new PtlUconfModel();
            }

            //タブの最初か、最後に表示したポータルか判定
            if ((model.getPacDefKbn() == GSConstPortal.EDIT_KBN_ADM
                    && model.getPacDefType() == 0)
                || (model.getPacDefKbn() == GSConstPortal.EDIT_KBN_PUBLIC
                    && uconfModel.getPucDefType() == 0)) {

                //最初のタブがメインかどうか
                List<PtlPortalModel> list = __getPortal(con, paramMdl, usrSid);
                portalSid = list.get(0).getPtlSid();
                if (portalSid <= 0) {
                    seniFlg = false;
                } else {
                    seniFlg = true;
                }
            } else {
                //最後に表示したポータル
                //DBから値取得
                PtlMainConfDao mainConfDao = new PtlMainConfDao(con);
                PtlMainConfModel mainConfModel = mainConfDao.select(usrSid);
                if (mainConfModel == null) {
                    mainConfModel = new PtlMainConfModel();
                }
                portalSid = mainConfModel.getPtlSid();
                if (portalSid > 0) {
                    if (sidList.contains(portalSid)) {
                        seniFlg = true;
                    } else {
                        seniFlg = false;
                    }
                } else {
                    seniFlg = false;
                }
            }
        }
        return seniFlg;
    }

    /**
     * <br>[機  能] 最終表示ポータルを更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __updateDspPortal(Connection con, int usrSid) throws SQLException {

        PtlMainConfDao mainConfDao = new PtlMainConfDao(con);
        PtlMainConfModel mainConfModel = new PtlMainConfModel();
        mainConfModel.setUsrSid(usrSid);
        mainConfModel.setPtlSid(0);
        int upCnt = mainConfDao.update(mainConfModel);
        if (upCnt < 1) {
            mainConfDao.insert(mainConfModel);
        }
    }

    /**
     * <br>[機  能] 管理者設定よりポータル編集区分を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return int 区分
     */
    private int __getAdmConfEditKbn(Connection con)
    throws SQLException {

        int editKbn = GSConstPortal.EDIT_KBN_PUBLIC;

        PtlAdmConfDao admConfDao = new PtlAdmConfDao(con);

        //ポータル管理者設定を取得する。
        PtlAdmConfModel admConfModel = admConfDao.select();
        if (admConfModel != null) {
            editKbn = admConfModel.getPacPtlEditkbn();
        }

        return editKbn;
    }

    /**
     * <br>[機  能] メインに表示する画面情報をメイン画面表示情報一覧へ追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param mainScreenList メイン画面表示情報一覧
     * @param mainDspConfList メイン画面表示設定一覧
     * @param mdpPid 画面表示項目区分
     * @param screenId 画面ID
     * @param screenMap メイン画面位置設定情報Mapping
     * @param screenNameId 画面名メッセージキー
     * @param screenUrl 画面Url
     */
    private void __addMainScreenList(List<MainScreenOfPluginModel> mainScreenList,
                                    ArrayList<CmnMdispModel> mainDspConfList,
                                    String mdpPid, String screenId,
                                    Map<String, CmnMainscreenConfModel> screenMap,
                                    String screenNameId,
                                    String screenUrl) {

        //「ニュース」を一覧に追加
        boolean dspFlg = true;
        for (CmnMdispModel mdispData : mainDspConfList) {
            if (mdispData.getMdpPid().equals(mdpPid)) {
                dspFlg = mdispData.getMdpDsp() == GSConstCommon.MAIN_DSP;
            }
        }

        if (dspFlg) {
            MainScreenOfPluginModel screenModel = new MainScreenOfPluginModel();
            screenModel.setPluginId(screenId);
            GsMessage gsMsg = new GsMessage(reqMdl__);
            screenModel.setPluginName(gsMsg.getMessage(screenNameId));
            screenModel.setId(screenId);
            screenModel.setScreenUrl(screenUrl);
            CmnMainscreenConfModel screenConf = screenMap.get(screenId);
            if (screenConf != null) {
                screenModel.setPosition(screenConf.getMscPosition());
                screenModel.setOrder(screenConf.getMscOrder());
            } else {
                screenModel.setPosition(MainScreenOfPluginModel.POSITION_RIGHT);
                screenModel.setOrder(-1);
            }
            mainScreenList.add(screenModel);
        }
    }

    /**
     * <br>[機  能] レイアウト情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL例外発生
     */
    private void __setLayoutInfo(Man001ParamModel paramMdl,
                                    Connection con,
                                    int userSid)
    throws SQLException {

        //管理者設定を取得する。
        CmnMainscreenLayoutAdminDao admDao = new CmnMainscreenLayoutAdminDao(con);
        CmnMainscreenLayoutAdminModel admModel = admDao.select();
        boolean defFlg = false;

        if (admModel != null
            && admModel.getMlcAdmLayoutKbn() == GSConstMain.MANSCREEN_LAYOUTKBN_ADMIN) {

            if (admModel.getMlcDefaultKbn() == GSConstMain.MANSCREEN_LAYOUT_DEFAULT) {
                defFlg = true;

            } else {
                //管理者のレイアウトを取得
                __setLayout(paramMdl, con, 0);

            }

        } else {
            //ユーザが設定する。
            CmnMainscreenLayoutUserDao userDao = new CmnMainscreenLayoutUserDao(con);
            CmnMainscreenLayoutUserModel userModel = userDao.select(userSid);
            if (userModel == null) {
                //個人設定がない場合、デフォルト
                defFlg = true;

            } else if (userModel.getMsuDefaultKbn() == GSConstMain.MANSCREEN_LAYOUT_DEFAULT) {
                //デフォルト
                defFlg = true;

            } else {
                //個人のレイアウト取得
                __setLayout(paramMdl, con, userSid);
            }
        }

        //デフォルト時の表示設定
        if (defFlg) {
            paramMdl.setMan001areaLeft(GSConstMain.LAYOUT_VIEW_ON);
            paramMdl.setMan001areaRight(GSConstMain.LAYOUT_VIEW_ON);
            paramMdl.setMan001areaTop(GSConstMain.LAYOUT_VIEW_OFF);
            paramMdl.setMan001areaBottom(GSConstMain.LAYOUT_VIEW_OFF);
            paramMdl.setMan001areaCenter(GSConstMain.LAYOUT_VIEW_OFF);
        }

        paramMdl.setMan001layoutDefFlg(defFlg);
    }

    /**
     * <br>[機  能] レイアウト情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL例外発生
     */
    private void __setLayout(Man001ParamModel paramMdl,
                                    Connection con,
                                    int userSid)
    throws SQLException {

        //管理者設定を取得する。
        CmnMainscreenLayoutDao layoutDao = new CmnMainscreenLayoutDao(con);
        List<CmnMainscreenLayoutModel> layoutList = layoutDao.select(userSid);

        //レイアウトの表示区分を設定する。
        for (CmnMainscreenLayoutModel layoutModel : layoutList) {

            switch (layoutModel.getMscPosition()) {
                case MainScreenOfPluginModel.POSITION_LEFT :
                    paramMdl.setMan001areaLeft(GSConstMain.LAYOUT_VIEW_ON);
                    break;
                case MainScreenOfPluginModel.POSITION_RIGHT :
                    paramMdl.setMan001areaRight(GSConstMain.LAYOUT_VIEW_ON);
                    break;
                case MainScreenOfPluginModel.POSITION_TOP :
                    paramMdl.setMan001areaTop(GSConstMain.LAYOUT_VIEW_ON);
                    break;
                case MainScreenOfPluginModel.POSITION_BOTTOM :
                    paramMdl.setMan001areaBottom(GSConstMain.LAYOUT_VIEW_ON);
                    break;
                case MainScreenOfPluginModel.POSITION_CENTER :
                    paramMdl.setMan001areaCenter(GSConstMain.LAYOUT_VIEW_ON);
                    break;
                default:
                    break;
            }
        }
    }
}