create table WML_DELMAIL_LIST
(
  WAC_SID         integer      not null,
  WMD_MAILNUM     bigint       not null,
  WDL_ADATE       timestamp    not null,
  primary key (WAC_SID, WMD_MAILNUM)
);