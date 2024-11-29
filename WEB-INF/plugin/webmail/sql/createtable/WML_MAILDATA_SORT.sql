create table WML_MAILDATA_SORT
(
  WAC_SID         integer      not null,
  USR_SID         integer      not null,
  WDR_SID         bigint       not null,
  WLB_SID         integer      not null,
  WMS_SORTKEY     integer      not null,
  WMS_ORDER       integer      not null,
  primary key (WAC_SID, USR_SID, WDR_SID, WLB_SID)
);