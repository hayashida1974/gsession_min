package jp.groupsession.v2.rng.rng020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.rng.rng060.IRng060PersonalParam;
import jp.groupsession.v2.rng.rng060.Rng060PersonalParamImpl;
import jp.groupsession.v2.rng.rng130.Rng130ParamModel;

/**
 * <br>[機  能] 稟議作成画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020ParamModel extends Rng130ParamModel
implements IRng060PersonalParam, IRng020PeronalParam {
    /** 稟議タイトル */
    private String rng020Title__ = null;
    /** 内容 */
    private String rng020Content__ = null;
    /** 選択した削除ファイル名リスト */
    private String[] rng020files__ = null;
    /** 稟議テンプレートID*/
    private int rng020rtpSid__ = 0;
    /** テンプレートバージョン*/
    private int rng020rtpVer__ = 0;
    /** 経路テンプレートバージョン*/
    private int rng020rtpKeiroVersion__ = 0;

    /** テンプレートから設定した添付ファイルのファイルID */
    private String rng020TemplateFileId__ = null;
    /** 複写して申請 */
    private boolean rng020copyApply__ = false;
    /** フォーム入力要素 DBからのロード値*/
    private Map<FormAccesser, List<String>> rng020inputLoad__;
    /** 経路設定 DBからのロード値*/
    private Map<Integer, Rng020KeiroBlock> rng020keiro__
    = new HashMap<Integer, Rng020KeiroBlock>();
    /** 最終確認設定 DBからのロード値*/
    private Map<Integer, Rng020KeiroBlock> rng020kakuninKeiro__
    = new HashMap<Integer, Rng020KeiroBlock>();
    /** 最終確認設定(元データ保持用) DBからのロード値*/
    private Map<Integer, Rng020KeiroBlock> rng020kakuninSvKeiro__
    = new HashMap<Integer, Rng020KeiroBlock>();

    /** 申請ID 使用フラグ 0：使用しない 1：使用する*/
    private int idUseFlg__ = 0;
    /** 申請ID */
    private String rng020ID__ = null;
    /** 発行予定申請ID */
    private String rng020PlanID__ = null;
    /** 申請ID タイトル */
    private String rng020IdTitle__ = null;
    /** 申請ID 手動入力フラグ 0:手入力変更しない 1:手入力変更する */
    private int rng020IdPrefManual__ = 0;
    /** 申請ID 手動入力 可能フラグ */
    private int idPrefManualEditable__ = 0;
    /** テンプレート選択画面選択情報*/
    private Rng060PersonalParamImpl rng060params__ = new Rng060PersonalParamImpl();
    /** 申請画面からの戻り先画面を保管*/
    private String rng020prevForward__;
    /** 読み込む経路テンプレートSID*/
    private int loadRctSid__ = 0;
    /** スクロール位置*/
    private int scrollY__;
    /** フォーム要素 */
    private FormInputBuilder rng020input__ = new FormInputBuilder();

    //表示項目

    /** 申請者 */
    private String rng020requestUser__ = null;
    /** 申請者 id*/
    private String rng020requestUserId__ = null;
    /** 申請者(選択) */
    private String rng020selectedRequestUserId__;
    /** 申請者(選択) ユーザ一覧 */
    private List<LabelValueBean> rng020selectedRequestUserList__;
    /** 申請者 グループ(選択) */
    private String rng020selectedRequestGroupId__;
    /** 申請者 グループ(選択) グループ一覧 */
    private List<LabelValueBean> rng020selectedRequestGroupList__;

    /** 作成日 */
    private String rng020createDate__ = null;
    /** 添付ファイル一覧 */
    private List<LabelValueBean> rng020fileList__ = null;
    /** 添付ファイル一覧(テンプレートから取得) */
    private List<LabelValueBean> rng020templateFileList__ = null;

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String rng020ScrollFlg__ = "0";
    /** 稟議テンプレート種類 */
    private int rng020rtpType__ = -1;
    /** テンプレート使用制限 */
    private int useTemplateFlg__ = -1;
    /** 個人経路テンプレート使用制限 */
    private int useKeiroTemplateFlg__ = -1;

    /**経路変更フラグ フォーム入力値によって変更があった場合のフラグ*/
    private int keiroAutoChanged__ = 0;
    /**テンプレート更新フラグ 草稿or複写で開いた稟議のテンプレートでバージョン判定(0:違いなし / 1:テンプレートバージョン違い / 2:経路バージョン違い / 3:両方違う) */
    private int rtpVerUpdated__ = 0;
    /**最終確認経路表示判定フラグ*/
    private int kakuninKeiroDspFlg__ = 0;
    /**複写用経路データ使用フラグ(個人テンプレート、汎用稟議テンプレート, 旧式テンプレートは使用しない)*/
    private boolean rng020useCopyKeiro__ = false;
    /** ボタン表示フラグ */
    private boolean rng020ButtonDsp__ = true;
    /** 決済後アクション使用フラグ */
    private int rng020UseApiConnect__;
    /** 決済後アクションコメント */
    private String rng020ApiComment__;

    /**
     * <p>rng020Content を取得します。
     * @return rng020Content
     */
    public String getRng020Content() {
        return rng020Content__;
    }
    /**
     * <p>rng020Content をセットします。
     * @param rng020Content rng020Content
     */
    public void setRng020Content(String rng020Content) {
        rng020Content__ = rng020Content;
    }
    /**
     * <p>rng020createDate を取得します。
     * @return rng020createDate
     */
    public String getRng020createDate() {
        return rng020createDate__;
    }
    /**
     * <p>rng020createDate をセットします。
     * @param rng020createDate rng020createDate
     */
    public void setRng020createDate(String rng020createDate) {
        rng020createDate__ = rng020createDate;
    }
    /**
     * <p>rng020files を取得します。
     * @return rng020files
     */
    public String[] getRng020files() {
        return rng020files__;
    }
    /**
     * <p>rng020files をセットします。
     * @param rng020files rng020files
     */
    public void setRng020files(String[] rng020files) {
        rng020files__ = rng020files;
    }
    /**
     * <p>rng020fileList を取得します。
     * @return rng020fileList
     */
    public List<LabelValueBean> getRng020fileList() {
        return rng020fileList__;
    }
    /**
     * <p>rng020fileList をセットします。
     * @param rng020fileList rng020fileList
     */
    public void setRng020fileList(List<LabelValueBean> rng020fileList) {
        rng020fileList__ = rng020fileList;
    }
    /**
     * <p>rng020Title を取得します。
     * @return rng020Title
     */
    public String getRng020Title() {
        return rng020Title__;
    }
    /**
     * <p>rng020Title をセットします。
     * @param rng020Title rng020Title
     */
    public void setRng020Title(String rng020Title) {
        rng020Title__ = rng020Title;
    }
    /**
     * <p>rng020requestUser を取得します。
     * @return rng020requestUser
     */
    public String getRng020requestUser() {
        return rng020requestUser__;
    }
    /**
     * <p>rng020requestUser をセットします。
     * @param rng020requestUser rng020requestUser
     */
    public void setRng020requestUser(String rng020requestUser) {
        rng020requestUser__ = rng020requestUser;
    }
    /**
     * <p>rng020copyApply を取得します。
     * @return rng020copyApply
     */
    public boolean isRng020copyApply() {
        return rng020copyApply__;
    }
    /**
     * <p>rng020copyApply をセットします。
     * @param rng020copyApply rng020copyApply
     */
    public void setRng020copyApply(boolean rng020copyApply) {
        rng020copyApply__ = rng020copyApply;
    }

    /**
     * @return rng020ScrollFlg
     */
    public String getRng020ScrollFlg() {
        return rng020ScrollFlg__;
    }
    /**
     * @param rng020ScrollFlg 設定する rng020ScrollFlg
     */
    public void setRng020ScrollFlg(String rng020ScrollFlg) {
        rng020ScrollFlg__ = rng020ScrollFlg;
    }
    /**
     * <p>rng020requestUserId を取得します。
     * @return rng020requestUserId
     */
    public String getRng020requestUserId() {
        return rng020requestUserId__;
    }
    /**
     * <p>rng020requestUserId をセットします。
     * @param rng020requestUserId rng020requestUserId
     */
    public void setRng020requestUserId(String rng020requestUserId) {
        rng020requestUserId__ = rng020requestUserId;
    }
    /**
     * <p>rng020selectedRequestUserId を取得します。
     * @return rng020selectedRequestUserId
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestUserId__
     */
    public String getRng020selectedRequestUserId() {
        return rng020selectedRequestUserId__;
    }
    /**
     * <p>rng020selectedRequestUserId をセットします。
     * @param rng020selectedRequestUserId rng020selectedRequestUserId
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestUserId__
     */
    public void setRng020selectedRequestUserId(String rng020selectedRequestUserId) {
        rng020selectedRequestUserId__ = rng020selectedRequestUserId;
    }
    /**
     * <p>rng020selectedRequestUserList を取得します。
     * @return rng020selectedRequestUserList
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestUserList__
     */
    public List<LabelValueBean> getRng020selectedRequestUserList() {
        return rng020selectedRequestUserList__;
    }
    /**
     * <p>rng020selectedRequestUserList をセットします。
     * @param rng020selectedRequestUserList rng020selectedRequestUserList
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestUserList__
     */
    public void setRng020selectedRequestUserList(
            List<LabelValueBean> rng020selectedRequestUserList) {
        rng020selectedRequestUserList__ = rng020selectedRequestUserList;
    }
    /**
     * <p>rng020selectedRequestGroupId を取得します。
     * @return rng020selectedRequestGroupId
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestGroupId__
     */
    public String getRng020selectedRequestGroupId() {
        return rng020selectedRequestGroupId__;
    }
    /**
     * <p>rng020selectedRequestGroupId をセットします。
     * @param rng020selectedRequestGroupId rng020selectedRequestGroupId
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestGroupId__
     */
    public void setRng020selectedRequestGroupId(
            String rng020selectedRequestGroupId) {
        rng020selectedRequestGroupId__ = rng020selectedRequestGroupId;
    }
    /**
     * <p>rng020selectedRequestGroupList を取得します。
     * @return rng020selectedRequestGroupList
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestGroupList__
     */
    public List<LabelValueBean> getRng020selectedRequestGroupList() {
        return rng020selectedRequestGroupList__;
    }
    /**
     * <p>rng020selectedRequestGroupList をセットします。
     * @param rng020selectedRequestGroupList rng020selectedRequestGroupList
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020selectedRequestGroupList__
     */
    public void setRng020selectedRequestGroupList(
            List<LabelValueBean> rng020selectedRequestGroupList) {
        rng020selectedRequestGroupList__ = rng020selectedRequestGroupList;
    }
    /**
     * <p>rng020TemplateFileId を取得します。
     * @return rng020TemplateFileId
     */
    public String getRng020TemplateFileId() {
        return rng020TemplateFileId__;
    }
    /**
     * <p>rng020TemplateFileId をセットします。
     * @param rng020TemplateFileId rng020TemplateFileId
     */
    public void setRng020TemplateFileId(String rng020TemplateFileId) {
        rng020TemplateFileId__ = rng020TemplateFileId;
    }
    /**
     * <p>rng020templateFileList を取得します。
     * @return rng020templateFileList
     */
    public List<LabelValueBean> getRng020templateFileList() {
        return rng020templateFileList__;
    }
    /**
     * <p>rng020templateFileList をセットします。
     * @param rng020templateFileList rng020templateFileList
     */
    public void setRng020templateFileList(
            List<LabelValueBean> rng020templateFileList) {
        rng020templateFileList__ = rng020templateFileList;
    }
    /**
     * <p>rng020rtpSid を取得します。
     * @return rng020rtpSid
     */
    public int getRng020rtpSid() {
        return rng020rtpSid__;
    }
    /**
     * <p>rng020rtpSid をセットします。
     * @param rng020rtpSid rng020rtpSid
     */
    public void setRng020rtpSid(int rng020rtpSid) {
        rng020rtpSid__ = rng020rtpSid;
    }
    /**
     * <p>rng020rtpVer を取得します。
     * @return rng020rtpVer
     */
    public int getRng020rtpVer() {
        return rng020rtpVer__;
    }
    /**
     * <p>rng020rtpVer をセットします。
     * @param rng020rtpVer rng020rtpVer
     */
    public void setRng020rtpVer(int rng020rtpVer) {
        rng020rtpVer__ = rng020rtpVer;
    }
    /**
     * <p>rng020rtpKeiroVersion を取得します。
     * @return rng020rtpKeiroVersion
     */
    public int getRng020rtpKeiroVersion() {
        return rng020rtpKeiroVersion__;
    }
    /**
     * <p>rng020rtpKeiroVersion をセットします。
     * @param rng020rtpKeiroVersion rng020rtpKeiroVersion
     */
    public void setRng020rtpKeiroVersion(int rng020rtpKeiroVersion) {
        rng020rtpKeiroVersion__ = rng020rtpKeiroVersion;
    }

    /**
     * <p>rng020inputLoad を取得します。
     * @return rng020inputLoad
     */
    public Map<FormAccesser, List<String>> getRng020inputLoad() {
        return rng020inputLoad__;
    }
    /**
     * <p>rng020inputLoad をセットします。
     * @param rng020inputLoad rng020inputLoad
     */
    public void setRng020inputLoad(Map<FormAccesser, List<String>> rng020inputLoad) {
        rng020inputLoad__ = rng020inputLoad;
    }
    /**
     * <p>rng020IdPrefManual を取得します。
     * @return rng020IdPrefManual
     */
    public int getRng020IdPrefManual() {
        return rng020IdPrefManual__;
    }
    /**
     * <p>rng020IdPrefManual をセットします。
     * @param rng020IdPrefManual rng020IdPrefManual
     */
    public void setRng020IdPrefManual(int rng020IdPrefManual) {
        rng020IdPrefManual__ = rng020IdPrefManual;
    }
    /**
     * <p>rng020ID を取得します。
     * @return rng020ID
     */
    public String getRng020ID() {
        return rng020ID__;
    }
    /**
     * <p>rng020ID をセットします。
     * @param rng020id rng020ID
     */
    public void setRng020ID(String rng020id) {
        rng020ID__ = rng020id;
    }
    /**
     * <p>rng020PlanID を取得します。
     * @return rng020PlanID
     */
    public String getRng020PlanID() {
        return rng020PlanID__;
    }
    /**
     * <p>rng020PlanID をセットします。
     * @param rng020PlanID rng020PlanID
     */
    public void setRng020PlanID(String rng020PlanID) {
        rng020PlanID__ = rng020PlanID;
    }
    /**
     * <p>rng020IdTitle を取得します。
     * @return rng020IdTitle
     */
    public String getRng020IdTitle() {
        return rng020IdTitle__;
    }
    /**
     * <p>rng020IdTitle をセットします。
     * @param rng020IdTitle rng020IdTitle
     */
    public void setRng020IdTitle(String rng020IdTitle) {
        rng020IdTitle__ = rng020IdTitle;
    }
    /**
     * <p>rng020keiro を取得します。
     * @return rng020keiro
     */
    public Map<Integer, Rng020KeiroBlock> getRng020keiroMap() {
        return rng020keiro__;
    }
    /**
     * <p>rng020keiro をセットします。
     * @param rng020keiro rng020keiro
     */
    public void setRng020keiroMap(Map<Integer, Rng020KeiroBlock> rng020keiro) {
        rng020keiro__ = rng020keiro;
    }
    /**
     * <p>rng020kakuninKeiro を取得します。
     * @return rng020kakuninKeiro
     */
    public Map<Integer, Rng020KeiroBlock> getRng020kakuninKeiroMap() {
        return rng020kakuninKeiro__;
    }
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setRng020kakuninKeiroMap(Map<Integer, Rng020KeiroBlock> rng020kakuninKeiro) {
        rng020kakuninKeiro__ = rng020kakuninKeiro;
    }
    /**
     * <p>rng020kakuninSvKeiro を取得します。
     * @return rng020kakuninSvKeiro
     */
    public Map<Integer, Rng020KeiroBlock> getRng020kakuninSvKeiroMap() {
        return rng020kakuninSvKeiro__;
    }
    /**
     * <p>rng020kakuninSvKeiro をセットします。
     * @param rng020kakuninSvKeiro rng020kakuninSvKeiro
     */
    public void setRng020kakuninSvKeiroMap(Map<Integer, Rng020KeiroBlock> rng020kakuninSvKeiro) {
        rng020kakuninSvKeiro__ = rng020kakuninSvKeiro;
    }
    /**
     * <p>rng020keiro を取得します。
     * @param sortNo 表示順
     * @return rng020keiro
     */
    public Rng020KeiroBlock getRng020keiro(String sortNo) {
        Integer sort = Integer.valueOf(sortNo);
        if (rng020keiro__.containsKey(sort)) {
            return rng020keiro__.get(sort);
        }
        Rng020KeiroBlock ret = new Rng020KeiroBlock();
        rng020keiro__.put(sort, ret);
        return ret;
    }
    /**
     * <p>rng020keiro をセットします。
     * @param rng020keiro rng020keiro
     * @return 追加したキー
     */
    public int putRng020keiro(Rng020KeiroBlock rng020keiro) {
        int max = 0;
        if (rng020keiro__.size() > 0) {
            //最終要素のキー値を取得する
            max = new ArrayList<Integer>(rng020keiro__.keySet()).get(rng020keiro__.size() - 1);
            max++;
        }
        setRng020keiro(max, rng020keiro);
        return max;

    }
    /**
     * <p>rng020keiro をセットします。
     * @param sort 表示順
     * @param rng020keiro rng020keiro
     */
    public void setRng020keiro(Integer sort, Rng020KeiroBlock rng020keiro) {
        rng020keiro__.put(sort, rng020keiro);
    }

    /**
     * <p>rng020kakuninKeiro を取得します。
     * @param sortNo 表示順
     * @return rng020kakuninKeiro
     */
    public Rng020KeiroBlock getRng020kakuninKeiro(String sortNo) {
        Integer sort = Integer.valueOf(sortNo);
        if (rng020kakuninKeiro__.containsKey(sort)) {
            return rng020kakuninKeiro__.get(sort);
        }
        Rng020KeiroBlock ret = new Rng020KeiroBlock();
        rng020kakuninKeiro__.put(sort, ret);
        return ret;
    }

    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param sort 表示順
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setRng020kakuninKeiro(Integer sort, Rng020KeiroBlock rng020kakuninKeiro) {
        rng020kakuninKeiro__.put(sort, rng020kakuninKeiro);
    }
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     * @return 追加したキー
     */
    public int putRng020kakuninKeiro(Rng020KeiroBlock rng020kakuninKeiro) {
        int max = 0;
        if (rng020kakuninKeiro__.size() > 0) {
            //最終要素のキー値を取得する
            max = new ArrayList<Integer>(rng020kakuninKeiro__.keySet())
                    .get(rng020kakuninKeiro__.size() - 1);
            max++;
        }
        setRng020kakuninKeiro(max, rng020kakuninKeiro);
        return max;
    }

    /**
     * <p>rng020input を取得します。
     * @return rng020input
     */
    public FormInputBuilder getRng020input() {
        return rng020input__;
    }
    /**
     * <p>rng020input をセットします。
     * @param rng020input rng020input
     */
    public void setRng020input(FormInputBuilder rng020input) {
        rng020input__ = rng020input;
    }
    /**
     * <p>idPrefManualEditable を取得します。
     * @return idPrefManualEditable
     */
    public int getIdPrefManualEditable() {
        return idPrefManualEditable__;
    }
    /**
     * <p>idPrefManualEditable をセットします。
     * @param idPrefManualEditable idPrefManualEditable
     */
    public void setIdPrefManualEditable(int idPrefManualEditable) {
        idPrefManualEditable__ = idPrefManualEditable;
    }
    /**
     * <p>keiroAutoChanged を取得します。
     * @return keiroAutoChanged
     */
    public int getKeiroAutoChanged() {
        return keiroAutoChanged__;
    }
    /**
     * <p>keiroAutoChanged をセットします。
     * @param keiroAutoChanged keiroAutoChanged
     */
    public void setKeiroAutoChanged(int keiroAutoChanged) {
        keiroAutoChanged__ = keiroAutoChanged;
    }
    /**
     * <p>rng060params を取得します。
     * @return rng060params
     */
    public Rng060PersonalParamImpl getRng060params() {
        return rng060params__;
    }
    /**
     * <p>rng060params をセットします。
     * @param rng060params rng060params
     */
    public void setRng060params(Rng060PersonalParamImpl rng060params) {
        rng060params__ = rng060params;
    }
    @Override
    public String getRng060SortRadio() {
        return rng060params__.getRng060SortRadio();
    }
    @Override
    public void setRng060SortRadio(String rng060SortRadio) {
        rng060params__.setRng060SortRadio(rng060SortRadio);
    }
    @Override
    public String getRng060SortRadioPrivate() {
        return rng060params__.getRng060SortRadioPrivate();
    }
    @Override
    public void setRng060SortRadioPrivate(String rng060SortRadioPrivate) {
        rng060params__.setRng060SortRadioPrivate(rng060SortRadioPrivate);
    }
    @Override
    public int getRng060TemplateMode() {
        return rng060params__.getRng060TemplateMode();
    }
    @Override
    public void setRng060TemplateMode(int rng060TemplateMode) {
        rng060params__.setRng060TemplateMode(rng060TemplateMode);
    }
    @Override
    public int getRng060SelectCat() {
        return rng060params__.getRng060SelectCat();
    }
    @Override
    public void setRng060SelectCat(int rng060SelectCat) {
        rng060params__.setRng060SelectCat(rng060SelectCat);
    }
    @Override
    public int getRng060SelectCatUsr() {
        return rng060params__.getRng060SelectCatUsr();
    }
    @Override
    public void setRng060SelectCatUsr(int rng060SelectCatUsr) {
        rng060params__.setRng060SelectCatUsr(rng060SelectCatUsr);
    }
    /**
     * <p>rng020prevForward を取得します。
     * @return rng020prevForward
     */
    public String getRng020prevForward() {
        return rng020prevForward__;
    }
    /**
     * <p>rng020prevForward をセットします。
     * @param rng020prevForward rng020prevForward
     */
    public void setRng020prevForward(String rng020prevForward) {
        rng020prevForward__ = rng020prevForward;
    }
    /**
     * <p>loadRctSid を取得します。
     * @return loadRctSid
     */
    public int getLoadRctSid() {
        return loadRctSid__;
    }
    /**
     * <p>loadRctSid をセットします。
     * @param loadRctSid loadRctSid
     */
    public void setLoadRctSid(int loadRctSid) {
        loadRctSid__ = loadRctSid;
    }
    /**
     * <p>scrollY を取得します。
     * @return scrollY
     */
    public int getScrollY() {
        return scrollY__;
    }
    /**
     * <p>scrollY をセットします。
     * @param scrollY scrollY
     */
    public void setScrollY(int scrollY) {
        scrollY__ = scrollY;
    }
    /**
     * <p>idUseFlg を取得します。
     * @return idUseFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#idUseFlg__
     */
    public int getIdUseFlg() {
        return idUseFlg__;
    }
    /**
     * <p>idUseFlg をセットします。
     * @param idUseFlg idUseFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#idUseFlg__
     */
    public void setIdUseFlg(int idUseFlg) {
        idUseFlg__ = idUseFlg;
    }
    /**
     * <p>rtpVerUpdated を取得します。
     * @return rtpVerUpdated
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rtpVerUpdated__
     */
    public int getRtpVerUpdated() {
        return rtpVerUpdated__;
    }
    /**
     * <p>rtpVerUpdated をセットします。
     * @param rtpVerUpdated rtpVerUpdated
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rtpVerUpdated__
     */
    public void setRtpVerUpdated(int rtpVerUpdated) {
        rtpVerUpdated__ = rtpVerUpdated;
    }
    /**
     * <p>kakuninKeiroDspFlg を取得します。
     * @return kakuninKeiroDspFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#kakuninKeiroDspFlg__
     */
    public int getKakuninKeiroDspFlg() {
        return kakuninKeiroDspFlg__;
    }
    /**
     * <p>kakuninKeiroDspFlg をセットします。
     * @param kakuninKeiroDspFlg kakuninKeiroDspFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#kakuninKeiroDspFlg__
     */
    public void setKakuninKeiroDspFlg(int kakuninKeiroDspFlg) {
        kakuninKeiroDspFlg__ = kakuninKeiroDspFlg;
    }


    /**
     * <p>rng020useCopyKeiro を取得します。
     * @return rng020useCopyKeiro
     */
    public boolean isRng020useCopyKeiro() {
        return rng020useCopyKeiro__;
    }
    /**
     * <p>rng020useCopyKeiro をセットします。
     * @param rng020useCopyKeiro rng020useCopyKeiro
     */
    public void setRng020useCopyKeiro(boolean rng020useCopyKeiro) {
        rng020useCopyKeiro__ = rng020useCopyKeiro;
    }

    /**
     * <p>rng020rtpType を取得します。
     * @return rng020rtpType
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020rtpType__
     */
    public int getRng020rtpType() {
        return rng020rtpType__;
    }
    /**
     * <p>rng020rtpType をセットします。
     * @param rng020rtpType rng020rtpType
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020rtpType__
     */
    public void setRng020rtpType(int rng020rtpType) {
        rng020rtpType__ = rng020rtpType;
    }
    /**
     * <p>rng020ButtonDsp を取得します。
     * @return rng020ButtonDsp
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ButtonDsp__
     */
    public boolean isRng020ButtonDsp() {
        return rng020ButtonDsp__;
    }
    /**
     * <p>rng020ButtonDsp をセットします。
     * @param rng020ButtonDsp rng020ButtonDsp
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ButtonDsp__
     */
    public void setRng020ButtonDsp(boolean rng020ButtonDsp) {
        rng020ButtonDsp__ = rng020ButtonDsp;
    }
    /**
     * <p>useTemplateFlg を取得します。
     * @return useTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useTemplateFlg__
     */
    public int getUseTemplateFlg() {
        return useTemplateFlg__;
    }
    /**
     * <p>useTemplateFlg をセットします。
     * @param useTemplateFlg useTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useTemplateFlg__
     */
    public void setUseTemplateFlg(int useTemplateFlg) {
        useTemplateFlg__ = useTemplateFlg;
    }
    /**
     * <p>useKeiroTemplateFlg を取得します。
     * @return useKeiroTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useKeiroTemplateFlg__
     */
    public int getUseKeiroTemplateFlg() {
        return useKeiroTemplateFlg__;
    }
    /**
     * <p>useKeiroTemplateFlg をセットします。
     * @param useKeiroTemplateFlg useKeiroTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useKeiroTemplateFlg__
     */
    public void setUseKeiroTemplateFlg(int useKeiroTemplateFlg) {
        useKeiroTemplateFlg__ = useKeiroTemplateFlg;
    }
    /**
     * <p>rng020UseApiConnect を取得します。
     * @return rng020UseApiConnect
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020UseApiConnect__
     */
    public int getRng020UseApiConnect() {
        return rng020UseApiConnect__;
    }
    /**
     * <p>rng020UseApiConnect をセットします。
     * @param rng020UseApiConnect rng020UseApiConnect
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020UseApiConnect__
     */
    public void setRng020UseApiConnect(int rng020UseApiConnect) {
        rng020UseApiConnect__ = rng020UseApiConnect;
    }
    /**
     * <p>rng020ApiComment を取得します。
     * @return rng020ApiComment
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ApiComment__
     */
    public String getRng020ApiComment() {
        return rng020ApiComment__;
    }
    /**
     * <p>rng020ApiComment をセットします。
     * @param rng020ApiComment rng020ApiComment
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ApiComment__
     */
    public void setRng020ApiComment(String rng020ApiComment) {
        rng020ApiComment__ = rng020ApiComment;
    }


}
