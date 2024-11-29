package jp.groupsession.v2.cmn.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メール暗号化に関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MailEncryptBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MailEncryptBiz.class);

    /** 暗号化なし */
    public static final int ANGO_NONE = 0;
    /** 暗号化:SSL/TLS */
    public static final int ANGO_SSL_TLS = 1;
    /** 暗号化:STARTTLS */
    public static final int ANGO_STARTTLS = 2;

    /** 種類 */
    public static final int[] ANGO_TYPE = {
            ANGO_NONE,
            ANGO_SSL_TLS,
            ANGO_STARTTLS
            };

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public MailEncryptBiz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public MailEncryptBiz() {

    }

    /**
     * <br>[機  能] 画面表示用のラベルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @return ラベル
     */
    public List<LabelValueBean> setDspProtocolLabels() {
        List<LabelValueBean> labels = new ArrayList<LabelValueBean>();
        int[] protocols = ANGO_TYPE;
       for (int i = 0; i < protocols.length; i++) {
           LabelValueBean bean = new LabelValueBean();
           bean.setValue(String.valueOf(protocols[i]));
           bean.setLabel(getProtocolName(protocols[i]));
           labels.add(bean);
       }
        return labels;
    }

    /**
     * <br>[機  能] 暗号化プロトコル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param type プロトコル種類
     * @return プロトコル名
     */
    public String getProtocolName(int type) {
        String name = null;
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (type) {
        // SSL_TLS
        case ANGO_SSL_TLS:
            name = gsMsg.getMessage("cmn.ssl.tls");
            break;
        // STARTTLS
        case ANGO_STARTTLS:
            name = gsMsg.getMessage("cmn.start.tls");
            break;
        // なし
        default :
            name = gsMsg.getMessage("cmn.no");
            break;
        }
        return name;
    }

    /**
     * <br>[機  能] 選択された暗号化プロトコルが存在するか確認
     * <br>[解  説]
     * <br>[備  考]
     * @param type 種類
     * @return true:存在する
     */
    public boolean isExistProtocol(int type) {
        for (int value : ANGO_TYPE) {
            if (type == value) {
                return true;
            }
        }
        return false;
    }


}
