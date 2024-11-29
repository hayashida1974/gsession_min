package jp.groupsession.v2.cmn.ui.parts.usergroupselect;

import jp.groupsession.v2.cmn.ui.parts.select.IChildType;

/**
 *
 * <br>[機  能] ラベル区分 列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumChildType implements IChildType{
    /** ユーザ*/
    USER,
    /** グループ*/
    GROUP,
    /** マイグループ*/
    MYGROUP,
    /** その他*/
    OTHER,
}
