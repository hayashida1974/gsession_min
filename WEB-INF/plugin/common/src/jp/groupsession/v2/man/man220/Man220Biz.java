package jp.groupsession.v2.man.man220;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] メイン 管理者設定 グループ・ユーザ並び順設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man220Biz {

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Man220ParamModel paramMdl, RequestModel reqMdl, Connection con)
    throws SQLException {

        //コンボデータをセット
        setMan220CombData(paramMdl, reqMdl);

        if (paramMdl.getMan220initFlg() == 0) {
            CmnCmbsortConfDao cmbSortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel cmbSortMdl = cmbSortDao.getCmbSortData();
            if (cmbSortMdl != null) {
                paramMdl.setMan220UserSortKey1(cmbSortMdl.getCscUserSkey1());
                paramMdl.setMan220UserSortKey2(cmbSortMdl.getCscUserSkey2());
                paramMdl.setMan220UserSortOrder1(cmbSortMdl.getCscUserOrder1());
                paramMdl.setMan220UserSortOrder2(cmbSortMdl.getCscUserOrder2());

                if (cmbSortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_NOSET) {
                    paramMdl.setMan220GroupSortKbn(0);
                } else {
                    paramMdl.setMan220GroupSortKbn(Man220Form.GRPSORTKBN_SELECT);
                }

                paramMdl.setMan220GroupSortKey1(cmbSortMdl.getCscGroupSkey1());
                paramMdl.setMan220GroupSortKey2(cmbSortMdl.getCscGroupSkey2());
                paramMdl.setMan220GroupSortOrder1(cmbSortMdl.getCscGroupOrder1());
                paramMdl.setMan220GroupSortOrder2(cmbSortMdl.getCscGroupOrder2());
            }

            paramMdl.setMan220initFlg(1);
        }
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行エラー
     */
    public void entryCmbSortConfig(Man220ParamModel paramMdl, Connection con, int sessionUserSid)
    throws SQLException {

        UDate now = new UDate();
        CmnCmbsortConfModel cmbSortMdl = new CmnCmbsortConfModel();

        cmbSortMdl.setCscUserSkey1(paramMdl.getMan220UserSortKey1());
        cmbSortMdl.setCscUserSkey2(paramMdl.getMan220UserSortKey2());
        cmbSortMdl.setCscUserOrder1(paramMdl.getMan220UserSortOrder1());
        cmbSortMdl.setCscUserOrder2(paramMdl.getMan220UserSortOrder2());

        if (paramMdl.getMan220GroupSortKbn() == Man220Form.GRPSORTKBN_SELECT) {
            cmbSortMdl.setCscGroupSkbn(GSConst.GROUPCMB_SKBN_SET);
        } else {
            cmbSortMdl.setCscGroupSkbn(GSConst.GROUPCMB_SKBN_NOSET);
        }

        cmbSortMdl.setCscGroupSkey1(paramMdl.getMan220GroupSortKey1());
        cmbSortMdl.setCscGroupSkey2(paramMdl.getMan220GroupSortKey2());
        cmbSortMdl.setCscGroupOrder1(paramMdl.getMan220GroupSortOrder1());
        cmbSortMdl.setCscGroupOrder2(paramMdl.getMan220GroupSortOrder2());
        cmbSortMdl.setCscAuid(sessionUserSid);
        cmbSortMdl.setCscAdate(now);
        cmbSortMdl.setCscAuid(sessionUserSid);
        cmbSortMdl.setCscEdate(now);

        CmnCmbsortConfDao cmbSortDao = new CmnCmbsortConfDao(con);
        if (cmbSortDao.update(cmbSortMdl) < 1) {
            cmbSortDao.insert(cmbSortMdl);
        }
    }

    /**
     * <br>[機  能] コンボデータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     */
    public void setMan220CombData(Man220ParamModel paramMdl, RequestModel reqMdl) {

        List<LabelValueBean> userSortKeyLabel = new ArrayList<LabelValueBean>();
        Map<String, String> sortKeyUserMap = new HashMap<String, String>();
        Map<String, String> sortKeyGroupMap = new HashMap<String, String>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        sortKeyUserMap.put(String.valueOf(Man220Form.SORTKEY_USER[0]),
                gsMsg.getMessage("cmn.name4"));
        sortKeyUserMap.put(String.valueOf(Man220Form.SORTKEY_USER[1]),
                gsMsg.getMessage("cmn.employee.staff.number"));
        sortKeyUserMap.put(String.valueOf(Man220Form.SORTKEY_USER[2]),
                gsMsg.getMessage("cmn.post"));
        sortKeyUserMap.put(String.valueOf(Man220Form.SORTKEY_USER[3]),
                gsMsg.getMessage("cmn.birthday"));
        sortKeyUserMap.put(String.valueOf(Man220Form.SORTKEY_USER[4]),
                gsMsg.getMessage("cmn.sortkey") + 1);
        sortKeyUserMap.put(String.valueOf(Man220Form.SORTKEY_USER[5]),
                gsMsg.getMessage("cmn.sortkey") + 2);

        sortKeyGroupMap.put(String.valueOf(Man220Form.SORTKEY_GROUP[0]),
                gsMsg.getMessage("cmn.group.id"));
        sortKeyGroupMap.put(String.valueOf(Man220Form.SORTKEY_GROUP[1]),
                gsMsg.getMessage("cmn.name4"));

        for (int sortKey : Man220Form.SORTKEY_USER) {
            String strSortKey = String.valueOf(sortKey);
            userSortKeyLabel.add(
                    new LabelValueBean(sortKeyUserMap.get(strSortKey), strSortKey));
        }

        List<LabelValueBean> groupSortKeyLabel = new ArrayList<LabelValueBean>();
        for (int sortKey : Man220Form.SORTKEY_GROUP) {
            String strSortKey = String.valueOf(sortKey);
            groupSortKeyLabel.add(
                    new LabelValueBean(sortKeyGroupMap.get(strSortKey), strSortKey));
        }
        paramMdl.setUserSortKeyLabel(userSortKeyLabel);
        paramMdl.setGroupSortKeyLabel(groupSortKeyLabel);
    }
}
