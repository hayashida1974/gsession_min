package jp.groupsession.v2.cmn.restapi.users;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 列挙型 ソートキー (ユーザ情報)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumSortKey implements IEnumMsgkeyValue {

    /** 未設定 */
    @MsgkeyValue(value = 0, msgKey = "cmn.notset")
    @BindSortConst(-1)
    NOSET,
    /** 名前 */
    @MsgkeyValue(value = 1, msgKey = "cmn.name")
    @BindSortConst(GSConstUser.USER_SORT_NAME)
    NAME,
    /** 社員番号 */
    @MsgkeyValue(value = 2, msgKey = "cmn.employee.staff.number")
    @BindSortConst(GSConstUser.USER_SORT_SNO)
    SYAINNO,
    /** 役職 */
    @MsgkeyValue(value = 3, msgKey = "cmn.post")
    @BindSortConst(GSConstUser.USER_SORT_YKSK)
    YAKUSYOKU,
    /** 誕生日 */
    @MsgkeyValue(value = 4, msgKey = "cmn.birthday")
    @BindSortConst(GSConstUser.USER_SORT_BDATE)
    BIRTHDAY,
    /** ソートキー1 */
    @MsgkeyValue(value = 5, msgKey = "cmn.sortkey")
    @BindSortConst(GSConstUser.USER_SORT_SORTKEY1)
    SORTKEY1,
    /** ソートキー2 */
    @MsgkeyValue(value = 6, msgKey = "cmn.sortkey")
    @BindSortConst(GSConstUser.USER_SORT_SORTKEY2)
    SORTKEY2;

    @Override
    public int getValue() {
        return IEnumMsgkeyValue.getValue(this);
    }

    @Override
    public String getMsgKey() {
        return IEnumMsgkeyValue.getMsgKey(this);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface BindSortConst {
        /** @return 値 */
        int value();
    }

    public int getBindConstValue() {
        String name;
        try {
            name = (String) getClass().getMethod("name").invoke(this);
            BindSortConst ant
                = this.getClass().getField(name)
                    .getAnnotation(BindSortConst.class);
            return ant.value();
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException | NoSuchFieldException e) {
        }
        return 0;

    }
}
