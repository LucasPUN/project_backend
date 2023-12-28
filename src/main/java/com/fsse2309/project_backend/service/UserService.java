package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.entity.UserEntity;

public interface UserService {

    UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData);
}
