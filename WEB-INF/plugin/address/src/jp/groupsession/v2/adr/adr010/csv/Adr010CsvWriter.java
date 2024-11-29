package jp.groupsession.v2.adr.adr010.csv;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.adr.adr010.dao.Adr010Dao;
import jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳一覧のエクスポートを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr010CsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** アドレス帳情報一覧ファイル名 */
    public static final String FILE_NAME = "addressList.csv";

    /** 検索条件 */
    private Adr010SearchModel searchModel__ = null;

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @param reqMdl RequestModel
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath, RequestModel reqMdl)
    throws CSVException {

        setCon(con);
        setReqMdl(reqMdl);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
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
     * @throws CSVException CSV出力時例外
     */
    private void __writeHeader(PrintWriter pw) throws CSVException {

        try {
            GsMessage gsMsg = new GsMessage(reqMdl_);

            StringBuilder strHeader = new StringBuilder();

            //氏名(姓)
            strHeader.append(gsMsg.getMessage("user.src.47"));
            strHeader.append(",");
            //氏名(名)
            strHeader.append(gsMsg.getMessage("user.src.45"));
            strHeader.append(",");
            //氏名カナ(姓)
            strHeader.append(gsMsg.getMessage("user.src.48"));
            strHeader.append(",");
            //氏名カナ(名)
            strHeader.append(gsMsg.getMessage("user.src.46"));
            strHeader.append(",");
            //所属
            strHeader.append(gsMsg.getMessage("cmn.affiliation"));
            strHeader.append(",");
            //役職
            strHeader.append(gsMsg.getMessage("cmn.post"));
            strHeader.append(",");
            //メールアドレス１
            strHeader.append(gsMsg.getMessage("cmn.mailaddress1"));
            strHeader.append(",");
            //メールアドレスコメント１
            strHeader.append(gsMsg.getMessage("cmn.mailaddress1.comment"));
            strHeader.append(",");
            //メールアドレス２
            strHeader.append(gsMsg.getMessage("cmn.mailaddress2"));
            strHeader.append(",");
            //メールアドレスコメント２
            strHeader.append(gsMsg.getMessage("mailaddress2.comment"));
            strHeader.append(",");
            //メールアドレス３
            strHeader.append(gsMsg.getMessage("cmn.mailaddress3"));
            strHeader.append(",");
            //メールアドレスコメント３
            strHeader.append(gsMsg.getMessage("mailaddress3.comment"));
            strHeader.append(",");
            //郵便番号
            strHeader.append(gsMsg.getMessage("cmn.postalcode"));
            strHeader.append(",");
            //都道府県
            strHeader.append(gsMsg.getMessage("cmn.prefectures"));
            strHeader.append(",");
            //住所１
            strHeader.append(gsMsg.getMessage("cmn.address1"));
            strHeader.append(",");
            //住所２
            strHeader.append(gsMsg.getMessage("cmn.address2"));
            strHeader.append(",");
            //電話番号１
            strHeader.append(gsMsg.getMessage("cmn.tel1"));
            strHeader.append(",");
            //内線１
            strHeader.append(gsMsg.getMessage("address.src.17"));
            strHeader.append(",");
            //電話番号コメント１
            strHeader.append(gsMsg.getMessage("user.src.36"));
            strHeader.append(",");
            //電話番号２
            strHeader.append(gsMsg.getMessage("cmn.tel2"));
            strHeader.append(",");
            //内線２
            strHeader.append(gsMsg.getMessage("address.src.18"));
            strHeader.append(",");
            //電話番号コメント２
            strHeader.append(gsMsg.getMessage("user.src.37"));
            strHeader.append(",");
            //電話番号３
            strHeader.append(gsMsg.getMessage("cmn.tel3"));
            strHeader.append(",");
            //内線３
            strHeader.append(gsMsg.getMessage("address.src.19"));
            strHeader.append(",");
            //電話番号コメント３
            strHeader.append(gsMsg.getMessage("user.src.38"));
            strHeader.append(",");
            //ＦＡＸ１
            strHeader.append("ＦＡＸ１");
            strHeader.append(",");
            //ＦＡＸコメント１
            strHeader.append(gsMsg.getMessage("cmn.fax.comment1"));
            strHeader.append(",");
            //ＦＡＸ２
            strHeader.append("ＦＡＸ２");
            strHeader.append(",");
            //ＦＡＸコメント２
            strHeader.append(gsMsg.getMessage("cmn.fax.comment2"));
            strHeader.append(",");
            //ＦＡＸ３
            strHeader.append("ＦＡＸ３");
            strHeader.append(",");
            //ＦＡＸコメント３
            strHeader.append(gsMsg.getMessage("cmn.fax.comment3"));
            strHeader.append(",");
            //備考
            strHeader.append(gsMsg.getMessage("cmn.memo"));
            strHeader.append(",");
            //企業コード
            strHeader.append(gsMsg.getMessage("address.7"));
            strHeader.append(",");
            //会社名
            strHeader.append(gsMsg.getMessage("cmn.company.name"));
            strHeader.append(",");
            //会社名(カナ)
            strHeader.append(gsMsg.getMessage("cmn.cmn160.1"));
            strHeader.append(",");
            //会社郵便番号
            strHeader.append(gsMsg.getMessage("address.src.35"));
            strHeader.append(",");
            //会社都道府県
            strHeader.append(gsMsg.getMessage("address.src.36"));
            strHeader.append(",");
            //会社住所１
            strHeader.append(gsMsg.getMessage("address.src.37"));
            strHeader.append(",");
            //会社住所２
            strHeader.append(gsMsg.getMessage("address.src.38"));
            strHeader.append(",");
            //URL
            strHeader.append("URL");
            strHeader.append(",");
            //備考(会社情報)
            strHeader.append(gsMsg.getMessage("address.src.42"));
            strHeader.append(",");
            //企業拠点種別
            strHeader.append(gsMsg.getMessage("address.src.29"));
            strHeader.append(",");
            //企業拠点名
            strHeader.append(gsMsg.getMessage("address.src.30"));
            strHeader.append(",");
            //拠点郵便番号
            strHeader.append(gsMsg.getMessage("address.src.10"));
            strHeader.append(",");
            //拠点都道府県
            strHeader.append(gsMsg.getMessage("address.src.11"));
            strHeader.append(",");
            //拠点住所１
            strHeader.append(gsMsg.getMessage("address.src.12"));
            strHeader.append(",");
            //拠点住所２
            strHeader.append(gsMsg.getMessage("address.src.13"));
            strHeader.append(",");
            //企業拠点備考
            strHeader.append(gsMsg.getMessage("address.src.31"));
            strHeader.append(",");

            pw.println(strHeader.toString());

        } catch (Exception e) {
            log__.error("Exception", e);
            throw new CSVException("ヘッダ部分生成に失敗", e);
        }
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        Adr010CsvRecordListenerImpl csvListener
                = new Adr010CsvRecordListenerImpl(pw);
        Connection con = getCon();

        try {

            Adr010Dao adr010Dao = new Adr010Dao(con);
            List<Adr010CsvDetailModel> csvDataList
                = adr010Dao.getSearchResultListForExport(getSearchModel(), getReqMdl());
            GsMessage gsMsg = new GsMessage(reqMdl_);

            for (Adr010CsvDetailModel detailData : csvDataList) {

                if (detailData.getContactType() == GSConst.CONTYP_OTHER) {
                    //その他
                    detailData.setTypeName(gsMsg.getMessage("cmn.other"));
                } else if (detailData.getContactType() == GSConst.CONTYP_TEL) {
                    //電話
                    detailData.setTypeName(gsMsg.getMessage("cmn.phone"));
                } else if (detailData.getContactType() == GSConst.CONTYP_MAIL) {
                    //メール
                    detailData.setTypeName(gsMsg.getMessage("cmn.mail"));
                } else if (detailData.getContactType() == GSConst.CONTYP_WEB) {
                    detailData.setTypeName(GSConst.TEXT_CONTYP_WEB);
                } else if (detailData.getContactType() == GSConst.CONTYP_MEETING) {
                    //打ち合わせ
                    detailData.setTypeName(gsMsg.getMessage("address.28"));
                }
                csvListener.setRecord(detailData);
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
    public Adr010SearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(Adr010SearchModel searchModel) {
        searchModel__ = searchModel;
    }

    /**
     * <p>req を取得します。
     * @return reqMdl RequestModel
     */
    public RequestModel getReqMdl() {
        return reqMdl_;
    }

    /**
     * <p>req をセットします。
     * @param reqMdl RequestModel
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }
}