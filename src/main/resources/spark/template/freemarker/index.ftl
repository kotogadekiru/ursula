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
    <h1>Ursula GIS</h1>
    <p>Ursula GIS es para agricultores interesados en agricultura de precisi&oacute;n. Permite ver, limpiar mapas de rendimiento y producir mapas de margenes economicos, recomendaciones de fertilizacion y mapas de balance de nutrientes en el suelo. Adem&aacute;s es libre y gratuito!</p>
	<a type="button" class="btn btn-lg btn-default" href="./login"><span class="glyphicon glyphicon-flash"></span> Login</a>
    <a type="button" class="btn btn-lg btn-default" href="http://bit.ly/2pQKUiy"><span class="glyphicon glyphicon-flash"></span> Download Desktop App </a>
    <a type="button" class="btn btn-lg btn-primary" href="http://github.com/kotogadekiru/UrsulaGIS"><span class="glyphicon glyphicon-download"></span> Source on GitHub</a>
  </div>
</div>
<div class="container">
  <div class="alert alert-info text-center" role="alert">
  <a href="/docs/ModoDeUso_0.2.18.pdf"><span class="glyphicon glyphicon-flash"></span> Modo de uso</a>
   <a href="/docs/ObtenerNDVI.pdf"><span class="glyphicon glyphicon-flash"></span> Obtener NDVI</a>
    <a href="/docs/Instructivo_0.2.18.pdf"><span class="glyphicon glyphicon-flash"></span> Modo de uso</a>
  </div>
  <hr>
  <div class="row">
    <div class="col-md-6">
      <h3><span class="glyphicon glyphicon-info-sign"></span> Noticias </h3>
      <ul>
       <li>
       	22 Abril 2017: Se libero la versi&oacute;n 0.2.18 que resuelve todos los bugs conocidos de las versiones anteriores y ademas implementa muchas funcionalidades nuevas.
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
        <li>Estimar rendimientos desde imagenes NDVI</li>
        <li>Obtener el histograma de imagenes NDVI</li>
        <li>Guardar e importar Poligonos</li>
        <li>Guardar las labores cargadas en una base de datos</li>
        <!--ul>
          <li><code>git clone https://github.com/heroku/java-getting-started.git</code> - this will create a local copy of the source code for the app</li>
          <li><code>cd java-getting-started</code> - change directory into the local source code repository</li>
          <li><code>heroku git:remote -a &lt;your-app-name></code> - associate the Heroku app with the repository</li>
          <li>You'll now be set up to run the app locally, or <a href="https://devcenter.heroku.com/articles/getting-started-with-java#push-local-changes">deploy changes</a> to Heroku</li>
        </ul-->
      </ul>


    </div>
  </div> <!-- row -->
   <div class="alert alert-info text-center" role="alert">
   Gracias por usar UrsulaGIS cualquier consulta enviar mail a <a href="mailto:kotogadekiru@gmail.com">kotogadekiru@gmail.com</a>
  </div>
</div>
</body>
</html>
