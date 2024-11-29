package jp.groupsession.v2.bbs.bbs220;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs070.Bbs070Biz;
import jp.groupsession.v2.bbs.dao.BbsSoukouBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouDao;
import jp.groupsession.v2.bbs.dao.BbsThreSumDao;
import jp.groupsession.v2.bbs.model.BbsSoukouBodyBinModel;
import jp.groupsession.v2.bbs.model.BbsThreSumModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] 掲示板 草稿一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs220Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Bbs220Biz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル*/
    RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Bbs220Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     */
    public Bbs220Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Bbs220Biz() {
    }


    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @param buMdl BaseUserModel
     * @param adminFlg 管理者フラグ
     * @throws Exception 例外処理
     */
    public void setInitData(
            Bbs220ParamModel paramMdl,
            int userSid,
            BaseUserModel buMdl,
            boolean adminFlg) throws Exception {
        log__.debug("init");
        BbsSoukouDao dao = new BbsSoukouDao(con__);
        BbsSoukouBinDao binDao = new BbsSoukouBinDao(con__);
        int sort = paramMdl.getBbs220SortKey();
        int order = paramMdl.getBbs220OrderKey();
        List<BulletinSoukouModel> soukouinf = dao.selectSoukouList(userSid, sort, order);
        List<BulletinSoukouModel> soukouList = new ArrayList<BulletinSoukouModel>();
        BbsBiz biz = new BbsBiz(con__);
        for (BulletinSoukouModel infmdl: soukouinf) {
         // 編集ボタン
         boolean dspButton = false;
         // フォーラム存在チェック
         if (infmdl.getForumName() != null) {
             infmdl.setForumDelFlg(GSConstBulletin.FORUM_DELETE_NO);
             // 編集ボタン表示判定
             dspButton = biz.canEditSoukou(
                     infmdl.getBfiSid(),
                     infmdl.getBtiSid(),
                     infmdl.getBskSoukouType(),
                     buMdl,
                     adminFlg);
         } else {
             infmdl.setForumDelFlg(GSConstBulletin.FORUM_DELETE_YES);
         }
         // 編集ボタン表示チェック
         if (dspButton) {
             infmdl.setEditFlg(GSConstBulletin.SOUKOU_EDITBUTTON_YES);
         } else {
             infmdl.setEditFlg(GSConstBulletin.SOUKOU_EDITBUTTON_NO);
         }
         // 内容設定
         if (infmdl.getBskType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
             String content = infmdl.getBskValue();
             String contentHtml = infmdl.getBskValuePlain();
             //html形式用の内容データは存在しない、text形式用の内容データが存在する場合
             if (StringUtil.isNullZeroStringSpace(contentHtml)
                     && !StringUtil.isNullZeroStringSpace(content)) {
                 Bbs070Biz bbs070Biz = new Bbs070Biz();
                 String fileId = bbs070Biz.getBbsBodytoFileId(content);
                 int fileSid = NullDefault.getInt(fileId, -1);
                 if (fileSid > 0) {
                     infmdl.setContentFileFlg(GSConstBulletin.SOUKOU_DSPTEMPCONTENT_YES);
                     contentHtml =  biz.getBodyImgFilename(infmdl.getBskSid(), fileSid);
                 }
             }

             infmdl.setContent(contentHtml);
         } else {
             infmdl.setContent(infmdl.getBskValue());
         }
         // 草稿がスレッドの場合
         if (infmdl.getBskSoukouType() == GSConstBulletin.SOUKOU_TYPE_THREAD) {
             // 添付ファイル存在チェック
             boolean flg = binDao.existBbsSoukouTmp(infmdl.getBskSid(), userSid);
             if (flg) {
                 infmdl.setFileFlg(GSConstBulletin.SOUKOU_TEMP_YES);
             }
          // 草稿が投稿の場合
         } else {
             // スレッド存在チェック
             if (infmdl.getThreadName() != null) {
                 infmdl.setThredDelFlg(GSConstBulletin.THREAD_DELETE_NO);
                 // 添付ファイル存在チェック
                 BbsThreSumDao sumDao = new BbsThreSumDao(con__);
                 BbsThreSumModel sumMdl = sumDao.select(infmdl.getBtiSid());
                 if (sumMdl == null || sumMdl
                         .getBtsTempflg() == GSConstBulletin.BTS_TEMPFLG_EXIST) {
                     infmdl.setFileFlg(GSConstBulletin.SOUKOU_TEMP_YES);
                 }
             } else {
                 infmdl.setThredDelFlg(GSConstBulletin.THREAD_DELETE_YES);
             }
         }
         soukouList.add(infmdl);
        }
        paramMdl.setBbs220SoukouList(soukouList);
    }


    /**
     * <br>[機  能] 吹き出しアイコンで選択した草稿情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSid
     * @throws SQLException SQL実行例外
     * @return JSONObject
     */
    public JSONObject getSelectSoukouInfo(
            Bbs220ParamModel paramMdl,
            int userSid) throws SQLException {
        log__.debug("getSoukouInfo");

        JSONObject ret = new JSONObject();

        int bskSid = paramMdl.getSoukouSid();
        BbsSoukouDao dao = new BbsSoukouDao(con__);
        BulletinSoukouModel mdl = dao.selectSoukouInfo(userSid, bskSid);

        // 草稿情報を取得できない場合
        if (mdl == null) {
            return ret;
        }
        BulletinSoukouModel infMdl = new BulletinSoukouModel();
        // 草稿種別
        infMdl.setBskSoukouType(mdl.getBskSoukouType());
        // 投稿者設定
        int postKbn = mdl.getBskAgid();
        if (postKbn > 0) {
            //グループ
            CmnGroupmDao grpDao = new CmnGroupmDao(con__);
            CmnGroupmModel grpMdl = grpDao.selectGroup(postKbn);
            if (grpMdl != null) {
                infMdl.setPostJKbn(grpMdl.getGrpJkbn());
                infMdl.setPostName(StringUtilHtml.transToHTmlPlusAmparsant(grpMdl.getGrpName()));
            }
        } else {
            //ユーザ

            CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con__);
            CmnUsrmInfModel usiMdl = usiDao.selectUserNameAndJtkbn(userSid);
            String name = NullDefault.getString(usiMdl.getUsiSei(), "")
                    + "　" + NullDefault.getString(usiMdl.getUsiMei(), "");
            name = StringUtilHtml.transToHTmlPlusAmparsant(name);
            infMdl.setPostName(name);
            infMdl.setPostJKbn(usiMdl.getUsrJkbn());
            infMdl.setPostYukoKbn(usiMdl.getUsrUkoFlg());

        }
        //フォーラムタイトル
        infMdl.setForumName(StringUtilHtml.transToHTmlPlusAmparsant(mdl.getForumName()));

        //スレッドタイトル、重要度設定
        if (mdl.getBskSoukouType() == GSConstBulletin.SOUKOU_TYPE_TOUKOU) {
            infMdl.setThreadName(StringUtilHtml.transToHTmlPlusAmparsant(mdl.getThreadName()));
            infMdl.setJuyoKbn(mdl.getJuyoKbn());
        } else {
            infMdl.setThreadName(StringUtilHtml.transToHTmlPlusAmparsant(mdl.getBskTitle()));
            infMdl.setJuyoKbn(mdl.getBskImportance());
        }

        // 内容設定
        infMdl.setContentKbn(mdl.getBskType());
        if (mdl.getBskType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
            infMdl.setContent(StringUtilHtml.removeIllegalTag(mdl.getBskValue()));
        } else {
            infMdl.setContent(
                    StringUtilHtml.transToHTmlPlusAmparsant(mdl.getBskValue()));
        }

        // 添付ファイル設定
        infMdl.setFilesInfo(dao.getSoukouTmpFileList(bskSid));

        // 掲示期間設定
        infMdl.setBskLimit(mdl.getBskLimit());
        if (mdl.getBskLimit() == GSConstBulletin.THREAD_LIMIT_YES) {
            Calendar cal = Calendar.getInstance();
            cal.set(mdl.getBskLimitFrDate().getYear(),
                    mdl.getBskLimitFrDate().getMonth() - 1,
                    mdl.getBskLimitFrDate().getIntDay());
            StringBuilder strDate = new StringBuilder("");

            strDate.append(UDateUtil.getYymdJ(mdl.getBskLimitFrDate(), reqMdl__));
            strDate.append(" ");
            strDate.append(UDateUtil.getSeparateHMJ(mdl.getBskLimitFrDate(), reqMdl__));
            infMdl.setLimitFrDate(strDate.toString());

            cal = Calendar.getInstance();
            cal.set(mdl.getBskLimitDate().getYear(),
                    mdl.getBskLimitDate().getMonth() - 1,
                    mdl.getBskLimitDate().getIntDay());
            strDate = new StringBuilder("");
            strDate.append(UDateUtil.getYymdJ(mdl.getBskLimitDate(), reqMdl__));
            strDate.append(" ");
            strDate.append(UDateUtil.getSeparateHMJ(mdl.getBskLimitDate(), reqMdl__));
            infMdl.setLimitToDate(strDate.toString());
        }
        paramMdl.setBbs220SelectSoukoInfo(infMdl);

        ret = JSONObject.fromObject(paramMdl);

        return ret;

    }


    /**
     * <br>[機  能]削除する草稿情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return 草稿情報
     * @param,
     */
    public List<BulletinSoukouModel> getDeleteSoukouInfo(
            String[] bskSid,
            int usrSid) throws SQLException {
        List<BulletinSoukouModel> ret = new ArrayList<BulletinSoukouModel>();
        BbsSoukouDao dao = new BbsSoukouDao(con__);
        ret = dao.getDeleteSoukouList(bskSid, usrSid);
        return ret;

    }


    /**
     * <br>[機  能]削除
     * <br>[解  説]
     * <br>[備  考]
     * @param delSoukouInfo 削除草稿情報
     * @throws Exception 例外
     */
    public void deleteSoukou(List<BulletinSoukouModel> delSoukouInfo) throws Exception {
        BbsSoukouDao dao = new BbsSoukouDao(con__);
        BbsSoukouBinDao binDao = new BbsSoukouBinDao(con__);
        BbsSoukouBodyBinDao binBodyDao = new BbsSoukouBodyBinDao(con__);
        Bbs070Biz bbs070Biz = new Bbs070Biz();
        BbsBiz bbsBiz = new BbsBiz(con__);
        for (BulletinSoukouModel delMdl: delSoukouInfo) {
            //バイナリー情報の論理削除
            int binNum = binDao.updateBinfForSoukou(delMdl.getBskSid());
            int binBodyNum = binBodyDao.updateBinfForSoukou(delMdl.getBskSid());
            // 草稿添付の削除
            if (binNum > 0) {
                binDao.deleteSoukouBin(delMdl.getBskSid());
            }
            // 草稿本文添付の削除
            if (binBodyNum > 0) {
                String content = delMdl.getBskValue();
                String contentHtml = delMdl.getBskValuePlain();
                if (StringUtil.isNullZeroStringSpace(contentHtml)
                        && !StringUtil.isNullZeroStringSpace(content)) {
                    String fileId = bbs070Biz.getBbsBodytoFileId(content);
                    int fileSid = NullDefault.getInt(fileId, -1);
                    if (fileSid > 0) {
                        contentHtml = bbsBiz.getBodyImgFilename(delMdl.getBskSid(), fileSid);
                    }
                    delMdl.setBskValuePlain(contentHtml);
                }
                binBodyDao.deleteSoukouBodyBin(delMdl.getBskSid());

            }
            // 草稿削除
            dao.delete(delMdl.getBskSid());
        }
    }

    /**
     * <br>[機  能] 添付ファイルバイナリSIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bskSid 草稿SID
     * @param tmpBinSid 添付ファイルバイナリSID
     * @param usrSid ユーザSID
     * @return 添付ファイル存在確認
     * @throws SQLException SQL実行例外
     */
    public boolean cheTmpBin(
            Connection con, int bskSid,
            Long tmpBinSid, int usrSid)
                    throws SQLException {

        boolean bskCheckFlg = false;

        //添付ファイルバイナリSIDチェック
        BbsSoukouBinDao binDao = new BbsSoukouBinDao(con);
        boolean existForTmpFlg = binDao.existBbsSoukouTmp(bskSid, tmpBinSid, usrSid);
        if (existForTmpFlg) {
            bskCheckFlg = true;
        }
        return bskCheckFlg;
    }


    /**
     * <br>[機  能] 添付ファイルバイナリSIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bskSid 草稿SID
     * @param bsbFileSid ファイルSID
     * @param usrSid ユーザSID
     * @return 添付ファイル存在確認
     * @throws SQLException SQL実行例外
     */
    public boolean cheTmpBodyBin(
            Connection con, int bskSid,
            int bsbFileSid, int usrSid)
                    throws SQLException {

        boolean bskCheckFlg = false;

        //添付ファイルバイナリSIDチェック
        BbsSoukouBodyBinDao binDao = new BbsSoukouBodyBinDao(con);
        boolean existForTmpFlg = binDao.existBbsSoukouBodyTmp(bskSid, bsbFileSid, usrSid);
        if (existForTmpFlg) {
            bskCheckFlg = true;
        }
        return bskCheckFlg;
    }




    /**
     * <br>[機  能] アクセス確認
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @param buMdl BaseUserModel
     * @param adminFlg 管理者フラグ
     * @return true　アクセスOK
     * @throws SQLException SQL実行例外
     */
    public boolean chkAccess(
            Bbs220ParamModel paramMdl,
            int userSid,
            BaseUserModel buMdl,
            boolean adminFlg) throws SQLException {
        BbsBiz biz = new BbsBiz(con__);
        int bskSid = paramMdl.getSoukouSid();
        BbsSoukouDao dao = new BbsSoukouDao(con__);
        BulletinSoukouModel mdl = dao.selectSoukouInfo(userSid, bskSid);
        if (paramMdl.getBbs010forumSid() != mdl.getBfiSid()
                || paramMdl.getThreadSid() != mdl.getBtiSid()) {
            return false;
        }
        return biz.canEditSoukou(
                mdl.getBfiSid(),
                mdl.getBtiSid(),
                mdl.getBskSoukouType(),
                buMdl,
                adminFlg);
    }



    /**
     * <br>[機  能] 登録済みの本文ファイルをTempディレクトリにコピーします
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param postSid 投稿SID
     * @param con コネクション
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 一時ファイル例外
     * @throws IOToolsException IOツール例外
     * @throws IOException IO実行時例外
     */
    public void bbsCopyBodyFileToTemp(
            RequestModel reqMdl, int postSid,
            Connection con, String appRoot, String tempDir)
                    throws SQLException, TempFileException, IOException, IOToolsException {

        //本文ファイルの取得
        BbsSoukouBodyBinDao bbbDao = new BbsSoukouBodyBinDao(con);
        List<BbsSoukouBodyBinModel> bbbModelList =  bbbDao.select(postSid);

        //「bodyFile」の「(ファイルID)」

        //Tempディレクトリへのコピー
        if (bbbModelList != null && bbbModelList.size() > 0) {
            //添付ファイルをテンポラリディレクトリへ移動する

            CommonBiz cmnBiz = new CommonBiz();
            String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
            int fileNum = 0;
            String tempDirBodyFile = null;

            for (BbsSoukouBodyBinModel bbbMdl : bbbModelList) {
                int fileSid = bbbMdl.getBsbFileSid();
                Long binSid = bbbMdl.getBinSid();

                tempDirBodyFile = tempDir + GSConstCommon.EDITOR_BODY_FILE
                        + File.separator + fileSid + File.separator;

                //取得したバイナリSIDからバイナリ情報を取得
                CmnBinfModel binData = cmnBiz.getBinInfoToDomain(con, binSid, reqMdl.getDomain());

                //Tempディレクトリの該当箇所に配置
                cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDirBodyFile, fileNum);
            }
        }

    }

}
