<?php

/**
 * This is a pretty ugly hack way to get scala running
 * via a web interface. Just invoke as a shell script ...
 */

// sanitize input to make sure it's valid json
$query = escapeshellarg(json_encode(json_decode(urldecode($_SERVER['QUERY_STRING']))));

$cmd = '/Users/lshepard/Code/scala-2.7.6.final/bin/scala '
     .'-cp .. blokus.Web ' . $query . '';

$result = shell_exec($cmd);
echo $result;

error_log($cmd);
