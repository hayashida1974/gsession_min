package jp.groupsession.v2.adr.restapi;

import jp.groupsession.v2.restapi.exception.IReasonCode;

/**
 *
 * <br>[機  能] 理由コード列挙型
 * <br>[解  説] 原因がアドレス帳のRESTAPIに関するエラーの場合
 * <br>[備  考]
 *
 * @author JTS
 */
public enum AdrEnumReasonCode implements IReasonCode {
    
    /** アドレス帳APIが利用できない */
    @ReasonCodeString("ADDRESS-000")
    PLUGIN_CANT_USE,

    /** 対象の会社情報へアクセスできない */
    @ReasonCodeString("ADDRESS-101")
    RESOURCE_CANT_ACCESS_COMPANY,

    /** 対象のアドレス情報へアクセスできない */
    @ReasonCodeString("ADDRESS-102")
    RESOURCE_CANT_ACCESS_ADDRESS,

    /** 入力パラメータで指定した会社情報へアクセスできない */
    @ReasonCodeString("ADDRESS-201")
    PARAM_CANT_ACCESS_COMPANY;
}