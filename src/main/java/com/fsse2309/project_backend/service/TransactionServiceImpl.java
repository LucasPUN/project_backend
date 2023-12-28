package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.domainObject.TransactionDataOut;
import com.fsse2309.project_backend.entity.TransactionEntity;
import com.fsse2309.project_backend.entity.TransactionProductEntity;
import com.fsse2309.project_backend.entity.UserEntity;
import com.fsse2309.project_backend.exception.TransactionNotFound;
import com.fsse2309.project_backend.repository.TransactionRepository;
import com.fsse2309.project_backend.status.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private UserService userService;
    private TransactionProductService transactionProductService;
    private ProductService productService;
    private CartItemService cartItemService;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(UserService userService, TransactionProductService transactionProductService, ProductService productService, CartItemService cartItemService, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.transactionProductService = transactionProductService;
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDataOut prepareTransaction(FirebaseUserData firebaseUserData){
        UserEntity userEntity = getEntityByFirebaseUserData(firebaseUserData);

        TransactionEntity transactionEntity = new TransactionEntity(userEntity);
        transactionEntity = transactionRepository.save(transactionEntity);

        List<TransactionProductEntity> transactionProductEntityList = transactionProductService.createEntityList(transactionEntity);
        transactionEntity.setTransactionProductEntityList(transactionProductEntityList);

        transactionEntity.setTotal(getTotal(transactionProductEntityList));
        transactionEntity = transactionRepository.save(transactionEntity);

        TransactionDataOut transactionDataOut = new TransactionDataOut(transactionEntity);
        return transactionDataOut;
    }

    @Override
    public TransactionDataOut getTransactionByTid(FirebaseUserData firebaseUserData, Integer tid){
        UserEntity userEntity = getEntityByFirebaseUserData(firebaseUserData);

        TransactionEntity transactionEntity = getEntityByTid(tid);

        if (!transactionEntity.getUserEntity().getUid().equals(userEntity.getUid())){
            throw new TransactionNotFound();
        }

        return new TransactionDataOut(transactionEntity);
    }

    @Override
    public boolean processTransacton(FirebaseUserData firebaseUserData, Integer tid){
        TransactionEntity transactionEntity = transactionRepository.getByTidAndUserEntity_Uid(
                tid, getEntityByFirebaseUserData(firebaseUserData).getUid()
        );

        if (transactionEntity.getStatus() != TransactionStatus.PREPARE){
            throw new TransactionNotFound();
        }

        transactionEntity.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(transactionEntity);

        return true;
    }

    @Override
    public TransactionDataOut finishTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        UserEntity userEntity = getEntityByFirebaseUserData(firebaseUserData);

        TransactionEntity transactionEntity = transactionRepository.getByTidAndUserEntity_Uid(
                tid,
                userEntity.getUid()
        );

        if (transactionEntity.getStatus() != TransactionStatus.PROCESSING){
            throw new TransactionNotFound();
        }

        for (TransactionProductEntity transactionProductEntity : transactionEntity.getTransactionProductEntityList()){
            productService.deductStock(transactionProductEntity.getPid(),transactionProductEntity.getQuantity());
        }

        cartItemService.emptyUserCart(userEntity);


        transactionEntity.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transactionEntity);

        return new TransactionDataOut(transactionEntity);
    }


    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData){
        return userService.getEntityByFirebaseUserData(firebaseUserData);
    }

    public BigDecimal getTotal(List<TransactionProductEntity> transactionProductEntityList){
        BigDecimal total = BigDecimal.ZERO;
        for (TransactionProductEntity transactionProductEntity : transactionProductEntityList){
            BigDecimal price = transactionProductEntity.getPrice();
            Integer quantity = transactionProductEntity.getQuantity();
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        return total;
    }

    public TransactionEntity getEntityByTid(Integer tid){
        return transactionRepository.findById(tid).orElseThrow(TransactionNotFound::new);
    }
}
