/*═══════════════════════════════════════════════════════════════════════════
  DATA   : Sample students with address, requirements, and parents
═══════════════════════════════════════════════════════════════════════════*/
SET search_path TO school, ibm_extension, public;

-- ---------------------------------------------------------------------------
-- 1. Geographic reference data (minimal)
-- ---------------------------------------------------------------------------
INSERT INTO provinces (province_code, name) VALUES ('P1', 'Metro Manila')
  ON CONFLICT (province_code) DO NOTHING;

INSERT INTO cities (city_code, province_code, name) VALUES ('C1', 'P1', 'Quezon City')
  ON CONFLICT (city_code) DO NOTHING;

INSERT INTO barangays (bgy_code, city_code, name) VALUES ('B1', 'C1', 'UP Campus')
  ON CONFLICT (bgy_code) DO NOTHING;

-- ---------------------------------------------------------------------------
-- 2. Students
-- ---------------------------------------------------------------------------
INSERT INTO students (lrn, last_name, first_name, middle_name, birth_date,
                      birth_place, gender, img_path)
VALUES
  ('100000000001', 'Perez',      'Juan',  'A', '2010-01-10', 'Manila', 'M', 'https://picsum.photos/id/1011/200/200'),
  ('100000000002', 'Santos',     'Maria', 'B', '2011-02-20', 'Manila', 'F', 'https://picsum.photos/id/1012/200/200'),
  ('100000000003', 'Reyes',      'Jose',  'C', '2012-03-30', 'Manila', 'M', 'https://picsum.photos/id/1013/200/200'),
  ('100000000004', 'Garcia',     'Ana',   'D', '2013-04-15', 'Manila', 'F', 'https://picsum.photos/id/1014/200/200'),
  ('100000000005', 'Dela Cruz',  'Mark',  'E', '2014-05-25', 'Manila', 'M', 'https://picsum.photos/id/1015/200/200');

-- ---------------------------------------------------------------------------
-- 3. Addresses for each student
-- ---------------------------------------------------------------------------
INSERT INTO addresses (student_id, house_no, street_subd, bgy_code)
SELECT id, '11', 'Sample St', 'B1' FROM students WHERE lrn='100000000001';
INSERT INTO addresses (student_id, house_no, street_subd, bgy_code)
SELECT id, '22', 'Sample St', 'B1' FROM students WHERE lrn='100000000002';
INSERT INTO addresses (student_id, house_no, street_subd, bgy_code)
SELECT id, '33', 'Sample St', 'B1' FROM students WHERE lrn='100000000003';
INSERT INTO addresses (student_id, house_no, street_subd, bgy_code)
SELECT id, '44', 'Sample St', 'B1' FROM students WHERE lrn='100000000004';
INSERT INTO addresses (student_id, house_no, street_subd, bgy_code)
SELECT id, '55', 'Sample St', 'B1' FROM students WHERE lrn='100000000005';

-- ---------------------------------------------------------------------------
-- 4. Submitted requirements for each student
-- ---------------------------------------------------------------------------
INSERT INTO requirements (student_id, requirement_type, submitted, submitted_date)
SELECT s.id, rt.id, TRUE, CURRENT_DATE
FROM students s CROSS JOIN requirement_types rt
WHERE s.lrn='100000000001';
INSERT INTO requirements (student_id, requirement_type, submitted, submitted_date)
SELECT s.id, rt.id, TRUE, CURRENT_DATE
FROM students s CROSS JOIN requirement_types rt
WHERE s.lrn='100000000002';
INSERT INTO requirements (student_id, requirement_type, submitted, submitted_date)
SELECT s.id, rt.id, TRUE, CURRENT_DATE
FROM students s CROSS JOIN requirement_types rt
WHERE s.lrn='100000000003';
INSERT INTO requirements (student_id, requirement_type, submitted, submitted_date)
SELECT s.id, rt.id, TRUE, CURRENT_DATE
FROM students s CROSS JOIN requirement_types rt
WHERE s.lrn='100000000004';
INSERT INTO requirements (student_id, requirement_type, submitted, submitted_date)
SELECT s.id, rt.id, TRUE, CURRENT_DATE
FROM students s CROSS JOIN requirement_types rt
WHERE s.lrn='100000000005';

-- ---------------------------------------------------------------------------
-- 5. Parents for each student (father & mother)
-- ---------------------------------------------------------------------------
INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'father', 'Perez', 'Carlos', NULL, 'Engineer', '09170000001', 'carlos.perez@example.com', 'College'
FROM students WHERE lrn='100000000001';
INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'mother', 'Perez', 'Maria', NULL, 'Teacher', '09170000002', 'maria.perez@example.com', 'College'
FROM students WHERE lrn='100000000001';

INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'father', 'Santos', 'Juan', NULL, 'Engineer', '09170000003', 'juan.santos@example.com', 'College'
FROM students WHERE lrn='100000000002';
INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'mother', 'Santos', 'Ana', NULL, 'Nurse', '09170000004', 'ana.santos@example.com', 'College'
FROM students WHERE lrn='100000000002';

INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'father', 'Reyes', 'Miguel', NULL, 'Driver', '09170000005', 'miguel.reyes@example.com', 'High School'
FROM students WHERE lrn='100000000003';
INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'mother', 'Reyes', 'Lucia', NULL, 'Vendor', '09170000006', 'lucia.reyes@example.com', 'High School'
FROM students WHERE lrn='100000000003';

INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'father', 'Garcia', 'Pedro', NULL, 'Clerk', '09170000007', 'pedro.garcia@example.com', 'College'
FROM students WHERE lrn='100000000004';
INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'mother', 'Garcia', 'Rosa', NULL, 'Homemaker', '09170000008', 'rosa.garcia@example.com', 'College'
FROM students WHERE lrn='100000000004';

INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'father', 'Dela Cruz', 'John', NULL, 'Farmer', '09170000009', 'john.delacruz@example.com', 'High School'
FROM students WHERE lrn='100000000005';
INSERT INTO parent_guardians (student_id, role, last_name, first_name, middle_name,
                              occupation, contact_num, email, highest_educational_attainment)
SELECT id, 'mother', 'Dela Cruz', 'Jane', NULL, 'Seamstress', '09170000010', 'jane.delacruz@example.com', 'High School'
FROM students WHERE lrn='100000000005';
