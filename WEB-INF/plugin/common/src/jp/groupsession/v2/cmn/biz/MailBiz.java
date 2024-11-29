package jp.groupsession.v2.cmn.biz;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;

/**
 * <br>[機  能] メール情報に関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MailBiz {
    /** MIME 改行文字数 */
    private static final int MIME_LINELEN__ = 76;

    /**
     * <br>[機  能] 指定された文字列をBase64変換する
     * <br>[解  説]
     * <br>[備  考] 76文字ごとに改行を行なう
     * @param value 文字列
     * @param encode 文字コード
     * @return 変換後の文字列
     * @throws UnsupportedEncodingException 文字コードが不正
     */
    public String createMime(String value, String encode)
    throws UnsupportedEncodingException {

        String mimeStr = value;
        if (!StringUtil.isNullZeroString(mimeStr)) {
            mimeStr = new String(
                    Base64.encodeBase64(value.getBytes(encode)), "UTF-8");

            if (mimeStr.length() > MIME_LINELEN__) {
                String lines = "";
                for (int idx = 0; idx < mimeStr.length(); idx += MIME_LINELEN__) {
                    int end = idx + MIME_LINELEN__;
                    if (end > mimeStr.length()) {
                        end = mimeStr.length();
                    }
                    String substr = mimeStr.substring(idx, end);
                    if (lines.length() > 0) {
                        lines += "\r\n";
                    }
                    lines += substr;
                }
                mimeStr = lines;
            }
        }

        return mimeStr;
    }

    /**
     * <br>[機  能] 指定した文字列をMIMEエンコードして返す
     * <br>[解  説]
     * <br>[備  考]
     * @param value 文字列
     * @param encode 文字コード
     * @return MIMEエンコードした文字列
     * @throws UnsupportedEncodingException 不正な文字コードを指定
     */
    public String mimeEncode(String value, String encode)
    throws UnsupportedEncodingException {
        if (StringUtil.isNullZeroString(encode)
        || StringUtil.isNullZeroString(value)) {
            return value;
        }
        String mimeEncode = "Q";
        if (encode.equals(Encoding.UTF_8)) {
            mimeEncode = "B";
        }

        return MimeUtility.encodeText(value,
                                                    encode,
                                                    mimeEncode);
    }

    /**
     * <br>[機  能] 指定した文字列をbase64変換した場合のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param value 文字列
     * @param encode 文字コード
     * @return サイズ
     * @throws UnsupportedEncodingException 文字コードが不正
     */
    public long getBase64Size(String value, String encode)
    throws UnsupportedEncodingException {
        String base64Value = createMime(value, encode);

        return NullDefault.getString(base64Value, "").length();
    }

    /**
     * <br>[機  能] 指定したバイナリ情報をbase64変換した場合のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dataLen バイナリ情報の長さ
     * @return サイズ
     * @throws UnsupportedEncodingException 文字コードが不正
     */
    public long getBinaryBase64Size(long dataLen)
    throws UnsupportedEncodingException {

        // 6bit単位のブロック数
        long block6Count = (dataLen * 8) + 5;
        block6Count = block6Count / 6;
        // 4文字単位のブロック数
        long block4Count = block6Count + 3;
        block4Count = block4Count / 4;

        // サイズ = base64変換後の文字数 + 76文字ごとの改行("\r\n")
        long dataSize = block4Count * 4;
        dataSize += (dataSize / MIME_LINELEN__) * 2;

        return dataSize;
    }

    /**
     * <br>[機  能] 送信先のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @param sendEncode 文字コード
     * @return 送信先のサイズ
     * @throws UnsupportedEncodingException 不正な文字コードを指定
     */
    public long getAddressSize(String address, String sendEncode)
    throws UnsupportedEncodingException {
        long size = 0;
        if (address != null && sendEncode != null) {
            try {
                InternetAddress[] addressList = parseAddressPlus(address, sendEncode);
                for (InternetAddress iAddress : addressList) {
                    size += iAddress.getAddress().length() + 2;
                }
            } catch (Exception e) {
                size += address.getBytes(Encoding.UTF_8).length;
            }
        }
        return size;
    }

    /**
     * <br>[機  能] 指定されたアドレスを InternetAddress オブジェクトに構文解析する
     * <br>[解  説]
     * <br>[備  考]
     * @param address アドレス
     * @return InternetAddress オブジェクト
     * @throws AddressException 構文解析に失敗
     */
    public static InternetAddress[] parseAddress(String address) throws AddressException {
        return InternetAddress.parse(address, true);
    }

    /**
     * <br>[機  能] 指定されたアドレスを InternetAddress オブジェクトに構文解析する
     * <br>[解  説] 個人名をエンコードする
     * <br>[備  考]
     * @param address アドレス
     * @param encode 文字コード
     * @return InternetAddress オブジェクト
     * @throws AddressException 構文解析に失敗
     * @throws UnsupportedEncodingException 個人名のエンコーディングに失敗
     */
    public static InternetAddress[] parseAddressPlus(String address, String encode)
    throws AddressException, UnsupportedEncodingException {
        InternetAddress[] addressList = parseAddress(address);
        for (int i = 0; i < addressList.length; i++) {
            if (!StringUtil.isNullZeroString(addressList[i].getPersonal())) {
                addressList[i].setPersonal(addressList[i].getPersonal(), encode);
            }
        }
        return addressList;
    }

    /**
     * <br>[機  能] メールアドレスの「名前」欄を変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param personal メールアドレスの「名前」欄
     * @return 変換したメールアドレスの「名前」欄
     */
    public static String formatPersonal(String personal) {
        if (personal == null || personal.trim().length() == 0) {
            return personal;
        }

        String formatPersonal = personal.toString();
        formatPersonal = formatPersonal.replaceAll("\\\\", "\\\\\\\\");
        formatPersonal = formatPersonal.replaceAll("\"", "\\\\\"");
        formatPersonal = "\"" + formatPersonal + "\"";

        return formatPersonal;
    }

    /**
     * <br>[機  能] 指定されたメールアドレスからアドレス部分のみを抽出する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailAddress メールアドレス
     * @return アドレス
     */
    public String extractAddress(String mailAddress) {
        if (StringUtil.isNullZeroString(mailAddress)) {
            return mailAddress;
        }

        String address = mailAddress;
        try {
            InternetAddress[] addressList = parseAddress(mailAddress);
            if (addressList != null && addressList.length > 0) {
                address = addressList[0].getAddress();
            }
        } catch (AddressException e) {
        }

        return address;
    }
}
