 insert
 into
 CMN_GROUPM(
   GRP_SID,
   GRP_ID,
   GRP_NAME,
   GRP_NAME_KN,
   GRP_COMMENT,
   GRP_AUID,
   GRP_ADATE,
   GRP_EUID,
   GRP_EDATE,
   GRP_SORT,
   GRP_JKBN
 )
 values
 (
   0,
   '00000000',
   'システム管理グループ',
   'システムカンリグループ',
   '',
   0,
   current_timestamp,
   0,
   current_timestamp,
   0,
   0
 );

 insert
 into
 CMN_GROUP_CLASS(
   GCL_SID1,
   GCL_SID2,
   GCL_SID3,
   GCL_SID4,
   GCL_SID5,
   GCL_SID6,
   GCL_SID7,
   GCL_SID8,
   GCL_SID9,
   GCL_SID10,
   GCL_AUID,
   GCL_ADATE,
   GCL_EUID,
   GCL_EDATE
 )
 values
 (
   0,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   -1,
   0,
   current_timestamp,
   0,
   current_timestamp
 );