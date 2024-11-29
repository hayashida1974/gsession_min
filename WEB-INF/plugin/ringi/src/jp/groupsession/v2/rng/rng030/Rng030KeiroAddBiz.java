package jp.groupsession.v2.rng.rng030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;
import jp.groupsession.v2.rng.rng020.Rng020Keiro;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;
import jp.groupsession.v2.rng.rng110keiro.RngTemplateKeiroSave;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] 経路追加可能経路か判定する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
class Rng030KeiroAddBiz {
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** 経路追加できる経路リスト*/
    private Set<Rng030KeiroParam> addibleRowSet__ = new HashSet<>();
    /** 経路追加対象の経路SIDと稟議テンプレート経路SIDのMAP 登録処理時に使用*/
    private Map<Integer, Rng110KeiroDialogParamModel> addRowTemplateMap__ = new HashMap<>();
    /** 経路テンプレート保管管理オブジェクト 経路追加時の追加経路のテンプレート取得に使用*/
    private RngTemplateKeiroSave rtkSave__;

    /**
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param rtpSid テンプレートSID
     * @param rtpVer テンプレートバージョン
     * @param fb フォーム管理クラス
     * @throws SQLException SQL実行時例外
     */
    public Rng030KeiroAddBiz(Connection con, RequestModel reqMdl,
            int rtpSid, int rtpVer,
            FormInputBuilder fb) throws SQLException {
        con__ = con;
        reqMdl__ = reqMdl;
        if (rtpSid == 0) {
            return;
        }
        rtkSave__ = RngTemplateKeiroSave.createInstanceForConvert(reqMdl__, con__);
    }

    /**
    *
    * <br>[機  能] 経路モデルのステップ追加可能フラグを設定する
    * <br>[解  説] 進行順にならんでいる経路ステップによる繰り返し処理の中で使用する
    * <br>[備  考] 呼び出されるたびに比較する経路テンプレート参照は先に進む
    * @param keiroMdl 設定対象経路モデル
    * @param singedFlg 0:RNG_RNCSTATUS_LESS 審議前 1:RNG_RNCSTATUS_CONFIRM 審議中
     * @throws SQLException SQL実行時例外
    */
   public void prefStepAddibleFlag(Rng030KeiroParam keiroMdl, int singedFlg) throws SQLException {

       if (singedFlg > RngConst.RNG_RNCSTATUS_CONFIRM) {
           return;
       }
       if (keiroMdl.getAddibleRtkSid() > 0) {
           RngTemplateKeiroDao rtkDao = new RngTemplateKeiroDao(con__);
           RngTemplateKeiroModel rtkMdl = rtkDao.select(keiroMdl.getAddibleRtkSid());
           Rng110KeiroDialogParamModel rtkDlgMdl;
           if (rtkMdl == null) {
               return;
           }
           keiroMdl.setKeiroAddible(RngConst.RNG_ABLE_ADDKEIRO);
           addibleRowSet__.add(keiroMdl);
           rtkDlgMdl = rtkSave__.convertParamModel(rtkMdl);
           addRowTemplateMap__.put(keiroMdl.getKeiroStepSid(), rtkDlgMdl);
       }
   }
   /**
    * <p>addibleRowCnt を取得します。
    * @return addibleRowCnt
    */
   public int getAddibleRowCnt() {
       return addibleRowSet__.size();
   }
   /**
    *
    * <br>[機  能] 経路追加要素の表示設定
    * <br>[解  説]
    * <br>[備  考]
    * @param inputMap 入力された経路追加要素パラメータMap
    * @throws SQLException SQL実行時例外
    */
   public void dspInitAddibleRow(
           Map<Integer, Rng020KeiroBlock> inputMap) throws SQLException {


       int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
       GsMessage gsMsg = new GsMessage(reqMdl__);
       UserGroupSelectBiz usrgrpBiz = new UserGroupSelectBiz();
       GroupBiz grpBiz = new GroupBiz();
       String defGrpSid = String.valueOf(
               grpBiz.getDefaultGroupSid(sessionUsrSid,
                       con__));
       List<UsrLabelValueBean> grplist =
               usrgrpBiz.getGroupLabel(reqMdl__, con__);

       //所属グループリスト取得
       UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con__);
       List<GroupModel> bossTargetList = gpDao.selectGroupNmListOrderbyClass(
               reqMdl__.getSmodel().getUsrsid());

