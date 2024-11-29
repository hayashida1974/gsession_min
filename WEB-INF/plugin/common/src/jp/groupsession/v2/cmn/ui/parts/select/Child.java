package jp.groupsession.v2.cmn.ui.parts.select;

import java.util.List;

public class Child implements IChild {
    /**名前*/
    String name__;
    /**値*/
    String value__;



    /**
     * コンストラクタ
     * @param name
     * @param value
     */
    public Child(String name, String value) {
        super();
        name__ = name;
        value__ = value;
    }

    @Override
    public String getName() {
        return name__;
    }

    @Override
    public String getValue() {
        return value__;
    }

    @Override
    public IChildType getType() {
        return EnumChildType.OTHER;
    }

    @Override
    public int getTeisiFlg() {
        return 0;
    }
    @Override
    public int getJkbn() {
        return 0;
    }

    @Override
    public List<String> getLabelList() {
        return List.of();
    }

    @Override
    public void setLabelList(List<String> userLabel) {
    }



}
