# StreetSneakers
Repositorio para la práctica de Desarrollo de Aplicaciones Distribuida (DAD) de la URJC.

## Integrantes
Alberto Martín Amengual:  
:octocat: [Albertomartin21](https://github.com/Albertomartin21)  
:envelope: a.martinam.2017@alumnos.urjc.es  
Fátima Ezahra Smounat Mahidar:  
:octocat: [ezi2000](https://github.com/ezi2000)  
:envelope: fe.smounat.2018@alumnos.urjc.es  
Raúl Heredia Horcajo:  
:octocat: [raulhh2000](https://github.com/raulhh2000)  
:envelope: r.heredia.2018@alumnos.urjc.es


## :open_book:Tabla de contenido

- [FASE 1](#fase-1)
    - [Descripción de la aplicación web](#descripción-de-la-aplicación-web)
    - [Funcionalidades](#funcionalidades)
    - [Entidades](#entidades)
    - [Funcionalidades del servicio interno](#funcionalidades-del-servicio-interno)
- [Licencia](#licencia)

## FASE 1

### Descripción de la aplicación web
`StreetSneakers` es una aplicación web de consulta :newspaper: y venta online :shopping_cart: de las mejores zapatillas :athletic_shoe: del mercado.

### Funcionalidades
```
-Funcionalidades públicas: El usuario al acceder a la página sin registrarse va a poder ver todas las
zapatillas en venta y además consultar las últimas novedades del mundo de las Sneakers.

-Funcionalidades privadas: El usuario una vez registrado tendrá acceso a realizar pedidos, consultar sus
últimos pedidos, tener una lista de deseados, hacer reseñas de zapatillas y hablar por un chat compartido
entre usuarios de la web.
```
### Entidades
```
-Administrador: Representa a los administradores del sistema. Realizarán la gestión tanto de los productos
como de las cuentas del sistema.

-Usuario: Representa a los clientes del sistema.

-Producto: Representa a los productos del sistema.

-Carrito: Representa a la cesta de productos que desea comprar el usuario.

-Pedido: Representa el pedido con los productos seleccionados por el usuario.

-Reseña: Representa a la reseña de un producto realizada por un usuario.

-Noticia: Representa a las noticias del sistema.
```
### Funcionalidades del servicio interno
```
-Envío de correo electrónico al usuario tras registrarse y al realizar un pedido.

-Generación de una factura del pedido realizado por el usuario en formato PDF.

-Chat entre todos los usuarios del sistema.
```
## Licencia
Este proyecto está bajo la licencia `Apache License 2.0`. Mira el archivo [LICENSE](LICENSE) para más detalles.