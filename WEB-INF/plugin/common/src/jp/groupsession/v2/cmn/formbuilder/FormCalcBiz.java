package jp.groupsession.v2.cmn.formbuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.Calc.Format;

/**
 *
 * <br>[機  能] 計算処理に必要になるビジネスロジックを提供する
 * <br>[解  説] フォームモデルから直接他の要素へアクセスさせないため（無限ループ防止）
 * <br>[備  考]
 *
 * @author JTS
 */
public final class FormCalcBiz {
    /**フォームIDをキーとする要素Map*/
    private Map<String, List<FormCell>> accsessMap__;
    /**計算処理を行ったアクセサクラスを保管*/
    private Set<FormAccesser> calced__ = new HashSet<>();
    /** 行番号*/
    private int rowNo__ = 0;
    /** エラーメッセージ*/
    private String errorMsg = "計算エラー";
    /** char初期化*/
    private char initChar__ = 0x00;

    /**
     * デフォルトコンストラクタ
     */
    private FormCalcBiz() {

    }
    /**
     *
     * <br>[機  能] イニシャライザ
     * <br>[解  説]
     * <br>[備  考]
     * @param accsessMap アクセスマップ
     * @return インスタンス
     */
    protected static final FormCalcBiz getInstance(
            Map<String, List<FormCell>> accsessMap) {
        FormCalcBiz ret = new FormCalcBiz();
        ret.accsessMap__ = accsessMap;
        return ret;
    }
    /**
     *
     * <br>[機  能] 参照する要素の計算処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param access アクセサクラス
     * @param cell 計算処理を行う要素
     * @param rowNo 行番号
     */
    public void calc(FormAccesser access, FormCell cell, int rowNo) {
        AbstractFormModel body = cell.getBody();
        if (calced__.contains(access)) {
            return;
        }
        if (body != null) {
            calced__.add(access);
            rowNo__ = rowNo;
            body.calc(this);
        }
    }
    /**
     *
     * <br>[機  能] フォームIDで指定した要素の計算結果を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param formID 計算対象 フォームID
     * @param table 同行計算
     * @return 計算結果
     */
    private String __calc(String formID, int table) {
        List<FormCell> targets = accsessMap__.get(formID);
        List<String> calced = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(targets)) {
            if (table == 0) {
                for (int rowNo = 0; rowNo < targets.size(); rowNo++) {
                    FormCell cell = targets.get(rowNo);
                    FormAccesser access = new FormAccesser(cell.getSid(), rowNo);
                    calc(access, cell, rowNo);
                }
                for (int rowNo = 0; rowNo < targets.size(); rowNo++) {
                    FormCell cell = targets.get(rowNo);
                    AbstractFormModel body = cell.getBody();
                    if (body != null) {
                        calced.add(NullDefault.getStringZeroLength(body.getCalced(), "0"));
                    }
                }
            } else {
                FormCell cell;
                if (targets.size() == 1) {
                    cell = targets.get(0);
                } else {
                    cell = targets.get(rowNo__);
                }

                FormAccesser access = new FormAccesser(cell.getSid(), rowNo__);
                calc(access, cell, rowNo__);
                AbstractFormModel body = cell.getBody();
                if (body != null) {
                    calced.add(NullDefault.getStringZeroLength(body.getCalced(), "0"));
                }
            }
        }
        return __sum(calced);
    }
    /**
    *
    * <br>[機  能] 合計を計算
    * <br>[解  説]
    * <br>[備  考]
    * @param calced 計算対象
    * @return 計算結果
    */
   private String __sum(List<String> calced) {
       BigDecimal calc = BigDecimal.ZERO;
       String ret = "";
       try {
           for (String string__ : calced) {
               String addStr = NullDefault.getString(string__, "");
               calc = calc.add(new BigDecimal(addStr));
           }
       } catch (NumberFormatException ne) {
           return ret;
       }
       return calc.toPlainString();
   }

    /**
    *
    * <br>[機  能] 指定のフォーマットリストの計算結果を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param format フォーマト
    * @param keta 桁
    * @param round 丸め方法 0:切り捨て 1:切り上げ 2:四捨五入
    * @param table 同行計算
    * @return 計算結果
    */
   public String calc(String format, int keta, int round, int table) {

       //計算処理
       String sResult = __calcOther(format, table, keta);
       if (!sResult.equals(errorMsg)) {
           //丸め処理
           sResult = __round(sResult, keta, round);
       }
       return sResult;
   }

   /**
    * <br>[機  能] 短縮乗算
    * <br>[解  説]
    * <br>[備  考]
    * @param sSiki 計算式
    * @param sLeft 左側検索文字
    * @param sRight 右側検索文字
    * @return sRet 計算式
    */
   private String __TansyukuMultiplication(String sSiki, String sLeft, String sRight) {
       String sRet = null;
       int nIdx = 0;
       int nleft = 0;
       int nright = 0;
       String sEdit = null;
       int nHensuL = 0;
       int nKakkoL = 0;
       int nHensuR = 0;
       int nKakkoR = 0;
       while (nright != -1) {
           nKakkoL = sSiki.indexOf("(", nIdx);
           nHensuL = sSiki.indexOf("[", nIdx);
           nKakkoR = sSiki.indexOf(")", nIdx);
           nHensuR = sSiki.indexOf("]", nIdx);
           if ((nKakkoL != -1 && nKakkoL < nHensuL) || nHensuL == -1) {
               nleft = nKakkoL;
           } else if ((nHensuL < nKakkoL && nHensuL != -1) || nKakkoL == -1) {
               nleft = nHensuL;
           }
           if ((nKakkoR != -1 && nKakkoR < nHensuR) || nHensuR == -1) {
               nright = nKakkoR;
           } else if ((nHensuR < nKakkoR && nHensuR != -1) || nKakkoR == -1) {
               nright = nHensuR;
           }
           if (nright == -1) {
               if (sEdit != null) {
                   sEdit = sEdit + sSiki.substring(nIdx);
               }
               break;
           }
           char word;
           if (nleft != -1 && nleft < nright) {
               if (nleft != 0) {
                   word = sSiki.charAt(nleft - 1);
               } else {
                   word = initChar__;
               }
               if (Character.isDigit(word)) {
                   if (sEdit == null) {
                       sEdit = sSiki.substring(nIdx, nleft) + "*"
                   + sSiki.substring(nleft, nleft + 1);
                   } else {
                       sEdit = sEdit + sSiki.substring(nIdx, nleft) + "*"
                   + sSiki.substring(nleft, nleft + 1);
                   }
                   nleft += 1;
               } else {
                   nleft += 1;
                   if (sEdit == null) {
                       sEdit = sSiki.substring(nIdx, nleft);
                   } else {
                       sEdit = sEdit + sSiki.substring(nIdx, nleft);
                   }
               }
               nIdx = nleft;
           } else {
               if (nright + 1 == sSiki.length()) {
                   sEdit = sEdit + sSiki.substring(nIdx);
                   break;
               }
               word = sSiki.charAt(nright + 1);
               boolean  bRight = false;
               bRight = Character.isDigit(word);
               if (word == '[' || word == '(') {
                   bRight = true;
               }
               if (bRight) {
                   sEdit = sEdit + sSiki.substring(nIdx, nright + 1) + "*";
               } else {
                   sEdit = sEdit + sSiki.substring(nIdx, nright + 1);
               }
               nIdx = nright + 1;
           }
       }
       if (sEdit != null) {
           sRet = sEdit;
       } else {
           sRet = sSiki;
       }
       return sRet;
   }

   /**
    *
    * <br>[機  能] その他の計算を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param format 計算式
    * @param table 同行計算
    * @param keta 桁
    * @return 計算結果
    */
   private String __calcOther(String format, int table, int keta) {

       String sRet = null;
       String sSiki = null;

       //()短縮乗算処理
       format = __TansyukuMultiplication(format, "[", "]");
       //formIdから値の取得
       int nStart = -1;
       int nFinish = 0;
       int nStrIdx = 0;
       int nFinIdx = 0;
       while (nStart != 0) {
           nStart = format.indexOf("[", nStrIdx) + 1;
           if (nStart == 0) {
               if (sSiki == null) {
                   sSiki = format.substring(nFinish);
               } else {
                   sSiki = sSiki + format.substring(nFinish);
               }
               break;
           }

           if (nStrIdx == 0) {
               sSiki = format.substring(0, nStart);
           } else if (nStrIdx < nStart) {
               sSiki = sSiki + format.substring(nStrIdx, nStart);
           }
           nFinish = format.indexOf("]", nFinIdx);
           String sFormId = format.substring(nStart, nFinish);
           sSiki = sSiki + __calc(sFormId, table);
           nStrIdx = nFinish + 1;
           nFinIdx = nFinish + 2;
       }
       sSiki = sSiki.replace("[", "");
       sSiki = sSiki.replace("]", "");

       //先頭マイナス処理
       String sMin = sSiki.replace("(", "");
       if (sMin.indexOf("-") == 0) {
           sSiki = "0" + sSiki;
       }

       //括弧の計算
       String sBrackets = sSiki;
       boolean bBracketsLoop = true;
       //入れ子括弧用ループ
       while (bBracketsLoop) {
           bBracketsLoop = false;
           sSiki = sBrackets;
           sBrackets = null;
           int nLeft = -1;
           int nRight = 0;
           int nLefIdx = 0;
           int nRigIdx = 0;
           String sSave = null;

           //括弧内を検索し、先に計算を行う
           while (nLeft != 0) {
               nLeft = sSiki.indexOf("(", nLefIdx) + 1;
               if (nLeft == 0) {
                   if (sBrackets == null) {
                       sBrackets = sSiki.substring(nRight);
                   } else {
                       sBrackets = sBrackets + sSiki.substring(nRight);
                   }
                   break;
               }
               if (nLefIdx == 0) {
                   sBrackets = sSiki.substring(0, nLeft);
               }
               int nLeftSec = sSiki.indexOf("(", nLeft);
               nRight = sSiki.indexOf(")", nRigIdx);
               if (nLeft < nRight && nLefIdx != 0 && nLefIdx < nLeft) {
                   sBrackets = sBrackets + sSiki.substring(nLefIdx, nLeft);
               }
               if (nLeft < nRight && (nLeftSec > nRight || nLeftSec == -1)) {
                   //計算
                   String sKekka = __calcFour(sSiki.substring(nLeft, nRight), keta);
                   if (sKekka.equals(errorMsg)) {
                       return sKekka;
                   }
                   //追加
                   sBrackets = sBrackets.substring(0, sBrackets.length() - 1);
                   if (sSave != null) {
                       sBrackets = sBrackets + sSave;
                       sSave = null;
                   }
                   sBrackets = sBrackets + sKekka;
                   nLefIdx = nRight + 1;
                   nRigIdx = nRight + 1;
               } else if (nLeftSec < nRight && nLeftSec > -1) {
                   bBracketsLoop = true;
                   if (sSave == null) {
                       sSave = sSiki.substring(nLeft, nLeftSec);
                   } else {
                       sSave = sSave + sSiki.substring(nLeft, nLeftSec);
                   }
                   nLefIdx = nLeftSec;
                   nRigIdx = nLeftSec;
               } else {
                   nLefIdx = nLeft;
                   nRigIdx = nRight;
               }
           }
       }
       sBrackets = sBrackets.replace("(", "");
       sBrackets = sBrackets.replace(")", "");

       //計算
       sRet = __calcFour(sBrackets, keta);
       return sRet;
   }

   /**
    * 四則演算を行う
    * <br>[機  能]
    * <br>[解  説]
    * <br>[備  考]
    * @param siki 計算式
    * @param keta 桁
    * @return sRet 結果
    */
   private String __calcFour(String siki, int keta) {

       if (siki == null) {
           return errorMsg;
       }

       //先頭マイナス処理
       String sMin = siki.replace("(", "");
       if (sMin.indexOf("-") == 0) {
           siki = "0" + siki;
       }
       String sRet = null;
       List<Format> formatList = new ArrayList<Format>();
       String[] sNum = siki.split("[-+*/]");
       int nIdx = 0;
       for (int idx = 0; idx < sNum.length; idx++) {
           Format format = new Format();
           if (idx > 0) {
               int nPlu = siki.indexOf(Format.OPR_PLUS, nIdx);
               int nMin = siki.indexOf(Format.OPR_MINUS, nIdx);
               int nMuln = siki.indexOf(Format.OPR_MUL, nIdx);
               int nPer = siki.indexOf(Format.OPR_PER, nIdx);
               if (nPlu == -1) {
                   nPlu = 10000;
               }
               if (nMin == -1) {
                   nMin = 10000;
               }
               if (nMuln == -1) {
                   nMuln = 10000;
               }
               if (nPer == -1) {
                   nPer = 10000;
               }
               if (nPlu < nMin && nPlu < nMuln && nPlu < nPer) {
                   format.setSiki(Format.OPR_PLUS);
                   nIdx = nPlu + 1;
               } else if (nMin < nPlu && nMin < nMuln && nMin < nPer) {
                   format.setSiki(Format.OPR_MINUS);
                   nIdx = nMin + 1;
               } else if (nMuln < nPlu && nMuln < nMin && nMuln < nPer) {
                   format.setSiki(Format.OPR_MUL);
                   nIdx = nMuln + 1;
               } else if (nPer < nPlu && nPer < nMin && nPer < nMuln) {
                   format.setSiki(Format.OPR_PER);
                   nIdx = nPer + 1;
               }
           }
           format.setValue(sNum[idx]);
           formatList.add(format);
       }
       //乗算除算を行う
       BigDecimal nBef = BigDecimal.ZERO;
       BigDecimal nAft = BigDecimal.ZERO;
       BigDecimal calc = BigDecimal.ZERO;
       boolean bOper = false;
       String sOperator = Format.OPR_PLUS;
       for (int idx = 0; idx < formatList.size(); idx++) {
           if (idx != 0) {
               //符号
               if (!bOper) {
                   sOperator = formatList.get(idx).getSiki();
               }
               //数値がセットされていない場合
               //演算子が続いている場合5/-1など
               if (formatList.get(idx).getValue().equals("")
                       || formatList.get(idx).getValue().length() == 0) {
                   bOper = true;
                   continue;
               }
               if (!bOper) {
                   nAft = new BigDecimal(formatList.get(idx).getValue());
               } else {
                   nAft = new BigDecimal("-" + formatList.get(idx).getValue());
               }
               int scale = keta + 3;
               boolean bEdit = false;
               if (sOperator.equals(Format.OPR_MUL)) {
                   calc = nBef.multiply(nAft);
                   bEdit = true;
               } else if (sOperator.equals(Format.OPR_PER)) {
                   if (nAft.compareTo(BigDecimal.ZERO) == 0) {
                       return errorMsg;
                   }
                   calc = nBef.divide(nAft, scale, BigDecimal.ROUND_DOWN);
                   bEdit = true;
               }
               if (bEdit && !bOper) {
                   Format format = new Format();
                   format.setSiki(formatList.get(idx - 1).getSiki());
                   format.setValue(calc.toString());
                   formatList.set(idx - 1, format);
                   formatList.remove(idx);
                   idx -= 1;
               } else if (bEdit && bOper) {
                   Format format = new Format();
                   format.setSiki(formatList.get(idx - 2).getSiki());
                   format.setValue(calc.toString());
                   formatList.set(idx - 2, format);
                   formatList.remove(idx);
                   formatList.remove(idx - 1);
                   idx -= 2;
               }
           }
           bOper = false;
           nBef = new BigDecimal(formatList.get(idx).getValue());
       }
       BigDecimal dAns = null;

       //加算減算を行う
       String operator = Format.OPR_PLUS;
       for (int idx = 0; idx < formatList.size(); idx++) {
           if (idx == 0) {
               dAns = new BigDecimal(formatList.get(idx).getValue());
           } else {
               if (formatList.get(idx).getValue().length() == 0) {
                   operator = formatList.get(idx).getSiki();
                   continue;
               }
               BigDecimal dValue = new BigDecimal(formatList.get(idx).getValue());
               if (formatList.get(idx).getSiki().equals(Format.OPR_PLUS)) {
                   dAns = dAns.add(dValue);
                   operator = Format.OPR_PLUS;
               } else if (formatList.get(idx).getSiki().equals(Format.OPR_MINUS)) {
                   if (operator.equals(Format.OPR_MINUS)) {
                       dAns = dAns.add(dValue);
                   } else {
                       dAns = dAns.subtract(dValue);
                   }
                   operator = Format.OPR_PLUS;
               }
           }
       }
       if (dAns == null) {
           return "";
       }
       sRet = dAns.toString();
       return sRet;
   }

    /**
     *
     * <br>[機  能] 計算リスト内の内掛け算割り算部を計算し計算リストを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param formatList 計算リスト
     * @param table 同行計算
     * @return 更新後計算リスト
     */
    private List<Format>  __calcMulPer(List<Format> formatList, int table) {
        List<Format> ret = new ArrayList<Format>(formatList);
        for (ListIterator<Format> it = ret.listIterator(); it.hasNext();) {
            Format right = it.next();
            String opr = right.getSiki();
            if (Format.OPR_MUL.equals(opr)
                    || Format.OPR_PER.equals(opr)) {
                Format left = null;
                it.previous();
                if (!it.hasPrevious()) {
                    it.next();
                    continue;
                }
                left = it.previous();
                BigDecimal lcalc = BigDecimal.ZERO;
                if (left.getType() == 0) {
                    lcalc = new BigDecimal(left.getValue());
                } else {
                    lcalc = new BigDecimal(__calc(left.getValue(), table));
                }
                BigDecimal rcalc = BigDecimal.ZERO;
                if (right.getType() == 0) {
                    rcalc = new BigDecimal(right.getValue());
                } else {
                    rcalc = new BigDecimal(__calc(right.getValue(), table));
                }
                int lScale = lcalc.scale();
                int rScale = rcalc.scale();
                int scale = Math.max(lScale, rScale);
                BigDecimal calc = BigDecimal.ZERO;
                switch (opr) {
                    case Format.OPR_MUL:
                        calc = lcalc.multiply(rcalc, new MathContext(scale));
                        break;
                    case Format.OPR_PER:
                        calc = lcalc.divide(rcalc, scale, BigDecimal.ROUND_DOWN);
                        break;
                    case Format.OPR_PLUS:
                        break;
                    case Format.OPR_MINUS:
                        break;
                    default:
                }
                Format result = new Format();
                result.setSiki(left.getSiki());
                result.setType(0);
                result.setValue(calc.toPlainString());
                it.add(result);
                it.next();
                it.remove();
                it.next();
                it.remove();
            }
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] 指定のフォーマットリストの計算結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param formatList フォーマトリスト
     * @param allOpr 全てのフォーマットに同じ計算を適用する場合
     * @param table 同行計算
     * @return 計算結果
     */
    private String __calc(List<Format> formatList, String allOpr, int table) {
        if (CollectionUtils.isEmpty(formatList)) {
            return "";
        }

        List<Format> work = new ArrayList<Format>(formatList);

        //数式中の掛け算割り算を先に計算
        work = new ArrayList<Format>(formatList);
        if (StringUtil.isNullZeroString(allOpr)) {
            work = __calcMulPer(work, table);
        }

        BigDecimal calc = BigDecimal.ZERO;
        boolean isFirst = true;
        try {
            for (Format format : work) {
                BigDecimal right = BigDecimal.ZERO;
                if (format.getType() == 0) {
                    right = new BigDecimal(format.getValue());
                } else {
                    right = new BigDecimal(__calc(format.getValue(), table));
                }
                if (isFirst) {
                    calc = right;
                    isFirst = false;
                    continue;
                }
                int lScale = calc.scale();
                int rScale = right.scale();
                int scale = Math.max(lScale, rScale);
                String opr = format.getSiki();
                if (!StringUtil.isNullZeroString(allOpr)) {
                    opr = allOpr;
                }
                switch (opr) {
                    case Format.OPR_PLUS:
                        calc = calc.add(right);
                        break;
                    case Format.OPR_MINUS:
                        right = right.multiply(new BigDecimal(-1));
                        calc = calc.add(right);
                        break;
                    case Format.OPR_MUL:
                        calc = calc.multiply(right, new MathContext(scale));
                        break;
                    case Format.OPR_PER:
                        calc = calc.divide(right, new MathContext(scale));
                        break;
                    default:
                }

            }
        } catch (NumberFormatException ne) {
            return "";
        } catch (ArithmeticException ae) {
            return "";
        }
        return calc.toPlainString();
    }

    /**
     *
     * <br>[機  能] 指定のフォーマットリストの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param formatList フォーマトリスト
     * @param keta 桁
     * @param round 丸め方法 0:切り捨て 1:切り上げ 2:四捨五入
     * @param table 同行計算
     * @return 計算結果
     */
    public String sum(List<Format> formatList, int keta, int round, int table) {
        //計算処理
        String sResult = __calc(formatList, Format.OPR_PLUS, table);

        //丸め処理
        sResult = __round(sResult, keta, round);

        return sResult;
    }

    /**
     *
     * <br>[機  能] 丸め処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sResult 元値
     * @param keta 桁数
     * @param round 丸め方法 0:切り捨て 1:切り上げ 2:四捨五入
     * @return 丸め値
     */
    private String __round(String sResult, int keta, int round) {

        String sRet = sResult;

        int index = sResult.indexOf(".");
        if (index == -1) {
            if (keta != 0) {
                DecimalFormat format = new DecimalFormat("#.#");
                format.setMinimumFractionDigits(keta);
                format.setMaximumFractionDigits(keta);
                BigDecimal number = new BigDecimal(sResult);
                sRet = format.format(number);
            }
            return sRet;
        }
        int nKeta = sResult.substring(index + 1).length();
        if (keta >= nKeta) {
            return sRet;
        }
        BigDecimal dResult = new BigDecimal(sResult);
        int roundStatus = 0;
        if (round == 0) {
            //切り捨て
            roundStatus = BigDecimal.ROUND_DOWN;
        } else if (round == 1) {
            //切り上げ
            roundStatus = BigDecimal.ROUND_UP;
        } else {
            //四捨五入
            roundStatus = BigDecimal.ROUND_HALF_UP;
        }
        dResult = dResult.setScale(keta, roundStatus);
        sRet = dResult.toString();

        return sRet;
    }

}
