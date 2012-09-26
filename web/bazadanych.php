<?php
ob_start();
//print_r($_SERVER);
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
//die();
//"q="+$('.question')[0].innerHTML+"&a1="+$("#questions div label")[0].innerHTML+"&a2="+$("#questions div label")[1].innerHTML+"&a3="+$("#questions div label")[2].innerHTML+"&a4="+$("#questions div label")[3].innerHTML+"&c="+$("#questions div label").get($("#questions div input").index($("#questions div input:checked")[0])).innerHTML
print "<html><head><meta charset=\"utf-8\" /></head><body>";
//print "<a href='xkom.user.js'>zainstaluj skrypt i pomoz arturowi grac w gre!</a>";
$con = mysql_connect("localhost","beltek92_arzo",file_get_contents('dbpass.txt'));
if (!$con)
  {
  die('Could not connect: ' . mysql_error(). "\nmake sure password's in ./dbpass.txt file");
  }
mysql_set_charset("utf8");
mysql_select_db("beltek92_arzo", $con);

$columns=array("add_time","start_time", "cashorcard", "queuelen", "shoppingsize", "taken_time", "cash_no", "user_login");
$cols=join($columns,',');
if(count($_REQUEST)>0)
	{
		//http://arzoxadi.tk/niduc/sql.php?add_time=31mar&start_time=30mar&cash_no=1&card=1&shoppingSize=2&queuelen=3&taken_time=154&user_login=htcdeside
		$vals=array();
		foreach($columns as $column)
			{
				if($column==$columns[0])
					array_push($vals, "NOW()");
				else
					array_push($vals, "\"".urldecode($_REQUEST[$column])."\"");
			}

		$s=sprintf("INSERT INTO niducstorage (%s)
		VALUES (%s)"
		,$cols
		,join($vals,','));

		//print $s;
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
$request="SELECT ".$cols." FROM niducstorage ORDER BY user_login,start_time ASC";
//print $request."<br/>";
$result = mysql_query($request);
print "<table border=1><tr><td>".join($columns,"</td><td>")."</td></tr>";
while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
	//print_r($row);
    print ("<tr><td>".join($row,"</td><td>")."</td></tr>\n");
}
print "</table>";
mysql_free_result($result);

mysql_close($con);
?>