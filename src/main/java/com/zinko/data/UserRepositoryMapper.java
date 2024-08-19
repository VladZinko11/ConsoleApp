package com.zinko.data;

import com.zinko.data.entity.User;

public interface UserRepositoryMapper {

    User mapToUser(String str);

    String mapToString(User user);
}
