create table RNG_AUTODELETE
(
  RAD_PENDING_KBN    integer not null,
  RAD_PENDING_YEAR   integer,
  RAD_PENDING_MONTH  integer,
  RAD_PENDING_DAY    integer,
  RAD_COMPLETE_KBN   integer not null,
  RAD_COMPLETE_YEAR  integer,
  RAD_COMPLETE_MONTH integer,
  RAD_COMPLETE_DAY   integer,
  RAD_DRAFT_KBN      integer not null,
  RAD_DRAFT_YEAR     integer,
  RAD_DRAFT_MONTH    integer,
  RAD_DRAFT_DAY      integer,
  RAD_AUID           integer not null,
  RAD_ADATE          timestamp not null,
  RAD_EUID           integer not null,
  RAD_EDATE          timestamp not null
);