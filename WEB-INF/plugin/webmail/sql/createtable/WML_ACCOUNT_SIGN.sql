create table WML_ACCOUNT_SIGN
(
  WAC_SID    integer not null,
  WSI_NO     integer not null,
  WSI_TITLE  varchar(100) not null,
  WSI_SIGN   varchar(1000) not null,
  WSI_DEF    integer not null,
  primary key (WAC_SID, WSI_NO)
);