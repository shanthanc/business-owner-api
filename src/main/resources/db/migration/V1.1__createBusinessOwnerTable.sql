CREATE TABLE business_owner (
                          business_id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          address VARCHAR not null,
                          ssn VARCHAR not null,
                          phone_number VARCHAR not null,
                          date_of_birth VARCHAR not null

);