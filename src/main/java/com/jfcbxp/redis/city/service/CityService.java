package com.jfcbxp.redis.city.service;

import com.jfcbxp.redis.city.client.CityClient;
import com.jfcbxp.redis.city.dto.CityDTO;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

    private RMapCacheReactive<String, CityDTO> cityDTORMapReactive;


    public CityService(RedissonReactiveClient client) {
        this.cityDTORMapReactive = client.getMapCache("city", new TypedJsonJacksonCodec(String.class,CityDTO.class));
    }

    public Mono<CityDTO> getCity(final String zipCode){
        return this.cityDTORMapReactive.get(zipCode).switchIfEmpty(
                this.cityClient.getCity(zipCode)
                        .flatMap(c -> this.cityDTORMapReactive.fastPut(zipCode,c,10, TimeUnit.SECONDS)
                                .thenReturn(c))

        );
    }
}
