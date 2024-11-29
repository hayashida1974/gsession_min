package jp.groupsession.v2.restapi.parameter;

import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;

/**
 * <br>[機  能] 列挙型へコンバートする
 * <br>[解  説]
 * <br>[備  考] コンバート失敗時 RestApiValidateExceptionをthrow
 * <br>         ReasonCodeはPARAM_OTHER-INVALID
 *
 * @author JTS
 */
public class EnumConverter implements Converter {
    /** チェック対象表示名*/
    String dispName__;
    /**
     *
     * コンストラクタ
     * @param dispName チェック対象表示名
     */
    public EnumConverter(String dispName) {
        super();
        dispName__ = dispName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(@SuppressWarnings("rawtypes") Class paramClass, Object paramObject) {
        if (IEnumMsgkeyValue.class.isAssignableFrom(paramClass)) {
            Object ret = null;
            if (paramObject instanceof String) {
                try {
                    ret = new EnumUtil<>(paramClass).valueOf(Integer.valueOf((String) paramObject));
                } catch (NumberFormatException | EnumOutRangeException e) {
                }
                if (ret != null) {
                    return ret;
                }
            }
            if (paramObject instanceof Integer) {
                try {
                    ret = new EnumUtil<>(paramClass).valueOf((Integer) paramObject);
                } catch (EnumOutRangeException e) {
                }
                if (ret != null) {
                    return ret;
                }
            }
            throw new RestApiValidateException(
                    EnumError.PARAM_FORMAT,
                    "error.input.comp.text.either",
                    dispName__,
                    EnumSet.allOf(paramClass).stream()
                        .map(num -> String.valueOf(((IEnumMsgkeyValue) num).getValue()))
                        .collect(Collectors.joining(","))
                    );

        }
        if (ValuedEnum.class.isAssignableFrom(paramClass)) {
            Object ret = null;
            if (paramObject instanceof String) {
                try {
                    ret = EnumUtils.getEnum(paramClass, Integer.valueOf((String) paramObject));
                } catch (NumberFormatException e) {
                }
                if (ret != null) {
                    return ret;
                }
            }
            if (paramObject instanceof Integer) {
                ret = EnumUtils.getEnum(paramClass, (Integer) paramObject);
                if (ret != null) {
                    return ret;
                }
            }
            throw new RestApiValidateException(
                    EnumError.PARAM_FORMAT,
                    "error.input.comp.text.either",
                    dispName__,
                    EnumSet.allOf(paramClass).stream()
                        .map(num -> String.valueOf(((ValuedEnum) num).getValue()))
                        .collect(Collectors.joining(","))
                    );

        }
        if (paramObject instanceof String) {
            try {
                return Enum.valueOf(paramClass, (String) paramObject);
            } catch (Exception e) {
            }
        }
        throw new RestApiValidateException(
                EnumError.PARAM_FORMAT,
                "error.input.comp.text.either",
                dispName__,
                EnumSet.allOf(paramClass).stream()
                    .collect(Collectors.joining(","))
                );

    }

}
