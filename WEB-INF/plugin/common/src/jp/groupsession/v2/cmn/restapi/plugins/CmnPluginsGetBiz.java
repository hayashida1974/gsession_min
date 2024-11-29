package jp.groupsession.v2.cmn.restapi.plugins;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.TopMenuBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.TopMenuInfo;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginParamDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginParamModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.restapi.plugins.PluginInfoModel.EnumDispKbn;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.struts.msg.GsMessage;

public class CmnPluginsGetBiz {
    /**コンテキスト*/
    RestApiContext ctx__;
    /**
     *
     * コンストラクタ
     * @param ctx コンテキスト
     */
    public CmnPluginsGetBiz(RestApiContext ctx) {
        super();
        ctx__ = ctx;
    }
    /**
     *
     * <br>[機  能] プラグイン情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return プラグイン情報一覧
     * @throws SQLException
     */
    public List<PluginInfoModel> get(CmnPluginsParamModel paramMdl) throws SQLException {
        List<PluginInfoModel> ret = new ArrayList<>();
        Connection con = ctx__.getCon();
        PluginConfig pconfig = ctx__.getPluginConfig();
        RequestModel reqMdl = ctx__.getRequestModel();
        String appRootPath = ctx__.getAppRootPath();
        GsMessage gsMsg = new GsMessage(reqMdl);
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();

        CmnUsrmInfModel usrInfMdl =
                new CmnUsrmInfDao(con)
                    .select(ctx__.getRequestUserSid());


        CommonBiz cmnBiz = new CommonBiz();
        //トップメニューの個人情報を取得
        TopMenuBiz topMenuBiz = new TopMenuBiz();
        Map<String, TopMenuInfo> topMenuMap
            = topMenuBiz.setTopMenu(reqMdl, con, pconfig);

        List<CmnTdispModel> dispList = null;
        dispList = __getDispMenuInfo();

        //プラグインリストに表示順を反映
        //対象全プラグインID
        List<String> allPluginId = pconfig.getPluginDataList().stream()
        .filter(plugin -> (
                Objects.equals(plugin.getId(), GSConstMain.PLUGIN_ID_MEMO)
                || Optional.ofNullable(plugin)
                    .map(p -> p.getTopMenuInfo())
                    .map(i -> (
                            NullDefault.getString(
                                    i.getView(),
                                    "false").equals("true")
                            && i.getUrl() != null
                            ))
                    .orElse(false)
                ))
        .map(p -> p.getId())
        .filter(plugin -> Optional.ofNullable(paramMdl) //パラメータ指定時は指定プラグインのみ
                                .map(p -> p.getPluginId())
                                .map(p -> Objects.equals(plugin, p))
                                .orElse(true))
        .collect(Collectors.toList());
        //表示プラグインID
        List<String> dispPluginId =
                dispList.stream()
                .map(d -> d.getTdpPid())
                .filter(d -> allPluginId.contains(d))
                .collect(Collectors.toList());




        List<Plugin> plugins =
                Stream.concat(
                        dispPluginId.stream(),
                        allPluginId.stream()
                            .filter(p -> !dispPluginId.contains(p)))
                .map(id -> pconfig.getPlugin(id))
                .filter(p -> (p != null))
                .collect(Collectors.toList());
        //パラメータモデルMap
        Map<String, List<CmnPluginParamModel>> cppMap = new HashMap<>();
        CmnPluginParamDao cppDao = new CmnPluginParamDao(con);
        cppDao.select().stream()
        .forEach(param -> {
            List<CmnPluginParamModel> list = cppMap.get(param.getCupPid());
            if (list == null) {
                list = new ArrayList<>();
                cppMap.put(param.getCupPid(), list);
            }
            list.add(param);
        });


        for (Plugin plugin : plugins) {
            PluginInfoModel mdl = new PluginInfoModel();
            //プラグインID
            mdl.setPlginId(plugin.getId());
            //プラグイン名
            mdl.setPlginName(plugin.getName(reqMdl));
            //プラグイン表示フラグ
            if (dispPluginId.contains(plugin.getId())) {
                mdl.setPlginDispFlg(EnumDispKbn.DISP);
            } else {
                mdl.setPlginDispFlg(EnumDispKbn.UNDISP);
            }
            try {
                // プラグインURL
                String url = "";
                if (Objects.equals(plugin.getId(), GSConstMain.PLUGIN_ID_MEMO)) {
                    url = "../memo/mem010.do";
                } else if (topMenuMap.containsKey(plugin.getId())) {
                    url = topMenuMap.get(plugin.getId()).getUrl();
                } else {
                    url = plugin.getTopMenuInfo().getUrl();
                }
                if (url.startsWith("../")) {
                    url = url.substring("../".length());
                    url = String.format("%s%s",
                            urlBiz.getBaseUrl(reqMdl),
                            url);
                }
                try {
                    mdl.setUrlText(cmnBiz.replaceGSReservedWord(
                            reqMdl, con, appRootPath, url, usrInfMdl, true));
                } catch (EncryptionException | NoSuchAlgorithmException
                        | UnsupportedEncodingException | SQLException e) {
                    throw new RuntimeException("URLパラメータモデル設定実行時例外", e);
                }

                //プラグイン区分
                int pluginKbn = plugin.getPluginKbn();
                //画像SID
                long binSid = 0;
                if (pluginKbn != 0 && plugin.getTopMenuInfo().getBinSid() != 0) {
                    binSid = plugin.getTopMenuInfo().getBinSid();
                }
                //アイコンURL
                String imgUrl = "";
                if (pluginKbn != 0) {
                    if (binSid != 0) {
                        imgUrl = String.format("%s%s%s&cmn003BinSid=%d",
                                urlBiz.getBaseUrl(reqMdl),
                                "/common/cmn003.do?CMD=getImageFile&cmn003PluginId=",
                                plugin.getId(),
                                binSid);
                    } else {
                        imgUrl = String.format("%s%s",
                                urlBiz.getBaseUrl(reqMdl),
                                "/common/images/pluginImg/original/"
                                + "menu_icon_plugin_default_50.png");
                    }
                } else  {
                    imgUrl = plugin.getTopMenuInfo().getIcon();
                    if (StringUtil.isNullZeroString(imgUrl)) {
                        imgUrl =
                            "../common/images/pluginImg/original/menu_icon_"
                            + plugin.getId()
                            + "_50.png";
                    }
                    if (imgUrl.startsWith("../")) {
                        imgUrl = imgUrl.substring("../".length());
                    }
                    imgUrl = String.format("%s%s",
                            urlBiz.getBaseUrl(reqMdl),
                            imgUrl);

                }
                mdl.setIconUrlText(imgUrl);
            } catch (URISyntaxException e) {
                throw new RuntimeException("URL設定実行時 例外", e);
            }

            //実行時HTTPメソッド
            if (plugin.getTopMenuInfo().getSendKbn() == GSConstMain.SEND_KBN_POST) {
                mdl.setMethodText("POST");
            } else {
                mdl.setMethodText("GET");
            }
            //パラメータ設定区分
            if (plugin.getTopMenuInfo().getParamKbn() == GSConstMain.PARAM_KBN_YES) {
                List<ParameterInfoModel> paramList = new ArrayList<>();
                mdl.setParameterArray(paramList);
                List<CmnPluginParamModel> cppList = Optional.ofNullable(cppMap.get(plugin.getId()))
                                                        .orElse(List.of());
                for (CmnPluginParamModel cppMdl : cppList) {


                    String paramName;
                    String paramValue;
                    try {
                        paramName = cmnBiz.replaceGSReservedWord(
                              reqMdl, con, appRootPath, cppMdl.getCppName(), usrInfMdl, true);
                        paramValue = cmnBiz.replaceGSReservedWord(
                              reqMdl, con, appRootPath, cppMdl.getCppValue(), usrInfMdl, true);
                    } catch (EncryptionException | NoSuchAlgorithmException
                            | UnsupportedEncodingException | SQLException e) {
                        throw new RuntimeException("パラメータモデル設定実行時例外", e);
                    }

                    ParameterInfoModel param = new ParameterInfoModel();
                    param.setName(paramName);
                    param.setValue(paramValue);
                    paramList.add(param);
                }
            }
            ret.add(mdl);
        }
        //権限のないプラグインを指定した場合
        if (paramMdl != null
                && !StringUtil.isNullZeroString(paramMdl.getPluginId())
                && ret.size() == 0) {
            throw new RestApiPermissionException(
                    ReasonCode.EnumError.IMPERMISSIBLE,
                    "search.data.notfound", gsMsg.getMessage("cmn.plugin"));
        }

        return ret;

    }


