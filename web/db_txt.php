<?php
ob_start();
if(!file_exists('baza.txt'))
{
	$keys=array_keys($_REQUEST);
	array_unshift($keys, 'request_time');
	print join($keys,"\t")."\n";
}
print date('Y-m-d H:i:s')."\t".join($_REQUEST,"\t")."\n";
$str=ob_get_clean();
//print_r($_GET);
file_put_contents('baza.txt',$str,FILE_APPEND);
print_r(array_keys($_REQUEST));
//print "<pre>".file_get_contents('baza.txt')."</pre>";
print 'ok';
?><?php
die();
//"q="+$('.question')[0].innerHTML+"&a1="+$("#questions div label")[0].innerHTML+"&a2="+$("#questions div label")[1].innerHTML+"&a3="+$("#questions div label")[2].innerHTML+"&a4="+$("#questions div label")[3].innerHTML+"&c="+$("#questions div label").get($("#questions div input").index($("#questions div input:checked")[0])).innerHTML
print "<html><head><meta charset=\"utf-8\" /></head><body>";
//print "<a href='xkom.user.js'>zainstaluj skrypt i pomoz arturowi grac w gre!</a>";
$con = mysql_connect("localhost","beltek92_arzo","arzoxadi");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
mysql_set_charset("utf8");
mysql_select_db("beltek92_arzo", $con);

if(isset($_GET['q']))
{
	//start_time, cashorcard, shoppingsize, queuelen, taken_time, cash_no
$s=sprintf("INSERT INTO questionsxkom (question,a1,a2,a3,a4,correct)
VALUES ('%s','%s','%s','%s','%s','%s')
ON DUPLICATE KEY UPDATE correct='%s'"
,urldecode($_GET['q'])
,urldecode($_GET['a1'])
,urldecode($_GET['a2'])
,urldecode($_GET['a3'])
,urldecode($_GET['a4'])
,urldecode($_GET['c'])
,urldecode($_GET['c'])
);
print $s;
mysql_query($s);

$lastinsertid=mysql_insert_id();
/*
for ($i=0; $i<4; $i++) 
{
$a=$_GET['a'.$i];
$correct=($a==$_GET['c']);
mysql_query("INSERT INTO xkomanwsers (questionid, anwser, correct) VALUES (".$lastinsertid.",'$a', ".$correct.")");
}*/

}

$result = mysql_query("SELECT question, a1, a2, a3, a4, correct FROM questionsxkom");
print "<table border=1><tr><td>pytanie</td><td>odp1</td><td>odp2</td><td>odp3</td><td>odp4</td><td>poprawnaodp</td></tr>";
while ($row = mysql_fetch_array($result, MYSQL_BOTH)) {
    printf ("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", $row[0], $row[1], $row[2], $row[3], $row[4], $row[5]);  
}
print "</table>";
mysql_free_result($result);

mysql_close($con);
?>