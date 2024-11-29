package jp.groupsession.v2.usr.model;

import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.groupsession.v2.usr.GSConstUser;

public enum EnumSeibetu implements IEnumMsgkeyValue {
    /** すべて*/
    @MsgkeyValue(value = GSConstUser.USER_JTKBN_ALL, msgKey = "cmn.all")
    ALL,
    /** 未設定*/
    @MsgkeyValue(value = GSConstUser.SEIBETU_UNSET, msgKey = "cmn.notset")
    UNSET,
    /** 男性*/
    @MsgkeyValue(value = GSConstUser.SEIBETU_MAN, msgKey = "user.124")
    MAN,
    /** 女性*/
    @MsgkeyValue(value = GSConstUser.SEIBETU_WOMAN, msgKey = "user.125")
    LADY;

    /** value 文字列 すべて*/
    public static final String STRVALUE_ALL = "-1";
    /** value 文字列  未設定*/
    public static final String STRVALUE_UNSET = "0";
    /** value 文字列  男性*/
    public static final String STRVALUE_MAN = "1";
    /** value 文字列  女性*/
    public static final String STRVALUE_LADY = "2";

    @Override
    public int getValue() {
        return IEnumMsgkeyValue.getValue(this);
    }

    @Override
    public String getMsgKey() {
        return IEnumMsgkeyValue.getMsgKey(this);
    }

}
