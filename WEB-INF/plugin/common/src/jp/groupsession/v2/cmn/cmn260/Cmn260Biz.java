package jp.groupsession.v2.cmn.cmn260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] OAuth認証情報登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn260Biz {

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Connection con,
            Cmn260ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        CmnOauthDao woDao = new CmnOauthDao(con);
        int authSid = paramMdl.getCmnAuthSid();

        //プロバイダコンボ生成
        paramMdl.setCmn260authList(__createAuthCombo(reqMdl));

        //初期表示 編集
        if (paramMdl.getCmn260initKbn() == Cmn260Form.INIT_FLG
        && paramMdl.getCmn250CmdMode() == GSConstCommon.MODE_EDIT) {

            //認証情報設定
            CmnOauthModel woMdl = woDao.getAuth(authSid);
            paramMdl.setCmn260provider(woMdl.getCouProvider());
            paramMdl.setCmn260cliendId(woMdl.getCouAuthId());
            paramMdl.setCmn260secret(woMdl.getCouAuthSecret());
            paramMdl.setCmn260biko(woMdl.getCouBiko());

            //初期表示完了
            paramMdl.setCmn260initKbn(Cmn260Form.NOT_INIT_FLG);
        }
    }

    /**
     * <br>[機  能] 認証情報の重複チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    protected boolean _duplicateCheck(Connection con, Cmn260ParamModel paramMdl)
            throws SQLException {

        int authSid = paramMdl.getCmnAuthSid();
        int provider = paramMdl.getCmn260provider();
        CmnOauthDao couDao = new CmnOauthDao(con);
        return couDao.existProvider(provider, authSid);
    }

    /**
     * <br>[機  能] 認証情報コンボを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 認証情報コンボ
     */
    private List<LabelValueBean> __createAuthCombo(RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //自動保存(分)ラベル作成
        List<LabelValueBean> cmn260AuthLabel = new ArrayList<LabelValueBean>();
        cmn260AuthLabel.add(new LabelValueBean(gsMsg.getMessage("cmn.cmn260.02"),
                String.valueOf(GSConstCommon.COU_PROVIDER_GOOGLE)));
        cmn260AuthLabel.add(new LabelValueBean(gsMsg.getMessage("cmn.cmn260.03"),
                String.valueOf(GSConstCommon.COU_PROVIDER_MICROSOFT)));
        return cmn260AuthLabel;
    }
}
