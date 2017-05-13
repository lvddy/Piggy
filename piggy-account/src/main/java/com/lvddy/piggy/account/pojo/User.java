package com.lvddy.piggy.account.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Created by Administrator on 2017/5/13 0013.
 */
@Data
@Builder
public class User {

    @Tolerate
    public User(){}

    private String nickName;

}
