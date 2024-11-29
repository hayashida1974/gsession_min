create table RNG_ACONF
(
  RAR_DEL_AUTH     integer not null,
  RAR_AUID         integer not null,
  RAR_ADATE        timestamp not null,
  RAR_EUID         integer not null,
  RAR_EDATE        timestamp not null,
  RAR_SML_NTF     integer    not null,
  RAR_SML_NTF_KBN integer,
  RAR_RNGID       integer,
  RAR_RNGID_DEF_SID integer,
  RAR_SML_DAIRI_KBN integer,
  RAR_SML_JUSIN_KBN integer,
  RAR_OVERLAP       integer not null,
  RAR_HANYO_FLG       integer not null,
  RAR_TEMPLATE_PERSONAL_FLG       integer not null,
  RAR_KEIRO_PERSONAL_FLG       integer not null
) ;