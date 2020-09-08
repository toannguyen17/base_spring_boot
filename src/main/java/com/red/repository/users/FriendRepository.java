package com.red.repository.users;

import com.red.model.Friend;
import com.red.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
//    Optional<Friend> findByUserIdAndFriendIdOrFriendIdAndUserId(User userId, User friendId, User userId1, User friendId1);

    @Query("SELECT f from Friend f WHERE f.userId = :u1 and f.friendId = :u2 or f.userId = :u2 and f.friendId = :u1")
    Optional<Friend> findByUserIdAndFriendId(@Param("u1") User u1,@Param("u2") User u2);
}
