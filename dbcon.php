
<?
     $conn = new mysqli('smartdoor.con8izhblf16.ap-northeast-2.rds.amazonaws.com', 'admin','12341234','smartdoor');
    
    if ($conn->connect_error) {
        die('conn Connect Error!');
    } //$conn 연결객체가 mysql에 잘 연결되었는지 확인(안되면 오류출력)
	mysqli_query($conn,'SET NAMES utf8');
?>

