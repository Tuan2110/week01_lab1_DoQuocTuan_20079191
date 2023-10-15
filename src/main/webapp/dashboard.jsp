<%@ page import="com.edu.iuh.fit.week1.models.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="com.edu.iuh.fit.week1.models.Role" %>
<%@ page import="com.edu.iuh.fit.week1.models.GrantAccess" %>
<%@ page import="com.edu.iuh.fit.week1.models.Logs" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    Account account = (Account) request.getAttribute("account");
    List<Role> roles = (List<Role>) request.getAttribute("roles");
    List<GrantAccess> grantAccesses = (List<GrantAccess>) request.getAttribute("grantAccesses");
    String url = request.getScheme() +"://"
            +request.getServerName() + ":"
            +request.getServerPort()
            +request.getContextPath();
    Logs log = (Logs) session.getAttribute("logs");
%>
<div class="col-8">
    <h1>Account Management</h1>
    <table class="table table-striped">
        <thead class="thead-dark">
        <tr>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Status</th>
            <th>Roles</th>
        </tr>
        </thead>
        <%
            for(Account acc: accounts){
        %>
        <tr>
            <td><%=acc.getFullName()%>
            </td>
            <td><%=acc.getEmail()%>
            </td>
            <td><%=acc.getPhone()%>
            </td>
            <td><%=acc.getStatus()%>
            </td>
            <td><%
                for (GrantAccess grantAccess : grantAccesses){
                    if(grantAccess.getAccount().getAccountId() == acc.getAccountId()){
                        for (Role role : roles){
                            if(role.getRoleId() == grantAccess.getRole().getRoleId()){
                                out.print(role.getRoleName() + " ");
                            }
                        }
                    }
                }
            %>
            </td>
            <td>
                <button class='btn btn-primary' onclick="update(<%=acc.getAccountId()%>)">Update</button>
                <button class='btn btn-danger' onclick="deleteAccount(<%=acc.getAccountId()%>)">Delete</button>
            </td>
        </tr>
        <%
            }
        %>
        <div class="modal fade" id="accountModal" tabindex="-1" aria-labelledby="accountModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="accountModalLabel">Account Information</h3>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="week1" method="post">
                            <input type="hidden" name="account-id" id="account-id">
                            <div class="mb-3">
                                <label for="full-name" class="form-label">Full Name</label>
                                <input type="text" class="form-control" id="full-name" name="full-name" placeholder="Full Name">
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                            </div>
                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone</label>
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="Phone">
                            </div>
                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" aria-label="Default select example" id="status" name="status">
                                    <option value="ACTIVE">ACTIVE</option>
                                    <option value="DEACTIVE">DEACTIVE</option>
                                    <option value="DELETED">DELETED</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="role" class="form-label">Role</label>
                                <%
                                    for(Role role : roles){
                                %>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="<%=role.getRoleName()%>" id="role" name="role">
                                    <label class="form-check-label" for="role">
                                        <%=role.getRoleName()%>
                                    </label>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                            <input type="hidden" name="action" value="update-account">
                            <input type="hidden" name="acc" value="<%=account%>">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </table>
</div>