package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.entity.UserEntity;
import com.fsse2309.project_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData){
        Optional<UserEntity> optionalUserEntity =
            userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid());

        if (optionalUserEntity.isEmpty()){
            UserEntity userEntity = new UserEntity(firebaseUserData);
            return userRepository.save(userEntity);
        }
        return optionalUserEntity.get();
    }
}
