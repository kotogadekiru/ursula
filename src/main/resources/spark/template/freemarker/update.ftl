<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>

  <#include "nav.ftl">

<div class="container">
 <ul>
       
    <li>${mensaje}</li>
   <li>lastversion=${lasVersionNumber}</li>
   <li><a  href=${lasVersionURL}> download <a/></li>
</ul>


</div>

</body>
</html>
