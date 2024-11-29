create table WML_DELMAIL_TEMP
(
  USR_SID         integer      not null,
  WMD_MAILNUM     bigint       not null,
  WAC_SID         integer      not null,
  WDT_ADATE       timestamp    not null,
  primary key (USR_SID, WMD_MAILNUM)
);
