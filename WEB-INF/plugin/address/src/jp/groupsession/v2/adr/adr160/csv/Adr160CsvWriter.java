package jp.groupsession.v2.adr.adr160.csv;

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
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.dao.Adr010Dao;
import jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.adr.adr160.dao.Adr160ContactDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴一覧画面のエクスポート処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr160CsvWriter.class);
    /** コネクション */
    private Connection con__ = null;
    /** コンタクト履歴情報一覧ファイル名 */
    public static final String FILE_NAME = "contactHistoryList.csv";
    /** 実行者SID */
    private int sessionUserSid__;
    /** アドレス帳SID */
    private int addressSid__;

    /** コンタクト履歴検索タイプ 0:アドレス帳一覧コンタクト履歴検索画面 1:コンタクト履歴一覧画面*/
    private int contactSchType__;
    /** 検索条件 */
    private Adr010SearchModel searchModel__ = null;
    /** リクエスト */
    protected RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @param reqMdl RequestModel
     * @throws CSVException CSV出力時例外
     * @throws IOToolsException IOToolsException
     */
    public void outputCsv(Connection con, String csvPath, RequestModel reqMdl)
    throws CSVException, IOToolsException {

        setCon(con);
        setReqMdl(reqMdl);

        //ディレクトリの作成
        IOTools.isDirCheck(csvPath, true);

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
            GsMessage gsMsg = new GsMessage(getReqMdl());
            StringBuilder strHeader = new StringBuilder();

//          String strHeader =
//          "氏名,氏名カナ,会社名,会社名カナ,支店・営業所種別,支店・営業所名,タイトル,種別,コンタクト日時,コンタクト日時TO,プロジェクト,備考";

            //氏名
            strHeader.append(gsMsg.getMessage("cmn.name"));
            strHeader.append(",");
            //氏名カナ
            strHeader.append(gsMsg.getMessage("cmn.name.kana"));
            strHeader.append(",");
            //会社名
            strHeader.append(gsMsg.getMessage("cmn.company.name"));
            strHeader.append(",");
            //会社名カナ
            strHeader.append(gsMsg.getMessage("address.9"));
            strHeader.append(",");
            //支店・営業所種別
            strHeader.append(gsMsg.getMessage("address.src.32"));
            strHeader.append(",");
            //支店・営業所名
            strHeader.append(gsMsg.getMessage("address.src.33"));
            strHeader.append(",");
            //タイトル
            strHeader.append(gsMsg.getMessage("cmn.title"));
            strHeader.append(",");
            //種別
            strHeader.append(gsMsg.getMessage("cmn.type"));
            strHeader.append(",");
            //コンタクト日付FROM
            strHeader.append(gsMsg.getMessage("address.contact.date") + "FROM");
            strHeader.append(",");
            //コンタクト時間FROM
            strHeader.append(gsMsg.getMessage("address.contact.time") + "FROM");
            strHeader.append(",");
            //コンタクト日付TO
            strHeader.append(gsMsg.getMessage("address.contact.date") + "TO");
            strHeader.append(",");
            //コンタクト時間TO
            strHeader.append(gsMsg.getMessage("address.contact.time") + "TO");
            strHeader.append(",");
            //備考
            strHeader.append(gsMsg.getMessage("cmn.memo"));

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

        Adr160CsvRecordListenerImpl rl = new Adr160CsvRecordListenerImpl(pw, getReqMdl());
        Connection con = getCon();

        try {
            Adr160ContactDao tcDao = new Adr160ContactDao(con);

            //アドレス帳コンタクト履歴一覧検索
            if (contactSchType__ == GSConstAddress.DSP_CONTACT_ADR010) {
                Adr010Dao adr010Dao = new Adr010Dao(con);
                List<Adr010CsvDetailModel> csvOutList
                                = adr010Dao.getSearchResultListForExport(
                                        searchModel__, getReqMdl());

                Adr160CsvRecordListenerImpl csvListener
                    = new Adr160CsvRecordListenerImpl(pw, getReqMdl());
                GsMessage gsMsg = new GsMessage();

                for (Adr010CsvDetailModel detailData : csvOutList) {

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
                        //WEB
                        detailData.setTypeName(GSConst.TEXT_CONTYP_WEB);
                    } else if (detailData.getContactType() == GSConst.CONTYP_MEETING) {
                        //打ち合わせ
                        detailData.setTypeName(gsMsg.getMessage("address.28"));
                    }

                    //CSV出力
                    csvListener.setRecord010(detailData);
                }

            //コンタクト履歴一覧
            } else if (contactSchType__ == GSConstAddress.DSP_CONTACT_ADR160) {
                //詳細
                tcDao.createContactForCsv(addressSid__, sessionUserSid__, rl, getReqMdl());
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
     * <p>sessionUserSid を取得します。
     * @return sessionUserSid
     */
    public int getSessionUserSid() {
        return sessionUserSid__;
    }

    /**
     * <p>sessionUserSid をセットします。
     * @param sessionUserSid sessionUserSid
     */
    public void setSessionUserSid(int sessionUserSid) {
        sessionUserSid__ = sessionUserSid;
    }
    /**
     * <p>addressSid を取得します。
     * @return addressSid
     */
    public int getAddressSid() {
        return addressSid__;
    }
    /**
     * <p>addressSid をセットします。
     * @param addressSid addressSid
     */
    public void setAddressSid(int addressSid) {
        addressSid__ = addressSid;
    }

    /**
     * <p>contactSchType を取得します。
     * @return contactSchType
     */
    public int getContactSchType() {
        return contactSchType__;
    }

    /**
     * <p>contactSchType をセットします。
     * @param contactSchType contactSchType
     */
    public void setContactSchType(int contactSchType) {
        contactSchType__ = contactSchType;
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
     * @return req
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }

    /**
     * <p>req をセットします。
     * @param reqMdl RequestModel
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

}