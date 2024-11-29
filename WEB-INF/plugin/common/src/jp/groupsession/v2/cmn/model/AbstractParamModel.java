package jp.groupsession.v2.cmn.model;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] パラメータ、および出力値を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class AbstractParamModel implements Serializable {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractParamModel.class);

    /**
     * <br>[機  能] フォームパラメータをコピーする
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setParam(Object form) {
        try {
            BeanUtils.copyProperties(this, form);
        } catch (Exception e) {
            log__.error("フォームパラメータのコピーに失敗", e);
        }
    }
    /**
     * <br>[機  能] Modelの出力値をフォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     */
    public void setFormData(Object form) {
        try {
            BeanUtils.copyProperties(form, this);
        } catch (Exception e) {
            log__.error("Modelの出力値をフォームへ設定する際、例外発生", e);
        }
    }
}
