/*═══════════════════════════════════════════════════════════════════════════
  SCHEMA : school  — Parent / Guardian
═══════════════════════════════════════════════════════════════════════════*/
SET search_path TO school, ibm_extension, public;

/* citext lives in ibm_extension; ensure it’s present */
CREATE EXTENSION IF NOT EXISTS citext WITH SCHEMA ibm_extension;

CREATE TABLE IF NOT EXISTS parent_guardians (
    id                     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id             BIGINT NOT NULL
                            REFERENCES students(id)
                            ON UPDATE CASCADE ON DELETE CASCADE,
    role                   TEXT   NOT NULL
                            CHECK (role IN ('father','mother','guardian')),
    last_name              CITEXT NOT NULL,
    first_name             CITEXT NOT NULL,
    middle_name            CITEXT,
    occupation             TEXT,
    contact_num            TEXT,
    email                  TEXT,
    highest_educational_attainment TEXT,
    created_at             TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE (student_id, role)
);

CREATE INDEX IF NOT EXISTS idx_parent_guardians_student
  ON parent_guardians (student_id);
