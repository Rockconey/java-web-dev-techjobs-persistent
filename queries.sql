## Part 1: Test it with SQL
    id int;
    employer varchar(255);
    name varchar(255);
    skills varchar(255);
## Part 2: Test it with SQL
SELECT employer.name
FROM employer;
## Part 3: Test it with SQL
DROP TABLE job;

## Part 4: Test it with SQL

SELECT skill.name, skill.description
FROM skill
INNER JOIN job_skills on skill.id = job_skills.skills_id
ORDER BY name asc;