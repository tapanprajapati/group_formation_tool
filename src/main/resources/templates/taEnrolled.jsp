<!DOCTYPE HTML>
<html xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Catme Dal</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
 <style>
 body {
 padding-top:0px;
  padding-bottom: 40px;
  background-color: #eee;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
} </style>
  </head>
<body>
	<h3 class = ".form-signin-heading" align="center">TA enrollment</h1>
	<center>
    <p th:text="'Course Id: ' + ${user.courseId}" />
    <p th:text="'Banner Id: ' + ${user.bannerId}" />
    <p th:text="${message}" />
    </center>
</body>
</html>