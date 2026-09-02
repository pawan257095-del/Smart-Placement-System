package com.smartplacement.service;

import com.smartplacement.dao.RecruiterDAO;

public class RecruiterService {

    private final RecruiterDAO recruiterDAO;

    public RecruiterService() {
        recruiterDAO = new RecruiterDAO();
    }

    /**
     * Finds the company ID associated with a recruiter user.
     */
    public int getCompanyIdByUserId(int userId) {

        return recruiterDAO.findCompanyIdByUserId(
                userId
        );
    }

    /**
     * Updates recruiter company profile.
     */
    public boolean updateCompanyProfile(
            int companyId,
            String companyName,
            String industry,
            String website,
            String email,
            String phone,
            String address,
            String description) {

        return recruiterDAO.updateCompany(
                companyId,
                companyName,
                industry,
                website,
                email,
                phone,
                address,
                description
        );
    }
}