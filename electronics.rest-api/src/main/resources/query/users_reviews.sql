DO $$
DECLARE
  v_users int := 200;
  v_avg_reviews_per_user int := 20;
  v_total_reviews int := v_users * v_avg_reviews_per_user;

  v_preferred_ratio float := 0.80;
  v_role_user_id int;
  addr_seq text;
BEGIN
  ----------------------------------------------------------------------
  -- PRECONDITIONS
  ----------------------------------------------------------------------
  IF (SELECT COUNT(*) FROM product) = 0 THEN
    RAISE EXCEPTION 'product is empty. Import products first.';
  END IF;

  IF (SELECT COUNT(*) FROM category) = 0 THEN
    RAISE EXCEPTION 'category is empty.';
  END IF;

  IF (SELECT COUNT(*) FROM county) = 0 THEN
    RAISE EXCEPTION 'county is empty.';
  END IF;

  SELECT id INTO v_role_user_id FROM role WHERE name='ROLE_USER' LIMIT 1;
  IF v_role_user_id IS NULL THEN
    RAISE EXCEPTION 'ROLE_USER not found in role table.';
  END IF;

  ----------------------------------------------------------------------
  -- 1) GENERATE ADDRESSES -> will randomize user addresses from these
  ----------------------------------------------------------------------
  SELECT pg_get_serial_sequence('address', 'id') INTO addr_seq;

  -- FIX SEQUENCE so next insert doesn't collide
  EXECUTE
      'SELECT setval(''' || addr_seq || ''', (SELECT COALESCE(MAX(id),0) FROM address))';

  -- INSERT 10 NEW ADDRESSES
  INSERT INTO address(city, lat, lon, number, post_code, street, county_id)
  SELECT
    (ARRAY['Bucharest','Cluj-Napoca','Iasi','Timisoara','Brasov'])
        [1 + floor(random()*5)]::text,
    (44.0 + random()*4.0),
    (22.0 + random()*6.0),
    (1 + floor(random()*150))::text,
    (100000 + floor(random()*89999))::text,
    ('Street ' ||
     (ARRAY['Union','Victory','Liberty','Flowers','Peace'])
        [1 + floor(random()*5)] || ' ' ||
     (ARRAY['A','B','C','D','E'])
        [1 + floor(random()*5)]
    )::text,
    (SELECT id FROM county ORDER BY random() LIMIT 1)
  FROM generate_series(1, 10);

  ----------------------------------------------------------------------
  -- 2) GENERATE USERS WITH RANDOM ADDRESSES (from the 10 new ones)
  ----------------------------------------------------------------------
  INSERT INTO store_user(name, username, email, password, profile_pic, address_id)
  SELECT
    ('User ' || gs)::text,
    ('user' || gs || '_' || floor(random()*100000)::int)::text,
    ('user' || gs || '_' || floor(random()*100000)::int || '@mock.local')::text,
    ('mock_password_' || gs)::text,
    NULL,
    (
      SELECT id FROM address ORDER BY random() LIMIT 1
    ) AS address_id
  FROM generate_series(1, v_users) gs
  ON CONFLICT DO NOTHING;

  ----------------------------------------------------------------------
  -- 3) ASSIGN ROLE_USER
  ----------------------------------------------------------------------
  INSERT INTO store_user_role(user_id, role_id)
  SELECT u.id, v_role_user_id
  FROM store_user u
  ON CONFLICT DO NOTHING;

  ----------------------------------------------------------------------
  -- 4) ASSIGN USER CATEGORY PREFERENCES (2–4 per user randomly)
  ----------------------------------------------------------------------
  INSERT INTO user_categories(user_id, category_id)
  SELECT u.id, c.id
  FROM store_user u
  JOIN LATERAL (
    SELECT id
    FROM category
    ORDER BY random()
    LIMIT (2 + floor(random()*3))::int
  ) c ON TRUE
  ON CONFLICT DO NOTHING;

  ----------------------------------------------------------------------
  -- 5) REVIEWS (~20 per user) from selections
  -- generates a raw number of reviews (raw_cnt), at least 1 review per user -> uneven activity, which is more realistic
  -- picks products based on user category preferences -> this makes most reviews aligned with user preferences, which creates category “clusters” and helps collaborative filtering
  ----------------------------------------------------------------------
  WITH users AS (
    SELECT id AS user_id
    FROM store_user
    ORDER BY id DESC
    LIMIT v_users
  ),
  counts AS (
    SELECT
      user_id,
      GREATEST(
        1,
        round(exp(1.8 + (random() - 0.5) * 1.2))::int
      ) AS raw_cnt
    FROM users
  ),
  scaled AS (
    SELECT
      user_id,
      GREATEST(
        1,
        round(raw_cnt * (v_total_reviews::numeric / NULLIF((SELECT SUM(raw_cnt) FROM counts),0)))::int
      ) AS cnt
    FROM counts
  ),
  expanded AS (
    SELECT
      s.user_id,
      gs AS k
    FROM scaled s
    JOIN LATERAL generate_series(1, s.cnt) gs ON TRUE
  ),
  picks AS (
    SELECT
      e.user_id,
      (random() < v_preferred_ratio) AS preferred,
      CASE
        WHEN (random() < v_preferred_ratio) THEN (
          SELECT p.id
          FROM user_categories uc
          JOIN product p ON p.category_id = uc.category_id
          WHERE uc.user_id = e.user_id
          ORDER BY random()
          LIMIT 1
        )
        ELSE (
          SELECT id FROM product ORDER BY random() LIMIT 1
        )
      END AS product_id
    FROM expanded e
  ),
  filtered AS (
    SELECT *
    FROM picks
    WHERE product_id IS NOT NULL
      AND NOT EXISTS (
        SELECT 1
        FROM product_review pr
        WHERE pr.store_user_id = picks.user_id
          AND pr.product_id = picks.product_id
      )
  )
  INSERT INTO product_review(date_posted, description, rating, title, product_id, store_user_id)
  SELECT
    NOW() - (random() * interval '240 days'),
    (ARRAY[
      'Great value for the price. Works as expected.',
      'Build quality is better than I anticipated.',
      'Does the job fine, no major complaints.',
      'Decent overall, but there are a few drawbacks.',
      'Disappointed — had issues after a couple of days.',
      'Fast delivery and the product matches the description.',
      'Solid performance for everyday use.',
      'Not worth it for me — expected more.',
      'Good product, but the battery could be better.',
      'Setup was easy and everything worked out of the box.'
    ])[1 + floor(random()*10)],
    CASE
      WHEN preferred THEN GREATEST(1, LEAST(5, round(3.7 + random()*1.5)))::int
      ELSE GREATEST(1, LEAST(5, round(2.3 + random()*2.7)))::int
    END,
    (ARRAY[
      'Highly recommended',
      'Good for the price',
      'Solid choice',
      'Just okay',
      'Not impressed',
      'Works great',
      'Could be better',
      'Exceeded expectations',
      'Not worth it',
      'Happy with it'
    ])[1 + floor(random()*10)],
    product_id,
    user_id
  FROM filtered;

  RAISE NOTICE 'Seed DONE: addresses=10, users=%, reviews≈% (~%/user).',
               v_users, v_total_reviews, v_avg_reviews_per_user;

END $$;