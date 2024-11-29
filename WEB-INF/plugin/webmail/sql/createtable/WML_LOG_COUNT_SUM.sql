 create table WML_LOG_COUNT_SUM
 (
  WLS_KBN      integer   not null,
  WLS_CNT      bigint    not null,
  WLS_CNT_TO   bigint    not null,
  WLS_CNT_CC   bigint    not null,
  WLS_CNT_BCC  bigint    not null,
  WLS_DATE     date      not null,
  WLS_MONTH    integer   not null,
  WLS_EDATE    timestamp not null
 );