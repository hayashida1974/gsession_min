package jp.groupsession.v2.man.restapi.informations;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] インフォメーション取得 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManInformationGetBiz {
    /**
     *
     * <br>[機  能] インフォメーション取得
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     * @param svc サーブレットコンテキスト
     * @return インフォメーション
     */
    public List<ManInformationResultModel> execute(RestApiContext ctx, ServletContext svc) {
        CommonBiz cmnBiz = new CommonBiz();

        //インフォーメーション情報を取得
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        List<MainInfoMessageModel> infoList = null;
        try {
            infoList = cmnBiz.getInfoMessageList(
                    gsMsg, ctx.getRequestModel(),
                    ctx.getCon(),
                    ctx.getRequestUserModel(),
                    ctx.getPluginConfig(),
                    svc);


        } catch (Exception e) {
            throw new RuntimeException("インフォメーション取得 実行時例外", e);
        }

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();

        return infoList.stream()
                .map(info -> {
                    ManInformationResultModel mdl = new ManInformationResultModel();
                    mdl.setMessageText(info.getOriginalMessage());
                    mdl.setPluginId(info.getPluginId());
                    String url = Optional.ofNullable(info.getLinkUrl())
                                .orElse("");
                    //独自追加インフォメーションURLの変換
                    if (url.startsWith("return openMainInfoWindow")) {
                        url = String.format("../main/man310.do?imssid=%s",
                                url.substring(
                                        url.indexOf("(") + 1,
                                        url.indexOf(")")));
                    }
                    //相対URLを絶対パスに変換
                    if (url.startsWith("../")) {
                        url = url.substring("../".length());
                    }
                    try {
                        url = String.format("%s%s",
                                urlBiz.getBaseUrl(ctx.getRequestModel()),
                                url);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException("URL 生成設定例外", e);
                    }

                    mdl.setUrlText(url);
                    return mdl;
                })
                .collect(Collectors.toList());

    }

}