    /**
     *
     * <br>[機  能] CmnTdispModelリストの取得
     * <br>[解  説] メニュー表示情報の反映に使用する
     * <br>[備  考]
     * @return CmnTdispModelリスト
     * @throws SQLException
     */
    private List<CmnTdispModel> __getDispMenuInfo() throws SQLException {
        List<CmnTdispModel> dispList = new ArrayList<CmnTdispModel>();
        Connection con = ctx__.getCon();

        CmnContmDao cntDao = new CmnContmDao(con);
        int menuStatic = cntDao.getMenuStatic();
        CmnTdispDao tdispDao = new CmnTdispDao(con);

        if (menuStatic == GSConstMain.MENU_STATIC_USE) {
            //メニュー項目固定の場合、管理者設定を優先
            dispList = tdispDao.getAdminTdispList();
        } else {
            dispList = tdispDao.select(ctx__.getRequestUserSid());
            if (dispList == null || dispList.isEmpty()) {
                //個人設定されていない場合、管理者設定を優先
                dispList = tdispDao.getAdminTdispList();
            } else {
                //個人設定されている場合、メモプラグインの管理者設定を追加
                CmnTdispModel cmnTdispMdl = new CmnTdispModel();
                cmnTdispMdl.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
                cmnTdispMdl.setTdpPid(GSConst.PLUGIN_ID_MEMO);
                CmnTdispModel memoTdispMdl = tdispDao.select(cmnTdispMdl);
                if (memoTdispMdl.getTdpOrder() > 0) {
                    dispList.add(memoTdispMdl);
                }
            }
        }
        return dispList;
    }
}