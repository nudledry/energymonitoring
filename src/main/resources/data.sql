INSERT INTO `roles` (`id`, `name`)
VALUES ('1', 'ADMIN'), ('2', 'USER');

INSERT INTO `users` (`id`, `created_at`, `email`, `password`, `updated_at`, `username`, `target`)
VALUES ('1',
        '2024-12-27 02:54:03.872532',
        'admin@example.com',
        '$2a$10$rWUIRxnV8sEHwK19OK10uO04BZb7P.QUu1HG/6oQN7MIA4zMqhq82',
        '2024-12-27 02:54:03.872532',
        'admin',
        '0');