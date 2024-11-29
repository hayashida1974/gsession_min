package jp.groupsession.v2.rng.rng110keiro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] 経路テンプレート設定要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng110Keiro {
    /**経路*/
    private TreeMap<Integer, Rng110KeiroDialogParamModel> keiro__ =
            new TreeMap<Integer, Rng110KeiroDialogParamModel>();
    /**最終確認経路*/
    private TreeMap<Integer, Rng110KeiroDialogParamModel> finalKeiro__ =
            new TreeMap<Integer, Rng110KeiroDialogParamModel>();
    /** ユーザグループ直接設定用ドラッグ要素 グループ選択*/
    private GroupComboModel group__ = new GroupComboModel();
    /** ユーザグループ直接設定用ドラッグ要素 選択要素*/
    private List<Rng110KeiroDialogParamModel> simpleDrags__;

    /** 汎用設定用ドラッグ要素*/
    private List<Rng110KeiroDialogParamModel> basicDrags__;

    /**グループコンボ選択 */
    public static final int GROUP_POSLIST = -10;
    /**
     * <p>rng110keiro を取得します。
     * @return rng110keiro
     */
    public TreeMap<Integer, Rng110KeiroDialogParamModel> getKeiroMap() {
        return keiro__;
    }

    /**
     * <p>rng110keiro をセットします。
     * @param rng110keiro rng110keiro
     */
    public void setKeiroMap(TreeMap<Integer, Rng110KeiroDialogParamModel> rng110keiro) {
        keiro__ = rng110keiro;
    }

    /**
     * <p>rng110finalKeiro を取得します。
     * @return rng110finalKeiro
     */
    public TreeMap<Integer, Rng110KeiroDialogParamModel> getFinalKeiroMap() {
        return finalKeiro__;
    }
    /**
     * <p>rng110keiro を取得します。
     * @param key key
     * @return rng110keiro
     */
    public  IRng110KeiroDialogParam getKeiro(Integer key) {
        if (keiro__.containsKey(key)) {
            return keiro__.get(key);
        }
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        keiro__.put(key, ret);
        return ret;
    }
    /**
     * <p>rng110keiro をセットします
     * @param keyStr key
     * @param rng110keiro rng110keiro
     */
    public void setKeiro(String keyStr,
            Rng110KeiroDialogParamModel rng110keiro) {
        Integer key = Integer.valueOf(keyStr);
        keiro__.put(key, rng110keiro);
    }
    /**
     * <p>rng110keiro を取得します。
     * @param keyStr key
     * @return rng110keiro
     */
    public  IRng110KeiroDialogParam getKeiro(String keyStr) {
        Integer key = Integer.valueOf(keyStr);
        if (keiro__.containsKey(key)) {
            return keiro__.get(key);
        }
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        keiro__.put(key, ret);
        return ret;
    }
    /**
     * <p>rng110keiro をセットします
     * @param key key
     * @param rng110keiro rng110keiro
     */
    public void setKeiro(Integer key,
            Rng110KeiroDialogParamModel rng110keiro) {
        keiro__.put(key, rng110keiro);
    }
    /**
     * <p>rng110finalKeiro をセットします。
     * @param rng110finalKeiro rng110finalKeiro
     */
    public void setFinalKeiroMap(
            TreeMap<Integer, Rng110KeiroDialogParamModel> rng110finalKeiro) {
        finalKeiro__ = rng110finalKeiro;
    }
    /**
     * <p>rng110finalKeiro を取得します。
     * @param key key
     * @return rng110finalKeiro
     */
    public  IRng110KeiroDialogParam getFinalKeiro(Integer key) {
        if (finalKeiro__.containsKey(key)) {
            return finalKeiro__.get(key);
        }
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        finalKeiro__.put(key, ret);

        return ret;
    }
    /**
     * <p>rng110finalKeiro をセットします
     * @param key key
     * @param rng110finalKeiro rng110finalKeiro
     */
    public void setFinalKeiro(Integer key,
            Rng110KeiroDialogParamModel rng110finalKeiro) {
        finalKeiro__.put(key, rng110finalKeiro);
    }
    /**
     * <p>rng110finalKeiro を取得します。
     * @param keyStr key
     * @return rng110finalKeiro
     */
    public  IRng110KeiroDialogParam getFinalKeiro(String keyStr) {
        Integer key = Integer.valueOf(keyStr);
        if (finalKeiro__.containsKey(key)) {
            return finalKeiro__.get(key);
        }
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        finalKeiro__.put(key, ret);

        return ret;
    }
    /**
     * <p>rng110finalKeiro をセットします
     * @param keyStr key
     * @param rng110finalKeiro rng110finalKeiro
     */
    public void setFinalKeiro(String keyStr,
            Rng110KeiroDialogParamModel rng110finalKeiro) {
        Integer key = Integer.valueOf(keyStr);
        finalKeiro__.put(key, rng110finalKeiro);
    }

    /**
     * <p>group を取得します。
     * @return group
     */
    public GroupComboModel getGroup() {
        return group__;
    }

    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(GroupComboModel group) {
        group__ = group;
    }

    /**
     * <p>simpleDrags を取得します。
     * @return simpleDrops
     */
    public List<Rng110KeiroDialogParamModel> getSimpleDrags() {
        return simpleDrags__;
    }

    /**
     * <p>simpleDrags をセットします。
     * @param simpleDrags simpleDrags
     */
    public void setSimpleDrags(List<Rng110KeiroDialogParamModel> simpleDrags) {
        simpleDrags__ = simpleDrags;
    }

    /**
     * <p>basicDrags を取得します。
     * @return basicDrags
     */
    public List<Rng110KeiroDialogParamModel> getBasicDrags() {
        return basicDrags__;
    }

    /**
     * <p>basicDrags をセットします。
     * @param basicDrags basicDrags
     */
    public void setBasicDrags(List<Rng110KeiroDialogParamModel> basicDrags) {
        basicDrags__ = basicDrags;
    }

    /**
     *
     * <br>[機  能] 表示初期化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @exception SQLException SQL実行時例外
     * @throws IOToolsException
     */
    public void dspInit(RequestModel reqMdl, Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        UserGroupSelectBiz biz = new UserGroupSelectBiz();
        //グループコンボ初期化
        ArrayList<UsrLabelValueBean> grplist  = biz.getGroupLabel(reqMdl, con);
        grplist.add(1, new UsrLabelValueBean(gsMsg.getMessage("cmn.post"),
                String.valueOf(GROUP_POSLIST)));
        GroupBiz gbiz = new GroupBiz();
        String defGroupSid = String.valueOf(gbiz.getDefaultGroupSid(sessionUsrSid, con));
        group__.init(grplist, defGroupSid);
        Rng110KeiroDialogBiz keiroBiz = new Rng110KeiroDialogBiz(con, reqMdl);
        //ドラッグ要素リスト初期化
        if (group__.getSelectedSingle().equals(String.valueOf(GROUP_POSLIST))) {
            PosBiz posBiz = new PosBiz();

            simpleDrags__ = new ArrayList<Rng110KeiroDialogParamModel>();

            List<LabelValueBean> posList = posBiz.getPosLabelList(con);
            for (LabelValueBean label : posList) {
                simpleDrags__.add(keiroBiz.getSinpleDragDataPos(label));
            }
        } else {
            List<UsrLabelValueBean> selList = biz.getNonSelectLabel(
                    Integer.valueOf(group__.getSelectedSingle()), null, con);
            simpleDrags__ = new ArrayList<Rng110KeiroDialogParamModel>();
            for (UsrLabelValueBean usr : selList) {
                usr.dspIcon(true, sessionUsrSid);
                simpleDrags__.add(keiroBiz.getSinpleDragDataUser(usr));
            }
        }
        //汎用ドラッグ要素リスト初期化
        EnumUtil<EnumKeiroKbn> enumUtil = new EnumUtil<EnumKeiroKbn>(EnumKeiroKbn.class);
        List<LabelValueBean> kbnList = enumUtil.getSelectList(gsMsg);
        basicDrags__ = new ArrayList<Rng110KeiroDialogParamModel>();
        for (LabelValueBean kbn : kbnList) {
            basicDrags__.add(keiroBiz.getBasicDragData(kbn));
        }
        for (Rng110KeiroDialogParamModel step : keiro__.values()) {
            keiroBiz.setDispData(step);
        }
        for (Rng110KeiroDialogParamModel step : finalKeiro__.values()) {
            keiroBiz.setDispData(step);
        }
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param errors アクションエラー格納先
     * @param rtpSpecVer テンプレート仕様バージョン
     * @param hisuFlg 最低一つでも経路が設定されている必要があるか
     * @return エラー
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            int rtpSpecVer,
            boolean hisuFlg) {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String error = gsMsg.getMessage("rng.82");


        //承認経路 最終確認ユーザ未選択チェック
        if (keiro__.size() == 0 && finalKeiro__.size() == 0 && hisuFlg) {

            //承認経路と最終確認ユーザの両方が選択されていない場合、エラー
            msg = new ActionMessage("error.select.required.text", error);
            StrutsUtil.addMessage(errors, msg, "rng110apprUser");

        }
        boolean hasErr = false;
        Rng110KeiroDialogValidater validater = new Rng110KeiroDialogValidater(rtpSpecVer);
        for (Entry<Integer, Rng110KeiroDialogParamModel> entry : keiro__.entrySet()) {
            String dlgErrName = "err_keiro(" + entry.getKey() + ")";
            ActionErrors dlgErrors = validater.validate(reqMdl, dlgErrName, entry.getValue());
            if (dlgErrors.size() > 0) {
                hasErr = true;
            }
        }
        for (Entry<Integer, Rng110KeiroDialogParamModel> entry : finalKeiro__.entrySet()) {
            String dlgErrName = "err_finalKeiro(" + entry.getKey() + ")";
            ActionErrors dlgErrors = validater.validate(reqMdl, dlgErrName, entry.getValue());
            if (dlgErrors.size() > 0) {
                hasErr = true;
            }
        }
        if (hasErr) {
            msg = new ActionMessage("errors.free.msg", "入力エラーのある経路設定があります。");
            StrutsUtil.addMessage(errors, msg, "rng110keiro");
        }

        return errors;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((finalKeiro__ == null) ? 0 : finalKeiro__.hashCode());
        result = prime * result + ((keiro__ == null) ? 0 : keiro__.hashCode());
        return result;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Rng110Keiro)) {
            return false;
        }
        Rng110Keiro other = (Rng110Keiro) obj;
        if (finalKeiro__ == null) {
            if (other.finalKeiro__ != null) {
                return false;
            }
        } else if (!finalKeiro__.equals(other.finalKeiro__)) {
            return false;
        }
        if (keiro__ == null) {
            if (other.keiro__ != null) {
                return false;
            }
        } else if (!keiro__.equals(other.keiro__)) {
            return false;
        }
        return true;
    }

}
