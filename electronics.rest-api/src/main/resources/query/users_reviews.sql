DO $$
DECLARE
  v_users int := 300;
  v_reviews_per_user int := 30;
  v_total int := v_users * v_reviews_per_user;
  v_preferred_ratio float := 0.75;
BEGIN
  -- PRECONDITIONS
  IF (SELECT COUNT(*) FROM product) = 0 THEN
    RAISE EXCEPTION 'No products found.';
  END IF;

  IF (SELECT COUNT(*) FROM store_user) = 0 THEN
    RAISE EXCEPTION 'No users found.';
  END IF;

   ----------------------------------------------------------------------
   -- 1) Coverage phase → 1 review for each product
   ----------------------------------------------------------------------
   INSERT INTO product_review(date_posted, description, rating, title, product_id, store_user_id)
   SELECT
     NOW() - (random() * interval '240 days'),
     'Works as expected.',
     3,
     'Average product',
     p.id,
     (SELECT id FROM store_user ORDER BY random() LIMIT 1)
   FROM product p
   WHERE NOT EXISTS (
     SELECT 1 FROM product_review r WHERE r.product_id = p.id
   );

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
  expanded AS (
    SELECT u.user_id, gs AS k
    FROM users u
    CROSS JOIN LATERAL generate_series(1, v_reviews_per_user) gs
  ),
  picks AS (
    SELECT
      e.user_id,
      (random() < v_preferred_ratio) AS preferred,
      CASE
        WHEN random() < v_preferred_ratio THEN (
          SELECT p.id
          FROM user_categories uc
          JOIN product p ON p.category_id = uc.category_id
          WHERE uc.user_id = e.user_id
          ORDER BY random()
          LIMIT 1
        )
        ELSE (
          SELECT p.id FROM product p ORDER BY random() LIMIT 1
        )
      END AS product_id
    FROM expanded e
  ),
  scored AS (
    SELECT
      p.*,
      CASE
        WHEN preferred THEN
          CASE WHEN random() < 0.05 THEN 1
               WHEN random() < 0.15 THEN 2
               WHEN random() < 0.40 THEN 3
               WHEN random() < 0.70 THEN 4
               ELSE 5 END
        ELSE
          CASE WHEN random() < 0.15 THEN 1
               WHEN random() < 0.35 THEN 2
               WHEN random() < 0.60 THEN 3
               WHEN random() < 0.80 THEN 4
               ELSE 5 END
      END AS rating
    FROM picks p
  )

  INSERT INTO product_review(date_posted, description, rating, title, product_id, store_user_id)
  SELECT
    NOW() - (random() * interval '240 days'),

    CASE
      WHEN rating = 5 THEN (ARRAY[
        'Excellent quality — exceeded expectations.',
        'Absolutely love it.',
        'Amazing build quality.',
        'Fantastic product!'
      ])[1 + floor(random()*4)]
      WHEN rating = 4 THEN (ARRAY[
        'Very good, works great.',
        'Solid performance overall.',
        'Good value for money.',
        'Quite happy with it.'
      ])[1 + floor(random()*4)]
      WHEN rating = 3 THEN (ARRAY[
        'It’s fine, nothing special.',
        'Works okay.',
        'Average product.',
        'Expected a little more.'
      ])[1 + floor(random()*4)]
      WHEN rating = 2 THEN (ARRAY[
        'Not very good.',
        'A bit disappointing.',
        'Several issues.',
        'Quality is not great.'
      ])[1 + floor(random()*4)]
      ELSE (ARRAY[
        'Very bad experience.',
        'Would not recommend.',
        'Terrible quality.',
        'Stopped working early.'
      ])[1 + floor(random()*4)]
    END,

    rating,

    CASE
      WHEN rating = 5 THEN (ARRAY['Excellent', 'Perfect', 'Amazing', 'Highly recommend'])[1 + floor(random()*4)]
      WHEN rating = 4 THEN (ARRAY['Good', 'Solid', 'Works great', 'Happy'])[1 + floor(random()*4)]
      WHEN rating = 3 THEN (ARRAY['Decent', 'Okay', 'Average', 'Neutral'])[1 + floor(random()*4)]
      WHEN rating = 2 THEN (ARRAY['Poor', 'Below average', 'Not great', 'Weak'])[1 + floor(random()*4)]
      ELSE (ARRAY['Bad', 'Terrible', 'Awful', 'Do not buy'])[1 + floor(random()*4)]
    END,

    product_id,
    user_id
  FROM scored;

END $$;