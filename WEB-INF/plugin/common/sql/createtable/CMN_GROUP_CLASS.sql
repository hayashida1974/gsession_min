create table CMN_GROUP_CLASS
(
        GCL_SID1         integer      not null,
        GCL_SID2         integer      not null,
        GCL_SID3         integer      not null,
        GCL_SID4         integer      not null,
        GCL_SID5         integer      not null,
        GCL_SID6         integer      not null,
        GCL_SID7         integer      not null,
        GCL_SID8         integer      not null,
        GCL_SID9         integer      not null,
        GCL_SID10        integer      not null,
        GCL_AUID         integer      not null,
        GCL_ADATE        timestamp    not null,
        GCL_EUID         integer      not null,
        GCL_EDATE        timestamp    not null,
        primary key (GCL_SID1,
                     GCL_SID2,
                     GCL_SID3,
                     GCL_SID4,
                     GCL_SID5,
                     GCL_SID6,
                     GCL_SID7,
                     GCL_SID8,
                     GCL_SID9,
                     GCL_SID10
                    )
) ;