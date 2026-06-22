package com.example.survey.service;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.survey.dto.UserDto;
import com.example.survey.entity.Users;
import com.example.survey.repository.UserRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  // Lombok generates constructor for all final fields (replaces @Autowired)
public class SurveyService {

    private final UserRespository userRepository;

    // CacheManager lets us manually read/write Redis cache inside method logic
    private final CacheManager cacheManager;

    public Users createSurvey(UserDto userdto) {

        // Step 1: Get the "surveys" cache bucket from Redis
        Cache cache = cacheManager.getCache("surveys");

        // Step 2: Check if an entry with this first_name already exists in Redis
        // Key = first_name, so two users with same first name are treated as duplicates
        Cache.ValueWrapper cached = cache.get(userdto.getFirst_name());

        if (cached != null) {
            // Cache HIT → entry exists in Redis, skip DB entirely and return cached value
            return (Users) cached.get();
        }

        // Cache MISS → first_name not found in Redis, create new entry in DB
        Users u = new Users();
        u.setFirst_name(userdto.getFirst_name());
        u.setLast_name(userdto.getLast_name());
        u.setEmail(userdto.getEmail());
        u.setDob(userdto.getDob());
        u.setGender(userdto.getGender());
        long currentTime = System.currentTimeMillis() / 1000L;
        u.setCreated_dt(currentTime);
        u.setUpdated_dt(currentTime);
        Users saved = userRepository.save(u);

        // Step 3: Store the new entry in Redis with key = first_name
        // TTL is 120s (configured in AppConfig), after which Redis auto-removes this key
        cache.put(userdto.getFirst_name(), saved);

        return saved;
    }

    public List<Users> getAllSurveys() {
        return userRepository.findAll();
    }

    // @Cacheable: Spring checks Redis first using surveyId as key
    // Cache HIT  → returns value directly from Redis (no DB query)
    // Cache MISS → runs the method, fetches from DB, then Spring stores result in Redis automatically
    @Cacheable(value = "surveys", key = "#surveyId")
    public Users getSurveyById(int surveyId) {
        return userRepository.findById(surveyId).get();
    }
}
