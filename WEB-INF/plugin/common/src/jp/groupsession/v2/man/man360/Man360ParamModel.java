package jp.groupsession.v2.man.man360;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 個人設定 メイン画面レイアウト設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man360ParamModel extends AbstractParamModel {
    /** レイアウト（デフォルトorカスタマイズ） */
    private int man360layout__ = GSConstMain.MANSCREEN_LAYOUT_DEFAULT;

    /** 初期表示フラグ */
    private int man360init__ = 0;
    /** レイアウト 左 */
    private String man360area1__ = null;
    /** レイアウト 右 */
    private String man360area2__ = null;
    /** レイアウト 上 */
    private String man360area3__ = null;
    /** レイアウト 下 */
    private String man360area4__ = null;
    /** レイアウト 中 */
    private String man360area5__ = null;

    /**
     * <p>man360layout を取得します。
     * @return man360layout
     */
    public int getMan360layout() {
        return man360layout__;
    }

    /**
     * <p>man360layout をセットします。
     * @param man360layout man360layout
     */
    public void setMan360layout(int man360layout) {
        man360layout__ = man360layout;
    }

    /**
     * <p>man360init を取得します。
     * @return man360init
     */
    public int getMan360init() {
        return man360init__;
    }

    /**
     * <p>man360init をセットします。
     * @param man360init man360init
     */
    public void setMan360init(int man360init) {
        man360init__ = man360init;
    }

    /**
     * <p>man360area1 を取得します。
     * @return man360area1
     */
    public String getMan360area1() {
        return man360area1__;
    }

    /**
     * <p>man360area1 をセットします。
     * @param man360area1 man360area1
     */
    public void setMan360area1(String man360area1) {
        man360area1__ = man360area1;
    }

    /**
     * <p>man360area2 を取得します。
     * @return man360area2
     */
    public String getMan360area2() {
        return man360area2__;
    }

    /**
     * <p>man360area2 をセットします。
     * @param man360area2 man360area2
     */
    public void setMan360area2(String man360area2) {
        man360area2__ = man360area2;
    }

    /**
     * <p>man360area3 を取得します。
     * @return man360area3
     */
    public String getMan360area3() {
        return man360area3__;
    }

    /**
     * <p>man360area3 をセットします。
     * @param man360area3 man360area3
     */
    public void setMan360area3(String man360area3) {
        man360area3__ = man360area3;
    }

    /**
     * <p>man360area4 を取得します。
     * @return man360area4
     */
    public String getMan360area4() {
        return man360area4__;
    }

    /**
     * <p>man360area4 をセットします。
     * @param man360area4 man360area4
     */
    public void setMan360area4(String man360area4) {
        man360area4__ = man360area4;
    }

    /**
     * <p>man360area5 を取得します。
     * @return man360area5
     */
    public String getMan360area5() {
        return man360area5__;
    }

    /**
     * <p>man360area5 をセットします。
     * @param man360area5 man360area5
     */
    public void setMan360area5(String man360area5) {
        man360area5__ = man360area5;
    }
}