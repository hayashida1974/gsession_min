create table WML_LABEL_RELATION
(
  WMD_MAILNUM  bigint       not null,
  WLB_SID      integer      not null,
  WAC_SID      integer      not null,
  primary key(WMD_MAILNUM, WLB_SID)
);