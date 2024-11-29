package jp.groupsession.v2.wml.wml280;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlGetDestListInfoBiz;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAccessConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAddressDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistDao;
import jp.groupsession.v2.wml.model.WmlDestListInfoModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;

/**
 * <br>[機  能] WEBメール 送信先リスト登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml280Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml280Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Wml280ParamModel paramMdl, RequestModel reqMdl)
    throws Exception {

        WmlGetDestListInfoBiz wdlInfBiz = new WmlGetDestListInfoBiz();
        WmlDestListInfoModel wdlInfMdl = new WmlDestListInfoModel();

        //初期表示
        if (paramMdl.getWml280initFlg() == GSConstWebmail.DSP_FIRST) {
            //新規登録
            if (paramMdl.getWmlCmdMode() == GSConstWebmail.CMDMODE_ADD) {
                if (paramMdl.getWmlAccountMode() == 1) {
                    //個人設定からの遷移の場合、アクセス権限 編集ユーザの初期値としてセッションユーザを設定する
                    String[] accessFull
                        = new String[] {String.valueOf(reqMdl.getSmodel().getUsrsid())};
                    paramMdl.setWml280accessFull(accessFull);
                }

            } else if (paramMdl.getWmlCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
                //編集
                wdlInfBiz.getDestlistInfo(con, paramMdl.getWmlEditDestList(), wdlInfMdl);
                _setDestListInfo(paramMdl, wdlInfMdl);
                
            }

            paramMdl.setWml280initFlg(GSConstWebmail.DSP_ALREADY);
        }

        //グループが未選択の場合、デフォルトグループを設定
        if (paramMdl.getWml280accessGroup() == -1) {
            GroupBiz grpBz = new GroupBiz();
            int defGrp = grpBz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setWml280accessGroup(defGrp);
        }

        //送信先を設定
        _setParam(paramMdl, wdlInfMdl);
        wdlInfBiz.setDestListParam(con, wdlInfMdl);
        paramMdl.setDestUserList(wdlInfMdl.getDestUserList());
    }

    /**
     * <br>[機  能] 送信先リスト 送信先情報から送信先IDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param type 種別
     * @param sid SID(ユーザSID or アドレス帳SID)
     * @param mailNo メール番号
     * @return 送信先ID
     */
    protected String _createDestAddressId(int type, int sid, int mailNo) {
        String destAddressId = type + "-" + sid;
        if (type == GSConstWebmail.WDA_TYPE_USER) {
            destAddressId += "-" + mailNo;
        } else if (type == GSConstWebmail.WDA_TYPE_ADDRESS) {
            destAddressId += "_" + mailNo;
        }

        return destAddressId;
    }

    /**
     * <br>[機  能] 送信先の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void deleteDestUser(Wml280ParamModel paramMdl) {

        CommonBiz cmnBiz = new CommonBiz();

        paramMdl.setWml280destUser(
                cmnBiz.getDeleteMember(paramMdl.getWml280destUserSelect(),
                        paramMdl.getWml280destUser()));
        paramMdl.setWml280destAddress(
                cmnBiz.getDeleteMember(paramMdl.getWml280destUserSelect(),
                        paramMdl.getWml280destAddress()));
    }

    /**
     * <br>[機  能] 送信先リストの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteDestList(Connection con, Wml280ParamModel paramMdl)
    throws SQLException {

        WmlDestlistDao destlistDao = new WmlDestlistDao(con);
        WmlDestlistAddressDao destAddressDao = new WmlDestlistAddressDao(con);
        WmlDestlistAccessConfDao destAccessDao = new WmlDestlistAccessConfDao(con);

        int wdlSid = paramMdl.getWmlEditDestList();
        destAddressDao.delete(wdlSid);
        destAccessDao.delete(wdlSid);
        destlistDao.delete(wdlSid);
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] ログ用 連絡先情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlSid SID
     * @return 名前
     * @throws SQLException SQL実行時例外
     */
    public String getDestList(Connection con, int wdlSid)
    throws SQLException {

        WmlDestlistDao destlistDao = new WmlDestlistDao(con);
        WmlDestlistModel destMdl = destlistDao.select(wdlSid);
        return destMdl.getWdlName();
    }

    /**
     * <br>[機  能] 取得した値をパラメータモデルにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Wml280ParamModel
     * @param wdlIndMdl WmlDestListInfoModel
     */
    protected void _setDestListInfo(
        Wml280ParamModel paramMdl,
        WmlDestListInfoModel wdlIndMdl) {

        paramMdl.setWml280name(wdlIndMdl.getDestListName());
        paramMdl.setWml280biko(wdlIndMdl.getDestListBiko());
        paramMdl.setWml280destUser(wdlIndMdl.getDestUsers());
        paramMdl.setWml280destAddress(wdlIndMdl.getDestAddresses());
        paramMdl.setWml280accessFull(wdlIndMdl.getAccessFullUsers());
        paramMdl.setWml280accessRead(wdlIndMdl.getAccessReadUsers());
    }

    /**
     * <br>[機  能] パラメータモデルの値をWmlDestListInfoModelにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Wml280ParamModel
     * @param wdlIndMdl WmlDestListInfoModel
     */
    protected void _setParam(
        Wml280ParamModel paramMdl,
        WmlDestListInfoModel wdlIndMdl) {

            wdlIndMdl.setDestUsers(paramMdl.getWml280destUser());
            wdlIndMdl.setDestAddresses(paramMdl.getWml280destAddress());
    }
}
