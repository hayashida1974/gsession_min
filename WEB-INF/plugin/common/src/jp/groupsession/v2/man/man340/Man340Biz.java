package jp.groupsession.v2.man.man340;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPluginAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlMemberDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginParamDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPluginDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginParamModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPluginModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man340.model.Man340UrlParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man340Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、スレッド情報を設定する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param domain ドメイン
     * @param cmd 操作種別パラメータ
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Man340ParamModel paramMdl, Connection con,
                            String appRoot, String tempDir, String domain,
                            String cmd)
    throws IOException, IOToolsException, SQLException, TempFileException {
        log__.debug("START");
        if (paramMdl.getMan340cmdMode() ==  GSConstMain.CMD_MAIN_EDIT
            && cmd.equals("edit")
            && !StringUtil.isNullZeroString(paramMdl.getMan120pluginId())) {
            //DBからプラグイン情報を取得
            CmnUsrPluginModel mdl = null;
            CmnUsrPluginDao cmnUsrPlgDao = new CmnUsrPluginDao(con);
            mdl = cmnUsrPlgDao.select(paramMdl.getMan120pluginId());
            if (mdl != null) {
                paramMdl.setMan340cmdMode(1);
                paramMdl.setMan340funcId(mdl.getCupPid());
                paramMdl.setMan340title(mdl.getCupName());
                paramMdl.setMan340url(mdl.getCupUrl());
                paramMdl.setMan340openKbn(mdl.getCupTarget());
                if (mdl.getBinSid() != 0) {
                    CommonBiz cmnBiz = new CommonBiz();
                    //添付ファイルをテンポラリディレクトリに格納する。
                    CmnBinfModel binMdl = cmnBiz.getBinInfo(con, mdl.getBinSid(), domain);
                    if (binMdl != null) {
                        //添付ファイルをテンポラリディレクトリに格納する。
                        String imageSaveName = cmnBiz.saveSingleTempFile(binMdl, appRoot, tempDir);
                        paramMdl.setMan340file(binMdl.getBinFileName());
                        paramMdl.setMan340SaveFile(imageSaveName);
                    }
                }
            }

            paramMdl.setMan340paramKbn(mdl.getCupParamKbn());
            paramMdl.setMan340sendKbn(mdl.getCupSendKbn());

            ArrayList<Man340UrlParamModel> urlParamList = new ArrayList<Man340UrlParamModel>();

            //パラメータリスト 「設定する」の場合
            if (mdl.getCupParamKbn() == GSConstMain.PARAM_KBN_YES) {
                CmnPluginParamDao paramDao = new CmnPluginParamDao(con);
                ArrayList<CmnPluginParamModel> paramList =
                        paramDao.select(paramMdl.getMan120pluginId());

                for (CmnPluginParamModel cppMdl : paramList) {
                    Man340UrlParamModel urlMdl = new Man340UrlParamModel();
                    urlMdl.setParamName(cppMdl.getCppName());
                    urlMdl.setParamValue(cppMdl.getCppValue());
                    urlParamList.add(urlMdl);
                }
            }
            urlParamList = __retEmptyUrlParamListAt10(urlParamList);
            paramMdl.setMan340urlParamListForm(urlParamList);
        } else {

            if (paramMdl.getMan340initFlg() == 0) {
                //空リストを生成する
                paramMdl.setMan340urlParamListForm(__retEmptyUrlParamListAt10(null));
            }
        }

        paramMdl.setMan340initFlg(1);
        log__.debug("End");
    }

    /**
     * <br>[機  能] 既存のパラメータリストに合計10件になるまで空行を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param urlParamList パラメータリスト
     * @return パラメータリスト（10件）
     */
    private ArrayList<Man340UrlParamModel> __retEmptyUrlParamListAt10(
            ArrayList<Man340UrlParamModel> urlParamList) {

        ArrayList<Man340UrlParamModel> ret = null;

        int nowSize = 0;

        if (urlParamList != null) {
            ret = urlParamList;
            nowSize = urlParamList.size();
        } else {
            ret = new ArrayList<Man340UrlParamModel>();
        }

        for (int i = 0; i < (GSConstMain.MAX_SET_PARAM_NUM - nowSize); i++) {
            Man340UrlParamModel urlMdl = new Man340UrlParamModel();
            urlMdl.setParamName("");
            urlMdl.setParamValue("");
            ret.add(urlMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] テンポラリディレクトリのファイル削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void doDeleteFile(String tempDir) throws IOToolsException {
        //テンポラリディレクトリのファイルを削除する
        IOTools.deleteDir(tempDir);
    }
    /**
     * <br>[機  能] フォーラム情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void deletePluginData(Man340ParamModel paramMdl, Connection con)
    throws Exception {

        String pluginId = paramMdl.getMan340funcId();

        if (!StringUtil.isNullZeroString(pluginId)) {
            //プラグインアイコン削除
            CmnUsrPluginDao cmnUsrPlgDao = new CmnUsrPluginDao(con);
            cmnUsrPlgDao.updateIconFlg(paramMdl.getMan340funcId());

            //ユーザプラグイン削除
            CmnUsrPluginDao dao = new CmnUsrPluginDao(con);
            dao.delete(paramMdl.getMan340funcId());

            //プラグインパラメータ情報削除
            CmnPluginParamDao cppDao = new CmnPluginParamDao(con);
            cppDao.delete(paramMdl.getMan340funcId());

            //プラグインソート削除
            CmnTdispDao tdDao = new CmnTdispDao(con);
            tdDao.delete(paramMdl.getMan340funcId());

            //プラグイン管理者削除
            CmnPluginAdminDao admDao = new CmnPluginAdminDao(con);
            admDao.delete(paramMdl.getMan340funcId());

            //プラグイン使用制限削除
            CmnPluginControlDao cntDao = new CmnPluginControlDao(con);
            cntDao.delete(paramMdl.getMan340funcId());

            //プラグイン使用制限メンバー削除
            CmnPluginControlMemberDao cntMemDao = new CmnPluginControlMemberDao(con);
            cntMemDao.delete(paramMdl.getMan340funcId());
        }
    }
}
