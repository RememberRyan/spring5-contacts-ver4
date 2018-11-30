create table contact (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(10) NOT NULL,
    description TEXT,
    PRIMARY KEY (id)
);

CREATE INDEX contact_idx ON contact(`name`,`last_name`, `status`, `creation_time`);


