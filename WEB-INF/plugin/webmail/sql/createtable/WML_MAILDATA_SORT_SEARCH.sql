create table WML_MAILDATA_SORT_SEARCH
(
  WAC_SID         integer      not null,
  USR_SID         integer      not null,
  WSS_SORTKEY     integer      not null,
  WSS_ORDER       integer      not null,
  primary key (WAC_SID, USR_SID)
);