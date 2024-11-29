package jp.groupsession.v2.sml.pdf;

import java.io.FileOutputStream;
import java.io.OutputStream;

import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;


public class SmlPdfUtilTest {

    public static void main(String[] args) throws Exception {
        SmlPdfUtilTest self = new SmlPdfUtilTest();
        String appRootPath = "/Users/n_yoshida/Documents/workspace/GSession4_5/war";
        String outTempDir = "/Users/n_yoshida/Desktop/";
        SmlPdfModel smlMdl = self.createSmlPdf(appRootPath, outTempDir, 0);

    }
    /**
     * <br>[機  能] test1メール内容をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @return pdfModel SmlPdfModel
     * @throws Exception
     */
    public SmlPdfModel createSmlPdf(
            String appRootPath,
            String outTempDir, int i)
        throws Exception {
        OutputStream oStream = null;


        //アカウント名
        String accName = "ユーザ １";
        //件名
        String title = "TEST" + String.valueOf(i+1);
        //差出人
        String sender = "ユーザ １";
        //日時
        String date = "2014/11/28 16:58:41";
        //宛先
        String atesaki =  "あああああ あああああ, いいいいい いいいいい, ううううう ううううう, えええええ えええええ"
                        + ", おおおおお おおおおお, かかかかか　かかかかか, ききききき ききききき, くくくくく くくく"
                        + "くく, けけけけけ　けけけけけ, こここここ こここここ, さささささ さささささ, ししししし し"
                        + "しししし, すすすすす すすすすす, せせせせせ せせせせせ, そそそそそ そそそそそ, たた たたた";
        if (i > 0)
        atesaki        += ", おおおおお おおおおお, かかかかか　かかかかか, ききききき ききききき, くくくくく くくく";
        if (i > 1)
        atesaki        += "くく, けけけけけ　けけけけけ, こここここ こここここ, さささささ さささささ, ししししし し";
        if (i > 2)
        atesaki        += "しししし, すすすすす すすすすす, せせせせせ せせせせせ, そそそそそ そそそそそ, たた たたた";
        if (i > 3)
        atesaki        += ", おおおおお おおおおお, かかかかか　かかかかか, ききききき ききききき, くくくくく くくく";
        if (i > 4)
        atesaki        += "くく, けけけけけ　けけけけけ, こここここ こここここ, さささささ さささささ, ししししし し";
        if (i > 5)
        atesaki        += "しししし, すすすすす すすすすす, せせせせせ せせせせせ, そそそそそ そそそそそ, たた たたた";

        //CC
        String atesakiCC = "";

        String mailKbn = "1";
        String atesakiBCC = null;


        //マーク
        int mark = 0;
        //添付
        String tempFile = "tEsttest";
        //本文
        String main = "◎Group Session スケジュール登録通知\n" +
            "2014/11/27 16:58:41に下記のスケジュールが登録されました。\n" +
            "┏━\n" +
            "┃ショートメール通知\n" +
            "┃ 2014/11/27 09:00:00　～　2014/11/27 18:00:00\n" +
            "┗━\n" +
            "URL:http://192.168.1.168:8080/gsession4_5/common/cmn001.do?url=%2Fgsession4_5%2Fschedu\n" +
            "le%2Fsch040.do%3Fsch010SelectDate%3D20141127%26cmd%3Dedit%26sch010SchSid%3D231165%26sc\n" +
            "h010SelectUsrSid%3D101%26sch010SelectUsrKbn%3D0%26sch010DspDate%3D20141127%26dspMod%3D\n" +
            "1%26sch010DspGpSid%3D1\n" +
            "被登録者:山田 太郎\n" +
            "登録者:石橋 鈴音\n" +
            "◆内容1\n" +
            "◆内容2\n" +
            "◆内容3\n" +
            "◆内容4\n" +
            "◆内容5\n" +
            "◆内容6\n" +
            "◆内容7\n" +
            "◆内容8\n" +
            "◆内容9\n" +
            "◆内容10\n" +
            "◆内容11\n" +
            "◆内容12\n" +
            "◆内容13\n" +
            "◆内容14\n" +
            "◆内容15\n" +
            "◆内容16\n" +
            "◆内容17\n" +
            "◆内容18\n" +
            "◆内容19\n" +
            "◆内容20\n" +
            "◆内容21\n" +
            "◆内容22\n" +
            "◆内容23\n" +
            "◆内容24\n" +
            "◆内容25\n" +
            "◆内容26\n" +
            "◆内容27\n" +
            "◆内容28\n" +
            "◆内容29\n" +
            "◆内容30\n" +
            "◆備考";

        //PDF用モデルにデータセット
        SmlPdfModel pdfModel = new SmlPdfModel();
        pdfModel.setAccName(accName);
        pdfModel.setTitle(title);
        pdfModel.setSender(sender);
        pdfModel.setDate(date);
        pdfModel.setAtesaki(atesaki);
        pdfModel.setAtesakiCC(atesakiCC);
        pdfModel.setAtesakiBCC(atesakiBCC);
        pdfModel.setMark(mark);
        pdfModel.setTempFile(tempFile);
        pdfModel.setMain(main);

        String bookName = "141122"
                + "_" + "16:58:41"
                + "_" + title;

        String outBookName = bookName + ".pdf";
        pdfModel.setFileName(outBookName);

        //ダウンロード時のファイル名
        String saveFileName = outBookName;
        pdfModel.setSaveFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            SmlPdfUtil util = new SmlPdfUtil();
            util.createSmalPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            throw e;
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }

        return pdfModel;
    }


}
