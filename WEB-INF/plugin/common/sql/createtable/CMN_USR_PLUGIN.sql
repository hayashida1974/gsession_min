create table CMN_USR_PLUGIN
(
  CUP_PID  varchar(10)  not null,
  CUP_NAME  varchar(10)  not null,
  CUP_URL       varchar(1000) not null,
  CUP_VIEW integer      not null,
  CUP_TARGET integer      not null,
  BIN_SID    bigint  not null,
  CUP_PARAM_KBN integer      not null,
  CUP_SEND_KBN integer      not null,
  primary key (CUP_PID)
) ;
