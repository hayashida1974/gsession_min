package jp.groupsession.v2.wml.wml160;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール アカウントインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160Biz {
    /** 画面ID */
    public static final String SCR_ID = "wml160";

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void getInitData(Wml160ParamModel paramMdl, RequestModel reqMdl)
            throws Exception {

        WmlBiz wmlBiz = new WmlBiz();

        //初期表示の場合、テンポラリディレクトリを初期化する
        if (paramMdl.getWml160initFlg() != 1) {
            wmlBiz.clearTempDir(reqMdl, SCR_ID);
            paramMdl.setWml160initFlg(1);
        }

        //取込みファイルコンボを設定
        String tempDir = getTempDir(reqMdl);
        paramMdl.setWml160FileLabelList(wmlBiz.getFileCombo(tempDir));
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        return wmlBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.deleteTempDir(reqMdl, SCR_ID);
    }
}
