<!DOCTYPE html>
<html>
<head>
  <#include "header.ftl">
</head>

<body>

  <#include "nav.ftl">

<div class="jumbotron text-center">
  <div class="container">
    <a href="/" class="lang-logo">
      <img src="/1-512.png">
    </a>
    <h1>${title}</h1>
    <p>Ursula GIS es para agricultores interesados en agricultura de precisi&oacute;n. Permite ver y limpiar mapas de rendimiento, producir mapas de m&aacute;rgenes econ&oacute;micos, recomendaciones de fertilizaci&oacute;n y mapas de balance de nutrientes en el suelo.</br>
     Adem&aacute;s es libre y gratuito!
     </p>
	<!--a type="button" class="btn btn-lg btn-default" href="./login"><span class="glyphicon glyphicon-flash"></span> Login</a-->
    <a type="button" class="btn btn-lg btn-default" href="http://bit.ly/2vKg0du"><span class="glyphicon glyphicon-download"></span> Descargar Aplicacion para Windows 64 bits </a>
    <a type="button" class="btn btn-lg btn-default" href="http://bit.ly/2xNFWpR"><span class="glyphicon glyphicon-download"></span> Descargar Aplicacion para Windows 32 bits </a>
    <a type="button" class="btn btn-lg btn-primary" href="http://github.com/kotogadekiru/UrsulaGIS"><span class="glyphicon glyphicon-flash"></span> Source on GitHub</a>

  </div>
</div>
<div class="container">
  <p>Ahora todos podemos ver y limpiar mapas de cosecha, descargar mapas de ndvi, generar mapas de potencial y recomendaciones de fertilizaci&oacute;n. Luego generar mapas de m&aacute;rgenes para auditar las decisiones econ&oacute;micas y mapas de balance de nutrientes para auditar las decisiones ambientales y mejorar las decisiones a futuro.
 </p>
 <p>Mientas los proveedores de herramientas para agricultura de precisi&oacute;n sigan pretendiendo cobrar 5 d&oacute;lares por hect&aacute;rea para empezar a ver de que se trata el tema, la agricultura de precisi&oacute;n nunca va a ser la pr&aacute;ctica generalizada que deber&iacute;a.
</p>
 <p>La agricultura de precisi&oacute;n o por ambientes no es volver al futuro. Es la misma agronom&iacute;a de siempre con herramientas que nos permitan tomar m&aacute;s decisiones por hect&aacute;rea. Es f&aacute;cil es r&aacute;pida, es conveniente y es aplicable ac&aacute; y ahora. Ya no tenemos razones para no seguir haci&eacute;ndolo.
