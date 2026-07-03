package com.example.rediscache;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;

public class PersonCacheApp {

    private static final int RECORD_COUNT = 10_000;
    private static final String KEY_PREFIX = "person:";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(50);

        try (JedisPool pool = new JedisPool(poolConfig, "localhost", 6379)) {
            try (Jedis jedis = pool.getResource()) {

                System.out.println("Connected to Redis: " + jedis.ping());

                insertPeople(jedis, objectMapper, RECORD_COUNT);

                Person[] retrieved = retrievePeople(jedis, objectMapper, RECORD_COUNT);

                System.out.println("\n--- Sample of retrieved Person objects ---");
                printSample(retrieved);

                System.out.println("\nTotal keys in Redis matching 'person:*': "
                        + jedis.keys(KEY_PREFIX + "*").size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertPeople(Jedis jedis, ObjectMapper mapper, int count) throws Exception {
        Random random = new Random();
        String[] sampleNames = {"Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona", "George", "Hannah"};

        long start = System.currentTimeMillis();

        for (int i = 1; i <= count; i++) {
            Person person = new Person(
                    i,
                    sampleNames[random.nextInt(sampleNames.length)] + "_" + i,
                    18 + random.nextInt(60)
            );

            String json = mapper.writeValueAsString(person);
            jedis.set(KEY_PREFIX + person.getId(), json);
        }

        long durationMs = System.currentTimeMillis() - start;
        System.out.printf("Inserted %,d Person objects in %,d ms (%.2f ops/sec)%n",
                count, durationMs, count / (durationMs / 1000.0));
    }

    private static Person[] retrievePeople(Jedis jedis, ObjectMapper mapper, int count) throws Exception {
        Person[] people = new Person[count];

        long start = System.currentTimeMillis();

        for (int i = 1; i <= count; i++) {
            String json = jedis.get(KEY_PREFIX + i);
            if (json != null) {
                people[i - 1] = mapper.readValue(json, Person.class);
            }
        }

        long durationMs = System.currentTimeMillis() - start;
        System.out.printf("Retrieved %,d Person objects in %,d ms (%.2f ops/sec)%n",
                count, durationMs, count / (durationMs / 1000.0));

        return people;
    }

    private static void printSample(Person[] people) {
        int[] sampleIndexes = {0, 1, 4999, 5000, people.length - 1};
        for (int idx : sampleIndexes) {
            if (idx >= 0 && idx < people.length && people[idx] != null) {
                System.out.println("[" + idx + "] " + people[idx]);
            }
        }
    }
}
