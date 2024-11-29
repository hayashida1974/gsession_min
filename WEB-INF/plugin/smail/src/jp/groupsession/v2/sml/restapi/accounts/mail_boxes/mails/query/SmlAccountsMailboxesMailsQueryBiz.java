package jp.groupsession.v2.sml.restapi.accounts.mail_boxes.mails.query;

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
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlAtesakiNameBiz;
import jp.groupsession.v2.sml.dao.SmailSearchDao;
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
import jp.groupsession.v2.sml.sml090.Sml090SearchParameterModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メール情報検索API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAccountsMailboxesMailsQueryBiz {
    /** 検索実行後から取得可能 検索Hit件数*/
    private int maxCount__ = 0;

    /**
     * <p>maxCont を取得します。
     * @return maxCont
     * @see maxCount__
     */
    public int getMaxCount() {
        return maxCount__;
    }

    /**
     * <br>[機  能] ショートメールリスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUserSid ユーザSID
     * @param param パラメータモデル
     * @return ActionForward フォワード
     * @throws SQLException
     */
    public List<SmlRestapiMailModel> getMailList(
            Connection con, int sessionUserSid,
            SmlAccountsMailboxesMailsQueryPostParamModel param)
            throws SQLException {

        List<SmlRestapiMailModel> resList = new ArrayList<SmlRestapiMailModel>();

        SmlAtesakiNameBiz nameBiz = new SmlAtesakiNameBiz();
        SmailSearchDao searchDao = new SmailSearchDao(con);
        List<SmailModel> smailList = new ArrayList<SmailModel>();
        HashMap<Integer, SmlRestapiMailBodyModel> mailBodyMap = null;
        boolean isMyAc = false;

        // メールボックス
        String smlKbnStr = "-1";

        Sml090SearchParameterModel prmModel = new Sml090SearchParameterModel();
        prmModel.setMySid(param.getAccountSid());
        prmModel.setOffset(param.getOffset());
        prmModel.setLimit(param.getLimit());
        prmModel.setSearchOrderKey1(GSConstSmail.ORDER_KEY_DESC);
        prmModel.setSearchOrderKey2(GSConstSmail.ORDER_KEY_DESC);

        prmModel.setFromDate(param.getFromDateTime());
        prmModel.setToDate(param.getToDateTime());

        // マーク
        if (param.getMarkFlg() >= 0) {
            prmModel.setMailMark(param.getMarkFlg());
        }

        // 差出人
        if (param.getSenderAccountSid() >= 0) {
            prmModel.setSltGroup("0"); // 仮グループSID ※値は実際には使用されない
            prmModel.setSltUser(GSConstSmail.SML_ACCOUNT_STR + String.valueOf(
                    param.getSenderAccountSid()));
        }

        // キーワード
        if (param.getKeywordText() != null && param.getKeywordText().length() > 0) {
            List<String> keywordList = new ArrayList<String>();
            keywordList.add(param.getKeywordText());
            prmModel.setKeywordList(keywordList);

            String[] targets = null;
            if (param.getKeywordTargetType() == 3) { // 検索条件 件名+本文
                targets = new String[2];
                targets[0] = String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE);
                targets[1] = String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN);
            } else if (param.getKeywordTargetType() == 2) {
                targets = new String[1];
                targets[0] = String.valueOf(GSConstSmail.SEARCH_TARGET_HONBUN);
            } else if (param.getKeywordTargetType() == 1) {
                targets = new String[1];
                targets[0] = String.valueOf(GSConstSmail.SEARCH_TARGET_TITLE);
            }
            prmModel.setSearchTarget(targets);
        }

        // 宛先ユーザ(複数)
        if (param.getToAccountSidArray() != null && param.getToAccountSidArray().length > 0) {
            String[] atesakiList = new String[param.getToAccountSidArray().length];
            for (int i = 0; i < param.getToAccountSidArray().length; i++) {
                atesakiList[i] = GSConstSmail.SML_ACCOUNT_STR
                                  + Integer.valueOf(param.getToAccountSidArray()[i]).toString();
            }
            prmModel.setAtesaki(atesakiList);
        }

        //ショートメールSID
        if (param.getSmlSid() > -1) {
            prmModel.setSmlSid(param.getSmlSid());
        }

        if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_INBOX)) {
            // 受信
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_JUSIN;
            prmModel.setMailMode(smlKbnStr);
            maxCount__ = searchDao.getSearchDataCountJushin(prmModel);
            if (param.getOffset() > maxCount__) {
                return List.of();
            }
            smailList = searchDao.getSearchDataJushin(prmModel);

        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_SENT)) {
            // 送信
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_SOSIN;
            prmModel.setMailMode(smlKbnStr);
            maxCount__ = searchDao.getSearchDataCountSoushin(prmModel);
            if (param.getOffset() > maxCount__) {
                return List.of();
            }
            smailList = searchDao.getSearchDataSoushin(prmModel);
            smailList = nameBiz.setSendAtesakiList(smailList);
            isMyAc    = true;
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_DRAFT)) {
            // 草稿
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_SOKO;
            prmModel.setMailMode(smlKbnStr);
            maxCount__ = searchDao.getSearchDataCountSoukou(prmModel);
            if (param.getOffset() > maxCount__) {
                return List.of();
            }
            smailList = searchDao.getSearchDataSoukou(prmModel);
            smailList = nameBiz.setSendAtesakiList(smailList);
            isMyAc    = true;
        } else if (param.getBoxName().equals(GSConstSmail.RESTAPI_MAILBOX_TRASH)) {
            // ゴミ箱
            smlKbnStr = GSConstSmail.TAB_DSP_MODE_GOMIBAKO;
            prmModel.setMailMode(smlKbnStr);
            maxCount__ = searchDao.getSearchDataCountGomiBako(prmModel);
            if (param.getOffset() > maxCount__) {
                return List.of();
            }
            smailList = searchDao.getSearchDataGomiBako(prmModel);
        }

        if (smailList != null && smailList.size() > 0) {
            ArrayList<Integer> sidList     = new ArrayList<Integer>();
            ArrayList<Integer> atesakiSids = new ArrayList<Integer>();
            String sacName   = null;
            int    ukoFlg    = GSConst.YUKOMUKO_YUKO;

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
            for (SmailModel mdl : smailList) {
                if (mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                    sidList.add(mdl.getSmlSid());

                    // 受信メールの場合のみ宛先用メールSID一覧
                    atesakiSids.add(mdl.getSmlSid());
                } else if (mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                        || mdl.getMailKbn().equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                    sidList.add(mdl.getSmlSid());
                }

                if (isMyAc) {
                    // 送信・草稿の場合、差出人情報は自アカウントで固定
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

            // 送信 or 草稿ボックス の場合、全件において宛先データが必要(TO以外)
            if (smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
             || smlKbnStr.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                atesakiSids = sidList;
            }

            // メールSID一覧から情報を個別に取得
            SmlRestapiMailDao dao = new SmlRestapiMailDao(con);
            mailBodyMap = dao.getMailBodyMap(sidList);
            HashMap<Integer, ArrayList<AtesakiModel>> atesakiMap
                                       = dao.getMailAtesakiMap(atesakiSids, smlKbnStr);
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
