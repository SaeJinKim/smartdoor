
<?
    include('dbcon.php');
		$ID;
		$TIME;
		//쿼리문
	
		$res = mysqli_query($conn,"select * from LOG");
		
		$response =array();
		
		
		while($row = mysqli_fetch_array($res)){
			array_push($response, array('ID'=>$row[0],'TIME'=>$row[1])); 
		}
		// 배열형식의 결과를 json으로 변환 
		echo json_encode(array("response"=>$response),JSON_UNESCAPED_UNICODE);
		
		
    mysqli_close($conn);
?>

