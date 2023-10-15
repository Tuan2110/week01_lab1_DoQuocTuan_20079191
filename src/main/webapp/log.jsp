<%@ page import="java.util.List" %>
<%@ page import="com.edu.iuh.fit.week1.models.Account" %>
<%@ page import="com.edu.iuh.fit.week1.models.Logs" %>
<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/12/2023
  Time: 11:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logs</title>
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
<%
    String url = request.getScheme() +"://"
            +request.getServerName() + ":"
            +request.getServerPort()
            +request.getContextPath();
    Account account = (Account) session.getAttribute("account");
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
        <div class="col-8">
            <h1>Logs</h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                <tr>
                    <th>Account Name</th>
                    <th>Log-In Time</th>
                    <th>Log-Out Time</th>
                    <th>Note</th>
                </tr>
                </thead>
                <%
                    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
                    List<Logs> logs = (List<Logs>) request.getAttribute("logs");
                %>
                <%
                    for(Logs log : logs){
                        Account accountLog = accounts.stream().filter(a -> a.getAccountId() == log.getAccount().getAccountId()).findFirst().orElse(null);
                %>
                <tr>
                    <td><%= accountLog.getFullName() %></td>
                    <td><% LocalDateTime loginTime = log.getLoginTime(); %>
                        <%= loginTime.getHour() +":"+loginTime.getMinute()+" "+ loginTime.getDayOfMonth()+":"+loginTime.getMonth().getValue()+":"+loginTime.getYear() %>
                    </td>
                    <td>
                        <% LocalDateTime logoutTime = log.getLogoutTime(); %>
                        <%if(logoutTime!=null){%>
                            <%= logoutTime.getHour() +":"+logoutTime.getMinute()+" "+ logoutTime.getDayOfMonth()+":"+logoutTime.getMonth().getValue()+":"+logoutTime.getYear() %>
                        <%}else{%>
                            <%}%>
                    </td>
                    <td><%= log.getNotes() %></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <div class="col-2">
            <p><b><%=account.getFullName()%></b></p>
            <a href="<%=url%>/week1?action=log-out&log-id=<%=log_id%>" type="submit" class="btn btn-primary">Log Out</a>
        </div>
    </div>
</div>
</body>
</html>
