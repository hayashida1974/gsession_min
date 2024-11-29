package jp.groupsession.v2.man.man350;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 メイン画面レイアウト設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man350ParamModel extends AbstractParamModel {
    /** レイアウト設定区分 */
    private int man350kbn__ = GSConstMain.MANSCREEN_LAYOUTKBN_USER;
    /** レイアウト（デフォルトorカスタマイズ） */
    private int man350layout__ = GSConstMain.MANSCREEN_LAYOUT_DEFAULT;

    /** 初期表示フラグ */
    private int man350init__ = 0;
    /** レイアウト 左 */
    private String man350area1__ = null;
    /** レイアウト 右 */
    private String man350area2__ = null;
    /** レイアウト 上 */
    private String man350area3__ = null;
    /** レイアウト 下 */
    private String man350area4__ = null;
    /** レイアウト 中 */
    private String man350area5__ = null;

    /**
     * <p>man350init を取得します。
     * @return man350init
     */
    public int getMan350init() {
        return man350init__;
    }

    /**
     * <p>man350init をセットします。
     * @param man350init man350init
     */
    public void setMan350init(int man350init) {
        man350init__ = man350init;
    }

    /**
     * <p>man350area1 を取得します。
     * @return man350area1
     */
    public String getMan350area1() {
        return man350area1__;
    }

    /**
     * <p>man350area1 をセットします。
     * @param man350area1 man350area1
     */
    public void setMan350area1(String man350area1) {
        man350area1__ = man350area1;
    }

    /**
     * <p>man350area2 を取得します。
     * @return man350area2
     */
    public String getMan350area2() {
        return man350area2__;
    }

    /**
     * <p>man350area2 をセットします。
     * @param man350area2 man350area2
     */
    public void setMan350area2(String man350area2) {
        man350area2__ = man350area2;
    }

    /**
     * <p>man350area3 を取得します。
     * @return man350area3
     */
    public String getMan350area3() {
        return man350area3__;
    }

    /**
     * <p>man350area3 をセットします。
     * @param man350area3 man350area3
     */
    public void setMan350area3(String man350area3) {
        man350area3__ = man350area3;
    }

    /**
     * <p>man350area4 を取得します。
     * @return man350area4
     */
    public String getMan350area4() {
        return man350area4__;
    }

    /**
     * <p>man350area4 をセットします。
     * @param man350area4 man350area4
     */
    public void setMan350area4(String man350area4) {
        man350area4__ = man350area4;
    }

    /**
     * <p>man350area5 を取得します。
     * @return man350area5
     */
    public String getMan350area5() {
        return man350area5__;
    }

    /**
     * <p>man350area5 をセットします。
     * @param man350area5 man350area5
     */
    public void setMan350area5(String man350area5) {
        man350area5__ = man350area5;
    }

    /**
     * <p>man350kbn を取得します。
     * @return man350kbn
     */
    public int getMan350kbn() {
        return man350kbn__;
    }

    /**
     * <p>man350kbn をセットします。
     * @param man350kbn man350kbn
     */
    public void setMan350kbn(int man350kbn) {
        man350kbn__ = man350kbn;
    }

    /**
     * <p>man350layout を取得します。
     * @return man350layout
     */
    public int getMan350layout() {
        return man350layout__;
    }

    /**
     * <p>man350layout をセットします。
     * @param man350layout man350layout
     */
    public void setMan350layout(int man350layout) {
        man350layout__ = man350layout;
    }
}