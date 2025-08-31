package com.example.E_Commerce.E_commerce.Controller.Auth;

import com.example.E_Commerce.E_commerce.Exceptions.Customer.InvalidPropertyMyUserId;
import com.example.E_Commerce.E_commerce.Models.Auth.entities.MyUser;
import com.example.E_Commerce.E_commerce.Models.Response.MyProfileResponse;
import com.example.E_Commerce.E_commerce.Service.Auth.UserService;
import com.example.E_Commerce.E_commerce.Service.admin.JwtExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class UserInformationController {
    @Autowired
    JwtExtractService jwtExtractService;

    @Autowired
    UserService userService;

    @GetMapping("/myProfile/all")
    public ResponseEntity<?> getProfile(HttpServletRequest httpServletRequest) throws InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        MyUser myUser = userService.loadMyUserByUsername(username);
        MyProfileResponse myProfileResponse = new MyProfileResponse(myUser);
        return ResponseEntity.ok(myProfileResponse);
    }

    @PostMapping("/myProfile/update")
    public ResponseEntity<?> updateProfile(@RequestBody MyProfileResponse myProfileResponse, HttpServletRequest httpServletRequest) throws InvalidPropertyMyUserId {
        String username = jwtExtractService.getUserName(httpServletRequest);
        try {
            userService.updateMyUserByUsername(username, myProfileResponse);
            return ResponseEntity.ok("Updated Profile Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/myProfile/firstName")
    public ResponseEntity<?> getFirstName(HttpServletRequest httpServletRequest) throws InvalidPropertyMyUserId {
        try {
            String username = jwtExtractService.getUserName(httpServletRequest);
            MyUser myUser = userService.loadMyUserByUsername(username);
            HashMap<String, String> map = new HashMap<>();
            map.put("firstName", myUser.getFirstName());
            map.put("status", "success");
            return ResponseEntity.ok(map);

        } catch (InvalidPropertyMyUserId invalidPropertyMyUserId) {
            HashMap<String, String> map = new HashMap<>();
            map.put("status", invalidPropertyMyUserId.getMessage());
            return ResponseEntity.ok(map);
        }

    }

}
