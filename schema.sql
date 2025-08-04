DROP TABLE APP.MONTHLY_EXPENSES;
DROP TABLE APP.MONTHLY_INCOME;
DROP TABLE APP.USERS;


-- users table (login info)
CREATE TABLE users (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- placeholders for income / expenses so teammates can extend them
CREATE TABLE monthly_income (
    income_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT
    -- later: add amount, month, etc.
);

CREATE TABLE monthly_expenses (
    expense_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT
    -- later: add amount, month, etc.
);