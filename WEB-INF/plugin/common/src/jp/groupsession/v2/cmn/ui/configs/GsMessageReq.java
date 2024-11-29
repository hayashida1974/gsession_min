package jp.groupsession.v2.cmn.ui.configs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] メッセージリクエスト
 * <br>[解  説] 後でロケール反映したメッセージ取得を行うためのメッセージモデルクラス
 * <br>[備  考] GsMessageReq.union(GsMessageReq, String, GsMessageReq)で複数メッセージの結合に対応
 *
 * @author JTS
 */
public class GsMessageReq {
    /** メッセージキー*/
    private final String msgKey__;
    /** パラメータ*/
    private final String[] params__;
    /** 結合メッセージ*/
    private List<GsMessageReq> union__ = new ArrayList<>();
    /** 接続文字列*/
    private List<String> connecterList__ = new ArrayList<>();

    public GsMessageReq(String msgKey, String[] params) {
        msgKey__ = msgKey;
        params__ = params;
        if (params == null) {
            params = new String[0];
        }
    }
    /**
     *
     * <br>[機  能] メッセージを結合する
     * <br>[解  説]
     * <br>[備  考]
     * @param connecter 接続語
     * @param msgReq 追加メッセージ
     * @return 結合後のメッセージリクエスト
     */
    public GsMessageReq union(String connecter, GsMessageReq msgReq) {
        return GsMessageReq.union(msgReq, connecter, msgReq);
    }
    /**
     *
     * <br>[機  能] メッセージを結合する
     * <br>[解  説]
     * <br>[備  考]
     * @param srcReq ベース
     * @param connecter 接続語
     * @param msgReq 追加メッセージ
     * @return 結合後のメッセージリクエスト
     */
    public static GsMessageReq union(GsMessageReq srcReq, String connecter, GsMessageReq msgReq) {
        GsMessageReq ret = new GsMessageReq(srcReq.msgKey__, srcReq.params__);
        ret.union__.addAll(srcReq.union__);
        ret.connecterList__.addAll(srcReq.connecterList__);

        connecter = NullDefault.getString(connecter, "");
        ret.connecterList__.add(connecter);

        ret.union__.add(new GsMessageReq(msgReq.msgKey__, msgReq.params__));
        ret.union__.addAll(msgReq.union__);
        ret.connecterList__.addAll(msgReq.connecterList__);
        return ret;
    }

    /**
     *
     * <br>[機  能] ロケール反映したメッセージの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg
     * @return ロケール反映したメッセージ
     */
    public String getMessage(GsMessage gsMsg) {
        StringBuilder sb = new StringBuilder();
        sb.append(gsMsg.getMessage(msgKey__, params__));

        Iterator<String> conIt = connecterList__.iterator();
        Iterator<GsMessageReq> reqIt = union__.iterator();
        while (conIt.hasNext() && reqIt.hasNext()) {
            sb.append(conIt.next());
            sb.append(reqIt.next().getMessage(gsMsg));
        }

        return sb.toString();
    }
    /**
    *
    * <br>[機  能] ロケール反映したメッセージの生成
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @return ロケール反映したメッセージ
    */
   public String getMessage(HttpServletRequest req) {
       GsMessage gsMsg = new GsMessage(req);
       StringBuilder sb = new StringBuilder();
       sb.append(gsMsg.getMessage(msgKey__, params__));

       Iterator<String> conIt = connecterList__.iterator();
       Iterator<GsMessageReq> reqIt = union__.iterator();
       while (conIt.hasNext() && reqIt.hasNext()) {
           sb.append(conIt.next());
           sb.append(reqIt.next().getMessage(gsMsg));
       }

       return sb.toString();
   }


}
