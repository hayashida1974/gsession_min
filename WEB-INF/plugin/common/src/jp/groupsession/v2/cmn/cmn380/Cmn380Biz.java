package jp.groupsession.v2.cmn.cmn380;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * <br>[機  能] ファイルプレビュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn380Biz {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(Cmn380Biz.class);

    /**
     * <br>[機  能] プレビュー対象ファイルがプレビュー可能かをチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramModel Cmn380ParamModel
     * @return boolean 判定結果（true:プレビュー可能, false:プレビュー不可能）
     */
    public boolean checkPreviewFile(Cmn380ParamModel paramModel) {

        boolean result = true;
        String extension = NullDefault.getString(paramModel.getCmn380PreviewFileExtension(), "");
        String previewUrl = paramModel.getCmn380PreviewURL();
        String checkUrl = NullDefault.getString(previewUrl, "").toLowerCase();

        //拡張子がプレビュー対象外のファイルはプレビュー不可能
        if (!Arrays.asList(GSConstCommon.FILEPREVIEW_EXTENSION).contains(extension.toLowerCase())) {
            return false;
        }

        //外部のファイルはプレビュー不可能
        if (checkUrl.startsWith("http://") || checkUrl.startsWith("https://")) {
            return false;
        }

        //各機能のダウンロードURL以外(ファイルの直接指定)はプレビュー不可能
        int lastIdx = checkUrl.indexOf("?");
        if (lastIdx <= 0 || !checkUrl.substring(0, lastIdx).endsWith(".do")) {
            return false;
        }
        return result;
    }

    /**
     * <br>[機  能] 表示用データをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramModel Cmn380ParamModel
     * @param reqMdl リクエストモデル
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void setDspData(
        Cmn380ParamModel paramModel,
        RequestModel reqMdl) throws URISyntaxException, UnsupportedEncodingException {

        String extension = NullDefault.getString(paramModel.getCmn380PreviewFileExtension(), "");
        String previewUrl = paramModel.getCmn380PreviewURL();

        //PDFファイルの場合、PDFプレビュー用URLを生成
        if (extension.toLowerCase().equals("pdf") && !StringUtil.isNullZeroString(previewUrl)) {
            AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
            if (previewUrl.startsWith("../")) {
                previewUrl = previewUrl.substring(3);
            }
            previewUrl = "/" + urlBiz.getContextPath(reqMdl) + "/" + previewUrl;
            paramModel.setCmn380PreviewPdfURL(URLEncoder.encode(previewUrl, Encoding.UTF_8));
        }

        //テキストファイルの場合、文字コード選択コンボに値を設定
        if (Arrays.asList(GSConstCommon.TEXTFILEPREVIEW_EXTENSION)
            .contains(extension.toLowerCase())) {

            GsMessage gsMsg = new GsMessage(reqMdl);
            ArrayList<LabelValueBean> encordingList = new ArrayList<LabelValueBean>();
            encordingList.add(new LabelValueBean(gsMsg.getMessage("cmn.encording.auto"), "0"));
            encordingList.add(new LabelValueBean(gsMsg.getMessage("cmn.encording.utf8"), "1"));
            encordingList.add(new LabelValueBean(gsMsg.getMessage("cmn.encording.jis"), "2"));
            encordingList.add(new LabelValueBean(gsMsg.getMessage("cmn.encording.sjis"), "3"));
            encordingList.add(new LabelValueBean(gsMsg.getMessage("cmn.encording.euc"), "4"));
            paramModel.setCmn380EncordingList(encordingList);
        }
    }

    /**
     * <br>[機  能] プレビュー対象ファイルを表示形式に合わせて変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramModel Cmn380ParamModel
     * @throws IOException
     */
    public void convertFile(Cmn380ParamModel paramModel) throws IOException {

        //プレビュー対象ファイルの取得
        byte[] fileData = null;
        try {
            fileData = paramModel.getCmn380File().getFileData();
        } catch (IOException e) {
            log__.error("プレビュー対象ファイルの読み込みに失敗", e);
            throw e;
        }

        //文字エンコードコンボの選択値に応じて文字エンコードを変更
        Charset charset = Charset.forName(Encoding.UTF_8);
        switch (paramModel.getCmn380SelectEncording()) {
            case 0:
                //「自動判別」選択時、文字コードを判別
                charset = __getEncording(fileData);
                break;
            case 1:
                charset = Charset.forName(Encoding.UTF_8);
                break;
            case 2:
                charset = Charset.forName(Encoding.ISO_2022_JP);
                break;
            case 3:
                charset = Charset.forName(Encoding.SHIFT_JIS);
                break;
            case 4:
                charset = Charset.forName(Encoding.EUC_JP);
                break;
            default:
                charset = Charset.forName(Encoding.UTF_8);
                break;
        }
        String fileContent = new String(fileData, charset);

        //拡張子に応じて無害化またはエスケープ
        String extension = NullDefault.getString(paramModel.getCmn380PreviewFileExtension(), "");
        if (extension.toLowerCase().equals("html")) {
            paramModel.setCmn380FileContent(StringUtilHtml.removeIllegalTag(fileContent));
        } else {
            paramModel.setCmn380FileContent(
                StringUtilHtml.transToHTmlPlusAmparsantAndLink(fileContent));
        }
    }

    /**
     * <br>[機  能] プレビュー対象ファイルの文字コードを解析する
     * <br>[解  説] 解析結果として文字コードを返す
     * <br>[備  考] 解析結果がnullまたはJavaで対応していない文字コードの場合、「UTF-8」を返す
     *
     * @param data 解析対象データ
     * @return Charset 文字コード
     */
    private Charset __getEncording(byte[] data) {

        UniversalDetector detector = new UniversalDetector();
        String encording = null;
        int checkCnt = 0;
        while (encording == null && checkCnt <= 5) {
            detector.handleData(data, 0, data.length);
            detector.dataEnd();
            encording = detector.getDetectedCharset();
            checkCnt++;
        }
        if (encording == null
            || encording.toUpperCase().equals("X-ISO-10646-UCS-4-3412")
            || encording.toUpperCase().equals("X-ISO-10646-UCS-4-2143")) {
            encording = Encoding.UTF_8;
        }
        detector.reset();
        return Charset.forName(encording);
    }
}