package jp.groupsession.v2.sml.sml060;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlHinaModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール ひな形登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml060Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public Sml060Biz() {
    }

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @return sessionUsrSid セッションユーザSID
     */
    private int __getSessionUserSid(RequestModel reqMdl) {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }

    /**
     * <br>[機  能] ひな形SIDからひな形データを取得しセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param hinaSid ひな形SID
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(int hinaSid,
                             Sml060ParamModel paramMdl,
                             Connection con,
                             RequestModel reqMdl)
        throws SQLException {

        log__.debug("ひな形データ取得");


        if (paramMdl.getSml050HinaKbn() == GSConstSmail.HINA_KBN_PRI) {
            SmlAccountDao sacDao = new SmlAccountDao(con);
            SmlAccountModel sacMdl = new SmlAccountModel();


            if (paramMdl.getSmlAccountSid() <= 0) {
                sacMdl = sacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
            } else {
                sacMdl = sacDao.select(paramMdl.getSmlAccountSid());
            }

            if (sacMdl != null) {
                paramMdl.setSmlAccountSid(sacMdl.getSacSid());
                paramMdl.setSml060AccountName(sacMdl.getSacName());
            }
        }

        if (hinaSid > 0) {

            //ひな形データ取得
            SmlHinaModel param = new SmlHinaModel();
            param.setShnSid(hinaSid);

            SmlHinaDao hinaDao = new SmlHinaDao(con);
            SmlHinaModel ret = hinaDao.select(param);

            //取得したデータをセット
            __setParam(paramMdl, ret);
        }
    }

    /**
     * <br>[機  能] 取得したひな形データをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param bean 取得したひな形データModel
     */
    private void __setParam(Sml060ParamModel paramMdl,
                              SmlHinaModel bean) {

        //ひな形名称
        paramMdl.setSml060HinaName(NullDefault.getString(bean.getShnHname() , ""));
        //件名
        paramMdl.setSml060HinaTitle(NullDefault.getString(bean.getShnTitle(), ""));
        //マーク
        paramMdl.setSml060HinaMark(bean.getShnMark());
        //本文
        paramMdl.setSml060HinaBody(NullDefault.getString(bean.getShnBody(), ""));
    }

   /**
    * <br>[機  能] ひな形SIDからひな形名称を取得する
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param hinaSid ひな形SID
    * @param con コネクション
    * @return hinaName ひな形名称
    * @throws SQLException SQL実行時例外
    */
    public String getHinaName(int hinaSid,
                               Connection con)
     throws SQLException {

     log__.debug("削除するひな形の名称取得");

     String hinaName = "";

     //ひな形名称取得
     SmlHinaDao hinaDao = new SmlHinaDao(con);
     hinaName = hinaDao.getHinaName(hinaSid);

     return hinaName;
 }

    /**
     * <br>[機  能] ひな形編集処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateHinaData(Sml060ParamModel paramMdl,
                                RequestModel reqMdl,
                                Connection con)
        throws SQLException {

        log__.debug("ひな形データ更新");

        //更新データ設定
        SmlHinaModel param = new SmlHinaModel();
        param.setShnSid(paramMdl.getSelectedHinaSid());
        param.setShnHname(NullDefault.getString(paramMdl.getSml060HinaName(), ""));
        param.setShnTitle(NullDefault.getString(paramMdl.getSml060HinaTitle(), ""));
        param.setShnMark(paramMdl.getSml060HinaMark());
        param.setShnBody(NullDefault.getString(paramMdl.getSml060HinaBody(), ""));
        param.setShnEuid(__getSessionUserSid(reqMdl));
        param.setShnEdate(new UDate());
        param.setShnCkbn(paramMdl.getSml050HinaKbn());

        SmlHinaDao hdao = new SmlHinaDao(con);
        hdao.updateHinaData(param);
    }

    /**
     * <br>[機  能] ひな形追加処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param cntCon 採番用コネクション
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertHinaData(Sml060ParamModel paramMdl,
                                RequestModel reqMdl,
                                MlCountMtController cntCon,
                                Connection con)
        throws SQLException {

        log__.debug("ひな形データ追加");

        int usrSid = __getSessionUserSid(reqMdl);
        UDate now = new UDate();
        //SID採番
        int hinaSid =
            (int) cntCon.getSaibanNumber(
                    GSConstSmail.SAIBAN_SML_SID,
                    GSConstSmail.SAIBAN_SUB_HINA_SID,
                    usrSid);

        //追加データ設定
        SmlHinaModel param = new SmlHinaModel();
        if (paramMdl.getSml050HinaKbn() == GSConstSmail.HINA_KBN_PRI) {
            param.setSacSid(paramMdl.getSmlAccountSid());
        } else {
            param.setSacSid(0);
        }

        param.setShnSid(hinaSid);
        param.setShnHname(NullDefault.getString(paramMdl.getSml060HinaName(), ""));
        param.setShnTitle(NullDefault.getString(paramMdl.getSml060HinaTitle(), ""));
        param.setShnMark(paramMdl.getSml060HinaMark());
        param.setShnBody(NullDefault.getString(paramMdl.getSml060HinaBody(), ""));
        param.setShnJkbn(GSConst.JTKBN_TOROKU);
        param.setShnCkbn(paramMdl.getSml050HinaKbn());
        param.setShnAuid(usrSid);
        param.setShnAdate(now);
        param.setShnEuid(usrSid);
        param.setShnEdate(now);

        SmlHinaDao hdao = new SmlHinaDao(con);

        hdao.insert(param);

    }

   /**
    * <br>[機  能] 指定されたひな形SIDのデータを物理削除する
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param hinaSid ひな形SID
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
    public void deleteHinaData(int hinaSid,
                                Connection con)
        throws SQLException {

        log__.debug("ひな形データ削除");

        //ひな形データ削除
        SmlHinaModel param = new SmlHinaModel();
        param.setShnSid(hinaSid);

        SmlHinaDao hinaDao = new SmlHinaDao(con);
        hinaDao.delete(param);
    }
}