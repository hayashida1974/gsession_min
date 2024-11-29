package jp.groupsession.v2.usr.usr040;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ユーザ情報一覧(CSV)を出力する(一般ユーザ向け)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsrCsvWriterIppan extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(UsrCsvWriterIppan.class);

    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /** ユーザ情報一覧ファイル名 */
    public static final String FILE_NAME = "shainList.csv";

    /** 検索条件 */
    private ShainSearchModel searchModel__ = null;
    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public UsrCsvWriterIppan(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath)
    throws CSVException {

        setCon(con);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
//        UDate date = new UDate();
        String fileName = FILE_NAME;
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        //出力初期セット
        setCsvPath(fileFullPath);

        log__.debug("開始");
        write();
        log__.debug("終了");
    }

    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public void create(PrintWriter pw) throws CSVException {

        //ヘッダ
        __writeHeader(pw);

        //明細
        __writeItem(pw);
    }

    /**
     * <p>ヘッダ部分を生成します。
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //メールアドレスコメント１
        String textMailAdrComment1 = gsMsg.getMessage("cmn.mailaddress1.comment");
        //メールアドレスコメント2
        String textMailAdrComment2 = gsMsg.getMessage("mailaddress2.comment");
        //メールアドレスコメント3
        String textMailAdrComment3 = gsMsg.getMessage("mailaddress3.comment");
        //メールアドレス１公開フラグ
        String textMailPublicFlg1 = gsMsg.getMessage("user.src.21");
        //メールアドレス２公開フラグ
        String textMailPublicFlg2 = gsMsg.getMessage("user.src.22");
        //メールアドレス３公開フラグ
        String textMailPublicFlg3 = gsMsg.getMessage("user.src.23");
        //電話番号内線１
        String textTelNai1 = gsMsg.getMessage("user.src.39");
        //電話番号内線２
        String textTelNai2 = gsMsg.getMessage("user.src.40");
        //電話番号内線３
        String textTelNai3 = gsMsg.getMessage("user.src.41");
        //電話番号１公開フラグ
        String textTelPubFlg1 = gsMsg.getMessage("user.src.42");
        //電話番号２公開フラグ
        String textTelPubFlg2 = gsMsg.getMessage("user.src.43");
        //電話番号３公開フラグ
        String textTelPubFlg3 = gsMsg.getMessage("user.src.44");
        //電話番号コメント１
        String textTelComment1 = gsMsg.getMessage("user.src.36");
        //電話番号コメント２
        String textTelComment2 = gsMsg.getMessage("user.src.37");
        //電話番号コメント３
        String textTelComment3 = gsMsg.getMessage("user.src.38");
        //ＦＡＸ１公開フラグ
        String textFaxPubFlg1 = gsMsg.getMessage("user.src.13");
        //ＦＡＸ２公開フラグ
        String textFaxPubFlg2 = gsMsg.getMessage("user.src.14");
        //ＦＡＸ３公開フラグ
        String textFaxPubFlg3 = gsMsg.getMessage("user.src.15");
        //ＦＡＸコメント１
        String textFaxComment1 = gsMsg.getMessage("cmn.fax.comment1");
        //ＦＡＸコメント２
        String textFaxComment2 = gsMsg.getMessage("cmn.fax.comment2");
        //ＦＡＸコメント３
        String textFaxComment3 = gsMsg.getMessage("cmn.fax.comment3");
        //郵便番号
        String textPostCode = gsMsg.getMessage("cmn.postalcode");
        //郵便番号公開フラグ
        String textPostPubFlg = gsMsg.getMessage("user.src.32");
        //都道府県コード
        String textTdfkCode = gsMsg.getMessage("user.148");
        //都道府県公開フラグ
        String textTdfkPubFlg = gsMsg.getMessage("user.src.35");
        //住所１公開フラグ
        String textAddressPubFlg = gsMsg.getMessage("user.src.6");
        //住所２公開フラグ
        String textAddress2Open = gsMsg.getMessage("user.src.7");
        //生年月日(西暦)
        String textBirthDday = gsMsg.getMessage("user.120");
        //生年月日公開フラグ
        String textBirthDdayFlg = gsMsg.getMessage("user.src.8");
        //メールアドレス1
        String textMailAddress1 = gsMsg.getMessage("cmn.mailaddress1");
        //メールアドレス2
        String textMailAddress2 = gsMsg.getMessage("cmn.mailaddress2");
        //メールアドレス3
        String textMailAddress3 = gsMsg.getMessage("cmn.mailaddress3");
        //電話番号１
        String textTel1 = gsMsg.getMessage("cmn.tel1");
        //電話番号２
        String textTel2 = gsMsg.getMessage("cmn.tel2");
        //電話番号３
        String textTel3 = gsMsg.getMessage("cmn.tel3");
        //住所１
        String textAddress1 = gsMsg.getMessage("cmn.address1");
        //住所２
        String textAddress2 = gsMsg.getMessage("cmn.address2");
        //備考
        String textMemo = gsMsg.getMessage("cmn.memo");
        //社員/職員番号
        String textStaffNumber = gsMsg.getMessage("cmn.employee.staff.number");
        //役職
        String textPost = gsMsg.getMessage("cmn.post");
        //姓
        String textLastName = gsMsg.getMessage("cmn.lastname");
        //名
        String textFirstName = gsMsg.getMessage("cmn.name3");
        //姓カナ
        String textSeiKana = gsMsg.getMessage("user.src.48");
        //名カナ
        String textMeiKana = gsMsg.getMessage("user.src.46");
        //所属
        String textAffiliation = gsMsg.getMessage("cmn.affiliation");
        //性別
        String textSeibetu = gsMsg.getMessage("user.123");
        //入社年月日
        String textEntranceDate = gsMsg.getMessage("user.156");


        String strHeader =
            textStaffNumber + "," + textLastName + "," + textFirstName + "," + textSeiKana
        + "," + textMeiKana + "," + textAffiliation + "," + textPost
        + "," + textSeibetu + "," + textEntranceDate + "," + textBirthDday
        + "," + textBirthDdayFlg + "," + textMailAddress1 + "," + textMailAdrComment1
        + "," + textMailPublicFlg1 + "," + textMailAddress2 + "," + textMailAdrComment2
        + "," + textMailPublicFlg2 + "," + textMailAddress3 + "," + textMailAdrComment3
        + "," + textMailPublicFlg3 + "," + textPostCode + "," + textPostPubFlg + "," + textTdfkCode
        + "," + textTdfkPubFlg + "," + textAddress1 + "," + textAddressPubFlg + "," + textAddress2
        + "," + textAddress2Open + "," + textTel1 + "," + textTelNai1 + "," + textTelComment1
        + "," + textTelPubFlg1 + "," + textTel2 + "," + textTelNai2 + "," + textTelComment2
        + "," + textTelPubFlg2 + "," + textTel3 + "," + textTelNai3 + "," + textTelComment3
        + "," + textTelPubFlg3 + "," + "ＦＡＸ１" + "," + textFaxComment1 + "," + textFaxPubFlg1
        + "," + "ＦＡＸ２" + "," + textFaxComment2 + "," + textFaxPubFlg2 + "," + "ＦＡＸ３"
        + "," + textFaxComment3 + "," + textFaxPubFlg3 + "," + textMemo;
        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        UsrCsvRecordListenerIppanImpl rl = new UsrCsvRecordListenerIppanImpl(pw, con__, reqMdl__);
        Connection con = getCon();

        try {

            //都道府県情報を取得、セット
            CmnTdfkDao ctDao = new CmnTdfkDao(con);
            rl.setTdfkMap(ctDao.getTdfkMap());

            UserSearchDao sDao = new UserSearchDao(con);
            int mode = searchModel__.getOutputCsvMode();
            if (mode == GSConstUser.MODE_NAME) {
                //氏名
                sDao.createUserInfoKanaForCsv(searchModel__.getSearchKana(),
                        rl, searchModel__.getSortKey(), searchModel__.getSortOrder(),
                        searchModel__.getSortKey2(), searchModel__.getSortOrder2(),
                        searchModel__.getLabelSid());
            } else if (mode == GSConstUser.MODE_GROUP) {
                //グループ
                sDao.createUserInfoGroupForCsv(searchModel__.getSelectgsid(),
                        null, rl, searchModel__.getSortKey(), searchModel__.getSortOrder(),
                        searchModel__.getSortKey2(), searchModel__.getSortOrder2(),
                        searchModel__.getLabelSid());
            } else if (mode == GSConstUser.MODE_SHOUSAI) {
                //詳細
                sDao.createUserInfoSyousaiForCsv(searchModel__, rl);
            }
        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>searchModel を取得します。
     * @return searchModel
     */
    public ShainSearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(ShainSearchModel searchModel) {
        searchModel__ = searchModel;
    }
}