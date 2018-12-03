create table email (
  id INT AUTO_INCREMENT,
  contact_id INT NOT NULL,
  email VARCHAR(300) NOT NULL,
  type VARCHAR(10),
  description TEXT,
  PRIMARY KEY (id)
);

CREATE INDEX email_contact_idx ON email(`contact_id`);