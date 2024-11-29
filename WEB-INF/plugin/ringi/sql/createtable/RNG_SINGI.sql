create table RNG_SINGI
(
  RKS_SID        integer        not null,
  USR_SID        integer        not null,
  RNG_SID        integer        not null,
  USR_SID_KOETU  integer ,
  USR_SID_DAIRI  integer ,
  RSS_STATUS     integer        not null,
  RSS_COMMENT    varchar(300) ,
  RSS_CHKDATE    timestamp ,
  RSS_AUID       integer        not null,
  RSS_ADATE      timestamp      not null,
  RSS_EUID       integer        not null,
  RSS_EDATE      timestamp      not null,
  primary key    (RKS_SID, USR_SID)
);