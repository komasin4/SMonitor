<!DOCTYPE html>

<html lang="ko">

<body>
	<table border=1>
		<tr><td>코드</td><td>종목명</td><td>현재가</td><td>변동율</td></tr>
		<#list indexes as index>
	  		<tr><td>${index.cd}</td><td>${index.nm?default(index.cd)}</td><td>${index.nv/100}</td><td>${index.cr}</td></tr>
		</#list>
	</table>
	
	<br>
	
	<table border=1>
		<tr><td>코드</td><td>종목명</td><td>현재가</td><td>변동율</td></tr>
		<#list items as item>
	  		<tr><td>${item.cd}</td><td>${item.nm}</td><td>${item.nv}</td><td>${item.cr}</td></tr>
		</#list>
	</table>
	
</body>

</html>