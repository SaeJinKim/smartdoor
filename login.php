
<?
    include('dbcon.php');
	
	$USER_ID = $_POST["USER_ID"];
	$USER_PASSWORD = $_POST["USER_PASSWORD"];
	
		//쿼리문
		$statement = mysqli_prepare($conn, "select USER_ID from USER where USER_ID = ? and USER_PASSWORD = ?");
		
		mysqli_stmt_bind_param($statement, "ss", $USER_ID, $USER_PASSWORD);
		mysqli_stmt_execute($statement);
		
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement,$USER_ID);
		
		
		$response = array();
		$response["success"] = false;
		
		while(mysqli_stmt_fetch($statement)){
			$response["success"] = true;
			$response["USER_ID"] = $USER_ID;
		}
		
		echo json_encode($response);
    mysqli_close($conn);
?>

