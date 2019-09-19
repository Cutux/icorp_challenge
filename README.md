# Reto Tech DevOps
Proyecto challenge propuesto por intercorp.

El siguiente consiste en una aplicacion CRUD el cual expone los siguientes endpoints:

  - /cruddemo/v1/create :                Crea una entidad de persona y la registra en MongoDB.
  - /cruddemo/v1/get/{person-dni} :      Busca a la persona correspondiente al dni indicado.
  - /cruddemo/v1/getAll :                Busca a todas las personas registradas.
  - /cruddemo/v1/update :                Actualiza los datos de una persona.
  - /cruddemo/v1/delete/{person-dni} :   Elimina a la persona correspondiente al dni indicado.
  - /cruddemo/v1/deleteAll :             Elimina a todas las personas registradas.

Para crear la infraestructura ejecutar:
  - terraform init
  - terraform plan
  - terraform apply

  Se creara una instanca EC2 con Docker y un contenedor de Jenkins el cual debera ser configurado manualmente.
  Al crear la instancia EC2 se descargara una copia de la llave privada (.pem) en la ruta /terraform/icorp.pem, se deberan asignar permisos 400 antes de ingresar a la instancia.
  - chmod 400 icorp.pem

Por ultimo crear el job de Jenkins utilizando el Jenkinsfile ya creado y probar.
