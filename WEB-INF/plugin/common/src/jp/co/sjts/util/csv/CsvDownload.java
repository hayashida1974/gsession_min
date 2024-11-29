package jp.co.sjts.util.csv;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] CSVのダウンロードを簡易化したクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class CsvDownload {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CsvDownload.class);

    /**
     * <br>[機  能] リスト内のデータを順次とりだしCSVとしてレスポンスに付加します。
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ダウンロードファイル名
     * @param csvList CSVリスト : ArrayList（ルート） - ArrayList(行) - String(CSV項目)
     * @param hederText 1行目のコメント文がある場合は指定する
     * @param response レスポンス
     * @throws IOException 入出力時例外
     */
    @SuppressWarnings("all")
    public void getCsvResponse(
        String fileName,
        ArrayList csvList,
        String hederText,
        HttpServletResponse response)
        throws IOException {

        CsvPrintWriter pw = null;
        try {
            //ダウンロード実行
            response.setContentType("application/octet-stream; " + Encoding.SHIFT_JIS);
            //日本語ファイル名対応 IEのみ
            String enc =  new String(fileName.getBytes(Encoding.WINDOWS_31J),
                                        Encoding.ISO8859_1);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + enc + "\"");
            //文字コードShift_JISで書き出し
            OutputStreamWriter osw =
              new OutputStreamWriter(response.getOutputStream(), Encoding.WINDOWS_31J);
            pw = new CsvPrintWriter(osw, true);

            if (csvList != null) {
                if (!StringUtil.isNullZeroString(hederText)) {
                    pw.println(hederText);
                }
                for (int i = 0; i < csvList.size(); i++) {
                    ArrayList row = (ArrayList) csvList.get(i);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < row.size(); j++) {
                        String item = (String) row.get(j);
                        if (item != null) {
                            sb.append(CsvEncode.encString(item));
                        }
                        sb.append(",");
                    }
                    //最後の「,」削除
                    int lenAry = sb.length() - 1;
                    if (sb.charAt(lenAry) == ',') {
                        sb.deleteCharAt(lenAry);
                    }
                    pw.println(sb.toString()); //書込み+改行
                }
            }
        } catch (UnsupportedEncodingException e) {
            log__.error("サポートされないエンコード", e);
        } catch (IOException e) {
            log__.error("CSV書き出しエラー", e);
            throw e;
        } finally {
            pw.flush();
            pw.close();
        }
    }
}
