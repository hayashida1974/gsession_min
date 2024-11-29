package jp.groupsession.v2.cmn;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
/**
 *
 * <br>[機  能] DB接続数ログクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSDBConCntLogger {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GSDBConCntLogger.class);
    /** ロギングクラス 接続数記録用 */
    private static Log infolog__ = LogFactory.getLog("GSDBConCnt");
    /** ロギングクラス 接続数警告用 */
    private static Log warnlog__ = LogFactory.getLog("GSDBConWarn");
    /** 警告対象ドメインセット*/
    private static final HashSet<String[]> WARN_DOMAIN__ = new HashSet<String[]>();
    /** 警告対象使用率最大*/
    private static final AtomicInteger MAX_CONRATE__ = new AtomicInteger(0);
    /** 警告対象閾値設定*/
    private static final Properties PROPERTY__ = new Properties();
    /** 設定値キー 使用率閾値*/
    private static final String CONFKEY_GSDBCONWARN_HORIZON = "GSDBCONWARN_HORIZON";
    /**
     *
     * <br>[機  能] 指定ドメインのDBコネクション数ログを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param gsdomain GSドメイン
     */
    public static void outputCntCon(String gsdomain) {

        try {
            DataSource ds = GroupSession.getResourceManager().getDataSource(gsdomain);

            int[] conCnts = JDBCUtil.getConCnt(ds);
            int aconcnt = conCnts[0];
            int iconcnt = conCnts[1];

            int maxaconcnt = conCnts[2];
            BigDecimal conRate = BigDecimal.valueOf(aconcnt);
            conRate = conRate.multiply(BigDecimal.valueOf(100));
            if (maxaconcnt > 0) {
                conRate = (conRate.divide(BigDecimal.valueOf(maxaconcnt), 0, RoundingMode.DOWN));
            } else {
                conRate = BigDecimal.valueOf(100);
            }
            Set<String[]> warnSet = Collections.synchronizedSet(WARN_DOMAIN__);
            synchronized (PROPERTY__) {
                infolog__.info("" + maxaconcnt + "," + aconcnt
                        + "," + iconcnt + "," + conRate.toPlainString());
                int horizon =
                        NullDefault.getInt(
                                String.valueOf(
                                        PROPERTY__.get(CONFKEY_GSDBCONWARN_HORIZON)),
                        101);
                //使用率が閾値を超える場合ドメインを記録
                if (conRate.intValue() >= horizon) {
                    if (conRate.intValue() > MAX_CONRATE__.get()) {
                        MAX_CONRATE__.set(conRate.intValue());
                    }
                    warnSet.add(new String[] {gsdomain, String.valueOf(conRate.intValue())});
                }

            }


        } catch (Exception e) {
            log__.error("コネクションログ出力例外", e);
        }
    }
    /**
     *
     * <br>[機  能] DBコネクション数警告を出力する
     * <br>[解  説]
     * <br>[備  考]
     */
    public static void outputWarn() {
        Set<String[]> warnSet = Collections.synchronizedSet(WARN_DOMAIN__);
        if (warnSet.size() <= 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(MAX_CONRATE__.get());
        for (String[] domainInfo : warnSet) {
            sb.append(",");
            sb.append(domainInfo[0]);
            sb.append(",");
            sb.append(domainInfo[1]);
        }
        warnlog__.info(sb.toString());
        warnSet.clear();
    }
    /**
     *
     * <br>[機  能] 閾値設定を再読み込みする
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションパス
     */
    public static void reloadProperty(String appRootPath) {
        synchronized (PROPERTY__) {
            String filePath = appRootPath;
            filePath += "/WEB-INF/conf/";
            File confFile = new File(IOTools.replaceFileSep(filePath + "dbcon_cntlog.conf"));
            try {
                PROPERTY__.load(Files.newBufferedReader(confFile.toPath()));
            } catch (IOException | SecurityException | IllegalArgumentException e) {
                return;
            }
        }
    }

}
