package com.jfcbxp.redis.city.client;


import com.jfcbxp.redis.city.dto.CityDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityClient {
    private final WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url){
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public Mono<CityDTO> getCity(final String zipCode){
        return this.webClient.get().uri("{zipCode}",zipCode).retrieve().bodyToMono(CityDTO.class);
    }

    public Flux<CityDTO> getAll(){
        return this.webClient.get().retrieve().bodyToFlux(CityDTO.class);
    }

}
