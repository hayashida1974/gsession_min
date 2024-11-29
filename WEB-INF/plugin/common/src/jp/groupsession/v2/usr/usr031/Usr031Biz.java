package jp.groupsession.v2.usr.usr031;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnMblUidDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnMblUidModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSPassword;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー（追加）画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr031Biz {
    /** 画面ID */
    public static final String SCR_ID = "usr031";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr031Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr031Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 編集処理時初期設定
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションルート
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @param domain ドメイン
     * @param usModel セッションユーザモデル
     * @throws SQLException SQL実行時例外
     * @throws IOException  ファイル操作時例外
     * @throws IOToolsException 写真データ操作時例外
     * @throws EncryptionException パスワード復号時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(String appRoot,
                             Usr031ParamModel paramMdl,
                             Connection con,
                             String domain,
                             BaseUserModel usModel)
        throws SQLException, IOException, IOToolsException, EncryptionException, TempFileException {

        CmnUsrmDao udao = new CmnUsrmDao(con);
        CmnUsrmModel umodel = new CmnUsrmModel();
        CommonBiz cmnBiz = new CommonBiz();

        //選択されたユーザーSID取得
        String[] usids = paramMdl.getUsr030selectusers();
        int usid = NullDefault.getInt(usids[0], -1);
        paramMdl.setUsr030selectuser(usid);
        umodel.setUsrSid(usid);

        //ユーザーLIDセット
        CmnUsrmModel sumodel = udao.select(usid);
        paramMdl.setUsr031userid(sumodel.getUsrLgid());
        //パスワード設定
        paramMdl.setUsr031password(GSPassword.getDecryPassword(sumodel.getUsrPswd()));
        paramMdl.setUsr031passwordkn(paramMdl.getUsr031password());
        paramMdl.setUsr031PswdKbn(String.valueOf(sumodel.getUsrPswdKbn()));

        //有効無効フラグ
        paramMdl.setUsr031UsrUkoFlg(sumodel.getUsrUkoFlg());

        //ユーザ情報詳細取得
        CmnUsrmInfDao ufdao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel ufmodel = new CmnUsrmInfModel();
        ufmodel.setUsrSid(usid);
        CmnUsrmInfModel infBean = ufdao.select(ufmodel);
        CmnUsrmLabelDao culDao = new CmnUsrmLabelDao(con);

        //ワンタイムパスワード通知先アドレス
        paramMdl.setUsr031OtpToAddress(infBean.getUsiOtpsendAddress());

        //社員・職員番号セット
        paramMdl.setUsr031shainno(NullDefault.getString(infBean.getUsiSyainNo(), ""));
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
//        //役職セット
//        paramMdl.setUsr031yakushoku(NullDefault.getString(infBean.getUsiYakusyoku(), ""));
        //役職セット
        paramMdl.setUsr031yakushoku(String.valueOf(infBean.getPosSid()));
        //性別セット
        paramMdl.setUsr031seibetu(NullDefault.getString(
                                                    String.valueOf(infBean.getUsiSeibetu()),
                                                    String.valueOf(GSConstUser.SEIBETU_UNSET)));
        //ソートキー1セット
        paramMdl.setUsr031sortkey1(NullDefault.getString(infBean.getUsiSortkey1(), ""));
        //ソートキー2セット
        paramMdl.setUsr031sortkey2(NullDefault.getString(infBean.getUsiSortkey2(), ""));

        //入社年月日セット
        UDate entranceDate = infBean.getUsiEntranceDate();
        if (entranceDate != null) {
            paramMdl.setUsr031entranceYear(entranceDate.getStrYear());
            paramMdl.setUsr031entranceMonth(entranceDate.getStrMonth());
            paramMdl.setUsr031entranceDay(entranceDate.getStrDay());
        }

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
        CmnMblUidDao uidDao = new CmnMblUidDao(con);
        CmnMblUidModel uidMdl = uidDao.select(usid);
        if (uidMdl != null) {
            paramMdl.setUsr031CmuUid1(NullDefault.getString(uidMdl.getCmuUid1(), ""));
            paramMdl.setUsr031CmuUid2(NullDefault.getString(uidMdl.getCmuUid2(), ""));
            paramMdl.setUsr031CmuUid3(NullDefault.getString(uidMdl.getCmuUid3(), ""));
        }

        //ラベル
        paramMdl.setUsrLabel(culDao.getLabListBelongUsr(usid));

        //備考
        paramMdl.setUsr031bikou(infBean.getUsiBiko());

        //所属部署
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        List<CmnBelongmModel> bmodelList = bdao.selectUserBelongGroup(usid);
        paramMdl.setSelectgroup(Usr031Biz.makeCSVStatement(bmodelList));

        //デフォルトグループ
        GroupBiz grpBz = new GroupBiz();
        int defGrp = grpBz.getDefaultGroupSid(usid, con);
        paramMdl.setUsr031defgroup(defGrp);

        //バイナリSIDが取得できていたら画像を取得
        if (paramMdl.getUsr031BinSid() > 0) {
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, paramMdl.getUsr031BinSid(), domain);
            if (binMdl != null) {

                //テンポラリディレクトリパス
                String tempDir = getTempDir();

                //テンポラリディレクトリにバイナリデータから作成したファイルを保存する
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
     * <br>[機  能] 画面に常に表示する値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(Usr031ParamModel paramMdl, Connection con)
    throws SQLException {

        //グループリスト
        setGroupList(paramMdl, con);
        //都道府県ラベル
        setTdfkList(paramMdl, con);
        //役職ラベル
        setPosList(paramMdl, con);
        //ユーザラベル
        setLabelList(paramMdl, con);
        //性別ラベル
        setSeibetuList(paramMdl, con);
    }

    /**
     * <br>[機  能] グループリストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setGroupList(Usr031ParamModel paramMdl,
                              Connection con)
        throws SQLException {

        //グループリスト
//        GroupDao dao = new GroupDao(con);
//        ArrayList<GroupModel> tree = dao.getGroupTree(null);
        GroupBiz grpBiz = new GroupBiz();
        ArrayList<GroupModel> tree = grpBiz.getGroupTree(con);

        //管理者グループを除去する
        ArrayList<GroupModel> groupList = new ArrayList<GroupModel>();
        for (GroupModel grpMdl : tree) {
            if (grpMdl.getGroupSid() != 0) {
                groupList.add(grpMdl);
            }
        }

        paramMdl.setGroupList(groupList);
    }

    /**
     * <br>[機  能] 都道府県リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setTdfkList(Usr031ParamModel paramMdl,
                             Connection con)
        throws SQLException {

        //都道府県ラベル
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setTdfkLabelList(cmnBiz.getTdfkLabelList(con, gsMsg));
    }

    /**
     * <br>[機  能] 役職リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setPosList(Usr031ParamModel paramMdl, Connection con)
        throws SQLException {

        //役職ラベル
        PosBiz pBiz = new PosBiz();
        paramMdl.setPosLabelList(pBiz.getPosLabelList(con));
    }

    /**
     * <br>[機  能] 性別リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setSeibetuList(Usr031ParamModel paramMdl, Connection con)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.notset"),
                        String.valueOf(GSConstUser.SEIBETU_UNSET)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("user.124"),
                        String.valueOf(GSConstUser.SEIBETU_MAN)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("user.125"),
                        String.valueOf(GSConstUser.SEIBETU_WOMAN)));

        //性別ラベル
        paramMdl.setSeibetuLabelList(labelList);
    }

    /**
     * <br>[機  能] ユーザラベルを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setLabelList(Usr031ParamModel paramMdl, Connection con)
        throws SQLException {

        if (paramMdl.getUsrLabel() != null) {
            //重複ラベルチェック
            checkLabel(paramMdl);

            //ユーザラベル
            CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
            String[] labelSids = paramMdl.getUsrLabel();
            ArrayList<CmnLabelUsrModel> list = new ArrayList<CmnLabelUsrModel>();
            for (String str : labelSids) {
                CmnLabelUsrModel model = dao.selectOneLabel(Integer.parseInt(str));
                list.add(model);
            }
            paramMdl.setSelectLabelList(list);
        }
    }

    /**
     * <br>[機  能] List / model を解析して、
     * <br>         CSV形式（カンマ）のステートメントを返却します
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param modelsList モデルリスト
     * @return state カンマ区切り
     */
    public static String makeCSVStatement(List<CmnBelongmModel> modelsList) {

        log__.debug("START");

        String state = "";
        if (!modelsList.isEmpty()) {
            for (int i = 0; i < modelsList.size(); i++) {
                CmnBelongmModel bmodel = (CmnBelongmModel) modelsList.get(i);
                log__.debug("■[" + i + "] bmodel.getGrpSid() :" + bmodel.getGrpSid());
                if (i > 0) {
                    state += ",";
                }
                state += Integer.toString(bmodel.getGrpSid());
            }
            log__.debug("■生成state :" + state);
        }

        log__.debug("END");
        return state;
    }

    /**
     * <br>[機  能] 追加した役職をコンボで選択済みにする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setNewPosition(Usr031ParamModel paramMdl, Connection con) throws SQLException {

        CmnPositionDao cpDao = new CmnPositionDao(con);
        CmnPositionModel cpMdl = cpDao.getLastPos();

        if (cpMdl == null) {
            return;
        }

        paramMdl.setUsr031yakushoku(String.valueOf(cpMdl.getPosSid()));
    }

    /**
     * <br>[機  能] ラベルの重複チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void checkLabel(Usr031ParamModel paramMdl) throws SQLException {
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
     * <br>[機  能] 編集対象ユーザのユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @return ユーザSID
     */
    public static int getSelectUserSid(Usr031Form form) {
        String[] usrSids = form.getUsr030selectusers();
        if (usrSids == null || usrSids.length == 0) {
            return -1;
        }
        return NullDefault.getInt(usrSids[0], -1);
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンポラリディレクトリパス
     */
    public String getTempDir() {
        UsrCommonBiz usrCmnBiz = new UsrCommonBiz(reqMdl__);
        return usrCmnBiz.getTempDir(SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void deleteTempDir() {
        UsrCommonBiz usrCmnBiz = new UsrCommonBiz(reqMdl__);
        usrCmnBiz.deleteTempDir(SCR_ID);
    }

    /**
     * <br>[機  能] 編集対象ユーザが「ログイン無効ユーザ」かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param form フォーム
     * @return true:有効、false:無効
     * @throws SQLException SQL実行時例外
     */
    public boolean isMukoUser(Connection con, Usr031Form form) throws SQLException {
        String[] usids = form.getUsr030selectusers();
        int usid = NullDefault.getInt(usids[0], -1);
        CmnUsrmDao udao = new CmnUsrmDao(con);
        CmnUsrmModel uMdl = udao.select(usid);
        return uMdl != null && uMdl.getUsrUkoFlg() == GSConst.YUKOMUKO_MUKO;
    }
}