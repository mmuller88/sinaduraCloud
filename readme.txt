-----------------------
Sinadura Cloud
-----------------------

Las clases del model (los VOs) se están copiando de forma manual tanto en SinaduraDesktop, como
en sinaduraCloudTest. Es decir, las clases del siguiente path:
"/src/net/zylk/sinadura/cloud/model/"

TODO Habría que hacer un jar con dichas clases, y publicarlo en el nexus con maven para poder
declarar la dependencia en dichos proyectos. 



Para probar la instalación del servicio:
http://alf4.zylk.net:8080/sinaduraCloud/rest/v1/version/get