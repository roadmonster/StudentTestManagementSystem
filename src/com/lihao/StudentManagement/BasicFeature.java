package com.lihao.StudentManagement;

import com.lihao.StudentManagement.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class BasicFeature {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert your name: ");
        String name = scanner.nextLine();
        System.out.println("Insert your email: ");
        String email = scanner.nextLine();
        System.out.println("Insert your birthday: ");
        String birth = scanner.nextLine();

        String sql = "insert into `customers`(name, email, birth)values(?, ?, ?)";
        int insertCount = update(sql, name, email, birth);
        if(insertCount > 0){
            System.out.println("User create successfully.");
        }
        else
            System.out.println("User creation failed");
    }

    /**
     * General purpose operation for Create, Delete and Update
     * @param sql
     * @param args
     * @return
     */
    public static int update(String sql, Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < args.length; i++){
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;

    }


}