       PosBiz posBiz = new PosBiz();
       //役職選択
       List<LabelValueBean> allPosLabelList = posBiz.getPosLabelList(con__, false);
       //役職のないユーザ指定用に「役職なし」を追加
       allPosLabelList.add(0, new LabelValueBean(gsMsg.getMessage("cmn.nopost"), "0"));
       Map<Integer, LabelValueBean> posLabelMap = new HashMap<>();
       for (LabelValueBean label : allPosLabelList) {
           posLabelMap.put(Integer.valueOf(label.getValue()), label);
       }

       for (Rng030KeiroParam keiroMdl : addibleRowSet__) {
           //画面パラメータで追加された経路を設定
           Rng020KeiroBlock block;
           if (inputMap.containsKey(keiroMdl.getKeiroStepSid())) {
               block = inputMap.get(keiroMdl.getKeiroStepSid());
               keiroMdl.setAddBlock(block);

           } else {
               block = new Rng020KeiroBlock();
               keiroMdl.setAddBlock(block);
           }
           block.getPref().setOwn(RngConst.RNG_OWNSINGI_YES);
           __keiroMapDspInit(block, defGrpSid, grplist, bossTargetList, posLabelMap);
       }
   }
   /**
   *
   * <br>[機  能] 経路描画設定
   * <br>[解  説]
   * <br>[備  考]
   * @param block 経路要素ブロック
   * @param defGrpSid デフォルトグループSID
   * @param grplist グループ一覧
   * @param belongGrpList 所属グループ一覧
   * @param posLabelMap 役職ラベル一覧
   * @throws SQLException SQL実行時例外
   */
  private void __keiroMapDspInit(
          Rng020KeiroBlock block,
          String defGrpSid,
          List<UsrLabelValueBean> grplist,
          List<GroupModel> belongGrpList,
          Map<Integer, LabelValueBean> posLabelMap
          ) throws SQLException {

      if (!ArrayUtils.isEmpty(block.getDeleteRow())) {
          //経路の削除
          Map<Integer, Rng020Keiro> keiroMap = new HashMap<Integer, Rng020Keiro>();
          for (String rowNo : block.getDeleteRow()) {
              block.getKeiroMap().remove(Integer.valueOf(rowNo));
          }
          //削除した経路を詰める
          Collection<Rng020Keiro> keiroSet = block.getKeiroMap().values();
          int rowNo = 0;
          for (Rng020Keiro keiro : keiroSet) {
              keiroMap.put(rowNo, keiro);
              rowNo++;
          }
          block.setKeiroMap(keiroMap);
      }
      if (!ArrayUtils.isEmpty(block.getAddRow())) {
          //経路の追加
          for (Iterator<String> it = Arrays.asList(block.getAddRow()).iterator();
                  it.hasNext();) {
              Rng020Keiro keiro = new Rng020Keiro();
              block.setKeiro(block.getKeiroMap().size(), keiro);
              it.next();
          }
      }
      Collection<Rng020Keiro> keiroSet = block.getKeiroMap().values();
      for (Rng020Keiro keiro : keiroSet) {
          keiro.dspInit(con__, reqMdl__,
                  defGrpSid, grplist, belongGrpList,
                  posLabelMap, block, false, 0);
      }

  }
  /**
   * <p>addRowTemplateMap を取得します。
   * @return addRowTemplateMap
   */
  public Map<Integer, Rng110KeiroDialogParamModel> getAddRowTemplateMap() {
      return addRowTemplateMap__;
  }


}