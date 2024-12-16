INSERT INTO habit_types (name, description, created_at, updated_at)
VALUES
    ('Exercise', 'Physical activity aimed at improving fitness and overall health.', '2024-09-13 10:00:00', '2024-09-13 10:00:00'),
    ('Meditation', 'Practices to calm the mind and reduce stress through mindfulness techniques.', '2024-09-13 10:05:00', '2024-09-13 10:05:00'),
    ('Healthy Eating', 'Focus on balanced nutrition with wholesome, nutrient-rich foods.', '2024-09-13 10:10:00', '2024-09-13 10:10:00'),
    ('Sleep Hygiene', 'Habits that contribute to a good night’s rest, improving sleep quality.', '2024-09-13 10:15:00', '2024-09-13 10:15:00'),
    ('Hydration', 'Consuming adequate amounts of water daily for proper body function.', '2024-09-13 10:20:00', '2024-09-13 10:20:00');

INSERT INTO habits (habit_type_id, name, description, frequency)
VALUES
    (1, 'Daily Exercise', 'Exercise for at least 30 minutes every day', 'DAILY'),
    (2, 'Healthy Eating', 'Follow a balanced and nutritious diet', 'WEEKLY'),
    (3, 'Meditation', 'Practice mindfulness meditation', 'DAILY'),
    (4, 'Reading', 'Read at least 10 pages of a book', 'WEEKLY'),
    (5, 'Sleep 8 hours', 'Ensure at least 8 hours of sleep every night', 'DAILY');

INSERT INTO sub_plans (name, description, price, duration_days)
VALUES
    ('Basic Plan', 'Access to essential health resources', 9.99, 30),
    ('Premium Plan', 'Full access to all health resources and expert consultations', 19.99, 30),
    ('Annual Wellness', 'Year-round access to all resources with significant savings', 199.99, 365);

INSERT INTO experts (first_name, last_name, expertise, bio, created_at, updated_at)
VALUES
    ('Anna', 'Smith', 'Fitness Coach', 'Anna Smith is a certified fitness coach with 8 years of experience helping clients achieve their health and fitness goals.', '2024-09-13 10:30:00', '2024-09-13 10:30:00'),
    ('David', 'Johnson', 'Yoga Instructor', 'David Johnson is a yoga instructor specializing in mindfulness and flexibility, with over 10 years of teaching experience.', '2024-09-13 10:35:00', '2024-09-13 10:35:00'),
    ('Emily', 'Davis', 'Nutritionist', 'Emily Davis is a licensed nutritionist focusing on plant-based diets and overall well-being.', '2024-09-13 10:40:00', '2024-09-13 10:40:00'),
    ('Michael', 'Brown', 'Psychologist', 'Michael Brown is a psychologist specializing in mental health and stress management, working with individuals to enhance emotional resilience.', '2024-09-13 10:45:00', '2024-09-13 10:45:00'),
    ('Laura', 'Wilson', 'Physiotherapist', 'Laura Wilson has a decade of experience in physiotherapy, helping patients recover from injuries and improve mobility.', '2024-09-13 10:50:00', '2024-09-13 10:50:00');

INSERT INTO resources (expert_id, sub_plan_id,title, description, resource_type, content)
VALUES
    (1, 1, '10-Minute HIIT Workout', 'A quick high-intensity interval training workout to boost your metabolism and burn fat.', 'GUIDE', 'https://example.com/hiit-workout'),
    (2, 2,'Beginner Yoga Routine', 'A beginner-friendly yoga session focused on relaxation and flexibility.', 'GUIDE', 'https://example.com/yoga-beginner'),
    (3, 1,'Plant-Based Meal Plan', 'A comprehensive meal plan designed for those looking to follow a plant-based diet.', 'RECIPE', 'https://example.com/plant-meal-plan'),
    (4, 2,'Stress Management Techniques', 'Tips and exercises to help manage stress and improve mental well-being.', 'ARTICLE', 'https://example.com/stress-management'),
    (5, 2,'Post-Injury Rehabilitation Guide', 'A detailed guide on how to safely recover from common injuries.', 'WORKOUT_PLAN', 'https://example.com/injury-rehab');

