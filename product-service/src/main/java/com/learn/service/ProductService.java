package com.learn.service;

import com.learn.model.ProductDTO;
import com.learn.productproto.*;
import com.learn.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void getProductBySkuId(GetProduct request, StreamObserver<Product> responseObserver) {

        ProductDTO productDTO = productRepository.findBySkuId(request.getSkuId());
        System.out.println("Got Product by Sku " + productDTO.getId() + "  "  + productDTO.getSkuId());
        responseObserver.onNext(Product.newBuilder().setSkuId(productDTO.getSkuId()).setAvail(productDTO.isAvail()).setPrice(productDTO.getPrice()).setQty(productDTO.getQty()).setId(productDTO.getId()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void saveProduct(Product request, StreamObserver<Product> responseObserver) {

        //DTO is to map the object from the request

        ProductDTO pr = new ProductDTO();
        pr.setId(UUID.randomUUID().toString());
        pr.setSkuId(request.getSkuId());
        pr.setAvail(true);
        pr.setQty(request.getQty());
        pr.setPrice(request.getPrice());

        productRepository.save(pr);

        responseObserver.onNext(Product.newBuilder().setAvail(true).setPrice("SAVED").setSkuId(pr.getSkuId()).build());
        //this will send the response to the handler
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProducts(Empty request, StreamObserver<ProductList> responseObserver) {

        List<ProductDTO> productDTO = productRepository.findAll();
        List<Product> prList = new ArrayList<>();
        for(ProductDTO pr : productDTO){
            prList.add(Product.newBuilder().setAvail(pr.isAvail()).setId(pr.getId()).setQty(pr.getQty()).setPrice(pr.getPrice()).build());
        }

        responseObserver.onNext(ProductList.newBuilder().addAllPr(prList).build());
        responseObserver.onCompleted();
    }
}
