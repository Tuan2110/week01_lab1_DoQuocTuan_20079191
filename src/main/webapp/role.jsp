<%@ page import="com.edu.iuh.fit.week1.models.Role" %>
<%@ page import="java.util.List" %>
<%@ page import="com.edu.iuh.fit.week1.models.Account" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/12/2023
  Time: 11:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Role</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
            crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous"></script>
    <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
            crossorigin="anonymous"></script>
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
            integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
            crossorigin="anonymous"></script>
</head>
<body>
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
                            <a class="nav-link" href="role.jsp">Role</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="log.jsp">Log</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="col-8">
            <%
                List<Account> accounts = (List<Account>) request.getAttribute("accounts");
                List<Role> roles = (List<Role>) request.getAttribute("roles");
                String roleName = (String) request.getAttribute("role-name");
            %>

            <select class="form-select" aria-label="Default select example" id="roles" name="roles">
                <% for(Role role: roles){ %>
                    <option value="<%=role.getRoleName()%>"><%=role.getRoleName()%></option>
                <% } %>
            </select>
            <form action="week1" method="get">
                <input type="hidden" name="action" value="change-role">
                <input type="hidden" name="role-name" id="role-name" value="">
                <button type="submit" onclick="changeRole();" class="btn btn-primary" id="btnSearch">Search</button>
            </form>
            <h1>Account of Role <%=roleName%></h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
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
                </tr>
                <%
                    }
                %>
            </table>
        </div>
    </div>
    <script>
        function changeRole() {
            let roleName = document.getElementById("roles").value;
            document.getElementById("role-name").value = roleName;
        }
    </script>
</div>
</body>
</html>
