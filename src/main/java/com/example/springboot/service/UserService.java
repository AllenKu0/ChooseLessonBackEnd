package com.example.springboot.service;

//import com.example.springboot.entity.Authenticator;
import com.example.springboot.entity.Student;
import com.example.springboot.exception.AlreadyExistsException;
//import com.example.springboot.repository.AuthenticatorRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.request.UserLoginRequest;
import com.example.springboot.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private AuthenticatorRepository authenticatorRepository;
//
//
//    private final Cache<ByteArray, PublicKeyCredentialCreationOptions> registrationCache;
//    private final Cache<String,  AssertionRequest> loginCache;
//    public UserService() {
//        this.loginCache = Caffeine.newBuilder().maximumSize(1000)
//                .expireAfterAccess(5, TimeUnit.MINUTES).build();
//        this.registrationCache = Caffeine.newBuilder().maximumSize(1000)
//                .expireAfterAccess(5, TimeUnit.MINUTES).build();
//    }

    public UserService(){

    }
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterRequest userRegisterRequest) {
        Optional<Student> userOptional = userRepository.findByAccount(userRegisterRequest.getAccount());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Save failed, the user name already exist.");
        }
        Student user = new Student(userRegisterRequest);
        userRepository.save(user);
    }

    public Optional<Student> findByEmail(String userName) {
        return userRepository.findByEmail(userName);
    }

    public String startLogin(UserLoginRequest userLoginRequest) {
        Optional<Student> userOptional = userRepository.findByAccount(userLoginRequest.getAccount());
        if(userOptional.isPresent()){
            if(userOptional.get().getPassword().equals(userLoginRequest.getPassword())){
//                System.out.println(userOptional.get().getPassword());
//                System.out.println(userLoginRequest.getPassword());
                return "Login Suesses";
            }else{
                throw new AlreadyExistsException("login failed, the password was wrong.");
            }
        }else{
            throw new AlreadyExistsException("login failed, the user name dosen't exist.");
        }
    }
}
