package jp.groupsession.v2.man.man340;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man121.Man121ParamModel;
import jp.groupsession.v2.man.man340.model.Man340UrlParamModel;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340ParamModel extends Man121ParamModel {
    /** 初期表示フラグ */
    private int man340initFlg__ = 0;
    /** 処理モード */
    private int man340cmdMode__ = 0;
    /** プラグインID */
    private String man340pluginId__ = GSConst.PLUGINID_MAIN;
    /** 機能名 */
    private String man340funcId__ = null;
    /** タイトル */
    private String man340title__ = null;
    /** URL */
    private String man340url__ = null;
    /** ウィンドウ区分 0:インライン 1:別ウィンドウ */
    private int man340openKbn__ = GSConstMain.TARGET_BLANK;
    /** 添付ファイル */
    private String man340file__ = null;
    /** 添付ファイル 保存名 */
    private String man340SaveFile__ = null;
    /** パラメータ区分 */
    private int man340paramKbn__ = 0;
    /** 送信区分 */
    private int man340sendKbn__ = 0;
    /** パラメータリスト */
    private List<Man340UrlParamModel> man340urlParamList__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man340ParamModel() {
        man340urlParamList__ = new ArrayList <Man340UrlParamModel>();
    }

    /**
     * <p>man340openKbn を取得します。
     * @return man340openKbn
     */
    public int getMan340openKbn() {
        return man340openKbn__;
    }
    /**
     * <p>man340openKbn をセットします。
     * @param man340openKbn man340openKbn
     */
    public void setMan340openKbn(int man340openKbn) {
        man340openKbn__ = man340openKbn;
    }
    /**
     * <p>man340title を取得します。
     * @return man340title
     */
    public String getMan340title() {
        return man340title__;
    }
    /**
     * <p>man340title をセットします。
     * @param man340title man340title
     */
    public void setMan340title(String man340title) {
        man340title__ = man340title;
    }
    /**
     * <p>man340url を取得します。
     * @return man340url
     */
    public String getMan340url() {
        return man340url__;
    }
    /**
     * <p>man340url をセットします。
     * @param man340url man340url
     */
    public void setMan340url(String man340url) {
        man340url__ = man340url;
    }

    /**
     * <p>man340file を取得します。
     * @return man340file
     */
    public String getMan340file() {
        return man340file__;
    }
    /**
     * <p>man340file をセットします。
     * @param man340file man340file
     */
    public void setMan340file(String man340file) {
        man340file__ = man340file;
    }
    /**
     * <p>man340SaveFile を取得します。
     * @return man340SaveFile
     */
    public String getMan340SaveFile() {
        return man340SaveFile__;
    }

    /**
     * <p>man340SaveFile をセットします。
     * @param man340SaveFile man340SaveFile
     */
    public void setMan340SaveFile(String man340SaveFile) {
        man340SaveFile__ = man340SaveFile;
    }

    /**
     * <p>man340funcId を取得します。
     * @return man340funcId
     */
    public String getMan340funcId() {
        return man340funcId__;
    }
    /**
     * <p>man340funcId をセットします。
     * @param man340funcId man340funcId
     */
    public void setMan340funcId(String man340funcId) {
        man340funcId__ = man340funcId;
    }
    /**
     * <p>man340pluginId を取得します。
     * @return man340pluginId
     */
    public String getMan340pluginId() {
        return man340pluginId__;
    }
    /**
     * <p>man340pluginId をセットします。
     * @param man340pluginId man340pluginId
     */
    public void setMan340pluginId(String man340pluginId) {
        man340pluginId__ = man340pluginId;
    }
    /**
     * <p>man340cmdMode を取得します。
     * @return man340cmdMode
     */
    public int getMan340cmdMode() {
        return man340cmdMode__;
    }
    /**
     * <p>man340cmdMode をセットします。
     * @param man340cmdMode man340cmdMode
     */
    public void setMan340cmdMode(int man340cmdMode) {
        man340cmdMode__ = man340cmdMode;
    }

    /**
     * <p>man340paramKbn を取得します。
     * @return man340paramKbn
     */
    public int getMan340paramKbn() {
        return man340paramKbn__;
    }

    /**
     * <p>man340paramKbn をセットします。
     * @param man340paramKbn man340paramKbn
     */
    public void setMan340paramKbn(int man340paramKbn) {
        man340paramKbn__ = man340paramKbn;
    }

    /**
     * <p>man340sendKbn を取得します。
     * @return man340sendKbn
     */
    public int getMan340sendKbn() {
        return man340sendKbn__;
    }

    /**
     * <p>man340sendKbn をセットします。
     * @param man340sendKbn man340sendKbn
     */
    public void setMan340sendKbn(int man340sendKbn) {
        man340sendKbn__ = man340sendKbn;
    }

    /**
     * <p>man340urlParamList を取得します。
     * @return man340urlParamList
     */
    public List<Man340UrlParamModel> getMan340urlParamListToList() {
        return man340urlParamList__;
    }

    /**
     * <p>man340urlParamList をセットします。
     * @param man340urlParamList man340urlParamList
     */
    public void setMan340urlParamListForm(List<Man340UrlParamModel> man340urlParamList) {
        man340urlParamList__ = man340urlParamList;
    }

    /**
     * <p>Man340UrlParamModel を取得します。
     * @param iIndex インデックス番号
     * @return Man340UrlParamModel を戻す
     */
    public Man340UrlParamModel getMan340urlParamList(int iIndex) {
        while (man340urlParamList__.size() <= iIndex) {
            man340urlParamList__.add(new Man340UrlParamModel());
        }
        return man340urlParamList__.get(iIndex);
    }
    /**
     * <p>Man340UrlParamModel を取得します。
     * @return Man340UrlParamModel[]
     */
    public Man340UrlParamModel[] getMan340urlParamList() {
        int size = 0;
        if (man340urlParamList__ != null) {
            size = man340urlParamList__.size();
        }
        return (Man340UrlParamModel[]) man340urlParamList__.toArray(new Man340UrlParamModel[size]);
    }
    /**
     * <br>[機  能] 表の行数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 表の行数
     */
    public int getMan340urlParamListFormSize() {
        return man340urlParamList__.size();
    }

    /**
     * <br>[機  能] フォームパラメータをコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setParam(Object form) {
        super.setParam(form);

        setMan340urlParamListForm(((Man340Form) form).getMan340urlParamListToList());
    }
    /**
     * <br>[機  能] Modelの出力値をフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setFormData(Object form) {
        super.setFormData(form);

        ((Man340Form) form).setMan340urlParamListForm(getMan340urlParamListToList());
    }

    /**
     * <p>man340initFlg を取得します。
     * @return man340initFlg
     */
    public int getMan340initFlg() {
        return man340initFlg__;
    }

    /**
     * <p>man340initFlg をセットします。
     * @param man340initFlg man340initFlg
     */
    public void setMan340initFlg(int man340initFlg) {
        man340initFlg__ = man340initFlg;
    }
}