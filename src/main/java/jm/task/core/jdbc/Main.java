package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        for (int i = 0; i<4; i++) {
            String name = "user" + i;
            userService.saveUser(name, name + "_surname", (byte) (Math.random() * 100));
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        }

        for (User user : userService.getAllUsers()) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
