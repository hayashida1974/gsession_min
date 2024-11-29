package jp.groupsession.v2.wml.wml041;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.base.WmlAccountSignModel;
import jp.groupsession.v2.wml.wml040.Wml040Biz;
import jp.groupsession.v2.wml.wml040.Wml040SignModel;
import jp.groupsession.v2.wml.wml190.Wml190Biz;

/**
 * <br>[機  能] WEBメール アカウント 署名登録(ポップアップ)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml041Biz {

    /** 呼び出し元画面 アカウント登録画面(wml040) */
    private static final int PARENT_ID_WML040__ = 0;
    /** 呼び出し元画面 アカウント登録画面(wml190) */
    private static final int PARENT_ID_WML190__ = 1;

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws IOToolsException 署名情報の読み込みに失敗
     */
    public  void setInitData(RequestModel reqMdl,
                            Wml041ParamModel paramMdl)
    throws IOToolsException {

        if (paramMdl.getWml041initFlg() != 1) {
            paramMdl.setWml041initFlg(1);

            String tempDir = __getTempDir(reqMdl, paramMdl);

            Wml040Biz biz040 = new Wml040Biz();
            Wml040SignModel signData = biz040.loadSignModel(reqMdl, tempDir);
            if (paramMdl.getWml041mode() == Wml041Form.MODE_EDIT) {
                int signNo = paramMdl.getWml041No();
                WmlAccountSignModel signDetailData = signData.getSignList().get(signNo - 1);
                paramMdl.setWml041title(signDetailData.getWsiTitle());
                paramMdl.setWml041sign(signDetailData.getWsiSign());
            } else {
                paramMdl.setWml041No(signData.getSignList().size() + 1);
            }
        }
    }

    /**
     * <br>[機  能] 署名情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws IOToolsException 署名情報の保存に失敗
     */
    public  void saveSignData(RequestModel reqMdl,
                            Wml041ParamModel paramMdl)
    throws IOToolsException {

        int signNo = paramMdl.getWml041No();
        WmlAccountSignModel signDetailData = new WmlAccountSignModel();
        signDetailData.setWsiNo(signNo);
        signDetailData.setWsiTitle(paramMdl.getWml041title());
        signDetailData.setWsiSign(paramMdl.getWml041sign());

        String tempDir = __getTempDir(reqMdl, paramMdl);
        Wml040Biz biz040 = new Wml040Biz();
        if (paramMdl.getWml041mode() == Wml041Form.MODE_EDIT) {
            biz040.editSignModel(reqMdl, tempDir, signDetailData, signNo);
        } else {
            biz040.addSignModel(reqMdl, tempDir, signDetailData);
        }
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @return テンポラリディレクトリパス
     * @throws IOToolsException テンポラリディレクトリパスの取得に失敗
     */
    private String __getTempDir(RequestModel reqMdl,
                            Wml041ParamModel paramMdl)
    throws IOToolsException {

        String parentId = Wml040Biz.SCR_ID;
        if (paramMdl.getWml041parentId() == PARENT_ID_WML190__) {
            parentId = Wml190Biz.SCR_ID;
        }

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqMdl, parentId);

        //テンポラリディレクトリパスのチェック
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        if (tempPathUtil.checkTempPath(reqMdl, GSConstWebmail.PLUGIN_ID_WEBMAIL, parentId) == false) {
            throw new IOToolsException("テンポラリディレクトリパスが不正 : tempDir = " + tempDir);
        }

        return tempDir;
    }
}
