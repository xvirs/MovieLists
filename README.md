<h1>🎬 MovieLists App</h1>
<p>
  MovieLists es una aplicación móvil que permite a los usuarios explorar, organizar y gestionar sus películas mediante listas personalizadas. La app se integra con <strong>TMDB</strong> para autenticación y gestión de películas, facilitando el control de tus películas vistas y no vistas.
</p>

<h2>📱 Capturas de Pantalla</h2>

<h3>Inicio de Sesión</h3>
<p>
  La aplicacion totalmente responsiva, adaptándose al modo claro, oscuro y al color de sistema elegido por el usuario en su dispositivo.
</p>
<p align="center">
  <img src="./Screenshots/Screenshot_20241103_031827_Movie Lists.jpg" alt="Login Modo Claro" width="250" />
  <img src="./Screenshots/Screenshot_20241103_031839_Movie Lists.jpg" alt="Login Modo Oscuro" width="250" />
  <img src="./Screenshots/Screenshot_20241103_031904_Movie Lists.jpg" alt="Login Color de Sistema" width="250" />
</p>

<h3>Interfaz de Usuario en la App</h3>
<p>
  A continuación se muestran capturas de la app en funcionamiento, destacando la pantalla de exploración de películas, la vista de gestión de listas y otras, diseñadas con <strong>Jetpack Compose</strong> para una experiencia visual fluida y moderna.
</p>
<p align="center">
  <img src="./Screenshots/Screenshot_20241103_031932_Movie%20Lists.jpg" alt="Exploración de Películas" width="250" />
  <img src="./Screenshots/Screenshot_20241103_032005_Movie%20Lists.jpg" alt="Gestión de Listas" width="250" />
  <img src="./Screenshots/Screenshot_20241103_032036_Movie%20Lists.jpg" alt="Perfil de Usuario" width="250" />
  <img src="./Screenshots/Screenshot_20241103_032121_Movie%20Lists.jpg" alt="Perfil de Usuario" width="250" />
  <img src="./Screenshots/Screenshot_20241103_032235_Movie%20Lists.jpg" alt="Perfil de Usuario" width="250" />
  
</p>

<h2>🧩 Funcionalidades</h2>
<ul>
  <li><strong>Explorar películas:</strong> Descubre películas populares, estrenos y más, consultando detalles como título, descripción y calificación mediante la API de TMDB.</li>
  <li><strong>Gestión de listas:</strong> Crea, edita y elimina listas para organizar tus películas, sincronizadas con TMDB.</li>
  <li><strong>Control de películas vistas:</strong> Marca películas como vistas y organiza tus listas.</li>
  <li><strong>Autenticación TMDB:</strong> Inicia sesión mediante TMDB para personalizar tu experiencia y guardar tus listas.</li>
  <
li><strong>Modo claro y oscuro:</strong> Disfruta de una interfaz adaptable a diferentes condiciones de luz.</li>
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
<ul>
  <li><strong>Ktor:</strong> Cliente HTTP para interactuar con la API de TMDB.</li>
  <li><strong>Coroutines y Flows:</strong> Para manejar la concurrencia y los flujos de datos de manera eficiente.</li>
  <li><strong>Koin:</strong> Framework para la inyección de dependencias.</li>
  <li><strong>Jetpack Compose:</strong> Framework de UI declarativa para Android.</li>
  <li><strong>Material 3:</strong> Diseño de UI utilizando los componentes y directrices de Material Design 3.</li>
  <li><strong>DataStore:</strong> Almacenamiento de preferencias del usuario en lugar de SharedPreferences.</li>
  <li><strong>Coil:</strong> Librería de carga de imágenes optimizada para Compose.</li>
  <li><strong>Firebase:</strong> Se utiliza para <strong>Analytics</strong> y <strong>Crashlytics</strong> para análisis de uso y reporte de errores en la app.</li>
</ul>

<h3>Dependencias</h3>
<ul>
  <li><strong>Jetpack Libraries:</strong> Core KTX, Lifecycle, Navigation, Room, DataStore, y Material.</li>
  <li><strong>Koin:</strong> Inyección de dependencias.</li>
  <li><strong>Ktor:</strong> Cliente HTTP para solicitudes de API.</li>
  <li><strong>Coil:</strong> Librería de carga de imágenes para Compose.</li>
</ul>
<p>Para más detalles sobre las versiones y librerías específicas, consulta el archivo <a href="https://github.com/xvirs/MovieLists/blob/master/app/build.gradle.kts">build.gradle</a>.</p>

<h1>📐 Arquitectura de MovieLists App</h1>
<p>
  La arquitectura de MovieLists App está diseñada siguiendo los principios de <strong>Clean Architecture</strong>, 
  con una estructura de tres capas principales: <strong>Data</strong>, <strong>Domain</strong> y <strong>Presentation</strong>. 
  Esta estructura modular permite mantener una separación clara de responsabilidades y facilita el mantenimiento, escalabilidad y pruebas del código. 
  A continuación, se describe la estructura de carpetas detallada y el propósito de cada una.
</p>

<h2>🔍 Estructura de Carpetas</h2>
<p>La arquitectura se organiza en las siguientes carpetas principales:</p>

<h3>/Arquitectura</h3>

