create table RNG_COPY_KEIRO_STEP
(
  RKS_SID       integer     not null,
  RCK_SORT      integer     not null,
  RTK_SID       integer     not null,
  RKS_BELONG_SID integer not null,
  primary key   (RKS_SID, RCK_SORT)
);