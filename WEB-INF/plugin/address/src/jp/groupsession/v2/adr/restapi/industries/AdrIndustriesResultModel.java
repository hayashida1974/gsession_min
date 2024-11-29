package jp.groupsession.v2.adr.restapi.industries;

import jp.groupsession.v2.adr.restapi.model.IndustryInfo;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

/**
 * <br>[機  能] アドレス帳 業種一覧取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ResponceModel(targetField = {
            "sid",
            "name"})
public class AdrIndustriesResultModel extends IndustryInfo {
}
