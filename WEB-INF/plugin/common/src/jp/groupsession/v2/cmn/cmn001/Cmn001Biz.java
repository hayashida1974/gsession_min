package jp.groupsession.v2.cmn.cmn001;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.dao.base.CmnEnterInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnEnterInfModel;


/**
 * <br>[機  能] ログイン画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn001Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn001Biz.class);

    /** 設定値を格納するMap */
    private static Map<String, String> confMap__ = null;
    static {
        confMap__ = Collections.synchronizedMap(new HashMap<String, String>());
    }

    /**
     * <br>[機  能] 企業情報データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return true:チェックOK false:チェックNG
     */
    public CmnEnterInfModel getEnterData(Connection con)
    throws SQLException {

        CmnEnterInfDao ceiDao = new CmnEnterInfDao(con);

        //企業情報データを取得する
        CmnEnterInfModel ceiMdl = ceiDao.select();

        return ceiMdl;
    }

    /**
     * <br>[機  能] ロゴバイナリSIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn001Model パラメータ格納モデル
     * @throws SQLException SQL実行例外
     * @return true:チェックOK false:チェックNG
     */
    public boolean checkLogoBinSid(Connection con,
                       Cmn001ParamModel cmn001Model)
    throws SQLException {

        boolean logoCheckFlg = false;

        CmnEnterInfDao ceiDao = new CmnEnterInfDao(con);

        //ロゴバイナリSIDの一致チェック
        CmnEnterInfModel ceiMdl = ceiDao.getEntInfo(cmn001Model.getCmn001BinSid());

        if (ceiMdl != null) {
            logoCheckFlg = true;
        }

        return logoCheckFlg;
    }

    /**
     * <br>[機  能] 初期値を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param map マップ
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public void setConfData(Cmn001ParamModel paramMdl, String appRootPath,
            RequestModel reqMdl, Connection con, ActionMapping map) throws IOException {
        if (confMap__ != null) {
            //パラメータ設定
            paramMdl.setCmn001CrossRideLinkFlg(
                    Integer.parseInt(confMap__.get("CROSSRIDE_LINK_FLG")));
            paramMdl.setCmn001AppLinkFlg(
                    Integer.parseInt(confMap__.get("APP_LINK_FLG")));
        }
    }


    /**
     * <br>[機  能] 設定ファイルの値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public static synchronized void readConfig(String appRootPath)
            throws IOException {

        String filePath = IOTools.setEndPathChar(appRootPath);
        filePath += "/WEB-INF/conf/";
        filePath += "zlogin.conf";
        File confFile = new File(filePath);
        if (!confFile.exists()) {
            //設定ファイルがない場合はデフォルト設定
            confMap__.put("CROSSRIDE_LINK_FLG", "0");
            confMap__.put("APP_LINK_FLG", "0");
            return;
        }
        //設定ファイルの読み込みを行う
        StringTokenizer lines = new StringTokenizer(
                                    IOTools.readText(filePath, Encoding.UTF_8),
                                    "\n");

        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            line = StringUtil.toDeleteReturnCode(line);
            if (line.startsWith("#") || line.indexOf("=") < 0) {
                //先頭に"#"がついた行はコメントとして扱う
                continue;
            }

            int index = line.indexOf("=");
            String key = line.substring(0, index);
            String value = line.substring(index + 1).trim();
            if (value.length() == 0) {
                value = null;
            }
            log__.debug("<==設定ファイル内容==>");
            log__.debug("key:value==>" + key + ":" + value);
            confMap__.put(key, value);
        }
    }

    /**
     * <br>[機  能] 設定ファイルのキーに対する値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param key キー
     * @return 設定値
     */
    public static String getConfigValue(String key) {
        if (confMap__ != null && confMap__.containsKey(key)) {
            return confMap__.get(key);
        }
        return null;
    }

}