INSERT INTO users (email, password, role, username)
VALUES
    ('john.doe@example.com', 'password123', 'USER', 'johndoe'),
    ('jane.smith@example.com', 'password456', 'ADMIN', 'janesmith'),
    ('michael.tan@example.com', 'password789', 'USER', 'michaeltan'),
    ('emily.green@example.com', 'password012', 'USER', 'emilygreen'),
    ('chris.brown@example.com', 'securepass789', 'USER', 'chrisbrown');

INSERT INTO profiles (user_id, user_name, height, weight, age, gender, health_conditions)
VALUES
    (1,'john doe',1.75, 70.5, 29, 'MALE', 'No known health conditions'),
    (2,'janes mith',1.65, 60.0, 32, 'FEMALE', 'Asthma'),
    (3,'michael tan',1.80, 85.0, 35, 'MALE', 'Hypertension'),
    (4,'emily green',1.70, 55.0, 28, 'FEMALE', 'Diabetes'),
    (5,'chris brown',1.82, 90.0, 40, 'MALE', 'No known health conditions');

INSERT INTO profile_resources (profile_id, resource_id, is_active, access_expiration)
VALUES
    (1, 1, true, '2024-11-01 23:59:59'),
    (1, 3, true, '2024-11-01 23:59:59'),
    (2, 2, true, '2025-10-01 23:59:59'),
    (2, 4, true, '2025-10-01 23:59:59'),
    (2, 5, true, '2025-10-01 23:59:59');

INSERT INTO subscriptions (profile_id, sub_plan_id, start_at, end_at, payment_status, subscription_status)
VALUES
    (1, 1, '2024-10-01 00:00:00', '2024-11-01 23:59:59', 'PAID', 'ACTIVE'),
    (2, 2, '2024-10-01 00:00:00', '2025-10-01 23:59:59', 'PAID', 'ACTIVE');

INSERT INTO plans (profile_id, name, description, start_date, end_date, plan_status)
VALUES
    (1, 'Weight Loss Plan', 'A plan focused on losing weight through exercise and healthy eating.', '2024-09-01 08:00:00', '2024-12-01 08:00:00', 'ACTIVE'),
    (2, 'Meditation and Mindfulness', 'A plan to reduce stress through daily meditation and mindfulness practices.', '2024-09-05 07:00:00', '2024-10-05 07:00:00', 'ACTIVE'),
    (3, 'Reading Challenge', 'Read 5 books in 3 months to improve knowledge and reading speed.', '2024-09-10 09:00:00', '2024-12-10 09:00:00', 'ACTIVE'),
    (4, 'Sleep Improvement Plan', 'Improve sleep quality by following a strict sleep schedule.', '2024-09-01 22:00:00', '2024-11-01 22:00:00', 'ACTIVE'),
    (5, 'Muscle Gain Plan', 'A plan focused on gaining muscle mass through weight training.', '2024-09-15 06:00:00', '2024-12-15 06:00:00', 'ACTIVE');

INSERT INTO goals (profile_id, habit_id, plan_id, target_value, current_value, start_date, end_date, goal_status)
VALUES
    (1, 1, 1, 80.0, 72.0, '2024-09-01 08:00:00', '2024-12-01 08:00:00', 'IN_PROGRESS'),
    (2, 3, 2, 30.0, 10.0, '2024-09-05 07:00:00', '2024-10-05 07:00:00', 'IN_PROGRESS'),
    (3, 5, 4, 8.0, 6.0, '2024-09-01 22:00:00', '2024-11-01 22:00:00', 'IN_PROGRESS'),
    (4, 4, 3, 5.0, 2.0, '2024-09-10 09:00:00', '2024-12-10 09:00:00', 'IN_PROGRESS'),
    (5, 2, 1, 70.0, 65.0, '2024-09-01 08:00:00', '2024-12-01 08:00:00', 'IN_PROGRESS');

INSERT INTO tracking_records (date, value, note, goal_id)
VALUES
    ('2024-09-18 07:30:00', 30.0, 'Completed morning workout as per the fitness plan', 1),
    ('2024-09-18 12:00:00', 2.0, 'Healthy lunch aligned with diet plan', 2),
    ('2024-09-17 21:30:00', 8.0, 'Achieved full sleep as per sleep improvement goal', 3),
    ('2024-09-18 17:00:00', 10.0, 'Read 10 pages, progressing towards the reading habit goal', 4),
    ('2024-09-18 19:00:00', 10.0, 'Read 10 pages, progressing towards the reading habit goal', 4);