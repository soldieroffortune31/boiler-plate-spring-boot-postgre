CREATE TABLE pengguna_m (
	pengguna_id SERIAL PRIMARY KEY,
	email VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(100) NOT NULL,
	created_at timestamp,
	updated_at timestamp
);

ALTER TABLE pengguna_m
ADD CONSTRAINT constraint_name UNIQUE (email, username);


CREATE TABLE checklist_m (
    checklist_id SERIAL PRIMARY KEY,
    checklist_nama VARCHAR(100) NOT NULL,
    pengguna_id INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_pengguna FOREIGN KEY (pengguna_id) REFERENCES pengguna_m(pengguna_id)
);

CREATE TABLE checklistitem_m(
	checklistitem_id SERIAL NOT NULL,
	checklistitem_nama VARCHAR(100) NOT NULL,
	checklist_id INT NOT NULL,
	status_aktif BOOLEAN NOT NULL,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	PRIMARY KEY (checklistitem_id),
	CONSTRAINT fk_checklist FOREIGN KEY (checklist_id) REFERENCES checklist_m(checklist_id)
);