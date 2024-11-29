package jp.co.sjts.util.struts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.struts.action.ActionServlet;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.log.log4j.Log4jConfig;
import jp.co.sjts.util.struts.RequestLocal.RequestLocalLife;

/**
 * <br>[機  能] ActionServletの拡張クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BaseServlet extends ActionServlet {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(BaseServlet.class);

    /**
     * <br>[機 能] 初期化処理
     * <br>[解 説]
     * <br>[備 考]
     * @exception ServletException 初期設定の取得に失敗した場合にスロー
     */
    public void init() throws ServletException {
        try {
            super.init();
        } catch (ServletException e) {
            e.printStackTrace();
            log("INIT ERROR: " + e.getMessage());
            throw e;
        }
        initLogConfiguration();
        log__ = LogFactory.getLog(BaseServlet.class);
    }
    /**
     * <br>[機  能] ログ設定の再読込処理を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @throws ServletException 初期設定の取得に失敗した場合にスロー
     */
    public void initLogConfiguration() throws ServletException {
        //WEBアプリケーションのパス
        String prefix =  getServletContext().getRealPath("/");
        prefix = IOTools.setEndPathChar(prefix);


        //ログ出力先(ALL)
        String logOutPath = IOTools.replaceSlashFileSep(prefix + "WEB-INF/log/");
        //システム変数にログ出力先パスを保管
        //log4j2_tmp.xmlでプロパティ変数で参照する
        System.setProperty("gs.log.path", logOutPath);


        try {
            //log4j2_tmp.xmlファイルにクラスパスを通す
            String xmlPath = IOTools.replaceSlashFileSep(prefix + "WEB-INF/conf/");
            String logConfFile = xmlPath + "log4j2_tmp.xml";
            System.setProperty(
                    ConfigurationFactory.CONFIGURATION_FILE_PROPERTY,
                    "file:///" + logConfFile);
            Log4jConfig.reloadConfigFile(logConfFile);
        } catch (Exception e) {
            log__.error("log4j2の設定に失敗", e);
            throw new ServletException(e);
        }
    }

    /**
     * <br>[機  能] プロセスメソッドをオーバーライドし、セッションに会社ドメインを保存
     * <br>[解  説]
     * <br>[備  考]
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void process(HttpServletRequest request,
                            HttpServletResponse response) {
        try (RequestLocalLife life = RequestLocal.init();) {
            GSHttpServletRequestWrapper wrapper
            = new GSHttpServletRequestWrapper(request);
            super.process(wrapper, response);
        } catch (Throwable e) {
            log__.error(e);
            log__.error("プロセスメソッドの実行に失敗");
        }

    }
}