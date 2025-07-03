/*═══════════════════════════════════════════════════════════════════════════
  SCHEMA : school  — Attendance logs
═══════════════════════════════════════════════════════════════════════════*/
SET search_path TO school, ibm_extension, public;

CREATE TABLE IF NOT EXISTS attendance_logs (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id  BIGINT NOT NULL
                REFERENCES students(id)
                ON UPDATE CASCADE ON DELETE CASCADE,
    scan_time   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    event_type  TEXT        NOT NULL
                CHECK (event_type IN ('IN','OUT')),
    UNIQUE      (student_id, scan_time)
);

-- Composite index for “last swipe” queries
CREATE INDEX IF NOT EXISTS idx_attendance_student_time
  ON attendance_logs (student_id, scan_time DESC);
