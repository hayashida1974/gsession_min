package jp.groupsession.v2.cmn.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * <br>[機  能] ThemeCssパス取得用 biz
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ThemePathBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ThemePathBiz.class);
    /** デフォルトCtmSid */
    public static final int DEFAULT_CTM_SID = 7;
    /** デフォルトテーマパス */
    public static final String DEFAULT_THEME_PATH = "common/css/theme_original/light/original";

    /**
    *
    * <br>[機  能] theme_base.cssのパスリストを生成
    * <br>[解  説] themePathと途中のtheme_base.cssのパスを返す
    * <br>[備  考]
    * @param themePath thme.cssのパス
    * @return theme_base.cssのパスリスト
    */
   public List<String> createPathList(String themePath) {

       List<String> ret = new ArrayList<String>();
       ret.add(0, themePath);
       log__.debug("themePath:" + themePath);
       String readPath = themePath.substring(0, themePath.lastIndexOf("/"));
       log__.debug("readPath:" + readPath);
       readPath = readPath.substring(0, readPath.lastIndexOf("/"));

       while (!readPath.endsWith("common/css")) {
           log__.debug("readPath:" + readPath);
           ret.add(0, readPath + "/theme_base.css");
           readPath = readPath.substring(0, readPath.lastIndexOf("/"));
       }

       return ret;
   }


}
