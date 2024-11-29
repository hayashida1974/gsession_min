create table BBS_LOG_COUNT_SUM
(
  BLS_KBN   integer not null,
  BLS_CNT   bigint not null,
  BLS_DATE  date not null,
  BLS_MONTH integer not null,
  BLS_EDATE timestamp not null
);