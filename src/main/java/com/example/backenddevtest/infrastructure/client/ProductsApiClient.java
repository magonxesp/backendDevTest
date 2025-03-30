package com.example.backenddevtest.infrastructure.client;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.infrastructure.mapper.ProductDetailMapper;
import com.example.backenddevtest.infrastructure.model.ProductsApiProductDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsApiClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductDetailMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${products-api.cache.base-url:http://localhost:3001}")
    private String baseUrl = "http://localhost:3001";

    public ProductsApiClient(ProductDetailMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<ProductDetail> getProductDetail(String productId) throws IOException {
        String url = String.format("%s/product/%s", baseUrl, productId);
        Request request = new Request.Builder()
                .method("GET", null)
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();

            if (response.code() != 200 || body == null) {
                logger.warn("Products api {} responded with status code {}", url, response.code());
                return Optional.empty();
            }

            ProductsApiProductDetail details = objectMapper.readValue(body.bytes(), ProductsApiProductDetail.class);
            return Optional.ofNullable(mapper.toDomainEntity(details));
        }
    }

    public List<String> getProductSimilarIds(String productId) throws IOException {
        String url = String.format("%s/product/%s/similarids", baseUrl, productId);
        Request request = new Request.Builder()
                .method("GET", null)
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();

            if (response.code() != 200 || body == null) {
                logger.warn("Products api {} responded with status code {}", url, response.code());
                return List.of();
            }

            return List.of(objectMapper.readValue(body.bytes(), String[].class));
        }
    }
}
