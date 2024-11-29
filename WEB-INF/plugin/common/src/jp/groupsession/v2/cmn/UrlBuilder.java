package jp.groupsession.v2.cmn;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] URL取得に関するクラスです。
 * <br>[解  説] マルチドメインを自動判定し付与します。
 * 　　　　　　  パラメータがセットされた場合はパラメータ付URLで取得されます。
 * <br>[備  考]
 *
 * @author JTS
 */
public class UrlBuilder {

    /** リクエスト */
    private RequestModel reqMdl__ = null;
    /** プラグインID */
    private String pluginId__ = null;
    /** 画面ID */
    private String dispId__ = null;


    /** URLパラメータリスト */
    private ArrayList <LabelValueBean> urlParamList__;

    /**
     * <br>[機  能]コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param pluginId プラグインID
     * @param dispId 画面ID（.doはなければ自動追加）
     */
    public UrlBuilder(RequestModel reqMdl, String pluginId, String dispId) {
        reqMdl__ = reqMdl;
        pluginId__ = pluginId;
        dispId__ = dispId;
    }

    /**
     * <p>URLパラメータをListに追加セットします。
     * @param pName パラメータ名
     * @param value 値(int)
     */
    public void addUrlParam(String pName, int value) {
        addUrlParam(pName, String.valueOf(value));
    }

    /**
     * <p>URLパラメータをListに追加セットします。
     * @param pName パラメータ名
     * @param value 値(文字列)
     */
    public void addUrlParam(String pName, String value) {
        if (urlParamList__ == null) {
            urlParamList__ = new ArrayList<LabelValueBean>();
        }
        LabelValueBean mdl = new LabelValueBean();
        mdl.setLabel(pName);
        mdl.setValue(value);
        urlParamList__.add(mdl);
    }


    /**
     * <br>[機  能]URLを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return url 生成したURL
     */
    public String getUrl() {

        //リクエストURLからURLを取得
        String baseUrl = reqMdl__.getRequestURL().toString();
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();

        //基本URLに整形する
        String url;
        try {
            url = urlBiz.getBaseUrl(reqMdl__);
        } catch (URISyntaxException e) {
            url = "";
        }

        StringBuilder sb = new StringBuilder(url);

        //画面ID情報がない場合はベースURLの返す
        if (pluginId__ == null) {
            return sb.toString();
        }

        //プラグインID
        sb.append(pluginId__);
        sb.append("/");

        //画面ID
        sb.append(dispId__);
        if (!dispId__.endsWith(".do")) {
            sb.append(".do");
        }

        //URLパラメータがない場合は画面IDまでURLを返す
        if (urlParamList__ == null || urlParamList__.size() == 0) {
            return sb.toString();
        }

        sb.append("?");

        //URLパラメータ
        int cnt = 0;
        for (LabelValueBean bean : urlParamList__) {
            if (cnt > 0) {
                sb.append("&");
            }
            sb.append(bean.getLabel());
            sb.append("=");
            sb.append(bean.getValue());
            cnt++;
        }

        return sb.toString();
    }
}
