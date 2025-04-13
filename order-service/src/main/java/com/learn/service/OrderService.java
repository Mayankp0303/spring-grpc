package com.learn.service;


import com.google.rpc.Status;
import com.learn.Employee;
import com.learn.EmployeeById;
import com.learn.EmployeeServiceGrpc;
import com.learn.model.OrderDTO;
import com.learn.orderproto.OrderRequest;
import com.learn.orderproto.OrderResponse;
import com.learn.orderproto.OrderServiceGrpc;
import com.learn.productproto.GetProduct;
import com.learn.productproto.Product;
import com.learn.productproto.ProductServiceGrpc;
import com.learn.repository.OrderRepository;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrderService extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub;

    @Autowired
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    @Override
    public void placeOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        //check for customer info first
        // in our case Employee info is present in DB or not

        System.out.println("Inside service call");
        Employee emp =  employeeServiceBlockingStub.getEmployeeById(EmployeeById.newBuilder().setId(request.getCustomerId()).build());
        Product product  =   productServiceBlockingStub.getProductBySkuId(GetProduct.newBuilder().setSkuId(request.getSkuId()).build());

        System.out.println("After service call");
        if(emp.getId().equals("EMPTY")){
            responseObserver.onError(StatusProto.toStatusException(Status.newBuilder().setCode(400).setMessage("Customer not present in records").build()));
            responseObserver.onCompleted();
        }else{
            if(product.getQty()<0){
                responseObserver.onError(StatusProto.toStatusException(Status.newBuilder().setCode(400).setMessage("Item is not in stock").build()));
                responseObserver.onCompleted();
            }

        }
        System.out.println("Saving order in DAtaBAse");

        orderRepository.save(OrderDTO.builder().skuId(request.getSkuId()).order_id(request.getOrderId()).qty(request.getQty()).customer_id(request.getCustomerId()).status("InProgress").originated(LocalDate.now().toString()).build());
        responseObserver.onNext(OrderResponse.newBuilder().setId("Done").setCustomerId(request.getCustomerId()).setSkuId(request.getSkuId()).build());
        responseObserver.onCompleted();


    }

    @Override
    public void getOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        OrderDTO dto = orderRepository.findById(request.getOrderId()).get();
        responseObserver.onNext(OrderResponse.newBuilder().setId(dto.getOrder_id()).setCustomerId(dto.getCustomer_id()).setSkuId(request.getSkuId()).build());
        responseObserver.onCompleted();
    }
}
