package org.example.springaipgvectorsearchdemo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springaipgvectorsearchdemo.model.Product;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    @Autowired
    VectorStore vectorStore;

    @Value("classpath:products.json")
    Resource resource;


    public List<Document> loaddata() {
        List<Document> documents =  readAndPrintJsonFile();
                /* List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("country", "UK", "year", 2020)),
                new Document("The World is Big and Salvation Lurks Around the Corner", Map.of("country", "BG", "year", 2018)),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("country", "NL", "year", 2023)),
                new Document("Exploring the depths of the ocean is like diving into a new world of wonder.", Map.of("country", "USA", "year", 2019)),
                new Document("Technology shapes the future but leaves our past behind.", Map.of("category", "Technology")),
                new Document("The evolution of artificial intelligence is transforming industries.", Map.of("category", "Technology", "year", 2021)),
                new Document("Mountains are the beginning and the end of all natural scenery.", Map.of("country", "CH", "year", 2022)),
                new Document("Books are a uniquely portable magic.", Map.of("author", "Stephen King", "genre", "Literature")),
                new Document("The stars are not afraid of the darkness; they only shine brighter.", Map.of("country", "AU", "year", 2021))
        );*/
        TextSplitter textSplitter = new TokenTextSplitter();

        for (Document document : documents) {
            List<Document> splitteddocs = textSplitter.split(document);

            try {
               // Sleep for 1 second
                vectorStore.add(splitteddocs);
                System.out.println("Added document: " + document.getContent());
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        System.out.println("Transformed documents: " + documents);
        return documents;
    }

    public List<Document> search(String query) {
        //List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(3));
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
         List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query)
                .withTopK(3)
                .withFilterExpression(filter.eq("brand", "Apple").build()));

        return results;
    }

    public List<Document> readAndPrintJsonFile() {
        List<Document> documents = new ArrayList<>();
        List<Product> products = getProducts();
        for (Product product : products) {
            Document document = new Document(product.getBrand() + " " + product.getDescription()
                    , Map.of("product_name",product.getProductName(),"brand",product.getBrand()));
            documents.add(document);
        }
        return documents;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            for (JsonNode node : jsonNode) {
                Product product = objectMapper.treeToValue(node, Product.class);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
