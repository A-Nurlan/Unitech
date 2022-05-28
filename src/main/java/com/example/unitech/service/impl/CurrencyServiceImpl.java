package com.example.unitech.service.impl;

import com.example.unitech.resource.CurrencyResponseDto;
import com.example.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.util.Objects;

import static com.example.unitech.constant.ApiConstants.CURRENCY_URL;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate restTemplate;

    private Cache<String, BigDecimal> currencyCache;

    @PostConstruct
    public void init() {
        cacheManager.init();

        currencyCache = cacheManager
                .createCache("keyCache", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                String.class,
                                BigDecimal.class,
                                ResourcePoolsBuilder.heap(1000))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60))));
    }


    @Override
    public BigDecimal getCurrency(String from, String to) {
        String cacheKey = from + "-" + to;

        if (currencyCache.containsKey(cacheKey)) {
            return currencyCache.get(cacheKey);
        }

        BigDecimal value = queryLatestCurrencyRate(from, to);

        currencyCache.put(cacheKey, value);

        return value;
    }

    private BigDecimal queryLatestCurrencyRate(String from, String to) {
        String url = CURRENCY_URL + "?to=" + to + "&from=" + from + "&amount=" + "1";
        ResponseEntity<CurrencyResponseDto> response = restTemplate.exchange(
                buildEntityWithAuth(url, HttpMethod.GET, currencyHttpEntity())
                , CurrencyResponseDto.class);
        return Objects.requireNonNull(response.getBody()).getInfo().getRate();
    }

    private HttpEntity<String> currencyHttpEntity() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        headers.add(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.8");
        return new HttpEntity<String>(null, headers);
    }


    @SneakyThrows
    public <T> RequestEntity<T> buildEntityWithAuth(String url, HttpMethod method, T body) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("apikey", "stQKFtdwN367CIqa7j8YtxX5eC7F1wzk");
        return new RequestEntity<T>(body, headers, method, new URI(url));
    }

    private final CacheManager cacheManager = CacheManagerBuilder
            .newCacheManagerBuilder().build();

}
