# demoAdminLogs
Aplicació de demo amb el nou Mòdul d'Administració de Logs

Instal·lació

Des de l’Eclipse descarregar el projecte:

File -> Import -> Projects from Git -> Clone URI https://github.com/gencat/demoAdminLogs.git (introduïr unes credencials vàlides de Github)
-> Sel·lecccionar branch “master” -> Sel·leccionar directori “/home/canigo/Documents/workspace-sts-3.7.1.RELEASE” com a Destinations 
-> Sel·leccionar l’opció “Import Existing Eclipse projects” -> Finish

Construïr l’aplicació:

Run as… -> Maven build -> Configurar Base Directory “${workspace_loc:/demoAdminLogs}“, Goals “clean package”-> Executar

Adaptar els paths “home/canigo/…” del fitxer “<workspace_eclipse>/<demoAdminLogs>/src/main/docker/docker-compose.yml” als paths locals
en cas de no haver fer servit els noms per defecte:

<workspace_eclipse>=/home/canigo/Documents/workspace-sts-3.7.1.RELEASE
<demoAdminLogs>=demoAdminLogs

Executar la demo des de linia de comandes:

$ cd <workspace_eclipse>/<demoAdminLogs>/src/main/docker/
$ docker-compose up
O bé des d’Eclipse:

Run -> External tools -> External Tools Configurations… -> Program - New launch configuration 
-> Name “demoAdminLogs” -> Location: “/usr/local/bin/docker-compose”, 
Working Directory: “${workspace_loc:/<demoAdminLogs>/src/main/docker/}“, Arguments: “up” -> Executar

Accedir amb el Chrome a la URL http://localhost:8080/demoAdminLogs

La forma més fàcil d’eliminar els contenidors en execució, i per tant aturar l’aplicació, és executar la següent comanda:

$ docker rm -f $(docker ps -qa)
