package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.RsvScdOperationDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.RsvScdOperationModel;

/**
 * <br>[機  能] スケジュールと施設のリレーションチェッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RelationBetweenScdAndRsvChkBiz {

    /** リクエスト */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;

    /** チェック区分 単一予約編集時 */
    public static final int CHK_KBN_TANITU = 1;
    /** チェック区分 繰り返し予約編集時 */
    public static final int CHK_KBN_KURIKAESHI = 2;

    /** エラーコード エラー無し */
    public static final int ERR_CD_NON_ERR = 0;
    /** エラーコード 該当する施設予約情報が無い */
    public static final int ERR_CD_YRK_NONE = 1;
    /** エラーコード 施設予約に関連付いたスケジュールが無い */
    public static final int ERR_CD_SCD_REL_NONE = 2;
    /** エラーコード スケジュール変更不可 */
    public static final int ERR_CD_SCD_CANNOT_EDIT = 3;
    /** エラーコード 該当するスケジュール情報が無い */
    public static final int ERR_CD_SCD_NONE = 4;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public RelationBetweenScdAndRsvChkBiz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /**
     * <br>[機  能] セッションユーザModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return usModel セッションユーザModel
     */
    private BaseUserModel __getSessionUserModel() {

        //セッションユーザModelを取得
        BaseUserModel usModel = reqMdl__.getSmodel();

        return usModel;
    }

    /**
     * <br>[機  能] 施設予約SIDを元に関連付いたスケジュールの
     * <br>         編集権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 施設予約SID
     * @param kbn チェック区分
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isScdEdit(int rsySid, int kbn) throws SQLException {

        BaseUserModel usrMdl = __getSessionUserModel();

        //管理者グループに所属している
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, usrMdl, GSConstCommon.PLUGIN_ID_SCHEDULE);
        if (adminUser) {
            return ERR_CD_NON_ERR;
        }

        //施設予約情報取得
        RsvScdOperationDao scdDao = new RsvScdOperationDao(con__);
        RsvScdOperationModel yrkRet = scdDao.select(rsySid);

        if (yrkRet == null) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

       /*****************************************************************
        *
        * スケジュール情報取得。
        *
        * スケジュールが同時登録されている場合は
        * 複数件のレコードが存在するため全レコードを取得する。
        *
        *****************************************************************/

        ArrayList<RsvScdOperationModel> scdRet = null;
        if (kbn == CHK_KBN_TANITU) {
            scdRet = scdDao.selectSchList(yrkRet.getScdRsSid());
        } else if (kbn == CHK_KBN_KURIKAESHI) {
            scdRet = scdDao.selectSchListGrpSceSid(yrkRet.getScdRsSid());
        }

        if (scdRet.isEmpty()) {
            //エラー 関連付いたスケジュールが無い
            return ERR_CD_SCD_REL_NONE;
        }

       /*****************************************************************
        *
        * 取得したスケジュール情報の権限を判定。
        *
        * 編集権限が「制限なし」以外の場合に「本人」「登録者」の
        * チェックを行う。
        *
        *****************************************************************/
        //セッションユーザSID取得
        int sessionUsrSid = usrMdl.getUsrsid();

        boolean scdErr = false;
        HashMap<Integer, Integer> userMap = new HashMap<Integer, Integer>();
        for (RsvScdOperationModel scdMdl : scdRet) {

            //編集権限取得
            int scdEdit = scdMdl.getScdEdit();

            if (scdEdit != 0) {
                //本人・登録者であるか判定
                if (sessionUsrSid != scdMdl.getScdUsrSid()
                        && sessionUsrSid != scdMdl.getScdAuid()) {
                    scdErr = true;
                    if (scdEdit == 2) {
                        userMap.put(scdMdl.getScdUsrSid(), scdMdl.getScdUsrSid());
                    }
                }
            }
        }

        /*****************************************************************
         *
         * 権限設定が「所属グループ・本人」かつ、
         * その条件に該当しなかったユーザのSIDを元に
         * 「同グループかどうか」の判定を行う。
         *
         *****************************************************************/
        boolean grpErr = false;
        if (!userMap.isEmpty()) {
            ArrayList<Integer> userArray = new ArrayList<Integer>(userMap.values());
            for (int userSid : userArray) {
                //同じグループに所属しているか判定
                if (!scdDao.isScdEditTi(userSid, sessionUsrSid)) {
                    grpErr = true;
                }
            }
            if (!grpErr) {
                scdErr = false;
            }
        }

        if (scdErr || grpErr) {
            return ERR_CD_SCD_CANNOT_EDIT;
        }

        return ERR_CD_NON_ERR;
    }

    /**
     * <br>[機  能] 施設予約SIDを元に自分自身の予約データが
     * <br>         編集可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid スケジュールSID
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isEditMeRsv(int rsySid) throws SQLException {

        BaseUserModel usrMdl = __getSessionUserModel();
        int sessionUsrSid = usrMdl.getUsrsid();

        //管理者グループに所属している
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, usrMdl, GSConstCommon.PLUGIN_ID_RESERVE);
        if (adminUser) {
            return ERR_CD_NON_ERR;
        }

        //施設予約情報取得
        RsvScdOperationDao yrkDao = new RsvScdOperationDao(con__);
        RsvScdOperationModel yrkRet = yrkDao.select(rsySid);

        if (yrkRet == null) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

        RsvScdOperationDao opDao = new RsvScdOperationDao(con__);
        ArrayList<RsvScdOperationModel> yrkList = null;

        //繰り返しチェック
        if (yrkRet.getRsrRsid() > 0) {
            yrkList = opDao.selectRsvList(yrkRet.getRsrRsid());
        //単一チェック
        } else {
            yrkList = opDao.selectRsvMdl(rsySid);
        }

        if (yrkList.isEmpty()) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

        //施設グループ管理者情報取得
        HashMap<String, String> rsvGrpAdmMap =
            opDao.selectRsvGrpAdmMap(sessionUsrSid);

       /*****************************************************************
        *
        * 取得した施設情報の権限を判定。
        *
        * 編集権限が「制限なし」以外の場合に「予約者」「使用者」の
        * チェックを行う。
        *
        *****************************************************************/
        boolean rsvErr = false;
        boolean grpChkFlg = false;
        for (RsvScdOperationModel rsvMdl : yrkList) {

            //編集権限取得
            int rsvEdit = rsvMdl.getRsyEdit();

            if (rsvEdit != 0) {

                //施設グループ管理者か判定
                if (!rsvGrpAdmMap.isEmpty()) {
                    int rsgSid = rsvMdl.getRsgSid();
                    String rsvGrpKey =
                        rsgSid + "-" + sessionUsrSid;

                    if (rsvGrpAdmMap.containsKey(rsvGrpKey)) {
                        continue;
                    }
                }

                //int rssid = rsvMdl.getScdRsSid();
                //String key = rssid + "-" + sessionUsrSid;

                //本人または登録者か判定
                if (sessionUsrSid != rsvMdl.getRsyAuid()) {
                    if (rsvEdit == 2) {
                        if (!grpChkFlg) {
                            if (!opDao.isScdEditTi(rsvMdl.getRsyAuid(), sessionUsrSid)) {
                                rsvErr = true;
                            }
                            grpChkFlg = true;
                        }
                    } else {
                        rsvErr = true;
                    }
                }
            }
        }

        if (rsvErr) {
            return ERR_CD_SCD_CANNOT_EDIT;
        }

        return ERR_CD_NON_ERR;
    }

    /**
     * <br>[機  能] スケジュールSIDを元に関連付いた施設予約の
     * <br>         編集権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdSid スケジュールSID
     * @param kbn チェック区分
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isRsvEdit(int scdSid, int kbn) throws SQLException {

        BaseUserModel usrMdl = __getSessionUserModel();
        return isRsvEdit(scdSid, kbn, usrMdl);
    }

    /**
     * <br>[機  能] スケジュールSIDを元に関連付いた施設予約の
     * <br>         編集権限をチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdSid スケジュールSID
     * @param kbn チェック区分
     * @param usrMdl ユーザModel
     * @return リターンコード
     * @throws SQLException SQL実行時例外
     */
    public int isRsvEdit(int scdSid, int kbn, BaseUserModel usrMdl)
        throws SQLException {

        //管理者グループに所属している
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con__, usrMdl, GSConstCommon.PLUGIN_ID_RESERVE);

        if (adminUser) {
            return ERR_CD_NON_ERR;
        }

       /*****************************************************************
        *
        * ベースになるスケジュール情報取得。
        *
        *****************************************************************/
        RsvScdOperationDao scdDao = new RsvScdOperationDao(con__);
        RsvScdOperationModel scdBase = scdDao.selectSchMdl(scdSid);

        //該当するスケジュール情報が無い
        if (scdBase == null) {
            return ERR_CD_SCD_NONE;
        }

       /*****************************************************************
        *
        * チェック対象のスケジュール情報取得。
        *
        *****************************************************************/
        RsvScdOperationModel scdRet = null;
        if (kbn == CHK_KBN_TANITU) {
            int scdRsSid = scdBase.getScdRsSid();
            scdRet = scdDao.selectSchMdlGrpRssid(scdRsSid);
        } else if (kbn == CHK_KBN_KURIKAESHI) {
            scdRet = scdDao.selectSchMdlGrpSceSid(scdSid);
        }

        if (scdRet == null) {
            //エラー 関連付いたスケジュールが無い
            return ERR_CD_SCD_NONE;
        }

       /*****************************************************************
        *
        * セッションユーザSIDの施設グループ管理者情報を取得。
        *
        *****************************************************************/
        //施設グループ管理者マッピング作成
        int sessionUsrSid = usrMdl.getUsrsid();
        HashMap<String, String> rsvGrpAdmMap =
            scdDao.selectRsvGrpAdmMap(sessionUsrSid);

       /*****************************************************************
        *
        * 施設情報取得。
        *
        *****************************************************************/
        //スケジュールリレーションSIDマップを配列に変換
        ArrayList<Integer> rssidArray =
            new ArrayList<Integer>(scdRet.getRssidMap().values());
        Collections.sort(rssidArray);

        ArrayList<RsvScdOperationModel> rsvArray =
            scdDao.selectRsvList(rssidArray);

        if (rsvArray.isEmpty()) {
            //エラー 施設予約情報が無い
            return ERR_CD_YRK_NONE;
        }

       /*****************************************************************
        *
        * 取得した施設情報の権限を判定。
        *
        * 編集権限が「制限なし」以外の場合に「予約者」「使用者」の
        * チェックを行う。
        *
        *****************************************************************/
        boolean rsvErr = false;
        boolean grpChkFlg = false;
        HashMap<String, String> usrMap = scdRet.getUsrMap();
        for (RsvScdOperationModel rsvMdl : rsvArray) {

            //編集権限取得
            int rsvEdit = rsvMdl.getRsyEdit();

            if (rsvEdit != 0) {

                //施設グループ管理者か判定
                if (!rsvGrpAdmMap.isEmpty()) {
                    int rsgSid = rsvMdl.getRsgSid();
                    String rsvGrpKey =
                        rsgSid + "-" + sessionUsrSid;

                    if (rsvGrpAdmMap.containsKey(rsvGrpKey)) {
                        continue;
                    }
                }

                int rssid = rsvMdl.getScdRsSid();
                String key = rssid + "-" + sessionUsrSid;

                //予約者または使用者か判定
                if (sessionUsrSid != rsvMdl.getRsyAuid()
                        && !usrMap.containsKey(key)) {

                    if (rsvEdit == 2) {
                        if (!grpChkFlg) {
                            if (!scdDao.isScdEditTi(rsvMdl.getRsyAuid(), sessionUsrSid)) {
                                rsvErr = true;
                            }
                            grpChkFlg = true;
                        }
                    } else {
                        rsvErr = true;
                    }
                }
            }
        }

        if (rsvErr) {
            return ERR_CD_SCD_CANNOT_EDIT;
        }

        return ERR_CD_NON_ERR;
    }

    /**
     * <br>[機  能] 指定したスケジュールデータを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdUsrSid スケジュールユーザSID
     * @param scdUsrKbn スケジュールユーザ区分
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canAccessSchedule(int scdUsrSid, int scdUsrKbn, int sessionUserSid)
    throws SQLException {
        List<Integer> notAccessList = null;
        SchDao schDao = new SchDao(con__);
        if (scdUsrKbn == GSConstSchedule.USER_KBN_GROUP) {
            notAccessList = schDao.getNotAccessGrpList(sessionUserSid);
        } else {
            notAccessList = schDao.getNotAccessUserList(sessionUserSid);
        }

        return notAccessList.indexOf(scdUsrSid) < 0;
    }
    /**
     * <br>[機  能] 指定したユーザにスケジュールデータを登録可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdUsrSid スケジュールユーザSID
     * @param scdUsrKbn スケジュールユーザ区分
     * @param sessionUserSid セッションユーザSID
     * @return true: 編集可能 false: 編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistSchedule(int scdUsrSid, int scdUsrKbn, int sessionUserSid)
    throws SQLException {
        List<Integer> notAccessList = null;
        SchDao schDao = new SchDao(con__);
        if (scdUsrKbn == GSConstSchedule.USER_KBN_GROUP) {
            notAccessList = schDao.getNotRegistGrpList(sessionUserSid);
        } else {
            notAccessList = schDao.getNotRegistUserList(sessionUserSid);
        }

        return notAccessList.indexOf(scdUsrSid) < 0;
    }
}