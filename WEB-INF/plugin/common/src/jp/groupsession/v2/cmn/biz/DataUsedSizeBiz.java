package jp.groupsession.v2.cmn.biz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnDatausedDao;
import jp.groupsession.v2.cmn.model.base.CmnDatausedModel;
/**
 *
 * <br>[機  能] GSデータ使用量計算 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DataUsedSizeBiz {
    /** コネクション */
    private final Connection con__;
    /** dao */
    private final CmnDatausedDao dataUsedDao__;

    private final List<CmnDatausedModel> dataUsedList__;
    /**
     * コンストラクタ
     * @param con
     * @param pconfig
     * @throws SQLException SQL実行時例外
     */
    public DataUsedSizeBiz(Connection con) throws SQLException {
        super();
        con__ = con;
        dataUsedDao__ = new CmnDatausedDao(con__);
        dataUsedList__ =
                dataUsedDao__.getDatausedList();
    }

    /**
     * <p>dataUsedList を取得します。
     * @return dataUsedList
     * @see jp.groupsession.v2.cmn.biz.DataUsedSizeBiz#dataUsedList__
     */
    public List<CmnDatausedModel> getDataUsedList() {
        return dataUsedList__;
    }
    /**
     *
     * <br>[機  能] データ使用サイズの合計を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return データサイズの合計
     */
    public long getTotalByteSize() {
        return dataUsedList__.stream()
                .mapToLong(CmnDatausedModel::getCduSize)
                .sum();
    }
    /**
     *
     * <br>[機  能] 単位変換後のサイズ文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param size
     * @return 容量
     */
    public static String changeSizeString(long size) {
        if (size == 0) {
            return String.valueOf(0);
        }

        long divisor = 1024;
        boolean byteFlg = false;
        if (size < 1024 / 10) {
            //Byte
            byteFlg = true;
        } else if (size < 1024 * 1024) {
            //Byte、KB
        } else if (size < 1024 * 1024 * 1024) {
            //MB
            divisor = 1024 * 1024;
        } else {
            //GB
            divisor = 1024 * 1024 * 1024;
        }

        BigDecimal decimalSize = new BigDecimal(size);
        if (byteFlg) {
            decimalSize = decimalSize.divide(new BigDecimal(divisor), 1, RoundingMode.UP);
        } else {
            decimalSize = decimalSize.divide(new BigDecimal(divisor), 1, RoundingMode.HALF_UP);
        }

        return decimalSize.toString();

    }
    /**
     *
     * <br>[機  能] 単位変換後した場合の単位文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param size
     * @return 単位
     */
    public static String sizeUnit(long size) {
        String unitStr = "";
        if (size < 1024 * 1024) {
            //KB
            unitStr = "KB";
        } else if (size < 1024 * 1024 * 1024) {
            //MB
            unitStr = "MB";
        } else {
            //GB
            unitStr = "GB";
        }

        return unitStr;
    }


}
