# Sistema de Gestión de Inventario

## Descripción
Sistema de gestión de inventario desarrollado en Java que permite administrar productos, registrar ventas, generar reportes en PDF y mantener un registro completo de bitácora de todas las operaciones realizadas.

## Características Principales
- Gestión completa de productos (crear, buscar, eliminar)
- Sistema de ventas con control de stock
- Generación de reportes en PDF (inventario y ventas)
- Bitácora de operaciones con registro de éxito/error
- Interfaz de consola intuitiva
- Validación de datos de entrada

## Requisitos del Sistema
- Java JDK 8 o superior
- Biblioteca iTextPDF (para generación de PDFs)
- Sistema operativo: Windows, Linux o macOS

## Instalación y Configuración

### 1. Instalar Java
Verifique que tiene Java instalado:

java -version
Si no tiene Java, descárguelo e instálelo desde Oracle JDK o OpenJDK

### 2. Descargar iTextPDF
Descargar la biblioteca iTextPDF desde:
https://github.com/itext/itextpdf

### 3. Compilar el Proyecto en Netbeans o bien:

# Navegar a la carpeta src
cd Proyecto1/src

# Compilar todos los archivos (ajustar la ruta de itextpdf)
javac -cp .:itextpdf-5.5.13.3.jar *.java


### 4. Ejecutar la Aplicación
java -cp .:itextpdf-5.5.13.3.jar main

# Uso del Programa
Menú Principal
Al ejecutar el programa, se mostrará un menú con las siguientes opciones:

-Agregar Producto: Añadir nuevo producto al inventario

-Buscar Producto: Buscar producto por código

-Eliminar Producto: Eliminar producto del inventario

-Registrar Venta: Registrar venta de producto

-Ver Inventario: Mostrar todos los productos

-Ver datos del estudiante: Mostrar información del desarrollador

-Ver Bitácora: Mostrar registro de operaciones

-Generar reporte de stock: Crear PDF con inventario actual

-Generar reporte de inventario: Crear PDF con historial de ventas

# Flujo de Trabajo Típico
-Agregar productos al inventario

-Registrar ventas cuando ocurran

-Consultar el inventario cuando sea necesario

-Generar reportes PDF para análisis

-Revisar la bitácora para auditoría de operaciones



# Validaciones Implementadas
-Código de producto único

-Nombre y categoría no vacíos

-Precio mayor que cero

-Cantidad no negativa

-Stock suficiente para ventas

-Control de errores en entrada de datos

# Formatos de Archivo
-ventas.txt: Registro histórico de ventas

-PDF reports: Reportes con timestamp en el nombre (dd_MM_yyyy_HH_mm_ss)

# Limitaciones Conocidas
-Límite de 100 productos en inventario

-Límite de 200 registros en bitácora

-No persistencia entre ejecuciones (excepto ventas.txt)

-Requiere instalación manual de iTextPDF

# Solución de Problemas
-Error: "Class not found" o "Package not found"
Verifique que iTextPDF esté en el classpath

Confirme la versión de la biblioteca

-Error al generar PDF
Verifique permisos de escritura en el directorio

Confirme que iTextPDF esté correctamente instalado

-El programa no encuentra archivos
Ejecute desde el directorio correcto

Verifique que los archivos .java estén compilados


