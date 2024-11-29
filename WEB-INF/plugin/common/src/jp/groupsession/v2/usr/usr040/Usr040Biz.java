package jp.groupsession.v2.usr.usr040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPriSortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmLabelModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.model.UsrAconfModel;
import jp.groupsession.v2.usr.usr030.KanaLinkModel;

/**
 * <br>[機  能] ユーザ情報一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr040Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr040Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr040Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期画面設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param action アクション
     * @param reqMdl RequestModel
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public static void setDsp(
            Usr040Action action,
            RequestModel reqMdl,
            Usr040ParamModel paramMdl,
            Connection con) throws Exception {

        BaseUserModel smodel = reqMdl.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, smodel, GSConstUser.PLUGIN_ID_USER);
        if (adminUser) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

    }

    /**
     * <br>[機  能] エクスポートボタンの表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @param admin true:管理者 false:管理者以外
     * @throws Exception 実行例外
     */
    public void setExportBtn(
            Usr040ParamModel paramMdl, Connection con, boolean admin) throws Exception {
        UsrCommonBiz userBiz = new UsrCommonBiz(con, reqMdl__);
        UsrAconfModel aconfModel = userBiz.getAConfModel(con);

        int export = aconfModel.getUadExport();
        log__.debug(">>>export :" + export);
        if (admin) {
            paramMdl.setUsr040CsvExportKbn(GSConstUser.EXPORT_USE_OK);
        } else if (export == GSConstUser.CSV_EXPORT_ALL) {
            paramMdl.setUsr040CsvExportKbn(GSConstUser.EXPORT_USE_OK);
        } else {
            paramMdl.setUsr040CsvExportKbn(GSConstUser.EXPORT_USE_NG);
        }

    }

    /**
     * <br>[機  能] ラベル追加ボタンの表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @param admin true:管理者 false:管理者以外
     * @throws Exception 実行例外
     */
    public void setLabelSetBtn(
            Usr040ParamModel paramMdl, Connection con, boolean admin) throws Exception {
        UsrCommonBiz userBiz = new UsrCommonBiz(con, reqMdl__);
        CmnLabelUsrConfModel lconfModel = userBiz.getLabelConfModel(con);


        ArrayList<UsrCategoryLabelModel> categoryLabelList = paramMdl.getUsr040CaegoryLabelList();

        //ラベルデータ有無フラグ
        boolean dataFlg = false;
        if (categoryLabelList != null) {
            for (UsrCategoryLabelModel mdl : categoryLabelList) {
                if (mdl.getLabelList().size() > 0) {
                    dataFlg = true;
                    break;
                }
            }
        }

        int label = lconfModel.getLufSet();
        log__.debug(">>>label :" + label);
        if (!dataFlg) {
            paramMdl.setUsrLabelSetKbn(GSConstUser.LABEL_SET_NG);
        } else if (admin) {
            paramMdl.setUsrLabelSetKbn(GSConstUser.LABEL_SET_OK);
        } else if (label == GSConstUser.POW_ALL) {
            paramMdl.setUsrLabelSetKbn(GSConstUser.LABEL_SET_OK);
        } else {
            paramMdl.setUsrLabelSetKbn(GSConstUser.LABEL_SET_NG);
        }
    }

    /**
     * <br>[機  能] ラベル管理ボタンの表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @param admin true:管理者 false:管理者以外
     * @throws Exception 実行例外
     */
    public void setLabelEditBtn(
            Usr040ParamModel paramMdl, Connection con, boolean admin) throws Exception {
        UsrCommonBiz userBiz = new UsrCommonBiz(con, reqMdl__);
        CmnLabelUsrConfModel lconfModel = userBiz.getLabelConfModel(con);

        int labelEdit = lconfModel.getLufEdit();
        log__.debug(">>>label :" + labelEdit);
        if (admin) {
            paramMdl.setUsrLabelEditKbn(GSConstUser.LABEL_EDIT_OK);
        } else if (labelEdit == GSConstUser.POW_ALL) {
            paramMdl.setUsrLabelEditKbn(GSConstUser.LABEL_EDIT_OK);
        } else {
            paramMdl.setUsrLabelEditKbn(GSConstUser.LABEL_EDIT_NG);
        }

    }

    /**
     * <p>五十音のリストを作成しフォームにセットする。
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setKanaIndex(Usr040ParamModel paramMdl, Connection con) throws Exception {
        log__.debug("START");

        //五十音の存在するリストを取得
        CmnUsrmDao udao = new CmnUsrmDao(con);
        Map<String, String> ekanas = udao.getExistsKanaIndex(false);

        List<KanaLinkModel> list50 = getKanaLinkList(ekanas);
        paramMdl.setUsr040ekanas(list50);

        log__.debug("END");
    }

    /**
     * <p>カナのリストを作成する
     * @param existsMap 存在カナのマップ
     * @return 50音作成のモデル
     */
    private List<KanaLinkModel> getKanaLinkList(Map<String, String> existsMap) {
        log__.debug("START");

        List<KanaLinkModel> list50 = new ArrayList<KanaLinkModel>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 50音カタカナ **/
        String kana = gsMsg.getMessage("cmn.kana.a")
                    + gsMsg.getMessage("cmn.kana.ka")
                    + gsMsg.getMessage("cmn.kana.sa")
                    + gsMsg.getMessage("cmn.kana.ta")
                    + gsMsg.getMessage("cmn.kana.na")
                    + gsMsg.getMessage("cmn.kana.ha")
                    + gsMsg.getMessage("cmn.kana.ma")
                    + gsMsg.getMessage("cmn.kana.ya")
                    + gsMsg.getMessage("cmn.kana.ra")
                    + gsMsg.getMessage("cmn.kana.wa")
                    + gsMsg.getMessage("cmn.kana.i")
                    + gsMsg.getMessage("cmn.kana.ki")
                    + gsMsg.getMessage("cmn.kana.shi")
                    + gsMsg.getMessage("cmn.kana.chi")
                    + gsMsg.getMessage("cmn.kana.ni")
                    + gsMsg.getMessage("cmn.kana.hi")
                    + gsMsg.getMessage("cmn.kana.mi")
                    + " "
                    + gsMsg.getMessage("cmn.kana.ri")
                    + gsMsg.getMessage("cmn.kana.wo")
                    + gsMsg.getMessage("cmn.kana.u")
                    + gsMsg.getMessage("cmn.kana.ku")
                    + gsMsg.getMessage("cmn.kana.su")
                    + gsMsg.getMessage("cmn.kana.tsu")
                    + gsMsg.getMessage("cmn.kana.nu")
                    + gsMsg.getMessage("cmn.kana.fu")
                    + gsMsg.getMessage("cmn.kana.mu")
                    + gsMsg.getMessage("cmn.kana.yu")
                    + gsMsg.getMessage("cmn.kana.ru")
                    + gsMsg.getMessage("cmn.kana.n")
                    + gsMsg.getMessage("cmn.kana.e")
                    + gsMsg.getMessage("cmn.kana.ke")
                    + gsMsg.getMessage("cmn.kana.se")
                    + gsMsg.getMessage("cmn.kana.te")
                    + gsMsg.getMessage("cmn.kana.ne")
                    + gsMsg.getMessage("cmn.kana.he")
                    + gsMsg.getMessage("cmn.kana.me")
                    + " "
                    + gsMsg.getMessage("cmn.kana.re")
                    + " "
                    + gsMsg.getMessage("cmn.kana.o")
                    + gsMsg.getMessage("cmn.kana.ko")
                    + gsMsg.getMessage("cmn.kana.so")
                    + gsMsg.getMessage("cmn.kana.to")
                    + gsMsg.getMessage("cmn.kana.no")
                    + gsMsg.getMessage("cmn.kana.ho")
                    + gsMsg.getMessage("cmn.kana.mo")
                    + gsMsg.getMessage("cmn.kana.yo")
                    + gsMsg.getMessage("cmn.kana.ro")
                    + " ";
        char[] katakana = kana.toCharArray();
        //
        int row = 0;
        for (int i = 0; i < katakana.length; i++) {
            KanaLinkModel kmodel = new KanaLinkModel();
            if ((i % 10) == 0) {
                row += 1;
            }
            kmodel.setRow(Integer.toString(row));
            String tmp = String.valueOf(katakana[i]);
            kmodel.setKana(tmp);
            if (existsMap.get(tmp) == null) {
                kmodel.setExists(false);
            } else {
                kmodel.setExists(true);
            }
            list50.add(kmodel);
        }

        log__.debug("END");
        return list50;
    }

    /**
     * <br>[機  能] 画面に常に表示する値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(Usr040ParamModel paramMdl, Connection con)
    throws SQLException {

        //グループリスト
        setGroupList(paramMdl, con);
        //都道府県ラベル
        setTdfkList(paramMdl, con);
        //役職ラベル
        setPosList(paramMdl, con);
        //性別ラベル
        setSeibetuList(paramMdl, con);
        //年月日コンボを設定
        paramMdl.setUsr040entranceYearFrLabel(__getYearLabelList(reqMdl__));   //年
        paramMdl.setUsr040entranceMonthFrLabel(__getMonthLabelList(reqMdl__)); //月
        paramMdl.setUsr040entranceDayFrLabel(__getDayLabelList(reqMdl__));     //日

        //カテゴリー・ラベルデータ取得
        setCategoryLabelData(paramMdl, con);

    }

    /**
     * <br>[機  能] 都道府県リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setTdfkList(Usr040ParamModel paramMdl,
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
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setPosList(Usr040ParamModel paramMdl, Connection con)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //役職ラベル
        PosBiz pBiz = new PosBiz();
        paramMdl.setPosLabelList(pBiz.getPosSearchLabelList(con, gsMsg));
    }

    /**
     * <br>[機  能] 性別リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr031ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setSeibetuList(Usr040ParamModel paramMdl, Connection con)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        labelList.add(
                new LabelValueBean(gsMsg.getMessage("ntp.166"),
                        String.valueOf("-1")));
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
     * <br>[機  能] グループコンボのリストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setGroupList(Usr040ParamModel paramMdl,
            Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz biz = new GroupBiz(); //
        ArrayList <LabelValueBean> grpLabel = biz.getGroupCombLabelList(con, true, gsMsg);
        paramMdl.setGrpLabelList(grpLabel);
    }

    /**
     * ソートコンボを設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     */
    public void setSortCombo(Usr040ParamModel paramMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //社員/職員番号
        String textStaffNumber = gsMsg.getMessage("cmn.employee.staff.number");
        //氏名
        String textName = gsMsg.getMessage("cmn.name");
        //役職
        String textPost = gsMsg.getMessage("cmn.post");
        //生年月日
        String textBirthday = gsMsg.getMessage("cmn.birthday");
        //ソートキー1
        String textSortkey1 = gsMsg.getMessage("cmn.sortkey") + 1;
        //ソートキー2
        String textSortkey2 = gsMsg.getMessage("cmn.sortkey") + 2;

        String[] listSortKeyUsrText = new String[] {
                textStaffNumber, textName, textPost,
                             textBirthday, textSortkey1, textSortkey2 };
        //ソートキーラベル
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstUser.LIST_SORT_KEY_USR.length; i++) {
            String label = listSortKeyUsrText[i];
            String value = Integer.toString(GSConstUser.LIST_SORT_KEY_USR[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        paramMdl.setSortKeyLabelList(sortLabel);
    }

    /**
     * <br>[機  能] カテゴリー＆ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行時エラー
     */
    public void setCategoryLabelData(Usr040ParamModel paramMdl, Connection con)
            throws SQLException {

        ArrayList<UsrCategoryLabelModel> uclMdlList = new ArrayList<UsrCategoryLabelModel>();
        CmnLabelUsrCategoryDao dao = new CmnLabelUsrCategoryDao(con);

        ArrayList<CmnLabelUsrCategoryModel> modelList = dao.select();
        for (CmnLabelUsrCategoryModel model : modelList) {
            UsrCategoryLabelModel uclMdl = new UsrCategoryLabelModel();
            uclMdl.setCategorySid(model.getLucSid());
            uclMdl.setCategoryName(model.getLucName());

            CmnLabelUsrDao cluDao = new CmnLabelUsrDao(con);
            ArrayList<CmnLabelUsrModel> labelUsrList = cluDao.select(model.getLucSid());
            uclMdl.setLabelList(labelUsrList);

            uclMdlList.add(uclMdl);
        }

        paramMdl.setUsr040CaegoryLabelList(uclMdlList);
    }


    /**
     * <br>[機  能] ソート順を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @param umodel ユーザ情報モデル
     * @throws SQLException SQL実行時例外
     */
    public void setSort(
            Usr040ParamModel paramMdl, Connection con, BaseUserModel umodel) throws SQLException {

        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        //管理者が設定したソート設定値を取得
        CmnUsrAdmSortConfModel aconf = biz.getSortAdmConfModel(con);
        //各ユーザ独自のソート設定値を取得
        CmnUsrPriSortConfModel pconf = biz.getSortPriConfModel(con, umodel.getUsrsid(), aconf);

        //管理者強制のソート
        if (aconf.getUasSortKbn() == GSConstUser.DEFO_DSP_ADM) {
            paramMdl.setUsr040sortKey(aconf.getUasSortKey1());
            paramMdl.setUsr040sortKey2(aconf.getUasSortKey2());
            paramMdl.setUsr040orderKey(aconf.getUasSortOrder1());
            paramMdl.setUsr040orderKey2(aconf.getUasSortOrder2());

        //個人でソートを設定
        } else {
            paramMdl.setUsr040sortKey(pconf.getUpsSortKey1());
            paramMdl.setUsr040sortKey2(pconf.getUpsSortKey2());
            paramMdl.setUsr040orderKey(pconf.getUpsSortOrder1());
            paramMdl.setUsr040orderKey2(pconf.getUpsSortOrder2());
        }
    }

    /**
     * <br>[機  能] ユーザラベルを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setUsrLabel(
            Usr040ParamModel paramMdl, Connection con, int usrSid) throws SQLException {

        String[] selectLabel = paramMdl.getUsr040selectLabel();
        if (selectLabel == null || selectLabel.length < 1) {
            return;
        }

        String[] userSid = paramMdl.getUsr040selectUser();
        UDate now = new UDate();

        CmnUsrmLabelDao dao = new CmnUsrmLabelDao(con);
        if (userSid != null) {
            for (int i = 0; i < userSid.length; i++) {
                CmnUsrmLabelModel model = new CmnUsrmLabelModel();
                model.setUsrSid(Integer.parseInt(userSid[i]));
                model.setUslAdate(now);
                model.setUslAuid(usrSid);
                model.setUslEdate(now);
                model.setUslEuid(usrSid);
                for (String labelSid : selectLabel) {
                    model.setLabSid(Integer.parseInt(labelSid));
                    dao.insertLabelMulti(model);
                }
            }

        }
    }

    /**
     * <br>[機  能] 宛先・CC・BCCにセットするユーザのSIDを設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAddressUsrSid(Usr040ParamModel paramMdl, Connection con)
        throws SQLException {


        int setFlg = GSConst.SEND_KBN_ATESAKI;

        if (paramMdl.getUsr040SendMailMode() == GSConst.SEND_KBN_ATESAKI) {
            //宛先
            if (paramMdl.getUsr040UsrSid() > 0) {
                //アドレスデータ選択
                setFlg = GSConst.SEND_KBN_ATESAKI_1;
            }

        } else if (paramMdl.getUsr040SendMailMode() == GSConst.SEND_KBN_CC) {
            //CC
            setFlg = GSConst.SEND_KBN_CC;
        } else {
            //BCC
            setFlg = GSConst.SEND_KBN_BCC;
        }

        if (setFlg == GSConst.SEND_KBN_ATESAKI
                || setFlg == GSConst.SEND_KBN_ATESAKI_1) {
            //宛先セットユーザSID
            String [] usrSidsAtsk = getUsrSid(con,
                                              paramMdl.getUsr040SidsAtsk(),
                                              paramMdl.getUsr040selectUser(),
                                              setFlg,
                                              paramMdl.getUsr040UsrSid(),
                                              paramMdl.getUsr040AddressKbn());

            paramMdl.setUsr040SidsAtsk(usrSidsAtsk);

        } else if (setFlg == GSConst.SEND_KBN_CC) {

            //CCセットユーザSID
            String [] usrSidsCc = getUsrSid(con,
                                            paramMdl.getUsr040SidsCc(),
                                            paramMdl.getUsr040selectUser(),
                                            setFlg,
                                            paramMdl.getUsr040UsrSid(),
                                            paramMdl.getUsr040AddressKbn());

            paramMdl.setUsr040SidsCc(usrSidsCc);

        } else if (setFlg == GSConst.SEND_KBN_BCC) {
            //BCCセットユーザSID
            String [] usrSidsBcc = getUsrSid(con,
                                             paramMdl.getUsr040SidsBcc(),
                                             paramMdl.getUsr040selectUser(),
                                             setFlg,
                                             paramMdl.getUsr040UsrSid(),
                                             paramMdl.getUsr040AddressKbn());

            paramMdl.setUsr040SidsBcc(usrSidsBcc);
        }
    }

    /**
     * <br>[機  能] 送信先のユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param oldUsrSids 既に設定しているユーザSID
     * @param newUsrSids 選択したユーザSID
     * @param setFlg 選択ユーザセットフラグ
     * @param oneAtskUsrSid 宛先にセットするユーザSID
     * @param addressKbn アドレス区分
     * @return 画面に表示するユーザSID
     * @throws SQLException SQL実行時例外
     */
    public String[] getUsrSid(Connection con,
                               String[] oldUsrSids,
                               String[] newUsrSids,
                               int setFlg,
                               int oneAtskUsrSid,
                               String addressKbn)
    throws SQLException {

        List<String> usrSidList = new ArrayList<String>();

        if (oldUsrSids != null) {
            //選択済のアドレスSID
            for (int i = 0; i < oldUsrSids.length; i++) {
                usrSidList.add(oldUsrSids[i]);
            }
        }

        boolean nameFlg = addressKbn.equals("0");
        CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel userMdl = null;
        if (setFlg == GSConst.SEND_KBN_ATESAKI_1 && oneAtskUsrSid > 0) {
            String oneAddressKbn = addressKbn.toString();
            if (nameFlg) {
                userMdl = usrInfDao.getUserAddressData(oneAtskUsrSid);
                oneAddressKbn = __getAddressKbn(userMdl);
            }
            if (!usrSidList.contains(oneAtskUsrSid + "-" + oneAddressKbn)) {
                //検索結果データの各リンクをクリックしたユーザSID
                usrSidList.add(String.valueOf(oneAtskUsrSid) + "-" + oneAddressKbn);
                return (String[]) usrSidList.toArray(new String[usrSidList.size()]);
            }
        }

        if (newUsrSids == null || newUsrSids.length < 1) {
            return (String[]) usrSidList.toArray(new String[usrSidList.size()]);
        }
        for (int i = 0; i < newUsrSids.length; i++) {
            if (nameFlg) {
                userMdl = usrInfDao.getUserAddressData(Integer.parseInt(newUsrSids[i]));
                addressKbn = __getAddressKbn(userMdl);
            }
            //チェックボックスで選択したアドレスSID
            if (!usrSidList.contains(newUsrSids[i] + "-" + addressKbn)) {
                usrSidList.add(newUsrSids[i] + "-" + addressKbn);
            }
        }

        return (String[]) usrSidList.toArray(new String[usrSidList.size()]);

    }

    /**
     * <br>[機  能] メールアドレスの設定状況に応じてメールアドレス区分を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param userMdl ユーザ情報
     * @return メールアドレス区分(1:メールアドレス1 or 2:メールアドレス2 or 3:メールアドレス3)
     */
    private String __getAddressKbn(CmnUsrmInfModel userMdl) {
        String addressKbn = "0";
        if (userMdl != null) {
            if (!StringUtil.isNullZeroString(userMdl.getUsiMail1())
            && userMdl.getUsiMail1Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                addressKbn = "1";
            } else  if (!StringUtil.isNullZeroString(userMdl.getUsiMail2())
            && userMdl.getUsiMail2Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                addressKbn = "2";
            } else  if (!StringUtil.isNullZeroString(userMdl.getUsiMail3())
            && userMdl.getUsiMail3Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                addressKbn = "3";
            }
        }

        return addressKbn;
    }

    /**
     * <br>[機  能] ユーザアドレス情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSids ユーザSID
     * @param con コネクション
     * @return ret 選択アドレス情報リスト
     * @throws SQLException SQL実行時例外
     */
    public List<CmnUsrmInfModel> getSelUsrList(String[] usrSids,
                                                   Connection con)
        throws SQLException {

        String strUsrSid = "";
        int kCnt = 0;

        List<CmnUsrmInfModel> usrDataList = new ArrayList<CmnUsrmInfModel>();
        List<String> setUsrList = new ArrayList<String>();

        if (usrSids == null || usrSids.length < 1) {
            return usrDataList;
        }

        CmnUsrmInfModel model = new CmnUsrmInfModel();
        CmnUsrmInfDao dao = new CmnUsrmInfDao(con);

        Map<String, CmnUsrmInfModel> usrDataMap = new HashMap<String, CmnUsrmInfModel>();

        for (int i = 0; i < usrSids.length; i++) {

            //区分の位置
            kCnt = usrSids[i].indexOf("-");

            if (usrSids[i].indexOf("-") == -1) {
                //区分位置がない場合
                strUsrSid = usrSids[i];
            } else {
                //区分位置がある場合
                strUsrSid = usrSids[i].substring(0, kCnt);
            }

            if (!setUsrList.contains(strUsrSid)) {
                model = dao.getUserAddressData(Integer.parseInt(strUsrSid));
                usrDataMap.put(strUsrSid, model);
                setUsrList.add(strUsrSid);
            }
        }

        return getUsrList(usrSids, usrDataMap);
    }

    /**
     * <br>[機  能] ユーザアドレス情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSids ユーザSID
     * @param usrDataMapList ユーザ情報
     * @return ret 選択アドレス情報リスト
     * @throws SQLException SQL実行時例外
     */
    public List<CmnUsrmInfModel> getUsrList(String[] usrSids,
                                             Map<String, CmnUsrmInfModel> usrDataMapList)
        throws SQLException {

        String strUsrSid = "";
        String strAddKbn = "";
        int kCnt = 0;

        List<CmnUsrmInfModel> sendUsrDataList = new ArrayList<CmnUsrmInfModel>();

        if (usrDataMapList == null || usrDataMapList.size() < 1) {
            return sendUsrDataList;
        }

        List<String> setUsrList = new ArrayList<String>();

            CmnUsrmInfModel insertModel = new CmnUsrmInfModel();

            for (int m = 0; m < usrSids.length; m++) {

                //区分の位置
                kCnt = usrSids[m].indexOf("-");

                if (usrSids[m].indexOf("-") == -1) {
                    //区分位置がない場合
                    strUsrSid = usrSids[m];
                    strAddKbn = String.valueOf(GSConst.ADDRESS_KBN_1);
                } else {
                    //区分位置がある場合
                    strUsrSid = usrSids[m].substring(0, kCnt);
                    strAddKbn = usrSids[m].substring(kCnt + 1, kCnt + 2);
                }

                if (setUsrList.contains(strUsrSid + strAddKbn)) {
                    continue;
                }

                insertModel = __setUsrData(usrDataMapList.get(strUsrSid), strAddKbn);
                sendUsrDataList.add(insertModel);
                setUsrList.add(strUsrSid + strAddKbn);
            }

        return sendUsrDataList;
    }

    /**
     * <br>[機  能] ユーザデータをセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param setMdl ユーザ情報
     * @param addKbn アドレス区分
     * @return CmnUsrmInfModel
     */
    private CmnUsrmInfModel __setUsrData(CmnUsrmInfModel setMdl,
                                          String addKbn) {
        CmnUsrmInfModel insertModel = new CmnUsrmInfModel();

        insertModel.setUsrSid(setMdl.getUsrSid());
        insertModel.setUsrUkoFlg(setMdl.getUsrUkoFlg());
        insertModel.setUsiSei(setMdl.getUsiSei());
        insertModel.setUsiMei(setMdl.getUsiMei());

        if (addKbn.equals(String.valueOf(GSConst.ADDRESS_KBN_1))) {
            //アドレス１をセット
            insertModel.setUsiMail1(setMdl.getUsiMail1());

        } else if (addKbn.equals(String.valueOf(GSConst.ADDRESS_KBN_2))) {
            //アドレス２をセット
            insertModel.setUsiMail2(setMdl.getUsiMail2());

        } else if (addKbn.equals(String.valueOf(GSConst.ADDRESS_KBN_3))) {
            //アドレス３をセット
            insertModel.setUsiMail3(setMdl.getUsiMail3());
        }
        return insertModel;
    }

    /**
     * <br>[機  能] 削除するユーザを除くユーザ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void deleteUserAtesaki(Usr040ParamModel paramMdl, Connection con)
    throws Exception {

        boolean delFlgAtsk = false;
        boolean delFlgCc = false;
        boolean delFlgBcc = false;

        if (paramMdl.getUsr040SendMailMode() == GSConst.SEND_KBN_ATESAKI) {
            //宛先
            delFlgAtsk = true;
        } else if (paramMdl.getUsr040SendMailMode() == GSConst.SEND_KBN_CC) {
            //CC
            delFlgCc = true;
        } else {
            //BCC
            delFlgBcc = true;
        }

        //削除アドレス
        String[] delAdrSid = new String[1];
        delAdrSid[0] = String.valueOf(paramMdl.getUsr040DelUsrSid());

        //宛先アドレスデータ
        String [] adrSidsAtsk = delAdrSid(paramMdl.getUsr040SidsAtsk(),
                                          delAdrSid[0] + "-" + paramMdl.getUsr040AddressKbn(),
                                          delFlgAtsk);

        paramMdl.setUsr040SidsAtsk(adrSidsAtsk);

        //TOアドレスデータ
        String [] adrSidsCc = delAdrSid(paramMdl.getUsr040SidsCc(),
                                        delAdrSid[0] + "-"  + paramMdl.getUsr040AddressKbn(),
                                        delFlgCc);

        paramMdl.setUsr040SidsCc(adrSidsCc);

        //BCCアドレスデータ
        String [] adrSidsBcc = delAdrSid(paramMdl.getUsr040SidsBcc(),
                                         delAdrSid[0] + "-"  + paramMdl.getUsr040AddressKbn(),
                                         delFlgBcc);

        paramMdl.setUsr040SidsBcc(adrSidsBcc);

    }

    /**
     * <br>[機  能] 削除するユーザを除く送信先のアドレス情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adrSids アドレスSID
     * @param delAdrSid 削除するアドレスSID
     * @param delFlgAtsk 削除対象フラグ
     * @return 画面に表示するアドレスSID
     */
    public String[] delAdrSid(String[] adrSids, String delAdrSid, boolean delFlgAtsk) {

        List<String> adrSidList = new ArrayList<String>();

        if (adrSids == null) {
            return (String[]) adrSidList.toArray(new String[adrSidList.size()]);
        }

        if (!delFlgAtsk) {
            //削除ユーザなし
            return adrSids;
        }

        for (int i = 0; i < adrSids.length; i++) {

            if (adrSids[i].equals(delAdrSid)) {
                continue;
            }
            adrSidList.add(adrSids[i]);
        }
        return (String[]) adrSidList.toArray(new String[adrSidList.size()]);
    }

    /**
     * 年コンボを作成する
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getYearLabelList(RequestModel reqMdl) {

        int sysYear = (new UDate()).getYear();
        int start = sysYear - 50;
        int end = sysYear + 0;
        GsMessage gsMsg = new GsMessage(reqMdl);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        labelList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.notset"), ""));

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                            gsMsg.getMessage(
                                    "cmn.year",
                                    new String[] {String.valueOf(value)}), String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * 月コンボを作成する
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getMonthLabelList(RequestModel reqMdl) {

        int start = 1;
        int end = 12;
        GsMessage gsMsg = new GsMessage(reqMdl);
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        labelList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.notset"), ""));

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                            value + gsMsg.getMessage("cmn.month"), String.valueOf(value)));
        }
        return labelList;
    }
    /**
     * 日コンボを作成する
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  コンボ
     */
    private List < LabelValueBean > __getDayLabelList(RequestModel reqMdl) {

        int start = 1;
        int end = 31;

        GsMessage gsMsg = new GsMessage();
        List < LabelValueBean > labelList = new ArrayList < LabelValueBean >();

        labelList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.notset"), ""));

        for (int value = start; value <= end; value++) {
            labelList.add(
                    new LabelValueBean(
                             value + gsMsg.getMessage("cmn.day"), String.valueOf(value)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] カテゴリー一覧の開閉フラグの初期値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setCategoryOpenFlg(Usr040ParamModel paramMdl, Connection con) throws SQLException {

        CmnLabelUsrCategoryDao dao = new CmnLabelUsrCategoryDao(con);
        int cnt = dao.count();
        String [] openFlgs = new String[cnt];
        for (int i = 0; i < cnt; i++) {
            //0:閉  1:開
            openFlgs[i] = "0";
        }
        paramMdl.setUsr040CategoryOpenFlg(openFlgs);
    }
}
