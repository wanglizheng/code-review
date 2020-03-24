package com.inspur.ihealth.codes.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void Async_A() {
        System.out.println("Async_A is running");
    }

    @Async
    public void Async_B() {
        System.out.println("Async_B is running");
    }

}
