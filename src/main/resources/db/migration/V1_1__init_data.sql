INSERT INTO categories (id, title)
VALUES
    ('191e88d2-cc65-4a9c-89c1-5bf10c511a2a', 'Motorbike'),
    ('2a19cb64-0440-42bf-a1fa-cedf3592e453', 'Car');

INSERT INTO products (id, title, sku_number, price_unit, quantity, category_id, merchant_user_id)
VALUES
    ('de768385-e69b-4f4a-8993-2f935f71cb84', 'Wave RSX 2024', 'KS944RUR', 10.2, 1000, '191e88d2-cc65-4a9c-89c1-5bf10c511a2a', 'ed18fd1d-0d07-4bb1-8bc5-a9e480e7236c'),
    ('66f9263d-1e9c-4aaf-8db6-0e8b12627cd0', 'Wave RSX 2023', 'KS945RUR', 15, 3, '191e88d2-cc65-4a9c-89c1-5bf10c511a2a', 'ed18fd1d-0d07-4bb1-8bc5-a9e480e7236c'),
    ('058b4aed-5395-4b0a-aff8-186798914512', 'Tesla model 3', 'KS946RUR', 100.4, 1000, '2a19cb64-0440-42bf-a1fa-cedf3592e453', '508a6a36-32c3-4537-bcd9-6d52736f7ffa'),
    ('7d133276-595b-40ab-a343-758bbe783af1', 'Tesla model 5', 'KS947RUR', 200.4, 4, '2a19cb64-0440-42bf-a1fa-cedf3592e453', '508a6a36-32c3-4537-bcd9-6d52736f7ffa');
