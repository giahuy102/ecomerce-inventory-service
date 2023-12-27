CREATE TABLE categories (
    id UUID,
    title VARCHAR(100),
    image_url VARCHAR(400),
--    parent_id UUID,
    CONSTRAINT pk_category 
        PRIMARY KEY(id)
--    CONSTRAINT fk_parent_category
--        FOREIGN KEY(parent_id)
--        REFERENCES categories(id)
--        ON DELETE SET NULL
);

CREATE TABLE products (
    id UUID,
    title VARCHAR(100),
    image_url VARCHAR(400),
    sku_number CHAR(8) UNIQUE NOT NULL,
    price_unit DOUBLE,
    quantity INTEGER,
    CONSTRAINT pk_product PRIMARY KEY(id)
);

CREATE TABLE product_categories (
    product_id UUID,
    category_id UUID,
    CONSTRAINT pk_product_category 
        PRIMARY KEY(product_id, category_id),
    CONSTRAINT fk_product_category_product
        FOREIGN KEY(product_id)
        REFERENCES products(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_product_category_category
        FOREIGN KEY(category_id)
        REFERENCES categories(id)
        ON DELETE CASCADE
);
