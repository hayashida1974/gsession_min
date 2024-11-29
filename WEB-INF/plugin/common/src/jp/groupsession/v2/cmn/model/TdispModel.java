package jp.groupsession.v2.cmn.model;

import java.lang.reflect.InvocationTargetException;

import jp.groupsession.v2.cmn.model.base.CmnTdispModel;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * トップ表示設定にjava.lang.Comparable#compareTo(T o)を実装したJavaBean
 * @author JTS
 *
 */
/**
 * <br>[機  能] トップ表示設定にjava.lang.Comparable#compareTo(T o)を実装したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TdispModel extends CmnTdispModel implements Comparable<TdispModel> {

    /** プラグイン名称 */
    private String pluginName__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param model CMN_TDISP Model
     * @throws IllegalAccessException パラメータの設定に失敗
     * @throws InvocationTargetException パラメータの設定に失敗
     * @throws NoSuchMethodException パラメータの設定に失敗
     */
    public TdispModel(CmnTdispModel model)
    throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyUtils.copyProperties(this, model);
    }

    /**
     * <br>[機  能] このオブジェクトと指定されたオブジェクトの順序を比較します。
     * <br>[解  説] 表示順を元に比較を行います。
     * <br>[備  考]
     *
     * @param o 比較対象のオブジェクト
     * @return 比較結果
     */
    public int compareTo(TdispModel o) {
        return getTdpOrder() - o.getTdpOrder();
    }

    /**
     * <p>pluginName を取得します。
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }

}
