create table RNG_BIN
(
  RNG_SID integer not null,
  BIN_SID bigint  not null,
  USR_SID integer not null,
  RKS_SID integer not null,
  primary key (RNG_SID, BIN_SID)
) ;