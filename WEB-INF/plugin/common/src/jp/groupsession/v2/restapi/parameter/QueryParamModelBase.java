package jp.groupsession.v2.restapi.parameter;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Default;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
/**
 *
 * <br>[機  能] RestApi パラメータモデルクラス
 * <br>[解  説] アクションごとに継承し、必要なパラメータを足すこと
 * <br>[備  考]
 *
 * @author JTS
 */
public class QueryParamModelBase extends AbstractParamModel {
    /** 1アクセスで取得する件数*/
    @Default("50")
    @MaxValue(100)
    @MinValue(1)
    private int limit__;
    /** 取得開始する先頭(0)からの位置*/
    private int offset__;
    /** ソート1キー 区分値*/
    private int sortKeyType__;
    /** ソート1 0:昇順,1降順*/
    @Selectable({
        "" + GSConst.ORDER_KEY_ASC,
        "" + GSConst.ORDER_KEY_DESC
    })
    private int sortOrderFlg__;
    /** ソート2キー 区分値*/
    private int sortKey2Type__;
    /** ソート2 0:昇順,1降順*/
    @Selectable({
        "" + GSConst.ORDER_KEY_ASC,
        "" + GSConst.ORDER_KEY_DESC
    })
    private int sortOrder2Flg__;
    /**
     * <p>limit を取得します。
     * @return limit
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#limit__
     */
    public int getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#limit__
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
    /**
     * <p>offset を取得します。
     * @return offset
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#offset__
     */
    public int getOffset() {
        return offset__;
    }
    /**
     * <p>offset をセットします。
     * @param offset offset
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#offset__
     */
    public void setOffset(int offset) {
        offset__ = offset;
    }
    /**
     * <p>sortKeyType を取得します。
     * @return sortKeyType
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortKeyType__
     */
    public int getSortKeyType() {
        return sortKeyType__;
    }
    /**
     * <p>sortKeyType をセットします。
     * @param sortKeyType sortKeyType
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortKeyType__
     */
    public void setSortKeyType(int sortKeyType) {
        sortKeyType__ = sortKeyType;
    }
    /**
     * <p>sortOrderFlg を取得します。
     * @return sortOrderFlg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortOrderFlg__
     */
    public int getSortOrderFlg() {
        return sortOrderFlg__;
    }
    /**
     * <p>sortOrderFlg をセットします。
     * @param sortOrderFlg sortOrderFlg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortOrderFlg__
     */
    public void setSortOrderFlg(int sortOrderFlg) {
        sortOrderFlg__ = sortOrderFlg;
    }
    /**
     * <p>sortKey2Type を取得します。
     * @return sortKey2Type
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortKey2Type__
     */
    public int getSortKey2Type() {
        return sortKey2Type__;
    }
    /**
     * <p>sortKey2Type をセットします。
     * @param sortKey2Type sortKey2Type
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortKey2Type__
     */
    public void setSortKey2Type(int sortKey2Type) {
        sortKey2Type__ = sortKey2Type;
    }
    /**
     * <p>sortOrder2Flg を取得します。
     * @return sortOrder2Flg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortOrder2Flg__
     */
    public int getSortOrder2Flg() {
        return sortOrder2Flg__;
    }
    /**
     * <p>sortOrder2Flg をセットします。
     * @param sortOrder2Flg sortOrder2Flg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#sortOrder2Flg__
     */
    public void setSortOrder2Flg(int sortOrder2Flg) {
        sortOrder2Flg__ = sortOrder2Flg;
    }

}
