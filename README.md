<h1>🎬 MovieLists App</h1>
<p>
  MovieLists es una aplicación móvil que permite a los usuarios explorar, organizar y gestionar sus películas mediante la creación de listas personalizadas. A diferencia de otras aplicaciones, MovieLists se integra con <strong>TMDB</strong> para autenticación y gestión de películas, ofreciendo una experiencia única para llevar el control de tus películas vistas y no vistas.
</p>

<h2>📱 Capturas de Pantalla</h2>
<p align="center">
  <img src="./Screenshots/Screenshot_20241103_031827_Movie Lists.jpg" alt="Pantalla Principal" width="250" />
  <img src="./Screenshots/Screenshot_20241103_031839_Movie Lists.jpg" alt="Pantalla Principal" width="250" />
  <img src="./Screenshots/Screenshot_20241103_031904_Movie Lists.jpg" alt="Pantalla Principal" width="250" />
</p>

<h2>🧩 Funcionalidades</h2>
<ul>
  <li><strong>Explorar películas:</strong> Descubre películas populares, estrenos y más, consultando detalles como título, descripción y calificación mediante los endpoints de TMDB.</li>
  <li><strong>Gestión de listas personalizadas:</strong> Crea, edita y elimina listas para organizar tus películas, sincronizadas con TMDB para acceder a ellas desde cualquier dispositivo.</li>
  <li><strong>Control de películas vistas:</strong> Marca las películas que has visto y organiza tus listas para separar las películas vistas de las no vistas.</li>
  <li><strong>Autenticación TMDB:</strong> Inicia sesión mediante el sistema de autenticación de TMDB para personalizar tu experiencia y guardar tus listas.</li>
  <li><strong>Modo claro y oscuro:</strong> Disfruta de una interfaz adaptable tanto para condiciones de baja como de alta iluminación.</li>
</ul>

<h2>🌐 Endpoints de TMDB Utilizados</h2>
<p>La aplicación utiliza los siguientes endpoints de la API de TMDB para ofrecer funcionalidades avanzadas de gestión de películas:</p>
<ul>
  <li><strong>Autenticación:</strong>
    <ul>
      <li>Obtener token temporal: <code>/authentication/token/new</code></li>
      <li>Validar login: <code>/authentication/token/validate_with_login</code></li>
      <li>Crear sesión: <code>/authentication/session/new</code></li>
      <li>Eliminar sesión: <code>/authentication/session</code></li>
    </ul>
  </li>
  <li><strong>Gestión de Favoritos:</strong> 
    <ul>
      <li>Obtener películas favoritas (usadas como películas vistas): <code>/account/${accountID}/favorite/movies</code></li>
      <li>Agregar o remover películas de favoritos (vistas/no vistas): <code>/account/${accountID}/favorite</code></li>
    </ul>
  </li>
  <li><strong>Listas:</strong> 
    <ul>
      <li>Crear lista: <code>/list</code></li>
      <li>Obtener una lista: <code>/list/${listId}</code></li>
      <li>Obtener todas las listas de una cuenta: <code>/account/${accountId}/lists</code></li>
      <li>Agregar película a la lista: <code>/list/${listId}/add_item</code></li>
      <li>Eliminar película de la lista: <code>/list/${listId}/remove_item</code></li>
      <li>Eliminar lista: <code>/list/${listId}</code></li>
    </ul>
  </li>
  <li><strong>Películas:</strong>
    <ul>
      <li>Obtener detalles de una película: <code>/movie/$movieId</code></li>
      <li>Explorar listas de películas populares, estrenos, etc.: <code>/movie/${movieListType.endpoint}</code></li>
      <li>Buscar películas: <code>/search/movie</code></li>
    </ul>
  </li>
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
</ul>
<p>Para más detalles sobre las versiones y librerías específicas, consulta el archivo <a href="[./build.gradle](https://github.com/xvirs/MovieLists/blob/master/app/build.gradle.kts)">build.gradle</a>.</p>

<h2>🏗️ Arquitectura</h2>
<p>Esta aplicación sigue los principios de <strong>Clean Architecture</strong> y utiliza una arquitectura en capas que separa claramente las responsabilidades:</p>
<ul>
  <li><strong>Capa de Datos (Data):</strong> Maneja las fuentes de datos, como la base de datos local (Room) y el cliente de API (Ktor). También contiene los modelos y mappers para la transformación de datos.</li>
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
  <li><strong>Cuenta de TMDB</strong> para autenticación y acceso a la API.</li>
</ul>

<h3>Instalación</h3>
<ol>
  <li>Clona el repositorio: 
    <pre><code>git clone https://github.com/tu-usuario/MovieListsApp.git</code></pre>
  </li>
  <li>Configura la API de TMDB:
    <ul>
      <li>Regístrate en TMDB y genera una API Key.</li>
      <li>Agrega la API Key en el archivo de configuración apropiado en el proyecto.</li>
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
