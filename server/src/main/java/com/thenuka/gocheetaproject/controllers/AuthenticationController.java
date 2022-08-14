package com.thenuka.gocheetaproject.controllers;
import com.thenuka.gocheetaproject.configurations.TokenProvider;
import com.thenuka.gocheetaproject.dto.JwtResponseDTO;
import com.thenuka.gocheetaproject.dto.UserDto;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.interfaces.IRoleService;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.modals.User;
import com.thenuka.gocheetaproject.requests.LoginRequest;
import com.thenuka.gocheetaproject.util.ISendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class AuthenticationController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    private ISendEmailUtil emailUtil;

    private final String mailBody = "<h2 style=\"text-align:center;color:#4E944F;\">Welcome To GoCheeta Service</h2>\n" +
            "<br/><br/>\n" +
            "\n" +
            "<p style=\"margin: 0 60px 0 60px;text-align:center;font-size:17px;line-height:2;\">\n" +
            "  <b>We</b> are here to provide you best vehicle ride experiance in Sri Lanka. When you ready just go into out site and make your first journey. We have several types of vehicles and great group of drivers to make your ride <b>safe</b> and <b>comfotable</b> and <b>quick</b>.\n" +
            "</p>\n" +
            "\n" +
            "<br/><br/>\n" +
            "\n" +
            "<div style=\"text-align:center;opacity:8;\">\n" +
            "  <img width=\"70%\" style=\"border-radius: 2%;box-shadow: 6px 9px 5px 2px rgba(0,0,0,0.14);\" src=\"https://images.unsplash.com/photo-1555215695-3004980ad54e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80\" alt=\"BMW\">\n" +
            "</div>\n" +
            "<br><br>\n" +
            "\n" +
            "<div style=\"margin: 0 0 16px 16px;\">\n" +
            "  <h3>Thanks and regards</h3>\n" +
            "  <div style=\"color:#4E944F;margin-bottom:8px;\"><b>GoCheeta Team</b></div>\n" +
            "  <div>Thenuka</div>\n" +
            "</div>\n";

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginRequest loginUser) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            User user = userService.findOne(loginUser.getUsername());
            return ResponseEntity.ok(new JwtResponseDTO(
                    token,
                    userService.convertEntityToDto(user)
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody UserDto user){
        try {
            User userDto = userService.save(user);
            if (userDto != null && userDto.getEmail() != null) {
                emailUtil.sendEmailWithAttachment(userDto.getEmail(), "Welcome mail", mailBody);
            }
            return new ResponseEntity(userDto, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(value="/send-mail", method = RequestMethod.GET)
    public ResponseEntity<?> sendMail() {
        try {
            emailUtil.sendEmailWithAttachment("testgocheeta@yopmail.com", "Test welcome mail", mailBody);
            return new ResponseEntity<>("the mail has been successfully sent", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