</p>
</div>
<div class="container">
  <div class="alert alert-info text-center" role="alert">
    <h2>Manuales</h2>
  <a href="/docs/ModoDeUso_0.2.18.pdf"><span class="glyphicon glyphicon-flash"></span> Modo de uso</a>
   <a href="/docs/ObtenerNDVI.pdf"><span class="glyphicon glyphicon-flash"></span> Obtener NDVI</a>
    <a href="/docs/Instructivo_0.2.18.pdf"><span class="glyphicon glyphicon-flash"></span> Instructivo</a>
  </div>
  <div class="alert alert-info text-center" role="alert">
    <h2>Tutoriales</h2>
  <iframe width="560" height="315" src="https://www.youtube.com/embed/FTjohUDQ4EE?list=PLvNJi6IFrYVK5Ur5LjBa7T5S8MQ0Peo2_" frameborder="0" allowfullscreen></iframe>
  <iframe width="560" height="315" src="https://www.youtube.com/embed/CV6VzV5GHF0?list=PLvNJi6IFrYVK5Ur5LjBa7T5S8MQ0Peo2_" frameborder="0" allowfullscreen></iframe>
  <iframe width="560" height="315" src="https://www.youtube.com/embed/El13Ey7q5dM?list=PLvNJi6IFrYVK5Ur5LjBa7T5S8MQ0Peo2_" frameborder="0" allowfullscreen></iframe>
  
   </div>
  <hr>
  <div class="row">
    <div class="col-md-6">
      <h3><span class="glyphicon glyphicon-info-sign"></span> Noticias </h3>
      <ul>
      	<li>
       	29 Agosto 2017: Se liber&oacute; la versi&oacute;n 0.2.22 para Windows de 64 bits y 32 bits que permite generar prescripciones de fertilizacion nitrogenada y descargar imagenes de NDVI clasificadas segun nube, cultivo o agua en superficie. El servicio de publicaci&oacute;n de imagenes NDVI sigue con demoras en origen pero se han podido descargar imagenes del 15 de agosto 2017.
        <li>
        <li>
       	28 Julio 2017: De acuedo con la informaci&oacute;n de la agencia espacial europea se informa que las imagenes de NDVI solo estan disponibles hasta el 13 de julio 2017 (por temas de mantenimiento) y desde 23 Junio 2015. Mas informaci&oacute;n en   <a href="https://explorer.earthengine.google.com/#detail/COPERNICUS%2FS2">Earth Engine </a> y <a href="https://scihub.copernicus.eu/news/"> SciHub </a>   
        <li>
       	5 Junio 2017: Se liber&oacute; la versi&oacute;n 0.2.20 que permite guardar los poligonos marcados, los coeficientes de requerimiento de los diferentes cultivos, crear nuevos fertilizantes, semillas y cultivos. Tambien permite generar, importar y exportar mapas de suelo que se usaran para los balances de nutrientes y para las recomendaciones de N.
        </li>
        <li>
       	8 Mayo 2017: Se liber&oacute; la versi&oacute;n 0.2.19 que permite exportar las recomendaciones de fertilizacion generadas, y demas labores. Tambien permite ver el histograma de los mapas de NDVI y convertir un mapa ndvi a una cosecha estimada. 
       	Tambien se creo una funcionalidad para advertir al usuario que hay una version nueva disponible y permite actualizar la aplicacion automaticamente.
        </li>
       <li>
       	22 Abril 2017: Se liber&oacute; la versi&oacute;n 0.2.18 que resuelve todos los bugs conocidos de las versiones anteriores y ademas implementa muchas funcionalidades nuevas.
      	Algunas son: Generaci&oacute;n de capas de cosecha, siembra, fertilizaci&oacute;n y pulverizaci&oacute;n a partir de poligonos, tambien generar margenes economicos, balance de fosforo y recomendaci&oacute;n de fertilizaci&oacute;n fosforada.
        </li>
        <li>
        17 Abril 2017: En la versi&oacute;n 0.2.17.2 se descubrio que no se puede descargar los mapas de ndvi 
        de los meses de enero debido a un error de calculo de la fecha correspondiente al mes anterior. 
        Calcula diciembre del mismo a&ntilde;o en vez de diciembre del a&ntilde;o anterior. 
        Corregido en la versi&oacute;n 0.2.18
        </li>
        <li>
        23 Marzo 2017: En la versi&oacute;n 0.2.17.2 se descubrio que no se puede descargar los mapas de ndvi 
        debido a que la aplicaci&oacute;n no tiene permiso de escritura en el directorio "Archivos de programas" o 
        "Program Files" para resolver esto momentaneamente se puede mover la aplicaci&oacute;n de "Archivos de Programas" a 
        "Program Data" o cualquier otro directorio que no este protegido por windows.
        </li>
   
      </ul>
    </div>
    <div class="col-md-6">
      <h3><span class="glyphicon glyphicon-link"></span> Proximos Desarrollos</h3>
      <ul>
        <li>Importar Poligonos</li>
        <li>Guardar las labores cargadas en una base de datos</li>
        <li>Mantener las labores cargadas entre sesiones</li>
        <li>Generar Puntos de muestreo dirigido</li>
        <li>Interpolar muestreo de suelos para obtener mapa de suelos</li>
        <li>Importar mapas de cosecha con formato JohnDeere GS2</li>        
      </ul>


    </div>
  </div> <!-- row -->
   <div class="alert alert-info text-center" role="alert">
   Gracias por usar UrsulaGIS cualquier consulta enviar mail a <a href="mailto:kotogadekiru@gmail.com">kotogadekiru@gmail.com</a>
  </div>
</div>
</body>
</html>
