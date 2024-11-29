package jp.groupsession.v2.usr.usr031kn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmHistoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnMblUidDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmHistoryModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnMblUidModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmLabelModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.usr031.Usr031Biz;
import jp.groupsession.v2.usr.usr031.Usr031ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー（追加）確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr031knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr031knBiz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Usr031knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usid ユーザSID
     * @param appRoot アプリケーションルート
     * @param paramMdl Usr031knParamModel
     * @param reqMdl リクエスト情報
     * @param domain ドメイン
     * @param usModel セッションユーザモデル
     * @throws SQLException SQL実行時例外
     * @throws IOException  ファイル操作時例外
     * @throws IOToolsException 写真ファイル操作時例外
     * @throws EncryptionException パスワード復号時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(int usid,
                             String appRoot,
                             Usr031knParamModel paramMdl,
                             RequestModel reqMdl,
                             String domain,
                             BaseUserModel usModel)
        throws SQLException, IOException, IOToolsException, EncryptionException, TempFileException {

        CmnUsrmDao udao = new CmnUsrmDao(con__);
        CmnUsrmModel sumodel = udao.select(usid);
        CmnUsrmInfDao ufdao = new CmnUsrmInfDao(con__);
        CmnUsrmInfModel ufmodel = new CmnUsrmInfModel();
        ufmodel.setUsrSid(usid);
        CmnUsrmInfModel infBean = ufdao.select(ufmodel);
        CommonBiz cmnBiz = new CommonBiz();

        //ログインLID
        paramMdl.setUsr031userid(sumodel.getUsrLgid());
        //パスワード
        paramMdl.setUsr031password(GSPassword.getDecryPassword(sumodel.getUsrPswd()));
        paramMdl.setUsr031passwordkn(paramMdl.getUsr031password());
        paramMdl.setUsr031UsrUkoFlg(sumodel.getUsrUkoFlg());
        //社員・職員番号
        paramMdl.setUsr031shainno(infBean.getUsiSyainNo());
        //バイナリSIDセット
        paramMdl.setUsr031BinSid(infBean.getBinSid());
        //写真公開フラグセット
        paramMdl.setUsr031UsiPicgKf(infBean.getUsiPictKf());
        //氏名(漢字)セット
        paramMdl.setUsr031sei(NullDefault.getString(infBean.getUsiSei(), ""));
        paramMdl.setUsr031mei(NullDefault.getString(infBean.getUsiMei(), ""));
        //氏名(カナ)セット
        paramMdl.setUsr031seikn(NullDefault.getString(infBean.getUsiSeiKn(), ""));
        paramMdl.setUsr031meikn(NullDefault.getString(infBean.getUsiMeiKn(), ""));
        //所属セット
        paramMdl.setUsr031syozoku(NullDefault.getString(infBean.getUsiSyozoku(), ""));
        //役職セット
        paramMdl.setUsr031yakushoku(String.valueOf(infBean.getPosSid()));
        //性別セット
        paramMdl.setUsr031seibetu(String.valueOf(infBean.getUsiSeibetu()));
        //入社年月日セット
        UDate entranceDate = infBean.getUsiEntranceDate();
        if (entranceDate != null) {
            paramMdl.setUsr031entranceYear(entranceDate.getStrYear());
            paramMdl.setUsr031entranceMonth(entranceDate.getStrMonth());
            paramMdl.setUsr031entranceDay(entranceDate.getStrDay());
        }
        //ソートキー1
        paramMdl.setUsr031sortkey1(NullDefault.getString(infBean.getUsiSortkey1(), ""));
        //ソートキー2
        paramMdl.setUsr031sortkey2(NullDefault.getString(infBean.getUsiSortkey2(), ""));
        //生年月日セット
        UDate bDate = infBean.getUsiBdate();
        if (bDate != null) {
            paramMdl.setUsr031birthYear(bDate.getStrYear());
            paramMdl.setUsr031birthMonth(bDate.getStrMonth());
            paramMdl.setUsr031birthDay(bDate.getStrDay());
        }
        //生年月日公開フラグセット
        paramMdl.setUsr031UsiBdateKf(infBean.getUsiBdateKf());
        //メールアドレスセット
        paramMdl.setUsr031mail1(NullDefault.getString(infBean.getUsiMail1(), ""));
        paramMdl.setUsr031mail2(NullDefault.getString(infBean.getUsiMail2(), ""));
        paramMdl.setUsr031mail3(NullDefault.getString(infBean.getUsiMail3(), ""));

        //HTML表示用
        String mailAddress1 = paramMdl.getUsr031mail1();
        mailAddress1 = StringUtilHtml.transToHTmlWithWbr(
                StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress1)), 30);
        paramMdl.setUsr031knMail1(mailAddress1);

        String mailAddress2 = paramMdl.getUsr031mail2();
        mailAddress2 = StringUtilHtml.transToHTmlWithWbr(
                StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress2)), 30);
        paramMdl.setUsr031knMail2(mailAddress2);

        String mailAddress3 = paramMdl.getUsr031mail3();
        mailAddress3 = StringUtilHtml.transToHTmlWithWbr(
                StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress3)), 30);
        paramMdl.setUsr031knMail3(mailAddress3);

        //メールアドレスコメントセット
        paramMdl.setUsr031mailCmt1(NullDefault.getString(infBean.getUsiMailCmt1(), ""));
        paramMdl.setUsr031mailCmt2(NullDefault.getString(infBean.getUsiMailCmt2(), ""));
        paramMdl.setUsr031mailCmt3(NullDefault.getString(infBean.getUsiMailCmt3(), ""));
        //メールアドレス公開フラグセット
        paramMdl.setUsr031UsiMail1Kf(infBean.getUsiMail1Kf());
        paramMdl.setUsr031UsiMail2Kf(infBean.getUsiMail2Kf());
        paramMdl.setUsr031UsiMail3Kf(infBean.getUsiMail3Kf());
        //郵便番号上3桁セット
        paramMdl.setUsr031post1(NullDefault.getString(infBean.getUsiZip1(), ""));
        //郵便番号下4桁セット
        paramMdl.setUsr031post2(NullDefault.getString(infBean.getUsiZip2(), ""));
        //郵便番号公開フラグセット
        paramMdl.setUsr031UsiZipKf(infBean.getUsiZipKf());
        //都道府県コードセット
        paramMdl.setUsr031tdfkCd(String.valueOf(infBean.getTdfSid()));
        //都道府県公開フラグセット
        paramMdl.setUsr031UsiTdfKf(infBean.getUsiTdfKf());
        //住所1セット
        paramMdl.setUsr031address1(NullDefault.getString(infBean.getUsiAddr1(), ""));
        //住所1公開フラグセット
        paramMdl.setUsr031UsiAddr1Kf(infBean.getUsiAddr1Kf());
        //住所2セット
        paramMdl.setUsr031address2(NullDefault.getString(infBean.getUsiAddr2(), ""));
        //住所2公開フラグセット
        paramMdl.setUsr031UsiAddr2Kf(infBean.getUsiAddr2Kf());
        //電話セット
        paramMdl.setUsr031tel1(NullDefault.getString(infBean.getUsiTel1(), ""));
        paramMdl.setUsr031tel2(NullDefault.getString(infBean.getUsiTel2(), ""));
        paramMdl.setUsr031tel3(NullDefault.getString(infBean.getUsiTel3(), ""));
        //電話内線セット
        paramMdl.setUsr031telNai1(NullDefault.getString(infBean.getUsiTelNai1(), ""));
        paramMdl.setUsr031telNai2(NullDefault.getString(infBean.getUsiTelNai2(), ""));
        paramMdl.setUsr031telNai3(NullDefault.getString(infBean.getUsiTelNai3(), ""));
        //電話コメントセット
        paramMdl.setUsr031telCmt1(NullDefault.getString(infBean.getUsiTelCmt1(), ""));
        paramMdl.setUsr031telCmt2(NullDefault.getString(infBean.getUsiTelCmt2(), ""));
        paramMdl.setUsr031telCmt3(NullDefault.getString(infBean.getUsiTelCmt3(), ""));
        //電話番号公開フラグセット
        paramMdl.setUsr031UsiTel1Kf(infBean.getUsiTel1Kf());
        paramMdl.setUsr031UsiTel2Kf(infBean.getUsiTel2Kf());
        paramMdl.setUsr031UsiTel3Kf(infBean.getUsiTel3Kf());
        //FAXセット
        paramMdl.setUsr031fax1(NullDefault.getString(infBean.getUsiFax1(), ""));
        paramMdl.setUsr031fax2(NullDefault.getString(infBean.getUsiFax2(), ""));
        paramMdl.setUsr031fax3(NullDefault.getString(infBean.getUsiFax3(), ""));
        //FAXコメントセット
        paramMdl.setUsr031faxCmt1(NullDefault.getString(infBean.getUsiFaxCmt1(), ""));
        paramMdl.setUsr031faxCmt2(NullDefault.getString(infBean.getUsiFaxCmt2(), ""));
        paramMdl.setUsr031faxCmt3(NullDefault.getString(infBean.getUsiFaxCmt3(), ""));
        //FAX公開フラグセット
        paramMdl.setUsr031UsiFax1Kf(infBean.getUsiFax1Kf());
        paramMdl.setUsr031UsiFax2Kf(infBean.getUsiFax2Kf());
        paramMdl.setUsr031UsiFax3Kf(infBean.getUsiFax3Kf());
        //モバイル使用
        paramMdl.setUsr031UsiMblUseKbn(infBean.getUsiMblUse());
        //固体識別番号制御
        paramMdl.setUsr031NumCont(String.valueOf(infBean.getUsiNumCont()));
        //固体識別番号自動登録
        paramMdl.setUsr031NumAutAdd(String.valueOf(infBean.getUsiNumAutadd()));

        //固体識別番号1～3
        CmnMblUidDao uidDao = new CmnMblUidDao(con__);
        CmnMblUidModel uidMdl = uidDao.select(usid);
        if (uidMdl != null) {
            paramMdl.setUsr031CmuUid1(NullDefault.getString(uidMdl.getCmuUid1(), ""));
            paramMdl.setUsr031CmuUid2(NullDefault.getString(uidMdl.getCmuUid2(), ""));
            paramMdl.setUsr031CmuUid3(NullDefault.getString(uidMdl.getCmuUid3(), ""));
        }

        //備考
        paramMdl.setUsr031bikouHtml(StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(infBean.getUsiBiko(), "")));
        //所属グループ
        CmnBelongmDao bdao = new CmnBelongmDao(con__);
        List<CmnBelongmModel> bmodelList = bdao.selectUserBelongGroup(usid);
        paramMdl.setSelectgroup(Usr031Biz.makeCSVStatement(bmodelList));

        //デフォルトグループ
        GroupBiz grpBz = new GroupBiz();
        int defGrp = grpBz.getDefaultGroupSid(usid, con__);
        paramMdl.setUsr031defgroup(defGrp);

        //バイナリSIDが取得できていたら画像を取得
        if (paramMdl.getUsr031BinSid() > 0) {
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con__, paramMdl.getUsr031BinSid(), domain);
            if (binMdl != null) {

                //テンポラリディレクトリパス
                Usr031Biz biz031 = new Usr031Biz(reqMdl__);
                String tempDir = biz031.getTempDir();

                //添付ファイルをテンポラリディレクトリにコピーする。
                String imageSaveName = cmnBiz.saveSingleTempFile(binMdl, appRoot, tempDir);
                //管理者か、編集者本人ならば画像を表示
                String[] selectUsers = paramMdl.getUsr030selectusers();
                int selectUserNum = NullDefault.getInt(selectUsers[0], -1);
                if (paramMdl.isAdminFlg() || usModel.getUsrsid() == selectUserNum) {
                    paramMdl.setUsr031ImageName(binMdl.getBinFileName());
                    paramMdl.setUsr031ImageSaveName(imageSaveName);
                }
            }
        }
    }

    /**
     * <br>[機  能] ユーザの登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl アクションフォーム
     * @param cntCon 採番コントローラ
     * @param lis ユーザリスナー
     * @param appRoot アプリケーションルート
     * @param loginBiz ログイン処理クラス
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void executeAdd(Usr031ParamModel paramMdl,
                             MlCountMtController cntCon,
                             IUserGroupListener[] lis,
                             String appRoot,
                             ILogin loginBiz,
                             RequestModel reqMdl)
        throws Exception {

        boolean commitFlg = false;

        try {
            //セッションユーザSID
            int sessionUser = reqMdl.getSmodel().getUsrsid();

            //SID採番
            int usid =
                (int) cntCon.getSaibanNumber(
                        SaibanModel.SBNSID_USER,
                        SaibanModel.SBNSID_SUB_USER,
                        sessionUser);

            //ユーザマスタ登録
            CmnUsrmModel umodel = new CmnUsrmModel();
            CmnUsrmDao udao = new CmnUsrmDao(con__);
            CommonBiz cmnBiz = new CommonBiz();
            CmnUsrmLabelDao culDao = new CmnUsrmLabelDao(con__);
            UDate now = new UDate();
            umodel.setUsrSid(usid);
            umodel.setUsrLgid(paramMdl.getUsr031userid());
            String password = loginBiz.formatPassword(paramMdl.getUsr031password());
            umodel.setUsrJkbn(GSConstUser.USER_JTKBN_ACTIVE);
            umodel.setUsrEuid(sessionUser);
            umodel.setUsrEdate(now);
            umodel.setUsrPswd(GSPassword.getEncryPassword(password));
            umodel.setUsrAuid(sessionUser);
            umodel.setUsrAdate(now);
            umodel.setUsrPswdKbn(Integer.parseInt(paramMdl.getUsr031PswdKbn()));
            umodel.setUsrPswdEdate(now);
            umodel.setUsrUkoFlg(paramMdl.getUsr031UsrUkoFlg());
            udao.insert(umodel);

            //ユーザ個人情報登録
            CmnUsrmInfModel uifmodel = new CmnUsrmInfModel();
            CmnUsrmInfDao uifdao = new CmnUsrmInfDao(con__);
            uifmodel.setUsrSid(usid);
            uifmodel.setUsiSei(NullDefault.getString(paramMdl.getUsr031sei(), ""));
            uifmodel.setUsiMei(NullDefault.getString(paramMdl.getUsr031mei(), ""));
            uifmodel.setUsiSeiKn(NullDefault.getString(paramMdl.getUsr031seikn(), ""));
            uifmodel.setUsiMeiKn(NullDefault.getString(paramMdl.getUsr031meikn(), ""));
            uifmodel.setUsiSini(StringUtilKana.getInitKanaChar(paramMdl.getUsr031seikn()));
            int byear = NullDefault.getInt(paramMdl.getUsr031birthYear(), -1);
            int bmonth = NullDefault.getInt(paramMdl.getUsr031birthMonth(), -1);
            int bday = NullDefault.getInt(paramMdl.getUsr031birthDay(), -1);
            if (byear != -1) {
                UDate bDate = new UDate();
                bDate.setDate(byear, bmonth, bday);
                bDate.setHour(0);
                bDate.setMinute(0);
                bDate.setSecond(0);
                bDate.setMilliSecond(0);
                uifmodel.setUsiBdate(bDate);
            }
            uifmodel.setUsiZip1(NullDefault.getStringZeroLength(paramMdl.getUsr031post1(), null));
            uifmodel.setUsiZip2(NullDefault.getStringZeroLength(paramMdl.getUsr031post2(), null));
            uifmodel.setTdfSid(NullDefault.getInt(paramMdl.getUsr031tdfkCd(), 0));
            uifmodel.setUsiAddr1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031address1(), null));
            uifmodel.setUsiAddr2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031address2(), null));
            uifmodel.setUsiTel1(NullDefault.getStringZeroLength(paramMdl.getUsr031tel1(), null));
            uifmodel.setUsiTelNai1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telNai1(), null));
            uifmodel.setUsiTelCmt1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telCmt1(), null));
            uifmodel.setUsiTel2(NullDefault.getStringZeroLength(paramMdl.getUsr031tel2(), null));
            uifmodel.setUsiTelNai2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telNai2(), null));
            uifmodel.setUsiTelCmt2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telCmt2(), null));
            uifmodel.setUsiTel3(NullDefault.getStringZeroLength(paramMdl.getUsr031tel3(), null));
            uifmodel.setUsiTelNai3(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telNai3(), null));
            uifmodel.setUsiTelCmt3(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telCmt3(), null));
            uifmodel.setUsiFax1(NullDefault.getStringZeroLength(paramMdl.getUsr031fax1(), null));
            uifmodel.setUsiFaxCmt1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031faxCmt1(), null));
            uifmodel.setUsiFax2(NullDefault.getStringZeroLength(paramMdl.getUsr031fax2(), null));
            uifmodel.setUsiFaxCmt2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031faxCmt2(), null));
            uifmodel.setUsiFax3(NullDefault.getStringZeroLength(paramMdl.getUsr031fax3(), null));
            uifmodel.setUsiFaxCmt3(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031faxCmt3(), null));
            uifmodel.setUsiMail1(NullDefault.getStringZeroLength(paramMdl.getUsr031mail1(), null));
            uifmodel.setUsiMailCmt1(
                    NullDefault.getStringZeroLength(paramMdl.getUsr031mailCmt1(), null));
            uifmodel.setUsiMail2(NullDefault.getStringZeroLength(paramMdl.getUsr031mail2(), null));
            uifmodel.setUsiMailCmt2(
                    NullDefault.getStringZeroLength(paramMdl.getUsr031mailCmt2(), null));
            uifmodel.setUsiMail3(NullDefault.getStringZeroLength(paramMdl.getUsr031mail3(), null));
            uifmodel.setUsiMailCmt3(
                    NullDefault.getStringZeroLength(paramMdl.getUsr031mailCmt3(), null));
            uifmodel.setUsiSyainNo(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031shainno(), null));
            uifmodel.setUsiSyozoku(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031syozoku(), null));
            uifmodel.setUsiSortkey1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031sortkey1(), null));
            uifmodel.setUsiSortkey2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031sortkey2(), null));
            uifmodel.setUsiSeibetu(NullDefault.getInt(paramMdl.getUsr031seibetu(), 0));
            int entranceYear = NullDefault.getInt(paramMdl.getUsr031entranceYear(), -1);
            int entranceMonth = NullDefault.getInt(paramMdl.getUsr031entranceMonth(), -1);
            int entranceDay = NullDefault.getInt(paramMdl.getUsr031entranceDay(), -1);
            if (entranceYear != -1) {
                UDate entranceDate = new UDate();
                entranceDate.setDate(entranceYear, entranceMonth, entranceDay);
                entranceDate.setHour(0);
                entranceDate.setMinute(0);
                entranceDate.setSecond(0);
                entranceDate.setMilliSecond(0);
                uifmodel.setUsiEntranceDate(entranceDate);
            }
//            uifmodel.setUsiYakusyoku(paramMdl.getUsr031yakushoku());
            uifmodel.setPosSid(NullDefault.getInt(paramMdl.getUsr031yakushoku(), 0));
            uifmodel.setUsiBiko(NullDefault.getStringZeroLength(paramMdl.getUsr031bikou(), null));

            //写真情報を登録
            Long binSid = new Long(0);
            if (!NullDefault.getStringZeroLength(
                    paramMdl.getUsr031ImageName(), "").equals("")
                    && !NullDefault.getStringZeroLength(
                            paramMdl.getUsr031ImageSaveName(), "").equals("")) {

                //テンポラリディレクトリパス
                Usr031Biz biz031 = new Usr031Biz(reqMdl__);
                String tempDir = biz031.getTempDir();

                String filePath =
                    IOTools.replaceFileSep(tempDir
                                        + paramMdl.getUsr031ImageSaveName()
                                        + GSConstCommon.ENDSTR_SAVEFILE);

                binSid = cmnBiz.insertBinInfo(
                        con__, appRoot, cntCon, usid, now, filePath, paramMdl.getUsr031ImageName());
            }
            uifmodel.setBinSid(binSid);
            uifmodel.setUsiPictKf(paramMdl.getUsr031UsiPicgKf());
            uifmodel.setUsiBdateKf(paramMdl.getUsr031UsiBdateKf());
            uifmodel.setUsiMail1Kf(paramMdl.getUsr031UsiMail1Kf());
            uifmodel.setUsiMail2Kf(paramMdl.getUsr031UsiMail2Kf());
            uifmodel.setUsiMail3Kf(paramMdl.getUsr031UsiMail3Kf());
            uifmodel.setUsiZipKf(paramMdl.getUsr031UsiZipKf());
            uifmodel.setUsiTdfKf(paramMdl.getUsr031UsiTdfKf());
            uifmodel.setUsiAddr1Kf(paramMdl.getUsr031UsiAddr1Kf());
            uifmodel.setUsiAddr2Kf(paramMdl.getUsr031UsiAddr2Kf());
            uifmodel.setUsiTel1Kf(paramMdl.getUsr031UsiTel1Kf());
            uifmodel.setUsiTel2Kf(paramMdl.getUsr031UsiTel2Kf());
            uifmodel.setUsiTel3Kf(paramMdl.getUsr031UsiTel3Kf());
            uifmodel.setUsiFax1Kf(paramMdl.getUsr031UsiFax1Kf());
            uifmodel.setUsiFax2Kf(paramMdl.getUsr031UsiFax2Kf());
            uifmodel.setUsiFax3Kf(paramMdl.getUsr031UsiFax3Kf());
            uifmodel.setUsiMblUse(paramMdl.getUsr031UsiMblUseKbn());
            uifmodel.setUsiAuid(sessionUser);
            uifmodel.setUsiAdate(now);
            uifmodel.setUsiEuid(sessionUser);
            uifmodel.setUsiEdate(now);

            if (paramMdl.isAdminFlg() && paramMdl.getChangeOtpToAddress() == GSConstMain.OTP_USE) {
                uifmodel.setUsiOtpsendAddress(paramMdl.getUsr031OtpToAddress());
            }

            //管理者 && モバイルプラグイン使用
            if (paramMdl.isAdminFlg() && paramMdl.getUsr031UsiMblUse() == GSConstUser.MBL_USE_OK) {
                //モバイル使用 = 可
                if (paramMdl.getUsr031UsiMblUseKbn() == GSConstUser.MBL_USE_OK) {

                    int numCont =
                        NullDefault.getInt(
                                paramMdl.getUsr031NumCont(),
                                GSConstUser.UID_DOESNT_CONTROL);

                    uifmodel.setUsiNumCont(numCont);

                    //ログイン制御 = する
                    if (numCont == GSConstUser.UID_CONTROL) {
                        uifmodel.setUsiNumAutadd(Integer.parseInt(paramMdl.getUsr031NumAutAdd()));
                    }
                }
            }

            uifdao.insert(uifmodel);

            //管理者 && モバイルプラグイン使用
            if (paramMdl.isAdminFlg() && paramMdl.getUsr031UsiMblUse() == GSConstUser.MBL_USE_OK) {
                //モバイル使用 = 可
                if (paramMdl.getUsr031UsiMblUseKbn() == GSConstUser.MBL_USE_OK) {

                    int numCont =
                        NullDefault.getInt(
                                paramMdl.getUsr031NumCont(),
                                GSConstUser.UID_DOESNT_CONTROL);

                    //ログイン制御 = する
                    if (numCont == GSConstUser.UID_CONTROL) {

                        //固体識別番号
                        CmnMblUidModel uidParam = new CmnMblUidModel();
                        uidParam.setUsrSid(usid);
                        uidParam.setCmuUid1(
                                NullDefault.getString(paramMdl.getUsr031CmuUid1(), ""));
                        uidParam.setCmuUid2(
                                NullDefault.getString(paramMdl.getUsr031CmuUid2(), ""));
                        uidParam.setCmuUid3(
                                NullDefault.getString(paramMdl.getUsr031CmuUid3(), ""));
                        uidParam.setCmuAuid(sessionUser);
                        uidParam.setCmuAdate(now);
                        uidParam.setCmuEuid(sessionUser);
                        uidParam.setCmuEdate(now);

                        CmnMblUidDao uidDao = new CmnMblUidDao(con__);
                        uidDao.insertUid(uidParam);
                    }
                }
            }

            //ラベル
            if (paramMdl.getUsrLabel() != null && paramMdl.getUsrLabel().length > 0) {
                CmnUsrmLabelModel culMdl = new CmnUsrmLabelModel();
                for (String sid : paramMdl.getUsrLabel()) {
                    culMdl.setLabSid(Integer.parseInt(sid));
                    culMdl.setUsrSid(usid);
                    culMdl.setUslAdate(now);
                    culMdl.setUslAuid(sessionUser);
                    culMdl.setUslEdate(now);
                    culMdl.setUslEuid(sessionUser);
                    culDao.insert(culMdl);
                }
            }

            //ユーザ所属
            CmnBelongmDao bdao = new CmnBelongmDao(con__);
            CmnBelongmHistoryDao bHisdao = new CmnBelongmHistoryDao(con__);
            //所属グループ
            int[] gsids = UserUtil.toGroupSidFromCsv(paramMdl.getSelectgroup());
            //デフォルトグループ
            int dgsid = paramMdl.getUsr031defgroup();


            for (int gsid : gsids) {
                CmnBelongmModel bmodel =
                    __createCmnBelongmModel(dgsid, gsid, usid, sessionUser, now, null);
                bdao.insert(bmodel);

                CmnBelongmHistoryModel bHismodel = new CmnBelongmHistoryModel();
                BeanUtils.copyProperties(bHismodel, bmodel);
                now.setZeroHhMmSs();
                bHismodel.setBegDate(now);
                bHisdao.insert(bHismodel);
            }

            //各プラグインリスナーを呼び出し
            for (int i = 0; i < lis.length; i++) {
                lis[i].addUser(cntCon, con__, usid, sessionUser, reqMdl);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ユーザ追加に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] ユーザの編集処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl アクションフォーム
     * @param cntCon 採番コントローラ
     * @param usModel 登録者のユーザモデル
     * @param lis ユーザリスナー
     * @param appRoot アプリケーションルート
     * @param canChangePassword パスワード変更の有効・無効
     * @throws Exception 実行例外
     */
    public void executeEdit(Usr031ParamModel paramMdl,
                             MlCountMtController cntCon,
                             BaseUserModel usModel,
                             IUserGroupListener[] lis,
                             String appRoot,
                             boolean canChangePassword)
        throws Exception {

        boolean commitFlg = false;

        try {
            //ユーザーSID取得
            String[] usrSids = paramMdl.getUsr030selectusers();
            int usid = NullDefault.getInt(usrSids[0], -1);
            //セッションユーザSID
            int euid = usModel.getUsrsid();
            //システム日付
            UDate now = new UDate();

            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfDao cbDao = new CmnBinfDao(con__);
            CmnUsrmModel umodel = new CmnUsrmModel();
            CmnUsrmDao udao = new CmnUsrmDao(con__);
            
            //ユーザマスタ更新
            umodel.setUsrSid(usid);
            umodel.setUsrEuid(euid);
            umodel.setUsrEdate(now);

            //管理者はID、パスも変更可
            if (paramMdl.isAdminFlg()) {
                umodel.setUsrLgid(paramMdl.getUsr031userid());
                String password = paramMdl.getUsr031password();
                umodel.setUsrJkbn(GSConstUser.USER_JTKBN_ACTIVE);
                String encPass = GSPassword.getEncryPassword(password);
                umodel.setUsrPswd(encPass);
                umodel.setUsrPswdKbn(Integer.parseInt(paramMdl.getUsr031PswdKbn()));
                umodel.setUsrPswdEdate(now);
                umodel.setUsrUkoFlg(paramMdl.getUsr031UsrUkoFlg());
                udao.updateCmnUser(umodel, canChangePassword);
            //一般ユーザはIDとパスを変更不可
            } else {
                udao.updateCmnUserKojn(umodel);
            }

            //ユーザ個人情報更新
            CmnUsrmInfModel uifmodel = new CmnUsrmInfModel();
            CmnUsrmInfDao uifdao = new CmnUsrmInfDao(con__);

            CmnUsrmInfModel ufmodel = new CmnUsrmInfModel();
            ufmodel.setUsrSid(usid);
            //表示外パラメータ引き継ぎのため 旧モデル取得
            CmnUsrmInfModel old = uifdao.select(usid);


            uifmodel.setUsrSid(usid);
            uifmodel.setUsiSei(NullDefault.getStringZeroLength(paramMdl.getUsr031sei(), ""));
            uifmodel.setUsiMei(NullDefault.getStringZeroLength(paramMdl.getUsr031mei(), ""));
            uifmodel.setUsiSeiKn(NullDefault.getStringZeroLength(paramMdl.getUsr031seikn(), ""));
            uifmodel.setUsiMeiKn(NullDefault.getStringZeroLength(paramMdl.getUsr031meikn(), ""));
            uifmodel.setUsiSini(StringUtilKana.getInitKanaChar(paramMdl.getUsr031seikn()));
            int byear = NullDefault.getInt(paramMdl.getUsr031birthYear(), -1);
            int bmonth = NullDefault.getInt(paramMdl.getUsr031birthMonth(), -1);
            int bday = NullDefault.getInt(paramMdl.getUsr031birthDay(), -1);
            if (byear != -1) {
                UDate bDate = new UDate();
                bDate.setDate(byear, bmonth, bday);
                bDate.setHour(0);
                bDate.setMinute(0);
                bDate.setSecond(0);
                bDate.setMilliSecond(0);
                uifmodel.setUsiBdate(bDate);
            }
            uifmodel.setUsiZip1(NullDefault.getStringZeroLength(paramMdl.getUsr031post1(), null));
            uifmodel.setUsiZip2(NullDefault.getStringZeroLength(paramMdl.getUsr031post2(), null));
            uifmodel.setTdfSid(NullDefault.getInt(paramMdl.getUsr031tdfkCd(), 0));
            uifmodel.setUsiAddr1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031address1(), null));
            uifmodel.setUsiAddr2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031address2(), null));
            uifmodel.setUsiTel1(NullDefault.getStringZeroLength(paramMdl.getUsr031tel1(), null));
            uifmodel.setUsiTelNai1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telNai1(), null));
            uifmodel.setUsiTelCmt1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telCmt1(), null));
            uifmodel.setUsiTel2(NullDefault.getStringZeroLength(paramMdl.getUsr031tel2(), null));
            uifmodel.setUsiTelNai2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telNai2(), null));
            uifmodel.setUsiTelCmt2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telCmt2(), null));
            uifmodel.setUsiTel3(NullDefault.getStringZeroLength(paramMdl.getUsr031tel3(), null));
            uifmodel.setUsiTelNai3(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telNai3(), null));
            uifmodel.setUsiTelCmt3(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031telCmt3(), null));
            uifmodel.setUsiFax1(NullDefault.getStringZeroLength(paramMdl.getUsr031fax1(), null));
            uifmodel.setUsiFaxCmt1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031faxCmt1(), null));
            uifmodel.setUsiFax2(NullDefault.getStringZeroLength(paramMdl.getUsr031fax2(), null));
            uifmodel.setUsiFaxCmt2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031faxCmt2(), null));
            uifmodel.setUsiFax3(NullDefault.getStringZeroLength(paramMdl.getUsr031fax3(), null));
            uifmodel.setUsiFaxCmt3(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031faxCmt3(), null));
            uifmodel.setUsiMail1(NullDefault.getStringZeroLength(paramMdl.getUsr031mail1(), null));
            uifmodel.setUsiMailCmt1(
                    NullDefault.getStringZeroLength(paramMdl.getUsr031mailCmt1(), null));
            uifmodel.setUsiMail2(NullDefault.getStringZeroLength(paramMdl.getUsr031mail2(), null));
            uifmodel.setUsiMailCmt2(
                    NullDefault.getStringZeroLength(paramMdl.getUsr031mailCmt2(), null));
            uifmodel.setUsiMail3(NullDefault.getStringZeroLength(paramMdl.getUsr031mail3(), null));
            uifmodel.setUsiMailCmt3(
                    NullDefault.getStringZeroLength(paramMdl.getUsr031mailCmt3(), null));
            uifmodel.setUsiSyainNo(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031shainno(), null));
            uifmodel.setUsiSyozoku(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031syozoku(), null));
            uifmodel.setUsiSortkey1(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031sortkey1(), null));
            uifmodel.setUsiSortkey2(NullDefault.getStringZeroLength(
                    paramMdl.getUsr031sortkey2(), null));
            uifmodel.setUsiSeibetu(NullDefault.getInt(paramMdl.getUsr031seibetu(), 0));
            int entranceYear = NullDefault.getInt(paramMdl.getUsr031entranceYear(), -1);
            int entranceMonth = NullDefault.getInt(paramMdl.getUsr031entranceMonth(), -1);
            int entranceDay = NullDefault.getInt(paramMdl.getUsr031entranceDay(), -1);
            if (entranceYear != -1) {
                UDate entranceDate = new UDate();
                entranceDate.setDate(entranceYear, entranceMonth, entranceDay);
                entranceDate.setHour(0);
                entranceDate.setMinute(0);
                entranceDate.setSecond(0);
                entranceDate.setMilliSecond(0);
                uifmodel.setUsiEntranceDate(entranceDate);
            }
