<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- Bootstrap DateTimePicker CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css">

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap JavaScript -->
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- Moment.js (required by Bootstrap DateTimePicker) -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>

<!-- Bootstrap DateTimePicker JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet" href="/static/css/static.css">
<style>
.row.content {
	margin: 10px;
	height: 855px;
	box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
}

.sidenav {
	background-color: #f1f1f1;
	height: 805px;
	padding-top: 20px;
}

.cus-pagination {
	text-align: center;
}

.up-btn-gp {
	text-align: right;
}

.mrg_form {
	margin-bottom: 10px;
}

.table {
	margin-top: 20px;
	margin-bottom: 10px !important;
}

.custom-label {
	display: block;
	margin-bottom: 15px;
}

.custom-checkbox {
	width: 20px;
	height: 20px;
	margin-right: 5px;
}

.custom-color_box {
	width: 30px;
	height: 30px;
}

.cus_left-panel {
	border: 1px solid #000;
	padding: 10px;
}

.cus_modal-footer {
	padding: 15px;
	text-align: right;
	border: none;
}

.mt-link {
	margin-top: 20px;
}
</style>
<title><%=request.getAttribute("title")%></title>
</head>
<body>