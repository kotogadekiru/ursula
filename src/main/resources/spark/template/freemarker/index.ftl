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
    <p>This is an aplication to clean an review harvest maps asociated to the Ursula ERP.</p>
	<a type="button" class="btn btn-lg btn-default" href="./login"><span class="glyphicon glyphicon-flash"></span> Login</a>
    <a type="button" class="btn btn-lg btn-default" href="http://bit.ly/2n7HnNr"><span class="glyphicon glyphicon-flash"></span> Download Desktop App </a>
    <a type="button" class="btn btn-lg btn-primary" href="https://github.com/kotogadekiru/ursula"><span class="glyphicon glyphicon-download"></span> Source on GitHub</a>
  </div>
</div>
<div class="container">
  <div class="alert alert-info text-center" role="alert">
   Esta pagina es un trabajo en proceso y se ira actualizando a medida que crezca la aplicacion
  </div>
  <hr>
  <div class="row">
    <div class="col-md-6">
      <h3><span class="glyphicon glyphicon-info-sign"></span> Noticias </h3>
      <ul>
        <li>23 Marzo 2017: En la version 0.2.17.2 se descubrio que no se puede descargar los mapas de ndvi debido a que la aplicacion no tiene permiso de escritura en el directorio "Archivos de programas" o "Program Files" para resolver esto momentaneamente se puede mover la aplicacion de "Archivos de Programas" a "Program Data" o cualquier otro directorio que no este protegido por windows.</li>
   
      </ul>
    </div>
    <div class="col-md-6">
      <h3><span class="glyphicon glyphicon-link"></span> Proximos Desarrollos</h3>
      <ul>
        <li>Estimar rendimientos desde imagenes NDVI</li>
        <li>Guardar e importar Polygonos</li>
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
