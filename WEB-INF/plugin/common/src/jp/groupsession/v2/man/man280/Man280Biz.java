package jp.groupsession.v2.man.man280;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnPluginAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlMemberDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPluginDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginAdminModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlMemberModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPluginModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン使用制限画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man280Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man280Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     */
    public void setInitData(Man280ParamModel paramMdl, Connection con,
                            PluginConfig pconfig, RequestModel reqMdl)
    throws SQLException {
        log__.debug("START");

        String pluginId = paramMdl.getMan120pluginId();

        //初期表示の場合は登録されているプラグイン使用制限情報を設定する
        if (paramMdl.getMan280initFlg() == 0) {
            paramMdl.setMan280initFlg(1);

            CmnPluginControlDao pcontrolDao = new CmnPluginControlDao(con);
            CmnPluginControlModel pcontrolModel = pcontrolDao.select(pluginId);
            if (pcontrolModel != null) {
                paramMdl.setMan280useKbn(pcontrolModel.getPctKbn());
                paramMdl.setMan280limitType(pcontrolModel.getPctType());

                if (pcontrolModel.getPctKbn() == GSConstMain.PCT_KBN_MEMBER) {
                    CmnPluginControlMemberDao pcontrolMemDao = new CmnPluginControlMemberDao(con);
                    List<CmnPluginControlMemberModel> memList = pcontrolMemDao.select(pluginId);

                    List<String> memSidList = new ArrayList<String>();
                    for (CmnPluginControlMemberModel memMdl : memList) {
                        if (memMdl.getGrpSid() == GSConstMain.PCM_MEMSID_NOSET) {
                            memSidList.add(String.valueOf(memMdl.getUsrSid()));
                        } else {
                            memSidList.add("G" + String.valueOf(memMdl.getGrpSid()));
                        }
                    }
                    paramMdl.setMan280memberSid(
                            (String[]) memSidList.toArray(new String[memSidList.size()]));
                }
            }

            if (!paramMdl.getMan120pluginId().equals(GSConst.PLUGINID_MAIN)) {
                //管理者
                CmnPluginAdminDao pcontrolAdminDao = new CmnPluginAdminDao(con);
                List<CmnPluginAdminModel> admList = pcontrolAdminDao.selectValidity(pluginId);
                List<String> admSidList = new ArrayList<String>();
                if (admList != null && admList.size() > 0) {

                    for (CmnPluginAdminModel admMdl : admList) {
                        if (admMdl.getGrpSid() == GSConstMain.PCM_MEMSID_NOSET) {
                            admSidList.add(String.valueOf(admMdl.getUsrSid()));
                        } else {
                            //システム管理グループは除外する。
                            if (admMdl.getGrpSid() != GSConstUser.SID_ADMIN) {
                                admSidList.add("G" + String.valueOf(admMdl.getGrpSid()));
                            }
                        }
                    }

                    paramMdl.setMan280AdminSid(
                            (String[]) admSidList.toArray(new String[admSidList.size()]));
                } else {
                    paramMdl.setMan280AdminSid(
                            (String[]) admSidList.toArray(new String[admSidList.size()]));

                }
            }
        }


        //プラグインの名称を設定
        Plugin pg = new Plugin();
        pg = pconfig.getPlugin(pluginId);
        paramMdl.setMan280pluginName(pg.getName(reqMdl));

        int pgKbn = 0;
        pgKbn = pg.getPluginKbn();
        if (pgKbn == GSConstMain.PLUGIN_USER) {
            //ユーザプラグインの場合はバイナリSIDを取得
            paramMdl.setMan280pluginKbn(pgKbn);
            paramMdl.setMan280BinSid(pg.getTopMenuInfo().getBinSid());
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグインIDの存在チェック(mainと存在しないものを弾く)
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param pluginId プラグインID
     * @return ActionForward
     * @throws SQLException 
     */
    public boolean _existCheckPluginId(RequestModel reqMdl,
            Connection con, String pluginId) throws SQLException {

        //プラグインIDがメインか
        if (pluginId.equals(GSConst.PLUGINID_MAIN)) {
            return false;
        }

        CommonBiz biz = new CommonBiz();
        List < String > pidList = biz.getPluginConfig(reqMdl).getPluginIdList();
        //プラグインID一覧に含まれるか
        if (pidList.contains(pluginId)) {
            return true;
        }

        CmnUsrPluginDao cupDao = new CmnUsrPluginDao(con);
        CmnUsrPluginModel cupMdl = cupDao.select(pluginId);

        //CMN_USR_PLUGINのデータと一致するか
        if (cupMdl != null) {
            if (cupMdl.getCupPid().equals(pluginId)) {
                return true;
            }
        }
        
        return false;
    }
}
