package com.example.backenddevtest.infrastructure.client;

import com.example.backenddevtest.domain.ProductDetail;
import com.example.backenddevtest.infrastructure.mapper.ProductDetailMapper;
import com.example.backenddevtest.infrastructure.model.ProductsApiProductDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsApiClient {
    private static final String BASE_URL = "http://localhost:3001";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductDetailMapper mapper;

    public ProductsApiClient(ProductDetailMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<ProductDetail> getProductDetail(String productId) throws IOException {
        Request request = new Request.Builder()
                .method("GET", null)
                .url(String.format("%s/product/%s", BASE_URL, productId))
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();

            if (response.code() != 200 || body == null) {
                return Optional.empty();
            }

            ProductsApiProductDetail details = objectMapper.readValue(body.bytes(), ProductsApiProductDetail.class);
            return Optional.ofNullable(mapper.toDomainEntity(details));
        }
    }

    public List<String> getProductSimilarIds(String productId) throws IOException {
        Request request = new Request.Builder()
                .method("GET", null)
                .url(String.format("%s/product/%s/similarids", BASE_URL, productId))
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();

            if (response.code() != 200 || body == null) {
                return List.of();
            }

            return List.of(objectMapper.readValue(body.bytes(), String[].class));
        }
    }
}
