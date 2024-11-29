package jp.groupsession.v2.rng.rng110;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngChannelTemplateDao;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.rng110keiro.Rng110Keiro;
import jp.groupsession.v2.rng.rng110keiro.RngTemplateKeiroSave;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 稟議 経路テンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng110Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Rng110Biz.class);

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Rng110Biz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Rng110Biz(Connection con) {
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 編集用データの読み込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     */
    public void loadData(Rng110ParamModel paramMdl, RequestModel reqMdl)
            throws SQLException {

        int rctSid = paramMdl.getRctSid();
        int userSid = paramMdl.getRng110UserSid();
        if (rctSid > 0) {
            //経路テンプレート情報を設定
            RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con__);
            RngChannelTemplateModel rctData = rctDao.select(rctSid, userSid);
            paramMdl.setRng110name(rctData.getRctName());
            RngTemplateKeiroSave saveBiz =
                    RngTemplateKeiroSave.createInstanceForRCT(
                            rctSid, userSid, reqMdl, con__);
            paramMdl.setRng110keiro(saveBiz.loadRng110Keiro());

        }
    }
    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、経路テンプレート情報を設定する
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void setInitData(RequestModel reqMdl, Rng110ParamModel paramMdl)
    throws IOException, IOToolsException, SQLException {

        int userSid = paramMdl.getRng110UserSid();

        //グループが未選択の場合、デフォルトグループを設定
        if (paramMdl.getRng110group() == -1) {
            GroupBiz grpBz = new GroupBiz();
            int defGrp = grpBz.getDefaultGroupSid(userSid, con__);
            paramMdl.setRng110group(defGrp);
        }

        paramMdl.setRng110UserSid(userSid);

        //経路設定の表示初期化
        Rng110Keiro keiro = paramMdl.getRng110keiro();
        keiro.dspInit(reqMdl, con__);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void entryKeiroTemplate(Rng110ParamModel paramMdl,
                                    RequestModel reqMdl,
                                    MlCountMtController cntCon
                                    )
    throws Exception {
        log__.debug("START");

        RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con__);

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        int userSid = paramMdl.getRng110UserSid();
        int rctCmdMode = paramMdl.getRngRctCmdMode();
        UDate now = new UDate();

        int rctSid = paramMdl.getRctSid();
        int rctVer = 0; // 経路バージョン
        if (rctCmdMode == RngConst.RNG_CMDMODE_ADD) {
            //新規登録の場合は経路テンプレートSIDを採番する
            rctSid = (int) cntCon.getSaibanNumber(RngConst.SBNSID_RINGI,
                                                RngConst.SBNSID_SUB_RINGI_CHANNEL_TEMPLATE,
                                                userSid);
        } else if (userSid == 0) {
            // 共有経路テンプレートの場合のみ前回のバージョンを取得して更新する
            RngChannelTemplateModel rctMdl = rctDao.select(rctSid, userSid);
            if (rctMdl != null) {
                rctVer = rctMdl.getRctVer();
            }
            rctVer++; // バージョン番号を加算
        }
        //経路テンプレート情報の登録
        RngChannelTemplateModel model = new RngChannelTemplateModel();
        model.setRctSid(rctSid);
        model.setUsrSid(userSid);
        model.setRctName(paramMdl.getRng110name());
        model.setRctAuid(sessionUsrSid);
        model.setRctAdate(now);
        model.setRctEuid(sessionUsrSid);
        model.setRctEdate(now);
        model.setRctVer(rctVer);
        if (rctCmdMode == RngConst.RNG_CMDMODE_ADD) {
            rctDao.insert(model);
        } else if (rctCmdMode == RngConst.RNG_CMDMODE_EDIT) {
            rctDao.update(model);
        }
        //稟議テンプレート経路情報の登録
        RngTemplateKeiroSave saveBiz =
                RngTemplateKeiroSave.createInstanceForRCT(
                        rctSid, userSid, reqMdl, con__);
        saveBiz.save(paramMdl.getRng110keiro(), true, cntCon);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void deleteKeiroTemplate(Rng110ParamModel paramMdl)
    throws Exception {
        log__.debug("START");

        int rctSid = paramMdl.getRctSid();

        //経路テンプレート情報の論理削除
        RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con__);
        rctDao.updateJkbn(rctSid, RngConst.JKBN_DELETE, paramMdl.getRng110UserSid());

        log__.debug("End");
    }

}
