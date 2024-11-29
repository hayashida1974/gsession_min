create table RNG_DAIRI_USER
(
  USR_SID integer not null,
  USR_SID_DAIRI integer not null,
  RDU_START timestamp not null,
  RDU_END timestamp ,
  primary key (USR_SID, USR_SID_DAIRI)
);