package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] トップメニュー情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TopMenuInfo {

    /** 表示/非表示 */
    private String view__ = null;
    /** URL */
    private String url__ = null;
    /** 並び順 */
    private String order__ = null;
    /** 別ウィンドウで開くか   0:規定 1:別ウィンドウ*/
    private String target__ = null;
    /** バイナリSID */
    private Long binSid__ = Long.valueOf(0);
    /** パラメータ設定区分  0:設定しない 1:設定する */
    private int paramKbn__ = 0;
    /** 送信区分  0:POST形式 1:GET形式*/
    private int sendKbn__ = 0;
    /** アイコンURL */
    private String icon__ = null;
    /** アイコンURL（クラシック） */
    private String iconClassic__ = null;

    /**
     * <p>target を取得します。
     * @return target
     */
    public String getTarget() {
        return target__;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>target をセットします。
     * @param target target
     */
    public void setTarget(String target) {
        target__ = target;
    }
    /**
     * <p>url を取得します。
     * @return url
     */
    public String getUrl() {
        return url__;
    }
    /**
     * <p>url をセットします。
     * @param url url
     */
    public void setUrl(String url) {
        url__ = url;
    }
    /**
     * <p>view を取得します。
     * @return view
     */
    public String getView() {
        return view__;
    }
    /**
     * <p>view をセットします。
     * @param view view
     */
    public void setView(String view) {
        view__ = view;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public String getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(String order) {
        order__ = order;
    }
    /**
     * <p>paramKbn を取得します。
     * @return paramKbn
     */
    public int getParamKbn() {
        return paramKbn__;
    }
    /**
     * <p>paramKbn をセットします。
     * @param paramKbn paramKbn
     */
    public void setParamKbn(int paramKbn) {
        paramKbn__ = paramKbn;
    }
    /**
     * <p>sendKbn を取得します。
     * @return sendKbn
     */
    public int getSendKbn() {
        return sendKbn__;
    }
    /**
     * <p>sendKbn をセットします。
     * @param sendKbn sendKbn
     */
    public void setSendKbn(int sendKbn) {
        sendKbn__ = sendKbn;
    }
    /**
     * <p>icon を取得します。
     * @return icon
     * @see jp.groupsession.v2.cmn.config.TopMenuInfo#icon__
     */
    public String getIcon() {
        return icon__;
    }
    /**
     * <p>icon をセットします。
     * @param icon icon
     * @see jp.groupsession.v2.cmn.config.TopMenuInfo#icon__
     */
    public void setIcon(String icon) {
        icon__ = icon;
    }
    /**
     * <p>iconClassic を取得します。
     * @return iconClassic
     * @see jp.groupsession.v2.cmn.config.TopMenuInfo#iconClassic__
     */
    public String getIconClassic() {
        return iconClassic__;
    }
    /**
     * <p>iconClassic をセットします。
     * @param iconClassic iconClassic
     * @see jp.groupsession.v2.cmn.config.TopMenuInfo#iconClassic__
     */
    public void setIconClassic(String iconClassic) {
        iconClassic__ = iconClassic;
    }

}
