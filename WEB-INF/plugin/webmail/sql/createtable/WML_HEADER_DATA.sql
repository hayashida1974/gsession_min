create table WML_HEADER_DATA
(
  WMD_MAILNUM  bigint         not null,
  WMH_NUM      integer        not null,
  WMH_TYPE     varchar(200)   not null,
  WMH_CONTENT  varchar(1000)  not null,
  WAC_SID      integer        not null,
  primary key(WMD_MAILNUM, WMH_NUM)
);