INSERT INTO contact (id, name, last_name, creation_time, status, description) VALUES (1, 'First', 'User', '2018-11-30 19:07:29', 'ACTIVE', null);
INSERT INTO contact (id, name, last_name, creation_time, status, description) VALUES (2, 'Alex', 'Beljakov', '2018-11-30 19:11:21', 'ACTIVE', null);
INSERT INTO contact (id, name, last_name, creation_time, status, description) VALUES (3, 'Test', 'Test', '2018-11-30 19:11:33', 'ACTIVE', null);
INSERT INTO contact (id, name, last_name, creation_time, status, description) VALUES (4, 'Den', 'T', '2018-11-30 19:11:40', 'ACTIVE', null);

INSERT INTO phone (id, contact_id, country_code, phone_number, description, type) VALUES (1, 1, 372, 54333445, 'Here is my phone number', 'HOME');