CREATE TABLE inventory_service.categories (
    id UUID,
    title VARCHAR(100),
    image_url VARCHAR(400),
    parent_id UUID,
    CONSTRAINT pk_category
        PRIMARY KEY(id),
    CONSTRAINT fk_parent_category
        FOREIGN KEY(parent_id)
        REFERENCES categories(id)
        ON DELETE SET NULL
);

CREATE TABLE products (
    id UUID,
    title VARCHAR(100),
    image_url VARCHAR(400),
    sku_number CHAR(8) UNIQUE NOT NULL,
    price_unit DOUBLE PRECISION,
    quantity INTEGER,
    category_id UUID,
    CONSTRAINT pk_product PRIMARY KEY(id),
    CONSTRAINT fk_product_category
        FOREIGN KEY(category_id)
        REFERENCES categories(id)
        ON DELETE SET NULL
);
