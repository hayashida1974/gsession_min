package jp.groupsession.v2.cmn.formbuilder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
/**
 * FormInputBuilder 画面設定モデル
 * コンストラクタ
 */
public class FormInputInitPrefarence implements Cloneable {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FormInputInitPrefarence.class);

    /**  mode 0: 入力、入力確認モード（削除済みユーザなど選択できないデータは除外される
     *        1: 登録済みデータ表示モード（登録時のデータを表示する）*/
    private int mode__;

    /** ダウンロード用URL(添付フォーム用)*/
    private String url__;
    /** アプリケーションルートパス(添付フォーム用)*/
    private String appRoot__;
    /** テンポラリディレクトリパス(添付フォーム用)*/
    private GSTemporaryPathModel tempDir__;
    /** ファイル読込フラグ(添付フォーム用)*/
    private boolean isLoad__;


    public FormInputInitPrefarence() {
    }

    @Override
    protected FormInputInitPrefarence clone() throws CloneNotSupportedException {
        FormInputInitPrefarence ret = new FormInputInitPrefarence();

        try {
            BeanUtils.copyProperties(ret, this);
        } catch (Exception e) {
            log__.error("クローン作成に失敗", e);
        }

        ret.setTempDir(tempDir__.clone());

        return ret;

    }

    /**
     * <p>mode を取得します。
     * @return mode
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#mode__
     */
    public int getMode() {
        return mode__;
    }


    /**
     * <p>mode をセットします。
     * @param mode mode
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#mode__
     */
    public void setMode(int mode) {
        mode__ = mode;
    }


    /**
     * <p>url を取得します。
     * @return url
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#url__
     */
    public String getUrl() {
        return url__;
    }


    /**
     * <p>url をセットします。
     * @param url url
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#url__
     */
    public void setUrl(String url) {
        url__ = url;
    }


    /**
     * <p>appRoot を取得します。
     * @return appRoot
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#appRoot__
     */
    public String getAppRoot() {
        return appRoot__;
    }


    /**
     * <p>appRoot をセットします。
     * @param appRoot appRoot
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#appRoot__
     */
    public void setAppRoot(String appRoot) {
        appRoot__ = appRoot;
    }


    /**
     * <p>tempDir を取得します。
     * @return tempDir
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#tempDir__
     */
    public GSTemporaryPathModel getTempDir() {
        return tempDir__;
    }


    /**
     * <p>tempDir をセットします。
     * @param tempDir tempDir
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#tempDir__
     */
    public void setTempDir(GSTemporaryPathModel tempDir) {
        tempDir__ = tempDir;
    }


    /**
     * <p>isLoad を取得します。
     * @return isLoad
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#isLoad__
     */
    public boolean isLoad() {
        return isLoad__;
    }


    /**
     * <p>isLoad をセットします。
     * @param isLoad isLoad
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence#isLoad__
     */
    public void setLoad(boolean isLoad) {
        isLoad__ = isLoad;
    }



}
