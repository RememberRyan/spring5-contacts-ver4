create table address (
    id INT AUTO_INCREMENT,
    contact_id INT NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    type VARCHAR(10),
    description TEXT,
    PRIMARY KEY (id)
);

CREATE INDEX address_contact_idx ON address(`contact_id`);

create table phone (
  id INT AUTO_INCREMENT,
  contact_id INT NOT NULL,
  country_code int NOT NULL,
  phone_number INT NOT NULL,
  description TEXT,
  type VARCHAR(10),
  PRIMARY KEY (id)
);

CREATE INDEX phone_contact_idx ON phone(`contact_id`);



