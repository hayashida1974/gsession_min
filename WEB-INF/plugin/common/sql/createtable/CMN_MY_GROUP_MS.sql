create table CMN_MY_GROUP_MS
(
        USR_SID          integer      not null,
        MGP_SID          integer      not null,
        MGM_SID          integer      not null,
        primary key (USR_SID, MGP_SID, MGM_SID)
);