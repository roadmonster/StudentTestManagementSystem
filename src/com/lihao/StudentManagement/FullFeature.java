package com.lihao.StudentManagement;

import com.lihao.StudentManagement.bean.Student;
import com.lihao.StudentManagement.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import com.lihao.StudentManagement.BasicFeature;

public class FullFeature {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Choose the job:");
            System.out.println("Press 1 for insertion");
            System.out.println("Press 2 for update");
            System.out.println("Press 3 for deletion");
            System.out.println("Press 4 for checking");
            int job = Integer.parseInt(scanner.nextLine());
            if (job == 1) {
                insertNewStudent();
            } else if (job == 2) {
                updateStudentInfo();
            } else if (job == 3) {
                deleteStudentWithExamID();
            } else if (job == 4) {
                queryWithIDorExamID();
            }

            System.out.println("Would you like to do another job? (y/n)");
            String choice = scanner.nextLine();
            if("y".equalsIgnoreCase(choice)){
                exit = false;
            }else if("n".equalsIgnoreCase(choice)){
                exit = true;
            }
        }while(!exit);
    }

    /**
     * Create new student
     */
    public static void insertNewStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your test type:");
        System.out.println("Press 1 for Intermediate");
        System.out.println("Press 2 for Advanced");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("ID card number:");
        String id = scanner.nextLine();

        System.out.println("Exam ID number:");
        String examId = scanner.nextLine();

        System.out.println("Name:");
        String name = scanner.nextLine();

        System.out.println("City:");
        String city = scanner.nextLine();

        System.out.println("grade:");
        int grade = Integer.parseInt(scanner.nextLine());

        String sql = "insert into examstudent(Type, IDCard, ExamCard, StudentName, Location, Grade)values(?,?,?,?,?,?)";
        int insertCount = BasicFeature.update(sql, type, id, examId, name, city, grade );
        if(insertCount > 0){
            System.out.println("Successfully added student");
        }else{
            System.out.println("Adding failed");
        }

    }

    /**
     * Retrieve Student
     */
    public static void queryWithIDorExamID(){
        System.out.println("Choose your ID type:");
        System.out.println("a. Exam ID");
        System.out.println("b. ID");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();
        if("a".equalsIgnoreCase(selection)){
            System.out.println("Input your exam id number: ");
            String examId = scanner.nextLine();
            String sql =  "select FlowID flowId,Type type,IDCard id,ExamCard examId,StudentName studentName,Location location,Grade grade from examstudent where `ExamCard` = ?";
            Student student = getInstace(Student.class, sql, examId);
            if(student != null){
                System.out.println(student);
            }else{
                System.out.println("Exam id not existed");
            }
        }
        else if("b".equalsIgnoreCase(selection)){
            System.out.println("Input your id number: ");
            String Id = scanner.nextLine();
            String sql = "select Flow_ID flowId, Type type, IDCard id, ExamCard examId, StudentName studentName, Location location, Grade grade" +
                    "from examstudent where IDCard = ?";
            Student student = getInstace(Student.class, sql, Id);
            if(student != null){
                System.out.println(student);
            }else{
                System.out.println("ID not existed");
            }
        }
    }

    /**
     * Delete student with exam id
     */
    public static void deleteStudentWithExamID(){
        System.out.println("Enter student exam ID:");
        Scanner scanner = new Scanner(System.in);
        String examId = scanner.nextLine();

        String sql = "delete from examstudent where `ExamCard` = ?";
        int numberDeleted = BasicFeature.update(sql, examId);

        if(numberDeleted > 0){
            System.out.println("delete student succesfully");
        }
        else{
            System.out.println("record not existed");
        }

    }

    /**
     * Update student info
     */
    public static void updateStudentInfo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter exam id to retrieve:");
        String examId = scanner.nextLine();
        String sql = "select FlowID flowId,Type type,IDCard id,ExamCard examId,StudentName studentName,Location location,Grade grade from examstudent where `ExamCard` = ?";
        Student myStu = getInstace(Student.class, sql, examId);

        System.out.println("Choose which info to update:");
        System.out.println("Press 1 for name");
        System.out.println("Press 2 for id");
        System.out.println("Press 3 for location");
        System.out.println("Press 4 for grade");

        String sql2 = "update examstudent set `StudentName` = ? where `ExamCard` = ?";
        String sql3 = "update examstudent set `IDCard` = ? where `ExamCard` = ?";
        String sql4 = "update examstudent set `Location` = ? where `ExamCard` = ?";
        String sql5 = "update examstudent set `Grade` = ? where `ExamCard` = ?";

        int selection = Integer.parseInt(scanner.nextLine());

        if(selection == 1){
            System.out.println("Enter new name:");
            String name = scanner.nextLine();
            int updateCount = BasicFeature.update(sql2, name, examId);
            System.out.println((updateCount > 0)? "Update name successfully": "Update failure");

        }

        else if (selection == 2){
            System.out.println("Enter new IDCard number:");
            String id  = scanner.nextLine();
            int updateCount = BasicFeature.update(sql3, id, examId);
            System.out.println((updateCount > 0)? "Update IDcard successfully": "Update failure");
        }

        else if (selection == 3){
            System.out.println("Enter new location:");
            String location  = scanner.nextLine();
            int updateCount = BasicFeature.update(sql4, location, examId);
            System.out.println((updateCount > 0)? "Update location successfully": "Update failure");
        }

        else if (selection == 4){
            System.out.println("Enter new Grade:");
            int grade  = scanner.nextInt();
            int updateCount = BasicFeature.update(sql5, grade, examId);
            System.out.println((updateCount > 0)? "Update grade successfully": "Update failure");
        }




    }

    public static <T> T getInstace(Class<T>clazz, String sql, Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < args.length; i++){
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            if(rs.next()){
                T t = clazz.newInstance();
                for(int i = 0; i < columnCount; i++){
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);

                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }




}
