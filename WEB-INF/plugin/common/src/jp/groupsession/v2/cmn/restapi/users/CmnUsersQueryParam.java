package jp.groupsession.v2.cmn.restapi.users;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.UserQueryModel;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.Default;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.usr.model.EnumSeibetu;

/**
 * <br>[機  能] ユーザ情報検索 パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class CmnUsersQueryParam  {
    /** 検索 パラメータ*/
    private final UserQueryModel queryUsr__ = new UserQueryModel();
    /** ソートキー１*/
    private EnumSortKey sortKeyType__;
    /** ソートキー２*/
    private EnumSortKey sortKey2Type__;

    /**
     * @return groupId
     * @see UserQueryModel#getGroupId()
     */
    @MaxLength(50)
    @TextField
    public String getGroupId() {
        return queryUsr__.getGroupId();
    }

    /**
     * @param groupId
     * @see UserQueryModel#setGroupId(java.lang.Integer)
     */
    public void setGroupId(String groupId) {
        queryUsr__.setGroupId(groupId);
    }

    /**
     * @return myGroupSid
     * @see UserQueryModel#getMyGroupSid()
     */
    @MaxLength(50)
    @TextField
    public Integer getMyGroupSid() {
        return queryUsr__.getMyGroupSid();
    }

    /**
     * @param myGroupSid
     * @see UserQueryModel#setMyGroupSid(java.lang.Integer)
     */
    public void setMyGroupSid(Integer myGroupSid) {
        queryUsr__.setMyGroupSid(myGroupSid);
    }

    /**
     * @return seimeiKnStartText
     * @see UserQueryModel#getSeimeiKnStartText()
     */
    @MaxLength(1)
    @TextField
    public String getSeimeiKnStartText() {
        return queryUsr__.getSeimeiKnStartText();
    }

    /**
     * @param seimeiKnStartText
     * @see UserQueryModel#setSeimeiKnStartText(java.lang.String)
     */
    public void setSeimeiKnStartText(String seimeiKnStartText) {
        queryUsr__.setSeimeiKnStartText(seimeiKnStartText);
    }

    /**
     * @return shainNoText
     * @see UserQueryModel#getShainNoText()
     */
    @MaxLength(20)
    @TextField
    public String getShainNoText() {
        return queryUsr__.getShainNoText();
    }

    /**
     * @param shainNoText
     * @see UserQueryModel#setShainNoText(java.lang.String)
     */
    public void setShainNoText(String shainNoText) {
        queryUsr__.setShainNoText(shainNoText);
    }

    /**
     * @return seimeiText
     * @see UserQueryModel#getSeimeiText()
     */
    @MaxLength(60)
    @TextField
    public String getSeimeiText() {
        return queryUsr__.getSeimeiText();
    }

    /**
     * @param seimeiText
     * @see UserQueryModel#setSeimeiText(java.lang.String)
     */
    public void setSeimeiText(String seimeiText) {
        queryUsr__.setSeimeiText(seimeiText);
    }

    /**
     * @return seimeiKnText
     * @see UserQueryModel#getSeimeiKnText()
     */
    @MaxLength(120)
    @TextField
    public String getSeimeiKnText() {
        return queryUsr__.getSeimeiKnText();
    }

    /**
     * @param seimeiKnText
     * @see UserQueryModel#setSeimeiKnText(java.lang.String)
     */
    public void setSeimeiKnText(String seimeiKnText) {
        queryUsr__.setSeimeiKnText(seimeiKnText);
    }

    /**
     * @return telText
     * @see UserQueryModel#getTelText()
     */
    @MaxLength(20)
    @TextField
    public String getTelText() {
        return queryUsr__.getTelText();
    }

    /**
     * @param telText
     * @see UserQueryModel#setTelText(java.lang.String)
     */
    public void setTelText(String telText) {
        queryUsr__.setTelText(telText);
    }

    /**
     * @return mailText
     * @see UserQueryModel#getMailText()
     */
    @MaxLength(256)
    @TextField
    public String getMailText() {
        return queryUsr__.getMailText();
    }

    /**
     * @param mailText
     * @see UserQueryModel#setMailText(java.lang.String)
     */
    public void setMailText(String mailText) {
        queryUsr__.setMailText(mailText);
    }

    /**
     * @return seibetuNum
     * @see UserQueryModel#getSeibetuType()
     */
    public EnumSeibetu getSeibetuType() {
        return queryUsr__.getSeibetuType();
    }

    /**
     * @param seibetuNum
     * @see UserQueryModel#setSeibetuType(jp.groupsession.v2.usr.model.EnumSeibetu)
     */
    public void setSeibetuType(EnumSeibetu seibetuNum) {
        queryUsr__.setSeibetuType(seibetuNum);
    }

    /**
     * @return ageFromNum
     * @see UserQueryModel#getAgeFromNum()
     */
    @MaxLength(3)
    @TextField
    public Integer getAgeFromNum() {
        return queryUsr__.getAgeFromNum();
    }

    /**
     * @param ageFromNum
     * @see UserQueryModel#setAgeFromNum(java.lang.Integer)
     */
    public void setAgeFromNum(Integer ageFromNum) {
        queryUsr__.setAgeFromNum(ageFromNum);
    }

    /**
     * @return ageToNum
     * @see UserQueryModel#getAgeToNum()
     */
    @MaxLength(3)
    @TextField
    public Integer getAgeToNum() {
        return queryUsr__.getAgeToNum();
    }

    /**
     * @param ageToNum
     * @see UserQueryModel#setAgeToNum(java.lang.Integer)
     */
    public void setAgeToNum(Integer ageToNum) {
        queryUsr__.setAgeToNum(ageToNum);
    }

    /**
     * @return entranceFromDate
     * @see UserQueryModel#getEntranceFromDate()
     */
    @UDateFormat(Format.DATE_SLUSH)
    public UDate getEntranceFromDate() {
        return queryUsr__.getEntranceFromDate();
    }

    /**
     * @param entranceFromDate
     * @see UserQueryModel#setEntranceFromDate(jp.co.sjts.util.date.UDate)
     */
    public void setEntranceFromDate(UDate entranceFromDate) {
        queryUsr__.setEntranceFromDate(entranceFromDate);
    }

    /**
     * @return entranceToDate
     * @see UserQueryModel#getEntranceToDate()
     */
    @UDateFormat(Format.DATE_SLUSH)
    public UDate getEntranceToDate() {
        return queryUsr__.getEntranceToDate();
    }

    /**
     * @param entranceToDate
     * @see UserQueryModel#setEntranceToDate(jp.co.sjts.util.date.UDate)
     */
    public void setEntranceToDate(UDate entranceToDate) {
        queryUsr__.setEntranceToDate(entranceToDate);
    }

    /**
     * @return postsSid
     * @see UserQueryModel#getPostsSid()
     */
     public Integer getPostsSid() {
        return queryUsr__.getPostsSid();
    }

    /**
     * @param postsSid
     * @see UserQueryModel#setPostsSid(java.lang.Integer)
     */
    public void setPostsSid(Integer postsSid) {
        queryUsr__.setPostsSid(postsSid);
    }

    /**
     * @return todofukenSid
     * @see UserQueryModel#getTodofukenSid()
     */
    @MaxLength(3)
    @TextField
     public Integer getTodofukenSid() {
        return queryUsr__.getTodofukenSid();
    }

    /**
     * @param todofukenSid
     * @see UserQueryModel#setTodofukenSid(java.lang.Integer)
     */
    public void setTodofukenSid(Integer todofukenSid) {
        queryUsr__.setTodofukenSid(todofukenSid);
    }

    /**
     * @return labelSidArray
     * @see UserQueryModel#getLabelSidArray()
     */
    public int[] getLabelSidArray() {
        return queryUsr__.getLabelSidArray();
    }

    /**
     * @param labelSidArray
     * @see UserQueryModel#setLabelSidArray(int[])
     */
    public void setLabelSidArray(int[] labelSidArray) {
        queryUsr__.setLabelSidArray(labelSidArray);
    }


    /**
     * @return limit
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getLimit()
     */
    @Default("50")
    @MaxValue(100)
    @MinValue(1)
    public int getLimit() {
        return queryUsr__.getLimit();
    }

    /**
     * @param limit
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setLimit(int)
     */
    public void setLimit(int limit) {
        queryUsr__.setLimit(limit);
    }

    /**
     * @return offset
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getOffset()
     */
    @Default("0")
    @MinValue(0)
    public int getOffset() {
        return queryUsr__.getOffset();
    }

    /**
     * @param offset
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setOffset(int)
     */
    public void setOffset(int offset) {
        queryUsr__.setOffset(offset);
    }

    /**
     * @return sortKeyType
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getSortKeyType()
     */
    @Default("0")
    public EnumSortKey getSortKeyType() {
        return sortKeyType__;
    }

    /**
     * @param sortKeyType
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setSortKeyType(int)
     */
    public void setSortKeyType(EnumSortKey sortKeyType) {
        sortKeyType__ = sortKeyType;
        queryUsr__.setSortKeyType(sortKeyType.getBindConstValue());
    }

    /**
     * @return sortOrderFlg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getSortOrderFlg()
     */
    @Selectable({
        "" + GSConst.ORDER_KEY_ASC,
        "" + GSConst.ORDER_KEY_DESC
    })
    public int getSortOrderFlg() {
        return queryUsr__.getSortOrderFlg();
    }

    /**
     * @param sortOrderFlg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setSortOrderFlg(int)
     */
    public void setSortOrderFlg(int sortOrderFlg) {
        queryUsr__.setSortOrderFlg(sortOrderFlg);
    }

    /**
     * @return sortKey2Type
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getSortKey2Type()
     */
    @Default("2")
    public EnumSortKey getSortKey2Type() {
        return sortKey2Type__;
    }

    /**
     * @param sortKey2Type
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setSortKey2Type(int)
     */
    public void setSortKey2Type(EnumSortKey sortKey2Type) {
        sortKey2Type__ = sortKey2Type;
        queryUsr__.setSortKey2Type(sortKey2Type.getBindConstValue());
    }

    /**
     * @return sortOrder2Flg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getSortOrder2Flg()
     */
    @Selectable({
        "" + GSConst.ORDER_KEY_ASC,
        "" + GSConst.ORDER_KEY_DESC
    })
    public int getSortOrder2Flg() {
        return queryUsr__.getSortOrder2Flg();
    }

    /**
     * @param sortOrder2Flg
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setSortOrder2Flg(int)
     */
    public void setSortOrder2Flg(int sortOrder2Flg) {
        queryUsr__.setSortOrder2Flg(sortOrder2Flg);
    }

    /**
     * @return keywordText
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getKeywordText()
     */
    @MaxLength(50)
    @TextField
    public String getKeywordText() {
        return queryUsr__.getKeywordText();
    }
    /**
     * @param keywordText
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setKeywordText(String)
     */
    public void setKeywordText(String keywordText) {
        queryUsr__.setKeywordText(keywordText);
    }
    /**
     * @return kanaStartOffsetText
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#getKanaStartOffsetText()
     */
    @MaxLength(1)
    public String getKanaStartOffsetText() {
        return queryUsr__.getKanaStartOffsetText();
    }
    /**
     * @param kanaStartOffsetText
     * @see jp.groupsession.v2.restapi.parameter.QueryParamModelBase#setKanaStartOffsetText(String)
     */
    public void setKanaStartOffsetText(String kanaStartOffsetText) {
        queryUsr__.setKanaStartOffsetText(kanaStartOffsetText);
    }

    @Validator
    public void validate() {

        //ソートキー2 不正な値チェック
        if (getSortKey2Type().getValue() < 1 || getSortKey2Type().getValue() > 6) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.comp.text.either",
                "sortKey2Type",
                "1,2,3,4,5,6")
                .setParamName("sortKey2Type");
        }

        //カナ順取得開始位置 全角カナチェック
        if (!StringUtil.isNullZeroString(getKanaStartOffsetText())
        && !ValidateUtil.isWideKana(getKanaStartOffsetText())) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text", "kanaStartOffsetText")
                .setParamName("kanaStartOffsetText");
        }

        if (getSeimeiKnStartText() != null
                && !GSValidateUtil.isGsWideKana(getSeimeiKnStartText())) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text", "seimeiKnStartText")
                .setParamName("seimeiKnStartText");
        }

        if (getSeimeiKnText() != null
                && !GSValidateUtil.isGsWideKana(getSeimeiKnText())) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text", "seimeiKnText")
                .setParamName("seimeiKnText");

        }


        //年齢開始 大小
        if (getAgeFromNum() != null
                && getAgeToNum() != null
                && getAgeFromNum() > getAgeToNum()) {
            throw new RestApiValidateException(
                    EnumError.PARAM_COLLABORATION,
                    "error.input.number.over",
                    "ageFromTo",
                    "ageFromNum")
                .setParamName("ageFromTo",
                    "ageFromNum")
                ;
        }
        //入社年月日 開始 大小
        if (getEntranceFromDate() != null
                && getEntranceToDate() != null
                && getEntranceFromDate().compare(
                        getEntranceToDate(),
                        getEntranceFromDate()) == UDate.LARGE) {
            throw new RestApiValidateException(
                    EnumError.PARAM_COLLABORATION,
                    "error.input.date.over",
                    "ageFromTo",
                    "ageFromNum")
                .setParamName("ageFromTo",
                    "ageFromNum")
                ;
        }
    }

    /**
     * <p>queryUsr を取得します。
     * @return queryUsr
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersQueryParam#queryUsr__
     */
    public UserQueryModel getQueryUsr() {
        UserQueryModel qmdl = new UserQueryModel();
        try {
            PropertyUtils.copyProperties(qmdl, queryUsr__);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            throw new RuntimeException("検索パラメータ生成に失敗");
        }

        return qmdl;
    }



}
