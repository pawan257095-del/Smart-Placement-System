package com.smartplacement.service;

import com.smartplacement.dao.EligibilityDAO;
import com.smartplacement.model.EligibilityResult;

public class EligibilityService {

    private final EligibilityDAO eligibilityDAO;

    public EligibilityService() {
        this.eligibilityDAO = new EligibilityDAO();
    }

    public EligibilityResult checkEligibility(
            int userId,
            int jobId) {

        return eligibilityDAO.checkEligibility(
                userId,
                jobId
        );
    }
}