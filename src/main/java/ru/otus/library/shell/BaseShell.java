package ru.otus.library.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.config.LibraryProps;

import java.util.List;


public class BaseShell {

    @Autowired
    private LibraryProps props;

    private RestTemplate restTemplate;

    public BaseShell() {
        restTemplate = new RestTemplate();
    }

    private String createURL(String uri) {
        return props.getApi() + uri;
    }

    <T> List<T> getResultList(String uri, ParameterizedTypeReference<List<T>> response) {
        return restTemplate.exchange(createURL(uri), HttpMethod.GET, null, response).getBody();
    }

    <T, K> T postRequest(String uri, K body, ParameterizedTypeReference<T> response) {
        HttpEntity<K> httpEntity = new HttpEntity<>(body, new HttpHeaders());
        return restTemplate.exchange(
                createURL(uri),
                HttpMethod.POST,
                httpEntity,
                response
        ).getBody();
    }
}
