
<?
    include('dbcon.php');
	
	$USER_ID = $_POST["USER_ID"];

	
		//쿼리문
		$statement = mysqli_prepare($conn, "update USER set SIGN = '1'  where USER_ID = ?");
		
		mysqli_stmt_bind_param($statement, "s", $USER_ID);
		mysqli_stmt_execute($statement);
		
		
		
		$response = array();
		$response["success"] = true;
		
		echo json_encode($response);
    mysqli_close($conn);
?>

