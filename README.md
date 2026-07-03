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
