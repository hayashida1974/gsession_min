create table RNG_TEMPLATE_KEIRO_USER
(
  RTK_SID integer not null,
  USR_SID integer not null,
  GRP_SID integer not null,
  POS_SID integer not null,
  RKU_TYPE integer not null,
  RKU_AUID integer not null,
  RKU_ADATE timestamp not null,
  RKU_EUID integer not null,
  RKU_EDATE timestamp not null,
  primary key   (RTK_SID, USR_SID, GRP_SID, POS_SID)
);