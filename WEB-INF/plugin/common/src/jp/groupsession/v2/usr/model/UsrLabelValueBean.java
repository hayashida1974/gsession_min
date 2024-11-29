package jp.groupsession.v2.usr.model;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.UserUtil;
/**
 *
 * <br>[機  能] ユーザ選択用ラベル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsrLabelValueBean extends CmnLabelValueModel {
    /**定数 アイコン使用フラグ 使用しない*/
    public static final int ICON_FLG_NO = 0;
    /**定数 アイコン使用フラグ 使用する*/
    public static final int ICON_FLG_USE = 1;
    /**定数 アイコン使用フラグ デフォルト*/
    public static final int ICON_FLG_DEF = 2;
    /**定数 アイコン使用フラグ 非公開*/
    public static final int ICON_FLG_NOTPUBLIC = 3;
    /** ユーザログイン有効フラグ disableとは異なる*/
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    /** アイコン使用フラグ*/
    private int useIconFlg__ = ICON_FLG_NO;
    /** アイコン使用binSid*/
    private long iconBinSid__;
    /** アイコン公開フラグ */
    private int usiPictKf__;
    /** 削除済みフラグ */
    private int jkbn__ = GSConstUser.USER_JTKBN_ACTIVE;

    /**
     * コンストラクタ
     */
    public UsrLabelValueBean() {
        super(null, null, "");
    }
    /**
     * コンストラクタ
     * @param label ラベル
     * @param value 値
     */
    public UsrLabelValueBean(String label, String value) {
        super(label, value, "");
    }
    /**
     * コンストラクタ
     * @param label ラベル
     * @param value 値
     * @param ukoFlg 有効フラグ
     */
    public UsrLabelValueBean(String label, String value, int ukoFlg) {
        super(label, value, "");
        setUsrUkoFlg(ukoFlg);
    }
    /**
     * コンストラクタ
     * @param label ラベル
     * @param value 値
     * @param usrUkoFlg 有効フラグ
     * @param useIconFlg アイコン使用フラグ
     * @param iconBinSid アイコン画像SID
     */
    public UsrLabelValueBean(String label, String value,
            int usrUkoFlg, int useIconFlg, long iconBinSid) {
        super(label, value, "");
        usrUkoFlg__ = usrUkoFlg;
        useIconFlg__ = useIconFlg;
        iconBinSid__ = iconBinSid;
    }
    /**
     * コンストラクタ
     * @param usrMdl ユーザ情報
     */
    public UsrLabelValueBean(CmnUsrmInfModel usrMdl) {
        super(
                UserUtil.makeName(usrMdl.getUsiSei(), usrMdl.getUsiMei()),
                String.valueOf(usrMdl.getUsrSid()), "");
        setUsrUkoFlg(usrMdl.getUsrUkoFlg());
        usiPictKf__ = usrMdl.getUsiPictKf();
        iconBinSid__ = usrMdl.getBinSid();
        setJkbn(usrMdl.getUsrJkbn());
    }
    /**
     * コンストラクタ
     * @param grpMdl グループ情報
     */
    public UsrLabelValueBean(CmnGroupmModel grpMdl) {
        super(
                UserUtil.makeName(grpMdl.getGrpName(), ""), 
                "G" + String.valueOf(grpMdl.getGrpSid()), "");
    }
    /**
     * コンストラクタ (ユーザアイコンを使用する場合)
     * @param usrMdl ユーザ情報
     * @param sessionUsrSid セッションユーザSID
     */
    public UsrLabelValueBean(CmnUsrmInfModel usrMdl, int sessionUsrSid) {
        this(usrMdl);
        dspIcon(true, sessionUsrSid);
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
    /**
     * <p>useIconFlg を取得します。
     * @return useIconFlg
     */
    public int getUseIconFlg() {
        return useIconFlg__;
    }
    /**
     * <p>useIconFlg をセットします。
     * @param useIconFlg useIconFlg
     */
    public void setUseIconFlg(int useIconFlg) {
        useIconFlg__ = useIconFlg;
    }
    /**
     * <p>iconBinSid を取得します。
     * @return iconBinSid
     */
    public long getIconBinSid() {
        return iconBinSid__;
    }
    /**
     * <p>iconBinSid をセットします。
     * @param iconBinSid iconBinSid
     */
    public void setIconBinSid(long iconBinSid) {
        iconBinSid__ = iconBinSid;
    }
    /**
     *
     * <br>[機  能] ログイン停止状態用のCSSクラス名を返す
     * <br>[解  説]
     * <br>[備  考] JSPのスクリプトレットにて使用
     * @return ログイン停止状態用のCSSクラス名
     */
    public String getCSSClassNameNormal() {
        return UserUtil.getCSSClassNameNormal(usrUkoFlg__);
    }
    /**
     *
     * <br>[機  能] ログイン停止状態用のCSSクラス名を返す
     * <br>[解  説]
     * <br>[備  考] JSPのスクリプトレットにて使用
     * @return ログイン停止状態用のCSSクラス名
     */
    public String getCSSClassNameOption() {
        return UserUtil.getCSSClassNameOption(usrUkoFlg__);
    }
    /**
     *
     * <br>[機  能] ユーザアイコンの表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param onoff 表示するかしないか
     * @param sessionUsrSid セッションユーザSID
     */
    public void dspIcon(boolean onoff, int sessionUsrSid) {
        if (onoff) {
            if (usiPictKf__ == 1 && !getValue().equals(String.valueOf(sessionUsrSid))) {
                useIconFlg__ = ICON_FLG_NOTPUBLIC;
            } else if (iconBinSid__ == 0) {
                useIconFlg__ = ICON_FLG_DEF;
            } else {
                useIconFlg__ = ICON_FLG_USE;
            }
        } else {
            useIconFlg__ = ICON_FLG_NO;
        }
    }
    /**
     * <p>jkbn を取得します。
     * @return jkbn
     * @see jp.groupsession.v2.usr.model.UsrLabelValueBean#jkbn__
     */
    public int getJkbn() {
        return jkbn__;
    }
    /**
     * <p>jkbn をセットします。
     * @param jkbn jkbn
     * @see jp.groupsession.v2.usr.model.UsrLabelValueBean#jkbn__
     */
    public void setJkbn(int jkbn) {
        jkbn__ = jkbn;
    }
}
