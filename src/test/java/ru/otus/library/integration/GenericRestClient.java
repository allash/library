package ru.otus.library.integration;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GenericRestClient {

    private static TestRestTemplate restTemplate = new TestRestTemplate();

    public static class Get<V> {
        public ResponseEntity<V> getResult(String url, Class<V> clazz) {
            return restTemplate.exchange(url, HttpMethod.GET, null, clazz);
        }

        public ResponseEntity<List<V>> getResultList(String url, ParameterizedTypeReference<List<V>> clazz) {
            return restTemplate.exchange(url, HttpMethod.GET, null, clazz);
        }
    }

    public static class PostOrPut<T, V> {
        public ResponseEntity<V> execute(String url, HttpMethod method, T data, Class<V> clazz) {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<T> entity = new HttpEntity<>(data, headers);

            return restTemplate.exchange(url, method,
                    entity, clazz);
        }
    }
}
