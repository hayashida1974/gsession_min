package jp.groupsession.v2.adr;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.dao.AdrContactDao;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.model.AdrContactModel;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳プラグインで使用する共通機能クラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public AdrCommonBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public AdrCommonBiz(Connection con) {
        con__ = con;
    }

    /**
     * アドレス帳全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value) {
        outPutLog(map, req, res, opCode, level, value, null);
    }

    /**
     * アドレス帳全般のログ出力を行う
     * @param map マップ
     * @param req リクエスト
     * @param res レスポンス
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルSID
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            HttpServletResponse res,
            String opCode,
            String level,
            String value,
            String  fileId) {
        GsMessage gsMsg = new GsMessage();
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstAddress.PLUGIN_ID_ADDRESS);
        logMdl.setLogPluginName(gsMsg.getMessage(req, "cmn.addressbook"));
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), req));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = GroupSession.getResourceManager().getDomain(req);
        logBiz.outPutLog(logMdl, domain);
    }


    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param req リクエスト
     * @return String
     */
    public String getPgName(String id, HttpServletRequest req) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        log__.info("プログラムID==>" + id);
        GsMessage gsMsg = new GsMessage();

        //アドレス一覧
        if (id.equals("jp.groupsession.v2.adr.adr010.Adr010Action")) {
            return gsMsg.getMessage(req, "address.141");
        }
        //アドレス登録・編集
        if (id.equals("jp.groupsession.v2.adr.adr020.Adr020Action")) {
            return gsMsg.getMessage(req, "address.142");
        }
        //管理者設定 権限設定確認
        if (id.equals("jp.groupsession.v2.adr.adr040kn.Adr040knAction")) {
            return gsMsg.getMessage(req, "cmn.admin.setting")
                    + gsMsg.getMessage(req, "cmn.setting.permissions.kn");
        }
        //個人設定 表示件数設定
        if (id.equals("jp.groupsession.v2.adr.adr060.Adr060Action")) {
            return gsMsg.getMessage(req, "cmn.preferences2")
                    + gsMsg.getMessage(req, "cmn.number.display.settings");
        }
        if (id.equals("jp.groupsession.v2.adr.adr070.Adr070Action")) {
            //アドレスインポート
            return gsMsg.getMessage(req, "address.adr070.1");
        }
        if (id.equals("jp.groupsession.v2.adr.adr070kn.Adr070knAction")) {
            //アドレスインポート確認
            return gsMsg.getMessage(req, "address.adr070kn.1");
        }
        if (id.equals("jp.groupsession.v2.adr.adr080.Adr080Action")) {
            //業種一覧
            return gsMsg.getMessage(req, "address.adr080.1");
        }
        if (id.equals("jp.groupsession.v2.adr.adr090.Adr090Action")) {
            //業種登録
            return gsMsg.getMessage(req, "address.144");
        }
        //会社一覧
        if (id.equals("jp.groupsession.v2.adr.adr100.Adr100Action")) {
            return gsMsg.getMessage(req, "address.102");
        }
        //会社登録
        if (id.equals("jp.groupsession.v2.adr.adr110.Adr110Action")) {
            return gsMsg.getMessage(req, "address.adr110.4");
        }
        //会社インポート
        if (id.equals("jp.groupsession.v2.adr.adr120.Adr120Action")) {
            return gsMsg.getMessage(req, "address.adr120.1");
        }
        if (id.equals("jp.groupsession.v2.adr.adr120kn.Adr120knAction")) {
            //会社インポート確認
            return gsMsg.getMessage(req, "address.adr120kn.1");
        }
        //ラベル一覧
        if (id.equals("jp.groupsession.v2.adr.adr130.Adr130Action")) {
            return gsMsg.getMessage(req, "cmn.labellist");
        }
        //ラベル登録・編集
        if (id.equals("jp.groupsession.v2.adr.adr140.Adr140Action")) {
            return gsMsg.getMessage(req, "cmn.label.for.editing");
        }
        if (id.equals("jp.groupsession.v2.adr.adr160.Adr160Action")) {
            //コンタクト履歴一覧
            return gsMsg.getMessage(req, "address.adr160.1");
        }
        if (id.equals("jp.groupsession.v2.adr.adr161.Adr161Action")) {
            //コンタクト履歴
            return gsMsg.getMessage(req, "address.6");
        }
        //コンタクト履歴登録・編集
        if (id.equals("jp.groupsession.v2.adr.adr170.Adr170Action")) {
            return gsMsg.getMessage(req, "address.146");
        }
        //役職登録
        if (id.equals("jp.groupsession.v2.adr.adr180.Adr180Action")) {
            return gsMsg.getMessage(req, "cmn.entry.position");
        }
        //ラベル登録
        if (id.equals("jp.groupsession.v2.adr.adr200.Adr200Action")) {
            return gsMsg.getMessage(req, "cmn.entry.label");
        }
        if (id.equals("jp.groupsession.v2.adr.adr210.Adr210Action")) {
            //役職一覧
            return gsMsg.getMessage(req, "address.adr210.1");
        }
        //役職登録
        if (id.equals("jp.groupsession.v2.adr.adr220.Adr220Action")) {
            return gsMsg.getMessage(req, "address.148");
        }
        //カテゴリ登録
        if (id.equals("jp.groupsession.v2.adr.adr290.Adr290Action")) {
            return gsMsg.getMessage(req, "address.adr290.1");
        }
        //カテゴリ登録
        if (id.equals("jp.groupsession.v2.adr.adr300.Adr300Action")) {
            return gsMsg.getMessage(req, "address.adr300.1");
        }
        //カテゴリ登録
        if (id.equals("jp.groupsession.v2.adr.adr310.Adr310Action")) {
            return gsMsg.getMessage(req, "address.adr310.1");
        }
        //管理者設定 アドレス帳登録者制限設定
        if (id.equals("jp.groupsession.v2.adr.adr320kn.Adr320knAction")) {
            return gsMsg.getMessage(req, "address.adr320.5");
        }
        //管理者設定 アドレス管理
        if (id.equals("jp.groupsession.v2.adr.adr330.Adr330Action")) {
            return gsMsg.getMessage(req, "address.adr330.3");
        }

        return ret;
    }

    /**
     * ユーザIDを指定しマイグループラベルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getMyGroupLabel(int userSid, Connection con)
    throws SQLException {
        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

        //マイグループリストをセット
        List<LabelValueBean> cmgLabelList = new ArrayList<LabelValueBean>();
        for (CmnMyGroupModel cmgMdl : cmgList) {

            cmgLabelList.add(
                    new LabelValueBean(
                            cmgMdl.getMgpName(),
                            "M"
                            + String.valueOf(cmgMdl.getMgpSid()))
                            );
        }
        return cmgLabelList;
    }

    /**
     * フォーム情報のグループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("M");

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * フォーム情報のグループコンボ値からグループSID又はマイグループSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (gpSid == null) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     *
     * <br>[機  能] コンタクト履歴の表示可能判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adcSid アドレスSID
     * @throws SQLException SQL実行時例外
     * @return 0：表示可能 1:データがないため表示不可
     */
    public int canViewContactData(Connection con, int adcSid) throws SQLException {
        AdrContactDao adcDao = new AdrContactDao(con);
        AdrContactModel adcMdl = adcDao.select(adcSid);
        if (adcMdl == null) {
            return 1;
        }
        return 0;
    }


    /**
     * <br>[機  能] カテゴリーが存在するかチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param alcSid カテゴリSID
     * @return true:存在する  false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckExistAdrCategory(Connection con, int alcSid) throws SQLException {

        AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel mdl = dao.select(alcSid);
        return mdl != null;
    }

}
