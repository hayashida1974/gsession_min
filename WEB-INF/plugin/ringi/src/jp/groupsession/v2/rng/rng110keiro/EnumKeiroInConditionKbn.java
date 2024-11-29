package jp.groupsession.v2.rng.rng110keiro;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.IEnumMsgkeyValue;

/**
*
* <br>[機  能] 経路使用条件区分定数定義
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public enum EnumKeiroInConditionKbn implements IEnumMsgkeyValue {
   /**所属グループ*/
   CONDKBN_GROUP(0, "cmn.affiliation.group"),
   /**役職*/
   CONDKBN_POS(1, "cmn.post"),
   /**入力内容*/
   CONDKBN_INPUT(2, "rng.rng110.28");

   /** 値 */
   private final int value__;
   /** メッセージキー */
   private final String msgKey__;
   /**
    * <p>value を取得します。
    * @return value
    */
   public int getValue() {
       return value__;
   }

   /**
    * <p>label を取得します。
    * @return msgKey
    */
   public String getMsgKey() {
       return msgKey__;
   }
   /**
    * コンストラクタ
    * @param value 値
    * @param msgKey メッセージキー
    */
   private EnumKeiroInConditionKbn(int value, String msgKey) {
       this.value__ = value;
       this.msgKey__ = msgKey;
   }
   /**
   *
   * <br>[機  能] 数値から列挙型を返す
   * <br>[解  説]
   * <br>[備  考]
   * @param val 選択数値
   * @return 列挙型
   * @throws EnumOutRangeException 列挙型範囲外
   */
  public static EnumKeiroInConditionKbn valueOf(int val)
          throws EnumOutRangeException {
      switch (val) {
      case 0:
          return CONDKBN_GROUP;
      case 1:
          return CONDKBN_POS;
      case 2:
          return CONDKBN_INPUT;
      default:
          throw new EnumUtil.EnumOutRangeException();
      }
  }

}
