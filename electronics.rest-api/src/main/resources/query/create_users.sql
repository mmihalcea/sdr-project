DO $$
DECLARE
  v_users int := 300;
  v_role_user_id int;
  addr_seq text;
BEGIN
  ----------------------------------------------------------------------
  -- PRECONDITIONS
  ----------------------------------------------------------------------
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
  -- 1) FIX address sequence
  ----------------------------------------------------------------------
  SELECT pg_get_serial_sequence('address', 'id') INTO addr_seq;
  EXECUTE
      'SELECT setval(''' || addr_seq || ''', (SELECT COALESCE(MAX(id),0) FROM address))';

  ----------------------------------------------------------------------
  -- 2) INSERT 10 NEW ADDRESSES
  ----------------------------------------------------------------------
  INSERT INTO address(city, lat, lon, number, post_code, street, county_id)
  SELECT
    (ARRAY['Bucharest','Cluj-Napoca','Iasi','Timisoara','Brasov'])[1 + floor(random()*5)]::text,
    (44.0 + random()*4.0),
    (22.0 + random()*6.0),
    (1 + floor(random()*150))::text,
    (100000 + floor(random()*89999))::text,
    ('Street ' ||
     (ARRAY['Union','Victory','Liberty','Flowers','Peace'])[1 + floor(random()*5)] || ' ' ||
     (ARRAY['A','B','C','D','E'])[1 + floor(random()*5)]
    )::text,
    (SELECT id FROM county ORDER BY random() LIMIT 1)
  FROM generate_series(1, 10);

  ----------------------------------------------------------------------
  -- 3) GENERATE USERS
  ----------------------------------------------------------------------
  INSERT INTO store_user(name, username, email, password, profile_pic, address_id)
  SELECT
    ('User ' || gs)::text,
    ('user' || gs || '_' || floor(random()*100000)::int)::text,
    ('user' || gs || '_' || floor(random()*100000)::int || '@mock.local')::text,
    ('mock_password_' || gs)::text,
    NULL,
    (SELECT id FROM address ORDER BY random() LIMIT 1)
  FROM generate_series(1, v_users) gs
  ON CONFLICT DO NOTHING;

  ----------------------------------------------------------------------
  -- 4) ASSIGN ROLE_USER
  ----------------------------------------------------------------------
  INSERT INTO store_user_role(user_id, role_id)
  SELECT u.id, v_role_user_id
  FROM store_user u
  ON CONFLICT DO NOTHING;

  ----------------------------------------------------------------------
  -- 5) ASSIGN 2–4 RANDOM CATEGORIES PER USER
  ----------------------------------------------------------------------
  INSERT INTO user_categories(user_id, category_id)
  SELECT u.id, c.id
  FROM store_user u
  JOIN LATERAL (
    SELECT id FROM category ORDER BY random() LIMIT (2 + floor(random()*3))::int
  ) c ON TRUE
  ON CONFLICT DO NOTHING;

  RAISE NOTICE 'Users seed DONE: users=%, addresses=10', v_users;

END $$;