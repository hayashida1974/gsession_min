package jp.groupsession.v2.cmn.model;

import jp.groupsession.v2.cmn.GSConstSchedule;

/**
 *
 * <br>[機  能] リマインダー通知欄の設定モード列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum SchEnumRemindMode {
    /** 自分のスケジュール（プッシュ通知すべて利用可） */
    MINE,
    /** プッシュ通知利用不可 */
    OTHERS,
    /** グループスケジュール（プッシュ通知すべて利用可） */
    GROUP;

    /**
     *
     * <br>[機  能] リマインダー通知欄 表示判定
     * <br>[解  説]
     * <br>[備  考]
     * @return true : 表示する
     */
    public boolean isAbleSelReminder() {
        if (this == OTHERS) {
            return false;
        }
        return true;
    }
    /**
     *
     * <br>[機  能] ターゲット選択チェックボックス表示判定
     * <br>[解  説]
     * <br>[備  考]
     * @return true : 表示する
     */
    public boolean isAbleTargetSelect() {
        if (this == MINE) {
            return true;
        }
        return false;
    }

    /**
     *
     * <br>[機  能] リマインダー通知入力欄のモードを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param schUsrKbn スケジュールユーザ区分
     * @param sessionUsrSid セッションユーザSID
     * @param selectUsrSid スケジュール対象ユーザSID
     * @return リマインダー通知入力欄のモード
     */
    public static SchEnumRemindMode valueOf(int schUsrKbn,
            int sessionUsrSid,
            int selectUsrSid) {

        if (schUsrKbn == GSConstSchedule.USER_KBN_GROUP) {
            return SchEnumRemindMode.GROUP;

        } else {
            if(sessionUsrSid == selectUsrSid) {
                return SchEnumRemindMode.MINE;
            }
        }
        return SchEnumRemindMode.OTHERS;
    }
}
