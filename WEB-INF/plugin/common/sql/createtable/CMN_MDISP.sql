create table CMN_MDISP
(
    USR_SID         integer         not null,
    MDP_PID         varchar(10)     not null,
    MDP_DSP         integer         not null,
    MDP_RELOAD      integer         not null default 600000,
    MDP_AUID        integer         not null,
    MDP_ADATE       timestamp       not null,
    MDP_EUID        integer         not null,
    MDP_EDATE       timestamp       not null,
    primary key (
      USR_SID, MDP_PID
    ) 
) ;