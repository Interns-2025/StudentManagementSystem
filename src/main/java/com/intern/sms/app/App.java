package com.intern.sms.app;

import com.intern.sms.controller.UserController;

public class App {
    //starting the app
    public static void main(String[] args) {
        UserController controller = new UserController();
        controller.start();
    }
}
