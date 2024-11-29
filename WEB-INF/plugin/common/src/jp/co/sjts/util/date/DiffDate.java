package jp.co.sjts.util.date;

import java.io.Serializable;

/**
 * <br>[機  能] 日付の差を格納するオブジェクト
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class DiffDate implements Serializable {

    /** 年 */
    private int year__ = 0;
    /** 月 */
    private int month__ = 0;
    /** 日 */
    private int day__ = 0;
    /** 時 */
    private int hour__ = 0;
    /** 分 */
    private int minuts__ = 0;
    /** 秒 */
    private int seconds__ = 0;
    /** ミリ秒 */
    private int millis__ = 0;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public DiffDate() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] ミリ秒を指定してインスタンスを生成します
     * <br>[備  考]
     * @param millis ミリ秒
     */
    public DiffDate(long millis) {
        setAuto(millis);
    }

    /**
     * <br>[機  能] 日を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 日
     */
    public int getDay() {
        return day__;
    }

    /**
     * <br>[機  能] 時を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 時
     */
    public int getHour() {
        return hour__;
    }

    /**
     * <br>[機  能] 分を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 分
     */
    public int getMinuts() {
        return minuts__;
    }

    /**
     * <br>[機  能] 月を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 月
     */
    public int getMonth() {
        return month__;
    }

    /**
     * <br>[機  能] 秒を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 秒
     */
    public int getSeconds() {
        return seconds__;
    }

    /**
     * <br>[機  能] 年を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 年
     */
    public int getYear() {
        return year__;
    }

    /**
     * <br>[機  能] 日をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 日
     */
    public void setDay(int i) {
        day__ = i;
    }

    /**
     * <br>[機  能] 時間をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 時
     */
    public void setHour(int i) {
        hour__ = i;
    }

    /**
     * <br>[機  能] 分をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 分
     */
    public void setMinuts(int i) {
        minuts__ = i;
    }

    /**
     * <br>[機  能] 月をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 月
     */
    public void setMonth(int i) {
        month__ = i;
    }

    /**
     * <br>[機  能] 秒をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 秒
     */
    public void setSeconds(int i) {
        seconds__ = i;
    }

    /**
     * <br>[機  能] 年をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 年
     */
    public void setYear(int i) {
        year__ = i;
    }

    /**
     * <br>[機  能] ミリ秒から自動で年からミリ秒までをセットします
     * <br>[解  説]
     * <br>[備  考] 1年を365日、1月を30日で計算します
     * @param i ミリ秒
     */
    public void setAuto(final long i) {
        long mil = i;

        long y = 365;
        long m = 30;
        long h = 24;
        long mm = 60;
        long s = 60 * 1000;

        //年　(1年365日で算出)
        long ymil = (y * h * mm * s);
//        System.out.println("mil = " + mil);
//        System.out.println("ymil = " + ymil);
        if (ymil < mil) {
            year__ = Math.abs((int) (mil / ymil));
            mil = mil - (year__ * ymil);
        }

        //月 (1ヶ月30日で算出)
        long mmil = (m * h * mm * s);
        if (mmil < mil) {
            month__ = Math.abs((int) (mil / mmil));
        }

        //日
        mil = mil - month__ * mmil;
        long dmil = (h * mm * s);
        if (dmil < mil) {
            day__ = Math.abs((int) (mil / dmil));
            mil = mil - (day__ * dmil);
        }

        //時
        long hmil = (mm * s);
        if (hmil < mil) {
            hour__ = Math.abs((int) (mil / hmil));
            mil = mil - (hour__ * hmil);
        }

        //分
        long mmmil = s;
        if (mmmil < mil) {
            minuts__ = Math.abs((int) (mil / mmmil));
            mil = mil - (minuts__ * mmmil);
        }

        //秒
        long smil = 1000;
        if (smil < mil) {
            seconds__ = Math.abs((int) (mil / smil));
        }
    }

    /**
     * <br>[機  能] このオブジェクトの文字列形式を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 文字列形式
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("年=").append(year__).append(",");
        buf.append("月=").append(month__).append(",");
        buf.append("日=").append(day__).append(",");
        buf.append("時=").append(hour__).append(",");
        buf.append("分=").append(minuts__).append(",");
        buf.append("秒=").append(seconds__).append(",");
        buf.append("ミリ秒=").append(millis__);
        return buf.toString();
    }

    /**
     * <br>[機  能] ミリ秒を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return ミリ秒
     */
    public int getMillis() {
        return millis__;
    }

    /**
     * <br>[機  能] ミリ秒をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i ミリ秒
     */
    public void setMillis(int i) {
        millis__ = i;
    }

}
