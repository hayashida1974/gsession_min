package jp.groupsession.v2.bbs;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.ITempDirIdUseable;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] 掲示板で共通使用するアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractBulletinAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractBulletinAction.class);

    /** プラグインID */
    private static final String PLUGIN_ID = "bulletin";

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * <br>[機  能] フォーラム閲覧権限、または編集権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考] 編集権限がある場合は閲覧権限もあるとみなします。
     * <br> 権限無しの場合はエラーメッセージページへのパラメータを設定
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param accessKbn アクセス区分 0:閲覧 1:追加・編集・削除
     * @return 権限の有無 true:権限あり false:権限無し
     * @throws Exception 実行例外
     */
    protected boolean _checkForumAuth(ActionMapping map,
        HttpServletRequest req,
        Connection con,
        int bfiSid,
        int accessKbn)
        throws Exception {

        log__.debug("checkForumAuth START");

//        //管理者の場合は権限あり
//        if (buMdl.isAdmin()) {
//            return true;
//        }

        BbsBiz bbsBiz = new BbsBiz(con);
        BaseUserModel buMdl = getSessionUserModel(req);
            //フォーラム権限のチェック
        if (bbsBiz.checkForumAuth(con, buMdl, bfiSid, accessKbn)) {
            return true;
        }

        //権限なしの場合はメッセージページのパラメータを設定する
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "";
        if (accessKbn == GSConstBulletin.ACCESS_KBN_WRITE) {
            //フォーラムの編集権限がないためご利用いただけません。
            msgState = "error.access.forum.user.write";
        } else {
            //フォーラムの閲覧権限がないためご利用いただけません。
            msgState = "error.access.forum.user";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState));

        req.setAttribute("cmn999Form", cmn999Form);

        log__.debug("checkForumAuth END");

        return false;
    }

    /**
     * <br>[機  能] 返信可能なフォーラムかチェックを行う。
     * <br>[解  説]
     * <br>[備  考] 権限無しの場合はエラーメッセージページへのパラメータを設定
     * @param map マップ
     * @param req リクエスト
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @return 返信可能区分 true:可能 false:不可能
     * @throws Exception 実行例外
     */
    protected boolean _checkReplyAuth(ActionMapping map,
        HttpServletRequest req,
        Connection con,
        int bfiSid)
        throws Exception {

        log__.debug("checkReplyAuth START");

        //フォーラム閲覧権限のチェック
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        BbsForInfModel bfiModel = new BbsForInfModel();
        bfiModel.setBfiSid(bfiSid);
        BbsForInfModel ret = bfiDao.select(bfiModel);
        if (ret.getBfiReply() == GSConstBulletin.BBS_THRE_REPLY_YES) {
            return true;
        }

        //権限なしの場合はメッセージページのパラメータを設定する
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "error.cannot.reply.thread";
        cmn999Form.setMessage(msgRes.getMessage(msgState));

        req.setAttribute("cmn999Form", cmn999Form);

        log__.debug("checkReplyAuth END");

        return false;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説] 処理モード = 編集の場合、スレッド情報を設定する
     * <br>[備  考]
     * @param req リクエスト
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    protected String _getBulletinTempDir(HttpServletRequest req, String dirId) {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        RequestModel reqMdl = getRequestModel(req);

        String tempDir = pathUtil.getTempPath(reqMdl,
                GSConstBulletin.PLUGIN_ID_BULLETIN,
                dirId
                );
        log__.debug("テンポラリディレクトリ = " + tempDir);

        return tempDir;
    }
    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param form テンポラリディレクトリ利用画面 フォーム、パラメータモデル
     * @return テンポラリディレクトリパス
     */
    protected String _getBulletinTempDir(HttpServletRequest req, ITempDirIdUseable form) {

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = pathUtil.getTempPath(getRequestModel(req),
                GSConstBulletin.PLUGIN_ID_BULLETIN,
                form.getTempDirId());
        log__.debug("テンポラリディレクトリ = " + tempDir);

        return tempDir;
    }
    /**
     * <br>[機  能] テンポラリディレクトリID初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param form テンポラリディレクトリ利用画面 フォーム、パラメータモデル
     * @throws IOToolsException IOエラー
     */
    protected void _initBulletinTempDir(HttpServletRequest req, ITempDirIdUseable form)
            throws IOToolsException {
        RequestModel reqMdl = getRequestModel(req);

        if (StringUtil.isNullZeroString(form.getTempDirId())) {
            GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
            String tmpDirId = pathUtil.getTempDirID(reqMdl,
                    GSConstBulletin.PLUGIN_ID_BULLETIN);

            form.setTempDirId(tmpDirId);

            pathUtil.createTempDir(reqMdl, GSConstBulletin.PLUGIN_ID_BULLETIN, tmpDirId);

        }
    }
    /**
     * <br>[機  能] テンポラリディレクトリ削除
     * <br>[解  説]
     * <br>[備  考] 登録処理完了時に実行
     * @param req リクエスト
     * @param form テンポラリディレクトリ利用画面 フォーム、パラメータモデル
     */
    protected void _deleteBulletinTempDir(HttpServletRequest req, ITempDirIdUseable form) {
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        if (!StringUtil.isNullZeroString(form.getTempDirId())) {
            //テンポラリディレクトリのファイルを削除する
            pathUtil.deleteTempPath(getRequestModel(req),
                    GSConstBulletin.PLUGIN_ID_BULLETIN,
                    form.getTempDirId());
            log__.debug("テンポラリディレクトリのファイル削除");
        }
    }

}
