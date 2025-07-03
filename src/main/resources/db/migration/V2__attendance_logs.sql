-- Attendance logs table
CREATE TABLE attendance_logs (
    id          BIGSERIAL PRIMARY KEY,
    student_id  BIGINT NOT NULL REFERENCES students
                ON UPDATE CASCADE ON DELETE CASCADE,
    scan_time   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    event_type  TEXT NOT NULL CHECK (event_type IN ('IN','OUT'))
);

CREATE INDEX idx_attendance_logs_student ON attendance_logs (student_id);
