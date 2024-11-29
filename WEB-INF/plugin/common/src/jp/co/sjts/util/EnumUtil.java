package jp.co.sjts.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;
/**
 *
 * <br>[機  能] 列挙型ユーティリティクラス
 * <br>[解  説] 列挙型を扱う汎用処理
 * <br>[備  考] インスタンスとして生成すると、値から列挙型を逆検索する機能等を使用可能
 * @param <E> 列挙型
 * @author JTS
 */
public class EnumUtil<E extends Enum<E>>  {
    private Class<E> cls__;
    /** 列挙リスト*/
    private EnumSet<E> enumSet__;
    /** 値からの逆検索用 列挙型Map*/
    private Map<Integer, IEnumMsgkeyValue> mapMsg__;
    /** 出力済みラベルリスト*/
    private List<LabelValueBean> selectList__;
    /** 出力済みラベルリスト*/
    private List<LabelValueBean> selectNameList__;

    /** 列挙型がメッセージキー保存型かどうか*/
    private boolean isEnumMsgkeyValue__ = false;
    /**
     * コンストラクタ
     * @param cls 列挙型クラス情報
     */
    @SuppressWarnings("unchecked")
    public EnumUtil(Class<E> cls) {
        cls__ = cls;
        enumSet__ = EnumSet.allOf(cls);
        if (IEnumMsgkeyValue.class.isAssignableFrom(cls)) {
            isEnumMsgkeyValue__ = true;
            mapMsg__ = EnumUtil.getValueMap((EnumSet<? extends IEnumMsgkeyValue>) enumSet__);
        }
    }
    /**
     *
     * <br>[機  能] セレクト用リスト取得
     * <br>[解  説]
     * <br>[備  考] ただしUNKNOWNを含めない
     * @param gsMsg メッセージクラス
     * @return セレクトリスト
     */
    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getSelectList(GsMessage gsMsg) {
        if (selectList__ != null) {
            return selectList__;
        }
        if (isEnumMsgkeyValue__) {
            selectList__ = EnumUtil.createSelectList(gsMsg,
                    (EnumSet<? extends IEnumMsgkeyValue>) enumSet__);
            return selectList__;
        }
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        return ret;
    }
    /**
     *
     * <br>[機  能] 列挙型の名前で列挙型クラスを返す
     * <br>[解  説] 存在しない名前を指定した場合はエクセプション
     * <br>[備  考]
     * @param name 列挙型名
     * @throws EnumOutRangeException 列挙型範囲外例外
     */
    public E valueOf(String name) throws EnumOutRangeException {
        E ret = null;
        try {
            ret = Enum.valueOf(cls__, name);
        } catch (NullPointerException npe) {
            throw new EnumOutRangeException();
        } catch (IllegalArgumentException iae) {
            throw new EnumOutRangeException();
        }
        return ret;
    }

    /**
    *
    * <br>[機  能] セレクト用リスト取得
    * <br>[解  説] 列挙型の名前をキーに持つラベルリストを返す
    * <br>[備  考] ただしUNKNOWNを含めない
    * @param gsMsg メッセージクラス
    * @return セレクトリスト
    */
    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getSelectNameList(GsMessage gsMsg) {
        if (selectNameList__ != null) {
            return selectNameList__;
        }
        if (isEnumMsgkeyValue__) {
            ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
            for (IEnumMsgkeyValue kbn : (EnumSet<? extends IEnumMsgkeyValue>) enumSet__) {
                ret.add(
                    new LabelValueBean(gsMsg.getMessage(kbn.getMsgKey()),
                            kbn.toString())
                        );
            }
            return ret;
        }
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        return ret;
    }
    /**
     *
     * <br>[機  能] 数値から列挙子クラスを取得
     * <br>[解  説]
     * <br>[備  考] 列挙型にない場合は例外
     * @param value 検索値
     * @return 列挙子
     * @throws EnumOutRangeException 列挙型にない場合
     */
    @SuppressWarnings("unchecked")
    public E valueOf(int value) throws EnumOutRangeException {
        E ret = null;
        if (isEnumMsgkeyValue__) {
            ret = (E) mapMsg__.get(Integer.valueOf(value));
        }
        if (ret == null) {
            throw new EnumOutRangeException();
        }
        return ret;
    }
    /**
    *
    * <br>[機  能] EnumUtil.valueOf が範囲外の場合の例外
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   public static class EnumOutRangeException extends Exception {

   }
    /**
    *
    * <br>[機  能] 選択用ラベルを生成
    * <br>[解  説]
    * <br>[備  考]
    * @param gsMsg メッセージオブジェクト
    * @param enumSet 列挙型セット
    * @return ラベルリスト
    */
   public static List<LabelValueBean>
   createSelectList(GsMessage gsMsg, EnumSet<? extends IEnumMsgkeyValue> enumSet) {
       ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
       for (IEnumMsgkeyValue kbn : enumSet) {
           ret.add(
               new LabelValueBean(gsMsg.getMessage(kbn.getMsgKey()),
                       String.valueOf(kbn.getValue()))
                   );
       }
       return ret;
   }

   /**
    *
    * <br>[機  能] バリューの値をキーとして列挙定数を格納したMapを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param enumSet 列挙型セット
    * @return 列挙ラベル
    */
   public static Map<Integer, IEnumMsgkeyValue>
   getValueMap(EnumSet<? extends IEnumMsgkeyValue> enumSet) {
       HashMap<Integer, IEnumMsgkeyValue> map = new HashMap<Integer, IEnumMsgkeyValue>();
       for (IEnumMsgkeyValue kbn : enumSet) {
           map.put(Integer.valueOf(kbn.getValue()), kbn);
       }
       return map;
   }
   /**
    * <p>enumSet を取得します。
    * @return enumSet
    */
   public EnumSet<E> getEnumSet() {
       return enumSet__;
   }
   /**
    * <p>enumSet をセットします。
    * @param enumSet enumSet
    */
   public void setEnumSet(EnumSet<E> enumSet) {
       enumSet__ = enumSet;
   }


}
