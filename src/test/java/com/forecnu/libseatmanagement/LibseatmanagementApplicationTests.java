package com.forecnu.libseatmanagement;

import com.forecnu.libseatmanagement.Thread.MyThread;
import com.forecnu.libseatmanagement.entity.Seat;
import com.forecnu.libseatmanagement.entity.User;
import com.forecnu.libseatmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
class LibseatmanagementApplicationTests {

    @Autowired
    UserService userService;

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    @Test
    void contextLoads() {
        for(int i=0;i<3;i++){
            new Thread(new MyThread(userService,countDownLatch,i+1)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


