import psycopg2
from psycopg2.extras import execute_values
import os

conn = psycopg2.connect(
    host="localhost",
    port=5432,
    dbname="electronics",
    user="appuser",
    password="secret"
)
conn.autocommit = False

# map category_id → local image path
CATEGORY_LOCAL_IMAGES = {
    1: ["images/1.jpg", "images/1_2.jpg", "images/1_3.jpg"],
    2: ["images/2.jpg", "images/2_2.jpg", "images/2_3.jpg"],
    3: ["images/3.jpg", "images/3_2.jpg", "images/3_3.jpg"],
    4: ["images/4.jpg"],
    5: ["images/5.jpg"],
    6: ["images/6.jpg"],
    7: ["images/7.jpg"],
    8: ["images/8.jpg", "images/8_2.jpg"],
    9: ["images/9.jpg"],
    10: ["images/10.jpg"],
    11: ["images/11.jpg"],
    12: ["images/12.jpg"],
    13: ["images/13.jpg"],
    14: ["images/14.jpg"],
    15: ["images/15.jpg"],
    16: ["images/16.jpg"],
    17: ["images/17.jpg"],
    18: ["images/18.jpg"],
    19: ["images/19.jpg"],
    20: ["images/20.jpg"],
    21: ["images/21.jpg"],
    22: ["images/22.jpg"],
    23: ["images/23.jpg"],
}


def load_bytes(path):
    with open(path, "rb") as f:
        return f.read()


cur = conn.cursor()

# fetch all products that don't have a photo yet
cur.execute("""
    SELECT id, category_id
    FROM product
    WHERE NOT EXISTS (
      SELECT 1 FROM product_photo WHERE product_id = product.id
    )
""")
rows = cur.fetchall()

inserts = []
cache = {}

for product_id, category_id in rows:
    paths = CATEGORY_LOCAL_IMAGES.get(category_id, [])
    if not paths:
        continue

    for path in paths:
        if path not in cache:
            cache[path] = load_bytes(path)

        inserts.append((cache[path], product_id))

# bulk insert
if inserts:
    execute_values(
        cur,
        "INSERT INTO product_photo(photo, product_id) VALUES %s",
        inserts,
        template="(%s, %s)"
    )
    conn.commit()
    print(f"Inserted {len(inserts)} images")

conn.close()