create table WML_ACCOUNT_RCVSVR
(
  WAC_SID            integer      not null,
  WRS_RECEIVE_COUNT  bigint       not null,
  WRS_RECEIVE_SIZE   bigint       not null,
  WRS_EDATE          timestamp,
  primary key (WAC_SID)
);