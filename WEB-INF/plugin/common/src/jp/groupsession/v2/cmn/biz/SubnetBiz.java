package jp.groupsession.v2.cmn.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] IPアドレスに関する各種機能を提供する共通クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SubnetBiz {

    /** 区切り文字 */
    public static String DELIMITER = "/";

    /**
     * <br>[機  能] IPアドレス/サブネット文字列の入力チェックを行う
     * <br>[解  説] 複数のIPアドレス/サブネット文字列が1行毎に設定されていることを想定
     * <br>[備  考]
     * @param errors ActionErrors
     * @param reqMdl RequstModel
     * @param subnetStr IPアドレス/サブネット文字列
     * @param paramName パラメータ名
     * @param paramNameJp パラメータ名(日本語)
     * @return AcitonErrors
     */
    public ActionErrors validateSubnet(ActionErrors errors,
                                    RequestModel reqMdl,
                                    String subnetStr,
                                    String paramName, String paramNameJp) {
        String[] chkArr = subnetStr
                        .replaceAll("\\r\\n|\\r|\\n", "\r\n")
                        .split("\\r\\n");
        SubnetBiz ipBiz = new SubnetBiz();
        List<String> failIpList = ipBiz.checkIp(Arrays.asList(chkArr));
        if (!failIpList.isEmpty()) {
            String failIpStr = "";
            for (String failIp : failIpList) {
                if (!failIpStr.isEmpty()) {
                    failIpStr += "<br>";
                }
                failIpStr += "・" + failIp;
            }

            GsMessage gsMsg = new GsMessage(reqMdl);
            ActionMessage msg = new ActionMessage("error.input.format.text.multi",
                                                paramNameJp,
                                                failIpStr);
            StrutsUtil.addMessage(errors, msg, "error.input.format.text.multi." + paramName);
        }

        return errors;
    }

    /**
     * <br>[機  能] 指定したIPアドレスの表記チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ipList チェック対象のIPアドレス一覧
     * @return 不正なIPアドレス一覧
     */
    public List<String> checkIp(List<String> ipList) {
        List<String> failIpList = new ArrayList<String>();

        for (String ip : ipList) {
            //未入力 or 先頭が「#」(コメント)は除外
            if (ip.length() == 0 || ip.startsWith("#")) {
                continue;
            }

            //「*」を含む場合、CIDR表記かをチェック
            if (ip.indexOf("*") >= 0) {
                if (ip.indexOf(DELIMITER) >= 0) {
                    failIpList.add(ip);
                }
                continue;
            }

            //前後の空白除去
            ip = ip.trim();

            //IPv4アドレスチェック
            InetAddressValidator validator = InetAddressValidator.getInstance();
            if (validator.isValidInet4Address(ip)) {
                continue;
            }
 
            //IPv6アドレスチェック
            if (validator.isValidInet6Address(ip)) {
                continue;
            }

            //CIDR表記チェック
            try {
                SubnetUtils.SubnetInfo subnetInfo = __getSubnetInfo(ip);
                subnetInfo.getNetworkAddress();
            } catch (Exception e) {
                failIpList.add(ip);
            }
        }

        return failIpList;
    }

    /**
     * <br>[機  能] 対象のIPアドレスが指定サブネットの範囲内かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param subnet 比較対象アドレス(CIDR表記)
     * @param ipAddress 判定対象のIPアドレス
     * @return true: 範囲内、false: 範囲外
     */
    public boolean checkIPinRange(String subnet, String ipAddress) {
        //未入力 or 先頭が「#」(コメント)は除外
        if (subnet.length() == 0 || subnet.startsWith("#")) {
            return false;
        }

        //前後の空白除去
        subnet = subnet.trim();
        //プレフィックス or サブネットマスク指定なしの場合、範囲外とする
        if (subnet.indexOf(DELIMITER) <= 0) {
            return false;
        } 

        //プレフィックス指定あり
        try {
            SubnetUtils.SubnetInfo subnetInfo = __getSubnetInfo(subnet);
            if (subnetInfo.isInRange(ipAddress)) {
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    /**
     * <br>[機  能] 指定IPアドレスからSubnetUtils.SubnetInfoインスタンスを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param ipAddress IPアドレス
     * @return SubnetUtils.SubnetInfoインスタンス
     */
    private SubnetUtils.SubnetInfo  __getSubnetInfo(String ipAddress) {
        SubnetUtils subnetUtil = null;

        int delimIdx = ipAddress.indexOf(DELIMITER);
        if (delimIdx <= 0) {
            subnetUtil = new SubnetUtils(ipAddress, "255.255.255.0");
        } else {

            String address = ipAddress.substring(0, delimIdx);
            String prefix = ipAddress.substring(delimIdx + 1, ipAddress.length());
            if (prefix.indexOf(".") < 0) {
                subnetUtil = new SubnetUtils(ipAddress);
            } else {
                subnetUtil = new SubnetUtils(address, prefix);
            }
        }

        subnetUtil.setInclusiveHostCount(true);
        return subnetUtil.getInfo();
    }

}
