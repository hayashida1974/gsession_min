 create table CMN_MY_GROUP_SHARE
 (
  USR_SID          integer      not null,
  MGP_SID          integer      not null,
  MGS_USR_SID          integer      not null,
  MGS_GRP_SID          integer      not null,
  primary key (USR_SID, MGP_SID, MGS_USR_SID ,MGS_GRP_SID)
 );
