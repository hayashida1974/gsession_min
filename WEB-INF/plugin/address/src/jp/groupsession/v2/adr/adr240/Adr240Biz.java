package jp.groupsession.v2.adr.adr240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr240.dao.Adr240Dao;
import jp.groupsession.v2.adr.adr240.model.Adr240Model;
import jp.groupsession.v2.adr.adr240.model.Adr240SearchModel;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 会社選択画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr240Biz {
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Adr240Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr240ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Adr240ParamModel paramMdl, int sessionUsrSid) throws SQLException {

        //インデックスを設定する。
        __setIndex(paramMdl);

//        //ラベルを設定する
//        __setLabelList(paramMdl, sessionUsrSid);

        //業種を設定する
        __setGyoshuList(paramMdl, sessionUsrSid);

        //都道府県を設定する
        __setTdfList(paramMdl, sessionUsrSid);


        //一覧を設定する。
        if (paramMdl.getAdr240SearchMode() == GSConstAddress.SEARCH_COMPANY_MODE_50) {
            __setDspList(paramMdl, sessionUsrSid);
        } else {
            //詳細検索
            __setDspDetailList(paramMdl, sessionUsrSid);
        }

        //画面モード設定する。
        __setMode(paramMdl);

        //プロジェクト選択フラグを設定する
        __setProAddFlg(paramMdl);

   }

    /**
     * <br>[機  能] インデックスをセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr240ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setIndex(Adr240ParamModel paramMdl) throws SQLException {

        Adr240Dao dao = new Adr240Dao(con__);
        List<String> comkanaList = dao.getCompanyInitialList();
        List<LabelValueBean> indexList = new ArrayList<LabelValueBean>();

        List<String[]> kanaList = new ArrayList<String[]>();

        GsMessage gsMsg = new GsMessage(reqMdl_);
        //50音
        String[] kana_a_line = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                gsMsg.getMessage("cmn.kana.i"),
                                gsMsg.getMessage("cmn.kana.u"),
                                gsMsg.getMessage("cmn.kana.e"),
                                gsMsg.getMessage("cmn.kana.o")};
        String[] kana_ka_line = new String[] {gsMsg.getMessage("cmn.kana.ka"),
                                 gsMsg.getMessage("cmn.kana.ki"),
                                 gsMsg.getMessage("cmn.kana.ku"),
                                 gsMsg.getMessage("cmn.kana.ke"),
                                 gsMsg.getMessage("cmn.kana.ko")};
        String[] kana_sa_line = new String[] {gsMsg.getMessage("cmn.kana.sa"),
                                 gsMsg.getMessage("cmn.kana.shi"),
                                 gsMsg.getMessage("cmn.kana.su"),
                                 gsMsg.getMessage("cmn.kana.se"),
                                 gsMsg.getMessage("cmn.kana.so")};
        String[] kana_ta_line = new String[] {gsMsg.getMessage("cmn.kana.ta"),
                                 gsMsg.getMessage("cmn.kana.chi"),
                                 gsMsg.getMessage("cmn.kana.tsu"),
                                 gsMsg.getMessage("cmn.kana.te"),
                                 gsMsg.getMessage("cmn.kana.to")};
        String[] kana_na_line = new String[] {gsMsg.getMessage("cmn.kana.na"),
                                 gsMsg.getMessage("cmn.kana.ni"),
                                 gsMsg.getMessage("cmn.kana.nu"),
                                 gsMsg.getMessage("cmn.kana.ne"),
                                 gsMsg.getMessage("cmn.kana.no")};
        String[] kana_ha_line = new String[] {gsMsg.getMessage("cmn.kana.ha"),
                                 gsMsg.getMessage("cmn.kana.hi"),
                                 gsMsg.getMessage("cmn.kana.fu"),
                                 gsMsg.getMessage("cmn.kana.he"),
                                 gsMsg.getMessage("cmn.kana.ho")};
        String[] kana_ma_line = new String[] {gsMsg.getMessage("cmn.kana.ma"),
                                 gsMsg.getMessage("cmn.kana.mi"),
                                 gsMsg.getMessage("cmn.kana.mu"),
                                 gsMsg.getMessage("cmn.kana.me"),
                                 gsMsg.getMessage("cmn.kana.mo")};
        String[] kana_ya_line = new String[] {gsMsg.getMessage("cmn.kana.ya"),
                                 gsMsg.getMessage("cmn.kana.yu"),
                                 gsMsg.getMessage("cmn.kana.yo")};
        String[] kana_ra_line = new String[] {gsMsg.getMessage("cmn.kana.ra"),
                                 gsMsg.getMessage("cmn.kana.ri"),
                                 gsMsg.getMessage("cmn.kana.ru"),
                                 gsMsg.getMessage("cmn.kana.re"),
                                 gsMsg.getMessage("cmn.kana.ro")};
        String[] kana_wa_line = new String[] {gsMsg.getMessage("cmn.kana.wa"),
                                 gsMsg.getMessage("cmn.kana.wo"),
                                 gsMsg.getMessage("cmn.kana.n")};

        String[] kana_index = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                            gsMsg.getMessage("cmn.kana.ka"),
                                            gsMsg.getMessage("cmn.kana.sa"),
                                            gsMsg.getMessage("cmn.kana.ta"),
                                            gsMsg.getMessage("cmn.kana.na"),
                                            gsMsg.getMessage("cmn.kana.ha"),
                                            gsMsg.getMessage("cmn.kana.ma"),
                                            gsMsg.getMessage("cmn.kana.ya"),
                                            gsMsg.getMessage("cmn.kana.ra"),
                                            gsMsg.getMessage("cmn.kana.wa")};
        kanaList.add(kana_a_line);
        kanaList.add(kana_ka_line);
        kanaList.add(kana_sa_line);
        kanaList.add(kana_ta_line);
        kanaList.add(kana_na_line);
        kanaList.add(kana_ha_line);
        kanaList.add(kana_ma_line);
        kanaList.add(kana_ya_line);
        kanaList.add(kana_ra_line);
        kanaList.add(kana_wa_line);

        if (comkanaList != null && comkanaList.size() > 0) {

            for (String[] kanaLine : kanaList) {

                String kanaExist = Adr240Const.NOT_EXIST;
                int i = 0;
                String headKana = null;
                for (String kana : kanaLine) {

                    if (i == 0) {
                        headKana = kana;
                    }

                    if (kana.equals(paramMdl.getAdr240Index())) {
                        kanaExist = Adr240Const.INDEX_SELECT;
                        break;
                    }
                    if (comkanaList.contains(kana)) {
                        kanaExist = Adr240Const.EXIST;
                        break;
                    }
                    i++;
                }
                indexList.add(new LabelValueBean(headKana, kanaExist));
            }
        } else {
            //会社情報が一件も存在しない場合。
            for (String kana : kana_index) {
                indexList.add(new LabelValueBean(kana, Adr240Const.NOT_EXIST));
            }
        }

        paramMdl.setAdr240IndexList(indexList);

        //インデックスの詳細を設定する。
        __setIndexDetail(paramMdl);
    }

    /**
     * <br>[機  能] インデックスの詳細をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr240ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setIndexDetail(Adr240ParamModel paramMdl) throws SQLException {

        String index = paramMdl.getAdr240Index();
        if (index.equals(Adr240Const.KANA_ALL) || index.equals(Adr240Const.KANA_COMPANY)) {
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl_);
        //50音
        String[] kana_a_line = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                gsMsg.getMessage("cmn.kana.i"),
                                gsMsg.getMessage("cmn.kana.u"),
                                gsMsg.getMessage("cmn.kana.e"),
                                gsMsg.getMessage("cmn.kana.o")};
        String[] kana_ka_line = new String[] {gsMsg.getMessage("cmn.kana.ka"),
                                 gsMsg.getMessage("cmn.kana.ki"),
                                 gsMsg.getMessage("cmn.kana.ku"),
                                 gsMsg.getMessage("cmn.kana.ke"),
                                 gsMsg.getMessage("cmn.kana.ko")};
        String[] kana_sa_line = new String[] {gsMsg.getMessage("cmn.kana.sa"),
                                 gsMsg.getMessage("cmn.kana.shi"),
                                 gsMsg.getMessage("cmn.kana.su"),
                                 gsMsg.getMessage("cmn.kana.se"),
                                 gsMsg.getMessage("cmn.kana.so")};
        String[] kana_ta_line = new String[] {gsMsg.getMessage("cmn.kana.ta"),
                                 gsMsg.getMessage("cmn.kana.chi"),
                                 gsMsg.getMessage("cmn.kana.tsu"),
                                 gsMsg.getMessage("cmn.kana.te"),
                                 gsMsg.getMessage("cmn.kana.to")};
        String[] kana_na_line = new String[] {gsMsg.getMessage("cmn.kana.na"),
                                 gsMsg.getMessage("cmn.kana.ni"),
                                 gsMsg.getMessage("cmn.kana.nu"),
                                 gsMsg.getMessage("cmn.kana.ne"),
                                 gsMsg.getMessage("cmn.kana.no")};
        String[] kana_ha_line = new String[] {gsMsg.getMessage("cmn.kana.ha"),
                                 gsMsg.getMessage("cmn.kana.hi"),
                                 gsMsg.getMessage("cmn.kana.fu"),
                                 gsMsg.getMessage("cmn.kana.he"),
                                 gsMsg.getMessage("cmn.kana.ho")};
        String[] kana_ma_line = new String[] {gsMsg.getMessage("cmn.kana.ma"),
                                 gsMsg.getMessage("cmn.kana.mi"),
                                 gsMsg.getMessage("cmn.kana.mu"),
                                 gsMsg.getMessage("cmn.kana.me"),
                                 gsMsg.getMessage("cmn.kana.mo")};
        String[] kana_ya_line = new String[] {gsMsg.getMessage("cmn.kana.ya"),
                                 gsMsg.getMessage("cmn.kana.yu"),
                                 gsMsg.getMessage("cmn.kana.yo")};
        String[] kana_ra_line = new String[] {gsMsg.getMessage("cmn.kana.ra"),
                                 gsMsg.getMessage("cmn.kana.ri"),
                                 gsMsg.getMessage("cmn.kana.ru"),
                                 gsMsg.getMessage("cmn.kana.re"),
                                 gsMsg.getMessage("cmn.kana.ro")};
        String[] kana_wa_line = new String[] {gsMsg.getMessage("cmn.kana.wa"),
                                 gsMsg.getMessage("cmn.kana.wo"),
                                 gsMsg.getMessage("cmn.kana.n")};

        HashMap<String, String[]> map = new HashMap<String, String[]>();
        map.put(gsMsg.getMessage("cmn.kana.a"), kana_a_line);
        map.put(gsMsg.getMessage("cmn.kana.ka"), kana_ka_line);
        map.put(gsMsg.getMessage("cmn.kana.sa"), kana_sa_line);
        map.put(gsMsg.getMessage("cmn.kana.ta"), kana_ta_line);
        map.put(gsMsg.getMessage("cmn.kana.na"), kana_na_line);
        map.put(gsMsg.getMessage("cmn.kana.ha"), kana_ha_line);
        map.put(gsMsg.getMessage("cmn.kana.ma"), kana_ma_line);
        map.put(gsMsg.getMessage("cmn.kana.ya"), kana_ya_line);
        map.put(gsMsg.getMessage("cmn.kana.ra"), kana_ra_line);
        map.put(gsMsg.getMessage("cmn.kana.wa"), kana_wa_line);

        String[] indexLine = map.get(index);

        Adr240Dao dao = new Adr240Dao(con__);
        List<String> comkanaList = dao.getCompanyInitialList(indexLine);

        List<LabelValueBean> indexList = new ArrayList<LabelValueBean>();

        if (comkanaList != null && comkanaList.size() > 0) {
            for (String kana : indexLine) {
                String exist = Adr240Const.NOT_EXIST;
                if (kana.equals(paramMdl.getAdr240Str())) {
                    //選択中
                    exist = Adr240Const.INDEX_SELECT;
                } else if (comkanaList.contains(kana)) {
                    exist = Adr240Const.EXIST;
                }
                indexList.add(new LabelValueBean(kana, exist));
            }
        } else {
            for (String kana : comkanaList) {
                indexList.add(new LabelValueBean(kana, Adr240Const.NOT_EXIST));
            }
        }
        paramMdl.setAdr240StrList(indexList);
    }

    /**
    * <br>[機  能] 業種コンボをセットする。
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param paramMdl Adr240ParamModel
    * @param sessionUsrSid ユーザSID
    * @throws SQLException SQL実行例外
    */
       private void __setGyoshuList(
               Adr240ParamModel paramMdl, int sessionUsrSid) throws SQLException {
           AddressBiz adrBiz = new AddressBiz(reqMdl_);
           paramMdl.setAtiCmbList(adrBiz.getGyosyuLabelList(con__));
    }

   /**
    * <br>[機  能] 都道府県コンボをセットする。
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param paramMdl Adr240ParamModel
    * @param sessionUsrSid ユーザSID
    * @throws SQLException SQL実行例外
    */
       private void __setTdfList(Adr240ParamModel paramMdl, int sessionUsrSid) throws SQLException {
           //都道府県コンボを設定
           CommonBiz cmnBiz = new CommonBiz();
           GsMessage gsMsg = new GsMessage(reqMdl_);
           paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con__, gsMsg));
    }

    /**
     * <br>[機  能] 会社一覧をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr240ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setDspList(Adr240ParamModel paramMdl, int sessionUsrSid) throws SQLException {

        String index = paramMdl.getAdr240Index();
        String str = paramMdl.getAdr240Str();

        String[] initials = null;
        GsMessage gsMsg = new GsMessage(reqMdl_);

        if (index.equals(Adr240Const.KANA_ALL)) {
            //全て
            initials = null;
        } else if (index.equals(Adr240Const.KANA_COMPANY)) {
            //会社未選択
            Adr240Model model = new Adr240Model();
            model.setAcoSid(0);
            model.setAbaSid(0);
            model.setAcoName(gsMsg.getMessage("address.adr240.1"));
            model.setAbaName(null);
            List<Adr240Model> dspList = new ArrayList<Adr240Model>();
            dspList.add(model);
            paramMdl.setAdr240CompanyList(dspList);
            return;

        } else if (str.equals(Adr240Const.KANA_ALL)) {
            //行全て

            //50音
            String[] kana_a_line = new String[] {gsMsg.getMessage("cmn.kana.a"),
                                    gsMsg.getMessage("cmn.kana.i"),
                                    gsMsg.getMessage("cmn.kana.u"),
                                    gsMsg.getMessage("cmn.kana.e"),
                                    gsMsg.getMessage("cmn.kana.o")};
            String[] kana_ka_line = new String[] {gsMsg.getMessage("cmn.kana.ka"),
                                     gsMsg.getMessage("cmn.kana.ki"),
                                     gsMsg.getMessage("cmn.kana.ku"),
                                     gsMsg.getMessage("cmn.kana.ke"),
                                     gsMsg.getMessage("cmn.kana.ko")};
            String[] kana_sa_line = new String[] {gsMsg.getMessage("cmn.kana.sa"),
                                     gsMsg.getMessage("cmn.kana.shi"),
                                     gsMsg.getMessage("cmn.kana.su"),
                                     gsMsg.getMessage("cmn.kana.se"),
                                     gsMsg.getMessage("cmn.kana.so")};
            String[] kana_ta_line = new String[] {gsMsg.getMessage("cmn.kana.ta"),
                                     gsMsg.getMessage("cmn.kana.chi"),
                                     gsMsg.getMessage("cmn.kana.tsu"),
                                     gsMsg.getMessage("cmn.kana.te"),
                                     gsMsg.getMessage("cmn.kana.to")};
            String[] kana_na_line = new String[] {gsMsg.getMessage("cmn.kana.na"),
                                     gsMsg.getMessage("cmn.kana.ni"),
                                     gsMsg.getMessage("cmn.kana.nu"),
                                     gsMsg.getMessage("cmn.kana.ne"),
                                     gsMsg.getMessage("cmn.kana.no")};
            String[] kana_ha_line = new String[] {gsMsg.getMessage("cmn.kana.ha"),
                                     gsMsg.getMessage("cmn.kana.hi"),
                                     gsMsg.getMessage("cmn.kana.fu"),
                                     gsMsg.getMessage("cmn.kana.he"),
                                     gsMsg.getMessage("cmn.kana.ho")};
            String[] kana_ma_line = new String[] {gsMsg.getMessage("cmn.kana.ma"),
                                     gsMsg.getMessage("cmn.kana.mi"),
                                     gsMsg.getMessage("cmn.kana.mu"),
                                     gsMsg.getMessage("cmn.kana.me"),
                                     gsMsg.getMessage("cmn.kana.mo")};
            String[] kana_ya_line = new String[] {gsMsg.getMessage("cmn.kana.ya"),
                                     gsMsg.getMessage("cmn.kana.yu"),
                                     gsMsg.getMessage("cmn.kana.yo")};
            String[] kana_ra_line = new String[] {gsMsg.getMessage("cmn.kana.ra"),
                                     gsMsg.getMessage("cmn.kana.ri"),
                                     gsMsg.getMessage("cmn.kana.ru"),
                                     gsMsg.getMessage("cmn.kana.re"),
                                     gsMsg.getMessage("cmn.kana.ro")};
            String[] kana_wa_line = new String[] {gsMsg.getMessage("cmn.kana.wa"),
                                     gsMsg.getMessage("cmn.kana.wo"),
                                     gsMsg.getMessage("cmn.kana.n")};

            HashMap<String, String[]> map = new HashMap<String, String[]>();
            map.put(gsMsg.getMessage("cmn.kana.a"), kana_a_line);
            map.put(gsMsg.getMessage("cmn.kana.ka"), kana_ka_line);
            map.put(gsMsg.getMessage("cmn.kana.sa"), kana_sa_line);
            map.put(gsMsg.getMessage("cmn.kana.ta"), kana_ta_line);
            map.put(gsMsg.getMessage("cmn.kana.na"), kana_na_line);
            map.put(gsMsg.getMessage("cmn.kana.ha"), kana_ha_line);
            map.put(gsMsg.getMessage("cmn.kana.ma"), kana_ma_line);
            map.put(gsMsg.getMessage("cmn.kana.ya"), kana_ya_line);
            map.put(gsMsg.getMessage("cmn.kana.ra"), kana_ra_line);
            map.put(gsMsg.getMessage("cmn.kana.wa"), kana_wa_line);
            initials = map.get(index);

        } else {
            initials = new String[]{str};
        }

        Adr240Dao dao = new Adr240Dao(con__);
        //検索件数
        int searchCnt = dao.countCompanyList(initials);

        //最大件数
        int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;

        AdrUconfDao uconfdao = new AdrUconfDao(con__);
        AdrUconfModel model = uconfdao.select(sessionUsrSid);
        if (model != null && model.getAucComcount() != 0) {
            maxCnt = model.getAucComcount();
        } else {
            maxCnt = Integer.parseInt(GSConstAddress.DEFAULT_COMCOUNT);
        }

        if (paramMdl.getAdr240PrsMode() != 0) {
            maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;
        }


        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getAdr240page();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setAdr240page(page);
        paramMdl.setAdr240pageTop(page);
        paramMdl.setAdr240pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
        }

        if (searchCnt < 1) {
            return;
        }
        //会社一覧を取得
        List<Adr240Model> dspList = dao.getCompanyList(initials, page, maxCnt);
        paramMdl.setAdr240CompanyList(dspList);
    }

    /**
     * <br>[機  能] 会社一覧をセットする。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr240ParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setDspDetailList(
            Adr240ParamModel paramMdl, int sessionUsrSid) throws SQLException {

        Adr240SearchModel searchMdl = new Adr240SearchModel();

        //ソートキー
        searchMdl.setSortKey(paramMdl.getAdr240SortKey());
        //オーダーキー
        searchMdl.setOrderKey(paramMdl.getAdr240OrderKey());
        //企業コード
        searchMdl.setCoCode(paramMdl.getAdr240svCode());
        //会社名
        searchMdl.setCoName(paramMdl.getAdr240svCoName());
        //会社名カナ
        searchMdl.setCoNameKn(paramMdl.getAdr240svCoNameKn());
        //支店・営業所名
        searchMdl.setCoBaseName(paramMdl.getAdr240svCoBaseName());
        //業種
        searchMdl.setAtiSid(paramMdl.getAdr240svAtiSid());
        //都道府県
        searchMdl.setTdfk(paramMdl.getAdr240svTdfk());
        //備考
        searchMdl.setBiko(paramMdl.getAdr240svBiko());

        Adr240Dao dao = new Adr240Dao(con__);
        //検索件数
        int searchCnt = dao.getSearchCount(searchMdl);

        //最大件数
        int maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;

        AdrUconfDao uconfdao = new AdrUconfDao(con__);
        AdrUconfModel model = uconfdao.select(sessionUsrSid);
        if (model != null && model.getAucComcount() != 0) {
            maxCnt = model.getAucComcount();
        } else {
            maxCnt = Integer.parseInt(GSConstAddress.DEFAULT_COMCOUNT);
        }

        if (paramMdl.getAdr240PrsMode() != 0) {
            maxCnt = GSConstAddress.COMPANYSEARCH_MAXCOUNT;
        }


        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getAdr240page();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setAdr240page(page);
        paramMdl.setAdr240pageTop(page);
        paramMdl.setAdr240pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
        }

        if (searchCnt < 1) {
            return;
        }
        //会社一覧を取得
        searchMdl.setPage(page);
        searchMdl.setMaxViewCount(maxCnt);
        List<Adr240Model> dspList = dao.getSearchResultList(searchMdl);
        paramMdl.setAdr240CompanyList(dspList);
    }

    /**
     * <br>[機  能] 画面モード設定する。
     * <br>[解  説]
     * <br>[備  考] 0:会社・担当者選択 1:会社のみ選択
     *
     * @param paramMdl Adr240ParamModel
     */
    private void __setMode(Adr240ParamModel paramMdl) {
        String parentPage = NullDefault.getString(paramMdl.getAdr240parentPageId(), "");

        if (parentPage.equals("adr020")) {
            paramMdl.setAdr240mode(1);

        } else if (parentPage.equals("ntp040")
            || parentPage.equals("ntp061")
            || parentPage.equals("ntp100")) {

            paramMdl.setAdr240mode(1);
            paramMdl.setAdr240selMode(1);
        }
    }

    /**
     * <br>[機  能] プロジェクト選択フラグを設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Adr240ParamModel
     */
    private void __setProAddFlg(Adr240ParamModel paramMdl) {
        int prjFlg = 0;

        if (paramMdl.getAdr240parentPageId().equals("prj150")) {
            prjFlg = 1;
        }

        paramMdl.setAdr240ProAddFlg(prjFlg);
    }
}