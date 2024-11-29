create table WML_MAIL_LOG_SEND
(
  WMD_MAILNUM  bigint        not null,
  WLS_NUM      integer       not null,
  WLS_TYPE     integer       not null,
  WLS_ADDRESS  varchar(768)  not null,
  WAC_SID      integer       not null,
  primary key(WMD_MAILNUM, WLS_NUM)
);