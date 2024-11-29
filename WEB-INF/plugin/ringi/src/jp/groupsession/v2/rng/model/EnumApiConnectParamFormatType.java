package jp.groupsession.v2.rng.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.rng.biz.exception.RngParameterBindException;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.UserUtil;

/**
 * <br>[機  能] 列挙型 稟議API連携フォーマット
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumApiConnectParamFormatType {

    /** 未設定 */
    @Params(value = 0, labelMsgKey = "cmn.notset")
    DEFAULT,
    /** ユーザID */
    @Params(value = 100, labelMsgKey = "cmn.user.id")
    USER_ID,
    /** ユーザ姓 名*/
    @Params(value = 101, labelMsgKey = "rng.rng090.25")
    USER_SEIMEI,
    /** ユーザ姓 */
    @Params(value = 102, labelMsgKey = "rng.rng090.26")
    USER_SEI,
    /** ユーザ名 */
    @Params(value = 103, labelMsgKey = "rng.rng090.27")
    USER_MEI,
    /** ユーザ姓名（カナ） */
    @Params(value = 104, labelMsgKey = "rng.rng090.28")
    USER_SEIMEI_KN,
    /** ユーザ姓（カナ） */
    @Params(value = 105, labelMsgKey = "rng.rng090.29")
    USER_SEI_KN,
    /** ユーザ名（カナ） */
    @Params(value = 106, labelMsgKey = "rng.rng090.30")
    USER_MEI_KN,
    /** 社員/職員番号 */
    @Params(value = 107, labelMsgKey = "cmn.employee.staff.number")
    USER_NO,
    /** 所属 */
    @Params(value = 108, labelMsgKey = "cmn.affiliation")
    USER_SYOZOKU,
    /** 役職 */
    @Params(value = 109, labelMsgKey = "cmn.post")
    USER_YAKUSYOKU,
    /** 性別 */
    @Params(value = 110, labelMsgKey = "user.123")
    USER_SEX,
    /** 入社年月日(西暦 /区切りフォーマット)*/
    @Params(value = 111, labelMsgKey = "user.122")
    USER_NYUSYADAY,
    /** 生年月日(西暦/区切りフォーマット） */
    @Params(value = 112, labelMsgKey = "user.120")
    USER_BIRTHDAY,
    /** メールアドレス1 */
    @Params(value = 113, labelMsgKey = "cmn.mailaddress1")
    USER_MAIL1,
    /** メールアドレス2 */
    @Params(value = 114, labelMsgKey = "cmn.mailaddress2")
    USER_MAIL2,
    /** メールアドレス3 */
    @Params(value = 115, labelMsgKey = "cmn.mailaddress3")
    USER_MAIL3,
    /** 郵便番号(ハイフン付き) */
    @Params(value = 116, labelMsgKey = "cmn.postalcode")
    USER_POSTALCODE,
    /** 住所 */
    @Params(value = 118, labelMsgKey = "cmn.address")
    USER_ADDR,
    /** 電話番号1 */
    @Params(value = 119, labelMsgKey = "cmn.tel1")
    USER_TEL1,
    /** 電話番号2 */
    @Params(value = 120, labelMsgKey = "cmn.tel2")
    USER_TEL2,
    /** 電話番号3 */
    @Params(value = 121, labelMsgKey = "cmn.tel3")
    USER_TEL3,
    /** FAX1 */
    @Params(value = 122, labelMsgKey = "address.adr020.10")
    USER_FAX1,
    /** FAX2 */
    @Params(value = 123, labelMsgKey = "address.adr020.11")
    USER_FAX2,
    /** FAX3 */
    @Params(value = 124, labelMsgKey = "address.adr020.12")
    USER_FAX3,


    /** グループID */
    @Params(value = 201, labelMsgKey = "cmn.group.id")
    GROUP_ID,
    /** グループ名 */
    @Params(value = 202, labelMsgKey = "cmn.group.name")
    GROUP_NAME,

    /** yyyy/mm/dd hh:mm */
    @DateTimeFormat("yyyy/MM/dd HH:mm")
    @Params(value = 301, labelMsgKey = "rng.rng090.31")
    DATETIME_SLUSH,
    /** yyyy-mm-dd hh-mm */
    @DateTimeFormat("yyyy-MM-dd HH-mm")
    @Params(value = 302, labelMsgKey = "rng.rng090.32")
    DATETIME_CORON,
    /** yyyymmdd hhmm */
    @DateTimeFormat("yyyyMMdd HHmm")
    @Params(value = 303, labelMsgKey = "rng.rng090.33")
    DATETIME_TYOKKETU,
    /** yyyy/mm/dd */
    @DateTimeFormat("yyyy/MM/dd")
    @Params(value = 304, labelMsgKey = "rng.rng090.34")
    DATE_SLUSH,
    /** yyyy-mm-dd */
    @DateTimeFormat("yyyy-MM-dd")
    @Params(value = 305, labelMsgKey = "rng.rng090.35")
    DATE_HIHUN,
    /** yyyymmdd */
    @DateTimeFormat("yyyyMMdd")
    @Params(value = 306, labelMsgKey = "rng.rng090.36")
    DATE_TYOKKETU,
    /** yyyy */
    @DateTimeFormat("yyyy")
    @Params(value = 307, labelMsgKey = "rng.rng090.37")
    YEAR_SEIREKI,
    /** yy(年の下2桁） */
    @DateTimeFormat("yy")
    @Params(value = 308, labelMsgKey = "rng.rng090.38")
    YEAR_SEIREKI2KETA,
    /** mm */
    @DateTimeFormat("MM")
    @Params(value = 309, labelMsgKey = "rng.rng090.39")
    MONTH,
    /** dd */
    @DateTimeFormat("dd")
    @Params(value = 310, labelMsgKey = "rng.rng090.40")
    DAY,
    /** hh:mm */
    @DateTimeFormat("HH:mm")
    @Params(value = 311, labelMsgKey = "rng.rng090.41")
    TIME_CORON,
    /** hh-mm */
    @DateTimeFormat("HH-mm")
    @Params(value = 312, labelMsgKey = "rng.rng090.42")
    TIME_HIHUN,
    /** hhmm */
    @DateTimeFormat("HHmm")
    @Params(value = 313, labelMsgKey = "rng.rng090.43")
    TIME_TYOKKETU,
    /** hh */
    @DateTimeFormat("HH")
    @Params(value = 314, labelMsgKey = "rng.rng090.44")
    HOUR,
    /** mm */
    @DateTimeFormat("mm")
    @Params(value = 315, labelMsgKey = "rng.rng090.45")
    MINUTES;

    /** ユーザ フォーマットセット*/
    public static final EnumSet<EnumApiConnectParamFormatType> SET_FOR_USER =
        EnumSet.of(USER_ID,
                 USER_SEIMEI,
                 USER_SEI,
                 USER_MEI,
                 USER_SEIMEI_KN,
                 USER_SEI_KN,
                 USER_MEI_KN,
                 USER_NO,
                 USER_SYOZOKU,
                 USER_YAKUSYOKU,
                 USER_SEX,
                 USER_NYUSYADAY,
                 USER_BIRTHDAY,
                 USER_MAIL1,
                 USER_MAIL2,
                 USER_MAIL3,
                 USER_POSTALCODE,
                 USER_ADDR,
                 USER_TEL1,
                 USER_TEL2,
                 USER_TEL3,
                 USER_FAX1,
                 USER_FAX2,
                 USER_FAX3);

    /** グループ フォーマットセット*/
    public static final EnumSet<EnumApiConnectParamFormatType> SET_FOR_GROUP =
            EnumSet.of(GROUP_ID,
                 GROUP_NAME);

    /** DATETIME フォーマットセット*/
    public static final EnumSet<EnumApiConnectParamFormatType> SET_FOR_DATETIME =
            EnumSet.of(DATETIME_SLUSH,
                DATETIME_CORON,
                DATETIME_TYOKKETU,
                DATE_SLUSH,
                DATE_HIHUN,
                DATE_TYOKKETU,
                YEAR_SEIREKI,
                YEAR_SEIREKI2KETA,
                MONTH,
                DAY,
                TIME_CORON,
                TIME_HIHUN,
                TIME_TYOKKETU,
                HOUR,
                MINUTES);
    /** DATE フォーマットセット*/
    public static final EnumSet<EnumApiConnectParamFormatType> SET_FOR_DATE =
            EnumSet.of(DATE_SLUSH,
                DATE_HIHUN,
                DATE_TYOKKETU,
                YEAR_SEIREKI,
                YEAR_SEIREKI2KETA,
                MONTH,
                DAY);

    /** TIME フォーマットセット*/
    public static final EnumSet<EnumApiConnectParamFormatType> SET_FOR_TIME =
            EnumSet.of(TIME_CORON,
                TIME_HIHUN,
                TIME_TYOKKETU,
                HOUR,
                MINUTES);
    /** 日時フォーム名*/
    public static final String FORM_NAME_DATETIME = "datetime";

    /** 入力用 フォーマットマップ*/
    public static final Map
    <String, EnumSet<EnumApiConnectParamFormatType>> MAP_FORMATSET =
        Map.of(
                FORM_NAME_DATETIME,
                SET_FOR_DATETIME,
                EnumFormModelKbn.date.name(),
                SET_FOR_DATE,
                EnumFormModelKbn.time.name(),
                SET_FOR_TIME,
                EnumFormModelKbn.user.name(),
                SET_FOR_USER,
                EnumFormModelKbn.group.name(),
                SET_FOR_GROUP
                );

        /** 値検索用インデクスマップ*/
    private static Map<Integer, EnumApiConnectParamFormatType> valIndexMap__ =
            Stream.of(EnumApiConnectParamFormatType.values())
            .collect(Collectors.toMap(
                    EnumApiConnectParamFormatType::getValue,
                    e -> e));

    /**
     *
     * <br>[機  能] 列挙型初期化アノテーション
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface Params {
        /** @return 値 */
        int value();
        /** @return ラベル表示用メッセージキー*/
        String labelMsgKey();
    }

    /**
     *
     * <br>[機  能] 列挙型初期化アノテーション
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface DateTimeFormat {
        /** @return 値 */
        String value();
    }


    /**値*/
    private final int value__;
    /**ラベル表示メッセージキー*/
    private final String labelMsgKey__;

    /**
     *
     * コンストラクタ
     */
    EnumApiConnectParamFormatType() {
        Params ann;
        Field[] fields = getClass().getFields();
        ann = fields[ordinal()].getAnnotation(Params.class);
        value__ = ann.value();
        labelMsgKey__ = ann.labelMsgKey();
    }
    /**
     * <p>labelMsgKey を取得します。
     * @return labelMsgKey
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamFormatType#labelMsgKey__
     */
    public String getLabelMsgKey() {
        return labelMsgKey__;
    }
    /**
     * <p>value を取得します。
     * @return value
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamFormatType#value__
     */
    public int getValue() {
        return value__;
    }
    /**
     *
     * <br>[機  能] 値に一致する 列挙型を返す
     * <br>[解  説] 一致しない場合 null
     * <br>[備  考]
     * @param val 列挙型 値
     * @return 列挙型 稟議API連携フォーマット
     */
    public static EnumApiConnectParamFormatType valueOf(int val) {
        return valIndexMap__.get(val);
    }

    /**
     *
     * <br>[機  能] 日付からフォーマッタに基づいて文字列値を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @return 日付文字列
     * @throws RngParameterBindException
     */
    public String dateString(UDate date) throws RngParameterBindException {

        SimpleDateFormat formater;
        try {
            DateTimeFormat ann;
            Field[] fields = getClass().getFields();
            ann = fields[ordinal()].getAnnotation(DateTimeFormat.class);
            formater = new SimpleDateFormat(ann.value());
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new RngParameterBindException();
        }

        return formater.format(date.toJavaUtilDate());
    }
    /**
     *
     * <br>[機  能] フォーマッタに基づいて文字列値を変換し返す
     * <br>[解  説] ユーザ、グループは検索のためコネクションが必要
     * <br>[備  考] デフォルトフォーマッタは変換せずに値を返す
     * @param value 文字列値
     * @param con DBコネクション
     * @param locale ロケール
     * @return 変換した文字列値
     * @throws SQLException SQL実行時例外
     * @throws NumberFormatException 不正な入力値変換
     * @throws RngParameterBindException
     */
    public String dataString(String value, Connection con, Locale locale)
            throws NumberFormatException,
            SQLException, RngParameterBindException {
        //日付関連データタイプ時
        DateTimeFormat ann;
        try {
            Field[] fields = getClass().getFields();
            ann = fields[ordinal()].getAnnotation(DateTimeFormat.class);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new RngParameterBindException();
        }
        if (ann != null) {
            UDate date;

            try {
                SimpleDateFormat formater;
                formater = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                date = UDate.getInstanceDate(formater.parse(value));
                return dateString(date);
            } catch (ParseException e) {
            }
            try {
                SimpleDateFormat formater;
                formater = new SimpleDateFormat("yyyy/MM/dd");
                date = UDate.getInstanceDate(formater.parse(value));
                return dateString(date);
            } catch (ParseException e) {
            }
            try {
                SimpleDateFormat formater;
                formater = new SimpleDateFormat("HH:mm");
                date = UDate.getInstanceDate(formater.parse(value));
                return dateString(date);
            } catch (ParseException e) {
            }
            throw new RngParameterBindException();
        }

        //ユーザデータ
        if (SET_FOR_USER.contains(this)) {
            int sid;
            try {
                sid = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                throw new RngParameterBindException();
            }
            return __userInfo(sid, con, locale);
        }
        //グループデータ
        if (SET_FOR_GROUP.contains(this)) {
            int sid;
            try {
                sid = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                throw new RngParameterBindException();
            }
            return __groupInfo(sid, con);
        }

        return value;
    }
    /**
     *
     * <br>[機  能] ユーザSIDからフォーマッタに従った値に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param sid ユーザSID
     * @param con DBコネクション
     * @param locale ロケール
     * @return 変換した文字列値
     * @throws SQLException SQL実行時例外
     * @throws RngParameterBindException  未設定のデータ参照例外
     */
    private String __userInfo(
            int sid,
            Connection con,
            Locale locale) throws SQLException, RngParameterBindException {
        CmnUsrmDao uDao = new CmnUsrmDao(con);
        CmnUsrmInfDao uiDao = new CmnUsrmInfDao(con);
        CmnUsrmModel uMdl = uDao.select(sid);
        CmnUsrmInfModel uiMdl = uiDao.select(sid);
        CmnPositionDao posDao = new CmnPositionDao(con);
        CmnTdfkDao tdDao = new CmnTdfkDao(con);
        StringBuilder sb = new StringBuilder();
        GsMessage gsMsg = new GsMessage(locale);
        if (uMdl == null || uiMdl == null) {
            throw new RngParameterBindException();
        }
        SimpleDateFormat formater;
        formater = new SimpleDateFormat("yyyy/MM/dd");

        switch (this) {
        case USER_ID:
            return uMdl.getUsrLgid();
        case USER_SEIMEI:
            return UserUtil.makeName(uiMdl.getUsiSei(), uiMdl.getUsiMei());
        case USER_SEI:
            return uiMdl.getUsiSei();
        case USER_MEI:
            return uiMdl.getUsiMei();
        case USER_SEIMEI_KN:
            return UserUtil.makeName(uiMdl.getUsiSeiKn(), uiMdl.getUsiMeiKn());
        case USER_SEI_KN:
            return uiMdl.getUsiSeiKn();
        case USER_MEI_KN:
            return uiMdl.getUsiMeiKn();
        case USER_NO:
            return uiMdl.getUsiSyainNo();
        case USER_SYOZOKU:
            return uiMdl.getUsiSyozoku();
        case USER_YAKUSYOKU:
            return posDao.getPosInfo(uiMdl.getPosSid())
                    .getPosName();
        case USER_SEX:
            if (uiMdl.getUsiSeibetu() == GSConstUser.SEIBETU_MAN) {
                return gsMsg.getMessage("user.124");
            }
            if (uiMdl.getUsiSeibetu() == GSConstUser.SEIBETU_WOMAN) {
                return gsMsg.getMessage("user.125");
            }
            return gsMsg.getMessage("cmn.notset");
        case USER_NYUSYADAY:
            if (uiMdl.getUsiEntranceDate() == null) {
                return null;
            }
            return formater.format(uiMdl.getUsiEntranceDate().toJavaUtilDate());
        case USER_BIRTHDAY:
            if (uiMdl.getUsiEntranceDate() == null) {
                return null;
            }
            return formater.format(uiMdl.getUsiBdate().toJavaUtilDate());
        case USER_MAIL1:
            return uiMdl.getUsiMail1();
        case USER_MAIL2:
            return uiMdl.getUsiMail2();
        case USER_MAIL3:
            return uiMdl.getUsiMail3();
        case USER_POSTALCODE:
            if (uiMdl.getUsiZip1() == null
            && uiMdl.getUsiZip2() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiZip1() != null) {
                sb.append(uiMdl.getUsiZip1());
            }
            if (uiMdl.getUsiZip2() != null) {
                if (sb.length() > 0) {
                    sb.append("-");
                }
                sb.append(uiMdl.getUsiZip2());
            }
            return sb.toString();
        case USER_ADDR:
            if (uiMdl.getTdfSid() == 0
            && uiMdl.getUsiAddr1() == null
            && uiMdl.getUsiAddr2() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getTdfSid() > 0) {
                try {
                    sb.append(
                    tdDao.select(
                            uiMdl.getTdfSid()
                            ).getTdfName()
                        );
                } catch (NullPointerException e) {
                }
            }
            if (uiMdl.getUsiAddr1() != null) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(uiMdl.getUsiAddr1());
            }
            if (uiMdl.getUsiAddr2() != null) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(uiMdl.getUsiAddr2());
            }
            return sb.toString();
        case USER_TEL1:
            if (uiMdl.getUsiTel1() == null
            && uiMdl.getUsiTelNai1() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiTel1() != null) {
                sb.append(uiMdl.getUsiTel1());
            }
            return sb.toString();

        case USER_TEL2:
            if (uiMdl.getUsiTel2() == null
            && uiMdl.getUsiTelNai2() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiTel2() != null) {
                sb.append(uiMdl.getUsiTel2());
            }
            return sb.toString();
        case USER_TEL3:
            if (uiMdl.getUsiTel3() == null
            && uiMdl.getUsiTelNai3() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiTel3() != null) {
                sb.append(uiMdl.getUsiTel3());
            }
            return sb.toString();
        case USER_FAX1:
            if (uiMdl.getUsiFax1() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiFax1() != null) {
                sb.append(uiMdl.getUsiFax1());
            }
            return sb.toString();
        case USER_FAX2:
            if (uiMdl.getUsiFax2() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiFax2() != null) {
                sb.append(uiMdl.getUsiFax2());
            }
            return sb.toString();
        case USER_FAX3:
            if (uiMdl.getUsiFax3() == null) {
                throw new RngParameterBindException();
            }
            if (uiMdl.getUsiFax3() != null) {
                sb.append(uiMdl.getUsiFax3());
            }
            return sb.toString();
        default:
            return "" + sid;
        }
    }
    /**
     *
     * <br>[機  能] グループSIDからフォーマッタに従った値に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param sid グループSID
     * @param con DBコネクション
     * @return 変換した文字列値
     * @throws SQLException SQL実行時例外
     * @throws RngParameterBindException  未設定のデータ参照例外
     */
    private String __groupInfo(int sid,
            Connection con) throws SQLException, RngParameterBindException {
        CmnGroupmDao gDao = new CmnGroupmDao(con);
        CmnGroupmModel mdl = gDao.select(sid);

        if (mdl == null) {
            throw new RngParameterBindException();
        }
        switch (this) {
        case GROUP_ID:
            return mdl.getGrpId();
        case GROUP_NAME:
            return mdl.getGrpName();
        default:
            return "" + sid;

        }
    }

    /**
     *
     * <br>[機  能] Api連携設定 フォーマット用 ラベルリストを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param locale ロケール
     * @return Api連携設定 フォーマット用 ラベルリスト
     */
    public static Map<String, Collection<LabelValueBean>> createRacFormatLabelMap(Locale locale) {
        GsMessage gsMsg = new GsMessage(locale);
        return MAP_FORMATSET
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> {
                            return entry.getValue().stream()
                                .map(num -> new LabelValueBean(
                                        gsMsg.getMessage(
                                                num.getLabelMsgKey()
                                                ),
                                        String.valueOf(num.getValue())))
                                .collect(Collectors.toList());
                            }));

    }


}
