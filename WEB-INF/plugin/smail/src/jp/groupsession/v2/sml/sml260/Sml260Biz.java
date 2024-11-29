package jp.groupsession.v2.sml.sml260;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;



/**
 * <br>[機  能] ショートメール アカウントインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260Biz {

    /** 画面ID */
    public static final String SCR_ID = "sml260";

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void getInitData(Sml260ParamModel paramMdl, RequestModel reqMdl)
    throws Exception {

        //テンポラリディレクトリパスを取得
        String tempDir = getTempDir(reqMdl);

        //取込みファイルコンボを設定
        SmlCommonBiz sBiz = new SmlCommonBiz();
        paramMdl.setSml260FileLabelList(sBiz.getFileCombo(tempDir));
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        return smlCmnBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.deleteTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを初期化する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @throws IOToolsException テンポラリディレクトリの再作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl)
    throws IOToolsException {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.clearTempDir(reqMdl, SCR_ID);
    }
}
