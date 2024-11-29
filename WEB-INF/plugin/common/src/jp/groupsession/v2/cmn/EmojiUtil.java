package jp.groupsession.v2.cmn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;

/**
 * <br>[機  能] GSで使用可能な絵文字を保持するクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EmojiUtil {

    /** 使用可能絵文字リスト */
    private static List<String> useableEmoji__ = null;

    /**
     * @return the useableEmoji
     */
    public static synchronized List<String> getUseableEmoji() {
        return useableEmoji__;
    }

    /**
     *<br> [機 能] コンストラクタ
     *<br> [解 説]
     *<br> [備 考]
     */
    private EmojiUtil() {
    }

    /**
     *<br> [機 能] 使用できる絵文字の読み込みを行う
     *<br> [解 説] common/js/emoji/data_japanese.json内の絵文字を読み込む
     *<br> [備 考]
     *
     * @param context Context
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public static synchronized void readData(ServletContext context) throws IOException {

        String path =  context.getRealPath("/");
        path = IOTools.setEndPathChar(path);
        path = path.concat("/common/js/emoji/data_japanese.json");

        //設定ファイル一覧を取得
        StringTokenizer lines = new StringTokenizer(IOTools.readText(path, Encoding.UTF_8), "\n");

        StringBuilder sb = new StringBuilder();
        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            line = StringUtil.toDeleteReturnCode(line);
            sb.append(line);
        }

        List<String> emojiList = new ArrayList<String>();
        JSONArray jsonArray = JSONArray.fromObject(sb.toString());
        JSONObject jsonObject;
        String skinStr;
        JSONArray skinArray;
        for (int idx = 0; idx < jsonArray.size(); idx++) {
            jsonObject = jsonArray.getJSONObject(idx);
            emojiList.add(jsonObject.getString("emoji"));
            if (!jsonObject.has("skins")) {
                continue;
            }

            skinStr = jsonObject.getString("skins");
            skinArray = JSONArray.fromObject(skinStr);
            for (int skinIdx = 0; skinIdx < skinArray.size(); skinIdx++) {
                emojiList.add(skinArray.getJSONObject(skinIdx).getString("emoji"));
            }
        }

        useableEmoji__ = emojiList;
    }

}
