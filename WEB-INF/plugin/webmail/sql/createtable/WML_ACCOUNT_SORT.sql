create table WML_ACCOUNT_SORT
(
  WAC_SID         integer      not null,
  USR_SID         integer      not null,
  WAS_SORT        BIGINT       not null,
  primary key (WAC_SID, USR_SID)
);