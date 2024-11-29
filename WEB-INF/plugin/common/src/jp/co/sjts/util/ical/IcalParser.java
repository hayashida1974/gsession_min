package jp.co.sjts.util.ical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] iCal形式のファイルを解析する。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IcalParser {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IcalParser.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public IcalParser() {
        super();
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param args 引数 ここでは使用しない
     * @throws Exception 例外
     */
    public static void main(String[] args) throws Exception {
        BufferedReader in = null;
        File srcFile = null;
        String srcEncoding = null;

        //初期値
        srcFile = new File("c:\\basic.ics");
        //文字コード
        srcEncoding = "UTF-8";

        try {
            in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(srcFile),
                    srcEncoding));

            String line = in.readLine();
            ArrayList<String> tmp = new ArrayList<String>();;
            while (line != null) {
                if (line.startsWith("BEGIN:VEVENT")) {
                    tmp = new ArrayList<String>();
                } else if (line.startsWith("END:VEVENT")) {
                    __parseEvent(tmp);
                }
                tmp.add(line);
                line = in.readLine();
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * <br>[機  能] BEGIN:VEVENTからEND:VEVENTを処理する
     * <br>[解  説]
     * <br>[備  考]
     * @param lineList 1つのBEGIN:VEVENTからEND:VEVENTまでの文字列を格納したリスト
     */
    private static void __parseEvent(List<String> lineList) {
        //
        log__.debug("");
        log__.debug("-- START ------------------------------------------------------------");
        for (String line : lineList) {
            IcalProperty prop = createProperty(line);
            if (prop == null) {
                continue;
            }
            String name = prop.getName();
            if (name.startsWith("DTSTART")) {
                log__.debug("開始日付 = " + prop.getValue());
            } else if (name.startsWith("DTEND")) {
                log__.debug("終了日付 = " + prop.getValue());
            } else if (name.startsWith("SUMMARY")) {
                log__.debug("タイトル = " + prop.getValue());
            } else if (name.startsWith("DTSTAMP")) {
                log__.debug("日付 = " + prop.getValue());
            }
        }
        log__.debug("-- END ------------------------------------------------------------");
        log__.debug("");
    }

    /**
     * <br>[機  能] 1行の文字列からIcalPropertyを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param line 対象の文字列
     * @return 生成したIcalProperty
     */
    private static IcalProperty createProperty(String line) {
        if (line == null) {
            return null;
        }
        IcalProperty prop = new IcalProperty();
        List<String> list = StringUtil.split(":", line);
        if (list == null) {
            return null;
        } else if (list.size() == 0) {
            return null;
        } else if (list.size() == 1) {
            prop.setName((String) list.get(0));
            prop.setValue(null);
        } else if (list.size() == 2) {
            prop.setName((String) list.get(0));
            prop.setValue((String) list.get(1));
        } else if (list.size() > 2) {
            int cnt = 0;
            StringBuilder buf = new StringBuilder();
            for (Object tmp : list) {
                String tmp2 = (String) tmp;
                cnt++;
                if (cnt == 1) {
                    prop.setName(tmp2);
                } else {
                    buf.append(tmp2);
                }
            }
            prop.setValue(buf.toString());
        }
        return prop;
    }
}
