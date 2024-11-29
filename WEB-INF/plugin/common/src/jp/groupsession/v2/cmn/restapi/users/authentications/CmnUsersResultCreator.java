package jp.groupsession.v2.cmn.restapi.users.authentications;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import jp.co.sjts.util.CloneableUtil;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.restapi.users.CmnUsersDao;
/**
 *
 * <br>[機  能] ユーザ情報レスポンスモデル生成
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
import jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel;
import jp.groupsession.v2.usr.GSConstUser;
public class CmnUsersResultCreator {
    /**
     *
     * <br>[機  能] ビルダー 兼 パラメータモデルクラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder {
        /** コネクション*/
        Connection con__;
        /** リザルトモデル生成対象 ユーザSID*/
        List<Integer> usrSidList__ = new ArrayList<>();
        /** リザルトモデル生成対象 ユーザ詳細情報*/
        LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap__ = new LinkedHashMap<>();
        /** リザルトモデル生成対象 ユーザ情報*/
        Map<Integer, CmnUsrmModel> usrmMap__ = new HashMap<>();
        /** リザルトモデル生成対象 役職情報*/
        Map<Integer, CmnPositionModel> posMap__ = new HashMap<>();
        /** リザルトモデル生成対象 役職情報*/
        Map<Integer, CmnTdfkModel> tdfkMap__ = new HashMap<>();
        /** 閲覧者ユーザSID*/
        int accsessUsrSid__;
        /**
         *
         * <br>[機  能] ユーザ情報詳細情報をセット
         * <br>[解  説]
         * <br>[備  考]
         * @param usrInf ユーザ情報詳細情報
         * @return this
         */
        public Builder putUsrInf(Collection<CmnUsrmInfModel> usrInf) {
            usrInf.stream()
                .forEach(u -> usrmInfMap__.put(u.getUsrSid(), u));
            return this;
        }
        /**
         *
         * <br>[機  能] レスポンスに追加するユーザのSIDを追加
         * <br>[解  説] ユーザ情報はレスポンス生成時に取得される
         * <br>[備  考]
         * @param usrSids ユーザSID
         * @return this
         */
        public Builder putUsrSid(Collection<Integer> usrSids) {
            usrSids.stream()
            .forEach(sid -> {
                usrSidList__.add(sid);
                usrmInfMap__.put(sid, null);
            });
            return this;
        }

        /**
         * <p>閲覧者ユーザSID をセットします。
         * @param accsessUsrSid 閲覧者ユーザSID
         * @return this
         */
        public Builder setAccsessUsrSid(int accsessUsrSid) {
            accsessUsrSid__ = accsessUsrSid;
            return this;
        }
        /**
         *
         * <br>[機  能] インスタンス生成
         * <br>[解  説]
         * <br>[備  考]
         * @return CmnUsersResultCreator
         * @throws SQLException SQL実行時例外
         */
        public CmnUsersResultCreator build() throws SQLException {
            Builder ret = new Builder();
            CloneableUtil.copyField(ret, this);
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(ret.con__);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            ret.usrmInfMap__ = new LinkedHashMap<>(this.usrmInfMap__);
            ret.usrmInfMap__.putAll(
                    new CmnUsrmInfDao(ret.con__)
                    .getUsersInfList(ret.usrSidList__
                            .stream()
                            .map(sid -> Integer.toString(sid))
                            .collect(Collectors.toList())
                            .toArray(new String[ret.usrSidList__.size()]),
                            sortMdl)
                    .stream()
                    .collect(Collectors.toMap(
                            u -> u.getUsrSid(),
                            u -> u))
                    );
            ret.usrmMap__ = new CmnUsrmDao(ret.con__)
                    .select(ret.usrmInfMap__.keySet())
                    .stream()
                    .collect(Collectors.toMap(
                            u -> u.getUsrSid(),
                            u -> u
                            ));

            ret.posMap__ = new CmnPositionDao(ret.con__)
                .getPosList(true)
                .stream()
                .collect(Collectors.toMap(
                            pos -> pos.getPosSid(),
                            pos -> pos
                        ));
            ret.tdfkMap__ = new CmnTdfkDao(ret.con__)
                    .select()
                    .stream()
                    .collect(Collectors.toMap(
                            s -> s.getTdfSid(),
                            s -> s
                            ));

            return new CmnUsersResultCreator(ret);
        }
    }

    /**パラメータモデル*/
    Builder param__;
    /** 検索結果*/
    List<CmnUsersResultModel> result__ = new ArrayList<>();

    /**
     *
     * コンストラクタ
     * @param param パラメータモデル
     */
    private CmnUsersResultCreator(Builder param) {
        param__ = param;
    }
    /**
     *
     * <br>[機  能] ビルダー取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ビルダー
     */
    public static Builder builder(Connection con) {
        Builder ret =  new Builder();
        ret.con__ = con;
        return ret;
    }
    /**
     *
     * <br>[機  能] リザルトモデル生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @return リザルトモデルリスト
     * @throws SQLException 所属グループ、またはラベル情報の取得に失敗
     */
    public List<CmnUsersResultModel> execute(Connection con) throws SQLException {
        ArrayList<CmnUsersResultModel> ret = new ArrayList<>();

        for (Entry<Integer, CmnUsrmInfModel> entry: param__.usrmInfMap__.entrySet()) {
            int sid = entry.getKey();
            final CmnUsersResultModel mdl = new CmnUsersResultModel();
            ret.add(mdl);

            Optional.ofNullable(param__.usrmMap__.get(sid))
                .ifPresent(usr -> {
                    mdl.setUserSid(sid);
                    mdl.setUserId(usr.getUsrLgid());
                    mdl.setLoginStopFlg(usr.getUsrUkoFlg());
                    mdl.setAddDateTime(__toTimeStampString(usr.getUsrAdate()));
                    mdl.setEditDateTime(__toTimeStampString(usr.getUsrEdate()));
                                   });
            Optional.ofNullable(entry.getValue())
                .ifPresent(uinf -> {
                    mdl.setSeiText(uinf.getUsiSei());
                    mdl.setMeiText(uinf.getUsiMei());
                    mdl.setSeiKanaText(uinf.getUsiSeiKn());
                    mdl.setMeiKanaText(uinf.getUsiMeiKn());
                    mdl.setSyainNoText(uinf.getUsiSyainNo());
                    mdl.setSyozokuText(uinf.getUsiSyozoku());
                    mdl.setYakusyokuSid(uinf.getPosSid());
                    mdl.setYakusyokuName(
                            Optional.ofNullable(
                                    param__.posMap__.get(
                                            uinf.getPosSid()
                                            )
                                    )
                                .map(p -> p.getPosName())
                                .orElse("")
                            );
                    mdl.setEntranceDate(__toDateString(uinf.getUsiEntranceDate()));
                    mdl.setSeibetuType(uinf.getUsiSeibetu());

                    mdl.setBirthdayKoukaiFlg(uinf.getUsiBdateKf());
                    mdl.setBirthdayDate(__toDateString(uinf.getUsiBdate()));
                    mdl.setMail1KoukaiFlg(uinf.getUsiMail1Kf());
                    mdl.setMail1Text(
                            NullDefault.getString(uinf.getUsiMail1(), ""));
                    mdl.setMail1CommentText(
                            NullDefault.getString(uinf.getUsiMailCmt1(), ""));
                    mdl.setMail2KoukaiFlg(uinf.getUsiMail2Kf());
                    mdl.setMail2Text(
                            NullDefault.getString(uinf.getUsiMail2(), ""));
                    mdl.setMail2CommentText(
                            NullDefault.getString(uinf.getUsiMailCmt2(), ""));
                    mdl.setMail3KoukaiFlg(uinf.getUsiMail3Kf());
                    mdl.setMail3Text(
                            NullDefault.getString(uinf.getUsiMail3(), ""));
                    mdl.setMail3CommentText(
                            NullDefault.getString(uinf.getUsiMailCmt3(), ""));
                    mdl.setZipKoukaiFlg(uinf.getUsiZipKf());
                    mdl.setZip1Text(
                            NullDefault.getString(uinf.getUsiZip1(), ""));
                    mdl.setZip2Text(
                            NullDefault.getString(uinf.getUsiZip2(), ""));
                    mdl.setTodofukenKoukaiFlg(uinf.getUsiTdfKf());
                    mdl.setTodofukenSid(uinf.getTdfSid());
                    mdl.setTodofukenName(
                            Optional.ofNullable(uinf.getTdfSid())
                            .filter(i -> i > 0)
                            .map(i -> param__.tdfkMap__.get(i))
                            .map(t -> t.getTdfName())
                            .orElse("")
                            );
                    mdl.setAddress1KoukaiFlg(uinf.getUsiAddr1Kf());
                    mdl.setAddress1Text(
                            NullDefault.getString(uinf.getUsiAddr1(), ""));
                    mdl.setAddress2KoukaiFlg(uinf.getUsiAddr2Kf());
                    mdl.setAddress2Text(
                            NullDefault.getString(uinf.getUsiAddr2(), ""));

                    mdl.setTel1KoukaiFlg(uinf.getUsiTel1Kf());
                    mdl.setTel1Text(
                            NullDefault.getString(uinf.getUsiTel1(), ""));
                    mdl.setTel1NaisenText(
                            NullDefault.getString(uinf.getUsiTelNai1(), ""));
                    mdl.setTel1CommentText(
                            NullDefault.getString(uinf.getUsiTelCmt1(), ""));
                    mdl.setTel2KoukaiFlg(uinf.getUsiTel2Kf());
                    mdl.setTel2Text(
                            NullDefault.getString(uinf.getUsiTel2(), ""));
                    mdl.setTel2NaisenText(
                            NullDefault.getString(uinf.getUsiTelNai2(), ""));
                    mdl.setTel2CommentText(
                            NullDefault.getString(uinf.getUsiTelCmt2(), ""));
                    mdl.setTel3KoukaiFlg(uinf.getUsiTel3Kf());
                    mdl.setTel3Text(
                            NullDefault.getString(uinf.getUsiTel3(), ""));
                    mdl.setTel3NaisenText(
                            NullDefault.getString(uinf.getUsiTelNai3(), ""));
                    mdl.setTel3CommentText(
                            NullDefault.getString(uinf.getUsiTelCmt3(), ""));

                    mdl.setFax1KoukaiFlg(uinf.getUsiFax1Kf());
                    mdl.setFax1Text(
                            NullDefault.getString(uinf.getUsiFax1(), ""));
                    mdl.setFax1CommentText(
                            NullDefault.getString(uinf.getUsiFaxCmt1(), ""));
                    mdl.setFax2KoukaiFlg(uinf.getUsiFax2Kf());
                    mdl.setFax2Text(
                            NullDefault.getString(uinf.getUsiFax2(), ""));
                    mdl.setFax2CommentText(
                            NullDefault.getString(uinf.getUsiFaxCmt2(), ""));
                    mdl.setFax3KoukaiFlg(uinf.getUsiFax3Kf());
                    mdl.setFax3Text(
                            NullDefault.getString(uinf.getUsiFax3(), ""));
                    mdl.setFax3CommentText(
                            NullDefault.getString(uinf.getUsiFaxCmt3(), ""));

                    mdl.setBikouText(
                            NullDefault.getString(uinf.getUsiBiko(), ""));

                    mdl.setImageBinSid(uinf.getBinSid());
                    mdl.setImageKoukaiFlg(uinf.getUsiPictKf());
                });
        }

        //所属グループ、ラベルを取得
        CmnUsersDao usersDao = new CmnUsersDao(con);
        ret = usersDao.createUserQueryResultList(ret);

        result__.addAll(ret);
        return ret;
    }
    /**
     *
     * <br>[機  能] 取得結果に非公開設定を反映したリストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return リザルトモデルリスト
     */
    public List<CmnUsersResultModel> getMaskingResultList() {
        ArrayList<CmnUsersResultModel> ret = new ArrayList<>();
        for (CmnUsersResultModel userInf: result__) {
            final CmnUsersResultModel mdl = new CmnUsersResultModel();
            ret.add(mdl);
            try {
                BeanUtils.copyProperties(mdl, userInf);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            if (param__.accsessUsrSid__ == userInf.getUserSid()) {
                continue;
            }


            if (mdl.getBirthdayKoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setBirthdayDate(null);
            }
            if (mdl.getMail1KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setMail1Text(null);
                mdl.setMail1CommentText(null);
            }
            if (mdl.getMail2KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setMail2Text(null);
                mdl.setMail2CommentText(null);
            }
            if (mdl.getMail3KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setMail3Text(null);
                mdl.setMail3CommentText(null);
            }
            if (mdl.getZipKoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setZip1Text(null);
                mdl.setZip2Text(null);
            }
            if (mdl.getTodofukenKoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setTodofukenSid(null);
                mdl.setTodofukenName(null);
            }
            if (mdl.getAddress1KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setAddress1Text(null);

            }

            if (mdl.getAddress2KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setAddress2Text(null);
            }

            if (mdl.getTel1KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setTel1Text(null);
                mdl.setTel1NaisenText(null);
                mdl.setTel1CommentText(null);

            }

            if (mdl.getTel2KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setTel2Text(null);
                mdl.setTel2NaisenText(null);
                mdl.setTel2CommentText(null);
            }

            if (mdl.getTel3KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setTel3Text(null);
                mdl.setTel3NaisenText(null);
                mdl.setTel3CommentText(null);
            }
            if (mdl.getFax1KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setFax1Text(null);
                mdl.setFax1CommentText(null);
            }
            if (mdl.getFax2KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setFax2Text(null);
                mdl.setFax2CommentText(null);
            }
            if (mdl.getFax3KoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setFax3Text(null);
                mdl.setFax3CommentText(null);

            }
            if (mdl.getImageKoukaiFlg() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                mdl.setImageBinSid(Long.valueOf(0L));
            }
        }
        return ret;

    }

    /**
     *
     * <br>[機  能] yyyy/MM/dd hh:mm:ss フォーマットへ変換
     * <br>[解  説]
     * <br>[備  考]
     * @param udate
     * @return 日付文字列
     */
    private String __toTimeStampString(UDate udate) {
        if (udate == null) {
            return "";
        }
        return String.format("%s %s",
                UDateUtil.getSlashYYMD(udate),
                UDateUtil.getSeparateHMS(udate));
    }
    /**
    *
    * <br>[機  能] yyyy/MM/dd フォーマットへ変換
    * <br>[解  説]
    * <br>[備  考]
    * @param udate
    * @return 日付文字列
    */
    private String __toDateString(UDate udate) {
        if (udate == null) {
            return "";
        }
        return UDateUtil.getSlashYYMD(udate);
    }
}
