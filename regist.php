
<?
    include('dbcon.php');
	
	$USER_ID = $_POST["USER_ID"];
	$USER_PASSWORD = $_POST["USER_PASSWORD"];
	$USER_NAME = $_POST["USER_NAME"];
	$USER_EMAIL = $_POST["USER_EMAIL"];
	$USER_PHONE = $_POST["USER_PHONE"];
	
		//쿼리문
		$statement = mysqli_prepare($conn, "insert into USER values (?,?,?,?,?,0)");
		
		mysqli_stmt_bind_param($statement,"sssss", $USER_ID, $USER_PASSWORD, $USER_NAME, $USER_EMAIL, $USER_PHONE);
		mysqli_stmt_execute($statement);
		
		$response =array();
		$response["success"] = true;
		
		echo json_encode($response);
    mysqli_close($conn);
?>

