package com.example.demo.service.user;

import com.example.demo.dto.Map;
import com.example.demo.dto.user.ListUserDto;
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

    public ListUserDto getListUser(int page, int pageSize, String countOnly){
        List<UserDto> userList = new ArrayList<>();
        Pageable paging = PageRequest.of(page, pageSize);
        Page<User> users = userRepository.findAll(paging);
        if (countOnly.equals("N")){
            for (User user :
                    users) {
                userList.add(map.userDto(user));
            }
            return new ListUserDto(userList);
        }
        return new ListUserDto(users.getTotalElements(),users.getTotalPages(), userList);
    }

    public Long countUser(){
        return userRepository.count();
    }
}
