package jp.groupsession.v2.wml.restapi.destination_lists;

import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;

public class WmlDestinationListsResultModel {

    /** 送信先リストSID */
    private Integer sid__;
    /** 送信先リスト名 */
    private String name__;
    /** ユーザ情報 */
    @ChildElementName("userInfo")
    private List<UserInfo> userArray__;
    
    public static class UserInfo {
        /** ユーザID */
        private Integer userId__;
        /** 送信先 姓 */
        private String seiText__;
        /** 送信先 名 */
        private String meiText__;
        /** 送信先 姓カナ */
        private String seiKanaText__;
        /** 送信先 名カナ */
        private String meiKanaText__;
        /** 送信先メールアドレス */
        private String mailAddressText__;
        /** ログイン停止フラグ */
        private Integer loginStopFlg__;
        /** 役職コード */
        private String yakusyokuId__;
        /** 役職名 */
        private String yakusyokuName__;
        
        public Integer getUserId() {
            return userId__;
        }
        public void setUserId(Integer userId) {
            userId__ = userId;
        }
        public String getSeiText() {
            return seiText__;
        }
        public void setSeiText(String seiText) {
            seiText__ = seiText;
        }
        public String getMeiText() {
            return meiText__;
        }
        public void setMeiText(String meiText) {
            meiText__ = meiText;
        }
        public String getSeiKanaText() {
            return seiKanaText__;
        }
        public void setSeiKanaText(String seiKanaText) {
            seiKanaText__ = seiKanaText;
        }
        public String getMeiKanaText() {
            return meiKanaText__;
        }
        public void setMeiKanaText(String meiKanaText) {
            meiKanaText__ = meiKanaText;
        }
        public String getYakusyokuId() {
            return yakusyokuId__;
        }
        public void setYakusyokuId(String yakusyokuId) {
            yakusyokuId__ = yakusyokuId;
        }
        public String getYakusyokuName() {
            return yakusyokuName__;
        }
        public void setYakusyokuName(String yakusyokuName) {
            yakusyokuName__ = yakusyokuName;
        }
        public Integer getLoginStopFlg() {
            return loginStopFlg__;
        }
        public void setLoginStopFlg(Integer loginStopFlg) {
            loginStopFlg__ = loginStopFlg;
        }
        public String getMailAddressText() {
            return mailAddressText__;
        }
        public void setMailAddressText(String mailAddressText) {
            mailAddressText__ = mailAddressText;
        }
    }

    /** アドレス情報 */
    @ChildElementName("addressInfo")
    private List<AddressInfo> addressArray__;

    public static class AddressInfo {
        /** アドレスSID */
        private Integer addressSid__;
        /** 送信先 姓 */
        private String seiText__;
        /** 送信先 名 */
        private String meiText__;
        /** 送信先 姓カナ */
        private String seiKanaText__;
        /** 送信先 名カナ */
        private String meiKanaText__;
        /** 送信先メールアドレス */
        private String mailAddressText__;
        /** 役職SID */
        private Integer yakusyokuSid__;
        /** 役職名 */
        private String yakusyokuName__;
        /** 企業コード */
        private String companyId__;
        /** 会社拠点コード */
        private String baseId__;
        /** 企業名 */
        private String companyName__;
        /** 会社拠点名 */
        private String baseName__;
        /** 会社拠点種別名 */
        private String baseTypeText__;
        
        public Integer getAddressSid() {
            return addressSid__;
        }
        public void setAddressSid(Integer addressSid) {
            addressSid__ = addressSid;
        }
        public String getSeiText() {
            return seiText__;
        }
        public void setSeiText(String seiText) {
            seiText__ = seiText;
        }
        public String getMeiText() {
            return meiText__;
        }
        public void setMeiText(String meiText) {
            meiText__ = meiText;
        }
        public String getSeiKanaText() {
            return seiKanaText__;
        }
        public void setSeiKanaText(String seiKanaText) {
            seiKanaText__ = seiKanaText;
        }
        public String getMeiKanaText() {
            return meiKanaText__;
        }
        public void setMeiKanaText(String meiKanaText) {
            meiKanaText__ = meiKanaText;
        }
        public Integer getYakusyokuSid() {
            return yakusyokuSid__;
        }
        public void setYakusyokuSid(Integer yakusyokuSid) {
            yakusyokuSid__ = yakusyokuSid;
        }
        public String getYakusyokuName() {
            return yakusyokuName__;
        }
        public void setYakusyokuName(String yakusyokuName) {
            yakusyokuName__ = yakusyokuName;
        }
        public String getCompanyId() {
            return companyId__;
        }
        public void setCompanyId(String companyId) {
            companyId__ = companyId;
        }
        public String getBaseId() {
            return baseId__;
        }
        public void setBaseId(String baseId) {
            baseId__ = baseId;
        }
        public String getCompanyName() {
            return companyName__;
        }
        public void setCompanyName(String companyName) {
            companyName__ = companyName;
        }
        public String getBaseName() {
            return baseName__;
        }
        public void setBaseName(String baseName) {
            baseName__ = baseName;
        }
        public String getBaseTypeText() {
            return baseTypeText__;
        }
        public void setBaseTypeText(String baseTypeText) {
            baseTypeText__ = baseTypeText;
        }
        public String getMailAddressText() {
            return mailAddressText__;
        }
        public void setMailAddressText(String mailAddressText) {
            mailAddressText__ = mailAddressText;
        } 
    }

    public Integer getSid() {
        return sid__;
    }

    public void setSid(Integer sid) {
        sid__ = sid;
    }

    public String getName() {
        return name__;
    }

    public void setName(String name) {
        name__ = name;
    }

    public List<UserInfo> getUserArray() {
        return userArray__;
    }

    public void setUserArray(List<UserInfo> userArray) {
        userArray__ = userArray;
    }

    public List<AddressInfo> getAddressArray() {
        return addressArray__;
    }

    public void setAddressArray(List<AddressInfo> addressArray) {
        addressArray__ = addressArray;
    }
}