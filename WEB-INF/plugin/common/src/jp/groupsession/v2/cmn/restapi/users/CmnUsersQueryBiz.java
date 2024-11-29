package jp.groupsession.v2.cmn.restapi.users;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.UserQueryModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.restapi.users.authentications.CmnUsersResultCreator;
import jp.groupsession.v2.restapi.controller.RestApiContext;

/**
 * <br>[機  能] ユーザ検索ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnUsersQueryBiz {
    /** パラメータ*/
    private final CmnUsersQueryParam param__;
    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 取得結果*/
    private List<CmnUsersResultModel> result__ = new ArrayList<>();
    /** 結果 最大件数*/
    private int resultMaxCount__ = 0;

    /**
     *
     * コンストラクタ
     * @param param パラメータ
     * @param ctx コンテキスト
     */
    public CmnUsersQueryBiz(CmnUsersQueryParam param, RestApiContext ctx) {
        param__ = param;
        ctx__ = ctx;
    }

    /**
     *
     * <br>[機  能] 検索の実行
     * <br>[解  説]
     * <br>[備  考]
     */
    public void execute() {
        UserSearchDao udao = new UserSearchDao(ctx__.getCon());
        UserQueryModel qmdl = param__.getQueryUsr();

        try {
            resultMaxCount__ = udao.getSyousaiSearchCount(qmdl);
            if (resultMaxCount__ < qmdl.getOffset()) {
                result__ = List.of();
                return;
            }

            //ソート1キー = 表示設定値の場合、管理者設定 並び順に定められたソート順を使用する
            if (qmdl.getSortKeyType() <= 0) {
                CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(ctx__.getCon());
                CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

                qmdl.setSortKeyType(UserBiz.getSortKey(sortMdl.getCscUserSkey1()));
                qmdl.setSortOrderFlg(sortMdl.getCscUserOrder1());
                qmdl.setSortKey2Type(UserBiz.getSortKey(sortMdl.getCscUserSkey2()));
                qmdl.setSortOrder2Flg(sortMdl.getCscUserOrder2());
            }

            CmnUsersResultCreator resultCreater =
                CmnUsersResultCreator
                .builder(ctx__.getCon())
                .putUsrSid(udao.getSyousaiSearchList(qmdl)
                        .stream()
                        .map(usr -> usr.getUsrSid())
                        .collect(Collectors.toList())
                        )
                .setAccsessUsrSid(ctx__.getRequestUserSid())
                .build();
            resultCreater.execute(ctx__.getCon());
            result__.addAll(
                resultCreater.getMaskingResultList()
            );
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行に失敗", e);
        }

    }
    /**
     *
     * <br>[機  能] 検索結果 取得一覧を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 検索結果
     */
    public Collection<CmnUsersResultModel> resultUsr() {
        return result__.stream()
                .collect(Collectors.toList());
    }

    /**
     *
     * <br>[機  能] 検索結果 最大件数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 検索結果
     */
    public Integer getMaxCount() {
        return resultMaxCount__;
    }

}
