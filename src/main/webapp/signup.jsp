<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/10/2023
  Time: 11:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
          crossorigin="anonymous">
</head>
<body>
<%
  String error = request.getAttribute("error") == null ? "" : (String) request.getAttribute("error");
%>
<section class="vh-100 bg-image"
         style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
  <div class="mask d-flex align-items-center h-100 gradient-custom-3">
    <div class="container h-100">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-9 col-lg-7 col-xl-6">
          <div class="card" style="border-radius: 15px;">
            <div class="card-body p-5">
              <h2 class="text-uppercase text-center mb-5">Create an account</h2>
              <h3>
                <%=
                  error
                %>
              </h3>
              <form action="week1" method="POST">

                <div class="form-outline mb-4">
                  <input type="text" id="full-name" name="full-name" class="form-control form-control-lg" />
                  <label class="form-label" for="full-name">Full Name</label>
                </div>
                <div class="form-outline mb-4">
                  <input type="text" id="phone" name="phone" class="form-control form-control-lg" />
                  <label class="form-label" for="phone">Phone</label>
                </div>
                <div class="form-outline mb-4">
                  <input type="email" id="email" name="email" class="form-control form-control-lg" />
                  <label class="form-label" for="email">Email</label>
                </div>
                <div class="form-outline mb-4">
                  <input type="password" id="password" name="password" class="form-control form-control-lg" />
                  <label class="form-label" for="password">Password</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="password" id="confirm-password" name="confirm-password" class="form-control form-control-lg" />
                  <label class="form-label" for="confirm-password">Confirm Password</label>
                </div>

                <div class="d-flex justify-content-center">
                  <input type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" value="Register"/>
                </div>

                <input type="hidden" name="action" value="register">
              </form>
              <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="login.jsp" class="fw-bold text-body"><u>Login here</u></a></p>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
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
