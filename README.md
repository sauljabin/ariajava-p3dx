AriaJava P3DX
=============

Descripción
-----------
Algoritmos de navegación para robot Pioneer P3DX usando la Librería ARIA http://robots.mobilerobots.com/wiki/ARIA y el API Java.

- Copyright: 2014 Saúl Piña <sauljabin@gmail.com>, Jorge Parra <thejorgemylio@gmail.com>
- Repository: https://github.com/sauljabin/ariajava-p3dx
- License: MIT
- Language: Java
- Ubuntu

Documentación
-------------
[Informe](documents/informe/articulo.pdf)

Instalación
-----------

Instalar [ARIA](http://robots.mobilerobots.com/wiki/ARIA), [ARIA Java](http://robots.mobilerobots.com/wiki/ARIA) y [MobileSim](http://robots.mobilerobots.com/wiki/MobileSim).

Configurar las variables de entorno:

```
export ARIA_PATH=/usr/local/Aria
export LD_LIBRARY_PATH=/lib:/usr/lib:/usr/local/lib:$ARIA_PATH/lib
```

Iniciar Aplicación
------------------

Abrir MobileSim con el mapa `documents/maps/PlantaAltaModuloK.map`,
ejecutar la aplicación con `make run` y conectar.

Screenshots
------------
![](documents/screenshots/screenshot-1.png)

![](documents/screenshots/screenshot-2.png)

![](documents/screenshots/screenshot-3.png)

![](documents/screenshots/screenshot-4.png)

