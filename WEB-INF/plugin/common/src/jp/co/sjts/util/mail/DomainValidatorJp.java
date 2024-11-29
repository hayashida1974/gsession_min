package jp.co.sjts.util.mail;



/**
 * <br>[機  能] ドメインチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class DomainValidatorJp  {

    /** 追加TLD一覧 */
    private String[] tldPlus__ = null;
    /** ローカルTLDの許可 */
    private boolean allowLocal__ = false;

    /**
     * Singleton instance of this validator, which
     *  doesn't consider local addresses as valid.
     */
    private static final DomainValidatorJp DOMAIN_VALIDATOR = new DomainValidatorJp(false);

    /**
     * Singleton instance of this validator, which does
     *  consider local addresses valid.
     */
    private static final DomainValidatorJp
        DOMAIN_VALIDATOR_WITH_LOCAL = new DomainValidatorJp(true);

    /**
     * Returns the singleton instance of this validator. It
     *  will not consider local addresses as valid.
     * @return the singleton instance of this validator
     */
    public static synchronized DomainValidatorJp getInstance() {
        return DOMAIN_VALIDATOR;
    }

    /**
     * Returns the singleton instance of this validator,
     *  with local validation as required.
     * @param allowLocal Should local addresses be considered valid?
     * @return the singleton instance of this validator
     */
    public static synchronized DomainValidatorJp getInstance(boolean allowLocal) {
       if (allowLocal) {
          return DOMAIN_VALIDATOR_WITH_LOCAL;
       }
       return DOMAIN_VALIDATOR;
    }

    /**
     * <p>コンストラクタ
     * @param allowLocal ローカルドメインの許可
     */
    private DomainValidatorJp(boolean allowLocal) {
        allowLocal__ = allowLocal;
    }

    /**
     * <br>[機  能] 指定されたドメインのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return 判定結果
     */
    public boolean isValid(String domain) {
        DomainValidator domainValidator = DomainValidator.getInstance(allowLocal__);
        return domainValidator.isValidPlus(domain); // 追加分のみチェック
    }

    /**
     * <br>[機  能] 指定されたトップレベルドメインのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tld トップレベルドメイン
     * @return 判定結果
     */
    public boolean isValidTld(String tld) {
        if (tldPlus__ == null) {
            return true; // リストを使用しない場合はSKIP
        }

        DomainValidator domainValidator = DomainValidator.getInstance(allowLocal__);
        return domainValidator.isValidTldPlus(tld); // 追加分のみチェック
    }

    /**
     * <br>[機  能] 許可するTLDの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tldPlus 追加するTLD許可リスト
     */
    public void setTldList(String[] tldPlus) {
        DomainValidator.clearTLDOverrides(); // 一旦初期化

        tldPlus__ = tldPlus;
        if (tldPlus__ != null) {
            DomainValidator.updateTLDOverride(DomainValidator.ArrayType.GENERIC_PLUS, tldPlus__);
        }
    }
}
