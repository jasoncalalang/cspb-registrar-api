/*═══════════════════════════════════════════════════════════════════════════
  SCHEMA  : school    — Parent/Guardian Table
  AUTHOR  : DBA Team
  NOTES   : Run with psql -f V2__parent_guardian.sql  (PostgreSQL ≥ 14)
═══════════════════════════════════════════════════════════════════════════*/

SET search_path TO school, public;

CREATE TABLE parent_guardians (
    id        BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES students
                ON UPDATE CASCADE ON DELETE CASCADE,
    role       TEXT NOT NULL CHECK (role IN ('father','mother','guardian')),
    last_name  CITEXT NOT NULL,
    first_name CITEXT NOT NULL,
    middle_name CITEXT,
    occupation TEXT,
    contact_num TEXT,
    email      TEXT,
    highest_educational_attainment TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (student_id, role)
);

-- quick lookup by student
CREATE INDEX idx_parent_guardians_student ON parent_guardians (student_id);
