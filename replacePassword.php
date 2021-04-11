
<?
    include('dbcon.php');
	
	$USER_ID = $_POST["USER_ID"];
	$USER_PASSWORD = $_POST["USER_PASSWORD"];
	
		//쿼리문
		$statement = mysqli_prepare($conn, "update USER set USER_PASSWORD = ? where USER_ID = ?");
		
		mysqli_stmt_bind_param($statement, "ss", $USER_PASSWORD, $USER_ID);
		mysqli_stmt_execute($statement);
		
		$response =array();
		$response["success"] = true;
		
		echo json_encode($response);
    mysqli_close($conn);
?>

