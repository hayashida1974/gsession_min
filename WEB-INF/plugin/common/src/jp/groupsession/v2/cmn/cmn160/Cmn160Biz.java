package jp.groupsession.v2.cmn.cmn160;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnEnterInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnEnterInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 企業情報画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160Biz {

    /** GSメッセージ */
    public GsMessage gsMsg__ = new GsMessage();

    /**
     * <p>コンストラクタ
     * @param gsMsg GsMessage
     */
    public Cmn160Biz(GsMessage gsMsg) {
        gsMsg__ = gsMsg;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn160Model パラメータ格納モデル
     * @param appRoot アプリケーションルートパス
     * @param tempDir ログイン画面 ロゴ テンポラリディレクトリパス
     * @param tempMenuDir ログイン画面メニュー テンポラリディレクトリパス
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     */
    public void getInitData(Connection con,
               Cmn160ParamModel cmn160Model,
                             String appRoot,
                             String tempDir,
                             String tempMenuDir,
                             String domain)
    throws SQLException, IOToolsException, TempFileException, IOException {

        //処理モード = 編集 かつ 初期表示の場合は会社情報を設定する
        if (cmn160Model.getCmn160InitFlg() == 0) {
            __readCompanyData(con, cmn160Model, appRoot, tempDir, tempMenuDir);
            cmn160Model.setCmn160InitFlg(1);
        }

        if (cmn160Model.getCmn160TourokuKbn() == GSConstCommon.MODE_EDIT
                && cmn160Model.isCmn160DelExeFlg()
                && cmn160Model.getCmn160DspLogoKbn() == 1) {
            //ログイン画面 編集かつ添付削除ボタン押下時
            __setLogoData(con, cmn160Model, appRoot, tempDir);
        }

        if (cmn160Model.getCmn160TourokuKbn() == GSConstCommon.MODE_EDIT
                && cmn160Model.isCmn160MenuDelExeFlg()
                && cmn160Model.getCmn160MenuDspLogoKbn() == 1) {
            //メニュー 編集かつ添付削除ボタン押下時
            __setMenuLogoData(con, cmn160Model, appRoot, tempMenuDir);
        }

        //月コンボを設定する。
        cmn160Model.setCmn160MonthList(__getMonthCombo());

        //ログイン画面 バイナリSIDが取得可能または
        //ログイン画面 テンポラリディレクトリセットフラグが未セット時の場合
        if (cmn160Model.getCmn160BinSid() > 0
                || cmn160Model.getCmn160TempSetFlg() == 0) {

            //処理モード = 編集
            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, cmn160Model.getCmn160BinSid(), domain);
            if (binMdl != null) {

                //テンポラリディレクトリにバイナリデータから作成したファイルを保存する
                String imageSaveName = cmnBiz.saveSingleTempFile(binMdl, appRoot, tempDir);
                cmn160Model.setCmn160LogoName(binMdl.getBinFileName());
                cmn160Model.setCmn160LogoSaveName(imageSaveName);
                cmn160Model.setCmn160TempSetFlg(1);
            }
        }

        //メニュー バイナリSIDが取得可能または
        //メニュー テンポラリディレクトリセットフラグが未セット時の場合
        if (cmn160Model.getCmn160MenuBinSid() > 0
                || cmn160Model.getCmn160MenuTempSetFlg() == 0) {

            //処理モード = 編集
            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, cmn160Model.getCmn160MenuBinSid(), domain);
            if (binMdl != null) {

                //テンポラリディレクトリにバイナリデータから作成したファイルを保存する
                String imageSaveName = cmnBiz.saveSingleTempFile(binMdl, appRoot, tempMenuDir);
                cmn160Model.setCmn160MenuLogoName(binMdl.getBinFileName());
                cmn160Model.setCmn160MenuLogoSaveName(imageSaveName);
                cmn160Model.setCmn160MenuTempSetFlg(1);
            }
        }
    }

    /**
     * <br>[機  能] 月コンボを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return monthCombo 月コンボ
     */
    private List<LabelValueBean> __getMonthCombo() {

        List<LabelValueBean> monthList = new ArrayList<LabelValueBean>();
        LabelValueBean label = null;
        for (int i = 1; i <= 12; i++) {
            label = new LabelValueBean(
                    String.valueOf(i) + gsMsg__.getMessage("cmn.month"),
                    String.valueOf(i));
            monthList.add(label);
        }
        return monthList;
    }

    /**
     * <br>[機  能] DBから会社情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn160Model パラメータ格納モデル
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param tempMenuDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     */
    private void __readCompanyData(Connection con,
                      Cmn160ParamModel cmn160Model,
                                    String appRoot,
                                    String tempDir,
                                    String tempMenuDir)
    throws SQLException, IOToolsException, TempFileException, IOException {

        //DBより企業情報取得
        CmnEnterInfDao entDao = new CmnEnterInfDao(con);
        CmnEnterInfModel entModel = entDao.select();
        if (entModel == null) {
            //会社情報新規登録
            cmn160Model.setCmn160TourokuKbn(GSConstCommon.MODE_ADD);
            return;
        }
        //会社情報編集
        cmn160Model.setCmn160TourokuKbn(GSConstCommon.MODE_EDIT);
        cmn160Model.setCmn160ComName(entModel.getEniName());
        cmn160Model.setCmn160ComNamekn(entModel.getEniNameKn());
        cmn160Model.setCmn160Kisyu(entModel.getEniKisyu());
        cmn160Model.setCmn160Url(entModel.getEniUrl());
        cmn160Model.setCmn160Biko(entModel.getEniBiko());

        //ログイン画面 ロゴ表示区分
        cmn160Model.setCmn160DspLogoKbn(entModel.getEniImgKbn());
        cmn160Model.setCmn160DbLogoKbn(entModel.getEniImgKbn());

        if (cmn160Model.getCmn160TempSetFlg() == 0
                && cmn160Model.getCmn160DbLogoKbn() == 1) {
            //テンポラリディレクトリ未セット時かつ表示ロゴがオリジナルの場合
            //画像バイナリSIDを取得
            cmn160Model.setCmn160BinSid(entModel.getBinSid());
        }

        //メニュー ロゴ表示区分
        cmn160Model.setCmn160MenuDspLogoKbn(entModel.getEniMenuImgKbn());
        cmn160Model.setCmn160MenuDbLogoKbn(entModel.getEniMenuImgKbn());

        if (cmn160Model.getCmn160MenuTempSetFlg() == 0
                && cmn160Model.getCmn160MenuDbLogoKbn() == 1) {
            //テンポラリディレクトリ未セット時かつ表示ロゴがオリジナルの場合
            //画像バイナリSIDを取得
            cmn160Model.setCmn160MenuBinSid(entModel.getMenuBinSid());
        }
    }

    /**
     * <br>[機  能] DBから画像バイナリ情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn160Model パラメータ格納モデル
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     */
    private void __setLogoData(Connection con,
                      Cmn160ParamModel cmn160Model,
                                    String appRoot,
                                    String tempDir)
    throws SQLException, IOToolsException, TempFileException, IOException {

        //DBより画像バイナリ情報取得
        CmnEnterInfDao entDao = new CmnEnterInfDao(con);
        CmnEnterInfModel entModel = entDao.select();
        if (entModel == null) {
            return;
        }

        if (cmn160Model.getCmn160TempSetFlg() == 0) {
            //テンポラリディレクトリ未セット時
            //画像バイナリSIDを取得
            cmn160Model.setCmn160BinSid(entModel.getBinSid());
        }
    }

    /**
     * <br>[機  能] DBから画像バイナリ情報を取得し、パラメータとして設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cmn160Model パラメータ格納モデル
     * @param appRoot アプリケーションルートパス
     * @param menuTempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     */
    private void __setMenuLogoData(Connection con,
                      Cmn160ParamModel cmn160Model,
                                    String appRoot,
                                    String menuTempDir)
    throws SQLException, IOToolsException, TempFileException, IOException {

        //DBより画像バイナリ情報取得
        CmnEnterInfDao entDao = new CmnEnterInfDao(con);
        CmnEnterInfModel entModel = entDao.select();
        if (entModel == null) {
            return;
        }

        if (cmn160Model.getCmn160MenuTempSetFlg() == 0) {
            //テンポラリディレクトリ未セット時
            //画像バイナリSIDを取得
            cmn160Model.setCmn160MenuBinSid(entModel.getMenuBinSid());
        }
    }
}
