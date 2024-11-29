package jp.co.sjts.util;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] Nullの場合に指定したデフォルト値を返すクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NullDefault {

    /**
     * <br>[機  能] targetがNullの場合にデフォルト値のStringを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返す文字列
     * @return 文字列
     */
    public static String getString(String target, String def) {
        if (target == null) {
            return def;
        }
        return target;
    }
    /**
     * <br>[機  能] targetがNull or 空文字("")の場合にデフォルト値のStringを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返す文字列
     * @return 文字列
     */
    public static String getStringZeroLength(String target, String def) {
        if (target == null || target.length() == 0) {
            return def;
        }
        return target;
    }

    /**
     * <br>[機  能] targetがNullの場合にデフォルト値のStringを返す
     * <br>[解  説]
     * <br>[備  考] インスタンスがStringではない場合はデフォルト値を返す
     * @param target 対象の文字列
     * @param def Nullの場合に返す文字列
     * @return 文字列
     */
    public static String getStringFmObj(Object target, String def) {
        if (target == null) {
            return def;
        }
        if (!(target instanceof java.lang.String)) {
            return def;
        }
        return (String) target;
    }

    /**
     * <br>[機  能] targetがNullもしくは数字以外の場合にデフォルト値のintを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返す数値
     * @return int数値
     */
    public static int getInt(String target, int def) {
        int num = def;

        try {
            if (target != null) {
                num = Integer.parseInt(target);
            }
        } catch (NumberFormatException ne) {
        }

        return num;
    }
    /**
     * <br>[機  能] targetがNullもしくは条件式がfalseになる場合にデフォルト値のintを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param predicate 条件式
     * @param def Nullの場合に返す数値
     * @return int数値
     */
    public static int getInt(String target, Predicate<? super Integer> predicate, int def) {
        int num = def;

        try {
            if (target != null) {

                num = Integer.parseInt(target);

                return Optional.of(num)
                .filter(predicate)
                .orElse(def);
            }
        } catch (NumberFormatException ne) {
        }

        return num;
    }


    /**
     * <br>[機  能] targetがNullもしくは数字以外の場合にデフォルト値のlongを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返す数値
     * @return long数値
     */
    public static long getLong(String target, long def) {
        long num = def;

        try {
            if (target != null) {
                num = Long.parseLong(target);
            }
        } catch (NumberFormatException ne) {
        }
        return num;
    }

    /**
     * <br>[機  能] targetがNullもしくは数字以外の場合にデフォルト値のdoubleを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返す数値
     * @return double数値
     */
    public static double getDouble(String target, double def) {
        double num = def;

        try {
            if (target != null) {
                num = Double.parseDouble(target);
            }
        } catch (NumberFormatException ne) {
        }
        return num;
    }

    /**
     * <br>[機  能] targetがNullもしくは数字以外の場合にデフォルト値のBigDecimalを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返す数値
     * @return BigDecimal数値
     */
    public static BigDecimal getBigDecimal(String target, BigDecimal def) {
        BigDecimal num = def;

        try {
            if (target != null) {
                num = new BigDecimal(target);
            }
        } catch (NumberFormatException ne) {
        }
        return num;
    }

    /**
     * <br>[機  能] targetがNullの場合にデフォルト値のUDateを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param target 対象の文字列
     * @param def Nullの場合に返すUDate
     * @return long数値
     */
    public static UDate getUDate(UDate target, UDate def) {
        if (target == null) {
            return def;
        }
        return target;
    }

}
