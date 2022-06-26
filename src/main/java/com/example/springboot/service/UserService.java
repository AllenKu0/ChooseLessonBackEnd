package com.example.springboot.service;

//import com.example.springboot.entity.Authenticator;
import com.example.springboot.entity.Student;
import com.example.springboot.exception.AlreadyExistsException;
//import com.example.springboot.repository.AuthenticatorRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.request.StudentUpdateRequest;
import com.example.springboot.request.UserLoginRequest;
import com.example.springboot.request.UserRegisterRequest;
import com.example.springboot.response.StudentDetailResponse;
import com.example.springboot.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
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

    public List<StudentResponse> getAll(){
        List<StudentResponse> requestList = new ArrayList<>();
        for (Student student:userRepository.findAll()){
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setStudent_phone(student.getStudent_phone());
            studentResponse.setAccount(student.getAccount());
            studentResponse.setName(student.getName());
            requestList.add(studentResponse);
        }
        return requestList;
    }

    public StudentDetailResponse getStudent(String account){
        Optional<Student> studentOptional = userRepository.findByAccount(account);
        if(studentOptional.isPresent()){
            StudentDetailResponse studentResponse = new StudentDetailResponse();
            studentResponse.setId(studentOptional.get().getId());
            studentResponse.setName(studentOptional.get().getName());
            studentResponse.setAccount(studentOptional.get().getAccount());
            studentResponse.setEmail(studentOptional.get().getEmail());
            studentResponse.setPassword(studentOptional.get().getPassword());
            studentResponse.setStudent_phone(studentOptional.get().getStudent_phone());
            return studentResponse;
        }else{
            throw new AlreadyExistsException("Get Student failed.");
        }
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
                return "Login Successes";
            }else{
                throw new AlreadyExistsException("login failed, the password was wrong.");
            }
        }else{
            throw new AlreadyExistsException("login failed, the user name dosen't exist.");
        }
    }

    public String updateProfile(String account,StudentUpdateRequest studentUpdateRequest){
        Optional<Student> studentOptional = userRepository.findByAccount(account);
        if(studentOptional.isPresent()){
            studentOptional.get().setName(studentUpdateRequest.getName());
            studentOptional.get().setEmail(studentUpdateRequest.getEmail());
            studentOptional.get().setStudent_phone(studentUpdateRequest.getStudent_phone());
            userRepository.save(studentOptional.get());
            return "update Successes";
        }else{
            return "Student is not Searched";
        }
    }

    public String updatePassWord(String account,String password){
        Optional<Student> studentOptional = userRepository.findByAccount(account);
        if(studentOptional.isPresent()){
            studentOptional.get().setPassword(password);
            userRepository.save(studentOptional.get());
            return "update Successes";
        }else{
            return "Student is not Searched , update password failed";
        }
    }
}
