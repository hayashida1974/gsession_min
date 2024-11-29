create table WML_DIRECTORY
(
  WDR_SID      bigint          not null,
  WAC_SID      integer         not null,
  WDR_NAME     varchar(100)    not null,
  WDR_TYPE     integer         not null,
  WDR_DEFAULT  integer         not null,
  WDR_VIEW     integer         not null,
  primary key(WDR_SID)
);