package jp.groupsession.v2.rng.rng030;

import java.util.List;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.groupsession.v2.rng.RingiParamModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;

/**
 * <br>[機  能] 稟議内容確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng030KeiroParam extends RingiParamModel {
    /** singiTable*/
    private List<Rng030SingiParam> singiList__ = null;

    /** 稟議経路ステップSID*/
    private int keiroStepSid__ = 0;
    /** 経路進行条件*/
    private int keiroProgress__ = 0;
    /** 経路進行条件閾値*/
    private int keiroLimit__ = 0;
    /** 経路進行*/
    private int keiroMessage__ = 0;
    /** 経路名*/
    private String keiroName__ = null;
    /** 状態*/
    private int keiroStatus__ = 0;
    /** 経路順*/
    private int keiroSort__ = 0;
    /** 審議種別*/
    private int keiroSingi__ = 0;
    /** 審議者数*/
    private int keiroCount__ = 0;
    /** 後閲指示受け可能フラグ*/
    private int keiroKoetu__ = 0;
    /** 後閲者名*/
    private String keiroKoetuMei__ = null;
    /** 後閲指示出し可能フラグ*/
    private int keiroKoetuSizi__ = 0;
    /** テンプレート経路SIDステップ*/
    private int rtkSid__;
    /** 経路種別*/
    private EnumKeiroKbn rtkType__;
    /** 関連経路SID 任意設定経路の場合の親ステップ*/
    private int rksBelongSid__;
    /** 経路追加可能フラグ*/
    private int keiroAddible__;
    /** 経路追加時使用テンプレート経路SID*/
    private int addibleRtkSid__;
    /** 経路グループ削除フラグ*/
    private int groupDelFlg__ = 0;

    /** 追加経路ブロック*/
    private Rng020KeiroBlock addBlock__;
    /** 経路内全ユーザ削除フラグ*/
    private int keiroDelFlg__ = 0;

    /**
     * <p>singiList を取得します。
     * @return singiList
     */

    public List<Rng030SingiParam> getAddLocListToList() {
        return singiList__;
    }

    /**
     * <p>Rng030SingiParam を取得します。
     * @param iIndex インデックス番号
     * @return Rng030SingiParam を戻す
     */

    public Rng030SingiParam getAddLocList(int iIndex) {
        while (singiList__.size() <= iIndex) {
            singiList__.add(new Rng030SingiParam());
        }
        return singiList__.get(iIndex);
    }

    /**
     * <p>Rng030SingiParam を取得します。
     * @return Rng030SingiParam[]
     */

    public Rng030SingiParam[] getAddLocList() {
        int size = 0;
        if (singiList__ != null) {
            size = singiList__.size();
        }
        return (Rng030SingiParam[]) singiList__.toArray(new Rng030SingiParam[size]);
    }

    /**
     * <p>singiList をセットします。
     * @param singiList singiList
     */
    public void setAddLocListForm(List<Rng030SingiParam> singiList) {
        this.singiList__ = singiList;
    }

    /**
     * <p>singiList を取得します。
     * @return singiList
     */
    public List<Rng030SingiParam> getSingiList() {
        return singiList__;
    }

    /**
     * <p>singiList をセットします。
     * @param singiList singiList
     */
    public void setSingiList(List<Rng030SingiParam> singiList) {
        singiList__ = singiList;
    }

    /**
     * <p>keiroName を取得します。
     * @return keiroName
     */
    public String getKeiroName() {
        return keiroName__;
    }

    /**
     * <p>keiroName をセットします。
     * @param keiroName keiroName
     */
    public void setKeiroName(String keiroName) {
        keiroName__ = keiroName;
    }

    /**
     * <p>keiroStatus を取得します。
     * @return keiroStatus
     */
    public int getKeiroStatus() {
        return keiroStatus__;
    }

    /**
     * <p>keiroStatus をセットします。
     * @param keiroStatus keiroStatus
     */
    public void setKeiroStatus(int keiroStatus) {
        keiroStatus__ = keiroStatus;
    }

    /**
     * <p>keiroSort を取得します。
     * @return keiroSort
     */
    public int getKeiroSort() {
        return keiroSort__;
    }

    /**
     * <p>keiroSort をセットします。
     * @param keiroSort keiroSort
     */
    public void setKeiroSort(int keiroSort) {
        keiroSort__ = keiroSort;
    }

    /**
     * <p>keiroSingi を取得します。
     * @return keiroSingi
     */
    public int getKeiroSingi() {
        return keiroSingi__;
    }

    /**
     * <p>keiroSingi をセットします。
     * @param keiroSingi keiroSingi
     */
    public void setKeiroSingi(int keiroSingi) {
        keiroSingi__ = keiroSingi;
    }

    /**
     * <p>keiroStepSid を取得します。
     * @return keiroStepSid
     */
    public int getKeiroStepSid() {
        return keiroStepSid__;
    }

    /**
     * <p>keiroStepSid をセットします。
     * @param keiroStepSid keiroStepSid
     */
    public void setKeiroStepSid(int keiroStepSid) {
        keiroStepSid__ = keiroStepSid;
    }

    /**
     * <p>keiroCount を取得します。
     * @return keiroCount
     */
    public int getKeiroCount() {
        return keiroCount__;
    }

    /**
     * <p>keiroCount をセットします。
     * @param keiroCount keiroCount
     */
    public void setKeiroCount(int keiroCount) {
        keiroCount__ = keiroCount;
    }

    /**
     * <p>keiroProgress を取得します。
     * @return keiroProgress
     */
    public int getKeiroProgress() {
        return keiroProgress__;
    }

    /**
     * <p>keiroProgress をセットします。
     * @param keiroProgress keiroProgress
     */
    public void setKeiroProgress(int keiroProgress) {
        keiroProgress__ = keiroProgress;
    }

    /**
     * <p>keiroLimit を取得します。
     * @return keiroLimit
     */
    public int getKeiroLimit() {
        return keiroLimit__;
    }

    /**
     * <p>keiroLimit をセットします。
     * @param keiroLimit keiroLimit
     */
    public void setKeiroLimit(int keiroLimit) {
        keiroLimit__ = keiroLimit;
    }

    /**
     * <p>keiroMessage を取得します。
     * @return keiroMessage
     */
    public int getKeiroMessage() {
        return keiroMessage__;
    }

    /**
     * <p>keiroMessage をセットします。
     * @param keiroMessage keiroMessage
     */
    public void setKeiroMessage(int keiroMessage) {
        keiroMessage__ = keiroMessage;
    }

    /**
     * <p>keiroKoetu を取得します。
     * @return keiroKoetu
     */
    public int getKeiroKoetu() {
        return keiroKoetu__;
    }

    /**
     * <p>keiroKoetu をセットします。
     * @param keiroKoetu keiroKoetu
     */
    public void setKeiroKoetu(int keiroKoetu) {
        keiroKoetu__ = keiroKoetu;
    }

    /**
     * <p>keiroKoetuMei を取得します。
     * @return keiroKoetuMei
     */
    public String getKeiroKoetuMei() {
        return keiroKoetuMei__;
    }

    /**
     * <p>keiroKoetuMei をセットします。
     * @param keiroKoetuMei keiroKoetuMei
     */
    public void setKeiroKoetuMei(String keiroKoetuMei) {
        keiroKoetuMei__ = keiroKoetuMei;
    }
    /**
     *
     * <br>[機  能] 経路追加モード時経路追加可能かどうか
     * <br>[解  説]
     * <br>[備  考]
     * @return boolean
     */
     public boolean isAddable() {
         if (keiroAddible__ == RngConst.RNG_ABLE_ADDKEIRO) {
             return true;
         }
         return false;
     }

    /**
     * <p>rtkSid を取得します。
     * @return rtkSid
     */
    public int getRtkSid() {
        return rtkSid__;
    }

    /**
     * <p>rtkSid をセットします。
     * @param rtkSid rtkSid
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
    }

    /**
     * <p>rtkType を取得します。
     * @return rtkType
     */
    public EnumKeiroKbn getRtkType() {
        return rtkType__;
    }

    /**
     * <p>rtkType をセットします。
     * @param rtkType rtkType
     */
    public void setRtkType(EnumKeiroKbn rtkType) {
        rtkType__ = rtkType;
    }
    /**
     * <p>rtkType をセットします。
     * @param rtkTypeInt rtkTypeの値
     */
    public void setRtkType(int rtkTypeInt) {
        try {
            rtkType__ = EnumKeiroKbn.valueOf(rtkTypeInt);
        } catch (EnumOutRangeException e) {
            rtkType__ = null;
        }
    }

    /**
     * <p>rksBelongSid を取得します。
     * @return rksBelongSid
     */
    public int getRksBelongSid() {
        return rksBelongSid__;
    }

    /**
     * <p>rksBelongSid をセットします。
     * @param rksBelongSid rksBelongSid
     */
    public void setRksBelongSid(int rksBelongSid) {
        rksBelongSid__ = rksBelongSid;
    }
    /**
     * <p>keiroKoetuSizi を取得します。
     * @return keiroKoetuSizi
     */
    public int getKeiroKoetuSizi() {
        return keiroKoetuSizi__;
    }

    /**
     * <p>keiroKoetuSizi をセットします。
     * @param keiroKoetuSizi keiroKoetuSizi
     */
    public void setKeiroKoetuSizi(int keiroKoetuSizi) {
        keiroKoetuSizi__ = keiroKoetuSizi;
    }

    /**
     * <p>keiroAddible を取得します。
     * @return keiroAddible
     */
    public int getKeiroAddible() {
        return keiroAddible__;
    }

    /**
     * <p>keiroAddible をセットします。
     * @param keiroAddible keiroAddible
     */
    public void setKeiroAddible(int keiroAddible) {
        keiroAddible__ = keiroAddible;
    }

    /**
     * <p>addBlock を取得します。
     * @return addBlock
     */
    public Rng020KeiroBlock getAddBlock() {
        return addBlock__;
    }

    /**
     * <p>addBlock をセットします。
     * @param addBlock addBlock
     */
    public void setAddBlock(Rng020KeiroBlock addBlock) {
        addBlock__ = addBlock;
    }

    /**
     * <p>keiroDelFlg を取得します。
     * @return keiroDelFlg
     * @see jp.groupsession.v2.rng.rng030.Rng030KeiroParam#keiroDelFlg__
     */
    public int getKeiroDelFlg() {
        return keiroDelFlg__;
    }

    /**
     * <p>keiroDelFlg をセットします。
     * @param keiroDelFlg keiroDelFlg
     * @see jp.groupsession.v2.rng.rng030.Rng030KeiroParam#keiroDelFlg__
     */
    public void setKeiroDelFlg(int keiroDelFlg) {
        keiroDelFlg__ = keiroDelFlg;
    }

    /**
     * <p>addibleRtkSid を取得します。
     * @return addibleRtkSid
     * @see jp.groupsession.v2.rng.rng030.Rng030KeiroParam#addibleRtkSid__
     */
    public int getAddibleRtkSid() {
        return addibleRtkSid__;
    }

    /**
     * <p>addibleRtkSid をセットします。
     * @param addibleRtkSid addibleRtkSid
     * @see jp.groupsession.v2.rng.rng030.Rng030KeiroParam#addibleRtkSid__
     */
    public void setAddibleRtkSid(int addibleRtkSid) {
        addibleRtkSid__ = addibleRtkSid;
    }

    /**
     * <p>groupDelFlg を取得します。
     * @return groupDelFlg
     * @see jp.groupsession.v2.rng.rng030.Rng030KeiroParam#groupDelFlg__
     */
    public int getGroupDelFlg() {
        return groupDelFlg__;
    }

    /**
     * <p>groupDelFlg をセットします。
     * @param groupDelFlg groupDelFlg
     * @see jp.groupsession.v2.rng.rng030.Rng030KeiroParam#groupDelFlg__
     */
    public void setGroupDelFlg(int groupDelFlg) {
        groupDelFlg__ = groupDelFlg;
    }

}