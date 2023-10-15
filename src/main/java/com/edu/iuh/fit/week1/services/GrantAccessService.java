package com.edu.iuh.fit.week1.services;

import com.edu.iuh.fit.week1.models.GrantAccess;
import com.edu.iuh.fit.week1.repositories.GrantAccessRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GrantAccessService {
    GrantAccessRepository grantAccessRepository;
    public GrantAccessService() {
        grantAccessRepository = new GrantAccessRepository();
    }
    public List<GrantAccess> getAllGrantAccesses() throws SQLException, ClassNotFoundException {
        return grantAccessRepository.getAllGrantAccesses();
    }
    public Optional<GrantAccess> getGrantAccessByRoleIdAndAcId(int roleId,int accountId) throws SQLException, ClassNotFoundException {
        return grantAccessRepository.getGrantAccessByRoleIdAndAcId(roleId,accountId);
    }
    public boolean insertGrantAccess(GrantAccess grantAccess) throws SQLException, ClassNotFoundException {
        return grantAccessRepository.insertGrantAccess(grantAccess);
    }
    public boolean deleteGrantAccessByAcId(int accountId) throws SQLException, ClassNotFoundException {
        return grantAccessRepository.deleteGrantAccessByAcId(accountId);
    }
}
