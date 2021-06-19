package com.example.client;

import com.example.config.BookClientProperties;
import com.example.entity.Book;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Service
public class BookClient {

    private final WebClient webClient;

    public BookClient(BookClientProperties bookClientProperties, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(bookClientProperties.getUrl())
                .build();
    }

    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient.get().uri(isbn)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> {
                    log.error(exception.toString());
                    return Mono.empty();
                });

    }
}
