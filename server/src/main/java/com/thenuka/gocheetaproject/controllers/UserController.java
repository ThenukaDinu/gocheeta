package com.thenuka.gocheetaproject.controllers;

import com.thenuka.gocheetaproject.dto.UserDto;
import com.thenuka.gocheetaproject.dto.UserRoleDTO;
import com.thenuka.gocheetaproject.interfaces.IUserService;
import com.thenuka.gocheetaproject.requests.UserRoleRequest;
import com.thenuka.gocheetaproject.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

@RequestMapping("/user")
@RestController
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable(value = "userId") int id) {
        try {
            if (id <= 0) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            UserRoleDTO userRoleDTO = userService.getUser(id);
            if (userRoleDTO == null) {
                return new ResponseEntity(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userRoleDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody UserDto userRequest, @PathVariable(value = "userId") int id) {
        try {
            UserRoleDTO userRoleDTO = userService.update(id, userRequest);
            if (userRoleDTO == null) {
                return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(userRoleDTO, HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'DRIVER')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "userId") int id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "assign-roles/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> assignRoles(@RequestBody UserRoleRequest userRoleRequest, @PathVariable(value = "userId") int id) {
        try {
            UserRoleDTO userRoleDTO = userService.assignRoles(id, userRoleRequest);
            if (userRoleDTO == null) {
                return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(userRoleDTO, HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("No records found with given id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "update-avatar/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAvatar(@RequestParam("image") MultipartFile multipartFile, @PathVariable(value = "userId") int id) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            UserRoleDTO userReturned = userService.saveAvatar(id, fileName);
            String uploadDir = "static/user-photos/" + userReturned.getUserId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return new ResponseEntity<>(userReturned, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(
            value = "/get-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody ResponseEntity<?> getImageWithMediaType(@RequestParam String url) {
        try {
            String path = new FileSystemResource("").getFile().getAbsolutePath();
            Path fullPath = Paths.get(path, url);
            InputStream in = getClass()
                    .getResourceAsStream(fullPath.toString());
            return new ResponseEntity<>(IOUtils.toByteArray(in), HttpStatus.OK);
        }
         catch (Exception e) {
             e.printStackTrace();
         }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
