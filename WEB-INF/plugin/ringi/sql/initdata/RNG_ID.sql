insert into RNG_ID
(
  RID_SID,
  RID_FORMAT,
  RID_INIT,
  RID_MANUAL,
  RID_RESET,
  RID_ZERO,
  RID_TITLE,
  RID_USE_DATE
) values
(
  0,
  '    ${NO}',
  1,
  2,
  0,
  0,
  '汎用申請ID',
  current_timestamp
  );