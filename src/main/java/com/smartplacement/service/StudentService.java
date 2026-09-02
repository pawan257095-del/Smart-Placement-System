package com.smartplacement.service;

import com.smartplacement.dao.StudentDAO;
import com.smartplacement.model.Student;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        studentDAO = new StudentDAO();
    }

    public Student getStudentProfile(int userId) {

        System.out.println(
                "DEBUG: StudentService received user ID = " + userId
        );

        return studentDAO.findByUserId(userId);
    }

    /**
     * Updates editable student profile information.
     *
     * Enrollment number and email are intentionally
     * not included here.
     */
    public boolean updateStudentProfile(
            int userId,
            String phone,
            String course,
            String branch,
            int semester,
            double cgpa,
            int backlogs,
            int graduationYear) {

        return studentDAO.updateStudentProfile(
                userId,
                phone,
                course,
                branch,
                semester,
                cgpa,
                backlogs,
                graduationYear
        );
    }
}