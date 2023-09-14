<%@ page import="java.util.List" %>
<%@ page import="com.edu.iuh.fit.week1.models.Account" %>
<%@ page import="com.edu.iuh.fit.week1.repositories.RoleRepository" %>
<%@ page import="com.edu.iuh.fit.week1.models.Role" %>
<%@ page import="com.edu.iuh.fit.week1.models.GrantAccess" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
            crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous"></script>
</head>
<body>
<%
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    List<Role> roles = (List<Role>) request.getAttribute("roles");
    List<GrantAccess> grantAccesses = (List<GrantAccess>) request.getAttribute("grantAccesses");
    String url = request.getScheme() +"://"
            +request.getServerName() + ":"
            +request.getServerPort()
            +request.getContextPath();
%>
<div class="container">
    <div class="row">
        <div class="col-2">
            <nav class="navbar bg-light">
                <div class="container-fluid">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                        <%--to week1/login--%>
                            <a class="nav-link" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <form action="week1" method="GET">
                                <input type="hidden" name="action" value="role">
                                <button type="submit" class="btn btn-link">Role</button>
                            </form>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="log.jsp">Log</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
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
                    for(Account account: accounts){
                %>
                <tr>
                    <td><%=account.getFullName()%>
                    </td>
                    <td><%=account.getEmail()%>
                    </td>
                    <td><%=account.getPhone()%>
                    </td>
                    <td><%=account.getStatus()%>
                    </td>
                    <td><%
                        for (GrantAccess grantAccess : grantAccesses){
                            if(grantAccess.getAccount().getAccountId() == account.getAccountId()){
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
                        <button class='btn btn-primary' onclick="update(<%=account.getAccountId()%>)">Update</button>
                        <button class='btn btn-danger' onclick="deleteAccount(<%=account.getAccountId()%>)">Delete</button>
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
                                    <button type="submit" class="btn btn-primary">Save</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </table>
        </div>
    </div>
</div>
<div class="modal" id="deleteModel" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Delete Account</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Are you sure want to delete it?</p>
            </div>
            <form action="week1" method="post">
                <input type="hidden" name="account-id-delete" id="account-id-delete">
                <input type="hidden" name="action" value="delete-account">
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Yes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function deleteAccount(accountId) {
        document.getElementById('account-id-delete').value = accountId;
        $('#deleteModel').modal('show');
    }
    function update(accountId) {
        <%
        for(Account account: accounts){
        %>
            if(accountId === <%=account.getAccountId()%>) {
                var fullName = "<%=account.getFullName()%>";
                var email = "<%=account.getEmail()%>";
                var phone = "<%=account.getPhone()%>";
                var status = "<%=account.getStatus()%>";
                var roles = "";
                <%-- Lặp qua các role của tài khoản và lưu vào biến roles --%>
                <%
                    for (GrantAccess grantAccess : grantAccesses){
                        if(grantAccess.getAccount().getAccountId() == account.getAccountId()){
                            for (Role role : roles){
                                if(role.getRoleId() == grantAccess.getRole().getRoleId()){
                                    out.println("roles += '" + role.getRoleName() + " ';");
                                }
                            }
                        }
                    }
                %>
            }
        <%
        }
        %>
        document.getElementById('account-id').value = accountId;
        document.getElementById('full-name').value = fullName;
        document.getElementById('email').value = email;
        document.getElementById('phone').value = phone;
        document.getElementById('status').value = status;
        var roleCheckboxes = document.querySelectorAll('input[name="role"]');
        // is Checked for each role
        for (var i = 0; i < roleCheckboxes.length; i++) {
            if (roles.includes(roleCheckboxes[i].value)) {
                roleCheckboxes[i].checked = true;
            }else {
                roleCheckboxes[i].checked = false;
            }
        }
        $('#accountModal').modal('show');
    }
</script>
<script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
        integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
        crossorigin="anonymous"></script>
</body>
</html>
