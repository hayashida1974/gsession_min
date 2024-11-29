package jp.groupsession.v2.man;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.batch.IBatchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] Servlet init() 又はdestroy()実行時に実行されるリスナーを実装
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManGsListenerImpl implements IGsListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ManGsListenerImpl.class);

    /** Servlet destroy()時に実行される
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @see jp.groupsession.v2.cmn.IGsListener
     * #gsDestroy(jp.groupsession.v2.cmn.GSContext, java.sql.Connection)
     */
    public void gsDestroy(GSContext gscontext, Connection con, String domain) throws SQLException {
    }



    /** Servlet init()時に実行される
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws Exception 実行時例外
     * @see jp.groupsession.v2.cmn.IGsListener
     * #gsInit(jp.groupsession.v2.cmn.GSContext, java.sql.Connection)
     */
    public void gsInit(GSContext gscontext, Connection con, String domain)
                                              throws Exception {

        try {
            con.setAutoCommit(false);
            //プラグイン設定の初期処理を行う
            __pluginConfInit(gscontext, con, domain);

            con.commit();

        } catch (SQLException e) {
            log__.error("プラグイン管理者設定に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        }

        try {
            con.setAutoCommit(false);
            //システムメールユーザの初期処理を行う
            __systemUserInit(gscontext, con, domain);
            //データフォルダ容量の登録，更新を行う
            __updateFolderSize(gscontext, con, domain);
            con.commit();

        } catch (SQLException e) {
            log__.error("システムメールユーザ設定に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        }
    }

    /**
     * プラグイン管理者設定が未登録の場合、プラグイン情報から初期値を登録します。
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     */
    private void __pluginConfInit(GSContext gscontext, Connection con, String domain)
    throws SQLException {
        log__.debug("プラグイン管理者設定初期処理：開始");
        //トップ表示設定を取得し未登録の場合、登録する
        CmnTdispDao dispDao = new CmnTdispDao(con);
        //管理者ユーザの登録値を取得
        List<CmnTdispModel> dispList = dispDao.select(GSConst.SYSTEM_USER_ADMIN);
        PluginConfig pconfig = GroupSession.getResourceManager().getPluginConfig(domain);
        if (pconfig == null) {
            pconfig = GroupSession.getPconfig().clonePluginConfig(null);
        }
        int order = 1;

        CmnTdispModel model = new CmnTdispModel();
        model.setTdpAuid(GSConst.SYSTEM_USER_ADMIN);
        model.setTdpAdate(new UDate());
        model.setTdpEuid(GSConst.SYSTEM_USER_ADMIN);
        model.setTdpEdate(model.getTdpAdate());

        List<Plugin> pluginDataList = new ArrayList<Plugin>();
        if (dispList == null || dispList.isEmpty()) {
            //管理者設定が無い場合は全てのプラグインを対象とする
            pluginDataList = pconfig.getPluginDataList();
        } else {
            //新規登録されたプラグインの管理者設定を作成する
            for (Plugin pluginData : pconfig.getPluginDataList()) {
                boolean exist = false;
                for (CmnTdispModel dispModel : dispList) {
                    if (dispModel.getTdpPid().equals(pluginData.getId())) {
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    pluginDataList.add(pluginData);

                    if (pconfig.getPlugin(pluginData.getId()).isMenuPlugin()) {
                        //メニューに表示するプラグインの場合、個人設定の登録を行う
                        model.setTdpPid(pluginData.getId());
                        dispDao.insertNewPluginConfig(model);
                    }
                }
            }

            order = dispList.get(dispList.size() - 1).getTdpOrder() + 1;
        }

        //共通、ヘルプは対象外とする
        pluginDataList.remove("common");
        pluginDataList.remove("help");

        //プラグイン情報を一時格納するmap
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<Integer> list = new ArrayList<Integer>();
        int count = 999;

        model.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
        for (Plugin pluginData : pluginDataList) {
            int menuOrder = 0;
            try {
                menuOrder = NullDefault.getInt(pluginData.getTopMenuInfo().getOrder(), 0);
            } catch (Exception e) {
            }

            if (menuOrder > 0) {
                //プラグインに表示順の指定がある場合
                map.put(menuOrder, pluginData.getId());
                list.add(menuOrder);
            } else {
                //プラグインに表示順の指定がない場合
                map.put(count, pluginData.getId());
                list.add(count);
                count++;
            }
        }

        Collections.sort(list);
        for (int i : list) {
            model.setTdpPid(map.get(i));
            model.setTdpOrder(order++);
            dispDao.insert(model);
        }

        log__.debug("プラグイン管理者設定初期処理：終了");
    }

    /**
     * システムメールユーザの画像が登録されていなければ、登録します。
     * @param gscontext 基本情報
     * @param con コネクション
     * @param dsKey ドメイン
     * @throws Exception 実行時例外
     */
    private void __systemUserInit(GSContext gscontext, Connection con, String dsKey)
    throws Exception {
        log__.debug("システムメールユーザ初期処理：開始");
        CmnUsrmInfDao uinfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uinfModel = uinfDao.selectInit(GSConstUser.SID_SYSTEM_MAIL);

        if (uinfModel != null && uinfModel.getBinSid() < 1) {

            //採番クラス取得
            MlCountMtController mtconer
             = GroupSession.getResourceManager().getCountController(dsKey);

            String appRootPath = (String) gscontext.get(GSContext.APP_ROOT_PATH);
            String fileName = "icon_photo_system_mail.png";
            String filePath = appRootPath + "common/images/original/";

            GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
            String tempPath = tempPathUtil.getBatchTempPath(dsKey, "gsInit", "main", "systemmailIco");
            IOTools.isDirCheck(tempPath, true);

            IOTools.copyBinFile(filePath + fileName, tempPath + fileName);

            UDate now = new UDate();

            //画像登録
            CommonBiz cmnBiz = new CommonBiz();
            Long binSid = 0L;
            try {
                binSid = cmnBiz.insertBinInfo(
                        con, appRootPath, mtconer, GSConstUser.SID_ADMIN, now, tempPath + fileName, fileName);
            } catch (TempFileException e) {
            }

            //システムメール ユーザ情報更新
            CmnUsrmInfModel upModel = new CmnUsrmInfModel();
            upModel.setUsrSid(GSConstUser.SID_SYSTEM_MAIL);
            upModel.setUsiEdate(now);
            upModel.setUsiEuid(GSConstUser.SID_ADMIN);
            upModel.setBinSid(binSid);
            uinfDao.updateCmnUserPct(upModel);

            tempPathUtil.deleteBatchTempPath(dsKey, "gsInit", "main", "systemmailIco");

        }

        log__.debug("システムメールユーザ初期処理：終了");
    }
    
    /**
     * <br>[機  能] データフォルダ容量の登録，更新を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @return ディスク情報
     * @throws SQLException SQL実行例外
     */
    private void __updateFolderSize(
            GSContext gscontext, Connection con, String domain) throws SQLException {
        
        CommonBiz cmnBiz = new CommonBiz();
        IBatchModel ibm = new IBatchModel();
        ibm.setGsContext(gscontext);
        ibm.setDomain(domain);
        GSContext gsContext = ibm.getGsContext();
        String appRootPath = (String) gsContext.get(GSContext.APP_ROOT_PATH);
        cmnBiz.updateFolderSize(con, appRootPath);
    }
}