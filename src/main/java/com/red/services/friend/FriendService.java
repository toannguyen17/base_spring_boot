package com.red.services.friend;

import com.red.model.Friend;
import com.red.services.IGeneralService;

public interface FriendService extends IGeneralService<Friend> {
    boolean isFriend(Long id);
}
