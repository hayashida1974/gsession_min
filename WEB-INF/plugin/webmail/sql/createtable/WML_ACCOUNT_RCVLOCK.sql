create table WML_ACCOUNT_RCVLOCK
(
  WAC_SID            integer      not null,
  WRL_RCVEDATE       timestamp    not null,
  primary key (WAC_SID)
);