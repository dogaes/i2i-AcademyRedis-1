# Redis Person Cache Demo

A small Java project demonstrating high-volume caching with **Redis**, using **Jedis** as the client and **RedisInsight** as the management GUI. The app connects to a local Redis instance, inserts 10,000 dummy `Person` objects, retrieves them back, and prints a sample to verify the round trip.

## What this demonstrates

- Running Redis and RedisInsight locally via Docker
- Connecting to Redis from Java using a `JedisPool`
- Serializing Java objects to JSON (via Jackson) for storage as Redis string values
- Bulk insert/retrieve performance for an in-memory data store

This will:
1. Connect to Redis on \`localhost:6379\`
2. Insert 10,000 \`Person\` objects as JSON strings (\`person:<id>\`)
3. Retrieve all 10,000 back and print a sample
4. Report insert/retrieve timing and throughput

You can also inspect the stored keys live in RedisInsight's key browser.

## Project structure

\`\`\`
person-cache-demo/
├── pom.xml
└── src/main/java/com/example/rediscache/
    ├── Person.java          # dummy domain object (id, name, age)
    └── PersonCacheApp.java  # connects, inserts, retrieves, verifies
\`\`\`

<img width="577" height="432" alt="Screenshot 2026-07-03 at 9 10 01 PM" src="https://github.com/user-attachments/assets/e7ed4ff0-31bd-4ff7-895f-cdc112a3e942" />

<img width="766" height="684" alt="Screenshot 2026-07-03 at 8 52 09 PM" src="https://github.com/user-attachments/assets/4470298e-891b-4712-bdc2-0c5cde3820fd" />

<img width="1042" height="554" alt="Screenshot 2026-07-03 at 9 07 11 PM" src="https://github.com/user-attachments/assets/2a3db54c-cb6e-4af3-ab17-877d0444827a" />

