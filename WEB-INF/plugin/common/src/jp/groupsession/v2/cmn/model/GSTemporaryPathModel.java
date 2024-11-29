package jp.groupsession.v2.cmn.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GroupSession;

/**
 *
 * <br>[機  能] テンポラリディレクトリパスを扱うモデルオブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSTemporaryPathModel implements Cloneable {
    /** テンポラリディレクトリ GSTempDir部 */
    private String base__;
    /** テンポラリディレクトリ セッションID・バッチID部 */
    private String sessionId__;
    /** テンポラリディレクトリ プラグインID部 */
    private String pluginId__;
    /** テンポラリディレクトリ ディレクトリID部 */
    private String tempDirId__;
    /** テンポラリディレクトリ サブディレクトリ部 */
    private List<String> subDir__ = new ArrayList<String>();

    /**
     *
     * コンストラクタ
     * @param domain
     * @param batchId
     * @param pluginId
     * @param dirId
     * @param subDir
     */
    private GSTemporaryPathModel(String domain, String batchId,
            String pluginId, String dirId, String... subDir) {
        base__ = GroupSession.getResourceManager().getTempPath(domain);
        sessionId__ = __changeToSaftyPathStr(batchId);

        pluginId__ = __changeToSaftyPathStr(pluginId);

        tempDirId__ = __changeToSaftyPathStr(dirId);

        if (subDir != null && subDir.length > 0) {
            ArrayList<String> list = new ArrayList<String>();
            for (String dirStr : subDir) {
                String[] path = StringUtils.split(
                        IOTools.replaceFileSep(dirStr),
                        File.separator);
                list.addAll(Arrays.asList(path));
            }
            ArrayList<String> safelist = new ArrayList<String>();
            for (String dirStr : list) {
                safelist.add(__changeToSaftyPathStr(dirStr));
            }
            subDir__ = safelist;
        }
    }
    /**
     *
     * <br>[機  能] インスタンス生成 バッチ処理用
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     * @return インスタンス
     */
    public static GSTemporaryPathModel getBatchInstance(String domain,
            String batchId, String pluginId, String... subDir) {
        return new GSTemporaryPathModel(domain, batchId, pluginId, "", subDir);
    }

    /**
     *
     * <br>[機  能] インスタンス生成 各画面用
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dirId ディレクトリID
     * @param subDir サブディレクトリ
     */
    public static GSTemporaryPathModel getInstance(RequestModel reqMdl,
            String pluginId, String dirId, String... subDir) {
        return new GSTemporaryPathModel(reqMdl.getDomain(),
                reqMdl.getSession().getId(),
                pluginId, dirId, subDir);
    }

    /**
     *
     * コンストラクタ サブディレクトリパス生成用
     * @param base
     * @param subDir
     */
    public GSTemporaryPathModel(GSTemporaryPathModel base, String... subDir) {
        base__ = base.getBase();
        sessionId__ = base.getSessionId();
        pluginId__ = base.getPluginId();
        tempDirId__ = base.getTempDirId();
        ArrayList<String> safelist = new ArrayList<String>(base.getSubDir());
        if (subDir != null && subDir.length > 0) {
            ArrayList<String> list = new ArrayList<String>();
            for (String dirStr : subDir) {
                String[] path = StringUtils.split(
                        IOTools.replaceFileSep(dirStr),
                        File.separator);
                list.addAll(Arrays.asList(path));
            }
            for (String dirStr : list) {
                safelist.add(__changeToSaftyPathStr(dirStr));
            }
        }
        subDir__ = safelist;
    }


    /**
     *
     * <br>[機  能] フォルダ名指定文字列を無害化
     * <br>[解  説] ディレクトリ移動文字列を削除する
     * <br>[備  考]
     * @param pathStr
     * @return
     */
    private String __changeToSaftyPathStr(String pathStr) {
        String saftyStr = NullDefault.getString(pathStr, "").replace("../", "");
        saftyStr = saftyStr.replace("/..", "");
        saftyStr = saftyStr.replace("..\\", "");
        saftyStr = saftyStr.replace("\\..", "");
        saftyStr = saftyStr.replace("\\", "");
        if (Objects.equals("..",saftyStr)) {
            saftyStr = "";
        }
        if (Objects.equals(".",saftyStr)) {
            saftyStr = "";
        }
        return saftyStr;

    }

    /**
     *
     * <br>[機  能] テンポラリディレクトリオブジェクトを複製
     * <br>[解  説]
     * <br>[備  考]
     * @param pathStr
     * @return
     */
    public GSTemporaryPathModel clone() {
        return new GSTemporaryPathModel(this);
    }

    /**
     * <p>base__ を取得します。
     * @return base__
     * @see jp.groupsession.v2.cmn.model.GSTemporaryPathModel#base__
     */
    public String getBase() {
        return base__;
    }
    /**
     * <p>sessionId を取得します。
     * @return sessionId
     * @see jp.groupsession.v2.cmn.model.GSTemporaryPathModel#sessionId__
     */
    public String getSessionId() {
        return sessionId__;
    }
    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see jp.groupsession.v2.cmn.model.GSTemporaryPathModel#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     * @see jp.groupsession.v2.cmn.model.GSTemporaryPathModel#tempDirId__
     */
    public String getTempDirId() {
        return tempDirId__;
    }
    /**
     * <p>subDir を取得します。
     * @return subDir
     * @see jp.groupsession.v2.cmn.model.GSTemporaryPathModel#subDir__
     */
    public List<String> getSubDir() {
        return new ArrayList<String>(subDir__);
    }

    public String getTempPath() {
        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(base__);
        tempDir.append("/");
        tempDir.append(sessionId__);
        tempDir.append("/");
        tempDir.append(pluginId__);
        tempDir.append("/");
        tempDir.append(tempDirId__);

        if (subDir__.size() > 0) {

            for (String dirStr : subDir__) {
                tempDir.append("/");
                tempDir.append(dirStr);
            }
        }

        tempDir.append("/");

        return IOTools.replaceFileSep(tempDir.toString());

    }

}
