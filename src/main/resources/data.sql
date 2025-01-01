INSERT INTO `roles` (`id`, `name`)
VALUES ('1', 'ADMIN'), ('2', 'USER')
    ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `users` (`id`, `created_at`, `email`, `password`, `updated_at`, `username`, `target`)
VALUES ('1',
        '2024-12-27 02:54:03.872532',
        'admin@example.com',
        '$2a$10$rWUIRxnV8sEHwK19OK10uO04BZb7P.QUu1HG/6oQN7MIA4zMqhq82',
        '2024-12-27 02:54:03.872532',
        'admin',
        '0'),
       ('2',
        '2025-01-02 01:09:28.259875',
        'user@example.com',
        '$2a$10$YuT9ITUPgwAPc3Cc4jyHpeG8OzepSfxXSvMl6FodAGXMBTHFOj0Pe',
        '2025-01-02 01:09:28.259875',
        'user',
        '0')
    ON DUPLICATE KEY UPDATE
                         `email` = VALUES(`email`),
                         `password` = VALUES(`password`),
                         `updated_at` = VALUES(`updated_at`),
                         `username` = VALUES(`username`),
                         `target` = VALUES(`target`);

INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES ('1', '1'), ('2', '2')
    ON DUPLICATE KEY UPDATE `role_id` = VALUES(`role_id`);