//            uifmodel.setUsiYakusyoku(paramMdl.getUsr031yakushoku());
            uifmodel.setPosSid(NullDefault.getInt(paramMdl.getUsr031yakushoku(), 0));
            uifmodel.setUsiBiko(NullDefault.getStringZeroLength(paramMdl.getUsr031bikou(), null));

            if (paramMdl.isAdminFlg() && paramMdl.getChangeOtpToAddress() == GSConstMain.OTP_USE) {
                uifmodel.setUsiOtpsendAddress(paramMdl.getUsr031OtpToAddress());
            } else {
                uifmodel.setUsiOtpsendAddress(old.getUsiOtpsendAddress());
            }

            Long binSid = new Long(0);


            //以前のデータ取得
            CmnBinfModel binMdl = cbDao.getBinInfo(paramMdl.getUsr031BinSid());

            //該当データがあれば削除
            if (binMdl != null) {
                //バイナリー情報を更新する
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(paramMdl.getUsr031BinSid());
                cbMdl.setBinUpuser(usid);
                cbMdl.setBinUpdate(now);
                cbDao.removeBinData(cbMdl);
            }

            //写真情報を登録
            if (!NullDefault.getStringZeroLength(
                    paramMdl.getUsr031ImageName(), "").equals("")
                    && !NullDefault.getStringZeroLength(
                            paramMdl.getUsr031ImageSaveName(), "").equals("")) {

                //テンポラリディレクトリパス
                Usr031Biz biz031 = new Usr031Biz(reqMdl__);
                String tempDir = biz031.getTempDir();
                String filePath =
                    IOTools.replaceFileSep(
                            tempDir
                            + paramMdl.getUsr031ImageSaveName()
                            + GSConstCommon.ENDSTR_SAVEFILE);

                binSid = cmnBiz.insertBinInfo(
                        con__, appRoot, cntCon, usid, now, filePath, paramMdl.getUsr031ImageName());

            }
            uifmodel.setBinSid(binSid);
            uifmodel.setUsiPictKf(paramMdl.getUsr031UsiPicgKf());
            uifmodel.setUsiBdateKf(paramMdl.getUsr031UsiBdateKf());
            uifmodel.setUsiMail1Kf(paramMdl.getUsr031UsiMail1Kf());
            uifmodel.setUsiMail2Kf(paramMdl.getUsr031UsiMail2Kf());
            uifmodel.setUsiMail3Kf(paramMdl.getUsr031UsiMail3Kf());
            uifmodel.setUsiZipKf(paramMdl.getUsr031UsiZipKf());
            uifmodel.setUsiTdfKf(paramMdl.getUsr031UsiTdfKf());
            uifmodel.setUsiAddr1Kf(paramMdl.getUsr031UsiAddr1Kf());
            uifmodel.setUsiAddr2Kf(paramMdl.getUsr031UsiAddr2Kf());
            uifmodel.setUsiTel1Kf(paramMdl.getUsr031UsiTel1Kf());
            uifmodel.setUsiTel2Kf(paramMdl.getUsr031UsiTel2Kf());
            uifmodel.setUsiTel3Kf(paramMdl.getUsr031UsiTel3Kf());
            uifmodel.setUsiFax1Kf(paramMdl.getUsr031UsiFax1Kf());
            uifmodel.setUsiFax2Kf(paramMdl.getUsr031UsiFax2Kf());
            uifmodel.setUsiFax3Kf(paramMdl.getUsr031UsiFax3Kf());
            uifmodel.setUsiEuid(euid);
            uifmodel.setUsiEdate(now);

            //管理者 かつ モバイルプラグイン使用可の場合
            if (paramMdl.isAdminFlg() && paramMdl.getUsr031UsiMblUse() == GSConstUser.MBL_USE_OK) {

                uifmodel.setUsiMblUse(paramMdl.getUsr031UsiMblUseKbn());

                //モバイル使用 = 可
                if (paramMdl.getUsr031UsiMblUseKbn() == GSConstUser.MBL_USE_OK) {

                    int numCont =
                        NullDefault.getInt(
                                paramMdl.getUsr031NumCont(),
                                GSConstUser.UID_DOESNT_CONTROL);

                    uifmodel.setUsiNumCont(numCont);

                    //ログイン制御 = する
                    if (numCont == GSConstUser.UID_CONTROL) {
                        uifmodel.setUsiNumAutadd(Integer.parseInt(paramMdl.getUsr031NumAutAdd()));
                    //ログイン制御 = しない
                    } else {
                        uifmodel.setUsiNumAutadd(GSConstUser.UID_AUTO_REG_NO);
                    }

                //モバイル使用 = 不可
                } else {
                    uifmodel.setUsiNumCont(GSConstUser.UID_DOESNT_CONTROL);
                    uifmodel.setUsiNumAutadd(GSConstUser.UID_AUTO_REG_NO);
                }

                uifdao.updateCmnUserInfWithMblKbn(uifmodel);

            } else {
                uifdao.updateCmnUserInf(uifmodel);
            }

            //管理者 && モバイルプラグイン使用
            if (paramMdl.isAdminFlg() && paramMdl.getUsr031UsiMblUse() == GSConstUser.MBL_USE_OK) {

                //モバイル使用 = 可
                if (paramMdl.getUsr031UsiMblUseKbn() == GSConstUser.MBL_USE_OK) {

                    int numCont =
                        NullDefault.getInt(
                                paramMdl.getUsr031NumCont(),
                                GSConstUser.UID_DOESNT_CONTROL);

                    //ログイン制御 = する
                    if (numCont == GSConstUser.UID_CONTROL) {

                        //固体識別番号
                        CmnMblUidModel uidParam = new CmnMblUidModel();
                        uidParam.setUsrSid(usid);
                        uidParam.setCmuUid1(
                                NullDefault.getString(paramMdl.getUsr031CmuUid1(), ""));
                        uidParam.setCmuUid2(
                                NullDefault.getString(paramMdl.getUsr031CmuUid2(), ""));
                        uidParam.setCmuUid3(
                                NullDefault.getString(paramMdl.getUsr031CmuUid3(), ""));
                        uidParam.setCmuAuid(euid);
                        uidParam.setCmuAdate(now);
                        uidParam.setCmuEuid(euid);
                        uidParam.setCmuEdate(now);

                        CmnMblUidDao uidDao = new CmnMblUidDao(con__);
                        if (uidDao.updateUid(uidParam) < 1) {
                            uidDao.insertUid(uidParam);
                        }
                    }
                }
            }

            //グループ情報は一般利用者には更新不可能
            //予約済みユーザも更新不可能
            if (paramMdl.isAdminFlg() && usid > GSConstUser.USER_RESERV_SID) {
                //ユーザ所属
                CmnBelongmDao bdao = new CmnBelongmDao(con__);
                //所属グループ
                int[] gsids = UserUtil.toGroupSidFromCsv(paramMdl.getSelectgroup());
                //デフォルトグループ
                int dgsid = paramMdl.getUsr031defgroup();
                log__.debug(">>>デフォルトグループSID: " + dgsid);

                //ユーザが現在所属しているグループ情報を取得
                List<CmnBelongmModel> belongList = bdao.selectUserBelongGroup(usid);
                int[] pastGsids = new int[belongList.size()];
                for (int gidIndex = 0; gidIndex < belongList.size(); gidIndex++) {
                    pastGsids[gidIndex] = belongList.get(gidIndex).getGrpSid();
                }

                //レコード追加／削除
                bdao.deleteUserBelongGroup(usid);
                ArrayList<CmnBelongmModel> addLists = new ArrayList<CmnBelongmModel>();

                for (int gsid : gsids) {
                    CmnBelongmModel bmodel =
                        __createCmnBelongmModel(dgsid, gsid, usid, euid, now, belongList);
                    addLists.add(bmodel);
                }
                //追加
                bdao.insert(addLists);

                //履歴 レコード追加／削除
                CmnBelongmHistoryDao bhisdao = new CmnBelongmHistoryDao(con__);
                UDate date = new UDate();
                date.setZeroHhMmSs();
                bhisdao.deleteUserBelongGroup(usid, date);

                //履歴 追加
                ArrayList<CmnBelongmHistoryModel> addHistoryLists
                                            = new ArrayList<CmnBelongmHistoryModel>();
                CmnBelongmHistoryModel hisMdl = null;
                if (!addLists.isEmpty()) {
                    for (CmnBelongmModel mdl : addLists) {
                        hisMdl = new CmnBelongmHistoryModel();
                        BeanUtils.copyProperties(hisMdl, mdl);
                        hisMdl.setBegDate(date);
                        addHistoryLists.add(hisMdl);
                    }
                }
                bhisdao.insert(addHistoryLists);

                //各プラグインリスナーを呼び出し
                for (int i = 0; i < lis.length; i++) {
                    //ユーザ所属グループ変更
                    lis[i].changeBelong(con__, usid, pastGsids, gsids, euid);
                    //デフォルトグループ変更
                    lis[i].changeDefaultBelong(con__, usid, dgsid, euid);
                }
            }

            //ラベル情報設定
            CmnUsrmLabelDao dao = new CmnUsrmLabelDao(con__);
            dao.delete(usid);
            if (paramMdl.getUsrLabel() != null && paramMdl.getUsrLabel().length > 0) {
                for (String sid : paramMdl.getUsrLabel()) {
                    CmnUsrmLabelModel model = new CmnUsrmLabelModel();
                    model.setUsrSid(usid);
                    model.setLabSid(Integer.parseInt(sid));
                    model.setUslAdate(now);
                    model.setUslAuid(euid);
                    model.setUslEdate(now);
                    model.setUslEuid(euid);
                    dao.insert(model);
                }
            }
            
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ユーザ更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <p>ユーザの削除処理を行います
     * @param paramMdl アクションフォーム
     * @param cntCon 採番コントローラ
     * @param lis ユーザリスナー
     * @param appRoot アプリケーションルート
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     * @return boolean
     */
    public boolean executeDel(Usr031ParamModel paramMdl,
                            MlCountMtController cntCon,
                            IUserGroupListener[] lis,
                            String appRoot,
                            RequestModel reqMdl)
        throws Exception {

        boolean commitFlg = false;
        try {

            //ユーザーSID取得
            String[] usrSids = paramMdl.getUsr030selectusers();
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> uinfList = userBiz.getUserList(con__, usrSids);

            //削除処理
            delUser(uinfList, lis, reqMdl);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ユーザ削除に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
        log__.debug("END");
        return commitFlg;
    }

    /**
     * <p>ユーザの削除処理を行います
     * @param uinfList ユーザ情報一覧
     * @param lis ユーザリスナー
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void delUser(List<CmnUsrmInfModel> uinfList,
                            IUserGroupListener[] lis,
                            RequestModel reqMdl)
                                    throws SQLException {

        if (uinfList == null || uinfList.size() < 1) {
            return;
        }
        for (CmnUsrmInfModel uinfMdl : uinfList) {
            int usid = uinfMdl.getUsrSid();
            log__.debug("usid = " + usid);

            //セッションユーザSID
            int euid = reqMdl__.getSmodel().getUsrsid();

            CmnUsrmModel umodel = new CmnUsrmModel();
            CmnUsrmDao udao = new CmnUsrmDao(con__);

            UDate now = new UDate();

            //共通セット項目
            umodel.setUsrSid(usid);
            umodel.setUsrEuid(euid);
            umodel.setUsrEdate(now);
            umodel.setUsrJkbn(CmnGroupmDao.GRP_JKBN_DELETED);
            udao.updateCmnUserDel(umodel);

            //ユーザ所属
            CmnBelongmDao bdao = new CmnBelongmDao(con__);
            bdao.deleteUserBelongGroup(usid);

            //バイナリファイルが存在する場合
            if (uinfMdl.getBinSid() > 0) {

                //バイナリー情報を削除する
                CmnBinfDao cbDao = new CmnBinfDao(con__);
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(uinfMdl.getBinSid());
                cbMdl.setBinUpuser(usid);
                cbMdl.setBinUpdate(now);
                cbDao.removeBinData(cbMdl);
            }

            //固体識別番号削除
            CmnMblUidDao uidDao = new CmnMblUidDao(con__);
            uidDao.deleteUid(usid);


            //各プラグインリスナーを呼び出し
            for (int i = 0; i < lis.length; i++) {
                lis[i].deleteUser(con__, usid, euid, reqMdl__);
            }
        }
    }

    /**
     * ユーザ所属マスタの項目の共通セットを行います
     * @param dgSid デフォルトグループSID
     * @param gSid グループSID
     * @param uSid ユーザSID
     * @param sessionUser セッションユーザID
     * @param now タイムスタンプ
     * @param belongList ユーザの現在の所属グループリスト
     * @return CmnBelongmModel
     */
    private CmnBelongmModel __createCmnBelongmModel(
        int dgSid,
        int gSid,
        int uSid,
        int sessionUser,
        UDate now,
        List<CmnBelongmModel> belongList) {

        CmnBelongmModel bmodel = new CmnBelongmModel();

        bmodel.setGrpSid(gSid);
        bmodel.setUsrSid(uSid);
        //システム項目
        bmodel.setBegAuid(sessionUser);
        bmodel.setBegAdate(now);
        bmodel.setBegEuid(sessionUser);
        bmodel.setBegEdate(now);
        if (gSid == dgSid) {
            log__.debug(">>>デフォルトグループ");
            bmodel.setBegDefgrp(CmnBelongmModel.DEFGRP_FLG_DEFAULT);
        } else {
            log__.debug(">>>所属グループ");
            bmodel.setBegDefgrp(CmnBelongmModel.DEFGRP_FLG_NORMAL);
        }

        if (belongList == null || belongList.isEmpty()) {
            return bmodel;
        }

        //現在の所属グループのグループ区分を取得、
        //管理者権限有りの場合は、登録する所属するグループ情報も管理者権限有りにする
        for (CmnBelongmModel bolongMdl : belongList) {
            if (gSid == bolongMdl.getGrpSid()) {
                if (bolongMdl.getBegGrpkbn() == GSConst.USER_ADMIN) {
                    bmodel.setBegGrpkbn(GSConst.USER_ADMIN);
                }
                break;
            }
        }

        return bmodel;
    }

    /**
     * 都道府県情報を取得します
     * @param tdfkSid 都道府県SID
     * @return CmnTdfkModel
     * @throws SQLException SQL実行時例外
     */
    private CmnTdfkModel __getTdfkMdl(int tdfkSid) throws SQLException {
        CmnTdfkModel tdMdl = null;
        CmnTdfkDao tdDao = new CmnTdfkDao(con__);
        tdMdl = tdDao.select(tdfkSid);
        return tdMdl;
    }

    /**
     * <br>[機  能] 各SIDから名称を取得し、画面へセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setSidName(Usr031knParamModel paramMdl, Connection con) throws SQLException {

        //都道府県名取得
        int tdfkSid = NullDefault.getInt(paramMdl.getUsr031tdfkCd(), -1);
        CmnTdfkModel tdMdl = __getTdfkMdl(tdfkSid);
        if (tdMdl != null) {
            paramMdl.setUsr031kntdfkName(tdMdl.getTdfName());
        }

        //役職名取得
        PosBiz pBiz = new PosBiz();
        String pos = pBiz.getPosName(con__, NullDefault.getInt(paramMdl.getUsr031yakushoku(), 0));
        paramMdl.setUsr031knposName(NullDefault.getString(pos, ""));

        //ラベル名取得
        if (paramMdl.getUsrLabel() != null && paramMdl.getUsrLabel().length > 0) {
            CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
            ArrayList<CmnLabelUsrModel> model = new ArrayList<CmnLabelUsrModel>();
            for (String strSid : paramMdl.getUsrLabel()) {
                int labSid = Integer.parseInt(strSid);
                model.add(dao.selectOneLabel(labSid));
            }
            paramMdl.setSelectLabelList(model);
        }

        //性別取得
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String seibetu = gsMsg.getMessage("cmn.notset");
        if (paramMdl.getUsr031seibetu().equals(String.valueOf(GSConstUser.SEIBETU_MAN))) {
            seibetu = gsMsg.getMessage("user.124");
        } else if (paramMdl.getUsr031seibetu().equals(String.valueOf(GSConstUser.SEIBETU_WOMAN))) {
            seibetu = gsMsg.getMessage("user.125");
        }
        paramMdl.setUsr031seibetuName(seibetu);
    }

    /**
     * <br>[機  能] パスワードを取得する
     * <br>[解  説] パスワード変更 = 無効 の場合、規定の文字列をパスワードとして返す
     * <br>[備  考]
     * @param paramMdl Usr031knParamModel
     * @param canChangePassword パスワード変更の有効・無効
     * @return パスワード
     */
    public String getPassword(Usr031ParamModel paramMdl, boolean canChangePassword) {
        String password = "password";

        if (canChangePassword) {
            password = paramMdl.getUsr031password();
        }

        return password;
    }

    /**
     * <br>[機  能] 初期表示処理(複数削除モード)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Usr031knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataDelPlural(Usr031knParamModel paramMdl)
        throws SQLException {

        //削除ユーザ
        String[] usids = paramMdl.getUsr030selectusers();

        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> uinfList = userBiz.getUserList(con__, usids);

        paramMdl.setUsr031delUsrList(uinfList);

    }

    /**
     * <br>[機  能] ラベルの重複チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void checkLabel(Usr031knParamModel paramMdl) throws SQLException {
        //重複ラベル削除
        String[] labelList = paramMdl.getUsrLabel();
        List<String> labelIdList = new ArrayList<String>();
        if (labelList != null) {
            for (String labelId : labelList) {
                if (!labelIdList.contains(labelId)) {
                    labelIdList.add(labelId);
                }
            }

            labelList = (String[]) labelIdList.toArray(new String[labelIdList.size()]);
            paramMdl.setUsrLabel(labelList);
        }
    }

    /**
     * <br>[機  能] メールアドレスの成型
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void createAddrForHTML(Usr031knParamModel paramMdl) throws SQLException {
        //HTML表示用
        //メールアドレス1
        String mailAddress1 = paramMdl.getUsr031mail1();
        mailAddress1 = StringUtilHtml.transToHTmlWithWbr(
                StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress1)), 30);
        paramMdl.setUsr031knMail1(mailAddress1);

        //メールアドレス2
        String mailAddress2 = paramMdl.getUsr031mail2();
        mailAddress2 = StringUtilHtml.transToHTmlWithWbr(
                StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress2)), 30);
        paramMdl.setUsr031knMail2(mailAddress2);

        //メールアドレス3
        String mailAddress3 = paramMdl.getUsr031mail3();
        mailAddress3 = StringUtilHtml.transToHTmlWithWbr(
                StringUtilHtml.deleteHtmlTag(StringUtilHtml.transToText(mailAddress3)), 30);
        paramMdl.setUsr031knMail3(mailAddress3);
    }
    /**
     *
     * <br>[機  能] ユーザの有効無効設定変更
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param flg 有効無効フラグ 0：有効 1:無効
     * @param sids 対象SIDリスト
     * @throws SQLException SQL実行時例外
     *
     */
    public void changeYukoMuko(Connection con,
            RequestModel reqMdl, int flg, String[] sids) throws SQLException {
        if (sids == null || sids.length == 0) {
            return;
        }
        List<Integer> sidList = new ArrayList<Integer>();
        for (String sid : sids) {
            int sidInt = NullDefault.getInt(sid, -1);
            sidList.add(sidInt);
        }

        boolean commitFlg = false;
        try {
            CmnUsrmDao dao = new CmnUsrmDao(con);
            dao.updateYukoMuko(flg, sidList, reqMdl.getSmodel().getUsrsid());
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ユーザの有効化，無効化に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

  /**
    *
    * <br>[機  能] ユーザの有効無効設定変更
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param sids 対象SIDリスト
    * @return ユーザリスト
    * @throws SQLException SQL実行時例外
    *
    */
   public ArrayList<BaseUserModel> getTargetName(Connection con, String[] sids)
           throws SQLException {
       if (sids == null || sids.length == 0) {
           return null;
       }
       CmnUsrmDao dao = new CmnUsrmDao(con);
       ArrayList<BaseUserModel> userList = dao.getSelectedUserLogList(sids);
       return userList;
   }
}