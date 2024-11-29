package jp.groupsession.v2.cmn.biz;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RedirectingActionForward;
import org.apache.struts.util.LabelValueBean;

import ajd4jp.AJD;
import ajd4jp.LSCD;
import ajd4jp.LunisolarYear;
import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.encryption.Sha;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.lang.ClassUtil;
import jp.co.sjts.util.ldap.LdapConst;
import jp.groupsession.v2.batch.DayJob;
import jp.groupsession.v2.batch.IBatchBackupListener;
import jp.groupsession.v2.batch.IBatchListener;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.GsResourceBundle;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.ITopMenuInfo;
import jp.groupsession.v2.cmn.biz.firewall.FirewallBiz;
import jp.groupsession.v2.cmn.biz.information.CmnInfoMsgBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.AdminSettingInfo;
import jp.groupsession.v2.cmn.config.LoggingConfig;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.PortletInfo;
import jp.groupsession.v2.cmn.config.PrivateSettingInfo;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnDatafolderDao;
import jp.groupsession.v2.cmn.dao.base.CmnDispAconfDao;
import jp.groupsession.v2.cmn.dao.base.CmnDispPconfDao;
import jp.groupsession.v2.cmn.dao.base.CmnLogConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlMemberDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginMobilePermissionDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.UserAgent;
import jp.groupsession.v2.cmn.login.biz.GSLoginBiz;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CliPluginModel;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnDatafolderModel;
import jp.groupsession.v2.cmn.model.base.CmnDatausedModel;
import jp.groupsession.v2.cmn.model.base.CmnDispAconfModel;
import jp.groupsession.v2.cmn.model.base.CmnDispPconfModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnLogConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.lic.LicenseOperation;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] GroupSession全体で使用する共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CommonBiz.class);
    /** 拡張子(BMP) */
    private static final String EXTENSION_BMP = ".BMP";
    /** 拡張子(JPG) */
    private static final String EXTENSION_JPG = ".JPG";
    /** 拡張子(JPEG) */
    private static final String EXTENSION_JPEG = ".JPEG";
    /** 拡張子(GIF) */
    private static final String EXTENSION_GIF = ".GIF";
    /** 拡張子(PNG) */
    private static final String EXTENSION_PNG = ".PNG";

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     *
     * <br>[解  説] 登録に使用する側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で削除矢印ボタンをクリックした場合、
     *              登録に使用する側のコンボで選択中の値(deleteSelectSid)をメンバーリストから削除する。
     *
     * <br>[備  考] 登録に使用する側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param deleteSelectSid 登録に使用する側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 削除済みのメンバーリスト
     */
    public String[] getDeleteMember(String[] deleteSelectSid, String[] memberSid) {

        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }

        if (memberSid == null) {
            memberSid = new String[0];
        }

        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < memberSid.length; i++) {
            boolean setFlg = true;

            for (int j = 0; j < deleteSelectSid.length; j++) {
                if (memberSid[i].equals(deleteSelectSid[j])) {
                    setFlg = false;
                    break;
                }
            }

            if (setFlg) {
                list.add(memberSid[i]);
            }
        }

        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     *
     * <br>[解  説] 登録に使用する側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で削除矢印ボタンをクリックした場合、
     *              登録に使用する側のコンボで選択中の値(deleteSelectSid)をメンバーリストから削除する。
     *
     * <br>[備  考] 登録に使用する側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param deleteSelectSid 登録に使用する側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @param splitFlg true:区切り文字を含む false:含まない
     * @return 削除済みのメンバーリスト
     */
    public String[] getDeleteMemberSplitKey(String[] deleteSelectSid,
                                             String[] memberSid,
                                             boolean splitFlg) {

        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }

        if (memberSid == null) {
            memberSid = new String[0];
        }

        String[] spDelSidList = null;
        if (deleteSelectSid != null && deleteSelectSid.length > 0) {

            int idx = 0;
            spDelSidList = new String[deleteSelectSid.length];

            for (String hdn : deleteSelectSid) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spDelSidList[idx] = String.valueOf(splitStr[0]);
                idx += 1;
            }
        }

        String[] spMemSidList = null;

        if (memberSid != null && memberSid.length > 0) {

            int idx = 0;
            spMemSidList = new String[memberSid.length];

            for (String hdn : memberSid) {

                String[] splitStr = hdn.split(GSConst.GSESSION2_ID);
                spMemSidList[idx] = String.valueOf(splitStr[0]);
                idx += 1;
            }
        }

        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (spMemSidList != null) {
            for (int i = 0; i < spMemSidList.length; i++) {
                boolean setFlg = true;

                for (int j = 0; j < spDelSidList.length; j++) {
                    if (spMemSidList[i].equals(spDelSidList[j])) {
                        setFlg = false;
                        break;
                    }
                }

                if (setFlg) {

                    if (splitFlg) {
                        list.add(
                                memberSid[i]
                              + GSConst.GSESSION2_ID);
                    } else {
                        list.add(memberSid[i]);
                    }
                }
            }
        }

        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値(追加済みのメンバー)
     * @param memberSid メンバーリスト(追加しようとしているメンバー)
     * @return 追加済みのメンバーリスト(実際に追加されるメンバー)
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }

        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (ValidateUtil.isNumber(memberSid[j])) {
                    if (Integer.parseInt(memberSid[j]) >= 0) {
                        list.add(memberSid[j]);
                    }
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (ValidateUtil.isNumber(addSelectSid[i])) {
                if (Integer.parseInt(addSelectSid[i]) >= 0) {
                    list.add(addSelectSid[i]);
                }
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }


    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMemberAndGroup(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMemberSplitKey(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }

        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                list.add(memberSid[j]);
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (Integer.parseInt(addSelectSid[i]) >= 0) {
                list.add(
                        addSelectSid[i]
                       + GSConst.GSESSION2_ID);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * GSデータの保存先ディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String GSデータの保存先ディレクトリPATH
     */
    public String getGSDataDirPath(String appRootPath) {
        StringBuilder rootDir = new StringBuilder("");
        if (ConfigBundle.getValue("GSDATA_DIR") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            String confPath = ConfigBundle.getValue("GSDATA_DIR");
            rootDir.append(confPath);
        } else {
            //デフォルト
            appRootPath = IOTools.setEndPathChar(appRootPath);
            rootDir.append(appRootPath);
            rootDir.append(GSConst.GSDATA_DIR);
        }
        String gsDataDir = IOTools.replaceSlashFileSep(rootDir.toString());
        gsDataDir = IOTools.setEndPathChar(gsDataDir);
        return gsDataDir;
    }

    /**
     * dbの保存先ディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String dbの保存先ディレクトリPATH
     */
    public String getDbRootPath(String appRootPath) {
        StringBuilder rootDir = new StringBuilder("");
        if (ConfigBundle.getValue("GSDATA_DIR") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            String confPath = ConfigBundle.getValue("GSDATA_DIR");
            confPath = IOTools.setEndPathChar(confPath);
            rootDir.append(confPath);
            rootDir.append(GSConst.DB_DIR);
        } else {
            //デフォルト
            appRootPath = IOTools.setEndPathChar(appRootPath);
            rootDir.append(appRootPath);
            rootDir.append(GSConst.DB_SAVE_DIR);
        }

        String dbRoot = IOTools.replaceSlashFileSep(rootDir.toString());
        dbRoot = IOTools.setEndPathChar(dbRoot);
        return dbRoot;
    }

    /**
     * バイナリーデータの保存先ディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String バイナリーデータの保存先ディレクトリPATH
     */
    public String getFileRootPath(String appRootPath) {
        StringBuilder rootDir = new StringBuilder("");
        if (ConfigBundle.getValue("GSDATA_DIR") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            String confPath = ConfigBundle.getValue("GSDATA_DIR");
            confPath = IOTools.setEndPathChar(confPath);
            rootDir.append(confPath);
            rootDir.append(GSConst.FILE_DIR);
        } else {
            //デフォルト
            appRootPath = IOTools.setEndPathChar(appRootPath);
            rootDir.append(appRootPath);
            rootDir.append(GSConst.FILE_SAVE_DIR);
        }

        String fileRoot = IOTools.replaceSlashFileSep(rootDir.toString());
        fileRoot = IOTools.setEndPathChar(fileRoot);
        return fileRoot;
    }
    /**
     * ファイル管理用のバイナリーデータの保存先ディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String バイナリーデータの保存先ディレクトリPATH
     */
    public String getFileRootPathForFileKanri(String appRootPath) {
        StringBuilder rootDir = new StringBuilder("");
        if (ConfigBundle.getValue("FILEKANRI_DIR") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            String confPath = ConfigBundle.getValue("FILEKANRI_DIR");
            confPath = IOTools.setEndPathChar(confPath);
            rootDir.append(confPath);
            rootDir.append(GSConst.FILE_KANRI_DIR);
        } else {
            //デフォルト
            appRootPath = IOTools.setEndPathChar(appRootPath);
            rootDir.append(appRootPath);
            rootDir.append(GSConst.FILE_KANRI_SAVE_DIR);
        }

        String fileRoot = IOTools.replaceSlashFileSep(rootDir.toString());
        fileRoot = IOTools.setEndPathChar(fileRoot);
        return fileRoot;
    }
    /**
     * <br>[機  能] WEBメール用のバイナリーデータの保存先ディレクトリPATHを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String バイナリーデータの保存先ディレクトリPATH
     */
    public String getFileRootPathForWebmail(String appRootPath) {
        StringBuilder rootDir = new StringBuilder("");
        if (ConfigBundle.getValue("WEBMAIL_DIR") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            String confPath = IOTools.replaceFileSep(ConfigBundle.getValue("WEBMAIL_DIR"));
            rootDir.append(IOTools.setEndPathChar(confPath));
        } else {
            //デフォルト
            appRootPath = IOTools.replaceFileSep(appRootPath);
            rootDir.append(IOTools.setEndPathChar(appRootPath));
            rootDir.append(IOTools.replaceFileSep(GSConst.FILE_WEBMAIL_SAVE_DIR));
        }

        String fileRoot = IOTools.setEndPathChar(rootDir.toString());
        return IOTools.replaceSlashFileSep(fileRoot);
    }
    /**
     * バックアップディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String バックアップディレクトリPATH
     */
    public static String getBackupDirPath(String appRootPath) {
        String backupDir
            = NullDefault.getString(ConfigBundle.getValue("BACKUP_DIR"), "").trim();
        if (backupDir.length() == 0) {
            backupDir = appRootPath.concat("WEB-INF/");
        } else if (!(new File(backupDir)).isDirectory()) {
            backupDir = backupDir.concat("/");
        }
        backupDir = backupDir.concat("backup/");
        backupDir = IOTools.replaceFileSep(backupDir);

        return backupDir;
    }

    /**
     * 手動バックアップディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String 手動バックアップディレクトリPATH
     */
    public static String getManualBackupDirPath(String appRootPath) {
        String backupDir
            = NullDefault.getString(ConfigBundle.getValue("BACKUP_DIR"), "").trim();
        if (backupDir.length() == 0) {
            backupDir = appRootPath.concat("WEB-INF/");
        } else if (!IOTools.replaceSlashFileSep(backupDir).endsWith("/")) {
            backupDir += "/";
        }

        backupDir = backupDir.concat("backup/");
        backupDir = backupDir.concat("manual/");
        backupDir = IOTools.replaceFileSep(backupDir);

        return backupDir;
    }

    /**
     * <br>[機  能] 添付ファイル保存用のパスを取得する(DB登録用のパス)
     * <br>[解  説] 例）2006/08/23/1
     * <br>[備  考]
     * @param saveDate 日付
     * @param binSid バイナリーSID
     * @return 添付ファイル保存用のパス
     */
    public String getSavePathForDb(UDate saveDate, Long binSid) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(saveDate.getStrYear());
        tempDir.append("/");
        tempDir.append(saveDate.getStrMonth());
        tempDir.append("/");
        tempDir.append(saveDate.getStrDay());
        tempDir.append("/");
        tempDir.append(String.valueOf(binSid));
//      添付ファイル保存先PATHのファイルセパレーターは/で保存
//        String savePath = IOTools.replaceFileSep(tempDir.toString());
        String savePath = IOTools.replaceSlashFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] 添付ファイル保存用のパスを取得する(DB登録用のパス)
     * <br>[解  説] 例）2006/08/23/1
     * <br>[備  考]
     * @param saveDate 日付
     * @param binSid バイナリーSID
     * @return 添付ファイル保存用のパス
     */
    public String getSavePathForDb(UDate saveDate, long binSid) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(saveDate.getStrYear());
        tempDir.append("/");
        tempDir.append(saveDate.getStrMonth());
        tempDir.append("/");
        tempDir.append(saveDate.getStrDay());
        tempDir.append("/");
        tempDir.append(String.valueOf(binSid));
//      添付ファイル保存先PATHのファイルセパレーターは/で保存
//        String savePath = IOTools.replaceFileSep(tempDir.toString());
        String savePath = IOTools.replaceSlashFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] ライセンスファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file
     * <br>[備  考]
     *
     * @param appRootPath アプリケーションルートパス
     * @param domain ドメイン
     * @return 添付ファイル保存用のパス
     */
    public String getSaveLicensePath(String appRootPath, String domain) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(__getLicenseFileRootPath(appRootPath, domain));
        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] 添付ファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file/2006/08/23/1
     * <br>[備  考]
     * @param saveDate 日付
     * @param binSid バイナリーSID
     * @param appRootPath アプリケーションルートパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveFullPath(UDate saveDate, Long binSid, String appRootPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPath(appRootPath));
//        tempDir.append(appRootPath);
//        tempDir.append(GSConst.FILE_SAVE_DIR);
        tempDir.append(getSavePathForDb(saveDate, binSid));
        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] 添付ファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file/2006/08/23/1
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param binPath DB(CMN_BINF)から取得したファイルパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveFullPath(String appRootPath, String binPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPath(appRootPath));
//        tempDir.append(appRootPath);
//        tempDir.append(GSConst.FILE_SAVE_DIR);
        tempDir.append(binPath);

        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] 添付ファイル保存用ディレクトリのパスを取得する
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file/2006/08/23/
     * <br>[備  考]
     * @param saveDate 日付
     * @param appRootPath アプリケーションルートパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveDirPath(UDate saveDate, String appRootPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPath(appRootPath));
