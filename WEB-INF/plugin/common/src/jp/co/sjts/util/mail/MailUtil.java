package jp.co.sjts.util.mail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParameterList;
import javax.mail.internet.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;

/**
 * <br>[機  能] メールに関するユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class MailUtil {
    /** RFC5322 規約上の1行の文字数制限 */
    private static final int MAXSTRLENGTH_RFC5322 = 65;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MailUtil.class);

    /**
     * <br>[機  能] <p>Address配列オブジェクトから<br>
     *              InternetAddress配列オブジェクトへ型変換する。</p>
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param address 変換元address配列オブジェクト
     * @return 型変換した、InternetAddress配列オブジェクト
     */
    public static InternetAddress[] addressToInternetAddress(Address[] address) {
        if (address == null || address.length <= 0) {
            return null;
        }

        InternetAddress[] internet = new InternetAddress[address.length];
        for (int intCnt = 0; intCnt < address.length; intCnt++) {
            internet[intCnt] = (InternetAddress) address[intCnt];
        }
        return internet;
    }

    /**
     * メールの内容を表示します
     * @param m メッセージ
     * @throws Exception 予測不明な例外
     */
    public static void debugMessage(Message m) throws Exception {
        debugMessageHead(m);
        debugMessagePart(m);
    }

    /**
     * メールの内容を表示します
     * @param p Part
     * @throws Exception 予測不明な例外
     */
    public static void debugMessagePart(Part p) throws Exception {
//        String html = "";
        boolean attachment = false;

        if (p.isMimeType("text/plain")) { // テキストの場合
          System.out.println("内容：\n" + p.getContent());
        } else if (p.isMimeType("multipart/*")) { // マルチパートの場合
          Multipart mp = (Multipart) p.getContent();
          int count = mp.getCount();
          for (int i = 0; i < count; i++) {
              debugMessagePart(mp.getBodyPart(i));
          }
        } else if (p.isMimeType("message/rfc822")) { // メッセージの場合
            debugMessagePart((Part) p.getContent());
        } else if (p.isMimeType("text/html")) { // HTMLの場合
//          html = ".html";
          attachment = true;
        } else { // その他の場合
          attachment = true;
        }

//        int attachnum = 1;
        /**
         * 添付ファイル
         */
         if (attachment) {
           String disp = p.getDisposition();
//           System.out.println("disp = " + disp);
           // 添付ファイルの場合
           if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
             String filename = p.getFileName();
             if (filename != null) {
               filename =  MimeUtility.decodeText(filename);
               System.out.println("添付ファイル = " + filename);
//             } else {
//               filename = "添付ファイル" + attachnum++ + html;
//               System.out.println("添付ファイル = " + null);
             }
//             try {
//               File f = new File(filename);
//               if (f.exists()) {
//                 throw new IOException("同名のファイルが存在します");
//               }
//               OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
//               InputStream is = p.getInputStream();
//               int c;
//               while ((c = is.read()) != -1) {
//                 os.write(c);
//               }
//               os.close();
//               System.out.println(filename + "を保存しました。");
//             } catch (IOException e) {
//               System.out.println("添付ファイルの保存に失敗しました。" + e);
//             }
           }
         }
    }

    /**
     * 文字列をエンコードします。
     * <p>
     * MimeUtility(強いてはMimeMessage等も)では、1字でも非ASCII文字が含まれる
     * と文字列全体をエンコードしてしまいます。<br>
     * このメソッドでは空白で区切られた範囲だけをエンコードします。<br>
     * Subjectの"Re: "等がエンコードされていると、この文字列でIn-Reply-To:
     * References:の代わりにスレッドを形成しようとしても失敗することになる
     * ため、こちらのエンコード方式を用いたがる人もいるかもしれません・・。
     * </p><p>
     * 方針は、ASCII部に前後の空白一つを含ませ、それ以外は空白も含めて全て
     * encoded-wordとします。
     * </p>
     * @param source text
     * @param charset charset
     * @param encoding エンコード
     * @return encoded エンコード後の文字列
     * @throws UnsupportedEncodingException 適切なエンコードを指定していれば通常スローすることはない
     */
    public static String encodeText(String source,
                                    String charset, String encoding)
                throws UnsupportedEncodingException {
        int boundaryIndex;
        int startIndex;
        int endIndex = 0;
        int lastLWSPIndex;
        String encodeTargetText;
        StringBuilder buf = new StringBuilder();

        while (true) {
            boundaryIndex = indexOfNonAscii(source, endIndex);
            if (boundaryIndex == -1) {
                buf.append(source.substring(endIndex));
                return new String(buf);
            }
            // any LWSP has taken.
            lastLWSPIndex = indexOfLWSP(source, boundaryIndex, true, '(');
            startIndex    = indexOfNonLWSP(source, lastLWSPIndex, true) + 1;
            if (source.charAt(startIndex) == '(') {
                startIndex++;
            }
            startIndex = (endIndex > startIndex) ? endIndex : startIndex;
            if (startIndex > endIndex) {
                // ASCII part
                // foldingしない場合はASCII part末尾に空白を含める必要あり
                // startIndex++;
                buf.append("\r\n ");
                buf.append(source.substring(endIndex, startIndex));
                // JavaMailはencodeWord内でfoldingするけどそれはencodedWord
                // に対してのみ。ヘッダそのものに対するfoldingはしてくれない。
                buf.append("\r\n ");
                startIndex++;
            } else if (endIndex > 0) {
                // prev is encoded-word
                buf.append("\r\n ");
            }
            // any LWSP has taken.
            endIndex = indexOfNonLWSP(source, boundaryIndex, false);
            endIndex = indexOfLWSP(source, endIndex, false, ')');
            endIndex = indexOfNonLWSP(source, endIndex, false);
            if (endIndex < 0) {
                endIndex = source.length();
            } else if (source.charAt(endIndex) != ')') {
                endIndex--;
            }

            encodeTargetText = source.substring(startIndex, endIndex);
            buf.append(MimeUtility.encodeWord(
                    encodeTargetText, charset, encoding));
        }
    }

    /**
     * <br>[機  能] 漢字のjisコードをsjisコードに変換して文字列化(仮)
     * <br>[解  説]
     * <br>[備  考]
     * @param arr jisバイト配列
     * @return デコードした文字列
     * @throws UnsupportedEncodingException エンコードエラー
     */
    public static String decodeJis(byte[] arr) throws UnsupportedEncodingException {
        return __decodeJis(arr);
    }

    /**
     * ヘッダ内の文字列をデコードします。
     * <p>
     * MimeUtilityの制約を緩めて日本で流通するエンコード形式に対応。
     * 本来は、encoded-wordとnon-encoded-wordの間にはlinear-white-spaceが必要
     * なのですが、空白が無い場所でエンコードするタコメイラが多いので。
     * </p><p>
     * JISコードをエンコード無しで記述するタコメイラもあります。<br>
     * ソースにESCが含まれていたら生JISと見なします。
     * </p><p>
     * =?utf-8?Q?・・・JISコード・・?=なんてさらにタコなメイラも。<br>
     * 試しにデコード後にまだESCが残ってたらISO-2022-JPと見なすことにします。
     * </p><p>
     * 日本語に特化してますねえ・・・。
     * </p>
     * @param source encoded text
     * @return decoded text
     * @throws ParseException 解析に失敗した場合にスロー
     */
    public static String decodeText(String source) throws ParseException {
        if (source == null) {
            return null;
        }

        // specially for Japanese
        if (source.indexOf('\u001b') > 0) {
            // ISO-2022-JP
            try {
                return new String(source.getBytes("ISO-8859-1"), "ISO-2022-JP");
            } catch (UnsupportedEncodingException e) {
                throw new ParseException();
            }
        }

        int startIndex;
        int endIndex = 0;
        String encodedWord;
        StringBuilder buf = new StringBuilder();

        while (true) {
            startIndex = source.indexOf("=?", endIndex);
            if (startIndex == -1) {
                buf.append(source.substring(endIndex));
                break;
            } else if (startIndex > endIndex) {
                String work = source.substring(endIndex, startIndex);
                if (indexOfNonLWSP(work, 0, false) > -1) {
                    buf.append(work);
                }
                // encoded-word同士の間のLWSPは削除
            }
            // skip "=?..?..?"
            // because In the case of "Q" encoding,
            // it exists that a word is the case of "=?..?Q?=1B...?=".
            endIndex = source.indexOf('?', startIndex + 2);
            if (endIndex == -1) {
                buf.append(source.substring(startIndex));
                break;
            }
            endIndex = source.indexOf('?', endIndex + 1);
            if (endIndex == -1) {
                buf.append(source.substring(startIndex));
                break;
            }
            endIndex = source.indexOf("?=", endIndex + 1);
            if (endIndex == -1) {
                buf.append(source.substring(startIndex));
                break;
            }
            endIndex += 2;

            encodedWord = source.substring(startIndex, endIndex);
            try {
                buf.append(MimeUtility.decodeWord(encodedWord));
            } catch (UnsupportedEncodingException ex) {
                buf.append(encodedWord);
            }
        }
        String decodedText = new String(buf);

        if (decodedText.indexOf('\u001b') > 0) {
            try {
                return new String(decodedText.getBytes("ISO-8859-1"), "ISO-2022-JP");
            } catch (UnsupportedEncodingException e) {
                throw new InternalError();
            }
        }
        return decodedText;
    }

    /**
     * 指定位置から最初に見つかった非ASCII文字のIndexを取得する。
     * @param source 検索する文字列
     * @param startIndex 検索開始位置
     * @return 検出した非ASCII文字Index。見つからなければ-1。
     */
    public static int indexOfNonAscii(String source, int startIndex) {
        for (int i = startIndex; i < source.length(); i++) {
            if (source.charAt(i) > 0x7f) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 指定位置から最初に見つかったLWSP以外の文字のIndexを返します。
     * @param source 検索する文字列
     * @param startIndex 検索開始位置
     * @param decrease trueで後方検索
     * @return 検出した非ASCII文字Index。見つからなければ-1。
     */
    public static int indexOfNonLWSP(String source, int startIndex,
                                     boolean decrease) {
        char c;
        int inc = 1;
        if (decrease) {
            inc = -1;
        }

        for (int i = startIndex; i >= 0 && i < source.length(); i += inc) {
            c = source.charAt(i);
            if (!isLWSP(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 指定位置から最初に見つかったLWSPのIndexを返します。
     * @param source 検索する文字列
     * @param startIndex 検索開始位置
     * @param decrease trueで後方検索
     * @param additionalDelimiter LWSP以外に区切りとみなす文字(1字のみ)
     * @return 検出した非ASCII文字Index。見つからなければ-1。
     */
    public static int indexOfLWSP(String source, int startIndex,
                                  boolean decrease, char additionalDelimiter) {
        char c;
        int inc = 1;
        if (decrease) {
            inc = -1;
        }

        for (int i = startIndex; i >= 0 && i < source.length(); i += inc) {
            c = source.charAt(i);
            if (isLWSP(c) || c == additionalDelimiter) {
                return i;
            }
        }
        return -1;
    }

    /**
     * <br>[機  能] キャラクターがCR,LF,TAB
     * <br>[解  説]
     * <br>[備  考]
     * @param c 判定キャラクタ
     * @return true: false:
     */
    public static boolean isLWSP(char c) {
        return c == '\r' || c == '\n' || c == ' ' || c == '\t';
    }

    /**
     * メールの内容を表示します
     * @param m メッセージ
     * @throws Exception 予測不明な例外
     */
    public static void debugMessageHead(Message m) throws Exception {
        Address[] a;
        // 差出人
        a = m.getFrom();
        if (a != null) {
          for (int j = 0; j < a.length; j++) {
            System.out.println("差出人: " + MimeUtility.decodeText(a[j].toString()));
          }
        }

        // 宛先
        a = m.getRecipients(Message.RecipientType.TO);
        if (a != null) {
          for (int j = 0; j < a.length; j++) {
            System.out.println("宛先: " + MimeUtility.decodeText(a[j].toString()));
          }
        }
        //ReplyTo
        a = m.getReplyTo();
        if (a != null) {
            for (int j = 0; j < a.length; j++) {
              System.out.println("ReplyTo: " + MimeUtility.decodeText(a[j].toString()));
            }
        }
        //CC
        a = m.getRecipients(Message.RecipientType.CC);
        if (a != null) {
            for (int j = 0; j < a.length; j++) {
              System.out.println("CC: " + MimeUtility.decodeText(a[j].toString()));
            }
        }
        //BCC
        a = m.getRecipients(Message.RecipientType.BCC);
        if (a != null) {
            for (int j = 0; j < a.length; j++) {
              System.out.println("BCC: " + MimeUtility.decodeText(a[j].toString()));
            }
        }

        // 題名
        System.out.println("題名: " + m.getSubject());
        // 日付
        Date d = m.getSentDate();
        System.out.println("日付: " + (d != null ? d.toString() : "不明"));
        // サイズ
        System.out.println("サイズ: " + m.getSize());

    }
    /**
     *
     * <br>[機  能] MIMEエンコードされた文字列をデコードする
     * <br>[解  説]
     * <br>[備  考]
     * @param target MIMEエンコード文字列
     * @return デコード文字列
     */
    public static String decodeMimeText(String target)  {
        String buffStr = MimeUtility.unfold(target);

        ArrayList<String> encList = new ArrayList<String>();
        //複数のエンコードを含む場合があるのでエンコード切り替わりごとに分割する
        while (true) {
            int start = buffStr.indexOf("=?");
            if (start < 0) {
                encList.add(buffStr);
                break;
            }
            if (start > 0) {
                String work = buffStr.substring(0, start);
                if (MailUtil.indexOfNonLWSP(work, 0, false) > -1) {
                    encList.add(work);
                }
                // encoded-word同士の間のLWSPは削除
            }
            int end = buffStr.indexOf('?', start + 2);
            if (end == -1) {
                encList.add(buffStr);
                break;
            }
            end = buffStr.indexOf('?', end + 1);
            if (end == -1) {
                encList.add(buffStr);
                break;
            }
            end = buffStr.indexOf("?=", end + 1);
            if (end == -1) {
                encList.add(buffStr);
                break;
            }
            end = end + 2;
            encList.add(buffStr.substring(start, end));
            buffStr = buffStr.substring(end);
        };

        try {
            return __decodeMIMEList(encList);
        } catch (UnsupportedEncodingException e) {
            return target;
        }
    }

    /**漢字以外モード*/
    public static final int MODE_OTHER = 0;
    /**漢字モード JIS 208X 1983*/
    public static final int MODE_JIS208X_1983 = 1;
    /**漢字モード JIS 208X 1900*/
    public static final int MODE_JIS208X_1990 = 2;
    /**漢字モード JIS 212X 1990*/
    public static final int MODE_JIS212X_1990 = 3;
    /**
     *
     * <br>[機  能] MIMEデコード
     * <br>[解  説]
     *       1.エンコード部をバイト[]にデコード
     *       2.バイト[]を文字列化し結合
     * <br>[備  考]
     * @param targets MIMEエンコード文字列のリスト エンコードごとにsplitしたもの
     * @return デコード文字列
     * @throws UnsupportedEncodingException エンコードエラー
     */
    private static String __decodeMIMEList(List<String> targets)
            throws UnsupportedEncodingException {
        StringBuilder ret = new StringBuilder();
        String charSetStr = null;
        String encoding = null;
        byte[] decoded = new byte[0];
        for (String target : targets) {
            int start = target.indexOf("=?");
            if (start < 0) {
                if (decoded.length > 0) {
                    ret.append(__decodeMimeBytes(decoded, charSetStr));
                }
                decoded = new byte[0];
                charSetStr = null;
                encoding = null;
                ret.append(target);
                continue;
            }
            int end = target.indexOf('?', start + 2);
            if (end == -1) {
                if (decoded.length > 0) {
                    ret.append(__decodeMimeBytes(decoded, charSetStr));
                }
                decoded = new byte[0];
                charSetStr = null;
                encoding = null;

                ret.append(target);
                continue;
            }
            String nCharSet = MimeUtility.javaCharset(target.substring(start + 2, end));
            start = end;
            end = target.indexOf('?', start + 1);
            if (end == -1) {
                if (decoded.length > 0) {
                    ret.append(__decodeMimeBytes(decoded, charSetStr));
                }
                decoded = new byte[0];
                charSetStr = null;
                encoding = null;
                ret.append(target);
                continue;
            }
            String nEncoding = target.substring(start + 1, end);
            start = end;
            end = target.indexOf("?=", start + 1);
            if (end == -1) {
                if (decoded.length > 0) {
                    ret.append(__decodeMimeBytes(decoded, charSetStr));
                }
                decoded = new byte[0];
                charSetStr = null;
                encoding = null;
                ret.append(target);
                continue;
            }
            if (!nCharSet.equals(charSetStr) || !nEncoding.equals(encoding)) {
                if (decoded.length > 0) {
                    ret.append(__decodeMimeBytes(decoded, charSetStr));
                }
                decoded = new byte[0];
                charSetStr = null;
                encoding = null;
            }
            String str = target.substring(start + 1, end);
            charSetStr = nCharSet;
            encoding = nEncoding;
            String regex = "(?i)B";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(encoding);
            String encStr;
            if (m.find() && m.start() == 0) {
                encStr = "base64";
            } else {
                encStr = "quoted-printable";
            }
            try {
                if (encStr.equals("quoted-printable")) {
                    str = str.replace("_", " ");
                }

                byte[] bs = str.getBytes(Encoding.UTF_8);
                byte[] tmp = new byte[bs.length];
                ByteArrayInputStream bais = new ByteArrayInputStream(bs);
                InputStream is = MimeUtility.decode(bais, encStr);
                int n = is.read(tmp);
                byte[] added = new byte[decoded.length + n];
                System.arraycopy(decoded, 0, added, 0, decoded.length);
                System.arraycopy(tmp, 0, added, decoded.length, n);
                decoded = added;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return ret.toString();
    }
    /**
     *
     * <br>[機  能] 文字列デコード
     * <br>[解  説]
     *       1.Charsetの通りに出コード
     *       　canDecodeString 範囲チェック
     *       　範囲内ならそのままリターン（デコード下文字列を返却）
     *       2.範囲外かつCharsetがISO-2022-JPの場合
     *       　base64部分をbyte[]に変換、１文字ずつSJISに変換し文字列化
     *       　canDecodeString 範囲チェック
     *       　範囲内ならそのままリターン（デコード下文字列を返却）
     *       3.範囲外かつCharsetがISO-2022-JP以外の場合
     *       　範囲外の場合1の範囲外の値を返却
     * <br>[備  考]
     * @param bytes 文字列byte
     * @param mimeCharSet MIME上のCharset
     * @return デコード文字列
     * @throws UnsupportedEncodingException エンコードエラー
     */
    private static String __decodeMimeBytes(byte[] bytes, String mimeCharSet)
            throws UnsupportedEncodingException {
        //MIME上のCharsetをjavaのCharsetに変換
        String javaCharSet = MimeUtility.javaCharset(mimeCharSet);
        String ret = "";
        String regex = "(?i)CP932";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(javaCharSet);
        if (m.matches()) {
            ret = new String(bytes, Encoding.WINDOWS_31J);
            return ret;
        }

        ret = new String(bytes, javaCharSet);
        regex = "(?i)ISO-2022-JP";
        p = Pattern.compile(regex);
        m = p.matcher(javaCharSet);
        if (m.matches() && !canDecodeString(ret)) {
            String bdecode = __decodeJis(bytes);
            if (canDecodeString(bdecode)) {
                return bdecode;
            }
        }
        return ret;
    }


    /**
     *
     * <br>[機  能] 漢字のjisコードをsjisコードに変換して文字列化
     * <br>[解  説]
     * <br>[備  考]
     * @param arr jisバイト配列
     * @return デコードした文字列
     * @throws UnsupportedEncodingException エンコードエラー
     */
    private static String __decodeJis(byte[] arr) throws UnsupportedEncodingException {
        int kanjiMode = 0;
        StringBuilder ret = new StringBuilder();
        int start = 0;
        int end = 0;
        int i = 0;
        do {
            start = i;
            int escoffset = 3;
            //ESC判定 jisコードの文字区分定義部を探す
            if (arr[i] == 0x1b && i + 2 < arr.length
                    ) {
                //漢字IN
                if (arr[i + 1] == 0x24) {
                    kanjiMode = MODE_JIS208X_1983;
                    if (arr[i + 2] == 0x28) {
                        kanjiMode = MODE_JIS212X_1990;
                        escoffset = 4;
                    }
                }
                if (arr[i + 1] == 0x26) {
                    kanjiMode = MODE_JIS208X_1990;
                    escoffset = 6;
                }
                //漢字OUT
                if (i + escoffset - 1 >= arr.length) {
                    kanjiMode = MODE_OTHER;
                    escoffset = 1;
                }
                if (arr[i + 1] == 0x28) {
                    kanjiMode = MODE_OTHER;
                    escoffset = 3;
                }
            }
            end = i + 1;
            //次の文字区分登場部までで区切る
            while (end < arr.length) {
                if (arr[end] == 0x1B) {
                    break;
                }
                end++;
            }
            if (kanjiMode != MODE_OTHER) {
                //jisコードの漢字をsjisに変換して文字列追加
                ret.append(__convertJisKanji(Arrays.copyOfRange(arr, start, end), kanjiMode));
            } else {
                //漢字以外はそのまま追加
                ret.append(__convertJisOther(Arrays.copyOfRange(arr, start, end)));
            }
            i = end;
        } while (i < arr.length);

        return ret.toString();
    }
    /**
     *
     * <br>[機  能] jis漢字をsjis変換し文字列化
     * <br>[解  説]
     * <br>[備  考]
     * @param arr jisバイト配列
     * @param mode jis漢字モード
     * @return デコードした文字列
     * @throws UnsupportedEncodingException エンコードエラー
     */
    private static String __convertJisKanji(byte[] arr, int mode)
            throws UnsupportedEncodingException {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        switch (mode) {
        case MODE_JIS208X_1983:
            i = 3;
            break;
        case MODE_JIS208X_1990:
            i = 6;
            break;
        case MODE_JIS212X_1990:
            i = 4;
            break;

        default:
            ret.toString();
        }
        while (i + 1 < arr.length) {

            byte c1b, c2b;
            char c1, c2;
            c1b = arr[i];
            c2b = arr[i + 1];


            if ((c1b % 2) != 0) {
                c2b += 0x1F;
            } else {
                c2b += 0x7E;
            }

            c1b -= 0x21;
            c1b = (byte) (c1b >> 1);
            c1 = (char) (c1b & 0xFF);

            if (c1 <= 0x1E) {
                c1b += 0x81;
            } else {
                c1b += 0xC1;
            }
            c2 = (char) (c2b & 0xFF);
            if (c2 >= 0x7F && c2 <= 0x9D) {
                c2b += 0x01;
            }

            ret.append(new String(new byte[] {c1b, c2b}, Encoding.WINDOWS_31J));
            i += 2;
        }
        return ret.toString();
    }
    /**
     *
     * <br>[機  能] jisの漢字以外の文字列化
     * <br>[解  説]
     * <br>[備  考]
     * @param arr jisバイト配列
     * @return デコードした文字列
     * @throws UnsupportedEncodingException エンコードエラー
     */
    private static String __convertJisOther(byte[] arr) throws UnsupportedEncodingException {
        StringBuilder ret = new StringBuilder();

        ret.append(new String(arr, Encoding.ISO_2022_JP));
        return ret.toString();
    }
    /**
     * JIS第二水準、NEC特殊文字、NEC選定IBM拡張文字、IBM拡張文字の範囲内でデコード可能か判定する
     * @param source 判定対象の文字列
     * @return true:範囲内である(デコード可能) , false:範囲外の文字が含まれる(デコード不可能)
     */
    public static boolean canDecodeString(String source) {

        char[] charList = source.toCharArray();
        //1文字づつ範囲内かチェックし、1文字でも範囲外であればfalse
        for (char charValue : charList) {

            if (!StringUtil.isAscii(charValue)
                    && !ValidateUtil.isFullWidthJISX0208PlusNecIbm(charValue)) {
                String hexText = Integer.toHexString(charValue);
                log__.debug("範囲外の文字列:" + charValue + "[" + hexText + "]");
                return false;
            }
        }
        return true;
    }
    /**
     *
     * <br>[機  能] 送信メール ボディの添付ファイル追加
     * <br>[解  説]
     * <br>[備  考]
     * @param mp 出力先マルチパート
     * @param file ファイル実体
     * @param fileName ファイル名
     * @param encode 出力文字コード
     * @throws MessagingException メッセージ出力例外
     * @throws UnsupportedEncodingException 文字コード未実装例外
     */
    public static void addTempFileForSend(
            Multipart mp, File file, String fileName, String encode
            ) throws MessagingException, UnsupportedEncodingException {
        MimeBodyPart mbpFile = createTempFileBody(file, fileName, encode);

        //ボディパートの追加
        mp.addBodyPart(mbpFile);

    }
    /**
    *
    * <br>[機  能] 送信メール ボディの添付ファイル追加
    * <br>[解  説]
    * <br>[備  考]
    * @param file ファイル実体
    * @param fileName ファイル名
    * @param encode 出力文字コード
    * @throws MessagingException メッセージ出力例外
    * @throws UnsupportedEncodingException 文字コード未実装例外
    * @return メールボディモデル
    */
   public static MimeBodyPart createTempFileBody(
           File file, String fileName, String encode
           ) throws MessagingException, UnsupportedEncodingException {
       MimeBodyPart mbpFile = new MimeBodyPart();
       // 添付するファイル名を指定
       FileDataSource fds = new FileDataSource(file);
       mbpFile.setDataHandler(new DataHandler(fds));
       String bEnc = MimeUtility.encodeText(fileName, encode, "B");

       ContentType cType = new ContentType(fds.getContentType());
       cType.setParameter("name", bEnc);
       mbpFile.setHeader("Content-Type", cType.toString());

       //filename登録
       __setFileNameRFC2231(mbpFile, fileName, encode);

       return mbpFile;

   }
    /**
     *
     * <br>[機  能] Content-Disposition filename を RFC2231に置き換え
     * <br>[解  説]
     * <br>[備  考]
     * @param mbpFile ボディパート
     * @param fileName ファイル名
     * @param encode エンコード指定
     * @throws MessagingException メール書き込み例外
     * @throws UnsupportedEncodingException 文字コード変換例外
     */
    private static void __setFileNameRFC2231(
            MimeBodyPart mbpFile,
            String fileName,
            String encode
            ) throws MessagingException, UnsupportedEncodingException {
        mbpFile.removeHeader("Content-Disposition");
        ContentDisposition cd =
                new ContentDisposition(Part.ATTACHMENT);
        String prop = "filename";
        //すべてアスキーの場合はエンコードかけずにセット
        boolean nonEnc = __isAllAscii(fileName);
        boolean nonSplit = false;
        String propEnc = "";
        int chrLength = 1;
        if (!nonEnc) {
            chrLength = 3;
            propEnc = "*";
        }
        //バイト配列
        byte[] bArr = fileName.getBytes(encode);

        //filename*=<エンコード済>;<CRLF>で79文字以内の場合
        if (nonEnc && fileName.length() * chrLength + prop.length()
                + propEnc.length() + 3 <= MAXSTRLENGTH_RFC5322) {
            nonSplit = true;
        } else if (!nonEnc && bArr.length * chrLength + prop.length()
        + propEnc.length() + 3 + encode.length() + 2 <= MAXSTRLENGTH_RFC5322) {
            nonSplit = true;
        }
        ParameterList pl = new ParameterList();
        //プロパティ名含め79以内毎に分割
        StringBuilder sb = new StringBuilder();
        if (!nonEnc) {
            sb.append(encode);
            sb.append("''");
        }
        int segment = 0;
        for (byte c : bArr) {
            if (nonEnc) {
                sb.append(new String(new byte[] {c}, encode));
            } else {
                sb.append("%");
                sb.append(String.format("%02X", c));
            }
            if (!nonSplit && sb.length()
                    + prop.length() + propEnc.length() + 1
                    + String.valueOf(segment).length() + 1
                    + chrLength >= MAXSTRLENGTH_RFC5322) {
                pl.set(prop + "*" + String.valueOf(segment) + propEnc, sb.toString());
                segment++;
                sb = new StringBuilder();
            }
        }
        if (nonSplit) {
            pl.set(prop + propEnc, sb.toString());
            cd.setParameterList(pl);
        } else {
            pl.set(prop + "*" + String.valueOf(segment) + propEnc, sb.toString());
            pl.combineSegments();
            cd.setParameterList(pl);
        }
        mbpFile.setHeader("Content-Disposition", cd.toString());

    }
    /**
     *
     * <br>[機  能] すべてAscii文字か確認
     * <br>[解  説]
     * <br>[備  考]
     * @param name チェック対象文字列
     * @return true:すべてAscii false:Ascii以外の文字を含む
     */
    private static boolean __isAllAscii(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!StringUtil.isAscii(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}