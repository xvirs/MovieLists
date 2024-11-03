<h1>🎬 MovieLists App</h1>

<p>MovieLists es una aplicación móvil que permite a los usuarios explorar, organizar y gestionar sus películas favoritas mediante la creación de listas personalizadas. La aplicación también ofrece autenticación con Firebase y características adicionales como el guardado de preferencias mediante DataStore.</p>

<h2>📱 Capturas de Pantalla</h2>

<!-- Agrega aquí las URLs de las imágenes -->
<p align="center">
  ![Pantalla Principal](./screenshots/Screenshot_20241103_031827_Movie Lists.jpg)
  ![Pantalla Principal](./screenshots/Screenshot_20241103_031839_Movie Lists.jpg)
  ![Pantalla Principal](./screenshots/Screenshot_20241103_031904_Movie Lists.jpg)
</p>

<h2>🧩 Funcionalidades</h2>
<ul>
  <li><strong>Explorar películas:</strong> Descubre nuevas películas y consulta detalles como título, descripción y calificación.</li>
  <li><strong>Gestión de listas:</strong> Crea, edita y elimina listas personalizadas para organizar tus películas favoritas.</li>
  <li><strong>Marcar como vista o favorita:</strong> Guarda tus películas favoritas o marca las que ya has visto.</li>
  <li><strong>Autenticación Firebase:</strong> Inicia sesión para personalizar tu experiencia y guardar tus listas.</li>
  <li><strong>Compatibilidad con modo claro y oscuro:</strong> Disfruta de una interfaz adaptable para distintas condiciones de iluminación.</li>
</ul>

<h2>🛠️ Tecnologías Utilizadas</h2>
<h3>Lenguaje y Frameworks</h3>
<ul>
  <li><strong>Kotlin:</strong> Lenguaje principal de desarrollo.</li>
  <li><strong>Jetpack Compose:</strong> Framework de UI declarativa para construir interfaces modernas.</li>
</ul>

<h3>Dependencias</h3>
<ul>
  <li><strong>Jetpack Libraries:</strong> Core KTX, Lifecycle, Navigation, Room, DataStore, y Material.</li>
  <li><strong>Koin:</strong> Inyección de dependencias.</li>
  <li><strong>Ktor:</strong> Cliente HTTP para solicitudes de API.</li>
  <li><strong>Coil:</strong> Librería de carga de imágenes para Compose.</li>
  <li><strong>Firebase:</strong> Autenticación y análisis de uso de la app.</li>
</ul>

<p>Para más detalles sobre las versiones y librerías específicas, consulta el archivo <a href="./build.gradle">build.gradle</a>.</p>

<h2>🏗️ Arquitectura</h2>
<p>Esta aplicación sigue los principios de <strong>Clean Architecture</strong> y utiliza una arquitectura en capas que separa claramente las responsabilidades:</p>

<ul>
  <li><strong>Capa de Datos (Data):</strong> Se encarga de manejar las fuentes de datos, incluyendo la base de datos local (Room) y el cliente de API (Ktor). También contiene los modelos y mappers para la transformación de datos.</li>
  <li><strong>Capa de Dominio (Domain):</strong> Contiene los casos de uso que representan las reglas de negocio de la aplicación, proporcionando una interfaz para la capa de presentación.</li>
  <li><strong>Capa de Presentación (Presentation):</strong> Gestiona la lógica de UI utilizando <code>ViewModels</code>, <code>UIState</code>, y componibles de Jetpack Compose.</li>
</ul>

<h3>Patrón de Manejo de Estados</h3>
<p>Utiliza una clase <code>sealed class UIState</code> para representar diferentes estados de la interfaz (cargando, éxito, error), lo que simplifica la gestión de estados en los <code>ViewModels</code>.</p>

<h3>Navegación</h3>
<p>La navegación se gestiona mediante <strong>Navigation Compose</strong>, con un stack principal que permite moverse entre pantallas de detalles de películas, listas y la pantalla principal.</p>

<h2>🔧 Configuración de Entorno</h2>

<h3>Requisitos</h3>
<ul>
  <li><strong>Android Studio Giraffe | 2022.3.1 o superior</strong></li>
  <li><strong>JDK 11 o superior</strong></li>
  <li><strong>Firebase Console</strong> para configurar autenticación y servicios adicionales.</li>
</ul>

<h3>Instalación</h3>
<ol>
  <li>Clona el repositorio:
    <pre><code>git clone https://github.com/tu-usuario/MovieListsApp.git</code></pre>
  </li>
  <li>Configura Firebase:
    <ul>
      <li>Crea un proyecto en Firebase Console.</li>
      <li>Descarga el archivo <code>google-services.json</code> y colócalo en la carpeta <code>app</code> del proyecto.</li>
    </ul>
  </li>
  <li>Sincroniza y compila el proyecto en Android Studio.</li>
</ol>

<h2>🚀 Instalación y Uso</h2>
<p>La aplicación se puede instalar directamente desde el archivo <code>.apk</code> incluido en el repositorio, o puedes compilarla manualmente desde Android Studio siguiendo los pasos de la sección de configuración.</p>

<h2>🤝 Contribuciones</h2>
<p>Las contribuciones son bienvenidas. Si deseas colaborar, por favor abre un "issue" o envía un "pull request" con tus propuestas.</p>

<h2>📄 Licencia</h2>
<p>Este proyecto está bajo la licencia MIT. Consulta el archivo <a href="./LICENSE">LICENSE</a> para más detalles.</p>
