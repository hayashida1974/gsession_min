package jp.groupsession.v2.rng.rng110keiro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
*
* <br>[機  能] 稟議経路詳細設定ダイアログ用 ビジネスロジック
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class Rng110KeiroDialogBiz {

    /**  コネクション */
    Connection con__;
    /**  リクエストモデル */
    RequestModel reqMdl__;


    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public Rng110KeiroDialogBiz(Connection con, RequestModel reqMdl) {
        super();
        con__ = con;
        reqMdl__ = reqMdl;
    }


    /**
     *
     * <br>[機  能] 表示用設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォームモデル
     * @throws SQLException SQL実行時例外
     */
    public void setDispData(IRng110KeiroDialogParam paramMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz grpBiz = new GroupBiz();
        PosBiz posBiz = new PosBiz();
        //経路使用条件設定---------------------
        //グループ選択
        List<LabelValueBean> allGrpLabelList = grpBiz.getGroupLabelList(con__, false, gsMsg);
        //役職選択
        List<LabelValueBean> allPosLabelList = posBiz.getPosLabelList(con__, false);
        //役職のないユーザ指定用に「役職なし」を追加
        allPosLabelList.add(0, new LabelValueBean(gsMsg.getMessage("cmn.nopost"), "0"));

        KeiroInCondition.DspInitter dspInitter =
                new KeiroInCondition.DspInitter(allGrpLabelList, allPosLabelList, gsMsg);
        Map<String, KeiroInCondition> inCondMap = paramMdl.getInCondMap();
        //テンプレート用モデルを初期化
        inCondMap.put("template", new KeiroInCondition());
        //選択済み要素
        for (Entry<String, KeiroInCondition> entry : inCondMap.entrySet()) {
            KeiroInCondition inCond = entry.getValue();
            dspInitter.dspInit(inCond);
            inCond.getSelGrp().setParamName("inCond(" + entry.getKey() + ").selGrp");
            inCond.getSelPos().setParamName("inCond(" + entry.getKey() + ").selPos");

        }
        //___
        //指定対象 ユーザ・グループ
        UserGroupSelectModel target = paramMdl.getUsrgrouptarget();
        target.init(con__, reqMdl__, "", null, null);
        //指定対象 役職
        TreeMap<String, TargetPosSel> targetPos = paramMdl.getTargetposMap();

        if (!targetPos.containsKey("template")) {
            targetPos.put("template", new TargetPosSel());
        }
        if (targetPos.size() <= 1) {
            //雛形以外の要素がない場合
            targetPos.put("0", new TargetPosSel());
        }

        //役職選択のグループ指定用に「指定なし」を持つグループリストを生成
        List<LabelValueBean> allPosSelGrpLabelList = new ArrayList<LabelValueBean>(allGrpLabelList);
        allPosSelGrpLabelList.add(0,
                new LabelValueBean(gsMsg.getMessage("cmn.specified.no"), "-1"));

        for (TargetPosSel targetPosSel__ : targetPos.values()) {
            targetPosSel__.getGrpSel().setDspSelectData(allPosSelGrpLabelList);
            targetPosSel__.getPosSel().setDspSelectData(allPosLabelList);
        }
        //選択 ユーザ
        UserGroupSelectModel select = paramMdl.getUsrgroupselect();
        select.init(con__, reqMdl__, "", null, null);
        //選択 グループ
        GroupComboModel groupSel = paramMdl.getGroupSel();
        groupSel.setMultiFlg(UserGroupSelectModel.FLG_MULTI_ON);
        try {
            paramMdl.getGroupSel().dspInit(reqMdl__, con__);
        } catch (IOToolsException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * <br>[機  能] ユーザ、グループ直接指定の要素を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param user ユーザラベルモデル
     * @return 要素モデル
     */
    public Rng110KeiroDialogParamModel getSinpleDragDataUser(UsrLabelValueBean user) {
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        ret.setKeiroKbn(EnumKeiroKbn.USERTARGET.getValue());
        ret.setKeiroName(user.getLabel());
        ret.getUsrgrouptarget().setSelected("target", user.getValue());
        ArrayList<UsrLabelValueBean> selList = new ArrayList<UsrLabelValueBean>();
        selList.add(user);
        ret.getUsrgrouptarget().setSelectedList("target", selList);
        if (user.getValue().startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
            ret.setDragContentKbn(Rng110KeiroDialogParamModel.DRAG_CONTENT_KBN_GROUP);
        } else {
            ret.setDragContentKbn(Rng110KeiroDialogParamModel.DRAG_CONTENT_KBN_USER);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 役職直接指定の要素を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pos 役職ラベルモデル
     * @return 要素モデル
     */
    public Rng110KeiroDialogParamModel getSinpleDragDataPos(LabelValueBean pos) {
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        ret.setKeiroKbn(EnumKeiroKbn.POSTARGET.getValue());
        ret.setKeiroName(pos.getLabel());
        TargetPosSel posSel = new TargetPosSel();
        posSel.getPosSel().setSelected(pos.getValue());
        ret.setTargetpos("0", posSel);
        return ret;
    }
    /**
    *
    * <br>[機  能] 汎用の経路ドラッグ要素を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param kbn 経路区分ラベルモデル
    * @return 要素モデル
    */
   public Rng110KeiroDialogParamModel getBasicDragData(LabelValueBean kbn) {
       Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
       ret.setKeiroKbn(Integer.valueOf(kbn.getValue()));
       ret.setKeiroName(kbn.getLabel());
       return ret;
   }


}
