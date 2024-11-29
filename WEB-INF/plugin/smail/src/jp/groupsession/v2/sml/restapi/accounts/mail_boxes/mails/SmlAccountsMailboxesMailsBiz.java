package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlAtesakiNameBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.restapi.dao.SmlRestapiMailDao;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailAtesakiInfoModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailBodyModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailLabelInfoModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailSendAtesakiInfoModel;
import jp.groupsession.v2.sml.restapi.model.SmlRestapiMailTmpFileInfoModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メール情報API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsMailboxesMailsBiz {
    /** 検索実行後から取得可能 検索Hit件数*/
    private Integer maxCount__ = null;

    /**
     * <br>[機  能] ショートメールリスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx コンテキスト
     * @param param パラメータモデル
     * @return ActionForward フォワード
     * @throws SQLException
     */
    public List<SmlRestapiMailModel> getMailList(
            RestApiContext ctx,
            SmlAccountsMailboxesMailsGetParamModel param)
            throws SQLException {
        Connection con = ctx.getCon();

        List<SmlRestapiMailModel> resList = new ArrayList<SmlRestapiMailModel>();

        SmlAtesakiNameBiz nameBiz = new SmlAtesakiNameBiz();
        SmailDao smlDao = new SmailDao(con);
        List<SmailModel> smailList = new ArrayList<SmailModel>();
        HashMap<Integer, SmlRestapiMailBodyModel> mailBodyMap = null;
        boolean isMyAc = false;

        // メールボックス
        String smlKbnStr = "-1";
        countMax(ctx, param);
        if (param.getOffset() > maxCount__) {
            return List.of();
        }

        if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_INBOX)) {
            // 受信
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_JUSIN;

            smailList = smlDao.selectJmeisList(param.getAccountSid(),
                    param.getOffset(),
                    param.getLimit(),
                    GSConstSmail.MSG_SORT_KEY_DATE,
                    GSConstSmail.ORDER_KEY_DESC,
                    GSConstCommon.NUM_INIT,
                    param.getFromDateTime(),
                    param.getToDateTime());
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_SENT)) {
            // 送信
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_SOSIN;
            smailList = smlDao.selectSmeisList(param.getAccountSid(),
                    param.getOffset(),
                    param.getLimit(),
                    GSConstSmail.MSG_SORT_KEY_DATE,
                    GSConstSmail.ORDER_KEY_DESC,
                    param.getFromDateTime(),
                    param.getToDateTime());
            //宛先を設定
            smailList = nameBiz.setSendAtesakiList(smailList);

            isMyAc = true;
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_DRAFT)) {
            // 草稿
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_SOKO;
            smailList = smlDao.selectWmeisList(param.getAccountSid(),
                    param.getOffset(),
                    param.getLimit(),
                    GSConstSmail.MSG_SORT_KEY_DATE,
                    GSConstSmail.ORDER_KEY_DESC,
                    param.getFromDateTime(),
                    param.getToDateTime());
            //宛先を設定
            smailList = nameBiz.setSendAtesakiList(smailList);
            isMyAc = true;
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_TRASH)) {
            // ゴミ箱
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_GOMIBAKO;
            smailList = smlDao.selectGomibakoList(param.getAccountSid(),
                    param.getOffset(),
                    param.getLimit(),
                    GSConstSmail.MSG_SORT_KEY_DATE,
                    GSConstSmail.ORDER_KEY_DESC,
                    param.getFromDateTime(),
                    param.getToDateTime());
        } else {
            // メールボックス
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_LABEL;
            smailList = smlDao.selectLabelList(param.getAccountSid(),
                    NullDefault.getInt(param.getBoxName(), -1),
                    param.getOffset(),
                    param.getLimit(),
                    GSConstSmail.MSG_SORT_KEY_DATE,
                    GSConstSmail.ORDER_KEY_DESC,
                    param.getFromDateTime(),
                    param.getToDateTime());

        }


        if (smailList != null && smailList.size() > 0) {
            String sacName = null;
            int    ukoFlg  = GSConst.YUKOMUKO_YUKO;

            if (isMyAc) {
                // 自アカウントデータを使用する為、ここでデータ取得
                SmlAccountDao sacDao = new SmlAccountDao(con);
                SmlAccountModel mdl = sacDao.select(param.getAccountSid());
                if (mdl != null) {
                    sacName   = mdl.getSacName();

                    // ユーザSIDが存在する場合、ユーザ無効フラグを取得
                    if (mdl.getUsrSid() > 0) {
                        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
                        CmnUsrmInfModel cuiMdl = cuiDao.selectUserNameAndJtkbn(mdl.getUsrSid());
                        if (cuiMdl != null) {
                            ukoFlg  = cuiMdl.getUsrUkoFlg();
                        }
                    }
                }
            }

            List<Integer> sidList = new ArrayList<Integer>(); // 不足データを取得する為のアカウントSID一覧
            for (SmailModel mdl : smailList) {
                if (mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
                 || mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                 || mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                    sidList.add(mdl.getSmlSid());
                }

                if (isMyAc) {
                    // 送信・草稿の場合、[差出人 = 自アカウント]で固定
                    mdl.setAccountSid(param.getAccountSid());
                    mdl.setAccountName(sacName);
                    mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_NORMAL); // 使用中の自アカウントの為、常に状態区分:有効
                    mdl.setUsrUkoFlg(ukoFlg);
                } else if (!StringUtil.isNullZeroStringSpace(mdl.getUsiSei())
                        && !StringUtil.isNullZeroStringSpace(mdl.getUsiMei())) {
                    // 通常アカウント(ユーザ情報があるアカウント)の場合、ユーザ情報を使用
                    mdl.setAccountName(mdl.getUsiSei() + " " + mdl.getUsiMei());
                    if (mdl.getUsrJkbn() == GSConstUser.USER_JTKBN_DELETE) {
                        mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_DELETE);
                    } else {
                        mdl.setAccountJkbn(GSConstSmail.SAC_JKBN_NORMAL);
                    }
                }
            }

            // メールSID一覧から情報を個別に取得
            SmlRestapiMailDao dao = new SmlRestapiMailDao(con);
            mailBodyMap = dao.getMailBodyMap(sidList);
            HashMap<Integer, ArrayList<AtesakiModel>> atesakiMap
                          = dao.getMailAtesakiMap(sidList, smlKbnStr);
            if (atesakiMap != null) {
                for (SmailModel mdl : smailList) {
                    ArrayList<AtesakiModel> list = atesakiMap.get(mdl.getSmlSid());
                    if (list != null) {
                        // 既存データがある場合、一覧へ追加(送信・草稿は宛先データが取得済みの為)
                        if (mdl.getAtesakiList() != null && mdl.getAtesakiList().size() > 0) {
                            list.addAll(mdl.getAtesakiList());
                        }
                        mdl.setAtesakiList(list);
                    }
                }
            }
        }

        // リザルトモデルリスト作成
        for (SmailModel smlModel : smailList) {
            Integer key = Integer.valueOf(smlModel.getSmlSid());
            // 不足分データの補完
            String bodyStr = null;
            List<CmnBinfModel> retBin  = null;
            int smlType = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;
            if (mailBodyMap != null && mailBodyMap.get(key) != null) {
                SmlRestapiMailBodyModel mailBodyMdl = mailBodyMap.get(key);
                smlType = mailBodyMdl.getMailType();
                bodyStr = mailBodyMdl.getMailBody();
                retBin  = mailBodyMdl.getBinList();
            }

            // メール情報
            SmlRestapiMailModel resMdl = new SmlRestapiMailModel();
            resMdl.setSid(smlModel.getSmlSid());
            resMdl.setMailType(NullDefault.getInt(smlModel.getMailKbn(), 0));
            resMdl.setBodyFormatFlg(smlType);
            resMdl.setOpenFlg(smlModel.getSmjOpkbn());
            resMdl.setReplyFlg(smlModel.getReturnKbn());
            resMdl.setForwardFlg(smlModel.getFwKbn());
            resMdl.setSubjectText(smlModel.getSmsTitle());
            resMdl.setMarkType(smlModel.getSmsMark());
            resMdl.setStatusType(smlModel.getUsrJkbn());
            if (bodyStr != null) {
                resMdl.setBodyText(bodyStr);
            }

            // 差出人アカウント情報
            resMdl.setSenderAccountSid(smlModel.getAccountSid());
            resMdl.setSenderName(smlModel.getAccountName());
            resMdl.setSenderUserDeleteFlg(smlModel.getAccountJkbn());
            resMdl.setSenderLoginStopFlg(smlModel.getUsrUkoFlg());

            // 送信日時
            resMdl.setSendDateTime(__getDateString(smlModel.getSmsSdate()));

            // 送信先情報
            int mailType_sosin = Integer.parseInt(GSConstSmail.TAB_DSP_MODE_SOSIN);
            List<SmlRestapiMailAtesakiInfoModel> atsakiInfoList
                    = new ArrayList<SmlRestapiMailAtesakiInfoModel>();
            List<AtesakiModel>  retAts = smlModel.getAtesakiList();
            if (retAts != null) {
                for (AtesakiModel mdl : retAts) {
                    if (resMdl.getMailType() == mailType_sosin) {
                        SmlRestapiMailSendAtesakiInfoModel atesakiInfoMdl
                                = new SmlRestapiMailSendAtesakiInfoModel();
                        atesakiInfoMdl.setType(mdl.getSmjSendkbn());
                        atesakiInfoMdl.setAccountSid(mdl.getAccountSid());
                        atesakiInfoMdl.setName(mdl.getAccountName());
                        atesakiInfoMdl.setUserDeleteFlg(mdl.getAccountJkbn());
                        atesakiInfoMdl.setLoginStopFlg(mdl.getUsrUkoFlg());

                        //開封日時、転送フラグは送信済みメールのみ設定
                        atesakiInfoMdl.setOpenDateTime(__getDateString(mdl.getSmjOpdate()));
                        atesakiInfoMdl.setMailFwFlg(NullDefault.getInt(mdl.getSmjFwkbn(), 0));
                        atsakiInfoList.add(atesakiInfoMdl);
                    } else {
                        SmlRestapiMailAtesakiInfoModel atesakiInfoMdl
                                = new SmlRestapiMailAtesakiInfoModel();
                        atesakiInfoMdl.setType(mdl.getSmjSendkbn());
                        atesakiInfoMdl.setAccountSid(mdl.getAccountSid());
                        atesakiInfoMdl.setName(mdl.getAccountName());
                        atesakiInfoMdl.setUserDeleteFlg(mdl.getAccountJkbn());
                        atesakiInfoMdl.setLoginStopFlg(mdl.getUsrUkoFlg());

                        atsakiInfoList.add(atesakiInfoMdl);
                    }
                }
            }
            resMdl.setAtesakiArray(atsakiInfoList);

            // ラベル情報
            List<SmlRestapiMailLabelInfoModel> labelInfoList
                    = new ArrayList<SmlRestapiMailLabelInfoModel>();
            List<SmlLabelModel> retLbl = smlModel.getLabelList();
            if (retLbl != null) {
                for (SmlLabelModel mdl : retLbl) {
                    SmlRestapiMailLabelInfoModel labelInfoMdl
                            = new SmlRestapiMailLabelInfoModel();
                    labelInfoMdl.setSid(mdl.getSlbSid());
                    labelInfoMdl.setName(mdl.getSlbName());
                    labelInfoList.add(labelInfoMdl);
                }
            }
            resMdl.setLabelArray(labelInfoList);

            // 添付ファイル情報
            List<SmlRestapiMailTmpFileInfoModel> tmpFileInfoList
                    = new ArrayList<SmlRestapiMailTmpFileInfoModel>();
            if (retBin != null) {
                for (CmnBinfModel mdl : retBin) {
                    SmlRestapiMailTmpFileInfoModel tmpFileInfoMdl
                            = new SmlRestapiMailTmpFileInfoModel();
                    tmpFileInfoMdl.setBinSid(mdl.getBinSid());
                    tmpFileInfoMdl.setFileName(mdl.getBinFileName());
                    tmpFileInfoMdl.setFileSizeByteNum((int) mdl.getBinFileSize());
                    tmpFileInfoList.add(tmpFileInfoMdl);
                }
            }
            resMdl.setTmpFileArray(tmpFileInfoList);

            resList.add(resMdl);
        }
        return resList;
    }

    public int countMax(RestApiContext ctx,
            SmlAccountsMailboxesMailsGetParamModel param) throws SQLException {
        if (maxCount__ != null) {
            return maxCount__;
        }
        Connection con = ctx.getCon();
        SmailDao smlDao = new SmailDao(con);
        int ret = 0;
        if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_INBOX)) {
            // 受信
            ret = smlDao.getJmeisCount(param.getAccountSid(),
                    GSConstCommon.NUM_INIT,
                    param.getFromDateTime(),
                    param.getToDateTime());
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_SENT)) {
            // 送信
            ret = smlDao.getSmeisCount(param.getAccountSid(),
                    param.getFromDateTime(),
                    param.getToDateTime());
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_DRAFT)) {
            // 草稿
            ret = smlDao.getWmeisCount(param.getAccountSid(),
                    param.getFromDateTime(),
                    param.getToDateTime());
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_TRASH)) {
            // ゴミ箱
            ret = smlDao.getGomibakoCount(param.getAccountSid(),
                    param.getFromDateTime(),
                    param.getToDateTime());
        } else {
            // メールボックス
            ret = smlDao.getLabelCount(param.getAccountSid(),
                    NullDefault.getInt(param.getBoxName(), -1),
                    param.getFromDateTime(),
                    param.getToDateTime());

        }
        maxCount__ = ret;
        return ret;
    }

    /**
     * <br>[機  能] 日時文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日時
     * @return 日時文字列
     */
    private String __getDateString(UDate date) {
        if (date == null) {
            return null;
        }

        return UDateUtil.getSlashYYMD(date)
                + "  "
                + UDateUtil.getSeparateHMS(date);
    }
}
