package jp.groupsession.v2.adr.adr120;

import java.sql.Connection;

import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アドレス帳 会社インポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr120Biz {

    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr120Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr120ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @throws Exception 実行例外
     */
    public void getInitData(Connection con, Adr120ParamModel paramMdl, String tempDir)
    throws Exception {

        AddressBiz addressBiz = new AddressBiz(reqMdl_);

        //取込みファイルコンボを設定
        paramMdl.setAdr120fileCombo(addressBiz.getFileCombo(tempDir));

        paramMdl.setAdr120init(1);

    }
}
