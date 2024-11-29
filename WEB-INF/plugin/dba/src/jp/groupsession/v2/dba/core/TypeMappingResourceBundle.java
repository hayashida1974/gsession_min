/*
 * create   : 2004/07/30
 * modified :
 */
package jp.groupsession.v2.dba.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <br>[機  能] JavaとDBのオブジェクトマッピングを取得するクラス
 * <br>[解  説] propertyファイルより情報を取得
 * <br>[備  考]
 * @author JTS
 */
public class TypeMappingResourceBundle {

    /** プロパティファイル名 */
    private static final String BUNDLE_NAME = "DaoResource";

    /** インスタンス */
    private static final ResourceBundle RESOURCE_BUNDLE =
    ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * <p>インスタンスを生成させないためのプライベートコンストラクタ
     */
    private TypeMappingResourceBundle() {
    }

    /**
     * <p>プロパティファイルから引数をキーに値を取得する
     * <>
     * @param key メッセージを取得するキー
     * @return 引数に対応した値
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
