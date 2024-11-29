create table WML_SENDADDRESS
(
  WMD_MAILNUM      bigint        not null,
  WSA_NUM          integer       not null,
  WSA_TYPE         integer       not null,
  WSA_ADDRESS      varchar(768)  not null,
  WAC_SID          integer       not null,
  WSA_ADDRESS_SRH  varchar(768),
  primary key(WMD_MAILNUM, WSA_NUM)
);