//        tempDir.append(appRootPath);
//        tempDir.append(GSConst.FILE_SAVE_DIR);
        tempDir.append(saveDate.getStrYear());
        tempDir.append("/");
        tempDir.append(saveDate.getStrMonth());
        tempDir.append("/");
        tempDir.append(saveDate.getStrDay());
        tempDir.append("/");

        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }


    /**
     * <br>[機  能] ファイル管理用の添付ファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file/2006/08/23/1
     * <br>[備  考]
     * @param saveDate 日付
     * @param binSid バイナリーSID
     * @param appRootPath アプリケーションルートパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveFullPathForFileKanri(UDate saveDate, Long binSid, String appRootPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPathForFileKanri(appRootPath));
        tempDir.append(getSavePathForDb(saveDate, binSid));
        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] ファイル管理用の添付ファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file/2006/08/23/1
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param binPath DB(CMN_BINF)から取得したファイルパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveFullPathForFileKanri(String appRootPath, String binPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPathForFileKanri(appRootPath));
        tempDir.append(binPath);

        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] ファイル管理用の添付ファイル保存用ディレクトリのパスを取得する
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/filekanri/2006/08/23/
     * <br>[備  考]
     * @param saveDate 日付
     * @param appRootPath アプリケーションルートパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveDirPathForFileKanri(UDate saveDate, String appRootPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPathForFileKanri(appRootPath));
        tempDir.append(saveDate.getStrYear());
        tempDir.append("/");
        tempDir.append(saveDate.getStrMonth());
        tempDir.append("/");
        tempDir.append(saveDate.getStrDay());
        tempDir.append("/");

        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] WEBメール用の添付ファイル保存用ディレクトリのパスを取得する
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/webmail/2006/08/23/
     * <br>[備  考]
     * @param saveDate 日付
     * @param appRootPath アプリケーションルートパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveDirPathForFileWebmail(UDate saveDate, String appRootPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPathForWebmail(appRootPath));
        tempDir.append(saveDate.getStrYear());
        tempDir.append("/");
        tempDir.append(saveDate.getStrMonth());
        tempDir.append("/");
        tempDir.append(saveDate.getStrDay());
        tempDir.append("/");

        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] WEBメール添付ファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/webmail/2010/06/21/1
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @param binPath DB(WML_TEMPFILE)から取得したファイルパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveFullPathForWebmail(String appRootPath, String binPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPathForWebmail(appRootPath));
        tempDir.append(binPath);

        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] WEBメール用の添付ファイル保存用のパスを取得する(フルパス)
     * <br>[解  説] 例）C:/gsession/war/WEB-INF/file/2006/08/23/1
     * <br>[備  考]
     * @param saveDate 日付
     * @param binSid バイナリーSID
     * @param appRootPath アプリケーションルートパス
     * @return 添付ファイル保存用のパス
     */
    public String getSaveFullPathForWebmail(UDate saveDate, long binSid, String appRootPath) {

        StringBuilder tempDir = new StringBuilder("");
        tempDir.append(getFileRootPathForWebmail(appRootPath));
        tempDir.append(getSavePathForDb(saveDate, binSid));
        String savePath = IOTools.replaceFileSep(tempDir.toString());
        return savePath;
    }

    /**
     * <br>[機  能] 添付ファイル(本体)のパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @return 添付ファイル(本体)のパス
     */
    public static File getSaveFilePath(String tempDir, String dateStr, int fileNum) {

        return __getFilePath(tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_SAVEFILE);
    }

    /**
     * <br>[機  能] オブジェクトファイルのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @return 添付ファイル(本体)のパス
     */
    public static File getObjFilePath(String tempDir, String dateStr, int fileNum) {

        return __getFilePath(tempDir, dateStr, fileNum, GSConstCommon.ENDSTR_OBJFILE);
    }

    /**
     * <br>[機  能] ファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリファイル
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param fileNum 連番
     * @param endStr 接尾文字列("file" or "obj")
     * @return ファイルパス
     */
    private static File __getFilePath(
        String tempDir,
        String dateStr,
        int fileNum,
        String endStr) {

        StringBuilder filePath = new StringBuilder("");
        filePath.append(tempDir);
        filePath.append(dateStr);
        filePath.append(StringUtil.toDecFormat(fileNum, "000"));
        filePath.append(endStr);

        return new File(filePath.toString());
    }

    /**
     * バイナリーデータの保存先ディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @param domain ドメイン
     * @return String バイナリーデータの保存先ディレクトリPATH
     */
    private String __getLicenseFileRootPath(String appRootPath, String domain) {
        StringBuilder rootDir = new StringBuilder("");
        if (ConfigBundle.getValue("GSDATA_DIR") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            String confPath = ConfigBundle.getValue("GSDATA_DIR");
            confPath = IOTools.setEndPathChar(confPath);
            rootDir.append(confPath);
            rootDir.append(GSConst.FILE_DIR);
        } else {
            //デフォルト
            appRootPath = IOTools.setEndPathChar(appRootPath);
            rootDir.append(appRootPath);
            rootDir.append(GSConst.FILE_SAVE_DIR);
        }
        String fileRoot
                 = GroupSession.getResourceManager().getSaveLicensePath(rootDir.toString(), domain);
        fileRoot = IOTools.replaceSlashFileSep(fileRoot);
        fileRoot = IOTools.setEndPathChar(fileRoot);
        return fileRoot;
    }
    /**
     *
     * <br>[機  能] テンポラリディレクトリに指定日時より新しいファイルがあるか判定する
     * <br>[解  説]
     * <br>[備  考] テンポラリディレクトリが空の場合はfalseが帰る
     * @param tempDir テンポラリディレクトリパス
     * @param date 判定日時
     * @return 判定結果 true 存在する false 存在しない
     */
    public boolean isHaveNewFile(String tempDir, UDate date) {
        //テンポラリディレクトリにあるファイルを取得
        Enumeration<File> files = IOTools.getFiles(tempDir);
        if (files != null) {
            while (files.hasMoreElements()) {
                File file = files.nextElement();
                UDate lastMoified = UDate.getInstance(file.lastModified());
                if (date.compare(date, lastMoified) == UDate.LARGE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 画面に表示する添付ファイル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @return 画面表示用添付ファイル一覧
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public List<LabelValueBean> getTempFileLabelList(String tempDir)
    throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        List<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                long atattiSize = new File(tempDir, name).length();
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(
                        addAtattiSizeForName(fMdl.getFileName(), atattiSize), value[0]));

                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
                log__.debug("ファイルサイズ(byte) =" + fMdl.getAtattiSize());
            }
        }

        return fileLblList;
    }

    /**
     * <br>[機  能] TEMPディレクトリにある添付ファイルの合計サイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @return 画面表示用添付ファイル一覧
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public Long getTempFileSize(String tempDir)
    throws IOToolsException {

        Long fileSize = new Long(0);

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(tempDir);

        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                long atattiSize = new File(tempDir, name).length();

                fileSize += atattiSize;
            }
        }

        return fileSize;
    }

    /**
     * <br>[機  能] TEMPディレクトリにある添付ファイル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @return 添付ファイル一覧
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     * @deprecated リファクタリング対象の為、将来的に削除します。
     */
    @Deprecated(since = "5.6.0", forRemoval = true)
    public List<TempFileModel> getTempFiles(String tempDir)
    throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(tempDir);

        //ファイルのリストを作成
        List<TempFileModel> fileLblList = new ArrayList<TempFileModel>();
        TempFileModel tempFileMdl = null;
        if (fileList != null) {

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                File file = new File(tempDir, name);
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                log__.debug("fMdl.getFileName()==>" + fMdl.getFileName());
                tempFileMdl = new TempFileModel();
                tempFileMdl.setFile(file);
                tempFileMdl.setFileName(fMdl.getFileName());
                fileLblList.add(tempFileMdl);
                log__.debug("file.getName()==>" + file.getName());
            }
        }

        return fileLblList;
    }
    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     *
     * <br>[解  説] ファイル本体は保存用ディレクトリにコピー、
     *              ファイル情報はDBに登録する
     * <br>[備  考]
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertBinInfo(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<String> binList
            = tempFileUtil.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);

        return binList;

    }

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、
     *              登録時のバイナリーSIDを返す
     *
     * <br>[解  説] ファイル本体は保存用ディレクトリにコピー、
     *              ファイル情報はDBに登録する
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param filePath ファイルパス
     * @param fileName ファイル名
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long insertBinInfo(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        String filePath,
        String fileName) throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        Long binSid = tempFileUtil.insertBinInfo(
                con, appRootPath, cntCon, userSid, now, filePath, fileName);

        return binSid;

    }

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、
     *              登録時のバイナリーSIDを返す
     *
     * <br>[解  説] ファイル本体は保存用ディレクトリにコピー、
     *              ファイル情報はDBに登録する
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param binSid ファイルSID
     * @param fileName ファイル名
     * @param domain ドメイン
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long insertBinInfoForFilekanri(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        long binSid,
        String fileName,
        String domain) throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        Long ret = tempFileUtil.insertBinInfoForFilekanri(
                con, appRootPath, cntCon, userSid, now, binSid, fileName, domain);

        return ret;

    }

    /**
     * <br>[機  能] 添付ファイルを登録し、登録時のバイナリーSIDリストを返す
     * <br>[解  説] ファイル本体の保存先を振り分ける。
     * <br>[備  考] ウェブメール用
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param fileDataList ファイルデータリスト
     * @param mailNum メール番号
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<Long> insertBinInfoForWebmail(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        List<WmlMailFileModel> fileDataList,
        long mailNum) throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<Long> binSidList = tempFileUtil.insertBinInfoForWebmail(
                con, appRootPath, cntCon, userSid, now, fileDataList, mailNum);

        return binSidList;

    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説]
     * <br>[備  考] ファイル管理で使用
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertBinInfoForFileKanri(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<String> binList = tempFileUtil.insertBinInfoForFilekanri(
                con, tempDir, appRootPath, cntCon, userSid, now);

        return binList;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertSameBinInfo(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<String> binList = tempFileUtil.insertSameBinInfo(
                con, tempDir, appRootPath, cntCon, userSid, now);

        return binList;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーモデルをListで返す
     * <br>[解  説]
     * <br>[備  考] ファイル管理で使用
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<CmnBinfModel> insertSameBinInfoForFileKanri(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<CmnBinfModel> binList = tempFileUtil.insertSameBinInfoForFileKanri(
                con, tempDir, appRootPath, cntCon, userSid, now);
        return binList;
    }

    /**
     * <br>[機  能] 指定した添付ファイルを更新し、更新件数を返す。
     * <br>[解  説] ファイル本体は保存保存先を振り分ける。
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param filePath ファイルパス
     * @param binSid バイナリーSID
     * @param fileName ファイル名
     * @param cntCon MlCountMtController
     * @return 更新件数
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public int updateBinInfo(
        Connection con,
        String appRootPath,
        int userSid,
        UDate now,
        String filePath,
        Long binSid,
        String fileName,
        MlCountMtController cntCon) throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        int count = tempFileUtil.updateBinInfo(
                con, appRootPath, userSid, now, filePath, binSid, fileName, cntCon);

        return count;

    }

    /**
     * <br>[機  能] データフォルダ容量の登録，更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @return ディスク情報
     * @throws SQLException SQL実行例外
     */
    public void updateFolderSize(Connection con, String appRootPath) throws SQLException {

        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() != GSConst.DBTYPE_H2DB) {
            return;
        }

        CommonBiz cmnBiz = new CommonBiz();
        CmnDatafolderDao cdfDao = new CmnDatafolderDao(con);
        CmnDatafolderModel cdfMdl = new CmnDatafolderModel();
        int sort = 1;

        String dbPath = cmnBiz.getDbRootPath(appRootPath);
        File dbDir = new File(dbPath);
        if (dbDir.isDirectory()) {
            long dbSize = FileUtils.sizeOfDirectory(dbDir);
            cdfMdl.setCdfName(GSConst.DIR_NAME_DB);
            cdfMdl.setCdfSize(dbSize);
            cdfMdl.setCdfSort(sort);
            if (cdfDao.update(cdfMdl) == 0) {
                cdfDao.insert(cdfMdl);
            }
            sort++;
        }

        String filePath = cmnBiz.getFileRootPath(appRootPath);
        File fileDir = new File(filePath);
        if (fileDir.isDirectory()) {
            long fileSize = FileUtils.sizeOfDirectory(fileDir);
            cdfMdl.setCdfName(GSConst.DIR_NAME_FILE);
            cdfMdl.setCdfSize(fileSize);
            cdfMdl.setCdfSort(sort);
            if (cdfDao.update(cdfMdl) == 0) {
                cdfDao.insert(cdfMdl);
            }
            sort++;
        }

        String filekanriPath = cmnBiz.getFileRootPathForFileKanri(appRootPath);
        File filekanriDir = new File(filekanriPath);
        if (filekanriDir.isDirectory()) {
            long filekanriSize = FileUtils.sizeOfDirectory(filekanriDir);
            cdfMdl.setCdfName(GSConst.DIR_NAME_FILEKANRI);
            cdfMdl.setCdfSize(filekanriSize);
            cdfMdl.setCdfSort(sort);
            if (cdfDao.update(cdfMdl) == 0) {
                cdfDao.insert(cdfMdl);
            }
            sort++;
        } else {
            cdfDao.delete(GSConst.DIR_NAME_FILEKANRI);
        }

        String webmailPath = cmnBiz.getFileRootPathForWebmail(appRootPath);
        File webmailDir = new File(webmailPath);
        if (webmailDir.isDirectory()) {
            long webmailSize = FileUtils.sizeOfDirectory(webmailDir);
            cdfMdl.setCdfName(GSConst.DIR_NAME_WEBMAIL);
            cdfMdl.setCdfSize(webmailSize);
            cdfMdl.setCdfSort(sort);
            if (cdfDao.update(cdfMdl) == 0) {
                cdfDao.insert(cdfMdl);
            }
        }  else {
            cdfDao.delete(GSConst.DIR_NAME_WEBMAIL);
        }
    }

    /**
     * <br>[機  能] 指定したバイナリSIDのファイルをコピーする。
     * <br>[解  説]
     * <br>[備  考] ファイル管理で使用する。
     * @param appRoot アプリケーションのルートパス
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param domain ドメイン
     * @return newBinSid 採番バイナリSID
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long copyfile(
            String appRoot,
            Long binSid,
            int usrSid,
            Connection con,
            MlCountMtController cntCon,
            String domain
            ) throws TempFileException {


        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        Long newBinSid = tempFileUtil.copyFile(appRoot, binSid, usrSid, con, cntCon, domain);
        tempFileUtil.utilDestroy();
        return newBinSid;

    }

    /**
     * <br>[機  能] 添付ファイルを削除する。
     * <br>[解  説] 添付ファイルの実体と添付ファイル情報を削除する。
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param appRootPath アプリケーションのルートパス
     * @param con コネクション
     * @throws SQLException SQL例外処理
     */
    public void deleteFileComp(CmnBinfModel cbMdl, String appRootPath, Connection con
            ) throws SQLException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        tempFileUtil.deleteFileComp(cbMdl, appRootPath, con);
    }

    /**
     * <br>[機  能] 添付ファイルを削除する。
     * <br>[解  説] ファイルシステムの添付ファイル本体を削除する。
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param appRootPath アプリケーションのルートパス
     * @throws IOToolsException ファイル操作時例外
     */
    public void deleteFile(CmnBinfModel cbMdl, String appRootPath) throws IOToolsException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        tempFileUtil.deleteFile(cbMdl, appRootPath);
    }

    /**
     * <br>[機  能] 添付ファイルを削除する。
     * <br>[解  説] 添付ファイル本体を削除する。
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param binSidList 削除対象のバイナリSID一覧
     * @throws SQLException ExceptionSQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public void deleteFileForWebmail(Connection con, String appRootPath,
                                    List<Long> binSidList)
    throws SQLException, IOToolsException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        tempFileUtil.deleteFileForWebmail(con, appRootPath, binSidList);
    }


    /**
     * <br>[機  能] 添付ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリーSID(複数)
     * @return int 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteBinInf(Connection con, String[] binSids) throws SQLException {

        int count = 0;
        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        count = tempFileUtil.deleteBinInf(con, binSids);
        return count;
    }

    /**
     * <br>[機  能] 論理削除済み添付ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rootPath アプリケーションのルートパス
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス実行時例外
     */
    public int deleteAllLogicalDeletedBinInf(Connection con,
            String rootPath) throws SQLException, IOToolsException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        return tempFileUtil.deleteAllLogicalDeletedBinInf(con, rootPath);

    }

    /**
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public CmnBinfModel getBinInfo(Connection con, Long binSid, String domain)
    throws TempFileException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
        CmnBinfModel model = tempFileUtil.getBinInfo(con, binSid, domain);

        return model;
    }

    /**
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public CmnBinfModel getBinInfoToDomain(Connection con, Long binSid, String domain)
    throws TempFileException {

        ITempFileUtil tempFileUtil
        = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
        CmnBinfModel model = tempFileUtil.getBinInfoToDomain(con, binSid, domain);

        return model;
    }

    /**
     * <br>[機  能] 指定したバイナリー情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリSIDリスト
     * @param domain ドメイン
     * @return バイナリ情報リスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<CmnBinfModel> getBinInfo(Connection con, String[] binSids, String domain)
    throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<CmnBinfModel> binList = tempFileUtil.getBinInfo(con, binSids, domain);

        return binList;
    }

    /**
     * <br>[機  能] 指定したバイナリー情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリSIDリスト
     * @param domain ドメイン
     * @return バイナリ情報リスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<CmnBinfModel> getBinInfoToDomain(Connection con, String[] binSids, String domain)
    throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        List<CmnBinfModel> binList = tempFileUtil.getBinInfoToDomain(con, binSids, domain);

        return binList;
    }

    /**
     * <br>[機  能] バイナリー情報を取得する
     * <br>[解  説]
     * <br>[備  考] ウェブメールで使用する
     * @param con コネクション
     * @param wmdMailnum メッセージ番号
     * @param wtfSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public WmlTempfileModel getBinInfoForWebmail(Connection con,
                                                 long wmdMailnum,
                                                 long wtfSid,
                                                 String domain)
    throws TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        WmlTempfileModel model = tempFileUtil.getBinInfoForWebmail(con, wmdMailnum, wtfSid, domain);
        return model;
    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する(単一ファイル用)
     * <br>[解  説]
     * <br>[備  考] 添付ファイル名と保存用ファイル名を返します
     * @param binMdl 添付ファイル情報
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @return 保存用ファイル名
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String saveSingleTempFile(
            CmnBinfModel binMdl,
            String appRoot,
            String tempDir)
                    throws IOException,
                    IOToolsException,
                    TempFileException {
        String saveName = "";

        //添付ファイルを本体とオブジェクトファイルにしてテンポラリディレクトリに格納する。
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
        saveTempFile(dateStr, binMdl, appRoot, tempDir, 0);

        List <String> fileList = IOTools.getFileNames(tempDir);
        for (String fileName : fileList) {
            if (fileName.endsWith(GSConstCommon.ENDSTR_SAVEFILE)) {
                String[] splitFileName = fileName.split(GSConstCommon.ENDSTR_SAVEFILE);
                saveName = splitFileName[0];
                break;
            }
        }

        return saveName;
    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param fileNum ファイルの連番
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String saveTempFile(String dateStr, CmnBinfModel binData,
                            String appRoot, String tempDir, int fileNum)
    throws IOException, IOToolsException, TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        String filePath = tempFileUtil.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);

        return filePath;

    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param binData バイナリデータ
     * @param fileMdl 添付ファイル情報
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param fileNum ファイルの連番
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String saveTempFile(String dateStr, CmnBinfModel binData, Cmn110FileModel fileMdl,
                            String appRoot, String tempDir, int fileNum)
    throws IOException, IOToolsException, TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        String filePath = tempFileUtil.saveTempFile(
                dateStr, binData, fileMdl, appRoot, tempDir, fileNum);

        return filePath;

    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] オブジェクトファイルを生成しない
     * <br>[備  考]
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String saveTempFile(CmnBinfModel binData, String appRoot, String tempDir)
    throws IOException, IOToolsException, TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        String filePath = tempFileUtil.saveTempFile(binData, appRoot, tempDir);

        return filePath;

    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説]
     * <br>[備  考] ウェブメールで使用する。
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param fileNum ファイルの連番
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public String saveTempFileForWebmail(String dateStr, WmlTempfileModel binData,
                            String appRoot, String tempDir, int fileNum)
    throws IOException, IOToolsException, TempFileException {

        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);

        String filePath
            = tempFileUtil.saveTempFileForWebmail(
                    dateStr, binData, appRoot, tempDir, fileNum);

        return filePath;

    }

    /**
     * <br>[機  能] ファイルのサイズを付加した名称を取得します。ex ZZZZ.9(MB) or ZZZZ.9(KB)
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名称
     * @param byteSize バイトサイズ
     * @return MB単位のファイルサイズ
     */
    @Deprecated
    public static String addAtattiSizeForName(String fileName, long byteSize) {

        //TODO リファクタリング対象
//        String value = fileName + "(" + formatByteSizeString(byteSize) + ")";

        log__.debug("ファイルサイズ :" + fileName);

        return fileName;
    }

    /**
     * <br>[機  能] バイトのサイズを文字列で取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param byteSize バイトサイズ
     * @return バイトの文字列表現 (ex: xxxKB xMB)
     */
    public static String formatByteSizeString(long byteSize) {

        String ret = "";

        //フォーマッタ
        String formatta = "0.0";
        DecimalFormat decimalFormat = new DecimalFormat(formatta);

        long divider = 1024;
        String unit = "KB";
        log__.debug("ファイルサイズ(byte) :" + byteSize);
        if (byteSize >= (1024 * 1024)) {
            divider = divider * 1024;
            unit = "MB";
        }

        //ファイルサイズ取得
        BigDecimal bunbo = new BigDecimal(divider);
        BigDecimal bd = new BigDecimal(byteSize);
        ret = decimalFormat.format(bd.divide(bunbo, 1, BigDecimal.ROUND_HALF_UP));

        return ret.concat(unit);
    }

    /**
     * <br>[機  能] バイトのサイズを文字列で取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param byteSize バイトサイズ
     * @return バイトの文字列表現 (ex: xxxKB xMB)
     */
    public String getByteSizeString(long byteSize) {

        String ret = "";

        //フォーマッタ
        String formatta = "0.0";
        DecimalFormat decimalFormat = new DecimalFormat(formatta);

        long divider = 1024;
        String unit = "KB";
        log__.debug("ファイルサイズ(byte) :" + byteSize);
        if (byteSize >= (1024 * 1024)) {
            divider = divider * 1024;
            unit = "MB";
        }

        //ファイルサイズ取得
        BigDecimal bunbo = new BigDecimal(divider);
        BigDecimal bd = new BigDecimal(byteSize);
        ret = decimalFormat.format(bd.divide(bunbo, 1, BigDecimal.ROUND_HALF_UP));

        String str = "(" + ret.concat(unit) + ")";
        return str;
    }

    /**
     * <br>[機  能] 六曜を表示するかの判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @return result true:表示する false:表示しない
     * @throws SQLException
     */
    public boolean getRokuyoDspKbn(Connection con, int sessionUsrSid) throws SQLException {

        boolean result = true;
        CmnDispAconfDao aDao = new CmnDispAconfDao(con);
        CmnDispAconfModel aModel = aDao.select();
        if (aModel == null) {
            //データが存在しない
            result = false;
        } else if (aModel.getCdaRokuyouKbn() == GSConst.SETTING_ADM
                && aModel.getCdaRokuyou() == GSConst.DSP_NOT) {
            //管理者が設定する 表示しない
            result = false;
        } else if (aModel.getCdaRokuyouKbn() == GSConst.SETTING_USR) {
            //各ユーザが設定する
            CmnDispPconfDao pDao = new CmnDispPconfDao(con);
            CmnDispPconfModel pModel = pDao.select(sessionUsrSid);
            if (pModel == null) {
                //データが存在しない
                result = false;
            } else if (pModel.getCdpRokuyou() == GSConst.DSP_NOT) {
                //表示しない
                result = false;
            }
        }
        return result;
    }

    /**
     * <br>[機  能] 指定した日付の六曜を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @return 日付の六曜
     */
    public String getRokuyou(UDate date) {

        AJD ajd = new AJD(new Date(date.getTimeMillis()));
        //SimpleDateFormat year = new SimpleDateFormat("yyyy");
        LunisolarYear ly = LunisolarYear.getLunisolarYear(ajd);
        LSCD lscd = ly.getLSCD(ajd);
        String rokuyou = "";
        if (lscd != null) {
            rokuyou = lscd.getRokuyo().getName();
        }
        return rokuyou;
    }

    /**
     * <br>[機  能] 取得した六曜を区分値に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param rokuyou 六曜
     * @return 六曜の区分
     */
    public int setRkyKbn(String rokuyou) {

        if (rokuyou.equals(GSConst.ROKUYOU_SENSHOU)) {
            return GSConst.RKY_KBN_SENSHOU;
        } else if (rokuyou.equals(GSConst.ROKUYOU_TOMOBIKI)) {
            return GSConst.RKY_KBN_TOMOBIKI;
        } else if (rokuyou.equals(GSConst.ROKUYOU_SENBU)) {
            return GSConst.RKY_KBN_SENBU;
        } else if (rokuyou.equals(GSConst.ROKUYOU_BUTSUMETSU)) {
            return GSConst.RKY_KBN_BUTSUMETSU;
        } else if (rokuyou.equals(GSConst.ROKUYOU_TAIAN)) {
            return GSConst.RKY_KBN_TAIAN;
        } else if (rokuyou.equals(GSConst.ROKUYOU_SHAKKOU)) {
            return GSConst.RKY_KBN_SHAKKOU;
        }
        return 0;
    }

    /**
     * <br>[機  能] 取得した六曜を区分値に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param rkyKbn 六曜区分
     * @return 六曜の区分
     */
    public String setRkyName(String rkyKbn) {
        if (!rkyKbn.isEmpty()) {
            int rky = Integer.parseInt(NullDefault.getString(rkyKbn, "0"));
            if (rky == GSConst.RKY_KBN_SENSHOU) {
                return GSConst.ROKUYOU_SENSHOU;
            } else if (rky == GSConst.RKY_KBN_TOMOBIKI) {
                return GSConst.ROKUYOU_TOMOBIKI;
            } else if (rky == GSConst.RKY_KBN_SENBU) {
                return GSConst.ROKUYOU_SENBU;
            } else if (rky == GSConst.RKY_KBN_BUTSUMETSU) {
                return GSConst.ROKUYOU_BUTSUMETSU;
            } else if (rky == GSConst.RKY_KBN_TAIAN) {
                return GSConst.ROKUYOU_TAIAN;
            } else if (rky == GSConst.RKY_KBN_SHAKKOU) {
                return GSConst.ROKUYOU_SHAKKOU;
            }
        }
        return "";
    }

    /**
     * 都道府県コンボ用の都道府県リストを取得する
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getTdfkLabelList(
            Connection con, GsMessage gsMsg) throws SQLException {

        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(textSelect, String.valueOf(0)));

        //グループリスト取得
        CmnTdfkDao tdDao = new CmnTdfkDao(con);
        List < CmnTdfkModel > tdList = tdDao.select();

        CmnTdfkModel tdMdl = null;
        for (int i = 0; i < tdList.size(); i++) {
            tdMdl = tdList.get(i);
            labelList.add(
                    new LabelValueBean(tdMdl.getTdfName(), String.valueOf(tdMdl.getTdfSid())));
        }
        log__.debug("labelList.size()=>" + labelList.size());
        return labelList;
    }

    /**
     * <br>[機  能] 検索キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param keyword キーワード
     * @return List in String
     */
    public List<String> setKeyword(String keyword) {
        List < String > keywordList = new ArrayList < String >();
        String searchKey = StringUtil.substitute(keyword, "　", " ");
        StringTokenizer st = new StringTokenizer(searchKey, " ");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (!StringUtil.isNullZeroString(str)) {
                keywordList.add(str);
            }
        }
        return keywordList;
    }

    /**
     * プラグインが使用可能か判定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginId プラグインID
     * @param pconfig プラグイン設定
     * @return boolean true:使用可能 false:使用不可
     */
    public boolean isCanUsePlugin(String pluginId, PluginConfig pconfig) {
        for (String plugin : pconfig.getPluginIdList()) {
            if (pluginId == null || plugin.equals(pluginId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] セッション保持時間を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return int セッション保持時間
     * @throws SQLException SQL実行時例外
     */
    public int getSessionTime(Connection con) throws SQLException {

        CmnContmDao dao = new CmnContmDao(con);
        int sessionTime = dao.getSessionTime();
        if (sessionTime < 0) {
            sessionTime = GSConst.SESSION_TIME;

            CmnContmModel model = dao.select();
            if (model != null) {
                boolean commitFlg = false;
                try {
                    //コントロールマスタに情報が無い場合は、登録する。
                    model.setCntPxyUse(GSConstMain.PROXY_SERVER_NOT_USE);
                    model.setCntSessionTime(GSConst.SESSION_TIME);
                    model.setCntMenuStatic(GSConstMain.MENU_STATIC_NOT_USE);
                    dao.insert(model);
                    con.commit();
                    commitFlg = true;
                } catch (SQLException e) {
                    log__.error("SQLException", e);
                    throw e;
                } finally {
                    if (!commitFlg) {
                        JDBCUtil.rollback(con);
                    }
                }
            }
        }
        return sessionTime * 60;
    }

    /**
     * <br>[機  能]拡張子からブラウザで表示する画像ファイルか判定を行う
     * <br>[解  説] true:表示,false:非表示
     * <br>[備  考] jpeg,jpg,gif,pngを対象とする
     * @param ext 拡張子
     * @return boolean true:表示,false:非表示
     */
    public static boolean isViewFile(String ext) {
        if (".gif".equals(ext)
                || ".jpeg".equals(ext)
                || ".jpg".equals(ext)
                || ".png".equals(ext)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] WEB検索の使用可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig PluginConfig
     * @return 判定結果 0:使用可能 or 1:使用不可
     */
    public static int getWebSearchUse(PluginConfig pconfig) {
        return GSConst.PLUGIN_USE;
    }

    /**
     * <br>[機  能] ユーザ情報プラグインの使用可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig PluginConfig
     * @return 判定結果 0:使用可能 or 1:使用不可
     */
    public static int getUserPluginUse(PluginConfig pconfig) {
        int pluginUse = GSConst.PLUGIN_NOT_USE;
        if (pconfig.getPlugin(GSConst.PLUGINID_USER) != null) {
            pluginUse = GSConst.PLUGIN_USE;
        }

        return pluginUse;
    }

    /**
     * <br>[機  能] アドレス帳プラグインの使用可否を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig PluginConfig
     * @return 判定結果 0:使用可能 or 1:使用不可
     */
    public static int getAddressPluginUse(PluginConfig pconfig) {
        int pluginUse = GSConst.PLUGIN_NOT_USE;
        if (pconfig.getPlugin("address") != null) {
            pluginUse = GSConst.PLUGIN_USE;
        }

        return pluginUse;
    }

    /**
     * <br>[機  能] ヘッダ文字列からcharsetを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param contentType ヘッダ文字列
     * @return charset
     */
    public static String getHeaderCharset(String contentType) {
        if (StringUtil.isNullZeroString(contentType)) {
            return contentType;
        }

        String charset = "";
        int csIdx = contentType.trim().toLowerCase().indexOf("charset=");
        if (csIdx > 0) {
            charset = contentType.substring(csIdx + 8);
            charset = charset.replace("\"", "");

            String[] separatorList = {";", " ", "\r", "\n"};
            for (String separator : separatorList) {
                csIdx = charset.indexOf(separator);
                if (csIdx > 0) {
                    charset = charset.substring(0, csIdx);
                    break;
                }
            }
            separatorList = null;
        }

        return charset;
    }

    /**
     * 共通部 WARN ERRORのシステムログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutSystemLog(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            String value) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        //エラーログを画面表示用に作成・セット
        Object oerror = reqMdl.getOerror();
        if (oerror != null) {
            Throwable terror = (Throwable) oerror;
            StringBuilder ebuf = new StringBuilder();

            ebuf.append(terror.toString());
            value = ebuf.toString();
        }

        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("cmn.sys.public");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
        logMdl.setLogPluginName(sys);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(sys);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(
                StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);

        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * 共通部　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutCommonLog(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            String value) {
        outPutCommonLog(map, reqMdl, gsMsg, con, opCode, level, value, null);
    }

    /**
     * 共通部　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     */
    public void outPutCommonLog(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            String value,
            String fileId) {

        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("cmn.sys.public");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
        logMdl.setLogPluginName(sys);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(
                StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con);

        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * 共通部　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param valueList 内容
     * @param fileId 添付ファイルID
     */
    public void outPutCommonLog(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            List<String> valueList,
            String fileId) {

        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("cmn.sys.public");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        List<CmnLogModel> logMdlList = new ArrayList<CmnLogModel>();
        for (String value : valueList) {
            CmnLogModel logMdl = new CmnLogModel();
            logMdl.setLogDate(now);
            logMdl.setUsrSid(usrSid);
            logMdl.setLogLevel(level);
            logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
            logMdl.setLogPluginName(sys);
            String type = map.getType();
            logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
            logMdl.setLogPgName(getPgName(type));
            logMdl.setLogOpCode(opCode);
            logMdl.setLogOpValue(
                    StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
            logMdl.setLogIp(reqMdl.getRemoteAddr());
            logMdl.setVerVersion(GSConst.VERSION);
            if (fileId != null) {
                logMdl.setLogCode("binSid：" + fileId);
            }
            logMdlList.add(logMdl);
        }

        LoggingBiz logBiz = new LoggingBiz(con);

        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdlList, domain);
    }

    /**
     * 共通部　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param dspName 画面名
     */
    public void outPutLogNoDspName(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            String value,
            String dspName) {

        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("cmn.sys.public");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
        logMdl.setLogPluginName(sys);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(dspName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(
                StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);

        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * 共通部　個別ログ出力を行う
     * @param pgid バッチプログラムID
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param domain ドメイン
     */
    public void outPutBatchLog(
            String pgid,
            Connection con,
            String opCode,
            String level,
            String value,
            String domain) {

        int usrSid = -1;
        GsMessage gsMsg = new GsMessage();
        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("cmn.sys.public", null);

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
        logMdl.setLogPluginName(sys);
        logMdl.setLogPgId(pgid);
        logMdl.setLogPgName(getPgName(pgid));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(
                StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
        logMdl.setLogIp(null);
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * リスナークラスのログ出力を行う
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param pgName 画面・機能名
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutListenerLog(
            HttpServletRequest req,
            RequestModel reqMdl,
            Connection con,
            int usid,
            String pgId,
            String pgName,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 共通 **/
        String common = gsMsg.getMessage("cmn.sys.public");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_COMMON);
        logMdl.setLogPluginName(common);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(pgName);
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(req.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * システム共通API全般のログ出力を行う
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutCommonApiLog(
            RequestModel reqMdl,
            Connection con,
            int usid,
            String pgId,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //プラグイン名 システム共通
        String pluginName = gsMsg.getMessage("cmn.sys.public");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstCommon.PLUGIN_ID_COMMON);
        logMdl.setLogPluginName(pluginName);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getApiPgName(pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        log__.info("プログラムID==>" + id);

        GsMessage gsMsg = new GsMessage();
        String textKojin = gsMsg.getMessage("cmn.preferences2", null);
        String textKanri = gsMsg.getMessage("cmn.admin.setting", null);

        if (id.equals("jp.groupsession.v2.batch.DayJob")) {
            //日次バッチ
            String text = gsMsg.getMessage("cmn.batch.day", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.ManBatchBackupListenerImpl")) {
            //自動バックアップ処理
            String text = gsMsg.getMessage("main.man081.1", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn001.Cmn001Action")) {
            //ログイン
            String text = gsMsg.getMessage("cmn.login", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn004.Cmn004Action")) {
            //ワンタイムパスワード
            String text = gsMsg.getMessage("cmn.onetimepassword", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn130.Cmn130Action")) {
            //マイグループ設定
            String text = gsMsg.getMessage("main.man030.8", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn131.Cmn131Action")) {
            //マイグループ登録/編集確認
            String text = gsMsg.getMessage("cmn.mygroup.add", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn131kn.Cmn131knAction")) {
            //マイグループ登録/編集確認
            String text = gsMsg.getMessage("cmn.mygroup.add", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn140.Cmn140Action")) {
            //メニュー項目の設定
            String text = gsMsg.getMessage("main.man030.5", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn150.Cmn150Action")) {
            //メイン画面表示設定
            String text = gsMsg.getMessage("cmn.setting.main.view2", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn160kn.Cmn160knAction")) {
            //企業情報登録確認
            String text = gsMsg.getMessage("cmn.cmn160kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn170.Cmn170Action")) {
            //テーマ設定
            String text = gsMsg.getMessage("main.man030.10", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn250.Cmn250Action")) {
            //OAuth認証情報管理
            String text = gsMsg.getMessage("cmn.cmn250.01", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn260kn.Cmn260knAction")) {
            //OAuth認証情報登録確認
            String text = gsMsg.getMessage("cmn.cmn260kn.01", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn320.Cmn320Action")) {
            //表示設定
            String text = gsMsg.getMessage("cmn.display.settings", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn350.Cmn350Action")) {
            //表示設定
            String text = gsMsg.getMessage("cmn.display.settings", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn340.Cmn340Action")) {
            return textKanri + " " + gsMsg.getMessage("cmn.cmn340.01")
                             + gsMsg.getMessage("cmn.entry");
        }
        if (id.equals("jp.groupsession.v2.cmn.cmn390.Cmn390Action")) {
            return textKanri + " " + gsMsg.getMessage("cmn.cmn390.01");
        }
        if (id.equals("jp.groupsession.v2.man.man020.Man020Action")) {
            //休日設定
            String text = gsMsg.getMessage("main.holiday.setting", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man021.Man021Action")) {
            //休日設定/追加
            String text = gsMsg.getMessage("main.man021.3", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man022kn.Man022knAction")) {
            //休日削除確認
            String text = gsMsg.getMessage("main.man022kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man023.Man023Action")) {
            //休日テンプレート一覧
            String text = gsMsg.getMessage("main.man023.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man024kn.Man024knAction")) {
            //休日テンプレート追加確認
            String text = gsMsg.getMessage("main.holiday.template.add.kn", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man025.Man025Action")) {
            //休日テンプレート追加/通常
            String text = gsMsg.getMessage("main.holiday.template.add", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man026.Man026Action")) {
            //休日テンプレート追加/拡張
            String text = gsMsg.getMessage("main.holiday.template.add.ex", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man027kn.Man027knAction")) {
            //休日テンプレート削除確認
            String text = gsMsg.getMessage("main.holiday.template.del", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man040.Man040Action")) {
            //バッチ処理起動時間設定
            String text = gsMsg.getMessage("main.man002.34", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man040kn.Man040knAction")) {
            //バッチ処理起動時間設定確認
            String text = gsMsg.getMessage("main.man002.34", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man070kn.Man070knAction")) {
            //プロキシサーバ設定確認
            String text = gsMsg.getMessage("main.man070kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man080.Man080Action")) {
            //自動バックアップ設定
            String text = gsMsg.getMessage("cmn.autobackup.setting", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man080kn.Man080knAction")) {
            //自動バックアップ設定確認
            String text = gsMsg.getMessage("main.man080kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man081.Man081Action")) {
            //手動バックアップ設定
            String text = gsMsg.getMessage("man.backup.configuration.manual", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man090.Man090Action")) {
            //アプリケーションログ一覧
            String text = gsMsg.getMessage("main.man002.57", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man100.Man100Action")) {
            //役職マネージャー
            String text = gsMsg.getMessage("main.man002.26", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man110.Man110Action")) {
            //役職登録
            String text = gsMsg.getMessage("cmn.entry.position", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man110kn.Man110knAction")) {
            //役職登録確認
            String text = gsMsg.getMessage("cmn.entry.position.kn", null);
            return textKanri + " " + text;
        }

        if (id.equals("jp.groupsession.v2.man.man112.Man112Action")) {
            //役職インポート
            String text = gsMsg.getMessage("main.man112.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man112kn.Man112knAction")) {
            //役職インポート確認
            String text = gsMsg.getMessage("main.man112kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man120.Man120Action")) {
            //プラグインマネージャー
            String text = gsMsg.getMessage("main.man002.19", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man130kn.Man130knAction")) {
            //添付ファイル設定確認
            String text = gsMsg.getMessage("main.man130kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man140kn.Man140knAction")) {
            //セッション保持時間設定確認
            String text = gsMsg.getMessage("main.man140kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man150kn.Man150knAction")) {
            //ライセンスファイル登録・更新確認
            String text = gsMsg.getMessage("main.man150.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man160kn.Man160knAction")) {
            //モバイルID・パスワード設定確認
            String text = gsMsg.getMessage("main.man160kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man180kn.Man180knAction")) {
            //ログイン履歴自動削除設定
            String text = gsMsg.getMessage("main.man180.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man190kn.Man190knAction")) {
            //ログイン履歴手動削除
            String text = gsMsg.getMessage("main.manualdelete.login.history", null);
            return textKanri + " " + text;
        }

        if (id.equals("jp.groupsession.v2.man.man200kn.Man200knAction")) {
            //パスワードルール設定確認
            String text = gsMsg.getMessage("main.man200kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man210kn.Man210knAction")) {
            //モバイル使用一括設定確認
            String text = gsMsg.getMessage("main.man210kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man220.Man220Action")) {
            //グループ・ユーザ並び順設定
            String text = gsMsg.getMessage("main.grp.usr.sort.setting", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man230.Man230Action")) {
            //システム情報確認
            String text = gsMsg.getMessage("main.man002.67", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man240kn.Man240knAction")) {
            //オペレーションログ設定確認
            String text = gsMsg.getMessage("main.man240kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man250.Man250Action")) {
            //オペレーションログ検索
            String text = gsMsg.getMessage("main.man002.53", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man260kn.Man260knAction")) {
            //オペレーションログ自動削除設定確認
            String text = gsMsg.getMessage("main.man260kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man270kn.Man270knAction")) {
            //ログイン設定確認
            String text = gsMsg.getMessage("main.man270kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man290.Man290Action")) {
            //インフォメーション登録
            String text = gsMsg.getMessage("main.man290.1", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man290kn.Man290knAction")) {
            //インフォメーション登録確認
            String text = gsMsg.getMessage("main.man290kn.1", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man300.Man300Action")) {
            //インフォメーション管理者設定
            String text = gsMsg.getMessage("man.info.admin.settings", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man300kn.Man300knAction")) {
            //インフォメーション管理者設定確認
            String text = gsMsg.getMessage("man.info.admin.settings.kn", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man310.Man310Action")) {
            //インフォメーション詳細
            String text = gsMsg.getMessage("main.man310.1", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man320.Man320Action")) {
            //インフォメーション一覧
            String text = gsMsg.getMessage("main.man320.1", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man330.Man330Action")) {
            //所属情報一括設定
            String text = gsMsg.getMessage("main.memberships.conf", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man330kn.Man330knAction")) {
            //所属情報一括設定確認
            String text = gsMsg.getMessage("main.memberships.conf.kn", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.man.man350kn.Man350knAction")) {
            //管理者設定 メイン画面レイアウト設定確認
            String text = gsMsg.getMessage("main.man350kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.man.man360kn.Man360knAction")) {
            //個人設定 メイン画面レイアウト設定確認
            String text = gsMsg.getMessage("main.man350kn.1", null);
            return textKojin + " " +  text;
        }
        if (id.equals("jp.groupsession.v2.man.man500kn.Man500knAction")) {
            //管理者設定 個人情報編集権限設定確認
            String text = gsMsg.getMessage("main.man500kn.1", null);
            return textKanri + " " +  text;
        }
        if (id.equals("jp.groupsession.v2.man.man510kn.Man510knAction")) {
            //管理者設定 ワンタイムパスワード設定確認
            String text = gsMsg.getMessage("main.man510kn.1", null);
            return textKanri + " " +  text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr010.Usr010Action")) {
            //グループマネージャー
            String text = gsMsg.getMessage("user.44", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr011kn.Usr011knAction")) {
            //グループマネージャー(追加/編集)確認
            String text = gsMsg.getMessage("user.usr011kn.2", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr013.Usr013Action")) {
            //グループインポート
            String text = gsMsg.getMessage("user.usr013.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr013kn.Usr013knAction")) {
            //グループインポート確認
            String text = gsMsg.getMessage("user.usr013kn.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr030.Usr030Action")) {
            //ユーザマネージャー
            String text = gsMsg.getMessage("main.man002.24", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr031kn.Usr031knAction")) {
            //ユーザマネージャー(追加/修正)確認
            String text = gsMsg.getMessage("user.usr031kn.2", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr032.Usr032Action")) {
            //ユーザインポート
            String text = gsMsg.getMessage("user.usr032.3", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr032kn.Usr032knAction")) {
            //ユーザインポート確認
            String text = gsMsg.getMessage("user.usr032kn.8", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr033.Usr033Action")) {
            //ユーザ一括削除
            String text = gsMsg.getMessage("user.usr033.1", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr033kn.Usr033knAction")) {
            //ユーザ一括削除確認
            String text = gsMsg.getMessage("user.usr033kn.1", null);
            return textKanri + " " + text;
        }

        if (id.equals("jp.groupsession.v2.usr.usr050.Usr050Action")) {
            //パスワード変更
            String text = gsMsg.getMessage("cmn.change.password", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr060.Usr060Action")) {
            //パスワード変更
            String text = gsMsg.getMessage("cmn.change.password", null);
            return textKojin + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr061.Usr061Action")) {
            //パスワード変更
            String text = gsMsg.getMessage("cmn.change.password", null);
            return textKanri + " " + text;
        }
        if (id.equals("jp.groupsession.v2.usr.usr045.Usr045Action")) {
            return textKojin + " " + gsMsg.getMessage("cmn.category.entry");
        }
        if (id.equals("jp.groupsession.v2.usr.usr046.Usr046Action")) {
            return textKojin + " " + gsMsg.getMessage("cmn.entry.label");
        }
        return ret;
    }
    /**
     * 「APIの」プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getApiPgName(String id) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        log__.info("プログラムID==>" + id);

        GsMessage gsMsg = new GsMessage();

        if (id.equals("jp.groupsession.v2.cmn.restapi.users.authentications.CmnAuthenticationsAction")) {
            //API ログインする (表示名は"ログインAPI")
            return gsMsg.getMessage("cmn.login.api");
        }

        return ret;
    }
    /**
     * [機  能] システムのプラグイン情報を取得します。<br>
     * [解  説] <br>使用可などの情報を持ちません
     * [備  考] <br>
     * @param reqMdl リクエストモデル
     * @return PluginConfig
     */
    public PluginConfig getPluginConfig(RequestModel reqMdl) {

        PluginConfig pconfig = null;
        pconfig = GroupSession.getResourceManager().getPluginConfig(reqMdl);
        if (pconfig == null) {
            log__.fatal("プラグインコンフィグの取得に失敗");
        }
        return pconfig;
    }
    /**
     * 管理者設定の使用するプラグイン設定を反映したPluginConfigを取得します。
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    public PluginConfig getPluginConfigForMain(Connection con, RequestModel reqMdl)
    throws SQLException {
        PluginConfig pconfig = getPluginConfig(reqMdl);
        if (pconfig == null) {
            return pconfig;
        }
        return getPluginConfigForMain(pconfig, con);
    }
    /**
     * [機  能] ユーザの使用するプラグイン設定を反映したPluginConfigを取得します。<br>
     * [解  説] 指定されたユーザが使用不可のプラグインは除外します<br>
     * [備  考] <br>
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userSid ユーザSID
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    public PluginConfig getPluginConfigForUser(Connection con, RequestModel reqMdl, int userSid)
    throws SQLException {
        PluginConfig pconfig = getPluginConfig(reqMdl);
        if (pconfig == null) {
            return pconfig;
        }

        PluginConfig ret = new PluginConfig();

        CommonBiz cmnBiz = new CommonBiz();
        List<String> menuPluginIdList = cmnBiz.getCanUsePluginIdList(con, userSid);

        if (!menuPluginIdList.isEmpty()) {
            List<String> allowMblAppIdList = cmnBiz.getAllowPluginIdForMblApp(con, reqMdl);
            Plugin plugin = null;
            for (String pId : menuPluginIdList) {
                if (allowMblAppIdList == null || allowMblAppIdList.contains(pId)) {
                    plugin = pconfig.getPlugin(pId);
                    if (plugin != null) {
                        ret.addPlugin(plugin);
                    }
                }
            }
        }
        return ret;
    }
    /**
     * 管理者設定の使用するプラグイン設定を反映したPluginConfigを取得します。
     * @param pconfig プラグイン設定
     * @param con コネクション
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    public PluginConfig getPluginConfigForMain(PluginConfig pconfig, Connection con)
    throws SQLException {

        PluginConfig ret = new PluginConfig();
        //管理者設定を取得
        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<String> menuPluginIdList = tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);

        Plugin plugin = null;
        for (String pId : menuPluginIdList) {
            plugin = pconfig.getPlugin(pId);
            if (plugin != null) {
                ret.addPlugin(plugin);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したユーザが使用可能なプラグインのプラグインIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return プラグインID
     * @throws SQLException SQL実行時例外
     */
    public List<String> getCanUsePluginIdList(Connection con, int userSid)
    throws SQLException {

        //管理者設定を取得
        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<String> canUsePluginIdList = tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);

        if (userSid > 0) {
            canUsePluginIdList = getPluginIdWithoutControl(con, canUsePluginIdList, userSid);
        }

        return canUsePluginIdList;
    }

    /**
     * 指定されたプラグインIDからプラグイン使用制限で使用不可のプラグインIDを除外する
     * @param con コネクション
     * @param pluginIdList プラグインID
     * @param userSid ユーザSID
     * @return プラグインID
     * @throws SQLException SQL実行時例外
     */
    public List<String> getPluginIdWithoutControl(Connection con, List<String> pluginIdList,
                                                int userSid)
    throws SQLException {
        List<String> controlList = new ArrayList<String>();
        controlList.addAll(pluginIdList);

        //グループ・ユーザ指定のプラグインID一覧を取得する
        CmnPluginControlDao controlDao = new CmnPluginControlDao(con);
        List<CmnPluginControlModel> limitPluginList
                = controlDao.getMemberControlPluginList(pluginIdList);
        List<String> limitPluginIdList = new ArrayList<String>();

        //プラグインIDリストを作成
        if (!limitPluginList.isEmpty()) {
            for (CmnPluginControlModel ctrlModel : limitPluginList) {
                limitPluginIdList.add(ctrlModel.getPctPid());
            }
        }

        //アクセス権設定を行っているプラグインIDを取得
        CmnPluginControlMemberDao memberDao = new CmnPluginControlMemberDao(con);
        List<String> setteiList = memberDao.getCantUsePluginList(limitPluginIdList, userSid);

        boolean setteiFlg = false;
        if (!limitPluginList.isEmpty()) {
            for (CmnPluginControlModel ctrlModel : limitPluginList) {

                //設定存在フラグ
                setteiFlg = false;
                for (String setPlugin : setteiList) {
                    if (setPlugin.equals(ctrlModel.getPctPid())) {
                        setteiFlg = true;
                        break;
                    }
                }

                if (ctrlModel.getPctType() == GSConstMain.PCT_TYPE_PERMIT) {
                    if (setteiFlg) {
                        //制限ユーザに指定されている場合
                        controlList.remove(ctrlModel.getPctPid());
                    }
                } else {
                    if (!setteiFlg) {
                        //許可ユーザに指定されていない場合
                        controlList.remove(ctrlModel.getPctPid());
                    }

                }
            }
        }

        return controlList;
    }

    /**
     * <br>[機  能] 指定したプラグインが使用可能なユーザを取得します。
     * <br>[解  説]
     * <br>[備  考] 引数のユーザSID内に重複がある場合でも、プラグイン使用可能ユーザには重複が無い状態で結果を返します。
     * @param con コネクション
     * @param pluginId プラグインID
     * @param userSidList ユーザSID
     * @return 指定したユーザSIDのうち、プラグインが使用可能なユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getCanUsePluginUser(Connection con, String pluginId,
                                            List<Integer> userSidList)
    throws SQLException {

        List<Integer> retList = new ArrayList<Integer>();

        //プラグイン使用制限が未登録 or 使用制限区分 = 全てのユーザが使用可能 の場合
        //指定されたユーザSIDをそのまま返す
        CmnPluginControlDao ctrDao = new CmnPluginControlDao(con);
        CmnPluginControlModel ctrData = ctrDao.select(pluginId);
        if (ctrData == null || ctrData.getPctKbn() == GSConstMain.PCT_KBN_ALLOK) {
            return userSidList;
        }

        //入力されたユーザSIDの重複を取り除く
        if (userSidList != null) {
            userSidList = userSidList.stream()
                .distinct()
                .collect(Collectors.toList());
        }

        //設定されているユーザSID一覧を取得する。
        CmnPluginControlMemberDao memberDao = new CmnPluginControlMemberDao(con);
        List<Integer> memList = memberDao.getCantUseUserList(pluginId, userSidList);


        if (ctrData.getPctType() == GSConstMain.PCT_TYPE_PERMIT) {
            //制限方法が許可の場合
            retList.addAll(userSidList);
            for (Integer userId : memList) {
                retList.remove(userId);
            }

        } else {
            //制限方法が制限の場合
            retList.addAll(memList);
        }

        return retList;

    }
    /**
     * <br>[機  能] 指定したプラグインが使用不可能なユーザを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param pluginId プラグインID
     * @param userSidList ユーザSID
     * @return 指定したユーザSIDのうち、プラグインが使用可能なユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getCantUsePluginUser(Connection con, String pluginId,
                                            List<Integer> userSidList)
    throws SQLException {

        List<Integer> retList = new ArrayList<Integer>();

        //プラグイン使用制限が未登録 or 使用制限区分 = 全てのユーザが使用可能 の場合
        //指定されたユーザSIDをそのまま返す
        CmnPluginControlDao ctrDao = new CmnPluginControlDao(con);
        CmnPluginControlModel ctrData = ctrDao.select(pluginId);
        if (ctrData == null || ctrData.getPctKbn() == GSConstMain.PCT_KBN_ALLOK) {
            return retList;
        }

        //設定されているユーザSID一覧を取得する。
        CmnPluginControlMemberDao memberDao = new CmnPluginControlMemberDao(con);
        List<Integer> memList = memberDao.getCantUseUserList(pluginId, userSidList);


        if (ctrData.getPctType() == GSConstMain.PCT_TYPE_PERMIT) {
            //制限方法が制限の場合
            retList.addAll(memList);

        } else {
            //制限方法が制限の場合
            retList.addAll(userSidList);
            for (Integer userId : memList) {
                retList.remove(userId);
            }
        }

        return retList;

    }

    /**
     * <br>[機  能] 戻るボタン押下時の戻り先画面を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエスト情報
     * @param pconfig プラグイン情報
     * @return トップページURL
     */
    public ActionForward getBackUrl(ActionMapping map, RequestModel reqMdl, PluginConfig pconfig) {

        BaseUserModel umodel = reqMdl.getSmodel();
        String backPluginId = umodel.getBackPluginId();
        if (StringUtil.isNullZeroString(backPluginId)) {
            return map.findForward("back");
        }

        String backUrl = __getPluginTopUrl(pconfig, backPluginId);
        return new RedirectingActionForward(backUrl);
    }

    /**
     * <br>[機  能] 指定したプラグインのトップページURLを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig プラグインコンフィグ
     * @param pluginId プラグインID
     * @return トップページURL
     */
    private String __getPluginTopUrl(PluginConfig pconfig, String pluginId) {

        Plugin plugin = pconfig.getPlugin(pluginId);
        if (plugin == null || plugin.getTopMenuInfo() == null
                || StringUtil.isNullZeroString(plugin.getTopMenuInfo().getUrl())) {
            plugin = pconfig.getPlugin(GSConst.PLUGINID_MAIN);
        }
        String topUrl = plugin.getTopMenuInfo().getUrl().replace("..", "");
        return topUrl;
    }

    /**
     * <br>[機  能] ショートメールが使用可能なユーザを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID
     * @return 指定したユーザのうち、ショートメールが使用可能なユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getCanUseSmailUser(Connection con, List<Integer> userSidList)
    throws SQLException {
        return getCanUsePluginUser(con, GSConstMain.PLUGIN_ID_SMAIL, userSidList);
    }

    /**
     * <br>[機  能] チャットが使用可能なユーザを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID
     * @return 指定したユーザのうち、ショートメールが使用可能なユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getCanUseChatUser(Connection con, List<Integer> userSidList)
    throws SQLException {
        return getCanUsePluginUser(con, GSConstMain.PLUGIN_ID_CHAT, userSidList);
    }

    /**
     * <br>[機  能] 回覧板が使用可能なユーザを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID
     * @return 指定したユーザのうち、ショートメールが使用可能なユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getCanUseCircularUser(Connection con, List<Integer> userSidList)
    throws SQLException {
        return getCanUsePluginUser(con, GSConstMain.PLUGIN_ID_CIRCULAR, userSidList);
    }

    /**
     * <p>バックアップリスナー実装クラスのリストを返す
     * @param pluginConfig プラグイン情報
     * @param con コネクション
     * @throws ClassNotFoundException 指定されたバックアップリスナークラスが存在しない
     * @throws IllegalAccessException バックアップリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException バックアップリスナー実装クラスのインスタンス生成に失敗
     * @throws SQLException 管理者設定 - プラグイン設定の取得に失敗
     * @return バッチリスナー
     */
    public IBatchBackupListener[] getBackupBatchListeners(PluginConfig pluginConfig, Connection con)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        List<IBatchBackupListener> batchList = new ArrayList<IBatchBackupListener>();

        //バックアップバッチリスナーを取得する
        PluginConfig pconfig = getPluginConfigForMain(pluginConfig, con);
        String[] backupBatchListenerClass = pconfig.getBackupBatchListeners();
        for (String listenerClass : backupBatchListenerClass) {
            Object obj = ClassUtil.getObject(listenerClass);
            batchList.add((IBatchBackupListener) obj);
        }

        return (IBatchBackupListener[]) batchList.toArray(
                        new IBatchBackupListener[batchList.size()]);
    }
    /**
     * <p>トップメニュー情報実装クラスのリストを返す
     * @param pluginConfig プラグイン情報
     * @param con コネクション
     * @throws ClassNotFoundException 指定されたトップメニュー情報リスナークラスが存在しない
     * @throws IllegalAccessException トップメニュー情報実装クラスのインスタンス生成に失敗
     * @throws InstantiationException トップメニュー情報実装クラスのインスタンス生成に失敗
     * @throws SQLException 管理者設定 - プラグイン設定の取得に失敗
     * @return バッチリスナー
     */
    public ITopMenuInfo[] getIMenuInfo(PluginConfig pluginConfig, Connection con)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        List<ITopMenuInfo> topMenuList = new ArrayList<ITopMenuInfo>();

        //バックアップバッチリスナーを取得する
        PluginConfig pconfig = getPluginConfigForMain(pluginConfig, con);
        String[] topMenuInfoClass = pconfig.getTopMenuInfoImpl();
        for (String listenerClass : topMenuInfoClass) {
            Object obj = ClassUtil.getObject(listenerClass);
            topMenuList.add((ITopMenuInfo) obj);
        }
        return (ITopMenuInfo[]) topMenuList.toArray(
                new ITopMenuInfo[topMenuList.size()]);
    }
    /**
     * プラグインIDを指定してアイコンのURLを取得します。
     * @param pluginId プラグインID
     * @param domain ドメイン
     * @return アイコンのURL
     */
    public String getPluginIconUrl(String pluginId, String domain) {

        String defaultIcon = "../main/images/menu_icon_single_info.gif";
        IGsResourceManager resourceManager = GroupSession.getResourceManager();
        PluginConfig pconf = resourceManager.getPluginConfig(domain);
        Plugin plugin = pconf.getPlugin(pluginId);
        AdminSettingInfo admInfo = plugin.getAdminSettingInfo();
        PrivateSettingInfo priInfo = plugin.getPrivateSettingInfo();
        String admIcon = admInfo.getIconClassic();
        String priIcon = priInfo.getIconClassic();
        if (!StringUtil.isNullZeroString(admIcon)) {
            return admIcon;
        }
        if (!StringUtil.isNullZeroString(priIcon)) {
            return priIcon;
        }
        return defaultIcon;
    }

    /**
     * <br>[機  能] 指定したユーザがプラグインの管理者かを判定します。
     * <br>[解  説]
     * <br>[備  考] システム管理グループに所属するユーザもプラグインの管理者として扱う
     * @param con コネクション
     * @param umodel セッションユーザ情報
     * @param pluginId プラグインID
     * @return true:プラグイン管理者 false:一般ユーザ
     * @throws SQLException SQL実行時例外
     */
    public boolean isPluginAdmin(Connection con, BaseUserModel umodel, String pluginId)
    throws SQLException {
        if (umodel.isAdmin()) {
            return true;
        } else if (StringUtil.isNullZeroString(pluginId)
                    || pluginId.equals(GSConst.PLUGINID_MAIN)) {
            return false;
        }

        return isPluginAdmin(con, umodel.getUsrsid(), pluginId);
    }


    /**
     * <br>[機  能] 指定したユーザがプラグインの管理者かを判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID ユーザSID
     * @param pluginId プラグインID
     * @return true:プラグイン管理者 false:一般ユーザ
     * @throws SQLException SQL実行時例外
     */
    public boolean isPluginAdmin(Connection con, int userSid, String pluginId)
    throws SQLException {

        CmnPluginAdminDao dao = new CmnPluginAdminDao(con);
        int count = dao.getCountPluginAdmin(userSid, pluginId);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] APサーバ複数台の場合は、オペレーションログ設定を更新する。
     * <br>[解  説] DBの設定からGSContextを更新する。
     * <br>[備  考]
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     */
    public void setOperationLogGsContext(Connection con,
                                         String domain) throws SQLException {

        if (CommonBiz.isMultiAP()) {
            //オペレーションログの再設定
            LoggingConfig loggingConfig = new LoggingConfig();

            CmnLogConfDao dao = new CmnLogConfDao(con);
            ArrayList<CmnLogConfModel> logConfList = dao.select();

            if (logConfList == null || logConfList.size() < 1) {
                GroupSession.getResourceManager().setLoggingConfig(domain, loggingConfig);
                return;
            }

            for (CmnLogConfModel model : logConfList) {
                loggingConfig.addLogConf(model);
            }

            //GSコンテキストへ設定
            GroupSession.getResourceManager().setLoggingConfig(domain, loggingConfig);
        }
    }

    /**
     * <br>[機  能] BatchStatusを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return BatchStatus
     */
    public static String getBatchStatus() {
        return NullDefault.getString(GsResourceBundle.getString(GSConst.BATCH_STATUS), "");
    }

    /**
     * <br>[機  能] Multi APの状態を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return Multi APの状態
     */
    public static boolean isMultiAP() {
        return NullDefault.getString(
                GsResourceBundle.getString(GSConst.MULTIAP), "").equals(GSConst.MULTIAP_MULTI);
    }

    /**
     * <br>[機  能] APサーバ番号を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return APサーバ番号の状態
     */
    public static int getApNumber() {
        if (!isMultiAP()) {
            return 0;
        }
        return NullDefault.getInt(
                GsResourceBundle.getString(GSConst.AP_NUMBER), 0);
    }

    /**
     * <br>[機  能] IPアドレス取得条件の状態を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return IPアドレス取得条件
     */
    public static boolean isIpAdrState() {
        return NullDefault.getString(
                GsResourceBundle.getString(
                        GSConst.IPADRSTATUS), "").equals(GSConst.IPADRSTATUS_TRUE);
    }

    /**
     * <br>[機  能] PGroongaの状態(使用 or 未使用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return BatchStatus
     */
    public static boolean isPGroongaUse() {
        return NullDefault.getString(
                GsResourceBundle.getString(GSConst.PGROONGA), "")
                .equals(GSConst.PGROONGA_USE_TRUE);
    }

    /**
     * <br>[機  能] バッチリスナー実装クラスのリストを返す
     * <br>[解  説]
     * <br>[備  考] 管理者設定の使用するプラグインのリスナーのみ取得します。
     * @param pluginConfig プラグイン情報
     * @param con コネクション
     * @throws ClassNotFoundException 指定されたバッチリスナークラスが存在しない
     * @throws IllegalAccessException バッチリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException バッチリスナー実装クラスのインスタンス生成に失敗
     * @throws SQLException SQL実行例外
     * @return バッチリスナー
     */
    public IBatchListener[] getBatchListeners(PluginConfig pluginConfig, Connection con)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        log__.debug("getBatchListeners START");

        List<IBatchListener> batchList = new ArrayList<IBatchListener>();
        String batchStatus = CommonBiz.getBatchStatus();

        CommonBiz cmnBiz = new CommonBiz();
        PluginConfig pconfig = cmnBiz.getPluginConfigForMain(pluginConfig, con);

        String[] batchListenerClass = new String[0];
        //バッチリスナーを取得する
        if (batchStatus.equals(GSConst.BATCH_STATUS_TRUE)) {
            batchListenerClass = pconfig.getBatchListeners();
        } else if (batchStatus.equals(GSConst.BATCH_STATUS_LIMITATION)) {
            List<Plugin> pluginList = pconfig.getPluginDataList();
            List<String> batchListenerClassList = new ArrayList<String>();
            for (Plugin plugin : pluginList) {
                if (plugin.getBatchInfo().isLimitation()) {
                    batchListenerClassList.addAll(
                            Arrays.asList(pconfig.getBatchListeners(plugin.getId())));
                }
            }

            batchListenerClass
                = (String[]) batchListenerClassList.toArray(
                                                    new String[batchListenerClassList.size()]);
        }

        //バッチリスナーに「ユーザ自動連係バッチ」を追加
        int batchCnt = batchListenerClass.length;
        batchListenerClass = Arrays.copyOf(batchListenerClass, batchCnt + 1);
        batchListenerClass[batchCnt] = "jp.groupsession.v2.usr.AutoImportListnerImpl";

        for (int i = 0; i < batchListenerClass.length; i++) {
            Object obj = ClassUtil.getObject(batchListenerClass[i]);
            batchList.add((IBatchListener) obj);
            log__.debug("BatchListener class = " + batchListenerClass[i]);
        }

        log__.debug("__getBatchListeners END");
        return (IBatchListener[]) batchList.toArray(new IBatchListener[batchList.size()]);
    }

    /**
     * <br>[機  能] メインインフォーメーションメッセージをセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg Gsメッセージ
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param usModel ユーザ情報
     * @param pconfig プラグイン設定情報
     * @param context コンテキスト
     * @return インフォーメーションメッセージリスト
     * @throws Exception インフォーメーション取得クラスの設定ミスの場合にスロー
     */
    public ArrayList<MainInfoMessageModel> getInfoMessageList(
            GsMessage gsMsg, RequestModel reqMdl,
            Connection con, BaseUserModel usModel,
            PluginConfig pconfig, ServletContext context) throws Exception {
        //        __setMainInfoMessage(con, usModel, pconfig, form);
        String [] pifclss = pconfig.getMainInfoMessageImpl();
        MainInfoMessage[] info = null;
        try {
            info = __getMainInfoMessages(pifclss);
        } catch (ClassNotFoundException e) {
            log__.error("クラスが見つかりません。設定を見直してください。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("クラスの設定が間違っています。設定を見直してください。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("クラスの設定が間違っています。設定を見直してください。", e);
            throw e;
        }

        //
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("realPath", context.getRealPath("/"));

        ArrayList<MainInfoMessageModel> msgs = new ArrayList<MainInfoMessageModel>();
        for (MainInfoMessage imsgCls : info) {
            List<MainInfoMessageModel> plist
                    = imsgCls.getMessage(paramMap, usModel.getUsrsid(), con, gsMsg, reqMdl);
            if (plist != null) {
                msgs.addAll(plist);
            }
        }

        //メインインフォメーション詳細POPUPURL
        String infoPopupUrl = "return openMainInfoWindow";

        //手動登録インフォメーションを取得
        CmnInfoMsgBiz biz = new CmnInfoMsgBiz();
        ArrayList<CmnInfoMsgModel> infoList = biz.getActiveInformationMsg(
                usModel.getUsrsid(), new UDate(), con);
        MainInfoMessageModel model = null;
        for (CmnInfoMsgModel infMdl : infoList) {
            model = new MainInfoMessageModel();
            model.setPluginId(GSConst.PLUGINID_MAIN);
            model.setPluginName(gsMsg.getMessage("main.man031.4"));
            model.setLinkUrl(infoPopupUrl + "(" + infMdl.getImsSid() + ")");
            model.setMessage(infMdl.getImsMsg());
            model.setOriginalMessage(infMdl.getImsMsg());
            model.setPopupDsp(true);
            model.setIcon("../main/images/classic/menu_icon_single_info.gif");
            msgs.add(model);
        }

        return msgs;
    }

    /**
     * <p>メッセージのリストを取得する
     * @param classNames プラグインクラス名
     * @throws ClassNotFoundException 指定されたクラスが存在しない
     * @throws IllegalAccessException 実装クラスのインスタンス生成に失敗
     * @throws InstantiationException 実装クラスのインスタンス生成に失敗
     * @return バッチリスナー
     */
    private MainInfoMessage[] __getMainInfoMessages(String[] classNames)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MainInfoMessage[] iclasses = new MainInfoMessage[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            Object obj = ClassUtil.getObject(classNames[i]);
            iclasses[i] = (MainInfoMessage) obj;
        }
        return iclasses;
    }

    /**
     * <br>[機  能] ポータル管理者権限があるか判定する。
     * <br>[解  説]
     * <br>[備  考] ポータルプラグインが使用可能かつシステム管理者かプラグイン管理者ならばtrue
     * @param con コネクション
     * @param usModel ユーザモデル
     * @param pconfig プラグイン設定
     * @throws SQLException SQL実行例外
     * @return バッチリスナー
     */
    public boolean isPortalAdmin(Connection con, BaseUserModel usModel, PluginConfig pconfig)
    throws SQLException {

        boolean adminFlg = false;

        //ポータルプラグインが使用可能ならばtrue
        if (isCanUsePlugin(GSConstMain.PLUGIN_ID_PORTAL, pconfig)) {
            //システム管理者かプラグイン管理者ならばtrue
            adminFlg = isPluginAdmin(con, usModel, GSConstMain.PLUGIN_ID_PORTAL);
        }
        return adminFlg;
    }

    /**
     * [機  能] プラグインポートレット選択画面のコンボボックスを取得する。
     * [解  説] 指定されたユーザが使用不可のプラグインは除外します<br>
     * [備  考] <br>
     * @param con コネクション
     * @param gsMsg GSメッセージ
     * @param domain ドメイン
     * @return PluginConfig
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getPluginPortletCombo(
            Connection con, GsMessage gsMsg, String domain)
    throws SQLException {
        List<LabelValueBean> combo = new ArrayList<LabelValueBean>();
        //ポータル プラグイン選択画面はデフォルトとして設定する。
        combo.add(new LabelValueBean(gsMsg.getMessage("plugin.portlet"),
                                    "ptl080"));

        IGsResourceManager resourceManager = GroupSession.getResourceManager();
        PluginConfig pconfig = resourceManager.getPluginConfig(domain);
        List<String> pluginIdList = getCanUsePluginIdList(con, -1);

        if (!pluginIdList.isEmpty()) {
            List<PortletInfo> portletList = new ArrayList<PortletInfo>();
            Plugin plugin = null;
            for (String pId : pluginIdList) {
                plugin = pconfig.getPlugin(pId);
                if (plugin != null) {
                    portletList.addAll(plugin.getPortletInfo());
                }
            }

            Collections.sort(portletList);
            for (PortletInfo portlet : portletList) {
                combo.add(new LabelValueBean(gsMsg.getMessage(portlet.getListNameId()),
                        portlet.getListId()));
            }
        }

        return combo;
    }

    /**
     * [機  能] プラグインポートレットの画面IDを取得する。
     * [解  説] <br>
     * [備  考] <br>
     * @param pconfig PluginConfig
     * @param pluginId プラグインID
     * @param screenId 選択画面ID
     * @return dspScreenId ポートレット画面ID
     */
    public String getPluginPortletScreenId(
            PluginConfig pconfig, String pluginId, String screenId) {

        String dspScreenId = "";

        ArrayList<PortletInfo> portletInfoList = pconfig.getPlugin(pluginId).getPortletInfo();

        for (PortletInfo pInfo : portletInfoList) {
            String listId = pInfo.getListId();
            if (!StringUtil.isNullZeroStringSpace(listId)) {
                if (listId.equals(screenId)) {
                    dspScreenId = pInfo.getId();
                    break;
                }
            }
        }

        return dspScreenId;
    }

    /**
     * <br>[機  能] Group SESSION biz URL を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return Group SESSION biz URL
     */
    public static String getBizUrl(RequestModel reqMdl) {
        String bizUrl = ConfigBundle.getValue("GS_BIZ_URL");

        // GroupSessionへアクセスしているURLのプロトコルを取得
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        String protocol = urlBiz.getScheme(reqMdl);


        // servername.confから取得したBizのアドレスを判定
        int idx = bizUrl.indexOf(GSConst.BIZ_URL);
        if (idx > 0) {
            bizUrl = bizUrl.substring(idx);
        }
        // GroupSessionへのアクセスに利用しているプロトコルよりBizのURLを作成
        bizUrl = protocol + "://" + bizUrl;

        if (!bizUrl.endsWith("/")) {
            bizUrl += "/";
        }
        return bizUrl;
    }

    /**
     * <br>[機  能] 拡張日付を取得する
     * <br>[解  説]
     * <br>[備  考] "日" = 末日の場合、月の末日を設定する
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 拡張日付
     */
    public static UDate createExDate(String year, String month, String day) {
        return createExDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    /**
     * <br>[機  能] 拡張日付を取得する
     * <br>[解  説]
     * <br>[備  考] "日" = 末日の場合、月の末日を設定する
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 拡張日付
     */
    public static UDate createExDate(int year, int month, int day) {
        UDate date = new UDate();
        date.setDate(year, month, 1);
        date.setDay(getExDay(date, day));
        return date;
    }

    /**
     * <br>[機  能] 拡張日付 日を取得する
     * <br>[解  説]
     * <br>[備  考] "日" = 末日の場合、月の末日を返す
     * @param date 拡張日付
     * @param day 日
     * @return 拡張日付 日
     */
    public static int getExDay(UDate date, int day) {
        if (day == GSConstCommon.LAST_DAY_OF_MONTH) {
            return date.getMaxDayOfMonth();
        }
        return day;
    }

    /**
     * <br>[機  能] 拡張日付 日を取得する
     * <br>[解  説]
     * <br>[備  考] "日" = 末日の場合、月の末日を返す
     * @param date 拡張日付
     * @param day 日
     * @return 拡張日付 日
     */
    public static String getExDay(UDate date, String day) {
        String exDay = NullDefault.getString(day, "");
        if (exDay.equals(Integer.toString(GSConstCommon.LAST_DAY_OF_MONTH))) {
            exDay = Integer.toString(date.getMaxDayOfMonth());
        }
        return exDay;
    }

    /**
     * <br>[機  能] マルチアカウント 登録者コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @return 登録者コンボ
     * @throws SQLException SQL実行時例外
     */
    public List<UsrLabelValueBean> getMARegistrantCombo(Connection con, int userSid,
                                                                                RequestModel reqMdl)
    throws SQLException {
        List<UsrLabelValueBean> registList = new ArrayList<UsrLabelValueBean>();

        //ユーザを設定
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> userList
            = userBiz.getUserList(con, new String[] {Integer.toString(userSid)});
        String usrFullName = userList.get(0).getUsiSei() + "  " + userList.get(0).getUsiMei();
        UsrLabelValueBean usrSetting = new UsrLabelValueBean(usrFullName, "0");
        usrSetting.setUsrUkoFlg(userList.get(0).getUsrUkoFlg());
        registList.add(usrSetting);

        //ユーザの所属グループを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz grpBiz = new GroupBiz();
        registList.addAll(
                grpBiz.getBelongGroupLabelList(userSid, con, false, gsMsg));

        return registList;
    }

    /** KB */
    private static final long KB__ = 1024;
    /** MB */
    private static final long MB__ = (long) Math.pow(1024, 2);
    /** GB */
    private static final long GB__ = (long) Math.pow(1024, 3);
    /**
     * <br>[機  能] ディスクサイズの変換を行う
     * <br>[解  説] サイズに応じた単位を付加する
     * <br>[備  考]
     * @param diskSize ディスクサイズ
     * @return ディスクサイズ(換算)
     */
    public String convertDiskSize(long diskSize) {

        String strDiskSize = "";
        BigDecimal decDiskSize = new BigDecimal(diskSize);
        if (diskSize >= MB__  && diskSize < GB__) {
            strDiskSize
                    = decDiskSize.divide(new BigDecimal(MB__), 1,
                                            BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        } else if (diskSize >= GB__ && diskSize >= GB__) {
            strDiskSize
                    = decDiskSize.divide(new BigDecimal(GB__), 1,
                                            BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        } else {
            strDiskSize
            = decDiskSize.divide(new BigDecimal(KB__), 0,
                                        BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        return strDiskSize + "B";
    }

    /**
     * <br>[機  能] アクセス元のIPアドレスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return メッセージ
     */
    public static String getRemoteAddr(HttpServletRequest req) {

        String remoteAddr = req.getRemoteAddr();

        if (CommonBiz.isIpAdrState()) {
            remoteAddr = req.getHeader("x-forwarded-for");

            //取得できない場合は通常の取得処理
            if (StringUtil.isNullZeroStringSpace(remoteAddr)) {
                remoteAddr = req.getRemoteAddr();
            }

        } else {
            remoteAddr = req.getRemoteAddr();
        }

        return remoteAddr;
    }

    /**
     * <br>[機  能] 指定したバイナリSIDがユーザ画像のバイナリSIDか、また
     *                    その画像が公開されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @return true:ユーザ画像のbinSid false:それ以外
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckUserImage(Connection con, Long binSid, int usrSid) throws SQLException {

        CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
        return dao.isCheckUserImage(binSid, usrSid);
    }

    /**
     *
     * <br>[機  能] リダイレクト先のURLがWEB-INF以下のファイルを参照していないことを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param pconfig プラグインコンフィグ
     * @param url リダイレクト先
     * @return 正常:urlからのforward 不正:エラー画面forward
     */
    public ActionForward saveForwardUrl(ActionMapping map, PluginConfig pconfig, String url) {
        //エラー画面を設定
        ActionForward forward = map.findForward("gf_submit");

        //有効なプラグインIDかチェックする
        List < String > pList = pconfig.getPluginIdList();
        boolean existPid = false;
        String checkUrl = url;
        if (url.startsWith("/")) {
            checkUrl = checkUrl.substring(1);
        }
        for (String pStr : pList) {
            if (checkUrl.startsWith(pStr)) {
                existPid = true;
            }
        }
        if (!existPid) {
            //エラー画面へ遷移
            return forward;
        }
        if (!checkUrl.matches("[A-Za-z0-9_.=&?/]*")) {
            //エラー画面へ遷移
            return forward;
        }
        if (checkUrl.indexOf("..") != -1) {
            //エラー画面へ遷移
            return forward;
        }
        //urlがmap.getPath()と前方一致していないか
        if (url.startsWith(map.getPath())) {
            //エラー画面へ遷移
            return forward;
        }

        //正常なurl
        forward = new ActionForward(url);
        return forward;
    }

    /**
     *
     * <br>[機  能] リダイレクト先のURLがWEB-INF以下のファイルを参照していないことを確認する
     * <br>[解  説]
     * <br>[備  考] 遷移先URLのCMDパラメータと遷移元のCMDパラメータが一致していないことを確認する
     * @param map アクションマッピング
     * @param pconfig プラグインコンフィグ
     * @param url リダイレクト先
     * @param cmd コマンドパラメータ
     * @return 正常:urlからのforward 不正:エラー画面forward
     */
    public ActionForward saveForwardUrl(
            ActionMapping map, PluginConfig pconfig, String url, String cmd) {

        //エラー画面を設定
        ActionForward forward = map.findForward("gf_submit");

        //有効なプラグインIDかチェックする
        List < String > pList = pconfig.getPluginIdList();
        boolean existPid = false;
        String checkUrl = url;
        if (url.startsWith("/")) {
            checkUrl = checkUrl.substring(1);
        }
        for (String pStr : pList) {
            if (checkUrl.startsWith(pStr)) {
                existPid = true;
            }
        }
        if (!existPid) {
            //エラー画面へ遷移
            return forward;
        }
        if (!checkUrl.matches("[A-Za-z0-9_.=&?/]*")) {
            //エラー画面へ遷移
            return forward;
        }
        if (checkUrl.indexOf("..") != -1) {
            //エラー画面へ遷移
            return forward;
        }

        int cmdStartIdx = url.indexOf(GSConst.P_CMD) + (GSConst.P_CMD + "=").length();
        int cmdEndIdx = url.indexOf("&", cmdStartIdx);
        if (cmdEndIdx == -1) {
            cmdEndIdx = url.length();
        }
        String urlCmd = url.substring(cmdStartIdx, cmdEndIdx);

        cmd = NullDefault.getString(cmd, "");
        if (url.startsWith(map.getPath()) && cmd.equals(urlCmd)) {
            //エラー画面へ遷移
            return forward;
        }

        //正常なurl
        forward = new ActionForward(url);
        return forward;
    }

    /**
     * バイナリーデータの保存先ディレクトリPATHを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションROOT
     * @return String バイナリーデータの保存先ディレクトリPATH
     */
    public String getSsoKeyword(String appRootPath) {
        String keyword = null;
        if (ConfigBundle.getValue("SSO_KEYWORD") != null) {
            //設定ファイル(gsdata.conf)の指定ディレクトリ
            keyword = ConfigBundle.getValue("SSO_KEYWORD");
        }

        return keyword;
    }


    /**
     * <br>[機  能] 文字列内に${USERID}${PASSWORD}等の予約語があった場合に置換えを行なう
     * <br>[解  説]
     * <br>[備  考] urlEncFlg=true時、予約語で置き換わる文字のみURLエンコードを実行
     *
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param str 文字列
     * @param appRootPath アプリケーションルートパス
     * @param usrInfMdl ユーザ個人情報モデル
     * @param urlEncFlg URLエンコードフラグ  true:エンコードする  false:しない
     * @return メニュー情報
     * @throws SQLException SQL実行例外
     * @throws EncryptionException 暗号化に失敗時例外
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     * @throws UnsupportedEncodingException 文字エンコード時例外
     */
    public String replaceGSReservedWord(
            RequestModel reqMdl,
            Connection con,
            String appRootPath,
            String str,
            CmnUsrmInfModel usrInfMdl,
            boolean urlEncFlg)
                    throws SQLException, EncryptionException,
                    NoSuchAlgorithmException, UnsupportedEncodingException {

        //置き換え１
        String repStr = replaceGSReservedWordNoTime(
                reqMdl, con, appRootPath, str, usrInfMdl, urlEncFlg);

        //置き換え２（TIME、HASHのみ）
        repStr = replaceGSReservedWordOnlyTime(
                reqMdl, con, appRootPath, repStr);

        return repStr;

    }

    /**
     * <br>[機  能] タイムスタンプを必要としない予約語を置き換える
     * <br>[解  説]
     * <br>[備  考] urlEncFlg=true時、予約語で置き換わる文字のみURLエンコードを実行
     *
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param str 文字列
     * @param appRootPath アプリケーションルートパス
     * @param usrInfMdl ユーザ個人情報モデル
     * @param urlEncFlg URLエンコードフラグ  true:エンコードする  false:しない
     * @return メニュー情報
     * @throws SQLException SQL実行例外
     * @throws EncryptionException 暗号化に失敗時例外
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     * @throws UnsupportedEncodingException 文字エンコード時例外
     */
    public String replaceGSReservedWordNoTime(
            RequestModel reqMdl,
            Connection con,
            String appRootPath,
            String str,
            CmnUsrmInfModel usrInfMdl,
            boolean urlEncFlg)
                    throws SQLException, EncryptionException,
                    NoSuchAlgorithmException, UnsupportedEncodingException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = usModel.getUsrsid(); //セッションユーザSID

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (str.indexOf("${USERID}") != -1) {
            //ユーザsidを取得
            if (usrSid > 0) {
                str = str.replace("${USERID}", usModel.getLgid());
            }
        }
        if (str.indexOf("${PASSWORD}") != -1) {
            //LDAPプラグインの使用を判定し、パスワードを取得する
            boolean ldapFlg = false;
            CmnTdispDao dispDao = new CmnTdispDao(con);
            List<CmnTdispModel> dispList = dispDao.getAdminTdispList();
            for (CmnTdispModel tdispMdl : dispList) {
                //LDAPプラグインを使用している場合はパスワードを取得しない
                if (tdispMdl.getTdpPid().equals(LdapConst.PROTOCOL_LDAP)) {
                    ldapFlg = true;
                }
            }
            if (!ldapFlg) {
                //パスワードを取得
                String pass = null;
                CmnUsrmDao udao = new CmnUsrmDao(con);
                CmnUsrmModel sumodel = udao.select(usrSid);
                pass = GSPassword.getDecryPassword(sumodel.getUsrPswd());
                if (!StringUtil.isNullZeroString(pass)) {
                    str = str.replace("${PASSWORD}", pass);
                }
            }
        }

        if (str.indexOf("${GROUPSID}") != -1) {
            //デフォルトグループSIDを取得
            if (usrSid > 0) {
                GroupBiz gb = new GroupBiz();
                int grpSid = gb.getDefaultGroupSid(usrSid, con);
                str = str.replace("${GROUPSID}", String.valueOf(grpSid));
            }
        }

        if (str.indexOf("${GROUPID}") != -1) {
            //デフォルトグループIDを取得
            if (usrSid > 0) {
                GroupBiz gb = new GroupBiz();
                String grpId = gb.getDefaultGroupId(usrSid, con);
                str = str.replace("${GROUPID}", NullDefault.getString(grpId, ""));
            }
        }

        if (usrInfMdl != null) {
            //社員番号
            if (str.indexOf("${SYAINNO}") != -1) {
                str = str.replace("${SYAINNO}",
                        __urlEncode(
                                NullDefault.getString(usrInfMdl.getUsiSyainNo(), ""), urlEncFlg));
            }

            //氏名（姓名）
            if (str.indexOf("${NAME}") != -1) {
                str = str.replace("${NAME}",
                        __urlEncode(usrInfMdl.getUsiSei() + usrInfMdl.getUsiMei(), urlEncFlg));
            }

            //氏名（姓のみ）
            if (str.indexOf("${SEI}") != -1) {
                str = str.replace("${SEI}", __urlEncode(usrInfMdl.getUsiSei(), urlEncFlg));
            }

            //氏名（名のみ）
            if (str.indexOf("${MEI}") != -1) {
                str = str.replace("${MEI}",
                        __urlEncode(usrInfMdl.getUsiMei(), urlEncFlg));
            }

            //氏名カナ(姓名)
            if (str.indexOf("${NAMEKANA}") != -1) {
                str = str.replace("${NAMEKANA}",
                        __urlEncode(usrInfMdl.getUsiSeiKn() + usrInfMdl.getUsiMeiKn(), urlEncFlg));
            }

            //氏名カナ(姓のみ)
            if (str.indexOf("${SEIKANA}") != -1) {
                str = str.replace("${SEIKANA}", __urlEncode(usrInfMdl.getUsiSeiKn(), urlEncFlg));
            }

            //氏名カナ(名のみ)
            if (str.indexOf("${MEIKANA}") != -1) {
                str = str.replace("${MEIKANA}", __urlEncode(usrInfMdl.getUsiMeiKn(), urlEncFlg));
            }

            //所属
            if (str.indexOf("${SYOZOKU}") != -1) {
                str = str.replace("${SYOZOKU}",
                        __urlEncode(
                                NullDefault.getString(usrInfMdl.getUsiSyozoku(), ""), urlEncFlg));
            }

            //役職
            if (str.indexOf("${POSITION}") != -1) {
                int posSid = usrInfMdl.getPosSid();
                CmnPositionDao posDao = new CmnPositionDao(con);
                CmnPositionModel posMdl = posDao.getPosInfo(posSid);
                if (posMdl != null) {
                    str = str.replace("${POSITION}",
                            __urlEncode(posMdl.getPosName(), urlEncFlg));
                } else {
                    str = str.replace("${POSITION}", "");
                }
            }

            //性別
            if (str.indexOf("${SEIBETU}") != -1) {

                int seibetu = usrInfMdl.getUsiSeibetu();
                String strSeibetu = null;
                if (seibetu == GSConstUser.SEIBETU_MAN) {
                    strSeibetu = gsMsg.getMessage("user.124");
                } else if (seibetu == GSConstUser.SEIBETU_WOMAN) {
                    strSeibetu = gsMsg.getMessage("user.125");
                } else {
                    strSeibetu = gsMsg.getMessage("cmn.notset");
                }

                str = str.replace("${SEIBETU}", __urlEncode(strSeibetu, urlEncFlg));
            }

            //入社年月日(西暦)
            if (str.indexOf("${EDATE}") != -1) {
                if (usrInfMdl.getUsiEntranceDate() != null) {
                    str = str.replace("${EDATE}", usrInfMdl.getUsiEntranceDate().getDateString());
                } else {
                    str = str.replace("${EDATE}", "");
                }
            }

            //生年月日(西暦)
            if (str.indexOf("${BDATE}") != -1) {
                if (usrInfMdl.getUsiBdate() != null) {
                    str = str.replace("${BDATE}", usrInfMdl.getUsiBdate().getDateString());
                } else {
                    str = str.replace("${BDATE}", "");
                }
            }

            //メールアドレス１
            if (str.indexOf("${MAIL1}") != -1) {
                str = str.replace("${MAIL1}", NullDefault.getString(usrInfMdl.getUsiMail1(), ""));
            }

            //メールアドレス２
            if (str.indexOf("${MAIL2}") != -1) {
                str = str.replace("${MAIL2}", NullDefault.getString(usrInfMdl.getUsiMail2(), ""));
            }

            //メールアドレス３
            if (str.indexOf("${MAIL3}") != -1) {
                str = str.replace("${MAIL3}", NullDefault.getString(usrInfMdl.getUsiMail3(), ""));
            }

            //住所 郵便番号（ハイフン付き）
            if (str.indexOf("${ZIP}") != -1) {
                str = str.replace("${ZIP}",
                        NullDefault.getString(usrInfMdl.getUsiZip1(), "")
                        + "-"
                        + NullDefault.getString(usrInfMdl.getUsiZip2(), ""));
            }

            //住所１
            if (str.indexOf("${ADDR1}") != -1) {
                str = str.replace("${ADDR1}",
                        __urlEncode(
                                NullDefault.getString(usrInfMdl.getUsiAddr1(), ""), urlEncFlg));
            }

            //住所２
            if (str.indexOf("${ADDR2}") != -1) {
                str = str.replace("${ADDR2}",
                        __urlEncode(
                                NullDefault.getString(usrInfMdl.getUsiAddr2(), ""), urlEncFlg));
            }

            //電話番号１
            if (str.indexOf("${TEL1}") != -1) {
                str = str.replace("${TEL1}",
                        NullDefault.getString(usrInfMdl.getUsiTel1(), ""));
            }

            //電話番号２
            if (str.indexOf("${TEL2}") != -1) {
                str = str.replace("${TEL2}",
                        NullDefault.getString(usrInfMdl.getUsiTel2(), ""));
            }

            //電話番号３
            if (str.indexOf("${TEL3}") != -1) {
                str = str.replace("${TEL3}",
                        NullDefault.getString(usrInfMdl.getUsiTel3(), ""));
            }

            //ＦＡＸ１
            if (str.indexOf("${FAX1}") != -1) {
                str = str.replace("${FAX1}",
                        NullDefault.getString(usrInfMdl.getUsiFax1(), ""));
            }

            //ＦＡＸ２
            if (str.indexOf("${FAX2}") != -1) {
                str = str.replace("${FAX2}",
                        NullDefault.getString(usrInfMdl.getUsiFax2(), ""));
            }

            //ＦＡＸ３
            if (str.indexOf("${FAX3}") != -1) {
                str = str.replace("${FAX3}",
                        NullDefault.getString(usrInfMdl.getUsiFax3(), ""));
            }
        }

        return str;
    }

    /**
     * <br>[機  能] タイムスタンプを必要とするGS予約語を置き換える。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param str 文字列
     * @param appRootPath アプリケーションルートパス
     * @return メニュー情報
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     */
    public String replaceGSReservedWordOnlyTime(
            RequestModel reqMdl,
            Connection con,
            String appRootPath,
            String str)
                    throws NoSuchAlgorithmException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();

        //キーワード
        CommonBiz cmnBiz = new CommonBiz();
        String keyword = cmnBiz.getSsoKeyword(appRootPath);

        //タイムスタンプ（取得時時間）
        UDate now = new UDate();
        if (str.indexOf("${TIME}") != -1) {
            str = str.replace("${TIME}", now.getTimeStamp());
        }

        //ハッシュ( ユーザSID＋タイムスタンプ＋キーワード の連結文字列をSHA-512で暗号化したもの）
        if (str.indexOf("${HASH_UID_TM_KW}") != -1) {

            String hashkey = usModel.getLgid()
                    + now.getTimeStamp()
                    + NullDefault.getString(keyword, "");

            str = str.replace("${HASH_UID_TM_KW}", Sha.encryptSha512AsHex(hashkey));
        }

        return str;
    }

    /**
     * <br>[機  能] 指定文字をURLエンコードする
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字
     * @param encFlg エンコードフラグ true:エンコードする  false:しない
     * @return エンコード文字列
     * @throws UnsupportedEncodingException 文字エンコード時例外
     */
    private String __urlEncode(String str, boolean encFlg) throws UnsupportedEncodingException {
        if (encFlg) {
            str = URLEncoder.encode(str, Encoding.UTF_8);
        }

        return str;
    }

    /**
     * <br>[機  能] 基本URLを取得する。
     * <br>[解  説] スキーマ＋サーバー名 or IPアドレス (+ポート番号)＋コンテキストパスを取得する。
     *                    例) http://localhost:8080/gsession/
     * <br>[備  考] 最後尾に / (バックスラッシュ)有り
     * @param reqMdl リクエストモデル
     * @return SqlBuffer
     */
    public String getBaseUrl(RequestModel reqMdl) {

        String threadUrl = null;
        String url = null;



        if (!StringUtil.isNullZeroString(reqMdl.getReferer())) {
            //リファラ―が存在する場合はリファラ―からURLを取得
            url = reqMdl.getReferer();

            //基本URLに整形する
            threadUrl = url.substring(0, url.lastIndexOf("/"));
            threadUrl = threadUrl.substring(0, threadUrl.lastIndexOf("/"));
            threadUrl += "/";

        } else {
            //リクエストURLからURLを取得
            url = reqMdl.getRequestURL().toString();

            //基本URLに整形する
            threadUrl = url.substring(0, url.lastIndexOf("/"));
            threadUrl = threadUrl.substring(0, threadUrl.lastIndexOf("/"));
            threadUrl += "/";

            //基本のドメインでない場合、ドメインを追加する
            if (!reqMdl.getDomain().equals(GSConst.GS_DOMAIN)) {
                threadUrl += reqMdl.getDomain() + "/";
            }
        }

        return threadUrl;
    }

    /**
     *
     * <br>[機  能] 現在時までの時間文字列を返す
     * <br>[解  説]
     * <br>[備  考] 日本語のみ
     * @param time 開始時間
     * @return 時間文字列
     */
    public String getExecBatchTimeString(long time) {
        time = System.currentTimeMillis() - time;
        time -= time % 1000;
        time = time / 1000;
        String receiveTime = time % 60 + "秒";
        if (time >= 60) {
            time -= time % 60;
            time = time / 60;
            receiveTime = time + "分" + receiveTime;
        }
        return receiveTime;
    }

    /**
     * <br>[機  能] ログイン処理の実装クラスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ログイン処理の実装クラス
     */
    public ILogin getLoginInstance() {
        String strLoginClass = GsResourceBundle.getString("ILogin");
        log__.info("LoginBiz is " + strLoginClass);
        ILogin loginBiz = null;

        if (strLoginClass != null) {
            try {
                loginBiz = (ILogin) Class.forName(strLoginClass).newInstance();
            } catch (InstantiationException e) {
                log__.error(e);
            } catch (IllegalAccessException e) {
                log__.error(e);
            } catch (ClassNotFoundException e) {
                log__.error(e);
            }
        } else {
            loginBiz = new GSLoginBiz();
        }

        return loginBiz;
    }

    /**
     * <br>[機  能] ユーザ情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userList 取得するユーザSID・グループSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<UsrLabelValueBean> getUserLabelList(Connection con, String[] userList)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (userList != null) {
            for (String userId : userList) {
                String str = NullDefault.getString(userId, "-1");
                log__.debug("str==>" + str);
                log__.debug("G.index==>" + str.indexOf("G"));
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }
        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        UsrLabelValueBean label = null;
        for (GroupModel gmodel : glist) {
            label = new UsrLabelValueBean();
            label.setLabel(gmodel.getGroupName());
            label.setValue("G" + String.valueOf(gmodel.getGroupSid()));
            ret.add(label);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con,
                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            label = new UsrLabelValueBean();
            label.setLabel(umodel.getUsiSei() + " " + umodel.getUsiMei());
            label.setValue(String.valueOf(umodel.getUsrSid()));
            label.setUsrUkoFlg(umodel.getUsrUkoFlg());
            ret.add(label);
        }

        return ret;
    }

    /**
     * <br>[機  能] GS初回起動時の時間を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 初回起動時間
     * @throws SQLException SQL実行例外
     */
    public UDate getInitialDate(Connection con) throws SQLException {

        CmnUsrmDao dao = new CmnUsrmDao(con);
        UDate initDate = dao.getInitialDate();
        UDate ret = NullDefault.getUDate(initDate, new UDate());
        return ret;
    }

    /**
     * <br>[機  能] 添付フォルダにあるファイルが画像かどうかチェックをする。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 判定結果
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 例外処理
     */
    public boolean checkTempPhotoFile(String tempDir)
          throws SQLException, IOToolsException {
        boolean ret = true;

        //テンポラリディレクトリにあるファイル名称を取得
        List <String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (String file : fileList) {

                if (!file.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, file);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

                //ファイル名がNULLまたは空の場合は処理しない
                if (fMdl.getFileName() != null && !fMdl.getFileName().equals("")) {
                    String strExt = StringUtil.getExtension(fMdl.getFileName());
                    if (strExt == null) {
                        ret = false;
                        break;
                    } else if (!strExt.toUpperCase().equals(EXTENSION_BMP)
                            && !strExt.toUpperCase().equals(EXTENSION_JPG)
                            && !strExt.toUpperCase().equals(EXTENSION_JPEG)
                            && !strExt.toUpperCase().equals(EXTENSION_GIF)
                            && !strExt.toUpperCase().equals(EXTENSION_PNG)) {
                        ret = false;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] システム情報モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param domain ドメイン
     * @param dbDirPath DBディレクトリパス
     * @param appRootPath アプリケーションルートパス
     * @param tempPath テンポラリディレクトリ
     * @param lmdl ライセンスモデル
     * @return システム情報
     * @throws Exception 実行例外
     */
    public String getSystemInfoReqParam(
            Connection con,
            String domain,
            String dbDirPath,
            String appRootPath,
            String tempPath,
            LicenseModel lmdl)
            throws Exception {

        GsMessage gsmsg = new GsMessage(Locale.JAPAN);
        CommonBiz cmnBiz = new CommonBiz();

        //GSシリアル番号
        StringBuilder sb = new StringBuilder();
        CmnContmDao cntDao = new CmnContmDao(con);
        String gsUid = cntDao.getGsUid();

        //登録日時
        sb.append("cliSysinfoModel.csiAdateStr=" + new UDate());

        if (!StringUtil.isNullZeroString(gsUid)) {
            sb.append("&cliSysinfoModel.csiGssn=" + LicenseOperation.getDecryString(gsUid));
        }

        if (lmdl != null) {
            //ライセンスID
            sb.append("&cliSysinfoModel.csiLiid=" + lmdl.getLicenseId());

            //契約区分
            String type = lmdl.getType();
            if (StringUtil.isNullZeroString(lmdl.getType())) {
                if (!StringUtil.isNullZeroString(lmdl.getLicenseFreeSid())) {
                    type = GSConstLicese.TYPE_FREE;
                }
            }
            sb.append("&cliSysinfoModel.csiType=" + type);

            //会社名
            if (!StringUtil.isNullZeroString(lmdl.getLicenseCom())) {
                sb.append("&cliSysinfoModel.csiCampany="
                        + URLEncoder.encode(lmdl.getLicenseCom(), Encoding.UTF_8));
            }

            //サポート期限
            //期限をセパレータで分解
            if (!StringUtil.isNullZeroString(lmdl.getLicenseLimitSupport())) {
                String[] splitSpLimitStr = lmdl.getLicenseLimitSupport().split("/");
                UDate spDate  = UDateUtil.getUDate2(
                        splitSpLimitStr[0], splitSpLimitStr[1], splitSpLimitStr[2]);
                spDate.setMaxHhMmSs();
                sb.append("&cliSysinfoModel.csiSplimitStr=" + spDate);
            }

            //GSモバイル期限
            if (!StringUtil.isNullZeroString(lmdl.getLicenseLimitMobile())) {
                String[] splitMbLimitStr = lmdl.getLicenseLimitMobile().split("/");
                UDate mbDate  = UDateUtil.getUDate2(
                        splitMbLimitStr[0], splitMbLimitStr[1], splitMbLimitStr[2]);
                mbDate.setMaxHhMmSs();
                sb.append("&cliSysinfoModel.csiMblimitStr=" + mbDate);
            }

            //CrossRide期限
            if (!StringUtil.isNullZeroString(lmdl.getLicenseLimitCrossRide())) {
                String[] splitCrLimitStr = lmdl.getLicenseLimitCrossRide().split("/");
                UDate crDate  = UDateUtil.getUDate2(
                        splitCrLimitStr[0], splitCrLimitStr[1], splitCrLimitStr[2]);
                crDate.setMaxHhMmSs();
                sb.append("&cliSysinfoModel.csiCrlimitStr=" + crDate);
            }
        }

        //登録済みユーザ数
        UserBiz userBiz = new UserBiz();
        int userNum = userBiz.getActiveUserCount(con);
        sb.append("&cliSysinfoModel.csiUsers=" + userNum);

        //GS利用開始日時
        UDate initDate = cmnBiz.getInitialDate(con);
        sb.append("&cliSysinfoModel.csiSdateStr=" + initDate);

        //使用中GSバージョン
        sb.append("&cliSysinfoModel.csiGsver=" + GSConst.VERSION);

        //OS名
        MainCommonBiz manBiz = new MainCommonBiz();
        sb.append("&cliSysinfoModel.csiOsname=" + manBiz.getServerOS());

        //CPUのコア数
        sb.append("&cliSysinfoModel.csiCpucore=" + manBiz.getServerCpuCore());

        //JVMビットモード
        sb.append("&cliSysinfoModel.csiJvmbit=" + manBiz.getServerJVM());

        //Javaバージョン
        sb.append("&cliSysinfoModel.csiJavaver=" + manBiz.getServerJava());

        //メモリ使用
        DecimalFormat format1 = new DecimalFormat("#,###MB");
        DecimalFormat format2 = new DecimalFormat("##.#");
        long free = Runtime.getRuntime().freeMemory() / (1024 * 1024);
        long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long used = max - free;
        double ratio = (used * 100 / (double) max);
        String usedMem = format1.format(used) + " (" + format2.format(ratio) + ")";
        sb.append("&cliSysinfoModel.csiUsedmem=" + usedMem);

        //メモリ最大
        sb.append("&cliSysinfoModel.csiMaxmem=" + format1.format(max));

        //空きディスク容量
        sb.append("&cliSysinfoModel.csiAdisc=" + manBiz.getServerFreeSpace(dbDirPath));

        //コネクション使用状況
        sb.append("&cliSysinfoModel.csiCon=" + __getServerConnection(domain));

        ResourceBundle conOp = ResourceBundle.getBundle("connectOption");
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2LockMode=" + conOp.getString("LOCK_MODE"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2LockTimeout=" + conOp.getString("LOCK_TIMEOUT"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2DefaultLockTimeout="
            + conOp.getString("DEFAULT_LOCK_TIMEOUT"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2MultiThreaded=" + conOp.getString("MULTI_THREADED"));
        //H2コネクション設定
        if (conOp.getString("IFEXISTS") != null) {
            sb.append("&cliSysinfoModel.csiH2Ifexists=" + conOp.getString("IFEXISTS"));
        }
        //H2コネクション設定
        if (conOp.getString("AUTOCOMMIT") != null) {
            sb.append("&cliSysinfoModel.csiH2Autocommit=" + conOp.getString("AUTOCOMMIT"));
        }
        //H2コネクション設定
        if (conOp.getString("DB_CLOSE_ON_EXIT") != null) {
            sb.append("&cliSysinfoModel.csiH2DbCloseOnExit=" + conOp.getString("DB_CLOSE_ON_EXIT"));
        }
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2CacheSize=" + conOp.getString("CACHE_SIZE"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2PageSize=" + conOp.getString("PAGE_SIZE"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2MaxLengthInplaceLob="
            + conOp.getString("MAX_LENGTH_INPLACE_LOB"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2CacheType=" + conOp.getString("CACHE_TYPE"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2Mvcc=" + conOp.getString("MVCC"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2QueryTimeout=" + conOp.getString("QUERY_TIMEOUT"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2QueryTimeoutBatch="
            + conOp.getString("QUERY_TIMEOUT_BATCH"));
        //H2コネクション設定
        sb.append("&cliSysinfoModel.csiH2MaxLogSize=" + conOp.getString("MAX_LOG_SIZE"));

        String gsdataDir = __pathNormalization(cmnBiz.getFileRootPath(appRootPath));
        sb.append("&cliSysinfoModel.csiGsdataDir="
                + URLEncoder.encode(NullDefault.getString(gsdataDir, ""), Encoding.UTF_8));
        //ディレクトリ情報(BACKUP_DIR)\
        String backupDir = __pathNormalization(CommonBiz.getBackupDirPath(appRootPath));
        sb.append("&cliSysinfoModel.csiBackupDir="
                + URLEncoder.encode(NullDefault.getString(backupDir, ""), Encoding.UTF_8));
        //ディレクトリ情報(FILEKANRI_DIR)
        String fileDir = __pathNormalization(cmnBiz.getFileRootPathForFileKanri(appRootPath));
        sb.append("&cliSysinfoModel.csiFilekanriDir="
                + URLEncoder.encode(NullDefault.getString(fileDir, ""), Encoding.UTF_8));
        //ディレクトリ情報(WEBMAIL_DIR)
        String wmlDir = __pathNormalization(cmnBiz.getFileRootPathForWebmail(appRootPath));
        sb.append("&cliSysinfoModel.csiWebmailDir="
                + URLEncoder.encode(NullDefault.getString(wmlDir, ""), Encoding.UTF_8));

        CmnBackupConfDao backConfDao = new CmnBackupConfDao(con);
        CmnBackupConfModel backConfMdl = backConfDao.select();

        String kankaku = null;
        String sedai = null;
        String syuturyoku = null;

        if (backConfMdl == null || backConfMdl.getBucInterval() == 0) {
            kankaku = gsmsg.getMessage("cmn.notset");
        } else {
            kankaku = __getKankaku(gsmsg, backConfMdl);
            sedai = backConfMdl.getBucGeneration() + gsmsg.getMessage("fil.6");

            if (backConfMdl.getBucZipout() == 0) {
                syuturyoku = gsmsg.getMessage("cmn.not.compress");
            } else {
                syuturyoku = gsmsg.getMessage("main.zip.format.output");
            }
        }

        //バックアップ間隔
        if (!StringUtil.isNullZeroString(kankaku)) {
            sb.append("&cliSysinfoModel.csiBackupInterval="
                    + URLEncoder.encode(kankaku, Encoding.UTF_8));
        }

        //バックアップ世代数
        if (!StringUtil.isNullZeroString(sedai)) {
            sb.append("&cliSysinfoModel.csiBackupGeneration="
                    + URLEncoder.encode(sedai, Encoding.UTF_8));
        }

        //バックアップ圧縮有無
        if (!StringUtil.isNullZeroString(syuturyoku)) {
            sb.append("&cliSysinfoModel.csiBackupZip="
                    + URLEncoder.encode(syuturyoku, Encoding.UTF_8));
        }

        //バッチ処理情報の取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        if (batMdl != null) {
            sb.append("&cliSysinfoModel.csiBatchjob=" + batMdl.getBatFrDate());
        }

        //テンポラリディレクトリパス
        sb.append("&cliSysinfoModel.csiTempdir="
                + URLEncoder.encode(NullDefault.getString(tempPath, ""), Encoding.UTF_8));
        //COMPLIANT_VERSION
        if (ConfigBundle.getValue("COMPLIANT_VERSION") != null) {
            sb.append("&cliSysinfoModel.csiCompliantVersion="
                + ConfigBundle.getValue("COMPLIANT_VERSION"));
        }
        //GS_MBA_IOS_VERSION
        if (ConfigBundle.getValue("GS_MBA_IOS_VERSION") != null) {
            sb.append("&cliSysinfoModel.csiGsMbaIosVersion="
                + ConfigBundle.getValue("GS_MBA_IOS_VERSION"));
        }
        //GS_CAL_IOS_VERSION
        if (ConfigBundle.getValue("GS_CAL_IOS_VERSION") != null) {
            sb.append("&cliSysinfoModel.csiGsCalIosVersion="
                + ConfigBundle.getValue("GS_CAL_IOS_VERSION"));
        }
        //GS_ADR_IOS_VERSION
        if (ConfigBundle.getValue("GS_ADR_IOS_VERSION") != null) {
            sb.append("&cliSysinfoModel.csiGsAdrIosVersion="
                + ConfigBundle.getValue("GS_ADR_IOS_VERSION"));
        }
        //GS_SML_IOS_VERSION
        if (ConfigBundle.getValue("GS_SML_IOS_VERSION") != null) {
            sb.append("&cliSysinfoModel.csiGsSmlIosVersion="
                + ConfigBundle.getValue("GS_SML_IOS_VERSION"));
        }
        //GS_WML_IOS_VERSION
        if (ConfigBundle.getValue("GS_WML_IOS_VERSION") != null) {
            sb.append("&cliSysinfoModel.csiGsWmlIosVersion="
                + ConfigBundle.getValue("GS_WML_IOS_VERSION"));
        }
        //FIL_ALL_SEARCH_USE
        sb.append("&cliSysinfoModel.csiFilAllSearchUse="
            + ConfigBundle.getValue("FIL_ALL_SEARCH_USE"));
        //MAIL_PORT_NUMBER
        sb.append("&cliSysinfoModel.csiMailPortNumber="
            + ConfigBundle.getValue("MAIL_PORT_NUMBER"));
        //PORTLET_MAXLENGTH
        sb.append("&cliSysinfoModel.csiPortletMaxlength="
            + ConfigBundle.getValue("PORTLET_MAXLENGTH"));
        //RSV_PRINT_USE
        sb.append("&cliSysinfoModel.csiRsvPrintUse=" + ConfigBundle.getValue("RSV_PRINT_USE"));
        //MAILRECEIVE_THREAD_MAXCOUNT
        sb.append("&cliSysinfoModel.csiMailreceiveThreadMaxcount="
                + ConfigBundle.getValue("MAILRECEIVE_THREAD_MAXCOUNT"));
        //MAILRECEIVE_MAXCOUNT
        sb.append("&cliSysinfoModel.csiMailreceiveMaxcount="
            + ConfigBundle.getValue("MAILRECEIVE_MAXCOUNT"));
        //MAILRECEIVE_CONNECTTIMEOUT
        sb.append("&cliSysinfoModel.csiMailreceiveConnecttimeout="
                + ConfigBundle.getValue("MAILRECEIVE_CONNECTTIMEOUT"));
        //MAILRECEIVE_TIMEOUT
        sb.append("&cliSysinfoModel.csiMailreceiveTimeout="
            + ConfigBundle.getValue("MAILRECEIVE_TIMEOUT"));
        //MAILRECEIVE_RCVSVR_CHECKTIME
        sb.append("&cliSysinfoModel.csiMailreceiveRcvsvrChecktime="
                + ConfigBundle.getValue("MAILRECEIVE_RCVSVR_CHECKTIME"));
        //MAILBODY_LIMIT
        sb.append("&cliSysinfoModel.csiMailbodyLimit=" + ConfigBundle.getValue("MAILBODY_LIMIT"));

        return sb.toString();
    }

    /**
     * <br>[機  能] システム情報モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @return システム情報
     * @throws SQLException SQL実行例外
     */
    public List <CliPluginModel> getPluginInfo(
            Connection con, PluginConfig pconfig) throws SQLException {

        List <CliPluginModel> ret = new ArrayList<>();
        CliPluginModel mdl = null;

        //トップ表示設定を取得
        CmnTdispDao dispDao = new CmnTdispDao(con);
        //管理者ユーザの登録値を取得(使用に設定されているプラグインの取得)
        List<CmnTdispModel> dispList = dispDao.getAdminTdispList();

        RequestModel reqMdl = new RequestModel();
        reqMdl.setLocale(Locale.JAPAN);

        //存在チェック用プラグインリスト
        ArrayList<String> checkList = new ArrayList<>();

        if (dispList != null || !dispList.isEmpty()) {
            for (CmnTdispModel dbDispMdl : dispList) {
                if (dbDispMdl.getTdpPid().equals("license")
                        || dbDispMdl.getTdpPid().equals(GSConst.PLUGINID_COMMON)
                        || dbDispMdl.getTdpPid().equals("help")) {
                    continue;
                }
                Plugin plugin = pconfig.getPlugin(dbDispMdl.getTdpPid());
                if (plugin != null) {

                    mdl = new CliPluginModel();
                    mdl.setCplPid(plugin.getId());
                    mdl.setCplPname(plugin.getName(reqMdl));
                    mdl.setCplUsekbn(GSConstMain.PLUGIN_USE);
                    ret.add(mdl);
                    checkList.add(plugin.getName(reqMdl));
                }
            }
        }
        //未使用に設定されているプラグインの取得
        for (Plugin plugin : pconfig.getPluginDataList()) {
            if (plugin != null
            && !plugin.getId().equals(GSConst.PLUGINID_COMMON)
            && !plugin.getId().equals("help")
            && !plugin.getId().equals("license")) {

                if (checkList.indexOf(plugin.getName(reqMdl)) == -1) {
                    mdl = new CliPluginModel();
                    mdl.setCplPid(plugin.getId());
                    mdl.setCplPname(plugin.getName(reqMdl));
                    mdl.setCplUsekbn(GSConstMain.PLUGIN_NOT_USE);
                    ret.add(mdl);
                    checkList.add(plugin.getName(reqMdl));
                }

            }
        }

        return ret;
    }

    /**
     * <br>[機  能] プラグイン毎のデータ使用量を元に、パラメータ文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @return パラメータ文字列
     * @throws Exception 実行例外
     */
    public String getDatausedParam(Connection con,
            PluginConfig pconfig) throws Exception {

        DataUsedSizeBiz dataUsedBiz = new DataUsedSizeBiz(con);
        List<CmnDatausedModel> dataUsedList = dataUsedBiz.getDataUsedList();
        long sum = dataUsedBiz.getTotalByteSize();
        StringBuilder sb = new StringBuilder();

        //プラグインIDとデータ使用量のMap
        Map<String, CmnDatausedModel> dataUsedMap
            = dataUsedList.stream().collect(
                Collectors.toMap(
                    dataUsed -> dataUsed.getCduPlugin()
                    ,dataUsed -> dataUsed
                    ,(a, b) -> a));

        //「プラグイン毎のデータ使用量」を元に、パラメータ文字列を作成
        pconfig.getPluginDataList()
        .stream()
        .filter(plugin ->
            //対象プラグインのデータ使用量が未登録の場合、パラメータに含めない
            dataUsedMap.containsKey(plugin.getId())
        ).forEach(p -> {
            String psId = p.getShortId();
            if (!StringUtil.isNullZeroString(psId)) {
                sb.append("&cliDatausedModel.cdu");
                sb.append(psId.substring(0, 1).toUpperCase());
                sb.append(psId.substring(1));
                sb.append("=" + dataUsedMap.get(p.getId()).getCduSize());
            }
        });

        sb.append("&cliDatausedModel.cduSum=" + sum);

        return sb.toString();
    }

    /**
     *
     * <br>[機  能]パスの正規化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param path 各ディレクトリのパス
     * @return 正規化を行ったパス
     */
    private String __pathNormalization(String path) {

        //ファイルセパレートを全てスラッシュに変換
        path = IOTools.replaceSlashFileSep(path);

        //スラッシュが余分についていた場合は取り除く
        if (path.endsWith("//")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * <br>[機  能] バックアップ情報より間隔（文字）を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsmsg GSメッセージ
     * @param backConfMdl バックアップモデル
     * @return 間隔情報
     */
    private String __getKankaku(GsMessage gsmsg, CmnBackupConfModel backConfMdl) {

        //曜日
        String kankaku = null;
        String dow = null;
        if (backConfMdl.getBucInterval() != 1) {
            switch (backConfMdl.getBucDow()) {
            case (1):
                dow = gsmsg.getMessage("cmn.sunday3");
            break;
            case (2):
                dow = gsmsg.getMessage("cmn.monday3");
            break;
            case (3):
                dow = gsmsg.getMessage("cmn.tuesday3");
            break;
            case (4):
                dow = gsmsg.getMessage("cmn.wednesday3");
            break;
            case (5):
                dow = gsmsg.getMessage("cmn.thursday3");
            break;
            case (6):
                dow = gsmsg.getMessage("main.src.man080.7");
            break;
            case (7):
                dow = gsmsg.getMessage("cmn.saturday3");
            break;
            default:
                break;
            }
        }

        switch (backConfMdl.getBucInterval()) {
        case (1):
            kankaku = gsmsg.getMessage("cmn.everyday");
        break;
        case (2):
            kankaku = gsmsg.getMessage("cmn.weekly2") + dow;
        break;
        case (3):
            kankaku = gsmsg.getMessage("cmn.monthly.2")
            + gsmsg.getMessage("main.src.man025.3")
            + backConfMdl.getBucWeekMonth() + dow;
        break;
        default:
            kankaku = gsmsg.getMessage("cmn.notset");
            break;
        }

        return kankaku;
    }


    /**
     * <br>[機  能] リクエスト用プラグイン情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginList プラグイン情報リスト
     * @return リクエスト用システム情報
     * @throws Exception 実行例外
     */
    public String getPluginInfoReqParam(List <CliPluginModel> pluginList)
            throws Exception {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pluginList.size(); i++) {
            CliPluginModel mdl = pluginList.get(i);
            sb.append("&pluginList[" + i + "].cplPid=" + mdl.getCplPid());
            sb.append("&pluginList[" + i + "].cplPname="
                    + URLEncoder.encode(
                            NullDefault.getString(mdl.getCplPname(), ""), Encoding.UTF_8));
            sb.append("&pluginList[" + i + "].cplUsekbn=" + mdl.getCplUsekbn());
        }

        return sb.toString();
    }

    /**
     * <br>[機  能] コネクション件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return ディスクの空き容量
     * @throws Exception 実行例外
     */
    public String __getServerConnection(String domain) throws Exception {

        String connectionCount = new String();
        DataSource ds = GroupSession.getResourceManager().getDataSource(domain);
        if (ds instanceof org.apache.commons.dbcp2.BasicDataSource) {
            org.apache.commons.dbcp2.BasicDataSource bds
            = (org.apache.commons.dbcp2.BasicDataSource) ds;
            int aconcnt = bds.getNumActive();
            int iconcnt = bds.getNumIdle();
            connectionCount = "ACTIVE=" + aconcnt + " IDLE=" + iconcnt;
        }
        return connectionCount;
    }

    /**
     * <br>[機  能] DBへのアクセスが可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true: アクセス可能、false: アクセス不可
     */
    public boolean canDBAccess() {
        return !DayJob.isBackup() || DBUtilFactory.getInstance().getDbType() != GSConst.DBTYPE_H2DB;
    }

    /**
     * <br>[機  能] モバイルアプリで利用可能なプラグインのプラグインIDを表示する
     * <br>[解  説] 「リクエストのユーザエージェントがGSモバイルアプリを示す場合」のみ、プラグインID一覧を返す
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return モバイルアプリで利用可能なプラグインのプラグインID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAllowPluginIdForMblApp(Connection con, RequestModel reqMdl) throws SQLException {

        //リクエスト情報、またはUserAgentが存在しない場合、nullを返す
        if (reqMdl == null || reqMdl.getUserAgent() == null) {
            return null;
        }

        //GSモバイルアプリ以外からのアクセスの場合、nullを返す
        if (reqMdl.getUserAgent().getClient() != UserAgent.CLIENT_TYPE_GSMOBILE) {
            return null;
        }

        //アクセス元がGSファイアウォールの許可IPアドレスに含まれる場合、
        //プラグインの制限を行わないため、nullを返す
        FirewallBiz firewallBiz = FirewallBiz.getInstance();
        reqMdl.getUserAgent().setClient(UserAgent.CLIENT_TYPE_NOSEL);
        try {
            if (firewallBiz.isArrowAccess(reqMdl, con, reqMdl.getUserAgent())) {
                return null;
            }
        } finally {
            reqMdl.getUserAgent().setClient(UserAgent.CLIENT_TYPE_GSMOBILE);
        }

        //外部アクセス時、GSモバイルアプリで使用可能なプラグイン一覧を取得
        CmnPluginMobilePermissionDao pluginMobileDao = new CmnPluginMobilePermissionDao(con);
        List<String> allowPidList = pluginMobileDao.getAllowPluginIdList();
        if (!allowPidList.isEmpty()) {
            //使用可能プラグインが存在する = 外部アクセス時のプラグイン制限ありのため
            //GSモバイルアプリでデフォルト使用可能なプラグインを追加する
            allowPidList.addAll(Arrays.asList(GSConstCommon.FIREWALL_MBLAPP_ALLOW_PLUGINLIST));
        } else {
            //使用可能プラグインが存在しない = 外部アクセス時のプラグイン制限なしのため、nullを返す
            return null;
        }

        return allowPidList;
    }
}
