package jp.groupsession.v2.rng.rng290;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngFormBuildBiz;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.rng020.Rng020Biz;
import jp.groupsession.v2.rng.rng110keiro.Rng110Keiro;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;

/**
 * <br>[機  能] 稟議作成画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng290Biz extends Rng020Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Rng290Biz.class);

    /** サンプル用添付ファイルダウンロードURL(画面操作できないので現在は未実装) */
    private static final String TEMPFILE_DOWNLOAD_URL = "../ringi/rng290.do";
            //"../ringi/rng290.do?CMD=fileDownload&rng020BinSid={binSid}&rng020BinDirId={dirId}";

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rng290Biz(Connection con, RequestModel reqMdl) {
        super(con, reqMdl);
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、稟議情報を設定する
     * <br>[備  考]
     * @param req リクエスト
     * @param paramMdl パラメータ情報
     * @param userMdl セッションユーザ情報
     * @param appRoot ルートディレクトリパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public void setInitData(HttpServletRequest req, Rng290ParamModel paramMdl,
                            BaseUserModel userMdl, String appRoot, GSTemporaryPathModel tempDir)
    throws IOException, IOToolsException, SQLException,
    TempFileException, RtpNotfoundException {

        UDate now = new UDate();

        // タイトル
        String rngTitle = paramMdl.getRng290rngTitle();
        paramMdl.setRng020Title(rngTitle);

        // 申請ID 手動入力チェックボックス表示あり
        if (paramMdl.getIdPrefManualEditable() > 0) {
            if (paramMdl.getRng290idPrefManual() == RngConst.RAR_SINSEI_NOT_KYOKA) {
                paramMdl.setIdPrefManualEditable(0); // テンプレートの設定で手動設定を許可しない場合
            }
        }

        // 作成日を設定する
        paramMdl.setRng020createDate(UDateUtil.getSlashYYMD(now)
                                + " "
                                + UDateUtil.getSeparateHMS(now));

        if (paramMdl.getRngSelectTplSid() > 0) {
            paramMdl.setRng020rtpSid(paramMdl.getRngSelectTplSid());
        }

        setDspData(paramMdl, userMdl, true, appRoot, tempDir);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 描画情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、稟議情報を設定する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userMdl セッションユーザ情報
     * @param first 初回アクセスフラグ
     * @param appRoot ルートディレクトリパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public void setDspData(Rng290ParamModel paramMdl,
            BaseUserModel userMdl, boolean first,
            String appRoot, GSTemporaryPathModel tempDir)
    throws IOException, IOToolsException, SQLException,
    TempFileException, RtpNotfoundException {

        paramMdl.setRng020rtpSid(-1);

        FormInputBuilder builder = paramMdl.getRng020input();

        // 入力フォーム
        if (paramMdl.getRng290templateJSON() != null
        && !paramMdl.getRng290templateJSON().isEmpty()) {
            // 入力フォーム情報あり → JSONから解析
            builder.setFormTable(paramMdl.getRng290templateJSON());
        } else if (paramMdl.getRng290content() != null) {
            // 入力フォーム情報なし → 汎用のフォーム([内容])をセット
            RngFormBuildBiz formBiz = new RngFormBuildBiz(reqMdl__);

            //添付ファイル一覧(テンプレートから取得)を設定
            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> samples = cmnBiz.getTempFileLabelList(tempDir.getTempPath());

            // 汎用フォーム生成
            formBiz.setFormHanyouRingi(builder, paramMdl.getRng290content(), samples);
        }

        //申請者を設定する
        paramMdl.setRng020requestUser(userMdl.getUsiseimei());
        paramMdl.setRng020requestUserId(String.valueOf(userMdl.getUsrsid()));

        RngBiz rBiz = new RngBiz(con__);
        paramMdl.setIdUseFlg(0);

        RingiIdModel idModel = null;
        idModel = rBiz.getRngidModel(paramMdl.getRng290idSid());
        int idEditable = RngConst.RAR_SINSEI_NOT_KYOKA;
        //稟議ID情報がnullの場合（稟議IDを使用しない場合のみnullが帰る）、稟議IDを使用しない
        if (idModel != null) {
            paramMdl.setIdUseFlg(1);
            idEditable = idModel.getRngManual();
            if (idEditable == RngConst.RAR_SINSEI_MANUAL_TEMPLATE) {
                idEditable = paramMdl.getRng290idPrefManual();
            }
        }
        paramMdl.setIdPrefManualEditable(idEditable);

        if (idModel != null) {
            paramMdl.setRng020IdTitle(idModel.getRngTitle());
        } else {
            paramMdl.setRng020IdTitle("");
        }
        // 発行予定の申請ID
        String planId = rBiz.getNewRngid(
                idModel, null, false, paramMdl.getRngSid(), reqMdl__);
        paramMdl.setRng020PlanID(planId);
        paramMdl.setRng020ID(planId);

        if (first) {
            paramMdl.getRng020input().defaultInit();
        }

        // フォーム要素を表示(プレビュー画面ではダウンロードできないのでURLは仮を使用)
        FormInputInitPrefarence pref = new FormInputInitPrefarence();
        pref.setAppRoot(appRoot);
        pref.setLoad(false);
        pref.setMode(FormInputBuilder.INITMODE_PREVIEW);
        pref.setUrl(TEMPFILE_DOWNLOAD_URL);
        pref.setTempDir(tempDir);
        paramMdl.getRng020input().setInitPrefarence(pref);

        paramMdl.getRng020input().dspInit(reqMdl__, con__);

        Rng110Keiro rngKeiro = paramMdl.getRng290keiro();
        Map<Integer, Rng110KeiroDialogParamModel> keiroTplMap      = null;
        Map<Integer, Rng110KeiroDialogParamModel> finalKeiroTplMap = null;
        if (rngKeiro != null && first) {
            keiroTplMap      = rngKeiro.getKeiroMap();
            finalKeiroTplMap = rngKeiro.getFinalKeiroMap();
        }

        if (keiroTplMap != null) {
            // 不要データの削除
            for (Entry<Integer, Rng110KeiroDialogParamModel> entry : keiroTplMap.entrySet()) {
                Rng110KeiroDialogParamModel keiroPref = entry.getValue();
                if (keiroPref.getInCondMap() != null) {
                    keiroPref.getInCondMap().remove("template");
                }
            }
        }
        if (finalKeiroTplMap != null) {
            // 不要データの削除
            for (Entry<Integer, Rng110KeiroDialogParamModel> entry : finalKeiroTplMap.entrySet()) {
                Rng110KeiroDialogParamModel keiroPref = entry.getValue();
                if (keiroPref.getInCondMap() != null) {
                    keiroPref.getInCondMap().remove("template");
                }
            }
        }

        this.initKeiroTpl(paramMdl, keiroTplMap, finalKeiroTplMap, first, 0);

        this.dspKeiroTpl(paramMdl, true, true);
    }
}
