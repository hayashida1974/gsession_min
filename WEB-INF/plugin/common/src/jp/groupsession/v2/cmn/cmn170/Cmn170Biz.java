package jp.groupsession.v2.cmn.cmn170;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.ThemePathBiz;
import jp.groupsession.v2.cmn.cmn170.dao.Cmn170Dao;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrThemeDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrThemeModel;

/**
 * <br>[機  能] メイン 個人設定 テーマ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn170Biz {

    /** パス格納変数 */
    private String ctmPath__ = null;
    /** テーマSID格納変数 */
    private int ctmSid__ = 0;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn170Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cmn170Biz() {
        super();
    }

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Cmn170Biz(Connection con) {
        con__ = con;
    }
    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn170Model パラメータ格納モデル
     * @param umodel ユーザ基本情報モデル
     * @throws Exception 実行例外
     */


    public void setInitData(
        Cmn170ParamModel cmn170Model,
        BaseUserModel umodel) throws Exception {
        log__.debug("start");

        //テーマ画像パスを取得する
        Cmn170Dao cmn170Dao = new Cmn170Dao(con__);

        // クラシックテーマリスト
        List<Cmn170Model> classicThemeList = new ArrayList<Cmn170Model>();
        classicThemeList =  cmn170Dao.selectClassicTheme();
        cmn170Model.setClassicThemeList(classicThemeList);

        // オリジナルテーマリスト
        List<Cmn170Model> originalThemeList = new ArrayList<Cmn170Model>();
        originalThemeList =  cmn170Dao.selectOriginalTheme();
        cmn170Model.setOriginalThemeList(originalThemeList);

        // ctmSidの取得
        ctmSid__ = cmn170Dao.select2(umodel.getUsrsid());

        if (ctmSid__ == 0) {
            ctmSid__ = ThemePathBiz.DEFAULT_CTM_SID;
        }
        cmn170Model.setCmn170Dsp1(ctmSid__);

        log__.debug("end");
    }

    /**
     * <br>[機  能] 設定されたテーマ設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn170Model パラメータ格納モデル
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return ctmPath__ テーマパス
     */
    public String setPconfSetting(Cmn170ParamModel cmn170Model,
            BaseUserModel umodel, Connection con)
            throws SQLException {

        //
        UDate now = new UDate();
        CmnUsrThemeModel tmodel = new CmnUsrThemeModel();
        boolean commitFlg = false;
        try {
            CmnUsrThemeDao dao = new CmnUsrThemeDao(con);
            tmodel.setUsrSid(umodel.getUsrsid());
            tmodel.setCtmSid(cmn170Model.getCmn170Dsp1());
            tmodel.setUtmAuid(umodel.getUsrsid());
            tmodel.setUtmAdate(now);
            tmodel.setUtmEuid(umodel.getUsrsid());
            tmodel.setUtmEdate(now);

            if (dao.update(tmodel) == 0) {
                dao.insert(tmodel);
            }

            // ctmPthの取得
            Cmn170Dao cmn170Dao = new Cmn170Dao(con);
            ctmPath__ = cmn170Dao.select(cmn170Model.getCmn170Dsp1());

            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            }
        }
        return ctmPath__;
    }
}