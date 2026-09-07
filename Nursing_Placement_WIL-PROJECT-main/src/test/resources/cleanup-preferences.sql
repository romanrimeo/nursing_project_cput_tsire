-- Clean up in correct order to avoid foreign key violations
DELETE FROM student_preference WHERE student_id = 1;
DELETE FROM student WHERE student_id = 1;
DELETE FROM facility WHERE facility_id IN (1, 2, 3);