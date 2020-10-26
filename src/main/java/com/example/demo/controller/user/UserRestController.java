package com.example.demo.controller.user;

import com.example.demo.dto.user.LoginDto;
import com.example.demo.dto.user.RegisterDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.repository.device.DeviceLogRepository;
import com.example.demo.repository.device.DeviceRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.device.DeviceService;
import com.example.demo.service.auth.AuthenticationService;
import com.example.demo.service.message.Message;
import com.example.demo.service.message.ResponseMessage;

import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/rest/user/")
public class UserRestController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public UserRestController(UserRepository userRepository, DeviceRepository deviceRepository, DeviceLogRepository deviceLogRepository, AuthenticationService authenticationService, DeviceService deviceService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(HttpSession session, @RequestBody LoginDto loginDto) {
        if (authenticationService.login(loginDto, session)) {
            ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.LOGIN_SUCCESS);
            return reMessage;
        }
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.LOGIN_FAILURE);
        return reMessage;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Object register(@RequestBody RegisterDto registerDto
    ) {
        if (authenticationService.register(registerDto)) {
            ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.REGISTER_SUCCESS);
            return reMessage;
        }
        ResponseMessage reMessage = new ResponseMessage(HttpServletResponse.SC_OK, Message.REGISTER_FAILURE);
        return reMessage;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<UserDto> getList(@RequestParam(name = "page", required = false, defaultValue = "0") int pageIndex,
                                 @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return userService.getListUser(pageIndex,pageSize);
    }
}
