package com.us.worlddetails.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OTPGenerator {
    private static final Integer EXPIRE_MIN = Integer.parseInt(System.getenv("OTP_EXPIRY"));
    private final LoadingCache<String, Integer> otpCache;
    private final Random random = new Random();

    public OTPGenerator() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public String generateOTP(String key) {
        int otp = random.nextInt(9999);
        otpCache.put(key, otp);
        return String.valueOf(otp);
    }

    public String getOtpByKey(String key) {
        try {
            return String.valueOf(otpCache.get(key));
        } catch (ExecutionException e) {
            return "";
        }
    }
}
