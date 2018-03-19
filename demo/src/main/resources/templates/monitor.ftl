<!DOCTYPE html>

<html lang="ko">

	<head>
	
		<style>
			@import url(//fonts.googleapis.com/earlyaccess/nanumgothic.css);
		</style>
	
	</head>

	<body>
		
		<font size="2" face="Nanum Gothic" >
		
		<#assign oldGname = "">
	
		${getTime}
	
		<table border=1">
		  	<tr align="center">
			 	<td>종목코드</td>
			 	<td>종목명</td>
			 	<td>현재가</td>
			 	<td>전일종가</td>
			 	<td>상승율</td>
			 	<td bgcolor="lavender">정찰</td>
			 	<td bgcolor="orchid">선발</td>
			 	<td bgcolor="magenta">본대1</td>
			 	<td bgcolor="blueViolet">본대2</td>
			 	<td bgcolor="purple">후발</td>
		  	</tr>
	
			<#list displayList as display>
			
			<#if oldGname != display.group_name>
			<tr><td colspan=10 align=center bgcolor="gainsboro">${display.group_name}</td></tr>
			</#if>
			
		  	<tr>
			 	<td bgcolor="${display.color}">${display.code}</td>
			 	<td bgcolor="${display.color}">${display.name}</td>
			 	<td align="right" bgcolor="${display.color}">${display.current_price}</td>
			 	<td align="right">${display.base_price}</td>
			 	<td align="right">${display.ratio}</td>
			 	
			 	<#if display.oncolor == 1><td align="right" bgcolor=${display.color}><#else><td align="right"></#if>${display.level1}</td>
			 	<#if display.oncolor == 2><td align="right" bgcolor=${display.color}><#else><td align="right"></#if>${display.level2}</td>
			 	<#if display.oncolor == 3><td align="right" bgcolor=${display.color}><#else><td align="right"></#if>${display.level3}</td>
			 	<#if display.oncolor == 4><td align="right" bgcolor=${display.color}><#else><td align="right"></#if>${display.level4}</td>
			 	<#if display.oncolor == 5><td align="right" bgcolor=${display.color}><#else><td align="right"></#if>${display.level5}</td>
		  	</tr>
		  	
		  	
	
			<#assign oldGname = display.group_name>
	
			</#list>
		


		</table>
	</body>
</html>