package jp.groupsession.v2.rng.rng020;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
/**
 *
 * <br>[機  能] Rng020登録画面機能で使用するパラメータ宣言
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IRng020PeronalParam {
    /**
     * <p>rngSid を取得します。
     * @return rngSid
     */
    int getRngSid();
    /**
     * <p>rngSid を設定します。
     * @param sid 稟議Sid
     */
    void setRngSid(int sid);
    /**
     * <p>rng020createDate を取得します。
     * @return rng020createDate
     */
    String getRng020createDate();

    /**
     * <p>rng020createDate をセットします。
     * @param rng020createDate rng020createDate
     */
    void setRng020createDate(String rng020createDate);


    /**
     * <p>rng020Title を取得します。
     * @return rng020Title
     */
    String getRng020Title();

    /**
     * <p>rng020Title をセットします。
     * @param rng020Title rng020Title
     */
    void setRng020Title(String rng020Title);

    /**
     * <p>rng020requestUser を取得します。
     * @return rng020requestUser
     */
    String getRng020requestUser();

    /**
     * <p>rng020requestUser をセットします。
     * @param rng020requestUser rng020requestUser
     */
    void setRng020requestUser(String rng020requestUser);

    /**
     * <p>rng020copyApply を取得します。
     * @return rng020copyApply
     */
    boolean isRng020copyApply();

    /**
     * <p>rng020copyApply をセットします。
     * @param rng020copyApply rng020copyApply
     */
    void setRng020copyApply(boolean rng020copyApply);

    /**
     * @return rng020ScrollFlg
     */
    String getRng020ScrollFlg();

    /**
     * @param rng020ScrollFlg 設定する rng020ScrollFlg
     */
    void setRng020ScrollFlg(String rng020ScrollFlg);

    /**
     * <p>rng020requestUserId を取得します。
     * @return rng020requestUserId
     */
    String getRng020requestUserId();

    /**
     * <p>rng020requestUserId をセットします。
     * @param rng020requestUserId rng020requestUserId
     */
    void setRng020requestUserId(String rng020requestUserId);

    /**
     * <p>rng020selectedRequestUserId を取得します。
     * @return rng020selectedRequestUserId
     */
    String getRng020selectedRequestUserId();

    /**
     * <p>rng020selectedRequestUserId をセットします。
     * @param rng020selectedRequestUserId selectedRng020requestUserId
     */
    void setRng020selectedRequestUserId(String rng020selectedRequestUserId);

    /**
     * <p>rng020selectedRequestUserList を取得します。
     * @return rng020selectedRequestUserList
     */
    List<LabelValueBean> getRng020selectedRequestUserList();

    /**
     * <p>rng020selectedRequestUserList をセットします。
     * @param rng020selectedRequestUserList rng020selectedRequestUserList
     */
    void setRng020selectedRequestUserList(List<LabelValueBean> rng020selectedRequestUserList);

    /**
     * <p>rng020selectedRequestGroupId を取得します。
     * @return rng020selectedRequestGroupId
     */
    String getRng020selectedRequestGroupId();

    /**
     * <p>rng020selectedRequestGroupId をセットします。
     * @param rng020selectedRequestGroupId rng020selectedRequestGroupId
     */
    void setRng020selectedRequestGroupId(String rng020selectedRequestGroupId);

    /**
     * <p>rng020requestGroupList を取得します。
     * @return rng020requestGroupList
     */
    List<LabelValueBean> getRng020selectedRequestGroupList();

    /**
     * <p>rng020selectedRequestGroupList をセットします。
     * @param rng020selectedRequestGroupList rng020selectedRequestGroupList
     */
    void setRng020selectedRequestGroupList(List<LabelValueBean> rng020selectedRequestGroupList);

    /**
     * <p>rng020rtpSid を取得します。
     * @return rng020rtpSid
     */
    int getRng020rtpSid();

    /**
     * <p>rng020rtpSid をセットします。
     * @param rng020rtpSid rng020rtpSid
     */
    void setRng020rtpSid(int rng020rtpSid);

    /**
     * <p>rng020rtpVer を取得します。
     * @return rng020rtpVer
     */
    int getRng020rtpVer();

    /**
     * <p>rng020rtpVer をセットします。
     * @param rng020rtpVer rng020rtpVer
     */
    void setRng020rtpVer(int rng020rtpVer);

    /**
     * <p>rng020rtpKeiroVersion を取得します。
     * @return rng020rtpKeiroVersion
     */
    int getRng020rtpKeiroVersion();

    /**
     * <p>rng020rtpKeiroVersion をセットします。
     * @param rng020rtpKeiroVersion rng020rtpKeiroVersion
     */
    void setRng020rtpKeiroVersion(int rng020rtpKeiroVersion);

    /**
     * <p>rng020inputLoad を取得します。
     * @return rng020inputLoad
     */
    Map<FormAccesser, List<String>> getRng020inputLoad();

    /**
     * <p>rng020inputLoad をセットします。
     * @param rng020inputLoad rng020inputLoad
     */
    void setRng020inputLoad(Map<FormAccesser, List<String>> rng020inputLoad);

    /**
     * <p>rng020IdPrefManual を取得します。
     * @return rng020IdPrefManual
     */
    int getRng020IdPrefManual();

    /**
     * <p>rng020IdPrefManual をセットします。
     * @param rng020IdPrefManual rng020IdPrefManual
     */
    void setRng020IdPrefManual(int rng020IdPrefManual);

    /**
     * <p>rng020ID を取得します。
     * @return rng020ID
     */
    String getRng020ID();

    /**
     * <p>rng020ID をセットします。
     * @param rng020id rng020ID
     */
    void setRng020ID(String rng020id);

    /**
     * <p>rng020PlanID を取得します。
     * @return rng020PlanID
     */
    String getRng020PlanID();

    /**
     * <p>rng020PlanID をセットします。
     * @param rng020PlanID rng020PlanID
     */
    void setRng020PlanID(String rng020PlanID);

    /**
     * <p>rng020IdTitle を取得します。
     * @return rng020IdTitle
     */
    String getRng020IdTitle();

    /**
     * <p>rng020IdTitle をセットします。
     * @param rng020IdTitle rng020IdTitle
     */
    void setRng020IdTitle(String rng020IdTitle);

    /**
     * <p>rng020keiro を取得します。
     * @return rng020keiro
     */
    Map<Integer, Rng020KeiroBlock> getRng020keiroMap();

    /**
     * <p>rng020keiro をセットします。
     * @param rng020keiro rng020keiro
     */
    void setRng020keiroMap(Map<Integer, Rng020KeiroBlock> rng020keiro);

    /**
     * <p>rng020kakuninKeiro を取得します。
     * @return rng020kakuninKeiro
     */
    Map<Integer, Rng020KeiroBlock> getRng020kakuninKeiroMap();

    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    void setRng020kakuninKeiroMap(
            Map<Integer, Rng020KeiroBlock> rng020kakuninKeiro);

    /**
     * <p>rng020kakuninSvKeiro を取得します。
     * @return rng020kakuninSvKeiro
     */
    Map<Integer, Rng020KeiroBlock> getRng020kakuninSvKeiroMap();

    /**
     * <p>rng020kakuninSvKeiro をセットします。
     * @param rng020kakuninSvKeiro rng020kakuninSvKeiro
     */
    void setRng020kakuninSvKeiroMap(
            Map<Integer, Rng020KeiroBlock> rng020kakuninSvKeiro);

    /**
     * <p>rng020keiro を取得します。
     * @param sortNo 表示順
     * @return rng020keiro
     */
    Rng020KeiroBlock getRng020keiro(String sortNo);

    /**
     * <p>rng020keiro をセットします。
     * @param rng020keiro rng020keiro
     * @return 追加したキー
     */
    int putRng020keiro(Rng020KeiroBlock rng020keiro);

    /**
     * <p>rng020keiro をセットします。
     * @param sort 表示順
     * @param rng020keiro rng020keiro
     */
    void setRng020keiro(Integer sort, Rng020KeiroBlock rng020keiro);

    /**
     * <p>rng020kakuninKeiro を取得します。
     * @param sortNo 表示順
     * @return rng020kakuninKeiro
     */
    Rng020KeiroBlock getRng020kakuninKeiro(String sortNo);


    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param sort 表示順
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    void setRng020kakuninKeiro(Integer sort,
            Rng020KeiroBlock rng020kakuninKeiro);

    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     * @return 追加したキー
     */
    int putRng020kakuninKeiro(Rng020KeiroBlock rng020kakuninKeiro);

    /**
     * <p>rng020input を取得します。
     * @return rng020input
     */
    FormInputBuilder getRng020input();

    /**
     * <p>rng020input をセットします。
     * @param rng020input rng020input
     */
    void setRng020input(FormInputBuilder rng020input);

    /**
     * <p>idPrefManualEditable を取得します。
     * @return idPrefManualEditable
     */
    int getIdPrefManualEditable();

    /**
     * <p>idPrefManualEditable をセットします。
     * @param idPrefManualEditable idPrefManualEditable
     */
    void setIdPrefManualEditable(int idPrefManualEditable);

    /**
     * <p>keiroAutoChanged を取得します。
     * @return keiroAutoChanged
     */
    int getKeiroAutoChanged();

    /**
     * <p>keiroAutoChanged をセットします。
     * @param keiroAutoChanged keiroAutoChanged
     */
    void setKeiroAutoChanged(int keiroAutoChanged);

    /**
     * <p>loadRctSid を取得します。
     * @return loadRctSid
     */
    int getLoadRctSid();

    /**
     * <p>loadRctSid をセットします。
     * @param loadRctSid loadRctSid
     */
    void setLoadRctSid(int loadRctSid);

    /**
     * <p>scrollY を取得します。
     * @return scrollY
     */
    int getScrollY();

    /**
     * <p>scrollY をセットします。
     * @param scrollY scrollY
     */
    void setScrollY(int scrollY);

    /**
     * <p>idUseFlg を取得します。
     * @return idUseFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#idUseFlg__
     */
    int getIdUseFlg();

    /**
     * <p>idUseFlg をセットします。
     * @param idUseFlg idUseFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#idUseFlg__
     */
    void setIdUseFlg(int idUseFlg);

    /**
     * <p>rtpVerUpdated を取得します。
     * @return rtpVerUpdated
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rtpVerUpdated__
     */
    int getRtpVerUpdated();

    /**
     * <p>rtpVerUpdated をセットします。
     * @param rtpVerUpdated rtpVerUpdated
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rtpVerUpdated__
     */
    void setRtpVerUpdated(int rtpVerUpdated);

    /**
     * <p>kakuninKeiroDspFlg を取得します。
     * @return kakuninKeiroDspFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#kakuninKeiroDspFlg__
     */
    int getKakuninKeiroDspFlg();

    /**
     * <p>kakuninKeiroDspFlg をセットします。
     * @param kakuninKeiroDspFlg kakuninKeiroDspFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#kakuninKeiroDspFlg__
     */
    void setKakuninKeiroDspFlg(int kakuninKeiroDspFlg);

    /**
     * <p>rng020useCopyKeiro を取得します。
     * @return rng020useCopyKeiro
     */
    boolean isRng020useCopyKeiro();

    /**
     * <p>rng020useCopyKeiro をセットします。
     * @param rng020useCopyKeiro rng020useCopyKeiro
     */
    void setRng020useCopyKeiro(boolean rng020useCopyKeiro);
    /**
     * <p>rngCmdMode を取得します。
     * @return rngCmdMode
     * @see jp.groupsession.v2.rng.RingiParamModel#rngCmdMode__
     */
    int getRngCmdMode();
    /**
     * <p>rngCmdMode をセットします。
     * @param mode rngCmdMode
     * @see jp.groupsession.v2.rng.RingiParamModel#rngCmdMode__
     */
    void setRngCmdMode(int mode);
    /**
     * <p>rngSelectTplSid を取得します。
     * @return rngSelectTplSid
     * @see jp.groupsession.v2.rng.RingiParamModel#rngSelectTplSid__
     */
    int getRngSelectTplSid();
    /**
     * <p>rngSelectTplSid をセットします。
     * @param rngSelectTplSid rngSelectTplSid
     * @see jp.groupsession.v2.rng.RingiParamModel#rngSelectTplSid__
     */
    void setRngSelectTplSid(int rngSelectTplSid);
    /**
     * <p>rng020rtpType を取得します。
     * @return rngSelectTplSid
     * @see jp.groupsession.v2.rng.RingiParamModel#rngSelectTplSid__
     */
    int getRng020rtpType();
    /**
     * <p>rng020rtpType をセットします。
     * @param rtpType rtpType
     * @see jp.groupsession.v2.rng.RingiParamModel#rng020rtpType__
     */
    void setRng020rtpType(int rtpType);

    /**
     * <p>rng020ButtonDsp を取得します。
     * @return rng020ButtonDsp
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ButtonDsp__
     */
    public boolean isRng020ButtonDsp();
    /**
     * <p>rng020ButtonDsp をセットします。
     * @param rng020ButtonDsp rng020ButtonDsp
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ButtonDsp__
     */
    public void setRng020ButtonDsp(boolean rng020ButtonDsp);
    /**
     * <p>useTemplateFlg を取得します。
     * @return useTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useTemplateFlg__
     */
    public int getUseTemplateFlg();
    /**
     * <p>useTemplateFlg をセットします。
     * @param useTemplateFlg useTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useTemplateFlg__
     */
    public void setUseTemplateFlg(int useTemplateFlg);
    /**
     * <p>useKeiroTemplateFlg を取得します。
     * @return useKeiroTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useKeiroTemplateFlg__
     */
    public int getUseKeiroTemplateFlg();
    /**
     * <p>useKeiroTemplateFlg をセットします。
     * @param useKeiroTemplateFlg useKeiroTemplateFlg
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#useKeiroTemplateFlg__
     */
    public void setUseKeiroTemplateFlg(int useKeiroTemplateFlg);
    /**
     * <p>rng020UseApiConnect を取得します。
     * @return rng020UseApiConnect
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020UseApiConnect__
     */
    public int getRng020UseApiConnect();
    /**
     * <p>rng020UseApiConnect をセットします。
     * @param rng020UseApiConnect rng020UseApiConnect
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020UseApiConnect__
     */
    public void setRng020UseApiConnect(int rng020UseApiConnect);
    /**
     * <p>rng020ApiComment を取得します。
     * @return rng020ApiComment
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ApiComment__
     */
    public String getRng020ApiComment();
    /**
     * <p>rng020ApiComment をセットします。
     * @param rng020ApiComment rng020ApiComment
     * @see jp.groupsession.v2.rng.rng020.Rng020ParamModel#rng020ApiComment__
     */
    public void setRng020ApiComment(String rng020ApiComment);

}