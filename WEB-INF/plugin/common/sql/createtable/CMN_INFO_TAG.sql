create table CMN_INFO_TAG
(
  IMS_SID      integer         not null,
  USR_SID      integer         not null,
  GRP_SID      integer         not null,
  primary key (IMS_SID,USR_SID,GRP_SID)
);
