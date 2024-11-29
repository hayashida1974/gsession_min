package jp.groupsession.v2.usr.biz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrAdmSortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPriSortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPriSortConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.dao.UsrAconfDao;
import jp.groupsession.v2.usr.model.UsrAconfModel;

/**
 * <br>[機  能] ユーザ情報に関する共通ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsrCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UsrCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public UsrCommonBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public UsrCommonBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ユーザ情報の管理者設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param con DBコネクション
     * @return ユーザ情報管理者設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public UsrAconfModel getAConfModel(Connection con) throws SQLException {
        //DBより現在の設定を取得する。
        UsrAconfDao dao = new UsrAconfDao(con);
        UsrAconfModel conf = dao.select();
        if (conf == null) {
            //データがない場合
            conf = new UsrAconfModel();
            //CSVエクスポート制限設定(デフォルトは管理者のみ)
            conf.setUadExport(GSConstUser.CSV_EXPORT_ADMIN);

            //登録者・更新者
            UDate now = new UDate();
            conf.setUadAuid(0);
            conf.setUadAdate(now);
            conf.setUadEuid(0);
            conf.setUadEdate(now);
            conf.setUadQrKbn(GSConstUser.QR_CODE_DISP);
        }

        log__.debug(conf.toCsvString());
        return conf;
    }

    /**
     * <br>[機  能] ユーザ情報ソート管理者設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @return ユーザ情報ソート管理者設定
     * @throws SQLException SQL実行エラー
     */
    public CmnUsrAdmSortConfModel getSortAdmConfModel(Connection con) throws SQLException {

        CmnUsrAdmSortConfDao dao = new CmnUsrAdmSortConfDao(con);
        CmnUsrAdmSortConfModel sortAdmConf = dao.select();
        if (sortAdmConf == null) {
            sortAdmConf = new CmnUsrAdmSortConfModel();
        }

        if (sortAdmConf.getUasAdate() == null) {

            //デフォルト表示順
            sortAdmConf.setUasSortKbn(GSConstUser.DEFO_DSP_USR);
            //ソート1
            sortAdmConf.setUasSortKey1(GSConstUser.USER_SORT_YKSK);
            sortAdmConf.setUasSortOrder1(GSConst.ORDER_KEY_ASC);
            //ソート2
            sortAdmConf.setUasSortKey2(GSConstUser.USER_SORT_NAME);
            sortAdmConf.setUasSortOrder2(GSConst.ORDER_KEY_ASC);

            //登録者・更新者
            UDate now = new UDate();
            sortAdmConf.setUasAuid(0);
            sortAdmConf.setUasAdate(now);
            sortAdmConf.setUasEuid(0);
            sortAdmConf.setUasEdate(now);

        }
        log__.debug("sortAdmConf = " + sortAdmConf.toCsvString());
        return sortAdmConf;
    }

    /**
     * <br>[機  能] ユーザ情報ソート個人設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param aconf CmnUsrAdmSortConfModel
     * @return ユーザ情報ソート個人設定l
     * @throws SQLException SQL実行エラー
     */
    public CmnUsrPriSortConfModel getSortPriConfModel(
            Connection con,
            int usrSid,
            CmnUsrAdmSortConfModel aconf) throws SQLException {

        CmnUsrPriSortConfDao dao = new CmnUsrPriSortConfDao(con);
        CmnUsrPriSortConfModel sortPriConf = dao.select(usrSid);
        if (sortPriConf == null) {
            sortPriConf = new CmnUsrPriSortConfModel();
        }

        if (sortPriConf.getUpsAdate() == null) {

            log__.debug("***管理者が設定したデフォルトのソート順で表示します***");
            //ユーザSID
            sortPriConf.setUsrSid(usrSid);
            //ソート1
            sortPriConf.setUpsSortKey1(aconf.getUasSortKey1());
            sortPriConf.setUpsSortOrder1(aconf.getUasSortOrder1());
            //ソート2
            sortPriConf.setUpsSortKey2(aconf.getUasSortKey2());
            sortPriConf.setUpsSortOrder2(aconf.getUasSortOrder2());

            //登録者・更新者
            UDate now = new UDate();
            sortPriConf.setUpsAuid(0);
            sortPriConf.setUpsAdate(now);
            sortPriConf.setUpsEuid(0);
            sortPriConf.setUpsEdate(now);

        }
        log__.debug("sortPriConf = " + sortPriConf.toCsvString());
        return sortPriConf;
    }

    /**
     * <br>[機  能] ユーザ情報のラベル権限設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @return  ユーザ情報のラベル権限設定
     * @throws SQLException SQL実行エラー
     */
    public CmnLabelUsrConfModel getLabelConfModel(
            Connection con) throws SQLException {

        CmnLabelUsrConfDao dao = new CmnLabelUsrConfDao(con);
        CmnLabelUsrConfModel labelConf = dao.select();
        if (labelConf == null) {
            labelConf = new CmnLabelUsrConfModel();
        }

        if (labelConf.getLufAdate() == null) {

            log__.debug("***デフォルトの設定で表示します***");

            //ラベル編集権限設定取得
            labelConf.setLufEdit(0);
            //ラベル付与権限設定取得
            labelConf.setLufSet(0);

            //登録者・更新者
            UDate now = new UDate();
            labelConf.setLufAuid(0);
            labelConf.setLufAdate(now);
            labelConf.setLufEuid(0);
            labelConf.setLufEdate(now);

        }
        log__.debug("sortPriConf = " + labelConf.toCsvString());
        return labelConf;
    }

    /**
     * <br>[機  能] 管理者グループ以外のグループが存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @return true=存在する、false=存在しない
     * @throws SQLException SQL実行エラー
     */
    public boolean isNotAdminGroupExists(Connection con) throws SQLException {

        CmnGroupmDao cgDao = new CmnGroupmDao(con);
        int count = cgDao.getNotAdminGroupCount();

        if (count < 1) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] ユーザの登録可能数を超えていないかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con DBコネクション
     * @return true=制限数に達している、false=制限以内
     * @throws SQLException SQL実行エラー
     */
    public boolean isUserCountOver(RequestModel reqMdl, Connection con) throws SQLException {
        return this.isUserCountOver(reqMdl, con, 1, 0);
    }

    /**
     * <br>[機  能] 指定した人数のユーザを追加した場合に、ユーザの登録可能数を超えていないかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con DBコネクション
     * @param addCnt 追加人数
     * @return true=制限数を超える、false=制限以内
     * @throws SQLException SQL実行エラー
     */
    public boolean isUserCountOver(RequestModel reqMdl, Connection con, int addCnt)
    throws SQLException {
        return this.isUserCountOver(reqMdl, con, addCnt, 0);
    }

    /**
     * <br>[機  能] 指定した人数のユーザを追加or削除した場合に、ユーザの登録可能数を超えていないかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con DBコネクション
     * @param addCnt 追加人数
     * @param delCnt 削除人数
     * @return true=制限数を超える、false=制限以内
     * @throws SQLException SQL実行エラー
     */
    public boolean isUserCountOver(RequestModel reqMdl, Connection con, int addCnt, int delCnt)
    throws SQLException {
        int userLimit = NullDefault.getInt(String.valueOf(
                GroupSession.getResourceManager().getUserCountLimit(reqMdl)), 0);
        if (userLimit == 0) {
            return false;
        }

        UserBiz userBiz = new UserBiz();
        int activeUser = userBiz.getActiveUserCount(con);

        if (userLimit >= activeUser + addCnt - delCnt) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] 公開・非公開フラグから文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param showFlg 公開・非公開フラグ
     * @return 公開・非公開(文字列)
     */
    public String getShowFlgString(int showFlg) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //非公開
        String textPrivate = gsMsg.getMessage("cmn.private");
        //公開
        String textPublic = gsMsg.getMessage("cmn.public");
        if (showFlg == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            return textPublic;
        }
        return textPrivate;
    }

    /**
     * ユーザ情報全般のログ出力を行う
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type type
     */
    public void outPutLog(
            String opCode,
            String level,
            String value,
            String type) {

        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = usModel.getUsrsid(); //セッションユーザSID
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ ユーザ情報 **/
        String syainInfo = gsMsg.getMessage("cmn.shain.info");
        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstUser.PLUGIN_ID_USER);
        logMdl.setLogPluginName(syainInfo);
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl__.getDomain();
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

        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ プログラム名 **/
        String kojinSetting = gsMsg.getMessage("cmn.preferences2");
        String adminSetting = gsMsg.getMessage("cmn.admin.setting");
        String dspSetting = gsMsg.getMessage("cmn.display.settings");
        String categorySetting = gsMsg.getMessage("cmn.category.entry");
        String labelSetting = gsMsg.getMessage("cmn.label.settings");
        String expoSetting = gsMsg.getMessage("cmn.export.restrictions.set");
        String defDspSetting = gsMsg.getMessage("cmn.default.display.order.setting");
        String syainInfoSearch = gsMsg.getMessage("user.src.60");

        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.usr.usr040.Usr040Action")) {
            return syainInfoSearch;
        }
        if (id.equals("jp.groupsession.v2.usr.usr041.Usr041Action")) {
            return kojinSetting + " " + dspSetting;
        }
        if (id.equals("jp.groupsession.v2.usr.usr043.Usr043Action")) {
            return kojinSetting + " " + categorySetting;
        }
        if (id.equals("jp.groupsession.v2.usr.usr044.Usr044Action")) {
            return kojinSetting + " " + labelSetting;
        }
        if (id.equals("jp.groupsession.v2.usr.usr045.Usr045Action")) {
            return kojinSetting + " " + gsMsg.getMessage("cmn.category.entry");
        }
        if (id.equals("jp.groupsession.v2.usr.usr046.Usr046Action")) {
            return kojinSetting + " " + gsMsg.getMessage("cmn.entry.label");
        }
        if (id.equals("jp.groupsession.v2.usr.usr081.Usr081Action")) {
            return adminSetting + " " + expoSetting;
        }
        if (id.equals("jp.groupsession.v2.usr.usr082.Usr082Action")) {
            return adminSetting + " " + defDspSetting;
        }
        if (id.equals("jp.groupsession.v2.usr.usr083kn.Usr083knAction")) {
            return adminSetting + " " + gsMsg.getMessage("cmn.setting.permissions.kn");
        }

        return ret;
    }

    /**
     * <br>[機  能] カテゴリーが存在するかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param lucSid カテゴリSID
     * @return true:存在する  false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckExistUsrCategory(Connection con, int lucSid) throws SQLException {

        CmnLabelUsrCategoryDao dao = new CmnLabelUsrCategoryDao(con);
        CmnLabelUsrCategoryModel mdl = dao.select(lucSid);
        return mdl != null;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl__,
                                                GSConstUser.PLUGIN_ID_USER,
                                                dirId);
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId ディレクトリID
     */
    public void deleteTempDir(String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl__,
                                    GSConstUser.PLUGIN_ID_USER,
                                    dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void createTempDir(String dirId)
    throws IOToolsException {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl__,
                                GSConstUser.PLUGIN_ID_USER,
                                dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリの初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(String dirId)
    throws IOToolsException {
        //テンポラリディレクトリの削除後、再作成
        deleteTempDir(dirId);
        createTempDir(dirId);
    }
}
