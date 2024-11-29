package jp.groupsession.v2.usr.usr030;

import java.io.PrintWriter;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.UserCsvModel;

/**
 * <br>[機  能] ユーザ情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsrCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public UsrCsvRecordListenerImpl(PrintWriter pw) {
        pw__ = pw;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        UserCsvModel csvModel = (UserCsvModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //ログインID
        sb.append(CsvEncode.encString(csvModel.getUsrLgid()));
        sb.append(",");

        //ワンタイムパスワード通知先メールアドレス
        sb.append(CsvEncode.encString(csvModel.getUsiOtpsendAddress()));
        sb.append(",");

        //職員・職員番号
        sb.append(CsvEncode.encString(csvModel.getUsiSyainNo()));
        sb.append(",");

        //姓
        sb.append(CsvEncode.encString(csvModel.getUsiSei()));
        sb.append(",");

        //名
        sb.append(CsvEncode.encString(csvModel.getUsiMei()));
        sb.append(",");

        //姓カナ
        sb.append(CsvEncode.encString(csvModel.getUsiSeiKn()));
        sb.append(",");

        //名カナ
        sb.append(CsvEncode.encString(csvModel.getUsiMeiKn()));
        sb.append(",");

        //ログイン停止
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsrUkoFlg())));
        sb.append(",");

        //所属
        sb.append(CsvEncode.encString(csvModel.getUsiSyozoku()));
        sb.append(",");

        //役職
        sb.append(CsvEncode.encString(csvModel.getUsiYakusyoku()));
        sb.append(",");

        //ソートキー1
        sb.append(CsvEncode.encString(csvModel.getUsiSortkey1()));
        sb.append(",");

        //ソートキー2
        sb.append(CsvEncode.encString(csvModel.getUsiSortkey2()));
        sb.append(",");

        //性別
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiSeibetu())));
        sb.append(",");

        //入社年月日
        if (csvModel.getUsiEntranceDate() != null) {
            sb.append(CsvEncode.encString(csvModel.getUsiEntranceDate().getDateString()));
        } else {
            sb.append("");
        }
        sb.append(",");

        //生年月日
        if (csvModel.getUsiBdate() != null) {
            sb.append(CsvEncode.encString(csvModel.getUsiBdate().getDateString()));
        } else {
            sb.append("");
        }
        sb.append(",");

        //生年月日公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiBdateKf())));
        sb.append(",");

        //メールアドレス１
        sb.append(CsvEncode.encString(csvModel.getUsiMail1()));
        sb.append(",");

        //メールアドレスコメント１
        sb.append(CsvEncode.encString(csvModel.getUsiMailCmt1()));
        sb.append(",");

        //メールアドレス１公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiMail1Kf())));
        sb.append(",");

        //メールアドレス２
        sb.append(CsvEncode.encString(csvModel.getUsiMail2()));
        sb.append(",");

        //メールアドレスコメント２
        sb.append(CsvEncode.encString(csvModel.getUsiMailCmt2()));
        sb.append(",");

        //メールアドレス２公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiMail2Kf())));
        sb.append(",");

        //メールアドレス３
        sb.append(CsvEncode.encString(csvModel.getUsiMail3()));
        sb.append(",");

        //メールアドレスコメント３
        sb.append(CsvEncode.encString(csvModel.getUsiMailCmt3()));
        sb.append(",");

        //メールアドレス３公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiMail3Kf())));
        sb.append(",");

        //郵便番号
        String postNum = "";
        String post1 = NullDefault.getString(csvModel.getUsiZip1(), "");
        String post2 = NullDefault.getString(csvModel.getUsiZip2(), "");
        if (post1.length() > 0 && post2.length() > 0) {
            postNum = CsvEncode.encString(post1 + "-" + post2);
        }
        sb.append(postNum);
        sb.append(",");

        //郵便番号公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiZipKf())));
        sb.append(",");

        //都道府県コード
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getTdfSid())));
        sb.append(",");

        //都道府県公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiTdfKf())));
        sb.append(",");

        //住所１
        sb.append(CsvEncode.encString(csvModel.getUsiAddr1()));
        sb.append(",");

        //住所１公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiAddr1Kf())));
        sb.append(",");

        //住所２
        sb.append(CsvEncode.encString(csvModel.getUsiAddr2()));
        sb.append(",");

        //住所２公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiAddr2Kf())));
        sb.append(",");

        //電話番号１
        sb.append(CsvEncode.encString(csvModel.getUsiTel1()));
        sb.append(",");

        //電話番号内線１
        sb.append(CsvEncode.encString(csvModel.getUsiTelNai1()));
        sb.append(",");

        //電話番号コメント１
        sb.append(CsvEncode.encString(csvModel.getUsiTelCmt1()));
        sb.append(",");

        //電話番号１公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiTel1Kf())));
        sb.append(",");

        //電話番号２
        sb.append(CsvEncode.encString(csvModel.getUsiTel2()));
        sb.append(",");

        //電話番号内線２
        sb.append(CsvEncode.encString(csvModel.getUsiTelNai2()));
        sb.append(",");

        //電話番号コメント２
        sb.append(CsvEncode.encString(csvModel.getUsiTelCmt2()));
        sb.append(",");

        //電話番号２公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiTel2Kf())));
        sb.append(",");

        //電話番号３
        sb.append(CsvEncode.encString(csvModel.getUsiTel3()));
        sb.append(",");

        //電話番号内線３
        sb.append(CsvEncode.encString(csvModel.getUsiTelNai3()));
        sb.append(",");

        //電話番号コメント３
        sb.append(CsvEncode.encString(csvModel.getUsiTelCmt3()));
        sb.append(",");

        //電話番号３公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiTel3Kf())));
        sb.append(",");

        //ＦＡＸ１
        sb.append(CsvEncode.encString(csvModel.getUsiFax1()));
        sb.append(",");

        //ＦＡＸコメント１
        sb.append(CsvEncode.encString(csvModel.getUsiFaxCmt1()));
        sb.append(",");

        //ＦＡＸ１公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiFax1Kf())));
        sb.append(",");

        //ＦＡＸ２
        sb.append(CsvEncode.encString(csvModel.getUsiFax2()));
        sb.append(",");

        //ＦＡＸコメント２
        sb.append(CsvEncode.encString(csvModel.getUsiFaxCmt2()));
        sb.append(",");

        //ＦＡＸ２公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiFax2Kf())));
        sb.append(",");

        //ＦＡＸ３
        sb.append(CsvEncode.encString(csvModel.getUsiFax3()));
        sb.append(",");

        //ＦＡＸコメント３
        sb.append(CsvEncode.encString(csvModel.getUsiFaxCmt3()));
        sb.append(",");

        //ＦＡＸ３公開フラグ
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUsiFax3Kf())));
        sb.append(",");

        //備考
        sb.append(CsvEncode.encString(csvModel.getUsiBiko()));

        pw__.println(sb.toString());
    }
}