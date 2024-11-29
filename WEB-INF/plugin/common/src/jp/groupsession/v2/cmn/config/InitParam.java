package jp.groupsession.v2.cmn.config;

/**
 * <br>[機  能] リクエストに付加するパラメータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class InitParam {

    /** パラメータ名 */
    private String paramname__ = null;
    /** パラメータ値 */
    private String paramvalue__ = null;

    /**
     * @return paramname を戻します。
     */
    public String getParamname() {
        return paramname__;
    }
    /**
     * @param paramname 設定する paramname。
     */
    public void setParamname(String paramname) {
        paramname__ = paramname;
    }
    /**
     * @return paramvalue を戻します。
     */
    public String getParamvalue() {
        return paramvalue__;
    }
    /**
     * @param paramvalue 設定する paramvalue。
     */
    public void setParamvalue(String paramvalue) {
        paramvalue__ = paramvalue;
    }

}
