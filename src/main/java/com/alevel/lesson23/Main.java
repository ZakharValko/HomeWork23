package com.alevel.lesson23;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();
        System.out.println(studentDao.getAll());
    }
}
