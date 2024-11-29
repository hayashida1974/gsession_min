package jp.groupsession.v2.man.man360;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


/**
 * <br>[機  能] メイン 個人設定 メイン画面レイアウト設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man360Form extends AbstractGsForm {

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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textLayout = gsMsg.getMessage("ptl.5");

        //レイアウト全非表示
        if (man360layout__ == GSConstMain.MANSCREEN_LAYOUT_CUSTOM) {

            if (NullDefault.getInt(man360area1__, 0) == 0
                    && NullDefault.getInt(man360area2__, 0) == 0
                    && NullDefault.getInt(man360area3__, 0) == 0
                    && NullDefault.getInt(man360area4__, 0) == 0
                    && NullDefault.getInt(man360area5__, 0) == 0) {

                msg = new ActionMessage("error.select.required.text", textLayout);
                StrutsUtil.addMessage(errors, msg, "man350area");

            }

        }

        return errors;
    }

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
