<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
 <c:if test="${not empty channels}">
     <ul>
         <c:forEach var="val" items="${channels}">
         </c:forEach>
     </ul>
 </c:if>
<h4>Message : ${message}</h1>	
</body>
</html>