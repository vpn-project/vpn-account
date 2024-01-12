CREATE TABLE subscription.account_linked_account (
     id BIGSERIAL PRIMARY KEY,
     main_account_id BIGINT,
     linked_account_id BIGINT,
     FOREIGN KEY (main_account_id) REFERENCES subscription.account (id),
     FOREIGN KEY (linked_account_id) REFERENCES subscription.account (id)
);
