
<?
     $conn = new mysqli('smartdoor.con8izhblf16.ap-northeast-2.rds.amazonaws.com', 'admin','12341234','smartdoor');
    
    include('dbcon.php');
	
		//쿼리문
		$sql = 'select * from USER';
		
		//쿼리 실행 결과
		$res = mysqli_query($conn,$sql);
		//배열의 변수 선언
		$result = array(); 
		//쿼리문 결과를 배열 형시긍로 변환
		while($row = mysqli_fetch_array($res)) { 
			array_push($result, array($row[0],$row[1],$row[2],$row[3],$row[4])); 
		}
		
		//배열형싱의 결과를 json으로 변환
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
		
    mysqli_close($conn);
?>

