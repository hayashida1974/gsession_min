package jp.co.sjts.util.date;

import java.text.ParseException;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;

/**
 * <br>[機 能] 時間(hh:MM:ssのみ)を表すオブジェクト
 * <br>[解 説] 24時は0時として設定してください
 * <br>[備 考]
 * @author JTS
 */
public class UTime {
    /** 時間(2桁) */
    private int hh__ = 0;
    /** 分(2桁) */
    private int mM__ = 0;
    /** 秒(2桁) */
    private int ss__ = 0;

    /**
     * <p>現在の時間で、新規にUTimeオブジェクトを生成します
     */
    public UTime() {
        //
        UDate ud = new UDate();
        hh__ = ud.getIntHour();
        mM__ = ud.getIntMinute();
        ss__ = ud.getIntSecond();
    }

    /**
     * <p>StringよりUTimeオブジェクトを生成する<br>
     * フォーマットが不正な場合はNullを返す<br>
     * フォーマットは[hh:MM] or [hh:MM:ss]
     * @param target 指定フォーマットの文字列
     * @return 生成したUTime
     * @throws ParseException フォーマットが不正な場合にスロー
     */
    public static UTime getInstance(String target) throws ParseException {
        List<String> at = StringUtil.split(":", target);
        if (at.size() == 3 || at.size() == 2) {
        } else {
            throw new ParseException("フォーマットが不正です", 0);
        }
        String tmpHh = (String) at.get(0);
        String tmpMm = (String) at.get(1);
        String tmpSs = "0";
        if (at.size() == 3) {
            tmpSs = (String) at.get(2);
        }
        //入力チェック
        //時間
        if (!ValidateUtil.isNumber(tmpHh)) {
            throw new ParseException("フォーマットが不正です(hh)", 1);
        }
        int h = Integer.parseInt(tmpHh);
        if (h > 23) {
            throw new ParseException("23時を超える値は設定できません", 1);
        }
        //分
        if (!ValidateUtil.isNumber(tmpMm)) {
            throw new ParseException("フォーマットが不正です(MM)", 2);
        }
        int m = Integer.parseInt(tmpMm);
        if (h > 59) {
            throw new ParseException("59分を超える値は設定できません", 2);
        }
        //秒
        if (!ValidateUtil.isNumber(tmpSs)) {
            throw new ParseException("フォーマットが不正です(ss)", 3);
        }
        int s = Integer.parseInt(tmpSs);
        if (s > 59) {
            throw new ParseException("59秒を超える値は設定できません", 3);
        }

        UTime ut = new UTime();
        ut.setHour(h);
        ut.setMinute(m);
        ut.setSecond(s);
        return ut;
    }
    /**
     * <p>時間をセットする
     * @param h 時間
     */
    public void setHour(int h) {
        hh__ = h;
    }
    /**
     * <p>時間を返す
     * @return 時間
     */
    public int getHour() {
        return hh__;
    }
    /**
     * <p>時間を文字列(0サプレス)で返す
     * @return 時間を返す
     */
    public String getStrHour() {
        return StringUtil.toDecFormat(hh__, "00");
    }
    /**
     * <p>分をセットする
     * @param m 分
     */
    public void setMinute(int m) {
        mM__ = m;
    }
    /**
     * <p>分を返す
     * @return 分
     */
    public int getMinute() {
        return mM__;
    }
    /**
     * <p>分を文字列(0サプレス)で返す
     * @return 分を返す
     */
    public String getStrMinute() {
        return StringUtil.toDecFormat(mM__, "00");
    }
    /**
     * <p>秒をセットする
     * @param s 秒
     */
    public void setSecond(int s) {
        ss__ = s;
    }
    /**
     * <p>秒を返す
     * @return 秒
     */
    public int getSecond() {
        return ss__;
    }
    /**
     * <p>秒を文字列(0サプレス)で返す
     * @return 秒
     */
    public String getStrSecond() {
        return StringUtil.toDecFormat(ss__, "00");
    }
    /**
     * <p>このオブジェクトの文字列形式を返します
     * @return hh:MM:ss文字列
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStrHour());
        sb.append(":");
        sb.append(getStrMinute());
        sb.append(":");
        sb.append(getStrSecond());
        return sb.toString();
    }

    /**
     * long形式(hhMMss)を返します
     * @return hhMMss
     */
    public long toLong() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStrHour());
        sb.append(getStrMinute());
        sb.append(getStrSecond());
        return Long.parseLong(sb.toString());
    }

    /**
     * <p>本日の日付でUDateを返します
     * @return UDate
     */
    public UDate toUDate() {
        UDate date = new UDate();
        date.setHour(hh__);
        date.setMinute(mM__);
        date.setSecond(ss__);
        return date;
    }
}
