package jp.co.sjts.util;

public interface IEnumDefaultMsgKeyValue extends IEnumMsgkeyValue {

    @Override
    default String getMsgKey() {
        return IEnumMsgkeyValue.getMsgKey(this);
    }

    @Override
    default int getValue() {
        return IEnumMsgkeyValue.getValue(this);
    }
}
