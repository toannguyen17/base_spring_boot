package com.red.services.friend;

import com.red.model.Friend;
import com.red.model.User;
import com.red.repository.users.FriendRepository;
import com.red.services.user.UserService;
import com.red.system.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private Auth auth;

    @Override
    public Iterable<Friend> findAll() {
        return friendRepository.findAll();
    }

    @Override
    public void save(Friend entity) {
        friendRepository.save(entity);
    }

    @Override
    public void update(Friend entity) {
        friendRepository.save(entity);
    }

    @Override
    public Optional<Friend> findById(Long id) {
        return friendRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        friendRepository.deleteById(id);
    }

    @Override
    public void delete(Friend entity) {
        friendRepository.delete(entity);
    }

    @Override
    public boolean isFriend(Long id) {

        User user = auth.user();
        User friendUser = userService.findById(id).get();
        Optional<Friend> f = friendRepository.findByUserIdAndFriendId(user, friendUser);
        if (f.isPresent()) {
            Friend friend = f.get();
            return friend.isStatus();
        }
        return false;
    }
}
