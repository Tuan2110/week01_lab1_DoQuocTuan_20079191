<%@ page import="java.util.List" %>
<%@ page import="com.edu.iuh.fit.week1.models.Account" %>
<%@ page import="com.edu.iuh.fit.week1.repositories.RoleRepository" %>
<%@ page import="com.edu.iuh.fit.week1.models.Role" %>
<%@ page import="com.edu.iuh.fit.week1.models.GrantAccess" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="com.edu.iuh.fit.week1.models.Logs" %>
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
    Account account = (Account) session.getAttribute("account");
    List<Role> roles = (List<Role>) request.getAttribute("roles");
    List<GrantAccess> grantAccesses = (List<GrantAccess>) request.getAttribute("grantAccesses");
    String url = request.getScheme() +"://"
            +request.getServerName() + ":"
            +request.getServerPort()
            +request.getContextPath();
    int log_id = (int) session.getAttribute("log_id");
%>
<div class="container">
    <div class="row">
        <div class="col-2">
            <nav class="navbar bg-light">
                <div class="container-fluid">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                        <li>
                            <a href="<%=url%>/week1?action=home">Home</a>
                        </li>
                        <li>
                            <a href="<%=url%>/week1?action=role">Role</a>
                        </li>
                        <li>
                            <a href="<%=url%>/week1?action=log">Log</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <%
            for (GrantAccess grantAccess1 : grantAccesses){
                if(grantAccess1.getAccount().getAccountId() == account.getAccountId()){
                    for (Role role1 : roles){
                        if(role1.getRoleId() == grantAccess1.getRole().getRoleId()){
                            if(role1.getRoleName().equals("ADMIN")){
        %>
            <jsp:include page="dashboard.jsp"></jsp:include>
        <%
                            }else if(role1.getRoleName().equals("USER")){
        %>
            <div class="col-8">
            <h1>User Information</h1>
                <p><b>Full name :</b> <%=account.getFullName()%> </p>
                <p><b>Email :</b> <%=account.getEmail()%></p>
                <p><b>Phone :</b> <%=account.getPhone()%></p>
                <p><b>Status :</b> <%=account.getStatus()%></p>
                <p><b>Role :</b>
                    <%
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
                </p>
            </div>
        <%
                            }
                        }
                    }
                }
            }
        %>
        <div class="col-2">
            <p><b><%=account.getFullName()%></b></p>
            <p><%=log_id%></p>
            <a href="<%=url%>/week1?action=log-out&log-id=<%=log_id%>" type="submit" class="btn btn-primary">Log Out</a>
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
        for(Account acc: accounts){
        %>
            if(accountId === <%=acc.getAccountId()%>) {
                var fullName = "<%=acc.getFullName()%>";
                var email = "<%=acc.getEmail()%>";
                var phone = "<%=acc.getPhone()%>";
                var status = "<%=acc.getStatus()%>";
                var roles = "";
                <%-- Lặp qua các role của tài khoản và lưu vào biến roles --%>
                <%
                    for (GrantAccess grantAccess : grantAccesses){
                        if(grantAccess.getAccount().getAccountId() == acc.getAccountId()){
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
