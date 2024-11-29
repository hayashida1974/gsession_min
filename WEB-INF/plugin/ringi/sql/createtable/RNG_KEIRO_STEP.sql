create table RNG_KEIRO_STEP
(
  RKS_SID       integer     not null,
  RNG_SID       integer     not null,
  RKS_SORT      integer     not null,
  RKS_STATUS    integer     not null,
  RTK_SID       integer     not null,
  RKS_ROLL_TYPE integer     not null,
  RKS_RCVDATE   timestamp ,
  RKS_CHKDATE   timestamp ,
  RKS_AUID      integer     not null,
  RKS_ADATE     timestamp   not null,
  RKS_EUID      integer     not null,
  RKS_EDATE     timestamp   not null,
  RKS_BELONG_SID integer     not null,
  RKS_KEIRO_KOETU integer   not null,
  RKS_KOETU_SIZI integer    not null,
  RKS_ADDSTEP integer    not null,
  primary key   (RKS_SID)
);