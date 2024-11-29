create table RNG_KEIROSTEP_SELECT
(
  RKS_SID integer not null,
  USR_SID integer not null,
  GRP_SID integer not null,
  POS_SID integer not null,
  primary key   (RKS_SID, USR_SID, GRP_SID, POS_SID)
);