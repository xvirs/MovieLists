<h1>🎬 MovieLists App</h1>
<p>
  MovieLists es una aplicación móvil que permite a los usuarios explorar, organizar y gestionar sus películas mediante listas personalizadas. La app se integra con <strong>TMDB</strong> para autenticación y gestión de películas, facilitando el control de tus películas vistas y no vistas.
</p>

<h2>📱 Capturas de Pantalla</h2>
<p align="center">
  <img src="./Screenshots/Screenshot_20241103_031827_Movie Lists.jpg" alt="Pantalla Principal" width="250" />
  <img src="./Screenshots/Screenshot_20241103_031839_Movie Lists.jpg" alt="Pantalla Principal" width="250" />
  <img src="./Screenshots/Screenshot_20241103_031904_Movie Lists.jpg" alt="Pantalla Principal" width="250" />
</p>

<h2>🧩 Funcionalidades</h2>
<ul>
  <li><strong>Explorar películas:</strong> Descubre películas populares, estrenos y más, consultando detalles como título, descripción y calificación mediante la API de TMDB.</li>
  <li><strong>Gestión de listas:</strong> Crea, edita y elimina listas para organizar tus películas, sincronizadas con TMDB.</li>
  <li><strong>Control de películas vistas:</strong> Marca películas como vistas y organiza tus listas.</li>
  <li><strong>Autenticación TMDB:</strong> Inicia sesión mediante TMDB para personalizar tu experiencia y guardar tus listas.</li>
  <li><strong>Modo claro y oscuro:</strong> Disfruta de una interfaz adaptable a diferentes condiciones de luz.</li>
</ul>

<h2>🌐 Endpoints de TMDB Utilizados</h2>
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
      <li>Obtener películas favoritas (vistas): <code>/account/${accountID}/favorite/movies</code></li>
      <li>Agregar o remover favoritos (vistas/no vistas): <code>/account/${accountID}/favorite</code></li>
    </ul>
  </li>
  <li><strong>Listas:</strong> 
    <ul>
      <li>Crear lista: <code>/list</code></li>
      <li>Obtener listas de una cuenta: <code>/account/${accountId}/lists</code></li>
      <li>Agregar/Eliminar película en lista: <code>/list/${listId}/add_item</code>, <code>/list/${listId}/remove_item</code></li>
      <li>Eliminar lista: <code>/list/${listId}</code></li>
    </ul>
  </li>
  <li><strong>Películas:</strong>
    <ul>
      <li>Obtener detalles de una película: <code>/movie/$movieId</code></li>
      <li>Explorar listas de películas populares: <code>/movie/${movieListType.endpoint}</code></li>
      <li>Buscar películas: <code>/search/movie</code></li>
    </ul>
  </li>
</ul>

<h2>🛠️ Tecnologías Utilizadas</h2>
<ul>
  <li><strong>Kotlin y Jetpack Compose:</strong> Lenguaje principal y framework de UI declarativa.</li>
  <li><strong>Ktor:</strong> Cliente HTTP para la API de TMDB.</li>
  <li><strong>Coroutines y Flows:</strong> Para concurrencia y flujo de datos.</li>
  <li><strong>Koin:</strong> Inyección de dependencias.</li>
  <li><strong>Material 3:</strong> UI basada en Material Design 3.</li>
  <li><strong>DataStore:</strong> Almacenamiento de preferencias del usuario.</li>
  <li><strong>Coil:</strong> Librería de carga de imágenes para Compose.</li>
  <li><strong>Firebase:</strong> Analytics y Crashlytics para análisis de uso y errores.</li>
</ul>

<h3>Dependencias</h3>
<ul>
  <li><strong>Jetpack Libraries:</strong> Core KTX, Lifecycle, Navigation, Room, DataStore, y Material.</li>
  <li><strong>Ktor, Koin y Coil:</strong> Para solicitudes HTTP, inyección de dependencias y carga de imágenes.</li>
</ul>
<p>Para más detalles sobre versiones, consulta el archivo <a href="https://github.com/xvirs/MovieLists/blob/master/app/build.gradle.kts">build.gradle</a>.</p>

<h1>📐 Arquitectura de MovieLists App</h1>
<p>
  La app sigue los principios de <strong>Clean Architecture</strong>, con tres capas principales: <strong>Data</strong>, <strong>Domain</strong> y <strong>Presentation</strong>, asegurando modularidad y fácil mantenimiento.
</p>

<h2>🔍 Estructura de Carpetas</h2>
<ul>
  <li><strong>/data:</strong> Gestiona fuentes de datos (API y almacenamiento local) e incluye mapeadores y repositorios.</li>
  <li><strong>/domain:</strong> Contiene la lógica de negocio, casos de uso y modelos.</li>
  <li><strong>/presentation:</strong> Responsable de la interfaz de usuario usando Jetpack Compose. Incluye componentes de UI reutilizables, navegación y modelos de UI.</li>
  <li><strong>/di:</strong> Configuración de Koin para inyección de dependencias.</li>
  <li><strong>/utils:</strong> Funciones y clases auxiliares.</li>
</ul>

<p>La estructura modular permite independencia entre capas y facilita la prueba y el mantenimiento del código.</p>
