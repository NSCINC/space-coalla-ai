CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    token VARCHAR(255)
);

CREATE TABLE crm_data (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    data JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE erp_data (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    data JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