<ul>
  <li><strong>/data:</strong> Capa de datos responsable de gestionar las fuentes de datos de la aplicación, ya sea a través de APIs externas o de almacenamiento local.</li>
  <ul>
    <li><strong>/datasource:</strong> Incluye las fuentes de datos, como los servicios de red o las consultas a la base de datos local.</li>
    <li><strong>/interfaces:</strong> Define las interfaces de los repositorios, permitiendo que la capa de dominio interactúe con la capa de datos a través de abstracciones.</li>
    <li><strong>/models:</strong> Contiene los modelos de datos utilizados exclusivamente dentro de esta capa, junto con un mapeador (<strong>mapper</strong>) para transformar estos modelos a otros modelos de la app.</li>
    <li><strong>/network:</strong> Contiene la lógica de red, gestionada a través de <strong>Ktor</strong>, incluyendo las configuraciones y llamadas HTTP.</li>
    <li><strong>/repository:</strong> Implementación de los repositorios que se comunican con las fuentes de datos (remotas y locales) y proporcionan datos procesados a la capa de dominio.</li>
  </ul>

  <li><strong>/domain:</strong> Capa de dominio donde se encuentran las reglas de negocio y lógica principal de la aplicación, independiente de frameworks o detalles de implementación.</li>
  <ul>
    <li><strong>/interfaces:</strong> Define las interfaces necesarias para la comunicación entre la capa de dominio y la capa de datos.</li>
    <li><strong>/models:</strong> Contiene modelos específicos para esta capa, representando la lógica de negocio.</li>
    <li><strong>/usecase:</strong> Contiene los casos de uso que encapsulan las acciones o funcionalidades principales de la aplicación, usando <strong>Flows</strong> y <strong>Coroutines</strong> para el manejo de flujos de datos.</li>
  </ul>

  <li><strong>/presentation:</strong> Capa de presentación responsable de la interfaz de usuario, utilizando <strong>Jetpack Compose</strong> y otras herramientas de diseño modernas.</li>
  <ul>
    <li><strong>/components:</strong> Contiene los elementos reutilizables de la UI, divididos en subcarpetas como <code>drawer</code>, <code>scaffold</code>, <code>searchBar</code>, entre otros.</li>
    <ul>
      <li><strong>/component:</strong> Subcarpeta dentro de algunos componentes clave que contiene los elementos reutilizables de cada uno, como en <code>searchBar/component</code>.</li>
      <li><strong>/shared:</strong> Almacena los componentes de UI que son compartidos en varias partes de la app.</li>
    </ul>
    <li><strong>/models:</strong> Incluye modelos de UI específicos para esta capa, junto con mappers para transformar modelos de dominio a modelos de presentación.</li>
    <li><strong>/mappers:</strong> Mapea modelos entre las capas de dominio y presentación, permitiendo la independencia entre ellas.</li>
    <li><strong>/navigation:</strong> Gestiona la navegación de la aplicación, estructurada con <strong>Navigation Compose</strong>.</li>
    <li><strong>/screens:</strong> Contiene las pantallas principales de la app (como <code>explorer</code>, <code>list</code>, <code>lists</code>, <code>login</code>, <code>movie</code>, <code>watched</code>), cada una estructurada para facilitar la modularidad.</li>
    <ul>
      <li><strong>/component:</strong> Subcarpeta dentro de algunas pantallas que organiza los componentes específicos de cada pantalla, como en <code>lists/component</code>.</li>
    </ul>
    <li><strong>/ui.theme:</strong> Configura los temas de la app, incluyendo el soporte para <strong>Material 3</strong> y modos claro/oscuro.</li>
  </ul>

  <li><strong>/utils:</strong> Incluye clases y funciones auxiliares que se utilizan en varias capas de la aplicación.</li>
</ul>

<h3>Otras Carpetas</h3>
<ul>
  <li><strong>/di:</strong> Configuración de <strong>Koin</strong> para la inyección de dependencias, permitiendo la instancia única de servicios y ViewModels.</li>
</ul>

<h2>📄 Descripción General de la Arquitectura</h2>
<p>La arquitectura de MovieLists App sigue los principios de Clean Architecture, donde cada capa tiene su responsabilidad claramente definida:</p>
<ul>
  <li><strong>Capa de Datos (Data):</strong> Gestiona las fuentes de datos y maneja la lógica de transformación de datos con mappers. Utiliza Ktor para llamadas de red, DataStore para el almacenamiento de preferencias, y Coil para carga de imágenes.</li>
  <li><strong>Capa de Dominio (Domain):</strong> Implementa la lógica de negocio de la aplicación. Los casos de uso permiten que la capa de presentación interactúe con los datos de manera estructurada, empleando Flows y Coroutines para la manipulación de datos asíncrona.</li>
  <li><strong>Capa de Presentación (Presentation):</strong> Controla la lógica de UI y los eventos del usuario. Organiza la UI en componentes y pantallas modulares, con Jetpack Compose y Material 3 para una experiencia moderna y responsive.</li>
</ul>
<p>Con esta estructura, MovieLists App garantiza que cada capa sea fácilmente testeable y reutilizable, optimizando la app para su mantenimiento y escalabilidad.</p>

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

<!-- Continúa con las secciones de endpoints, tecnologías, arquitectura, etc., como se muestra en la versión anterior -->
