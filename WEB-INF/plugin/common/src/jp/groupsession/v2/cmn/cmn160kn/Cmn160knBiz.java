package jp.groupsession.v2.cmn.cmn160kn;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn160.Cmn160Biz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnEnterInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnEnterInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 企業情報確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160knBiz extends Cmn160Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn160knBiz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     */
    public Cmn160knBiz(GsMessage gsMsg) {
        super(gsMsg);
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn160knModel パラメータ格納モデル
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Cmn160knParamModel cmn160knModel) throws SQLException {

        //備考をHTML表示用に変換する。
        cmn160knModel.setCmn160knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(cmn160knModel.getCmn160Biko()));

    }

    /**
     * <br>[機  能] 企業情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn160knModel パラメータ格納モデル
     * @param sessionUserSid セッションユーザSID
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param tempMenuDir メニュー テンポラリディレクトリ
     * @param cntCon MlCountMtController
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws SQLException SQL実行例外
     */
    public void entryEnterPriseData(Connection con,
                       Cmn160knParamModel cmn160knModel,
                                     int sessionUserSid,
                                     String appRoot,
                                     String tempDir,
                                     String tempMenuDir,
                                     MlCountMtController cntCon)
    throws TempFileException, IOException, IOToolsException, SQLException {

        log__.debug("START");

        UDate now = new UDate();

        //企業情報の登録を行う
        CmnEnterInfModel entMdl = new CmnEnterInfModel();
        entMdl.setEniName(cmn160knModel.getCmn160ComName());
        entMdl.setEniNameKn(cmn160knModel.getCmn160ComNamekn());
        entMdl.setEniUrl(cmn160knModel.getCmn160Url());
        entMdl.setEniKisyu(cmn160knModel.getCmn160Kisyu());
        entMdl.setEniBiko(cmn160knModel.getCmn160Biko());
        entMdl.setEniImgKbn(cmn160knModel.getCmn160DspLogoKbn());
        entMdl.setEniMenuImgKbn(cmn160knModel.getCmn160MenuDspLogoKbn());

        //以前の企業情報データ取得
        CmnEnterInfDao entDao = new CmnEnterInfDao(con);
        CmnEnterInfModel delDataMdl = entDao.select();

        if (delDataMdl != null) {
            CmnBinfDao cbDao = new CmnBinfDao(con);
            CmnBinfModel binMdl = cbDao.getBinInfo(delDataMdl.getBinSid());
            CmnBinfModel menuBinMdl = cbDao.getBinInfo(delDataMdl.getMenuBinSid());

            //ログイン画面ロゴに該当データがあれば削除
            if (binMdl != null) {
                //バイナリー情報を更新する
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(delDataMdl.getBinSid());
                cbMdl.setBinUpuser(sessionUserSid);
                cbMdl.setBinUpdate(now);
                cbDao.removeBinData(cbMdl);
            }

            //メニューロゴに該当データがあれば削除
            if (menuBinMdl != null) {
                //バイナリー情報を更新する
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(delDataMdl.getMenuBinSid());
                cbMdl.setBinUpuser(sessionUserSid);
                cbMdl.setBinUpdate(now);
                cbDao.removeBinData(cbMdl);
            }
        }

        //ログイン画面 ロゴ情報を登録
        Long binSid = new Long(0);
        CommonBiz cmnBiz = new CommonBiz();
        if (!NullDefault.getStringZeroLength(cmn160knModel.getCmn160LogoName(), "").equals("")
                && cmn160knModel.getCmn160DspLogoKbn() == 1) {

            String filePath = tempDir + File.separator
                    + cmn160knModel.getCmn160LogoSaveName()
                    + GSConstCommon.ENDSTR_SAVEFILE;

            binSid = cmnBiz.insertBinInfo(
                    con, appRoot, cntCon, sessionUserSid, now,
                    filePath, cmn160knModel.getCmn160LogoName());
        }

        //メニュー ロゴ情報を登録
        Long menubinSid = new Long(0);
        if (!NullDefault.getStringZeroLength(
                cmn160knModel.getCmn160MenuLogoName(), "").equals("")
                && !NullDefault.getStringZeroLength(
                        cmn160knModel.getCmn160MenuLogoSaveName(), "").equals("")
                        && cmn160knModel.getCmn160MenuDspLogoKbn() == 1) {

            String filePath = tempMenuDir + File.separator
                    + cmn160knModel.getCmn160MenuLogoSaveName()
                    + GSConstCommon.ENDSTR_SAVEFILE;

            menubinSid = cmnBiz.insertBinInfo(
                    con, appRoot, cntCon, sessionUserSid, now,
                    filePath, cmn160knModel.getCmn160MenuLogoName());
        }

        entMdl.setBinSid(binSid);
        entMdl.setMenuBinSid(menubinSid);
        entMdl.setEniEuid(sessionUserSid);
        entMdl.setEniEdate(now);

        int count = entDao.updateEnterData(entMdl);
        if (count < 1) {
            entMdl.setEniAuid(sessionUserSid);
            entMdl.setEniAdate(now);
            entDao.insertEnterData(entMdl);
        }

        log__.debug("End");
    }
}
