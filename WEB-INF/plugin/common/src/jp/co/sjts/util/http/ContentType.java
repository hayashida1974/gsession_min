package jp.co.sjts.util.http;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <br>[機  能] content-type取得クラス
 * <br>[解  説] contentType.propertiesから各拡張子に対応するcontentTypeを取得します。
 * <br>[備  考]
 * @author JTS
 */
public class ContentType {

    /** プロパティファイル名 */
    private static final String BUNDLE_NAME = "contentType";

    /** 拡張子なし or 登録されていない拡張子だった場合のcontentType */
    private static final String CONTENTTYPE_OCTETSTREAM = "application/octet-stream";

    /** インスタンス */
    private static final ResourceBundle RESOURCE_BUNDLE =
        ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * <br>[機  能] 空のコンストラクタの禁止
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     */
    private ContentType() { };

    /**
     * <br>[機  能] ファイル名からcontent-type を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param filename ファイル名
     * @return content-type
     */
    public static String getContentType(String filename) {
        int lastIndex = filename.lastIndexOf(".");

        //拡張子が存在しない場合はapplication/octet-streamを返す
        if (lastIndex == -1) {
            return CONTENTTYPE_OCTETSTREAM;
        }

        String key = filename.substring(lastIndex);
        try {
            return RESOURCE_BUNDLE.getString(key.toLowerCase());
        } catch (MissingResourceException e) {
            //application/octet-stream
            //"avb, AVB, bin, bpk, class, exe, tad, TAD, ttf,その他"
            return CONTENTTYPE_OCTETSTREAM;
        }
    }
}
