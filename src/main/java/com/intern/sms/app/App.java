package com.intern.sms.app;

import com.intern.sms.controller.UserController;

public class App {
    public static void main(String[] args) {
        UserController controller = new UserController();
        controller.start();
    }
}
