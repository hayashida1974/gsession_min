package jp.groupsession.v2.restapi.controller;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.CloneableUtil;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.biz.apiconnect.EnumContentType;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] RESTAPI リクエスト関連情報取得 コンテキスト
 * <br>[解  説] AbstractRestApiAction.doMethod プロセス内で実行可能
 * <br>[備  考]
 *
 * @author JTS
 */
public final class RestApiContext implements AutoCloseable {

    /** requestLocalキー RESTAPI*/
    private static final Object LOCALKEY_INIT = new Object();

    /** ACCEPT 列挙型*/
    private static EnumSet<EnumContentType> acceptTypeSet =
            EnumSet.of(
                    EnumContentType.APPLICATION_JSON,
                    EnumContentType.APPLICATION_XML,
                    EnumContentType.APPLICATION_XML_DTD,
                    EnumContentType.APPLICATION_XML_EXTERNAL_PARSED_ENTITY,
                    EnumContentType.TEXT_XML,
                    EnumContentType.TEXT_XML_EXTERNAL_PARSED_ENTITY
                    );


    /** パラメータオブジェクト*/
    private final Builder param__;
    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    protected static Builder _builder() {
        return new Builder();
    }
    /**
     *
     * <br>[機  能] RestApiResponceWriter ビルダー
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder implements Cloneable {
        /** コネクション*/
        private Connection con__;
        /** ActionMap*/
        private ActionMapping map__;
        /** リクエストモデル*/
        private RequestModel reqMdl__;
        /** コンテキストパス*/
        private String contextPath__;
        /** API URL*/
        private String apiUrl__;
        /** PLUGINCONFIG*/
        private PluginConfig pconfig__;
        /** メッセージリソース*/
        private MessageResources messageResources__;
        /** 要求されたResuponceType*/
        private EnumContentType acceptType__;
        /** アプリケーションルートパス*/
        private String appRootPath__;
        /**
         * <p>con をセットします。
         * @param con con
         * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#con__
         * @return this
         */
        public Builder setCon(Connection con) {
            con__ = con;
            return this;
        }
        /**
         * <p>map をセットします。
         * @param map map
         * @return this
         * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#map__
         */
        public Builder setMap(ActionMapping map) {
            map__ = map;
            return this;
        }
        /**
         * <p>reqMdl をセットします。
         * @param reqMdl reqMdl
         * @return this
         * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#reqMdl__
         */
        public Builder setReqMdl(RequestModel reqMdl) {
            reqMdl__ = reqMdl;
            return this;
        }
        /**
         * <p>pconfig をセットします。
         * @param pconfig pconfig
         * @return this
         * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#pconfig__
         */
        public Builder setPconfig(PluginConfig pconfig) {
            pconfig__ = pconfig;
            return this;
        }
        /**
         * <p>messageResources をセットします。
         * @param messageResources messageResources
         * @return this
         * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#messageResources__
         */
        public Builder setMessageResources(MessageResources messageResources) {
            messageResources__ = messageResources;
            return this;
        }

