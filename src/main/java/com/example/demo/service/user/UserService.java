package com.example.demo.service.user;

import com.example.demo.dto.Map;
import com.example.demo.dto.user.UserDto;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final Map map;
    public UserService(UserRepository userRepository, Map map) {
        this.userRepository = userRepository;
        this.map = map;
    }

    public List<UserDto> getListUser(int page, int pageSize){
        List<UserDto> userList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, pageSize);
        Page<User> users = userRepository.findAll(paging);
        for (User user :
                users) {
            userList.add(map.userDto(user));
        }
        return userList;
    }
}
