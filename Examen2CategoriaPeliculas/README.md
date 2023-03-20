# IndevelUpChallenge
IdevelUp Movie Database es una app con 3 listas de peliculas segun las siguientes categorias: : Popular, Top Rated, Upcoming y brinda la posibildad de visualizar los detalles de cada una ellas, incluyendo Sinopsis, Cast y Trailer. 
Ademas permite realizar busquedas online y offline, por categorías, gracias a la utilizacion de una base de datos local.
Consume recursos de la Api de [TheMovieDB.org](https://api.themoviedb.org)

### Este proyecto utiliza:
* Java
* MVVM
* Retrofit
* LiveData
* Room
* Glide
* OKHttp
* RxJava
* Mockito
* MAterial Design
* y mas


### Capas de la aplicación:


* **Vistas:** Esta capa, tambien conocida como interfaz de usuario, compone la información que se envía al cliente y los mecanismos interacción con éste. 


| Clase | Función |
| ------ | ------ |
| SplashActivity | Actividad inicial con el fin de mostrar una animacion al iniciar la aplicación por 3 segundos, luego da lugar al MainActivity |
| MainActivity | Actividad principal de la aplicación, contiene los Fragment MainFragment y SearchFragment |
| DetailsActivity | Actividad desde donde se instancia DetailsFragment con el objeto Movie como argumento (recibido del MainActivity a traves de un bundle)|
| MainFragment | Fagment principal donde se realizan los pedidos de las listas de las 3 categorias (POPULAR, TOP RATED y UPCOMING) mediante MovieResponseViewModel, contiene ademas un Viewpager con pestañas para las distintas categorias, en donde se ubican los MovieFragments |
| MovieFragment | Visualiza un RecyclerView con la lista resultante de peliculas y, ademas, una SearchView para realizar busquedas tanto online como offline |
| DetailsFragment | Donde se realizan los pedidos a MovieCreditsviewmodel para obtener los datos del cast y los creditos y se muestran por pantalla las respuestas observadas y los detalles de las peliculas, tambien mediante MovieVideosViewmodel se obtiene, en caso de tener conexion, la url del trailer que se visualizará en el YouTubePlayer que contiene la vista |
| SearchResponseFrgment | Visualiza un RecyclerView con el resultado de la busqueda online |


* **Negocio:** En esta capa se gestiona la lógica de la aplicación. Es donde se dice que se hace con los datos. Estará conectada con la capa de persistencia y red para poder realizar sus funciones.


| Clase | Función |
| ------ | ------ |
| MovieResponse | Modelo de respuesta obtenida al hacer un pedido de listas de peliculas, tambien define el modelo de la movie_table de la base de datos local |
| Movie | Esta clase define los atributos de la pelicula: id, title, overview y otros  |
| MovieCreditsResponse,  | Modelo de respuesta obtenida al hacer un pedido de creditos de una pelicula  |
| MovieVideosResponse | Modelo de la respuesta obtenida al hacer un pedido de la url del trailer de la pelicula  |
| MovieResponseRepository | Modulo que implementa el acceso y logica del modelo de negocio contiene dos Data Sources, Network y Database  |
| MovieCreditsRepository | Maneja las operaciones de datos para la obtención de lo creditos de la pelicula mediante su id  |
| MovieVideoRepository | Manejan las operaciones de datos para la obtención de la url del trailer de la pelicula  |


* **Persistencia:** Esta capa se encarga de guardar los datos. Será donde se gestione todo lo relativo a la base de datos y a la creación, edición y borrado de datos de ésta.


| Clase | Función |
| ------ | ------ |
| MovieDao | Proporcionará los métodos necesarios para insertar, actualizar, borrar y consultar la Base de datos local |
| MovieDatabase | Donde see crea la base de datos y se obtiene una instancia |


* **Red:** Esta capa proporciona las herramientas para conseguir los datos de una fuente externa a través de los servicios de red.


| Clase | Función |
| ------ | ------ |
| MovieDBApiService | En MovieDBApiService se define la construccion URL. La aplicación trabaja con Retrofit para gestionar la obtención de datos |
| RetrofitInstance | Proporciona la instancia de retrofit |


### Principio de responsabilidad única:

Es uno de los cinco principios básicos de la programación orientada a objetos, SOLID: "Cada objeto en el sistema deben tener una simple responsabilidad, y todos los servicios de los objetos deben cumplir con esa simple responsabilidad"
Es decir, una clase debería tener una única razón para cambiar. Gracias a ello, el código creado será más sencillo de leer, testear y mantener.

### Caracteristicas de un codigo limpio:

* Que permita la realizacion de test rapidos y repetibles, con repuestas true or false para definir claramente el error y tambien independiente, con el fin de evitar que cause efecto cascada cuando ocurra alguna falla y asi facilitar su analisis. Ademas idealmente deben ser escritos antes que el codigo de producción.
* Que contenga comentarios solo en caso de ser necesarios para evitar el exceso y la confusión que conlleva
* Evitar codigo repetido, es decir, dos o mas partes del programa que realicen la misma función
* Crear funciones/metodos lo mas breves posible
* Limpiar el codigo, eliminar el codigo en desuso, corregir errores y duplicados
* Los nombres de las variables, funciones, metodos o clases deben ser claros y precisos. Debe tener relación con su funcion
* Debe regir el principio "Open Closed": "una clase debe estar abierta a extensiones pero cerrada a modificaciones" para evitar efectos colaterales a la hora de necesitar extender el comportamiento de una clase.