        /**
         * <p>acceptType をセットします。
         * @param acceptType acceptType
         * @return this
         * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#acceptType__
         */
        private Builder __setAcceptType(String acceptType) {
            String typeStr = Optional.ofNullable(acceptType)
                .map(a -> {
                    if (a.indexOf(";") >= 0) {
                        return a.substring(0, a.indexOf(";"));
                    }
                    return a;
                })
                .orElse("");

            acceptType__ = acceptTypeSet.stream()
                .filter(content -> Objects.equals(
                        content.getLabel(),
                        typeStr))
                .findAny()
                .orElse(EnumContentType.APPLICATION_JSON);
            return this;
        }
        /**
         * <p>appRootPath をセットします。
         * @param appRootPath appRootPath
         * @return this
         * @see jp.groupsession.v2.restapi.Builder#appRootPath__
         */
        public Builder setAppRootPath(String appRootPath) {
            appRootPath__ = appRootPath;
            return this;
        }
        private Builder() {
        }
        @Override
        public Builder clone() {
            Builder ret = new Builder();
            CloneableUtil.copyField(ret, this);
            return ret;
        }
        /**
         *
         * <br>[機  能] RestApiResponceWriter インスタンス生成
         * <br>[解  説]
         * <br>[備  考]
         * @param req リクエスト
         * @return RestApiActionUtil
         */
        public RestApiContext build(HttpServletRequest req) {
            if (RequestLocal.containsKey(LOCALKEY_INIT)) {
                throw new RuntimeException("二重実行エラー");
            }
            RestApiContext ret = new RestApiContext(req, this.clone());
            RequestLocal.put(LOCALKEY_INIT, ret);
            return ret;
        }
    }
    /**
     *
     * <br>[機  能] インスタンス取得
     * <br>[解  説]
     * <br>[備  考]
     * @return シングルトンインスタンス
     */
    protected static RestApiContext _getInstance() {
        if (!RequestLocal.containsKey(LOCALKEY_INIT)) {
            throw new RuntimeException("初期化前の実行エラー");
        }
        return RequestLocal.get(LOCALKEY_INIT, RestApiContext.class);

    }
    /**
     *
     * コンストラクタ
     * @param req
     * @param clone パラメータ
     */
    private RestApiContext(HttpServletRequest req, Builder clone) {
        param__ = clone;
        param__.__setAcceptType(req.getHeader("Accept"));
        param__.contextPath__ = req.getContextPath();
        param__.apiUrl__ = __createApiUrl();

    }
    /**
     *
     * <br>[機  能] RESTAPI リクエスト関連情報取得ユーティリテルビルダー
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    protected static Builder builder() {
        return new Builder();
    }
    /**
     * リクエストモデルを返す
     * 都度生成せず、キャッシュしたモデルが再利用される
     * @return リクエストモデル
     */
    public RequestModel getRequestModel() {
        return param__.reqMdl__;
    }

    /**
     *
     * <br>[機  能] リクエストからユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ユーザ情報
     */
    public BaseUserModel getRequestUserModel() {
        return getRequestModel().getSmodel();
    }
    /**
     *
     * <br>[機  能] リクエストからユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ユーザSID
     */
    public int getRequestUserSid() {
        return getRequestModel().getSmodel().getUsrsid();
    }
    /**
     *
     * <br>[機  能] 対象ユーザのプラグインコンフィグを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return プラグインコンフィグ
     */
    public PluginConfig getPluginConfig() {
        return param__.pconfig__;

    }
    /**
     * <p>con を取得します。
     * @return con
     * @see jp.groupsession.v2.restapi.controller.RestApiContext#con__
     */
    public Connection getCon() {
        return param__.con__;
    }
    /**
     * <p>map を取得します。
     * @return map
     * @see jp.groupsession.v2.restapi.controller.RestApiContext#map__
     */
    public ActionMapping getMap() {
        return param__.map__;
    }
    /**
     * <p>contextPath を取得します。
     * @return contextPath
     * @see jp.groupsession.v2.restapi.controller.RestApiContext#contextPath__
     */
    public String getContextPath() {
        return param__.contextPath__;
    }
    /**
     * <p>apiUrl を取得します。
     * @return apiUrl
     * @see jp.groupsession.v2.restapi.controller.RestApiContext#apiUrl__
     */
    public String getApiUrl() {
        return param__.apiUrl__;
    }
    /**
     * <p>messageResources を取得します。
     * @return messageResources
     * @see jp.groupsession.v2.restapi.controller.RestApiContext#messageResources__
     */
    public MessageResources getMessageResources() {
        return param__.messageResources__;
    }
    /**
     * <p>acceptType を取得します。
     * @return acceptType
     * @see jp.groupsession.v2.restapi.controller.RestApiContext.Builder#acceptType__
     */
    public EnumContentType getAcceptType() {
        return param__.acceptType__;
    }
    /**
     * <p>appRootPath を取得します。
     * @return appRootPath
     * @see jp.groupsession.v2.restapi.Builder#appRootPath__
     */
    public String getAppRootPath() {
        return param__.appRootPath__;
    }

    /**
     * <br>[機  能] APIのURLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return APIのURL
     */
    private String __createApiUrl() {
        StringBuilder buf = new StringBuilder();
        String cpath = getContextPath();
        if (cpath != null) {
            buf.append(cpath);
        }
        buf.append("/restapi");

        String mapPath = getMap().getPath();
        if (mapPath != null) {
            buf.append(mapPath);
        }
        return buf.toString();
    }


    @Override
    public void close() throws Exception {
        for (Field fld : getClass().getFields()) {
            if (AutoCloseable.class.isAssignableFrom(fld.getType())) {
                ((AutoCloseable) fld.get(this)).close();
            }
        }
    }
}
